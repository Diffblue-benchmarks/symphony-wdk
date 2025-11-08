package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RoomReactivatedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RoomReactivatedEvent#equals(Object)}
   *   <li>{@link RoomReactivatedEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link RoomReactivatedEvent#equals(Object)}
   *   <li>{@link RoomReactivatedEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link RoomReactivatedEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link RoomReactivatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    RoomReactivatedEvent roomReactivatedEvent = new RoomReactivatedEvent();
    roomReactivatedEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomReactivatedEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link RoomReactivatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    RoomReactivatedEvent roomReactivatedEvent = new RoomReactivatedEvent();
    roomReactivatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomReactivatedEvent, null);
  }

  /**
   * Method under test: {@link RoomReactivatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    RoomReactivatedEvent roomReactivatedEvent = new RoomReactivatedEvent();
    roomReactivatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(roomReactivatedEvent, "Different type to RoomReactivatedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link RoomReactivatedEvent}
   *   <li>{@link RoomReactivatedEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    RoomReactivatedEvent actualRoomReactivatedEvent = new RoomReactivatedEvent();

    // Assert
    assertEquals("RoomReactivatedEvent()", actualRoomReactivatedEvent.toString());
    assertNull(actualRoomReactivatedEvent.getId());
  }
}
