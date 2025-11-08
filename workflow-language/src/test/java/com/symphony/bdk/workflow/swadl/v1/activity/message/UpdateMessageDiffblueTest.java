package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class UpdateMessageDiffblueTest {
  /**
   * Test {@link UpdateMessage#setContent(Object)}.
   * <ul>
   *   <li>Given {@code template}.</li>
   *   <li>Then {@link UpdateMessage} (default constructor) Template is {@code Not all who wander are lost}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#setContent(Object)}
   */
  @Test
  @DisplayName("Test setContent(Object); given 'template'; then UpdateMessage (default constructor) Template is 'Not all who wander are lost'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UpdateMessage.setContent(Object)"})
  void testSetContent_givenTemplate_thenUpdateMessageTemplateIsNotAllWhoWanderAreLost() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("template", "Not all who wander are lost");
    objectObjectMap.put("template-path", "Not all who wander are lost");

    // Act
    updateMessage.setContent(objectObjectMap);

    // Assert
    assertEquals("Not all who wander are lost", updateMessage.getTemplate());
    assertEquals("Not all who wander are lost", updateMessage.getTemplatePath());
    assertNull(updateMessage.getContent());
  }

  /**
   * Test {@link UpdateMessage#setContent(Object)}.
   * <ul>
   *   <li>When {@code Content}.</li>
   *   <li>Then {@link UpdateMessage} (default constructor) Content is {@code Content}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#setContent(Object)}
   */
  @Test
  @DisplayName("Test setContent(Object); when 'Content'; then UpdateMessage (default constructor) Content is 'Content'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UpdateMessage.setContent(Object)"})
  void testSetContent_whenContent_thenUpdateMessageContentIsContent() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    // Act
    updateMessage.setContent("Content");

    // Assert
    assertEquals("Content", updateMessage.getContent());
    assertNull(updateMessage.getTemplate());
    assertNull(updateMessage.getTemplatePath());
  }

  /**
   * Test {@link UpdateMessage#setContent(Object)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then {@link UpdateMessage} (default constructor) Content is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#setContent(Object)}
   */
  @Test
  @DisplayName("Test setContent(Object); when one; then UpdateMessage (default constructor) Content is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UpdateMessage.setContent(Object)"})
  void testSetContent_whenOne_thenUpdateMessageContentIsNull() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    // Act
    updateMessage.setContent(1);

    // Assert that nothing has changed
    assertNull(updateMessage.getContent());
    assertNull(updateMessage.getTemplate());
    assertNull(updateMessage.getTemplatePath());
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}, and {@link UpdateMessage#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateMessage#equals(Object)}
   *   <li>{@link UpdateMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    UpdateMessage updateMessage2 = new UpdateMessage();

    // Act and Assert
    assertEquals(updateMessage, updateMessage2);
    int expectedHashCodeResult = updateMessage.hashCode();
    assertEquals(expectedHashCodeResult, updateMessage2.hashCode());
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}, and {@link UpdateMessage#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateMessage#equals(Object)}
   *   <li>{@link UpdateMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setContent("Content");

    UpdateMessage updateMessage2 = new UpdateMessage();
    updateMessage2.setContent("Content");

    // Act and Assert
    assertEquals(updateMessage, updateMessage2);
    int expectedHashCodeResult = updateMessage.hashCode();
    assertEquals(expectedHashCodeResult, updateMessage2.hashCode());
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}, and {@link UpdateMessage#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateMessage#equals(Object)}
   *   <li>{@link UpdateMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setMessageId("42");

    UpdateMessage updateMessage2 = new UpdateMessage();
    updateMessage2.setMessageId("42");

    // Act and Assert
    assertEquals(updateMessage, updateMessage2);
    int expectedHashCodeResult = updateMessage.hashCode();
    assertEquals(expectedHashCodeResult, updateMessage2.hashCode());
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}, and {@link UpdateMessage#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateMessage#equals(Object)}
   *   <li>{@link UpdateMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setTemplate("Template");

    UpdateMessage updateMessage2 = new UpdateMessage();
    updateMessage2.setTemplate("Template");

    // Act and Assert
    assertEquals(updateMessage, updateMessage2);
    int expectedHashCodeResult = updateMessage.hashCode();
    assertEquals(expectedHashCodeResult, updateMessage2.hashCode());
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}, and {@link UpdateMessage#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateMessage#equals(Object)}
   *   <li>{@link UpdateMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setTemplatePath("Template Path");

    UpdateMessage updateMessage2 = new UpdateMessage();
    updateMessage2.setTemplatePath("Template Path");

    // Act and Assert
    assertEquals(updateMessage, updateMessage2);
    int expectedHashCodeResult = updateMessage.hashCode();
    assertEquals(expectedHashCodeResult, updateMessage2.hashCode());
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}, and {@link UpdateMessage#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateMessage#equals(Object)}
   *   <li>{@link UpdateMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    // Act and Assert
    assertEquals(updateMessage, updateMessage);
    int expectedHashCodeResult = updateMessage.hashCode();
    assertEquals(expectedHashCodeResult, updateMessage.hashCode());
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.add("Key", "Value");

    // Act and Assert
    assertNotEquals(updateMessage, new UpdateMessage());
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setContent("Content");

    // Act and Assert
    assertNotEquals(updateMessage, new UpdateMessage());
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setMessageId("42");

    // Act and Assert
    assertNotEquals(updateMessage, new UpdateMessage());
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setTemplate("Template");

    // Act and Assert
    assertNotEquals(updateMessage, new UpdateMessage());
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setTemplatePath("Template Path");

    // Act and Assert
    assertNotEquals(updateMessage, new UpdateMessage());
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    UpdateMessage updateMessage2 = new UpdateMessage();
    updateMessage2.setContent("Content");

    // Act and Assert
    assertNotEquals(updateMessage, updateMessage2);
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    UpdateMessage updateMessage2 = new UpdateMessage();
    updateMessage2.setMessageId("42");

    // Act and Assert
    assertNotEquals(updateMessage, updateMessage2);
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    UpdateMessage updateMessage2 = new UpdateMessage();
    updateMessage2.setTemplate("Template");

    // Act and Assert
    assertNotEquals(updateMessage, updateMessage2);
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    UpdateMessage updateMessage2 = new UpdateMessage();
    updateMessage2.setTemplatePath("Template Path");

    // Act and Assert
    assertNotEquals(updateMessage, updateMessage2);
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateMessage(), null);
  }

  /**
   * Test {@link UpdateMessage#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean UpdateMessage.equals(Object)", "int UpdateMessage.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateMessage(), "Different type to UpdateMessage");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateMessage#setMessageId(String)}
   *   <li>{@link UpdateMessage#setSilent(Boolean)}
   *   <li>{@link UpdateMessage#setTemplate(String)}
   *   <li>{@link UpdateMessage#setTemplatePath(String)}
   *   <li>{@link UpdateMessage#toString()}
   *   <li>{@link UpdateMessage#getContent()}
   *   <li>{@link UpdateMessage#getMessageId()}
   *   <li>{@link UpdateMessage#getSilent()}
   *   <li>{@link UpdateMessage#getTemplate()}
   *   <li>{@link UpdateMessage#getTemplatePath()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String UpdateMessage.getContent()", "String UpdateMessage.getMessageId()",
      "Boolean UpdateMessage.getSilent()", "String UpdateMessage.getTemplate()",
      "String UpdateMessage.getTemplatePath()", "void UpdateMessage.setMessageId(String)",
      "void UpdateMessage.setSilent(Boolean)", "void UpdateMessage.setTemplate(String)",
      "void UpdateMessage.setTemplatePath(String)", "String UpdateMessage.toString()"})
  void testGettersAndSetters() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    // Act
    updateMessage.setMessageId("42");
    updateMessage.setSilent(true);
    updateMessage.setTemplate("Template");
    updateMessage.setTemplatePath("Template Path");
    String actualToStringResult = updateMessage.toString();
    String actualContent = updateMessage.getContent();
    String actualMessageId = updateMessage.getMessageId();
    Boolean actualSilent = updateMessage.getSilent();
    String actualTemplate = updateMessage.getTemplate();

    // Assert
    assertEquals("42", actualMessageId);
    assertEquals("Template Path", updateMessage.getTemplatePath());
    assertEquals("Template", actualTemplate);
    assertEquals(
        "UpdateMessage(messageId=42, template=Template, templatePath=Template Path, content=null," + " silent=true)",
        actualToStringResult);
    assertNull(actualContent);
    assertTrue(actualSilent);
  }

  /**
   * Test new {@link UpdateMessage} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of {@link UpdateMessage}
   */
  @Test
  @DisplayName("Test new UpdateMessage (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void UpdateMessage.<init>()"})
  void testNewUpdateMessage() {
    // Arrange and Act
    UpdateMessage actualUpdateMessage = new UpdateMessage();

    // Assert
    assertNull(actualUpdateMessage.getOn());
    assertNull(actualUpdateMessage.getElseCondition());
    assertNull(actualUpdateMessage.getId());
    assertNull(actualUpdateMessage.getIfCondition());
    assertNull(actualUpdateMessage.getContent());
    assertNull(actualUpdateMessage.getMessageId());
    assertNull(actualUpdateMessage.getTemplate());
    assertNull(actualUpdateMessage.getTemplatePath());
    assertTrue(actualUpdateMessage.getSilent());
    assertTrue(actualUpdateMessage.getVariableProperties().isEmpty());
  }
}
