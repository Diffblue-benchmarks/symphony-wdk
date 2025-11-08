package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ActivityFailedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityFailedEvent#equals(Object)}
   *   <li>{@link ActivityFailedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ActivityFailedEvent activityFailedEvent = new ActivityFailedEvent();
    activityFailedEvent.setActivityId("42");
    activityFailedEvent.setId("42");

    ActivityFailedEvent activityFailedEvent2 = new ActivityFailedEvent();
    activityFailedEvent2.setActivityId("42");
    activityFailedEvent2.setId("42");

    // Act and Assert
    assertEquals(activityFailedEvent, activityFailedEvent2);
    int expectedHashCodeResult = activityFailedEvent.hashCode();
    assertEquals(expectedHashCodeResult, activityFailedEvent2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityFailedEvent#equals(Object)}
   *   <li>{@link ActivityFailedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ActivityFailedEvent activityFailedEvent = new ActivityFailedEvent();
    activityFailedEvent.setActivityId("42");
    activityFailedEvent.setId("42");

    // Act and Assert
    assertEquals(activityFailedEvent, activityFailedEvent);
    int expectedHashCodeResult = activityFailedEvent.hashCode();
    assertEquals(expectedHashCodeResult, activityFailedEvent.hashCode());
  }

  /**
   * Method under test: {@link ActivityFailedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ActivityFailedEvent activityFailedEvent = new ActivityFailedEvent();
    activityFailedEvent.setActivityId("Activity Id");
    activityFailedEvent.setId("42");

    ActivityFailedEvent activityFailedEvent2 = new ActivityFailedEvent();
    activityFailedEvent2.setActivityId("42");
    activityFailedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(activityFailedEvent, activityFailedEvent2);
  }

  /**
   * Method under test: {@link ActivityFailedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ActivityFailedEvent activityFailedEvent = new ActivityFailedEvent();
    activityFailedEvent.setActivityId("42");
    activityFailedEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setActivityId(Mockito.<String>any());
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityFailedEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link ActivityFailedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ActivityFailedEvent activityFailedEvent = new ActivityFailedEvent();
    activityFailedEvent.setActivityId("42");
    activityFailedEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityFailedEvent, null);
  }

  /**
   * Method under test: {@link ActivityFailedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ActivityFailedEvent activityFailedEvent = new ActivityFailedEvent();
    activityFailedEvent.setActivityId("42");
    activityFailedEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityFailedEvent, "Different type to ActivityFailedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ActivityFailedEvent}
   *   <li>{@link ActivityFailedEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ActivityFailedEvent actualActivityFailedEvent = new ActivityFailedEvent();

    // Assert
    assertEquals("ActivityFailedEvent()", actualActivityFailedEvent.toString());
    assertNull(actualActivityFailedEvent.getActivityId());
    assertNull(actualActivityFailedEvent.getId());
  }
}
