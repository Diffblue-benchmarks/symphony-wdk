package com.symphony.bdk.workflow.swadl.v1.activity.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import org.junit.jupiter.api.Test;

class UpdateGroupDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#equals(Object)}
   *   <li>{@link UpdateGroup#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    UpdateGroup updateGroup2 = new UpdateGroup();

    // Act and Assert
    assertEquals(updateGroup, updateGroup2);
    int expectedHashCodeResult = updateGroup.hashCode();
    assertEquals(expectedHashCodeResult, updateGroup2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#equals(Object)}
   *   <li>{@link UpdateGroup#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setGroupId("42");

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setGroupId("42");

    // Act and Assert
    assertEquals(updateGroup, updateGroup2);
    int expectedHashCodeResult = updateGroup.hashCode();
    assertEquals(expectedHashCodeResult, updateGroup2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#equals(Object)}
   *   <li>{@link UpdateGroup#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setStatus("SDL");

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setStatus("SDL");

    // Act and Assert
    assertEquals(updateGroup, updateGroup2);
    int expectedHashCodeResult = updateGroup.hashCode();
    assertEquals(expectedHashCodeResult, updateGroup2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#equals(Object)}
   *   <li>{@link UpdateGroup#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setEtag("SDL");

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setEtag("SDL");

    // Act and Assert
    assertEquals(updateGroup, updateGroup2);
    int expectedHashCodeResult = updateGroup.hashCode();
    assertEquals(expectedHashCodeResult, updateGroup2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#equals(Object)}
   *   <li>{@link UpdateGroup#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setImagePath("SDL");

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setImagePath("SDL");

    // Act and Assert
    assertEquals(updateGroup, updateGroup2);
    int expectedHashCodeResult = updateGroup.hashCode();
    assertEquals(expectedHashCodeResult, updateGroup2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#equals(Object)}
   *   <li>{@link UpdateGroup#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    // Act and Assert
    assertEquals(updateGroup, updateGroup);
    int expectedHashCodeResult = updateGroup.hashCode();
    assertEquals(expectedHashCodeResult, updateGroup.hashCode());
  }

  /**
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.add("SDL", "Value");

    // Act and Assert
    assertNotEquals(updateGroup, new UpdateGroup());
  }

  /**
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.add("SDL", mock(CreateGroup.class));

    // Act and Assert
    assertNotEquals(updateGroup, new UpdateGroup());
  }

  /**
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setGroupId("42");

    // Act and Assert
    assertNotEquals(updateGroup, new UpdateGroup());
  }

  /**
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setStatus("SDL");

    // Act and Assert
    assertNotEquals(updateGroup, new UpdateGroup());
  }

  /**
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setEtag("SDL");

    // Act and Assert
    assertNotEquals(updateGroup, new UpdateGroup());
  }

  /**
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setImagePath("SDL");

    // Act and Assert
    assertNotEquals(updateGroup, new UpdateGroup());
  }

  /**
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setGroupId("42");

    // Act and Assert
    assertNotEquals(updateGroup, updateGroup2);
  }

  /**
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setStatus("SDL");

    // Act and Assert
    assertNotEquals(updateGroup, updateGroup2);
  }

  /**
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setEtag("SDL");

    // Act and Assert
    assertNotEquals(updateGroup, updateGroup2);
  }

  /**
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setImagePath("SDL");

    // Act and Assert
    assertNotEquals(updateGroup, updateGroup2);
  }

  /**
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateGroup(), null);
  }

  /**
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateGroup(), "Different type to UpdateGroup");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#setEtag(String)}
   *   <li>{@link UpdateGroup#setGroupId(String)}
   *   <li>{@link UpdateGroup#setImagePath(String)}
   *   <li>{@link UpdateGroup#setStatus(String)}
   *   <li>{@link UpdateGroup#toString()}
   *   <li>{@link UpdateGroup#getEtag()}
   *   <li>{@link UpdateGroup#getGroupId()}
   *   <li>{@link UpdateGroup#getImagePath()}
   *   <li>{@link UpdateGroup#getStatus()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    // Act
    updateGroup.setEtag("Etag");
    updateGroup.setGroupId("42");
    updateGroup.setImagePath("Image Path");
    updateGroup.setStatus("Status");
    String actualToStringResult = updateGroup.toString();
    String actualEtag = updateGroup.getEtag();
    String actualGroupId = updateGroup.getGroupId();
    String actualImagePath = updateGroup.getImagePath();

    // Assert that nothing has changed
    assertEquals("42", actualGroupId);
    assertEquals("Etag", actualEtag);
    assertEquals("Image Path", actualImagePath);
    assertEquals("Status", updateGroup.getStatus());
    assertEquals("UpdateGroup(groupId=42, status=Status, etag=Etag, imagePath=Image Path)", actualToStringResult);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link UpdateGroup}
   */
  @Test
  void testNewUpdateGroup() {
    // Arrange and Act
    UpdateGroup actualUpdateGroup = new UpdateGroup();

    // Assert
    assertEquals("SDL", actualUpdateGroup.getType());
    assertNull(actualUpdateGroup.getOn());
    assertNull(actualUpdateGroup.getImplicitConnection());
    assertNull(actualUpdateGroup.getInteractionTransfer());
    assertNull(actualUpdateGroup.getOwner());
    assertNull(actualUpdateGroup.getProfile());
    assertNull(actualUpdateGroup.getVisibilityRestriction());
    assertNull(actualUpdateGroup.getElseCondition());
    assertNull(actualUpdateGroup.getId());
    assertNull(actualUpdateGroup.getIfCondition());
    RelationalEvents events = actualUpdateGroup.getEvents();
    assertNull(events.getParentId());
    assertNull(actualUpdateGroup.getName());
    assertNull(actualUpdateGroup.getReferrer());
    assertNull(actualUpdateGroup.getSubType());
    assertNull(actualUpdateGroup.getEtag());
    assertNull(actualUpdateGroup.getGroupId());
    assertNull(actualUpdateGroup.getImagePath());
    assertNull(actualUpdateGroup.getStatus());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualUpdateGroup.getMembers().isEmpty());
    assertTrue(actualUpdateGroup.getVariableProperties().isEmpty());
  }
}
