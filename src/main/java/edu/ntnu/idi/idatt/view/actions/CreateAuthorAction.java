package edu.ntnu.idi.idatt.view.actions;

import com.sun.tools.jconsole.JConsoleContext;
import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;

import java.time.LocalDateTime;

public class CreateDiaryEntryAction implements Action {

  private final AuthorMenu authorMenu;
  private final AuthorController authorController;

  public CreateDiaryEntryAction(AuthorMenu authorMenu, AuthorController authorController) {
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
        authorController.validateAuthorName(userInput);
        validName = userInput;
      } catch (InvalidInputException e) {
        authorMenu.displayErrorMessage(e.getMessage());
      }
    }


  }


}
