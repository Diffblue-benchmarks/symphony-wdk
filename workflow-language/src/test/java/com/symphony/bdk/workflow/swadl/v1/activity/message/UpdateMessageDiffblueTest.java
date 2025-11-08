package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

class UpdateMessageDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateMessage#equals(Object)}
   *   <li>{@link UpdateMessage#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link UpdateMessage#setContent(Object)}
   */
  @Test
  void testSetContent() {
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
   * Method under test: {@link UpdateMessage#setContent(Object)}
   */
  @Test
  void testSetContent2() {
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
   * Method under test: {@link UpdateMessage#setContent(Object)}
   */
  @Test
  void testSetContent3() {
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
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateMessage#equals(Object)}
   *   <li>{@link UpdateMessage#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateMessage#equals(Object)}
   *   <li>{@link UpdateMessage#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateMessage#equals(Object)}
   *   <li>{@link UpdateMessage#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateMessage#equals(Object)}
   *   <li>{@link UpdateMessage#hashCode()}
   * </ul>
   */
  @Test
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
   * Methods under test:
   * <ul>
   *   <li>{@link UpdateMessage#equals(Object)}
   *   <li>{@link UpdateMessage#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    // Act and Assert
    assertEquals(updateMessage, updateMessage);
    int expectedHashCodeResult = updateMessage.hashCode();
    assertEquals(expectedHashCodeResult, updateMessage.hashCode());
  }

  /**
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.add("Key", "Value");

    // Act and Assert
    assertNotEquals(updateMessage, new UpdateMessage());
  }

  /**
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.add("Key", mock(GetMessage.class));

    // Act and Assert
    assertNotEquals(updateMessage, new UpdateMessage());
  }

  /**
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setContent("Content");

    // Act and Assert
    assertNotEquals(updateMessage, new UpdateMessage());
  }

  /**
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setMessageId("42");

    // Act and Assert
    assertNotEquals(updateMessage, new UpdateMessage());
  }

  /**
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setTemplate("Template");

    // Act and Assert
    assertNotEquals(updateMessage, new UpdateMessage());
  }

  /**
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setTemplatePath("Template Path");

    // Act and Assert
    assertNotEquals(updateMessage, new UpdateMessage());
  }

  /**
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    UpdateMessage updateMessage2 = new UpdateMessage();
    updateMessage2.setContent("Content");

    // Act and Assert
    assertNotEquals(updateMessage, updateMessage2);
  }

  /**
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    UpdateMessage updateMessage2 = new UpdateMessage();
    updateMessage2.setMessageId("42");

    // Act and Assert
    assertNotEquals(updateMessage, updateMessage2);
  }

  /**
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    UpdateMessage updateMessage2 = new UpdateMessage();
    updateMessage2.setTemplate("Template");

    // Act and Assert
    assertNotEquals(updateMessage, updateMessage2);
  }

  /**
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    UpdateMessage updateMessage2 = new UpdateMessage();
    updateMessage2.setTemplatePath("Template Path");

    // Act and Assert
    assertNotEquals(updateMessage, updateMessage2);
  }

  /**
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateMessage(), null);
  }

  /**
   * Method under test: {@link UpdateMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new UpdateMessage(), "Different type to UpdateMessage");
  }

  /**
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
  void testGettersAndSetters() {
    // Arrange
    UpdateMessage updateMessage = new UpdateMessage();

    // Act
    updateMessage.setMessageId("42");
    updateMessage.setSilent(true);
    updateMessage.setTemplate("Template");
    updateMessage.setTemplatePath("Template Path");
    String actualToStringResult = updateMessage.toString();
    updateMessage.getContent();
    String actualMessageId = updateMessage.getMessageId();
    Boolean actualSilent = updateMessage.getSilent();
    String actualTemplate = updateMessage.getTemplate();

    // Assert that nothing has changed
    assertEquals("42", actualMessageId);
    assertEquals("Template Path", updateMessage.getTemplatePath());
    assertEquals("Template", actualTemplate);
    assertEquals(
        "UpdateMessage(messageId=42, template=Template, templatePath=Template Path, content=null," + " silent=true)",
        actualToStringResult);
    assertTrue(actualSilent);
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link UpdateMessage}
   */
  @Test
  void testNewUpdateMessage() {
    // Arrange and Act
    UpdateMessage actualUpdateMessage = new UpdateMessage();

    // Assert
    assertNull(actualUpdateMessage.getOn());
    assertNull(actualUpdateMessage.getElseCondition());
    assertNull(actualUpdateMessage.getId());
    assertNull(actualUpdateMessage.getIfCondition());
    RelationalEvents events = actualUpdateMessage.getEvents();
    assertNull(events.getParentId());
    assertNull(actualUpdateMessage.getContent());
    assertNull(actualUpdateMessage.getMessageId());
    assertNull(actualUpdateMessage.getTemplate());
    assertNull(actualUpdateMessage.getTemplatePath());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(actualUpdateMessage.getSilent());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualUpdateMessage.getVariableProperties().isEmpty());
  }
}
