package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.controller.validators.AuthorValidator;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.AuthorRegister;
import edu.ntnu.idi.idatt.model.DiaryEntry;
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
   * @param authorRegister the AuthorController to be managed by this controller
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
   *
   * @param firstName the first name of the author
   * @param lastName the last name of the author
   * @param email the email of the author
   * @return the added author
   */
  public Author addAuthorToRegister(String firstName, String lastName, String email) {
    Author authorToAdd = new Author(firstName, lastName, email);
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


