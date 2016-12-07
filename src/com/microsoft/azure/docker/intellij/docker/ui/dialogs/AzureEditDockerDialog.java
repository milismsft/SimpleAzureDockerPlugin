package com.microsoft.azure.docker.intellij.docker.ui.dialogs;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.microsoft.azure.docker.resources.DockerHost;
import com.microsoft.azure.docker.ui.EditableDockerHost;
import com.microsoft.intellij.util.PluginUtil;
import org.jdesktop.swingx.JXHyperlink;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataListener;
import java.awt.event.*;

public class AzureEditDockerDialog extends DialogWrapper {
  private final String defaultTitle = "Docker Host Details";
  private JPanel mainPanel;
  private JLabel dockerHostNameLabel;
  private JTextField dockerHostPortTextField;
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

  private Action myClickApplyAction;
  private Project project;
  private EditableDockerHost editableHost;

  private void initDefaultUIValues() {
    DockerHost dockerHost = editableHost.originalDockerHost;

    // Docker VM info
    dockerHostNameLabel.setText(dockerHost.name);
    dockerHostUrlLabel.setText(dockerHost.apiUrl);
    dockerHostLocationLabel.setText(dockerHost.hostVM.region);
    dockerHostStatusLabel.setText(dockerHost.state.toString());

    // Docker VM settings
    dockerHostOSTypeLabel.setText(dockerHost.hostOSType.toString());
    dockerHostVMSizeLabel.setText(dockerHost.hostVM.vmSize);
    // TODO: enable resizing of the current VM -> see VirtualMachine::availableSizes() and update.withSize();
    dockerHostVMSizeLabel.setText(dockerHost.hostVM.resourceGroupName);
    dockerHostVnetNameAddrLabel.setText(String.format("%s (%s)", dockerHost.hostVM.vnetName, dockerHost.hostVM.vnetAddressSpace));
    dockerHostSubnetNameAddrLabel.setText(String.format("%s (%s)", dockerHost.hostVM.subnetName, dockerHost.hostVM.subnetAddressRange));
    dockerHostStorageNameTypeLabel.setText(String.format("%s (%s)", dockerHost.hostVM.storageAccountName, dockerHost.hostVM.storageAccountType));

    // Docker VM log in settings
    dockerHostUsernameLabel.setText(dockerHost.certVault.userName);
    dockerHostPwdLoginLabel.setText(dockerHost.hasPwdLogIn ? "Yes" : "No");
    dockerHostSshLoginLabel.setText(dockerHost.hasSSHLogIn ? "Yes" : "No");
    dockerHostSshExportHyperlink.setEnabled(dockerHost.hasSSHLogIn);

    // Docker Daemon settings
    dockerHostTlsAuthLabel.setText(dockerHost.isTLSSecured ? "Using TLS certificates" : "Open/unsecured access");
    dockerHostTlsExportHyperlink.setEnabled(dockerHost.isTLSSecured);
    dockerHostPortTextField.setEnabled(true);
    dockerHostPortTextField.setText(dockerHost.port);

    // Docker Keyvault settings
    dockerHostKeyvaultLabel.setText(dockerHost.hasKeyVault ? dockerHost.certVault.url : "Not using Key Vault");

    myClickApplyAction.setEnabled(false);

  }

  public AzureEditDockerDialog(Project project, EditableDockerHost host) {
    super(project, true);

    this.project = project;
    this.editableHost = host;
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

    dockerHostPortTextField.getDocument().addDocumentListener(getUpdateDockerPortDocumentListener());

    initDefaultUIValues();
    setTitle("Docker Host Details");
  }

  private DocumentListener getUpdateDockerPortDocumentListener() {
    return new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        onUpdateDockerPort();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        onUpdateDockerPort();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        onUpdateDockerPort();
      }
    };
  }

  private void onUpdateLoginCreds() {
    this.setTitle(defaultTitle + " (Updated)");
    myClickApplyAction.setEnabled(true);
  }

  private void onUpdateDockerPort() {
    this.setTitle(defaultTitle + " (Updated)");
    myClickApplyAction.setEnabled(true);
  }

  private void onExportSshKeys() {
  }

  private void onExportTlsCerts() {
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

  @Nullable
  @Override
  protected Action[] createActions() {
    myClickApplyAction = new ClickApplyAction();
    myClickApplyAction.setEnabled(false);
    return new Action[] {getCancelAction(), myClickApplyAction, getOKAction()};
  }

  protected class ClickApplyAction extends DialogWrapper.DialogWrapperAction {
    protected ClickApplyAction() {
      super("Apply");
    }

    protected void doAction(ActionEvent e) {
      ValidationInfo info = doClickApplyValidate();
      if(info != null) {
        DialogShaker(info);
      } else {
        doClickApplyAction();
      }
    }
  }

  private ValidationInfo doClickApplyValidate() {
    return new ValidationInfo("Apply is not implemented", this.getButton(getOKAction()));
  }

  private void doClickApplyAction() {
    if (doClickApplyValidate() != null) {
      return;
    }
  }

  private void DialogShaker(ValidationInfo info) {
    PluginUtil.DialogShaker(info, this);
  }

}
