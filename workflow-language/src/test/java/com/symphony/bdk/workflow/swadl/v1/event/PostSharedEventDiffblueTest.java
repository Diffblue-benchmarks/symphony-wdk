package com.symphony.bdk.workflow.swadl.v1.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PostSharedEventDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link PostSharedEvent#equals(Object)}
   *   <li>{@link PostSharedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    PostSharedEvent postSharedEvent = new PostSharedEvent();
    postSharedEvent.setId("42");

    PostSharedEvent postSharedEvent2 = new PostSharedEvent();
    postSharedEvent2.setId("42");

    // Act and Assert
    assertEquals(postSharedEvent, postSharedEvent2);
    int expectedHashCodeResult = postSharedEvent.hashCode();
    assertEquals(expectedHashCodeResult, postSharedEvent2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link PostSharedEvent#equals(Object)}
   *   <li>{@link PostSharedEvent#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    PostSharedEvent postSharedEvent = new PostSharedEvent();
    postSharedEvent.setId("42");

    // Act and Assert
    assertEquals(postSharedEvent, postSharedEvent);
    int expectedHashCodeResult = postSharedEvent.hashCode();
    assertEquals(expectedHashCodeResult, postSharedEvent.hashCode());
  }

  /**
   * Method under test: {@link PostSharedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    PostSharedEvent postSharedEvent = new PostSharedEvent();
    postSharedEvent.setId("Id");

    PostSharedEvent postSharedEvent2 = new PostSharedEvent();
    postSharedEvent2.setId("42");

    // Act and Assert
    assertNotEquals(postSharedEvent, postSharedEvent2);
  }

  /**
   * Method under test: {@link PostSharedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    PostSharedEvent postSharedEvent = new PostSharedEvent();
    postSharedEvent.setId("42");
    ActivityCompletedEvent activityCompletedEvent = mock(ActivityCompletedEvent.class);
    doNothing().when(activityCompletedEvent).setId(Mockito.<String>any());
    activityCompletedEvent.setId("42");

    // Act and Assert
    assertNotEquals(postSharedEvent, activityCompletedEvent);
  }

  /**
   * Method under test: {@link PostSharedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    PostSharedEvent postSharedEvent = new PostSharedEvent();
    postSharedEvent.setId("42");

    // Act and Assert
    assertNotEquals(postSharedEvent, null);
  }

  /**
   * Method under test: {@link PostSharedEvent#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    PostSharedEvent postSharedEvent = new PostSharedEvent();
    postSharedEvent.setId("42");

    // Act and Assert
    assertNotEquals(postSharedEvent, "Different type to PostSharedEvent");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link PostSharedEvent}
   *   <li>{@link PostSharedEvent#toString()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    PostSharedEvent actualPostSharedEvent = new PostSharedEvent();

    // Assert
    assertEquals("PostSharedEvent()", actualPostSharedEvent.toString());
    assertNull(actualPostSharedEvent.getId());
  }
}
