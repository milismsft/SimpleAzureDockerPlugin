package com.microsoft.azure.docker.intellij.docker.ui.forms;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.*;
import com.intellij.ui.table.JBTable;
import com.intellij.ui.wizard.WizardNavigationState;
import com.intellij.ui.wizard.WizardStep;
import com.microsoft.azure.docker.intellij.docker.ui.AzureSelectDockerWizardModel;
import com.microsoft.azure.docker.intellij.docker.ui.AzureSelectDockerWizardStep;
import com.microsoft.azure.docker.resources.DockerHost;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;
import com.microsoft.intellij.ui.util.UIUtils;
import com.microsoft.intellij.util.PluginUtil;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.Vector;

public class AzureSelectDockerHostStep extends AzureSelectDockerWizardStep {
  private AzureSelectDockerWizardModel model;
  private JPanel rootSelectHostPanel;
  private JTextField dockerImageName;
  private JCheckBox createRunConfigurationCheckBox;
  private JCheckBox createDebugConfigurationCheckBox;
  private JPanel dockerHostsPanel;
  private TextFieldWithBrowseButton artifactPath;
  private JBTable dockerHostsTable;

  private AzureDockerUIManager dockerUIManager;
  private DockerHostsTableSelection dockerHostsTableSelection;


  public AzureSelectDockerHostStep(String title, AzureSelectDockerWizardModel model){
    // TODO: The message should go into the plugin property file that handles the various dialog titles
    super(title, "Type an image name and select a Docker host");
    this.model = model;
    this.dockerUIManager = model.getDockerUIManager();
    this.dockerHostsTableSelection = null;

    Project project = model.getProject();
    project.getProjectFilePath();

    artifactPath.addActionListener(UIUtils.createFileChooserListener(artifactPath, model.getProject(),
        FileChooserDescriptorFactory.createSingleFolderDescriptor()));
    artifactPath.setText(model.getProject().getBasePath());

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
          DefaultTableModel model = (DefaultTableModel) dockerHostsTable.getModel();
          if ((Boolean) model.getValueAt(currentSelection.row, currentSelection.column)) {
            if (dockerHostsTableSelection == null) {
              dockerHostsTableSelection = currentSelection;
            } else {
              int oldRow = dockerHostsTableSelection.row;
              dockerHostsTableSelection = null;
              if (currentSelection.row != oldRow) {
                // disable previous selection
                model.setValueAt(false, oldRow, 0);
                dockerHostsTableSelection = currentSelection;
              }
            }
          }
        }
      }
    });

    dockerHostsPanel = ToolbarDecorator.createDecorator(dockerHostsTable)
        .setAddAction(new AnActionButtonRunnable() {
          @Override
          public void run(AnActionButton button) {
//            addBtnListener();
          }
        }).setEditAction(new AnActionButtonRunnable() {
          @Override
          public void run(AnActionButton anActionButton) {
//            editBtnListener();
          }
        }).setRemoveAction(new AnActionButtonRunnable() {
          @Override
          public void run(AnActionButton button) {
//            removeBtnListener();
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
        }).disableUpDownActions().createPanel();
  }

  private void onAddNewDockerHostAction() {

  }

  private void onEditDockerHostAction() {

  }

  private void onRemoveDockerHostAction() {

  }

  /* Force a refresh of the docker hosts entries in the select host table
   *   This call will retrieve the latest list of VMs form Azure suitable to be a Docker Host
   */
  void forceRefreshDockerHostsTable() {
    // call into Ops to retrieve the latest list of Docker VMs

    // Fake call to create some dummy entries
    dockerUIManager.createNewFakeDockerHostList();

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
    final DefaultTableModel model = (DefaultTableModel) dockerHostsTable.getModel();

    while (model.getRowCount() > 0) {
      model.removeRow(0);
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
          model.addRow(row);
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

    if (dockerHostsTableSelection == null){
      ValidationInfo info = new ValidationInfo("Missing Docker image name", dockerImageName);
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
