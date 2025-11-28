package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.controller.validators.AuthorValidator;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;

public class EditAuthorAction implements Action {

  private final AuthorMenu authorMenu;
  private final Author author;
  private final AuthorController authorController;


  public EditAuthorAction(AuthorMenu authorMenu, Author author, AuthorController authorController) {
    this.authorMenu = authorMenu;
    this.author = author;
    this.authorController = authorController;
  }

  @Override
  public void execute() {

    boolean choosingSubMenu = true;

    while (choosingSubMenu) {

      String subMenuUserChoice = authorMenu.subMenuForEditingAuthorAction(author);

      switch (subMenuUserChoice) {
        case "1" -> {
          authorMenu.displaySimpleMessage("Original first name: " + author.getFirstName());
          String newName = authorMenu.getUserStringWithPrompt("Enter the new first name:");

          try {
            authorController.updateAuthorFirstName(author, newName);
            authorMenu.displaySuccessMessage("First name updated successfully.");
          } catch (InvalidInputException e) {
            authorMenu.displayErrorMessage(e.getMessage());
          }
        }
        case "2" -> {
          authorMenu.displaySimpleMessage("Original last name: " + author.getLastName());
          String newName = authorMenu.getUserStringWithPrompt("Enter the new last name:");

          try {
            authorController.updateAuthorLastName(author, newName);
            authorMenu.displaySuccessMessage("Last name updated successfully.");
          } catch (InvalidInputException e) {
            authorMenu.displayErrorMessage(e.getMessage());
          }
        }
        case "3" -> {
          authorMenu.displaySimpleMessage("Original email: " + author.getEmail());
          String newEmail = authorMenu.getUserStringWithPrompt("Enter the new email: ");

          try {
            authorController.updateAuthorEmail(author, newEmail);
            authorMenu.displaySuccessMessage("Email updated successfully.");
          } catch (InvalidInputException e) {
            authorMenu.displayErrorMessage(e.getMessage());
          }
        }
        case "back" -> {
          choosingSubMenu = false;
        }
        default -> authorMenu.displayErrorMessage("Could not execute that command");
      }

    }


  }

}
