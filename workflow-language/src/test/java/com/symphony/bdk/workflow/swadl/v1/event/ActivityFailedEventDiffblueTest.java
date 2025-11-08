package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ActivityFailedEventDiffblueTest {
  /**
   * Test {@link ActivityFailedEvent#equals(Object)}, and {@link ActivityFailedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityFailedEvent#equals(Object)}
   *   <li>{@link ActivityFailedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityFailedEvent.equals(Object)", "int ActivityFailedEvent.hashCode()"})
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
   * Test {@link ActivityFailedEvent#equals(Object)}, and {@link ActivityFailedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityFailedEvent#equals(Object)}
   *   <li>{@link ActivityFailedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityFailedEvent.equals(Object)", "int ActivityFailedEvent.hashCode()"})
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
   * Test {@link ActivityFailedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityFailedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityFailedEvent.equals(Object)", "int ActivityFailedEvent.hashCode()"})
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
   * Test {@link ActivityFailedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityFailedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityFailedEvent.equals(Object)", "int ActivityFailedEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ActivityFailedEvent activityFailedEvent = new ActivityFailedEvent();
    activityFailedEvent.setActivityId("42");
    activityFailedEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityFailedEvent, null);
  }

  /**
   * Test {@link ActivityFailedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityFailedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityFailedEvent.equals(Object)", "int ActivityFailedEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ActivityFailedEvent activityFailedEvent = new ActivityFailedEvent();
    activityFailedEvent.setActivityId("42");
    activityFailedEvent.setId("42");

    // Act and Assert
    assertNotEquals(activityFailedEvent, "Different type to ActivityFailedEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ActivityFailedEvent}
   *   <li>{@link ActivityFailedEvent#toString()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ActivityFailedEvent.<init>()", "java.lang.String ActivityFailedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    ActivityFailedEvent actualActivityFailedEvent = new ActivityFailedEvent();

    // Assert
    assertEquals("ActivityFailedEvent()", actualActivityFailedEvent.toString());
    assertNull(actualActivityFailedEvent.getActivityId());
    assertNull(actualActivityFailedEvent.getId());
  }
}
