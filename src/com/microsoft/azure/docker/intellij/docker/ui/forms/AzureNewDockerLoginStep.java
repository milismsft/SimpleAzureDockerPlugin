package com.microsoft.azure.docker.intellij.docker.ui.forms;

import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.wizard.WizardNavigationState;
import com.intellij.ui.wizard.WizardStep;
import com.microsoft.azure.docker.intellij.docker.ui.AzureNewDockerWizardModel;
import com.microsoft.azure.docker.intellij.docker.ui.AzureNewDockerWizardStep;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;

import javax.swing.*;

public class AzureNewDockerLoginStep extends AzureNewDockerWizardStep {
  private JPanel rootConfigureContainerPanel;
  private JRadioButton useAPasswordToRadioButton;
  private JRadioButton useSSHCertificatesRadioButton;
  private JTextField textField1;
  private JPasswordField passwordField1;
  private JRadioButton unsecuredAccessToTheRadioButton;
  private JTextField textField2;
  private JRadioButton enableTLSSecuredAccessRadioButton;
  private JCheckBox saveNewSettingsIntoCheckBox;
  private JRadioButton createNewLogInRadioButton;
  private JRadioButton importFromAzureKeyRadioButton;
  private JComboBox comboBox1;
  private JTextField textField3;
  private JRadioButton importFromDirectoryRadioButton;
  private JRadioButton autoGenerateRadioButton;
  private JRadioButton autoGenerateRadioButton1;
  private JRadioButton importFromDirectoryRadioButton2;
  private JTabbedPane tabbedPane1;

  private AzureNewDockerWizardModel model;
  private AzureDockerUIManager dockerUIManager;

  public AzureNewDockerLoginStep(String title, AzureNewDockerWizardModel model) {
    // TODO: The message should go into the plugin property file that handles the various dialog titles
    super(title, "Configure Docker VM's login credentials and port settings");
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
