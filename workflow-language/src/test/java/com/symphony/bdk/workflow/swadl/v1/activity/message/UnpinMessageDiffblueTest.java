package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UnpinMessageDiffblueTest {
  /**
   * Test {@link UnpinMessage#equals(Object)}, and {@link UnpinMessage#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UnpinMessage#equals(Object)}
   *   <li>{@link UnpinMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UnpinMessage.equals(Object)", "int UnpinMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UnpinMessage unpinMessage = new UnpinMessage();
    UnpinMessage unpinMessage2 = new UnpinMessage();

    // Act and Assert
    assertEquals(unpinMessage, unpinMessage2);
    int expectedHashCodeResult = unpinMessage.hashCode();
    assertEquals(expectedHashCodeResult, unpinMessage2.hashCode());
  }

  /**
   * Test {@link UnpinMessage#equals(Object)}, and {@link UnpinMessage#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UnpinMessage#equals(Object)}
   *   <li>{@link UnpinMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UnpinMessage.equals(Object)", "int UnpinMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    UnpinMessage unpinMessage = new UnpinMessage();
    unpinMessage.setStreamId("42");

    UnpinMessage unpinMessage2 = new UnpinMessage();
    unpinMessage2.setStreamId("42");

    // Act and Assert
    assertEquals(unpinMessage, unpinMessage2);
    int expectedHashCodeResult = unpinMessage.hashCode();
    assertEquals(expectedHashCodeResult, unpinMessage2.hashCode());
  }

  /**
   * Test {@link UnpinMessage#equals(Object)}, and {@link UnpinMessage#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UnpinMessage#equals(Object)}
   *   <li>{@link UnpinMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UnpinMessage.equals(Object)", "int UnpinMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UnpinMessage unpinMessage = new UnpinMessage();

    // Act and Assert
    assertEquals(unpinMessage, unpinMessage);
    int expectedHashCodeResult = unpinMessage.hashCode();
    assertEquals(expectedHashCodeResult, unpinMessage.hashCode());
  }

  /**
   * Test {@link UnpinMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UnpinMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UnpinMessage.equals(Object)", "int UnpinMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UnpinMessage unpinMessage = new UnpinMessage();
    unpinMessage.add("Key", "Value");

    // Act and Assert
    assertNotEquals(unpinMessage, new UnpinMessage());
  }

  /**
   * Test {@link UnpinMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UnpinMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UnpinMessage.equals(Object)", "int UnpinMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UnpinMessage unpinMessage = new UnpinMessage();
    unpinMessage.setStreamId("42");

    // Act and Assert
    assertNotEquals(unpinMessage, new UnpinMessage());
  }

  /**
   * Test {@link UnpinMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UnpinMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UnpinMessage.equals(Object)", "int UnpinMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    UnpinMessage unpinMessage = new UnpinMessage();

    UnpinMessage unpinMessage2 = new UnpinMessage();
    unpinMessage2.setStreamId("42");

    // Act and Assert
    assertNotEquals(unpinMessage, unpinMessage2);
  }

  /**
   * Test {@link UnpinMessage#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UnpinMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UnpinMessage.equals(Object)", "int UnpinMessage.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UnpinMessage(), null);
  }

  /**
   * Test {@link UnpinMessage#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UnpinMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UnpinMessage.equals(Object)", "int UnpinMessage.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UnpinMessage(), "Different type to UnpinMessage");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link UnpinMessage}
   *   <li>{@link UnpinMessage#setStreamId(String)}
   *   <li>{@link UnpinMessage#toString()}
   *   <li>{@link UnpinMessage#getStreamId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UnpinMessage.<init>()", "String UnpinMessage.getStreamId()",
      "void UnpinMessage.setStreamId(String)", "String UnpinMessage.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    UnpinMessage actualUnpinMessage = new UnpinMessage();
    actualUnpinMessage.setStreamId("42");
    String actualToStringResult = actualUnpinMessage.toString();

    // Assert
    assertEquals("42", actualUnpinMessage.getStreamId());
    assertEquals("UnpinMessage(streamId=42)", actualToStringResult);
    assertNull(actualUnpinMessage.getOn());
    assertNull(actualUnpinMessage.getObo());
    assertNull(actualUnpinMessage.getElseCondition());
    assertNull(actualUnpinMessage.getId());
    assertNull(actualUnpinMessage.getIfCondition());
    assertTrue(actualUnpinMessage.getVariableProperties().isEmpty());
  }
}
