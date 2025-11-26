package edu.ntnu.idi.idatt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.model.exceptions.DiaryEntryNotFound;
import java.util.List;
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
  void testGetAllDiaryEntries() {
    List<DiaryEntry> diaries = diaryRegister.getAllDiaryEntries();

    assertTrue(diaries.isEmpty());
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

    assertTrue(diaryRegister.getAllDiaryEntries().contains(diaryEntry));
    assertEquals(diaryRegister.getAllDiaryEntries().get(0), diaryEntry);

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
    diaryRegister.deleteDiaryEntry(diaryEntry.getEntryID());

    assertTrue(diaryRegister.getAllDiaryEntries().isEmpty());
  }

  @Test
  void testDeleteDiaryEntryThrowsException() {
    assertThrows(DiaryEntryNotFound.class , () -> diaryRegister.deleteDiaryEntry(null),
        "Did not find the diary entry to delete.");
  }

  @Test
  void testDeleteDiaryEntryThrowsExceptionForUnknownId() {
    UUID randomId = java.util.UUID.randomUUID();

    assertThrows(DiaryEntryNotFound.class, () -> diaryRegister.deleteDiaryEntry(randomId));
  }




















}
