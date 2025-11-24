package edu.ntnu.idi.idatt.controller.validators;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DateValidator {

  public static List<String> validateDate(LocalDateTime date) {
    List<String> errors = new ArrayList<>();

    if (date == null) {
      errors.add("Date cannot be empty");
      return errors;
    }

    LocalDateTime dateAndTimeNow = LocalDateTime.now();

    if (date.isAfter(dateAndTimeNow)) {
      errors.add("The date can not be in the future");
    }

    return errors;
  }

}
