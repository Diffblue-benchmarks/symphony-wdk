package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class GetUserDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUser#equals(Object)}
   *   <li>{@link GetUser#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link GetUser#equals(Object)}
   *   <li>{@link GetUser#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link GetUser#equals(Object)}
   *   <li>{@link GetUser#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetUser getUser = new GetUser();

    // Act and Assert
    assertEquals(getUser, getUser);
    int expectedHashCodeResult = getUser.hashCode();
    assertEquals(expectedHashCodeResult, getUser.hashCode());
  }

  /**
   * Method under test: {@link GetUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetUser getUser = new GetUser();
    getUser.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getUser, new GetUser());
  }

  /**
   * Method under test: {@link GetUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetUser getUser = new GetUser();
    getUser.add("Key", mock(AddUserRole.class));

    // Act and Assert
    assertNotEquals(getUser, new GetUser());
  }

  /**
   * Method under test: {@link GetUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetUser getUser = new GetUser();
    getUser.setUserId("42");

    // Act and Assert
    assertNotEquals(getUser, new GetUser());
  }

  /**
   * Method under test: {@link GetUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetUser getUser = new GetUser();

    GetUser getUser2 = new GetUser();
    getUser2.setUserId("42");

    // Act and Assert
    assertNotEquals(getUser, getUser2);
  }

  /**
   * Method under test: {@link GetUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetUser(), null);
  }

  /**
   * Method under test: {@link GetUser#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetUser(), "Different type to GetUser");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetUser}
   *   <li>{@link GetUser#setUserId(String)}
   *   <li>{@link GetUser#toString()}
   *   <li>{@link GetUser#getUserId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetUser actualGetUser = new GetUser();
    actualGetUser.setUserId("42");
    String actualToStringResult = actualGetUser.toString();

    // Assert that nothing has changed
    assertEquals("42", actualGetUser.getUserId());
    assertEquals("GetUser(userId=42)", actualToStringResult);
    assertTrue(actualGetUser.getVariableProperties().isEmpty());
  }
}
