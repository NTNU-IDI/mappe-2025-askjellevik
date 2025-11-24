package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;

import java.time.LocalDateTime;

public class CreateDiaryEntryAction implements Action {

  private final DiaryMenu diaryMenu;
  private final DiaryController diaryController;
  private final AuthorMenu authorMenu;
  private final AuthorController authorController;

  public CreateDiaryEntryAction(DiaryMenu diaryMenu, DiaryController diaryController, AuthorMenu authorMenu,
      AuthorController authorController) {
    this.diaryMenu = diaryMenu;
      this.diaryController = diaryController;
      this.authorMenu = authorMenu;
    this.authorController = authorController;
  }

  @Override
  public void execute() {

    String validName = null;
    while (validName == null) {
      String userInput = authorMenu.getAuthorNamePrompt();
      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }
      try {
        authorController.
      }
    }


  }


}
