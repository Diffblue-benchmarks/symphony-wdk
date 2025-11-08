package com.symphony.bdk.workflow.swadl.v1.activity.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RemoveRoomMemberDiffblueTest {
  /**
   * Test {@link RemoveRoomMember#equals(Object)}, and {@link RemoveRoomMember#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveRoomMember#equals(Object)}
   *   <li>{@link RemoveRoomMember#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveRoomMember.equals(Object)", "int RemoveRoomMember.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();
    RemoveRoomMember removeRoomMember2 = new RemoveRoomMember();

    // Act and Assert
    assertEquals(removeRoomMember, removeRoomMember2);
    int expectedHashCodeResult = removeRoomMember.hashCode();
    assertEquals(expectedHashCodeResult, removeRoomMember2.hashCode());
  }

  /**
   * Test {@link RemoveRoomMember#equals(Object)}, and {@link RemoveRoomMember#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveRoomMember#equals(Object)}
   *   <li>{@link RemoveRoomMember#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveRoomMember.equals(Object)", "int RemoveRoomMember.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();
    removeRoomMember.setStreamId("42");

    RemoveRoomMember removeRoomMember2 = new RemoveRoomMember();
    removeRoomMember2.setStreamId("42");

    // Act and Assert
    assertEquals(removeRoomMember, removeRoomMember2);
    int expectedHashCodeResult = removeRoomMember.hashCode();
    assertEquals(expectedHashCodeResult, removeRoomMember2.hashCode());
  }

  /**
   * Test {@link RemoveRoomMember#equals(Object)}, and {@link RemoveRoomMember#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveRoomMember#equals(Object)}
   *   <li>{@link RemoveRoomMember#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveRoomMember.equals(Object)", "int RemoveRoomMember.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();

    // Act and Assert
    assertEquals(removeRoomMember, removeRoomMember);
    int expectedHashCodeResult = removeRoomMember.hashCode();
    assertEquals(expectedHashCodeResult, removeRoomMember.hashCode());
  }

  /**
   * Test {@link RemoveRoomMember#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RemoveRoomMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveRoomMember.equals(Object)", "int RemoveRoomMember.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();
    removeRoomMember.add("Key", "Value");

    // Act and Assert
    assertNotEquals(removeRoomMember, new RemoveRoomMember());
  }

  /**
   * Test {@link RemoveRoomMember#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RemoveRoomMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveRoomMember.equals(Object)", "int RemoveRoomMember.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();
    removeRoomMember.setStreamId("42");

    // Act and Assert
    assertNotEquals(removeRoomMember, new RemoveRoomMember());
  }

  /**
   * Test {@link RemoveRoomMember#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RemoveRoomMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveRoomMember.equals(Object)", "int RemoveRoomMember.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();

    RemoveRoomMember removeRoomMember2 = new RemoveRoomMember();
    removeRoomMember2.setStreamId("42");

    // Act and Assert
    assertNotEquals(removeRoomMember, removeRoomMember2);
  }

  /**
   * Test {@link RemoveRoomMember#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RemoveRoomMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveRoomMember.equals(Object)", "int RemoveRoomMember.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RemoveRoomMember(), null);
  }

  /**
   * Test {@link RemoveRoomMember#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link RemoveRoomMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean RemoveRoomMember.equals(Object)", "int RemoveRoomMember.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new RemoveRoomMember(), "Different type to RemoveRoomMember");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link RemoveRoomMember#setStreamId(String)}
   *   <li>{@link RemoveRoomMember#setUserIds(List)}
   *   <li>{@link RemoveRoomMember#toString()}
   *   <li>{@link RemoveRoomMember#getStreamId()}
   *   <li>{@link RemoveRoomMember#getUserIds()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String RemoveRoomMember.getStreamId()", "List RemoveRoomMember.getUserIds()",
      "void RemoveRoomMember.setStreamId(String)", "void RemoveRoomMember.setUserIds(List)",
      "String RemoveRoomMember.toString()"})
  void testGettersAndSetters() {
    // Arrange
    RemoveRoomMember removeRoomMember = new RemoveRoomMember();

    // Act
    removeRoomMember.setStreamId("42");
    ArrayList<Long> userIds = new ArrayList<>();
    removeRoomMember.setUserIds(userIds);
    String actualToStringResult = removeRoomMember.toString();
    String actualStreamId = removeRoomMember.getStreamId();
    List<Long> actualUserIds = removeRoomMember.getUserIds();

    // Assert
    assertEquals("42", actualStreamId);
    assertEquals("RemoveRoomMember(streamId=42, userIds=[])", actualToStringResult);
    assertTrue(actualUserIds.isEmpty());
    assertSame(userIds, actualUserIds);
  }

  /**
   * Test new {@link RemoveRoomMember} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link RemoveRoomMember}
   */
  @Test
  @DisplayName("Test new RemoveRoomMember (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RemoveRoomMember.<init>()"})
  void testNewRemoveRoomMember() {
    // Arrange and Act
    RemoveRoomMember actualRemoveRoomMember = new RemoveRoomMember();

    // Assert
    assertNull(actualRemoveRoomMember.getOn());
    assertNull(actualRemoveRoomMember.getObo());
    assertNull(actualRemoveRoomMember.getElseCondition());
    assertNull(actualRemoveRoomMember.getId());
    assertNull(actualRemoveRoomMember.getIfCondition());
    RelationalEvents events = actualRemoveRoomMember.getEvents();
    assertNull(events.getParentId());
    assertNull(actualRemoveRoomMember.getStreamId());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualRemoveRoomMember.getUserIds().isEmpty());
    assertTrue(actualRemoveRoomMember.getVariableProperties().isEmpty());
  }
}
