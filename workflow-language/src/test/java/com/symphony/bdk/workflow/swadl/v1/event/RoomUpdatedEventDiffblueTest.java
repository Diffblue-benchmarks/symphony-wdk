package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RoomUpdatedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RoomUpdatedEvent#equals(Object)}
   *   <li>{@link RoomUpdatedEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link RoomUpdatedEvent#equals(Object)}
   *   <li>{@link RoomUpdatedEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link RoomUpdatedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link RoomUpdatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    RoomUpdatedEvent roomUpdatedEvent = new RoomUpdatedEvent();
    roomUpdatedEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomUpdatedEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link RoomUpdatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    RoomUpdatedEvent roomUpdatedEvent = new RoomUpdatedEvent();
    roomUpdatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomUpdatedEvent, null);
  }

  /**
   * Method under test: {@link RoomUpdatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    RoomUpdatedEvent roomUpdatedEvent = new RoomUpdatedEvent();
    roomUpdatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomUpdatedEvent, "Different type to RoomUpdatedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RoomUpdatedEvent}
   *   <li>{@link RoomUpdatedEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    RoomUpdatedEvent actualRoomUpdatedEvent = new RoomUpdatedEvent();

    // Assert
    assertEquals("RoomUpdatedEvent()", actualRoomUpdatedEvent.toString());
    assertNull(actualRoomUpdatedEvent.getId());
  }
}
