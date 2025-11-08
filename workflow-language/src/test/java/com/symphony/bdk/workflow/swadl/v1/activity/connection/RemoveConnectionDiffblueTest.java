package com.symphony.bdk.workflow.swadl.v1.activity.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class RemoveConnectionDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveConnection#equals(Object)}
   *   <li>{@link RemoveConnection#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveConnection#equals(Object)}
   *   <li>{@link RemoveConnection#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RemoveConnection removeConnection = new RemoveConnection();

    // Act and Assert
    assertEquals(removeConnection, removeConnection);
    int expectedHashCodeResult = removeConnection.hashCode();
    assertEquals(expectedHashCodeResult, removeConnection.hashCode());
  }

  /**
   * Method under test: {@link RemoveConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RemoveConnection removeConnection = new RemoveConnection();
    removeConnection.add("Key", "Value");

    // Act and Assert
    assertNotEquals(removeConnection, new RemoveConnection());
  }

  /**
   * Method under test: {@link RemoveConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    RemoveConnection removeConnection = new RemoveConnection();
    removeConnection.add("Key", mock(AcceptConnection.class));

    // Act and Assert
    assertNotEquals(removeConnection, new RemoveConnection());
  }

  /**
   * Method under test: {@link RemoveConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RemoveConnection(), null);
  }

  /**
   * Method under test: {@link RemoveConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RemoveConnection(), "Different type to RemoveConnection");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RemoveConnection}
   *   <li>{@link RemoveConnection#toString()}
   * </ul>
   */
  @Test
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
