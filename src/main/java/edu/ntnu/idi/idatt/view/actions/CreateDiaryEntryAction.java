package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CreateDiaryEntryAction implements Action {

  private final DiaryMenu diaryMenu;
  private final DiaryController diaryController;
  private final AuthorMenu authorMenu;
  private final AuthorController authorController;

  public CreateDiaryEntryAction(DiaryMenu diaryMenu, DiaryController diaryController,
      AuthorMenu authorMenu,
      AuthorController authorController) {
    this.diaryMenu = diaryMenu;
    this.diaryController = diaryController;
    this.authorMenu = authorMenu;
    this.authorController = authorController;
  }

  @Override
  public void execute() {

    diaryMenu.displayMainHeader("Creating new diary entry");

    LocalDateTime validDate = getValidDate();
    if (validDate == null) {
      return;
    }


    Author validAuthor = null;
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
        case "back" -> choosingSubMenu = false;
        default -> diaryMenu.displayErrorMessage("Could not execute that command");
      }
    }
    if (validAuthor == null) {
      return;
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

  private String getValidText() {
    String validText = null;
    String userInput;
    while (validText == null) {
      userInput = diaryMenu.getUserStringWithPrompt("Enter the diary text");
      if ("back".equalsIgnoreCase(userInput)) {
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

  private String getValidTitle() {
    String validTitle = null;
    String userInput;

    while (validTitle == null) {
      userInput = diaryMenu.getUserStringWithPrompt("Enter the diary title");
      if ("back".equalsIgnoreCase(userInput)) {
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
      } catch (InvalidInputException e) {
        authorMenu.displayErrorMessage(e.getMessage());
      }
    }
    return validDate;

  }

  private Author getValidAuthorFromBrowsing() {

    List<Author> authors = authorController.getListOfAuthorsInRegister();
    Author validAuthor = null;

    int page = 0;
    int pageSize = 5;

    while (validAuthor == null) {

      int start = page * pageSize;
      int end = Math.min(authors.size(), start + pageSize);

      Map<Author, Integer> mapOfEntriesByAuthor= diaryController.getMapOfEntriesByAuthor();


      String userInput = authorMenu.displayAuthorsForSelection(authors, start, end, page+1, mapOfEntriesByAuthor);

      if ("back".equalsIgnoreCase(userInput)) {
        return null;
      }
      if ("next".equalsIgnoreCase(userInput) && end < authors.size()) {
        page += 1;
        continue;
      }
      if ("previous".equalsIgnoreCase(userInput) && page > 0) {
        page -= 1;
        continue;
      }

      try {
        int indexOfChosenAuthor = Integer.parseInt(userInput);

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
