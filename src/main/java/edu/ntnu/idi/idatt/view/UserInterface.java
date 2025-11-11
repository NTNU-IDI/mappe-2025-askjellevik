package edu.ntnu.idi.idatt.view;


import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidDateException;
import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.controller.validators.AuthorValidator;
import edu.ntnu.idi.idatt.controller.validators.DateValidator;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.AuthorRegister;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.fusesource.jansi.Ansi.Color;

public class UserInterface {

  private static final ConsoleUtils consoleUtils = new ConsoleUtils();
  private static AuthorController authorController;
  private static DiaryController diaryController;
  private static boolean running;
  private static boolean showMainMenu;


  public UserInterface() {
    running = true;
    showMainMenu = false;
  }


  public static void init() {

    AuthorRegister authorRegister = new AuthorRegister();
    DiaryRegister diaryRegister = new DiaryRegister();

    authorController = new AuthorController(authorRegister);
    diaryController = new DiaryController(diaryRegister);

  }

  public static void start() {
    showMainMenu = true;

    while (running) {

      if (showMainMenu) {
        consoleUtils.showColoredMessage("==== Main Menu ====", Color.GREEN);
        consoleUtils.showMessage("1. Browse the registered diary entries");
        consoleUtils.showMessage("2. Create a new diary entry");
        consoleUtils.showMessage("3. Find a specific diary entry");
        consoleUtils.showMessage("4. Browse and manage the registered authors");
        consoleUtils.showMessage("5. Exit");

        String userInput = consoleUtils.readUserInput();

        boolean validInputFromUser = false;

        while (!validInputFromUser) {
          switch (userInput) {
            case "1" -> {
              browseAndManageRegisteredDiaries();
              validInputFromUser = true;
            }
            case "2" -> {
              createNewDiaryEntry();
              validInputFromUser = true;
            }
            case "3" -> {
              findSpecificDiaryEntry();
              validInputFromUser = true;
            }
            case "4" -> {
              browseAndManageRegisteredAuthors();
              validInputFromUser = true;
            }
            case "5" -> {
              exitProgram();
              validInputFromUser = true;
            }
            default -> {
              consoleUtils.showColoredMessage("Invalid choice, please try again.", Color.RED);
              userInput = consoleUtils.readUserInput();

            }


          }

        }


      }


    }

  }

  private static void findSpecificDiaryEntry() {
    consoleUtils.showMessage("Under construction");
  }

  private static void browseAndManageRegisteredDiaries() {
    consoleUtils.showMessage("Under construction");
  }

  private static void browseAndManageRegisteredAuthors() {
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
            createNewAuthor();
            browseAndManageRegisteredAuthors();
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

  private static void exitProgram() {
    consoleUtils.showColoredMessage("Exiting program...", Color.WHITE);
    consoleUtils.shutDownConsoleUtils();
    running = false;
  }

  private static void createNewDiaryEntry() {
    showMainMenu = false;
    consoleUtils.showColoredMessage("=== Crating new Diary Entry ===", Color.WHITE);
    LocalDateTime entryDate = getDateOfNewDiaryEntry();
    Author author = getAuthorOfNewDiaryEntry();
    consoleUtils.showMessage(author + " is now chosen");
    String entryTitle = consoleUtils.readUserInputWithPrompt("Enter the diary entry title: ");
    String entryText = consoleUtils.readUserInputWithPrompt("Enter the text of the diary entry");

    DiaryEntry diaryEntry = new DiaryEntry(entryDate, author, entryTitle, entryText);
    consoleUtils.showMessage(diaryEntry.toString());
    showMainMenu = true;

  }

  private static void browseRegisteredDiaries() {
    showMainMenu = false;

    consoleUtils.showColoredMessage("=== Registered Authors ===", Color.WHITE);






  }

  private static Author getAuthorOfNewDiaryEntry() {
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


  }

  private static Author browseRegisteredAuthors() {
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

    String chosenAuthorInput;

    while (true) {

      chosenAuthorInput = consoleUtils.readUserInputWithPrompt("Enter author key or go 'back':");

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

  private static Author createNewAuthor() {
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


  }


  private static LocalDateTime getDateOfNewDiaryEntry() {
    consoleUtils.showMessage("Choose date for the diary entry:");
    consoleUtils.showMessage("Option 1: Automatically choose the current time");

    String[] messages = {"Option 2: Enter date manually as ", "dd-MM-yyyy HH:mm"};
    Color[] colors = {Color.DEFAULT, Color.YELLOW};
    consoleUtils.showMessageWithMultipleColors(messages, colors);

    String userInput = consoleUtils.readUserInputWithPrompt("Enter the date manually or enter '2'");

    LocalDateTime entryDate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    while (true) {
      if ("1".equals(userInput)) {
        entryDate = LocalDateTime.now();
        consoleUtils.showMessage("Using the current date and time: " + entryDate.format(formatter));
        return entryDate;

      } else {
        try {
          entryDate = LocalDateTime.parse(userInput, formatter);
          if (!DateValidator.validateDate(entryDate)) {
            throw new InvalidDateException("The date is not valid");
          }
          consoleUtils.showMessage("The date has been registered: " + entryDate);
          return entryDate;

        } catch (InvalidDateException | DateTimeParseException e) {
          consoleUtils.showColoredMessage(e.getMessage(), Color.RED);
          userInput = consoleUtils.readUserInputWithPrompt("Enter the date manually or enter '2'");
        }

      }
    }

  }


}
