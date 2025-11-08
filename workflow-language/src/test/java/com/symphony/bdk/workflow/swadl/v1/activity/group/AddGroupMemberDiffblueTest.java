package com.symphony.bdk.workflow.swadl.v1.activity.group;

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

class AddGroupMemberDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AddGroupMember#equals(Object)}
   *   <li>{@link AddGroupMember#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AddGroupMember addGroupMember = new AddGroupMember();
    AddGroupMember addGroupMember2 = new AddGroupMember();

    // Act and Assert
    assertEquals(addGroupMember, addGroupMember2);
    int expectedHashCodeResult = addGroupMember.hashCode();
    assertEquals(expectedHashCodeResult, addGroupMember2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AddGroupMember#equals(Object)}
   *   <li>{@link AddGroupMember#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    AddGroupMember addGroupMember = new AddGroupMember();
    addGroupMember.setGroupId("42");

    AddGroupMember addGroupMember2 = new AddGroupMember();
    addGroupMember2.setGroupId("42");

    // Act and Assert
    assertEquals(addGroupMember, addGroupMember2);
    int expectedHashCodeResult = addGroupMember.hashCode();
    assertEquals(expectedHashCodeResult, addGroupMember2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AddGroupMember#equals(Object)}
   *   <li>{@link AddGroupMember#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AddGroupMember addGroupMember = new AddGroupMember();

    // Act and Assert
    assertEquals(addGroupMember, addGroupMember);
    int expectedHashCodeResult = addGroupMember.hashCode();
    assertEquals(expectedHashCodeResult, addGroupMember.hashCode());
  }

  /**
   * Method under test: {@link AddGroupMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AddGroupMember addGroupMember = new AddGroupMember();
    addGroupMember.add("Key", "Value");

    // Act and Assert
    assertNotEquals(addGroupMember, new AddGroupMember());
  }

  /**
   * Method under test: {@link AddGroupMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    AddGroupMember addGroupMember = new AddGroupMember();
    addGroupMember.add("Key", mock(CreateGroup.class));

    // Act and Assert
    assertNotEquals(addGroupMember, new AddGroupMember());
  }

  /**
   * Method under test: {@link AddGroupMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    AddGroupMember addGroupMember = new AddGroupMember();
    addGroupMember.setGroupId("42");

    // Act and Assert
    assertNotEquals(addGroupMember, new AddGroupMember());
  }

  /**
   * Method under test: {@link AddGroupMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    AddGroupMember addGroupMember = new AddGroupMember();

    AddGroupMember addGroupMember2 = new AddGroupMember();
    addGroupMember2.setGroupId("42");

    // Act and Assert
    assertNotEquals(addGroupMember, addGroupMember2);
  }

  /**
   * Method under test: {@link AddGroupMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AddGroupMember(), null);
  }

  /**
   * Method under test: {@link AddGroupMember#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AddGroupMember(), "Different type to AddGroupMember");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AddGroupMember#setGroupId(String)}
   *   <li>{@link AddGroupMember#setMembers(List)}
   *   <li>{@link AddGroupMember#toString()}
   *   <li>{@link AddGroupMember#getGroupId()}
   *   <li>{@link AddGroupMember#getMembers()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    AddGroupMember addGroupMember = new AddGroupMember();

    // Act
    addGroupMember.setGroupId("42");
    ArrayList<CreateGroup.GroupMember> members = new ArrayList<>();
    addGroupMember.setMembers(members);
    String actualToStringResult = addGroupMember.toString();
    String actualGroupId = addGroupMember.getGroupId();
    List<CreateGroup.GroupMember> actualMembers = addGroupMember.getMembers();

    // Assert that nothing has changed
    assertEquals("42", actualGroupId);
    assertEquals("AddGroupMember(groupId=42, members=[])", actualToStringResult);
    assertTrue(actualMembers.isEmpty());
    assertSame(members, actualMembers);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link AddGroupMember}
   */
  @Test
  void testNewAddGroupMember() {
    // Arrange and Act
    AddGroupMember actualAddGroupMember = new AddGroupMember();

    // Assert
    assertNull(actualAddGroupMember.getOn());
    assertNull(actualAddGroupMember.getElseCondition());
    assertNull(actualAddGroupMember.getId());
    assertNull(actualAddGroupMember.getIfCondition());
    RelationalEvents events = actualAddGroupMember.getEvents();
    assertNull(events.getParentId());
    assertNull(actualAddGroupMember.getGroupId());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualAddGroupMember.getMembers().isEmpty());
    assertTrue(actualAddGroupMember.getVariableProperties().isEmpty());
  }
}
