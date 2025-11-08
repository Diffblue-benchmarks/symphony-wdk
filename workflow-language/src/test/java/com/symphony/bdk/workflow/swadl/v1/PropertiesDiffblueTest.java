package com.symphony.bdk.workflow.swadl.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class PropertiesDiffblueTest {
  /**
   * Test {@link Properties#equals(Object)}, and {@link Properties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Properties#equals(Object)}
   *   <li>{@link Properties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Properties.equals(Object)", "int Properties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    // Act and Assert
    assertEquals(properties, properties2);
    int expectedHashCodeResult = properties.hashCode();
    assertEquals(expectedHashCodeResult, properties2.hashCode());
  }

  /**
   * Test {@link Properties#equals(Object)}, and {@link Properties#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Properties#equals(Object)}
   *   <li>{@link Properties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Properties.equals(Object)", "int Properties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(null);

    Properties properties2 = new Properties();
    properties2.setPublish(null);

    // Act and Assert
    assertEquals(properties, properties2);
    int expectedHashCodeResult = properties.hashCode();
    assertEquals(expectedHashCodeResult, properties2.hashCode());
  }

  /**
   * Test {@link Properties#equals(Object)}, and {@link Properties#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Properties#equals(Object)}
   *   <li>{@link Properties#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Properties.equals(Object)", "int Properties.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    // Act and Assert
    assertEquals(properties, properties);
    int expectedHashCodeResult = properties.hashCode();
    assertEquals(expectedHashCodeResult, properties.hashCode());
  }

  /**
   * Test {@link Properties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Properties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Properties.equals(Object)", "int Properties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(false);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    // Act and Assert
    assertNotEquals(properties, properties2);
  }

  /**
   * Test {@link Properties#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Properties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Properties.equals(Object)", "int Properties.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(null);

    Properties properties2 = new Properties();
    properties2.setPublish(true);

    // Act and Assert
    assertNotEquals(properties, properties2);
  }

  /**
   * Test {@link Properties#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Properties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Properties.equals(Object)", "int Properties.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    // Act and Assert
    assertNotEquals(properties, null);
  }

  /**
   * Test {@link Properties#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Properties#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Properties.equals(Object)", "int Properties.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    // Act and Assert
    assertNotEquals(properties, "Different type to Properties");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Properties}
   *   <li>{@link Properties#setPublish(Boolean)}
   *   <li>{@link Properties#toString()}
   *   <li>{@link Properties#getPublish()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Properties.<init>()", "Boolean Properties.getPublish()",
      "void Properties.setPublish(Boolean)", "String Properties.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    Properties actualProperties = new Properties();
    actualProperties.setPublish(true);
    String actualToStringResult = actualProperties.toString();

    // Assert
    assertEquals("Properties(publish=true)", actualToStringResult);
    assertTrue(actualProperties.getPublish());
  }
}
