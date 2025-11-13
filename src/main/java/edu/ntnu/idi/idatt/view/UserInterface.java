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


/**
 * Class that handles user interference by initializing controllers, displaying menus
 * and running the main interface loop.
 *
 * @author andskj
 * @version 1.0
 * @since v0.2.1
 */
public class UserInterface {

  private final ConsoleUtils consoleUtils;
  private AuthorController authorController;
  private DiaryController diaryController;
  private boolean running;


  public UserInterface() {
    consoleUtils = new ConsoleUtils();
    running = true;
  }


  public void init() {

    AuthorRegister authorRegister = new AuthorRegister();
    DiaryRegister diaryRegister = new DiaryRegister();

    authorController = new AuthorController(authorRegister);
    diaryController = new DiaryController(diaryRegister);

  }

  public void start() {

    while (running) {
      showMainMenu();


    }

  }

  private void showMainMenu() {
    consoleUtils.showColoredMessage("==== Main Menu ====", Color.GREEN);
    consoleUtils.showMessage("1. Browse the registered diary entries");
    consoleUtils.showMessage("2. Create a new diary entry");
    consoleUtils.showMessage("3. Find a specific diary entry");
    consoleUtils.showMessage("4. Browse and manage the registered authors");
    consoleUtils.showMessage("5. Exit");

    String userInput = consoleUtils.readUserInput();

    while (true)

      switch (userInput) {
        case "1" -> {
          browseAndManageRegisteredDiaries();
        }
        case "2" -> {
          createNewDiaryEntry();
        }
        case "3" -> {
          findSpecificDiaryEntry();
        }
        case "4" -> {
          browseAndManageRegisteredAuthors();
        }
        case "5" -> {
          exitProgram();
        }
        default -> {
          consoleUtils.showColoredMessage("Invalid choice, please try again.", Color.RED);
          userInput = consoleUtils.readUserInput();

        }


      }

  }

  private void findSpecificDiaryEntry() {
    consoleUtils.showMessage("Under construction");

    consoleUtils.showColoredMessage("=== Diary entry lookup ===", Color.WHITE);
    consoleUtils.showMessage("Option 1: Search for diary entry by word");
    consoleUtils.showMessage("Option 2: Search for diary entry by specific date");
    consoleUtils.showMessage("Option 3: Search for diary entry within two dates");
    consoleUtils.showMessage("Option 4: Exit diary entry lookup");


    String userInput = consoleUtils.readUserInput();

    while (true) {
      switch (userInput) {
        case "1" -> searchForDiaryEntryByWord();
        case "2" -> searchForDiaryEntryWithSpecificDate();
        case "3" -> searchForDiaryEntryWithinTwoDates();
        case "4" -> showMainMenu();
        default -> {
          consoleUtils.showColoredMessage("Invalid choice, please try again.", Color.RED);
          userInput = consoleUtils.readUserInput();
        }

      }
    }


  }

  private static void searchForDiaryEntryWithinTwoDates() {
  }

  private static void searchForDiaryEntryWithSpecificDate() {
  }

  private void searchForDiaryEntryByWord() {
    consoleUtils.showColoredMessage("=== Searching for diary by word===", Color.WHITE);
    consoleUtils.showMessage("Type Back to go back");

    while (true) {
        String searchWord = consoleUtils.readUserInputWithPrompt("Enter the search word: ");

        if ("back".equalsIgnoreCase(searchWord)) {
          return;
        }

        List<DiaryEntry> diaries = diaryController.getListOfDiaryEntriesWithSpecificWord(searchWord);

        if (diaries.isEmpty()) {
          consoleUtils.showColoredMessage("No entries with that word found", Color.RED);
        } else {
          consoleUtils.showColoredMessage("Found" + diaries.size() + " diaries with that word:", Color.CYAN);
          displayListOfDiaries(diaries);
          break;


      }
    }

  }

  private static void displayListOfDiaries(List<DiaryEntry> diaries) {
  }

  private void browseAndManageRegisteredDiaries() {
    DiaryEntry chosenDiaryEntry = browseRegisteredDiaries();
    if (chosenDiaryEntry != null) {
      String[] messages = {"You have chosen the diary: ", chosenDiaryEntry.toString()};
      Color[] colors = {Color.CYAN, Color.YELLOW};

      consoleUtils.showMessageWithMultipleColors(messages, colors);
      consoleUtils.showMessage("Option 1: Read this diary entry");
      consoleUtils.showMessage("Option 2: Edit this diary entry");
      consoleUtils.showMessage("Option 3: Return to main menu");


      String userInput = consoleUtils.readUserInput();

      while (true) {
        switch (userInput) {
          case "1" -> {
            consoleUtils.showMessage(chosenDiaryEntry.toString());
          }
          case "2" -> {
            editChosenDiaryEntry(chosenDiaryEntry);
          }
          case "3" -> {
            consoleUtils.showColoredMessage("Returning to main menu", Color.CYAN);
            return;
          }
          default -> {
            consoleUtils.showColoredMessage("Invalid choice, please try again.", Color.RED);
            userInput = consoleUtils.readUserInput();
          }

        }
      }

    } else {
      consoleUtils.showColoredMessage("Write a new diary entry or go back", Color.CYAN);
      consoleUtils.showMessage("Option 1: Register a new diary entry");
      consoleUtils.showMessage("Option 2: Go back");

      String userInput = consoleUtils.readUserInput();

      while (true) {
        switch (userInput) {
          case "1" -> {
            createNewDiaryEntry();
            browseAndManageRegisteredDiaries();
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

  private void editChosenDiaryEntry(DiaryEntry chosenDiaryEntry) {
    consoleUtils.showMessage("Under construction");
    showMainMenu();
  }

  private void browseAndManageRegisteredAuthors() {
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

  private void exitProgram() {
    consoleUtils.showColoredMessage("Exiting program...", Color.WHITE);
    consoleUtils.shutDownConsoleUtils();
    running = false;
  }

  private void createNewDiaryEntry() {
    consoleUtils.showColoredMessage("=== Crating new Diary Entry ===", Color.WHITE);
    LocalDateTime entryDate = getDateOfNewDiaryEntry();
    Author author = getAuthorOfNewDiaryEntry();
    consoleUtils.showMessage(author + " is now chosen");
    String entryTitle = consoleUtils.readUserInputWithPrompt("Enter the diary entry title: ");
    String entryText = consoleUtils.readUserInputWithPrompt("Enter the text of the diary entry");

    DiaryEntry diaryEntry = new DiaryEntry(entryDate, author, entryTitle, entryText);
    diaryController.addDiaryToRegister(diaryEntry);

    showMainMenu();

  }

  private DiaryEntry browseRegisteredDiaries() {
    List<DiaryEntry> diaryEntries = diaryController.getListOfAllRegisteredDiaryEntries();
    consoleUtils.showColoredMessage("=== Registered Diary Entries ===", Color.WHITE);


    if (diaryEntries.isEmpty()) {
      consoleUtils.showColoredMessage("No diaries registered", Color.WHITE);
      showMainMenu();
    }

    int indexOfFirstDiaryToDisplay = 0;
    int amountOfDiariesToDisplay = Math.min(diaryEntries.size(), 5);

    for (int i = indexOfFirstDiaryToDisplay; i < amountOfDiariesToDisplay; i++) {
      consoleUtils.showMessage(i + ": " + diaryEntries.get(i).toString());
    }

    String chosenDiaryInput;

    while (true) {

      chosenDiaryInput = consoleUtils.readUserInputWithPrompt("Enter author key or go 'back':");

      if ("back".equalsIgnoreCase(chosenDiaryInput)) {
        showMainMenu();
      }

      try {
        int indexOfDiaryByInput = Integer.parseInt(chosenDiaryInput);
        if (indexOfDiaryByInput >= amountOfDiariesToDisplay || indexOfDiaryByInput < 0) {
          consoleUtils.showColoredMessage("Not a possible choice of diary", Color.RED);
          continue;
        }

        return diaryEntries.get(indexOfDiaryByInput);

      } catch (NumberFormatException e) {
        consoleUtils.showColoredMessage(e.getMessage(), Color.RED);
      }


    }


  }

  private Author getAuthorOfNewDiaryEntry() {
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

    String chosenAuthorInput;

    while (true) {

      chosenAuthorInput = consoleUtils.readUserInputWithPrompt("Enter author key or go 'back':");

      if ("back".equalsIgnoreCase(chosenAuthorInput)) {
        showMainMenu();
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

  private Author createNewAuthor() {
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


  private LocalDateTime getDateOfNewDiaryEntry() {
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
