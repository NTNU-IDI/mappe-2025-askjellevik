package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;


public class CreateAuthorAction implements Action {

  private final AuthorMenu authorMenu;
  private final AuthorController authorController;
  private Author createdAuthor;

  public CreateAuthorAction(AuthorMenu authorMenu, AuthorController authorController) {
    this.authorMenu = authorMenu;
    this.authorController = authorController;
  }

  @Override
  public void execute() {

    authorMenu.displayMainHeader("Creating new author");

    String userInput;
    String validFirstName = null;

    while (validFirstName == null) {
      userInput = authorMenu.getUserStringWithPrompt("Enter the first name of the author");
      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }
      try {
        authorController.validateAuthorName(userInput);
        validFirstName = userInput;
      } catch (InvalidInputException e) {
        authorMenu.displayErrorMessage(e.getMessage());
      }

    }

    String validLastName = null;
    while (validLastName == null) {
      userInput = authorMenu.getUserStringWithPrompt("Enter the last name of the author");
      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }
      try {
        authorController.validateAuthorName(userInput);
        validLastName = userInput;
      } catch (InvalidInputException e) {
        authorMenu.displayErrorMessage(e.getMessage());
      }

    }

    String validEmail = null;

    while (validEmail == null) {
      userInput = authorMenu.getUserStringWithPrompt("Enter the email of the author");
      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }
      try {
        authorController.validateAuthorEmail(userInput);
        validEmail = userInput;
      } catch (InvalidInputException e) {
        authorMenu.displayErrorMessage(e.getMessage());
      }
    }

    try {
      this.createdAuthor = authorController.addAuthorToRegister(validFirstName, validLastName, validEmail);
    } catch (Exception e) {
      authorMenu.displayErrorMessage("Unexpected error: " + e.getMessage());
    }


  }

  public Author getCreatedAuthor() {
    return this.createdAuthor;
  }


}
