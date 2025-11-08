package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConnectionRequestedEventDiffblueTest {
  /**
   * Test {@link ConnectionRequestedEvent#equals(Object)}, and {@link ConnectionRequestedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ConnectionRequestedEvent#equals(Object)}
   *   <li>{@link ConnectionRequestedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ConnectionRequestedEvent.equals(Object)", "int ConnectionRequestedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ConnectionRequestedEvent connectionRequestedEvent = new ConnectionRequestedEvent();
    connectionRequestedEvent.setId("42");

    ConnectionRequestedEvent connectionRequestedEvent2 = new ConnectionRequestedEvent();
    connectionRequestedEvent2.setId("42");

    // Act and Assert
    assertEquals(connectionRequestedEvent, connectionRequestedEvent2);
    int expectedHashCodeResult = connectionRequestedEvent.hashCode();
    assertEquals(expectedHashCodeResult, connectionRequestedEvent2.hashCode());
  }

  /**
   * Test {@link ConnectionRequestedEvent#equals(Object)}, and {@link ConnectionRequestedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ConnectionRequestedEvent#equals(Object)}
   *   <li>{@link ConnectionRequestedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ConnectionRequestedEvent.equals(Object)", "int ConnectionRequestedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ConnectionRequestedEvent connectionRequestedEvent = new ConnectionRequestedEvent();
    connectionRequestedEvent.setId("42");

    // Act and Assert
    assertEquals(connectionRequestedEvent, connectionRequestedEvent);
    int expectedHashCodeResult = connectionRequestedEvent.hashCode();
    assertEquals(expectedHashCodeResult, connectionRequestedEvent.hashCode());
  }

  /**
   * Test {@link ConnectionRequestedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectionRequestedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ConnectionRequestedEvent.equals(Object)", "int ConnectionRequestedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ConnectionRequestedEvent connectionRequestedEvent = new ConnectionRequestedEvent();
    connectionRequestedEvent.setId("Id");

    ConnectionRequestedEvent connectionRequestedEvent2 = new ConnectionRequestedEvent();
    connectionRequestedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(connectionRequestedEvent, connectionRequestedEvent2);
  }

  /**
   * Test {@link ConnectionRequestedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectionRequestedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ConnectionRequestedEvent.equals(Object)", "int ConnectionRequestedEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ConnectionRequestedEvent connectionRequestedEvent = new ConnectionRequestedEvent();
    connectionRequestedEvent.setId("42");

    // Act and Assert
    assertNotEquals(connectionRequestedEvent, null);
  }

  /**
   * Test {@link ConnectionRequestedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ConnectionRequestedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ConnectionRequestedEvent.equals(Object)", "int ConnectionRequestedEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ConnectionRequestedEvent connectionRequestedEvent = new ConnectionRequestedEvent();
    connectionRequestedEvent.setId("42");

    // Act and Assert
    assertNotEquals(connectionRequestedEvent, "Different type to ConnectionRequestedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ConnectionRequestedEvent}
   *   <li>{@link ConnectionRequestedEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ConnectionRequestedEvent.<init>()", "java.lang.String ConnectionRequestedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    ConnectionRequestedEvent actualConnectionRequestedEvent = new ConnectionRequestedEvent();

    // Assert
    assertEquals("ConnectionRequestedEvent()", actualConnectionRequestedEvent.toString());
    assertNull(actualConnectionRequestedEvent.getId());
  }
}
