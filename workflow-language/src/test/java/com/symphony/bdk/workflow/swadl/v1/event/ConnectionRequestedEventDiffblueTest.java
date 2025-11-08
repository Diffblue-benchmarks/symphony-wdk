package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ConnectionRequestedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ConnectionRequestedEvent#equals(Object)}
   *   <li>{@link ConnectionRequestedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ConnectionRequestedEvent connectionRequestedEvent = new ConnectionRequestedEvent();
    connectionRequestedEvent.setId("42");

    ConnectionRequestedEvent connectionRequestedEvent2 = new ConnectionRequestedEvent();
    connectionRequestedEvent2.setId("42");

    // Act and Assert
    assertEquals(connectionRequestedEvent, connectionRequestedEvent2);
    int expectedHashCodeResult = connectionRequestedEvent.hashCode();
    assertEquals(expectedHashCodeResult, connectionRequestedEvent2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ConnectionRequestedEvent#equals(Object)}
   *   <li>{@link ConnectionRequestedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ConnectionRequestedEvent connectionRequestedEvent = new ConnectionRequestedEvent();
    connectionRequestedEvent.setId("42");

    // Act and Assert
    assertEquals(connectionRequestedEvent, connectionRequestedEvent);
    int expectedHashCodeResult = connectionRequestedEvent.hashCode();
    assertEquals(expectedHashCodeResult, connectionRequestedEvent.hashCode());
  }

  /**
   * Method under test: {@link ConnectionRequestedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ConnectionRequestedEvent connectionRequestedEvent = new ConnectionRequestedEvent();
    connectionRequestedEvent.setId("Id");

    ConnectionRequestedEvent connectionRequestedEvent2 = new ConnectionRequestedEvent();
    connectionRequestedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(connectionRequestedEvent, connectionRequestedEvent2);
  }

  /**
   * Method under test: {@link ConnectionRequestedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ConnectionRequestedEvent connectionRequestedEvent = new ConnectionRequestedEvent();
    connectionRequestedEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(connectionRequestedEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link ConnectionRequestedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ConnectionRequestedEvent connectionRequestedEvent = new ConnectionRequestedEvent();
    connectionRequestedEvent.setId("42");

    // Act and Assert
    assertNotEquals(connectionRequestedEvent, null);
  }

  /**
   * Method under test: {@link ConnectionRequestedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ConnectionRequestedEvent connectionRequestedEvent = new ConnectionRequestedEvent();
    connectionRequestedEvent.setId("42");

    // Act and Assert
    assertNotEquals(connectionRequestedEvent, "Different type to ConnectionRequestedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ConnectionRequestedEvent}
   *   <li>{@link ConnectionRequestedEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ConnectionRequestedEvent actualConnectionRequestedEvent = new ConnectionRequestedEvent();

    // Assert
    assertEquals("ConnectionRequestedEvent()", actualConnectionRequestedEvent.toString());
    assertNull(actualConnectionRequestedEvent.getId());
  }
}
