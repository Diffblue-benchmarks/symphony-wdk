package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InnerEventDiffblueTest {
  /**
   * Test {@link InnerEvent#equals(Object)}, and {@link InnerEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InnerEvent#equals(Object)}
   *   <li>{@link InnerEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InnerEvent.equals(Object)", "int InnerEvent.hashCode()"})
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
   * Test {@link InnerEvent#equals(Object)}, and {@link InnerEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InnerEvent#equals(Object)}
   *   <li>{@link InnerEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InnerEvent.equals(Object)", "int InnerEvent.hashCode()"})
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
   * Test {@link InnerEvent#equals(Object)}, and {@link InnerEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link InnerEvent#equals(Object)}
   *   <li>{@link InnerEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InnerEvent.equals(Object)", "int InnerEvent.hashCode()"})
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
   * Test {@link InnerEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InnerEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InnerEvent.equals(Object)", "int InnerEvent.hashCode()"})
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
   * Test {@link InnerEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InnerEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InnerEvent.equals(Object)", "int InnerEvent.hashCode()"})
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
   * Test {@link InnerEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InnerEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InnerEvent.equals(Object)", "int InnerEvent.hashCode()"})
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
   * Test {@link InnerEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InnerEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InnerEvent.equals(Object)", "int InnerEvent.hashCode()"})
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
   * Test {@link InnerEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InnerEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InnerEvent.equals(Object)", "int InnerEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    InnerEvent innerEvent = new InnerEvent();
    innerEvent.setId("42");

    // Act and Assert
    assertNotEquals(innerEvent, null);
  }

  /**
   * Test {@link InnerEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link InnerEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean InnerEvent.equals(Object)", "int InnerEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    InnerEvent innerEvent = new InnerEvent();
    innerEvent.setId("42");

    // Act and Assert
    assertNotEquals(innerEvent, "Different type to InnerEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link InnerEvent}
   *   <li>{@link InnerEvent#setId(String)}
   *   <li>{@link InnerEvent#toString()}
   *   <li>{@link InnerEvent#getId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void InnerEvent.<init>()", "String InnerEvent.getId()", "void InnerEvent.setId(String)",
      "String InnerEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    InnerEvent actualInnerEvent = new InnerEvent();
    actualInnerEvent.setId("42");
    String actualToStringResult = actualInnerEvent.toString();

    // Assert
    assertEquals("42", actualInnerEvent.getId());
    assertEquals("InnerEvent(id=42)", actualToStringResult);
  }
}
