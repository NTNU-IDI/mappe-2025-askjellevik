package edu.ntnu.idi.idatt.view.menus;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.controller.validators.AuthorValidator;
import edu.ntnu.idi.idatt.model.Author;
import java.util.List;
import org.fusesource.jansi.Ansi.Color;

public class AuthorMenu {

  private final ConsoleUtils consoleUtils;
  private final AuthorController authorController;

  public AuthorMenu(AuthorController authorController, ConsoleUtils consoleUtils) {
    this.authorController = authorController;
    this.consoleUtils = consoleUtils;
  }


  public void browseAndManageRegisteredAuthors() {
    Author chosenAuthor = browseRegisteredAuthors();
    if (chosenAuthor != null) {
      String[] messages = {"You have chosen the author: ", chosenAuthor.toString()};
      Color[] colors = {Color.CYAN, Color.YELLOW};

      consoleUtils.showMessageWithMultipleColors(messages, colors);
      consoleUtils.showMessage("Option 1: Edit authors personalia");
      consoleUtils.showMessage("Option 2: Show authors diary entries");

    } else {
      consoleUtils.showColoredMessage("Register a new author or go back", Color.CYAN);
      consoleUtils.showMessage("Option 1: Register a new author");
      consoleUtils.showMessage("Option 2: Go back");

      String userInput = consoleUtils.readUserInput();

      while (true) {
        switch (userInput) {
          case "1" -> {
            //createNewAuthor();
            return;
          }
          case "2" -> {
            consoleUtils.showColoredMessage("Returning to main menu", Color.CYAN);
            return;
          }
          default -> {
            consoleUtils.showColoredMessage("Invalid choice, please try again.", Color.RED);
            userInput = consoleUtils.readUserInput();
          }

        }
      }


    }


  }


/*  public Author getAuthorOfNewDiaryEntry() {
    consoleUtils.showMessage("Choose the author of the diary entry:");
    consoleUtils.showMessage("Option 1: Register as a new author");
    consoleUtils.showMessage("Option 2: Choose an author that is already registered");

    String userInput = consoleUtils.readUserInput();

    while (true) {
      switch (userInput) {
        case "1" -> {
          return createNewAuthor();
        }
        case "2" -> {
          Author author = browseRegisteredAuthors();
          if (author == null) {
            consoleUtils.showColoredMessage("You have to register a new author", Color.CYAN);
            author = createNewAuthor();
          }
          return author;
        }
        default -> {
          consoleUtils.showColoredMessage("Invalid choice, please try again.", Color.RED);
          userInput = consoleUtils.readUserInput();
        }

      }
    }


  }*/


  /*private Author createNewAuthor() {
    consoleUtils.showColoredMessage("=== New Author ===", Color.BLUE);
    String authorName;
    String authorEmail;

    while (true) {

      authorName = consoleUtils.readUserInputWithPrompt("Enter the name of the author:");

      if (!AuthorValidator.validateName(authorName)) {
        consoleUtils.showColoredMessage("Invalid name, try again", Color.RED);
        continue;
      }

      authorEmail = consoleUtils.readUserInputWithPrompt("Enter the email of the author: ");

      if (!AuthorValidator.validateEmail(authorEmail)) {
        consoleUtils.showColoredMessage("Invalid email, try again", Color.RED);
        continue;
      }

      Author authorToAdd = new Author(authorName, authorEmail);
      authorController.addAuthorToRegister(authorToAdd);
      consoleUtils.showColoredMessage("Author created successfully", Color.WHITE);

      return authorToAdd;

    }
  }*/

  private Author browseRegisteredAuthors() {
    List<Author> authors = authorController.getListOfAuthorsInRegister();

    if (authors.isEmpty()) {
      consoleUtils.showColoredMessage("No authors registered", Color.WHITE);
      return null;
    }

    int indexOfFirstAuthorToDisplay = 0;
    int amountOfAuthorsToDisplay = Math.min(authors.size(), 5);

    for (int i = indexOfFirstAuthorToDisplay; i < amountOfAuthorsToDisplay; i++) {
      consoleUtils.showMessage(i + ": " + authors.get(i).toString());
    }


    while (true) {

      String chosenAuthorInput = consoleUtils.readUserInputWithPrompt("Enter author key or go 'back':");

      if ("back".equalsIgnoreCase(chosenAuthorInput)) {
        return null;
      }

      try {
        int indexOfAuthorByInput = Integer.parseInt(chosenAuthorInput);
        if (indexOfAuthorByInput >= amountOfAuthorsToDisplay || indexOfAuthorByInput < 0) {
          consoleUtils.showColoredMessage("Not a possible choice of author", Color.RED);
          continue;
        }

        return authors.get(indexOfAuthorByInput);

      } catch (NumberFormatException e) {
        consoleUtils.showColoredMessage(e.getMessage(), Color.RED);
      }


    }

  }


  public String subMenuForAuthorManagementAction(Author author) {
    String[] messages = {"You have chosen the author: \n",
            author.getName(), " |  email: ",
            author.getEmail(), " diary entries registered: ", String.valueOf(authorController.getAmountOfEntriesBySpecificAuthor(author))};
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

  public void displayAuthorsDiaryEntries(Author Author) {
  }

  public void displayAmountOFDiaryEntries(Author Author) {
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


