package com.microsoft.azure.docker.ui;

import com.microsoft.azure.docker.resources.DockerHost;

public class EditableDockerHost {
  public DockerHost originalDockerHost;
  public DockerHost updatedDockerHost;
  public boolean isUpdated;
  public boolean hasNewLoginCreds;
  public boolean hasNewDockerdCreds;
  public boolean hasNewDockerdPort;
  public boolean hasNewKeyVaultSettings;
  public boolean hasNewVMState; // see DockerHostVMState

  public EditableDockerHost(DockerHost host) {
    originalDockerHost = host;
    isUpdated = false;
    updatedDockerHost = (host != null) ? new DockerHost(host) : null;
    this.hasNewLoginCreds = false;
    this.hasNewDockerdCreds = false;
    this.hasNewDockerdPort = false;
    this.hasNewKeyVaultSettings = false;
    this.hasNewVMState = false;
  }

  public void undoEdit() {
    updatedDockerHost = new DockerHost(originalDockerHost);
  }

}
