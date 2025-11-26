package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.validators.AuthorValidator;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;

public class EditAuthorAction implements Action {

  private final AuthorMenu authorMenu;
  private final Author author;


  public EditAuthorAction(AuthorMenu authorMenu, Author author) {
    this.authorMenu = authorMenu;
    this.author = author;
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
          AuthorValidator.validateName(newName);
          author.setFirstName(newName);
          authorMenu.displaySuccessMessage("First name updated successfully.");
        }
        case "2" -> {
          authorMenu.displaySimpleMessage("Original last name: " + author.getLastName());
          String newName = authorMenu.getUserStringWithPrompt("Enter the new last name:");
          AuthorValidator.validateName(newName);
          author.setLastName(newName);
          authorMenu.displaySuccessMessage("Last name updated successfully.");
        }
        case "3" -> {
          authorMenu.displaySimpleMessage("Original email: " + author.getEmail());
          String newEmail = authorMenu.getUserStringWithPrompt("Enter the new email: ");
          AuthorValidator.validateEmail(newEmail);
          author.setEmail(newEmail);
          authorMenu.displaySuccessMessage("Email updated successfully.");
        }
        case "back" -> {
          choosingSubMenu = false;
        }
        default -> authorMenu.displayErrorMessage("Could not execute that command");
      }

    }


  }

}
