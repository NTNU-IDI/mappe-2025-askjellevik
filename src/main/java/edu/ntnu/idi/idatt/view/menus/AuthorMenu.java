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
   *
   * <p>Options include browsing diary entries,
   * editing the author, creating a diary entry, or returning.
   *
   * @param author the author being managed
   * @param amountOfEntries the number of diary entries registered to the author
   * @return the user input as a String.
   */
  public String subMenuForAuthorManagementAction(Author author, int amountOfEntries) {
    consoleUtils.clearScreen();
    String[] messages = {"You have chosen the author: \n",
        author.getName(), " |  email: ",
        author.getEmail(), " diary entries registered: ", String.valueOf(amountOfEntries)};
    Color[] colors = {Color.WHITE, Color.CYAN, Color.WHITE, Color.CYAN, Color.WHITE, Color.CYAN};

    tryShowMessageWithMultipleColors(messages, colors);

    consoleUtils.showMessage("Option 1: Browse this authors diary entries");
    consoleUtils.showMessage("Option 2: Edit this author");
    consoleUtils.showMessage("Option 3: Create a diary entry for this author");
    consoleUtils.showMessage("Option Back: Return to main menu");

    return consoleUtils.readUserInput();


  }

  /**
   * Displays the submenu for editing an author's details and returns the user's choice.
   *
   * <p>Options include editing the first name, last name, email, or returning.
   *
   * @param author the author being edited
   * @return the user input as a String.
   */
  public String subMenuForEditingAuthorAction(Author author) {
    consoleUtils.showColoredMessage(author.getName(), Color.CYAN);
    consoleUtils.showMessage("Option 1: Edit the authors first name");
    consoleUtils.showMessage("Option 2: Edit the authors last name");
    consoleUtils.showMessage("Option 3: Edit the authors email");
    consoleUtils.showMessage("Option Back: Exit author editing");

    return consoleUtils.readUserInput();


  }

  /**
   * Displays a paginated list of authors for user selection.
   * Each author is shown with their key, name, and count of diary entries.
   * The user can navigate through pages or select an author by their key.
   *
   * @param authors          the list of authors to display.
   * @param start            the starting index for the current page.
   * @param end              the ending index for the current page.
   * @param page             the current page number.
   * @param authorIntegerMap a map containing authors and their corresponding diary entry counts.
   */
  public void displayAuthorsForSelection(List<Author> authors, int start, int end, int page,
      Map<Author, Integer> authorIntegerMap) {
    System.out.format("+---+---------Page " + page + "---------------+%n");
    System.out.format("|KEY| Name                 |Diaries|%n");
    System.out.format("+---+------------------------------+%n");

    String leftAlignFormat = "| %-1d | %-20s | %-6d| %n";
    for (int i = start; i < end; i++) {
      System.out.format(leftAlignFormat, i + 1, authors.get(i).getName(),
          authorIntegerMap.get(authors.get(i)));
    }
    System.out.format("+---+------------------------------+%n");
  }

  /**
   * Displays a submenu for the user to choose an author when creating a diary entry.
   * Options include registering a new author, choosing an existing author, or returning.
   *
   * @return the user input as a String.
   */
  public String subMenuForChoosingAuthor() {
    displaySecondaryHeader("Author selection");
    consoleUtils.showMessage("Option 1: Register a new author");
    consoleUtils.showMessage("Option 2: Choose an existing author");
    consoleUtils.showMessage("Option cancel: Cancel author selection");

    return consoleUtils.readUserInput();


  }

}


