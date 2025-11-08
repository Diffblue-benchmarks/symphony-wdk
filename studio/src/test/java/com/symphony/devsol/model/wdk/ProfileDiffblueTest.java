package com.symphony.devsol.model.wdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ProfileDiffblueTest {
  /**
   * Test {@link Profile#equals(Object)}, and {@link Profile#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Profile#equals(Object)}
   *   <li>{@link Profile#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Profile profile = new Profile(true);
    Profile profile2 = new Profile(true);

    // Act and Assert
    assertEquals(profile, profile2);
    int expectedHashCodeResult = profile.hashCode();
    assertEquals(expectedHashCodeResult, profile2.hashCode());
  }

  /**
   * Test {@link Profile#equals(Object)}, and {@link Profile#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Profile#equals(Object)}
   *   <li>{@link Profile#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Profile profile = new Profile(true);

    // Act and Assert
    assertEquals(profile, profile);
    int expectedHashCodeResult = profile.hashCode();
    assertEquals(expectedHashCodeResult, profile.hashCode());
  }

  /**
   * Test {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Profile profile = new Profile(false);

    // Act and Assert
    assertNotEquals(profile, new Profile(true));
  }

  /**
   * Test {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Profile(true), null);
  }

  /**
   * Test {@link Profile#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Profile.equals(Object)", "int Profile.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Profile(true), "Different type to Profile");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Profile#Profile(boolean)}
   *   <li>{@link Profile#setAdmin(boolean)}
   *   <li>{@link Profile#toString()}
   *   <li>{@link Profile#isAdmin()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Profile.<init>(boolean)", "boolean Profile.isAdmin()", "void Profile.setAdmin(boolean)",
      "String Profile.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    Profile actualProfile = new Profile(true);
    actualProfile.setAdmin(true);
    String actualToStringResult = actualProfile.toString();

    // Assert
    assertEquals("Profile(isAdmin=true)", actualToStringResult);
    assertTrue(actualProfile.isAdmin());
  }
}
