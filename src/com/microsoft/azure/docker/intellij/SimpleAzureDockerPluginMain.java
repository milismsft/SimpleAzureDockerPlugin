package com.microsoft.azure.docker.intellij;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.microsoft.azure.docker.intellij.docker.AzureDockerPluginStart;
import com.microsoft.intellij.util.PluginUtil;

import javax.swing.*;

import static com.intellij.projectImport.ProjectImportBuilder.getCurrentProject;

public class SimpleAzureDockerPluginMain extends AnAction {

  @Override
  public void actionPerformed(AnActionEvent e) {
    // TODO: insert action logic here

    Project project = getCurrentProject();


    AzureDockerPluginStart dialog = new AzureDockerPluginStart(project);
    dialog.show();

  }
}
