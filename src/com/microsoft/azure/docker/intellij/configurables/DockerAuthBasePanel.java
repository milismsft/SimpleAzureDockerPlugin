package com.microsoft.azure.docker.intellij.configurables;

import javax.swing.*;

import com.intellij.openapi.options.BaseConfigurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class DockerAuthBasePanel extends BaseConfigurable  {
  private JPanel contentPane;

  public DockerAuthBasePanel() {

  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return contentPane;
  }

  @Override
  public void apply() throws ConfigurationException {

  }

  @Override
  public void reset() {
    setModified(false);
  }

  @Override
  public void disposeUIResources() {

  }

  @Override
  public String getDisplayName() {
    return "Auth";
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return "windows_azure_docker";
  }
}

