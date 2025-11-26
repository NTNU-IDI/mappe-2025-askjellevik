package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.controller.validators.DiaryEntryValidator;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;

public class EditDiaryAction implements Action {

  private final DiaryMenu diaryMenu;
  private final DiaryEntry diaryEntry;
  private final DiaryController diaryController;

  public EditDiaryAction(DiaryMenu diaryMenu, DiaryEntry diaryEntry,
      DiaryController diaryController) {
    this.diaryMenu = diaryMenu;
    this.diaryEntry = diaryEntry;
    this.diaryController = diaryController;
  }

  @Override
  public void execute() {
    boolean showSubMenu = true;

    while (showSubMenu) {
      String submenuUserChoice = diaryMenu.subMenuForEditingDiaryAction();

      switch (submenuUserChoice) {
        case "1" -> editDiaryTitle(diaryEntry);
        case "2" -> overwriteDiaryText(diaryEntry);
        case "3" -> addDiaryText(diaryEntry);
        case "back" -> showSubMenu = false;
        default -> diaryMenu.displayErrorMessage("Could not execute that command");
      }

    }

  }

  private void addDiaryText(DiaryEntry diaryEntry) {
    diaryMenu.displayHeader("### Adding text ###");

    String validTextToAdd = null;

    while (validTextToAdd == null) {
      String userInput = diaryMenu.addDiaryTextPrompt();
      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }
      try {
        DiaryEntryValidator.validateText(userInput);
        validTextToAdd = userInput;

        String finalDiaryText = String.join(" ", diaryEntry.getEntryText(), validTextToAdd);
        diaryController.setDiaryText(diaryEntry, finalDiaryText);
      } catch (InvalidInputException e) {
        diaryMenu.displayErrorMessage(e.getMessage());
      }
    }

  }

  private void overwriteDiaryText(DiaryEntry diaryEntry) {
    diaryMenu.displayHeader("### Overwriting text ###");

    String validTextToSet = null;

    while (validTextToSet == null) {
      String userInput = diaryMenu.overwriteDiaryTextPrompt();
      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }
      try {
        DiaryEntryValidator.validateText(userInput);
        validTextToSet = userInput;

        diaryController.setDiaryText(diaryEntry, validTextToSet);
      } catch (InvalidInputException e) {
        diaryMenu.displayErrorMessage(e.getMessage());
      }
    }
  }

  private void editDiaryTitle(DiaryEntry diaryEntry) {
    diaryMenu.displayHeader("### Editing title ###");

    String validTitleToSet = null;

    while (validTitleToSet == null) {
      String userInput = diaryMenu.overwriteDiaryTitlePrompt(diaryEntry);
      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }
      try {
        DiaryEntryValidator.validateText(userInput);
        validTitleToSet = userInput;

        diaryController.setDiaryTitle(diaryEntry, validTitleToSet);
      } catch (InvalidInputException e) {
        diaryMenu.displayErrorMessage(e.getMessage());
      }
    }

  }
}
