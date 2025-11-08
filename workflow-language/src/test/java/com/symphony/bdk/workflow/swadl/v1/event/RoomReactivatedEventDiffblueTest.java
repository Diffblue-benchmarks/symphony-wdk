package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RoomReactivatedEventDiffblueTest {
  /**
   * Test {@link RoomReactivatedEvent#equals(Object)}, and {@link RoomReactivatedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RoomReactivatedEvent#equals(Object)}
   *   <li>{@link RoomReactivatedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomReactivatedEvent.equals(Object)", "int RoomReactivatedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    RoomReactivatedEvent roomReactivatedEvent = new RoomReactivatedEvent();
    roomReactivatedEvent.setId("42");

    RoomReactivatedEvent roomReactivatedEvent2 = new RoomReactivatedEvent();
    roomReactivatedEvent2.setId("42");

    // Act and Assert
    assertEquals(roomReactivatedEvent, roomReactivatedEvent2);
    int expectedHashCodeResult = roomReactivatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, roomReactivatedEvent2.hashCode());
  }

  /**
   * Test {@link RoomReactivatedEvent#equals(Object)}, and {@link RoomReactivatedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RoomReactivatedEvent#equals(Object)}
   *   <li>{@link RoomReactivatedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomReactivatedEvent.equals(Object)", "int RoomReactivatedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RoomReactivatedEvent roomReactivatedEvent = new RoomReactivatedEvent();
    roomReactivatedEvent.setId("42");

    // Act and Assert
    assertEquals(roomReactivatedEvent, roomReactivatedEvent);
    int expectedHashCodeResult = roomReactivatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, roomReactivatedEvent.hashCode());
  }

  /**
   * Test {@link RoomReactivatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomReactivatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomReactivatedEvent.equals(Object)", "int RoomReactivatedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RoomReactivatedEvent roomReactivatedEvent = new RoomReactivatedEvent();
    roomReactivatedEvent.setId("Id");

    RoomReactivatedEvent roomReactivatedEvent2 = new RoomReactivatedEvent();
    roomReactivatedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(roomReactivatedEvent, roomReactivatedEvent2);
  }

  /**
   * Test {@link RoomReactivatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomReactivatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomReactivatedEvent.equals(Object)", "int RoomReactivatedEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    RoomReactivatedEvent roomReactivatedEvent = new RoomReactivatedEvent();
    roomReactivatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomReactivatedEvent, null);
  }

  /**
   * Test {@link RoomReactivatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomReactivatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomReactivatedEvent.equals(Object)", "int RoomReactivatedEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    RoomReactivatedEvent roomReactivatedEvent = new RoomReactivatedEvent();
    roomReactivatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomReactivatedEvent, "Different type to RoomReactivatedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RoomReactivatedEvent}
   *   <li>{@link RoomReactivatedEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RoomReactivatedEvent.<init>()", "java.lang.String RoomReactivatedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    RoomReactivatedEvent actualRoomReactivatedEvent = new RoomReactivatedEvent();

    // Assert
    assertEquals("RoomReactivatedEvent()", actualRoomReactivatedEvent.toString());
    assertNull(actualRoomReactivatedEvent.getId());
  }
}
