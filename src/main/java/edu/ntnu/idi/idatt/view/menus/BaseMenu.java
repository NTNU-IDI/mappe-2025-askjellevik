package edu.ntnu.idi.idatt.view.menus;

import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import org.fusesource.jansi.Ansi.Color;

public class BaseMenu {

  protected final ConsoleUtils consoleUtils;

  protected BaseMenu(ConsoleUtils consoleUtils) {
    this.consoleUtils = consoleUtils;
  }

  public String getUserStringWithPrompt(String prompt) {
    return consoleUtils.readUserInputWithPrompt(prompt);
  }

  public void displayErrorMessage(String message) {
    consoleUtils.showColoredMessage(message, Color.RED);
  }

  public void displaySuccessMessage(String message) {
    consoleUtils.showColoredMessage(message, Color.BLUE);
  }

  public void displaySimpleMessage(String message) {
    consoleUtils.showMessage(message);
  }

  public void displayMainHeader(String header) {
    consoleUtils.showColoredMessage("=== " + header + " ===", Color.WHITE);
  }
  public void displaySecondaryHeader (String header) {
    consoleUtils.showColoredMessage("--- " + header + " ---", Color.WHITE);
  }
  public void displayThirdHeader (String header) {
    consoleUtils.showColoredMessage("### " + header + " ###", Color.MAGENTA);
  }
}
