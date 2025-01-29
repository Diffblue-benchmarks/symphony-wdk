package com.symphony.bdk.workflow.swadl.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UniqueIdViolationExceptionDiffblueTest {
  /**
   * Test
   * {@link UniqueIdViolationException#UniqueIdViolationException(String, List)}.
   * <p>
   * Method under test:
   * {@link UniqueIdViolationException#UniqueIdViolationException(String, List)}
   */
  @Test
  @DisplayName("Test new UniqueIdViolationException(String, List)")
  void testNewUniqueIdViolationException() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    UniqueIdViolationException actualUniqueIdViolationException = new UniqueIdViolationException("42",
        new ArrayList<>());

    // Assert
    assertEquals("These ids [] are duplicated in more than one activity in workflow 42",
        actualUniqueIdViolationException.getLocalizedMessage());
    assertEquals("These ids [] are duplicated in more than one activity in workflow 42",
        actualUniqueIdViolationException.getMessage());
    assertNull(actualUniqueIdViolationException.getCause());
    assertEquals(0, actualUniqueIdViolationException.getSuppressed().length);
  }

  /**
   * Test
   * {@link UniqueIdViolationException#UniqueIdViolationException(String, List)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UniqueIdViolationException#UniqueIdViolationException(String, List)}
   */
  @Test
  @DisplayName("Test new UniqueIdViolationException(String, List); given 'foo'; when ArrayList() add 'foo'")
  void testNewUniqueIdViolationException_givenFoo_whenArrayListAddFoo() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ArrayList<String> duplicatedIds = new ArrayList<>();
    duplicatedIds.add("foo");
    duplicatedIds.add("These ids %s are duplicated in more than one activity in workflow %s");

    // Act
    UniqueIdViolationException actualUniqueIdViolationException = new UniqueIdViolationException("42", duplicatedIds);

    // Assert
    assertEquals("These ids [foo, These ids %s are duplicated in more than one activity in workflow %s] are duplicated"
        + " in more than one activity in workflow 42", actualUniqueIdViolationException.getLocalizedMessage());
    assertEquals("These ids [foo, These ids %s are duplicated in more than one activity in workflow %s] are duplicated"
        + " in more than one activity in workflow 42", actualUniqueIdViolationException.getMessage());
    assertNull(actualUniqueIdViolationException.getCause());
    assertEquals(0, actualUniqueIdViolationException.getSuppressed().length);
  }

  /**
   * Test
   * {@link UniqueIdViolationException#UniqueIdViolationException(String, List)}.
   * <ul>
   *   <li>Then return LocalizedMessage is a string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UniqueIdViolationException#UniqueIdViolationException(String, List)}
   */
  @Test
  @DisplayName("Test new UniqueIdViolationException(String, List); then return LocalizedMessage is a string")
  void testNewUniqueIdViolationException_thenReturnLocalizedMessageIsAString() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ArrayList<String> duplicatedIds = new ArrayList<>();
    duplicatedIds.add("These ids %s are duplicated in more than one activity in workflow %s");

    // Act
    UniqueIdViolationException actualUniqueIdViolationException = new UniqueIdViolationException("42", duplicatedIds);

    // Assert
    assertEquals("These ids [These ids %s are duplicated in more than one activity in workflow %s] are duplicated in"
        + " more than one activity in workflow 42", actualUniqueIdViolationException.getLocalizedMessage());
    assertEquals("These ids [These ids %s are duplicated in more than one activity in workflow %s] are duplicated in"
        + " more than one activity in workflow 42", actualUniqueIdViolationException.getMessage());
    assertNull(actualUniqueIdViolationException.getCause());
    assertEquals(0, actualUniqueIdViolationException.getSuppressed().length);
  }
}
