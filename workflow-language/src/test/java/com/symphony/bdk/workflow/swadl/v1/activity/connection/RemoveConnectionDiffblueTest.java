package com.symphony.bdk.workflow.swadl.v1.activity.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RemoveConnectionDiffblueTest {
  /**
   * Test {@link RemoveConnection#equals(Object)}, and {@link RemoveConnection#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveConnection#equals(Object)}
   *   <li>{@link RemoveConnection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveConnection.equals(Object)", "int RemoveConnection.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    RemoveConnection removeConnection = new RemoveConnection();
    RemoveConnection removeConnection2 = new RemoveConnection();

    // Act and Assert
    assertEquals(removeConnection, removeConnection2);
    int expectedHashCodeResult = removeConnection.hashCode();
    assertEquals(expectedHashCodeResult, removeConnection2.hashCode());
  }

  /**
   * Test {@link RemoveConnection#equals(Object)}, and {@link RemoveConnection#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveConnection#equals(Object)}
   *   <li>{@link RemoveConnection#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveConnection.equals(Object)", "int RemoveConnection.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RemoveConnection removeConnection = new RemoveConnection();

    // Act and Assert
    assertEquals(removeConnection, removeConnection);
    int expectedHashCodeResult = removeConnection.hashCode();
    assertEquals(expectedHashCodeResult, removeConnection.hashCode());
  }

  /**
   * Test {@link RemoveConnection#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RemoveConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveConnection.equals(Object)", "int RemoveConnection.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RemoveConnection removeConnection = new RemoveConnection();
    removeConnection.add("Key", "Value");

    // Act and Assert
    assertNotEquals(removeConnection, new RemoveConnection());
  }

  /**
   * Test {@link RemoveConnection#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RemoveConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveConnection.equals(Object)", "int RemoveConnection.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RemoveConnection(), null);
  }

  /**
   * Test {@link RemoveConnection#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RemoveConnection#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveConnection.equals(Object)", "int RemoveConnection.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RemoveConnection(), "Different type to RemoveConnection");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RemoveConnection}
   *   <li>{@link RemoveConnection#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RemoveConnection.<init>()", "java.lang.String RemoveConnection.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    RemoveConnection actualRemoveConnection = new RemoveConnection();

    // Assert
    assertEquals("RemoveConnection()", actualRemoveConnection.toString());
    assertNull(actualRemoveConnection.getOn());
    assertNull(actualRemoveConnection.getObo());
    assertNull(actualRemoveConnection.getElseCondition());
    assertNull(actualRemoveConnection.getId());
    assertNull(actualRemoveConnection.getIfCondition());
    assertNull(actualRemoveConnection.getUserId());
    assertTrue(actualRemoveConnection.getVariableProperties().isEmpty());
  }
}
