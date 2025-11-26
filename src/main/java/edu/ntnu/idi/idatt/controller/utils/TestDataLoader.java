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

    authorController.addAuthorToRegister("Hallgeir", "Kvadsheim", "and@mail.com");
    authorController.addAuthorToRegister("Martin", "Aronsen", "mart@mail.com");


    Author author1 = authorController.getAuthorByEmail("and@mail.com");
    Author author2 = authorController.getAuthorByEmail("mart@mail.com");


    diaryController.addDiaryToRegister(dateTime1, author1, "Diary 1", "Diary text 1");
    diaryController.addDiaryToRegister(dateTime2, author2, "Diary 2", "Diary text 2");
    diaryController.addDiaryToRegister(dateTime3, author1, "Diary 3", "Diary text 3");
    diaryController.addDiaryToRegister(dateTime4, author2, "Diary 4", "Diary text 4");
    diaryController.addDiaryToRegister(dateTime5, author1, "Diary 5", "Diary text 5");
    diaryController.addDiaryToRegister(dateTime6, author2, "Diary 6", "Diary text 6");

  }

}
