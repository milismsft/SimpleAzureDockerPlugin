package com.microsoft.azure.docker.ui;

import com.microsoft.azure.docker.resources.DockerHost;

public class AzureCreateDockerImageDescription {
  public Boolean hasNewDockerHost;
  public Boolean hasRunConfiguration;
  public Boolean hasDebugConfiguration;
  public DockerHost host;
  public String dockerImageName;
  public String artifactName;
  public String dockerContainerName;
  public String dockerPortSettings;
  public String dockerfileContent;
}
