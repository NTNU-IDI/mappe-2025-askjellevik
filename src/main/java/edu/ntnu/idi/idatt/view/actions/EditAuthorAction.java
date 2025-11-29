package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.exceptions.AuthorAlreadyExistsException;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;

/**
 * Action class for editing an existing author's details.
 * Allows users to update the author's first name, last name, and email.
 */
public class EditAuthorAction implements Action {

  private final AuthorMenu authorMenu;
  private final Author author;
  private final AuthorController authorController;


  /**
   * Constructs an EditAuthorAction with the injected dependencies.
   */
  public EditAuthorAction(AuthorMenu authorMenu, Author author, AuthorController authorController) {
    this.authorMenu = authorMenu;
    this.author = author;
    this.authorController = authorController;
  }

  /**
   * Executes the action of editing an author's details.
   * Presents a submenu for editing the author's first name, last name, or email.
   * Validates each input and updates the author's information accordingly.
   * Users can exit the submenu by selecting "back".
   * If the validation from the controller fails, an error message is displayed
   * and the user is prompted again.
   */
  @Override
  public void execute() {

    boolean choosingSubMenu = true;

    while (choosingSubMenu) {
      authorMenu.displaySecondaryHeader("Editing author");
      String subMenuUserChoice = authorMenu.subMenuForEditingAuthorAction(author);

      switch (subMenuUserChoice) {
        case "1" -> {
          authorMenu.displaySimpleMessage("Original first name: " + author.getFirstName());
          String newName = authorMenu.getUserStringWithPrompt("Enter the new first name");

          try {
            authorController.updateAuthorFirstName(author, newName);
            authorMenu.displaySuccessMessage("First name updated successfully.");
          } catch (InvalidInputException e) {
            authorMenu.displayErrorMessage(e.getMessage());
          }
        }
        case "2" -> {
          authorMenu.displaySimpleMessage("Original last name: " + author.getLastName());
          String newName = authorMenu.getUserStringWithPrompt("Enter the new last name");

          try {
            authorController.updateAuthorLastName(author, newName);
            authorMenu.displaySuccessMessage("Last name updated successfully.");
          } catch (InvalidInputException e) {
            authorMenu.displayErrorMessage(e.getMessage());
          }
        }
        case "3" -> {
          authorMenu.displaySimpleMessage("Original email: " + author.getEmail());
          String newEmail = authorMenu.getUserStringWithPrompt("Enter the new email");

          try {
            authorController.updateAuthorEmail(author, newEmail);
            authorMenu.displaySuccessMessage("Email updated successfully.");
          } catch (InvalidInputException | AuthorAlreadyExistsException e) {
            authorMenu.displayErrorMessage(e.getMessage());
          }
        }
        case "back" -> choosingSubMenu = false;
        default -> authorMenu.displayErrorMessage("Could not execute that command");
      }

    }


  }

}
