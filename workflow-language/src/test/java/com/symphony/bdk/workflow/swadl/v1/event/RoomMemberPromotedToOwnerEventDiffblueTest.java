package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RoomMemberPromotedToOwnerEventDiffblueTest {
  /**
   * Test {@link RoomMemberPromotedToOwnerEvent#equals(Object)}, and {@link RoomMemberPromotedToOwnerEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RoomMemberPromotedToOwnerEvent#equals(Object)}
   *   <li>{@link RoomMemberPromotedToOwnerEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomMemberPromotedToOwnerEvent.equals(Object)",
      "int RoomMemberPromotedToOwnerEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwnerEvent = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwnerEvent.setId("42");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwnerEvent2 = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwnerEvent2.setId("42");

    // Act and Assert
    assertEquals(roomMemberPromotedToOwnerEvent, roomMemberPromotedToOwnerEvent2);
    int expectedHashCodeResult = roomMemberPromotedToOwnerEvent.hashCode();
    assertEquals(expectedHashCodeResult, roomMemberPromotedToOwnerEvent2.hashCode());
  }

  /**
   * Test {@link RoomMemberPromotedToOwnerEvent#equals(Object)}, and {@link RoomMemberPromotedToOwnerEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RoomMemberPromotedToOwnerEvent#equals(Object)}
   *   <li>{@link RoomMemberPromotedToOwnerEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomMemberPromotedToOwnerEvent.equals(Object)",
      "int RoomMemberPromotedToOwnerEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwnerEvent = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwnerEvent.setId("42");

    // Act and Assert
    assertEquals(roomMemberPromotedToOwnerEvent, roomMemberPromotedToOwnerEvent);
    int expectedHashCodeResult = roomMemberPromotedToOwnerEvent.hashCode();
    assertEquals(expectedHashCodeResult, roomMemberPromotedToOwnerEvent.hashCode());
  }

  /**
   * Test {@link RoomMemberPromotedToOwnerEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomMemberPromotedToOwnerEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomMemberPromotedToOwnerEvent.equals(Object)",
      "int RoomMemberPromotedToOwnerEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwnerEvent = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwnerEvent.setId("Id");

    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwnerEvent2 = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwnerEvent2.setId("42");

    // Act and Assert
    assertNotEquals(roomMemberPromotedToOwnerEvent, roomMemberPromotedToOwnerEvent2);
  }

  /**
   * Test {@link RoomMemberPromotedToOwnerEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomMemberPromotedToOwnerEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomMemberPromotedToOwnerEvent.equals(Object)",
      "int RoomMemberPromotedToOwnerEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwnerEvent = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwnerEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomMemberPromotedToOwnerEvent, null);
  }

  /**
   * Test {@link RoomMemberPromotedToOwnerEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RoomMemberPromotedToOwnerEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RoomMemberPromotedToOwnerEvent.equals(Object)",
      "int RoomMemberPromotedToOwnerEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwnerEvent = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwnerEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomMemberPromotedToOwnerEvent, "Different type to RoomMemberPromotedToOwnerEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RoomMemberPromotedToOwnerEvent}
   *   <li>{@link RoomMemberPromotedToOwnerEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RoomMemberPromotedToOwnerEvent.<init>()",
      "java.lang.String RoomMemberPromotedToOwnerEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    RoomMemberPromotedToOwnerEvent actualRoomMemberPromotedToOwnerEvent = new RoomMemberPromotedToOwnerEvent();

    // Assert
    assertEquals("RoomMemberPromotedToOwnerEvent()", actualRoomMemberPromotedToOwnerEvent.toString());
    assertNull(actualRoomMemberPromotedToOwnerEvent.getId());
  }
}
