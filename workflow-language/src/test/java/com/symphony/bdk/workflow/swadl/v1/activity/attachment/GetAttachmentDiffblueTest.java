package com.symphony.bdk.workflow.swadl.v1.activity.attachment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetAttachmentDiffblueTest {
  /**
   * Test {@link GetAttachment#equals(Object)}, and {@link GetAttachment#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetAttachment#equals(Object)}
   *   <li>{@link GetAttachment#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetAttachment.equals(Object)", "int GetAttachment.hashCode()"})
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
   * Test {@link GetAttachment#equals(Object)}, and {@link GetAttachment#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetAttachment#equals(Object)}
   *   <li>{@link GetAttachment#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetAttachment.equals(Object)", "int GetAttachment.hashCode()"})
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
   * Test {@link GetAttachment#equals(Object)}, and {@link GetAttachment#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetAttachment#equals(Object)}
   *   <li>{@link GetAttachment#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetAttachment.equals(Object)", "int GetAttachment.hashCode()"})
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
   * Test {@link GetAttachment#equals(Object)}, and {@link GetAttachment#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetAttachment#equals(Object)}
   *   <li>{@link GetAttachment#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetAttachment.equals(Object)", "int GetAttachment.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();

    // Act and Assert
    assertEquals(getAttachment, getAttachment);
    int expectedHashCodeResult = getAttachment.hashCode();
    assertEquals(expectedHashCodeResult, getAttachment.hashCode());
  }

  /**
   * Test {@link GetAttachment#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetAttachment.equals(Object)", "int GetAttachment.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();
    getAttachment.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getAttachment, new GetAttachment());
  }

  /**
   * Test {@link GetAttachment#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetAttachment.equals(Object)", "int GetAttachment.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();
    getAttachment.setMessageId("42");

    // Act and Assert
    assertNotEquals(getAttachment, new GetAttachment());
  }

  /**
   * Test {@link GetAttachment#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetAttachment.equals(Object)", "int GetAttachment.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();
    getAttachment.setAttachmentId("42");

    // Act and Assert
    assertNotEquals(getAttachment, new GetAttachment());
  }

  /**
   * Test {@link GetAttachment#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetAttachment.equals(Object)", "int GetAttachment.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();

    GetAttachment getAttachment2 = new GetAttachment();
    getAttachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(getAttachment, getAttachment2);
  }

  /**
   * Test {@link GetAttachment#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetAttachment.equals(Object)", "int GetAttachment.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    GetAttachment getAttachment = new GetAttachment();

    GetAttachment getAttachment2 = new GetAttachment();
    getAttachment2.setAttachmentId("42");

    // Act and Assert
    assertNotEquals(getAttachment, getAttachment2);
  }

  /**
   * Test {@link GetAttachment#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetAttachment.equals(Object)", "int GetAttachment.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetAttachment(), null);
  }

  /**
   * Test {@link GetAttachment#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetAttachment#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetAttachment.equals(Object)", "int GetAttachment.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetAttachment(), "Different type to GetAttachment");
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetAttachment.<init>()", "String GetAttachment.getAttachmentId()",
      "String GetAttachment.getMessageId()", "void GetAttachment.setAttachmentId(String)",
      "void GetAttachment.setMessageId(String)", "String GetAttachment.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GetAttachment actualGetAttachment = new GetAttachment();
    actualGetAttachment.setAttachmentId("42");
    actualGetAttachment.setMessageId("42");
    String actualToStringResult = actualGetAttachment.toString();
    String actualAttachmentId = actualGetAttachment.getAttachmentId();

    // Assert
    assertEquals("42", actualAttachmentId);
    assertEquals("42", actualGetAttachment.getMessageId());
    assertEquals("GetAttachment(messageId=42, attachmentId=42)", actualToStringResult);
    assertNull(actualGetAttachment.getOn());
    assertNull(actualGetAttachment.getElseCondition());
    assertNull(actualGetAttachment.getId());
    assertNull(actualGetAttachment.getIfCondition());
    assertTrue(actualGetAttachment.getVariableProperties().isEmpty());
  }
}
