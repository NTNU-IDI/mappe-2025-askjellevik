package edu.ntnu.idi.idatt.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
    author = new Author("Test Author", "test.author@mail.com");
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
    Author author2 = new Author("Another Author", "another@mail.com");
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

    Map<Author, Integer> authorEntryCounts = diaryController.getMapOfEntriesByAuthor();

    assertEquals(1, authorEntryCounts.size());
    assertEquals(2, authorEntryCounts.get(author));
  }

}
