package edu.ntnu.idi.idatt.controller.utils;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.Scanner;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.AnsiConsole;

public class ConsoleUtils {

  private final Scanner scanner;


  public ConsoleUtils() {
    System.setProperty("jansi.passthrough", "true");
    AnsiConsole.systemInstall();
    this.scanner = new Scanner(System.in);
  }


  public void showColoredMessage(String message, Ansi.Color color) {
    /* Chat-GPT prompt:

    *   How do I make this method print out the message in the console with a given color?
    *
    * */
    System.out.println(Ansi.ansi().fg(color).a(message).reset());

  }

  public void showMessageWithMultipleColors(String[] strings, Color[] colors) {
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

  public void showMessage(String message) {
    System.out.println(message);
  }

  public String readUserInputWithPrompt(String prompt) {
    System.out.println(prompt);
    return scanner.nextLine().trim();
  }

  public String readUserInput() {
    return readUserInputWithPrompt("Enter input: ");
  }


  public void shutDownConsoleUtils() {
    AnsiConsole.systemUninstall();
    scanner.close();
  }

}
