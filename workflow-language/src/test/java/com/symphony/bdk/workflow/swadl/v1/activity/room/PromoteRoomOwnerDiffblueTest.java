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

class PromoteRoomOwnerDiffblueTest {
  /**
   * Test {@link PromoteRoomOwner#equals(Object)}, and {@link PromoteRoomOwner#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link PromoteRoomOwner#equals(Object)}
   *   <li>{@link PromoteRoomOwner#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PromoteRoomOwner.equals(Object)", "int PromoteRoomOwner.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();
    PromoteRoomOwner promoteRoomOwner2 = new PromoteRoomOwner();

    // Act and Assert
    assertEquals(promoteRoomOwner, promoteRoomOwner2);
    int expectedHashCodeResult = promoteRoomOwner.hashCode();
    assertEquals(expectedHashCodeResult, promoteRoomOwner2.hashCode());
  }

  /**
   * Test {@link PromoteRoomOwner#equals(Object)}, and {@link PromoteRoomOwner#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link PromoteRoomOwner#equals(Object)}
   *   <li>{@link PromoteRoomOwner#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PromoteRoomOwner.equals(Object)", "int PromoteRoomOwner.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();
    promoteRoomOwner.setStreamId("42");

    PromoteRoomOwner promoteRoomOwner2 = new PromoteRoomOwner();
    promoteRoomOwner2.setStreamId("42");

    // Act and Assert
    assertEquals(promoteRoomOwner, promoteRoomOwner2);
    int expectedHashCodeResult = promoteRoomOwner.hashCode();
    assertEquals(expectedHashCodeResult, promoteRoomOwner2.hashCode());
  }

  /**
   * Test {@link PromoteRoomOwner#equals(Object)}, and {@link PromoteRoomOwner#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link PromoteRoomOwner#equals(Object)}
   *   <li>{@link PromoteRoomOwner#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PromoteRoomOwner.equals(Object)", "int PromoteRoomOwner.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();

    // Act and Assert
    assertEquals(promoteRoomOwner, promoteRoomOwner);
    int expectedHashCodeResult = promoteRoomOwner.hashCode();
    assertEquals(expectedHashCodeResult, promoteRoomOwner.hashCode());
  }

  /**
   * Test {@link PromoteRoomOwner#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PromoteRoomOwner#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PromoteRoomOwner.equals(Object)", "int PromoteRoomOwner.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();
    promoteRoomOwner.add("Key", "Value");

    // Act and Assert
    assertNotEquals(promoteRoomOwner, new PromoteRoomOwner());
  }

  /**
   * Test {@link PromoteRoomOwner#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PromoteRoomOwner#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PromoteRoomOwner.equals(Object)", "int PromoteRoomOwner.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();
    promoteRoomOwner.setStreamId("42");

    // Act and Assert
    assertNotEquals(promoteRoomOwner, new PromoteRoomOwner());
  }

  /**
   * Test {@link PromoteRoomOwner#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PromoteRoomOwner#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PromoteRoomOwner.equals(Object)", "int PromoteRoomOwner.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();

    PromoteRoomOwner promoteRoomOwner2 = new PromoteRoomOwner();
    promoteRoomOwner2.setStreamId("42");

    // Act and Assert
    assertNotEquals(promoteRoomOwner, promoteRoomOwner2);
  }

  /**
   * Test {@link PromoteRoomOwner#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PromoteRoomOwner#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PromoteRoomOwner.equals(Object)", "int PromoteRoomOwner.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new PromoteRoomOwner(), null);
  }

  /**
   * Test {@link PromoteRoomOwner#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PromoteRoomOwner#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PromoteRoomOwner.equals(Object)", "int PromoteRoomOwner.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new PromoteRoomOwner(), "Different type to PromoteRoomOwner");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link PromoteRoomOwner#setStreamId(String)}
   *   <li>{@link PromoteRoomOwner#setUserIds(List)}
   *   <li>{@link PromoteRoomOwner#toString()}
   *   <li>{@link PromoteRoomOwner#getStreamId()}
   *   <li>{@link PromoteRoomOwner#getUserIds()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String PromoteRoomOwner.getStreamId()", "List PromoteRoomOwner.getUserIds()",
      "void PromoteRoomOwner.setStreamId(String)", "void PromoteRoomOwner.setUserIds(List)",
      "String PromoteRoomOwner.toString()"})
  void testGettersAndSetters() {
    // Arrange
    PromoteRoomOwner promoteRoomOwner = new PromoteRoomOwner();

    // Act
    promoteRoomOwner.setStreamId("42");
    ArrayList<Long> userIds = new ArrayList<>();
    promoteRoomOwner.setUserIds(userIds);
    String actualToStringResult = promoteRoomOwner.toString();
    String actualStreamId = promoteRoomOwner.getStreamId();
    List<Long> actualUserIds = promoteRoomOwner.getUserIds();

    // Assert
    assertEquals("42", actualStreamId);
    assertEquals("PromoteRoomOwner(streamId=42, userIds=[])", actualToStringResult);
    assertTrue(actualUserIds.isEmpty());
    assertSame(userIds, actualUserIds);
  }

  /**
   * Test new {@link PromoteRoomOwner} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link PromoteRoomOwner}
   */
  @Test
  @DisplayName("Test new PromoteRoomOwner (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void PromoteRoomOwner.<init>()"})
  void testNewPromoteRoomOwner() {
    // Arrange and Act
    PromoteRoomOwner actualPromoteRoomOwner = new PromoteRoomOwner();

    // Assert
    assertNull(actualPromoteRoomOwner.getOn());
    assertNull(actualPromoteRoomOwner.getObo());
    assertNull(actualPromoteRoomOwner.getElseCondition());
    assertNull(actualPromoteRoomOwner.getId());
    assertNull(actualPromoteRoomOwner.getIfCondition());
    RelationalEvents events = actualPromoteRoomOwner.getEvents();
    assertNull(events.getParentId());
    assertNull(actualPromoteRoomOwner.getStreamId());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualPromoteRoomOwner.getUserIds().isEmpty());
    assertTrue(actualPromoteRoomOwner.getVariableProperties().isEmpty());
  }
}
