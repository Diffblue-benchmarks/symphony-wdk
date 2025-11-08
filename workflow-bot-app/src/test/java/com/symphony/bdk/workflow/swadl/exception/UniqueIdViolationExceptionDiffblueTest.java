package com.symphony.bdk.workflow.swadl.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class UniqueIdViolationExceptionDiffblueTest {
  /**
   * Method under test:
   * {@link UniqueIdViolationException#UniqueIdViolationException(String, List)}
   */
  @Test
  void testNewUniqueIdViolationException() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
   * Method under test:
   * {@link UniqueIdViolationException#UniqueIdViolationException(String, List)}
   */
  @Test
  void testNewUniqueIdViolationException2() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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

  /**
   * Method under test:
   * {@link UniqueIdViolationException#UniqueIdViolationException(String, List)}
   */
  @Test
  void testNewUniqueIdViolationException3() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
}
