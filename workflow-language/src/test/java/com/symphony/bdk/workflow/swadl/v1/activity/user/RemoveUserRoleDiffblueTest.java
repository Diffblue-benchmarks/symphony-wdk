package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RemoveUserRoleDiffblueTest {
  /**
   * Test {@link RemoveUserRole#equals(Object)}, and {@link RemoveUserRole#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveUserRole#equals(Object)}
   *   <li>{@link RemoveUserRole#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveUserRole.equals(Object)", "int RemoveUserRole.hashCode()"})
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
   * Test {@link RemoveUserRole#equals(Object)}, and {@link RemoveUserRole#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveUserRole#equals(Object)}
   *   <li>{@link RemoveUserRole#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveUserRole.equals(Object)", "int RemoveUserRole.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RemoveUserRole removeUserRole = new RemoveUserRole();

    // Act and Assert
    assertEquals(removeUserRole, removeUserRole);
    int expectedHashCodeResult = removeUserRole.hashCode();
    assertEquals(expectedHashCodeResult, removeUserRole.hashCode());
  }

  /**
   * Test {@link RemoveUserRole#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RemoveUserRole#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveUserRole.equals(Object)", "int RemoveUserRole.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RemoveUserRole removeUserRole = new RemoveUserRole();
    removeUserRole.add("Key", "Value");

    // Act and Assert
    assertNotEquals(removeUserRole, new RemoveUserRole());
  }

  /**
   * Test {@link RemoveUserRole#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RemoveUserRole#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveUserRole.equals(Object)", "int RemoveUserRole.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RemoveUserRole(), null);
  }

  /**
   * Test {@link RemoveUserRole#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RemoveUserRole#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveUserRole.equals(Object)", "int RemoveUserRole.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RemoveUserRole(), "Different type to RemoveUserRole");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List RemoveUserRole.getRoles()", "List RemoveUserRole.getUserIds()",
      "void RemoveUserRole.setRoles(List)", "void RemoveUserRole.setUserIds(List)", "String RemoveUserRole.toString()"})
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

    // Assert
    assertEquals("RemoveUserRole(userIds=[], roles=[])", actualToStringResult);
    assertTrue(actualRoles.isEmpty());
    assertTrue(actualUserIds.isEmpty());
    assertSame(roles, actualRoles);
    assertSame(userIds, actualUserIds);
  }

  /**
   * Test new {@link RemoveUserRole} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link RemoveUserRole}
   */
  @Test
  @DisplayName("Test new RemoveUserRole (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RemoveUserRole.<init>()"})
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
