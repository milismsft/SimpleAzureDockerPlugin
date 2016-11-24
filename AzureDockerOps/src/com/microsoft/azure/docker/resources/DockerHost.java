package com.microsoft.azure.docker.resources;

import com.jcraft.jsch.Session;
import com.sun.javadoc.Doc;

import java.util.List;
import java.util.Objects;

public class DockerHost {
  public String name;
  public AzureDockerVM hostVM;
  public DockerHostVMState state;
  public boolean hasSSHLogIn;
  public Session session;
  public DockerHostOSType hostOSType;
  public AzureDockerCertVault certVault; // see using Azure Key Vault to store secrets
  public boolean isTLSSecured;
  public boolean hasKeyVault;
  public String apiUrl;
  public String port;
  public List<DockerImage> dockerImages;

  public DockerHost() {}

  public DockerHost(DockerHost copyHost) {
    if (copyHost == null) {
      return;
    }

    this.name = copyHost.name;
    this.hostVM = copyHost.hostVM;
    this.state = copyHost.state;
    this.hasSSHLogIn = copyHost.hasSSHLogIn;
    this.hostOSType = copyHost.hostOSType;
    this.certVault = (copyHost.certVault != null) ? new AzureDockerCertVault(copyHost.certVault) : null;
    this.isTLSSecured = copyHost.isTLSSecured;
    this.hasKeyVault = copyHost.hasKeyVault;
    this.apiUrl = copyHost.apiUrl;
    this.port = copyHost.port;
    this.dockerImages = copyHost.dockerImages;
    this.session = null;
  }

  public boolean equalsTo(DockerHost otherHost) {
    return Objects.equals(this.name, otherHost.name) &&
        this.hostVM == otherHost.hostVM &&
        this.state == otherHost.state &&
        this.hasSSHLogIn == otherHost.hasSSHLogIn &&
        this.isTLSSecured == otherHost.isTLSSecured &&
        this.hostOSType == otherHost.hostOSType &&
        this.certVault.equalsTo(otherHost.certVault) &&
        this.hasKeyVault == otherHost.hasKeyVault &&
        Objects.equals(this.apiUrl, otherHost.apiUrl) &&
        Objects.equals(this.port, otherHost.port);
  }

  public enum DockerHostOSType {
    UBUNTU_SERVER_14,
    UBUNTU_SERVER_16,
    UBUNTU_SNAPPY_CORE,
    COREOS,
    OPENLOGIC_CENTOS,
    LINUX_OTHER
  }

  public enum DockerHostVMState {
    ACTIVE,
    CREATING,
    DELETING,
    UPDATING,
    STOPPED,
    STARTING,
    FAILED
  }
}
