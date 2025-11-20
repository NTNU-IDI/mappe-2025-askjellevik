package edu.ntnu.idi.idatt.controller;

import edu.ntnu.idi.idatt.model.Author;
import edu.ntnu.idi.idatt.model.AuthorRegister;
import java.util.ArrayList;
import java.util.List;

public class AuthorController {

  private final AuthorRegister authorRegister;

  public AuthorController(AuthorRegister authorRegister) {
    this.authorRegister = authorRegister;
  }

  public void addAuthorToRegister(Author authorToAdd) {
    authorRegister.addAuthor(authorToAdd);
    }

    public List<Author> getListOfAuthorsInRegister() {
    return authorRegister.getAllAuthors();
    }


  }


