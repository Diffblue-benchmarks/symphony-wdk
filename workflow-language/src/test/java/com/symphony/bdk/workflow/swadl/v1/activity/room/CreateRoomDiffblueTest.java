package com.symphony.bdk.workflow.swadl.v1.activity.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CreateRoomDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateRoom#equals(Object)}
   *   <li>{@link CreateRoom#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    CreateRoom createRoom2 = new CreateRoom();

    // Act and Assert
    assertEquals(createRoom, createRoom2);
    int expectedHashCodeResult = createRoom.hashCode();
    assertEquals(expectedHashCodeResult, createRoom2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateRoom#equals(Object)}
   *   <li>{@link CreateRoom#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    // Act and Assert
    assertEquals(createRoom, createRoom);
    int expectedHashCodeResult = createRoom.hashCode();
    assertEquals(expectedHashCodeResult, createRoom.hashCode());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.add("Key", "Value");

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.add("Key", mock(AddRoomMember.class));

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setRoomName("Room Name");

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setRoomDescription("Room Description");

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setKeywords(new HashMap<>());

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setSubType("Sub Type");

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setMembersCanInvite(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setDiscoverable(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setReadOnly(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setCopyProtected(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setCrossPod(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setViewHistory(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setMultilateralRoom(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setIsPublic(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual16() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setRoomName("Room Name");

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual17() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setRoomDescription("Room Description");

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual18() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setKeywords(new HashMap<>());

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual19() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setSubType("Sub Type");

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual20() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual21() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setMembersCanInvite(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual22() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setDiscoverable(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual23() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setReadOnly(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual24() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setCopyProtected(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual25() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setCrossPod(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual26() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setViewHistory(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual27() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setMultilateralRoom(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual28() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setIsPublic(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateRoom(), null);
  }

  /**
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateRoom(), "Different type to CreateRoom");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateRoom}
   *   <li>{@link CreateRoom#setCopyProtected(Boolean)}
   *   <li>{@link CreateRoom#setCrossPod(Boolean)}
   *   <li>{@link CreateRoom#setDiscoverable(Boolean)}
   *   <li>{@link CreateRoom#setIsPublic(Boolean)}
   *   <li>{@link CreateRoom#setKeywords(Map)}
   *   <li>{@link CreateRoom#setMembersCanInvite(Boolean)}
   *   <li>{@link CreateRoom#setMultilateralRoom(Boolean)}
   *   <li>{@link CreateRoom#setReadOnly(Boolean)}
   *   <li>{@link CreateRoom#setRoomDescription(String)}
   *   <li>{@link CreateRoom#setRoomName(String)}
   *   <li>{@link CreateRoom#setSubType(String)}
   *   <li>{@link CreateRoom#setUserIds(List)}
   *   <li>{@link CreateRoom#setViewHistory(Boolean)}
   *   <li>{@link CreateRoom#toString()}
   *   <li>{@link CreateRoom#getCopyProtected()}
   *   <li>{@link CreateRoom#getCrossPod()}
   *   <li>{@link CreateRoom#getDiscoverable()}
   *   <li>{@link CreateRoom#getIsPublic()}
   *   <li>{@link CreateRoom#getKeywords()}
   *   <li>{@link CreateRoom#getMembersCanInvite()}
   *   <li>{@link CreateRoom#getMultilateralRoom()}
   *   <li>{@link CreateRoom#getReadOnly()}
   *   <li>{@link CreateRoom#getRoomDescription()}
   *   <li>{@link CreateRoom#getRoomName()}
   *   <li>{@link CreateRoom#getSubType()}
   *   <li>{@link CreateRoom#getUserIds()}
   *   <li>{@link CreateRoom#getUserIdsAsLongs()}
   *   <li>{@link CreateRoom#getViewHistory()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    CreateRoom actualCreateRoom = new CreateRoom();
    actualCreateRoom.setCopyProtected(true);
    actualCreateRoom.setCrossPod(true);
    actualCreateRoom.setDiscoverable(true);
    actualCreateRoom.setIsPublic(true);
    HashMap<String, String> keywords = new HashMap<>();
    actualCreateRoom.setKeywords(keywords);
    actualCreateRoom.setMembersCanInvite(true);
    actualCreateRoom.setMultilateralRoom(true);
    actualCreateRoom.setReadOnly(true);
    actualCreateRoom.setRoomDescription("Room Description");
    actualCreateRoom.setRoomName("Room Name");
    actualCreateRoom.setSubType("Sub Type");
    ArrayList<Long> userIds = new ArrayList<>();
    actualCreateRoom.setUserIds(userIds);
    actualCreateRoom.setViewHistory(true);
    String actualToStringResult = actualCreateRoom.toString();
    Boolean actualCopyProtected = actualCreateRoom.getCopyProtected();
    Boolean actualCrossPod = actualCreateRoom.getCrossPod();
    Boolean actualDiscoverable = actualCreateRoom.getDiscoverable();
    Boolean actualIsPublic = actualCreateRoom.getIsPublic();
    Map<String, String> actualKeywords = actualCreateRoom.getKeywords();
    Boolean actualMembersCanInvite = actualCreateRoom.getMembersCanInvite();
    Boolean actualMultilateralRoom = actualCreateRoom.getMultilateralRoom();
    Boolean actualReadOnly = actualCreateRoom.getReadOnly();
    String actualRoomDescription = actualCreateRoom.getRoomDescription();
    String actualRoomName = actualCreateRoom.getRoomName();
    String actualSubType = actualCreateRoom.getSubType();
    List<Long> actualUserIds = actualCreateRoom.getUserIds();
    List<Long> actualUserIdsAsLongs = actualCreateRoom.getUserIdsAsLongs();

    // Assert that nothing has changed
    assertEquals("CreateRoom(roomName=Room Name, roomDescription=Room Description, keywords={}, subType=Sub Type,"
        + " userIds=[], membersCanInvite=true, discoverable=true, readOnly=true, copyProtected=true, crossPod=true,"
        + " viewHistory=true, multilateralRoom=true, isPublic=true)", actualToStringResult);
    assertEquals("Room Description", actualRoomDescription);
    assertEquals("Room Name", actualRoomName);
    assertEquals("Sub Type", actualSubType);
    assertTrue(actualCopyProtected);
    assertTrue(actualCrossPod);
    assertTrue(actualDiscoverable);
    assertTrue(actualIsPublic);
    assertTrue(actualMembersCanInvite);
    assertTrue(actualMultilateralRoom);
    assertTrue(actualReadOnly);
    assertTrue(actualCreateRoom.getViewHistory());
    assertTrue(actualUserIds.isEmpty());
    assertTrue(actualCreateRoom.getVariableProperties().isEmpty());
    assertTrue(actualKeywords.isEmpty());
    assertSame(userIds, actualUserIds);
    assertSame(userIds, actualUserIdsAsLongs);
    assertSame(keywords, actualKeywords);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateRoom.KeywordItem#equals(Object)}
   *   <li>{@link CreateRoom.KeywordItem#hashCode()}
   * </ul>
   */
  @Test
  void testKeywordItemEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    CreateRoom.KeywordItem keywordItem = new CreateRoom.KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue("42");

    CreateRoom.KeywordItem keywordItem2 = new CreateRoom.KeywordItem();
    keywordItem2.setKey("Key");
    keywordItem2.setValue("42");

    // Act and Assert
    assertEquals(keywordItem, keywordItem2);
    int expectedHashCodeResult = keywordItem.hashCode();
    assertEquals(expectedHashCodeResult, keywordItem2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateRoom.KeywordItem#equals(Object)}
   *   <li>{@link CreateRoom.KeywordItem#hashCode()}
   * </ul>
   */
  @Test
  void testKeywordItemEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    CreateRoom.KeywordItem keywordItem = new CreateRoom.KeywordItem();
    keywordItem.setKey(null);
    keywordItem.setValue("42");

    CreateRoom.KeywordItem keywordItem2 = new CreateRoom.KeywordItem();
    keywordItem2.setKey(null);
    keywordItem2.setValue("42");

    // Act and Assert
    assertEquals(keywordItem, keywordItem2);
    int expectedHashCodeResult = keywordItem.hashCode();
    assertEquals(expectedHashCodeResult, keywordItem2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateRoom.KeywordItem#equals(Object)}
   *   <li>{@link CreateRoom.KeywordItem#hashCode()}
   * </ul>
   */
  @Test
  void testKeywordItemEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    CreateRoom.KeywordItem keywordItem = new CreateRoom.KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue(null);

    CreateRoom.KeywordItem keywordItem2 = new CreateRoom.KeywordItem();
    keywordItem2.setKey("Key");
    keywordItem2.setValue(null);

    // Act and Assert
    assertEquals(keywordItem, keywordItem2);
    int expectedHashCodeResult = keywordItem.hashCode();
    assertEquals(expectedHashCodeResult, keywordItem2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link CreateRoom.KeywordItem#equals(Object)}
   *   <li>{@link CreateRoom.KeywordItem#hashCode()}
   * </ul>
   */
  @Test
  void testKeywordItemEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateRoom.KeywordItem keywordItem = new CreateRoom.KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue("42");

    // Act and Assert
    assertEquals(keywordItem, keywordItem);
    int expectedHashCodeResult = keywordItem.hashCode();
    assertEquals(expectedHashCodeResult, keywordItem.hashCode());
  }

  /**
   * Method under test: {@link CreateRoom.KeywordItem#equals(Object)}
   */
  @Test
  void testKeywordItemEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateRoom.KeywordItem keywordItem = new CreateRoom.KeywordItem();
    keywordItem.setKey("42");
    keywordItem.setValue("42");

    CreateRoom.KeywordItem keywordItem2 = new CreateRoom.KeywordItem();
    keywordItem2.setKey("Key");
    keywordItem2.setValue("42");

    // Act and Assert
    assertNotEquals(keywordItem, keywordItem2);
  }

  /**
   * Method under test: {@link CreateRoom.KeywordItem#equals(Object)}
   */
  @Test
  void testKeywordItemEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateRoom.KeywordItem keywordItem = new CreateRoom.KeywordItem();
    keywordItem.setKey(null);
    keywordItem.setValue("42");

    CreateRoom.KeywordItem keywordItem2 = new CreateRoom.KeywordItem();
    keywordItem2.setKey("Key");
    keywordItem2.setValue("42");

    // Act and Assert
    assertNotEquals(keywordItem, keywordItem2);
  }

  /**
   * Method under test: {@link CreateRoom.KeywordItem#equals(Object)}
   */
  @Test
  void testKeywordItemEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateRoom.KeywordItem keywordItem = new CreateRoom.KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue("Key");

    CreateRoom.KeywordItem keywordItem2 = new CreateRoom.KeywordItem();
    keywordItem2.setKey("Key");
    keywordItem2.setValue("42");

    // Act and Assert
    assertNotEquals(keywordItem, keywordItem2);
  }

  /**
   * Method under test: {@link CreateRoom.KeywordItem#equals(Object)}
   */
  @Test
  void testKeywordItemEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateRoom.KeywordItem keywordItem = new CreateRoom.KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue(null);

    CreateRoom.KeywordItem keywordItem2 = new CreateRoom.KeywordItem();
    keywordItem2.setKey("Key");
    keywordItem2.setValue("42");

    // Act and Assert
    assertNotEquals(keywordItem, keywordItem2);
  }

  /**
   * Method under test: {@link CreateRoom.KeywordItem#equals(Object)}
   */
  @Test
  void testKeywordItemEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    CreateRoom.KeywordItem keywordItem = new CreateRoom.KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue("42");

    // Act and Assert
    assertNotEquals(keywordItem, null);
  }

  /**
   * Method under test: {@link CreateRoom.KeywordItem#equals(Object)}
   */
  @Test
  void testKeywordItemEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    CreateRoom.KeywordItem keywordItem = new CreateRoom.KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue("42");

    // Act and Assert
    assertNotEquals(keywordItem, "Different type to KeywordItem");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CreateRoom.KeywordItem}
   *   <li>{@link CreateRoom.KeywordItem#setKey(String)}
   *   <li>{@link CreateRoom.KeywordItem#setValue(String)}
   *   <li>{@link CreateRoom.KeywordItem#toString()}
   *   <li>{@link CreateRoom.KeywordItem#getKey()}
   *   <li>{@link CreateRoom.KeywordItem#getValue()}
   * </ul>
   */
  @Test
  void testKeywordItemGettersAndSetters() {
    // Arrange and Act
    CreateRoom.KeywordItem actualKeywordItem = new CreateRoom.KeywordItem();
    actualKeywordItem.setKey("Key");
    actualKeywordItem.setValue("42");
    String actualToStringResult = actualKeywordItem.toString();
    String actualKey = actualKeywordItem.getKey();

    // Assert that nothing has changed
    assertEquals("42", actualKeywordItem.getValue());
    assertEquals("CreateRoom.KeywordItem(key=Key, value=42)", actualToStringResult);
    assertEquals("Key", actualKey);
  }
}
