package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;

public class BrowseAuthorsAction implements Action{

  private final AuthorMenu authorMenu;

  public BrowseAuthorsAction(AuthorMenu authorMenu) {
    this.authorMenu = authorMenu;
  }

  @Override
  public void execute() {
    while (true) {

      Author author = authorMenu.chooseRegisteredAuthors();

      if (author == null) {
        return;
      }

      while (true) {
        String submenuUserChoice = authorMenu.subMenuForAuthorManagementAction(author);


        switch (submenuUserChoice) {
          case "1" -> authorMenu.findAuthorsEntries(author);
          case "2" -> authorMenu.editAuthor(author);
          case "3" -> {
          }
        }

      }
    }
  }

}





