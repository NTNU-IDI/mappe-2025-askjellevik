package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.controller.exceptions.InvalidInputException;
import edu.ntnu.idi.idatt.controller.validators.AuthorValidator;
import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.AuthorRegister;

import edu.ntnu.idi.idatt.model.exceptions.AuthorNotFoundException;
import java.util.List;

public class AuthorController {

    private final AuthorRegister authorRegister;
    private final DiaryController diaryController;

    public AuthorController(AuthorRegister authorRegister, DiaryController diaryController) {
        this.authorRegister = authorRegister;
        this.diaryController = diaryController;
    }

    public Author addAuthorToRegister(String name, String email) {
      Author authorToAdd = new Author(name, email);
        authorRegister.addAuthor(authorToAdd);
        return authorToAdd;
    }

    public List<Author> getListOfAuthorsInRegister() {
        return authorRegister.getAllAuthors();
    }

    public Integer getAmountOfEntriesBySpecificAuthor(Author author) {
        return diaryController.getAmountOfEntriesBySpecificAuthor(author);
    }

    public void setNewEmail(Author author, String newEmail) {
        author.setEmail(newEmail);
    }

    public void setNewName(Author author, String newName) {
        author.setName(newName);
    }

    public void validateAuthorName(String newName) throws InvalidInputException {
      List<String> errors = AuthorValidator.validateName(newName);

      if (!errors.isEmpty()) {
        throw new InvalidInputException(String.join(". ", errors));
      }

    }

    public void validateAuthorEmail(String newEmail) throws InvalidInputException {
      List<String> errors = AuthorValidator.validateEmail(newEmail);

      if (!errors.isEmpty()) {
        throw new InvalidInputException(String.join(". ", errors));
      }
    }

    public Author getAuthorByEmail(String email) throws AuthorNotFoundException {
      Author author = authorRegister.getAuthorByEmail(email);
      if (author == null) {
        throw new AuthorNotFoundException("Did not find an author with that email");
      }
      return author;
    }
}


