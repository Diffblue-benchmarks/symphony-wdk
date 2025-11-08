package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserRequestedToJoinRoomEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UserRequestedToJoinRoomEvent#equals(Object)}
   *   <li>{@link UserRequestedToJoinRoomEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent.setId("42");

    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent2 = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent2.setId("42");

    // Act and Assert
    assertEquals(userRequestedToJoinRoomEvent, userRequestedToJoinRoomEvent2);
    int expectedHashCodeResult = userRequestedToJoinRoomEvent.hashCode();
    assertEquals(expectedHashCodeResult, userRequestedToJoinRoomEvent2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UserRequestedToJoinRoomEvent#equals(Object)}
   *   <li>{@link UserRequestedToJoinRoomEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent.setId("42");

    // Act and Assert
    assertEquals(userRequestedToJoinRoomEvent, userRequestedToJoinRoomEvent);
    int expectedHashCodeResult = userRequestedToJoinRoomEvent.hashCode();
    assertEquals(expectedHashCodeResult, userRequestedToJoinRoomEvent.hashCode());
  }

  /**
   * Method under test: {@link UserRequestedToJoinRoomEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent.setId("Id");

    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent2 = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent2.setId("42");

    // Act and Assert
    assertNotEquals(userRequestedToJoinRoomEvent, userRequestedToJoinRoomEvent2);
  }

  /**
   * Method under test: {@link UserRequestedToJoinRoomEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(userRequestedToJoinRoomEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link UserRequestedToJoinRoomEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent.setId("42");

    // Act and Assert
    assertNotEquals(userRequestedToJoinRoomEvent, null);
  }

  /**
   * Method under test: {@link UserRequestedToJoinRoomEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    UserRequestedToJoinRoomEvent userRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();
    userRequestedToJoinRoomEvent.setId("42");

    // Act and Assert
    assertNotEquals(userRequestedToJoinRoomEvent, "Different type to UserRequestedToJoinRoomEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link UserRequestedToJoinRoomEvent}
   *   <li>{@link UserRequestedToJoinRoomEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    UserRequestedToJoinRoomEvent actualUserRequestedToJoinRoomEvent = new UserRequestedToJoinRoomEvent();

    // Assert
    assertEquals("UserRequestedToJoinRoomEvent()", actualUserRequestedToJoinRoomEvent.toString());
    assertNull(actualUserRequestedToJoinRoomEvent.getId());
  }
}
