package com.symphony.bdk.workflow.swadl.v1.activity.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetStreamMembersDiffblueTest {
  /**
   * Test {@link GetStreamMembers#equals(Object)}, and {@link GetStreamMembers#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetStreamMembers#equals(Object)}
   *   <li>{@link GetStreamMembers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
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
   * Test {@link GetStreamMembers#equals(Object)}, and {@link GetStreamMembers#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetStreamMembers#equals(Object)}
   *   <li>{@link GetStreamMembers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
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
   * Test {@link GetStreamMembers#equals(Object)}, and {@link GetStreamMembers#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetStreamMembers#equals(Object)}
   *   <li>{@link GetStreamMembers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
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
   * Test {@link GetStreamMembers#equals(Object)}, and {@link GetStreamMembers#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetStreamMembers#equals(Object)}
   *   <li>{@link GetStreamMembers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
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
   * Test {@link GetStreamMembers#equals(Object)}, and {@link GetStreamMembers#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetStreamMembers#equals(Object)}
   *   <li>{@link GetStreamMembers#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();

    // Act and Assert
    assertEquals(getStreamMembers, getStreamMembers);
    int expectedHashCodeResult = getStreamMembers.hashCode();
    assertEquals(expectedHashCodeResult, getStreamMembers.hashCode());
  }

  /**
   * Test {@link GetStreamMembers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getStreamMembers, new GetStreamMembers());
  }

  /**
   * Test {@link GetStreamMembers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.setStreamId("42");

    // Act and Assert
    assertNotEquals(getStreamMembers, new GetStreamMembers());
  }

  /**
   * Test {@link GetStreamMembers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.setLimit(1);

    // Act and Assert
    assertNotEquals(getStreamMembers, new GetStreamMembers());
  }

  /**
   * Test {@link GetStreamMembers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();
    getStreamMembers.setSkip(1);

    // Act and Assert
    assertNotEquals(getStreamMembers, new GetStreamMembers());
  }

  /**
   * Test {@link GetStreamMembers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();

    GetStreamMembers getStreamMembers2 = new GetStreamMembers();
    getStreamMembers2.setStreamId("42");

    // Act and Assert
    assertNotEquals(getStreamMembers, getStreamMembers2);
  }

  /**
   * Test {@link GetStreamMembers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();

    GetStreamMembers getStreamMembers2 = new GetStreamMembers();
    getStreamMembers2.setLimit(1);

    // Act and Assert
    assertNotEquals(getStreamMembers, getStreamMembers2);
  }

  /**
   * Test {@link GetStreamMembers#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    GetStreamMembers getStreamMembers = new GetStreamMembers();

    GetStreamMembers getStreamMembers2 = new GetStreamMembers();
    getStreamMembers2.setSkip(1);

    // Act and Assert
    assertNotEquals(getStreamMembers, getStreamMembers2);
  }

  /**
   * Test {@link GetStreamMembers#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetStreamMembers(), null);
  }

  /**
   * Test {@link GetStreamMembers#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStreamMembers#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStreamMembers.equals(Object)", "int GetStreamMembers.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetStreamMembers(), "Different type to GetStreamMembers");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetStreamMembers.<init>()", "Integer GetStreamMembers.getLimit()",
      "Integer GetStreamMembers.getSkip()", "String GetStreamMembers.getStreamId()",
      "void GetStreamMembers.setLimit(Integer)", "void GetStreamMembers.setSkip(Integer)",
      "void GetStreamMembers.setStreamId(String)", "String GetStreamMembers.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GetStreamMembers actualGetStreamMembers = new GetStreamMembers();
    actualGetStreamMembers.setLimit(1);
    actualGetStreamMembers.setSkip(1);
    actualGetStreamMembers.setStreamId("42");
    String actualToStringResult = actualGetStreamMembers.toString();
    Integer actualLimit = actualGetStreamMembers.getLimit();
    Integer actualSkip = actualGetStreamMembers.getSkip();

    // Assert
    assertEquals("42", actualGetStreamMembers.getStreamId());
    assertEquals("GetStreamMembers(streamId=42, limit=1, skip=1)", actualToStringResult);
    assertNull(actualGetStreamMembers.getOn());
    assertNull(actualGetStreamMembers.getElseCondition());
    assertNull(actualGetStreamMembers.getId());
    assertNull(actualGetStreamMembers.getIfCondition());
    assertEquals(1, actualLimit.intValue());
    assertEquals(1, actualSkip.intValue());
    assertTrue(actualGetStreamMembers.getVariableProperties().isEmpty());
  }
}
