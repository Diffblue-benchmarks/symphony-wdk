package com.symphony.bdk.workflow.swadl.v1.activity.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class UpdateRoomDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateRoom#equals(Object)}
   *   <li>{@link UpdateRoom#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    UpdateRoom updateRoom2 = new UpdateRoom();

    // Act and Assert
    assertEquals(updateRoom, updateRoom2);
    int expectedHashCodeResult = updateRoom.hashCode();
    assertEquals(expectedHashCodeResult, updateRoom2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateRoom#equals(Object)}
   *   <li>{@link UpdateRoom#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    // Act and Assert
    assertEquals(updateRoom, updateRoom);
    int expectedHashCodeResult = updateRoom.hashCode();
    assertEquals(expectedHashCodeResult, updateRoom.hashCode());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.add("Key", "Value");

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.add("Key", mock(AddRoomMember.class));

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setStreamId("42");

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setRoomName("Room Name");

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setRoomDescription("Room Description");

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setKeywords(new HashMap<>());

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setMembersCanInvite(true);

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setDiscoverable(true);

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setIsPublic(true);

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setReadOnly(true);

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setCopyProtected(true);

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setCrossPod(true);

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setViewHistory(true);

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setMultilateralRoom(true);

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();
    updateRoom.setActive(true);

    // Act and Assert
    assertNotEquals(updateRoom, new UpdateRoom());
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual16() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    UpdateRoom updateRoom2 = new UpdateRoom();
    updateRoom2.setStreamId("42");

    // Act and Assert
    assertNotEquals(updateRoom, updateRoom2);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual17() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    UpdateRoom updateRoom2 = new UpdateRoom();
    updateRoom2.setRoomName("Room Name");

    // Act and Assert
    assertNotEquals(updateRoom, updateRoom2);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual18() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    UpdateRoom updateRoom2 = new UpdateRoom();
    updateRoom2.setRoomDescription("Room Description");

    // Act and Assert
    assertNotEquals(updateRoom, updateRoom2);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual19() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    UpdateRoom updateRoom2 = new UpdateRoom();
    updateRoom2.setKeywords(new HashMap<>());

    // Act and Assert
    assertNotEquals(updateRoom, updateRoom2);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual20() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    UpdateRoom updateRoom2 = new UpdateRoom();
    updateRoom2.setMembersCanInvite(true);

    // Act and Assert
    assertNotEquals(updateRoom, updateRoom2);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual21() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    UpdateRoom updateRoom2 = new UpdateRoom();
    updateRoom2.setDiscoverable(true);

    // Act and Assert
    assertNotEquals(updateRoom, updateRoom2);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual22() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    UpdateRoom updateRoom2 = new UpdateRoom();
    updateRoom2.setIsPublic(true);

    // Act and Assert
    assertNotEquals(updateRoom, updateRoom2);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual23() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    UpdateRoom updateRoom2 = new UpdateRoom();
    updateRoom2.setReadOnly(true);

    // Act and Assert
    assertNotEquals(updateRoom, updateRoom2);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual24() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    UpdateRoom updateRoom2 = new UpdateRoom();
    updateRoom2.setCopyProtected(true);

    // Act and Assert
    assertNotEquals(updateRoom, updateRoom2);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual25() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    UpdateRoom updateRoom2 = new UpdateRoom();
    updateRoom2.setCrossPod(true);

    // Act and Assert
    assertNotEquals(updateRoom, updateRoom2);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual26() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    UpdateRoom updateRoom2 = new UpdateRoom();
    updateRoom2.setViewHistory(true);

    // Act and Assert
    assertNotEquals(updateRoom, updateRoom2);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual27() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    UpdateRoom updateRoom2 = new UpdateRoom();
    updateRoom2.setMultilateralRoom(true);

    // Act and Assert
    assertNotEquals(updateRoom, updateRoom2);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual28() {
    // Arrange
    UpdateRoom updateRoom = new UpdateRoom();

    UpdateRoom updateRoom2 = new UpdateRoom();
    updateRoom2.setActive(true);

    // Act and Assert
    assertNotEquals(updateRoom, updateRoom2);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateRoom(), null);
  }

  /**
   * Method under test: {@link UpdateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateRoom(), "Different type to UpdateRoom");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link UpdateRoom}
   *   <li>{@link UpdateRoom#setActive(Boolean)}
   *   <li>{@link UpdateRoom#setCopyProtected(Boolean)}
   *   <li>{@link UpdateRoom#setCrossPod(Boolean)}
   *   <li>{@link UpdateRoom#setDiscoverable(Boolean)}
   *   <li>{@link UpdateRoom#setIsPublic(Boolean)}
   *   <li>{@link UpdateRoom#setKeywords(Map)}
   *   <li>{@link UpdateRoom#setMembersCanInvite(Boolean)}
   *   <li>{@link UpdateRoom#setMultilateralRoom(Boolean)}
   *   <li>{@link UpdateRoom#setReadOnly(Boolean)}
   *   <li>{@link UpdateRoom#setRoomDescription(String)}
   *   <li>{@link UpdateRoom#setRoomName(String)}
   *   <li>{@link UpdateRoom#setStreamId(String)}
   *   <li>{@link UpdateRoom#setViewHistory(Boolean)}
   *   <li>{@link UpdateRoom#toString()}
   *   <li>{@link UpdateRoom#getActive()}
   *   <li>{@link UpdateRoom#getCopyProtected()}
   *   <li>{@link UpdateRoom#getCrossPod()}
   *   <li>{@link UpdateRoom#getDiscoverable()}
   *   <li>{@link UpdateRoom#getIsPublic()}
   *   <li>{@link UpdateRoom#getKeywords()}
   *   <li>{@link UpdateRoom#getMembersCanInvite()}
   *   <li>{@link UpdateRoom#getMultilateralRoom()}
   *   <li>{@link UpdateRoom#getReadOnly()}
   *   <li>{@link UpdateRoom#getRoomDescription()}
   *   <li>{@link UpdateRoom#getRoomName()}
   *   <li>{@link UpdateRoom#getStreamId()}
   *   <li>{@link UpdateRoom#getViewHistory()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    UpdateRoom actualUpdateRoom = new UpdateRoom();
    actualUpdateRoom.setActive(true);
    actualUpdateRoom.setCopyProtected(true);
    actualUpdateRoom.setCrossPod(true);
    actualUpdateRoom.setDiscoverable(true);
    actualUpdateRoom.setIsPublic(true);
    HashMap<String, String> keywords = new HashMap<>();
    actualUpdateRoom.setKeywords(keywords);
    actualUpdateRoom.setMembersCanInvite(true);
    actualUpdateRoom.setMultilateralRoom(true);
    actualUpdateRoom.setReadOnly(true);
    actualUpdateRoom.setRoomDescription("Room Description");
    actualUpdateRoom.setRoomName("Room Name");
    actualUpdateRoom.setStreamId("42");
    actualUpdateRoom.setViewHistory(true);
    String actualToStringResult = actualUpdateRoom.toString();
    Boolean actualActive = actualUpdateRoom.getActive();
    Boolean actualCopyProtected = actualUpdateRoom.getCopyProtected();
    Boolean actualCrossPod = actualUpdateRoom.getCrossPod();
    Boolean actualDiscoverable = actualUpdateRoom.getDiscoverable();
    Boolean actualIsPublic = actualUpdateRoom.getIsPublic();
    Map<String, String> actualKeywords = actualUpdateRoom.getKeywords();
    Boolean actualMembersCanInvite = actualUpdateRoom.getMembersCanInvite();
    Boolean actualMultilateralRoom = actualUpdateRoom.getMultilateralRoom();
    Boolean actualReadOnly = actualUpdateRoom.getReadOnly();
    String actualRoomDescription = actualUpdateRoom.getRoomDescription();
    String actualRoomName = actualUpdateRoom.getRoomName();
    String actualStreamId = actualUpdateRoom.getStreamId();

    // Assert that nothing has changed
    assertEquals("42", actualStreamId);
    assertEquals("Room Description", actualRoomDescription);
    assertEquals("Room Name", actualRoomName);
    assertEquals(
        "UpdateRoom(streamId=42, roomName=Room Name, roomDescription=Room Description, keywords={}, membersCanInvite"
            + "=true, discoverable=true, isPublic=true, readOnly=true, copyProtected=true, crossPod=true, viewHistory=true,"
            + " multilateralRoom=true, active=true)",
        actualToStringResult);
    assertTrue(actualActive);
    assertTrue(actualCopyProtected);
    assertTrue(actualCrossPod);
    assertTrue(actualDiscoverable);
    assertTrue(actualIsPublic);
    assertTrue(actualMembersCanInvite);
    assertTrue(actualMultilateralRoom);
    assertTrue(actualReadOnly);
    assertTrue(actualUpdateRoom.getViewHistory());
    assertTrue(actualUpdateRoom.getVariableProperties().isEmpty());
    assertTrue(actualKeywords.isEmpty());
    assertSame(keywords, actualKeywords);
  }
}
