package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.view.UserInterface;
import org.fusesource.jansi.Ansi.Color;

/**
 * Action class for exiting the program.
 * Displays an exit message, shuts down console utilities, and exits the application.
 */
public class ExitProgramAction implements Action {

  private final UserInterface userInterface;
  private final ConsoleUtils consoleUtils;

  /**
   * Constructs an ExitProgramAction with the injected dependencies.
   *
   * @param userInterface the UserInterface for managing program exit
   * @param consoleUtils the ConsoleUtils for console operations
   */
  public ExitProgramAction(UserInterface userInterface, ConsoleUtils consoleUtils) {
    this.userInterface = userInterface;
    this.consoleUtils = consoleUtils;
  }

  /**
   * Executes the action of exiting the program.
   * Displays an exit message in white color, shuts down console utilities,
   * and calls the user interface to exit the program.
   */
  @Override
  public void execute() {
    consoleUtils.showColoredMessage("Exiting program...", Color.WHITE);
    consoleUtils.shutDownConsoleUtils();
    userInterface.exitProgram();
  }

}
