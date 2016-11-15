package com.microsoft.azure.docker.intellij.docker.ui.forms;

import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.wizard.WizardNavigationState;
import com.intellij.ui.wizard.WizardStep;
import com.microsoft.azure.docker.intellij.docker.ui.AzureSelectDockerWizardModel;
import com.microsoft.azure.docker.intellij.docker.ui.AzureSelectDockerWizardStep;

import javax.swing.*;

public class AzureConfigureDockerContainerStep extends AzureSelectDockerWizardStep {
  private AzureSelectDockerWizardModel model;
  private JPanel rootConfigureContainerPanel;

  public AzureConfigureDockerContainerStep(String title, AzureSelectDockerWizardModel model){
    // TODO: The message should go into the plugin property file that handles the various dialog titles
    super(title, "Configure the Docker container to be created");
    this.model = model;
  }

  @Override
  public ValidationInfo doValidate() {
    return null;
  }

  @Override
  public JComponent prepare(final WizardNavigationState state) {
    rootConfigureContainerPanel.revalidate();
    return rootConfigureContainerPanel;
  }

  @Override
  public WizardStep onNext(final AzureSelectDockerWizardModel model) {
    if (doValidate() == null) {
      return super.onNext(model);
    } else {
      return this;
    }
  }
}
