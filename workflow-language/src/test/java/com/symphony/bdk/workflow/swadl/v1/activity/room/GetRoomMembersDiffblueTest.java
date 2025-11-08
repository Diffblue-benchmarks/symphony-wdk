package com.symphony.bdk.workflow.swadl.v1.activity.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetRoomMembersDiffblueTest {
  /**
   * Test {@link GetRoomMembers#equals(Object)}, and {@link GetRoomMembers#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetRoomMembers#equals(Object)}
   *   <li>{@link GetRoomMembers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoomMembers.equals(Object)", "int GetRoomMembers.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetRoomMembers getRoomMembers = new GetRoomMembers();
    GetRoomMembers getRoomMembers2 = new GetRoomMembers();

    // Act and Assert
    assertEquals(getRoomMembers, getRoomMembers2);
    int expectedHashCodeResult = getRoomMembers.hashCode();
    assertEquals(expectedHashCodeResult, getRoomMembers2.hashCode());
  }

  /**
   * Test {@link GetRoomMembers#equals(Object)}, and {@link GetRoomMembers#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetRoomMembers#equals(Object)}
   *   <li>{@link GetRoomMembers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoomMembers.equals(Object)", "int GetRoomMembers.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetRoomMembers getRoomMembers = new GetRoomMembers();
    getRoomMembers.setStreamId("42");

    GetRoomMembers getRoomMembers2 = new GetRoomMembers();
    getRoomMembers2.setStreamId("42");

    // Act and Assert
    assertEquals(getRoomMembers, getRoomMembers2);
    int expectedHashCodeResult = getRoomMembers.hashCode();
    assertEquals(expectedHashCodeResult, getRoomMembers2.hashCode());
  }

  /**
   * Test {@link GetRoomMembers#equals(Object)}, and {@link GetRoomMembers#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetRoomMembers#equals(Object)}
   *   <li>{@link GetRoomMembers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoomMembers.equals(Object)", "int GetRoomMembers.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetRoomMembers getRoomMembers = new GetRoomMembers();

    // Act and Assert
    assertEquals(getRoomMembers, getRoomMembers);
    int expectedHashCodeResult = getRoomMembers.hashCode();
    assertEquals(expectedHashCodeResult, getRoomMembers.hashCode());
  }

  /**
   * Test {@link GetRoomMembers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRoomMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoomMembers.equals(Object)", "int GetRoomMembers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetRoomMembers getRoomMembers = new GetRoomMembers();
    getRoomMembers.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getRoomMembers, new GetRoomMembers());
  }

  /**
   * Test {@link GetRoomMembers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRoomMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoomMembers.equals(Object)", "int GetRoomMembers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetRoomMembers getRoomMembers = new GetRoomMembers();
    getRoomMembers.setStreamId("42");

    // Act and Assert
    assertNotEquals(getRoomMembers, new GetRoomMembers());
  }

  /**
   * Test {@link GetRoomMembers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRoomMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoomMembers.equals(Object)", "int GetRoomMembers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetRoomMembers getRoomMembers = new GetRoomMembers();

    GetRoomMembers getRoomMembers2 = new GetRoomMembers();
    getRoomMembers2.setStreamId("42");

    // Act and Assert
    assertNotEquals(getRoomMembers, getRoomMembers2);
  }

  /**
   * Test {@link GetRoomMembers#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRoomMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoomMembers.equals(Object)", "int GetRoomMembers.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetRoomMembers(), null);
  }

  /**
   * Test {@link GetRoomMembers#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRoomMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoomMembers.equals(Object)", "int GetRoomMembers.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetRoomMembers(), "Different type to GetRoomMembers");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetRoomMembers}
   *   <li>{@link GetRoomMembers#setStreamId(String)}
   *   <li>{@link GetRoomMembers#toString()}
   *   <li>{@link GetRoomMembers#getStreamId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetRoomMembers.<init>()", "String GetRoomMembers.getStreamId()",
      "void GetRoomMembers.setStreamId(String)", "String GetRoomMembers.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GetRoomMembers actualGetRoomMembers = new GetRoomMembers();
    actualGetRoomMembers.setStreamId("42");
    String actualToStringResult = actualGetRoomMembers.toString();

    // Assert
    assertEquals("42", actualGetRoomMembers.getStreamId());
    assertEquals("GetRoomMembers(streamId=42)", actualToStringResult);
    assertNull(actualGetRoomMembers.getOn());
    assertNull(actualGetRoomMembers.getElseCondition());
    assertNull(actualGetRoomMembers.getId());
    assertNull(actualGetRoomMembers.getIfCondition());
    assertTrue(actualGetRoomMembers.getVariableProperties().isEmpty());
  }
}
