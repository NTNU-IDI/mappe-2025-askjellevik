package edu.ntnu.idi.idatt.view.menus;

import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.model.Author;
import java.util.List;
import java.util.Map;
import org.fusesource.jansi.Ansi.Color;


/**
 * Menu class for displaying author-related submenus and prompts to the user.
 * Extends the BaseMenu to utilize common menu functionalities.
 *
 * @author andskj
 */
public class AuthorMenu extends BaseMenu {


  /**
   * Constructs the AuthorMenu with the injected ConsoleUtils for user interaction.
   */
  public AuthorMenu(ConsoleUtils consoleUtils) {
    super(consoleUtils);
  }

  /**
   * Displays the submenu for author management actions and returns the user's choice.
   * Options include browsing diary entries, editing the author, creating a diary entry, or returning.
   *
   * @param author the author being managed
   * @param amountOfEntries the number of diary entries registered to the author
   * @return the user input as a String.
   */
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

      if ("1".equals(userInput) || "2".equals(userInput) || "3".equals(userInput) || "back".equals(
          userInput)) {
        return userInput;
      }


    }


  }

  /**
   * Displays the submenu for editing an author's details and returns the user's choice.
   * Options include editing the first name, last name, email, or returning.
   *
   * @param author the author being edited
   * @return the user input as a String.
   */
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

  /**
   * Displays a paginated list of authors for user selection.
   * Each author is shown with their key, name, and count of diary entries.
   * The user can navigate through pages or select an author by their key.
   *
   * @param authors the list of authors to display.
   * @param start the starting index for the current page.
   * @param end the ending index for the current page.
   * @param page the current page number.
   * @param authorIntegerMap a map containing authors and their corresponding diary entry counts.
   * @return the user input as a String.
   */
  public void displayAuthorsForSelection(List<Author> authors, int start, int end, int page,
      Map<Author, Integer> authorIntegerMap) {
    consoleUtils.showColoredMessage("=== Registered Authors ===", Color.WHITE);


    System.out.format("+---+-------Page " + page + "--------------+%n");
    System.out.format("|KEY| Name                 |Count|%n");
    System.out.format("+---+----------------------------+%n");

    String leftAlignFormat = "| %-1d | %-20s | %-4d| %n";
    for (int i = start; i < end; i++) {
      System.out.format(leftAlignFormat, i, authors.get(i).getName(),
          authorIntegerMap.get(authors.get(i)));
    }
    System.out.format("+---+---------------------------+%n");
/*    return consoleUtils.readUserInputWithPrompt(
        "Enter '(Author ID)', 'Next' or 'Previous'  | 'Back' for back");*/
  }

  /**
   * Displays a submenu for the user to choose an author when creating a diary entry.
   * Options include registering a new author, choosing an existing author, or returning.
   *
   * @return the user input as a String.
   * */
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


