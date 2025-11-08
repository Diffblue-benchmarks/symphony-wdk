package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RoomDeactivatedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RoomDeactivatedEvent#equals(Object)}
   *   <li>{@link RoomDeactivatedEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link RoomDeactivatedEvent#equals(Object)}
   *   <li>{@link RoomDeactivatedEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link RoomDeactivatedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link RoomDeactivatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    RoomDeactivatedEvent roomDeactivatedEvent = new RoomDeactivatedEvent();
    roomDeactivatedEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomDeactivatedEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link RoomDeactivatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    RoomDeactivatedEvent roomDeactivatedEvent = new RoomDeactivatedEvent();
    roomDeactivatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomDeactivatedEvent, null);
  }

  /**
   * Method under test: {@link RoomDeactivatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    RoomDeactivatedEvent roomDeactivatedEvent = new RoomDeactivatedEvent();
    roomDeactivatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomDeactivatedEvent, "Different type to RoomDeactivatedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RoomDeactivatedEvent}
   *   <li>{@link RoomDeactivatedEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    RoomDeactivatedEvent actualRoomDeactivatedEvent = new RoomDeactivatedEvent();

    // Assert
    assertEquals("RoomDeactivatedEvent()", actualRoomDeactivatedEvent.toString());
    assertNull(actualRoomDeactivatedEvent.getId());
  }
}
