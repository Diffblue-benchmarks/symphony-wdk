package com.symphony.bdk.workflow.swadl.v1.activity.connection;

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

class GetConnectionsDiffblueTest {
  /**
   * Test {@link GetConnections#equals(Object)}, and {@link GetConnections#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetConnections#equals(Object)}
   *   <li>{@link GetConnections#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnections.equals(Object)", "int GetConnections.hashCode()"})
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
   * Test {@link GetConnections#equals(Object)}, and {@link GetConnections#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetConnections#equals(Object)}
   *   <li>{@link GetConnections#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnections.equals(Object)", "int GetConnections.hashCode()"})
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
   * Test {@link GetConnections#equals(Object)}, and {@link GetConnections#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetConnections#equals(Object)}
   *   <li>{@link GetConnections#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnections.equals(Object)", "int GetConnections.hashCode()"})
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
   * Test {@link GetConnections#equals(Object)}, and {@link GetConnections#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetConnections#equals(Object)}
   *   <li>{@link GetConnections#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnections.equals(Object)", "int GetConnections.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetConnections getConnections = new GetConnections();

    // Act and Assert
    assertEquals(getConnections, getConnections);
    int expectedHashCodeResult = getConnections.hashCode();
    assertEquals(expectedHashCodeResult, getConnections.hashCode());
  }

  /**
   * Test {@link GetConnections#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnections.equals(Object)", "int GetConnections.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetConnections getConnections = new GetConnections();
    getConnections.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getConnections, new GetConnections());
  }

  /**
   * Test {@link GetConnections#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnections.equals(Object)", "int GetConnections.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetConnections getConnections = new GetConnections();
    getConnections.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getConnections, new GetConnections());
  }

  /**
   * Test {@link GetConnections#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnections.equals(Object)", "int GetConnections.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetConnections getConnections = new GetConnections();
    getConnections.setStatus("Status");

    // Act and Assert
    assertNotEquals(getConnections, new GetConnections());
  }

  /**
   * Test {@link GetConnections#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnections.equals(Object)", "int GetConnections.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetConnections getConnections = new GetConnections();

    GetConnections getConnections2 = new GetConnections();
    getConnections2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getConnections, getConnections2);
  }

  /**
   * Test {@link GetConnections#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnections.equals(Object)", "int GetConnections.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetConnections getConnections = new GetConnections();

    GetConnections getConnections2 = new GetConnections();
    getConnections2.setStatus("Status");

    // Act and Assert
    assertNotEquals(getConnections, getConnections2);
  }

  /**
   * Test {@link GetConnections#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnections.equals(Object)", "int GetConnections.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetConnections(), null);
  }

  /**
   * Test {@link GetConnections#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetConnections#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetConnections.equals(Object)", "int GetConnections.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetConnections(), "Different type to GetConnections");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetConnections.<init>()", "String GetConnections.getStatus()",
      "List GetConnections.getUserIds()", "void GetConnections.setStatus(String)",
      "void GetConnections.setUserIds(List)", "String GetConnections.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GetConnections actualGetConnections = new GetConnections();
    actualGetConnections.setStatus("Status");
    ArrayList<Long> userIds = new ArrayList<>();
    actualGetConnections.setUserIds(userIds);
    String actualToStringResult = actualGetConnections.toString();
    String actualStatus = actualGetConnections.getStatus();
    List<Long> actualUserIds = actualGetConnections.getUserIds();

    // Assert
    assertEquals("GetConnections(userIds=[], status=Status)", actualToStringResult);
    assertEquals("Status", actualStatus);
    assertNull(actualGetConnections.getOn());
    assertNull(actualGetConnections.getObo());
    assertNull(actualGetConnections.getElseCondition());
    assertNull(actualGetConnections.getId());
    assertNull(actualGetConnections.getIfCondition());
    assertTrue(actualUserIds.isEmpty());
    assertTrue(actualGetConnections.getVariableProperties().isEmpty());
    assertSame(userIds, actualUserIds);
  }
}
