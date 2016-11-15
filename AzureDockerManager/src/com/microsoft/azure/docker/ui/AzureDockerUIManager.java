package com.microsoft.azure.docker.ui;

import com.microsoft.azure.docker.resources.AzureDockerSubscription;
import com.microsoft.azure.docker.resources.DockerHost;
import com.microsoft.azure.management.Azure;

import java.util.ArrayList;
import java.util.List;

public class AzureDockerUIManager {
  public Azure azureMainClient;
  public List<AzureDockerSubscription> subscriptionList;

  public List<DockerHost> dockerHostsList;

  public DockerHost createNewFakeDockerHost(String name) {
    DockerHost host = new DockerHost();
    host.name = name;
    host.apiUrl = "http://" + name + ".centralus.cloudapp.azure.com";
    host.port = "2375";
    host.isTLSSecured = false;
    host.state = DockerHost.DockerHostVMState.ACTIVE;
    host.hostOSType = DockerHost.DockerHostOSType.UBUNTU_SERVER_16;

    return host;
  }

  public List<DockerHost> createNewFakeDockerHostList() {
    List<DockerHost> hosts = new ArrayList<>();
    hosts.add(createNewFakeDockerHost("someDockerHost112"));
    hosts.add(createNewFakeDockerHost("otherDockerHost212"));
    hosts.add(createNewFakeDockerHost("qnyDockerHost132"));
    hosts.add(createNewFakeDockerHost("anyDockerHost612"));
    dockerHostsList = hosts;

    return hosts;
  }

}
