package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RoomCreatedEventDiffblueTest {
  /**
   * Test {@link RoomCreatedEvent#equals(Object)}, and {@link RoomCreatedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RoomCreatedEvent#equals(Object)}
   *   <li>{@link RoomCreatedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomCreatedEvent.equals(Object)", "int RoomCreatedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    RoomCreatedEvent roomCreatedEvent = new RoomCreatedEvent();
    roomCreatedEvent.setId("42");

    RoomCreatedEvent roomCreatedEvent2 = new RoomCreatedEvent();
    roomCreatedEvent2.setId("42");

    // Act and Assert
    assertEquals(roomCreatedEvent, roomCreatedEvent2);
    int expectedHashCodeResult = roomCreatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, roomCreatedEvent2.hashCode());
  }

  /**
   * Test {@link RoomCreatedEvent#equals(Object)}, and {@link RoomCreatedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RoomCreatedEvent#equals(Object)}
   *   <li>{@link RoomCreatedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomCreatedEvent.equals(Object)", "int RoomCreatedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RoomCreatedEvent roomCreatedEvent = new RoomCreatedEvent();
    roomCreatedEvent.setId("42");

    // Act and Assert
    assertEquals(roomCreatedEvent, roomCreatedEvent);
    int expectedHashCodeResult = roomCreatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, roomCreatedEvent.hashCode());
  }

  /**
   * Test {@link RoomCreatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomCreatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomCreatedEvent.equals(Object)", "int RoomCreatedEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RoomCreatedEvent roomCreatedEvent = new RoomCreatedEvent();
    roomCreatedEvent.setId("Id");

    RoomCreatedEvent roomCreatedEvent2 = new RoomCreatedEvent();
    roomCreatedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(roomCreatedEvent, roomCreatedEvent2);
  }

  /**
   * Test {@link RoomCreatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomCreatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomCreatedEvent.equals(Object)", "int RoomCreatedEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    RoomCreatedEvent roomCreatedEvent = new RoomCreatedEvent();
    roomCreatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomCreatedEvent, null);
  }

  /**
   * Test {@link RoomCreatedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomCreatedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomCreatedEvent.equals(Object)", "int RoomCreatedEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    RoomCreatedEvent roomCreatedEvent = new RoomCreatedEvent();
    roomCreatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomCreatedEvent, "Different type to RoomCreatedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RoomCreatedEvent}
   *   <li>{@link RoomCreatedEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RoomCreatedEvent.<init>()", "java.lang.String RoomCreatedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    RoomCreatedEvent actualRoomCreatedEvent = new RoomCreatedEvent();

    // Assert
    assertEquals("RoomCreatedEvent()", actualRoomCreatedEvent.toString());
    assertNull(actualRoomCreatedEvent.getId());
  }
}
