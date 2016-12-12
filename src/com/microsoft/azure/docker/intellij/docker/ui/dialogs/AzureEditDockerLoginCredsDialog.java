package com.microsoft.azure.docker.intellij.docker.ui.dialogs;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.microsoft.azure.docker.ops.AzureDockerCertVaultOps;
import com.microsoft.azure.docker.resources.AzureDockerCertVault;
import com.microsoft.azure.docker.resources.AzureDockerHost;
import com.microsoft.azure.docker.resources.AzureDockerVM;
import com.microsoft.azure.docker.resources.DockerHost;
import com.microsoft.intellij.ui.util.UIUtils;
import com.microsoft.intellij.util.PluginUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.*;

public class AzureEditDockerLoginCredsDialog extends DialogWrapper {
  private JPanel mainPanel;

  private Project project;
  private DockerHost dockerHost;

  public AzureEditDockerLoginCredsDialog(Project project, DockerHost dockerHost) {
    super(project, true);

    this.project = project;
    this.dockerHost = dockerHost;

    setModal(true);

    init();
    setTitle("Update Login Credentials");
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
    exportAction.putValue(Action.NAME, "Update");
    return new Action[] {getCancelAction(), exportAction};
  }

  @Nullable
  @Override
  protected void doOKAction() {
    try {
      super.doOKAction();
    }
    catch (Exception e){
      String msg = "An error occurred while attempting to update the log in credentials.\n" + e.getMessage();
      PluginUtil.displayErrorDialogAndLog("Error", msg, e);
    }
  }

}
