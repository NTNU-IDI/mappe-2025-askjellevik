package edu.ntnu.idi.idatt.controller.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AuthorValidator {

  public static List<String> validateEmail(String email) {
    List<String> errors = new ArrayList<>();

    String regexPattern = "^(.+)@(\\S+)$";

    if (!Pattern.compile(regexPattern)
        .matcher(email)
        .matches()) {
      errors.add("The email does not fit the email format");
    }

    return errors;


  }


  public static List<String> validateName(String name) {
    List<String> errors = new ArrayList<>();
    /* Chat-GPT prompt:
    *
    *   Make a regex pattern to check that it's a valid name, not enforcing capitalization or formatting.
    *   also allowing for names with hyphens, apostrophes and spaces.
    * */

    String regexPattern = "^[a-zA-ZÀ-ÿ'’-]+(?:[ -][a-zA-ZÀ-ÿ'’-]+)*$";

    if(!Pattern.compile(regexPattern)
        .matcher(name)
        .matches()) {
          errors.add("The name does not fit the full name format");
    }
    if (name.length() < 2) {
      errors.add("The name can not be less than 2 characters");
    }
    return errors;

  }

}
