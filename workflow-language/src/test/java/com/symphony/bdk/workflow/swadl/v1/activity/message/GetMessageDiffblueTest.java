package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class GetMessageDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetMessage#equals(Object)}
   *   <li>{@link GetMessage#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetMessage getMessage = new GetMessage();
    GetMessage getMessage2 = new GetMessage();

    // Act and Assert
    assertEquals(getMessage, getMessage2);
    int expectedHashCodeResult = getMessage.hashCode();
    assertEquals(expectedHashCodeResult, getMessage2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetMessage#equals(Object)}
   *   <li>{@link GetMessage#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetMessage getMessage = new GetMessage();
    getMessage.setMessageId("42");

    GetMessage getMessage2 = new GetMessage();
    getMessage2.setMessageId("42");

    // Act and Assert
    assertEquals(getMessage, getMessage2);
    int expectedHashCodeResult = getMessage.hashCode();
    assertEquals(expectedHashCodeResult, getMessage2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetMessage#equals(Object)}
   *   <li>{@link GetMessage#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetMessage getMessage = new GetMessage();

    // Act and Assert
    assertEquals(getMessage, getMessage);
    int expectedHashCodeResult = getMessage.hashCode();
    assertEquals(expectedHashCodeResult, getMessage.hashCode());
  }

  /**
   * Method under test: {@link GetMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetMessage getMessage = new GetMessage();
    getMessage.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getMessage, new GetMessage());
  }

  /**
   * Method under test: {@link GetMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetMessage getMessage = new GetMessage();
    getMessage.add("Key", mock(GetMessages.class));

    // Act and Assert
    assertNotEquals(getMessage, new GetMessage());
  }

  /**
   * Method under test: {@link GetMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetMessage getMessage = new GetMessage();
    getMessage.setMessageId("42");

    // Act and Assert
    assertNotEquals(getMessage, new GetMessage());
  }

  /**
   * Method under test: {@link GetMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetMessage getMessage = new GetMessage();

    GetMessage getMessage2 = new GetMessage();
    getMessage2.setMessageId("42");

    // Act and Assert
    assertNotEquals(getMessage, getMessage2);
  }

  /**
   * Method under test: {@link GetMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetMessage(), null);
  }

  /**
   * Method under test: {@link GetMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetMessage(), "Different type to GetMessage");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetMessage}
   *   <li>{@link GetMessage#setMessageId(String)}
   *   <li>{@link GetMessage#toString()}
   *   <li>{@link GetMessage#getMessageId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetMessage actualGetMessage = new GetMessage();
    actualGetMessage.setMessageId("42");
    String actualToStringResult = actualGetMessage.toString();

    // Assert that nothing has changed
    assertEquals("42", actualGetMessage.getMessageId());
    assertEquals("GetMessage(messageId=42)", actualToStringResult);
    assertTrue(actualGetMessage.getVariableProperties().isEmpty());
  }
}
