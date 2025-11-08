package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConnectionAcceptedEventDiffblueTest {
  /**
   * Test {@link ConnectionAcceptedEvent#equals(Object)}, and {@link ConnectionAcceptedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ConnectionAcceptedEvent#equals(Object)}
   *   <li>{@link ConnectionAcceptedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ConnectionAcceptedEvent.equals(Object)", "int ConnectionAcceptedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ConnectionAcceptedEvent connectionAcceptedEvent = new ConnectionAcceptedEvent();
    connectionAcceptedEvent.setId("42");

    ConnectionAcceptedEvent connectionAcceptedEvent2 = new ConnectionAcceptedEvent();
    connectionAcceptedEvent2.setId("42");

    // Act and Assert
    assertEquals(connectionAcceptedEvent, connectionAcceptedEvent2);
    int expectedHashCodeResult = connectionAcceptedEvent.hashCode();
    assertEquals(expectedHashCodeResult, connectionAcceptedEvent2.hashCode());
  }

  /**
   * Test {@link ConnectionAcceptedEvent#equals(Object)}, and {@link ConnectionAcceptedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ConnectionAcceptedEvent#equals(Object)}
   *   <li>{@link ConnectionAcceptedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ConnectionAcceptedEvent.equals(Object)", "int ConnectionAcceptedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ConnectionAcceptedEvent connectionAcceptedEvent = new ConnectionAcceptedEvent();
    connectionAcceptedEvent.setId("42");

    // Act and Assert
    assertEquals(connectionAcceptedEvent, connectionAcceptedEvent);
    int expectedHashCodeResult = connectionAcceptedEvent.hashCode();
    assertEquals(expectedHashCodeResult, connectionAcceptedEvent.hashCode());
  }

  /**
   * Test {@link ConnectionAcceptedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectionAcceptedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ConnectionAcceptedEvent.equals(Object)", "int ConnectionAcceptedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ConnectionAcceptedEvent connectionAcceptedEvent = new ConnectionAcceptedEvent();
    connectionAcceptedEvent.setId("Id");

    ConnectionAcceptedEvent connectionAcceptedEvent2 = new ConnectionAcceptedEvent();
    connectionAcceptedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(connectionAcceptedEvent, connectionAcceptedEvent2);
  }

  /**
   * Test {@link ConnectionAcceptedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectionAcceptedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ConnectionAcceptedEvent.equals(Object)", "int ConnectionAcceptedEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ConnectionAcceptedEvent connectionAcceptedEvent = new ConnectionAcceptedEvent();
    connectionAcceptedEvent.setId("42");

    // Act and Assert
    assertNotEquals(connectionAcceptedEvent, null);
  }

  /**
   * Test {@link ConnectionAcceptedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectionAcceptedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ConnectionAcceptedEvent.equals(Object)", "int ConnectionAcceptedEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ConnectionAcceptedEvent connectionAcceptedEvent = new ConnectionAcceptedEvent();
    connectionAcceptedEvent.setId("42");

    // Act and Assert
    assertNotEquals(connectionAcceptedEvent, "Different type to ConnectionAcceptedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ConnectionAcceptedEvent}
   *   <li>{@link ConnectionAcceptedEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ConnectionAcceptedEvent.<init>()", "java.lang.String ConnectionAcceptedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    ConnectionAcceptedEvent actualConnectionAcceptedEvent = new ConnectionAcceptedEvent();

    // Assert
    assertEquals("ConnectionAcceptedEvent()", actualConnectionAcceptedEvent.toString());
    assertNull(actualConnectionAcceptedEvent.getId());
  }
}
