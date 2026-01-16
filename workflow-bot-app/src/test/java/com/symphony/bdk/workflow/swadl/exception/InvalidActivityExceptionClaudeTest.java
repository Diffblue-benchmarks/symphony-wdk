package com.symphony.bdk.workflow.swadl.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InvalidActivityExceptionClaudeTest {

  @Test
  void testConstructor_withValidParameters() {
    // Test that constructor properly initializes exception with formatted message
    String workflowId = "workflow-123";
    String message = "Missing required field";

    InvalidActivityException exception = new InvalidActivityException(workflowId, message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Invalid activity in the workflow workflow-123: Missing required field");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withNullWorkflowId() {
    // Test that constructor handles null workflowId
    String message = "Missing required field";

    InvalidActivityException exception = new InvalidActivityException(null, message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Invalid activity in the workflow null: Missing required field");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withNullMessage() {
    // Test that constructor handles null message
    String workflowId = "workflow-123";

    InvalidActivityException exception = new InvalidActivityException(workflowId, null);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Invalid activity in the workflow workflow-123: null");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withBothParametersNull() {
    // Test that constructor handles both parameters being null
    InvalidActivityException exception = new InvalidActivityException(null, null);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Invalid activity in the workflow null: null");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withEmptyStrings() {
    // Test that constructor handles empty string parameters
    String workflowId = "";
    String message = "";

    InvalidActivityException exception = new InvalidActivityException(workflowId, message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Invalid activity in the workflow : ");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_isRuntimeException() {
    // Test that InvalidActivityException is a RuntimeException
    String workflowId = "workflow-123";
    String message = "Invalid activity type";

    InvalidActivityException exception = new InvalidActivityException(workflowId, message);

    assertThat(exception).isInstanceOf(RuntimeException.class);
  }

  @Test
  void testConstructor_multipleInstances() {
    // Test that multiple instances can be created independently
    String workflowId1 = "workflow-1";
    String message1 = "First error";
    String workflowId2 = "workflow-2";
    String message2 = "Second error";

    InvalidActivityException exception1 = new InvalidActivityException(workflowId1, message1);
    InvalidActivityException exception2 = new InvalidActivityException(workflowId2, message2);

    assertThat(exception1).isNotNull();
    assertThat(exception2).isNotNull();
    assertThat(exception1).isNotSameAs(exception2);
    assertThat(exception1.getMessage()).isEqualTo("Invalid activity in the workflow workflow-1: First error");
    assertThat(exception2.getMessage()).isEqualTo("Invalid activity in the workflow workflow-2: Second error");
  }

  @Test
  void testConstructor_canBeThrown() {
    // Test that the exception can actually be thrown and caught
    String workflowId = "workflow-456";
    String message = "Activity not found";

    try {
      throw new InvalidActivityException(workflowId, message);
    } catch (InvalidActivityException e) {
      assertThat(e.getMessage()).isEqualTo("Invalid activity in the workflow workflow-456: Activity not found");
      assertThat(e.getCause()).isNull();
    }
  }

  @Test
  void testConstructor_canBeCaughtAsRuntimeException() {
    // Test that the exception can be caught as RuntimeException
    String workflowId = "workflow-789";
    String message = "Invalid configuration";

    try {
      throw new InvalidActivityException(workflowId, message);
    } catch (RuntimeException e) {
      assertThat(e).isInstanceOf(InvalidActivityException.class);
      assertThat(e.getMessage()).isEqualTo("Invalid activity in the workflow workflow-789: Invalid configuration");
    }
  }

  @Test
  void testConstructor_withLongParameters() {
    // Test that constructor handles long parameter strings
    String workflowId = "workflow-with-very-long-identifier-that-contains-many-characters";
    String message = "This is a very long error message that describes in detail what went wrong "
        + "during the activity validation process, including various context information "
        + "that may be useful for debugging purposes.";

    InvalidActivityException exception = new InvalidActivityException(workflowId, message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains(workflowId);
    assertThat(exception.getMessage()).contains(message);
    assertThat(exception.getMessage()).startsWith("Invalid activity in the workflow ");
  }

  @Test
  void testConstructor_withSpecialCharacters() {
    // Test that constructor handles special characters in parameters
    String workflowId = "workflow-@#$%^&*()";
    String message = "Invalid: \n\t\r special chars";

    InvalidActivityException exception = new InvalidActivityException(workflowId, message);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Invalid activity in the workflow workflow-@#$%^&*(): Invalid: \n\t\r special chars");
  }

  @Test
  void testConstructor_messageFormatting() {
    // Test that the message formatting follows the expected pattern
    String workflowId = "test-workflow";
    String message = "activity type mismatch";

    InvalidActivityException exception = new InvalidActivityException(workflowId, message);

    assertThat(exception.getMessage())
        .startsWith("Invalid activity in the workflow ")
        .contains(workflowId)
        .contains(": ")
        .endsWith(message);
  }

  @Test
  void testConstructor_withWorkflowIdContainingFormatSpecifiers() {
    // Test that the constructor handles format specifiers in workflowId without issues
    String workflowId = "workflow-%s-%d";
    String message = "Invalid activity";

    InvalidActivityException exception = new InvalidActivityException(workflowId, message);

    assertThat(exception).isNotNull();
    // The String.format should treat these as literal strings, not format specifiers
    assertThat(exception.getMessage()).contains(workflowId);
    assertThat(exception.getMessage()).contains(message);
  }

  @Test
  void testConstructor_withMessageContainingFormatSpecifiers() {
    // Test that the constructor handles format specifiers in message without issues
    String workflowId = "workflow-123";
    String message = "Invalid value: %s, expected: %d";

    InvalidActivityException exception = new InvalidActivityException(workflowId, message);

    assertThat(exception).isNotNull();
    // The String.format should treat these as literal strings, not format specifiers
    assertThat(exception.getMessage()).contains(workflowId);
    assertThat(exception.getMessage()).contains(message);
  }
}
