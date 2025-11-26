package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.controller.exceptions.InvalidDateException;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.controller.validators.DiaryEntryValidator;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import edu.ntnu.idi.idatt.model.exceptions.DiaryEntryNotFound;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiaryController {

  private final DiaryRegister diaryRegister;


  public DiaryController(DiaryRegister diaryRegister) {
    this.diaryRegister = diaryRegister;
  }

  public List<DiaryEntry> getListOfAllRegisteredDiaryEntries() {
    return diaryRegister.getAllDiaryEntries();
  }

  public void addDiaryToRegister(LocalDateTime entryDate, Author entryAuthor, String entryTitle,
      String entryText) {
    DiaryEntry diaryEntryToAdd = new DiaryEntry(entryDate, entryAuthor, entryTitle, entryText);
    diaryRegister.addDiaryEntry(diaryEntryToAdd);
  }
  /**
   * Deletes a diary entry from the register.
   *
   * @param diaryEntry the diary entry to delete
   * @throws DiaryEntryNotFound if the diary entry is null
   */
  public void deleteDiaryEntryInRegister(DiaryEntry diaryEntry) {
    diaryRegister.deleteDiaryEntry(diaryEntry.getEntryID());
  }

  public List<DiaryEntry> getListOfDiaryEntriesByDate(LocalDateTime date) {
    if (date == null) {
      return List.of();
    }

    return getListOfAllRegisteredDiaryEntries().stream()
        .filter(diaryEntry -> diaryEntry.getEntryDate().toLocalDate().equals(date.toLocalDate()))
        .toList();
  }

  /**
   * Retrieves all diary entries within the specified date range.
   *
   * @param startDate the start date of the range
   * @param endDate   the end date of the range
   * @return a list of diary entries within the date range
   */
  public List<DiaryEntry> getListOfDiaryEntriesWithinDates(LocalDateTime startDate,
      LocalDateTime endDate) {

    if (startDate == null || endDate == null) {
      return List.of();
    }

    return getListOfAllRegisteredDiaryEntries().stream()
        .filter(diaryEntry -> !diaryEntry.getEntryDate().isBefore(startDate) &&
            !diaryEntry.getEntryDate().isAfter(endDate))
        .collect(Collectors.toList());

  }

  /**
   * Retrieves all diary entries that contain a specific word in their title or text.
   *
   * @param word the word to search for
   * @return a list of diary entries containing the specified word
   */
  public List<DiaryEntry> getListOfDiaryEntriesWithSpecificWord(String word) {
    if (word == null || word.isEmpty()) {
      return List.of();
    }

    String lowerWord = word.toLowerCase().trim();

    return getListOfAllRegisteredDiaryEntries().stream()
        .filter(diaryEntry -> diaryEntry.getEntryTitle().toLowerCase().contains(lowerWord)
            || diaryEntry.getEntryText().toLowerCase().contains(lowerWord))
        .collect(Collectors.toList());
  }


  public int getAmountOfEntriesBySpecificAuthor(Author author) {
    if (author == null) {
      return 0;
    }

    return (int) getListOfAllRegisteredDiaryEntries().stream()
        .filter(diaryEntry -> diaryEntry.getEntryAuthor().equals(author))
        .count();
  }


  /**
   * Retrieves all diary entries authored by a specific author.
   *
   * @param author the author whose entries to retrieve
   * @return a list of diary entries by the specified author
   */
  public List<DiaryEntry> getListOfAllEntriesBySpecificAuthor(Author author) {
    if (author == null) {
      return List.of();
    }

    return getListOfAllRegisteredDiaryEntries().stream()
        .filter(diaryEntry -> diaryEntry.getEntryAuthor().equals(author))
        .toList();
  }

  public Map<Author, Integer> getMapOfEntriesByAuthor() {
    return getListOfAllRegisteredDiaryEntries().stream()
        .map(DiaryEntry::getEntryAuthor)
        .collect(Collectors.groupingBy(s -> s, Collectors.summingInt(e -> 1)));

  }


  public void validateDate(LocalDateTime date) throws InvalidDateException {
    List<String> errors = DiaryEntryValidator.validateDate(date);
    if (!errors.isEmpty()) {
      throw new InvalidDateException(String.join(". ", errors));
    }
  }


  public void validateEntryTitle(String title) throws InvalidInputException {
    List<String> errors = DiaryEntryValidator.validateTitle(title);
    if (!errors.isEmpty()) {
      throw new InvalidInputException(String.join(". ", errors));
    }
  }

  public void validateEntryText(String text) throws InvalidInputException {
    List<String> errors = DiaryEntryValidator.validateText(text);
    if (!errors.isEmpty()) {
      throw new InvalidInputException(String.join(". ", errors));
    }
  }
}
