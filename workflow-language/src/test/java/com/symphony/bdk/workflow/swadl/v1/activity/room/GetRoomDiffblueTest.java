package com.symphony.bdk.workflow.swadl.v1.activity.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetRoomDiffblueTest {
  /**
   * Test {@link GetRoom#equals(Object)}, and {@link GetRoom#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetRoom#equals(Object)}
   *   <li>{@link GetRoom#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoom.equals(Object)", "int GetRoom.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetRoom getRoom = new GetRoom();
    GetRoom getRoom2 = new GetRoom();

    // Act and Assert
    assertEquals(getRoom, getRoom2);
    int expectedHashCodeResult = getRoom.hashCode();
    assertEquals(expectedHashCodeResult, getRoom2.hashCode());
  }

  /**
   * Test {@link GetRoom#equals(Object)}, and {@link GetRoom#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetRoom#equals(Object)}
   *   <li>{@link GetRoom#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoom.equals(Object)", "int GetRoom.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetRoom getRoom = new GetRoom();
    getRoom.setStreamId("42");

    GetRoom getRoom2 = new GetRoom();
    getRoom2.setStreamId("42");

    // Act and Assert
    assertEquals(getRoom, getRoom2);
    int expectedHashCodeResult = getRoom.hashCode();
    assertEquals(expectedHashCodeResult, getRoom2.hashCode());
  }

  /**
   * Test {@link GetRoom#equals(Object)}, and {@link GetRoom#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetRoom#equals(Object)}
   *   <li>{@link GetRoom#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoom.equals(Object)", "int GetRoom.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetRoom getRoom = new GetRoom();

    // Act and Assert
    assertEquals(getRoom, getRoom);
    int expectedHashCodeResult = getRoom.hashCode();
    assertEquals(expectedHashCodeResult, getRoom.hashCode());
  }

  /**
   * Test {@link GetRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoom.equals(Object)", "int GetRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetRoom getRoom = new GetRoom();
    getRoom.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getRoom, new GetRoom());
  }

  /**
   * Test {@link GetRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoom.equals(Object)", "int GetRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetRoom getRoom = new GetRoom();
    getRoom.setStreamId("42");

    // Act and Assert
    assertNotEquals(getRoom, new GetRoom());
  }

  /**
   * Test {@link GetRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoom.equals(Object)", "int GetRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetRoom getRoom = new GetRoom();

    GetRoom getRoom2 = new GetRoom();
    getRoom2.setStreamId("42");

    // Act and Assert
    assertNotEquals(getRoom, getRoom2);
  }

  /**
   * Test {@link GetRoom#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoom.equals(Object)", "int GetRoom.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetRoom(), null);
  }

  /**
   * Test {@link GetRoom#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRoom.equals(Object)", "int GetRoom.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetRoom(), "Different type to GetRoom");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetRoom}
   *   <li>{@link GetRoom#setStreamId(String)}
   *   <li>{@link GetRoom#toString()}
   *   <li>{@link GetRoom#getStreamId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetRoom.<init>()", "String GetRoom.getStreamId()", "void GetRoom.setStreamId(String)",
      "String GetRoom.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GetRoom actualGetRoom = new GetRoom();
    actualGetRoom.setStreamId("42");
    String actualToStringResult = actualGetRoom.toString();

    // Assert
    assertEquals("42", actualGetRoom.getStreamId());
    assertEquals("GetRoom(streamId=42)", actualToStringResult);
    assertNull(actualGetRoom.getOn());
    assertNull(actualGetRoom.getObo());
    assertNull(actualGetRoom.getElseCondition());
    assertNull(actualGetRoom.getId());
    assertNull(actualGetRoom.getIfCondition());
    assertTrue(actualGetRoom.getVariableProperties().isEmpty());
  }
}
