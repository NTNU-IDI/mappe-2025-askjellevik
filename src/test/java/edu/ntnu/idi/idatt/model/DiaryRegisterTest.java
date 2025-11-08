package edu.ntnu.idi.idatt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.model.exceptions.DiaryEntryNotFound;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiaryRegisterTest {

  private DiaryRegister diaryRegister;
  private Author author;


  @BeforeEach
  void setUp() {
    diaryRegister = new DiaryRegister();
    author = new Author("Test Author", "test.author@mail.com");
  }

  @Test
  void testGetDiaryRegister() {
    Map<UUID, DiaryEntry> register = diaryRegister.getDiaryRegister();

    assertTrue(register.isEmpty());
  }

  @Test
  void testAddDiaryEntry() {
    DiaryEntry diaryEntry = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Test Title",
        "Test Text"
    );

    diaryRegister.addDiaryEntry(diaryEntry);

    assertTrue(diaryRegister.getDiaryRegister().containsKey(diaryEntry.getEntryID()));
    assertEquals(diaryRegister.getDiaryRegister().get(diaryEntry.getEntryID()), diaryEntry);

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
    List<DiaryEntry> results = diaryRegister.searchDiaryEntryByDate(searchDate);

    assertEquals(1, results.size());
    assertEquals(diaryEntry, results.getFirst());
  }


  @Test
  void testDeleteDiaryEntry() {
    DiaryEntry diaryEntry = new DiaryEntry(
        java.time.LocalDateTime.of(2024, 6, 15, 10, 30),
        author,
        "Test Title",
        "Test Text"
    );

    diaryRegister.addDiaryEntry(diaryEntry);
    diaryRegister.deleteDiaryEntry(diaryEntry);

    assertTrue(diaryRegister.getDiaryRegister().isEmpty());
  }

  @Test
  void testDeleteDiaryEntryThrowsException() {
    try {
      diaryRegister.deleteDiaryEntry(null);
    } catch (DiaryEntryNotFound e) {
      assertEquals("Did not find the diary entry to delete.", e.getMessage());
    }
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

    List<DiaryEntry> results = diaryRegister.getAllEntriesWithinDates(startDate, endDate);

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

    List<DiaryEntry> results = diaryRegister.getAllEntriesWithinDates(startDate, endDate);

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

    List<DiaryEntry> results = diaryRegister.getAllEntriesWithSpecificWord("day");

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
        "Workday",
        "Today was a busy ... at work."
    );

    diaryRegister.addDiaryEntry(diaryEntry1);
    diaryRegister.addDiaryEntry(diaryEntry2);

    List<DiaryEntry> results = diaryRegister.getAllEntriesWithSpecificWord("day");

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

    List<DiaryEntry> resultsNull = diaryRegister.getAllEntriesWithSpecificWord(null);
    List<DiaryEntry> resultsBlank = diaryRegister.getAllEntriesWithSpecificWord("");

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

    List<DiaryEntry> results = diaryRegister.getAllEntriesByAuthor(author);

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

    List<DiaryEntry> results = diaryRegister.getAllEntriesByAuthor(author);

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

    Map<Author, Integer> authorEntryCounts = diaryRegister.getAuthorEntryCounts();

    assertEquals(1, authorEntryCounts.size());
    assertEquals(2, authorEntryCounts.get(author));
  }


}
