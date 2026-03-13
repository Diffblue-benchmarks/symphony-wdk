package com.symphony.bdk.workflow.swadl.exception;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UniqueIdViolationExceptionTest {

  @Test
  void shouldCreateExceptionWithFormattedMessage() {
    // Arrange
    String workflowId = "workflow-123";
    List<String> duplicatedIds = Arrays.asList("activity-1", "activity-2");

    // Act
    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    // Assert
    assertThat(exception.getMessage())
        .contains("activity-1", "activity-2")
        .contains("workflow-123")
        .contains("duplicated in more than one activity");
  }

  @Test
  void shouldCreateExceptionWithSingleDuplicatedId() {
    // Arrange
    String workflowId = "test-workflow";
    List<String> duplicatedIds = Arrays.asList("duplicate-id");

    // Act
    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    // Assert
    assertThat(exception.getMessage())
        .contains("duplicate-id")
        .contains("test-workflow");
  }

  @Test
  void shouldCreateExceptionWithEmptyDuplicatedIds() {
    // Arrange
    String workflowId = "empty-workflow";
    List<String> duplicatedIds = Arrays.asList();

    // Act
    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    // Assert
    assertThat(exception.getMessage())
        .contains("empty-workflow")
        .isNotEmpty();
  }
}
