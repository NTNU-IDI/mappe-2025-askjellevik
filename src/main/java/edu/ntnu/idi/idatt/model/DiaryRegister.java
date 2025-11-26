package edu.ntnu.idi.idatt.model;

import edu.ntnu.idi.idatt.model.exceptions.DiaryEntryNotFound;
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
   * Gets a single diary entry from the diary register by its UUID.
   *
   * @param id the diary UUID id
   * @return the DiaryEntry object
   * */
  public DiaryEntry getDiaryEntryById(UUID id) {
    return diaryRegister.get(id);
  }

  /**
   * Deletes a diary entry from the register.
   *
   * @param diaryEntryId the UUID of diary entry to delete
   * @throws DiaryEntryNotFound if the diary entry ID is null
   */
  public void deleteDiaryEntry(UUID diaryEntryId) {
    if (diaryEntryId == null || !diaryRegister.containsKey(diaryEntryId)) {
      throw new DiaryEntryNotFound("Did not find the diary entry to delete.");
    }
    diaryRegister.remove(diaryEntryId);
  }

  /**
   * Gets a list of all the registered diary entries in the register.
   *
   * @return list of all registered diary entries
   */
  public List<DiaryEntry> getAllDiaryEntries() {
    return new ArrayList<>(diaryRegister.values());
  }


}
