package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.Test;

class UnpinMessageDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UnpinMessage#equals(Object)}
   *   <li>{@link UnpinMessage#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link UnpinMessage#equals(Object)}
   *   <li>{@link UnpinMessage#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link UnpinMessage#equals(Object)}
   *   <li>{@link UnpinMessage#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UnpinMessage unpinMessage = new UnpinMessage();

    // Act and Assert
    assertEquals(unpinMessage, unpinMessage);
    int expectedHashCodeResult = unpinMessage.hashCode();
    assertEquals(expectedHashCodeResult, unpinMessage.hashCode());
  }

  /**
   * Method under test: {@link UnpinMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UnpinMessage unpinMessage = new UnpinMessage();
    unpinMessage.add("Key", "Value");

    // Act and Assert
    assertNotEquals(unpinMessage, new UnpinMessage());
  }

  /**
   * Method under test: {@link UnpinMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UnpinMessage unpinMessage = new UnpinMessage();
    unpinMessage.add("Key", mock(PinMessage.class));

    // Act and Assert
    assertNotEquals(unpinMessage, new UnpinMessage());
  }

  /**
   * Method under test: {@link UnpinMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    UnpinMessage unpinMessage = new UnpinMessage();
    unpinMessage.setStreamId("42");

    // Act and Assert
    assertNotEquals(unpinMessage, new UnpinMessage());
  }

  /**
   * Method under test: {@link UnpinMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    UnpinMessage unpinMessage = new UnpinMessage();

    UnpinMessage unpinMessage2 = new UnpinMessage();
    unpinMessage2.setStreamId("42");

    // Act and Assert
    assertNotEquals(unpinMessage, unpinMessage2);
  }

  /**
   * Method under test: {@link UnpinMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UnpinMessage(), null);
  }

  /**
   * Method under test: {@link UnpinMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UnpinMessage(), "Different type to UnpinMessage");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link UnpinMessage}
   *   <li>{@link UnpinMessage#setStreamId(String)}
   *   <li>{@link UnpinMessage#toString()}
   *   <li>{@link UnpinMessage#getStreamId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    UnpinMessage actualUnpinMessage = new UnpinMessage();
    actualUnpinMessage.setStreamId("42");
    String actualToStringResult = actualUnpinMessage.toString();

    // Assert that nothing has changed
    assertEquals("42", actualUnpinMessage.getStreamId());
    assertEquals("UnpinMessage(streamId=42)", actualToStringResult);
    assertTrue(actualUnpinMessage.getVariableProperties().isEmpty());
  }
}
