package com.microsoft.azure.docker.resources;

import groovy.transform.Canonical;

import java.util.Objects;

@Canonical
public class AzureDockerCertVault {
  public String name;                   // Azure Key Vault's name
  public String hostName;               // name of the host (Docker host) to store the credentials for
  public String availabilitySet;        // Azure availability set where to create the vault
  public String resourceGroupName;      // Azure resource group where to create the vault
  public String region;                 // Azure location where to create the vault
  public String servicePrincipalId;     // service principal's client id if user logged in with service principal credentials
  public String userName;               // current user name as logged in via Azure Auth
  public String vmUsername;             // username to be used fro VM (Docker host) login
  public String vmPwd;                  // password to be used for VM (Docker host) login
  public String sshKey;                 // see "id_rsa"
  public String sshPubKey;              // see "id_rsa.pub"
  public String tlsCACert;              // see "ca.pem"
  public String tlsCAKey;               // see "ca-key.pem"
  public String tlsClientCert;          // see "cert.pem"
  public String tlsClientKey;           // see "key.pem"
  public String tlsServerCert;          // see "server.pem"
  public String tlsServerKey;           // see "server-key.pem"

  public AzureDockerCertVault() {}

  public AzureDockerCertVault(AzureDockerCertVault otherVault) {
    this.name = otherVault.name;
    this.hostName = otherVault.hostName;
    this.availabilitySet = otherVault.availabilitySet;
    this.resourceGroupName = otherVault.resourceGroupName;
    this.region = otherVault.region;
    this.servicePrincipalId = otherVault.servicePrincipalId;
    this.userName = otherVault.userName;
    this.vmUsername = otherVault.vmUsername;
    this.vmPwd = otherVault.vmPwd;
    this.sshKey = otherVault.sshKey;
    this.sshPubKey = otherVault.sshPubKey;
    this.tlsCACert = otherVault.tlsCACert;
    this.tlsCAKey = otherVault.tlsCAKey;
    this.tlsClientCert = otherVault.tlsClientCert;
    this.tlsClientKey = otherVault.tlsClientKey;
    this.tlsServerCert = otherVault.tlsServerCert;
    this.tlsServerKey = otherVault.tlsServerKey;
  }

  public boolean equalsTo(AzureDockerCertVault otherVault) {
    return Objects.equals(this.name, otherVault.name) &&
        Objects.equals(this.hostName, otherVault.hostName) &&
        Objects.equals(this.availabilitySet, otherVault.availabilitySet) &&
        Objects.equals(this.resourceGroupName, otherVault.resourceGroupName) &&
        Objects.equals(this.region, otherVault.region) &&
        Objects.equals(this.servicePrincipalId, otherVault.servicePrincipalId) &&
        Objects.equals(this.userName, otherVault.userName) &&
        Objects.equals(this.vmUsername, otherVault.vmUsername) &&
        Objects.equals(this.vmPwd, otherVault.vmPwd) &&
        Objects.equals(this.sshKey, otherVault.sshKey) &&
        Objects.equals(this.sshPubKey, otherVault.sshPubKey) &&
        Objects.equals(this.tlsCACert, otherVault.tlsCACert) &&
        Objects.equals(this.tlsCAKey, otherVault.tlsCAKey) &&
        Objects.equals(this.tlsClientKey, otherVault.tlsClientKey) &&
        Objects.equals(this.tlsServerCert, otherVault.tlsServerCert) &&
        Objects.equals(this.tlsServerKey, otherVault.tlsServerKey);
  }
}
