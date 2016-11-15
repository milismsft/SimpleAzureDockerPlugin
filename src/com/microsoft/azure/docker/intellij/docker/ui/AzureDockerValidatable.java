package com.microsoft.azure.docker.intellij.docker.ui;

import com.intellij.openapi.ui.ValidationInfo;

public interface AzureDockerValidatable {
  ValidationInfo doValidate();
}
