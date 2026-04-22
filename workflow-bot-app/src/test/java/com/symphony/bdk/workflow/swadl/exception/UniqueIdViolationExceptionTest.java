package com.symphony.bdk.workflow.swadl.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UniqueIdViolationExceptionTest {

  @Test
  @DisplayName("Constructor sets message with workflow id and empty duplicated ids")
  void testConstructor_emptyDuplicatedIds() {
    // Arrange and Act
    UniqueIdViolationException exception =
        new UniqueIdViolationException("myWorkflow", Collections.emptyList());

    // Assert
    assertEquals(
        "These ids [] are duplicated in more than one activity in workflow myWorkflow",
        exception.getMessage());
    assertEquals(
        "These ids [] are duplicated in more than one activity in workflow myWorkflow",
        exception.getLocalizedMessage());
    assertNull(exception.getCause());
    assertEquals(0, exception.getSuppressed().length);
  }

  @Test
  @DisplayName("Constructor sets message with workflow id and multiple duplicated ids")
  void testConstructor_multipleDuplicatedIds() {
    // Arrange
    List<String> duplicatedIds = Arrays.asList("activity1", "activity2");

    // Act
    UniqueIdViolationException exception =
        new UniqueIdViolationException("wf-123", duplicatedIds);

    // Assert
    assertEquals(
        "These ids [activity1, activity2] are duplicated in more than one activity in workflow wf-123",
        exception.getMessage());
    assertNull(exception.getCause());
  }
}
