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

public class AzureExportDockerTlsKeysDialog extends DialogWrapper {
  private JPanel mainPanel;
  private TextFieldWithBrowseButton exportTlsPath;

  private Project project;
  private AzureDockerCertVault certVault;

  public AzureExportDockerTlsKeysDialog(Project project, AzureDockerCertVault certVault) {
    super(project, true);

    this.project = project;
    this.certVault = certVault;

    setModal(true);

    exportTlsPath.addActionListener(UIUtils.createFileChooserListener(exportTlsPath, project,
        FileChooserDescriptorFactory.createSingleFolderDescriptor()));
    exportTlsPath.setText(project.getBasePath() + "/out/Docker/tls");

    init();
    setTitle("Export TLS Certificates");
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
      AzureDockerCertVaultOps.saveSshKeysToLocalDirectory(exportTlsPath.getText(), certVault);
      super.doOKAction();
    }
    catch (Exception e){
      String msg = "An error occurred while attempting to export the TLS keys.\n" + e.getMessage();
      PluginUtil.displayErrorDialogAndLog("Error", msg, e);
    }
  }

}
