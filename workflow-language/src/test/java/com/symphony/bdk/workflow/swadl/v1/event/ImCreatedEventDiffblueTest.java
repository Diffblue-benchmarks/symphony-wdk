package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ImCreatedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ImCreatedEvent#equals(Object)}
   *   <li>{@link ImCreatedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ImCreatedEvent imCreatedEvent = new ImCreatedEvent();
    imCreatedEvent.setId("42");

    ImCreatedEvent imCreatedEvent2 = new ImCreatedEvent();
    imCreatedEvent2.setId("42");

    // Act and Assert
    assertEquals(imCreatedEvent, imCreatedEvent2);
    int expectedHashCodeResult = imCreatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, imCreatedEvent2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ImCreatedEvent#equals(Object)}
   *   <li>{@link ImCreatedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ImCreatedEvent imCreatedEvent = new ImCreatedEvent();
    imCreatedEvent.setId("42");

    // Act and Assert
    assertEquals(imCreatedEvent, imCreatedEvent);
    int expectedHashCodeResult = imCreatedEvent.hashCode();
    assertEquals(expectedHashCodeResult, imCreatedEvent.hashCode());
  }

  /**
   * Method under test: {@link ImCreatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ImCreatedEvent imCreatedEvent = new ImCreatedEvent();
    imCreatedEvent.setId("Id");

    ImCreatedEvent imCreatedEvent2 = new ImCreatedEvent();
    imCreatedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(imCreatedEvent, imCreatedEvent2);
  }

  /**
   * Method under test: {@link ImCreatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ImCreatedEvent imCreatedEvent = new ImCreatedEvent();
    imCreatedEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(imCreatedEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link ImCreatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ImCreatedEvent imCreatedEvent = new ImCreatedEvent();
    imCreatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(imCreatedEvent, null);
  }

  /**
   * Method under test: {@link ImCreatedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ImCreatedEvent imCreatedEvent = new ImCreatedEvent();
    imCreatedEvent.setId("42");

    // Act and Assert
    assertNotEquals(imCreatedEvent, "Different type to ImCreatedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ImCreatedEvent}
   *   <li>{@link ImCreatedEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ImCreatedEvent actualImCreatedEvent = new ImCreatedEvent();

    // Assert
    assertEquals("ImCreatedEvent()", actualImCreatedEvent.toString());
    assertNull(actualImCreatedEvent.getId());
  }
}
