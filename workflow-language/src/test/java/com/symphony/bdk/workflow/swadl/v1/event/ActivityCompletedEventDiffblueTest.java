package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class ActivityCompletedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityCompletedEvent#equals(Object)}
   *   <li>{@link ActivityCompletedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");
    activityCompletedEvent.setIfCondition("If Condition");

    ActivityCompletedEvent activityCompletedEvent2 = new ActivityCompletedEvent();
    activityCompletedEvent2.setActivityId("42");
    activityCompletedEvent2.setId("42");
    activityCompletedEvent2.setIfCondition("If Condition");

    // Act and Assert
    assertEquals(activityCompletedEvent, activityCompletedEvent2);
    int expectedHashCodeResult = activityCompletedEvent.hashCode();
    assertEquals(expectedHashCodeResult, activityCompletedEvent2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityCompletedEvent#equals(Object)}
   *   <li>{@link ActivityCompletedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");
    activityCompletedEvent.setIfCondition("If Condition");

    // Act and Assert
    assertEquals(activityCompletedEvent, activityCompletedEvent);
    int expectedHashCodeResult = activityCompletedEvent.hashCode();
    assertEquals(expectedHashCodeResult, activityCompletedEvent.hashCode());
  }

  /**
   * Method under test: {@link ActivityCompletedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("If Condition");
    activityCompletedEvent.setId("42");
    activityCompletedEvent.setIfCondition("If Condition");

    ActivityCompletedEvent activityCompletedEvent2 = new ActivityCompletedEvent();
    activityCompletedEvent2.setActivityId("42");
    activityCompletedEvent2.setId("42");
    activityCompletedEvent2.setIfCondition("If Condition");

    // Act and Assert
    assertNotEquals(activityCompletedEvent, activityCompletedEvent2);
  }

  /**
   * Method under test: {@link ActivityCompletedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");
    activityCompletedEvent.setIfCondition("42");

    ActivityCompletedEvent activityCompletedEvent2 = new ActivityCompletedEvent();
    activityCompletedEvent2.setActivityId("42");
    activityCompletedEvent2.setId("42");
    activityCompletedEvent2.setIfCondition("If Condition");

    // Act and Assert
    assertNotEquals(activityCompletedEvent, activityCompletedEvent2);
  }

  /**
   * Method under test: {@link ActivityCompletedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");
    activityCompletedEvent.setIfCondition(null);

    ActivityCompletedEvent activityCompletedEvent2 = new ActivityCompletedEvent();
    activityCompletedEvent2.setActivityId("42");
    activityCompletedEvent2.setId("42");
    activityCompletedEvent2.setIfCondition("If Condition");

    // Act and Assert
    assertNotEquals(activityCompletedEvent, activityCompletedEvent2);
  }

  /**
   * Method under test: {@link ActivityCompletedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");
    activityCompletedEvent.setIfCondition("If Condition");

    // Act and Assert
    assertNotEquals(activityCompletedEvent, null);
  }

  /**
   * Method under test: {@link ActivityCompletedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");
    activityCompletedEvent.setIfCondition("If Condition");

    // Act and Assert
    assertNotEquals(activityCompletedEvent, "Different type to ActivityCompletedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ActivityCompletedEvent}
   *   <li>{@link ActivityCompletedEvent#setIfCondition(String)}
   *   <li>{@link ActivityCompletedEvent#toString()}
   *   <li>{@link ActivityCompletedEvent#getIfCondition()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ActivityCompletedEvent actualActivityCompletedEvent = new ActivityCompletedEvent();
    actualActivityCompletedEvent.setIfCondition("If Condition");
    String actualToStringResult = actualActivityCompletedEvent.toString();

    // Assert that nothing has changed
    assertEquals("ActivityCompletedEvent(ifCondition=If Condition)", actualToStringResult);
    assertEquals("If Condition", actualActivityCompletedEvent.getIfCondition());
  }
}
