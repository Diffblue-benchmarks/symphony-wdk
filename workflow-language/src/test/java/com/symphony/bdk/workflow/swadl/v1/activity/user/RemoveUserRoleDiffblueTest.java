package com.symphony.bdk.workflow.swadl.v1.activity.user;

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

class RemoveUserRoleDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveUserRole#equals(Object)}
   *   <li>{@link RemoveUserRole#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    RemoveUserRole removeUserRole = new RemoveUserRole();
    RemoveUserRole removeUserRole2 = new RemoveUserRole();

    // Act and Assert
    assertEquals(removeUserRole, removeUserRole2);
    int expectedHashCodeResult = removeUserRole.hashCode();
    assertEquals(expectedHashCodeResult, removeUserRole2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveUserRole#equals(Object)}
   *   <li>{@link RemoveUserRole#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RemoveUserRole removeUserRole = new RemoveUserRole();

    // Act and Assert
    assertEquals(removeUserRole, removeUserRole);
    int expectedHashCodeResult = removeUserRole.hashCode();
    assertEquals(expectedHashCodeResult, removeUserRole.hashCode());
  }

  /**
   * Method under test: {@link RemoveUserRole#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RemoveUserRole removeUserRole = new RemoveUserRole();
    removeUserRole.add("Key", "Value");

    // Act and Assert
    assertNotEquals(removeUserRole, new RemoveUserRole());
  }

  /**
   * Method under test: {@link RemoveUserRole#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    RemoveUserRole removeUserRole = new RemoveUserRole();
    removeUserRole.add("Key", mock(AddUserRole.class));

    // Act and Assert
    assertNotEquals(removeUserRole, new RemoveUserRole());
  }

  /**
   * Method under test: {@link RemoveUserRole#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RemoveUserRole(), null);
  }

  /**
   * Method under test: {@link RemoveUserRole#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RemoveUserRole(), "Different type to RemoveUserRole");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveUserRole#setRoles(List)}
   *   <li>{@link RemoveUserRole#setUserIds(List)}
   *   <li>{@link RemoveUserRole#toString()}
   *   <li>{@link RemoveUserRole#getRoles()}
   *   <li>{@link RemoveUserRole#getUserIds()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    RemoveUserRole removeUserRole = new RemoveUserRole();
    ArrayList<String> roles = new ArrayList<>();

    // Act
    removeUserRole.setRoles(roles);
    ArrayList<Long> userIds = new ArrayList<>();
    removeUserRole.setUserIds(userIds);
    String actualToStringResult = removeUserRole.toString();
    List<String> actualRoles = removeUserRole.getRoles();
    List<Long> actualUserIds = removeUserRole.getUserIds();

    // Assert that nothing has changed
    assertEquals("RemoveUserRole(userIds=[], roles=[])", actualToStringResult);
    assertTrue(actualRoles.isEmpty());
    assertTrue(actualUserIds.isEmpty());
    assertSame(roles, actualRoles);
    assertSame(userIds, actualUserIds);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link RemoveUserRole}
   */
  @Test
  void testNewRemoveUserRole() {
    // Arrange and Act
    RemoveUserRole actualRemoveUserRole = new RemoveUserRole();

    // Assert
    assertNull(actualRemoveUserRole.getOn());
    assertNull(actualRemoveUserRole.getElseCondition());
    assertNull(actualRemoveUserRole.getId());
    assertNull(actualRemoveUserRole.getIfCondition());
    RelationalEvents events = actualRemoveUserRole.getEvents();
    assertNull(events.getParentId());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    List<String> roles = actualRemoveUserRole.getRoles();
    assertTrue(roles.isEmpty());
    assertTrue(actualRemoveUserRole.getVariableProperties().isEmpty());
    assertSame(roles, actualRemoveUserRole.getUserIds());
  }
}
