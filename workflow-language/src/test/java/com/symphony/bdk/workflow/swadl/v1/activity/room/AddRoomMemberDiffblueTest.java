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

class AddRoomMemberDiffblueTest {
  /**
   * Test {@link AddRoomMember#equals(Object)}, and {@link AddRoomMember#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddRoomMember#equals(Object)}
   *   <li>{@link AddRoomMember#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddRoomMember.equals(Object)", "int AddRoomMember.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();
    AddRoomMember addRoomMember2 = new AddRoomMember();

    // Act and Assert
    assertEquals(addRoomMember, addRoomMember2);
    int expectedHashCodeResult = addRoomMember.hashCode();
    assertEquals(expectedHashCodeResult, addRoomMember2.hashCode());
  }

  /**
   * Test {@link AddRoomMember#equals(Object)}, and {@link AddRoomMember#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddRoomMember#equals(Object)}
   *   <li>{@link AddRoomMember#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddRoomMember.equals(Object)", "int AddRoomMember.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();
    addRoomMember.setStreamId("42");

    AddRoomMember addRoomMember2 = new AddRoomMember();
    addRoomMember2.setStreamId("42");

    // Act and Assert
    assertEquals(addRoomMember, addRoomMember2);
    int expectedHashCodeResult = addRoomMember.hashCode();
    assertEquals(expectedHashCodeResult, addRoomMember2.hashCode());
  }

  /**
   * Test {@link AddRoomMember#equals(Object)}, and {@link AddRoomMember#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddRoomMember#equals(Object)}
   *   <li>{@link AddRoomMember#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddRoomMember.equals(Object)", "int AddRoomMember.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();

    // Act and Assert
    assertEquals(addRoomMember, addRoomMember);
    int expectedHashCodeResult = addRoomMember.hashCode();
    assertEquals(expectedHashCodeResult, addRoomMember.hashCode());
  }

  /**
   * Test {@link AddRoomMember#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddRoomMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddRoomMember.equals(Object)", "int AddRoomMember.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();
    addRoomMember.add("Key", "Value");

    // Act and Assert
    assertNotEquals(addRoomMember, new AddRoomMember());
  }

  /**
   * Test {@link AddRoomMember#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddRoomMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddRoomMember.equals(Object)", "int AddRoomMember.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();
    addRoomMember.setStreamId("42");

    // Act and Assert
    assertNotEquals(addRoomMember, new AddRoomMember());
  }

  /**
   * Test {@link AddRoomMember#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddRoomMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddRoomMember.equals(Object)", "int AddRoomMember.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();

    AddRoomMember addRoomMember2 = new AddRoomMember();
    addRoomMember2.setStreamId("42");

    // Act and Assert
    assertNotEquals(addRoomMember, addRoomMember2);
  }

  /**
   * Test {@link AddRoomMember#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddRoomMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddRoomMember.equals(Object)", "int AddRoomMember.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AddRoomMember(), null);
  }

  /**
   * Test {@link AddRoomMember#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link AddRoomMember#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean AddRoomMember.equals(Object)", "int AddRoomMember.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new AddRoomMember(), "Different type to AddRoomMember");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link AddRoomMember#setStreamId(String)}
   *   <li>{@link AddRoomMember#setUserIds(List)}
   *   <li>{@link AddRoomMember#toString()}
   *   <li>{@link AddRoomMember#getStreamId()}
   *   <li>{@link AddRoomMember#getUserIds()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String AddRoomMember.getStreamId()", "List AddRoomMember.getUserIds()",
      "void AddRoomMember.setStreamId(String)", "void AddRoomMember.setUserIds(List)",
      "String AddRoomMember.toString()"})
  void testGettersAndSetters() {
    // Arrange
    AddRoomMember addRoomMember = new AddRoomMember();

    // Act
    addRoomMember.setStreamId("42");
    ArrayList<Long> userIds = new ArrayList<>();
    addRoomMember.setUserIds(userIds);
    String actualToStringResult = addRoomMember.toString();
    String actualStreamId = addRoomMember.getStreamId();
    List<Long> actualUserIds = addRoomMember.getUserIds();

    // Assert
    assertEquals("42", actualStreamId);
    assertEquals("AddRoomMember(streamId=42, userIds=[])", actualToStringResult);
    assertTrue(actualUserIds.isEmpty());
    assertSame(userIds, actualUserIds);
  }

  /**
   * Test new {@link AddRoomMember} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link AddRoomMember}
   */
  @Test
  @DisplayName("Test new AddRoomMember (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AddRoomMember.<init>()"})
  void testNewAddRoomMember() {
    // Arrange and Act
    AddRoomMember actualAddRoomMember = new AddRoomMember();

    // Assert
    assertNull(actualAddRoomMember.getOn());
    assertNull(actualAddRoomMember.getObo());
    assertNull(actualAddRoomMember.getElseCondition());
    assertNull(actualAddRoomMember.getId());
    assertNull(actualAddRoomMember.getIfCondition());
    RelationalEvents events = actualAddRoomMember.getEvents();
    assertNull(events.getParentId());
    assertNull(actualAddRoomMember.getStreamId());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualAddRoomMember.getUserIds().isEmpty());
    assertTrue(actualAddRoomMember.getVariableProperties().isEmpty());
  }
}
