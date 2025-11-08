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

class AddRoomMemberDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AddRoomMember#equals(Object)}
   *   <li>{@link AddRoomMember#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();
    AddRoomMember addRoomMember2 = new AddRoomMember();

    // Act and Assert
    assertEquals(addRoomMember, addRoomMember2);
    int expectedHashCodeResult = addRoomMember.hashCode();
    assertEquals(expectedHashCodeResult, addRoomMember2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AddRoomMember#equals(Object)}
   *   <li>{@link AddRoomMember#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();
    addRoomMember.setStreamId("42");

    AddRoomMember addRoomMember2 = new AddRoomMember();
    addRoomMember2.setStreamId("42");

    // Act and Assert
    assertEquals(addRoomMember, addRoomMember2);
    int expectedHashCodeResult = addRoomMember.hashCode();
    assertEquals(expectedHashCodeResult, addRoomMember2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AddRoomMember#equals(Object)}
   *   <li>{@link AddRoomMember#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();

    // Act and Assert
    assertEquals(addRoomMember, addRoomMember);
    int expectedHashCodeResult = addRoomMember.hashCode();
    assertEquals(expectedHashCodeResult, addRoomMember.hashCode());
  }

  /**
   * Method under test: {@link AddRoomMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();
    addRoomMember.add("Key", "Value");

    // Act and Assert
    assertNotEquals(addRoomMember, new AddRoomMember());
  }

  /**
   * Method under test: {@link AddRoomMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();
    addRoomMember.add("Key", mock(CreateRoom.class));

    // Act and Assert
    assertNotEquals(addRoomMember, new AddRoomMember());
  }

  /**
   * Method under test: {@link AddRoomMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();
    addRoomMember.setStreamId("42");

    // Act and Assert
    assertNotEquals(addRoomMember, new AddRoomMember());
  }

  /**
   * Method under test: {@link AddRoomMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();

    AddRoomMember addRoomMember2 = new AddRoomMember();
    addRoomMember2.setStreamId("42");

    // Act and Assert
    assertNotEquals(addRoomMember, addRoomMember2);
  }

  /**
   * Method under test: {@link AddRoomMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AddRoomMember(), null);
  }

  /**
   * Method under test: {@link AddRoomMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AddRoomMember(), "Different type to AddRoomMember");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AddRoomMember#setStreamId(String)}
   *   <li>{@link AddRoomMember#setUserIds(List)}
   *   <li>{@link AddRoomMember#toString()}
   *   <li>{@link AddRoomMember#getStreamId()}
   *   <li>{@link AddRoomMember#getUserIds()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();

    // Act
    addRoomMember.setStreamId("42");
    ArrayList<Long> userIds = new ArrayList<>();
    addRoomMember.setUserIds(userIds);
    String actualToStringResult = addRoomMember.toString();
    String actualStreamId = addRoomMember.getStreamId();
    List<Long> actualUserIds = addRoomMember.getUserIds();

    // Assert that nothing has changed
    assertEquals("42", actualStreamId);
    assertEquals("AddRoomMember(streamId=42, userIds=[])", actualToStringResult);
    assertTrue(actualUserIds.isEmpty());
    assertSame(userIds, actualUserIds);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link AddRoomMember}
   */
  @Test
  void testNewAddRoomMember() {
    // Arrange and Act
    AddRoomMember actualAddRoomMember = new AddRoomMember();

    // Assert
    assertNull(actualAddRoomMember.getOn());
    assertNull(actualAddRoomMember.getObo());
    assertNull(actualAddRoomMember.getElseCondition());
    assertNull(actualAddRoomMember.getId());
    assertNull(actualAddRoomMember.getIfCondition());
    RelationalEvents events = actualAddRoomMember.getEvents();
    assertNull(events.getParentId());
    assertNull(actualAddRoomMember.getStreamId());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualAddRoomMember.getUserIds().isEmpty());
    assertTrue(actualAddRoomMember.getVariableProperties().isEmpty());
  }
}
