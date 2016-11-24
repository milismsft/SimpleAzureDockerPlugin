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
import java.awt.event.*;

public class AzureEditDockerDialog extends DialogWrapper {
  private final String defaultTitle = "View Docker Host Details";
  private JPanel mainPanel;
  private JLabel dockerHostNameLabel;
  private JCheckBox dockerHostUpdateStatusCheckBox;
  private JComboBox dockerHostStatusComboBox;
  private JCheckBox dockerHostEditPortCheckBox;
  private JTextField dockerHostPortTextField;
  private JTabbedPane tabbedPane1;
  private JCheckBox dockerHostPwdAuthCheckBox;
  private JCheckBox dockerHostSshAuthCheckBox;
  private JXHyperlink dockerHostSshUpdateHyperlink;
  private JXHyperlink dockerHostSshExportHyperlink;
  private JRadioButton dockerHostOpenDaemonRadioButton;
  private JRadioButton dockerHostTlsAuthRadioButton;
  private JLabel dockerHostUsernameLabel;
  private JXHyperlink dockerHostTlsExportHyperlink;
  private JXHyperlink dockerHostTlsUpdateHyperlink;
  private JCheckBox dockerHostKeyvaultCheckBox;
  private JXHyperlink dockerHostKeyvaultUpdateHyperlink;
  private JLabel dockerHostKeyvaultLabel;
  private JLabel dockerHostOSTypeLabel;
  private JLabel dockerHostVMSizeLabel;
  private JLabel dockerHostRGNameLabel;
  private JLabel dockerHostVnetNameAddrLabel;
  private JLabel dockerHostSubnetNameAddrLabel;
  private JLabel dockerHostStorageNameTypeLabel;
  private JLabel dockerHostUrlLabel;
  private JLabel dockerHostLocationLabel;

  private Action myClickApplyAction;
  private Project project;
  private EditableDockerHost editableHost;

  private void initDefaultUIValues() {
    DockerHost dockerHost = editableHost.originalDockerHost;
    dockerHostNameLabel.setText(dockerHost.name);
    dockerHostUrlLabel.setText(dockerHost.apiUrl);
    dockerHostLocationLabel.setText(dockerHost.hostVM.region);

  }

  public AzureEditDockerDialog(Project project, EditableDockerHost host) {
    super(project, true);

    this.project = project;
    this.editableHost = host;
    setModal(true);
    setTitle("View Docker Host Details");

    init();
    dockerHostSshUpdateHyperlink.addActionListener(new ActionListener() {
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
    dockerHostTlsUpdateHyperlink.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onUpdateTlsCerts();
      }
    });
    dockerHostTlsExportHyperlink.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onExportTlsCerts();
      }
    });
    dockerHostKeyvaultUpdateHyperlink.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onUpdateKeyvault();
      }
    });

    initDefaultUIValues();
  }

  private void onUpdateLoginCreds() {
    this.setTitle(defaultTitle + " (Updated)");
    myClickApplyAction.setEnabled(true);
  }

  private void onExportSshKeys() {
    this.setTitle(defaultTitle + " (Updated)");
    myClickApplyAction.setEnabled(true);
  }

  private void onUpdateTlsCerts() {
    this.setTitle(defaultTitle + " (Updated)");
    myClickApplyAction.setEnabled(true);
  }

  private void onExportTlsCerts() {
    this.setTitle(defaultTitle + " (Updated)");
    myClickApplyAction.setEnabled(true);
  }

  private void onUpdateKeyvault() {
    this.setTitle(defaultTitle + " (Updated)");
    myClickApplyAction.setEnabled(true);
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
  }

  private void DialogShaker(ValidationInfo info) {
    PluginUtil.DialogShaker(info, this);
  }

}
