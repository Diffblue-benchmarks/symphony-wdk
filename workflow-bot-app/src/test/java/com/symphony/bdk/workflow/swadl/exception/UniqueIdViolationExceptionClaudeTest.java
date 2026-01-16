package com.symphony.bdk.workflow.swadl.exception;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UniqueIdViolationExceptionClaudeTest {

  @Test
  void testConstructor_withValidParameters() {
    // Test that constructor properly initializes exception with formatted message
    String workflowId = "workflow-123";
    List<String> duplicatedIds = Arrays.asList("id-1", "id-2", "id-3");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("These ids [id-1, id-2, id-3] are duplicated in more than one activity in workflow workflow-123");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withSingleDuplicatedId() {
    // Test that constructor handles a list with a single duplicated ID
    String workflowId = "workflow-456";
    List<String> duplicatedIds = Collections.singletonList("duplicated-id");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("These ids [duplicated-id] are duplicated in more than one activity in workflow workflow-456");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withEmptyList() {
    // Test that constructor handles an empty list of duplicated IDs
    String workflowId = "workflow-789";
    List<String> duplicatedIds = Collections.emptyList();

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("These ids [] are duplicated in more than one activity in workflow workflow-789");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withNullWorkflowId() {
    // Test that constructor handles null workflowId
    List<String> duplicatedIds = Arrays.asList("id-1", "id-2");

    UniqueIdViolationException exception = new UniqueIdViolationException(null, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("These ids [id-1, id-2] are duplicated in more than one activity in workflow null");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withNullDuplicatedIds() {
    // Test that constructor handles null duplicatedIds list
    String workflowId = "workflow-abc";

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, null);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("These ids null are duplicated in more than one activity in workflow workflow-abc");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withBothParametersNull() {
    // Test that constructor handles both parameters being null
    UniqueIdViolationException exception = new UniqueIdViolationException(null, null);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("These ids null are duplicated in more than one activity in workflow null");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withEmptyWorkflowId() {
    // Test that constructor handles empty string workflowId
    String workflowId = "";
    List<String> duplicatedIds = Arrays.asList("id-1", "id-2");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("These ids [id-1, id-2] are duplicated in more than one activity in workflow ");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_withEmptyStringsInList() {
    // Test that constructor handles empty strings in the duplicated IDs list
    String workflowId = "workflow-def";
    List<String> duplicatedIds = Arrays.asList("", "", "id-1");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("These ids [, , id-1] are duplicated in more than one activity in workflow workflow-def");
    assertThat(exception.getCause()).isNull();
  }

  @Test
  void testConstructor_isRuntimeException() {
    // Test that UniqueIdViolationException is a RuntimeException
    String workflowId = "workflow-test";
    List<String> duplicatedIds = Arrays.asList("id-1", "id-2");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isInstanceOf(RuntimeException.class);
  }

  @Test
  void testConstructor_multipleInstances() {
    // Test that multiple instances can be created independently
    String workflowId1 = "workflow-1";
    List<String> duplicatedIds1 = Arrays.asList("id-1", "id-2");
    String workflowId2 = "workflow-2";
    List<String> duplicatedIds2 = Arrays.asList("id-3", "id-4", "id-5");

    UniqueIdViolationException exception1 = new UniqueIdViolationException(workflowId1, duplicatedIds1);
    UniqueIdViolationException exception2 = new UniqueIdViolationException(workflowId2, duplicatedIds2);

    assertThat(exception1).isNotNull();
    assertThat(exception2).isNotNull();
    assertThat(exception1).isNotSameAs(exception2);
    assertThat(exception1.getMessage()).isEqualTo("These ids [id-1, id-2] are duplicated in more than one activity in workflow workflow-1");
    assertThat(exception2.getMessage()).isEqualTo("These ids [id-3, id-4, id-5] are duplicated in more than one activity in workflow workflow-2");
  }

  @Test
  void testConstructor_canBeThrown() {
    // Test that the exception can actually be thrown and caught
    String workflowId = "workflow-throw-test";
    List<String> duplicatedIds = Arrays.asList("duplicate-1", "duplicate-2");

    try {
      throw new UniqueIdViolationException(workflowId, duplicatedIds);
    } catch (UniqueIdViolationException e) {
      assertThat(e.getMessage()).isEqualTo("These ids [duplicate-1, duplicate-2] are duplicated in more than one activity in workflow workflow-throw-test");
      assertThat(e.getCause()).isNull();
    }
  }

  @Test
  void testConstructor_canBeCaughtAsRuntimeException() {
    // Test that the exception can be caught as RuntimeException
    String workflowId = "workflow-runtime-test";
    List<String> duplicatedIds = Arrays.asList("id-a", "id-b");

    try {
      throw new UniqueIdViolationException(workflowId, duplicatedIds);
    } catch (RuntimeException e) {
      assertThat(e).isInstanceOf(UniqueIdViolationException.class);
      assertThat(e.getMessage()).isEqualTo("These ids [id-a, id-b] are duplicated in more than one activity in workflow workflow-runtime-test");
    }
  }

  @Test
  void testConstructor_withLongWorkflowId() {
    // Test that constructor handles long workflow ID
    String workflowId = "workflow-with-very-long-identifier-that-contains-many-characters-and-continues-for-testing-purposes";
    List<String> duplicatedIds = Arrays.asList("id-1", "id-2");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains(workflowId);
    assertThat(exception.getMessage()).contains("[id-1, id-2]");
    assertThat(exception.getMessage()).startsWith("These ids ");
  }

  @Test
  void testConstructor_withManyDuplicatedIds() {
    // Test that constructor handles a large list of duplicated IDs
    String workflowId = "workflow-many-ids";
    List<String> duplicatedIds = Arrays.asList(
        "id-1", "id-2", "id-3", "id-4", "id-5",
        "id-6", "id-7", "id-8", "id-9", "id-10"
    );

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains(workflowId);
    assertThat(exception.getMessage()).contains("id-1");
    assertThat(exception.getMessage()).contains("id-10");
    assertThat(exception.getMessage()).contains("These ids ");
  }

  @Test
  void testConstructor_withSpecialCharactersInWorkflowId() {
    // Test that constructor handles special characters in workflowId
    String workflowId = "workflow-@#$%^&*()";
    List<String> duplicatedIds = Arrays.asList("id-1", "id-2");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).isEqualTo("These ids [id-1, id-2] are duplicated in more than one activity in workflow workflow-@#$%^&*()");
  }

  @Test
  void testConstructor_withSpecialCharactersInDuplicatedIds() {
    // Test that constructor handles special characters in duplicated IDs
    String workflowId = "workflow-special";
    List<String> duplicatedIds = Arrays.asList("id-@#$", "id-\n\t\r", "id-%^&*");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("id-@#$");
    assertThat(exception.getMessage()).contains("id-\n\t\r");
    assertThat(exception.getMessage()).contains("id-%^&*");
  }

  @Test
  void testConstructor_messageFormatting() {
    // Test that the message formatting follows the expected pattern
    String workflowId = "test-workflow";
    List<String> duplicatedIds = Arrays.asList("dup-1", "dup-2");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception.getMessage())
        .startsWith("These ids ")
        .contains("[dup-1, dup-2]")
        .contains("are duplicated in more than one activity in workflow ")
        .endsWith(workflowId);
  }

  @Test
  void testConstructor_withWorkflowIdContainingFormatSpecifiers() {
    // Test that the constructor handles format specifiers in workflowId
    String workflowId = "workflow-%s-%d";
    List<String> duplicatedIds = Arrays.asList("id-1");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains(workflowId);
    assertThat(exception.getMessage()).contains("[id-1]");
  }

  @Test
  void testConstructor_withDuplicatedIdsContainingFormatSpecifiers() {
    // Test that the constructor handles format specifiers in duplicated IDs
    String workflowId = "workflow-format";
    List<String> duplicatedIds = Arrays.asList("id-%s", "id-%d", "id-%n");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains(workflowId);
    assertThat(exception.getMessage()).contains("id-%s");
    assertThat(exception.getMessage()).contains("id-%d");
  }

  @Test
  void testConstructor_withNullValuesInList() {
    // Test that constructor handles null values within the duplicated IDs list
    String workflowId = "workflow-nulls";
    List<String> duplicatedIds = Arrays.asList("id-1", null, "id-3");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("id-1");
    assertThat(exception.getMessage()).contains("null");
    assertThat(exception.getMessage()).contains("id-3");
  }

  @Test
  void testConstructor_withUnicodeCharacters() {
    // Test that constructor handles Unicode characters
    String workflowId = "workflow-测试-テスト";
    List<String> duplicatedIds = Arrays.asList("id-中文", "id-日本語", "id-한글");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains(workflowId);
    assertThat(exception.getMessage()).contains("id-中文");
    assertThat(exception.getMessage()).contains("id-日本語");
    assertThat(exception.getMessage()).contains("id-한글");
  }

  @Test
  void testConstructor_withWhitespaceInParameters() {
    // Test that constructor handles whitespace in parameters
    String workflowId = "  workflow-whitespace  ";
    List<String> duplicatedIds = Arrays.asList("  id-1  ", "id-2", "  id-3");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    assertThat(exception).isNotNull();
    assertThat(exception.getMessage()).contains("  workflow-whitespace  ");
    assertThat(exception.getMessage()).contains("  id-1  ");
  }

  @Test
  void testConstructor_messageStructure() {
    // Test that message has the correct structure with all expected parts
    String workflowId = "workflow-structure";
    List<String> duplicatedIds = Arrays.asList("id-x", "id-y");

    UniqueIdViolationException exception = new UniqueIdViolationException(workflowId, duplicatedIds);

    String message = exception.getMessage();
    assertThat(message)
        .contains("These ids")
        .contains("[id-x, id-y]")
        .contains("are duplicated in more than one activity in workflow")
        .contains("workflow-structure");
  }
}
