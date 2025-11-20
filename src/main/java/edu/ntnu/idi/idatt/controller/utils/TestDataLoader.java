package edu.ntnu.idi.idatt.controller.utils;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import java.time.LocalDateTime;

public class TestDataLoader {

  /**
   *
   *
   * @author andskj
   * @since v0.3.1
   */
  public TestDataLoader() {
  }

  public void loadTestData(DiaryController diaryController, AuthorController authorController) {


    LocalDateTime dateTime1 = LocalDateTime.of(2025, 4,9,17,0 );
    LocalDateTime dateTime2 = LocalDateTime.of(2025, 4,10,18,0 );
    LocalDateTime dateTime3 = LocalDateTime.of(2025, 4,6,18,0 );
    LocalDateTime dateTime4 = LocalDateTime.of(2025, 1,13,18,0 );
    LocalDateTime dateTime5 = LocalDateTime.of(2025, 3,25,18,0 );
    LocalDateTime dateTime6 = LocalDateTime.of(2025, 6,13,18,0 );



    Author author1 = new Author("Andy Skjell", "and@mail.com");
    Author author2 = new Author("Martn Basj", "mart@mail.com");

    DiaryEntry diary1 = new DiaryEntry(dateTime1, author1, "test", "tesasdas");
    DiaryEntry diary2 = new DiaryEntry(dateTime2, author2, "tes3wt", "tesasdas");
    DiaryEntry diary3 = new DiaryEntry(dateTime3, author1, "testwr3w", "tesasdas");
    DiaryEntry diary4 = new DiaryEntry(dateTime4, author2, "test", "tesasdas");
    DiaryEntry diary5 = new DiaryEntry(dateTime5, author1, "tes455t", "tesasdas");
    DiaryEntry diary6 = new DiaryEntry(dateTime6, author2, "test", "tesasdas");

    authorController.addAuthorToRegister(author1);
    authorController.addAuthorToRegister(author2);

    diaryController.addDiaryToRegister(diary1);
    diaryController.addDiaryToRegister(diary2);
    diaryController.addDiaryToRegister(diary3);
    diaryController.addDiaryToRegister(diary4);
    diaryController.addDiaryToRegister(diary5);
    diaryController.addDiaryToRegister(diary6);

  }

}
