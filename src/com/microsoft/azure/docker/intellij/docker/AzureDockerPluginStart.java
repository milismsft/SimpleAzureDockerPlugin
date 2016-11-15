package com.microsoft.azure.docker.intellij.docker;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogEarthquakeShaker;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.wm.IdeFocusManager;
import com.microsoft.azure.docker.creds.AzureCredsManager;
import com.microsoft.azure.docker.intellij.docker.ui.AzureSelectDockerWizardDialog;
import com.microsoft.azure.docker.intellij.docker.ui.AzureSelectDockerWizardModel;
import com.microsoft.azure.docker.intellij.docker.ui.forms.AzureViewDockerHostsDialog;
import com.microsoft.azure.docker.resources.AzureDockerSubscription;
import com.microsoft.azure.docker.ui.AzureDockerUIManager;
import com.microsoft.intellij.util.PluginUtil;
import com.microsoft.azure.management.resources.Location;
import com.microsoft.azure.management.resources.Subscription;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static com.intellij.projectImport.ProjectImportBuilder.getCurrentProject;

public class AzureDockerPluginStart extends DialogWrapper {
  private JPanel mainPanel;
  private JButton publishAsAzureDockerButton;
  private JButton viewAzureDockerHostsButton;
  private JRadioButton selectADLoginRadioButton;
  private JRadioButton selectSPLoginRadioButton;
  private ButtonGroup loginButtonGroup;
  private JTable subscriptionTable;
  private JScrollPane subscriptionPane;

  private Project project;
  private AzureDockerUIManager dockerUIManager;


  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    return mainPanel;
  }

  @Nullable
  @Override
  protected String getHelpId() {
    return null;
  }

  @Nullable
  @Override
  protected Action[] createActions() {
    Action action = getCancelAction();
    action.putValue(Action.NAME, "Done");

    return new Action[] {getCancelAction()};
  }

  public AzureDockerPluginStart(Project project) {
    super(project, true);

    this.project = project;
    setModal(true);
    setTitle("Select Azure subscription");

    init();

    publishAsAzureDockerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onPublishAsAzureDockerContainer();
      }
    });

    viewAzureDockerHostsButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onViewAzureDockerHosts();
      }
    });

    selectADLoginRadioButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onSelectADLoginRadioButton();
      }
    });

    selectSPLoginRadioButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        onSelectSPLoginRadioButton();
      }
    });

    final DefaultTableModel model = new DefaultTableModel() {
      @Override
      public boolean isCellEditable(int row, int col) {
        return (col == 0);
      }

      public Class<?> getColumnClass(int colIndex) {
        return getValueAt(0, colIndex).getClass();
      }
    };

    model.addColumn("");
    model.addColumn("Name");
    model.addColumn("Id");

    subscriptionTable.setModel(model);

    TableColumn column = subscriptionTable.getColumnModel().getColumn(0);
    column.setMinWidth(23);
    column.setMaxWidth(23);

    loginButtonGroup = new ButtonGroup();
    loginButtonGroup.add(selectADLoginRadioButton);
    loginButtonGroup.add(selectSPLoginRadioButton);

    dockerUIManager = null;

  }

  private void onSelectADLoginRadioButton() {
    try {
      List<Subscription> subscriptionList = AzureCredsManager.getADSubscriptions();
      List<AzureDockerSubscription> subscriptions = new ArrayList<>();

      for (Subscription subscription : subscriptionList) {
        AzureDockerSubscription dockerSubscription = createSubscriptionElement(subscription);
        subscriptions.add(dockerSubscription);
      }

      createSubscriptionTable(subscriptions);
    } catch (Exception e) {
      String msg = "An error occurred while attempting to get the subscription list.\n" + e.getMessage();
      PluginUtil.displayErrorDialogAndLog("Error", msg, e);
    }
  }

  private void onSelectSPLoginRadioButton() {
    try {
      List<Subscription> subscriptionList = AzureCredsManager.getSPSubscriptions();
      List<AzureDockerSubscription> subscriptions = new ArrayList<>();

      for (Subscription subscription : subscriptionList) {
        AzureDockerSubscription dockerSubscription = createSubscriptionElement(subscription);
        subscriptions.add(dockerSubscription);
      }

      createSubscriptionTable(subscriptions);
    } catch (Exception e) {
      String msg = "An error occurred while attempting to get the subscription list.\n" + e.getMessage();
      PluginUtil.displayErrorDialogAndLog("Error", msg, e);
    }
  }

  private void createSubscriptionTable(List<AzureDockerSubscription> subscriptionList) {
    final DefaultTableModel model = (DefaultTableModel) subscriptionTable.getModel();

    while (model.getRowCount() > 0) {
      model.removeRow(0);
    }

    try {
      if (subscriptionList != null) {
        for (AzureDockerSubscription subs : subscriptionList) {
          Vector<Object> row = new Vector<Object>();
          row.add(subs.isSelected);
          row.add(subs.name);
          row.add(subs.id);
          model.addRow(row);
        }
      }
    } catch (Exception e) {
      String msg = "An error occurred while attempting to get the subscription list.\n" + e.getMessage();
      PluginUtil.displayErrorDialogAndLog("Error", msg, e);
    }
  }

  private void onPublishAsAzureDockerContainer() {
    System.out.println("Publishing as Azure Docker Container");
    setSelectedSubscriptions();

    if (dockerUIManager == null || dockerUIManager.subscriptionList == null || dockerUIManager.subscriptionList.size() < 1) {
      ValidationInfo info = new ValidationInfo("Must select at least one subscription", subscriptionTable);
      if(info.component != null && info.component.isVisible()) {
        IdeFocusManager.getInstance((Project)null).requestFocus(info.component, true);
      }

      DialogEarthquakeShaker.shake((JDialog) getPeer().getWindow());

      return;
    }

    AzureSelectDockerWizardModel model = new AzureSelectDockerWizardModel(project, dockerUIManager);
    AzureSelectDockerWizardDialog wizard = new AzureSelectDockerWizardDialog(model);
    wizard.setTitle("New Azure Docker Container Deployment");
    wizard.show();

  }

  private void onViewAzureDockerHosts() {
    System.out.println("View Azure Docker Hosts");
    setSelectedSubscriptions();

    if (dockerUIManager == null || dockerUIManager.subscriptionList == null || dockerUIManager.subscriptionList.size() < 1) {
      ValidationInfo info = new ValidationInfo("Must select at least one subscription", subscriptionTable);
      if(info.component != null && info.component.isVisible()) {
        IdeFocusManager.getInstance((Project)null).requestFocus(info.component, true);
      }

      DialogEarthquakeShaker.shake((JDialog) getPeer().getWindow());

      return;
    }

    AzureViewDockerHostsDialog viewDockerHosts;
    viewDockerHosts = new AzureViewDockerHostsDialog(project);
    viewDockerHosts.setDockerUIManager(dockerUIManager);
    viewDockerHosts.show();
//    dispose();
  }

  private void setSelectedSubscriptions() {
    try {
      System.out.println("Get selected subscriptions");
      dockerUIManager = null;

      java.util.List<String> selectedList = new ArrayList<String>();
      TableModel model = subscriptionTable.getModel();

      for (int i = 0; i < model.getRowCount(); i++) {
        Object selected = model.getValueAt(i, 0);
        if (selected instanceof Boolean && (Boolean) selected) {
          selectedList.add(model.getValueAt(i, 2).toString());
        }
      }

      if (selectADLoginRadioButton.isSelected()) {
        dockerUIManager = new AzureDockerUIManager();
        dockerUIManager.azureMainClient = AzureCredsManager.createSPDefaultAzureClient();
        List<Subscription> subscriptionList = AzureCredsManager.getSPSubscriptions();
        dockerUIManager.subscriptionList = new ArrayList<>();

        for (Subscription subscription : subscriptionList) {
          if (selectedList.contains(subscription.subscriptionId())) {
            AzureDockerSubscription dockerSubscription = createSubscriptionElement(subscription);
            dockerSubscription.azureClient = AzureCredsManager.getAzureSPClient(dockerSubscription.id);
            dockerUIManager.subscriptionList.add(dockerSubscription);
          }
        }

      } else if (selectSPLoginRadioButton.isSelected()) {
        dockerUIManager = new AzureDockerUIManager();
        dockerUIManager.azureMainClient = AzureCredsManager.createAuthLoginDefaultAzureClient();
        List<Subscription> subscriptionList = AzureCredsManager.getADSubscriptions();
        dockerUIManager.subscriptionList = new ArrayList<>();

        for (Subscription subscription : subscriptionList) {
          if (selectedList.contains(subscription.subscriptionId())) {
            AzureDockerSubscription dockerSubscription = createSubscriptionElement(subscription);
            dockerSubscription.azureClient = AzureCredsManager.getAzureADClient(dockerSubscription.id);
            dockerUIManager.subscriptionList.add(dockerSubscription);
          }
        }

      }
    } catch (Exception e) {
      dockerUIManager = null;
      String msg = "An error occurred while attempting to set the subscription list.\n" + e.getMessage();
      PluginUtil.displayErrorDialogAndLog("Error", msg, e);
    }
  }

  private AzureDockerSubscription createSubscriptionElement(Subscription subscription) {
    AzureDockerSubscription dockerSubscription = new AzureDockerSubscription();
    dockerSubscription.id = subscription.subscriptionId();
    dockerSubscription.name = subscription.displayName();
    dockerSubscription.isSelected = true;
    dockerSubscription.locations = new ArrayList<>();

    for (Location location : subscription.listLocations()) {
      dockerSubscription.locations.add(location.name());
    }

    return dockerSubscription;
  }

}
