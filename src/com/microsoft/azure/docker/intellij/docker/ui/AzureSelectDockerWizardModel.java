package com.microsoft.azure.docker.intellij.docker.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.table.TableView;
import com.intellij.ui.wizard.WizardModel;
import com.microsoft.azure.docker.intellij.docker.ui.forms.AzureConfigureDockerContainerStep;
import com.microsoft.azure.docker.intellij.docker.ui.forms.AzureSelectDockerHostStep;
import com.microsoft.azure.docker.ui.AzureCreateDockerImageDescription;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;

import javax.swing.*;
import java.util.Map;

public class AzureSelectDockerWizardModel extends WizardModel {
  private Project project;
  private AzureSelectDockerHostStep selectDockerHostForm;
  private AzureConfigureDockerContainerStep configureDockerContainerForm;
  private AzureSelectDockerWizardDialog selectDockerWizardDialog;

  private AzureDockerUIManager dockerUIManager;

  public AzureCreateDockerImageDescription dockerImageDescription;

  public AzureSelectDockerWizardModel(final Project project, AzureDockerUIManager uiManager) {
    super("Docker on Azure Deployment");
    this.project = project;
    this.dockerUIManager = uiManager;
    dockerImageDescription = new AzureCreateDockerImageDescription();
    dockerImageDescription.dockerImageName = dockerUIManager.getDefaultDockerImageName(project.getName());
    dockerImageDescription.dockerContainerName = dockerUIManager.getDefaultDockerContainerName(dockerImageDescription.dockerImageName);
    dockerImageDescription.artifactName = dockerUIManager.getDefaultArtifactName(project.getName());

    selectDockerHostForm = new AzureSelectDockerHostStep(this.getTitle(), this);
    configureDockerContainerForm = new AzureConfigureDockerContainerStep(this.getTitle(), this);
    add(selectDockerHostForm);
    add(configureDockerContainerForm);

  }

  public void setDockerUIManager(AzureDockerUIManager manager) {
    dockerUIManager = manager;
  }

  public void setSelectDockerWizardDialog(AzureSelectDockerWizardDialog dialog) {
    selectDockerWizardDialog = dialog;
  }

  public AzureSelectDockerWizardDialog getSelectDockerWizardDialog() {
    return selectDockerWizardDialog;
  }

  public AzureDockerUIManager getDockerUIManager() {
    return dockerUIManager;
  }

  public Project getProject() {
    return project;
  }

  public ValidationInfo doValidate() {
    return ((AzureSelectDockerWizardStep) getCurrentStep()).doValidate();
  }

}
