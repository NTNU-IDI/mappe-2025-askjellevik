package edu.ntnu.idi.idatt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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



}
