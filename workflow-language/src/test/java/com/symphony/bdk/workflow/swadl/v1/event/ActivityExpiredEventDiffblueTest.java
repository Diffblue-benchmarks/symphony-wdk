package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ActivityExpiredEventDiffblueTest {
  /**
   * Test {@link ActivityExpiredEvent#equals(Object)}, and {@link ActivityExpiredEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityExpiredEvent#equals(Object)}
   *   <li>{@link ActivityExpiredEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityExpiredEvent.equals(Object)", "int ActivityExpiredEvent.hashCode()"})
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
   * Test {@link ActivityExpiredEvent#equals(Object)}, and {@link ActivityExpiredEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityExpiredEvent#equals(Object)}
   *   <li>{@link ActivityExpiredEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityExpiredEvent.equals(Object)", "int ActivityExpiredEvent.hashCode()"})
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
   * Test {@link ActivityExpiredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityExpiredEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityExpiredEvent.equals(Object)", "int ActivityExpiredEvent.hashCode()"})
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
   * Test {@link ActivityExpiredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityExpiredEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityExpiredEvent.equals(Object)", "int ActivityExpiredEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ActivityExpiredEvent activityExpiredEvent = new ActivityExpiredEvent();
    activityExpiredEvent.setActivityId("42");
    activityExpiredEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityExpiredEvent, null);
  }

  /**
   * Test {@link ActivityExpiredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityExpiredEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityExpiredEvent.equals(Object)", "int ActivityExpiredEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ActivityExpiredEvent activityExpiredEvent = new ActivityExpiredEvent();
    activityExpiredEvent.setActivityId("42");
    activityExpiredEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityExpiredEvent, "Different type to ActivityExpiredEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ActivityExpiredEvent}
   *   <li>{@link ActivityExpiredEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ActivityExpiredEvent.<init>()", "java.lang.String ActivityExpiredEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    ActivityExpiredEvent actualActivityExpiredEvent = new ActivityExpiredEvent();

    // Assert
    assertEquals("ActivityExpiredEvent()", actualActivityExpiredEvent.toString());
    assertNull(actualActivityExpiredEvent.getActivityId());
    assertNull(actualActivityExpiredEvent.getId());
  }
}
