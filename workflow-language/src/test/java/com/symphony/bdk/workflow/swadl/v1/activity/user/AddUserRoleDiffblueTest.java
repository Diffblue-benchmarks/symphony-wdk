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

class AddUserRoleDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AddUserRole#equals(Object)}
   *   <li>{@link AddUserRole#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AddUserRole addUserRole = new AddUserRole();
    AddUserRole addUserRole2 = new AddUserRole();

    // Act and Assert
    assertEquals(addUserRole, addUserRole2);
    int expectedHashCodeResult = addUserRole.hashCode();
    assertEquals(expectedHashCodeResult, addUserRole2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AddUserRole#equals(Object)}
   *   <li>{@link AddUserRole#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AddUserRole addUserRole = new AddUserRole();

    // Act and Assert
    assertEquals(addUserRole, addUserRole);
    int expectedHashCodeResult = addUserRole.hashCode();
    assertEquals(expectedHashCodeResult, addUserRole.hashCode());
  }

  /**
   * Method under test: {@link AddUserRole#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AddUserRole addUserRole = new AddUserRole();
    addUserRole.add("Key", "Value");

    // Act and Assert
    assertNotEquals(addUserRole, new AddUserRole());
  }

  /**
   * Method under test: {@link AddUserRole#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    AddUserRole addUserRole = new AddUserRole();
    addUserRole.add("Key", mock(CreateSystemUser.class));

    // Act and Assert
    assertNotEquals(addUserRole, new AddUserRole());
  }

  /**
   * Method under test: {@link AddUserRole#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AddUserRole(), null);
  }

  /**
   * Method under test: {@link AddUserRole#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AddUserRole(), "Different type to AddUserRole");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link AddUserRole#setRoles(List)}
   *   <li>{@link AddUserRole#setUserIds(List)}
   *   <li>{@link AddUserRole#toString()}
   *   <li>{@link AddUserRole#getRoles()}
   *   <li>{@link AddUserRole#getUserIds()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    AddUserRole addUserRole = new AddUserRole();
    ArrayList<String> roles = new ArrayList<>();

    // Act
    addUserRole.setRoles(roles);
    ArrayList<Long> userIds = new ArrayList<>();
    addUserRole.setUserIds(userIds);
    String actualToStringResult = addUserRole.toString();
    List<String> actualRoles = addUserRole.getRoles();
    List<Long> actualUserIds = addUserRole.getUserIds();

    // Assert that nothing has changed
    assertEquals("AddUserRole(userIds=[], roles=[])", actualToStringResult);
    assertTrue(actualRoles.isEmpty());
    assertTrue(actualUserIds.isEmpty());
    assertSame(roles, actualRoles);
    assertSame(userIds, actualUserIds);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link AddUserRole}
   */
  @Test
  void testNewAddUserRole() {
    // Arrange and Act
    AddUserRole actualAddUserRole = new AddUserRole();

    // Assert
    assertNull(actualAddUserRole.getOn());
    assertNull(actualAddUserRole.getElseCondition());
    assertNull(actualAddUserRole.getId());
    assertNull(actualAddUserRole.getIfCondition());
    RelationalEvents events = actualAddUserRole.getEvents();
    assertNull(events.getParentId());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    List<String> roles = actualAddUserRole.getRoles();
    assertTrue(roles.isEmpty());
    assertTrue(actualAddUserRole.getVariableProperties().isEmpty());
    assertSame(roles, actualAddUserRole.getUserIds());
  }
}
