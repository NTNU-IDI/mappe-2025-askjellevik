package edu.ntnu.idi.idatt.view.menus;

import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.model.Author;
import java.util.List;
import org.fusesource.jansi.Ansi.Color;

public class AuthorMenu {

  private final ConsoleUtils consoleUtils;

  public AuthorMenu(ConsoleUtils consoleUtils) {
    this.consoleUtils = consoleUtils;
  }



  public String subMenuForAuthorManagementAction(Author author, int amountOfEntries) {
    String[] messages = {"You have chosen the author: \n",
            author.getName(), " |  email: ",
            author.getEmail(), " diary entries registered: ", String.valueOf(amountOfEntries)};
    Color[] colors = {Color.WHITE, Color.CYAN, Color.WHITE, Color.CYAN, Color.WHITE, Color.CYAN};

    consoleUtils.showMessageWithMultipleColors(messages, colors);

    while (true) {
      consoleUtils.showMessage("Option 1: Browse this authors diary entries");
      consoleUtils.showMessage("Option 2: Edit this author");
      consoleUtils.showMessage("Option Back: Return to main menu");

      String userInput = consoleUtils.readUserInput();

      if ("1".equals(userInput) || "2".equals(userInput) || "back".equals(userInput)) {
        return userInput;
      }


    }


  }


  public String editAuthorEmailPrompt(Author author) {
    consoleUtils.showColoredMessage("=== Change author email ===", Color.WHITE);
    consoleUtils.showMessage("Original email: "+ author.getEmail());
      return consoleUtils.readUserInputWithPrompt("Enter new email: ");
  }

  public String subMenuForEditingAuthorAction(Author author) {
    while (true) {
      consoleUtils.showColoredMessage("=== Author editing ===", Color.WHITE);
      consoleUtils.showMessage("Option 1: Edit the authors name");
      consoleUtils.showMessage("Option 2: Edit the authors email");
      consoleUtils.showMessage("Option Back: Exit author editing");

      String userInput = consoleUtils.readUserInput();

      if ("1".equals(userInput) || "2".equals(userInput) || "back".equals(
          userInput)) {
        return userInput;
      }

    }
  }


  public String displayEntriesForSelection(List<Author> authors, int start,int end, int page) {
    consoleUtils.showColoredMessage("=== Registered Authors ===", Color.WHITE);


    String leftAlignFormat = "| %-1d | %-20s |%n";

    System.out.format("+---+-------Page " + page + "---------+%n");
    System.out.format("|KEY| Name                 |%n");
    System.out.format("+---+----------------------+%n");
    for (int i = start; i < end; i++) {
      System.out.format(leftAlignFormat, i, authors.get(i).getName());
    }
    System.out.format("+---+----------------------+%n");
    return consoleUtils.readUserInputWithPrompt(
            "Enter '(Author ID)', 'Next' or 'Previous'  | 'Back' for back");
  }

  public void displayPromptedMessage(String s) {
    consoleUtils.showColoredMessage(s, Color.RED);
  }

  public String editAuthorNamePrompt(Author author) {
    consoleUtils.showColoredMessage("=== Change author name ===", Color.WHITE);
    consoleUtils.showMessage("Original name: "+ author.getName());
    return consoleUtils.readUserInputWithPrompt("Enter new name: ");
  }

  public String getAuthorNamePrompt() {
      return consoleUtils.readUserInputWithPrompt("Enter the name of the author");
  }

  public void displayErrorMessage(String message) {
    consoleUtils.showColoredMessage(message, Color.RED);

  }

  public String getAuthorEmailPrompt() {
    return consoleUtils.readUserInputWithPrompt("Enter the email of the author");
  }

  public void displayHeader(String header) {
    consoleUtils.showColoredMessage(header, Color.WHITE);
  }

  public String subMenuForChoosingAuthor() {
    while (true) {
      consoleUtils.showColoredMessage("=== Choose author ===", Color.WHITE);
      consoleUtils.showMessage("Option 1: Register a new author");
      consoleUtils.showMessage("Option 2: Choose an existing author");
      consoleUtils.showMessage("Option Back: Exit");

      String userInput = consoleUtils.readUserInput();

      if ("1".equals(userInput) || "2".equals(userInput) || "back".equals(
          userInput)) {
        return userInput;
      }

    }
  }
}


