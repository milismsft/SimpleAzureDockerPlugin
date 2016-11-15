package com.microsoft.azure.docker.intellij.docker.ui.forms;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.*;

public class AzureViewDockerHostsDialog extends DialogWrapper {
  private JPanel contentPane;
  private JPanel mainPanel;

  private Project project;
  private AzureDockerUIManager dockerUIManager;

  public AzureViewDockerHostsDialog(Project project) {
    super(project, true);

    setModal(true);
    init();
  }

  public void setDockerUIManager(AzureDockerUIManager dockerUIManager) {
    this.dockerUIManager = dockerUIManager;
  }

  public AzureDockerUIManager getDockerUIManager() {
    return dockerUIManager;
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
    Action action = getCancelAction();
    action.putValue(Action.NAME, "Done");

    return new Action[] {getCancelAction()};
  }

}
