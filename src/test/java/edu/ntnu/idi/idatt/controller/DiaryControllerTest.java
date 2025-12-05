package edu.ntnu.idi.idatt.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidDateException;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiaryControllerTest {

  private DiaryRegister diaryRegister;
  private DiaryController diaryController;
  private Author author;

  @BeforeEach
  void setUp() {
    diaryRegister = new DiaryRegister();
    diaryController = new DiaryController(diaryRegister);
    author = new Author("Test", "Author", "test.author@mail.com");
  }

  @Test
  void testSearchEntryByDate() {
    LocalDateTime searchDate = LocalDateTime.of(2024, 6, 15, 0, 0);
    DiaryEntry diaryEntry = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Test Title",
        "Test Text"
    );

    diaryRegister.addDiaryEntry(diaryEntry);
    List<DiaryEntry> results = diaryController.getListOfDiaryEntriesByDate(searchDate);

    assertEquals(1, results.size());
    assertEquals(diaryEntry, results.get(0));
  }

  @Test
  void testGetAllEntriesWithinDates() {
    DiaryEntry diaryEntry1 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 5, 30),
        author,
        "Test Title 1",
        "Test Text 1"
    );
    DiaryEntry diaryEntry2 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 16, 10, 30),
        author,
        "Test Title 2",
        "Test Text 2"
    );

    diaryRegister.addDiaryEntry(diaryEntry1);
    diaryRegister.addDiaryEntry(diaryEntry2);

    LocalDateTime startDate = LocalDateTime.of(2024, 6, 14, 0, 0);
    LocalDateTime endDate = LocalDateTime.of(2024, 6, 16, 23, 59);

    List<DiaryEntry> results = diaryController.getListOfDiaryEntriesWithinDates(startDate, endDate);

    assertTrue(results.contains(diaryEntry1));
    assertTrue(results.contains(diaryEntry2));
  }

  @Test
  void testGetAllDiaryEntriesWithinDatesNegative() {
    LocalDateTime startDate = LocalDateTime.of(2024, 6, 14, 0, 0);
    LocalDateTime endDate = LocalDateTime.of(2024, 6, 16, 23, 59);
    DiaryEntry diaryEntry1 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Test Title 1",
        "Test Text 1"
    );
    DiaryEntry diaryEntry2 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 17, 10, 30),
        author,
        "Test Title 2",
        "Test Text 2"
    );

    diaryRegister.addDiaryEntry(diaryEntry1);
    diaryRegister.addDiaryEntry(diaryEntry2);

    List<DiaryEntry> results = diaryController.getListOfDiaryEntriesWithinDates(startDate, endDate);

    assertTrue(results.contains(diaryEntry1));
    assertFalse(results.contains(diaryEntry2));
  }

  @Test
  void testGetAllEntriesWithSpecificWord() {
    DiaryEntry diaryEntry1 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "My first day",
        "I had a great day at the park."
    );
    DiaryEntry diaryEntry2 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 16, 10, 30),
        author,
        "Workday",
        "Today was a busy day at work."
    );

    diaryRegister.addDiaryEntry(diaryEntry1);
    diaryRegister.addDiaryEntry(diaryEntry2);

    List<DiaryEntry> results = diaryController.getListOfDiaryEntriesWithSpecificWord("day");

    assertTrue(results.contains(diaryEntry1));
    assertTrue(results.contains(diaryEntry2));
  }

  @Test
  void testGetAllEntriesWithSpecificWordNegative() {
    DiaryEntry diaryEntry1 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "My first day",
        "I had a great day at the park."
    );
    DiaryEntry diaryEntry2 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 16, 10, 30),
        author,
        "Work",
        "Busy at work."
    );

    diaryRegister.addDiaryEntry(diaryEntry1);
    diaryRegister.addDiaryEntry(diaryEntry2);

    List<DiaryEntry> results = diaryController.getListOfDiaryEntriesWithSpecificWord("day");

    assertTrue(results.contains(diaryEntry1));
    assertFalse(results.contains(diaryEntry2));

  }

  @Test
  void testGetAllEntriesWithSpecificWordNullOrBlank() {
    DiaryEntry diaryEntry1 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Test Title",
        "Test Text"
    );
    diaryRegister.addDiaryEntry(diaryEntry1);

    List<DiaryEntry> resultsNull = diaryController.getListOfDiaryEntriesWithSpecificWord(null);
    List<DiaryEntry> resultsBlank = diaryController.getListOfDiaryEntriesWithSpecificWord("");

    assertTrue(resultsNull.isEmpty());
    assertTrue(resultsBlank.isEmpty());
  }

  @Test
  void testGetAllEntriesByAuthor() {
    DiaryEntry diaryEntry1 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Test title 1",
        "Test text 1."
    );
    DiaryEntry diaryEntry2 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 16, 10, 30),
        author,
        "Test title 2",
        "Test text 2."
    );
    diaryRegister.addDiaryEntry(diaryEntry1);
    diaryRegister.addDiaryEntry(diaryEntry2);

    List<DiaryEntry> results = diaryController.getListOfAllEntriesBySpecificAuthor(author);

    assertTrue(results.contains(diaryEntry1));
    assertTrue(results.contains(diaryEntry2));

  }

  @Test
  void testGetAllEntriesByAuthorNegative() {
    DiaryEntry diaryEntry1 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Test title 1",
        "Test text 1."
    );
    Author author2 = new Author("Another", "Author", "another@mail.com");
    DiaryEntry diaryEntry2 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 16, 10, 30),
        author2,
        "Test title 2",
        "Test text 2."
    );
    diaryRegister.addDiaryEntry(diaryEntry1);
    diaryRegister.addDiaryEntry(diaryEntry2);

    List<DiaryEntry> results = diaryController.getListOfAllEntriesBySpecificAuthor(author);

    assertTrue(results.contains(diaryEntry1));
    assertFalse(results.contains(diaryEntry2));
  }

  @Test
  void testGetAuthorEntryCounts() {
    DiaryEntry diaryEntry1 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Test title 1",
        "Test text 1."
    );
    DiaryEntry diaryEntry2 = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 16, 10, 30),
        author,
        "Test title 2",
        "Test text 2."
    );

    diaryRegister.addDiaryEntry(diaryEntry1);
    diaryRegister.addDiaryEntry(diaryEntry2);

    Integer authorEntryCounts = diaryController.getAmountOfEntriesBySpecificAuthor(author);

    assertEquals(2, authorEntryCounts);
  }

  @Test
  void testDeleteDiaryEntry() {
    DiaryEntry diaryEntry = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Test title",
        "Test text."
    );

    diaryRegister.addDiaryEntry(diaryEntry);
    assertEquals(1, diaryController.getListOfAllRegisteredDiaryEntries().size());

    diaryController.deleteDiaryEntryInRegister(diaryEntry.getEntryId());

    assertEquals(0, diaryController.getListOfAllRegisteredDiaryEntries().size());
  }

  @Test
  void testDeleteDiaryEntryThrowsException() {
    assertThrows(Exception.class, () ->
        diaryController.deleteDiaryEntryInRegister(null)
    );
  }

  @Test
  void testUpdateDiaryEntryTitle() {
    DiaryEntry diaryEntry = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Old Title",
        "Test text."
    );
    diaryRegister.addDiaryEntry(diaryEntry);

    diaryController.updateDiaryEntryTitle(diaryEntry, "New Title");

    assertEquals("New Title",
        diaryController.getListOfAllRegisteredDiaryEntries().get(0).getEntryTitle());
  }

  @Test
  void testUpdateDiaryEntryTitleThrowsException() {
    DiaryEntry diaryEntry = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Test title",
        "Test text."
    );

    assertThrows(InvalidInputException.class, () ->
        diaryController.updateDiaryEntryTitle(diaryEntry, "")
    );
  }


  @Test
  void testUpdateDiaryEntryText() {
    DiaryEntry diaryEntry = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Title",
        "Old test text."
    );
    diaryRegister.addDiaryEntry(diaryEntry);

    diaryController.updateDiaryEntryText(diaryEntry, "New test text.");

    assertEquals("New test text.",
        diaryController.getListOfAllRegisteredDiaryEntries().get(0).getEntryText());
  }

  @Test
  void testUpdateDiaryEntryTextThrowsException() {
    DiaryEntry diaryEntry = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Title",
        "Old test text."
    );

    assertThrows(InvalidInputException.class, () ->
        diaryController.updateDiaryEntryText(diaryEntry, "")
    );
  }

  @Test
  void testUpdateDiaryEntryTextByAdding() {
    DiaryEntry diaryEntry = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Title",
        "Old test text."
    );
    diaryRegister.addDiaryEntry(diaryEntry);

    diaryController.updateDiaryEntryTextByAdding(diaryEntry, "This text is added.");

    assertEquals("Old test text.\nThis text is added.",
        diaryController.getListOfAllRegisteredDiaryEntries().get(0).getEntryText());
  }

  @Test
  void testUpdateDiaryEntryTextByAddingThrowsException() {
    DiaryEntry diaryEntry = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Test title",
        "Test text."
    );
    diaryRegister.addDiaryEntry(diaryEntry);

    assertThrows(InvalidInputException.class, () ->
        diaryController.updateDiaryEntryText(diaryEntry, "")
    );
  }

  @Test
  void testValidateDateThrowsException() {

    assertThrows(InvalidDateException.class, () ->
        diaryController.validateDate(null)
    );
  }

  @Test
  void testAddDiaryEntryToRegisterWithInvalidInputThrowsException() {
    LocalDateTime validDate = java.time.LocalDateTime.of(2024, 6, 15, 10, 30);

    assertThrows(InvalidDateException .class, () ->
        diaryController.addDiaryToRegister(null, author, "Title", "Text")
    );

    assertThrows(InvalidInputException.class, () ->
        diaryController.addDiaryToRegister(validDate, null, "Title", "Text")
    );

    assertThrows(InvalidInputException.class, () ->
        diaryController.addDiaryToRegister(validDate, author, null, "Text")
    );

    assertThrows(InvalidInputException.class, () ->
        diaryController.addDiaryToRegister(validDate, author, "Title", null)
    );



  }


}
