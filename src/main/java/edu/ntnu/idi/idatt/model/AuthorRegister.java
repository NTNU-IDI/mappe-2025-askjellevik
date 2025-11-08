package edu.ntnu.idi.idatt.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Represents a register for managing authors.
 */
public class AuthorRegister {

  private final Map<UUID, Author> authorRegister;

  /**
   * Constructs an empty AuthorRegister.
   */
  public AuthorRegister() {
    this.authorRegister = new HashMap<>();
  }

  /**
   * Adds a new author to the register.
   *
   * @param name  the name of the author
   * @param email the email of the author
   */
  public void addAuthor(String name, String email) {
    Author newAuthor = new Author(name, email);
    authorRegister.put(newAuthor.getId(), newAuthor);
  }

  /**
   * Gets a list of all authors in the register.
   *
   * @return a list of all authors
   */
  public List<Author> getAllAuthors() {
    return new ArrayList<>(authorRegister.values());
  }



}
