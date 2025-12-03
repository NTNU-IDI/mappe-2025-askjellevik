package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.model.exceptions.AuthorNotFoundException;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Action class for finding specific diary entries based a specific criteria.
 * Allows users to search for diary entries by keyword, specific date, or within a date range.
 */
public class FindSpecificDiaryAction implements Action {

  private final DiaryMenu diaryMenu;
  private final DiaryController diaryController;
  private final AuthorController authorController;

  /**
   * Constructs a FindSpecificDiaryAction with the injected dependencies.
   */
  public FindSpecificDiaryAction(DiaryMenu diaryMenu, DiaryController diaryController,
      AuthorController authorController) {
    this.diaryMenu = diaryMenu;
    this.diaryController = diaryController;
    this.authorController = authorController;
  }

  /**
   * Executes the action of finding specific diary entries.
   * Presents a submenu for searching diary entries by keyword, specific date, or within a date range.
   * Users can exit the submenu by selecting "back".
   * Displays the search results and allows further actions on the found diary entries.
   */
  @Override
  public void execute() {
    boolean isSearching = true;

    while (isSearching) {
      diaryMenu.displayMainHeader("Diary entry lookup ");
      String submenuUserChoice = diaryMenu.subMenuForFindingDiaryAction();

      switch (submenuUserChoice) {
        case "1" -> searchForDiaryEntryByWord();
        case "2" -> searchForDiaryEntryWithSpecificDate();
        case "3" -> searchForDiaryEntryWithinTwoDates();
        case "4" -> searchForDiaryEntryByAuthor();
        case "back" -> isSearching = false;
        default -> diaryMenu.displayErrorMessage("Could not execute that command");
      }
    }
  }

  private void searchForDiaryEntryByAuthor() {
    diaryMenu.displaySecondaryHeader("Searching by author");

    String authorEmail = diaryMenu.getUserStringWithPrompt("Enter the author's email");
    Author foundAuthor;

    try {
      foundAuthor = authorController.getAuthorByEmail(authorEmail);


    } catch (AuthorNotFoundException e) {
      diaryMenu.displayErrorMessage("Found no author with that email");
      return;
    }

    List<DiaryEntry> diaries = diaryController.getListOfAllEntriesBySpecificAuthor(foundAuthor);
    displaySearchResultsWithAction(diaries);


  }

  /**
   * Searches for diary entries within a specified start and end date.
   * Prompts the user to enter the start and end dates for the search.
   * Displays the search results and allows further actions on the found diary entries.
   */
  private void searchForDiaryEntryWithinTwoDates() {
    diaryMenu.displaySecondaryHeader("Searching within two dates");

    LocalDateTime startSearchDate = diaryMenu.getSearchDate("Cancel or Enter the start date: dd-MM-yyyy");

    if (startSearchDate == null) {
      return;
    }

    LocalDateTime endSearchDate = diaryMenu.getSearchDate("Cancel or Enter the end date: dd-MM-yyyy");

    if (endSearchDate == null) {
      return;
    }

    List<DiaryEntry> diaries = diaryController.getListOfDiaryEntriesWithinDates(startSearchDate,
        endSearchDate);

    displaySearchResultsWithAction(diaries);

  }

  /**
   * Searches for diary entries on a specific date.
   * Prompts the user to enter the date for the search.
   * Displays the search results and allows further actions on the found diary entries.
   */
  private void searchForDiaryEntryWithSpecificDate() {
    diaryMenu.displaySecondaryHeader("Searching by date");

    LocalDateTime searchDate = diaryMenu.getSearchDate("Cancel or Enter the search date: dd-MM-yyyy");

    List<DiaryEntry> diaries = diaryController.getListOfDiaryEntriesByDate(searchDate);

    displaySearchResultsWithAction(diaries);


  }

  /**
   * Searches for diary entries containing a specific word.
   * Prompts the user to enter the word for the search.
   * Displays the search results and allows further actions on the found diary entries.
   */
  private void searchForDiaryEntryByWord() {
    diaryMenu.displaySecondaryHeader("Searching by word");

    String searchWord = diaryMenu.getUserStringWithPrompt("Enter the search word");

    List<DiaryEntry> diaries = diaryController.getListOfDiaryEntriesWithSpecificWord(searchWord);

    displaySearchResultsWithAction(diaries);

  }

  /**
   * Displays the search results by creating and executing a BrowseDiariesAction.
   * If no diary entries are found, an error message is displayed.
   *
   * @param diaries the list of diary entries found in the search
   */
  private void displaySearchResultsWithAction(List<DiaryEntry> diaries) {
    if (diaries.isEmpty()) {
      diaryMenu.displayErrorMessage("Found no diary entries matching the search criteria");
    } else {
      BrowseDiariesAction action = new BrowseDiariesAction(diaryMenu, diaryController, diaries);
      action.execute();

    }
  }
}









