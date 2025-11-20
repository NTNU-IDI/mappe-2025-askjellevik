package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;
import java.util.List;

public class BrowseDiariesAction implements Action {

  private final DiaryMenu diaryMenu;

  public BrowseDiariesAction(DiaryMenu diaryMenu) {
    this.diaryMenu = diaryMenu;
  }

  @Override
  public void execute() {
    while (true) {

      DiaryEntry diaryEntry = diaryMenu.chooseRegisteredDiaries();

      if (diaryEntry == null) {
        return;
      }

      boolean choosing = true;

      while (choosing) {
        String submenuUserChoice = diaryMenu.subMenuForDiaryManagementAction(diaryEntry);


      switch (submenuUserChoice) {
        case "1" -> diaryMenu.readDiaryEntry(diaryEntry);
        case "2" -> {
          Action editDiaryAction = new EditDiaryAction(diaryMenu, diaryEntry);
          editDiaryAction.execute();
        }
        case "3" -> {
          diaryMenu.deleteDiaryEntry(diaryEntry);
          break;
        }
        case "back" -> {
          choosing = false;
        }
      }

    }
  }
}

}
