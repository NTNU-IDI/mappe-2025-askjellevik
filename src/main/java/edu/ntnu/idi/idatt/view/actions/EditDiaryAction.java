package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;

public class EditDiaryAction implements Action {

  private final DiaryMenu diaryMenu;
  private final DiaryEntry diaryEntry;

  public EditDiaryAction(DiaryMenu diaryMenu, DiaryEntry diaryEntry) {
    this.diaryMenu = diaryMenu;
    this.diaryEntry = diaryEntry;
  }

  @Override
  public void execute() {
    boolean showSubMenu = true;

    while (showSubMenu) {
      String submenuUserChoice = diaryMenu.subMenuForEditingDiaryAction();

      switch (submenuUserChoice) {
        case "1" -> diaryMenu.editDiaryTitle(diaryEntry);
        case "2" -> diaryMenu.overwriteDiaryText(diaryEntry);
        case "3" -> diaryMenu.addDiaryText(diaryEntry);
        case "back" -> {
          showSubMenu = false;
        }
      }

    }

  }
}
