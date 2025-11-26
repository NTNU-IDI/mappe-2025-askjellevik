package edu.ntnu.idi.idatt.view.menus;

import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.model.Author;
import java.util.List;
import java.util.Map;
import org.fusesource.jansi.Ansi.Color;

public class AuthorMenu extends BaseMenu{


  public AuthorMenu(ConsoleUtils consoleUtils) {
    super(consoleUtils);
  }

  public String subMenuForAuthorManagementAction(Author author, int amountOfEntries) {
    String[] messages = {"You have chosen the author: \n",
            author.getName(), " |  email: ",
            author.getEmail(), " diary entries registered: ", String.valueOf(amountOfEntries)};
    Color[] colors = {Color.WHITE, Color.CYAN, Color.WHITE, Color.CYAN, Color.WHITE, Color.CYAN};

    tryShowMessageWithMultipleColors(messages, colors);

    while (true) {
      consoleUtils.showMessage("Option 1: Browse this authors diary entries");
      consoleUtils.showMessage("Option 2: Edit this author");
      consoleUtils.showMessage("Option 3: Create a diary entry for this author");
      consoleUtils.showMessage("Option Back: Return to main menu");

      String userInput = consoleUtils.readUserInput();

      if ("1".equals(userInput) || "2".equals(userInput) || "3".equals(userInput) || "back".equals(userInput)) {
        return userInput;
      }


    }


  }

  public String subMenuForEditingAuthorAction(Author author) {
    while (true) {
      consoleUtils.showColoredMessage("=== Author editing ===", Color.WHITE);
      consoleUtils.showMessage("Option 1: Edit the authors first name");
      consoleUtils.showMessage("Option 2: Edit the authors last name");
      consoleUtils.showMessage("Option 3: Edit the authors email");
      consoleUtils.showMessage("Option Back: Exit author editing");

      String userInput = consoleUtils.readUserInput();

      if ("1".equals(userInput) || "2".equals(userInput) || "3".equals(userInput) || "back".equals(
          userInput)) {
        return userInput;
      }

    }
  }

  public String displayAuthorsForSelection(List<Author> authors, int start,int end, int page, Map<Author, Integer> authorIntegerMap) {
    consoleUtils.showColoredMessage("=== Registered Authors ===", Color.WHITE);


    String leftAlignFormat = "| %-1d | %-20s | %-4d| %n";

    System.out.format("+---+-------Page " + page + "--------------+%n");
    System.out.format("|KEY| Name                 |Count|%n");
    System.out.format("+---+----------------------------+%n");
    for (int i = start; i < end; i++) {
      System.out.format(leftAlignFormat, i, authors.get(i).getName(), authorIntegerMap.get(authors.get(i)));
    }
    System.out.format("+---+---------------------------+%n");
    return consoleUtils.readUserInputWithPrompt(
            "Enter '(Author ID)', 'Next' or 'Previous'  | 'Back' for back");
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


