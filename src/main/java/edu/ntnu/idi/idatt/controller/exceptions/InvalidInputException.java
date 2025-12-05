package edu.ntnu.idi.idatt.controller.exceptions;

/**
 * Exception thrown when invalid input is encountered.
 */
public class InvalidInputException extends RuntimeException {

  /**
   * Constructs a new InvalidInputException with the specified detail message.
   *
   * @param message the detail message
   */
  public InvalidInputException(String message) {
    super(message);
  }
}
