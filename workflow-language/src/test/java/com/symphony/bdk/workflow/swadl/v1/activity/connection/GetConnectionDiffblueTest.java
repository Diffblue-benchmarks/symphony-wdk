package com.symphony.bdk.workflow.swadl.v1.activity.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class GetConnectionDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetConnection#equals(Object)}
   *   <li>{@link GetConnection#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetConnection getConnection = new GetConnection();
    GetConnection getConnection2 = new GetConnection();

    // Act and Assert
    assertEquals(getConnection, getConnection2);
    int expectedHashCodeResult = getConnection.hashCode();
    assertEquals(expectedHashCodeResult, getConnection2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetConnection#equals(Object)}
   *   <li>{@link GetConnection#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetConnection getConnection = new GetConnection();

    // Act and Assert
    assertEquals(getConnection, getConnection);
    int expectedHashCodeResult = getConnection.hashCode();
    assertEquals(expectedHashCodeResult, getConnection.hashCode());
  }

  /**
   * Method under test: {@link GetConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetConnection getConnection = new GetConnection();
    getConnection.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getConnection, new GetConnection());
  }

  /**
   * Method under test: {@link GetConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetConnection getConnection = new GetConnection();
    getConnection.add("Key", mock(AcceptConnection.class));

    // Act and Assert
    assertNotEquals(getConnection, new GetConnection());
  }

  /**
   * Method under test: {@link GetConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetConnection(), null);
  }

  /**
   * Method under test: {@link GetConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetConnection(), "Different type to GetConnection");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetConnection}
   *   <li>{@link GetConnection#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetConnection actualGetConnection = new GetConnection();

    // Assert
    assertEquals("GetConnection()", actualGetConnection.toString());
    assertNull(actualGetConnection.getOn());
    assertNull(actualGetConnection.getObo());
    assertNull(actualGetConnection.getElseCondition());
    assertNull(actualGetConnection.getId());
    assertNull(actualGetConnection.getIfCondition());
    assertNull(actualGetConnection.getUserId());
    assertTrue(actualGetConnection.getVariableProperties().isEmpty());
  }
}
