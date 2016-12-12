package com.microsoft.intellij.helpers;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IDEHelperImpl {
  class ProjectDescriptor {
    @NotNull
    private final String name;
    @NotNull
    private final String path;

    public ProjectDescriptor(@NotNull String name, @NotNull String path) {
      this.name = name;
      this.path = path;
    }

    @NotNull
    public String getName() {
      return name;
    }

    @NotNull
    public String getPath() {
      return path;
    }
  }

  class ArtifactDescriptor {
    @NotNull
    private String name;
    @NotNull
    private String artifactType;

    public ArtifactDescriptor(@NotNull String name, @NotNull String artifactType) {
      this.name = name;
      this.artifactType = artifactType;
    }

    @NotNull
    public String getName() {
      return name;
    }

    @NotNull
    public String getArtifactType() {
      return artifactType;
    }
  }

  public static void runInBackground(@Nullable final Object project, @NotNull final String name, final boolean canBeCancelled,
                                     final boolean isIndeterminate, @Nullable final String indicatorText,
                                     final Runnable runnable) {
    // background tasks via ProgressManager can be scheduled only on the
    // dispatch thread
    ApplicationManager.getApplication().invokeLater(new Runnable() {
      @Override
      public void run() {
        ProgressManager.getInstance().run(new Task.Backgroundable((Project) project,
            name, canBeCancelled) {
          @Override
          public void run(@NotNull ProgressIndicator indicator) {
            if (isIndeterminate) {
              indicator.setIndeterminate(true);
            }

            if (indicatorText != null) {
              indicator.setText(indicatorText);
            }

            runnable.run();
          }
        });
      }
    }, ModalityState.any());
  }
}
