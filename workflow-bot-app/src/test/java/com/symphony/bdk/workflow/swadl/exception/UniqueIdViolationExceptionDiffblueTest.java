package com.symphony.bdk.workflow.swadl.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UniqueIdViolationException.class, String.class})
@ExtendWith(SpringExtension.class)
class UniqueIdViolationExceptionDiffblueTest {
  @Autowired private List<String> list;

  @Autowired private UniqueIdViolationException uniqueIdViolationException;

  /**
   * Test {@link UniqueIdViolationException#UniqueIdViolationException(String, List)}.
   *
   * <p>Method under test: {@link UniqueIdViolationException#UniqueIdViolationException(String,
   * List)}
   */
  @Test
  @DisplayName("Test new UniqueIdViolationException(String, List)")
  @Tag("MaintainedByDiffblue")
  void testNewUniqueIdViolationException() {
    // Arrange and Act
    UniqueIdViolationException actualUniqueIdViolationException =
        new UniqueIdViolationException("42", new ArrayList<>());

    // Assert
    assertEquals(
        "These ids [] are duplicated in more than one activity in workflow 42",
        actualUniqueIdViolationException.getLocalizedMessage());
    assertEquals(
        "These ids [] are duplicated in more than one activity in workflow 42",
        actualUniqueIdViolationException.getMessage());
    assertNull(actualUniqueIdViolationException.getCause());
    assertEquals(0, actualUniqueIdViolationException.getSuppressed().length);
  }

  /**
   * Test {@link UniqueIdViolationException#UniqueIdViolationException(String, List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link UniqueIdViolationException#UniqueIdViolationException(String,
   * List)}
   */
  @Test
  @DisplayName(
      "Test new UniqueIdViolationException(String, List); given 'foo'; when ArrayList() add 'foo'")
  @Tag("MaintainedByDiffblue")
  void testNewUniqueIdViolationException_givenFoo_whenArrayListAddFoo() {
    // Arrange
    ArrayList<String> duplicatedIds = new ArrayList<>();
    duplicatedIds.add("foo");
    duplicatedIds.add("These ids %s are duplicated in more than one activity in workflow %s");

    // Act
    UniqueIdViolationException actualUniqueIdViolationException =
        new UniqueIdViolationException("42", duplicatedIds);

    // Assert
    assertEquals(
        "These ids [foo, These ids %s are duplicated in more than one activity in workflow %s] are duplicated"
            + " in more than one activity in workflow 42",
        actualUniqueIdViolationException.getLocalizedMessage());
    assertEquals(
        "These ids [foo, These ids %s are duplicated in more than one activity in workflow %s] are duplicated"
            + " in more than one activity in workflow 42",
        actualUniqueIdViolationException.getMessage());
    assertNull(actualUniqueIdViolationException.getCause());
    assertEquals(0, actualUniqueIdViolationException.getSuppressed().length);
  }

  /**
   * Test {@link UniqueIdViolationException#UniqueIdViolationException(String, List)}.
   *
   * <ul>
   *   <li>Then return LocalizedMessage is a string.
   * </ul>
   *
   * <p>Method under test: {@link UniqueIdViolationException#UniqueIdViolationException(String,
   * List)}
   */
  @Test
  @DisplayName(
      "Test new UniqueIdViolationException(String, List); then return LocalizedMessage is a string")
  @Tag("MaintainedByDiffblue")
  void testNewUniqueIdViolationException_thenReturnLocalizedMessageIsAString() {
    // Arrange
    ArrayList<String> duplicatedIds = new ArrayList<>();
    duplicatedIds.add("These ids %s are duplicated in more than one activity in workflow %s");

    // Act
    UniqueIdViolationException actualUniqueIdViolationException =
        new UniqueIdViolationException("42", duplicatedIds);

    // Assert
    assertEquals(
        "These ids [These ids %s are duplicated in more than one activity in workflow %s] are duplicated in"
            + " more than one activity in workflow 42",
        actualUniqueIdViolationException.getLocalizedMessage());
    assertEquals(
        "These ids [These ids %s are duplicated in more than one activity in workflow %s] are duplicated in"
            + " more than one activity in workflow 42",
        actualUniqueIdViolationException.getMessage());
    assertNull(actualUniqueIdViolationException.getCause());
    assertEquals(0, actualUniqueIdViolationException.getSuppressed().length);
  }
}
