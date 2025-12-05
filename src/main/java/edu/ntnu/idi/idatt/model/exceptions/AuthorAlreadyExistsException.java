package edu.ntnu.idi.idatt.model.exceptions;

/**
 * Exception thrown when an author already exists.
 */
public class AuthorAlreadyExistsException extends RuntimeException {

  /**
   * Constructs a new AuthorAlreadyExistsException with the specified detail message.
   *
   * @param message the detail message
   */
  public AuthorAlreadyExistsException(String message) {
    super(message);
  }
}
