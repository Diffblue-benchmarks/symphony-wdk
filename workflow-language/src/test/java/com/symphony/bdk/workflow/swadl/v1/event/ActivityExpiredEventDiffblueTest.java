package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ActivityExpiredEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityExpiredEvent#equals(Object)}
   *   <li>{@link ActivityExpiredEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ActivityExpiredEvent activityExpiredEvent = new ActivityExpiredEvent();
    activityExpiredEvent.setActivityId("42");
    activityExpiredEvent.setId("42");

    ActivityExpiredEvent activityExpiredEvent2 = new ActivityExpiredEvent();
    activityExpiredEvent2.setActivityId("42");
    activityExpiredEvent2.setId("42");

    // Act and Assert
    assertEquals(activityExpiredEvent, activityExpiredEvent2);
    int expectedHashCodeResult = activityExpiredEvent.hashCode();
    assertEquals(expectedHashCodeResult, activityExpiredEvent2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityExpiredEvent#equals(Object)}
   *   <li>{@link ActivityExpiredEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ActivityExpiredEvent activityExpiredEvent = new ActivityExpiredEvent();
    activityExpiredEvent.setActivityId("42");
    activityExpiredEvent.setId("42");

    // Act and Assert
    assertEquals(activityExpiredEvent, activityExpiredEvent);
    int expectedHashCodeResult = activityExpiredEvent.hashCode();
    assertEquals(expectedHashCodeResult, activityExpiredEvent.hashCode());
  }

  /**
   * Method under test: {@link ActivityExpiredEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ActivityExpiredEvent activityExpiredEvent = new ActivityExpiredEvent();
    activityExpiredEvent.setActivityId("Activity Id");
    activityExpiredEvent.setId("42");

    ActivityExpiredEvent activityExpiredEvent2 = new ActivityExpiredEvent();
    activityExpiredEvent2.setActivityId("42");
    activityExpiredEvent2.setId("42");

    // Act and Assert
    assertNotEquals(activityExpiredEvent, activityExpiredEvent2);
  }

  /**
   * Method under test: {@link ActivityExpiredEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ActivityExpiredEvent activityExpiredEvent = new ActivityExpiredEvent();
    activityExpiredEvent.setActivityId("42");
    activityExpiredEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setActivityId(Mockito.<String>any());
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityExpiredEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link ActivityExpiredEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ActivityExpiredEvent activityExpiredEvent = new ActivityExpiredEvent();
    activityExpiredEvent.setActivityId("42");
    activityExpiredEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityExpiredEvent, null);
  }

  /**
   * Method under test: {@link ActivityExpiredEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ActivityExpiredEvent activityExpiredEvent = new ActivityExpiredEvent();
    activityExpiredEvent.setActivityId("42");
    activityExpiredEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityExpiredEvent, "Different type to ActivityExpiredEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ActivityExpiredEvent}
   *   <li>{@link ActivityExpiredEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ActivityExpiredEvent actualActivityExpiredEvent = new ActivityExpiredEvent();

    // Assert
    assertEquals("ActivityExpiredEvent()", actualActivityExpiredEvent.toString());
    assertNull(actualActivityExpiredEvent.getActivityId());
    assertNull(actualActivityExpiredEvent.getId());
  }
}
