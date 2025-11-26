package edu.ntnu.idi.idatt.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.AuthorRegister;
import edu.ntnu.idi.idatt.model.exceptions.AuthorNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorControllerTest {

  private AuthorRegister authorRegister;
  private AuthorController authorController;

  @BeforeEach
  void setUp() {
    authorRegister = new AuthorRegister();
    authorController = new AuthorController(authorRegister);
  }

  @Test
  void testValidateAuthorNameThrowsException() {
    String invalidName1 = " ";
    assertThrows(InvalidInputException.class, () ->
        authorController.validateAuthorName(invalidName1)
    );

    String invalidName2 = "Test123";
    assertThrows(InvalidInputException.class, () ->
        authorController.validateAuthorName(invalidName2)
    );

    String invalidName3 = "A";
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
    Author addedAuthor = authorController.addAuthorToRegister("Test Author", "test@mail.com");
    Author authorByEmail = authorController.getAuthorByEmail("test@mail.com");

    assert(addedAuthor.equals(authorByEmail));
  }

  @Test
  void testGetAuthorByEmailThrowsException() {
    assertThrows(AuthorNotFoundException.class, () ->
        authorController.getAuthorByEmail(""));
  }

}
