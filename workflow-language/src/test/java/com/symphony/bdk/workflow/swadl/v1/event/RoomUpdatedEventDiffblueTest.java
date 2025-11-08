package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RoomUpdatedEventDiffblueTest {
  /**
   * Test {@link RoomUpdatedEvent#equals(Object)}, and {@link RoomUpdatedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RoomUpdatedEvent#equals(Object)}
   *   <li>{@link RoomUpdatedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomUpdatedEvent.equals(Object)", "int RoomUpdatedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    RoomUpdatedEvent roomUpdatedEvent = new RoomUpdatedEvent();
    roomUpdatedEvent.setId("42");

    RoomUpdatedEvent roomUpdatedEvent2 = new RoomUpdatedEvent();
    roomUpdatedEvent2.setId("42");

    // Act and Assert
    assertEquals(roomUpdatedEvent, roomUpdatedEvent2);
    int expectedHashCodeResult = roomUpdatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, roomUpdatedEvent2.hashCode());
  }

  /**
   * Test {@link RoomUpdatedEvent#equals(Object)}, and {@link RoomUpdatedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RoomUpdatedEvent#equals(Object)}
   *   <li>{@link RoomUpdatedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomUpdatedEvent.equals(Object)", "int RoomUpdatedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RoomUpdatedEvent roomUpdatedEvent = new RoomUpdatedEvent();
    roomUpdatedEvent.setId("42");

    // Act and Assert
    assertEquals(roomUpdatedEvent, roomUpdatedEvent);
    int expectedHashCodeResult = roomUpdatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, roomUpdatedEvent.hashCode());
  }

  /**
   * Test {@link RoomUpdatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomUpdatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomUpdatedEvent.equals(Object)", "int RoomUpdatedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RoomUpdatedEvent roomUpdatedEvent = new RoomUpdatedEvent();
    roomUpdatedEvent.setId("Id");

    RoomUpdatedEvent roomUpdatedEvent2 = new RoomUpdatedEvent();
    roomUpdatedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(roomUpdatedEvent, roomUpdatedEvent2);
  }

  /**
   * Test {@link RoomUpdatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomUpdatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomUpdatedEvent.equals(Object)", "int RoomUpdatedEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    RoomUpdatedEvent roomUpdatedEvent = new RoomUpdatedEvent();
    roomUpdatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomUpdatedEvent, null);
  }

  /**
   * Test {@link RoomUpdatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomUpdatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomUpdatedEvent.equals(Object)", "int RoomUpdatedEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    RoomUpdatedEvent roomUpdatedEvent = new RoomUpdatedEvent();
    roomUpdatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomUpdatedEvent, "Different type to RoomUpdatedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RoomUpdatedEvent}
   *   <li>{@link RoomUpdatedEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RoomUpdatedEvent.<init>()", "java.lang.String RoomUpdatedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    RoomUpdatedEvent actualRoomUpdatedEvent = new RoomUpdatedEvent();

    // Assert
    assertEquals("RoomUpdatedEvent()", actualRoomUpdatedEvent.toString());
    assertNull(actualRoomUpdatedEvent.getId());
  }
}
