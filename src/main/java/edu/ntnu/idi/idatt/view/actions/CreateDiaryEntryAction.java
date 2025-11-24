package edu.ntnu.idi.idatt.view.actions;

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

  public CreateDiaryEntryAction(DiaryMenu diaryMenu, DiaryController diaryController, AuthorMenu authorMenu) {
    this.diaryMenu = diaryMenu;
      this.diaryController = diaryController;
      this.authorMenu = authorMenu;
  }

  @Override
  public void execute() {
    LocalDateTime entryDate = diaryMenu.getDateOfNewDiaryEntry();
    if (entryDate == null) {
      return;
    }
    try {

    } catch () {

    }

    Author author = authorMenu.getAuthorOfNewDiaryEntry();
    if (author == null) {
      return;
    }
    consoleUtils.showMessage(author + " is now chosen");
    String entryTitle = consoleUtils.readUserInputWithPrompt("Enter the diary entry title: ");
    String entryText = consoleUtils.readUserInputWithPrompt("Enter the text of the diary entry");

    DiaryEntry diaryEntry = new DiaryEntry(entryDate, author, entryTitle, entryText);
    diaryController.addDiaryToRegister(diaryEntry);

  }


}
