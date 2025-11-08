package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import org.junit.jupiter.api.Test;

class WorkflowExecutionRequestDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowExecutionRequest#equals(Object)}
   *   <li>{@link WorkflowExecutionRequest#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    WorkflowExecutionRequest workflowExecutionRequest = new WorkflowExecutionRequest();
    workflowExecutionRequest.setArgs(new HashMap<>());

    WorkflowExecutionRequest workflowExecutionRequest2 = new WorkflowExecutionRequest();
    workflowExecutionRequest2.setArgs(new HashMap<>());

    // Act and Assert
    assertEquals(workflowExecutionRequest, workflowExecutionRequest2);
    int expectedHashCodeResult = workflowExecutionRequest.hashCode();
    assertEquals(expectedHashCodeResult, workflowExecutionRequest2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowExecutionRequest#equals(Object)}
   *   <li>{@link WorkflowExecutionRequest#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    WorkflowExecutionRequest workflowExecutionRequest = new WorkflowExecutionRequest();
    workflowExecutionRequest.setArgs(new HashMap<>());

    // Act and Assert
    assertEquals(workflowExecutionRequest, workflowExecutionRequest);
    int expectedHashCodeResult = workflowExecutionRequest.hashCode();
    assertEquals(expectedHashCodeResult, workflowExecutionRequest.hashCode());
  }

  /**
   * Method under test: {@link WorkflowExecutionRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    HashMap<String, Object> args = new HashMap<>();
    args.put("foo", "42");

    WorkflowExecutionRequest workflowExecutionRequest = new WorkflowExecutionRequest();
    workflowExecutionRequest.setArgs(args);

    WorkflowExecutionRequest workflowExecutionRequest2 = new WorkflowExecutionRequest();
    workflowExecutionRequest2.setArgs(new HashMap<>());

    // Act and Assert
    assertNotEquals(workflowExecutionRequest, workflowExecutionRequest2);
  }

  /**
   * Method under test: {@link WorkflowExecutionRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    HashMap<String, Object> args = new HashMap<>();
    args.computeIfPresent("foo", mock(BiFunction.class));
    args.put("foo", "42");

    WorkflowExecutionRequest workflowExecutionRequest = new WorkflowExecutionRequest();
    workflowExecutionRequest.setArgs(args);

    WorkflowExecutionRequest workflowExecutionRequest2 = new WorkflowExecutionRequest();
    workflowExecutionRequest2.setArgs(new HashMap<>());

    // Act and Assert
    assertNotEquals(workflowExecutionRequest, workflowExecutionRequest2);
  }

  /**
   * Method under test: {@link WorkflowExecutionRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    WorkflowExecutionRequest workflowExecutionRequest = new WorkflowExecutionRequest();
    workflowExecutionRequest.setArgs(new HashMap<>());

    // Act and Assert
    assertNotEquals(workflowExecutionRequest, null);
  }

  /**
   * Method under test: {@link WorkflowExecutionRequest#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    WorkflowExecutionRequest workflowExecutionRequest = new WorkflowExecutionRequest();
    workflowExecutionRequest.setArgs(new HashMap<>());

    // Act and Assert
    assertNotEquals(workflowExecutionRequest, "Different type to WorkflowExecutionRequest");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link WorkflowExecutionRequest}
   *   <li>{@link WorkflowExecutionRequest#setArgs(Map)}
   *   <li>{@link WorkflowExecutionRequest#toString()}
   *   <li>{@link WorkflowExecutionRequest#getArgs()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    WorkflowExecutionRequest actualWorkflowExecutionRequest = new WorkflowExecutionRequest();
    HashMap<String, Object> args = new HashMap<>();
    actualWorkflowExecutionRequest.setArgs(args);
    String actualToStringResult = actualWorkflowExecutionRequest.toString();
    Map<String, Object> actualArgs = actualWorkflowExecutionRequest.getArgs();

    // Assert that nothing has changed
    assertEquals("WorkflowExecutionRequest(args={})", actualToStringResult);
    assertTrue(actualArgs.isEmpty());
    assertSame(args, actualArgs);
  }
}
