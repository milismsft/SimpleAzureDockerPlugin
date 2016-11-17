package com.microsoft.azure.docker.intellij.docker.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.wizard.WizardModel;

public class AzureNewDockerWizardModel extends WizardModel {
  private Project project;
//  private AzureNewDockerHost newDockerHostForm;
//  private AzureConfigureNewDockerHost configureNewDockerHostForm;


  public AzureNewDockerWizardModel(final Project project) {
    super("Azure Docker Deployment");
    this.project = project;

//    newDockerHostForm = new AzureNewDockerHost(this.getTitle(), this);
//    configureNewDockerHostForm = new AzureConfigureNewDocker(this.getTitle(), this);
//    add(newDockerHostForm);
//    add(configureNewDockerHostForm);
  }

  public Project getProject() {
    return project;
  }

  public ValidationInfo doValidate() {
    return ((AzureNewDockerWizardStep) getCurrentStep()).doValidate();
  }

  @Override
  public boolean isDone() {
    return super.isDone();
  }


}
