package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidDateException;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Action class for creating a new diary entry.
 * Prompts the user for diary entry details and validates the input.
 */
public class CreateDiaryEntryAction implements Action {

  private final DiaryMenu diaryMenu;
  private final DiaryController diaryController;
  private final AuthorMenu authorMenu;
  private final AuthorController authorController;
  private final Author authorOfDiaryEntry;

  /**
   * Constructs a CreateDiaryEntryAction with the injected dependencies.
   * This constructor is used when the author of the diary entry is not pre-defined,
   * and an author needs to be selected or created during the process.
   *
   * @param diaryMenu the DiaryMenu for displaying diary-related UI
   * @param diaryController the DiaryController for managing diary entries
   * @param authorMenu the AuthorMenu for displaying author-related UI
   * @param authorController the AuthorController for managing authors
   */
  public CreateDiaryEntryAction(DiaryMenu diaryMenu, DiaryController diaryController,
      AuthorMenu authorMenu,
      AuthorController authorController) {
    this.diaryMenu = diaryMenu;
    this.diaryController = diaryController;
    this.authorMenu = authorMenu;
    this.authorController = authorController;
    this.authorOfDiaryEntry = null;
  }

  /**
   * Constructs a CreateDiaryEntryAction with the injected dependencies and a predefined author.
   * This constructor is used when the author of the diary entry is already known.
   *
   * @param diaryMenu the DiaryMenu for displaying diary-related UI
   * @param diaryController the DiaryController for managing diary entries
   * @param authorMenu the AuthorMenu for displaying author-related UI
   * @param authorController the AuthorController for managing authors
   * @param authorOfDiaryEntry the predefined author of the diary entry
   */
  public CreateDiaryEntryAction(DiaryMenu diaryMenu, DiaryController diaryController,
      AuthorMenu authorMenu,
      AuthorController authorController, Author authorOfDiaryEntry) {
    this.diaryMenu = diaryMenu;
    this.diaryController = diaryController;
    this.authorMenu = authorMenu;
    this.authorController = authorController;
    this.authorOfDiaryEntry = authorOfDiaryEntry;
  }

  /**
   * Executes the action of creating a new diary entry.
   * Prompts the user for the diary entry's date, author, title, and text,
   * validating each input instantaneous. If the user inputs "back" at any prompt, the action exits.
   * If the validation from the controller fails, an error message is
   * displayed and the user is re-prompted.
   */
  @Override
  public void execute() {


    diaryMenu.displayMainHeader("Creating new diary entry");

    LocalDateTime validDate = getValidDate();
    if (validDate == null) {
      return;
    }

    Author validAuthor = null;
    if (authorOfDiaryEntry == null) {
      boolean choosingSubMenu = true;
      while (choosingSubMenu) {
        String subMenuUserChoice = authorMenu.subMenuForChoosingAuthor();
        switch (subMenuUserChoice) {
          case "1" -> {
            CreateAuthorAction action  = new CreateAuthorAction(authorMenu, authorController);
            action.execute();
            validAuthor = action.getCreatedAuthor();
            choosingSubMenu = false;
          }
          case "2" -> {
            validAuthor = getValidAuthorFromBrowsing();
            choosingSubMenu = false;
          }
          case "cancel" -> choosingSubMenu = false;
          default -> diaryMenu.displayErrorMessage("Could not execute that command");
        }
      }

      if (validAuthor == null) {
        return;
      }
    } else {
      validAuthor = authorOfDiaryEntry;
    }

    String validTitle = getValidTitle();
    if (validTitle == null) {
      return;
    }

    String validText = getValidText();
    if (validText == null) {
      return;
    }

    try {
      diaryController.addDiaryToRegister(validDate, validAuthor, validTitle, validText);
    } catch (Exception e) {
      authorMenu.displayErrorMessage("Unexpected error: " + e.getMessage());
    }


  }

  /**
   * Prompts the user for diary text and validates the input.
   * The method uses a loop to prompt until valid text is provided or the user chooses to go back.
   * If the user inputs "back", the method returns null.
   *
   * @return the valid diary text, or null if the user chose to go back
   */
  private String getValidText() {
    String validText = null;
    String userInput;
    while (validText == null) {
      userInput = diaryMenu.getUserStringWithPrompt("Cancel or Enter the diary text");
      if ("cancel".equalsIgnoreCase(userInput)) {
        return null;
      }
      try {
        diaryController.validateEntryText(userInput);
        validText = userInput;
      } catch (InvalidInputException e) {
        diaryMenu.displayErrorMessage(e.getMessage());
      }
    }
    return validText;
  }

  /**
   * Prompts the user for diary title and validates the input.
   * The method uses a loop to prompt until a valid title is
   * provided or the user chooses to go back.
   * If the user inputs "back", the method returns null.
   *
   * @return the valid diary title, or null if the user chose to go back
   */
  private String getValidTitle() {
    String validTitle = null;
    String userInput;

    while (validTitle == null) {
      userInput = diaryMenu.getUserStringWithPrompt("Cancel or Enter the diary title");
      if ("cancel".equalsIgnoreCase(userInput)) {
        return null;
      }
      try {
        diaryController.validateEntryTitle(userInput);
        validTitle = userInput;
      } catch (InvalidInputException e) {
        diaryMenu.displayErrorMessage(e.getMessage());
      }
    }
    return validTitle;
  }

  /**
   * Prompts the user for a diary entry date and validates the input.
   * The method uses a loop to prompt until a valid date is provided or the user chooses to go back.
   * If the user inputs "back", the method returns null.
   *
   * @return the valid diary entry date, or null if the user chose to go back
   */
  private LocalDateTime getValidDate() {
    LocalDateTime userInputDate;
    LocalDateTime validDate = null;

    while (validDate == null) {
      userInputDate = diaryMenu.getDateOfNewDiaryEntry();
      if (userInputDate == null) {
        return null;
      }

      try {
        diaryController.validateDate(userInputDate);
        validDate = userInputDate;
      } catch (InvalidDateException e) {
        diaryMenu.displayErrorMessage(e.getMessage());
      }
    }
    return validDate;

  }

  /**
   * Prompts the user to browse and select a valid author from the list of registered authors.
   * Authors are displayed with pagination. The user can navigate through pages,
   * select an author by their key, or go back to exit the selection.
   * If no authors are registered, an error message is displayed and the method returns null.
   *
   * @return the valid selected Author,
   *     or null if the user chose to go back or if no authors are registered
   */
  private Author getValidAuthorFromBrowsing() {

    List<Author> authors = authorController.getListOfAuthorsInRegister();
    if (authors.isEmpty()) {
      diaryMenu.displayErrorMessage("No authors registered yet");
      return null;
    }
    Author validAuthor = null;

    int page = 0;
    int pageSize = 5;

    while (validAuthor == null) {

      int start = page * pageSize;
      int end = Math.min(authors.size(), start + pageSize);

      Map<Author, Integer> mapOfEntriesByAuthor = authorController.getMapOfEntriesByAuthor();


      authorMenu.displaySecondaryHeader("Choose an author");
      authorMenu.displayAuthorsForSelection(authors, start, end, page + 1, mapOfEntriesByAuthor);

      String promptMessage = "Commands: (KEY NUMBER)";
      if (end < authors.size()) {
        promptMessage += ", NEXTPAGE";
      }
      if (page > 0) {
        promptMessage += ", PREVPAGE";
      }
      promptMessage += ", BACK";
      String userInput = diaryMenu.getUserStringWithPrompt(promptMessage);

      if ("back".equalsIgnoreCase(userInput)) {
        return null;
      }
      if ("nextpage".equalsIgnoreCase(userInput) && end < authors.size()) {
        page += 1;
        continue;
      }
      if ("prevpage".equalsIgnoreCase(userInput) && page > 0) {
        page -= 1;
        continue;
      }

      try {
        int indexOfChosenAuthor = Integer.parseInt(userInput) - 1;

        if (indexOfChosenAuthor < start || indexOfChosenAuthor >= end) {
          authorMenu.displayErrorMessage("The author ID was out of range");
          continue;
        }

        validAuthor = authors.get(indexOfChosenAuthor);

      } catch (NumberFormatException e) {
        authorMenu.displayErrorMessage("Wrong input format");
      }

    }
    return validAuthor;
  }
}
