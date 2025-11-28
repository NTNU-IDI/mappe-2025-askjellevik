package edu.ntnu.idi.idatt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthorTest {

  private Author author;


  @BeforeEach
  void setUp() {
    author = new Author("Navn", "Navnesen", "navn@mail.com");
  }

  @Test
  void testGetters() {
    assertEquals("Navn Navnesen", author.getName());
    assertEquals("navn@mail.com", author.getEmail());
    assertEquals("Navn", author.getFirstName());
    assertEquals("Navnesen", author.getLastName());
  }

  @Test
  void testSetEmail() {
    author.setEmail("navn.navnesen@mail.com");

    assertEquals("navn.navnesen@mail.com", author.getEmail());
  }

  @Test
  void testSetName() {
    author.setFirstName("Ola");
    author.setLastName("Nordmann");

    assertEquals("Ola", author.getFirstName());
    assertEquals("Nordmann", author.getLastName());

  }

  @Test
  void testToString() {
    String expectedString = "Author: Navn Navnesen (Email:" + " navn@mail.com" + ")";

    assertEquals(expectedString, author.toString());
  }

  @Test
  void testIdNotEqual() {
    Author anotherAuthor = new Author("Name", "Nameson", "nameson@mail.com");

    assertNotEquals(author.getId(), anotherAuthor.getId());
  }

}
