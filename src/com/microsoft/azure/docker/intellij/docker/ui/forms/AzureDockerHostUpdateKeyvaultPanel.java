package com.microsoft.azure.docker.intellij.docker.ui.forms;

import com.intellij.openapi.project.Project;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;
import com.microsoft.azure.docker.ui.EditableDockerHost;
import org.jdesktop.swingx.JXHyperlink;

import javax.swing.*;

public class AzureDockerHostUpdateKeyvaultPanel {
  private JPanel contentPane;
  private JPanel mainPanel;
  private JRadioButton keepCurrentSettingsRadioButton;
  private JRadioButton donTUseKeyRadioButton;
  private JXHyperlink dockerHostDeleteKeyvaultHyperlink;
  private JRadioButton newKeyVaultRadioButton;
  private JTextField dockerHostNewKeyvaultTextField;

  private Project project;
  private EditableDockerHost editableHost;
  private AzureDockerUIManager dockerUIManager;

  public AzureDockerHostUpdateKeyvaultPanel(Project project, EditableDockerHost editableHost, AzureDockerUIManager dockerUIManager) {
    this.project = project;
    this.editableHost = editableHost;
    this.dockerUIManager = dockerUIManager;
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }
}
