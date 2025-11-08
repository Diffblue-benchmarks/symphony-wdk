package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class PinMessageDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link PinMessage#equals(Object)}
   *   <li>{@link PinMessage#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link PinMessage#equals(Object)}
   *   <li>{@link PinMessage#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link PinMessage#equals(Object)}
   *   <li>{@link PinMessage#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    PinMessage pinMessage = new PinMessage();

    // Act and Assert
    assertEquals(pinMessage, pinMessage);
    int expectedHashCodeResult = pinMessage.hashCode();
    assertEquals(expectedHashCodeResult, pinMessage.hashCode());
  }

  /**
   * Method under test: {@link PinMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    PinMessage pinMessage = new PinMessage();
    pinMessage.add("Key", "Value");

    // Act and Assert
    assertNotEquals(pinMessage, new PinMessage());
  }

  /**
   * Method under test: {@link PinMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    PinMessage pinMessage = new PinMessage();
    pinMessage.add("Key", mock(GetMessage.class));

    // Act and Assert
    assertNotEquals(pinMessage, new PinMessage());
  }

  /**
   * Method under test: {@link PinMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    PinMessage pinMessage = new PinMessage();
    pinMessage.setMessageId("42");

    // Act and Assert
    assertNotEquals(pinMessage, new PinMessage());
  }

  /**
   * Method under test: {@link PinMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    PinMessage pinMessage = new PinMessage();

    PinMessage pinMessage2 = new PinMessage();
    pinMessage2.setMessageId("42");

    // Act and Assert
    assertNotEquals(pinMessage, pinMessage2);
  }

  /**
   * Method under test: {@link PinMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new PinMessage(), null);
  }

  /**
   * Method under test: {@link PinMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new PinMessage(), "Different type to PinMessage");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link PinMessage}
   *   <li>{@link PinMessage#setMessageId(String)}
   *   <li>{@link PinMessage#toString()}
   *   <li>{@link PinMessage#getMessageId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    PinMessage actualPinMessage = new PinMessage();
    actualPinMessage.setMessageId("42");
    String actualToStringResult = actualPinMessage.toString();

    // Assert that nothing has changed
    assertEquals("42", actualPinMessage.getMessageId());
    assertEquals("PinMessage(messageId=42)", actualToStringResult);
    assertTrue(actualPinMessage.getVariableProperties().isEmpty());
  }
}
