package edu.ntnu.idi.idatt.view;


import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.model.AuthorRegister;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;
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
