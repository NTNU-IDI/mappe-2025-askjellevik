package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.controller.validators.DateValidator;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class DiaryController {

  private final DiaryRegister diaryRegister;


  public DiaryController(DiaryRegister diaryRegister) {
    this.diaryRegister = diaryRegister;
  }

  public void addDiaryToRegister(DiaryEntry diaryEntry) {
    diaryRegister.addDiaryEntry(diaryEntry);
  }

  public List<DiaryEntry> getListOfDiaryEntriesByDate(LocalDateTime date) {
    return diaryRegister.searchDiaryEntryByDate(date);
  }

  public void deleteDiaryEntryInRegister(DiaryEntry diaryEntry) {
    diaryRegister.deleteDiaryEntry(diaryEntry);
  }

  public void setDiaryText(DiaryEntry diaryEntry, String diaryText) {
    diaryEntry.setEntryText(diaryText);
  }

  public void setDiaryTitle(DiaryEntry diaryEntry, String diaryTitle) {
    diaryEntry.setEntryTitle(diaryTitle);
  }

  public List<DiaryEntry> getListOfDiaryEntriesWithinDates(LocalDateTime startDate,
      LocalDateTime endDate) {
    return diaryRegister.getAllEntriesWithinDates(startDate, endDate);
  }

  public List<DiaryEntry> getListOfDiaryEntriesWithSpecificWord(String word) {
    return diaryRegister.getAllEntriesWithSpecificWord(word);
  }

  public List<DiaryEntry> getListOfAllEntriesBySpecificAuthor(Author author) {
    return diaryRegister.getAllEntriesByAuthor(author);
  }

  public Map<Author, Integer> getMapOfDiaryEntriesByAuthors() {
    return diaryRegister.getMapOfAuthorEntryCounts();
  }

  public Integer getAmountOfEntriesBySpecificAuthor(Author author) {
    return diaryRegister.getAmountOfDiaryEntriesBySpecificAuthor(author);
  }

  public List<DiaryEntry> getListOfAllRegisteredDiaryEntries() {
    return diaryRegister.getAllDiaryEntries();
  }




}
