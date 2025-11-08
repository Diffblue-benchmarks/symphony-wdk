package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class TimerFiredEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link TimerFiredEvent#equals(Object)}
   *   <li>{@link TimerFiredEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link TimerFiredEvent#equals(Object)}
   *   <li>{@link TimerFiredEvent#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
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
   * Method under test: {@link TimerFiredEvent#equals(Object)}
   */
  @Test
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
  void testGettersAndSetters() {
    // Arrange and Act
    TimerFiredEvent actualTimerFiredEvent = new TimerFiredEvent();
    actualTimerFiredEvent.setAt("At");
    actualTimerFiredEvent.setRepeat("Repeat");
    String actualToStringResult = actualTimerFiredEvent.toString();
    String actualAt = actualTimerFiredEvent.getAt();

    // Assert that nothing has changed
    assertEquals("At", actualAt);
    assertEquals("Repeat", actualTimerFiredEvent.getRepeat());
    assertEquals("TimerFiredEvent(at=At, repeat=Repeat)", actualToStringResult);
  }
}
