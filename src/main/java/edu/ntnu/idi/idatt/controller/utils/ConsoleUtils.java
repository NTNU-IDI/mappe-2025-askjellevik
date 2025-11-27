package edu.ntnu.idi.idatt.controller.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;

/**
 * ConsoleUtils is a utility class responsible for input and output operations in the console.
 * It provides methods for displaying colored messages, reading user input, and formatting dates.
 *
 * @author andskj
 */
public class ConsoleUtils {

  private final Scanner scanner;
  private static final DateTimeFormatter DEFAULT_FORMATTER =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

/**
 * Initiate the ConsoleUtils with Ansi for colored console output.
 */
  public ConsoleUtils() {
    System.setProperty("jansi.passthrough", "true");
    AnsiConsole.systemInstall();
    this.scanner = new Scanner(System.in);
  }


  /**
   * Method to show a colored message in the console.
   *
   * @param message The message to be displayed.
   * @param color   The color of the message.
   */
  public void showColoredMessage(String message, Ansi.Color color) {
    /* Chat-GPT prompt:

     *   How do I make this method print out the message in the console with a given color?
     *
     * */
    System.out.println(Ansi.ansi().fg(color).a(message).reset());

  }

  /**
   * Method to show a message with multiple colors in the console.
   * The strings and colors are provided as arrays, where each string corresponds to a color at the same index.
   * Therefore, the lengths of both arrays must match. If not, an IllegalArgumentException is thrown.
   *
   * @param strings The array of strings to be displayed.
   * @param colors  The array of colors corresponding to each string.
   * @throws IllegalArgumentException if the lengths of strings and colors do not match.
   * */
  public void showMessageWithMultipleColors(String[] strings, Color[] colors)
      throws IllegalArgumentException {
    /* Chat-GPT prompt:
     *
     *   How do I make the method print out a one-line message with multiple colors?*/
    if (strings.length != colors.length) {
      throw new IllegalArgumentException("The lists must be the same size");
    }

    Ansi ansi = Ansi.ansi();
    for (int i = 0; i < strings.length; i++) {
      ansi.fg(colors[i]).a(strings[i]).reset();
    }

    System.out.println(ansi);
  }

  /**
   * Method to show a simple message in the console.
   *
   * @param message The message to be displayed.
   */
  public void showMessage(String message) {
    System.out.println(message);
  }

  /**
   * Method to read user input from the console with a prompt.
   * The input is trimmed to remove potential whitespace before and after the input.
   *
   * @param prompt The prompt message to be displayed before reading input.
   * @return The user input as a trimmed string.
   */
  public String readUserInputWithPrompt(String prompt) {
    System.out.println(prompt);
    return scanner.nextLine().trim();
  }

  /**
   * Method to read user input from the console with a basic prompt.
   * The prompt is 'Enter input: '.
   * The input is trimmed to remove potential whitespace before and after the input.
   *
   * @return The user input as a trimmed string.
   */
  public String readUserInput() {
    return readUserInputWithPrompt("Enter input: ");
  }


  /**
   * Method to properly shut down console utilities.
   * The AnsiConsole is uninstalled and the scanner is closed to avoid resource leaks.
   */
  public void shutDownConsoleUtils() {
    AnsiConsole.systemUninstall();
    scanner.close();
  }

/**
 * Method to format a LocalDateTime object to a string for prettier display.
 *
 * @param date The LocalDateTime object to be formatted.
 * @return The formatted date string in the pattern "dd/MM/yyyy HH:mm". If the date is null, returns an empty string.
 */
  public String formatDateToView(LocalDateTime date) {
    if (date == null) {
      return "";
    }
    return date.format(DEFAULT_FORMATTER);
  }

  /**
   * Method to print a separator line in the console with a specified color.
   *
   * @param color The color of the separator line.
   */
  public void printLine(Color color) {
    System.out.println(Ansi.ansi().fg(color).a("--------------------").reset());

  }

  public void clearScreen() {
    for (int i = 0; i < 25; i++) {
      System.out.println();
    }
  }
}
