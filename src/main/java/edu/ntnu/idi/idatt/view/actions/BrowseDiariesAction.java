package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;
import java.util.List;

public class BrowseDiariesAction implements Action {

  private final DiaryMenu diaryMenu;
  private final DiaryController diaryController;
  private List<DiaryEntry> diaryEntries;

  public BrowseDiariesAction(DiaryMenu diaryMenu, DiaryController diaryController) {
    this.diaryMenu = diaryMenu;
    this.diaryController = diaryController;
  }

  public BrowseDiariesAction(DiaryMenu diaryMenu, DiaryController diaryController, List<DiaryEntry> diaryEntries) {
    this.diaryMenu = diaryMenu;
    this.diaryController = diaryController;
    this.diaryEntries = diaryEntries;
  }

  @Override
  public void execute() {

    if (diaryEntries == null) {
      diaryEntries = diaryController.getListOfAllRegisteredDiaryEntries();
    }


    int page = 0;
    int pageSize = 5;

    while (true) {

      int start = page * pageSize;
      int end = Math.min(diaryEntries.size(), start + pageSize);

      String userInput = diaryMenu.displayEntriesForSelection(diaryEntries, start, end, page);

      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }

      if ("next".equalsIgnoreCase(userInput) && end < diaryEntries.size()) {
        page += 1;
        continue;
      }
      if ("previous".equalsIgnoreCase(userInput) && page > 0 ) {
        page -= 1;
        continue;

      }
      try {

        int indexOfChosenDiary = Integer.parseInt(userInput);

        if (indexOfChosenDiary < start || indexOfChosenDiary >= end) {
          diaryMenu.displayPromptedMessage("The input key was out of range");
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
              diaryMenu.deleteDiaryEntry(diaryEntry);
              diaryEntries = diaryMenu.getListOfAllRegisteredDiaryEntries();
              choosingSubMenu = false;
            }
            case "back" -> choosingSubMenu = false;
          }

        }
      } catch (NumberFormatException e) {
        diaryMenu.displayPromptedMessage("Wrong input format");
      }


    }
  }
}










