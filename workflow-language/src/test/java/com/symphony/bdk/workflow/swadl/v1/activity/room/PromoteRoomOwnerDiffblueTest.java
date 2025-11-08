package com.symphony.bdk.workflow.swadl.v1.activity.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class PromoteRoomOwnerDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link PromoteRoomOwner#equals(Object)}
   *   <li>{@link PromoteRoomOwner#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();
    PromoteRoomOwner promoteRoomOwner2 = new PromoteRoomOwner();

    // Act and Assert
    assertEquals(promoteRoomOwner, promoteRoomOwner2);
    int expectedHashCodeResult = promoteRoomOwner.hashCode();
    assertEquals(expectedHashCodeResult, promoteRoomOwner2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link PromoteRoomOwner#equals(Object)}
   *   <li>{@link PromoteRoomOwner#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();
    promoteRoomOwner.setStreamId("42");

    PromoteRoomOwner promoteRoomOwner2 = new PromoteRoomOwner();
    promoteRoomOwner2.setStreamId("42");

    // Act and Assert
    assertEquals(promoteRoomOwner, promoteRoomOwner2);
    int expectedHashCodeResult = promoteRoomOwner.hashCode();
    assertEquals(expectedHashCodeResult, promoteRoomOwner2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link PromoteRoomOwner#equals(Object)}
   *   <li>{@link PromoteRoomOwner#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();

    // Act and Assert
    assertEquals(promoteRoomOwner, promoteRoomOwner);
    int expectedHashCodeResult = promoteRoomOwner.hashCode();
    assertEquals(expectedHashCodeResult, promoteRoomOwner.hashCode());
  }

  /**
   * Method under test: {@link PromoteRoomOwner#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();
    promoteRoomOwner.add("Key", "Value");

    // Act and Assert
    assertNotEquals(promoteRoomOwner, new PromoteRoomOwner());
  }

  /**
   * Method under test: {@link PromoteRoomOwner#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();
    promoteRoomOwner.add("Key", mock(AddRoomMember.class));

    // Act and Assert
    assertNotEquals(promoteRoomOwner, new PromoteRoomOwner());
  }

  /**
   * Method under test: {@link PromoteRoomOwner#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();
    promoteRoomOwner.setStreamId("42");

    // Act and Assert
    assertNotEquals(promoteRoomOwner, new PromoteRoomOwner());
  }

  /**
   * Method under test: {@link PromoteRoomOwner#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();

    PromoteRoomOwner promoteRoomOwner2 = new PromoteRoomOwner();
    promoteRoomOwner2.setStreamId("42");

    // Act and Assert
    assertNotEquals(promoteRoomOwner, promoteRoomOwner2);
  }

  /**
   * Method under test: {@link PromoteRoomOwner#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new PromoteRoomOwner(), null);
  }

  /**
   * Method under test: {@link PromoteRoomOwner#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new PromoteRoomOwner(), "Different type to PromoteRoomOwner");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link PromoteRoomOwner#setStreamId(String)}
   *   <li>{@link PromoteRoomOwner#setUserIds(List)}
   *   <li>{@link PromoteRoomOwner#toString()}
   *   <li>{@link PromoteRoomOwner#getStreamId()}
   *   <li>{@link PromoteRoomOwner#getUserIds()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();

    // Act
    promoteRoomOwner.setStreamId("42");
    ArrayList<Long> userIds = new ArrayList<>();
    promoteRoomOwner.setUserIds(userIds);
    String actualToStringResult = promoteRoomOwner.toString();
    String actualStreamId = promoteRoomOwner.getStreamId();
    List<Long> actualUserIds = promoteRoomOwner.getUserIds();

    // Assert that nothing has changed
    assertEquals("42", actualStreamId);
    assertEquals("PromoteRoomOwner(streamId=42, userIds=[])", actualToStringResult);
    assertTrue(actualUserIds.isEmpty());
    assertSame(userIds, actualUserIds);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link PromoteRoomOwner}
   */
  @Test
  void testNewPromoteRoomOwner() {
    // Arrange and Act
    PromoteRoomOwner actualPromoteRoomOwner = new PromoteRoomOwner();

    // Assert
    assertNull(actualPromoteRoomOwner.getOn());
    assertNull(actualPromoteRoomOwner.getObo());
    assertNull(actualPromoteRoomOwner.getElseCondition());
    assertNull(actualPromoteRoomOwner.getId());
    assertNull(actualPromoteRoomOwner.getIfCondition());
    RelationalEvents events = actualPromoteRoomOwner.getEvents();
    assertNull(events.getParentId());
    assertNull(actualPromoteRoomOwner.getStreamId());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualPromoteRoomOwner.getUserIds().isEmpty());
    assertTrue(actualPromoteRoomOwner.getVariableProperties().isEmpty());
  }
}
