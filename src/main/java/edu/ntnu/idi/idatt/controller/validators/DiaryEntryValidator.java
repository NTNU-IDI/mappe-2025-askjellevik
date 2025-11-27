package edu.ntnu.idi.idatt.controller.validators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Validator class for validating diary entry fields.
 */
public class DiaryEntryValidator {


  /**
   * Validates the given diary title.
   * The title must meet these criteria:
   * <ul>
   *   <li>Not be null or empty</li>
   *   <li>Not be longer than 30 characters</li>
   * </ul>
   *
   * @param title the title to validate
   * @return a list of error messages, empty if the title is valid
   */
  public static List<String> validateTitle(String title) {
    List<String> errors = new ArrayList<>();

    if (title == null || title.isEmpty()) {
      errors.add("The title can not be empty");
      return errors;
    }


    if (title.length() > 30) {
      errors.add("The title can not be longer than 30 characters");
    }

    return errors;


  }

  /**
   * Validates the given text.
   * The text must meet these criteria:
   * <ul>
   *   <li>Not be null or empty</li>
   * </ul>
   *
   * @param text the email to validate
   * @return a list of error messages, empty if the email is valid
   */
  public static List<String> validateText(String text) {
    List<String> errors = new ArrayList<>();


    if (text == null || text.isEmpty()) {
      errors.add("The text can not be empty");
      return errors;
    }

    return errors;

  }

  /**
   * Validates the given date.
   * The date must meet these criteria:
   * <ul>
   *   <li>Not be null or empty</li>
   *   <li>Not be in the future</li>
   * </ul>
   *
   * @param date the date to validate
   * @return a list of error messages, empty if the date is valid
   */
  public static List<String> validateDate(LocalDateTime date) {
    List<String> errors = new ArrayList<>();

    if (date == null) {
      errors.add("The date can not be null");
      return errors;
    }

    LocalDateTime dateAndTimeNow = LocalDateTime.now();

    if (date.isAfter(dateAndTimeNow)) {
      errors.add("The date can not be in the future");
    }

    return errors;
  }

}
