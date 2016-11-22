package com.microsoft.azure.docker.ui;

import com.microsoft.azure.docker.resources.DockerHost;

public class AzureCreateDockerImageDescription {
  public boolean hasNewDockerHost;
  public boolean hasRunConfiguration;
  public boolean hasDebugConfiguration;
  public DockerHost host;
  public String dockerImageName;
  public String artifactName;
  public String dockerContainerName;
  public String dockerPortSettings;
  public String dockerfileContent;
}
