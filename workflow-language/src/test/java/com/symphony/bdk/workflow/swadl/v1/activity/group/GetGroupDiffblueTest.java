package com.symphony.bdk.workflow.swadl.v1.activity.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class GetGroupDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetGroup#equals(Object)}
   *   <li>{@link GetGroup#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetGroup getGroup = new GetGroup();
    GetGroup getGroup2 = new GetGroup();

    // Act and Assert
    assertEquals(getGroup, getGroup2);
    int expectedHashCodeResult = getGroup.hashCode();
    assertEquals(expectedHashCodeResult, getGroup2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetGroup#equals(Object)}
   *   <li>{@link GetGroup#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetGroup getGroup = new GetGroup();
    getGroup.setGroupId("42");

    GetGroup getGroup2 = new GetGroup();
    getGroup2.setGroupId("42");

    // Act and Assert
    assertEquals(getGroup, getGroup2);
    int expectedHashCodeResult = getGroup.hashCode();
    assertEquals(expectedHashCodeResult, getGroup2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetGroup#equals(Object)}
   *   <li>{@link GetGroup#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetGroup getGroup = new GetGroup();

    // Act and Assert
    assertEquals(getGroup, getGroup);
    int expectedHashCodeResult = getGroup.hashCode();
    assertEquals(expectedHashCodeResult, getGroup.hashCode());
  }

  /**
   * Method under test: {@link GetGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetGroup getGroup = new GetGroup();
    getGroup.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getGroup, new GetGroup());
  }

  /**
   * Method under test: {@link GetGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetGroup getGroup = new GetGroup();
    getGroup.add("Key", mock(AddGroupMember.class));

    // Act and Assert
    assertNotEquals(getGroup, new GetGroup());
  }

  /**
   * Method under test: {@link GetGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetGroup getGroup = new GetGroup();
    getGroup.setGroupId("42");

    // Act and Assert
    assertNotEquals(getGroup, new GetGroup());
  }

  /**
   * Method under test: {@link GetGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetGroup getGroup = new GetGroup();

    GetGroup getGroup2 = new GetGroup();
    getGroup2.setGroupId("42");

    // Act and Assert
    assertNotEquals(getGroup, getGroup2);
  }

  /**
   * Method under test: {@link GetGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetGroup(), null);
  }

  /**
   * Method under test: {@link GetGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetGroup(), "Different type to GetGroup");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetGroup}
   *   <li>{@link GetGroup#setGroupId(String)}
   *   <li>{@link GetGroup#toString()}
   *   <li>{@link GetGroup#getGroupId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetGroup actualGetGroup = new GetGroup();
    actualGetGroup.setGroupId("42");
    String actualToStringResult = actualGetGroup.toString();

    // Assert that nothing has changed
    assertEquals("42", actualGetGroup.getGroupId());
    assertEquals("GetGroup(groupId=42)", actualToStringResult);
    assertTrue(actualGetGroup.getVariableProperties().isEmpty());
  }
}
