package edu.ntnu.idi.idatt.controller.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

public class AuthorValidatorTest {


  @Test
  void testValidateNameTest() {
    String positiveName = "André Navnesen-Skjellevik";

    List<String> errors = AuthorValidator.validateName(positiveName);

    assertTrue(errors.isEmpty());

  }

  @Test
  void testValidateNameWithNumbersTest() {
    String numberName = "André 123ellevik";

    List<String> errors = AuthorValidator.validateName(numberName);

    assertEquals(1, errors.size());
    assertEquals("The name does not fit the name format", errors.get(0));

  }

  @Test
  void testValidateNameWithOneCharTest() {
    String numberName = "A";

    List<String> errors = AuthorValidator.validateName(numberName);

    assertEquals(1, errors.size());
    assertEquals("The name can not be less than 2 characters", errors.get(0));

  }

  @Test
  void testValidateNameWithOneNumberTest() {
    String shortNumberName = "1";

    List<String> errors = AuthorValidator.validateName(shortNumberName);

    assertEquals(2, errors.size());
    assertTrue(errors.contains("The name does not fit the name format"));
    assertTrue(errors.contains("The name can not be less than 2 characters"));


  }


  @Test
  void testValidateEmailTest(){
    String positiveEmail = "test@domain.com";

    List<String> errors = AuthorValidator.validateEmail(positiveEmail);

    assertTrue(errors.isEmpty());
  }

  @Test
  void testValidateEmailWithNoAtTest(){
    String noAtEmail = "test.domain";

    List<String> errors = AuthorValidator.validateEmail(noAtEmail);

    assertEquals(1, errors.size());
    assertEquals("The email does not fit the email format", errors.get(0));
  }

  @Test
  void testValidateEmailWithNoDomainTest(){
    String noDomainEmail = "test@";

    List<String> errors = AuthorValidator.validateEmail(noDomainEmail);

    assertEquals(1, errors.size());
    assertEquals("The email does not fit the email format", errors.get(0));
  }

  @Test
  void testValidateEmptyEmail() {
    String emptyEmail = "";

    List<String> errors = AuthorValidator.validateEmail(emptyEmail);

    assertEquals(1, errors.size());
    assertEquals("The email can not be empty", errors.get(0));

  }

  @Test
  void testValidateEmptyName() {
    String emptyName = "";

    List<String> errors = AuthorValidator.validateName(emptyName);

    assertEquals(1, errors.size());
    assertEquals("The name can not be empty", errors.get(0));

  }




}
