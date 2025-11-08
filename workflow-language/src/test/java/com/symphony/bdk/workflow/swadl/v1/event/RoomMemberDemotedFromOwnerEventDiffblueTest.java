package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RoomMemberDemotedFromOwnerEventDiffblueTest {
  /**
   * Test {@link RoomMemberDemotedFromOwnerEvent#equals(Object)}, and {@link RoomMemberDemotedFromOwnerEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RoomMemberDemotedFromOwnerEvent#equals(Object)}
   *   <li>{@link RoomMemberDemotedFromOwnerEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomMemberDemotedFromOwnerEvent.equals(Object)",
      "int RoomMemberDemotedFromOwnerEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwnerEvent = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwnerEvent.setId("42");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwnerEvent2 = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwnerEvent2.setId("42");

    // Act and Assert
    assertEquals(roomMemberDemotedFromOwnerEvent, roomMemberDemotedFromOwnerEvent2);
    int expectedHashCodeResult = roomMemberDemotedFromOwnerEvent.hashCode();
    assertEquals(expectedHashCodeResult, roomMemberDemotedFromOwnerEvent2.hashCode());
  }

  /**
   * Test {@link RoomMemberDemotedFromOwnerEvent#equals(Object)}, and {@link RoomMemberDemotedFromOwnerEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RoomMemberDemotedFromOwnerEvent#equals(Object)}
   *   <li>{@link RoomMemberDemotedFromOwnerEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomMemberDemotedFromOwnerEvent.equals(Object)",
      "int RoomMemberDemotedFromOwnerEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwnerEvent = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwnerEvent.setId("42");

    // Act and Assert
    assertEquals(roomMemberDemotedFromOwnerEvent, roomMemberDemotedFromOwnerEvent);
    int expectedHashCodeResult = roomMemberDemotedFromOwnerEvent.hashCode();
    assertEquals(expectedHashCodeResult, roomMemberDemotedFromOwnerEvent.hashCode());
  }

  /**
   * Test {@link RoomMemberDemotedFromOwnerEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomMemberDemotedFromOwnerEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomMemberDemotedFromOwnerEvent.equals(Object)",
      "int RoomMemberDemotedFromOwnerEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwnerEvent = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwnerEvent.setId("Id");

    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwnerEvent2 = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwnerEvent2.setId("42");

    // Act and Assert
    assertNotEquals(roomMemberDemotedFromOwnerEvent, roomMemberDemotedFromOwnerEvent2);
  }

  /**
   * Test {@link RoomMemberDemotedFromOwnerEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomMemberDemotedFromOwnerEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomMemberDemotedFromOwnerEvent.equals(Object)",
      "int RoomMemberDemotedFromOwnerEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwnerEvent = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwnerEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomMemberDemotedFromOwnerEvent, null);
  }

  /**
   * Test {@link RoomMemberDemotedFromOwnerEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomMemberDemotedFromOwnerEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomMemberDemotedFromOwnerEvent.equals(Object)",
      "int RoomMemberDemotedFromOwnerEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwnerEvent = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwnerEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomMemberDemotedFromOwnerEvent, "Different type to RoomMemberDemotedFromOwnerEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RoomMemberDemotedFromOwnerEvent}
   *   <li>{@link RoomMemberDemotedFromOwnerEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RoomMemberDemotedFromOwnerEvent.<init>()",
      "java.lang.String RoomMemberDemotedFromOwnerEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    RoomMemberDemotedFromOwnerEvent actualRoomMemberDemotedFromOwnerEvent = new RoomMemberDemotedFromOwnerEvent();

    // Assert
    assertEquals("RoomMemberDemotedFromOwnerEvent()", actualRoomMemberDemotedFromOwnerEvent.toString());
    assertNull(actualRoomMemberDemotedFromOwnerEvent.getId());
  }
}
