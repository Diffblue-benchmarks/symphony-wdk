package com.symphony.bdk.workflow.swadl.v1.activity.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetUsersDiffblueTest {
  /**
   * Test {@link GetUsers#equals(Object)}, and {@link GetUsers#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
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
   * Test {@link GetUsers#equals(Object)}, and {@link GetUsers#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
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
   * Test {@link GetUsers#equals(Object)}, and {@link GetUsers#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
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
   * Test {@link GetUsers#equals(Object)}, and {@link GetUsers#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
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
   * Test {@link GetUsers#equals(Object)}, and {@link GetUsers#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
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
   * Test {@link GetUsers#equals(Object)}, and {@link GetUsers#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
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
   * Test {@link GetUsers#equals(Object)}, and {@link GetUsers#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUsers#equals(Object)}
   *   <li>{@link GetUsers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetUsers getUsers = new GetUsers();

    // Act and Assert
    assertEquals(getUsers, getUsers);
    int expectedHashCodeResult = getUsers.hashCode();
    assertEquals(expectedHashCodeResult, getUsers.hashCode());
  }

  /**
   * Test {@link GetUsers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getUsers, new GetUsers());
  }

  /**
   * Test {@link GetUsers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUsers, new GetUsers());
  }

  /**
   * Test {@link GetUsers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setEmails(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUsers, new GetUsers());
  }

  /**
   * Test {@link GetUsers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setUsernames(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUsers, new GetUsers());
  }

  /**
   * Test {@link GetUsers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setLocal(true);

    // Act and Assert
    assertNotEquals(getUsers, new GetUsers());
  }

  /**
   * Test {@link GetUsers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    GetUsers getUsers = new GetUsers();
    getUsers.setActive(true);

    // Act and Assert
    assertNotEquals(getUsers, new GetUsers());
  }

  /**
   * Test {@link GetUsers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    GetUsers getUsers = new GetUsers();

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUsers, getUsers2);
  }

  /**
   * Test {@link GetUsers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    GetUsers getUsers = new GetUsers();

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setEmails(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUsers, getUsers2);
  }

  /**
   * Test {@link GetUsers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    GetUsers getUsers = new GetUsers();

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setUsernames(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUsers, getUsers2);
  }

  /**
   * Test {@link GetUsers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    GetUsers getUsers = new GetUsers();

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setLocal(true);

    // Act and Assert
    assertNotEquals(getUsers, getUsers2);
  }

  /**
   * Test {@link GetUsers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    GetUsers getUsers = new GetUsers();

    GetUsers getUsers2 = new GetUsers();
    getUsers2.setActive(true);

    // Act and Assert
    assertNotEquals(getUsers, getUsers2);
  }

  /**
   * Test {@link GetUsers#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetUsers(), null);
  }

  /**
   * Test {@link GetUsers#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUsers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUsers.equals(Object)", "int GetUsers.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetUsers(), "Different type to GetUsers");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetUsers.<init>()", "Boolean GetUsers.getActive()", "List GetUsers.getEmails()",
      "Boolean GetUsers.getLocal()", "List GetUsers.getUserIds()", "List GetUsers.getUsernames()",
      "void GetUsers.setActive(Boolean)", "void GetUsers.setEmails(List)", "void GetUsers.setLocal(Boolean)",
      "void GetUsers.setUserIds(List)", "void GetUsers.setUsernames(List)", "String GetUsers.toString()"})
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

    // Assert
    assertEquals("GetUsers(userIds=[], emails=[], usernames=[], local=true, active=true)", actualToStringResult);
    assertNull(actualGetUsers.getOn());
    assertNull(actualGetUsers.getObo());
    assertNull(actualGetUsers.getElseCondition());
    assertNull(actualGetUsers.getId());
    assertNull(actualGetUsers.getIfCondition());
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
