package com.microsoft.azure.docker.intellij.docker.ui.forms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;
import com.microsoft.azure.docker.ui.EditableDockerHost;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AzureDockerHostUpdateLoginPanel {
  private JPanel contentPane;
  private JPanel mainPanel;
  private JRadioButton dockerHostUsePwdRadioButton;
  private JRadioButton dockerHostUseSSHCertificatesRadioButton;
  private JRadioButton dockerHostAutoSshRadioButton;
  private JRadioButton dockerHostImportSshRadioButton;
  private JRadioButton dockerHostImportKeyvaultCredsRadioButton;
  private JTextField dockerHostUsernameTextField;
  private JPasswordField dockerHostFirstPwdField;
  private JPasswordField dockerHostSecondPwdField;
  private JComboBox dockerHostImportKeyvaultComboBox;
  private JRadioButton dockerHostKeepCurrentRadioButton;
  private JLabel dockerHostCurrentUserLabel;
  private TextFieldWithBrowseButton dockerHostImportSSHBrowseTextField;
  private JLabel dockerHostCurrentPwdLabel;
  private JLabel dockerHostCurrentSshLabel;
  private ButtonGroup mainSelectionGroup;
  private ButtonGroup authSelectionGroup;


  private Project project;
  private EditableDockerHost editableHost;
  private AzureDockerUIManager dockerUIManager;

  public AzureDockerHostUpdateLoginPanel(Project project, EditableDockerHost editableHost, AzureDockerUIManager dockerUIManager) {
    this.project = project;
    this.editableHost = editableHost;
    this.dockerUIManager = dockerUIManager;

    mainSelectionGroup = new ButtonGroup();
    mainSelectionGroup.add(dockerHostKeepCurrentRadioButton);
    mainSelectionGroup.add(dockerHostUsePwdRadioButton);
    mainSelectionGroup.add(dockerHostUseSSHCertificatesRadioButton);
    mainSelectionGroup.add(dockerHostImportKeyvaultCredsRadioButton);

    authSelectionGroup = new ButtonGroup();
    authSelectionGroup.add(dockerHostAutoSshRadioButton);
    authSelectionGroup.add(dockerHostImportSshRadioButton);

    initDefaultUI();

    dockerHostKeepCurrentRadioButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onSelectKeepCurrentRadioButton();
      }
    });
    dockerHostUsePwdRadioButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onSelectUsePwdRadioButton();
      }
    });
    dockerHostUseSSHCertificatesRadioButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onSelectUseSshRadioButton();
      }
    });
    dockerHostImportKeyvaultCredsRadioButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onSelectUseKeyvaultRadioButton();
      }
    });
  }

  private void onSelectKeepCurrentRadioButton() {
    disablePwdUI();
    disableSshUI();
    disableKeyvaultUI();
  }

  private void onSelectUsePwdRadioButton() {
    enablePwdUI();
    disableSshUI();
    disableKeyvaultUI();
  }

  private void onSelectUseSshRadioButton() {
    disablePwdUI();
    enableSshUI();
    disableKeyvaultUI();
  }

  private void onSelectUseKeyvaultRadioButton() {
    disablePwdUI();
    disableSshUI();
    disableKeyvaultUI();
    enableKeyvaultUI();
  }

  private void initDefaultUI() {
    dockerHostKeepCurrentRadioButton.setEnabled(true);
    dockerHostKeepCurrentRadioButton.setSelected(true);
    String currentUserAuth = "Login name is ";
    currentUserAuth += editableHost.originalDockerHost.certVault.userName;
    if (editableHost.originalDockerHost.hasPwdLogIn) {
      dockerHostCurrentPwdLabel.setText("Password log in enable");
    } else {
      dockerHostCurrentPwdLabel.setText("");
    }
    if (editableHost.originalDockerHost.hasSSHLogIn) {
      dockerHostCurrentSshLabel.setText("SSH login enabled");
    } else {
      dockerHostCurrentSshLabel.setText("");
    }
    if (editableHost.isUpdated) {
      currentUserAuth += " (updating...)";
      dockerHostUsePwdRadioButton.setEnabled(false);
      dockerHostUseSSHCertificatesRadioButton.setEnabled(false);
      dockerHostImportKeyvaultCredsRadioButton.setEnabled(false);
    }
    dockerHostCurrentUserLabel.setText(currentUserAuth);

    disablePwdUI();
    disableSshUI();
    disableKeyvaultUI();
    dockerHostAutoSshRadioButton.setSelected(true);

  }

  private void enablePwdUI() {
    dockerHostUsernameTextField.setEnabled(true);
    dockerHostFirstPwdField.setEnabled(true);
    dockerHostSecondPwdField.setEnabled(true);
  }

  private void disablePwdUI() {
    dockerHostUsernameTextField.setEnabled(false);
    dockerHostFirstPwdField.setEnabled(false);
    dockerHostSecondPwdField.setEnabled(false);
  }

  private void enableSshUI() {
    dockerHostAutoSshRadioButton.setEnabled(true);
    dockerHostImportSshRadioButton.setEnabled(true);
    dockerHostImportSSHBrowseTextField.setEnabled(true);
  }

  private void disableSshUI() {
    dockerHostAutoSshRadioButton.setEnabled(false);
    dockerHostImportSshRadioButton.setEnabled(false);
    dockerHostImportSSHBrowseTextField.setEnabled(false);
  }

  private void enableKeyvaultUI() {
    dockerHostImportKeyvaultComboBox.setEnabled(true);
  }

  private void disableKeyvaultUI() {
    dockerHostImportKeyvaultComboBox.setEnabled(false);
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }
}
