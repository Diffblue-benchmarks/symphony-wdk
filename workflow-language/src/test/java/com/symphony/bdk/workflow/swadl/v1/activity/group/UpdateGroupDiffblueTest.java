package com.symphony.bdk.workflow.swadl.v1.activity.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UpdateGroupDiffblueTest {
  /**
   * Test {@link UpdateGroup#equals(Object)}, and {@link UpdateGroup#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#equals(Object)}
   *   <li>{@link UpdateGroup#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    UpdateGroup updateGroup2 = new UpdateGroup();

    // Act and Assert
    assertEquals(updateGroup, updateGroup2);
    int expectedHashCodeResult = updateGroup.hashCode();
    assertEquals(expectedHashCodeResult, updateGroup2.hashCode());
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}, and {@link UpdateGroup#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#equals(Object)}
   *   <li>{@link UpdateGroup#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setGroupId("42");

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setGroupId("42");

    // Act and Assert
    assertEquals(updateGroup, updateGroup2);
    int expectedHashCodeResult = updateGroup.hashCode();
    assertEquals(expectedHashCodeResult, updateGroup2.hashCode());
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}, and {@link UpdateGroup#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#equals(Object)}
   *   <li>{@link UpdateGroup#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setStatus("SDL");

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setStatus("SDL");

    // Act and Assert
    assertEquals(updateGroup, updateGroup2);
    int expectedHashCodeResult = updateGroup.hashCode();
    assertEquals(expectedHashCodeResult, updateGroup2.hashCode());
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}, and {@link UpdateGroup#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#equals(Object)}
   *   <li>{@link UpdateGroup#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setEtag("SDL");

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setEtag("SDL");

    // Act and Assert
    assertEquals(updateGroup, updateGroup2);
    int expectedHashCodeResult = updateGroup.hashCode();
    assertEquals(expectedHashCodeResult, updateGroup2.hashCode());
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}, and {@link UpdateGroup#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#equals(Object)}
   *   <li>{@link UpdateGroup#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setImagePath("SDL");

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setImagePath("SDL");

    // Act and Assert
    assertEquals(updateGroup, updateGroup2);
    int expectedHashCodeResult = updateGroup.hashCode();
    assertEquals(expectedHashCodeResult, updateGroup2.hashCode());
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}, and {@link UpdateGroup#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#equals(Object)}
   *   <li>{@link UpdateGroup#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    // Act and Assert
    assertEquals(updateGroup, updateGroup);
    int expectedHashCodeResult = updateGroup.hashCode();
    assertEquals(expectedHashCodeResult, updateGroup.hashCode());
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.add("SDL", "Value");

    // Act and Assert
    assertNotEquals(updateGroup, new UpdateGroup());
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setGroupId("42");

    // Act and Assert
    assertNotEquals(updateGroup, new UpdateGroup());
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setStatus("SDL");

    // Act and Assert
    assertNotEquals(updateGroup, new UpdateGroup());
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setEtag("SDL");

    // Act and Assert
    assertNotEquals(updateGroup, new UpdateGroup());
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();
    updateGroup.setImagePath("SDL");

    // Act and Assert
    assertNotEquals(updateGroup, new UpdateGroup());
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setGroupId("42");

    // Act and Assert
    assertNotEquals(updateGroup, updateGroup2);
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setStatus("SDL");

    // Act and Assert
    assertNotEquals(updateGroup, updateGroup2);
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setEtag("SDL");

    // Act and Assert
    assertNotEquals(updateGroup, updateGroup2);
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    UpdateGroup updateGroup2 = new UpdateGroup();
    updateGroup2.setImagePath("SDL");

    // Act and Assert
    assertNotEquals(updateGroup, updateGroup2);
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateGroup(), null);
  }

  /**
   * Test {@link UpdateGroup#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateGroup#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateGroup.equals(Object)", "int UpdateGroup.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateGroup(), "Different type to UpdateGroup");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateGroup#setEtag(String)}
   *   <li>{@link UpdateGroup#setGroupId(String)}
   *   <li>{@link UpdateGroup#setImagePath(String)}
   *   <li>{@link UpdateGroup#setStatus(String)}
   *   <li>{@link UpdateGroup#toString()}
   *   <li>{@link UpdateGroup#getEtag()}
   *   <li>{@link UpdateGroup#getGroupId()}
   *   <li>{@link UpdateGroup#getImagePath()}
   *   <li>{@link UpdateGroup#getStatus()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String UpdateGroup.getEtag()", "String UpdateGroup.getGroupId()",
      "String UpdateGroup.getImagePath()", "String UpdateGroup.getStatus()", "void UpdateGroup.setEtag(String)",
      "void UpdateGroup.setGroupId(String)", "void UpdateGroup.setImagePath(String)",
      "void UpdateGroup.setStatus(String)", "String UpdateGroup.toString()"})
  void testGettersAndSetters() {
    // Arrange
    UpdateGroup updateGroup = new UpdateGroup();

    // Act
    updateGroup.setEtag("Etag");
    updateGroup.setGroupId("42");
    updateGroup.setImagePath("Image Path");
    updateGroup.setStatus("Status");
    String actualToStringResult = updateGroup.toString();
    String actualEtag = updateGroup.getEtag();
    String actualGroupId = updateGroup.getGroupId();
    String actualImagePath = updateGroup.getImagePath();

    // Assert
    assertEquals("42", actualGroupId);
    assertEquals("Etag", actualEtag);
    assertEquals("Image Path", actualImagePath);
    assertEquals("Status", updateGroup.getStatus());
    assertEquals("UpdateGroup(groupId=42, status=Status, etag=Etag, imagePath=Image Path)", actualToStringResult);
  }

  /**
   * Test new {@link UpdateGroup} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link UpdateGroup}
   */
  @Test
  @DisplayName("Test new UpdateGroup (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UpdateGroup.<init>()"})
  void testNewUpdateGroup() {
    // Arrange and Act
    UpdateGroup actualUpdateGroup = new UpdateGroup();

    // Assert
    assertEquals("SDL", actualUpdateGroup.getType());
    assertNull(actualUpdateGroup.getOn());
    assertNull(actualUpdateGroup.getImplicitConnection());
    assertNull(actualUpdateGroup.getInteractionTransfer());
    assertNull(actualUpdateGroup.getOwner());
    assertNull(actualUpdateGroup.getProfile());
    assertNull(actualUpdateGroup.getVisibilityRestriction());
    assertNull(actualUpdateGroup.getElseCondition());
    assertNull(actualUpdateGroup.getId());
    assertNull(actualUpdateGroup.getIfCondition());
    assertNull(actualUpdateGroup.getName());
    assertNull(actualUpdateGroup.getReferrer());
    assertNull(actualUpdateGroup.getSubType());
    assertNull(actualUpdateGroup.getEtag());
    assertNull(actualUpdateGroup.getGroupId());
    assertNull(actualUpdateGroup.getImagePath());
    assertNull(actualUpdateGroup.getStatus());
    assertTrue(actualUpdateGroup.getMembers().isEmpty());
    assertTrue(actualUpdateGroup.getVariableProperties().isEmpty());
  }
}
