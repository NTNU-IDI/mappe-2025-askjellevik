package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.view.menus.DiaryMenu;

public class CreateDiaryEntryAction implements Action {

  private final DiaryMenu diaryMenu;

  public CreateDiaryEntryAction(DiaryMenu diaryMenu) {
    this.diaryMenu = diaryMenu;
  }

  @Override
  public void execute() {
    diaryMenu.createNewDiaryEntry();
  }


}
