package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.controller.validators.AuthorValidator;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.AuthorRegister;
import edu.ntnu.idi.idatt.model.exceptions.AuthorNotFoundException;
import java.util.List;

/**
 * Controller class for managing, manipulating and validating data in the author register.
 *
 **/
public class AuthorController {

  private final AuthorRegister authorRegister;

  /**
   * Constructs a DiaryController with the injected DiaryRegister.
   *
   * @param authorRegister the AuthorController to be managed by this controller
   */
  public AuthorController(AuthorRegister authorRegister) {
    this.authorRegister = authorRegister;
  }

  /**
   * Gets a list of all the registered authors in the register.
   *
   * @return list of all registered authors
   */
  public List<Author> getListOfAuthorsInRegister() {
    return authorRegister.getAllAuthors();
  }

  /**
   * Adds a new author to the register with the given parameters.
   *
   * @param name the name of the author
   * @param email the email of the author
   * @return the added author
   */
  public Author addAuthorToRegister(String name, String email) {
    Author authorToAdd = new Author(name, email);
    authorRegister.addAuthor(authorToAdd);
    return authorToAdd;
  }

  /**
   * Validates the author's name.
   *
   * @param newName the new name to validate
   * @throws InvalidInputException if the name is invalid
   * */
  public void validateAuthorName(String newName) throws InvalidInputException {
    List<String> errors = AuthorValidator.validateName(newName);

    if (!errors.isEmpty()) {
      throw new InvalidInputException(String.join(". ", errors));
    }

  }

  /**
   * Validates the author's email.
   *
   * @param newEmail the new email to validate
   * @throws InvalidInputException if the email is invalid
   * */
  public void validateAuthorEmail(String newEmail) throws InvalidInputException {
    List<String> errors = AuthorValidator.validateEmail(newEmail);

    if (!errors.isEmpty()) {
      throw new InvalidInputException(String.join(". ", errors));
    }
  }

  /**
   * Gets an author by their email.
   *
   * @param email the email of the author to return
   * @return the author with the specified email
   * @throws AuthorNotFoundException if no author with the specified email is found
   */
  public Author getAuthorByEmail(String email) throws AuthorNotFoundException {
    Author author = authorRegister.getAuthorByEmail(email);
    if (author == null) {
      throw new AuthorNotFoundException("Did not find an author with that email");
    }
    return author;
  }
}


