package edu.ntnu.idi.idatt.view.menus;

import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.fusesource.jansi.Ansi.Color;

/**
 * Menu class for displaying diary-related submenus and prompts to the user.
 * Extends the BaseMenu to utilize common menu functionalities.
 *
 * @author andskj
 */
public class DiaryMenu extends BaseMenu {


  /**
   * Constructs the DiaryMenu with the injected ConsoleUtils for user interaction.
   */
  public DiaryMenu(ConsoleUtils consoleUtils) {
    super(consoleUtils);
  }

  /**
   * Displays the submenu for finding diary entries and returns the user's choice.
   * Options include search by word, by date, within two dates or returning.
   *
   * @return the user input as a String.
   */
  public String subMenuForFindingDiaryAction() {

      consoleUtils.showMessage("Option 1: Search for diary entry by word");
      consoleUtils.showMessage("Option 2: Search for diary entry by specific date");
      consoleUtils.showMessage("Option 3: Search for diary entry within two dates");
      consoleUtils.showMessage("Option 4: Search for diary entry by author email");
      consoleUtils.showMessage("Option Back: Go back");

      return consoleUtils.readUserInput();



  }

  /**
   * Prompts the user to enter a search date in the format dd-MM-yyyy.
   * The input is validated and parsed to a LocalDateTime object.
   * If the user enters "cancel", the method returns null.
   *
   *
   *
   * @return the parsed LocalDateTime object or null if the user chooses to go back.
   */
  public LocalDateTime getSearchDate(String prompt) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    while (true) {
      String userInput = consoleUtils.readUserInputWithPrompt(prompt);

      if ("cancel".equalsIgnoreCase(userInput)) {
        return null;
      }

      try {
        LocalDate entryDate = LocalDate.parse(userInput, formatter);
        return entryDate.atTime(23, 59, 59);

      } catch (DateTimeParseException e) {
        consoleUtils.showColoredMessage("Invalid date format (dd-MM-yyyy)", Color.RED);
      }


    }
  }

  /**
   * Displays a paginated list of diary entries for user selection.
   * Each entry is shown with its key and title.
   * The user can navigate through pages or select a diary entry by its key.
   *
   * @param diaryEntries the list of diary entries to display.
   * @param start the starting index for the current page.
   * @param end the ending index for the current page.
   * @param page the current page number.
   */
  public void displayEntriesForSelection(List<DiaryEntry> diaryEntries, int start, int end,
      int page) {
    System.out.format("+----+----------------Page " + page + "-----------------------+%n");
    System.out.format("|KEY | Title                          | Author     |%n");
    System.out.format("+----+---------------------------------------------+%n");

    String leftAlignFormat = "| %-2d | %-30s | %-10s |%n";
    for (int i = start; i < end; i++) {
      System.out.format(leftAlignFormat, i + 1, diaryEntries.get(i).getEntryTitle(),
          diaryEntries.get(i).getEntryAuthor().getLastName());
    }
    System.out.format("+----+---------------------------------------------+%n");
  }

  /**
   * Displays the submenu for managing a specific diary entry and returns the user's choice.
   * Options include reading, editing, deleting the diary entry, or returning.
   *
   * @param diaryEntry the diary entry to manage.
   * @return the user input as a String.
   */
  public String subMenuForDiaryManagementAction(DiaryEntry diaryEntry) {
    consoleUtils.showMessage("");
    displaySecondaryHeader("You have chosen the diary entry:");
    String[] messages = {
        diaryEntry.getEntryTitle(), "\nWritten by ",
        diaryEntry.getEntryAuthor().getName(), " registered ",
        consoleUtils.formatDateToView(diaryEntry.getEntryDate())};
    Color[] colors = {Color.YELLOW, Color.WHITE, Color.YELLOW, Color.WHITE,
        Color.YELLOW};

    tryShowMessageWithMultipleColors(messages, colors);
    consoleUtils.showMessage("");

      consoleUtils.showMessage("Option 1: Read this diary entry");
      consoleUtils.showMessage("Option 2: Edit this diary entry");
      consoleUtils.showMessage("Option 3: Delete this diary entry");
      consoleUtils.showMessage("Option Back: Back to browsing");

      return consoleUtils.readUserInput();



  }

  /**
   * Prompts the user to choose a date for a new diary entry.
   * The user can either use the current date which will return LocalDateTime.now(),
   * or enter a date manually in the format dd-MM-yyyy HH:mm.
   * If the user enters "cancel", the method returns null.
   *
   * @return the chosen LocalDateTime for the new diary entry
   * or null if the user chooses to cancel.
   */
  public LocalDateTime getDateOfNewDiaryEntry() {
    displaySecondaryHeader("Date selection");
    consoleUtils.showMessage("Option 1: Automatically choose the current time");
    consoleUtils.showMessage("Option 2: Enter date manually");
    consoleUtils.showMessage("Cancel: Cancel diary entry creation");

    String userInput;

    LocalDateTime entryDate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    while (true) {
      userInput = consoleUtils.readUserInputWithPrompt("Enter your choice");
      if ("cancel".equalsIgnoreCase(userInput)) {
        return null;
      }

      if ("1".equals(userInput)) {
        entryDate = LocalDateTime.now();
        consoleUtils.printLine(Color.GREEN);
        consoleUtils.showMessage("Using the current date and time " + entryDate.format(formatter));
        consoleUtils.showMessage("");
        return entryDate;
      } else if ("2".equalsIgnoreCase(userInput)) {
        while (true) {
          String dateString = consoleUtils.readUserInputWithPrompt(
              "Cancel or Enter the date as dd-MM-yyyy HH:mm");
          if ("cancel".equalsIgnoreCase(dateString)) {
            return null;
          }
          try {
            entryDate = LocalDateTime.parse(dateString, formatter);
            return entryDate;

          } catch (DateTimeParseException e) {
            displayErrorMessage("That was not a valid date and time");
          }
        }


      } else {
        displayErrorMessage("That was not a valid option");
      }

    }

  }

  /**
   * Displays the full details of a diary entry to the user.
   * Includes title, author, date, and the entry text.
   * Waits for the user to press ENTER before returning.
   *
   * @param diaryEntry the diary entry to read.
   */
  public void readDiaryEntry(DiaryEntry diaryEntry) {
    consoleUtils.clearScreen();
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

  /**
   * Prompts the user for confirmation before deleting a diary entry.
   * Displays the diary entry details and retrieves a Yes/No response from the user.
   *
   * @param diaryEntry the diary entry to be deleted.
   * @return the user's response as a String.
   */
  public String deleteDiaryEntryPrompt(DiaryEntry diaryEntry) {
    consoleUtils.showMessage("");
    consoleUtils.showColoredMessage("? DELETE DIARY ENTRY ?", Color.RED);
    consoleUtils.showMessage("Are you sure you want to delete this diary from the register?");
    String[] messages = {
        diaryEntry.getEntryTitle(), " by ", diaryEntry.getEntryAuthor().getName(), " from ",
        consoleUtils.formatDateToView(diaryEntry.getEntryDate())};
    Color[] colors = {Color.WHITE, Color.DEFAULT, Color.WHITE, Color.DEFAULT, Color.WHITE};

    tryShowMessageWithMultipleColors(messages, colors);

    return consoleUtils.readUserInputWithPrompt("Yes/No");

  }

  /**
   * Displays the submenu for editing a diary entry and returns the user's choice.
   * Options include editing the title, overwriting the text, adding to the text, or returning.
   *
   * @return the user input as a String.
   */
  public String subMenuForEditingDiaryAction() {
      consoleUtils.showMessage("Option 1: Edit the diary entry title");
      consoleUtils.showMessage("Option 2: Overwrite the diary entry text");
      consoleUtils.showMessage("Option 3: Add text to the existing diary entry text");
      consoleUtils.showMessage("Option Back: Exit diary entry editing");

      return consoleUtils.readUserInput();


  }


}
