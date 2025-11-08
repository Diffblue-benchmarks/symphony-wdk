package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UserLeftRoomEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UserLeftRoomEvent#equals(Object)}
   *   <li>{@link UserLeftRoomEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UserLeftRoomEvent userLeftRoomEvent = new UserLeftRoomEvent();
    userLeftRoomEvent.setId("42");

    UserLeftRoomEvent userLeftRoomEvent2 = new UserLeftRoomEvent();
    userLeftRoomEvent2.setId("42");

    // Act and Assert
    assertEquals(userLeftRoomEvent, userLeftRoomEvent2);
    int expectedHashCodeResult = userLeftRoomEvent.hashCode();
    assertEquals(expectedHashCodeResult, userLeftRoomEvent2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UserLeftRoomEvent#equals(Object)}
   *   <li>{@link UserLeftRoomEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UserLeftRoomEvent userLeftRoomEvent = new UserLeftRoomEvent();
    userLeftRoomEvent.setId("42");

    // Act and Assert
    assertEquals(userLeftRoomEvent, userLeftRoomEvent);
    int expectedHashCodeResult = userLeftRoomEvent.hashCode();
    assertEquals(expectedHashCodeResult, userLeftRoomEvent.hashCode());
  }

  /**
   * Method under test: {@link UserLeftRoomEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UserLeftRoomEvent userLeftRoomEvent = new UserLeftRoomEvent();
    userLeftRoomEvent.setId("Id");

    UserLeftRoomEvent userLeftRoomEvent2 = new UserLeftRoomEvent();
    userLeftRoomEvent2.setId("42");

    // Act and Assert
    assertNotEquals(userLeftRoomEvent, userLeftRoomEvent2);
  }

  /**
   * Method under test: {@link UserLeftRoomEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UserLeftRoomEvent userLeftRoomEvent = new UserLeftRoomEvent();
    userLeftRoomEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(userLeftRoomEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link UserLeftRoomEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    UserLeftRoomEvent userLeftRoomEvent = new UserLeftRoomEvent();
    userLeftRoomEvent.setId("42");

    // Act and Assert
    assertNotEquals(userLeftRoomEvent, null);
  }

  /**
   * Method under test: {@link UserLeftRoomEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    UserLeftRoomEvent userLeftRoomEvent = new UserLeftRoomEvent();
    userLeftRoomEvent.setId("42");

    // Act and Assert
    assertNotEquals(userLeftRoomEvent, "Different type to UserLeftRoomEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link UserLeftRoomEvent}
   *   <li>{@link UserLeftRoomEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    UserLeftRoomEvent actualUserLeftRoomEvent = new UserLeftRoomEvent();

    // Assert
    assertEquals("UserLeftRoomEvent()", actualUserLeftRoomEvent.toString());
    assertNull(actualUserLeftRoomEvent.getId());
  }
}
