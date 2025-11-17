package edu.ntnu.idi.idatt.view;


import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.AuthorRegister;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;
import java.time.LocalDateTime;
import org.fusesource.jansi.Ansi.Color;


/**
 * Class that handles user interference by initializing controllers, displaying menus
 * and running the main interface loop.
 *
 * @author andskj
 * @version 1.0
 * @since v0.2.1
 */
public class UserInterface {

  private final ConsoleUtils consoleUtils;
  private DiaryMenu diaryMenu;
  private AuthorMenu authorMenu;
  private boolean running;


  public UserInterface() {
    consoleUtils = new ConsoleUtils();
    running = true;
  }


  public void init() {

    DiaryRegister diaryRegister = new DiaryRegister();
    AuthorRegister authorRegister = new AuthorRegister();

    LocalDateTime dateTime1 = LocalDateTime.of(2025, 4,9,17,0 );
    LocalDateTime dateTime2 = LocalDateTime.of(2025, 4,10,18,0 );
    LocalDateTime dateTime3 = LocalDateTime.of(2025, 4,6,18,0 );
    LocalDateTime dateTime4 = LocalDateTime.of(2025, 1,13,18,0 );
    LocalDateTime dateTime5 = LocalDateTime.of(2025, 3,25,18,0 );
    LocalDateTime dateTime6 = LocalDateTime.of(2025, 6,13,18,0 );



    Author author1 = new Author("Andy Skjell", "and@mail.com");
    Author author2 = new Author("Martn Basj", "mart@mail.com");

    DiaryEntry diary1 = new DiaryEntry(dateTime1, author1, "test", "tesasdas");
    DiaryEntry diary2 = new DiaryEntry(dateTime2, author2, "test", "tesasdas");
    DiaryEntry diary3 = new DiaryEntry(dateTime3, author1, "test", "tesasdas");
    DiaryEntry diary4 = new DiaryEntry(dateTime4, author2, "test", "tesasdas");
    DiaryEntry diary5 = new DiaryEntry(dateTime5, author1, "test", "tesasdas");
    DiaryEntry diary6 = new DiaryEntry(dateTime6, author2, "test", "tesasdas");

    authorRegister.addAuthor(author1);
    authorRegister.addAuthor(author2);

    diaryRegister.addDiaryEntry(diary1);
    diaryRegister.addDiaryEntry(diary2);
    diaryRegister.addDiaryEntry(diary3);
    diaryRegister.addDiaryEntry(diary4);
    diaryRegister.addDiaryEntry(diary5);
    diaryRegister.addDiaryEntry(diary6);


    this.authorMenu = new AuthorMenu(authorRegister, consoleUtils);
    this.diaryMenu = new DiaryMenu(diaryRegister, consoleUtils, authorMenu);


  }

  public void start() {

    while (running) {
      showMainMenu();


    }

  }

  public void showMainMenu() {
    consoleUtils.showColoredMessage("==== Main Menu ====", Color.GREEN);
    consoleUtils.showMessage("1. Browse the registered diary entries");
    consoleUtils.showMessage("2. Create a new diary entry");
    consoleUtils.showMessage("3. Find a specific diary entry");
    consoleUtils.showMessage("4. Browse and manage the registered authors");
    consoleUtils.showMessage("5. Exit");

    String userInput = consoleUtils.readUserInput();

    switch (userInput) {
      case "1" -> diaryMenu.browseAndManageRegisteredDiaries();
      case "2" -> diaryMenu.createNewDiaryEntry();
      case "3" -> diaryMenu.findSpecificDiaryEntry();
      case "4" -> authorMenu.browseAndManageRegisteredAuthors();
      case "5" -> exitProgram();
      default -> consoleUtils.showColoredMessage("Invalid choice, please try again.", Color.RED);


    }

  }


  private void exitProgram() {
    consoleUtils.showColoredMessage("Exiting program...", Color.WHITE);
    consoleUtils.shutDownConsoleUtils();
    running = false;
  }

}
