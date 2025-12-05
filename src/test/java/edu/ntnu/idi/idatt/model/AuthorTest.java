package edu.ntnu.idi.idatt.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

  @Test
  void testConstructorWithInvalidEmailThrowsException() {

    assertThrows(IllegalArgumentException.class, () -> new Author("Navn", "Navnesen", null));
    assertThrows(IllegalArgumentException.class, () -> new Author("Navn", "Navnesen", ""));
    assertThrows(IllegalArgumentException.class, () -> new Author("Navn", "Navnesen", " "));

    assertThrows(IllegalArgumentException.class, () -> author.setEmail(null));
    assertThrows(IllegalArgumentException.class, () -> author.setEmail(""));
    assertThrows(IllegalArgumentException.class, () -> author.setEmail(" "));
  }

  @Test
  void testInvalidFirstNameThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new Author(null, "Navnesen", "email@mail.com"));
    assertThrows(IllegalArgumentException.class, () -> new Author("", "Navnesen", "email@mail.com"));
    assertThrows(IllegalArgumentException.class, () -> new Author(" ", "Navnesen", "email@mail.com"));

    assertThrows(IllegalArgumentException.class, () -> author.setFirstName(null));
    assertThrows(IllegalArgumentException.class, () -> author.setFirstName(""));
    assertThrows(IllegalArgumentException.class, () -> author.setFirstName(" "));

  }

  @Test
  void testInvalidLastNameThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new Author("Navn", null, "email@mail.com"));
    assertThrows(IllegalArgumentException.class, () -> new Author("Navn", "", "email@mail.com"));
    assertThrows(IllegalArgumentException.class, () -> new Author("Navn", " ", "email@mail.com"));


    assertThrows(IllegalArgumentException.class, () -> author.setLastName(null));
    assertThrows(IllegalArgumentException.class, () -> author.setLastName(""));
    assertThrows(IllegalArgumentException.class, () -> author.setLastName(" "));

  }

}
