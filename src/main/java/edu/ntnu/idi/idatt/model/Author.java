package edu.ntnu.idi.idatt.model;

import java.util.UUID;

/**
 * Represents an author with id, name, and email.
 */
public class Author {

  private final UUID id;
  private String firstName;
  private String lastName;
  private String email;


  /**
   * Constructs an Author with the specified names and email.
   * The id is generated automatically as a UUID.
   *
   * @param firstName the first name of the author
   * @param lastName the last name of the author
   * @param email the email of the author
   * @throws IllegalArgumentException if any of the parameters are null or empty
   */
  public Author(String firstName, String lastName, String email) throws IllegalArgumentException {
    this.id = UUID.randomUUID();
    setFirstName(firstName);
    setLastName(lastName);
    setEmail(email);
  }

  /**
   * Gets the ID of the author.
   *
   * @return the author's ID
   */
  public UUID getId() {
    return id;
  }

  /**
   * Gets the name of the author.
   *
   * @return the author's name
   */
  public String getName() {
    return firstName + " " + lastName;
  }

  /**
   * Gets the first name of the author.
   *
   * @return the author's first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Gets the last name of the author.
   *
   * @return the author's last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Gets the email of the author.
   *
   * @return the author's email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the email of the author.
   *
   * @param email the new email of the author
   * @throws IllegalArgumentException if the email is null or empty
   */
  public void setEmail(String email) {
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("Email cannot be null or empty.");
    }
    this.email = email;
  }

  /**
   * Sets the first name of the author.
   *
   * @param firstName the new name of the author
   * @throws IllegalArgumentException if the first name is null or empty
   */
  public void setFirstName(String firstName) {
    if (firstName == null || firstName.isBlank()) {
      throw new IllegalArgumentException("First name cannot be null or empty.");
    }
    this.firstName = firstName;
  }

  /**
   * Sets the last name of the author.
   *
   * @param lastName the new name of the author
   */
  public void setLastName(String lastName) {
    if (lastName == null || lastName.isBlank()) {
      throw new IllegalArgumentException("Last name cannot be null or empty.");
    }
    this.lastName = lastName;
  }


  @Override
  public String toString() {
    return "Author: " + getName() + " (Email: " + email + ")";
  }

}
