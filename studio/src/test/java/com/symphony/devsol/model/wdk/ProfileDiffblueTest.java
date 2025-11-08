package com.symphony.devsol.model.wdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class ProfileDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Profile#equals(Object)}
   *   <li>{@link Profile#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link Profile#equals(Object)}
   *   <li>{@link Profile#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Profile profile = new Profile(true);

    // Act and Assert
    assertEquals(profile, profile);
    int expectedHashCodeResult = profile.hashCode();
    assertEquals(expectedHashCodeResult, profile.hashCode());
  }

  /**
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Profile profile = new Profile(false);

    // Act and Assert
    assertNotEquals(profile, new Profile(true));
  }

  /**
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Profile(true), null);
  }

  /**
   * Method under test: {@link Profile#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new Profile(true), "Different type to Profile");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Profile#Profile(boolean)}
   *   <li>{@link Profile#setAdmin(boolean)}
   *   <li>{@link Profile#toString()}
   *   <li>{@link Profile#isAdmin()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    Profile actualProfile = new Profile(true);
    actualProfile.setAdmin(true);
    String actualToStringResult = actualProfile.toString();

    // Assert that nothing has changed
    assertEquals("Profile(isAdmin=true)", actualToStringResult);
    assertTrue(actualProfile.isAdmin());
  }
}
