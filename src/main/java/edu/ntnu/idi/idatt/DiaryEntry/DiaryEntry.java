package edu.ntnu.idi.idatt.DiaryEntry;

import java.time.LocalDateTime;

/**
 *
 * */
public class DiaryEntry {

  private final java.time.LocalDateTime entryDate;
  private final String entryAuthor;
  private String entryTitle;
  private String entryText;

  public DiaryEntry(java.time.LocalDateTime entryDate, String entryAuthor, String entryTitle,
      String entryText) {

    this.entryDate = entryDate;
    this.entryAuthor = entryAuthor;
    this.entryTitle = entryTitle;
    this.entryText = entryText;
  }


  public LocalDateTime getEntryDate() {
    return entryDate;
  }

  public String getEntryAuthor() {
    return entryAuthor;
  }

  public String getEntryTitle() {
    return entryTitle;
  }

  public String getEntryText() {
    return entryText;
  }

  public void setEntryTitle(String entryTitle) {
    this.entryTitle = entryTitle;
  }

  public void setEntryText(String entryText) {
    this.entryText = entryText;
  }

  @Override
  public String toString() {
    return "Date: " + entryDate + "\n" +
        "Author: " + entryAuthor + "\n" +
        "Title " + entryTitle + "\n" +
        "Entry: " + entryText;
  }

}


