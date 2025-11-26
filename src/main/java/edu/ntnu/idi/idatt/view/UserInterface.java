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
import edu.ntnu.idi.idatt.view.menus.BaseMenu;
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
  private DiaryMenu diaryMenu;
  private AuthorMenu authorMenu;
  private boolean running;
  private Map<String, Action> mainMenuMap;


  public UserInterface() {
    consoleUtils = new ConsoleUtils();
    running = true;
  }


  public void init() {

    DiaryRegister diaryRegister = new DiaryRegister();
    AuthorRegister authorRegister = new AuthorRegister();

    DiaryController diaryController = new DiaryController(diaryRegister);
    AuthorController authorController = new AuthorController(authorRegister);

    this.authorMenu = new AuthorMenu(consoleUtils);
    this.diaryMenu = new DiaryMenu(consoleUtils);

    boolean validInput = false;
    while (!validInput) {
      String loadData = consoleUtils.readUserInputWithPrompt("\nLoad test data now? (y/n)");
      if ("y".equalsIgnoreCase(loadData)) {
        TestDataLoader loader = new TestDataLoader();
        loader.loadTestData(diaryController, authorController);
        consoleUtils.showMessage("Loaded data");
        validInput = true;
      }
      if ("n".equalsIgnoreCase(loadData)) {
        validInput = true;
      }
      consoleUtils.showMessage("Not valid input, enter (yes/no)");
    }

    this.mainMenuMap = new HashMap<>();

    mainMenuMap.put("1", new BrowseDiariesAction(diaryMenu, diaryController));
    mainMenuMap.put("2", new CreateDiaryEntryAction(diaryMenu, diaryController, authorMenu, authorController));
    mainMenuMap.put("3", new FindSpecificDiaryAction(diaryMenu, diaryController));
    mainMenuMap.put("4", new BrowseAuthorsAction(authorMenu, diaryMenu, diaryController, authorController));
    mainMenuMap.put("5", new ExitProgramAction(this, consoleUtils));


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
    consoleUtils.showMessage("5. Exit program");

    String userInput = consoleUtils.readUserInput();

    Action action = mainMenuMap.get(userInput);

    if (action != null) {
      action.execute();
    } else {
      consoleUtils.showColoredMessage("Invalid menu option, try again", Color.RED);
    }

  }


  public void exitProgram() {
    running = false;
  }

}
