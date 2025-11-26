package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.validators.AuthorValidator;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;

public class EditAuthorAction implements Action {

  private final AuthorMenu authorMenu;
  private final AuthorController authorController;
  private final Author author;


  public EditAuthorAction(AuthorMenu authorMenu, AuthorController authorController, Author author) {
    this.authorMenu = authorMenu;
    this.authorController = authorController;
    this.author = author;
  }

  @Override
  public void execute() {

    boolean choosingSubMenu = true;

    while (choosingSubMenu) {

      String subMenuUserChoice = authorMenu.subMenuForEditingAuthorAction(author);

      switch (subMenuUserChoice) {
        case "1" -> {
          authorMenu.displaySimpleMessage("Original email: " + author.getEmail());
          String newEmail = authorMenu.getUserStringWithPrompt("Enter the new email: ");
          AuthorValidator.validateEmail(newEmail);
          authorController.setNewEmail(author, newEmail);
        }
        case "2" -> {
          authorMenu.displaySimpleMessage("Original name: " + author.getName());
          String newName = authorMenu.getUserStringWithPrompt("Enter the new name:");
          AuthorValidator.validateName(newName);
          authorController.setNewName(author, newName);
        }
        case "back" -> {
          choosingSubMenu = false;
        }
        default -> authorMenu.displayErrorMessage("Could not execute that command");
      }

    }


  }

}
