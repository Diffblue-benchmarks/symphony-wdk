package com.symphony.bdk.workflow.swadl.v1.activity.stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetStreamDiffblueTest {
  /**
   * Test {@link GetStream#equals(Object)}, and {@link GetStream#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetStream#equals(Object)}
   *   <li>{@link GetStream#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStream.equals(Object)", "int GetStream.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetStream getStream = new GetStream();
    GetStream getStream2 = new GetStream();

    // Act and Assert
    assertEquals(getStream, getStream2);
    int expectedHashCodeResult = getStream.hashCode();
    assertEquals(expectedHashCodeResult, getStream2.hashCode());
  }

  /**
   * Test {@link GetStream#equals(Object)}, and {@link GetStream#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetStream#equals(Object)}
   *   <li>{@link GetStream#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStream.equals(Object)", "int GetStream.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetStream getStream = new GetStream();
    getStream.setStreamId("42");

    GetStream getStream2 = new GetStream();
    getStream2.setStreamId("42");

    // Act and Assert
    assertEquals(getStream, getStream2);
    int expectedHashCodeResult = getStream.hashCode();
    assertEquals(expectedHashCodeResult, getStream2.hashCode());
  }

  /**
   * Test {@link GetStream#equals(Object)}, and {@link GetStream#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetStream#equals(Object)}
   *   <li>{@link GetStream#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStream.equals(Object)", "int GetStream.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetStream getStream = new GetStream();

    // Act and Assert
    assertEquals(getStream, getStream);
    int expectedHashCodeResult = getStream.hashCode();
    assertEquals(expectedHashCodeResult, getStream.hashCode());
  }

  /**
   * Test {@link GetStream#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStream#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStream.equals(Object)", "int GetStream.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetStream getStream = new GetStream();
    getStream.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getStream, new GetStream());
  }

  /**
   * Test {@link GetStream#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStream#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStream.equals(Object)", "int GetStream.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetStream getStream = new GetStream();
    getStream.setStreamId("42");

    // Act and Assert
    assertNotEquals(getStream, new GetStream());
  }

  /**
   * Test {@link GetStream#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStream#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStream.equals(Object)", "int GetStream.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetStream getStream = new GetStream();

    GetStream getStream2 = new GetStream();
    getStream2.setStreamId("42");

    // Act and Assert
    assertNotEquals(getStream, getStream2);
  }

  /**
   * Test {@link GetStream#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStream#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStream.equals(Object)", "int GetStream.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetStream(), null);
  }

  /**
   * Test {@link GetStream#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetStream#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetStream.equals(Object)", "int GetStream.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetStream(), "Different type to GetStream");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetStream}
   *   <li>{@link GetStream#setStreamId(String)}
   *   <li>{@link GetStream#toString()}
   *   <li>{@link GetStream#getStreamId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetStream.<init>()", "String GetStream.getStreamId()", "void GetStream.setStreamId(String)",
      "String GetStream.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GetStream actualGetStream = new GetStream();
    actualGetStream.setStreamId("42");
    String actualToStringResult = actualGetStream.toString();

    // Assert
    assertEquals("42", actualGetStream.getStreamId());
    assertEquals("GetStream(streamId=42)", actualToStringResult);
    assertNull(actualGetStream.getOn());
    assertNull(actualGetStream.getObo());
    assertNull(actualGetStream.getElseCondition());
    assertNull(actualGetStream.getId());
    assertNull(actualGetStream.getIfCondition());
    assertTrue(actualGetStream.getVariableProperties().isEmpty());
  }
}
