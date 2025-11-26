package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;
import java.time.LocalDateTime;
import java.util.List;

public class FindSpecificDiaryAction implements Action {

  private final DiaryMenu diaryMenu;
  private final DiaryController diaryController;

  public FindSpecificDiaryAction(DiaryMenu diaryMenu, DiaryController diaryController) {
    this.diaryMenu = diaryMenu;
    this.diaryController = diaryController;
  }

  @Override
  public void execute() {
    boolean isSearching = true;

    while (isSearching) {
      String submenuUserChoice = diaryMenu.subMenuForFindingDiaryAction();

      switch (submenuUserChoice) {
        case "1" -> searchForDiaryEntryByWord();
        case "2" -> searchForDiaryEntryWithSpecificDate();
        case "3" -> searchForDiaryEntryWithinTwoDates();
        case "back" -> isSearching = false;
        default -> diaryMenu.displayErrorMessage("Could not execute that command");
      }
    }
  }

  private void searchForDiaryEntryWithinTwoDates() {
    diaryMenu.displayHeader("--- Searching within two dates ---");

    LocalDateTime startSearchDate = diaryMenu.getSearchDate();

    LocalDateTime endSearchDate = diaryMenu.getSearchDate();

    List<DiaryEntry> diaries = diaryController.getListOfDiaryEntriesWithinDates(startSearchDate,
        endSearchDate);

    displaySearchResultsWithAction(diaries);

  }

  private void searchForDiaryEntryWithSpecificDate() {
    diaryMenu.displayHeader("--- Searching by date ---");

    LocalDateTime searchDate = diaryMenu.getSearchDate();

    List<DiaryEntry> diaries = diaryController.getListOfDiaryEntriesByDate(searchDate);

    displaySearchResultsWithAction(diaries);


  }

  private void searchForDiaryEntryByWord() {
    diaryMenu.displayHeader("--- Searching by word ---");

    String searchWord = diaryMenu.searchForDiaryEntryByWordPrompt();

    List<DiaryEntry> diaries = diaryController.getListOfDiaryEntriesWithSpecificWord(searchWord);

    displaySearchResultsWithAction(diaries);

  }

  private void displaySearchResultsWithAction(List<DiaryEntry> diaries) {
    if (diaries.isEmpty()) {
      diaryMenu.displayErrorMessage("No entries from that date");
    } else {
      BrowseDiariesAction action = new BrowseDiariesAction(diaryMenu, diaryController, diaries);
      action.execute();

    }
  }
}









