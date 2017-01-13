package com.microsoft.azure.docker.intellij.configurables;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

import static com.intellij.projectImport.ProjectImportBuilder.getCurrentProject;

public class DockerAuthConfigurables extends SearchableConfigurable.Parent.Abstract {

  private DockerAuthBasePanel panel;

  public DockerAuthConfigurables() {
    panel = new DockerAuthBasePanel();
  }

  @Override
  public boolean hasOwnContent() {
    return true;
  }

  @Override
  protected Configurable[] buildConfigurables() {
    Project project = getCurrentProject();
    return new Configurable[]{new TestForm1()};
  }

  @NotNull
  @Override
  public String getId() {
    return getDisplayName();
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "Update auth...";
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return panel.getHelpTopic();
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return panel.createComponent();
  }

  @Override
  public boolean isModified() {
    return panel.isModified();
  }

  @Override
  public void apply() throws ConfigurationException {
    panel.apply();
  }

  @Override
  public void reset() {
    panel.reset();
  }
}
