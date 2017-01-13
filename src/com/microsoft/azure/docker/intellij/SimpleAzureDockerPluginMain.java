package com.microsoft.azure.docker.intellij;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.ConfigurableGroup;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.microsoft.azure.docker.creds.AzureCredsManager;
import com.microsoft.azure.docker.intellij.configurables.DockerAuthConfigGroup;
import com.microsoft.azure.docker.intellij.configurables.TestUpdateDockerDialog;
import com.microsoft.azure.docker.intellij.docker.AzureDockerPluginStart;
import com.microsoft.azure.docker.intellij.docker.ui.AzureSelectDockerWizardDialog;
import com.microsoft.azure.docker.intellij.docker.ui.AzureSelectDockerWizardModel;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;
import com.microsoft.azure.management.resources.Subscription;
import com.microsoft.intellij.util.PluginUtil;

import javax.swing.*;

import java.util.List;

import static com.intellij.projectImport.ProjectImportBuilder.getCurrentProject;

public class SimpleAzureDockerPluginMain extends AnAction {

  @Override
  public void actionPerformed(AnActionEvent e) {
    // TODO: insert action logic here

    Project project = getCurrentProject();


//    AzureDockerPluginStart dialog = new AzureDockerPluginStart(project);
//    dialog.show();

//    JOptionPane.showMessageDialog(null, "Done!");
//    ShowSettingsUtil.getInstance().showSettingsDialog(project, new ConfigurableGroup[]{new DockerAuthConfigGroup()});

//    TestUpdateDockerDialog dialog = new TestUpdateDockerDialog();
//    dialog.pack();
//    dialog.setVisible(true);

    AzureDockerUIManager dockerUIManager = getSrvPriUIManager();

    AzureSelectDockerWizardModel model = new AzureSelectDockerWizardModel(project, dockerUIManager);
    AzureSelectDockerWizardDialog wizard = new AzureSelectDockerWizardDialog(model);
    wizard.setTitle("New Docker on Azure Container Deployment");
    wizard.show();

  }

  private AzureDockerUIManager getSrvPriUIManager(){
    AzureDockerUIManager dockerUIManager = new AzureDockerUIManager();
    dockerUIManager.azureMainClient = AzureCredsManager.createSPDefaultAzureClient();
    List<Subscription> subscriptionList = AzureCredsManager.getSPSubscriptions();

    return dockerUIManager;
  }
}
