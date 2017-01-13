package com.microsoft.azure.docker.intellij.docker.ui.dialogs;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.microsoft.azure.docker.resources.DockerHost;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;
import com.microsoft.azure.docker.ui.EditableDockerHost;
import com.microsoft.intellij.util.PluginUtil;
import org.jdesktop.swingx.JXHyperlink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;

public class AzureViewDockerDialog extends DialogWrapper {
  private final String defaultTitle = "Docker Host Details";
  public static final int OK_EXIT_CODE = 0;
  public static final int CANCEL_EXIT_CODE = 1;
  public static final int CLOSE_EXIT_CODE = 1;
  public static final int UPDATE_EXIT_CODE = 3;
  private JPanel mainPanel;
  private JLabel dockerHostNameLabel;
  private JTabbedPane tabbedPane1;
  private JLabel dockerHostUsernameLabel;
  private JLabel dockerHostPwdLoginLabel;
  private JLabel dockerHostSshLoginLabel;
  private JLabel dockerHostTlsAuthLabel;
  private JLabel dockerHostKeyvaultLabel;
  private JXHyperlink dockerHostAuthUpdateHyperlink;
  private JXHyperlink dockerHostSshExportHyperlink;
  private JXHyperlink dockerHostTlsExportHyperlink;
  private JLabel dockerHostOSTypeLabel;
  private JLabel dockerHostVMSizeLabel;
  private JLabel dockerHostRGNameLabel;
  private JLabel dockerHostVnetNameAddrLabel;
  private JLabel dockerHostSubnetNameAddrLabel;
  private JLabel dockerHostStorageNameTypeLabel;
  private JLabel dockerHostUrlLabel;
  private JLabel dockerHostLocationLabel;
  private JLabel dockerHostStatusLabel;
  private JLabel dockerHostPortLabel;

  private Action myClickApplyAction;
  private Project project;
  private EditableDockerHost editableHost;
  private AzureDockerUIManager dockerUIManager;
  private int exitCode;

  private void initDefaultUIValues(DockerHost dockerHost, String isUpdated) {
    // Docker VM info
    dockerHostNameLabel.setText(dockerHost.name);
    dockerHostUrlLabel.setText(dockerHost.apiUrl);
    dockerHostLocationLabel.setText(dockerHost.hostVM.region);
    dockerHostStatusLabel.setText((isUpdated != null && editableHost.originalDockerHost.state != dockerHost.state) ?
        dockerHost.state.toString() + isUpdated :
        dockerHost.state.toString()
    );

    // Docker VM settings
    dockerHostOSTypeLabel.setText(dockerHost.hostOSType.toString());
    // TODO: enable resizing of the current VM -> see VirtualMachine::availableSizes() and update.withSize();
    dockerHostVMSizeLabel.setText((isUpdated != null && !editableHost.originalDockerHost.hostVM.vmSize.equals(dockerHost.hostVM.vmSize)) ?
        dockerHost.hostVM.vmSize + isUpdated :
        dockerHost.hostVM.vmSize
    );
    dockerHostRGNameLabel.setText(dockerHost.hostVM.resourceGroupName);
    dockerHostVnetNameAddrLabel.setText(String.format("%s (%s)", dockerHost.hostVM.vnetName, dockerHost.hostVM.vnetAddressSpace));
    dockerHostSubnetNameAddrLabel.setText(String.format("%s (%s)", dockerHost.hostVM.subnetName, dockerHost.hostVM.subnetAddressRange));
    dockerHostStorageNameTypeLabel.setText(String.format("%s (%s)", dockerHost.hostVM.storageAccountName, dockerHost.hostVM.storageAccountType));

    // Docker VM log in settings
    dockerHostAuthUpdateHyperlink.setEnabled(!dockerHost.isUpdating);
    dockerHostUsernameLabel.setText((isUpdated != null && !editableHost.originalDockerHost.certVault.userName.equals(dockerHost.certVault.userName)) ?
        dockerHost.certVault.userName + isUpdated :
        dockerHost.certVault.userName
    );
    dockerHostPwdLoginLabel.setText((isUpdated != null && editableHost.originalDockerHost.hasPwdLogIn != dockerHost.hasPwdLogIn) ?
        (dockerHost.hasPwdLogIn ? "Yes" : "No") + isUpdated :
        (dockerHost.hasPwdLogIn ? "Yes" : "No")
    );
    dockerHostSshLoginLabel.setText((isUpdated != null && editableHost.originalDockerHost.hasSSHLogIn != dockerHost.hasSSHLogIn) ?
        (dockerHost.hasSSHLogIn ? "Yes" : "No") + isUpdated :
        (dockerHost.hasSSHLogIn ? "Yes" : "No")
    );
    dockerHostSshExportHyperlink.setEnabled(!dockerHost.isUpdating && dockerHost.hasSSHLogIn);

    // Docker Daemon settings
    dockerHostTlsAuthLabel.setText((isUpdated != null && editableHost.originalDockerHost.isTLSSecured != dockerHost.isTLSSecured) ?
        (dockerHost.hasSSHLogIn ? "Using TLS certificates" : "Open/unsecured access") + isUpdated :
        (dockerHost.hasSSHLogIn ? "Using TLS certificates" : "Open/unsecured access")
    );
    dockerHostTlsExportHyperlink.setEnabled(!dockerHost.isUpdating && dockerHost.isTLSSecured);

    dockerHostPortLabel.setText((isUpdated != null && !editableHost.originalDockerHost.port.equals(dockerHost.port)) ?
            dockerHost.port + isUpdated :
            dockerHost.port
    );

    // Docker Keyvault settings
    dockerHostKeyvaultLabel.setText((isUpdated != null && editableHost.originalDockerHost.hasKeyVault != dockerHost.hasKeyVault) ?
        (dockerHost.hasKeyVault ? dockerHost.certVault.url : "Not using Key Vault") + isUpdated :
        (dockerHost.hasKeyVault ? dockerHost.certVault.url : "Not using Key Vault")
    );

    exitCode = CLOSE_EXIT_CODE;

//    myClickApplyAction.setEnabled(!editableHost.originalDockerHost.equalsTo(dockerHost));
  }

  public AzureViewDockerDialog(Project project, EditableDockerHost host, AzureDockerUIManager uiManager) {
    super(project, true);

    this.project = project;
    this.editableHost = host;
    this.dockerUIManager = uiManager;
    setModal(true);

    init();
    dockerHostAuthUpdateHyperlink.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onUpdateLoginCreds();
      }
    });
    dockerHostSshExportHyperlink.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onExportSshKeys();
      }
    });
    dockerHostTlsExportHyperlink.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onExportTlsCerts();
      }
    });

    initDefaultUIValues(editableHost.originalDockerHost, null);
    setTitle(defaultTitle);

    dockerHostSshExportHyperlink.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onExportSshKeys();
      }
    });

    dockerHostTlsExportHyperlink.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onExportTlsCerts();
      }
    });
  }


  private void onUpdateLoginCreds() {
    exitCode = UPDATE_EXIT_CODE;
    doOKAction();

//    if (editDockerLoginCredsDialog.getExitCode() == 0 && !editableHost.originalDockerHost.equalsTo(editableHost.updatedDockerHost)) {
//      initDefaultUIValues(editableHost.updatedDockerHost, " (changed)");
//      this.setTitle(defaultTitle + " (Changed)");
//      myClickApplyAction.setEnabled(true);
//    }
  }

//  private void onUpdateDockerPort() {
//    editableHost.updatedDockerHost.port = dockerHostPortTextField.getText();
//    this.setTitle(defaultTitle + " (Changed)");
//    myClickApplyAction.setEnabled(true);
//  }

  private void onExportSshKeys() {
    if (editableHost.updatedDockerHost.hasSSHLogIn && editableHost.updatedDockerHost.certVault != null) {
      AzureExportDockerSshKeysDialog exportDockerSshKeysDialog = new AzureExportDockerSshKeysDialog(project, editableHost.updatedDockerHost.certVault);
      exportDockerSshKeysDialog.show();
    }
  }

  private void onExportTlsCerts() {
    if (editableHost.updatedDockerHost.isTLSSecured && editableHost.updatedDockerHost.certVault != null) {
      AzureExportDockerTlsKeysDialog exportDockerTlsKeysDialog = new AzureExportDockerTlsKeysDialog(project, editableHost.updatedDockerHost.certVault);
      exportDockerTlsKeysDialog.show();
    }
  }

  public int getInternalExitCode() {
    return exitCode;
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    return mainPanel;
  }

  @Nullable
  @Override
  protected String getHelpId() {
    return null;
  }

//  @Nullable
//  @Override
//  protected Action[] createActions() {
//    myClickApplyAction = new ClickApplyAction();
//    myClickApplyAction.setEnabled(false);
//    return new Action[] {getCancelAction(), myClickApplyAction, getOKAction()};
//  }
//
//  protected class ClickApplyAction extends DialogWrapper.DialogWrapperAction {
//    protected ClickApplyAction() {
//      super("Apply");
//    }
//
//    protected void doAction(ActionEvent e) {
//      ValidationInfo info = doClickApplyValidate();
//      if(info != null) {
//        DialogShaker(info);
//      } else {
//        doClickApplyAction();
//      }
//    }
//  }

  @Nullable
  @Override
  protected void doOKAction() {
    super.doOKAction();
  }



}
