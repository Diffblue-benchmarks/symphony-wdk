package com.symphony.bdk.workflow.swadl.v1.activity.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class GetRoomDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetRoom#equals(Object)}
   *   <li>{@link GetRoom#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link GetRoom#equals(Object)}
   *   <li>{@link GetRoom#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link GetRoom#equals(Object)}
   *   <li>{@link GetRoom#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetRoom getRoom = new GetRoom();

    // Act and Assert
    assertEquals(getRoom, getRoom);
    int expectedHashCodeResult = getRoom.hashCode();
    assertEquals(expectedHashCodeResult, getRoom.hashCode());
  }

  /**
   * Method under test: {@link GetRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetRoom getRoom = new GetRoom();
    getRoom.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getRoom, new GetRoom());
  }

  /**
   * Method under test: {@link GetRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetRoom getRoom = new GetRoom();
    getRoom.add("Key", mock(AddRoomMember.class));

    // Act and Assert
    assertNotEquals(getRoom, new GetRoom());
  }

  /**
   * Method under test: {@link GetRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetRoom getRoom = new GetRoom();
    getRoom.setStreamId("42");

    // Act and Assert
    assertNotEquals(getRoom, new GetRoom());
  }

  /**
   * Method under test: {@link GetRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetRoom getRoom = new GetRoom();

    GetRoom getRoom2 = new GetRoom();
    getRoom2.setStreamId("42");

    // Act and Assert
    assertNotEquals(getRoom, getRoom2);
  }

  /**
   * Method under test: {@link GetRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetRoom(), null);
  }

  /**
   * Method under test: {@link GetRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetRoom(), "Different type to GetRoom");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetRoom}
   *   <li>{@link GetRoom#setStreamId(String)}
   *   <li>{@link GetRoom#toString()}
   *   <li>{@link GetRoom#getStreamId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetRoom actualGetRoom = new GetRoom();
    actualGetRoom.setStreamId("42");
    String actualToStringResult = actualGetRoom.toString();

    // Assert that nothing has changed
    assertEquals("42", actualGetRoom.getStreamId());
    assertEquals("GetRoom(streamId=42)", actualToStringResult);
    assertTrue(actualGetRoom.getVariableProperties().isEmpty());
  }
}
