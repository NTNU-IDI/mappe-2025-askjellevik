package edu.ntnu.idi.idatt.controller.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

public class DiaryEntryValidatorTest {

  @Test
  void testValidateTitleTest() {
    String validTitle = "Test title that is Valid";

    List<String> errors = DiaryEntryValidator.validateTitle(validTitle);

    assertTrue(errors.isEmpty());
  }

  @Test
  void testValidateTextTest() {
    String validText = "This string is a test containing numbers, 1,2,3 and special letters @ :D !";

    List<String> errors = DiaryEntryValidator.validateText(validText);

    assertTrue(errors.isEmpty());
  }

  @Test
  void testValidateDateTest() {
    LocalDateTime validDate = LocalDateTime.of(2024, 10, 6, 18,0);

    List<String> errors = DiaryEntryValidator.validateDate(validDate);

    assertTrue(errors.isEmpty());

  }

  @Test
  void testValidateEmptyTitle() {
    String emptyTitle = "";

    List<String> errors = DiaryEntryValidator.validateTitle(emptyTitle);

    assertEquals(1, errors.size());
    assertEquals("The title can not be empty", errors.get(0));

  }

  @Test
  void testValidateEmptyText() {
    String emptyText = "";

    List<String> errors = DiaryEntryValidator.validateText(emptyText);

    assertEquals(1, errors.size());
    assertEquals("The text can not be empty", errors.get(0));

  }

  @Test
  void testValidateEmptyDate() {
    LocalDateTime emptyDate = null;

    List<String> errors = DiaryEntryValidator.validateDate(emptyDate);

    assertEquals(1, errors.size());
    assertEquals("The date can not be null", errors.get(0));

  }

  @Test
  void testValidateTooLongTitle() {
    String emptyText = "This title is more than 30 characters long and therefore it will fail";

    List<String> errors = DiaryEntryValidator.validateTitle(emptyText);

    assertEquals(1, errors.size());
    assertEquals("The title can not be longer than 30 characters", errors.get(0));

  }


  @Test
  void testValidateFutureDate() {
    LocalDateTime emptyDate = LocalDateTime.of(2050,10,4,18,0);

    List<String> errors = DiaryEntryValidator.validateDate(emptyDate);

    assertEquals(1, errors.size());
    assertEquals("The date can not be in the future", errors.get(0));

  }
  



}
