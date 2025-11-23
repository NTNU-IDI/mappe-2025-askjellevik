package edu.ntnu.idi.idatt.model;

import java.util.UUID;

/**
 * Represents an author with id, name, and email.
 */
public class Author {

  private final UUID id;
  private String name;
  private String email;


  /**
   * Constructs an Author with the specified id, name, and email.
   *
   * @param name  the name of the author
   * @param email the email of the author
   */
  public Author(String name, String email) {
    this.id = UUID.randomUUID();
    this.name = name;
    this.email = email;
  }

  /**   Gets the name of the author.
   *
   * @return the author's name
   */
  public UUID getId() {
    return id;
  }

  /**   Gets the name of the author.
   *
   * @return the author's name
   */
  public String getName() {
    return name;
  }

  /**   Gets the email of the author.
   *
   * @return the author's email
   */
  public String getEmail() {
    return email;
  }

  /**   Sets the email of the author.
   *
   * @param email the new email of the author
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**   Sets the name of the author.
   *
   * @param name the new name of the author
   */
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Author: " + name + " (Email: " + email + ")";
  }

}
