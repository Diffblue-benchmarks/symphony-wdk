package com.symphony.devsol.model.wdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SimpleUserDiffblueTest {
  /**
   * Test {@link SimpleUser#equals(Object)}, and {@link SimpleUser#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleUser#equals(Object)}
   *   <li>{@link SimpleUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SimpleUser.equals(Object)", "int SimpleUser.hashCode()"})
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
   * Test {@link SimpleUser#equals(Object)}, and {@link SimpleUser#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleUser#equals(Object)}
   *   <li>{@link SimpleUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SimpleUser.equals(Object)", "int SimpleUser.hashCode()"})
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
   * Test {@link SimpleUser#equals(Object)}, and {@link SimpleUser#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleUser#equals(Object)}
   *   <li>{@link SimpleUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SimpleUser.equals(Object)", "int SimpleUser.hashCode()"})
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
   * Test {@link SimpleUser#equals(Object)}, and {@link SimpleUser#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SimpleUser#equals(Object)}
   *   <li>{@link SimpleUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SimpleUser.equals(Object)", "int SimpleUser.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SimpleUser simpleUser = new SimpleUser(1L, "Display Name");

    // Act and Assert
    assertEquals(simpleUser, simpleUser);
    int expectedHashCodeResult = simpleUser.hashCode();
    assertEquals(expectedHashCodeResult, simpleUser.hashCode());
  }

  /**
   * Test {@link SimpleUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SimpleUser.equals(Object)", "int SimpleUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SimpleUser simpleUser = new SimpleUser(2L, "Display Name");

    // Act and Assert
    assertNotEquals(simpleUser, new SimpleUser(1L, "Display Name"));
  }

  /**
   * Test {@link SimpleUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SimpleUser.equals(Object)", "int SimpleUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SimpleUser simpleUser = new SimpleUser(null, "Display Name");

    // Act and Assert
    assertNotEquals(simpleUser, new SimpleUser(1L, "Display Name"));
  }

  /**
   * Test {@link SimpleUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SimpleUser.equals(Object)", "int SimpleUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SimpleUser simpleUser = new SimpleUser(1L, null);

    // Act and Assert
    assertNotEquals(simpleUser, new SimpleUser(1L, "Display Name"));
  }

  /**
   * Test {@link SimpleUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SimpleUser.equals(Object)", "int SimpleUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    SimpleUser simpleUser = new SimpleUser(1L, "com.symphony.devsol.model.wdk.SimpleUser");

    // Act and Assert
    assertNotEquals(simpleUser, new SimpleUser(1L, "Display Name"));
  }

  /**
   * Test {@link SimpleUser#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SimpleUser.equals(Object)", "int SimpleUser.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SimpleUser(1L, "Display Name"), null);
  }

  /**
   * Test {@link SimpleUser#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SimpleUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SimpleUser.equals(Object)", "int SimpleUser.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SimpleUser(1L, "Display Name"), "Different type to SimpleUser");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SimpleUser.<init>(Long, String)", "String SimpleUser.getDisplayName()",
      "Long SimpleUser.getId()", "void SimpleUser.setDisplayName(String)", "void SimpleUser.setId(Long)",
      "String SimpleUser.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    SimpleUser actualSimpleUser = new SimpleUser(1L, "Display Name");
    actualSimpleUser.setDisplayName("Display Name");
    actualSimpleUser.setId(1L);
    String actualToStringResult = actualSimpleUser.toString();
    String actualDisplayName = actualSimpleUser.getDisplayName();

    // Assert
    assertEquals("Display Name", actualDisplayName);
    assertEquals("SimpleUser(id=1, displayName=Display Name)", actualToStringResult);
    assertEquals(1L, actualSimpleUser.getId().longValue());
  }
}
