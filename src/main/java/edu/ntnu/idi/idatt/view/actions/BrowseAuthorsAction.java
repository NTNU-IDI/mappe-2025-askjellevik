package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;

import java.util.List;

public class BrowseAuthorsAction implements Action {

    private final AuthorMenu authorMenu;
    private final DiaryMenu diaryMenu;
    private final DiaryController diaryController;

    public BrowseAuthorsAction(AuthorMenu authorMenu, DiaryMenu diaryMenu, DiaryController diaryController) {
        this.authorMenu = authorMenu;
        this.diaryMenu = diaryMenu;
        this.diaryController = diaryController;
    }

    @Override
    public void execute() {
        List<Author> authors = authorMenu.getListOfAllRegisteredAuthors();

        int page = 0;
        int pageSize = 5;

        while (true) {

            int start = page * pageSize;
            int end = Math.min(authors.size(), start + pageSize);


            String userInput = authorMenu.displayEntriesForSelection(authors, start, end, page);


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

                if (indexOfChosenAuthor < start || indexOfChosenAuthor > end) {
                    authorMenu.displayPromptedMessage("The author ID was out of range");
                }

                Author chosenAuthor = authors.get(indexOfChosenAuthor);

                boolean choosingSubMenu = true;
                while (choosingSubMenu) {
                    String subMenuUserChoice = authorMenu.subMenuForAuthorManagementAction(chosenAuthor);

                    switch (subMenuUserChoice) {
                        case "1" -> {
                            List<DiaryEntry> diaries = diaryController.getListOfAllEntriesBySpecificAuthor(chosenAuthor);
                            Action action = new BrowseDiariesAction(diaryMenu,diaries);
                            action.execute();
                        }
                        case "2" -> authorMenu.editAuthor(chosenAuthor);
                        case "back" -> {
                            choosingSubMenu = false;
                        }
                    }
                }

            } catch (NumberFormatException e) {
                authorMenu.displayPromptedMessage("Wrong input format");
            }
        }
    }
}





