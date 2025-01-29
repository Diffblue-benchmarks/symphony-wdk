package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WorkflowExecutionRequestDiffblueTest {
  /**
   * Test {@link WorkflowExecutionRequest#equals(Object)}, and
   * {@link WorkflowExecutionRequest#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowExecutionRequest#equals(Object)}
   *   <li>{@link WorkflowExecutionRequest#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
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
   * Test {@link WorkflowExecutionRequest#equals(Object)}, and
   * {@link WorkflowExecutionRequest#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowExecutionRequest#equals(Object)}
   *   <li>{@link WorkflowExecutionRequest#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
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
   * Test {@link WorkflowExecutionRequest#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowExecutionRequest#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
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
   * Test {@link WorkflowExecutionRequest#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowExecutionRequest#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    WorkflowExecutionRequest workflowExecutionRequest = new WorkflowExecutionRequest();
    workflowExecutionRequest.setArgs(new HashMap<>());

    // Act and Assert
    assertNotEquals(workflowExecutionRequest, null);
  }

  /**
   * Test {@link WorkflowExecutionRequest#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowExecutionRequest#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    WorkflowExecutionRequest workflowExecutionRequest = new WorkflowExecutionRequest();
    workflowExecutionRequest.setArgs(new HashMap<>());

    // Act and Assert
    assertNotEquals(workflowExecutionRequest, "Different type to WorkflowExecutionRequest");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link WorkflowExecutionRequest}
   *   <li>{@link WorkflowExecutionRequest#setArgs(Map)}
   *   <li>{@link WorkflowExecutionRequest#toString()}
   *   <li>{@link WorkflowExecutionRequest#getArgs()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    WorkflowExecutionRequest actualWorkflowExecutionRequest = new WorkflowExecutionRequest();
    HashMap<String, Object> args = new HashMap<>();
    actualWorkflowExecutionRequest.setArgs(args);
    String actualToStringResult = actualWorkflowExecutionRequest.toString();
    Map<String, Object> actualArgs = actualWorkflowExecutionRequest.getArgs();

    // Assert
    assertEquals("WorkflowExecutionRequest(args={})", actualToStringResult);
    assertTrue(actualArgs.isEmpty());
    assertSame(args, actualArgs);
  }
}
