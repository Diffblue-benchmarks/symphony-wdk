package com.symphony.bdk.workflow.swadl.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import com.symphony.bdk.workflow.swadl.v1.activity.ExecuteScript;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ActivityDiffblueTest {
  /**
   * Test {@link Activity#getEvent()}.
   * <ul>
   *   <li>Given {@link Activity} (default constructor) Implementation is {@link Debug} (default constructor).</li>
   *   <li>Then return not Present.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#getEvent()}
   */
  @Test
  @DisplayName("Test getEvent(); given Activity (default constructor) Implementation is Debug (default constructor); then return not Present")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.util.Optional Activity.getEvent()"})
  void testGetEvent_givenActivityImplementationIsDebug_thenReturnNotPresent() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    // Act and Assert
    assertFalse(activity.getEvent().isPresent());
  }

  /**
   * Test {@link Activity#getEvents()}.
   * <ul>
   *   <li>Given {@link Debug} (default constructor) On is {@code null}.</li>
   *   <li>Then return ParentId is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#getEvents()}
   */
  @Test
  @DisplayName("Test getEvents(); given Debug (default constructor) On is 'null'; then return ParentId is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"RelationalEvents Activity.getEvents()"})
  void testGetEvents_givenDebugOnIsNull_thenReturnParentIdIsNull() {
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
   * Test {@link Activity#equals(Object)}, and {@link Activity#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Activity#equals(Object)}
   *   <li>{@link Activity#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Activity.equals(Object)", "int Activity.hashCode()"})
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
   * Test {@link Activity#equals(Object)}, and {@link Activity#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Activity#equals(Object)}
   *   <li>{@link Activity#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Activity.equals(Object)", "int Activity.hashCode()"})
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
   * Test {@link Activity#equals(Object)}, and {@link Activity#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Activity#equals(Object)}
   *   <li>{@link Activity#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Activity.equals(Object)", "int Activity.hashCode()"})
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
   * Test {@link Activity#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Activity.equals(Object)", "int Activity.hashCode()"})
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
   * Test {@link Activity#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Activity.equals(Object)", "int Activity.hashCode()"})
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
   * Test {@link Activity#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Activity.equals(Object)", "int Activity.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    // Act and Assert
    assertNotEquals(activity, null);
  }

  /**
   * Test {@link Activity#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activity#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Activity.equals(Object)", "int Activity.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Activity activity = new Activity();
    activity.setImplementation(new Debug());

    // Act and Assert
    assertNotEquals(activity, "Different type to Activity");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Activity.<init>()", "BaseActivity Activity.getActivity()",
      "BaseActivity Activity.getImplementation()", "void Activity.setImplementation(BaseActivity)",
      "String Activity.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    Activity actualActivity = new Activity();
    Debug implementation = new Debug();
    actualActivity.setImplementation(implementation);
    String actualToStringResult = actualActivity.toString();
    BaseActivity actualActivity2 = actualActivity.getActivity();

    // Assert
    assertEquals("Activity(implementation=Debug(object=null))", actualToStringResult);
    assertSame(implementation, actualActivity2);
    assertSame(implementation, actualActivity.getImplementation());
  }
}
