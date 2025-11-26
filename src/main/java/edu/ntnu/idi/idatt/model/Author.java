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
   * Constructs an Author with the specified id, name, and email.
   *
   * @param firstName the first name of the author
   * @param lastName the last name of the author
   * @param email the email of the author
   */
  public Author(String firstName, String lastName, String email) {
    this.id = UUID.randomUUID();
    this.firstName = firstName;
    this.lastName = lastName;
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
    return firstName + " " + lastName;
  }

  /**   Gets the first name of the author.
   *
   * @return the author's first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**   Gets the last name of the author.
   *
   * @return the author's last name
   */
  public String getLastName() {
    return lastName;
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

  /**   Sets the first name of the author.
   *
   * @param firstName the new name of the author
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**   Sets the last name of the author.
   *
   * @param lastName the new name of the author
   */
  public void setLastName(String lastName) {
    this.firstName = lastName;
  }


  @Override
  public String toString() {
    return "Author: " + getName() + " (Email: " + email + ")";
  }

}
