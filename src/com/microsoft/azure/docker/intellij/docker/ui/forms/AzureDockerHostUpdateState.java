package com.microsoft.azure.docker.intellij.docker.ui.forms;

import com.intellij.openapi.project.Project;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;
import com.microsoft.azure.docker.ui.EditableDockerHost;

import javax.swing.*;

public class AzureDockerHostUpdateState {
  private JPanel contentPane;
  private JPanel mainPanel;
  private JComboBox dockerHostStateComboBox;
  private JLabel dockerHostNameLabel;
  private JLabel dockerHostUrlLabel;

  private Project project;
  private EditableDockerHost editableHost;
  private AzureDockerUIManager dockerUIManager;

  public AzureDockerHostUpdateState(Project project, EditableDockerHost editableHost, AzureDockerUIManager dockerUIManager) {
    this.project = project;
    this.editableHost = editableHost;
    this.dockerUIManager = dockerUIManager;
  }

  public JPanel getMainPanel() {
    return mainPanel;
  }
}
