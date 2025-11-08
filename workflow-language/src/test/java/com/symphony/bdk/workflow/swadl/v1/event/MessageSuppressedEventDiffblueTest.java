package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MessageSuppressedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MessageSuppressedEvent#equals(Object)}
   *   <li>{@link MessageSuppressedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    MessageSuppressedEvent messageSuppressedEvent = new MessageSuppressedEvent();
    messageSuppressedEvent.setId("42");

    MessageSuppressedEvent messageSuppressedEvent2 = new MessageSuppressedEvent();
    messageSuppressedEvent2.setId("42");

    // Act and Assert
    assertEquals(messageSuppressedEvent, messageSuppressedEvent2);
    int expectedHashCodeResult = messageSuppressedEvent.hashCode();
    assertEquals(expectedHashCodeResult, messageSuppressedEvent2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link MessageSuppressedEvent#equals(Object)}
   *   <li>{@link MessageSuppressedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    MessageSuppressedEvent messageSuppressedEvent = new MessageSuppressedEvent();
    messageSuppressedEvent.setId("42");

    // Act and Assert
    assertEquals(messageSuppressedEvent, messageSuppressedEvent);
    int expectedHashCodeResult = messageSuppressedEvent.hashCode();
    assertEquals(expectedHashCodeResult, messageSuppressedEvent.hashCode());
  }

  /**
   * Method under test: {@link MessageSuppressedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    MessageSuppressedEvent messageSuppressedEvent = new MessageSuppressedEvent();
    messageSuppressedEvent.setId("Id");

    MessageSuppressedEvent messageSuppressedEvent2 = new MessageSuppressedEvent();
    messageSuppressedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(messageSuppressedEvent, messageSuppressedEvent2);
  }

  /**
   * Method under test: {@link MessageSuppressedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    MessageSuppressedEvent messageSuppressedEvent = new MessageSuppressedEvent();
    messageSuppressedEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(messageSuppressedEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link MessageSuppressedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    MessageSuppressedEvent messageSuppressedEvent = new MessageSuppressedEvent();
    messageSuppressedEvent.setId("42");

    // Act and Assert
    assertNotEquals(messageSuppressedEvent, null);
  }

  /**
   * Method under test: {@link MessageSuppressedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    MessageSuppressedEvent messageSuppressedEvent = new MessageSuppressedEvent();
    messageSuppressedEvent.setId("42");

    // Act and Assert
    assertNotEquals(messageSuppressedEvent, "Different type to MessageSuppressedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link MessageSuppressedEvent}
   *   <li>{@link MessageSuppressedEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    MessageSuppressedEvent actualMessageSuppressedEvent = new MessageSuppressedEvent();

    // Assert
    assertEquals("MessageSuppressedEvent()", actualMessageSuppressedEvent.toString());
    assertNull(actualMessageSuppressedEvent.getId());
  }
}
