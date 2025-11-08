package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RoomMemberDemotedFromOwnerEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RoomMemberDemotedFromOwnerEvent#equals(Object)}
   *   <li>{@link RoomMemberDemotedFromOwnerEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link RoomMemberDemotedFromOwnerEvent#equals(Object)}
   *   <li>{@link RoomMemberDemotedFromOwnerEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link RoomMemberDemotedFromOwnerEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link RoomMemberDemotedFromOwnerEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwnerEvent = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwnerEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomMemberDemotedFromOwnerEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link RoomMemberDemotedFromOwnerEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwnerEvent = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwnerEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomMemberDemotedFromOwnerEvent, null);
  }

  /**
   * Method under test: {@link RoomMemberDemotedFromOwnerEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    RoomMemberDemotedFromOwnerEvent roomMemberDemotedFromOwnerEvent = new RoomMemberDemotedFromOwnerEvent();
    roomMemberDemotedFromOwnerEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomMemberDemotedFromOwnerEvent, "Different type to RoomMemberDemotedFromOwnerEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link RoomMemberDemotedFromOwnerEvent}
   *   <li>{@link RoomMemberDemotedFromOwnerEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    RoomMemberDemotedFromOwnerEvent actualRoomMemberDemotedFromOwnerEvent = new RoomMemberDemotedFromOwnerEvent();

    // Assert
    assertEquals("RoomMemberDemotedFromOwnerEvent()", actualRoomMemberDemotedFromOwnerEvent.toString());
    assertNull(actualRoomMemberDemotedFromOwnerEvent.getId());
  }
}
