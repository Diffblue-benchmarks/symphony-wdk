package com.symphony.bdk.workflow.swadl.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import com.symphony.bdk.workflow.swadl.v1.activity.ExecuteScript;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import org.junit.jupiter.api.Test;

class ActivityDiffblueTest {
  /**
   * Method under test: {@link Activity#getEvent()}
   */
  @Test
  void testGetEvent() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    // Act and Assert
    assertFalse(activity.getEvent().isPresent());
  }

  /**
   * Method under test: {@link Activity#getEvents()}
   */
  @Test
  void testGetEvents() {
    // Arrange
    Debug implementation = new Debug();
    implementation.setOn(null);

    Activity activity = new Activity();
    activity.setImplementation(implementation);

    // Act
    RelationalEvents actualEvents = activity.getEvents();

    // Assert
    assertNull(actualEvents.getParentId());
    assertFalse(actualEvents.isParallel());
    assertTrue(actualEvents.isEmpty());
    assertTrue(actualEvents.getEvents().isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Activity#equals(Object)}
   *   <li>{@link Activity#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(null);

    Activity activity2 = new Activity();
    activity2.setImplementation(null);

    // Act and Assert
    assertEquals(activity, activity2);
    int expectedHashCodeResult = activity.hashCode();
    assertEquals(expectedHashCodeResult, activity2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Activity#equals(Object)}
   *   <li>{@link Activity#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(new ExecuteScript());

    Activity activity2 = new Activity();
    activity2.setImplementation(new ExecuteScript());

    // Act and Assert
    assertEquals(activity, activity2);
    int expectedHashCodeResult = activity.hashCode();
    assertEquals(expectedHashCodeResult, activity2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Activity#equals(Object)}
   *   <li>{@link Activity#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    // Act and Assert
    assertEquals(activity, activity);
    int expectedHashCodeResult = activity.hashCode();
    assertEquals(expectedHashCodeResult, activity.hashCode());
  }

  /**
   * Method under test: {@link Activity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    Activity activity2 = new Activity();
    activity2.setImplementation(new Debug());

    // Act and Assert
    assertNotEquals(activity, activity2);
  }

  /**
   * Method under test: {@link Activity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(null);

    Activity activity2 = new Activity();
    activity2.setImplementation(new Debug());

    // Act and Assert
    assertNotEquals(activity, activity2);
  }

  /**
   * Method under test: {@link Activity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(mock(Debug.class));

    Activity activity2 = new Activity();
    activity2.setImplementation(new Debug());

    // Act and Assert
    assertNotEquals(activity, activity2);
  }

  /**
   * Method under test: {@link Activity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    // Act and Assert
    assertNotEquals(activity, null);
  }

  /**
   * Method under test: {@link Activity#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    // Act and Assert
    assertNotEquals(activity, "Different type to Activity");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Activity}
   *   <li>{@link Activity#setImplementation(BaseActivity)}
   *   <li>{@link Activity#toString()}
   *   <li>{@link Activity#getActivity()}
   *   <li>{@link Activity#getImplementation()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    Activity actualActivity = new Activity();
    Debug implementation = new Debug();
    actualActivity.setImplementation(implementation);
    String actualToStringResult = actualActivity.toString();
    BaseActivity actualActivity2 = actualActivity.getActivity();

    // Assert that nothing has changed
    assertEquals("Activity(implementation=Debug(object=null))", actualToStringResult);
    assertSame(implementation, actualActivity2);
    assertSame(implementation, actualActivity.getImplementation());
  }
}
