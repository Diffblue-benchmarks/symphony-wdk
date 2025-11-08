package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserJoinedRoomEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UserJoinedRoomEvent#equals(Object)}
   *   <li>{@link UserJoinedRoomEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UserJoinedRoomEvent userJoinedRoomEvent = new UserJoinedRoomEvent();
    userJoinedRoomEvent.setId("42");

    UserJoinedRoomEvent userJoinedRoomEvent2 = new UserJoinedRoomEvent();
    userJoinedRoomEvent2.setId("42");

    // Act and Assert
    assertEquals(userJoinedRoomEvent, userJoinedRoomEvent2);
    int expectedHashCodeResult = userJoinedRoomEvent.hashCode();
    assertEquals(expectedHashCodeResult, userJoinedRoomEvent2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UserJoinedRoomEvent#equals(Object)}
   *   <li>{@link UserJoinedRoomEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UserJoinedRoomEvent userJoinedRoomEvent = new UserJoinedRoomEvent();
    userJoinedRoomEvent.setId("42");

    // Act and Assert
    assertEquals(userJoinedRoomEvent, userJoinedRoomEvent);
    int expectedHashCodeResult = userJoinedRoomEvent.hashCode();
    assertEquals(expectedHashCodeResult, userJoinedRoomEvent.hashCode());
  }

  /**
   * Method under test: {@link UserJoinedRoomEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UserJoinedRoomEvent userJoinedRoomEvent = new UserJoinedRoomEvent();
    userJoinedRoomEvent.setId("Id");

    UserJoinedRoomEvent userJoinedRoomEvent2 = new UserJoinedRoomEvent();
    userJoinedRoomEvent2.setId("42");

    // Act and Assert
    assertNotEquals(userJoinedRoomEvent, userJoinedRoomEvent2);
  }

  /**
   * Method under test: {@link UserJoinedRoomEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UserJoinedRoomEvent userJoinedRoomEvent = new UserJoinedRoomEvent();
    userJoinedRoomEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(userJoinedRoomEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link UserJoinedRoomEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    UserJoinedRoomEvent userJoinedRoomEvent = new UserJoinedRoomEvent();
    userJoinedRoomEvent.setId("42");

    // Act and Assert
    assertNotEquals(userJoinedRoomEvent, null);
  }

  /**
   * Method under test: {@link UserJoinedRoomEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    UserJoinedRoomEvent userJoinedRoomEvent = new UserJoinedRoomEvent();
    userJoinedRoomEvent.setId("42");

    // Act and Assert
    assertNotEquals(userJoinedRoomEvent, "Different type to UserJoinedRoomEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link UserJoinedRoomEvent}
   *   <li>{@link UserJoinedRoomEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    UserJoinedRoomEvent actualUserJoinedRoomEvent = new UserJoinedRoomEvent();

    // Assert
    assertEquals("UserJoinedRoomEvent()", actualUserJoinedRoomEvent.toString());
    assertNull(actualUserJoinedRoomEvent.getId());
  }
}
