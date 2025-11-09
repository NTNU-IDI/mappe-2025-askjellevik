package edu.ntnu.idi.idatt.controller.validators;

import edu.ntnu.idi.idatt.controller.exceptions.InvalidDateException;
import java.time.LocalDateTime;

public class DateValidator {

  public static boolean validateDate(LocalDateTime date) {

    try {
      /*TODO: Add date validator logic*/
      return true;
    } catch (InvalidDateException e) {
      return false;
    }

  }

}
