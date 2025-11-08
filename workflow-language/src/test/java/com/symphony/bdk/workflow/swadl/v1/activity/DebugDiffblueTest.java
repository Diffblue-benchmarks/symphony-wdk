package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class DebugDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Debug#equals(Object)}
   *   <li>{@link Debug#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Debug debug = new Debug();

    // Act and Assert
    assertEquals(debug, debug);
    int expectedHashCodeResult = debug.hashCode();
    assertEquals(expectedHashCodeResult, debug.hashCode());
  }

  /**
   * Method under test: {@link Debug#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Debug debug = new Debug();

    // Act and Assert
    assertNotEquals(debug, new Debug());
  }

  /**
   * Method under test: {@link Debug#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange, Act and Assert
    assertNotEquals(new Debug(), mock(ExecuteScript.class));
  }

  /**
   * Method under test: {@link Debug#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Debug(), null);
  }

  /**
   * Method under test: {@link Debug#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Debug(), "Different type to Debug");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Debug#setObject(Object)}
   *   <li>{@link Debug#toString()}
   *   <li>{@link Debug#getObject()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    Debug debug = new Debug();

    // Act
    debug.setObject("Object");
    String actualToStringResult = debug.toString();

    // Assert that nothing has changed
    assertEquals("Debug(object=Object)", actualToStringResult);
    assertEquals("Object", debug.getObject());
  }

  /**
   * Method under test: default or parameterless constructor of {@link Debug}
   */
  @Test
  void testNewDebug() {
    // Arrange and Act
    Debug actualDebug = new Debug();

    // Assert
    assertNull(actualDebug.getOn());
    assertNull(actualDebug.getElseCondition());
    assertNull(actualDebug.getObject());
    assertNull(actualDebug.getIfCondition());
    RelationalEvents events = actualDebug.getEvents();
    assertNull(events.getParentId());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualDebug.getVariableProperties().isEmpty());
  }
}
