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

class DemoteRoomOwnerDiffblueTest {
  /**
   * Test {@link DemoteRoomOwner#equals(Object)}, and {@link DemoteRoomOwner#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DemoteRoomOwner#equals(Object)}
   *   <li>{@link DemoteRoomOwner#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean DemoteRoomOwner.equals(Object)", "int DemoteRoomOwner.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();
    DemoteRoomOwner demoteRoomOwner2 = new DemoteRoomOwner();

    // Act and Assert
    assertEquals(demoteRoomOwner, demoteRoomOwner2);
    int expectedHashCodeResult = demoteRoomOwner.hashCode();
    assertEquals(expectedHashCodeResult, demoteRoomOwner2.hashCode());
  }

  /**
   * Test {@link DemoteRoomOwner#equals(Object)}, and {@link DemoteRoomOwner#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DemoteRoomOwner#equals(Object)}
   *   <li>{@link DemoteRoomOwner#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean DemoteRoomOwner.equals(Object)", "int DemoteRoomOwner.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();
    demoteRoomOwner.setStreamId("42");

    DemoteRoomOwner demoteRoomOwner2 = new DemoteRoomOwner();
    demoteRoomOwner2.setStreamId("42");

    // Act and Assert
    assertEquals(demoteRoomOwner, demoteRoomOwner2);
    int expectedHashCodeResult = demoteRoomOwner.hashCode();
    assertEquals(expectedHashCodeResult, demoteRoomOwner2.hashCode());
  }

  /**
   * Test {@link DemoteRoomOwner#equals(Object)}, and {@link DemoteRoomOwner#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DemoteRoomOwner#equals(Object)}
   *   <li>{@link DemoteRoomOwner#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean DemoteRoomOwner.equals(Object)", "int DemoteRoomOwner.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();

    // Act and Assert
    assertEquals(demoteRoomOwner, demoteRoomOwner);
    int expectedHashCodeResult = demoteRoomOwner.hashCode();
    assertEquals(expectedHashCodeResult, demoteRoomOwner.hashCode());
  }

  /**
   * Test {@link DemoteRoomOwner#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoteRoomOwner#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean DemoteRoomOwner.equals(Object)", "int DemoteRoomOwner.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();
    demoteRoomOwner.add("Key", "Value");

    // Act and Assert
    assertNotEquals(demoteRoomOwner, new DemoteRoomOwner());
  }

  /**
   * Test {@link DemoteRoomOwner#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoteRoomOwner#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean DemoteRoomOwner.equals(Object)", "int DemoteRoomOwner.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();
    demoteRoomOwner.setStreamId("42");

    // Act and Assert
    assertNotEquals(demoteRoomOwner, new DemoteRoomOwner());
  }

  /**
   * Test {@link DemoteRoomOwner#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoteRoomOwner#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean DemoteRoomOwner.equals(Object)", "int DemoteRoomOwner.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();

    DemoteRoomOwner demoteRoomOwner2 = new DemoteRoomOwner();
    demoteRoomOwner2.setStreamId("42");

    // Act and Assert
    assertNotEquals(demoteRoomOwner, demoteRoomOwner2);
  }

  /**
   * Test {@link DemoteRoomOwner#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoteRoomOwner#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean DemoteRoomOwner.equals(Object)", "int DemoteRoomOwner.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new DemoteRoomOwner(), null);
  }

  /**
   * Test {@link DemoteRoomOwner#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link DemoteRoomOwner#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean DemoteRoomOwner.equals(Object)", "int DemoteRoomOwner.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new DemoteRoomOwner(), "Different type to DemoteRoomOwner");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link DemoteRoomOwner#setStreamId(String)}
   *   <li>{@link DemoteRoomOwner#setUserIds(List)}
   *   <li>{@link DemoteRoomOwner#toString()}
   *   <li>{@link DemoteRoomOwner#getStreamId()}
   *   <li>{@link DemoteRoomOwner#getUserIds()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DemoteRoomOwner.getStreamId()", "List DemoteRoomOwner.getUserIds()",
      "void DemoteRoomOwner.setStreamId(String)", "void DemoteRoomOwner.setUserIds(List)",
      "String DemoteRoomOwner.toString()"})
  void testGettersAndSetters() {
    // Arrange
    DemoteRoomOwner demoteRoomOwner = new DemoteRoomOwner();

    // Act
    demoteRoomOwner.setStreamId("42");
    ArrayList<Long> userIds = new ArrayList<>();
    demoteRoomOwner.setUserIds(userIds);
    String actualToStringResult = demoteRoomOwner.toString();
    String actualStreamId = demoteRoomOwner.getStreamId();
    List<Long> actualUserIds = demoteRoomOwner.getUserIds();

    // Assert
    assertEquals("42", actualStreamId);
    assertEquals("DemoteRoomOwner(streamId=42, userIds=[])", actualToStringResult);
    assertTrue(actualUserIds.isEmpty());
    assertSame(userIds, actualUserIds);
  }

  /**
   * Test new {@link DemoteRoomOwner} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link DemoteRoomOwner}
   */
  @Test
  @DisplayName("Test new DemoteRoomOwner (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DemoteRoomOwner.<init>()"})
  void testNewDemoteRoomOwner() {
    // Arrange and Act
    DemoteRoomOwner actualDemoteRoomOwner = new DemoteRoomOwner();

    // Assert
    assertNull(actualDemoteRoomOwner.getOn());
    assertNull(actualDemoteRoomOwner.getObo());
    assertNull(actualDemoteRoomOwner.getElseCondition());
    assertNull(actualDemoteRoomOwner.getId());
    assertNull(actualDemoteRoomOwner.getIfCondition());
    RelationalEvents events = actualDemoteRoomOwner.getEvents();
    assertNull(events.getParentId());
    assertNull(actualDemoteRoomOwner.getStreamId());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualDemoteRoomOwner.getUserIds().isEmpty());
    assertTrue(actualDemoteRoomOwner.getVariableProperties().isEmpty());
  }
}
