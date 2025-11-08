package com.symphony.devsol.model.wdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

class SimpleUserDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleUser#equals(Object)}
   *   <li>{@link SimpleUser#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    SimpleUser simpleUser = new SimpleUser(1L, "Display Name");
    SimpleUser simpleUser2 = new SimpleUser(1L, "Display Name");

    // Act and Assert
    assertEquals(simpleUser, simpleUser2);
    int expectedHashCodeResult = simpleUser.hashCode();
    assertEquals(expectedHashCodeResult, simpleUser2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleUser#equals(Object)}
   *   <li>{@link SimpleUser#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    SimpleUser simpleUser = new SimpleUser(null, "Display Name");
    SimpleUser simpleUser2 = new SimpleUser(null, "Display Name");

    // Act and Assert
    assertEquals(simpleUser, simpleUser2);
    int expectedHashCodeResult = simpleUser.hashCode();
    assertEquals(expectedHashCodeResult, simpleUser2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleUser#equals(Object)}
   *   <li>{@link SimpleUser#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    SimpleUser simpleUser = new SimpleUser(1L, null);
    SimpleUser simpleUser2 = new SimpleUser(1L, null);

    // Act and Assert
    assertEquals(simpleUser, simpleUser2);
    int expectedHashCodeResult = simpleUser.hashCode();
    assertEquals(expectedHashCodeResult, simpleUser2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleUser#equals(Object)}
   *   <li>{@link SimpleUser#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SimpleUser simpleUser = new SimpleUser(1L, "Display Name");

    // Act and Assert
    assertEquals(simpleUser, simpleUser);
    int expectedHashCodeResult = simpleUser.hashCode();
    assertEquals(expectedHashCodeResult, simpleUser.hashCode());
  }

  /**
   * Method under test: {@link SimpleUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SimpleUser simpleUser = new SimpleUser(2L, "Display Name");

    // Act and Assert
    assertNotEquals(simpleUser, new SimpleUser(1L, "Display Name"));
  }

  /**
   * Method under test: {@link SimpleUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SimpleUser simpleUser = new SimpleUser(null, "Display Name");

    // Act and Assert
    assertNotEquals(simpleUser, new SimpleUser(1L, "Display Name"));
  }

  /**
   * Method under test: {@link SimpleUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SimpleUser simpleUser = new SimpleUser(1L, null);

    // Act and Assert
    assertNotEquals(simpleUser, new SimpleUser(1L, "Display Name"));
  }

  /**
   * Method under test: {@link SimpleUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    SimpleUser simpleUser = new SimpleUser(1L, "com.symphony.devsol.model.wdk.SimpleUser");

    // Act and Assert
    assertNotEquals(simpleUser, new SimpleUser(1L, "Display Name"));
  }

  /**
   * Method under test: {@link SimpleUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SimpleUser(1L, "Display Name"), null);
  }

  /**
   * Method under test: {@link SimpleUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SimpleUser(1L, "Display Name"), "Different type to SimpleUser");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleUser#SimpleUser(Long, String)}
   *   <li>{@link SimpleUser#setDisplayName(String)}
   *   <li>{@link SimpleUser#setId(Long)}
   *   <li>{@link SimpleUser#toString()}
   *   <li>{@link SimpleUser#getDisplayName()}
   *   <li>{@link SimpleUser#getId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    SimpleUser actualSimpleUser = new SimpleUser(1L, "Display Name");
    actualSimpleUser.setDisplayName("Display Name");
    actualSimpleUser.setId(1L);
    String actualToStringResult = actualSimpleUser.toString();
    String actualDisplayName = actualSimpleUser.getDisplayName();

    // Assert that nothing has changed
    assertEquals("Display Name", actualDisplayName);
    assertEquals("SimpleUser(id=1, displayName=Display Name)", actualToStringResult);
    assertEquals(1L, actualSimpleUser.getId().longValue());
  }
}
