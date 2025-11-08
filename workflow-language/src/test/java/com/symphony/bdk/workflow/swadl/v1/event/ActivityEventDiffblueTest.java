package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ActivityEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityEvent#equals(Object)}
   *   <li>{@link ActivityEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ActivityEvent activityEvent = new ActivityEvent();
    activityEvent.setActivityId("42");
    activityEvent.setId("42");

    ActivityEvent activityEvent2 = new ActivityEvent();
    activityEvent2.setActivityId("42");
    activityEvent2.setId("42");

    // Act and Assert
    assertEquals(activityEvent, activityEvent2);
    int expectedHashCodeResult = activityEvent.hashCode();
    assertEquals(expectedHashCodeResult, activityEvent2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityEvent#equals(Object)}
   *   <li>{@link ActivityEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ActivityEvent activityEvent = new ActivityEvent();
    activityEvent.setActivityId("42");
    activityEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    when(activityCompletedEvent.getActivityId()).thenReturn("42");
    when(activityCompletedEvent.getId()).thenReturn("42");
    when(activityCompletedEvent.canEqual(Mockito.<Object>any())).thenReturn(true);
    doNothing().when(activityCompletedEvent).setActivityId(Mockito.<String>any());
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertEquals(activityEvent, activityCompletedEvent);
    int notExpectedHashCodeResult = activityEvent.hashCode();
    assertNotEquals(notExpectedHashCodeResult, activityCompletedEvent.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityEvent#equals(Object)}
   *   <li>{@link ActivityEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ActivityEvent activityEvent = new ActivityEvent();
    activityEvent.setActivityId("42");
    activityEvent.setId("42");

    // Act and Assert
    assertEquals(activityEvent, activityEvent);
    int expectedHashCodeResult = activityEvent.hashCode();
    assertEquals(expectedHashCodeResult, activityEvent.hashCode());
  }

  /**
   * Method under test: {@link ActivityEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");
    activityCompletedEvent.setIfCondition("42");
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");

    ActivityEvent activityEvent = new ActivityEvent();
    activityEvent.setActivityId("42");
    activityEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityCompletedEvent, activityEvent);
  }

  /**
   * Method under test: {@link ActivityEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ActivityEvent activityEvent = new ActivityEvent();
    activityEvent.setActivityId("Activity Id");
    activityEvent.setId("42");

    ActivityEvent activityEvent2 = new ActivityEvent();
    activityEvent2.setActivityId("42");
    activityEvent2.setId("42");

    // Act and Assert
    assertNotEquals(activityEvent, activityEvent2);
  }

  /**
   * Method under test: {@link ActivityEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ActivityEvent activityEvent = new ActivityEvent();
    activityEvent.setActivityId(null);
    activityEvent.setId("42");

    ActivityEvent activityEvent2 = new ActivityEvent();
    activityEvent2.setActivityId("42");
    activityEvent2.setId("42");

    // Act and Assert
    assertNotEquals(activityEvent, activityEvent2);
  }

  /**
   * Method under test: {@link ActivityEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ActivityEvent activityEvent = new ActivityEvent();
    activityEvent.setActivityId("42");
    activityEvent.setId("Id");

    ActivityEvent activityEvent2 = new ActivityEvent();
    activityEvent2.setActivityId("42");
    activityEvent2.setId("42");

    // Act and Assert
    assertNotEquals(activityEvent, activityEvent2);
  }

  /**
   * Method under test: {@link ActivityEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    ActivityEvent activityEvent = new ActivityEvent();
    activityEvent.setActivityId("42");
    activityEvent.setId("42");

    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");
    activityCompletedEvent.setIfCondition("42");
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link ActivityEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ActivityEvent activityEvent = new ActivityEvent();
    activityEvent.setActivityId("42");
    activityEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityEvent, null);
  }

  /**
   * Method under test: {@link ActivityEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ActivityEvent activityEvent = new ActivityEvent();
    activityEvent.setActivityId("42");
    activityEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityEvent, "Different type to ActivityEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ActivityEvent}
   *   <li>{@link ActivityEvent#setActivityId(String)}
   *   <li>{@link ActivityEvent#toString()}
   *   <li>{@link ActivityEvent#getActivityId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ActivityEvent actualActivityEvent = new ActivityEvent();
    actualActivityEvent.setActivityId("42");
    String actualToStringResult = actualActivityEvent.toString();

    // Assert that nothing has changed
    assertEquals("42", actualActivityEvent.getActivityId());
    assertEquals("ActivityEvent(activityId=42)", actualToStringResult);
  }
}
