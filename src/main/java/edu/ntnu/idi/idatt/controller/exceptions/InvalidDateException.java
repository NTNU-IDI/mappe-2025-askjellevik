package edu.ntnu.idi.idatt.controller.exceptions;

/**
 * Exception thrown when an invalid date is run into.
 */
public class InvalidDateException extends RuntimeException {

  /**
   * Constructs a new InvalidDateException with the specified detail message.
   *
   * @param message the detail message
   */
  public InvalidDateException(String message) {
    super(message);
  }
}
