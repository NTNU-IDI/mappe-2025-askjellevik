package edu.ntnu.idi.idatt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorRegisterTest {

  private AuthorRegister authorRegister;


  @BeforeEach
  void setUp() {
    authorRegister = new AuthorRegister();
  }

  @Test
  void testGetAllAuthorsIsInitiallyEmpty() {
    List<Author> authors = authorRegister.getAllAuthors();

    assertTrue(authors.isEmpty());
  }

  @Test
  void testAddAuthor() {
    Author author = new Author("Test",  "Author", "test@mail.com");

    authorRegister.addAuthor(author);
    List<Author> authors = authorRegister.getAllAuthors();


    assertEquals(1, authors.size());

    Author addedAuthor = authors.get(0);

    assertEquals("Test Author", addedAuthor.getName());
    assertEquals("test@mail.com", addedAuthor.getEmail());
    assertNotNull(addedAuthor.getId());
    assertSame(author, addedAuthor);

  }

  @Test
  void testGetAuthorByEmail() {
    Author author = new Author("Test", "Author", "test@mail.com");

    authorRegister.addAuthor(author);
    Author retrievedAuthor = authorRegister.getAuthorByEmail("test@mail.com");

    assertSame(author, retrievedAuthor);

  }

  @Test
  void testGetAuthorByEmailNotFound() {
    Author retrievedAuthor = authorRegister.getAuthorByEmail("not.found@mail.com");

    assertNull(retrievedAuthor);

  }

  @Test
  void testCheckIfAuthorExists() {
    Author author = new Author("Test", "Author", "test@author.com");

    authorRegister.addAuthor(author);
    boolean exists = authorRegister.checkIfAuthorExists("test@author.com");

    assertTrue(exists);

  }

  @Test
  void testCheckIfAuthorExistsNotFound() {
    boolean exists = authorRegister.checkIfAuthorExists("not.found@mail.com");

    assertFalse(exists);

  }
}
