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

class DemoteRoomOwnerDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DemoteRoomOwner#equals(Object)}
   *   <li>{@link DemoteRoomOwner#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();
    DemoteRoomOwner demoteRoomOwner2 = new DemoteRoomOwner();

    // Act and Assert
    assertEquals(demoteRoomOwner, demoteRoomOwner2);
    int expectedHashCodeResult = demoteRoomOwner.hashCode();
    assertEquals(expectedHashCodeResult, demoteRoomOwner2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DemoteRoomOwner#equals(Object)}
   *   <li>{@link DemoteRoomOwner#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();
    demoteRoomOwner.setStreamId("42");

    DemoteRoomOwner demoteRoomOwner2 = new DemoteRoomOwner();
    demoteRoomOwner2.setStreamId("42");

    // Act and Assert
    assertEquals(demoteRoomOwner, demoteRoomOwner2);
    int expectedHashCodeResult = demoteRoomOwner.hashCode();
    assertEquals(expectedHashCodeResult, demoteRoomOwner2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DemoteRoomOwner#equals(Object)}
   *   <li>{@link DemoteRoomOwner#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();

    // Act and Assert
    assertEquals(demoteRoomOwner, demoteRoomOwner);
    int expectedHashCodeResult = demoteRoomOwner.hashCode();
    assertEquals(expectedHashCodeResult, demoteRoomOwner.hashCode());
  }

  /**
   * Method under test: {@link DemoteRoomOwner#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();
    demoteRoomOwner.add("Key", "Value");

    // Act and Assert
    assertNotEquals(demoteRoomOwner, new DemoteRoomOwner());
  }

  /**
   * Method under test: {@link DemoteRoomOwner#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();
    demoteRoomOwner.add("Key", mock(AddRoomMember.class));

    // Act and Assert
    assertNotEquals(demoteRoomOwner, new DemoteRoomOwner());
  }

  /**
   * Method under test: {@link DemoteRoomOwner#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();
    demoteRoomOwner.setStreamId("42");

    // Act and Assert
    assertNotEquals(demoteRoomOwner, new DemoteRoomOwner());
  }

  /**
   * Method under test: {@link DemoteRoomOwner#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();

    DemoteRoomOwner demoteRoomOwner2 = new DemoteRoomOwner();
    demoteRoomOwner2.setStreamId("42");

    // Act and Assert
    assertNotEquals(demoteRoomOwner, demoteRoomOwner2);
  }

  /**
   * Method under test: {@link DemoteRoomOwner#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new DemoteRoomOwner(), null);
  }

  /**
   * Method under test: {@link DemoteRoomOwner#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new DemoteRoomOwner(), "Different type to DemoteRoomOwner");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DemoteRoomOwner#setStreamId(String)}
   *   <li>{@link DemoteRoomOwner#setUserIds(List)}
   *   <li>{@link DemoteRoomOwner#toString()}
   *   <li>{@link DemoteRoomOwner#getStreamId()}
   *   <li>{@link DemoteRoomOwner#getUserIds()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();

    // Act
    demoteRoomOwner.setStreamId("42");
    ArrayList<Long> userIds = new ArrayList<>();
    demoteRoomOwner.setUserIds(userIds);
    String actualToStringResult = demoteRoomOwner.toString();
    String actualStreamId = demoteRoomOwner.getStreamId();
    List<Long> actualUserIds = demoteRoomOwner.getUserIds();

    // Assert that nothing has changed
    assertEquals("42", actualStreamId);
    assertEquals("DemoteRoomOwner(streamId=42, userIds=[])", actualToStringResult);
    assertTrue(actualUserIds.isEmpty());
    assertSame(userIds, actualUserIds);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link DemoteRoomOwner}
   */
  @Test
  void testNewDemoteRoomOwner() {
    // Arrange and Act
    DemoteRoomOwner actualDemoteRoomOwner = new DemoteRoomOwner();

    // Assert
    assertNull(actualDemoteRoomOwner.getOn());
    assertNull(actualDemoteRoomOwner.getObo());
    assertNull(actualDemoteRoomOwner.getElseCondition());
    assertNull(actualDemoteRoomOwner.getId());
    assertNull(actualDemoteRoomOwner.getIfCondition());
    RelationalEvents events = actualDemoteRoomOwner.getEvents();
    assertNull(events.getParentId());
    assertNull(actualDemoteRoomOwner.getStreamId());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualDemoteRoomOwner.getUserIds().isEmpty());
    assertTrue(actualDemoteRoomOwner.getVariableProperties().isEmpty());
  }
}
