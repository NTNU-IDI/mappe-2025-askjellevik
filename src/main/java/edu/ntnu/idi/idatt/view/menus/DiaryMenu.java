package edu.ntnu.idi.idatt.view.menus;


import edu.ntnu.idi.idatt.controller.exceptions.InvalidDateException;
import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.model.DiaryEntry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.fusesource.jansi.Ansi.Color;

public class DiaryMenu extends BaseMenu{


  public DiaryMenu(ConsoleUtils consoleUtils) {
    super(consoleUtils);
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

  public String displayEntriesForSelection(List<DiaryEntry> diaryEntries, int start, int end,
      int page) {
    consoleUtils.showColoredMessage("=== Registered Diary Entries ===",
        Color.WHITE);

    String leftAlignFormat = "| %-1d | %-20s |%n";

    System.out.format("+---+-------Page " + page +  "---------+%n");
    System.out.format("|KEY| Title                |%n");
    System.out.format("+---+----------------------+%n");
    for (int i = start; i < end; i++) {
      System.out.format(leftAlignFormat, i, diaryEntries.get(i).getEntryTitle());
    }
    System.out.format("+---+----------------------+%n");
    return consoleUtils.readUserInputWithPrompt(
        "Enter '(Diary key)', 'Next' or 'Previous'  | 'Back' for back");

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
  
  public String deleteDiaryEntryPrompt(DiaryEntry diaryEntry) {
    consoleUtils.showColoredMessage("? DELETE DIARY ENTRY ?", Color.RED);
    consoleUtils.showMessage("Are you sure you want to delete this diary from the register?");
    String[] messages = {
        diaryEntry.getEntryTitle(), " by ", diaryEntry.getEntryAuthor().getName(), " from "
        , consoleUtils.formatDateToView(diaryEntry.getEntryDate())};
    Color[] colors = {Color.WHITE, Color.DEFAULT, Color.WHITE, Color.DEFAULT, Color.WHITE};

    consoleUtils.showMessageWithMultipleColors(messages, colors);


    return consoleUtils.readUserInputWithPrompt("Yes/No");

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


}
