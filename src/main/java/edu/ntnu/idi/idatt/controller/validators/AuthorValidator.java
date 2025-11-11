package edu.ntnu.idi.idatt.controller.validators;

import java.util.regex.Pattern;

public class AuthorValidator {

  public static boolean validateEmail(String email) {

    String regexPattern = "^(.+)@(\\S+)$";

    return Pattern.compile(regexPattern)
        .matcher(email)
        .matches();


  }


  public static boolean validateName(String name) {
    /* Chat-GPT prompt:
    *
    *   Make a regex pattern to check that it's a valid name, not enforcing capitalization or formatting.
    * */

    String regexPattern = "^[a-zA-Z'\\s-]+$";

    return Pattern.compile(regexPattern)
        .matcher(name)
        .matches();


  }

}
