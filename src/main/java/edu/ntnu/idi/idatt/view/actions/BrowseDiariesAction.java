package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;
import java.util.List;

public class BrowseDiariesAction implements Action {

  private final DiaryMenu diaryMenu;
  private final DiaryController diaryController;
  private List<DiaryEntry> diaryEntries;
  private final boolean isBrowsingAllDiaries;

  public BrowseDiariesAction(DiaryMenu diaryMenu, DiaryController diaryController) {
    this.diaryMenu = diaryMenu;
    this.diaryController = diaryController;
    this.isBrowsingAllDiaries = true;
  }

  public BrowseDiariesAction(DiaryMenu diaryMenu, DiaryController diaryController,
      List<DiaryEntry> diaryEntries) {
    this.diaryMenu = diaryMenu;
    this.diaryController = diaryController;
    this.diaryEntries = diaryEntries;
    this.isBrowsingAllDiaries = false;
  }

  @Override
  public void execute() {

    if (isBrowsingAllDiaries) {
      this.diaryEntries = diaryController.getListOfAllRegisteredDiaryEntries();
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



      String userInput = diaryMenu.displayEntriesForSelection(diaryEntries, start, end, page+1);

      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }

      if ("next".equalsIgnoreCase(userInput) && end < diaryEntries.size()) {
        page += 1;
        continue;
      }
      if ("previous".equalsIgnoreCase(userInput) && page > 0) {
        page -= 1;
        continue;
      }
      try {

        int indexOfChosenDiary = Integer.parseInt(userInput);

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
              Action editDiaryAction = new EditDiaryAction(diaryMenu, diaryEntry);
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

  private boolean deleteDiaryEntry(DiaryEntry diaryEntry) {
    while (true) {
      String userInput = diaryMenu.deleteDiaryEntryPrompt(diaryEntry);

      if ("no".equalsIgnoreCase(userInput)) {
        return false;
      }
      if ("yes".equalsIgnoreCase(userInput)) {
        diaryController.deleteDiaryEntryInRegister(diaryEntry);
        diaryMenu.displaySuccessMessage("Diary entry deleted");

        return true;
      }
      diaryMenu.displayErrorMessage("Not valid input, yes/no");
    }

  }
}










