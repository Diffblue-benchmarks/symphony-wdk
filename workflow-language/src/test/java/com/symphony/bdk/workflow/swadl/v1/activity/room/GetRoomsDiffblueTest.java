package com.symphony.bdk.workflow.swadl.v1.activity.room;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GetRoomsDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetRooms#equals(Object)}
   *   <li>{@link GetRooms#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link GetRooms#equals(Object)}
   *   <li>{@link GetRooms#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    // Act and Assert
    assertEquals(getRooms, getRooms);
    int expectedHashCodeResult = getRooms.hashCode();
    assertEquals(expectedHashCodeResult, getRooms.hashCode());
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.add("Key", mock(AddRoomMember.class));

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setQuery("Query");

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setLabels(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setActive(true);

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setIsPrivate(true);

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setCreatorId("42");

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setOwnerId("42");

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setMemberId("42");

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setSortOrder("asc");

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setLimit(1);

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    GetRooms getRooms = new GetRooms();
    getRooms.setSkip(1);

    // Act and Assert
    assertNotEquals(getRooms, new GetRooms());
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setQuery("Query");

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setLabels(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setActive(true);

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual16() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setIsPrivate(true);

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual17() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setCreatorId("42");

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual18() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setOwnerId("42");

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual19() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setMemberId("42");

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual20() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setSortOrder("asc");

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual21() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setLimit(1);

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual22() {
    // Arrange
    GetRooms getRooms = new GetRooms();

    GetRooms getRooms2 = new GetRooms();
    getRooms2.setSkip(1);

    // Act and Assert
    assertNotEquals(getRooms, getRooms2);
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetRooms(), null);
  }

  /**
   * Method under test: {@link GetRooms#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetRooms(), "Different type to GetRooms");
  }

  /**
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

    // Assert that nothing has changed
    assertEquals("42", actualCreatorId);
    assertEquals("42", actualMemberId);
    assertEquals("42", actualOwnerId);
    assertEquals("GetRooms(query=Query, labels=[], active=true, isPrivate=true, creatorId=42, ownerId=42, memberId=42,"
        + " sortOrder=asc, limit=1, skip=1)", actualToStringResult);
    assertEquals("Query", actualQuery);
    assertEquals("asc", actualGetRooms.getSortOrder());
    assertEquals(1, actualLimit.intValue());
    assertEquals(1, actualSkip.intValue());
    assertTrue(actualActive);
    assertTrue(actualIsPrivate);
    assertTrue(actualLabels.isEmpty());
    assertTrue(actualGetRooms.getVariableProperties().isEmpty());
    assertSame(labels, actualLabels);
  }
}
