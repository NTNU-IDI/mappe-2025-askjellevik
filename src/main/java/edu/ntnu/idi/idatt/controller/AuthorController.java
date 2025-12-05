package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.controller.validators.AuthorValidator;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.AuthorRegister;
import edu.ntnu.idi.idatt.model.DiaryEntry;
import edu.ntnu.idi.idatt.model.exceptions.AuthorAlreadyExistsException;
import edu.ntnu.idi.idatt.model.exceptions.AuthorNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller class for managing, manipulating and validating data in the author register.
 *
 **/
public class AuthorController {

  private final AuthorRegister authorRegister;
  private final DiaryController diaryController;

  /**
   * Constructs a DiaryController with the injected DiaryRegister.
   *
   * @param authorRegister the authorRegister to be managed by this controller
   * @param diaryController the DiaryController to get diary entries from;
   */
  public AuthorController(AuthorRegister authorRegister, DiaryController diaryController) {
    this.authorRegister = authorRegister;
    this.diaryController = diaryController;
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
   * For safety, all parameters are validated before creating and adding the author.
   *
   * @param firstName the first name of the author
   * @param lastName the last name of the author
   * @param email the email of the author
   * @return the added author
   */
  public Author addAuthorToRegister(String firstName, String lastName, String email)
      throws InvalidInputException, AuthorAlreadyExistsException {
    validateAuthorName(firstName);
    validateAuthorName(lastName);
    validateAuthorEmail(email);

    if (authorRegister.checkIfAuthorExists(email)) {
      throw new AuthorAlreadyExistsException("An author with that email already exists.");
    }

    try {
      Author authorToAdd = new Author(firstName, lastName, email);
      authorRegister.addAuthor(authorToAdd);
      return authorToAdd;
    } catch (IllegalArgumentException e) {
      throw new InvalidInputException(e.getMessage());
    }



  }

  /**
   * Updates the first name of an author after validating it.
   *
   * @param author the author to update
   * @param newName the new first name to set
   * @throws InvalidInputException if the new name is invalid
   */
  public void updateAuthorFirstName(Author author, String newName)
      throws InvalidInputException {
    validateAuthorName(newName);
    author.setFirstName(newName);
  }

  /**
   * Updates the last name of an author after validating it.
   *
   * @param author the author to update
   * @param newName the new last name to set
   * @throws InvalidInputException if the new name is invalid
   */
  public void updateAuthorLastName(Author author, String newName)
      throws InvalidInputException {
    validateAuthorName(newName);
    author.setLastName(newName);
  }

  /**
   * Updates the email of an author after validating it.
   *
   * @param author the author to update
   * @param newEmail the new name to set
   * @throws InvalidInputException if the new email is invalid
   * @throws AuthorAlreadyExistsException if an author with the new email already exists
   */
  public void updateAuthorEmail(Author author, String newEmail)
      throws InvalidInputException, AuthorAlreadyExistsException {
    validateAuthorEmail(newEmail);

    if (authorRegister.checkIfAuthorExists(newEmail)) {
      throw new AuthorAlreadyExistsException("An author with that email already exists.");
    }

    author.setEmail(newEmail);
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

  /**
   * Generates a map of authors and their corresponding number of diary entries.
   *
   * @return a map where the key is the author and the value is the number of entries by that author
   */
  public Map<Author, Integer> getMapOfEntriesByAuthor() {
    Map<Author, Integer> countsByRegisteredAuthors =
        diaryController.getListOfAllRegisteredDiaryEntries().stream()
            .map(DiaryEntry::getEntryAuthor)
            .collect(Collectors.groupingBy(s -> s, Collectors.summingInt(e -> 1)));

    return authorRegister.getAllAuthors().stream()
        .collect(Collectors.toMap(
            author -> author,
            author -> countsByRegisteredAuthors.getOrDefault(author, 0)
        ));
  }

}


