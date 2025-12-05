package edu.ntnu.idi.idatt.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.AuthorRegister;
import edu.ntnu.idi.idatt.model.DiaryRegister;
import edu.ntnu.idi.idatt.model.exceptions.AuthorAlreadyExistsException;
import edu.ntnu.idi.idatt.model.exceptions.AuthorNotFoundException;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorControllerTest {

  private AuthorController authorController;
  private DiaryController diaryController;

  @BeforeEach
  void setUp() {
    AuthorRegister authorRegister = new AuthorRegister();
    DiaryRegister diaryRegister = new DiaryRegister();

    diaryController = new DiaryController(diaryRegister);
    authorController = new AuthorController(authorRegister, diaryController);
  }

  @Test
  void testValidateAuthorNameThrowsException() {
    String invalidName1 = " ";
    String invalidName2 = "Test123";
    String invalidName3 = "A";

    assertThrows(InvalidInputException.class, () ->
        authorController.validateAuthorName(invalidName1)
    );

    assertThrows(InvalidInputException.class, () ->
        authorController.validateAuthorName(invalidName2)
    );

    assertThrows(InvalidInputException.class, () ->
        authorController.validateAuthorName(invalidName3)
    );
  }


  @Test
  void testValidateAuthorEmailThrowsException() {
    String invalidEmail = "email.com";
    assertThrows(InvalidInputException.class, () ->
        authorController.validateAuthorEmail(invalidEmail)
    );
  }

  @Test
  void testGetAuthorByEmailReturnsCorrectAuthor() throws AuthorNotFoundException {
    Author addedAuthor = authorController.addAuthorToRegister("Test", "Author", "test@mail.com");
    Author authorByEmail = authorController.getAuthorByEmail("test@mail.com");

    assertEquals(addedAuthor, authorByEmail);
  }

  @Test
  void testGetAuthorByEmailThrowsException() {
    assertThrows(AuthorNotFoundException.class, () ->
        authorController.getAuthorByEmail(""));
  }

  @Test
  void testAddExistingAuthorToRegisterThrowsException() {
    authorController.addAuthorToRegister("Test", "Author", "test@mail.com");

    assertThrows(AuthorAlreadyExistsException.class, () ->
        authorController.addAuthorToRegister("Another", "Authorer", "test@mail.com"));

  }

  @Test
  void testUpdateAuthorNamesThrowsException() {
    Author author = new Author("Test", "Author", "author@mail.com");

    assertThrows(InvalidInputException.class, () ->
        authorController.updateAuthorFirstName(author, "123Name"));
    assertThrows(InvalidInputException.class, () ->
        authorController.updateAuthorLastName(author, "123Name"));
  }

  @Test
  void testUpdateAuthorEmailThrowsInvalidException() {
    Author author = new Author("Test", "Author", "email.mail.com");

    assertThrows(InvalidInputException.class, () ->
        authorController.updateAuthorEmail(author, "invalidemail.com"));

  }

  @Test
  void testUpdateAuthorEmailThrowsAlreadyExistsException() {
    authorController.addAuthorToRegister("Test", "Author", "email@that.exist");
    Author author = authorController.addAuthorToRegister("Another", "Authorer",
        "another@to.update");


    assertThrows(Exception.class, () ->
        authorController.updateAuthorEmail(author, "email@that.exist"));
  }

    @Test
    void testGetMapOfEntriesByAuthor () {
      authorController.addAuthorToRegister("Test", "Author", "test@mail.com");
      authorController.addAuthorToRegister("Another", "Authorer", "tester@lyse.net");

      Author author1 = authorController.getAuthorByEmail("test@mail.com");
      Author author2 = authorController.getAuthorByEmail("tester@lyse.net");

      LocalDateTime date = LocalDateTime.of(2023, 1, 1, 12, 0);

      diaryController.addDiaryToRegister(date, author1, "Title1", "Text1");
      diaryController.addDiaryToRegister(date, author1, "Title2", "Text2");
      diaryController.addDiaryToRegister(date, author2, "Title3", "Text3");

      Map<Author, Integer> entriesByAuthor = authorController.getMapOfEntriesByAuthor();

      assertEquals(2, entriesByAuthor.get(author1));
      assertEquals(1, entriesByAuthor.get(author2));
    }

    @Test
    void testAddAuthorToRegisterWithInvalidInputThrowsException() {
      assertThrows(InvalidInputException.class, () ->
          authorController.addAuthorToRegister(" ", "Author", "author@mail.com"));

      assertThrows(InvalidInputException.class, () ->
          authorController.addAuthorToRegister("Test", " ", "author@mail.com"));

      assertThrows(InvalidInputException.class, () ->
          authorController.addAuthorToRegister("Test", "Author", " "));

    }

  }
