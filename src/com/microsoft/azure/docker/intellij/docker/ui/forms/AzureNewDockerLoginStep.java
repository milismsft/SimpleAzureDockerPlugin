package com.microsoft.azure.docker.intellij.docker.ui.forms;

import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.wizard.WizardNavigationState;
import com.intellij.ui.wizard.WizardStep;
import com.microsoft.azure.docker.intellij.docker.ui.AzureNewDockerWizardModel;
import com.microsoft.azure.docker.intellij.docker.ui.AzureNewDockerWizardStep;
import com.microsoft.azure.docker.resources.AzureDockerHost;
import com.microsoft.azure.docker.resources.DockerHost;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;

import javax.swing.*;

public class AzureNewDockerLoginStep extends AzureNewDockerWizardStep {
  private JPanel rootConfigureContainerPanel;
  private JRadioButton dockerHostImportKeyvaultCredsRadioButton;
  private JComboBox dockerHostImportKeyvaultComboBox;
  private JRadioButton dockerHostNewCredsRadioButton;
  private ButtonGroup groupImportNewCreds;
  private JRadioButton dockerHostUsePwdRadioButton;
  private JRadioButton dockerHostUseSshRadioButton;
  private JTextField dockerHostUsernameTextField;
  private JRadioButton dockerHostNoTlsRadioButton;
  private JTextField dockerDaemonPortTextField;
  private JRadioButton dockerHostEnableTlsRadioButton;
  private JRadioButton dockerHostImportSshRadioButton;
  private JRadioButton dockerHostAutoSshRadioButton;
  private JRadioButton dockerHostAutoTlsRadioButton;
  private JRadioButton dockerHostImportTlsRadioButton;
  private JTabbedPane tabbedPane1;
  private JPasswordField dockerHostFirstPwdField;
  private JPasswordField dockerHostSecondPwdField;
  private TextFieldWithBrowseButton dockerHostImportSSHBrowseTextField;
  private JCheckBox dockerHostSaveCredsCheckBox;
  private JTextField dockerHostNewKeyvaultTextField;
  private TextFieldWithBrowseButton dockerHostImportTLSBrowseTextField;

  private AzureNewDockerWizardModel model;
  private AzureDockerUIManager dockerUIManager;
  private AzureDockerHost newDockerHost;

  public AzureNewDockerLoginStep(String title, AzureNewDockerWizardModel model) {
    // TODO: The message should go into the plugin property file that handles the various dialog titles
    super(title, "Configure Docker VM's login credentials and port settings");
    this.model = model;
    this.dockerUIManager = model.getDockerUIManager();

    initDefaultDockerHost();
    initDialog();
  }

  private void initDefaultDockerHost() {
    newDockerHost = new AzureDockerHost();
  }

  private void initDialog() {
    // New or import Key Vault credentials
    groupImportNewCreds = new ButtonGroup();
    groupImportNewCreds.add(dockerHostImportKeyvaultCredsRadioButton);
    groupImportNewCreds.add(dockerHostNewCredsRadioButton);
    dockerHostNewCredsRadioButton.setSelected(true);
    dockerHostImportKeyvaultComboBox.setEnabled(false);

    // Save new credentials to a new Azure Key Vault

  }

  public DockerHost getDockerHost() {
    return newDockerHost;
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
