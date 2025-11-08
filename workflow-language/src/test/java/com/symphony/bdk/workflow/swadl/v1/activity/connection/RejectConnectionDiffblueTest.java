package com.symphony.bdk.workflow.swadl.v1.activity.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class RejectConnectionDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RejectConnection#equals(Object)}
   *   <li>{@link RejectConnection#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    RejectConnection rejectConnection = new RejectConnection();
    RejectConnection rejectConnection2 = new RejectConnection();

    // Act and Assert
    assertEquals(rejectConnection, rejectConnection2);
    int expectedHashCodeResult = rejectConnection.hashCode();
    assertEquals(expectedHashCodeResult, rejectConnection2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RejectConnection#equals(Object)}
   *   <li>{@link RejectConnection#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RejectConnection rejectConnection = new RejectConnection();

    // Act and Assert
    assertEquals(rejectConnection, rejectConnection);
    int expectedHashCodeResult = rejectConnection.hashCode();
    assertEquals(expectedHashCodeResult, rejectConnection.hashCode());
  }

  /**
   * Method under test: {@link RejectConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RejectConnection rejectConnection = new RejectConnection();
    rejectConnection.add("Key", "Value");

    // Act and Assert
    assertNotEquals(rejectConnection, new RejectConnection());
  }

  /**
   * Method under test: {@link RejectConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    RejectConnection rejectConnection = new RejectConnection();
    rejectConnection.add("Key", mock(AcceptConnection.class));

    // Act and Assert
    assertNotEquals(rejectConnection, new RejectConnection());
  }

  /**
   * Method under test: {@link RejectConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RejectConnection(), null);
  }

  /**
   * Method under test: {@link RejectConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RejectConnection(), "Different type to RejectConnection");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RejectConnection}
   *   <li>{@link RejectConnection#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    RejectConnection actualRejectConnection = new RejectConnection();

    // Assert
    assertEquals("RejectConnection()", actualRejectConnection.toString());
    assertNull(actualRejectConnection.getOn());
    assertNull(actualRejectConnection.getObo());
    assertNull(actualRejectConnection.getElseCondition());
    assertNull(actualRejectConnection.getId());
    assertNull(actualRejectConnection.getIfCondition());
    assertNull(actualRejectConnection.getUserId());
    assertTrue(actualRejectConnection.getVariableProperties().isEmpty());
  }
}
