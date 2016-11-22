package com.microsoft.azure.docker.resources;

import com.jcraft.jsch.Session;

import java.util.List;

public class DockerHost {
  public String name;
  public AzureDockerCertVault certVault;
  public String apiUrl;
  public String port;
  public boolean hasReleaseConfig;
  public boolean hasDebugConfig;
  public AzureDockerVM hostVM;
  public List<DockerImage> dockerImages;
  public boolean isTLSSecured;
  public DockerHostOSType hostOSType;
  public Session session;
  public DockerHostVMState state;

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
