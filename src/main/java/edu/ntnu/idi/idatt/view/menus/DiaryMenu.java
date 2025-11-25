package edu.ntnu.idi.idatt.view.menus;

import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.controller.exceptions.EmptyStringException;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidDateException;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.controller.validators.DateValidator;
import edu.ntnu.idi.idatt.controller.validators.DiaryEntryValidator;
import edu.ntnu.idi.idatt.controller.validators.StringValidator;
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

  public DiaryMenu(DiaryController diaryController, ConsoleUtils consoleUtils,
      AuthorMenu authorMenu) {
    this.diaryController = diaryController;
    this.consoleUtils = consoleUtils;
    this.authorMenu = authorMenu;
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

  public void searchForDiaryEntryWithinTwoDates() {
  }

  public LocalDateTime getSearchDate() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    while (true) {
      String userInput = consoleUtils.readUserInputWithPrompt("Enter the search date: dd-MM-yyyy");

      if ("back".equalsIgnoreCase(userInput)) {
        return null;
      }

      try {
        LocalDate entryDate = LocalDate.parse(userInput, formatter);
        return entryDate.atStartOfDay();

      } catch (DateTimeParseException e) {
        consoleUtils.showColoredMessage("Invalid date format (dd-MM-yyyy)", Color.RED);
      }


    }
  }

  public String searchForDiaryEntryByWordPrompt() {
    return consoleUtils.readUserInputWithPrompt("Enter the search word: ");

  }

  //    for (int i = start; i < end; i++) {
//      consoleUtils.showMessage(
//          i + page + "  |" + diaryEntries.get(i).getEntryTitle() + "    |"
//              + consoleUtils.formatDateToView(diaryEntries.get(i)
//              .getEntryDate()));
//    }
//
//    return consoleUtils.readUserInputWithPrompt(
//        "Enter '(Diary key)', 'Next' or 'Previous'  | 'Back' for back");
  public String displayEntriesForSelection(List<DiaryEntry> diaryEntries, int start, int end,
      int page) {
    consoleUtils.showColoredMessage("=== Registered Diary Entries (Page" + (page + 1) + ") ===",
        Color.WHITE);

    String leftAlignFormat = "| %-4d | %-20s |%n";

    System.out.format("+---+-----Page " + page + "--+%n");
    System.out.format("|ID | Title              |%n");
    System.out.format("+---+--------------------+%n");
    for (int i = start; i < end; i++) {
      System.out.format(leftAlignFormat, i, diaryEntries.get(i).getEntryTitle());
    }
    System.out.format("+-----------------+------+%n");
    return consoleUtils.readUserInputWithPrompt(
        "Enter '(Diary key)', 'Next' or 'Previous'  | 'Back' for back");

  }


  public void createNewDiaryEntry() {
    consoleUtils.showColoredMessage("=== Creating new Diary Entry ===", Color.WHITE);


  }

  public String subMenuForDiaryManagementAction(DiaryEntry diaryEntry) {
    String[] messages = {"You have chosen the diary: \n",
        diaryEntry.getEntryTitle(), " written by "
        , diaryEntry.getEntryAuthor().getName(), " registered ",
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

  public LocalDateTime getDateOfNewDiaryEntry() {
    consoleUtils.showMessage("Choose date for the diary entry:");
    consoleUtils.showMessage("Option 1: Automatically choose the current time");
    consoleUtils.showMessage("Option 2: Enter date manually as ");
    consoleUtils.showMessage("Back: Go back");

    String userInput;

    LocalDateTime entryDate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    while (true) {
      userInput = consoleUtils.readUserInputWithPrompt("Enter your choice:");
      if ("1".equals(userInput)) {
        entryDate = LocalDateTime.now();
        consoleUtils.showMessage("Using the current date and time: " + entryDate.format(formatter));
        return entryDate;

      }
      if ("back".equalsIgnoreCase(userInput)) {
        return null;
      }
      if ("2".equalsIgnoreCase(userInput)) {
        String dateString = consoleUtils.readUserInputWithPrompt(
            "Enter the date as dd-MM-yyyy HH:mm;");
        if ("back".equalsIgnoreCase(dateString)) {
          return null;
        }
        try {
          entryDate = LocalDateTime.parse(dateString, formatter);
          return entryDate;

        } catch (InvalidDateException | DateTimeParseException e) {
          consoleUtils.showColoredMessage(e.getMessage(), Color.RED);
        }

      }
    }

  }

  private void displayListOfDiaries(List<DiaryEntry> diaries) {

    if (diaries.isEmpty()) {
      consoleUtils.showColoredMessage("Found no diaries", Color.RED);
      return;
    }


  }


  public void readDiaryEntry(DiaryEntry diaryEntry) {
    consoleUtils.showColoredMessage("===== Reading diary entry =====", Color.MAGENTA);
    consoleUtils.showMessage(diaryEntry.getEntryTitle());
    consoleUtils.printLine(Color.GREEN);
    consoleUtils.showMessage("By " + diaryEntry.getEntryAuthor().getName());
    consoleUtils.showMessage("Date " + consoleUtils.formatDateToView(diaryEntry.getEntryDate()));
    consoleUtils.printLine(Color.GREEN);
    consoleUtils.showMessage(diaryEntry.getEntryText());
    consoleUtils.printLine(Color.GREEN);
    consoleUtils.readUserInputWithPrompt("Press ENTER to continue");
  }


  public void deleteDiaryEntry(DiaryEntry diaryEntry) {
    consoleUtils.showColoredMessage("? DELETE DIARY ENTRY ?", Color.RED);
    consoleUtils.showMessage("Are you sure you want to delete this diary from the register?");
    String[] messages = {
        diaryEntry.getEntryTitle(), " by ", diaryEntry.getEntryAuthor().getName(), " from "
        , consoleUtils.formatDateToView(diaryEntry.getEntryDate())};
    Color[] colors = {Color.WHITE, Color.DEFAULT, Color.WHITE, Color.DEFAULT, Color.WHITE};

    consoleUtils.showMessageWithMultipleColors(messages, colors);
    String userInput = consoleUtils.readUserInputWithPrompt("Yes/No");
    while (true) {
      if ("yes".equalsIgnoreCase(userInput)) {
        diaryController.deleteDiaryEntryInRegister(diaryEntry);
        consoleUtils.showColoredMessage("Diary entry deleted.", Color.BLACK);
        return;
      }
      if ("no".equalsIgnoreCase(userInput)) {
        return;
      }
      consoleUtils.showColoredMessage("Not a valid input, Yes/No", Color.RED);
      userInput = consoleUtils.readUserInput();
    }
  }

  public String subMenuForEditingDiaryAction() {
    while (true) {
      consoleUtils.showColoredMessage("=== Diary entry editing ===", Color.WHITE);
      consoleUtils.showMessage("Option 1: Edit the diary entry title");
      consoleUtils.showMessage("Option 2: Overwrite the diary entry text");
      consoleUtils.showMessage("Option 3: Add text to the existing diary entry text");
      consoleUtils.showMessage("Option Back: Exit diary entry editing");

      String userInput = consoleUtils.readUserInput();

      if ("1".equals(userInput) || "2".equals(userInput) || "3".equals(userInput) || "back".equals(
          userInput)) {
        return userInput;
      }

    }
  }


  public void editDiaryTitle(DiaryEntry diaryEntry) {
    consoleUtils.showColoredMessage("### Editing Diary Title ###", Color.MAGENTA);
    consoleUtils.showMessage("Original title: " + diaryEntry.getEntryTitle());
    consoleUtils.showMessage("------------------------------");

    String newTitle = consoleUtils.readUserInputWithPrompt(
        "Enter the new title: (Back for back)");
    while (true) {
      if ("back".equalsIgnoreCase(newTitle)) {
        return;
      }

      try {

        StringValidator.validateDiaryTitle(newTitle);

        diaryController.setDiaryTitle(diaryEntry, newTitle);
        return;

      } catch (InvalidInputException | EmptyStringException e) {
        consoleUtils.showColoredMessage(e.getMessage(), Color.RED);
        newTitle = consoleUtils.readUserInputWithPrompt(
            "Enter the new title: (Back for back)");
      }

    }

  }


  public void overwriteDiaryText(DiaryEntry diaryEntry) {
    consoleUtils.showColoredMessage("### Overwriting text ###", Color.MAGENTA);
    String newText = consoleUtils.readUserInputWithPrompt(
        "Enter the new text that will overwrite the existing text: (Back for back)");
    while (true) {
      if ("back".equalsIgnoreCase(newText)) {
        return;
      }

      try {

        StringValidator.validateDiaryEntryText(newText);

        diaryController.setDiaryText(diaryEntry, newText);
        return;

      } catch (EmptyStringException e) {
        consoleUtils.showColoredMessage(e.getMessage(), Color.RED);
        newText = consoleUtils.readUserInputWithPrompt(
            "Enter the new text that will overwrite the existing text: (Back for back)");
      }
    }

  }

  public void addDiaryText(DiaryEntry diaryEntry) {
    consoleUtils.showColoredMessage("### Adding text ###", Color.MAGENTA);
    String textToAdd = consoleUtils.readUserInputWithPrompt(
        "Enter the new text that will add to the existing text: (Back for back)");
    while (true) {
      if ("back".equalsIgnoreCase(textToAdd)) {
        return;
      }

      try {

        StringValidator.validateDiaryEntryText(textToAdd);

        String finalText = diaryEntry.getEntryText() + " " + textToAdd;

        diaryController.setDiaryText(diaryEntry, finalText);
        return;

      } catch (EmptyStringException e) {
        consoleUtils.showColoredMessage(e.getMessage(), Color.RED);
        textToAdd = consoleUtils.readUserInputWithPrompt(
            "Enter the new text that will overwrite the existing text: (Back for back)");
      }
    }

  }

  public List<DiaryEntry> getListOfAllRegisteredDiaryEntries() {
    return diaryController.getListOfAllRegisteredDiaryEntries();
  }

  public void displayPromptedMessage(String string) {
    consoleUtils.showColoredMessage(string, Color.RED);
  }

  public String getDiaryTitlePrompt() {
    return consoleUtils.readUserInputWithPrompt("Enter the diary title: ");
  }

  public void displayErrorMessage(String error) {
    consoleUtils.showColoredMessage(error, Color.RED);
  }

  public String getDiaryTextPrompt() {
    return null;
  }

  public void displayHeader(String s) {

  }
}
