package edu.ntnu.idi.idatt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiaryEntryTest {

  private DiaryEntry diaryEntry;
  private Author author;
  private LocalDateTime entryDate;


  @BeforeEach
  void setUp() {
    author = new Author("Navn",  "Navnesen", "navn.navnesen@mail.com");
    entryDate = LocalDateTime.of(2024, 6, 15, 10, 30);
    diaryEntry = new DiaryEntry(entryDate, author, "Test title", "Test text.");
  }

  @Test
  void testGetters() {
    assertEquals(entryDate, diaryEntry.getEntryDate());
    assertEquals(author, diaryEntry.getEntryAuthor());
    assertEquals("Test title", diaryEntry.getEntryTitle());
    assertEquals("Test text.", diaryEntry.getEntryText());
  }

  @Test
  void testSetters() {
    diaryEntry.setEntryTitle("Updated title");
    diaryEntry.setEntryText("Updated text.");

    assertEquals("Updated title", diaryEntry.getEntryTitle());
    assertEquals("Updated text.", diaryEntry.getEntryText());
  }

  @Test
  void testToString() {
    String expected = "Date: " + entryDate + "\n"
        + "Author: " + author.getName() + "\n"
        + "Title " + diaryEntry.getEntryTitle() + "\n"
        + "Entry: " + diaryEntry.getEntryText();

    assertEquals(expected, diaryEntry.toString());
  }

  @Test
  void testConstructorWithInvalidArguments() {
    assertThrows(IllegalArgumentException.class, () -> new DiaryEntry(null, author, "Title", "Text"));
    assertThrows(IllegalArgumentException.class, () -> new DiaryEntry(entryDate, null, "Title", "Text"));
    assertThrows(IllegalArgumentException.class, () -> new DiaryEntry(entryDate, author, null, "Text"));
    assertThrows(IllegalArgumentException.class, () -> new DiaryEntry(entryDate, author, "", "Text"));
    assertThrows(IllegalArgumentException.class, () -> new DiaryEntry(entryDate, author, " ", "Text"));
    assertThrows(IllegalArgumentException.class, () -> new DiaryEntry(entryDate, author, "Title", null));
    assertThrows(IllegalArgumentException.class, () -> new DiaryEntry(entryDate, author, "Title", ""));
    assertThrows(IllegalArgumentException.class, () -> new DiaryEntry(entryDate, author, "Title", " "));
  }

  @Test
  void testSettersWithInvalidArguments() {
    assertThrows(IllegalArgumentException.class, () -> diaryEntry.setEntryTitle(null));
    assertThrows(IllegalArgumentException.class, () -> diaryEntry.setEntryTitle(""));
    assertThrows(IllegalArgumentException.class, () -> diaryEntry.setEntryTitle(" "));

    assertThrows(IllegalArgumentException.class, () -> diaryEntry.setEntryText(null));
    assertThrows(IllegalArgumentException.class, () -> diaryEntry.setEntryText(""));
    assertThrows(IllegalArgumentException.class, () -> diaryEntry.setEntryText(" "));
  }
}
