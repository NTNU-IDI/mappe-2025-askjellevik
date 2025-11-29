package edu.ntnu.idi.idatt.view;


import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.controller.utils.TestDataLoader;
import edu.ntnu.idi.idatt.model.AuthorRegister;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import edu.ntnu.idi.idatt.view.actions.Action;
import edu.ntnu.idi.idatt.view.actions.BrowseAuthorsAction;
import edu.ntnu.idi.idatt.view.actions.BrowseDiariesAction;
import edu.ntnu.idi.idatt.view.actions.CreateDiaryEntryAction;
import edu.ntnu.idi.idatt.view.actions.ExitProgramAction;
import edu.ntnu.idi.idatt.view.actions.FindSpecificDiaryAction;
import edu.ntnu.idi.idatt.view.menus.AuthorMenu;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;
import java.util.HashMap;
import java.util.Map;
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
  private boolean running;
  private Map<String, Action> mainMenuMap;
  private String errorMessage;


  /**
   * Constructs the UserInterface and initializes the console utilities and setting running to true.
   *
   */
  public UserInterface() {
    consoleUtils = new ConsoleUtils();
    running = true;
    errorMessage = null;
  }


  /**
   * Initializes the user interface by creating the registers, controllers, and menus.
   * The user is also given a choice to load test data into the application.
   * Main menu user input options are mapped to their corresponding actions.
   * */
  public void init() {

    DiaryRegister diaryRegister = new DiaryRegister();
    AuthorRegister authorRegister = new AuthorRegister();

    DiaryController diaryController = new DiaryController(diaryRegister);
    AuthorController authorController = new AuthorController(authorRegister, diaryController);

    AuthorMenu authorMenu = new AuthorMenu(consoleUtils);
    DiaryMenu diaryMenu = new DiaryMenu(consoleUtils);

    consoleUtils.clearScreen();
    boolean validInput = false;
    while (!validInput) {
      String loadData = consoleUtils.readUserInputWithPrompt("\nLoad test data now? (y/n)");
      if ("y".equalsIgnoreCase(loadData)) {
        TestDataLoader loader = new TestDataLoader();
        loader.loadTestData(diaryController, authorController);
        consoleUtils.showMessage("Loaded data");
        validInput = true;
      } else if ("n".equalsIgnoreCase(loadData)) {
        validInput = true;
      } else {
        consoleUtils.showMessage("Not valid input, enter (y for yes , n for no)");
      }
    }

    this.mainMenuMap = new HashMap<>();

    mainMenuMap.put("1", new BrowseDiariesAction(diaryMenu, diaryController));
    mainMenuMap.put("2",
        new CreateDiaryEntryAction(diaryMenu, diaryController, authorMenu, authorController));
    mainMenuMap.put("3", new FindSpecificDiaryAction(diaryMenu, diaryController, authorController));
    mainMenuMap.put("4",
        new BrowseAuthorsAction(authorMenu, diaryMenu, diaryController, authorController));
    mainMenuMap.put("5", new ExitProgramAction(this, consoleUtils));


  }

  /**
   * Starts the main loop of the user interface by showing main menu.
   * */
  public void start() {

    while (running) {
      showMainMenu();

    }

  }

  /**
   * Displays the main menu and collect user input to execute the corresponding menu action.
   * */
  public void showMainMenu() {
    consoleUtils.clearScreen();
    consoleUtils.showColoredMessage("======= Main Menu =======", Color.GREEN);
    consoleUtils.showMessage("1. Browse the registered diary entries");
    consoleUtils.showMessage("2. Create a new diary entry");
    consoleUtils.showMessage("3. Find a specific diary entry");
    consoleUtils.showMessage("4. Browse and manage the registered authors");
    consoleUtils.showMessage("-  -  -  -  -  -  -  -  -  -  -  -  -  -  -");
    consoleUtils.showMessage("5. Exit program");
    consoleUtils.showMessage("");

    if (errorMessage != null) {
      consoleUtils.showColoredMessage(errorMessage, Color.RED);
      errorMessage = null;
    }

    String userInput = consoleUtils.readUserInput();

    Action action = mainMenuMap.get(userInput);

    if (action != null) {
      action.execute();
    } else {
      errorMessage = "Invalid menu option, try again";
    }

  }


  /**
   * Exits the program by setting running to false.
   * */
  public void exitProgram() {
    running = false;
  }

}
