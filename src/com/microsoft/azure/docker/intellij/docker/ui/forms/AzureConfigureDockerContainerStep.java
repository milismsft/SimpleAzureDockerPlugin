package com.microsoft.azure.docker.intellij.docker.ui.forms;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.wizard.WizardNavigationState;
import com.intellij.ui.wizard.WizardStep;
import com.microsoft.azure.docker.intellij.docker.ui.AzureSelectDockerWizardModel;
import com.microsoft.azure.docker.intellij.docker.ui.AzureSelectDockerWizardStep;
import com.microsoft.azure.docker.resources.KnownDockerImages;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;
import com.microsoft.intellij.ui.util.UIUtils;
import com.microsoft.intellij.util.PluginUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AzureConfigureDockerContainerStep extends AzureSelectDockerWizardStep {
  private AzureSelectDockerWizardModel model;
  private JPanel rootConfigureContainerPanel;
  private JTextField dockerContainerName;
  private JTextField dockerContainerPortSettings;
  private JRadioButton predefinedDockerfileRadioButton;
  private JRadioButton customDockerfileRadioButton;
  private ButtonGroup selectContainerButtonGroup;
  private JComboBox dockerfileComboBox;
  private TextFieldWithBrowseButton customDockerfileBrowseButton;

  private AzureDockerUIManager dockerUIManager;

  public AzureConfigureDockerContainerStep(String title, AzureSelectDockerWizardModel model){
    // TODO: The message should go into the plugin property file that handles the various dialog titles
    super(title, "Configure the Docker container to be created");
    this.model = model;
    this.dockerUIManager = model.getDockerUIManager();

    dockerContainerName.setText(model.dockerImageDescription.dockerContainerName);

    selectContainerButtonGroup = new ButtonGroup();
    selectContainerButtonGroup.add(predefinedDockerfileRadioButton);
    selectContainerButtonGroup.add(customDockerfileRadioButton);

    predefinedDockerfileRadioButton.setSelected(true);
    dockerfileComboBox.setEnabled(true);

    for (KnownDockerImages image : model.getDockerUIManager().getDefaultDockerImages()) {
      dockerfileComboBox.addItem(image);
    }

    customDockerfileBrowseButton.setEnabled(false);
    customDockerfileBrowseButton.addActionListener(UIUtils.createFileChooserListener(customDockerfileBrowseButton, model.getProject(),
        FileChooserDescriptorFactory.createSingleLocalFileDescriptor()));
    customDockerfileBrowseButton.setText("");

    predefinedDockerfileRadioButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dockerfileComboBox.setEnabled(true);
        customDockerfileBrowseButton.setEnabled(false);
      }
    });

    customDockerfileRadioButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dockerfileComboBox.setEnabled(false);
        customDockerfileBrowseButton.setEnabled(true);
      }
    });

    dockerContainerPortSettings.setText("18080:80");
  }

  @Override
  public ValidationInfo doValidate() {
    if (dockerContainerName.getText() == null || dockerContainerName.getText().equals("")){
      ValidationInfo info = new ValidationInfo("Please name your Docker container", dockerContainerName);
      model.getSelectDockerWizardDialog().DialogShaker(info);
      return info;
    }

    if (predefinedDockerfileRadioButton.isSelected() && dockerfileComboBox.getSelectedItem() == null){
      ValidationInfo info = new ValidationInfo("Please select a Docker image type form the list", dockerfileComboBox);
      model.getSelectDockerWizardDialog().DialogShaker(info);
      return info;
    }

    String dockerfileName = customDockerfileBrowseButton.getText();
    if (customDockerfileRadioButton.isSelected() &&
        (dockerfileName == null || dockerfileName.equals("") || Files.notExists(Paths.get(dockerfileName)))){
      ValidationInfo info = new ValidationInfo("Please input a valid Dockerfile", customDockerfileBrowseButton);
      model.getSelectDockerWizardDialog().DialogShaker(info);
      return info;
    }

    if (dockerContainerPortSettings.getText() == null || dockerContainerPortSettings.getText().equals("")){
      ValidationInfo info = new ValidationInfo("Please name your Docker container", dockerContainerPortSettings);
      model.getSelectDockerWizardDialog().DialogShaker(info);
      return info;
    }

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

  @Override
  public boolean onFinish() {
//    if (doValidate() == null) {
//      model.dockerImageDescription.dockerContainerName = dockerContainerName.getText();
//      model.dockerImageDescription.dockerPortSettings = dockerContainerPortSettings.getText();
//      if (predefinedDockerfileRadioButton.isSelected()) {
//        model.dockerImageDescription.dockerfileContent = ((KnownDockerImages) dockerfileComboBox.getSelectedItem()).getDockerfileContent();
//      } else if (customDockerfileRadioButton.isSelected()) {
//        try {
//          model.dockerImageDescription.dockerfileContent = new String(Files.readAllBytes(Paths.get(customDockerfileBrowseButton.getText())));
//        } catch (Exception e) {
//          String msg = "An error occurred while attempting to get the content of " + customDockerfileBrowseButton.getText() + " .\n" + e.getMessage();
//          PluginUtil.displayErrorDialogAndLog("Error", msg, e);
//
//          return false;
//        }
//      }
//
//      return super.onFinish();
//    } else {
//      return false;
//    }
    return false;
  }

}
