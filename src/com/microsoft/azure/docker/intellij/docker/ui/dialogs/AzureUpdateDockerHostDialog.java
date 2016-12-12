package com.microsoft.azure.docker.intellij.docker.ui.dialogs;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.microsoft.azure.docker.resources.DockerHost;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AzureUpdateDockerHostDialog extends DialogWrapper {
  private JPanel mainPanel;

  private Project project;
  private DockerHost updatedHost;

  public AzureUpdateDockerHostDialog(Project project, DockerHost dockerHost) {
    super(project, true);

    this.project = project;
    this.updatedHost = dockerHost;

    setModal(true);

    init();
    setTitle("Update Docker Host");
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
    Action exportAction = getOKAction();
    exportAction.putValue(Action.NAME, "Done");
    return new Action[] {getCancelAction(), exportAction};
  }

  @Nullable
  @Override
  protected void doOKAction() {
      super.doOKAction();
  }

}
