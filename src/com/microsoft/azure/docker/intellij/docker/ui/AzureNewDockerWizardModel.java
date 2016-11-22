package com.microsoft.azure.docker.intellij.docker.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.wizard.WizardModel;
import com.microsoft.azure.docker.intellij.docker.ui.forms.AzureNewDockerHostStep;
import com.microsoft.azure.docker.intellij.docker.ui.forms.AzureNewDockerLoginStep;
import com.microsoft.azure.docker.resources.DockerHost;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;

public class AzureNewDockerWizardModel extends WizardModel {
  private Project project;
//  private AzureNewDockerHost newDockerHostForm;
//  private AzureConfigureNewDockerHost configureNewDockerHostForm;

  private AzureNewDockerHostStep newDockerHostStep;
  private AzureNewDockerLoginStep newDockerLoginStep;
  private AzureNewDockerWizardDialog newDockerWizardDialog;

  private AzureDockerUIManager dockerUIManager;
  public DockerHost dockerHostDescription;

  public AzureNewDockerWizardModel(final Project project, AzureDockerUIManager uiManager) {
    super("Create a new Docker VM");
    this.project = project;
    this.dockerUIManager = uiManager;

    dockerHostDescription = new DockerHost();

    newDockerHostStep = new AzureNewDockerHostStep(this.getTitle(), this);
    newDockerLoginStep = new AzureNewDockerLoginStep(this.getTitle(), this);
    add(newDockerHostStep);
    add(newDockerLoginStep);
  }

  public Project getProject() {
    return project;
  }

  public ValidationInfo doValidate() {
    return ((AzureNewDockerWizardStep) getCurrentStep()).doValidate();
  }

  public void setDockerUIManager(AzureDockerUIManager manager) {
    dockerUIManager = manager;
  }

  public void setSelectDockerWizardDialog(AzureNewDockerWizardDialog dialog) {
    newDockerWizardDialog = dialog;
  }

  public AzureNewDockerWizardDialog getSelectDockerWizardDialog() {
    return newDockerWizardDialog;
  }

  public AzureDockerUIManager getDockerUIManager() {
    return dockerUIManager;
  }


}
