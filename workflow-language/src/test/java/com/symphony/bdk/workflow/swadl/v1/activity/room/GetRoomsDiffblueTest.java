package com.symphony.bdk.workflow.swadl.v1.activity.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetRoomsDiffblueTest {
  /**
   * Test {@link GetRooms#equals(Object)}, and {@link GetRooms#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetRooms#equals(Object)}
   *   <li>{@link GetRooms#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    GetRooms getRooms2 = new GetRooms();

    // Act and Assert
    assertEquals(getRooms, getRooms2);
    int expectedHashCodeResult = getRooms.hashCode();
    assertEquals(expectedHashCodeResult, getRooms2.hashCode());
  }

  /**
   * Test {@link GetRooms#equals(Object)}, and {@link GetRooms#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetRooms#equals(Object)}
   *   <li>{@link GetRooms#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    // Act and Assert
    assertEquals(getRooms, getRooms);
    int expectedHashCodeResult = getRooms.hashCode();
    assertEquals(expectedHashCodeResult, getRooms.hashCode());
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setQuery("Query");

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setLabels(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setActive(true);

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setIsPrivate(true);

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setCreatorId("42");

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setOwnerId("42");

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setMemberId("42");

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setSortOrder("asc");

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setLimit(1);

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setSkip(1);

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setQuery("Query");

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setLabels(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setActive(true);

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setIsPrivate(true);

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual16() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setCreatorId("42");

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual17() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setOwnerId("42");

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual18() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setMemberId("42");

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual19() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setSortOrder("asc");

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual20() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setLimit(1);

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual21() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setSkip(1);

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetRooms(), null);
  }

  /**
   * Test {@link GetRooms#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetRooms.equals(Object)", "int GetRooms.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetRooms(), "Different type to GetRooms");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetRooms}
   *   <li>{@link GetRooms#setActive(Boolean)}
   *   <li>{@link GetRooms#setCreatorId(String)}
   *   <li>{@link GetRooms#setIsPrivate(Boolean)}
   *   <li>{@link GetRooms#setLabels(List)}
   *   <li>{@link GetRooms#setLimit(Integer)}
   *   <li>{@link GetRooms#setMemberId(String)}
   *   <li>{@link GetRooms#setOwnerId(String)}
   *   <li>{@link GetRooms#setQuery(String)}
   *   <li>{@link GetRooms#setSkip(Integer)}
   *   <li>{@link GetRooms#setSortOrder(String)}
   *   <li>{@link GetRooms#toString()}
   *   <li>{@link GetRooms#getActive()}
   *   <li>{@link GetRooms#getCreatorId()}
   *   <li>{@link GetRooms#getIsPrivate()}
   *   <li>{@link GetRooms#getLabels()}
   *   <li>{@link GetRooms#getLimit()}
   *   <li>{@link GetRooms#getMemberId()}
   *   <li>{@link GetRooms#getOwnerId()}
   *   <li>{@link GetRooms#getQuery()}
   *   <li>{@link GetRooms#getSkip()}
   *   <li>{@link GetRooms#getSortOrder()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetRooms.<init>()", "Boolean GetRooms.getActive()", "String GetRooms.getCreatorId()",
      "Boolean GetRooms.getIsPrivate()", "List GetRooms.getLabels()", "Integer GetRooms.getLimit()",
      "String GetRooms.getMemberId()", "String GetRooms.getOwnerId()", "String GetRooms.getQuery()",
      "Integer GetRooms.getSkip()", "String GetRooms.getSortOrder()", "void GetRooms.setActive(Boolean)",
      "void GetRooms.setCreatorId(String)", "void GetRooms.setIsPrivate(Boolean)", "void GetRooms.setLabels(List)",
      "void GetRooms.setLimit(Integer)", "void GetRooms.setMemberId(String)", "void GetRooms.setOwnerId(String)",
      "void GetRooms.setQuery(String)", "void GetRooms.setSkip(Integer)", "void GetRooms.setSortOrder(String)",
      "String GetRooms.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GetRooms actualGetRooms = new GetRooms();
    actualGetRooms.setActive(true);
    actualGetRooms.setCreatorId("42");
    actualGetRooms.setIsPrivate(true);
    ArrayList<String> labels = new ArrayList<>();
    actualGetRooms.setLabels(labels);
    actualGetRooms.setLimit(1);
    actualGetRooms.setMemberId("42");
    actualGetRooms.setOwnerId("42");
    actualGetRooms.setQuery("Query");
    actualGetRooms.setSkip(1);
    actualGetRooms.setSortOrder("asc");
    String actualToStringResult = actualGetRooms.toString();
    Boolean actualActive = actualGetRooms.getActive();
    String actualCreatorId = actualGetRooms.getCreatorId();
    Boolean actualIsPrivate = actualGetRooms.getIsPrivate();
    List<String> actualLabels = actualGetRooms.getLabels();
    Integer actualLimit = actualGetRooms.getLimit();
    String actualMemberId = actualGetRooms.getMemberId();
    String actualOwnerId = actualGetRooms.getOwnerId();
    String actualQuery = actualGetRooms.getQuery();
    Integer actualSkip = actualGetRooms.getSkip();

    // Assert
    assertEquals("42", actualCreatorId);
    assertEquals("42", actualMemberId);
    assertEquals("42", actualOwnerId);
    assertEquals("GetRooms(query=Query, labels=[], active=true, isPrivate=true, creatorId=42, ownerId=42, memberId=42,"
        + " sortOrder=asc, limit=1, skip=1)", actualToStringResult);
    assertEquals("Query", actualQuery);
    assertEquals("asc", actualGetRooms.getSortOrder());
    assertNull(actualGetRooms.getOn());
    assertNull(actualGetRooms.getObo());
    assertNull(actualGetRooms.getElseCondition());
    assertNull(actualGetRooms.getId());
    assertNull(actualGetRooms.getIfCondition());
    assertEquals(1, actualLimit.intValue());
    assertEquals(1, actualSkip.intValue());
    assertTrue(actualActive);
    assertTrue(actualIsPrivate);
    assertTrue(actualLabels.isEmpty());
    assertTrue(actualGetRooms.getVariableProperties().isEmpty());
    assertSame(labels, actualLabels);
  }
}
