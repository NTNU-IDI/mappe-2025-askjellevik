package edu.ntnu.idi.idatt.view.menus;

import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidDateException;
import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.controller.validators.DateValidator;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.fusesource.jansi.Ansi.Color;

public class DiaryMenu {

  private final ConsoleUtils consoleUtils;
  private final DiaryController diaryController;
  private final AuthorMenu authorMenu;

  public DiaryMenu(DiaryRegister diaryRegister, ConsoleUtils consoleUtils, AuthorMenu authorMenu) {
    this.diaryController = new DiaryController(diaryRegister);
    this.consoleUtils = consoleUtils;
    this.authorMenu = authorMenu;
  }


  public void searchForDiaryEntryWithinTwoDates() {
  }

  public void searchForDiaryEntryWithSpecificDate() {
    consoleUtils.showColoredMessage("=== Searching for diary by date===", Color.WHITE);
    consoleUtils.showMessage("Type Back to go back");

    while (true) {
      String userInput = consoleUtils.readUserInputWithPrompt("Enter the search date: dd-MM-YYYY");
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      LocalDate entryDate;
      LocalDateTime entryDateTime = null;

      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }

      try {
        entryDate = LocalDate.parse(userInput, formatter);
        entryDateTime = entryDate.atStartOfDay();
        if (!DateValidator.validateDate(entryDateTime)) {
          throw new InvalidDateException("The date is not valid");
        }

      } catch (InvalidDateException | DateTimeParseException e) {
        consoleUtils.showColoredMessage(e.getMessage(), Color.RED);
      }

      List<DiaryEntry> diaries = diaryController.getListOfDiaryEntriesByDate(entryDateTime);

      if (diaries.isEmpty()) {
        consoleUtils.showColoredMessage("No entries from that date found", Color.RED);
      } else {
        consoleUtils.showColoredMessage("Found " + diaries.size() + " diaries from that date:",
            Color.CYAN);
        displayListOfDiaries(diaries);
        break;


      }
    }
  }

  public void searchForDiaryEntryByWord() {
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
        consoleUtils.showColoredMessage("Found " + diaries.size() + " diaries with that word:",
            Color.CYAN);
        displayListOfDiaries(diaries);
        break;


      }
    }

  }


  public DiaryEntry chooseRegisteredDiaries() {
    List<DiaryEntry> diaryEntries = diaryController.getListOfAllRegisteredDiaryEntries();
    consoleUtils.showColoredMessage("=== Registered Diary Entries ===", Color.WHITE);

    if (diaryEntries.isEmpty()) {
      consoleUtils.showColoredMessage("No diaries registered", Color.WHITE);
      return null;
    }

    int page = 0;
    int pageSize = 5;

    while (true) {

      int start = page * pageSize;
      int end = Math.min(diaryEntries.size(), start + pageSize);

      consoleUtils.showMessage("KEY|    TITLE   |   DATE");

      for (int i = start; i < end; i++) {
        consoleUtils.showMessage(
            i + page + "  |" + diaryEntries.get(i).getEntryTitle() + "    |"
                + consoleUtils.formatDateToView(diaryEntries.get(i)
                .getEntryDate()));
      }

      String userInput = consoleUtils.readUserInputWithPrompt(
          "Enter '(Diary key)', 'Next' or 'Previous'  | 'Back' for back");

      if ("back".equalsIgnoreCase(userInput)) {
        return null;
      }

      if (end < diaryEntries.size()) {
        if ("next".equalsIgnoreCase(userInput)) {
          page += 1;
          continue;
        }
      }
      if (page > 0) {
        if ("previous".equalsIgnoreCase(userInput)) {
          page -= 1;
          continue;
        }
      }

      try {
        int indexOfDiaryByInput = Integer.parseInt(userInput);
        if (indexOfDiaryByInput >= end || indexOfDiaryByInput < 0) {
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
    if (entryDate == null) {
      return;
    }

    Author author = authorMenu.getAuthorOfNewDiaryEntry();
    if (author == null) {
      return;
    }
    consoleUtils.showMessage(author + " is now chosen");
    String entryTitle = consoleUtils.readUserInputWithPrompt("Enter the diary entry title: ");
    String entryText = consoleUtils.readUserInputWithPrompt("Enter the text of the diary entry");

    DiaryEntry diaryEntry = new DiaryEntry(entryDate, author, entryTitle, entryText);
    diaryController.addDiaryToRegister(diaryEntry);

  }

  public String subMenuForDiaryManagementAction(DiaryEntry diaryEntry) {
    String[] messages = {"You have chosen the diary: \n",
        diaryEntry.getEntryTitle() , " written by "
            , diaryEntry.getEntryAuthor().getName() , " registered ",
        consoleUtils.formatDateToView(diaryEntry.getEntryDate())};
    Color[] colors = {Color.WHITE, Color.CYAN, Color.WHITE, Color.CYAN, Color.WHITE,
        Color.CYAN};

    consoleUtils.showMessageWithMultipleColors(messages, colors);

    while (true) {
      consoleUtils.showMessage("Option 1: Read this diary entry");
      consoleUtils.showMessage("Option 2: Edit this diary entry");
      consoleUtils.showMessage("Option 3: Delete this diary entry");
      consoleUtils.showMessage("Option Back: Return to main menu");

      String userInput = consoleUtils.readUserInput();

      if ("1".equals(userInput) || "2".equals(userInput) || "3".equals(userInput) || "back".equals(
          userInput)) {
        return userInput;
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


      }
      if ("back".equalsIgnoreCase(userInput)) {
        return null;
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


  public void readDiaryEntry(DiaryEntry diaryEntry) {
  }

  public void editDiaryEntry(DiaryEntry diaryEntry) {
  }

  public void deleteDiaryEntry(DiaryEntry diaryEntry) {
    consoleUtils.showColoredMessage("? DELETE DIARY ENTRY ?", Color.RED);
    consoleUtils.showMessage("Are you sure you want to delete this diary from the register?");
    String[] messages = {
        diaryEntry.getEntryTitle() + " by " + diaryEntry.getEntryAuthor().getName() + " from "
            + consoleUtils.formatDateToView(diaryEntry.getEntryDate())};
    Color[] colors = {Color.WHITE, Color.DEFAULT, Color.WHITE, Color.DEFAULT, Color.WHITE};

    consoleUtils.showMessageWithMultipleColors(messages, colors);
    String userInput = consoleUtils.readUserInputWithPrompt("Yes/No");
    while (true) {
      if ("yes".equalsIgnoreCase(userInput)) {
        diaryController.deleteDiaryEntryInRegister(diaryEntry);
        return;
      }
      if ("no".equalsIgnoreCase(userInput)) {
        return;
      }
      consoleUtils.showColoredMessage("Not a valid input, Yes/No", Color.RED);
      userInput = consoleUtils.readUserInput();
    }
  }

  public String subMenuForFindingDiaryAction() {

    while (true) {
      consoleUtils.showColoredMessage("=== Diary entry lookup ===", Color.WHITE);
      consoleUtils.showMessage("Option 1: Search for diary entry by word");
      consoleUtils.showMessage("Option 2: Search for diary entry by specific date");
      consoleUtils.showMessage("Option 3: Search for diary entry within two dates");
      consoleUtils.showMessage("Option Back: Exit diary entry lookup");

      String userInput = consoleUtils.readUserInput();

      if ("1".equals(userInput) || "2".equals(userInput) || "3".equals(userInput) || "back".equals(
          userInput)) {
        return userInput;
      }

    }


  }
}
