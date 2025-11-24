package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;


public class CreateAuthorAction implements Action {

  private final AuthorMenu authorMenu;
  private final AuthorController authorController;

  public CreateAuthorAction(AuthorMenu authorMenu, AuthorController authorController) {
    this.authorMenu = authorMenu;
    this.authorController = authorController;
  }

  @Override
  public void execute() {

    String userInput;
    String validName = null;
    while (validName == null) {
      userInput = authorMenu.getAuthorNamePrompt();
      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }
      try {
        authorController.validateAuthorName(userInput);
        validName = userInput;
      } catch (InvalidInputException e) {
        authorMenu.displayErrorMessage(e.getMessage());
      }

      String validEmail = null;
      while (validEmail == null) {
        userInput = authorMenu.getAuthorEmailPrompt();
        if ("back".equalsIgnoreCase(userInput)) {
          return;
        }
        try {
          authorController.validateAuthorName(userInput);
          validEmail = userInput;
        } catch (InvalidInputException e) {
          authorMenu.displayErrorMessage(e.getMessage());
        }

        try {
          authorController.addAuthorToRegister(validName, validEmail);
        }
        catch (Exception e) {
          authorMenu.displayErrorMessage("Unexpected error: " + e.getMessage());
        }
        }





    }


  }


}
