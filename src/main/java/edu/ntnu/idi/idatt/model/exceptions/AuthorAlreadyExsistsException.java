package edu.ntnu.idi.idatt.model.exceptions;

public class AuthorAlreadyExsistsException extends RuntimeException {

  public AuthorAlreadyExsistsException(String message) {
    super(message);
  }
}
