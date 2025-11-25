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

    private final Map<String, Author> authorRegister;

    /**
     * Constructs an empty AuthorRegister.
     */
    public AuthorRegister() {
      this.authorRegister = new HashMap<>();
    }

    /**
     * Adds a new author to the register.
     *
     * @param author the author to add
     */
    public void addAuthor(Author author) {
      authorRegister.put(author.getEmail(), author);
    }

    /**
     * Gets a list of all authors in the register.
     *
     * @return a list of all authors
     */
    public List<Author> getAllAuthors() {
      return new ArrayList<>(authorRegister.values());
    }


  public Author getAuthorByEmail(String email) {
    if (email == null) {
      return null;
    }
    return authorRegister.get(email);


  }
}
