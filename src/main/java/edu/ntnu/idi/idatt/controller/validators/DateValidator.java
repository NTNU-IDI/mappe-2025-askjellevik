package edu.ntnu.idi.idatt.controller.validators;

import edu.ntnu.idi.idatt.controller.exceptions.InvalidDateException;
import java.time.LocalDateTime;

public class DateValidator {

  public static boolean validateDate(LocalDateTime date) {
    LocalDateTime dateAndTimeNow = LocalDateTime.now();

    if (date.isAfter(dateAndTimeNow)) {
      throw new InvalidDateException("The date cannot be in the future");
    }

    return true;
  }

}
