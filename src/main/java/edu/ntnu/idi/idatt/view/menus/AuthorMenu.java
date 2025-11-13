package edu.ntnu.idi.idatt.view.menus;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.model.AuthorRegister;

public class AuthorMenu {

  private ConsoleUtils consoleUtils;
  private AuthorController authorController;

  public AuthorMenu(AuthorRegister authorRegister, ConsoleUtils consoleUtils) {
    this.authorController = new AuthorController(authorRegister);
    this.consoleUtils = consoleUtils;
  }



}
