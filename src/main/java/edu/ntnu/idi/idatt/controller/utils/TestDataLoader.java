package edu.ntnu.idi.idatt.controller.utils;

import edu.ntnu.idi.idatt.controller.AuthorController;
import edu.ntnu.idi.idatt.controller.DiaryController;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidDateException;
import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.model.Author;
import java.time.LocalDateTime;

public class TestDataLoader {

  /**
   * Constructs a TestDataLoader.
   *
   * @author andskj
   * @since v0.3.1
   */
  public TestDataLoader() {
  }

  /**
   * Loads hard-coded test data into the provided DiaryController and AuthorController.
   *
   * @param diaryController  the DiaryController to load data into
   * @param authorController the AuthorController to load data into
   */
  public void loadTestData(DiaryController diaryController, AuthorController authorController) {

    try {
      // Crating dateTime objects for diary entries
      LocalDateTime dateTime1 = LocalDateTime.of(2025, 4, 9, 17, 0);
      LocalDateTime dateTime2 = LocalDateTime.of(2025, 4, 10, 18, 16);
      LocalDateTime dateTime3 = LocalDateTime.of(2025, 4, 6, 18, 0);
      LocalDateTime dateTime4 = LocalDateTime.of(2025, 1, 13, 18, 20);
      LocalDateTime dateTime5 = LocalDateTime.of(2025, 3, 25, 18, 0);
      LocalDateTime dateTime6 = LocalDateTime.of(2025, 6, 13, 18, 33);
      LocalDateTime dateTime7 = LocalDateTime.of(2024, 12, 1, 12, 0);
      LocalDateTime dateTime8 = LocalDateTime.of(2024, 11, 5, 14, 30);
      LocalDateTime dateTime9 = LocalDateTime.of(2024, 10, 20, 16, 45);
      LocalDateTime dateTime10 = LocalDateTime.of(2025, 2, 14, 10, 0);
      LocalDateTime dateTime11 = LocalDateTime.of(2025, 5, 30, 9, 15);
      LocalDateTime dateTime12 = LocalDateTime.of(2025, 7, 4, 11, 45);

      // Adding authors to the register
      authorController.addAuthorToRegister("André", "Navnesen", "andre@email.no");
      authorController.addAuthorToRegister("Ole", "Hansen", "hansen@mail.com");
      authorController.addAuthorToRegister("Gyda", "Johnsen", "gyda.johnsen@mail.net");
      authorController.addAuthorToRegister("Gunnar", "Sønsteby", "nr24@secret.eu");

      // Retrieving authors by email
      Author author1 = authorController.getAuthorByEmail("andre@email.no");
      Author author2 = authorController.getAuthorByEmail("hansen@mail.com");
      Author author3 = authorController.getAuthorByEmail("gyda.johnsen@mail.net");
      Author author4 = authorController.getAuthorByEmail("nr24@secret.eu");

      // Adding diary entries for author1
      diaryController.addDiaryToRegister(dateTime1, author1, "My first every diary entry!",
          "Today I went to the mall.");
      diaryController.addDiaryToRegister(dateTime2, author1, "My meeting with a turtle",
          "Today i found a turtle in my garden. It was cool");
      diaryController.addDiaryToRegister(dateTime3, author1, "I sold the turtle...",
          "Something sad happened today. I sold the turtle...");

      // Adding diary entries for author2
      diaryController.addDiaryToRegister(dateTime4, author2, "The worst day of my life",
          "There was a spider in my room.");
      diaryController.addDiaryToRegister(dateTime5, author2, "The best day of my life ",
          "I got a new puppy, and it ate the spider in my room!");
      diaryController.addDiaryToRegister(dateTime6, author2, "My puppy ate all my snacks :(",
          "My puppy is so naughty. He ate all my snacks.");

      // Adding diary entries for author3
      diaryController.addDiaryToRegister(dateTime7, author3, "Me and my existential crisis",
          "What even is the meaning of life?");
      diaryController.addDiaryToRegister(dateTime8, author3, "I found a new meaning of life",
          "After I pondered for a while, I realized that life is about enjoying the little things.");
      diaryController.addDiaryToRegister(dateTime9, author3, "I watched Interstellar..",
          "Today I watched Interstellar, so now I am even more confused about the meaning of life.");

      // Adding diary entries for author4
      diaryController.addDiaryToRegister(dateTime10, author4, "Day one of being broke",
          "I am so broke that ive started eating instant noodles for every meal.");
      diaryController.addDiaryToRegister(dateTime11, author4, "Day two of being broke",
          "Today I bought myself a coffee. I am now even more broke. But the coffee was good.");
      diaryController.addDiaryToRegister(dateTime12, author4, "Day three of being broke",
          "I found a dollar on the ground today. So I am twice as rich as I was yesterday.");

    }
    catch (InvalidInputException | InvalidDateException e) {
      System.err.println(e.getMessage());
    }
  }
}
