package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.model.exceptions.DiaryEntryNotFound;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a register for managing diary entries.
 */
public class DiaryRegister {

  private final Map<UUID, DiaryEntry> diaryRegister;

  /**
   * Constructs an empty DiaryRegister.
   */
  public DiaryRegister() {
    this.diaryRegister = new HashMap<>();
  }


  /**
   * Adds a new diary entry to the register.
   *
   * @param diaryEntry the diary entry to add
   */
  public void addDiaryEntry(DiaryEntry diaryEntry) {
    diaryRegister.put(diaryEntry.getEntryID(), diaryEntry);
  }

  /**
   * Searches for diary entries by date.
   *
   * @param date the date to search for
   * @return a list of diary entries on the specified date
   */
  public List<DiaryEntry> searchDiaryEntryByDate(LocalDateTime date) {
    List<DiaryEntry> entriesOnDate = new ArrayList<>();
    for (DiaryEntry entry : diaryRegister.values()) {
      if (entry.getEntryDate().toLocalDate().equals(date.toLocalDate())) {
        entriesOnDate.add(entry);
      }
    }
    return entriesOnDate;
  }

  /**
   * Deletes a diary entry from the register.
   *
   * @param diaryEntry the diary entry to delete
   * @throws DiaryEntryNotFound if the diary entry is null
   */
  public void deleteDiaryEntry(DiaryEntry diaryEntry) {
    if (diaryEntry == null) {
      throw new DiaryEntryNotFound("Did not find the diary entry to delete.");
    }
    diaryRegister.remove(diaryEntry.getEntryID());
  }

  /**
   * Retrieves all diary entries within the specified date range.
   *
   * @param startDate the start date of the range
   * @param endDate   the end date of the range
   * @return a list of diary entries within the date range
   */
  public List<DiaryEntry> getAllEntriesWithinDates(LocalDateTime startDate, LocalDateTime endDate) {
    List<DiaryEntry> entriesWithinDates = new ArrayList<>();
    for (DiaryEntry entry : diaryRegister.values()) {
      if (!entry.getEntryDate().isBefore(startDate) && !entry.getEntryDate().isAfter(endDate)) {
        entriesWithinDates.add(entry);
      }
    }
    return entriesWithinDates;
  }

  /**
   * Retrieves all diary entries that contain a specific word in their title or text.
   *
   * @param word the word to search for
   * @return a list of diary entries containing the specified word
   */
  public List<DiaryEntry> getAllEntriesWithSpecificWord(String word) {
    List<DiaryEntry> entriesWithWord = new ArrayList<>();

    if (word == null || word.isBlank()) {
      return entriesWithWord;
    }

    String lowerWord = word.toLowerCase().trim();

    for (DiaryEntry entry : diaryRegister.values()) {
      if (entry.getEntryTitle().toLowerCase().contains(lowerWord)
          || entry.getEntryText().toLowerCase().contains(lowerWord)) {
        entriesWithWord.add(entry);
      }
    }

    return entriesWithWord;
  }

  /**
   * Retrieves all diary entries authored by a specific author.
   *
   * @param author the author whose entries to retrieve
   * @return a list of diary entries by the specified author
   */
  public List<DiaryEntry> getAllEntriesByAuthor(Author author) {
    List<DiaryEntry> entriesByAuthor = new ArrayList<>();

    for (DiaryEntry entry : diaryRegister.values()) {
      if (entry.getEntryAuthor().equals(author)) {
        entriesByAuthor.add(entry);
      }
    }
    return entriesByAuthor;
  }

  public Map<Author, Integer> getAuthorEntryCounts() {
    Map<Author, Integer> authorEntryCounts = new HashMap<>();

    for (DiaryEntry entry : diaryRegister.values()) {
      Author author = entry.getEntryAuthor();
      authorEntryCounts.put(author, authorEntryCounts.getOrDefault(author, 0) + 1);
    }

    return authorEntryCounts;
  }


}
