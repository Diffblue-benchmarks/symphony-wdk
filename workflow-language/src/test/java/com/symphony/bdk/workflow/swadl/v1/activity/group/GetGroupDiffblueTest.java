package com.symphony.bdk.workflow.swadl.v1.activity.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetGroupDiffblueTest {
  /**
   * Test {@link GetGroup#equals(Object)}, and {@link GetGroup#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetGroup#equals(Object)}
   *   <li>{@link GetGroup#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroup.equals(Object)", "int GetGroup.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetGroup getGroup = new GetGroup();
    GetGroup getGroup2 = new GetGroup();

    // Act and Assert
    assertEquals(getGroup, getGroup2);
    int expectedHashCodeResult = getGroup.hashCode();
    assertEquals(expectedHashCodeResult, getGroup2.hashCode());
  }

  /**
   * Test {@link GetGroup#equals(Object)}, and {@link GetGroup#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetGroup#equals(Object)}
   *   <li>{@link GetGroup#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroup.equals(Object)", "int GetGroup.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetGroup getGroup = new GetGroup();
    getGroup.setGroupId("42");

    GetGroup getGroup2 = new GetGroup();
    getGroup2.setGroupId("42");

    // Act and Assert
    assertEquals(getGroup, getGroup2);
    int expectedHashCodeResult = getGroup.hashCode();
    assertEquals(expectedHashCodeResult, getGroup2.hashCode());
  }

  /**
   * Test {@link GetGroup#equals(Object)}, and {@link GetGroup#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetGroup#equals(Object)}
   *   <li>{@link GetGroup#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroup.equals(Object)", "int GetGroup.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetGroup getGroup = new GetGroup();

    // Act and Assert
    assertEquals(getGroup, getGroup);
    int expectedHashCodeResult = getGroup.hashCode();
    assertEquals(expectedHashCodeResult, getGroup.hashCode());
  }

  /**
   * Test {@link GetGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroup.equals(Object)", "int GetGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetGroup getGroup = new GetGroup();
    getGroup.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getGroup, new GetGroup());
  }

  /**
   * Test {@link GetGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroup.equals(Object)", "int GetGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetGroup getGroup = new GetGroup();
    getGroup.setGroupId("42");

    // Act and Assert
    assertNotEquals(getGroup, new GetGroup());
  }

  /**
   * Test {@link GetGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroup.equals(Object)", "int GetGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetGroup getGroup = new GetGroup();

    GetGroup getGroup2 = new GetGroup();
    getGroup2.setGroupId("42");

    // Act and Assert
    assertNotEquals(getGroup, getGroup2);
  }

  /**
   * Test {@link GetGroup#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroup.equals(Object)", "int GetGroup.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetGroup(), null);
  }

  /**
   * Test {@link GetGroup#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetGroup.equals(Object)", "int GetGroup.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetGroup(), "Different type to GetGroup");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetGroup}
   *   <li>{@link GetGroup#setGroupId(String)}
   *   <li>{@link GetGroup#toString()}
   *   <li>{@link GetGroup#getGroupId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetGroup.<init>()", "String GetGroup.getGroupId()", "void GetGroup.setGroupId(String)",
      "String GetGroup.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GetGroup actualGetGroup = new GetGroup();
    actualGetGroup.setGroupId("42");
    String actualToStringResult = actualGetGroup.toString();

    // Assert
    assertEquals("42", actualGetGroup.getGroupId());
    assertEquals("GetGroup(groupId=42)", actualToStringResult);
    assertNull(actualGetGroup.getOn());
    assertNull(actualGetGroup.getElseCondition());
    assertNull(actualGetGroup.getId());
    assertNull(actualGetGroup.getIfCondition());
    assertTrue(actualGetGroup.getVariableProperties().isEmpty());
  }
}
