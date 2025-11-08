package com.symphony.bdk.workflow.swadl.v1.activity.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class GetStreamMembersDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetStreamMembers#equals(Object)}
   *   <li>{@link GetStreamMembers#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    GetStreamMembers getStreamMembers2 = new GetStreamMembers();

    // Act and Assert
    assertEquals(getStreamMembers, getStreamMembers2);
    int expectedHashCodeResult = getStreamMembers.hashCode();
    assertEquals(expectedHashCodeResult, getStreamMembers2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetStreamMembers#equals(Object)}
   *   <li>{@link GetStreamMembers#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.setStreamId("42");

    GetStreamMembers getStreamMembers2 = new GetStreamMembers();
    getStreamMembers2.setStreamId("42");

    // Act and Assert
    assertEquals(getStreamMembers, getStreamMembers2);
    int expectedHashCodeResult = getStreamMembers.hashCode();
    assertEquals(expectedHashCodeResult, getStreamMembers2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetStreamMembers#equals(Object)}
   *   <li>{@link GetStreamMembers#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.setLimit(1);

    GetStreamMembers getStreamMembers2 = new GetStreamMembers();
    getStreamMembers2.setLimit(1);

    // Act and Assert
    assertEquals(getStreamMembers, getStreamMembers2);
    int expectedHashCodeResult = getStreamMembers.hashCode();
    assertEquals(expectedHashCodeResult, getStreamMembers2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetStreamMembers#equals(Object)}
   *   <li>{@link GetStreamMembers#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.setSkip(1);

    GetStreamMembers getStreamMembers2 = new GetStreamMembers();
    getStreamMembers2.setSkip(1);

    // Act and Assert
    assertEquals(getStreamMembers, getStreamMembers2);
    int expectedHashCodeResult = getStreamMembers.hashCode();
    assertEquals(expectedHashCodeResult, getStreamMembers2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetStreamMembers#equals(Object)}
   *   <li>{@link GetStreamMembers#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();

    // Act and Assert
    assertEquals(getStreamMembers, getStreamMembers);
    int expectedHashCodeResult = getStreamMembers.hashCode();
    assertEquals(expectedHashCodeResult, getStreamMembers.hashCode());
  }

  /**
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getStreamMembers, new GetStreamMembers());
  }

  /**
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.add("Key", mock(GetStream.class));

    // Act and Assert
    assertNotEquals(getStreamMembers, new GetStreamMembers());
  }

  /**
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.setStreamId("42");

    // Act and Assert
    assertNotEquals(getStreamMembers, new GetStreamMembers());
  }

  /**
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.setLimit(1);

    // Act and Assert
    assertNotEquals(getStreamMembers, new GetStreamMembers());
  }

  /**
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.setSkip(1);

    // Act and Assert
    assertNotEquals(getStreamMembers, new GetStreamMembers());
  }

  /**
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();

    GetStreamMembers getStreamMembers2 = new GetStreamMembers();
    getStreamMembers2.setStreamId("42");

    // Act and Assert
    assertNotEquals(getStreamMembers, getStreamMembers2);
  }

  /**
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();

    GetStreamMembers getStreamMembers2 = new GetStreamMembers();
    getStreamMembers2.setLimit(1);

    // Act and Assert
    assertNotEquals(getStreamMembers, getStreamMembers2);
  }

  /**
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();

    GetStreamMembers getStreamMembers2 = new GetStreamMembers();
    getStreamMembers2.setSkip(1);

    // Act and Assert
    assertNotEquals(getStreamMembers, getStreamMembers2);
  }

  /**
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetStreamMembers(), null);
  }

  /**
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetStreamMembers(), "Different type to GetStreamMembers");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetStreamMembers}
   *   <li>{@link GetStreamMembers#setLimit(Integer)}
   *   <li>{@link GetStreamMembers#setSkip(Integer)}
   *   <li>{@link GetStreamMembers#setStreamId(String)}
   *   <li>{@link GetStreamMembers#toString()}
   *   <li>{@link GetStreamMembers#getLimit()}
   *   <li>{@link GetStreamMembers#getSkip()}
   *   <li>{@link GetStreamMembers#getStreamId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetStreamMembers actualGetStreamMembers = new GetStreamMembers();
    actualGetStreamMembers.setLimit(1);
    actualGetStreamMembers.setSkip(1);
    actualGetStreamMembers.setStreamId("42");
    String actualToStringResult = actualGetStreamMembers.toString();
    Integer actualLimit = actualGetStreamMembers.getLimit();
    Integer actualSkip = actualGetStreamMembers.getSkip();

    // Assert that nothing has changed
    assertEquals("42", actualGetStreamMembers.getStreamId());
    assertEquals("GetStreamMembers(streamId=42, limit=1, skip=1)", actualToStringResult);
    assertEquals(1, actualLimit.intValue());
    assertEquals(1, actualSkip.intValue());
    assertTrue(actualGetStreamMembers.getVariableProperties().isEmpty());
  }
}
