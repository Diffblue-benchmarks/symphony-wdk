package com.symphony.bdk.workflow.swadl.v1.activity.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class GetRoomMembersDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetRoomMembers#equals(Object)}
   *   <li>{@link GetRoomMembers#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link GetRoomMembers#equals(Object)}
   *   <li>{@link GetRoomMembers#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link GetRoomMembers#equals(Object)}
   *   <li>{@link GetRoomMembers#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetRoomMembers getRoomMembers = new GetRoomMembers();

    // Act and Assert
    assertEquals(getRoomMembers, getRoomMembers);
    int expectedHashCodeResult = getRoomMembers.hashCode();
    assertEquals(expectedHashCodeResult, getRoomMembers.hashCode());
  }

  /**
   * Method under test: {@link GetRoomMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetRoomMembers getRoomMembers = new GetRoomMembers();
    getRoomMembers.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getRoomMembers, new GetRoomMembers());
  }

  /**
   * Method under test: {@link GetRoomMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetRoomMembers getRoomMembers = new GetRoomMembers();
    getRoomMembers.add("Key", mock(AddRoomMember.class));

    // Act and Assert
    assertNotEquals(getRoomMembers, new GetRoomMembers());
  }

  /**
   * Method under test: {@link GetRoomMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetRoomMembers getRoomMembers = new GetRoomMembers();
    getRoomMembers.setStreamId("42");

    // Act and Assert
    assertNotEquals(getRoomMembers, new GetRoomMembers());
  }

  /**
   * Method under test: {@link GetRoomMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetRoomMembers getRoomMembers = new GetRoomMembers();

    GetRoomMembers getRoomMembers2 = new GetRoomMembers();
    getRoomMembers2.setStreamId("42");

    // Act and Assert
    assertNotEquals(getRoomMembers, getRoomMembers2);
  }

  /**
   * Method under test: {@link GetRoomMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetRoomMembers(), null);
  }

  /**
   * Method under test: {@link GetRoomMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetRoomMembers(), "Different type to GetRoomMembers");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetRoomMembers}
   *   <li>{@link GetRoomMembers#setStreamId(String)}
   *   <li>{@link GetRoomMembers#toString()}
   *   <li>{@link GetRoomMembers#getStreamId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetRoomMembers actualGetRoomMembers = new GetRoomMembers();
    actualGetRoomMembers.setStreamId("42");
    String actualToStringResult = actualGetRoomMembers.toString();

    // Assert that nothing has changed
    assertEquals("42", actualGetRoomMembers.getStreamId());
    assertEquals("GetRoomMembers(streamId=42)", actualToStringResult);
    assertTrue(actualGetRoomMembers.getVariableProperties().isEmpty());
  }
}
