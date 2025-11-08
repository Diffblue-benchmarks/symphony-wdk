package com.symphony.bdk.workflow.swadl.v1.activity.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.activity.room.CreateRoom.KeywordItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CreateRoomDiffblueTest {
  /**
   * Test {@link CreateRoom#equals(Object)}, and {@link CreateRoom#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateRoom#equals(Object)}
   *   <li>{@link CreateRoom#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
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
   * Test {@link CreateRoom#equals(Object)}, and {@link CreateRoom#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CreateRoom#equals(Object)}
   *   <li>{@link CreateRoom#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    // Act and Assert
    assertEquals(createRoom, createRoom);
    int expectedHashCodeResult = createRoom.hashCode();
    assertEquals(expectedHashCodeResult, createRoom.hashCode());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.add("Key", "Value");

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setRoomName("Room Name");

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setRoomDescription("Room Description");

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setKeywords(new HashMap<>());

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setSubType("Sub Type");

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setMembersCanInvite(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setDiscoverable(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setReadOnly(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setCopyProtected(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setCrossPod(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setViewHistory(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setMultilateralRoom(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();
    createRoom.setIsPublic(true);

    // Act and Assert
    assertNotEquals(createRoom, new CreateRoom());
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setRoomName("Room Name");

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual16() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setRoomDescription("Room Description");

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual17() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setKeywords(new HashMap<>());

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual18() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setSubType("Sub Type");

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual19() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual20() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setMembersCanInvite(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual21() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setDiscoverable(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual22() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setReadOnly(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual23() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setCopyProtected(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual24() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setCrossPod(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual25() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setViewHistory(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual26() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setMultilateralRoom(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual27() {
    // Arrange
    CreateRoom createRoom = new CreateRoom();

    CreateRoom createRoom2 = new CreateRoom();
    createRoom2.setIsPublic(true);

    // Act and Assert
    assertNotEquals(createRoom, createRoom2);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateRoom(), null);
  }

  /**
   * Test {@link CreateRoom#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CreateRoom#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean CreateRoom.equals(Object)", "int CreateRoom.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new CreateRoom(), "Different type to CreateRoom");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CreateRoom.<init>()", "Boolean CreateRoom.getCopyProtected()",
      "Boolean CreateRoom.getCrossPod()", "Boolean CreateRoom.getDiscoverable()", "Boolean CreateRoom.getIsPublic()",
      "Map CreateRoom.getKeywords()", "Boolean CreateRoom.getMembersCanInvite()",
      "Boolean CreateRoom.getMultilateralRoom()", "Boolean CreateRoom.getReadOnly()",
      "String CreateRoom.getRoomDescription()", "String CreateRoom.getRoomName()", "String CreateRoom.getSubType()",
      "List CreateRoom.getUserIds()", "List CreateRoom.getUserIdsAsLongs()", "Boolean CreateRoom.getViewHistory()",
      "void CreateRoom.setCopyProtected(Boolean)", "void CreateRoom.setCrossPod(Boolean)",
      "void CreateRoom.setDiscoverable(Boolean)", "void CreateRoom.setIsPublic(Boolean)",
      "void CreateRoom.setKeywords(Map)", "void CreateRoom.setMembersCanInvite(Boolean)",
      "void CreateRoom.setMultilateralRoom(Boolean)", "void CreateRoom.setReadOnly(Boolean)",
      "void CreateRoom.setRoomDescription(String)", "void CreateRoom.setRoomName(String)",
      "void CreateRoom.setSubType(String)", "void CreateRoom.setUserIds(List)",
      "void CreateRoom.setViewHistory(Boolean)", "String CreateRoom.toString()"})
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
    Boolean actualViewHistory = actualCreateRoom.getViewHistory();

    // Assert
    assertEquals("CreateRoom(roomName=Room Name, roomDescription=Room Description, keywords={}, subType=Sub Type,"
        + " userIds=[], membersCanInvite=true, discoverable=true, readOnly=true, copyProtected=true, crossPod=true,"
        + " viewHistory=true, multilateralRoom=true, isPublic=true)", actualToStringResult);
    assertEquals("Room Description", actualRoomDescription);
    assertEquals("Room Name", actualRoomName);
    assertEquals("Sub Type", actualSubType);
    assertNull(actualCreateRoom.getOn());
    assertNull(actualCreateRoom.getObo());
    assertNull(actualCreateRoom.getElseCondition());
    assertNull(actualCreateRoom.getId());
    assertNull(actualCreateRoom.getIfCondition());
    assertTrue(actualCopyProtected);
    assertTrue(actualCrossPod);
    assertTrue(actualDiscoverable);
    assertTrue(actualIsPublic);
    assertTrue(actualMembersCanInvite);
    assertTrue(actualMultilateralRoom);
    assertTrue(actualReadOnly);
    assertTrue(actualViewHistory);
    assertTrue(actualUserIds.isEmpty());
    assertTrue(actualCreateRoom.getVariableProperties().isEmpty());
    assertTrue(actualKeywords.isEmpty());
    assertSame(userIds, actualUserIds);
    assertSame(userIds, actualUserIdsAsLongs);
    assertSame(keywords, actualKeywords);
  }

  /**
   * Test KeywordItem {@link KeywordItem#equals(Object)}, and {@link KeywordItem#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link KeywordItem#equals(Object)}
   *   <li>{@link KeywordItem#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test KeywordItem equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean KeywordItem.equals(Object)", "int KeywordItem.hashCode()"})
  void testKeywordItemEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    KeywordItem keywordItem = new KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue("42");

    KeywordItem keywordItem2 = new KeywordItem();
    keywordItem2.setKey("Key");
    keywordItem2.setValue("42");

    // Act and Assert
    assertEquals(keywordItem, keywordItem2);
    int expectedHashCodeResult = keywordItem.hashCode();
    assertEquals(expectedHashCodeResult, keywordItem2.hashCode());
  }

  /**
   * Test KeywordItem {@link KeywordItem#equals(Object)}, and {@link KeywordItem#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link KeywordItem#equals(Object)}
   *   <li>{@link KeywordItem#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test KeywordItem equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean KeywordItem.equals(Object)", "int KeywordItem.hashCode()"})
  void testKeywordItemEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    KeywordItem keywordItem = new KeywordItem();
    keywordItem.setKey(null);
    keywordItem.setValue("42");

    KeywordItem keywordItem2 = new KeywordItem();
    keywordItem2.setKey(null);
    keywordItem2.setValue("42");

    // Act and Assert
    assertEquals(keywordItem, keywordItem2);
    int expectedHashCodeResult = keywordItem.hashCode();
    assertEquals(expectedHashCodeResult, keywordItem2.hashCode());
  }

  /**
   * Test KeywordItem {@link KeywordItem#equals(Object)}, and {@link KeywordItem#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link KeywordItem#equals(Object)}
   *   <li>{@link KeywordItem#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test KeywordItem equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean KeywordItem.equals(Object)", "int KeywordItem.hashCode()"})
  void testKeywordItemEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    KeywordItem keywordItem = new KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue(null);

    KeywordItem keywordItem2 = new KeywordItem();
    keywordItem2.setKey("Key");
    keywordItem2.setValue(null);

    // Act and Assert
    assertEquals(keywordItem, keywordItem2);
    int expectedHashCodeResult = keywordItem.hashCode();
    assertEquals(expectedHashCodeResult, keywordItem2.hashCode());
  }

  /**
   * Test KeywordItem {@link KeywordItem#equals(Object)}, and {@link KeywordItem#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link KeywordItem#equals(Object)}
   *   <li>{@link KeywordItem#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test KeywordItem equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean KeywordItem.equals(Object)", "int KeywordItem.hashCode()"})
  void testKeywordItemEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    KeywordItem keywordItem = new KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue("42");

    // Act and Assert
    assertEquals(keywordItem, keywordItem);
    int expectedHashCodeResult = keywordItem.hashCode();
    assertEquals(expectedHashCodeResult, keywordItem.hashCode());
  }

  /**
   * Test KeywordItem {@link KeywordItem#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link KeywordItem#equals(Object)}
   */
  @Test
  @DisplayName("Test KeywordItem equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean KeywordItem.equals(Object)", "int KeywordItem.hashCode()"})
  void testKeywordItemEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    KeywordItem keywordItem = new KeywordItem();
    keywordItem.setKey("42");
    keywordItem.setValue("42");

    KeywordItem keywordItem2 = new KeywordItem();
    keywordItem2.setKey("Key");
    keywordItem2.setValue("42");

    // Act and Assert
    assertNotEquals(keywordItem, keywordItem2);
  }

  /**
   * Test KeywordItem {@link KeywordItem#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link KeywordItem#equals(Object)}
   */
  @Test
  @DisplayName("Test KeywordItem equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean KeywordItem.equals(Object)", "int KeywordItem.hashCode()"})
  void testKeywordItemEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    KeywordItem keywordItem = new KeywordItem();
    keywordItem.setKey(null);
    keywordItem.setValue("42");

    KeywordItem keywordItem2 = new KeywordItem();
    keywordItem2.setKey("Key");
    keywordItem2.setValue("42");

    // Act and Assert
    assertNotEquals(keywordItem, keywordItem2);
  }

  /**
   * Test KeywordItem {@link KeywordItem#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link KeywordItem#equals(Object)}
   */
  @Test
  @DisplayName("Test KeywordItem equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean KeywordItem.equals(Object)", "int KeywordItem.hashCode()"})
  void testKeywordItemEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    KeywordItem keywordItem = new KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue("Key");

    KeywordItem keywordItem2 = new KeywordItem();
    keywordItem2.setKey("Key");
    keywordItem2.setValue("42");

    // Act and Assert
    assertNotEquals(keywordItem, keywordItem2);
  }

  /**
   * Test KeywordItem {@link KeywordItem#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link KeywordItem#equals(Object)}
   */
  @Test
  @DisplayName("Test KeywordItem equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean KeywordItem.equals(Object)", "int KeywordItem.hashCode()"})
  void testKeywordItemEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    KeywordItem keywordItem = new KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue(null);

    KeywordItem keywordItem2 = new KeywordItem();
    keywordItem2.setKey("Key");
    keywordItem2.setValue("42");

    // Act and Assert
    assertNotEquals(keywordItem, keywordItem2);
  }

  /**
   * Test KeywordItem {@link KeywordItem#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link KeywordItem#equals(Object)}
   */
  @Test
  @DisplayName("Test KeywordItem equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean KeywordItem.equals(Object)", "int KeywordItem.hashCode()"})
  void testKeywordItemEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    KeywordItem keywordItem = new KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue("42");

    // Act and Assert
    assertNotEquals(keywordItem, null);
  }

  /**
   * Test KeywordItem {@link KeywordItem#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link KeywordItem#equals(Object)}
   */
  @Test
  @DisplayName("Test KeywordItem equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean KeywordItem.equals(Object)", "int KeywordItem.hashCode()"})
  void testKeywordItemEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    KeywordItem keywordItem = new KeywordItem();
    keywordItem.setKey("Key");
    keywordItem.setValue("42");

    // Act and Assert
    assertNotEquals(keywordItem, "Different type to KeywordItem");
  }

  /**
   * Test KeywordItem getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link KeywordItem}
   *   <li>{@link KeywordItem#setKey(String)}
   *   <li>{@link KeywordItem#setValue(String)}
   *   <li>{@link KeywordItem#toString()}
   *   <li>{@link KeywordItem#getKey()}
   *   <li>{@link KeywordItem#getValue()}
   * </ul>
   */
  @Test
  @DisplayName("Test KeywordItem getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void KeywordItem.<init>()", "String KeywordItem.getKey()", "String KeywordItem.getValue()",
      "void KeywordItem.setKey(String)", "void KeywordItem.setValue(String)", "String KeywordItem.toString()"})
  void testKeywordItemGettersAndSetters() {
    // Arrange and Act
    KeywordItem actualKeywordItem = new KeywordItem();
    actualKeywordItem.setKey("Key");
    actualKeywordItem.setValue("42");
    String actualToStringResult = actualKeywordItem.toString();
    String actualKey = actualKeywordItem.getKey();

    // Assert
    assertEquals("42", actualKeywordItem.getValue());
    assertEquals("CreateRoom.KeywordItem(key=Key, value=42)", actualToStringResult);
    assertEquals("Key", actualKey);
  }
}
