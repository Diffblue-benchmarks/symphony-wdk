package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RoomCreatedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RoomCreatedEvent#equals(Object)}
   *   <li>{@link RoomCreatedEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link RoomCreatedEvent#equals(Object)}
   *   <li>{@link RoomCreatedEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link RoomCreatedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link RoomCreatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    RoomCreatedEvent roomCreatedEvent = new RoomCreatedEvent();
    roomCreatedEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomCreatedEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link RoomCreatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    RoomCreatedEvent roomCreatedEvent = new RoomCreatedEvent();
    roomCreatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomCreatedEvent, null);
  }

  /**
   * Method under test: {@link RoomCreatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    RoomCreatedEvent roomCreatedEvent = new RoomCreatedEvent();
    roomCreatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomCreatedEvent, "Different type to RoomCreatedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RoomCreatedEvent}
   *   <li>{@link RoomCreatedEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    RoomCreatedEvent actualRoomCreatedEvent = new RoomCreatedEvent();

    // Assert
    assertEquals("RoomCreatedEvent()", actualRoomCreatedEvent.toString());
    assertNull(actualRoomCreatedEvent.getId());
  }
}
