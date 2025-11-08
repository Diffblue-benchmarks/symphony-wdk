package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class OboDiffblueTest {
  /**
   * Test {@link Obo#equals(Object)}, and {@link Obo#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Obo#equals(Object)}
   *   <li>{@link Obo#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Obo.equals(Object)", "int Obo.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    Obo obo2 = new Obo();
    obo2.setUserId(1L);
    obo2.setUsername("janedoe");

    // Act and Assert
    assertEquals(obo, obo2);
    int expectedHashCodeResult = obo.hashCode();
    assertEquals(expectedHashCodeResult, obo2.hashCode());
  }

  /**
   * Test {@link Obo#equals(Object)}, and {@link Obo#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Obo#equals(Object)}
   *   <li>{@link Obo#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Obo.equals(Object)", "int Obo.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(null);
    obo.setUsername("janedoe");

    Obo obo2 = new Obo();
    obo2.setUserId(null);
    obo2.setUsername("janedoe");

    // Act and Assert
    assertEquals(obo, obo2);
    int expectedHashCodeResult = obo.hashCode();
    assertEquals(expectedHashCodeResult, obo2.hashCode());
  }

  /**
   * Test {@link Obo#equals(Object)}, and {@link Obo#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Obo#equals(Object)}
   *   <li>{@link Obo#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Obo.equals(Object)", "int Obo.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername(null);

    Obo obo2 = new Obo();
    obo2.setUserId(1L);
    obo2.setUsername(null);

    // Act and Assert
    assertEquals(obo, obo2);
    int expectedHashCodeResult = obo.hashCode();
    assertEquals(expectedHashCodeResult, obo2.hashCode());
  }

  /**
   * Test {@link Obo#equals(Object)}, and {@link Obo#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Obo#equals(Object)}
   *   <li>{@link Obo#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Obo.equals(Object)", "int Obo.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    // Act and Assert
    assertEquals(obo, obo);
    int expectedHashCodeResult = obo.hashCode();
    assertEquals(expectedHashCodeResult, obo.hashCode());
  }

  /**
   * Test {@link Obo#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Obo#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Obo.equals(Object)", "int Obo.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(2L);
    obo.setUsername("janedoe");

    Obo obo2 = new Obo();
    obo2.setUserId(1L);
    obo2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(obo, obo2);
  }

  /**
   * Test {@link Obo#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Obo#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Obo.equals(Object)", "int Obo.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(null);
    obo.setUsername("janedoe");

    Obo obo2 = new Obo();
    obo2.setUserId(1L);
    obo2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(obo, obo2);
  }

  /**
   * Test {@link Obo#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Obo#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Obo.equals(Object)", "int Obo.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("Username");

    Obo obo2 = new Obo();
    obo2.setUserId(1L);
    obo2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(obo, obo2);
  }

  /**
   * Test {@link Obo#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Obo#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Obo.equals(Object)", "int Obo.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername(null);

    Obo obo2 = new Obo();
    obo2.setUserId(1L);
    obo2.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(obo, obo2);
  }

  /**
   * Test {@link Obo#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Obo#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Obo.equals(Object)", "int Obo.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(obo, null);
  }

  /**
   * Test {@link Obo#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Obo#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Obo.equals(Object)", "int Obo.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(obo, "Different type to Obo");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Obo}
   *   <li>{@link Obo#setUserId(Long)}
   *   <li>{@link Obo#setUsername(String)}
   *   <li>{@link Obo#toString()}
   *   <li>{@link Obo#getUserId()}
   *   <li>{@link Obo#getUsername()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Obo.<init>()", "Long Obo.getUserId()", "String Obo.getUsername()",
      "void Obo.setUserId(Long)", "void Obo.setUsername(String)", "String Obo.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    Obo actualObo = new Obo();
    actualObo.setUserId(1L);
    actualObo.setUsername("janedoe");
    String actualToStringResult = actualObo.toString();
    Long actualUserId = actualObo.getUserId();

    // Assert
    assertEquals("Obo(username=janedoe, userId=1)", actualToStringResult);
    assertEquals("janedoe", actualObo.getUsername());
    assertEquals(1L, actualUserId.longValue());
  }
}
