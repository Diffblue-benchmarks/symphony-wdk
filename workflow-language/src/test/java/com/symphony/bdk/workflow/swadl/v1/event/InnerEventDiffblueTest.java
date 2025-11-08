package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InnerEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InnerEvent#equals(Object)}
   *   <li>{@link InnerEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    InnerEvent innerEvent = new InnerEvent();
    innerEvent.setId("42");

    InnerEvent innerEvent2 = new InnerEvent();
    innerEvent2.setId("42");

    // Act and Assert
    assertEquals(innerEvent, innerEvent2);
    int expectedHashCodeResult = innerEvent.hashCode();
    assertEquals(expectedHashCodeResult, innerEvent2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InnerEvent#equals(Object)}
   *   <li>{@link InnerEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    InnerEvent innerEvent = new InnerEvent();
    innerEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    when(activityCompletedEvent.getId()).thenReturn("42");
    when(activityCompletedEvent.canEqual(Mockito.<Object>any())).thenReturn(true);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertEquals(innerEvent, activityCompletedEvent);
    int notExpectedHashCodeResult = innerEvent.hashCode();
    assertNotEquals(notExpectedHashCodeResult, activityCompletedEvent.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link InnerEvent#equals(Object)}
   *   <li>{@link InnerEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    InnerEvent innerEvent = new InnerEvent();
    innerEvent.setId("42");

    // Act and Assert
    assertEquals(innerEvent, innerEvent);
    int expectedHashCodeResult = innerEvent.hashCode();
    assertEquals(expectedHashCodeResult, innerEvent.hashCode());
  }

  /**
   * Method under test: {@link InnerEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");
    activityCompletedEvent.setIfCondition("42");
    activityCompletedEvent.setId("42");

    InnerEvent innerEvent = new InnerEvent();
    innerEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityCompletedEvent, innerEvent);
  }

  /**
   * Method under test: {@link InnerEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    InnerEvent innerEvent = new InnerEvent();
    innerEvent.setId("Id");

    InnerEvent innerEvent2 = new InnerEvent();
    innerEvent2.setId("42");

    // Act and Assert
    assertNotEquals(innerEvent, innerEvent2);
  }

  /**
   * Method under test: {@link InnerEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    InnerEvent innerEvent = new InnerEvent();
    innerEvent.setId(null);

    InnerEvent innerEvent2 = new InnerEvent();
    innerEvent2.setId("42");

    // Act and Assert
    assertNotEquals(innerEvent, innerEvent2);
  }

  /**
   * Method under test: {@link InnerEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    InnerEvent innerEvent = new InnerEvent();
    innerEvent.setId("42");

    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");
    activityCompletedEvent.setIfCondition("42");
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(innerEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link InnerEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    InnerEvent innerEvent = new InnerEvent();
    innerEvent.setId("42");

    // Act and Assert
    assertNotEquals(innerEvent, null);
  }

  /**
   * Method under test: {@link InnerEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    InnerEvent innerEvent = new InnerEvent();
    innerEvent.setId("42");

    // Act and Assert
    assertNotEquals(innerEvent, "Different type to InnerEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link InnerEvent}
   *   <li>{@link InnerEvent#setId(String)}
   *   <li>{@link InnerEvent#toString()}
   *   <li>{@link InnerEvent#getId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    InnerEvent actualInnerEvent = new InnerEvent();
    actualInnerEvent.setId("42");
    String actualToStringResult = actualInnerEvent.toString();

    // Assert that nothing has changed
    assertEquals("42", actualInnerEvent.getId());
    assertEquals("InnerEvent(id=42)", actualToStringResult);
  }
}
