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

class RemoveRoomMemberDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveRoomMember#equals(Object)}
   *   <li>{@link RemoveRoomMember#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();
    RemoveRoomMember removeRoomMember2 = new RemoveRoomMember();

    // Act and Assert
    assertEquals(removeRoomMember, removeRoomMember2);
    int expectedHashCodeResult = removeRoomMember.hashCode();
    assertEquals(expectedHashCodeResult, removeRoomMember2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveRoomMember#equals(Object)}
   *   <li>{@link RemoveRoomMember#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();
    removeRoomMember.setStreamId("42");

    RemoveRoomMember removeRoomMember2 = new RemoveRoomMember();
    removeRoomMember2.setStreamId("42");

    // Act and Assert
    assertEquals(removeRoomMember, removeRoomMember2);
    int expectedHashCodeResult = removeRoomMember.hashCode();
    assertEquals(expectedHashCodeResult, removeRoomMember2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveRoomMember#equals(Object)}
   *   <li>{@link RemoveRoomMember#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();

    // Act and Assert
    assertEquals(removeRoomMember, removeRoomMember);
    int expectedHashCodeResult = removeRoomMember.hashCode();
    assertEquals(expectedHashCodeResult, removeRoomMember.hashCode());
  }

  /**
   * Method under test: {@link RemoveRoomMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();
    removeRoomMember.add("Key", "Value");

    // Act and Assert
    assertNotEquals(removeRoomMember, new RemoveRoomMember());
  }

  /**
   * Method under test: {@link RemoveRoomMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();
    removeRoomMember.add("Key", mock(AddRoomMember.class));

    // Act and Assert
    assertNotEquals(removeRoomMember, new RemoveRoomMember());
  }

  /**
   * Method under test: {@link RemoveRoomMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();
    removeRoomMember.setStreamId("42");

    // Act and Assert
    assertNotEquals(removeRoomMember, new RemoveRoomMember());
  }

  /**
   * Method under test: {@link RemoveRoomMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();

    RemoveRoomMember removeRoomMember2 = new RemoveRoomMember();
    removeRoomMember2.setStreamId("42");

    // Act and Assert
    assertNotEquals(removeRoomMember, removeRoomMember2);
  }

  /**
   * Method under test: {@link RemoveRoomMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RemoveRoomMember(), null);
  }

  /**
   * Method under test: {@link RemoveRoomMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RemoveRoomMember(), "Different type to RemoveRoomMember");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveRoomMember#setStreamId(String)}
   *   <li>{@link RemoveRoomMember#setUserIds(List)}
   *   <li>{@link RemoveRoomMember#toString()}
   *   <li>{@link RemoveRoomMember#getStreamId()}
   *   <li>{@link RemoveRoomMember#getUserIds()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();

    // Act
    removeRoomMember.setStreamId("42");
    ArrayList<Long> userIds = new ArrayList<>();
    removeRoomMember.setUserIds(userIds);
    String actualToStringResult = removeRoomMember.toString();
    String actualStreamId = removeRoomMember.getStreamId();
    List<Long> actualUserIds = removeRoomMember.getUserIds();

    // Assert that nothing has changed
    assertEquals("42", actualStreamId);
    assertEquals("RemoveRoomMember(streamId=42, userIds=[])", actualToStringResult);
    assertTrue(actualUserIds.isEmpty());
    assertSame(userIds, actualUserIds);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link RemoveRoomMember}
   */
  @Test
  void testNewRemoveRoomMember() {
    // Arrange and Act
    RemoveRoomMember actualRemoveRoomMember = new RemoveRoomMember();

    // Assert
    assertNull(actualRemoveRoomMember.getOn());
    assertNull(actualRemoveRoomMember.getObo());
    assertNull(actualRemoveRoomMember.getElseCondition());
    assertNull(actualRemoveRoomMember.getId());
    assertNull(actualRemoveRoomMember.getIfCondition());
    RelationalEvents events = actualRemoveRoomMember.getEvents();
    assertNull(events.getParentId());
    assertNull(actualRemoveRoomMember.getStreamId());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualRemoveRoomMember.getUserIds().isEmpty());
    assertTrue(actualRemoveRoomMember.getVariableProperties().isEmpty());
  }
}
