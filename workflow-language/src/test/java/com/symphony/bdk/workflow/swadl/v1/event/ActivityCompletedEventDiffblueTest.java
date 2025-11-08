package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ActivityCompletedEventDiffblueTest {
  /**
   * Test {@link ActivityCompletedEvent#equals(Object)}, and {@link ActivityCompletedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityCompletedEvent#equals(Object)}
   *   <li>{@link ActivityCompletedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityCompletedEvent.equals(Object)", "int ActivityCompletedEvent.hashCode()"})
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
   * Test {@link ActivityCompletedEvent#equals(Object)}, and {@link ActivityCompletedEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityCompletedEvent#equals(Object)}
   *   <li>{@link ActivityCompletedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityCompletedEvent.equals(Object)", "int ActivityCompletedEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ActivityCompletedEvent activityCompletedEvent = new ActivityCompletedEvent();
    activityCompletedEvent.setActivityId("42");
    activityCompletedEvent.setId("42");
    activityCompletedEvent.setIfCondition(null);

    ActivityCompletedEvent activityCompletedEvent2 = new ActivityCompletedEvent();
    activityCompletedEvent2.setActivityId("42");
    activityCompletedEvent2.setId("42");
    activityCompletedEvent2.setIfCondition(null);

    // Act and Assert
    assertEquals(activityCompletedEvent, activityCompletedEvent2);
    int expectedHashCodeResult = activityCompletedEvent.hashCode();
    assertEquals(expectedHashCodeResult, activityCompletedEvent2.hashCode());
  }

  /**
   * Test {@link ActivityCompletedEvent#equals(Object)}, and {@link ActivityCompletedEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityCompletedEvent#equals(Object)}
   *   <li>{@link ActivityCompletedEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityCompletedEvent.equals(Object)", "int ActivityCompletedEvent.hashCode()"})
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
   * Test {@link ActivityCompletedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityCompletedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityCompletedEvent.equals(Object)", "int ActivityCompletedEvent.hashCode()"})
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
   * Test {@link ActivityCompletedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityCompletedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityCompletedEvent.equals(Object)", "int ActivityCompletedEvent.hashCode()"})
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
   * Test {@link ActivityCompletedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityCompletedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityCompletedEvent.equals(Object)", "int ActivityCompletedEvent.hashCode()"})
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
   * Test {@link ActivityCompletedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityCompletedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityCompletedEvent.equals(Object)", "int ActivityCompletedEvent.hashCode()"})
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
   * Test {@link ActivityCompletedEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivityCompletedEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean ActivityCompletedEvent.equals(Object)", "int ActivityCompletedEvent.hashCode()"})
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
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ActivityCompletedEvent}
   *   <li>{@link ActivityCompletedEvent#setIfCondition(String)}
   *   <li>{@link ActivityCompletedEvent#toString()}
   *   <li>{@link ActivityCompletedEvent#getIfCondition()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void ActivityCompletedEvent.<init>()", "String ActivityCompletedEvent.getIfCondition()",
      "void ActivityCompletedEvent.setIfCondition(String)", "String ActivityCompletedEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    ActivityCompletedEvent actualActivityCompletedEvent = new ActivityCompletedEvent();
    actualActivityCompletedEvent.setIfCondition("If Condition");
    String actualToStringResult = actualActivityCompletedEvent.toString();

    // Assert
    assertEquals("ActivityCompletedEvent(ifCondition=If Condition)", actualToStringResult);
    assertEquals("If Condition", actualActivityCompletedEvent.getIfCondition());
    assertNull(actualActivityCompletedEvent.getActivityId());
    assertNull(actualActivityCompletedEvent.getId());
  }
}
