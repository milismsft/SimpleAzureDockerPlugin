package com.microsoft.azure.docker.intellij.docker.ui.forms;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.*;
import com.intellij.ui.table.JBTable;
import com.intellij.ui.wizard.WizardNavigationState;
import com.intellij.ui.wizard.WizardStep;
import com.microsoft.azure.docker.intellij.docker.ui.*;
import com.microsoft.azure.docker.intellij.docker.ui.dialogs.AzureEditDockerLoginCredsDialog;
import com.microsoft.azure.docker.intellij.docker.ui.dialogs.AzureViewDockerDialog;
import com.microsoft.azure.docker.resources.DockerHost;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;
import com.microsoft.azure.docker.ui.EditableDockerHost;
import com.microsoft.intellij.ui.util.UIUtils;
import com.microsoft.intellij.util.PluginUtil;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.Vector;

public class AzureSelectDockerHostStep extends AzureSelectDockerWizardStep {
  private JPanel rootSelectHostPanel;
  private JTextField dockerImageName;
  private JCheckBox createRunConfigurationCheckBox;
  private JCheckBox createDebugConfigurationCheckBox;
  private JPanel dockerHostsPanel;
  private TextFieldWithBrowseButton artifactPath;
  private JBTable dockerHostsTable;

  private AzureSelectDockerWizardModel model;
  private AzureDockerUIManager dockerUIManager;
  private DockerHostsTableSelection dockerHostsTableSelection;


  public AzureSelectDockerHostStep(String title, AzureSelectDockerWizardModel model){
    // TODO: The message should go into the plugin property file that handles the various dialog titles
    super(title, "Type an image name and select a Docker host");
    this.model = model;
    this.dockerUIManager = model.getDockerUIManager();
    this.dockerHostsTableSelection = null;

    String defaultImageName = model.dockerImageDescription.dockerImageName;
    String defaultArtifactName = model.dockerImageDescription.artifactName;

    dockerImageName.setText(defaultImageName);

    artifactPath.addActionListener(UIUtils.createFileChooserListener(artifactPath, model.getProject(),
        FileChooserDescriptorFactory.createSingleLocalFileDescriptor()));
    artifactPath.setText(model.getProject().getBasePath() + "/out/Docker/"+ defaultArtifactName);

    forceRefreshDockerHostsTable();

  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
    final DefaultTableModel dockerListTableModel = new DefaultTableModel() {
      @Override
      public boolean isCellEditable(int row, int col) {
        return (col == 0);
      }

      public Class<?> getColumnClass(int colIndex) {
        return getValueAt(0, colIndex).getClass();
      }
    };

    dockerListTableModel.addColumn("");
    dockerListTableModel.addColumn("Name");
    dockerListTableModel.addColumn("State");
    dockerListTableModel.addColumn("OS");
    dockerListTableModel.addColumn("API URL");
    dockerHostsTable = new JBTable(dockerListTableModel);
    dockerHostsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    TableColumn column = dockerHostsTable.getColumnModel().getColumn(0);
    column.setMinWidth(23);
    column.setMaxWidth(23);
    column = dockerHostsTable.getColumnModel().getColumn(1);
    column.setPreferredWidth(150);
    column = dockerHostsTable.getColumnModel().getColumn(2);
    column.setPreferredWidth(30);
    column = dockerHostsTable.getColumnModel().getColumn(3);
    column.setPreferredWidth(110);
    column = dockerHostsTable.getColumnModel().getColumn(4);
    column.setPreferredWidth(150);

    dockerListTableModel.addTableModelListener(new TableModelListener() {
      @Override
      public void tableChanged(TableModelEvent e) {
        DockerHostsTableSelection currentSelection = new DockerHostsTableSelection();
        currentSelection.column = dockerHostsTable.getSelectedColumn();
        currentSelection.row = dockerHostsTable.getSelectedRow();

        if (currentSelection.column == 0) {
          DefaultTableModel tableModel = (DefaultTableModel) dockerHostsTable.getModel();
          if ((Boolean) tableModel.getValueAt(currentSelection.row, currentSelection.column)) {
            if (dockerHostsTableSelection == null) {
              dockerHostsTableSelection = currentSelection;
            } else {
              int oldRow = dockerHostsTableSelection.row;
              dockerHostsTableSelection = null;
              if (currentSelection.row != oldRow) {
                // disable previous selection
                tableModel.setValueAt(false, oldRow, 0);
                dockerHostsTableSelection = currentSelection;
              }
            }
          }
        }
      }
    });

    AnActionButton viewDockerHostsAction = new ToolbarDecorator.ElementActionButton("Details", AllIcons.Actions.Export) {
      @Override
      public void actionPerformed(AnActionEvent anActionEvent) {
        onViewDockerHostAction();
      }
    };

    AnActionButton refreshDockerHostsAction = new AnActionButton("Refresh", AllIcons.Actions.Refresh) {
      @Override
      public void actionPerformed(AnActionEvent anActionEvent) {
        onRefreshDockerHostAction();
      }
    };

    ToolbarDecorator tableToolbarDecorator = ToolbarDecorator.createDecorator(dockerHostsTable)
        .setAddAction(new AnActionButtonRunnable() {
          @Override
          public void run(AnActionButton button) {
            onAddNewDockerHostAction();
          }
        }).setEditAction(new AnActionButtonRunnable() {
          @Override
          public void run(AnActionButton anActionButton) {
            onEditDockerHostAction();
          }
        }).setRemoveAction(new AnActionButtonRunnable() {
          @Override
          public void run(AnActionButton button) {
            onRemoveDockerHostAction();
          }
        }).setEditActionUpdater(new AnActionButtonUpdater() {
          @Override
          public boolean isEnabled(AnActionEvent e) {
            return dockerHostsTable.getSelectedRow() != -1;
          }
        }).setRemoveActionUpdater(new AnActionButtonUpdater() {
          @Override
          public boolean isEnabled(AnActionEvent e) {
            return dockerHostsTable.getSelectedRow() != -1;
          }
        }).disableUpDownActions()
        .addExtraActions(viewDockerHostsAction, refreshDockerHostsAction);


    dockerHostsPanel = tableToolbarDecorator.createPanel();
  }

  private void onRefreshDockerHostAction() {
    PluginUtil.displayInfoDialog("Refresh Docker Host", "some docker host");
  }

  private void onViewDockerHostAction() {
    try {
      DefaultTableModel tableModel = (DefaultTableModel) dockerHostsTable.getModel();
      String apiURL = (String) tableModel.getValueAt(dockerHostsTable.getSelectedRow(), 4);

      EditableDockerHost editableDockerHost = new EditableDockerHost(dockerUIManager.getDockerHostForURL(apiURL));

      AzureViewDockerDialog viewDockerDialog = new AzureViewDockerDialog(model.getProject(), editableDockerHost, dockerUIManager);
      viewDockerDialog.show();

      if (viewDockerDialog.getInternalExitCode() == AzureViewDockerDialog.UPDATE_EXIT_CODE) {
        onEditDockerHostAction();
      }
    } catch (Exception e) {
      String msg = "An error occurred while attempting to view the selected Docker host.\n" + e.getMessage();
      PluginUtil.displayErrorDialogAndLog("Error", msg, e);
    }
  }

  private void onAddNewDockerHostAction() {
    AzureNewDockerWizardModel newDockerHostModel = new AzureNewDockerWizardModel(model.getProject(), dockerUIManager);
    AzureNewDockerWizardDialog wizard = new AzureNewDockerWizardDialog(newDockerHostModel);
    wizard.setTitle("Create a Docker Host");
    wizard.show();

    if (wizard.getExitCode() == 0) {
      dockerHostsTable.setEnabled(false);

      DockerHost host = newDockerHostModel.dockerHostDescription;
      host = dockerUIManager.createNewFakeDockerHost("myNewHost");
      model.dockerImageDescription.host = host;
      model.dockerImageDescription.hasNewDockerHost = true;

      final DefaultTableModel tableModel = (DefaultTableModel) dockerHostsTable.getModel();
      Vector<Object> row = new Vector<Object>();
      row.add(false);
      row.add(host.name);
      row.add("NEW-AZURE-VM");
      row.add(host.hostOSType.toString());
      row.add(host.apiUrl);
      tableModel.insertRow(0, row);
      if (dockerHostsTableSelection != null && (Boolean) tableModel.getValueAt(dockerHostsTableSelection.row, 0)) {
        tableModel.setValueAt(false, dockerHostsTableSelection.row, 0);
      }
      tableModel.setValueAt(true, 0, 0);
    }
  }

  private void onEditDockerHostAction() {
    try {
      DefaultTableModel tableModel = (DefaultTableModel) dockerHostsTable.getModel();
      String apiURL = (String) tableModel.getValueAt(dockerHostsTable.getSelectedRow(), 4);

      EditableDockerHost editableDockerHost = new EditableDockerHost(dockerUIManager.getDockerHostForURL(apiURL));

      AzureEditDockerLoginCredsDialog editDockerDialog = new AzureEditDockerLoginCredsDialog(model.getProject(), editableDockerHost, dockerUIManager);
      editDockerDialog.show();

      if (editDockerDialog.getExitCode() == 0) {
        forceRefreshDockerHostsTable();
      }
    } catch (Exception e) {
      String msg = "An error occurred while attempting to edit the selected Docker host.\n" + e.getMessage();
      PluginUtil.displayErrorDialogAndLog("Error", msg, e);
    }
  }

  private void onRemoveDockerHostAction() {

  }

  /* Force a refresh of the docker hosts entries in the select host table
   *   This call will retrieve the latest list of VMs form Azure suitable to be a Docker Host
   */
  void forceRefreshDockerHostsTable() {
    dockerUIManager.forceRefreshDockerHostsList();
    refreshDockerHostsTable();
  }

  DockerHost newDockerHostDescription() {
    // TODO: create new docker dialog window and retrieve the description of the docker host to be created
    // i.e.:
    //    newDockerHost = myNewDialog.getDockerHostDescription();

    // temporary hack to add a fake docker host entry
    return dockerUIManager.createNewFakeDockerHost("myNewHost1");
  }

  /* Refresh the docker hosts entries in the select host table
   *
   */
  void refreshDockerHostsTable() {
    final DefaultTableModel tableModel = (DefaultTableModel) dockerHostsTable.getModel();

    while (tableModel.getRowCount() > 0) {
      tableModel.removeRow(0);
    }

    try {
      if (dockerUIManager.dockerHostsList != null) {
        for (DockerHost host : dockerUIManager.dockerHostsList) {
          Vector<Object> row = new Vector<Object>();
          row.add(false);
          row.add(host.name);
          row.add(host.state.toString());
          row.add(host.hostOSType.toString());
          row.add(host.apiUrl);
          tableModel.addRow(row);
        }
      }
    } catch (Exception e) {
      String msg = "An error occurred while attempting to get the list of recognizable Docker hosts.\n" + e.getMessage();
      PluginUtil.displayErrorDialogAndLog("Error", msg, e);
    }
  }

  @Override
  public ValidationInfo doValidate() {
    if (dockerImageName.getText() == null || dockerImageName.getText().equals("")){
      ValidationInfo info = new ValidationInfo("Missing Docker image name", dockerImageName);
      model.getSelectDockerWizardDialog().DialogShaker(info);
      return info;
    }

    if (artifactPath.getText() == null || artifactPath.getText().equals("")){
      ValidationInfo info = new ValidationInfo("Missing the artifact to be published", artifactPath);
      model.getSelectDockerWizardDialog().DialogShaker(info);
      return info;
    }

    if (dockerHostsTableSelection == null && !model.dockerImageDescription.hasNewDockerHost){
      ValidationInfo info = new ValidationInfo("No Docker host has been selected", dockerHostsTable);
      model.getSelectDockerWizardDialog().DialogShaker(info);
      return info;
    }

    return null;
  }

  @Override
  public JComponent prepare(final WizardNavigationState state) {
    rootSelectHostPanel.revalidate();
    return rootSelectHostPanel;
  }

  @Override
  public WizardStep onNext(final AzureSelectDockerWizardModel model) {
    if (doValidate() == null) {
      try {
        if (!model.dockerImageDescription.hasNewDockerHost) {
          DefaultTableModel tableModel = (DefaultTableModel) dockerHostsTable.getModel();
          String apiURL = (String) tableModel.getValueAt(dockerHostsTableSelection.row, 4);
          model.dockerImageDescription.host = dockerUIManager.getDockerHostForURL(apiURL);
        }
        model.dockerImageDescription.dockerImageName = dockerImageName.getText();
        model.dockerImageDescription.artifactName = artifactPath.getText();
        model.dockerImageDescription.hasRunConfiguration = createRunConfigurationCheckBox.isSelected();
        model.dockerImageDescription.hasDebugConfiguration = createDebugConfigurationCheckBox.isSelected();
      } catch (Exception e) {}

      return super.onNext(model);
    } else {
      return this;
    }
  }

  private class DockerHostsTableSelection {
    int row;
    int column;
    DockerHost host;
  }

}
