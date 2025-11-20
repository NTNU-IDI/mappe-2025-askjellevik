package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.view.UserInterface;
import org.fusesource.jansi.Ansi.Color;

public class ExitProgramAction implements Action {

  private final UserInterface userInterface;
  private final ConsoleUtils consoleUtils;

  public ExitProgramAction(UserInterface userInterface, ConsoleUtils consoleUtils) {
    this.userInterface = userInterface;
    this.consoleUtils = consoleUtils;
  }

  @Override
  public void execute() {
    consoleUtils.showColoredMessage("Exiting program...", Color.WHITE);
    consoleUtils.shutDownConsoleUtils();
    userInterface.exitProgram();
  }

}
