package com.symphony.bdk.workflow.swadl.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NoStartingEventExceptionClaudeTest {

  @Test
  void testConstructor_withValidWorkflowId() {
    // Test that constructor properly initializes exception with formatted message
    String workflowId = "workflow-123";

    NoStartingEventException exception = new NoStartingEventException(workflowId);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Workflow with id \"workflow-123\" does not have any starting event.");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withNullWorkflowId() {
    // Test that constructor handles null workflowId
    NoStartingEventException exception = new NoStartingEventException(null);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Workflow with id \"null\" does not have any starting event.");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withEmptyWorkflowId() {
    // Test that constructor handles empty string workflowId
    String workflowId = "";

    NoStartingEventException exception = new NoStartingEventException(workflowId);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Workflow with id \"\" does not have any starting event.");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_isRuntimeException() {
    // Test that NoStartingEventException is a RuntimeException
    String workflowId = "workflow-123";

    NoStartingEventException exception = new NoStartingEventException(workflowId);

    assertThat(exception).isInstanceOf(RuntimeException.class);
  }

  @Test
  void testConstructor_multipleInstances() {
    // Test that multiple instances can be created independently
    String workflowId1 = "workflow-1";
    String workflowId2 = "workflow-2";

    NoStartingEventException exception1 = new NoStartingEventException(workflowId1);
    NoStartingEventException exception2 = new NoStartingEventException(workflowId2);

    assertThat(exception1).isNotNull();
    assertThat(exception2).isNotNull();
    assertThat(exception1).isNotSameAs(exception2);
    assertThat(exception1.getMessage()).isEqualTo("Workflow with id \"workflow-1\" does not have any starting event.");
    assertThat(exception2.getMessage()).isEqualTo("Workflow with id \"workflow-2\" does not have any starting event.");
  }

  @Test
  void testConstructor_canBeThrown() {
    // Test that the exception can actually be thrown and caught
    String workflowId = "workflow-456";

    try {
      throw new NoStartingEventException(workflowId);
    } catch (NoStartingEventException e) {
      assertThat(e.getMessage()).isEqualTo("Workflow with id \"workflow-456\" does not have any starting event.");
      assertThat(e.getCause()).isNull();
    }
  }

  @Test
  void testConstructor_canBeCaughtAsRuntimeException() {
    // Test that the exception can be caught as RuntimeException
    String workflowId = "workflow-789";

    try {
      throw new NoStartingEventException(workflowId);
    } catch (RuntimeException e) {
      assertThat(e).isInstanceOf(NoStartingEventException.class);
      assertThat(e.getMessage()).isEqualTo("Workflow with id \"workflow-789\" does not have any starting event.");
    }
  }

  @Test
  void testConstructor_withLongWorkflowId() {
    // Test that constructor handles long workflowId string
    String workflowId = "workflow-with-very-long-identifier-that-contains-many-characters-and-continues-for-quite-a-while";

    NoStartingEventException exception = new NoStartingEventException(workflowId);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains(workflowId);
    assertThat(exception.getMessage()).startsWith("Workflow with id \"");
    assertThat(exception.getMessage()).endsWith("\" does not have any starting event.");
  }

  @Test
  void testConstructor_withSpecialCharacters() {
    // Test that constructor handles special characters in workflowId
    String workflowId = "workflow-@#$%^&*()";

    NoStartingEventException exception = new NoStartingEventException(workflowId);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Workflow with id \"workflow-@#$%^&*()\" does not have any starting event.");
  }

  @Test
  void testConstructor_withNewlineCharacters() {
    // Test that constructor handles newline and tab characters in workflowId
    String workflowId = "workflow-\n\t\r-with-whitespace";

    NoStartingEventException exception = new NoStartingEventException(workflowId);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("Workflow with id \"workflow-\n\t\r-with-whitespace\" does not have any starting event.");
  }

  @Test
  void testConstructor_messageFormatting() {
    // Test that the message formatting follows the expected pattern
    String workflowId = "test-workflow";

    NoStartingEventException exception = new NoStartingEventException(workflowId);

    assertThat(exception.getMessage())
        .startsWith("Workflow with id \"")
        .contains(workflowId)
        .endsWith("\" does not have any starting event.");
  }

  @Test
  void testConstructor_withWorkflowIdContainingQuotes() {
    // Test that constructor handles quotes in workflowId
    String workflowId = "workflow-\"quoted\"";

    NoStartingEventException exception = new NoStartingEventException(workflowId);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains(workflowId);
    assertThat(exception.getMessage()).isEqualTo("Workflow with id \"workflow-\"quoted\"\" does not have any starting event.");
  }

  @Test
  void testConstructor_withWorkflowIdContainingFormatSpecifiers() {
    // Test that the constructor handles format specifiers in workflowId
    String workflowId = "workflow-%s-%d";

    NoStartingEventException exception = new NoStartingEventException(workflowId);

    assertThat(exception).isNotNull();
    // The String.format should treat these as literal strings, not format specifiers
    assertThat(exception.getMessage()).contains(workflowId);
    assertThat(exception.getMessage()).isEqualTo("Workflow with id \"workflow-%s-%d\" does not have any starting event.");
  }
}
