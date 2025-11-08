package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ConnectionAcceptedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ConnectionAcceptedEvent#equals(Object)}
   *   <li>{@link ConnectionAcceptedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ConnectionAcceptedEvent connectionAcceptedEvent = new ConnectionAcceptedEvent();
    connectionAcceptedEvent.setId("42");

    ConnectionAcceptedEvent connectionAcceptedEvent2 = new ConnectionAcceptedEvent();
    connectionAcceptedEvent2.setId("42");

    // Act and Assert
    assertEquals(connectionAcceptedEvent, connectionAcceptedEvent2);
    int expectedHashCodeResult = connectionAcceptedEvent.hashCode();
    assertEquals(expectedHashCodeResult, connectionAcceptedEvent2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ConnectionAcceptedEvent#equals(Object)}
   *   <li>{@link ConnectionAcceptedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ConnectionAcceptedEvent connectionAcceptedEvent = new ConnectionAcceptedEvent();
    connectionAcceptedEvent.setId("42");

    // Act and Assert
    assertEquals(connectionAcceptedEvent, connectionAcceptedEvent);
    int expectedHashCodeResult = connectionAcceptedEvent.hashCode();
    assertEquals(expectedHashCodeResult, connectionAcceptedEvent.hashCode());
  }

  /**
   * Method under test: {@link ConnectionAcceptedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ConnectionAcceptedEvent connectionAcceptedEvent = new ConnectionAcceptedEvent();
    connectionAcceptedEvent.setId("Id");

    ConnectionAcceptedEvent connectionAcceptedEvent2 = new ConnectionAcceptedEvent();
    connectionAcceptedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(connectionAcceptedEvent, connectionAcceptedEvent2);
  }

  /**
   * Method under test: {@link ConnectionAcceptedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ConnectionAcceptedEvent connectionAcceptedEvent = new ConnectionAcceptedEvent();
    connectionAcceptedEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(connectionAcceptedEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link ConnectionAcceptedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ConnectionAcceptedEvent connectionAcceptedEvent = new ConnectionAcceptedEvent();
    connectionAcceptedEvent.setId("42");

    // Act and Assert
    assertNotEquals(connectionAcceptedEvent, null);
  }

  /**
   * Method under test: {@link ConnectionAcceptedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ConnectionAcceptedEvent connectionAcceptedEvent = new ConnectionAcceptedEvent();
    connectionAcceptedEvent.setId("42");

    // Act and Assert
    assertNotEquals(connectionAcceptedEvent, "Different type to ConnectionAcceptedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ConnectionAcceptedEvent}
   *   <li>{@link ConnectionAcceptedEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    ConnectionAcceptedEvent actualConnectionAcceptedEvent = new ConnectionAcceptedEvent();

    // Assert
    assertEquals("ConnectionAcceptedEvent()", actualConnectionAcceptedEvent.toString());
    assertNull(actualConnectionAcceptedEvent.getId());
  }
}
