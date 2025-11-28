package edu.ntnu.idi.idatt.model.exceptions;

public class AuthorAlreadyExistsException extends RuntimeException {

  public AuthorAlreadyExistsException(String message) {
    super(message);
  }
}
