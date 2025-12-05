package edu.ntnu.idi.idatt.model.exceptions;

/**
 * Exception thrown when a diary entry is not found.
 */
public class DiaryEntryNotFound extends RuntimeException {

  /**
   * Constructs a DiaryEntryNotFound exception with the specified message.
   *
   * @param message the detail message
   */
  public DiaryEntryNotFound(String message) {
    super(message);
  }
}
