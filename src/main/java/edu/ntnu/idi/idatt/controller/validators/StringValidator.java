package edu.ntnu.idi.idatt.controller.validators;

import edu.ntnu.idi.idatt.controller.exceptions.EmptyStringException;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;

public class StringValidator {

  public static void validateDiaryEntryText(String text) {
    if (text == null || text.isEmpty()) {
      throw new EmptyStringException("The string can not be empty.");
    }
  }

  public static void  validateDiaryTitle(String title) {
    if (title == null || title.isEmpty()) {
      throw new EmptyStringException("The title can not be empty.");
    }
    if (title.length() > 30) {
      throw new InvalidInputException("The title cannot be longer than 30 characters");
    }
  }

}
