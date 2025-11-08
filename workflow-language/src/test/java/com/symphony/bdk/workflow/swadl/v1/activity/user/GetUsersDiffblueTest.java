package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GetUsersDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    GetUsers getUsers2 = new GetUsers();

    // Act and Assert
    assertEquals(getUsers, getUsers2);
    int expectedHashCodeResult = getUsers.hashCode();
    assertEquals(expectedHashCodeResult, getUsers2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setUserIds(new ArrayList<>());

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(getUsers, getUsers2);
    int expectedHashCodeResult = getUsers.hashCode();
    assertEquals(expectedHashCodeResult, getUsers2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setEmails(new ArrayList<>());

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setEmails(new ArrayList<>());

    // Act and Assert
    assertEquals(getUsers, getUsers2);
    int expectedHashCodeResult = getUsers.hashCode();
    assertEquals(expectedHashCodeResult, getUsers2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setUsernames(new ArrayList<>());

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setUsernames(new ArrayList<>());

    // Act and Assert
    assertEquals(getUsers, getUsers2);
    int expectedHashCodeResult = getUsers.hashCode();
    assertEquals(expectedHashCodeResult, getUsers2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setLocal(true);

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setLocal(true);

    // Act and Assert
    assertEquals(getUsers, getUsers2);
    int expectedHashCodeResult = getUsers.hashCode();
    assertEquals(expectedHashCodeResult, getUsers2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual6() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setActive(true);

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setActive(true);

    // Act and Assert
    assertEquals(getUsers, getUsers2);
    int expectedHashCodeResult = getUsers.hashCode();
    assertEquals(expectedHashCodeResult, getUsers2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetUsers getUsers = new GetUsers();

    // Act and Assert
    assertEquals(getUsers, getUsers);
    int expectedHashCodeResult = getUsers.hashCode();
    assertEquals(expectedHashCodeResult, getUsers.hashCode());
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getUsers, new GetUsers());
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.add("Key", mock(AddUserRole.class));

    // Act and Assert
    assertNotEquals(getUsers, new GetUsers());
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUsers, new GetUsers());
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setEmails(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUsers, new GetUsers());
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setUsernames(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUsers, new GetUsers());
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setLocal(true);

    // Act and Assert
    assertNotEquals(getUsers, new GetUsers());
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setActive(true);

    // Act and Assert
    assertNotEquals(getUsers, new GetUsers());
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    GetUsers getUsers = new GetUsers();

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUsers, getUsers2);
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    GetUsers getUsers = new GetUsers();

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setEmails(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUsers, getUsers2);
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    GetUsers getUsers = new GetUsers();

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setUsernames(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUsers, getUsers2);
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    GetUsers getUsers = new GetUsers();

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setLocal(true);

    // Act and Assert
    assertNotEquals(getUsers, getUsers2);
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    GetUsers getUsers = new GetUsers();

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setActive(true);

    // Act and Assert
    assertNotEquals(getUsers, getUsers2);
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetUsers(), null);
  }

  /**
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetUsers(), "Different type to GetUsers");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetUsers}
   *   <li>{@link GetUsers#setActive(Boolean)}
   *   <li>{@link GetUsers#setEmails(List)}
   *   <li>{@link GetUsers#setLocal(Boolean)}
   *   <li>{@link GetUsers#setUserIds(List)}
   *   <li>{@link GetUsers#setUsernames(List)}
   *   <li>{@link GetUsers#toString()}
   *   <li>{@link GetUsers#getActive()}
   *   <li>{@link GetUsers#getEmails()}
   *   <li>{@link GetUsers#getLocal()}
   *   <li>{@link GetUsers#getUserIds()}
   *   <li>{@link GetUsers#getUsernames()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetUsers actualGetUsers = new GetUsers();
    actualGetUsers.setActive(true);
    ArrayList<String> emails = new ArrayList<>();
    actualGetUsers.setEmails(emails);
    actualGetUsers.setLocal(true);
    ArrayList<Long> userIds = new ArrayList<>();
    actualGetUsers.setUserIds(userIds);
    ArrayList<String> usernames = new ArrayList<>();
    actualGetUsers.setUsernames(usernames);
    String actualToStringResult = actualGetUsers.toString();
    Boolean actualActive = actualGetUsers.getActive();
    List<String> actualEmails = actualGetUsers.getEmails();
    Boolean actualLocal = actualGetUsers.getLocal();
    List<Long> actualUserIds = actualGetUsers.getUserIds();
    List<String> actualUsernames = actualGetUsers.getUsernames();

    // Assert that nothing has changed
    assertEquals("GetUsers(userIds=[], emails=[], usernames=[], local=true, active=true)", actualToStringResult);
    assertTrue(actualActive);
    assertTrue(actualLocal);
    assertTrue(actualEmails.isEmpty());
    assertTrue(actualUserIds.isEmpty());
    assertTrue(actualUsernames.isEmpty());
    assertTrue(actualGetUsers.getVariableProperties().isEmpty());
    assertSame(emails, actualEmails);
    assertSame(userIds, actualUserIds);
    assertSame(usernames, actualUsernames);
  }
}
