package edu.ntnu.idi.idatt.view.menus;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.controller.utils.ConsoleUtils;
import edu.ntnu.idi.idatt.model.DiaryRegister;

public class DiaryMenu {

  private ConsoleUtils consoleUtils;
  private DiaryController diaryController;

  public DiaryMenu(DiaryRegister diaryRegister, ConsoleUtils consoleUtils) {
    this.diaryController = new DiaryController(diaryRegister);
    this.consoleUtils = consoleUtils;
  }



}
