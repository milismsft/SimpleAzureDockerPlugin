package com.microsoft.intellij.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogEarthquakeShaker;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.wm.IdeFocusManager;

import javax.swing.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class PluginUtil {
  private static Logger logger = LogManager.getLogManager().getLogger("global");

  public static void displayErrorDialogAndLog(String title, String message, Exception e) {
    Logger mainLogger = LogManager.getLogManager().getLogger("");
    if (mainLogger != null) {
      mainLogger.setLevel(Level.ALL);
      logger.log(Level.SEVERE, message, e);
      mainLogger.setLevel(Level.OFF);
    }
    displayErrorDialog(title, message);
  }

  public static void displayInfoDialogAndLog(String title, String message) {
    Logger mainLogger = LogManager.getLogManager().getLogger("");
    if (mainLogger != null) {
      mainLogger.setLevel(Level.ALL);
      logger.log(Level.INFO, message);
      mainLogger.setLevel(Level.OFF);
    }
    displayInfoDialog(title, message);
  }

  /**
   * This method will display the error message box when any error occurs.It takes two parameters
   *
   * @param title   the text or title of the window.
   * @param message the message which is to be displayed
   */
  public static void displayErrorDialog(String title, String message) {
    JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
  }

  /**
   * This method will display the error message box when any error occurs.It takes two parameters
   *
   * @param title   the text or title of the window.
   * @param message the message which is to be displayed
   */
  public static void displayInfoDialog(String title, String message) {
    JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  public static void resetLoggers() {
    LogManager log = LogManager.getLogManager();

    Enumeration<String> enum_ = LogManager.getLogManager().getLoggerNames();
    while (enum_.hasMoreElements()) {
      String name = enum_.nextElement();
      if (!name.equals("global") /*&& !name.equals("") */) {
        Logger logger = LogManager.getLogManager().getLogger(name);
        logger.setLevel(Level.OFF);
      }
    }
  }

  public static void DialogShaker(ValidationInfo info, DialogWrapper dialogWrapper) {
    if(info.component != null && info.component.isVisible()) {
      IdeFocusManager.getInstance((Project)null).requestFocus(info.component, true);
    }

    DialogEarthquakeShaker.shake(dialogWrapper.getPeer().getWindow());
  }

}
