package com.microsoft.azure.docker.ui;

import com.microsoft.azure.docker.resources.*;
import com.microsoft.azure.management.Azure;

import java.util.*;

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

  public List<String> getDockerVMStates() {
    return Arrays.asList("Running", "Starting", "Stopped", "Deleting", "Failed");
  }

  public DockerHost createNewFakeDockerHost(String name) {
    DockerHost host = new DockerHost();
    host.name = name;
    host.apiUrl = "http://" + name + ".centralus.cloudapp.azure.com";
    host.port = "2375";
    host.isTLSSecured = false;
    host.state = DockerHost.DockerHostVMState.RUNNING;
    host.hostOSType = DockerHost.DockerHostOSType.UBUNTU_SERVER_16;
    host.hostVM = new AzureDockerVM();
    host.hostVM.name = name;
    host.hostVM.vmSize = "Standard_DS1_v2";
    host.hostVM.region = "centralus";
    host.hostVM.resourceGroupName = "myresourcegroup";
    host.hostVM.vnetName = "network1";
    host.hostVM.vnetAddressSpace = "10.0.0.0/8";
    host.hostVM.subnetName = "subnet1";
    host.hostVM.subnetAddressRange = "10.0.0.0/16";
    host.hostVM.storageAccountName = "sa12313111244";
    host.hostVM.storageAccountType = "Premium_LSR";

    host.hasPwdLogIn = true;
    host.hasSSHLogIn = true;
    host.isTLSSecured = true;
    host.hasKeyVault = true;

    host.certVault = new AzureDockerCertVault();
    host.certVault.name = "mykeyvault1";
    host.certVault.url = host.certVault.name + ".someregion.azure.com";
    host.certVault.hostName = name;
    host.certVault.resourceGroupName = "mykeyvault1rg";
    host.certVault.region = "centralus";
    host.certVault.userName = "ubuntu";
    host.certVault.vmPwd = "PasswordGoesHere";
    host.certVault.sshKey = "id_rsa";
    host.certVault.sshPubKey = "id_rsa.pub";
    host.certVault.tlsCACert = "ca.pem";
    host.certVault.tlsCAKey = "ca-key.pem";
    host.certVault.tlsClientCert = "cert.pem";
    host.certVault.tlsClientKey = "key.pem";
    host.certVault.tlsServerCert = "server.pem";
    host.certVault.tlsServerKey = "server-key.pem";

    return host;
  }

  public static List<String> getDockerVMStateToActionList(DockerHost.DockerHostVMState currentVMState) {
    if (currentVMState == DockerHost.DockerHostVMState.RUNNING) {
      return Arrays.asList(currentVMState.toString(), "Stop", "Restart", "Delete");
    } else if (currentVMState == DockerHost.DockerHostVMState.STOPPED) {
      return Arrays.asList(currentVMState.toString(), "Start", "Delete");
    } else if (currentVMState == DockerHost.DockerHostVMState.FAILED) {
      return Arrays.asList(currentVMState.toString(), "Stop", "Restart", "Delete");
    } else {
      return Arrays.asList(currentVMState.toString());
    }
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
