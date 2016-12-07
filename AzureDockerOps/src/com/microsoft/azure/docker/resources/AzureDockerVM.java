package com.microsoft.azure.docker.resources;

import java.util.Map;

public class AzureDockerVM {
  public String name;
  public String vmSize;
  public String resourceGroupName;
  public String region;
  public String availabilitySet;
  public String vnetName;
  public String vnetAddressSpace;
  public String subnetName;
  public String subnetAddressRange;
  public String networkSecurityGroupName;
  public String publicIpName;
  public String publicIp;
  public String dnsName;
  public String storageAccountName;
  public String storageAccountType;
  public String osDiskName;
  public AzureOSHost osHost;
  public Map<String, String> tags;

}
