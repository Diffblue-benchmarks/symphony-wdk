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

class AddUserRoleDiffblueTest {
  /**
   * Test {@link AddUserRole#equals(Object)}, and {@link AddUserRole#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddUserRole#equals(Object)}
   *   <li>{@link AddUserRole#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddUserRole.equals(Object)", "int AddUserRole.hashCode()"})
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
   * Test {@link AddUserRole#equals(Object)}, and {@link AddUserRole#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddUserRole#equals(Object)}
   *   <li>{@link AddUserRole#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddUserRole.equals(Object)", "int AddUserRole.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AddUserRole addUserRole = new AddUserRole();

    // Act and Assert
    assertEquals(addUserRole, addUserRole);
    int expectedHashCodeResult = addUserRole.hashCode();
    assertEquals(expectedHashCodeResult, addUserRole.hashCode());
  }

  /**
   * Test {@link AddUserRole#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddUserRole#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddUserRole.equals(Object)", "int AddUserRole.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AddUserRole addUserRole = new AddUserRole();
    addUserRole.add("Key", "Value");

    // Act and Assert
    assertNotEquals(addUserRole, new AddUserRole());
  }

  /**
   * Test {@link AddUserRole#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddUserRole#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddUserRole.equals(Object)", "int AddUserRole.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AddUserRole(), null);
  }

  /**
   * Test {@link AddUserRole#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddUserRole#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddUserRole.equals(Object)", "int AddUserRole.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AddUserRole(), "Different type to AddUserRole");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List AddUserRole.getRoles()", "List AddUserRole.getUserIds()", "void AddUserRole.setRoles(List)",
      "void AddUserRole.setUserIds(List)", "String AddUserRole.toString()"})
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

    // Assert
    assertEquals("AddUserRole(userIds=[], roles=[])", actualToStringResult);
    assertTrue(actualRoles.isEmpty());
    assertTrue(actualUserIds.isEmpty());
    assertSame(roles, actualRoles);
    assertSame(userIds, actualUserIds);
  }

  /**
   * Test new {@link AddUserRole} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link AddUserRole}
   */
  @Test
  @DisplayName("Test new AddUserRole (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AddUserRole.<init>()"})
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
