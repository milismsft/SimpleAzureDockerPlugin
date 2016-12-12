package com.microsoft.azure.docker.intellij.docker.ui.forms;

import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.wizard.WizardNavigationState;
import com.intellij.ui.wizard.WizardStep;
import com.microsoft.azure.docker.intellij.docker.ui.AzureNewDockerWizardModel;
import com.microsoft.azure.docker.intellij.docker.ui.AzureNewDockerWizardStep;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;

import javax.swing.*;

public class AzureNewDockerHostStep extends AzureNewDockerWizardStep {
  private JPanel rootConfigureContainerPanel;
  private JTextField dockerHostNameTextField;
  private JComboBox dockerSubscriptionComboBox;
  private JComboBox dockerLocationComboBox;
  private ButtonGroup dockerHostStorageGroup;
  private JRadioButton dockerHostNewStorageRadioButton;
  private JRadioButton dockerHostSelectStorageRadioButton;
  private JComboBox dockerSelectStorageComboBox;
  private JTextField dockerNewStorageTextField;
  private JTabbedPane tabbedPane1;
  private JComboBox dockerHostOSTypeComboBox;
  private JComboBox dockerHostVMSizeComboBox;
  private JComboBox dockerHostSelectRGComboBox;
  private JTextField dockerHostRGTextField;
  private ButtonGroup dockerHostVnetGroup;
  private JRadioButton dockerHostNewVNetRadioButton;
  private JRadioButton dockerHostSelectVNetRadioButton;
  private JComboBox dockerHostSelectVnetComboBox;
  private JComboBox dockerHostSelectSubnetComboBox;
  private JTextField dockerHostNewVNetNameTextField;
  private ButtonGroup dockerHostRGGroup;
  private JRadioButton dockerHostNewRGRadioButton;
  private JRadioButton dockerHostSelectRGRadioButton;
  private JTextField dockerHostNewVNetAddrSpaceTextField;

  private AzureNewDockerWizardModel model;
  private AzureDockerUIManager dockerUIManager;

  public AzureNewDockerHostStep(String title, AzureNewDockerWizardModel model) {
    // TODO: The message should go into the plugin property file that handles the various dialog titles
    super(title, "Configure the Docker VM to be created");
    this.model = model;
    this.dockerUIManager = model.getDockerUIManager();

    dockerHostStorageGroup = new ButtonGroup();
    dockerHostStorageGroup.add(dockerHostNewStorageRadioButton);
    dockerHostStorageGroup.add(dockerHostSelectStorageRadioButton);

    dockerHostVnetGroup = new ButtonGroup();
    dockerHostVnetGroup.add(dockerHostNewVNetRadioButton);
    dockerHostVnetGroup.add(dockerHostSelectVNetRadioButton);

    dockerHostRGGroup = new ButtonGroup();
    dockerHostRGGroup.add(dockerHostNewRGRadioButton);
    dockerHostRGGroup.add(dockerHostSelectRGRadioButton);

    dockerHostNameTextField.setText(dockerUIManager.getDefaultDockerHostName());
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
