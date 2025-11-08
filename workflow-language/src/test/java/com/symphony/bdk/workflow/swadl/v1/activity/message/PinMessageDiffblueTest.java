package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class PinMessageDiffblueTest {
  /**
   * Test {@link PinMessage#equals(Object)}, and {@link PinMessage#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link PinMessage#equals(Object)}
   *   <li>{@link PinMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PinMessage.equals(Object)", "int PinMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    PinMessage pinMessage = new PinMessage();
    PinMessage pinMessage2 = new PinMessage();

    // Act and Assert
    assertEquals(pinMessage, pinMessage2);
    int expectedHashCodeResult = pinMessage.hashCode();
    assertEquals(expectedHashCodeResult, pinMessage2.hashCode());
  }

  /**
   * Test {@link PinMessage#equals(Object)}, and {@link PinMessage#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link PinMessage#equals(Object)}
   *   <li>{@link PinMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PinMessage.equals(Object)", "int PinMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    PinMessage pinMessage = new PinMessage();
    pinMessage.setMessageId("42");

    PinMessage pinMessage2 = new PinMessage();
    pinMessage2.setMessageId("42");

    // Act and Assert
    assertEquals(pinMessage, pinMessage2);
    int expectedHashCodeResult = pinMessage.hashCode();
    assertEquals(expectedHashCodeResult, pinMessage2.hashCode());
  }

  /**
   * Test {@link PinMessage#equals(Object)}, and {@link PinMessage#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link PinMessage#equals(Object)}
   *   <li>{@link PinMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PinMessage.equals(Object)", "int PinMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    PinMessage pinMessage = new PinMessage();

    // Act and Assert
    assertEquals(pinMessage, pinMessage);
    int expectedHashCodeResult = pinMessage.hashCode();
    assertEquals(expectedHashCodeResult, pinMessage.hashCode());
  }

  /**
   * Test {@link PinMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PinMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PinMessage.equals(Object)", "int PinMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    PinMessage pinMessage = new PinMessage();
    pinMessage.add("Key", "Value");

    // Act and Assert
    assertNotEquals(pinMessage, new PinMessage());
  }

  /**
   * Test {@link PinMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PinMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PinMessage.equals(Object)", "int PinMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    PinMessage pinMessage = new PinMessage();
    pinMessage.setMessageId("42");

    // Act and Assert
    assertNotEquals(pinMessage, new PinMessage());
  }

  /**
   * Test {@link PinMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PinMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PinMessage.equals(Object)", "int PinMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    PinMessage pinMessage = new PinMessage();

    PinMessage pinMessage2 = new PinMessage();
    pinMessage2.setMessageId("42");

    // Act and Assert
    assertNotEquals(pinMessage, pinMessage2);
  }

  /**
   * Test {@link PinMessage#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PinMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PinMessage.equals(Object)", "int PinMessage.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new PinMessage(), null);
  }

  /**
   * Test {@link PinMessage#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link PinMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean PinMessage.equals(Object)", "int PinMessage.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new PinMessage(), "Different type to PinMessage");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link PinMessage}
   *   <li>{@link PinMessage#setMessageId(String)}
   *   <li>{@link PinMessage#toString()}
   *   <li>{@link PinMessage#getMessageId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void PinMessage.<init>()", "String PinMessage.getMessageId()",
      "void PinMessage.setMessageId(String)", "String PinMessage.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    PinMessage actualPinMessage = new PinMessage();
    actualPinMessage.setMessageId("42");
    String actualToStringResult = actualPinMessage.toString();

    // Assert
    assertEquals("42", actualPinMessage.getMessageId());
    assertEquals("PinMessage(messageId=42)", actualToStringResult);
    assertNull(actualPinMessage.getOn());
    assertNull(actualPinMessage.getObo());
    assertNull(actualPinMessage.getElseCondition());
    assertNull(actualPinMessage.getId());
    assertNull(actualPinMessage.getIfCondition());
    assertTrue(actualPinMessage.getVariableProperties().isEmpty());
  }
}
