package edu.ntnu.idi.idatt.model.exceptions;

public class AuthorNotFoundException extends RuntimeException {

  public AuthorNotFoundException(String message) {
    super(message);
  }
}
