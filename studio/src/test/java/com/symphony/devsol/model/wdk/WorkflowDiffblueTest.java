package com.symphony.devsol.model.wdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class WorkflowDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Workflow#equals(Object)}
   *   <li>{@link Workflow#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Workflow workflow = new Workflow();
    workflow.setActive(true);
    workflow.setCreatedBy(1L);

    Workflow workflow2 = new Workflow();
    workflow2.setActive(true);
    workflow2.setCreatedBy(1L);

    // Act and Assert
    assertEquals(workflow, workflow2);
    int expectedHashCodeResult = workflow.hashCode();
    assertEquals(expectedHashCodeResult, workflow2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Workflow#equals(Object)}
   *   <li>{@link Workflow#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Workflow workflow = new Workflow();
    workflow.setActive(true);
    workflow.setCreatedBy(1L);

    // Act and Assert
    assertEquals(workflow, workflow);
    int expectedHashCodeResult = workflow.hashCode();
    assertEquals(expectedHashCodeResult, workflow.hashCode());
  }

  /**
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Workflow workflow = new Workflow();
    workflow.setActive(false);
    workflow.setCreatedBy(1L);

    Workflow workflow2 = new Workflow();
    workflow2.setActive(true);
    workflow2.setCreatedBy(1L);

    // Act and Assert
    assertNotEquals(workflow, workflow2);
  }

  /**
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Workflow workflow = new Workflow();
    workflow.setActive(true);
    workflow.setCreatedBy(3L);

    Workflow workflow2 = new Workflow();
    workflow2.setActive(true);
    workflow2.setCreatedBy(1L);

    // Act and Assert
    assertNotEquals(workflow, workflow2);
  }

  /**
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Workflow workflow = new Workflow();
    workflow.setActive(true);
    workflow.setCreatedBy(1L);

    // Act and Assert
    assertNotEquals(workflow, null);
  }

  /**
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Workflow workflow = new Workflow();
    workflow.setActive(true);
    workflow.setCreatedBy(1L);

    // Act and Assert
    assertNotEquals(workflow, "Different type to Workflow");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Workflow}
   *   <li>{@link Workflow#setActive(boolean)}
   *   <li>{@link Workflow#setCreatedBy(long)}
   *   <li>{@link Workflow#toString()}
   *   <li>{@link Workflow#getCreatedBy()}
   *   <li>{@link Workflow#isActive()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    Workflow actualWorkflow = new Workflow();
    actualWorkflow.setActive(true);
    actualWorkflow.setCreatedBy(1L);
    String actualToStringResult = actualWorkflow.toString();
    long actualCreatedBy = actualWorkflow.getCreatedBy();

    // Assert that nothing has changed
    assertEquals("Workflow(active=true, createdBy=1)", actualToStringResult);
    assertEquals(1L, actualCreatedBy);
    assertTrue(actualWorkflow.isActive());
  }
}
