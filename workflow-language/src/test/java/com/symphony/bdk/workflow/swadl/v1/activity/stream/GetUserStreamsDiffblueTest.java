package com.symphony.bdk.workflow.swadl.v1.activity.stream;

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

class GetUserStreamsDiffblueTest {
  /**
   * Test {@link GetUserStreams#equals(Object)}, and {@link GetUserStreams#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUserStreams#equals(Object)}
   *   <li>{@link GetUserStreams#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
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
   * Test {@link GetUserStreams#equals(Object)}, and {@link GetUserStreams#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUserStreams#equals(Object)}
   *   <li>{@link GetUserStreams#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
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
   * Test {@link GetUserStreams#equals(Object)}, and {@link GetUserStreams#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUserStreams#equals(Object)}
   *   <li>{@link GetUserStreams#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
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
   * Test {@link GetUserStreams#equals(Object)}, and {@link GetUserStreams#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUserStreams#equals(Object)}
   *   <li>{@link GetUserStreams#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
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
   * Test {@link GetUserStreams#equals(Object)}, and {@link GetUserStreams#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUserStreams#equals(Object)}
   *   <li>{@link GetUserStreams#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
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
   * Test {@link GetUserStreams#equals(Object)}, and {@link GetUserStreams#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetUserStreams#equals(Object)}
   *   <li>{@link GetUserStreams#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();

    // Act and Assert
    assertEquals(getUserStreams, getUserStreams);
    int expectedHashCodeResult = getUserStreams.hashCode();
    assertEquals(expectedHashCodeResult, getUserStreams.hashCode());
  }

  /**
   * Test {@link GetUserStreams#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getUserStreams, new GetUserStreams());
  }

  /**
   * Test {@link GetUserStreams#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.setTypes(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUserStreams, new GetUserStreams());
  }

  /**
   * Test {@link GetUserStreams#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.setIncludeInactiveStreams(true);

    // Act and Assert
    assertNotEquals(getUserStreams, new GetUserStreams());
  }

  /**
   * Test {@link GetUserStreams#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.setLimit(1);

    // Act and Assert
    assertNotEquals(getUserStreams, new GetUserStreams());
  }

  /**
   * Test {@link GetUserStreams#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();
    getUserStreams.setSkip(1);

    // Act and Assert
    assertNotEquals(getUserStreams, new GetUserStreams());
  }

  /**
   * Test {@link GetUserStreams#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();

    GetUserStreams getUserStreams2 = new GetUserStreams();
    getUserStreams2.setTypes(new ArrayList<>());

    // Act and Assert
    assertNotEquals(getUserStreams, getUserStreams2);
  }

  /**
   * Test {@link GetUserStreams#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();

    GetUserStreams getUserStreams2 = new GetUserStreams();
    getUserStreams2.setIncludeInactiveStreams(true);

    // Act and Assert
    assertNotEquals(getUserStreams, getUserStreams2);
  }

  /**
   * Test {@link GetUserStreams#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();

    GetUserStreams getUserStreams2 = new GetUserStreams();
    getUserStreams2.setLimit(1);

    // Act and Assert
    assertNotEquals(getUserStreams, getUserStreams2);
  }

  /**
   * Test {@link GetUserStreams#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    GetUserStreams getUserStreams = new GetUserStreams();

    GetUserStreams getUserStreams2 = new GetUserStreams();
    getUserStreams2.setSkip(1);

    // Act and Assert
    assertNotEquals(getUserStreams, getUserStreams2);
  }

  /**
   * Test {@link GetUserStreams#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetUserStreams(), null);
  }

  /**
   * Test {@link GetUserStreams#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetUserStreams#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetUserStreams.equals(Object)", "int GetUserStreams.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetUserStreams(), "Different type to GetUserStreams");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetUserStreams.<init>()", "Boolean GetUserStreams.getIncludeInactiveStreams()",
      "Integer GetUserStreams.getLimit()", "Integer GetUserStreams.getSkip()", "List GetUserStreams.getTypes()",
      "void GetUserStreams.setIncludeInactiveStreams(Boolean)", "void GetUserStreams.setLimit(Integer)",
      "void GetUserStreams.setSkip(Integer)", "void GetUserStreams.setTypes(List)", "String GetUserStreams.toString()"})
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

    // Assert
    assertEquals("GetUserStreams(types=[], includeInactiveStreams=true, limit=1, skip=1)", actualToStringResult);
    assertNull(actualGetUserStreams.getOn());
    assertNull(actualGetUserStreams.getObo());
    assertNull(actualGetUserStreams.getElseCondition());
    assertNull(actualGetUserStreams.getId());
    assertNull(actualGetUserStreams.getIfCondition());
    assertEquals(1, actualLimit.intValue());
    assertEquals(1, actualSkip.intValue());
    assertTrue(actualIncludeInactiveStreams);
    assertTrue(actualTypes.isEmpty());
    assertTrue(actualGetUserStreams.getVariableProperties().isEmpty());
    assertSame(types, actualTypes);
  }
}
