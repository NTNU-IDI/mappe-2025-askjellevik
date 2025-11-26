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


/**
 * Controller class for managing, manipulating and validating data in the diary register.
 *
 **/
public class DiaryController {

  private final DiaryRegister diaryRegister;

  /**
   * Constructs a DiaryController with the injected DiaryRegister.
   *
   * @param diaryRegister the DiaryRegister to be managed by this controller
   */
  public DiaryController(DiaryRegister diaryRegister) {
    this.diaryRegister = diaryRegister;
  }

  /**
   * Gets a list of all the registered diary entries in the register.
   *
   * @return list of all registered diary entries
   */
  public List<DiaryEntry> getListOfAllRegisteredDiaryEntries() {
    return diaryRegister.getAllDiaryEntries();
  }

  /**
   * Adds a new diary entry to the register with the given parameters.
   *
   * @param entryDate the date of the diary entry
   * @param entryAuthor the author of the diary entry
   * @param entryTitle the title of the diary entry
   * @param entryText the text of the diary entry
   */
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

  /**
   * Searches for diary entries by date.
   *
   * @param date the date to search for
   * @return a list of diary entries on the specified date
   */
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
        .filter(diaryEntry -> !diaryEntry.getEntryDate().isBefore(startDate)
            && !diaryEntry.getEntryDate().isAfter(endDate))
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


  /**
   * Returns the number of diary entries registered by a specific author.
   *
   * @param author the author desired to count entries for
   * @return the number of diary entries by the specified author
   */
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

  /**
   * Generates a map of authors and their corresponding number of diary entries.
   *
   * @return a map where the key is the author and the value is the number of entries by that author
   */
  public Map<Author, Integer> getMapOfEntriesByAuthor() {
    return getListOfAllRegisteredDiaryEntries().stream()
        .map(DiaryEntry::getEntryAuthor)
        .collect(Collectors.groupingBy(s -> s, Collectors.summingInt(e -> 1)));

  }


  /**
   * Validates the date of a diary entry.
   *
   * @param date the date to validate
   * @throws InvalidDateException if the date is invalid
   */
  public void validateDate(LocalDateTime date) throws InvalidDateException {
    List<String> errors = DiaryEntryValidator.validateDate(date);
    if (!errors.isEmpty()) {
      throw new InvalidDateException(String.join(". ", errors));
    }
  }

  /**
   * Validates the title of a diary entry.
   *
   * @param title the title to validate
   * @throws InvalidInputException if the title is invalid
   */
  public void validateEntryTitle(String title) throws InvalidInputException {
    List<String> errors = DiaryEntryValidator.validateTitle(title);
    if (!errors.isEmpty()) {
      throw new InvalidInputException(String.join(". ", errors));
    }
  }

  /**
   * Validates the text of a diary entry.
   *
   * @param text the text to validate
   * @throws InvalidInputException if the text is invalid
   */
  public void validateEntryText(String text) throws InvalidInputException {
    List<String> errors = DiaryEntryValidator.validateText(text);
    if (!errors.isEmpty()) {
      throw new InvalidInputException(String.join(". ", errors));
    }
  }
}
