package com.microsoft.azure.docker.intellij.docker.ui.forms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;
import com.microsoft.azure.docker.ui.EditableDockerHost;

import javax.swing.*;

public class AzureDockerHostUpdateDaemonPanel {
  private JPanel contentPane;
  private JPanel mainPanel;
  private JTextField dockerDaemonPortTextField;
  private JRadioButton dockerHostKeepCurrentRadioButton;
  private JRadioButton dockerHostNoTlsRadioButton;
  private JRadioButton dockerHostEnableTlsRadioButton;
  private JRadioButton dockerHostAutoTlsRadioButton;
  private JRadioButton dockerHostImportTlsRadioButton;
  private TextFieldWithBrowseButton dockerHostImportTLSBrowseTextField;
  private JLabel dockerHostCurrentAuthLabel;
  private JRadioButton dockerHostImportKeyvaultRadioButton;
  private JTextField dockerHostImportKeyvaultTextField;
  private ButtonGroup mainSelectionGroup;
  private ButtonGroup authSelectionGroup;

  private Project project;
  private EditableDockerHost editableHost;
  private AzureDockerUIManager dockerUIManager;


  public AzureDockerHostUpdateDaemonPanel(Project project, EditableDockerHost editableHost, AzureDockerUIManager dockerUIManager) {
    this.project = project;
    this.editableHost = editableHost;
    this.dockerUIManager = dockerUIManager;

    dockerDaemonPortTextField.setText(editableHost.updatedDockerHost.port);


    mainSelectionGroup = new ButtonGroup();
    mainSelectionGroup.add(dockerHostKeepCurrentRadioButton);
    mainSelectionGroup.add(dockerHostNoTlsRadioButton);
    mainSelectionGroup.add(dockerHostEnableTlsRadioButton);
    dockerHostKeepCurrentRadioButton.setSelected(true);

    authSelectionGroup = new ButtonGroup();
    authSelectionGroup.add(dockerHostNoTlsRadioButton);
    authSelectionGroup.add(dockerHostAutoTlsRadioButton);
    authSelectionGroup.add(dockerHostImportTlsRadioButton);
    authSelectionGroup.add(dockerHostImportKeyvaultRadioButton);

    initDefaultUIState();
  }

  private void initDefaultUIState() {
    dockerHostNoTlsRadioButton.setEnabled(true);
    dockerHostAutoTlsRadioButton.setEnabled(false);
    dockerHostImportTlsRadioButton.setEnabled(false);
    dockerHostImportTLSBrowseTextField.setEnabled(false);
    dockerHostImportKeyvaultRadioButton.setEnabled(true);
    dockerHostImportKeyvaultTextField.setEnabled(false);

    dockerHostAutoTlsRadioButton.setSelected(true);

    if (editableHost.originalDockerHost.isTLSSecured) {
      dockerHostCurrentAuthLabel.setText("TLS secured");
    } else {
      dockerHostCurrentAuthLabel.setText("Unsecured");
    }
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }
}
