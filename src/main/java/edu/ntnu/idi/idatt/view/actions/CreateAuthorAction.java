package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;


/**
 * Action class for creating a new author.
 * Prompts the user for author details and validates the input.
 */
public class CreateAuthorAction implements Action {

  private final AuthorMenu authorMenu;
  private final AuthorController authorController;
  private Author createdAuthor;

  /**
   * Constructs a CreateAuthorAction with the injected dependencies.
   *
   * @param authorMenu the AuthorMenu for displaying author-related UI
   * @param authorController the AuthorController for managing authors
   */
  public CreateAuthorAction(AuthorMenu authorMenu, AuthorController authorController) {
    this.authorMenu = authorMenu;
    this.authorController = authorController;
  }

  /**
   * Executes the action of creating a new author.
   * Prompts the user for the author's first name, last name, and email,
   * validating each input instantaneous. If the user inputs "back" at any prompt, the action exits.
   */
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
      this.createdAuthor = authorController.addAuthorToRegister(validFirstName, validLastName,
          validEmail);
    } catch (Exception e) {
      authorMenu.displayErrorMessage("Unexpected error: " + e.getMessage());
    }


  }

  /**
   * Gets the created author after execution.
   * Useful for retrieving the newly created author object and performing further actions.
   *
   *
   * @return the created Author object
   */
  public Author getCreatedAuthor() {
    return this.createdAuthor;
  }


}
