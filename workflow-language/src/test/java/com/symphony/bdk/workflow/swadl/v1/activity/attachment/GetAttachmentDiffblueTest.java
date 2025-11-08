package com.symphony.bdk.workflow.swadl.v1.activity.attachment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import org.junit.jupiter.api.Test;

class GetAttachmentDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetAttachment#equals(Object)}
   *   <li>{@link GetAttachment#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();
    GetAttachment getAttachment2 = new GetAttachment();

    // Act and Assert
    assertEquals(getAttachment, getAttachment2);
    int expectedHashCodeResult = getAttachment.hashCode();
    assertEquals(expectedHashCodeResult, getAttachment2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetAttachment#equals(Object)}
   *   <li>{@link GetAttachment#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();
    getAttachment.setMessageId("42");

    GetAttachment getAttachment2 = new GetAttachment();
    getAttachment2.setMessageId("42");

    // Act and Assert
    assertEquals(getAttachment, getAttachment2);
    int expectedHashCodeResult = getAttachment.hashCode();
    assertEquals(expectedHashCodeResult, getAttachment2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetAttachment#equals(Object)}
   *   <li>{@link GetAttachment#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();
    getAttachment.setAttachmentId("42");

    GetAttachment getAttachment2 = new GetAttachment();
    getAttachment2.setAttachmentId("42");

    // Act and Assert
    assertEquals(getAttachment, getAttachment2);
    int expectedHashCodeResult = getAttachment.hashCode();
    assertEquals(expectedHashCodeResult, getAttachment2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link GetAttachment#equals(Object)}
   *   <li>{@link GetAttachment#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();

    // Act and Assert
    assertEquals(getAttachment, getAttachment);
    int expectedHashCodeResult = getAttachment.hashCode();
    assertEquals(expectedHashCodeResult, getAttachment.hashCode());
  }

  /**
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();
    getAttachment.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getAttachment, new GetAttachment());
  }

  /**
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();
    getAttachment.add("Key", mock(Debug.class));

    // Act and Assert
    assertNotEquals(getAttachment, new GetAttachment());
  }

  /**
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();
    getAttachment.setMessageId("42");

    // Act and Assert
    assertNotEquals(getAttachment, new GetAttachment());
  }

  /**
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();
    getAttachment.setAttachmentId("42");

    // Act and Assert
    assertNotEquals(getAttachment, new GetAttachment());
  }

  /**
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();

    GetAttachment getAttachment2 = new GetAttachment();
    getAttachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(getAttachment, getAttachment2);
  }

  /**
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();

    GetAttachment getAttachment2 = new GetAttachment();
    getAttachment2.setAttachmentId("42");

    // Act and Assert
    assertNotEquals(getAttachment, getAttachment2);
  }

  /**
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetAttachment(), null);
  }

  /**
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetAttachment(), "Different type to GetAttachment");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetAttachment}
   *   <li>{@link GetAttachment#setAttachmentId(String)}
   *   <li>{@link GetAttachment#setMessageId(String)}
   *   <li>{@link GetAttachment#toString()}
   *   <li>{@link GetAttachment#getAttachmentId()}
   *   <li>{@link GetAttachment#getMessageId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    GetAttachment actualGetAttachment = new GetAttachment();
    actualGetAttachment.setAttachmentId("42");
    actualGetAttachment.setMessageId("42");
    String actualToStringResult = actualGetAttachment.toString();
    String actualAttachmentId = actualGetAttachment.getAttachmentId();

    // Assert that nothing has changed
    assertEquals("42", actualAttachmentId);
    assertEquals("42", actualGetAttachment.getMessageId());
    assertEquals("GetAttachment(messageId=42, attachmentId=42)", actualToStringResult);
    assertTrue(actualGetAttachment.getVariableProperties().isEmpty());
  }
}
