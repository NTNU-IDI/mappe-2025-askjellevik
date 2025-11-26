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
    diaryMenu.displayThirdHeader("Adding text to entry");

    String validTextToAdd = null;

    while (validTextToAdd == null) {
      String userInput = diaryMenu.getUserStringWithPrompt("Enter the text to add: ");
      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }
      try {
        DiaryEntryValidator.validateText(userInput);
        validTextToAdd = userInput;

        String finalDiaryText = String.join(" ", diaryEntry.getEntryText(), validTextToAdd);
        diaryEntry.setEntryText(finalDiaryText);
      } catch (InvalidInputException e) {
        diaryMenu.displayErrorMessage(e.getMessage());
      }
    }

  }

  private void overwriteDiaryText(DiaryEntry diaryEntry) {
    diaryMenu.displayThirdHeader("Overwriting text");

    String validTextToSet = null;

    while (validTextToSet == null) {
      String userInput = diaryMenu.getUserStringWithPrompt("Enter new diary text:");
      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }
      try {
        DiaryEntryValidator.validateText(userInput);
        validTextToSet = userInput;

        diaryEntry.setEntryText(validTextToSet);
      } catch (InvalidInputException e) {
        diaryMenu.displayErrorMessage(e.getMessage());
      }
    }
  }

  private void editDiaryTitle(DiaryEntry diaryEntry) {
    diaryMenu.displayThirdHeader("Editing title");

    String validTitleToSet = null;

    while (validTitleToSet == null) {
      diaryMenu.displaySimpleMessage("Original title: " + diaryEntry.getEntryTitle());
      String userInput = diaryMenu.getUserStringWithPrompt("Enter new title");
      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }
      try {
        DiaryEntryValidator.validateText(userInput);
        validTitleToSet = userInput;

        diaryEntry.setEntryTitle(validTitleToSet);
      } catch (InvalidInputException e) {
        diaryMenu.displayErrorMessage(e.getMessage());
      }
    }

  }
}
