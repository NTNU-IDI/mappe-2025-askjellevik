package edu.ntnu.idi.idatt.view.menus;

import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import org.fusesource.jansi.Ansi.Color;

/**
 * Base class for menus containing basic functionalities for user interaction
 * and message display to be used by derived menu classes.
 */
public class BaseMenu {

  protected final ConsoleUtils consoleUtils;

  /**
   * Constructs the BaseMenu with the injected ConsoleUtils for user interaction.
   * ConsoleUtils is protected to allow derived classes to access its methods.
   */
  protected BaseMenu(ConsoleUtils consoleUtils) {
    this.consoleUtils = consoleUtils;
  }

  /**
   * Prompts the user with a message and return their input.
   * A blank line is displayed before the prompt for better readability.
   *
   * @param prompt the message to display to the user.
   * @return The user's input as a String.
   */
  public String getUserStringWithPrompt(String prompt) {
    consoleUtils.showMessage("");
    return consoleUtils.readUserInputWithPrompt(prompt);
  }

  /**
   * Displays a red colored error message to the user.
   *
   * @param message the error message to display.
   */
  public void displayErrorMessage(String message) {
    consoleUtils.showColoredMessage(message, Color.RED);
  }

  /**
   * Displays a blue colored success message to the user.
   *
   * @param message the success message to display.
   */
  public void displaySuccessMessage(String message) {
    consoleUtils.showColoredMessage(message, Color.BLUE);
  }

  /**
   * Displays a simple message to the user without any color formatting.
   *
   * @param message the message to display.
   */
  public void displaySimpleMessage(String message) {
    consoleUtils.showMessage(message);
  }

  /**
   * Displays a main header in cyan color to the user.
   * The header is formatted with "===" before and after the text.
   * A few blank lines are added before the header for better readability.
   *
   * @param header the header text to display.
   */
  public void displayMainHeader(String header) {
    consoleUtils.clearScreen();
    consoleUtils.showColoredMessage("=== " + header + " ===", Color.CYAN);
  }

  /**
   * Displays a header in cyan color to the user.
   * The header is formatted with "---" before and after the text.
   *
   * @param header the header text to display.
   */
  public void displaySecondaryHeader(String header) {
    consoleUtils.clearScreen();
    consoleUtils.showColoredMessage("--- " + header + " ---", Color.CYAN);
  }

  /**
   * Displays a header in magenta color to the user.
   * The header is formatted with "###" before and after the text.
   *
   * @param header the header text to display.
   */
  public void displayThirdHeader(String header) {
    consoleUtils.clearScreen();
    consoleUtils.showColoredMessage("### " + header + " ###", Color.MAGENTA);
  }

  /**
   * Tries to display a message desired to be shown with multiple colors.
   *
   * <p>It receives an array of messages and an array of corresponding colors.
   * If the lengths of the arrays do not match it catches an IllegalArgumentException
   * and an error message is displayed to the user.</p>
   *
   * @param messages the array of message parts to display.
   * @param colors the array of colors corresponding to each message part.
   */
  public void tryShowMessageWithMultipleColors(String[] messages, Color[] colors) {
    try {
      consoleUtils.showMessageWithMultipleColors(messages, colors);
    } catch (IllegalArgumentException e) {
      displayErrorMessage(e.getMessage());
    }
  }
}
