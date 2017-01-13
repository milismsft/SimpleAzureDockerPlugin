package com.microsoft.azure.docker.intellij.configurables;

import com.intellij.openapi.options.BaseConfigurable;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class TestForm1 extends BaseConfigurable implements SearchableConfigurable, Configurable.NoScroll {
  private JPanel contentPane;

  public TestForm1() {

  }

  @NotNull
  @Override
  public String getId() {
    return getDisplayName();
  }

  @Nullable
  @Override
  public Runnable enableSearch(String option) {
    return null;
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "Form1";
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return "windows_azure_envvar_page";
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

}
