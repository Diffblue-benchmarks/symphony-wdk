package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class OboDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Obo#equals(Object)}
   *   <li>{@link Obo#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link Obo#equals(Object)}
   *   <li>{@link Obo#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link Obo#equals(Object)}
   *   <li>{@link Obo#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link Obo#equals(Object)}
   *   <li>{@link Obo#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link Obo#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Obo#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Obo#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Obo#equals(Object)}
   */
  @Test
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
   * Method under test: {@link Obo#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(obo, null);
  }

  /**
   * Method under test: {@link Obo#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");

    // Act and Assert
    assertNotEquals(obo, "Different type to Obo");
  }

  /**
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
  void testGettersAndSetters() {
    // Arrange and Act
    Obo actualObo = new Obo();
    actualObo.setUserId(1L);
    actualObo.setUsername("janedoe");
    String actualToStringResult = actualObo.toString();
    Long actualUserId = actualObo.getUserId();

    // Assert that nothing has changed
    assertEquals("Obo(username=janedoe, userId=1)", actualToStringResult);
    assertEquals("janedoe", actualObo.getUsername());
    assertEquals(1L, actualUserId.longValue());
  }
}
