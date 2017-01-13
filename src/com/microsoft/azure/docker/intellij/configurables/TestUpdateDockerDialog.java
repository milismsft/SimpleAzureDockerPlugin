package com.microsoft.azure.docker.intellij.configurables;

import com.intellij.ui.components.JBList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;

public class TestUpdateDockerDialog extends JDialog {
  private JPanel contentPane;
  private JButton buttonOK;
  private JButton buttonCancel;
  private JList selectionList;
  private JPanel mainPanel;
  private JTextArea asdaAsFaAfsTextArea;

  TestUpdateDockerPanel updateDockerPanel;
  TestUpdateDockerPanelSecond updateDockerPanelSecond;

  public TestUpdateDockerDialog() {
    setContentPane(contentPane);
    setModal(true);
    getRootPane().setDefaultButton(buttonOK);

    buttonOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onOK();
      }
    });

    buttonCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onCancel();
      }
    });

    // call onCancel() when cross is clicked
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        onCancel();
      }
    });

    // call onCancel() on ESCAPE
    contentPane.registerKeyboardAction(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        onCancel();
      }
    }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

  }

  private void onOK() {
    // add your code here
    dispose();
  }

  private void onCancel() {
    // add your code here if necessary
    dispose();
  }

  private void createUIComponents() {
    // TODO: place custom component creation code here
    updateDockerPanel = new TestUpdateDockerPanel();
    updateDockerPanelSecond = new TestUpdateDockerPanelSecond();
    editPanels = new DockerHostEditPanel[] {
        new DockerHostEditPanel("Menu Item 1", updateDockerPanel.getPanel()),
        new DockerHostEditPanel("Menu Item 2", updateDockerPanelSecond.getPanel()),
        new DockerHostEditPanel("Menu Item 3", updateDockerPanel.getPanel()),
    };

    mainPanel = new JPanel();
    mainPanel.add(editPanels[0].getPanel());
    DefaultListModel<DockerHostEditPanel> selectionModel = new DefaultListModel<>();
    for (DockerHostEditPanel item : editPanels) {
      selectionModel.addElement(item);
    }
    selectionList = new JList(selectionModel);
    selectionList.setFixedCellHeight(selectionList.getFont().getSize() + 10);
    selectionList.setOpaque(false);
    selectionList.setSelectedIndex(0);
    selectionList.getComponent(0).setBackground(selectionList.getSelectionBackground());
    selectionList.getComponent(0).setForeground(selectionList.getSelectionForeground());
    selectionList.getComponent(0).revalidate();
    selectionList.getComponent(0).repaint();
//    selectionList.setSelectedValue(selectionList.getSelectedValue(), true);
//    selectionList.requestFocus();
//    selectionList.setSelectedIndices(new int[]{0});
    selectionList.revalidate();
    selectionList.repaint();

    selectionList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        int idx = selectionList.getSelectedIndex();
        switch (idx) {
          case 0: {
            mainPanel.removeAll();
            mainPanel.add(updateDockerPanel.getPanel());
            mainPanel.setAlignmentX(0);
            mainPanel.setAlignmentY(0);
            mainPanel.revalidate();
            mainPanel.repaint();
            break;
          }
          case 1: {
            mainPanel.removeAll();
            mainPanel.add(updateDockerPanelSecond.getPanel());
            mainPanel.setAlignmentX(0);
            mainPanel.setAlignmentY(0);
            mainPanel.revalidate();
            mainPanel.repaint();
            break;
          }
        }

      }
    });
  }

  private DockerHostEditPanel[] editPanels;

  class DockerHostEditPanel {
    String itemName;
    JPanel mainPanel;

    public DockerHostEditPanel(String name, JPanel panel) {
      this.itemName = name;
      this.mainPanel = panel;
    }

    public String getName() {
      return itemName;
    }

    public JPanel getPanel() {
      return mainPanel;
    }

    public void setName(String name) {
      this.itemName = name;
    }

    public void setPanel(JPanel panel) {
      this.mainPanel = panel;
    }

    @Override
    public String toString() {
      return itemName;
    }
  }

}
