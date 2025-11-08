package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UpdateSystemUserDiffblueTest {
  /**
   * Test new {@link UpdateSystemUser} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link UpdateSystemUser}
   */
  @Test
  @DisplayName("Test new UpdateSystemUser (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UpdateSystemUser.<init>()"})
  void testNewUpdateSystemUser() {
    // Arrange and Act
    UpdateSystemUser actualUpdateSystemUser = new UpdateSystemUser();

    // Assert
    assertEquals("SYSTEM", actualUpdateSystemUser.getType());
    assertNull(actualUpdateSystemUser.getOn());
    assertNull(actualUpdateSystemUser.getBusiness());
    assertNull(actualUpdateSystemUser.getContact());
    assertNull(actualUpdateSystemUser.getKeys());
    assertNull(actualUpdateSystemUser.getPassword());
    assertNull(actualUpdateSystemUser.getElseCondition());
    assertNull(actualUpdateSystemUser.getId());
    assertNull(actualUpdateSystemUser.getIfCondition());
    assertNull(actualUpdateSystemUser.getDisplayName());
    assertNull(actualUpdateSystemUser.getEmail());
    assertNull(actualUpdateSystemUser.getFirstname());
    assertNull(actualUpdateSystemUser.getLastname());
    assertNull(actualUpdateSystemUser.getRecommendedLanguage());
    assertNull(actualUpdateSystemUser.getStatus());
    assertNull(actualUpdateSystemUser.getUsername());
    assertNull(actualUpdateSystemUser.getUserId());
    assertNull(actualUpdateSystemUser.getRoles());
    assertNull(actualUpdateSystemUser.getEntitlements());
    assertTrue(actualUpdateSystemUser.getVariableProperties().isEmpty());
  }

  /**
   * Test {@link UpdateSystemUser#equals(Object)}, and {@link UpdateSystemUser#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateSystemUser#equals(Object)}
   *   <li>{@link UpdateSystemUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateSystemUser.equals(Object)", "int UpdateSystemUser.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UpdateSystemUser updateSystemUser = new UpdateSystemUser();
    UpdateSystemUser updateSystemUser2 = new UpdateSystemUser();

    // Act and Assert
    assertEquals(updateSystemUser, updateSystemUser2);
    int expectedHashCodeResult = updateSystemUser.hashCode();
    assertEquals(expectedHashCodeResult, updateSystemUser2.hashCode());
  }

  /**
   * Test {@link UpdateSystemUser#equals(Object)}, and {@link UpdateSystemUser#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateSystemUser#equals(Object)}
   *   <li>{@link UpdateSystemUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateSystemUser.equals(Object)", "int UpdateSystemUser.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UpdateSystemUser updateSystemUser = new UpdateSystemUser();

    // Act and Assert
    assertEquals(updateSystemUser, updateSystemUser);
    int expectedHashCodeResult = updateSystemUser.hashCode();
    assertEquals(expectedHashCodeResult, updateSystemUser.hashCode());
  }

  /**
   * Test {@link UpdateSystemUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateSystemUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateSystemUser.equals(Object)", "int UpdateSystemUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UpdateSystemUser updateSystemUser = new UpdateSystemUser();
    updateSystemUser.add("SYSTEM", "Value");

    // Act and Assert
    assertNotEquals(updateSystemUser, new UpdateSystemUser());
  }

  /**
   * Test {@link UpdateSystemUser#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateSystemUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateSystemUser.equals(Object)", "int UpdateSystemUser.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateSystemUser(), null);
  }

  /**
   * Test {@link UpdateSystemUser#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateSystemUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateSystemUser.equals(Object)", "int UpdateSystemUser.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateSystemUser(), "Different type to UpdateSystemUser");
  }

  /**
   * Test {@link UpdateSystemUser#toString()}.
   * <p>
   * Method under test: {@link UpdateSystemUser#toString()}
   */
  @Test
  @DisplayName("Test toString()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String UpdateSystemUser.toString()"})
  void testToString() {
    // Arrange, Act and Assert
    assertEquals("UpdateSystemUser()", (new UpdateSystemUser()).toString());
  }
}
