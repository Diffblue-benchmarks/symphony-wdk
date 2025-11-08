package com.symphony.bdk.workflow.swadl.v1.activity.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class AcceptConnectionDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AcceptConnection#equals(Object)}
   *   <li>{@link AcceptConnection#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AcceptConnection acceptConnection = new AcceptConnection();
    AcceptConnection acceptConnection2 = new AcceptConnection();

    // Act and Assert
    assertEquals(acceptConnection, acceptConnection2);
    int expectedHashCodeResult = acceptConnection.hashCode();
    assertEquals(expectedHashCodeResult, acceptConnection2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AcceptConnection#equals(Object)}
   *   <li>{@link AcceptConnection#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AcceptConnection acceptConnection = new AcceptConnection();

    // Act and Assert
    assertEquals(acceptConnection, acceptConnection);
    int expectedHashCodeResult = acceptConnection.hashCode();
    assertEquals(expectedHashCodeResult, acceptConnection.hashCode());
  }

  /**
   * Method under test: {@link AcceptConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AcceptConnection acceptConnection = new AcceptConnection();
    acceptConnection.add("Key", "Value");

    // Act and Assert
    assertNotEquals(acceptConnection, new AcceptConnection());
  }

  /**
   * Method under test: {@link AcceptConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    AcceptConnection acceptConnection = new AcceptConnection();
    acceptConnection.add("Key", mock(Connection.class));

    // Act and Assert
    assertNotEquals(acceptConnection, new AcceptConnection());
  }

  /**
   * Method under test: {@link AcceptConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AcceptConnection(), null);
  }

  /**
   * Method under test: {@link AcceptConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AcceptConnection(), "Different type to AcceptConnection");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link AcceptConnection}
   *   <li>{@link AcceptConnection#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    AcceptConnection actualAcceptConnection = new AcceptConnection();

    // Assert
    assertEquals("AcceptConnection()", actualAcceptConnection.toString());
    assertNull(actualAcceptConnection.getOn());
    assertNull(actualAcceptConnection.getObo());
    assertNull(actualAcceptConnection.getElseCondition());
    assertNull(actualAcceptConnection.getId());
    assertNull(actualAcceptConnection.getIfCondition());
    assertNull(actualAcceptConnection.getUserId());
    assertTrue(actualAcceptConnection.getVariableProperties().isEmpty());
  }
}
