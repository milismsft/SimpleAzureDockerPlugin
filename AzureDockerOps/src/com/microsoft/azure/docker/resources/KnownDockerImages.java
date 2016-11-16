package com.microsoft.azure.docker.resources;

import static com.microsoft.azure.docker.resources.KnownDockerImages.KnownDefaultDockerfiles.JBOSS_WILDFLY_DEFAULT_DOCKERFILE;
import static com.microsoft.azure.docker.resources.KnownDockerImages.KnownDefaultDockerfiles.TOMCAT8_DEFAULT_DOCKERFILE;

public enum KnownDockerImages {

  JBOSS_WILDFLY("JBoss WildFly", JBOSS_WILDFLY_DEFAULT_DOCKERFILE, "18080:80"),
  TOMCAT8("tomcat:8.0.20-jre8", TOMCAT8_DEFAULT_DOCKERFILE, "18080:80");

  private final String dockerfileContent;
  private final String name;
  private final String portSettings;

  KnownDockerImages(String name, String dockerFile, String defaultPortSettings) {
    this.dockerfileContent = dockerFile;
    this.name = name;
    this.portSettings = defaultPortSettings;
  }

  public String toString(){
    return name;
  }

  public String getPortSettings() {return portSettings;}
  public String getDockerfileContent() {return  dockerfileContent;}

  public static class KnownDefaultDockerfiles {
    public static final String JBOSS_WILDFLY_DEFAULT_DOCKERFILE =
        "FROM jboss/wildfly\n" +
        "ADD _MyArtifact.war_ /opt/jboss/wildfly/standalone/deployments/\n";
    public static final String TOMCAT8_DEFAULT_DOCKERFILE =
        "FROM tomcat:8.0.20-jre8\n" +
        "ADD _MyArtifact.war_ /usr/local/tomcat/webapps/\n";
  }
}

