package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;
import java.util.List;
import java.util.Map;

/**
 * Action class for browsing registered authors.
 * Allows users to view, edit, or create diary entries for selected authors.
 */
public class BrowseAuthorsAction implements Action {

  private final AuthorMenu authorMenu;
  private final DiaryMenu diaryMenu;
  private final DiaryController diaryController;
  private final AuthorController authorController;

  /**
   * Constructs a BrowseAuthorsAction with the injected dependencies.
   *
   * @param authorMenu the AuthorMenu for displaying author-related UI
   * @param diaryMenu the DiaryMenu for displaying diary-related UI
   * @param diaryController the DiaryController for managing diary entries
   * @param authorController the AuthorController for managing authors
   */
  public BrowseAuthorsAction(AuthorMenu authorMenu, DiaryMenu diaryMenu,
      DiaryController diaryController, AuthorController authorController) {
    this.authorMenu = authorMenu;
    this.diaryMenu = diaryMenu;
    this.diaryController = diaryController;
    this.authorController = authorController;
  }

  /**
   * Executes the action of browsing authors.
   * When the user selects an author, they can choose to view, edit,
   * or create diary entries for that author.
   * Authors are displayed with pagination.
   * If no authors are registered, an error message is displayed and the action exits.
   */
  @Override
  public void execute() {
    List<Author> authors = authorController.getListOfAuthorsInRegister();
    if (authors.isEmpty()) {
      diaryMenu.displayErrorMessage("No authors registered yet");
      return;
    }

    int page = 0;
    int pageSize = 5;

    while (true) {

      int start = page * pageSize;
      int end = Math.min(authors.size(), start + pageSize);

      Map<Author, Integer> mapOfEntriesByAuthor = authorController.getMapOfEntriesByAuthor();

      authorMenu.displayAuthorsForSelection(authors, start, end, page + 1,
          mapOfEntriesByAuthor);

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
        return;
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
        int indexOfChosenAuthor = Integer.parseInt(userInput) -1;

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
              Action action = new EditAuthorAction(authorMenu, chosenAuthor, authorController);
              action.execute();
            }
            case "3" -> {
              Action action = new CreateDiaryEntryAction(diaryMenu, diaryController, authorMenu,
                  authorController, chosenAuthor);
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





