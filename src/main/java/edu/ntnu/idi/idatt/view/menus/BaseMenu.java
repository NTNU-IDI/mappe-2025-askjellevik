package edu.ntnu.idi.idatt.view.menus;

import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import org.fusesource.jansi.Ansi.Color;

public class BaseMenu {

  private final ConsoleUtils consoleUtils;

  public BaseMenu(ConsoleUtils consoleUtils) {
    this.consoleUtils = consoleUtils;
  }

  public String getUserStringWithPrompt(String prompt) {
    return consoleUtils.readUserInputWithPrompt(prompt);
  }

  public void displayRedMessage(String message) {
    consoleUtils.showColoredMessage(message, Color.RED);
  }

  public void displayWhiteHeader(String header) {
    consoleUtils.showColoredMessage("=== " + header + " ===", Color.WHITE);
  }
}
