package com.symphony.bdk.workflow.swadl.v1.activity.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GetUserStreamsDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUserStreams#equals(Object)}
   *   <li>{@link GetUserStreams#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    GetUserStreams getUserStreams2 = new GetUserStreams();

    // Act and Assert
    assertEquals(getUserStreams, getUserStreams2);
    int expectedHashCodeResult = getUserStreams.hashCode();
    assertEquals(expectedHashCodeResult, getUserStreams2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUserStreams#equals(Object)}
   *   <li>{@link GetUserStreams#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.setTypes(new ArrayList<>());

    GetUserStreams getUserStreams2 = new GetUserStreams();
    getUserStreams2.setTypes(new ArrayList<>());

    // Act and Assert
    assertEquals(getUserStreams, getUserStreams2);
    int expectedHashCodeResult = getUserStreams.hashCode();
    assertEquals(expectedHashCodeResult, getUserStreams2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUserStreams#equals(Object)}
   *   <li>{@link GetUserStreams#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.setIncludeInactiveStreams(true);

    GetUserStreams getUserStreams2 = new GetUserStreams();
    getUserStreams2.setIncludeInactiveStreams(true);

    // Act and Assert
    assertEquals(getUserStreams, getUserStreams2);
    int expectedHashCodeResult = getUserStreams.hashCode();
    assertEquals(expectedHashCodeResult, getUserStreams2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUserStreams#equals(Object)}
   *   <li>{@link GetUserStreams#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.setLimit(1);

    GetUserStreams getUserStreams2 = new GetUserStreams();
    getUserStreams2.setLimit(1);

    // Act and Assert
    assertEquals(getUserStreams, getUserStreams2);
    int expectedHashCodeResult = getUserStreams.hashCode();
    assertEquals(expectedHashCodeResult, getUserStreams2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUserStreams#equals(Object)}
   *   <li>{@link GetUserStreams#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.setSkip(1);

    GetUserStreams getUserStreams2 = new GetUserStreams();
    getUserStreams2.setSkip(1);

    // Act and Assert
    assertEquals(getUserStreams, getUserStreams2);
    int expectedHashCodeResult = getUserStreams.hashCode();
    assertEquals(expectedHashCodeResult, getUserStreams2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetUserStreams#equals(Object)}
   *   <li>{@link GetUserStreams#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();

    // Act and Assert
    assertEquals(getUserStreams, getUserStreams);
    int expectedHashCodeResult = getUserStreams.hashCode();
    assertEquals(expectedHashCodeResult, getUserStreams.hashCode());
  }

  /**
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getUserStreams, new GetUserStreams());
  }

  /**
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.add("Key", mock(GetStream.class));

    // Act and Assert
    assertNotEquals(getUserStreams, new GetUserStreams());
  }

  /**
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.setTypes(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUserStreams, new GetUserStreams());
  }

  /**
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.setIncludeInactiveStreams(true);

    // Act and Assert
    assertNotEquals(getUserStreams, new GetUserStreams());
  }

  /**
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.setLimit(1);

    // Act and Assert
    assertNotEquals(getUserStreams, new GetUserStreams());
  }

  /**
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.setSkip(1);

    // Act and Assert
    assertNotEquals(getUserStreams, new GetUserStreams());
  }

  /**
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();

    GetUserStreams getUserStreams2 = new GetUserStreams();
    getUserStreams2.setTypes(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUserStreams, getUserStreams2);
  }

  /**
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();

    GetUserStreams getUserStreams2 = new GetUserStreams();
    getUserStreams2.setIncludeInactiveStreams(true);

    // Act and Assert
    assertNotEquals(getUserStreams, getUserStreams2);
  }

  /**
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();

    GetUserStreams getUserStreams2 = new GetUserStreams();
    getUserStreams2.setLimit(1);

    // Act and Assert
    assertNotEquals(getUserStreams, getUserStreams2);
  }

  /**
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();

    GetUserStreams getUserStreams2 = new GetUserStreams();
    getUserStreams2.setSkip(1);

    // Act and Assert
    assertNotEquals(getUserStreams, getUserStreams2);
  }

  /**
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetUserStreams(), null);
  }

  /**
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetUserStreams(), "Different type to GetUserStreams");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetUserStreams}
   *   <li>{@link GetUserStreams#setIncludeInactiveStreams(Boolean)}
   *   <li>{@link GetUserStreams#setLimit(Integer)}
   *   <li>{@link GetUserStreams#setSkip(Integer)}
   *   <li>{@link GetUserStreams#setTypes(List)}
   *   <li>{@link GetUserStreams#toString()}
   *   <li>{@link GetUserStreams#getIncludeInactiveStreams()}
   *   <li>{@link GetUserStreams#getLimit()}
   *   <li>{@link GetUserStreams#getSkip()}
   *   <li>{@link GetUserStreams#getTypes()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetUserStreams actualGetUserStreams = new GetUserStreams();
    actualGetUserStreams.setIncludeInactiveStreams(true);
    actualGetUserStreams.setLimit(1);
    actualGetUserStreams.setSkip(1);
    ArrayList<String> types = new ArrayList<>();
    actualGetUserStreams.setTypes(types);
    String actualToStringResult = actualGetUserStreams.toString();
    Boolean actualIncludeInactiveStreams = actualGetUserStreams.getIncludeInactiveStreams();
    Integer actualLimit = actualGetUserStreams.getLimit();
    Integer actualSkip = actualGetUserStreams.getSkip();
    List<String> actualTypes = actualGetUserStreams.getTypes();

    // Assert that nothing has changed
    assertEquals("GetUserStreams(types=[], includeInactiveStreams=true, limit=1, skip=1)", actualToStringResult);
    assertEquals(1, actualLimit.intValue());
    assertEquals(1, actualSkip.intValue());
    assertTrue(actualIncludeInactiveStreams);
    assertTrue(actualTypes.isEmpty());
    assertTrue(actualGetUserStreams.getVariableProperties().isEmpty());
    assertSame(types, actualTypes);
  }
}
