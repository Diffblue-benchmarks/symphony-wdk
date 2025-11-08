package com.symphony.bdk.workflow.swadl.v1.activity.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class CreateConnectionDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateConnection#equals(Object)}
   *   <li>{@link CreateConnection#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateConnection createConnection = new CreateConnection();
    CreateConnection createConnection2 = new CreateConnection();

    // Act and Assert
    assertEquals(createConnection, createConnection2);
    int expectedHashCodeResult = createConnection.hashCode();
    assertEquals(expectedHashCodeResult, createConnection2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateConnection#equals(Object)}
   *   <li>{@link CreateConnection#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateConnection createConnection = new CreateConnection();

    // Act and Assert
    assertEquals(createConnection, createConnection);
    int expectedHashCodeResult = createConnection.hashCode();
    assertEquals(expectedHashCodeResult, createConnection.hashCode());
  }

  /**
   * Method under test: {@link CreateConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateConnection createConnection = new CreateConnection();
    createConnection.add("Key", "Value");

    // Act and Assert
    assertNotEquals(createConnection, new CreateConnection());
  }

  /**
   * Method under test: {@link CreateConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateConnection createConnection = new CreateConnection();
    createConnection.add("Key", mock(AcceptConnection.class));

    // Act and Assert
    assertNotEquals(createConnection, new CreateConnection());
  }

  /**
   * Method under test: {@link CreateConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateConnection(), null);
  }

  /**
   * Method under test: {@link CreateConnection#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateConnection(), "Different type to CreateConnection");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateConnection}
   *   <li>{@link CreateConnection#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    CreateConnection actualCreateConnection = new CreateConnection();

    // Assert
    assertEquals("CreateConnection()", actualCreateConnection.toString());
    assertNull(actualCreateConnection.getOn());
    assertNull(actualCreateConnection.getObo());
    assertNull(actualCreateConnection.getElseCondition());
    assertNull(actualCreateConnection.getId());
    assertNull(actualCreateConnection.getIfCondition());
    assertNull(actualCreateConnection.getUserId());
    assertTrue(actualCreateConnection.getVariableProperties().isEmpty());
  }
}
