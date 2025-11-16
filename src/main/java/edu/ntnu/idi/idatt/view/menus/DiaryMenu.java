package edu.ntnu.idi.idatt.view.menus;

import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidDateException;
import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.controller.validators.DateValidator;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.fusesource.jansi.Ansi.Color;

public class DiaryMenu {

  private ConsoleUtils consoleUtils;
  private DiaryController diaryController;
  private AuthorMenu authorMenu;

  public DiaryMenu(DiaryRegister diaryRegister, ConsoleUtils consoleUtils, AuthorMenu authorMenu) {
    this.diaryController = new DiaryController(diaryRegister);
    this.consoleUtils = consoleUtils;
    this.authorMenu = authorMenu;
  }


  private void editChosenDiaryEntry(DiaryEntry chosenDiaryEntry) {
    consoleUtils.showMessage("Under construction");
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
        consoleUtils.showColoredMessage("Found" + diaries.size() + " diaries with that word:",
            Color.CYAN);
        displayListOfDiaries(diaries);
        break;


      }
    }

  }


  private DiaryEntry browseRegisteredDiaries() {
    List<DiaryEntry> diaryEntries = diaryController.getListOfAllRegisteredDiaryEntries();
    consoleUtils.showColoredMessage("=== Registered Diary Entries ===", Color.WHITE);

    if (diaryEntries.isEmpty()) {
      consoleUtils.showColoredMessage("No diaries registered", Color.WHITE);
      return null;
    }

    int indexOfFirstDiaryToDisplay = 0;
    int amountOfDiariesToDisplay = Math.min(diaryEntries.size(), 5);

    for (int i = indexOfFirstDiaryToDisplay; i < amountOfDiariesToDisplay; i++) {
      consoleUtils.showMessage(i + ": " + diaryEntries.get(i).toString());
    }


    while (true) {

      String chosenDiaryInput = consoleUtils.readUserInputWithPrompt("Enter author key or go 'back':");

      if ("back".equalsIgnoreCase(chosenDiaryInput)) {
        return null;
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


  public void createNewDiaryEntry() {
    consoleUtils.showColoredMessage("=== Creating new Diary Entry ===", Color.WHITE);
    LocalDateTime entryDate = getDateOfNewDiaryEntry();
    Author author = authorMenu.getAuthorOfNewDiaryEntry();
    consoleUtils.showMessage(author + " is now chosen");
    String entryTitle = consoleUtils.readUserInputWithPrompt("Enter the diary entry title: ");
    String entryText = consoleUtils.readUserInputWithPrompt("Enter the text of the diary entry");

    DiaryEntry diaryEntry = new DiaryEntry(entryDate, author, entryTitle, entryText);
    diaryController.addDiaryToRegister(diaryEntry);

  }

  public void findSpecificDiaryEntry() {
    consoleUtils.showColoredMessage("=== Diary entry lookup ===", Color.WHITE);
    consoleUtils.showMessage("Option 1: Search for diary entry by word");
    consoleUtils.showMessage("Option 2: Search for diary entry by specific date");
    consoleUtils.showMessage("Option 3: Search for diary entry within two dates");
    consoleUtils.showMessage("Option 4: Exit diary entry lookup");

    String userInput = consoleUtils.readUserInput();

      switch (userInput) {
        case "1" -> searchForDiaryEntryByWord();
        case "2" -> searchForDiaryEntryWithSpecificDate();
        case "3" -> searchForDiaryEntryWithinTwoDates();
        case "4" -> {
          return;
        }
        default -> {
          consoleUtils.showColoredMessage("Invalid choice, please try again.", Color.RED);
        }

      }



  }


  public void browseAndManageRegisteredDiaries() {
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
          case "1" -> consoleUtils.showMessage(chosenDiaryEntry.toString());
          case "2" -> editChosenDiaryEntry(chosenDiaryEntry);
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

  private static void displayListOfDiaries(List<DiaryEntry> diaries) {
  }


}
