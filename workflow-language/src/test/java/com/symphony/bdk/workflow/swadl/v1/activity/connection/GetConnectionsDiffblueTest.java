package com.symphony.bdk.workflow.swadl.v1.activity.connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class GetConnectionsDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetConnections#equals(Object)}
   *   <li>{@link GetConnections#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetConnections getConnections = new GetConnections();
    GetConnections getConnections2 = new GetConnections();

    // Act and Assert
    assertEquals(getConnections, getConnections2);
    int expectedHashCodeResult = getConnections.hashCode();
    assertEquals(expectedHashCodeResult, getConnections2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetConnections#equals(Object)}
   *   <li>{@link GetConnections#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetConnections getConnections = new GetConnections();
    getConnections.setUserIds(new ArrayList<>());

    GetConnections getConnections2 = new GetConnections();
    getConnections2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(getConnections, getConnections2);
    int expectedHashCodeResult = getConnections.hashCode();
    assertEquals(expectedHashCodeResult, getConnections2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetConnections#equals(Object)}
   *   <li>{@link GetConnections#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    GetConnections getConnections = new GetConnections();
    getConnections.setStatus("Status");

    GetConnections getConnections2 = new GetConnections();
    getConnections2.setStatus("Status");

    // Act and Assert
    assertEquals(getConnections, getConnections2);
    int expectedHashCodeResult = getConnections.hashCode();
    assertEquals(expectedHashCodeResult, getConnections2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetConnections#equals(Object)}
   *   <li>{@link GetConnections#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetConnections getConnections = new GetConnections();

    // Act and Assert
    assertEquals(getConnections, getConnections);
    int expectedHashCodeResult = getConnections.hashCode();
    assertEquals(expectedHashCodeResult, getConnections.hashCode());
  }

  /**
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetConnections getConnections = new GetConnections();
    getConnections.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getConnections, new GetConnections());
  }

  /**
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetConnections getConnections = new GetConnections();
    getConnections.add("Key", mock(AcceptConnection.class));

    // Act and Assert
    assertNotEquals(getConnections, new GetConnections());
  }

  /**
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetConnections getConnections = new GetConnections();
    getConnections.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getConnections, new GetConnections());
  }

  /**
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetConnections getConnections = new GetConnections();
    getConnections.setStatus("Status");

    // Act and Assert
    assertNotEquals(getConnections, new GetConnections());
  }

  /**
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetConnections getConnections = new GetConnections();

    GetConnections getConnections2 = new GetConnections();
    getConnections2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getConnections, getConnections2);
  }

  /**
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    GetConnections getConnections = new GetConnections();

    GetConnections getConnections2 = new GetConnections();
    getConnections2.setStatus("Status");

    // Act and Assert
    assertNotEquals(getConnections, getConnections2);
  }

  /**
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetConnections(), null);
  }

  /**
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetConnections(), "Different type to GetConnections");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetConnections}
   *   <li>{@link GetConnections#setStatus(String)}
   *   <li>{@link GetConnections#setUserIds(List)}
   *   <li>{@link GetConnections#toString()}
   *   <li>{@link GetConnections#getStatus()}
   *   <li>{@link GetConnections#getUserIds()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetConnections actualGetConnections = new GetConnections();
    actualGetConnections.setStatus("Status");
    ArrayList<Long> userIds = new ArrayList<>();
    actualGetConnections.setUserIds(userIds);
    String actualToStringResult = actualGetConnections.toString();
    String actualStatus = actualGetConnections.getStatus();
    List<Long> actualUserIds = actualGetConnections.getUserIds();

    // Assert that nothing has changed
    assertEquals("GetConnections(userIds=[], status=Status)", actualToStringResult);
    assertEquals("Status", actualStatus);
    assertTrue(actualUserIds.isEmpty());
    assertTrue(actualGetConnections.getVariableProperties().isEmpty());
    assertSame(userIds, actualUserIds);
  }
}
