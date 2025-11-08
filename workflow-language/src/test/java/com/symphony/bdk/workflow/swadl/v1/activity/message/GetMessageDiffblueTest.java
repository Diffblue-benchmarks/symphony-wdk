package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class GetMessageDiffblueTest {
  /**
   * Test {@link GetMessage#equals(Object)}, and {@link GetMessage#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetMessage#equals(Object)}
   *   <li>{@link GetMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetMessage.equals(Object)", "int GetMessage.hashCode()"})
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
   * Test {@link GetMessage#equals(Object)}, and {@link GetMessage#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetMessage#equals(Object)}
   *   <li>{@link GetMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetMessage.equals(Object)", "int GetMessage.hashCode()"})
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
   * Test {@link GetMessage#equals(Object)}, and {@link GetMessage#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetMessage#equals(Object)}
   *   <li>{@link GetMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetMessage.equals(Object)", "int GetMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    GetMessage getMessage = new GetMessage();

    // Act and Assert
    assertEquals(getMessage, getMessage);
    int expectedHashCodeResult = getMessage.hashCode();
    assertEquals(expectedHashCodeResult, getMessage.hashCode());
  }

  /**
   * Test {@link GetMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetMessage.equals(Object)", "int GetMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    GetMessage getMessage = new GetMessage();
    getMessage.add("Key", "Value");

    // Act and Assert
    assertNotEquals(getMessage, new GetMessage());
  }

  /**
   * Test {@link GetMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetMessage.equals(Object)", "int GetMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    GetMessage getMessage = new GetMessage();
    getMessage.setMessageId("42");

    // Act and Assert
    assertNotEquals(getMessage, new GetMessage());
  }

  /**
   * Test {@link GetMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetMessage.equals(Object)", "int GetMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    GetMessage getMessage = new GetMessage();

    GetMessage getMessage2 = new GetMessage();
    getMessage2.setMessageId("42");

    // Act and Assert
    assertNotEquals(getMessage, getMessage2);
  }

  /**
   * Test {@link GetMessage#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetMessage.equals(Object)", "int GetMessage.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetMessage(), null);
  }

  /**
   * Test {@link GetMessage#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean GetMessage.equals(Object)", "int GetMessage.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new GetMessage(), "Different type to GetMessage");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link GetMessage}
   *   <li>{@link GetMessage#setMessageId(String)}
   *   <li>{@link GetMessage#toString()}
   *   <li>{@link GetMessage#getMessageId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetMessage.<init>()", "String GetMessage.getMessageId()",
      "void GetMessage.setMessageId(String)", "String GetMessage.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    GetMessage actualGetMessage = new GetMessage();
    actualGetMessage.setMessageId("42");
    String actualToStringResult = actualGetMessage.toString();

    // Assert
    assertEquals("42", actualGetMessage.getMessageId());
    assertEquals("GetMessage(messageId=42)", actualToStringResult);
    assertNull(actualGetMessage.getOn());
    assertNull(actualGetMessage.getElseCondition());
    assertNull(actualGetMessage.getId());
    assertNull(actualGetMessage.getIfCondition());
    assertTrue(actualGetMessage.getVariableProperties().isEmpty());
  }
}
