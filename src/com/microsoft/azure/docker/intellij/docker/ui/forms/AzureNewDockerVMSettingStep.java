package com.microsoft.azure.docker.intellij.docker.ui.forms;

import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.wizard.WizardNavigationState;
import com.intellij.ui.wizard.WizardStep;
import com.microsoft.azure.docker.intellij.docker.ui.AzureNewDockerWizardModel;
import com.microsoft.azure.docker.intellij.docker.ui.AzureNewDockerWizardStep;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;

import javax.swing.*;

public class AzureNewDockerVMSettingStep extends AzureNewDockerWizardStep {
  private JPanel rootConfigureContainerPanel;
  private JRadioButton newResourceGroupRadioButton;
  private JRadioButton selectResourceGroupRadioButton;
  private JTextField dockerCreateNewRGTextField;
  private JComboBox dockerSelectOSTypeComboBox;
  private JComboBox dockerSelectVMSizeComboBox;
  private JComboBox dockerSelectRGComboBox;
  private JRadioButton noneRadioButton;
  private JRadioButton newAvailabilitySetRadioButton;
  private JRadioButton selectAvailabilitySetRadioButton;
  private JComboBox dockerSelectAvailabilitySetComboBox;
  private JTextField dockerCreateNewAvailabilitySetTextField;

  private AzureNewDockerWizardModel model;
  private AzureDockerUIManager dockerUIManager;

  public AzureNewDockerVMSettingStep(String title, AzureNewDockerWizardModel model) {
    // TODO: The message should go into the plugin property file that handles the various dialog titles
    super(title, "Configure VM's properties");
    this.model = model;
    this.dockerUIManager = model.getDockerUIManager();
  }

  @Override
  public ValidationInfo doValidate() {
    return doValidate(true);
  }

  private ValidationInfo doValidate(Boolean shakeOnError) {
    return null;
  }

  private void setFinishButtonState() {
    model.getCurrentNavigationState().FINISH.setEnabled(doValidate(false) == null);
  }

  @Override
  public JComponent prepare(final WizardNavigationState state) {
    rootConfigureContainerPanel.revalidate();
    setFinishButtonState();

    return rootConfigureContainerPanel;
  }

  @Override
  public WizardStep onNext(final AzureNewDockerWizardModel model) {
    if (doValidate() == null) {
      return super.onNext(model);
    } else {
      return this;
    }
  }

  @Override
  public boolean onFinish() {
    return super.onFinish();
  }
}
