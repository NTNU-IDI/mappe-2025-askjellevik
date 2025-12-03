package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;
import java.util.ArrayList;
import java.util.List;

/**
 * Action class for browsing registered diary entries.
 * Allows users to view, edit, or delete selected diary entries.
 */
public class BrowseDiariesAction implements Action {

  private final DiaryMenu diaryMenu;
  private final DiaryController diaryController;
  private List<DiaryEntry> diaryEntries;
  private final boolean isBrowsingAllDiaries;

  /**
   * Constructs a BrowseDiariesAction with the injected dependencies.
   * This constructor is used for browsing all diary entries in the register.
   *
   * @param diaryMenu the DiaryMenu for displaying diary-related UI
   * @param diaryController the DiaryController for managing diary entries
   */
  public BrowseDiariesAction(DiaryMenu diaryMenu, DiaryController diaryController) {
    this.diaryMenu = diaryMenu;
    this.diaryController = diaryController;
    this.isBrowsingAllDiaries = true;
  }

  /**
   * Constructs a BrowseDiariesAction with the injected dependencies and a list of diary entries.
   * This constructor is used for browsing a specific list of diary entries, such as search results
   * or by a specific author.
   *
   * @param diaryMenu the DiaryMenu for displaying diary-related menus
   * @param diaryController the DiaryController for managing diary entries
   * @param diaryEntries the list of diary entries to browse
   */
  public BrowseDiariesAction(DiaryMenu diaryMenu, DiaryController diaryController,
      List<DiaryEntry> diaryEntries) {
    this.diaryMenu = diaryMenu;
    this.diaryController = diaryController;
    this.diaryEntries = new ArrayList<>(diaryEntries);
    this.isBrowsingAllDiaries = false;
  }

  /**
   * Executes the action of browsing diary entries.
   * When the user selects a diary entry by a user input key, they can choose to
   * view, edit, or delete it.
   * Diary entries are displayed with pagination.
   * If no diary entries are registered, an error message is displayed and the action exits.
   */
  @Override
  public void execute() {

    if (isBrowsingAllDiaries) {
      List<DiaryEntry> immutableList = diaryController.getListOfAllRegisteredDiaryEntries();
      this.diaryEntries = new ArrayList<>(immutableList);
      if (this.diaryEntries.isEmpty()) {
        diaryMenu.displayErrorMessage("No diary entries registered yet");
        return;
      }
    }

    int page = 0;
    int pageSize = 5;

    while (true) {

      int start = page * pageSize;
      int end = Math.min(diaryEntries.size(), start + pageSize);

      if (isBrowsingAllDiaries) {
        diaryMenu.displayMainHeader("Registered diary entries");
      } else {
        diaryMenu.displayMainHeader("Registered diary entries from search");

      }



      diaryMenu.displayEntriesForSelection(diaryEntries, start, end, page + 1);

      String promptMessage = "Commands: (KEY NUMBER)";
      if (end < diaryEntries.size()) {
        promptMessage += ", NEXTPAGE";
      }
      if (page > 0) {
        promptMessage += ", PREVPAGE";
      }
      promptMessage += ", BACK";
      String userInput = diaryMenu.getUserStringWithPrompt(promptMessage);

      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }

      if ("nextpage".equalsIgnoreCase(userInput) && end < diaryEntries.size()) {
        page += 1;
        continue;
      }
      if ("prevpage".equalsIgnoreCase(userInput) && page > 0) {
        page -= 1;
        continue;
      }
      try {

        int indexOfChosenDiary = Integer.parseInt(userInput) - 1;

        if (indexOfChosenDiary < start || indexOfChosenDiary >= end) {
          diaryMenu.displayErrorMessage("The input key was out of range");
          continue;
        }

        DiaryEntry diaryEntry = diaryEntries.get(indexOfChosenDiary);

        boolean choosingSubMenu = true;
        while (choosingSubMenu) {
          String submenuUserChoice = diaryMenu.subMenuForDiaryManagementAction(diaryEntry);

          switch (submenuUserChoice) {
            case "1" -> diaryMenu.readDiaryEntry(diaryEntry);
            case "2" -> {
              Action editDiaryAction = new EditDiaryAction(diaryMenu, diaryEntry, diaryController);
              editDiaryAction.execute();

            }
            case "3" -> {
              if (deleteDiaryEntry(diaryEntry)) {
                diaryEntries.remove(diaryEntry);
                choosingSubMenu = false;
              }

            }
            case "back" -> choosingSubMenu = false;
            default -> diaryMenu.displayErrorMessage("Could not execute that command");
          }

        }
      } catch (NumberFormatException e) {
        diaryMenu.displayErrorMessage("Wrong input format");
      }


    }
  }

  /**
   * Prompts the user to confirm deletion of a diary entry.
   * If the user confirms, the diary entry is deleted from the register.
   *
   * @param diaryEntry the diary entry to be deleted
   * @return true if the diary entry was deleted, false otherwise
   */
  private boolean deleteDiaryEntry(DiaryEntry diaryEntry) {
    while (true) {
      String userInput = diaryMenu.deleteDiaryEntryPrompt(diaryEntry);

      if ("no".equalsIgnoreCase(userInput)) {
        return false;
      }
      if ("yes".equalsIgnoreCase(userInput)) {

        try {
          diaryController.deleteDiaryEntryInRegister(diaryEntry.getEntryID());
          diaryMenu.displaySuccessMessage("Diary entry deleted");
          return true;
        } catch (Exception e) {
          diaryMenu.displayErrorMessage("Could not delete the diary entry");
          return false;
        }
      }
      diaryMenu.displayErrorMessage("Not valid input, yes/no");
    }

  }
}










