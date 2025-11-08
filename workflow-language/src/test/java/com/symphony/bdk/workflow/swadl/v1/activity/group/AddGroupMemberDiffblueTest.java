package com.symphony.bdk.workflow.swadl.v1.activity.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import com.symphony.bdk.workflow.swadl.v1.activity.group.CreateGroup.GroupMember;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class AddGroupMemberDiffblueTest {
  /**
   * Test {@link AddGroupMember#equals(Object)}, and {@link AddGroupMember#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddGroupMember#equals(Object)}
   *   <li>{@link AddGroupMember#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddGroupMember.equals(Object)", "int AddGroupMember.hashCode()"})
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
   * Test {@link AddGroupMember#equals(Object)}, and {@link AddGroupMember#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddGroupMember#equals(Object)}
   *   <li>{@link AddGroupMember#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddGroupMember.equals(Object)", "int AddGroupMember.hashCode()"})
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
   * Test {@link AddGroupMember#equals(Object)}, and {@link AddGroupMember#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddGroupMember#equals(Object)}
   *   <li>{@link AddGroupMember#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddGroupMember.equals(Object)", "int AddGroupMember.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AddGroupMember addGroupMember = new AddGroupMember();

    // Act and Assert
    assertEquals(addGroupMember, addGroupMember);
    int expectedHashCodeResult = addGroupMember.hashCode();
    assertEquals(expectedHashCodeResult, addGroupMember.hashCode());
  }

  /**
   * Test {@link AddGroupMember#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddGroupMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddGroupMember.equals(Object)", "int AddGroupMember.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AddGroupMember addGroupMember = new AddGroupMember();
    addGroupMember.add("Key", "Value");

    // Act and Assert
    assertNotEquals(addGroupMember, new AddGroupMember());
  }

  /**
   * Test {@link AddGroupMember#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddGroupMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddGroupMember.equals(Object)", "int AddGroupMember.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    AddGroupMember addGroupMember = new AddGroupMember();
    addGroupMember.setGroupId("42");

    // Act and Assert
    assertNotEquals(addGroupMember, new AddGroupMember());
  }

  /**
   * Test {@link AddGroupMember#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddGroupMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddGroupMember.equals(Object)", "int AddGroupMember.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    AddGroupMember addGroupMember = new AddGroupMember();

    AddGroupMember addGroupMember2 = new AddGroupMember();
    addGroupMember2.setGroupId("42");

    // Act and Assert
    assertNotEquals(addGroupMember, addGroupMember2);
  }

  /**
   * Test {@link AddGroupMember#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddGroupMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddGroupMember.equals(Object)", "int AddGroupMember.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AddGroupMember(), null);
  }

  /**
   * Test {@link AddGroupMember#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddGroupMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddGroupMember.equals(Object)", "int AddGroupMember.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AddGroupMember(), "Different type to AddGroupMember");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String AddGroupMember.getGroupId()", "List AddGroupMember.getMembers()",
      "void AddGroupMember.setGroupId(String)", "void AddGroupMember.setMembers(List)",
      "String AddGroupMember.toString()"})
  void testGettersAndSetters() {
    // Arrange
    AddGroupMember addGroupMember = new AddGroupMember();

    // Act
    addGroupMember.setGroupId("42");
    ArrayList<GroupMember> members = new ArrayList<>();
    addGroupMember.setMembers(members);
    String actualToStringResult = addGroupMember.toString();
    String actualGroupId = addGroupMember.getGroupId();
    List<GroupMember> actualMembers = addGroupMember.getMembers();

    // Assert
    assertEquals("42", actualGroupId);
    assertEquals("AddGroupMember(groupId=42, members=[])", actualToStringResult);
    assertTrue(actualMembers.isEmpty());
    assertSame(members, actualMembers);
  }

  /**
   * Test new {@link AddGroupMember} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link AddGroupMember}
   */
  @Test
  @DisplayName("Test new AddGroupMember (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AddGroupMember.<init>()"})
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
