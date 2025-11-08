package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RoomMemberPromotedToOwnerEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RoomMemberPromotedToOwnerEvent#equals(Object)}
   *   <li>{@link RoomMemberPromotedToOwnerEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link RoomMemberPromotedToOwnerEvent#equals(Object)}
   *   <li>{@link RoomMemberPromotedToOwnerEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link RoomMemberPromotedToOwnerEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link RoomMemberPromotedToOwnerEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwnerEvent = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwnerEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomMemberPromotedToOwnerEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link RoomMemberPromotedToOwnerEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwnerEvent = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwnerEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomMemberPromotedToOwnerEvent, null);
  }

  /**
   * Method under test: {@link RoomMemberPromotedToOwnerEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    RoomMemberPromotedToOwnerEvent roomMemberPromotedToOwnerEvent = new RoomMemberPromotedToOwnerEvent();
    roomMemberPromotedToOwnerEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomMemberPromotedToOwnerEvent, "Different type to RoomMemberPromotedToOwnerEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link RoomMemberPromotedToOwnerEvent}
   *   <li>{@link RoomMemberPromotedToOwnerEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    RoomMemberPromotedToOwnerEvent actualRoomMemberPromotedToOwnerEvent = new RoomMemberPromotedToOwnerEvent();

    // Assert
    assertEquals("RoomMemberPromotedToOwnerEvent()", actualRoomMemberPromotedToOwnerEvent.toString());
    assertNull(actualRoomMemberPromotedToOwnerEvent.getId());
  }
}
