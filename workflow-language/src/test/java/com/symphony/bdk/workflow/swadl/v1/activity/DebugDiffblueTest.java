package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DebugDiffblueTest {
  /**
   * Test new {@link Debug} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link Debug}
   */
  @Test
  @DisplayName("Test new Debug (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Debug.<init>()"})
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

  /**
   * Test {@link Debug#equals(Object)}, and {@link Debug#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Debug#equals(Object)}
   *   <li>{@link Debug#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Debug.equals(Object)", "int Debug.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Debug debug = new Debug();

    // Act and Assert
    assertEquals(debug, debug);
    int expectedHashCodeResult = debug.hashCode();
    assertEquals(expectedHashCodeResult, debug.hashCode());
  }

  /**
   * Test {@link Debug#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Debug#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Debug.equals(Object)", "int Debug.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Debug debug = new Debug();

    // Act and Assert
    assertNotEquals(debug, new Debug());
  }

  /**
   * Test {@link Debug#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Debug#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Debug.equals(Object)", "int Debug.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Debug(), null);
  }

  /**
   * Test {@link Debug#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Debug#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Debug.equals(Object)", "int Debug.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Debug(), "Different type to Debug");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Debug#setObject(Object)}
   *   <li>{@link Debug#toString()}
   *   <li>{@link Debug#getObject()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Object Debug.getObject()", "void Debug.setObject(Object)", "String Debug.toString()"})
  void testGettersAndSetters() {
    // Arrange
    Debug debug = new Debug();

    // Act
    debug.setObject("Object");
    String actualToStringResult = debug.toString();

    // Assert
    assertEquals("Debug(object=Object)", actualToStringResult);
    assertEquals("Object", debug.getObject());
  }
}
