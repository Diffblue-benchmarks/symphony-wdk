package com.symphony.devsol.model.wdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class WorkflowDiffblueTest {
  /**
   * Test {@link Workflow#equals(Object)}, and {@link Workflow#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Workflow#equals(Object)}
   *   <li>{@link Workflow#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
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
   * Test {@link Workflow#equals(Object)}, and {@link Workflow#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Workflow#equals(Object)}
   *   <li>{@link Workflow#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
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
   * Test {@link Workflow#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
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
   * Test {@link Workflow#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
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
   * Test {@link Workflow#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Workflow workflow = new Workflow();
    workflow.setActive(true);
    workflow.setCreatedBy(1L);

    // Act and Assert
    assertNotEquals(workflow, null);
  }

  /**
   * Test {@link Workflow#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Workflow#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Workflow.equals(Object)", "int Workflow.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Workflow workflow = new Workflow();
    workflow.setActive(true);
    workflow.setCreatedBy(1L);

    // Act and Assert
    assertNotEquals(workflow, "Different type to Workflow");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Workflow.<init>()", "long Workflow.getCreatedBy()", "boolean Workflow.isActive()",
      "void Workflow.setActive(boolean)", "void Workflow.setCreatedBy(long)", "String Workflow.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    Workflow actualWorkflow = new Workflow();
    actualWorkflow.setActive(true);
    actualWorkflow.setCreatedBy(1L);
    String actualToStringResult = actualWorkflow.toString();
    long actualCreatedBy = actualWorkflow.getCreatedBy();

    // Assert
    assertEquals("Workflow(active=true, createdBy=1)", actualToStringResult);
    assertEquals(1L, actualCreatedBy);
    assertTrue(actualWorkflow.isActive());
  }
}
