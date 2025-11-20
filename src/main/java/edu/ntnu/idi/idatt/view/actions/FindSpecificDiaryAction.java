package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.view.menus.DiaryMenu;

public class FindSpecificDiaryAction implements Action {

  private final DiaryMenu diaryMenu;

  public FindSpecificDiaryAction(DiaryMenu diaryMenu) {
    this.diaryMenu = diaryMenu;
  }

  @Override
  public void execute() {
    String submenuUserChoice = diaryMenu.subMenuForFindingDiaryAction();

    switch (submenuUserChoice) {
      case "1" -> diaryMenu.searchForDiaryEntryByWord();
      case "2" -> diaryMenu.searchForDiaryEntryWithSpecificDate();
      case "3" -> diaryMenu.searchForDiaryEntryWithinTwoDates();
      case "back" -> {
      }
    }

  }
}





