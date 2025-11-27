package edu.ntnu.idi.idatt.controller.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validator class for validating author data.
 */
public class AuthorValidator {

  /**
   * Validates the given email.
   * The email must meet these criteria:
   * <ul>
   *   <li>Not be null or empty</li>
   *   <li>Contain one @ character</li>
   *   <li>Contain a domain after the @ character</li>
   * </ul>
   *
   * @param email the email to validate
   * @return a list of error messages, empty if the email is valid
   */
  public static List<String> validateEmail(String email) {
    List<String> errors = new ArrayList<>();

    if (email == null || email.isEmpty()) {
      errors.add("The email can not be empty");
      return errors;
    }

    String regexPattern = "^(.+)@(\\S+)$";

    if (!Pattern.compile(regexPattern)
        .matcher(email)
        .matches()) {
      errors.add("The email does not fit the email format");
    }

    return errors;

  }


  /**
   * Validates the given name.
   * The name must meet these criteria:
   * <ul>
   *   <li>Not be null or empty</li>
   *   <li>Be at least 2 characters long</li>
   *   <li>Contain only valid name characters (letters, hyphens, apostrophes, spaces)</li>
   * </ul>
   *
   * @param name the name to validate
   * @return a list of error messages, empty if the name is valid
   */
  public static List<String> validateName(String name) {
    List<String> errors = new ArrayList<>();

    if (name == null || name.isEmpty()) {
      errors.add("The name can not be empty");
      return errors;
    }

    /* Chat-GPT prompt:
    *
    *   Make a regex pattern to check that it's a valid name, not enforcing capitalization or formatting.
    *   also allowing for names with hyphens, apostrophes and spaces.
    * */

    String regexPattern = "^[a-zA-ZÀ-ÿ'’-]+(?:[ -][a-zA-ZÀ-ÿ'’-]+)*$";

    if(!Pattern.compile(regexPattern)
        .matcher(name)
        .matches()) {
          errors.add("The name does not fit the name format");
    }
    if (name.length() < 2) {
      errors.add("The name can not be less than 2 characters");
    }
    return errors;

  }

}
