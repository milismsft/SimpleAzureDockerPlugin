package com.microsoft.azure.docker.ui;

import com.microsoft.azure.docker.resources.AzureDockerSubscription;
import com.microsoft.azure.docker.resources.AzureDockerVM;
import com.microsoft.azure.docker.resources.DockerHost;
import com.microsoft.azure.docker.resources.KnownDockerImages;
import com.microsoft.azure.management.Azure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AzureDockerUIManager {
  public Azure azureMainClient;
  public List<AzureDockerSubscription> subscriptionList;

  public List<DockerHost> dockerHostsList;

  public AzureDockerUIManager() {
    forceRefreshSubscriptionList();
    forceRefreshDockerHostsList();
  }

  public void forceRefreshDockerHostsList() {
    // call into Ops to retrieve the latest list of Docker VMs
    // TODO: make the real thing happen here

    dockerHostsList = createNewFakeDockerHostList();
  }

  public void forceRefreshSubscriptionList() {
    // TODO: make the real thing happen here
    subscriptionList = createNewFakeSubscriptionList();
  }

  public List<KnownDockerImages> getDefaultDockerImages() {
    List<KnownDockerImages> dockerImagesList = new ArrayList<>();
    dockerImagesList.add(KnownDockerImages.JBOSS_WILDFLY);
    dockerImagesList.add(KnownDockerImages.TOMCAT8);

    return dockerImagesList;
  }

  /* Retrieves a Docker host object for a given API
   *   The API URL is unique and can be safely used to get a specific docker host description
   */
  public DockerHost getDockerHostForURL(String apiURL) {
    // TODO: make the real call into Docker Ops to retrieve the host info
    return createNewFakeDockerHost("someHost");
  }

  public DockerHost createNewFakeDockerHost(String name) {
    DockerHost host = new DockerHost();
    host.name = name;
    host.apiUrl = "http://" + name + ".centralus.cloudapp.azure.com";
    host.port = "2375";
    host.isTLSSecured = false;
    host.state = DockerHost.DockerHostVMState.ACTIVE;
    host.hostOSType = DockerHost.DockerHostOSType.UBUNTU_SERVER_16;
    host.hostVM = new AzureDockerVM();
    host.hostVM.name = name;
    host.hostVM.region = "centralus";

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

  public List<AzureDockerSubscription> createNewFakeSubscriptionList() {
    List<AzureDockerSubscription> subscriptionList = new ArrayList<>();


    return subscriptionList;
  }

  public String getDefaultDockerHostName() {
    return String.format("%s%d", "mydocker", new Random().nextInt(1000000));
  }

  public String getDefaultName(String projectName) {
    return projectName.replaceAll(" ", "_");
  }

  public String getDefaultDockerImageName(String projectName) {
    return String.format("%s%d", getDefaultName(projectName), new Random().nextInt(10000));
  }

  public String getDefaultDockerContainerName(String imageName) {
    return String.format("%s-%d", imageName, new Random().nextInt(10000));
  }

  public String getDefaultArtifactName(String projectName) {
    return getDefaultName(projectName) + ".war";
  }
}
