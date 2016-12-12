package com.microsoft.azure.docker.intellij.docker.ui.dialogs;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.microsoft.azure.docker.ops.AzureDockerCertVaultOps;
import com.microsoft.azure.docker.resources.AzureDockerCertVault;
import com.microsoft.intellij.ui.util.UIUtils;
import com.microsoft.intellij.util.PluginUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.*;

public class AzureExportDockerSshKeysDialog extends DialogWrapper {
  private JPanel mainPanel;
  private TextFieldWithBrowseButton exportSshPath;

  private Project project;
  private AzureDockerCertVault certVault;

  public AzureExportDockerSshKeysDialog(Project project, AzureDockerCertVault certVault) {
    super(project, true);

    this.project = project;
    this.certVault = certVault;

    setModal(true);

    exportSshPath.addActionListener(UIUtils.createFileChooserListener(exportSshPath, project,
        FileChooserDescriptorFactory.createSingleFolderDescriptor()));
    exportSshPath.setText(project.getBasePath() + "/out/Docker/ssh");

    init();
    setTitle("Export SSH Keys");
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
    exportAction.putValue(Action.NAME, "Save");
    return new Action[] {getCancelAction(), exportAction};
  }

  @Nullable
  @Override
  protected void doOKAction() {
    try {
      AzureDockerCertVaultOps.saveSshKeysToLocalDirectory(exportSshPath.getText(), certVault);
      super.doOKAction();
    }
    catch (Exception e){
      String msg = "An error occurred while attempting to export the SSh keys.\n" + e.getMessage();
      PluginUtil.displayErrorDialogAndLog("Error", msg, e);
    }
  }

}
