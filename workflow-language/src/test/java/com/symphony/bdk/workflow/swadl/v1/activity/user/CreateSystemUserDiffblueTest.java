package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CreateSystemUserDiffblueTest {
  /**
   * Test new {@link CreateSystemUser} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link CreateSystemUser}
   */
  @Test
  @DisplayName("Test new CreateSystemUser (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CreateSystemUser.<init>()"})
  void testNewCreateSystemUser() {
    // Arrange and Act
    CreateSystemUser actualCreateSystemUser = new CreateSystemUser();

    // Assert
    assertEquals("SYSTEM", actualCreateSystemUser.getType());
    assertNull(actualCreateSystemUser.getOn());
    assertNull(actualCreateSystemUser.getBusiness());
    assertNull(actualCreateSystemUser.getContact());
    assertNull(actualCreateSystemUser.getKeys());
    assertNull(actualCreateSystemUser.getPassword());
    assertNull(actualCreateSystemUser.getElseCondition());
    assertNull(actualCreateSystemUser.getId());
    assertNull(actualCreateSystemUser.getIfCondition());
    assertNull(actualCreateSystemUser.getDisplayName());
    assertNull(actualCreateSystemUser.getEmail());
    assertNull(actualCreateSystemUser.getFirstname());
    assertNull(actualCreateSystemUser.getLastname());
    assertNull(actualCreateSystemUser.getRecommendedLanguage());
    assertNull(actualCreateSystemUser.getStatus());
    assertNull(actualCreateSystemUser.getUsername());
    assertNull(actualCreateSystemUser.getRoles());
    assertNull(actualCreateSystemUser.getEntitlements());
    assertTrue(actualCreateSystemUser.getVariableProperties().isEmpty());
  }

  /**
   * Test {@link CreateSystemUser#equals(Object)}, and {@link CreateSystemUser#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateSystemUser#equals(Object)}
   *   <li>{@link CreateSystemUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateSystemUser.equals(Object)", "int CreateSystemUser.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateSystemUser createSystemUser = new CreateSystemUser();
    CreateSystemUser createSystemUser2 = new CreateSystemUser();

    // Act and Assert
    assertEquals(createSystemUser, createSystemUser2);
    int expectedHashCodeResult = createSystemUser.hashCode();
    assertEquals(expectedHashCodeResult, createSystemUser2.hashCode());
  }

  /**
   * Test {@link CreateSystemUser#equals(Object)}, and {@link CreateSystemUser#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateSystemUser#equals(Object)}
   *   <li>{@link CreateSystemUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateSystemUser.equals(Object)", "int CreateSystemUser.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateSystemUser createSystemUser = new CreateSystemUser();

    // Act and Assert
    assertEquals(createSystemUser, createSystemUser);
    int expectedHashCodeResult = createSystemUser.hashCode();
    assertEquals(expectedHashCodeResult, createSystemUser.hashCode());
  }

  /**
   * Test {@link CreateSystemUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateSystemUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateSystemUser.equals(Object)", "int CreateSystemUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateSystemUser createSystemUser = new CreateSystemUser();
    createSystemUser.add("SYSTEM", "Value");

    // Act and Assert
    assertNotEquals(createSystemUser, new CreateSystemUser());
  }

  /**
   * Test {@link CreateSystemUser#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateSystemUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateSystemUser.equals(Object)", "int CreateSystemUser.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateSystemUser(), null);
  }

  /**
   * Test {@link CreateSystemUser#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateSystemUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateSystemUser.equals(Object)", "int CreateSystemUser.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateSystemUser(), "Different type to CreateSystemUser");
  }

  /**
   * Test {@link CreateSystemUser#toString()}.
   * <p>
   * Method under test: {@link CreateSystemUser#toString()}
   */
  @Test
  @DisplayName("Test toString()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"java.lang.String CreateSystemUser.toString()"})
  void testToString() {
    // Arrange, Act and Assert
    assertEquals("CreateSystemUser()", (new CreateSystemUser()).toString());
  }
}
