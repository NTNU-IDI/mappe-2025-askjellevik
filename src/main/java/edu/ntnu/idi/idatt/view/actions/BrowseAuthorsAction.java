package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;
import java.util.List;
import java.util.Map;

public class BrowseAuthorsAction implements Action {

  private final AuthorMenu authorMenu;
  private final DiaryMenu diaryMenu;
  private final DiaryController diaryController;
  private final AuthorController authorController;

  public BrowseAuthorsAction(AuthorMenu authorMenu, DiaryMenu diaryMenu,
      DiaryController diaryController, AuthorController authorController) {
    this.authorMenu = authorMenu;
    this.diaryMenu = diaryMenu;
    this.diaryController = diaryController;
    this.authorController = authorController;
  }

  @Override
  public void execute() {
    List<Author> authors = authorController.getListOfAuthorsInRegister();

    int page = 0;
    int pageSize = 5;

    while (true) {

      int start = page * pageSize;
      int end = Math.min(authors.size(), start + pageSize);

      Map<Author, Integer> mapOfEntriesByAuthor= diaryController.getMapOfEntriesByAuthor();

      String userInput = authorMenu.displayAuthorsForSelection(authors, start, end, page + 1, mapOfEntriesByAuthor);

      if ("back".equalsIgnoreCase(userInput)) {
        return;
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

        Author chosenAuthor = authors.get(indexOfChosenAuthor);

        boolean choosingSubMenu = true;
        while (choosingSubMenu) {
          String subMenuUserChoice = authorMenu.subMenuForAuthorManagementAction(chosenAuthor,
              diaryController.getAmountOfEntriesBySpecificAuthor(chosenAuthor));

          switch (subMenuUserChoice) {
            case "1" -> {
              List<DiaryEntry> diaries = diaryController.getListOfAllEntriesBySpecificAuthor(
                  chosenAuthor);
              Action action = new BrowseDiariesAction(diaryMenu, diaryController, diaries);
              action.execute();
            }
            case "2" -> {
              Action action = new EditAuthorAction(authorMenu, authorController, chosenAuthor);
              action.execute();
            }
            case "back" -> choosingSubMenu = false;
            default -> diaryMenu.displayErrorMessage("Could not execute that command");
          }
        }

      } catch (NumberFormatException e) {
        authorMenu.displayErrorMessage("Wrong input format");
      }
    }
  }
}





