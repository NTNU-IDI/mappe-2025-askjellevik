package edu.ntnu.idi.idatt.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a diary entry with date, author, title, and text.
 */
public class DiaryEntry {

  private final UUID entryID;
  private final LocalDateTime entryDate;
  private final Author entryAuthor;
  private String entryTitle;
  private String entryText;

  /**
   * Constructs a DiaryEntry with the specified date, author, title, and text.
   * UUID is generated automatically.
   *
   * @param entryDate   the date of the diary entry
   * @param entryAuthor the author of the diary entry
   * @param entryTitle  the title of the diary entry
   * @param entryText   the text content of the diary entry
   */
  public DiaryEntry(LocalDateTime entryDate, Author entryAuthor, String entryTitle,
      String entryText) {
    if (entryDate == null) {
      throw new IllegalArgumentException("Entry date cannot be null.");
    }
    if (entryAuthor == null) {
      throw new IllegalArgumentException("Entry author cannot be null.");
    }
    this.entryID = UUID.randomUUID();
    this.entryDate = entryDate;
    this.entryAuthor = entryAuthor;
    setEntryTitle(entryTitle);
    setEntryText(entryText);
  }

  /**   * Gets the unique identifier of the diary entry.
   *
   * @return the entry ID
   */
  public UUID getEntryID() {
    return entryID;
  }

  /**   * Gets the date of the diary entry.
   *
   * @return the entry date
   */
  public LocalDateTime getEntryDate() {
    return entryDate;
  }

  /**   * Gets the author of the diary entry.
   *
   * @return the entry author
   */
  public Author getEntryAuthor() {
    return entryAuthor;
  }

  /**   * Gets the title of the diary entry.
   *
   * @return the entry title
   */
  public String getEntryTitle() {
    return entryTitle;
  }
/**   * Gets the text content of the diary entry.
   *
   * @return the entry text
   */
  public String getEntryText() {
    return entryText;
  }

  /**   * Sets the title of the diary entry.
   *
   * @param entryTitle the new entry title
   */
  public void setEntryTitle(String entryTitle) {
    if (entryTitle == null || entryTitle.isBlank()) {
      throw new IllegalArgumentException("Entry title cannot be null or empty.");
    }
    this.entryTitle = entryTitle;
  }

  /**   * Sets the text content of the diary entry.
   *
   * @param entryText the new entry text
   */
  public void setEntryText(String entryText) {
    if (entryText == null || entryText.isBlank()) {
      throw new IllegalArgumentException("Entry text cannot be null or empty.");
    }
    this.entryText = entryText;
  }

  /**   * Returns a string representation of the diary entry.
   *
   * @return a string containing the date, author, title, and text of the entry
   */
  @Override
  public String toString() {
    return "Date: " + entryDate + "\n" +
        "Author: " + entryAuthor.getName() + "\n" +
        "Title " + entryTitle + "\n" +
        "Entry: " + entryText;
  }

}


