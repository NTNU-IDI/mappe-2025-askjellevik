package edu.ntnu.idi.idatt.controller.validators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiaryEntryValidator {

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


  public static List<String> validateText(String text) {
    List<String> errors = new ArrayList<>();


    if (text == null || text.isEmpty()) {
      errors.add("The text can not be empty");
      return errors;
    }

    return errors;

  }

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
