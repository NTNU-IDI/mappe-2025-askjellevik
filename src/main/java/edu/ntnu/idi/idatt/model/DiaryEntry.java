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
    this.entryID = UUID.randomUUID();
    this.entryDate = entryDate;
    this.entryAuthor = entryAuthor;
    this.entryTitle = entryTitle;
    this.entryText = entryText;
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
    this.entryTitle = entryTitle;
  }

  /**   * Sets the text content of the diary entry.
   *
   * @param entryText the new entry text
   */
  public void setEntryText(String entryText) {
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


