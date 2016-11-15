package com.microsoft.azure.docker.intellij.docker.ui;

import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogEarthquakeShaker;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.wm.IdeFocusManager;
import com.intellij.ui.wizard.WizardDialog;
import com.microsoft.intellij.util.PluginUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class AzureSelectDockerWizardDialog extends WizardDialog<AzureSelectDockerWizardModel> {
  private AzureSelectDockerWizardModel model;

  public AzureSelectDockerWizardDialog(AzureSelectDockerWizardModel model) {
    super(model.getProject(), true, model);
    this.model = model;
    model.setSelectDockerWizardDialog(this);
  }

  @Override
  public void onWizardGoalAchieved() {
    super.onWizardGoalAchieved();
  }

  @Override
  protected Dimension getWindowPreferredSize() {
    return new Dimension(600, 600);
  }

  @Nullable
  @Override
  protected ValidationInfo doValidate() {
    return myModel.doValidate();
  }

  @Override
  protected void doOKAction() {
//        validateInput();
    if (isOKActionEnabled() && performFinish()) {
      super.doOKAction();
    }
  }

  public void DialogShaker(ValidationInfo info) {
    if(info.component != null && info.component.isVisible()) {
      IdeFocusManager.getInstance((Project)null).requestFocus(info.component, true);
    }

    DialogEarthquakeShaker.shake(getPeer().getWindow());

  }

  /**
   * This method gets called when wizard's finish button is clicked.
   *
   * @return True, if project gets created successfully; else false.
   */
  private boolean performFinish() {
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        // Do the heavy workload in a background thread
        doFinish();
      }
    };

    ProgressManager progressManager = null;
    try {
      progressManager = ProgressManager.getInstance();
    } catch (Exception e) {
      progressManager = null;
    }

    if (progressManager != null) {
      progressManager.runProcessWithProgressSynchronously(runnable, "Deploying...", true, model.getProject());
    } else {
      doFinish();
    }

    return true;
  }

  private void doFinish() {
    try {
      // do something interesting here
    } catch (Exception e) {
      // TODO: These strings should be retrieved from AzureBundle
      PluginUtil.displayErrorDialogAndLog("Error", "Error deploying to Docker", e);
    }
  }
}
