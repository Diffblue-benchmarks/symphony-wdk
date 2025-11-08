package com.symphony.bdk.workflow.swadl.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class PropertiesDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Properties#equals(Object)}
   *   <li>{@link Properties#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link Properties#equals(Object)}
   *   <li>{@link Properties#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link Properties#equals(Object)}
   *   <li>{@link Properties#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link Properties#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Properties#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Properties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    // Act and Assert
    assertNotEquals(properties, null);
  }

  /**
   * Method under test: {@link Properties#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    // Act and Assert
    assertNotEquals(properties, "Different type to Properties");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Properties}
   *   <li>{@link Properties#setPublish(Boolean)}
   *   <li>{@link Properties#toString()}
   *   <li>{@link Properties#getPublish()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    Properties actualProperties = new Properties();
    actualProperties.setPublish(true);
    String actualToStringResult = actualProperties.toString();

    // Assert that nothing has changed
    assertEquals("Properties(publish=true)", actualToStringResult);
    assertTrue(actualProperties.getPublish());
  }
}
