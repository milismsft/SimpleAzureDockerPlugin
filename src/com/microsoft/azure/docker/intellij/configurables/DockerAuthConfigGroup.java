package com.microsoft.azure.docker.intellij.configurables;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurableGroup;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DockerAuthConfigGroup extends SearchableConfigurable.Parent.Abstract
    implements SearchableConfigurable, ConfigurableGroup, Configurable.NoScroll {

  private static final String MY_GROUP_ID = "configurable.group.azure.docker.edit.auth";
  private final Configurable[] rolesConfigurable;

  public DockerAuthConfigGroup() {
    rolesConfigurable = new Configurable[]{new DockerAuthConfigurables()};
  }

  @Override
  protected Configurable[] buildConfigurables() {
    return rolesConfigurable;
  }

  @NotNull
  @Override
  public String getId() {
    return MY_GROUP_ID;
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return null;
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "azure";
  }

  @Override
  public String getShortName() {
    return getDisplayName();
  }

  @Override
  public boolean isModified() {
    return rolesConfigurable[0].isModified();
  }
}
