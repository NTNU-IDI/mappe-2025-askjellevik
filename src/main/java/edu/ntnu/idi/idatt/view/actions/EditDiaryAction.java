package edu.ntnu.idi.idatt.view.actions;

import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.view.menus.DiaryMenu;

/**
 * Action class for editing an existing diary entry.
 * Allows users to modify the title and text of the diary entry.
 */
public class EditDiaryAction implements Action {

  private final DiaryMenu diaryMenu;
  private final DiaryEntry diaryEntry;
  private final DiaryController diaryController;

  /**
   * Constructs an EditDiaryAction with the injected dependencies.
   */
  public EditDiaryAction(DiaryMenu diaryMenu, DiaryEntry diaryEntry,
      DiaryController diaryController) {
    this.diaryMenu = diaryMenu;
    this.diaryEntry = diaryEntry;
    this.diaryController = diaryController;
  }

  /**
   * Executes the action of editing a diary entry.
   * Presents a submenu for editing the diary entry's title or text.
   * Users can exit the submenu by selecting "back".
   * Validates each input and updates the diary entry accordingly.
   * If the validation from the controller fails, an error message is displayed
   * and the user is prompted again.
   */
  @Override
  public void execute() {
    boolean showSubMenu = true;

    while (showSubMenu) {
      diaryMenu.displaySecondaryHeader("Editing diary entry: " + diaryEntry.getEntryTitle());
      String submenuUserChoice = diaryMenu.subMenuForEditingDiaryAction();

      switch (submenuUserChoice.toLowerCase()) {
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
      String userInput = diaryMenu.getUserStringWithPrompt("Enter the text to add");
      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }
      try {
        diaryController.validateEntryText(userInput);
        validTextToAdd = userInput;

        diaryController.updateDiaryEntryTextByAdding(diaryEntry, validTextToAdd);
      } catch (InvalidInputException e) {
        diaryMenu.displayErrorMessage(e.getMessage());
      }
    }

  }

  /**
   * Overwrites the text of a diary entry.
   * Prompts the user for new text, validates the input, and updates the diary entry
   * through the controller.
   * If the user inputs "back", the operation is cancelled.
   * If the validation fails, an error message is displayed and the user is re-prompted.
   *
   * @param diaryEntry the diary entry to be updated
   */
  private void overwriteDiaryText(DiaryEntry diaryEntry) {
    diaryMenu.displayThirdHeader("Overwriting text");

    String validTextToSet = null;

    while (validTextToSet == null) {
      String userInput = diaryMenu.getUserStringWithPrompt("Enter new diary text");
      if ("back".equalsIgnoreCase(userInput)) {
        return;
      }
      try {
        diaryController.validateEntryText(userInput);
        validTextToSet = userInput;

        diaryController.updateDiaryEntryText(diaryEntry, validTextToSet);
      } catch (InvalidInputException e) {
        diaryMenu.displayErrorMessage(e.getMessage());
      }
    }
  }

  /**
   * Edits the title of a diary entry.
   * Prompts the user for a new title, validates the input, and updates the diary entry
   * through the controller.
   * If the user inputs "back", the operation is cancelled.
   * If the validation fails, an error message is displayed and the user is re-prompted.
   *
   * @param diaryEntry the diary entry to be updated
   */
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
        diaryController.validateEntryTitle(userInput);
        validTitleToSet = userInput;

        diaryController.updateDiaryEntryTitle(diaryEntry, validTitleToSet);
      } catch (InvalidInputException e) {
        diaryMenu.displayErrorMessage(e.getMessage());
      }
    }

  }
}
