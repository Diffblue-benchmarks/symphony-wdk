package com.symphony.bdk.workflow.swadl.v1.activity.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetGroupsDiffblueTest {
  /**
   * Test {@link GetGroups#equals(Object)}, and {@link GetGroups#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetGroups#equals(Object)}
   *   <li>{@link GetGroups#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetGroups getGroups = new GetGroups();
    GetGroups getGroups2 = new GetGroups();

    // Act and Assert
    assertEquals(getGroups, getGroups2);
    int expectedHashCodeResult = getGroups.hashCode();
    assertEquals(expectedHashCodeResult, getGroups2.hashCode());
  }

  /**
   * Test {@link GetGroups#equals(Object)}, and {@link GetGroups#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetGroups#equals(Object)}
   *   <li>{@link GetGroups#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetGroups getGroups = new GetGroups();
    getGroups.setStatus("SDL");

    GetGroups getGroups2 = new GetGroups();
    getGroups2.setStatus("SDL");

    // Act and Assert
    assertEquals(getGroups, getGroups2);
    int expectedHashCodeResult = getGroups.hashCode();
    assertEquals(expectedHashCodeResult, getGroups2.hashCode());
  }

  /**
   * Test {@link GetGroups#equals(Object)}, and {@link GetGroups#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetGroups#equals(Object)}
   *   <li>{@link GetGroups#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    GetGroups getGroups = new GetGroups();
    getGroups.setBefore("SDL");

    GetGroups getGroups2 = new GetGroups();
    getGroups2.setBefore("SDL");

    // Act and Assert
    assertEquals(getGroups, getGroups2);
    int expectedHashCodeResult = getGroups.hashCode();
    assertEquals(expectedHashCodeResult, getGroups2.hashCode());
  }

  /**
   * Test {@link GetGroups#equals(Object)}, and {@link GetGroups#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetGroups#equals(Object)}
   *   <li>{@link GetGroups#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    GetGroups getGroups = new GetGroups();
    getGroups.setAfter("SDL");

    GetGroups getGroups2 = new GetGroups();
    getGroups2.setAfter("SDL");

    // Act and Assert
    assertEquals(getGroups, getGroups2);
    int expectedHashCodeResult = getGroups.hashCode();
    assertEquals(expectedHashCodeResult, getGroups2.hashCode());
  }

  /**
   * Test {@link GetGroups#equals(Object)}, and {@link GetGroups#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetGroups#equals(Object)}
   *   <li>{@link GetGroups#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetGroups getGroups = new GetGroups();

    // Act and Assert
    assertEquals(getGroups, getGroups);
    int expectedHashCodeResult = getGroups.hashCode();
    assertEquals(expectedHashCodeResult, getGroups.hashCode());
  }

  /**
   * Test {@link GetGroups#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroups#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetGroups getGroups = new GetGroups();
    getGroups.add("SDL", "Value");

    // Act and Assert
    assertNotEquals(getGroups, new GetGroups());
  }

  /**
   * Test {@link GetGroups#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroups#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetGroups getGroups = new GetGroups();
    getGroups.setStatus("SDL");

    // Act and Assert
    assertNotEquals(getGroups, new GetGroups());
  }

  /**
   * Test {@link GetGroups#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroups#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetGroups getGroups = new GetGroups();
    getGroups.setBefore("SDL");

    // Act and Assert
    assertNotEquals(getGroups, new GetGroups());
  }

  /**
   * Test {@link GetGroups#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroups#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetGroups getGroups = new GetGroups();
    getGroups.setAfter("SDL");

    // Act and Assert
    assertNotEquals(getGroups, new GetGroups());
  }

  /**
   * Test {@link GetGroups#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroups#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetGroups getGroups = new GetGroups();
    getGroups.setLimit(1);

    // Act and Assert
    assertNotEquals(getGroups, new GetGroups());
  }

  /**
   * Test {@link GetGroups#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroups#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    GetGroups getGroups = new GetGroups();
    getGroups.setSortOrder("asc");

    // Act and Assert
    assertNotEquals(getGroups, new GetGroups());
  }

  /**
   * Test {@link GetGroups#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroups#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    GetGroups getGroups = new GetGroups();

    GetGroups getGroups2 = new GetGroups();
    getGroups2.setStatus("SDL");

    // Act and Assert
    assertNotEquals(getGroups, getGroups2);
  }

  /**
   * Test {@link GetGroups#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroups#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    GetGroups getGroups = new GetGroups();

    GetGroups getGroups2 = new GetGroups();
    getGroups2.setBefore("SDL");

    // Act and Assert
    assertNotEquals(getGroups, getGroups2);
  }

  /**
   * Test {@link GetGroups#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroups#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    GetGroups getGroups = new GetGroups();

    GetGroups getGroups2 = new GetGroups();
    getGroups2.setAfter("SDL");

    // Act and Assert
    assertNotEquals(getGroups, getGroups2);
  }

  /**
   * Test {@link GetGroups#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroups#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    GetGroups getGroups = new GetGroups();
    getGroups.setLimit(null);

    // Act and Assert
    assertNotEquals(getGroups, new GetGroups());
  }

  /**
   * Test {@link GetGroups#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroups#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    GetGroups getGroups = new GetGroups();
    getGroups.setSortOrder(null);

    // Act and Assert
    assertNotEquals(getGroups, new GetGroups());
  }

  /**
   * Test {@link GetGroups#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroups#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetGroups(), null);
  }

  /**
   * Test {@link GetGroups#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroups#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroups.equals(Object)", "int GetGroups.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetGroups(), "Different type to GetGroups");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetGroups}
   *   <li>{@link GetGroups#setAfter(String)}
   *   <li>{@link GetGroups#setBefore(String)}
   *   <li>{@link GetGroups#setLimit(Integer)}
   *   <li>{@link GetGroups#setSortOrder(String)}
   *   <li>{@link GetGroups#setStatus(String)}
   *   <li>{@link GetGroups#setType(String)}
   *   <li>{@link GetGroups#toString()}
   *   <li>{@link GetGroups#getAfter()}
   *   <li>{@link GetGroups#getBefore()}
   *   <li>{@link GetGroups#getLimit()}
   *   <li>{@link GetGroups#getSortOrder()}
   *   <li>{@link GetGroups#getStatus()}
   *   <li>{@link GetGroups#getType()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetGroups.<init>()", "String GetGroups.getAfter()", "String GetGroups.getBefore()",
      "Integer GetGroups.getLimit()", "String GetGroups.getSortOrder()", "String GetGroups.getStatus()",
      "String GetGroups.getType()", "void GetGroups.setAfter(String)", "void GetGroups.setBefore(String)",
      "void GetGroups.setLimit(Integer)", "void GetGroups.setSortOrder(String)", "void GetGroups.setStatus(String)",
      "void GetGroups.setType(String)", "String GetGroups.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GetGroups actualGetGroups = new GetGroups();
    actualGetGroups.setAfter("After");
    actualGetGroups.setBefore("Before");
    actualGetGroups.setLimit(1);
    actualGetGroups.setSortOrder("asc");
    actualGetGroups.setStatus("Status");
    actualGetGroups.setType("Type");
    String actualToStringResult = actualGetGroups.toString();
    String actualAfter = actualGetGroups.getAfter();
    String actualBefore = actualGetGroups.getBefore();
    Integer actualLimit = actualGetGroups.getLimit();
    String actualSortOrder = actualGetGroups.getSortOrder();
    String actualStatus = actualGetGroups.getStatus();

    // Assert
    assertEquals("After", actualAfter);
    assertEquals("Before", actualBefore);
    assertEquals("GetGroups(type=Type, status=Status, before=Before, after=After, limit=1, sortOrder=asc)",
        actualToStringResult);
    assertEquals("Status", actualStatus);
    assertEquals("Type", actualGetGroups.getType());
    assertEquals("asc", actualSortOrder);
    assertNull(actualGetGroups.getOn());
    assertNull(actualGetGroups.getElseCondition());
    assertNull(actualGetGroups.getId());
    assertNull(actualGetGroups.getIfCondition());
    assertEquals(1, actualLimit.intValue());
    assertTrue(actualGetGroups.getVariableProperties().isEmpty());
  }
}
