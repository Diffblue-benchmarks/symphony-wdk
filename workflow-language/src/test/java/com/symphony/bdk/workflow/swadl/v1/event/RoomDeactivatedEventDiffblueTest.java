package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RoomDeactivatedEventDiffblueTest {
  /**
   * Test {@link RoomDeactivatedEvent#equals(Object)}, and {@link RoomDeactivatedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RoomDeactivatedEvent#equals(Object)}
   *   <li>{@link RoomDeactivatedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomDeactivatedEvent.equals(Object)", "int RoomDeactivatedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    RoomDeactivatedEvent roomDeactivatedEvent = new RoomDeactivatedEvent();
    roomDeactivatedEvent.setId("42");

    RoomDeactivatedEvent roomDeactivatedEvent2 = new RoomDeactivatedEvent();
    roomDeactivatedEvent2.setId("42");

    // Act and Assert
    assertEquals(roomDeactivatedEvent, roomDeactivatedEvent2);
    int expectedHashCodeResult = roomDeactivatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, roomDeactivatedEvent2.hashCode());
  }

  /**
   * Test {@link RoomDeactivatedEvent#equals(Object)}, and {@link RoomDeactivatedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RoomDeactivatedEvent#equals(Object)}
   *   <li>{@link RoomDeactivatedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomDeactivatedEvent.equals(Object)", "int RoomDeactivatedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RoomDeactivatedEvent roomDeactivatedEvent = new RoomDeactivatedEvent();
    roomDeactivatedEvent.setId("42");

    // Act and Assert
    assertEquals(roomDeactivatedEvent, roomDeactivatedEvent);
    int expectedHashCodeResult = roomDeactivatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, roomDeactivatedEvent.hashCode());
  }

  /**
   * Test {@link RoomDeactivatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomDeactivatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomDeactivatedEvent.equals(Object)", "int RoomDeactivatedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RoomDeactivatedEvent roomDeactivatedEvent = new RoomDeactivatedEvent();
    roomDeactivatedEvent.setId("Id");

    RoomDeactivatedEvent roomDeactivatedEvent2 = new RoomDeactivatedEvent();
    roomDeactivatedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(roomDeactivatedEvent, roomDeactivatedEvent2);
  }

  /**
   * Test {@link RoomDeactivatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomDeactivatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomDeactivatedEvent.equals(Object)", "int RoomDeactivatedEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    RoomDeactivatedEvent roomDeactivatedEvent = new RoomDeactivatedEvent();
    roomDeactivatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomDeactivatedEvent, null);
  }

  /**
   * Test {@link RoomDeactivatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomDeactivatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomDeactivatedEvent.equals(Object)", "int RoomDeactivatedEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    RoomDeactivatedEvent roomDeactivatedEvent = new RoomDeactivatedEvent();
    roomDeactivatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomDeactivatedEvent, "Different type to RoomDeactivatedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RoomDeactivatedEvent}
   *   <li>{@link RoomDeactivatedEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RoomDeactivatedEvent.<init>()", "java.lang.String RoomDeactivatedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    RoomDeactivatedEvent actualRoomDeactivatedEvent = new RoomDeactivatedEvent();

    // Assert
    assertEquals("RoomDeactivatedEvent()", actualRoomDeactivatedEvent.toString());
    assertNull(actualRoomDeactivatedEvent.getId());
  }
}
