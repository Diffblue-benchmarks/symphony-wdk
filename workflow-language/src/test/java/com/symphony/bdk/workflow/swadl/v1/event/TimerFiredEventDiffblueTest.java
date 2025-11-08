package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TimerFiredEventDiffblueTest {
  /**
   * Test {@link TimerFiredEvent#equals(Object)}, and {@link TimerFiredEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TimerFiredEvent#equals(Object)}
   *   <li>{@link TimerFiredEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TimerFiredEvent.equals(Object)", "int TimerFiredEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setAt("At");
    timerFiredEvent.setId("42");
    timerFiredEvent.setRepeat("Repeat");

    TimerFiredEvent timerFiredEvent2 = new TimerFiredEvent();
    timerFiredEvent2.setAt("At");
    timerFiredEvent2.setId("42");
    timerFiredEvent2.setRepeat("Repeat");

    // Act and Assert
    assertEquals(timerFiredEvent, timerFiredEvent2);
    int expectedHashCodeResult = timerFiredEvent.hashCode();
    assertEquals(expectedHashCodeResult, timerFiredEvent2.hashCode());
  }

  /**
   * Test {@link TimerFiredEvent#equals(Object)}, and {@link TimerFiredEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TimerFiredEvent#equals(Object)}
   *   <li>{@link TimerFiredEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TimerFiredEvent.equals(Object)", "int TimerFiredEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setAt(null);
    timerFiredEvent.setId("42");
    timerFiredEvent.setRepeat("Repeat");

    TimerFiredEvent timerFiredEvent2 = new TimerFiredEvent();
    timerFiredEvent2.setAt(null);
    timerFiredEvent2.setId("42");
    timerFiredEvent2.setRepeat("Repeat");

    // Act and Assert
    assertEquals(timerFiredEvent, timerFiredEvent2);
    int expectedHashCodeResult = timerFiredEvent.hashCode();
    assertEquals(expectedHashCodeResult, timerFiredEvent2.hashCode());
  }

  /**
   * Test {@link TimerFiredEvent#equals(Object)}, and {@link TimerFiredEvent#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TimerFiredEvent#equals(Object)}
   *   <li>{@link TimerFiredEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TimerFiredEvent.equals(Object)", "int TimerFiredEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setAt("At");
    timerFiredEvent.setId("42");
    timerFiredEvent.setRepeat(null);

    TimerFiredEvent timerFiredEvent2 = new TimerFiredEvent();
    timerFiredEvent2.setAt("At");
    timerFiredEvent2.setId("42");
    timerFiredEvent2.setRepeat(null);

    // Act and Assert
    assertEquals(timerFiredEvent, timerFiredEvent2);
    int expectedHashCodeResult = timerFiredEvent.hashCode();
    assertEquals(expectedHashCodeResult, timerFiredEvent2.hashCode());
  }

  /**
   * Test {@link TimerFiredEvent#equals(Object)}, and {@link TimerFiredEvent#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link TimerFiredEvent#equals(Object)}
   *   <li>{@link TimerFiredEvent#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TimerFiredEvent.equals(Object)", "int TimerFiredEvent.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setAt("At");
    timerFiredEvent.setId("42");
    timerFiredEvent.setRepeat("Repeat");

    // Act and Assert
    assertEquals(timerFiredEvent, timerFiredEvent);
    int expectedHashCodeResult = timerFiredEvent.hashCode();
    assertEquals(expectedHashCodeResult, timerFiredEvent.hashCode());
  }

  /**
   * Test {@link TimerFiredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TimerFiredEvent.equals(Object)", "int TimerFiredEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setAt("42");
    timerFiredEvent.setId("42");
    timerFiredEvent.setRepeat("Repeat");

    TimerFiredEvent timerFiredEvent2 = new TimerFiredEvent();
    timerFiredEvent2.setAt("At");
    timerFiredEvent2.setId("42");
    timerFiredEvent2.setRepeat("Repeat");

    // Act and Assert
    assertNotEquals(timerFiredEvent, timerFiredEvent2);
  }

  /**
   * Test {@link TimerFiredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TimerFiredEvent.equals(Object)", "int TimerFiredEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setAt(null);
    timerFiredEvent.setId("42");
    timerFiredEvent.setRepeat("Repeat");

    TimerFiredEvent timerFiredEvent2 = new TimerFiredEvent();
    timerFiredEvent2.setAt("At");
    timerFiredEvent2.setId("42");
    timerFiredEvent2.setRepeat("Repeat");

    // Act and Assert
    assertNotEquals(timerFiredEvent, timerFiredEvent2);
  }

  /**
   * Test {@link TimerFiredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TimerFiredEvent.equals(Object)", "int TimerFiredEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setAt("At");
    timerFiredEvent.setId("At");
    timerFiredEvent.setRepeat("Repeat");

    TimerFiredEvent timerFiredEvent2 = new TimerFiredEvent();
    timerFiredEvent2.setAt("At");
    timerFiredEvent2.setId("42");
    timerFiredEvent2.setRepeat("Repeat");

    // Act and Assert
    assertNotEquals(timerFiredEvent, timerFiredEvent2);
  }

  /**
   * Test {@link TimerFiredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TimerFiredEvent.equals(Object)", "int TimerFiredEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setAt("At");
    timerFiredEvent.setId("42");
    timerFiredEvent.setRepeat("42");

    TimerFiredEvent timerFiredEvent2 = new TimerFiredEvent();
    timerFiredEvent2.setAt("At");
    timerFiredEvent2.setId("42");
    timerFiredEvent2.setRepeat("Repeat");

    // Act and Assert
    assertNotEquals(timerFiredEvent, timerFiredEvent2);
  }

  /**
   * Test {@link TimerFiredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TimerFiredEvent.equals(Object)", "int TimerFiredEvent.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setAt("At");
    timerFiredEvent.setId("42");
    timerFiredEvent.setRepeat(null);

    TimerFiredEvent timerFiredEvent2 = new TimerFiredEvent();
    timerFiredEvent2.setAt("At");
    timerFiredEvent2.setId("42");
    timerFiredEvent2.setRepeat("Repeat");

    // Act and Assert
    assertNotEquals(timerFiredEvent, timerFiredEvent2);
  }

  /**
   * Test {@link TimerFiredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TimerFiredEvent.equals(Object)", "int TimerFiredEvent.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setAt("At");
    timerFiredEvent.setId("42");
    timerFiredEvent.setRepeat("Repeat");

    // Act and Assert
    assertNotEquals(timerFiredEvent, null);
  }

  /**
   * Test {@link TimerFiredEvent#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean TimerFiredEvent.equals(Object)", "int TimerFiredEvent.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setAt("At");
    timerFiredEvent.setId("42");
    timerFiredEvent.setRepeat("Repeat");

    // Act and Assert
    assertNotEquals(timerFiredEvent, "Different type to TimerFiredEvent");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link TimerFiredEvent}
   *   <li>{@link TimerFiredEvent#setAt(String)}
   *   <li>{@link TimerFiredEvent#setRepeat(String)}
   *   <li>{@link TimerFiredEvent#toString()}
   *   <li>{@link TimerFiredEvent#getAt()}
   *   <li>{@link TimerFiredEvent#getRepeat()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void TimerFiredEvent.<init>()", "String TimerFiredEvent.getAt()",
      "String TimerFiredEvent.getRepeat()", "void TimerFiredEvent.setAt(String)",
      "void TimerFiredEvent.setRepeat(String)", "String TimerFiredEvent.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    TimerFiredEvent actualTimerFiredEvent = new TimerFiredEvent();
    actualTimerFiredEvent.setAt("At");
    actualTimerFiredEvent.setRepeat("Repeat");
    String actualToStringResult = actualTimerFiredEvent.toString();
    String actualAt = actualTimerFiredEvent.getAt();

    // Assert
    assertEquals("At", actualAt);
    assertEquals("Repeat", actualTimerFiredEvent.getRepeat());
    assertEquals("TimerFiredEvent(at=At, repeat=Repeat)", actualToStringResult);
    assertNull(actualTimerFiredEvent.getId());
  }
}
