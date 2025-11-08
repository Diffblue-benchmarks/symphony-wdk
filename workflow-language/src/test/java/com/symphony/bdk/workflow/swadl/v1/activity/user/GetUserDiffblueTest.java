package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetUserDiffblueTest {
  /**
   * Test {@link GetUser#equals(Object)}, and {@link GetUser#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUser#equals(Object)}
   *   <li>{@link GetUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUser.equals(Object)", "int GetUser.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetUser getUser = new GetUser();
    GetUser getUser2 = new GetUser();

    // Act and Assert
    assertEquals(getUser, getUser2);
    int expectedHashCodeResult = getUser.hashCode();
    assertEquals(expectedHashCodeResult, getUser2.hashCode());
  }

  /**
   * Test {@link GetUser#equals(Object)}, and {@link GetUser#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUser#equals(Object)}
   *   <li>{@link GetUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUser.equals(Object)", "int GetUser.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetUser getUser = new GetUser();
    getUser.setUserId("42");

    GetUser getUser2 = new GetUser();
    getUser2.setUserId("42");

    // Act and Assert
    assertEquals(getUser, getUser2);
    int expectedHashCodeResult = getUser.hashCode();
    assertEquals(expectedHashCodeResult, getUser2.hashCode());
  }

  /**
   * Test {@link GetUser#equals(Object)}, and {@link GetUser#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUser#equals(Object)}
   *   <li>{@link GetUser#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUser.equals(Object)", "int GetUser.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetUser getUser = new GetUser();

    // Act and Assert
    assertEquals(getUser, getUser);
    int expectedHashCodeResult = getUser.hashCode();
    assertEquals(expectedHashCodeResult, getUser.hashCode());
  }

  /**
   * Test {@link GetUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUser.equals(Object)", "int GetUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetUser getUser = new GetUser();
    getUser.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getUser, new GetUser());
  }

  /**
   * Test {@link GetUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUser.equals(Object)", "int GetUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetUser getUser = new GetUser();
    getUser.setUserId("42");

    // Act and Assert
    assertNotEquals(getUser, new GetUser());
  }

  /**
   * Test {@link GetUser#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUser.equals(Object)", "int GetUser.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetUser getUser = new GetUser();

    GetUser getUser2 = new GetUser();
    getUser2.setUserId("42");

    // Act and Assert
    assertNotEquals(getUser, getUser2);
  }

  /**
   * Test {@link GetUser#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUser.equals(Object)", "int GetUser.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetUser(), null);
  }

  /**
   * Test {@link GetUser#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUser#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUser.equals(Object)", "int GetUser.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetUser(), "Different type to GetUser");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetUser}
   *   <li>{@link GetUser#setUserId(String)}
   *   <li>{@link GetUser#toString()}
   *   <li>{@link GetUser#getUserId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetUser.<init>()", "String GetUser.getUserId()", "void GetUser.setUserId(String)",
      "String GetUser.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GetUser actualGetUser = new GetUser();
    actualGetUser.setUserId("42");
    String actualToStringResult = actualGetUser.toString();

    // Assert
    assertEquals("42", actualGetUser.getUserId());
    assertEquals("GetUser(userId=42)", actualToStringResult);
    assertNull(actualGetUser.getOn());
    assertNull(actualGetUser.getElseCondition());
    assertNull(actualGetUser.getId());
    assertNull(actualGetUser.getIfCondition());
    assertTrue(actualGetUser.getVariableProperties().isEmpty());
  }
}
