package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.swadl.v1.activity.message.SendMessage.Attachment;
import com.symphony.bdk.workflow.swadl.v1.activity.message.SendMessage.To;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SendMessageDiffblueTest {
  /**
   * Test Attachment {@link Attachment#equals(Object)}, and {@link Attachment#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Attachment#equals(Object)}
   *   <li>{@link Attachment#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Attachment equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Attachment.equals(Object)", "int Attachment.hashCode()"})
  void testAttachmentEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    Attachment attachment = new Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    Attachment attachment2 = new Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertEquals(attachment, attachment2);
    int expectedHashCodeResult = attachment.hashCode();
    assertEquals(expectedHashCodeResult, attachment2.hashCode());
  }

  /**
   * Test Attachment {@link Attachment#equals(Object)}, and {@link Attachment#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Attachment#equals(Object)}
   *   <li>{@link Attachment#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Attachment equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Attachment.equals(Object)", "int Attachment.hashCode()"})
  void testAttachmentEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    Attachment attachment = new Attachment();
    attachment.setAttachmentId(null);
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    Attachment attachment2 = new Attachment();
    attachment2.setAttachmentId(null);
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertEquals(attachment, attachment2);
    int expectedHashCodeResult = attachment.hashCode();
    assertEquals(expectedHashCodeResult, attachment2.hashCode());
  }

  /**
   * Test Attachment {@link Attachment#equals(Object)}, and {@link Attachment#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Attachment#equals(Object)}
   *   <li>{@link Attachment#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Attachment equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Attachment.equals(Object)", "int Attachment.hashCode()"})
  void testAttachmentEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    Attachment attachment = new Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath(null);
    attachment.setMessageId("42");

    Attachment attachment2 = new Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath(null);
    attachment2.setMessageId("42");

    // Act and Assert
    assertEquals(attachment, attachment2);
    int expectedHashCodeResult = attachment.hashCode();
    assertEquals(expectedHashCodeResult, attachment2.hashCode());
  }

  /**
   * Test Attachment {@link Attachment#equals(Object)}, and {@link Attachment#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Attachment#equals(Object)}
   *   <li>{@link Attachment#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Attachment equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Attachment.equals(Object)", "int Attachment.hashCode()"})
  void testAttachmentEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    Attachment attachment = new Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId(null);

    Attachment attachment2 = new Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId(null);

    // Act and Assert
    assertEquals(attachment, attachment2);
    int expectedHashCodeResult = attachment.hashCode();
    assertEquals(expectedHashCodeResult, attachment2.hashCode());
  }

  /**
   * Test Attachment {@link Attachment#equals(Object)}, and {@link Attachment#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Attachment#equals(Object)}
   *   <li>{@link Attachment#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test Attachment equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Attachment.equals(Object)", "int Attachment.hashCode()"})
  void testAttachmentEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Attachment attachment = new Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    // Act and Assert
    assertEquals(attachment, attachment);
    int expectedHashCodeResult = attachment.hashCode();
    assertEquals(expectedHashCodeResult, attachment.hashCode());
  }

  /**
   * Test Attachment {@link Attachment#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Attachment#equals(Object)}
   */
  @Test
  @DisplayName("Test Attachment equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Attachment.equals(Object)", "int Attachment.hashCode()"})
  void testAttachmentEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Attachment attachment = new Attachment();
    attachment.setAttachmentId("Not all who wander are lost");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    Attachment attachment2 = new Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, attachment2);
  }

  /**
   * Test Attachment {@link Attachment#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Attachment#equals(Object)}
   */
  @Test
  @DisplayName("Test Attachment equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Attachment.equals(Object)", "int Attachment.hashCode()"})
  void testAttachmentEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    Attachment attachment = new Attachment();
    attachment.setAttachmentId(null);
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    Attachment attachment2 = new Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, attachment2);
  }

  /**
   * Test Attachment {@link Attachment#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Attachment#equals(Object)}
   */
  @Test
  @DisplayName("Test Attachment equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Attachment.equals(Object)", "int Attachment.hashCode()"})
  void testAttachmentEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    Attachment attachment = new Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("42");
    attachment.setMessageId("42");

    Attachment attachment2 = new Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, attachment2);
  }

  /**
   * Test Attachment {@link Attachment#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Attachment#equals(Object)}
   */
  @Test
  @DisplayName("Test Attachment equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Attachment.equals(Object)", "int Attachment.hashCode()"})
  void testAttachmentEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    Attachment attachment = new Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath(null);
    attachment.setMessageId("42");

    Attachment attachment2 = new Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, attachment2);
  }

  /**
   * Test Attachment {@link Attachment#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Attachment#equals(Object)}
   */
  @Test
  @DisplayName("Test Attachment equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Attachment.equals(Object)", "int Attachment.hashCode()"})
  void testAttachmentEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    Attachment attachment = new Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("Not all who wander are lost");

    Attachment attachment2 = new Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, attachment2);
  }

  /**
   * Test Attachment {@link Attachment#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Attachment#equals(Object)}
   */
  @Test
  @DisplayName("Test Attachment equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Attachment.equals(Object)", "int Attachment.hashCode()"})
  void testAttachmentEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    Attachment attachment = new Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId(null);

    Attachment attachment2 = new Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, attachment2);
  }

  /**
   * Test Attachment {@link Attachment#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Attachment#equals(Object)}
   */
  @Test
  @DisplayName("Test Attachment equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Attachment.equals(Object)", "int Attachment.hashCode()"})
  void testAttachmentEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Attachment attachment = new Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, null);
  }

  /**
   * Test Attachment {@link Attachment#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link Attachment#equals(Object)}
   */
  @Test
  @DisplayName("Test Attachment equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean Attachment.equals(Object)", "int Attachment.hashCode()"})
  void testAttachmentEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Attachment attachment = new Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, "Different type to Attachment");
  }

  /**
   * Test Attachment getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Attachment}
   *   <li>{@link Attachment#setAttachmentId(String)}
   *   <li>{@link Attachment#setContentPath(String)}
   *   <li>{@link Attachment#setMessageId(String)}
   *   <li>{@link Attachment#toString()}
   *   <li>{@link Attachment#getAttachmentId()}
   *   <li>{@link Attachment#getContentPath()}
   *   <li>{@link Attachment#getMessageId()}
   * </ul>
   */
  @Test
  @DisplayName("Test Attachment getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Attachment.<init>()", "String Attachment.getAttachmentId()",
      "String Attachment.getContentPath()", "String Attachment.getMessageId()",
      "void Attachment.setAttachmentId(String)", "void Attachment.setContentPath(String)",
      "void Attachment.setMessageId(String)", "String Attachment.toString()"})
  void testAttachmentGettersAndSetters() {
    // Arrange and Act
    Attachment actualAttachment = new Attachment();
    actualAttachment.setAttachmentId("42");
    actualAttachment.setContentPath("Not all who wander are lost");
    actualAttachment.setMessageId("42");
    String actualToStringResult = actualAttachment.toString();
    String actualAttachmentId = actualAttachment.getAttachmentId();
    String actualContentPath = actualAttachment.getContentPath();

    // Assert
    assertEquals("42", actualAttachmentId);
    assertEquals("42", actualAttachment.getMessageId());
    assertEquals("Not all who wander are lost", actualContentPath);
    assertEquals("SendMessage.Attachment(messageId=42, attachmentId=42, contentPath=Not all who wander are lost)",
        actualToStringResult);
  }

  /**
   * Test {@link SendMessage#setContent(Object)}.
   * <ul>
   *   <li>Given {@code template}.</li>
   *   <li>Then {@link SendMessage} (default constructor) Template is {@code Not all who wander are lost}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#setContent(Object)}
   */
  @Test
  @DisplayName("Test setContent(Object); given 'template'; then SendMessage (default constructor) Template is 'Not all who wander are lost'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SendMessage.setContent(Object)"})
  void testSetContent_givenTemplate_thenSendMessageTemplateIsNotAllWhoWanderAreLost() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    objectObjectMap.put("template", "Not all who wander are lost");
    objectObjectMap.put("template-path", "Not all who wander are lost");

    // Act
    sendMessage.setContent(objectObjectMap);

    // Assert
    assertEquals("Not all who wander are lost", sendMessage.getTemplate());
    assertEquals("Not all who wander are lost", sendMessage.getTemplatePath());
    assertNull(sendMessage.getContent());
  }

  /**
   * Test {@link SendMessage#setContent(Object)}.
   * <ul>
   *   <li>When {@code Content}.</li>
   *   <li>Then {@link SendMessage} (default constructor) Content is {@code Content}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#setContent(Object)}
   */
  @Test
  @DisplayName("Test setContent(Object); when 'Content'; then SendMessage (default constructor) Content is 'Content'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SendMessage.setContent(Object)"})
  void testSetContent_whenContent_thenSendMessageContentIsContent() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    // Act
    sendMessage.setContent("Content");

    // Assert
    assertEquals("Content", sendMessage.getContent());
    assertNull(sendMessage.getTemplate());
    assertNull(sendMessage.getTemplatePath());
  }

  /**
   * Test {@link SendMessage#setContent(Object)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then {@link SendMessage} (default constructor) Content is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#setContent(Object)}
   */
  @Test
  @DisplayName("Test setContent(Object); when one; then SendMessage (default constructor) Content is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SendMessage.setContent(Object)"})
  void testSetContent_whenOne_thenSendMessageContentIsNull() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    // Act
    sendMessage.setContent(1);

    // Assert that nothing has changed
    assertNull(sendMessage.getContent());
    assertNull(sendMessage.getTemplate());
    assertNull(sendMessage.getTemplatePath());
  }

  /**
   * Test {@link SendMessage#equals(Object)}, and {@link SendMessage#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SendMessage#equals(Object)}
   *   <li>{@link SendMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    SendMessage sendMessage2 = new SendMessage();

    // Act and Assert
    assertEquals(sendMessage, sendMessage2);
    int expectedHashCodeResult = sendMessage.hashCode();
    assertEquals(expectedHashCodeResult, sendMessage2.hashCode());
  }

  /**
   * Test {@link SendMessage#equals(Object)}, and {@link SendMessage#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link SendMessage#equals(Object)}
   *   <li>{@link SendMessage#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    // Act and Assert
    assertEquals(sendMessage, sendMessage);
    int expectedHashCodeResult = sendMessage.hashCode();
    assertEquals(expectedHashCodeResult, sendMessage.hashCode());
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    sendMessage.add("Key", "Value");

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    sendMessage.setContent("Content");

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    sendMessage.setTemplate("Template");

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    sendMessage.setTemplatePath("Template Path");

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    To resultTo = new To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    SendMessage sendMessage = new SendMessage();
    sendMessage.setTo(resultTo);

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    sendMessage.setAttachments(new ArrayList<>());

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    sendMessage.setData("Data");

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    SendMessage sendMessage2 = new SendMessage();
    sendMessage2.setContent("Content");

    // Act and Assert
    assertNotEquals(sendMessage, sendMessage2);
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    SendMessage sendMessage2 = new SendMessage();
    sendMessage2.setTemplate("Template");

    // Act and Assert
    assertNotEquals(sendMessage, sendMessage2);
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    SendMessage sendMessage2 = new SendMessage();
    sendMessage2.setTemplatePath("Template Path");

    // Act and Assert
    assertNotEquals(sendMessage, sendMessage2);
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    To resultTo = new To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    SendMessage sendMessage2 = new SendMessage();
    sendMessage2.setTo(resultTo);

    // Act and Assert
    assertNotEquals(sendMessage, sendMessage2);
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    SendMessage sendMessage2 = new SendMessage();
    sendMessage2.setAttachments(new ArrayList<>());

    // Act and Assert
    assertNotEquals(sendMessage, sendMessage2);
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    SendMessage sendMessage2 = new SendMessage();
    sendMessage2.setData("Data");

    // Act and Assert
    assertNotEquals(sendMessage, sendMessage2);
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SendMessage(), null);
  }

  /**
   * Test {@link SendMessage#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean SendMessage.equals(Object)", "int SendMessage.hashCode()"})
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SendMessage(), "Different type to SendMessage");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SendMessage}
   *   <li>{@link SendMessage#setAttachments(List)}
   *   <li>{@link SendMessage#setData(String)}
   *   <li>{@link SendMessage#setTemplate(String)}
   *   <li>{@link SendMessage#setTemplatePath(String)}
   *   <li>{@link SendMessage#setTo(To)}
   *   <li>{@link SendMessage#toString()}
   *   <li>{@link SendMessage#getAttachments()}
   *   <li>{@link SendMessage#getContent()}
   *   <li>{@link SendMessage#getData()}
   *   <li>{@link SendMessage#getTemplate()}
   *   <li>{@link SendMessage#getTemplatePath()}
   *   <li>{@link SendMessage#getTo()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void SendMessage.<init>()", "List SendMessage.getAttachments()",
      "String SendMessage.getContent()", "String SendMessage.getData()", "String SendMessage.getTemplate()",
      "String SendMessage.getTemplatePath()", "To SendMessage.getTo()", "void SendMessage.setAttachments(List)",
      "void SendMessage.setData(String)", "void SendMessage.setTemplate(String)",
      "void SendMessage.setTemplatePath(String)", "void SendMessage.setTo(To)", "String SendMessage.toString()"})
  void testGettersAndSetters() {
    // Arrange and Act
    SendMessage actualSendMessage = new SendMessage();
    ArrayList<Attachment> attachments = new ArrayList<>();
    actualSendMessage.setAttachments(attachments);
    actualSendMessage.setData("Data");
    actualSendMessage.setTemplate("Template");
    actualSendMessage.setTemplatePath("Template Path");
    To resultTo = new To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());
    actualSendMessage.setTo(resultTo);
    String actualToStringResult = actualSendMessage.toString();
    List<Attachment> actualAttachments = actualSendMessage.getAttachments();
    String actualContent = actualSendMessage.getContent();
    String actualData = actualSendMessage.getData();
    String actualTemplate = actualSendMessage.getTemplate();
    String actualTemplatePath = actualSendMessage.getTemplatePath();
    To actualTo = actualSendMessage.getTo();

    // Assert
    assertEquals("Data", actualData);
    assertEquals(
        "SendMessage(template=Template, templatePath=Template Path, content=null, to=SendMessage.To(streamId=42,"
            + " streamIds=[], userIds=[]), attachments=[], data=Data)",
        actualToStringResult);
    assertEquals("Template Path", actualTemplatePath);
    assertEquals("Template", actualTemplate);
    assertNull(actualSendMessage.getOn());
    assertNull(actualSendMessage.getObo());
    assertNull(actualSendMessage.getElseCondition());
    assertNull(actualSendMessage.getId());
    assertNull(actualSendMessage.getIfCondition());
    assertNull(actualContent);
    assertTrue(actualAttachments.isEmpty());
    assertTrue(actualSendMessage.getVariableProperties().isEmpty());
    assertSame(resultTo, actualTo);
    assertSame(attachments, actualAttachments);
  }

  /**
   * Test To {@link To#equals(Object)}, and {@link To#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link To#equals(Object)}
   *   <li>{@link To#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test To equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean To.equals(Object)", "int To.hashCode()"})
  void testToEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    To resultTo = new To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    To resultTo2 = new To();
    resultTo2.setStreamId("42");
    resultTo2.setStreamIds(new ArrayList<>());
    resultTo2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(resultTo, resultTo2);
    int expectedHashCodeResult = resultTo.hashCode();
    assertEquals(expectedHashCodeResult, resultTo2.hashCode());
  }

  /**
   * Test To {@link To#equals(Object)}, and {@link To#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link To#equals(Object)}
   *   <li>{@link To#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test To equals(Object), and hashCode(); when other is equal; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean To.equals(Object)", "int To.hashCode()"})
  void testToEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    To resultTo = new To();
    resultTo.setStreamId(null);
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    To resultTo2 = new To();
    resultTo2.setStreamId(null);
    resultTo2.setStreamIds(new ArrayList<>());
    resultTo2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(resultTo, resultTo2);
    int expectedHashCodeResult = resultTo.hashCode();
    assertEquals(expectedHashCodeResult, resultTo2.hashCode());
  }

  /**
   * Test To {@link To#equals(Object)}, and {@link To#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link To#equals(Object)}
   *   <li>{@link To#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test To equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean To.equals(Object)", "int To.hashCode()"})
  void testToEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    To resultTo = new To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(resultTo, resultTo);
    int expectedHashCodeResult = resultTo.hashCode();
    assertEquals(expectedHashCodeResult, resultTo.hashCode());
  }

  /**
   * Test To {@link To#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link To#equals(Object)}
   */
  @Test
  @DisplayName("Test To equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean To.equals(Object)", "int To.hashCode()"})
  void testToEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    To resultTo = new To();
    resultTo.setStreamId("Stream Id");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    To resultTo2 = new To();
    resultTo2.setStreamId("42");
    resultTo2.setStreamIds(new ArrayList<>());
    resultTo2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(resultTo, resultTo2);
  }

  /**
   * Test To {@link To#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link To#equals(Object)}
   */
  @Test
  @DisplayName("Test To equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean To.equals(Object)", "int To.hashCode()"})
  void testToEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    To resultTo = new To();
    resultTo.setStreamId(null);
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    To resultTo2 = new To();
    resultTo2.setStreamId("42");
    resultTo2.setStreamIds(new ArrayList<>());
    resultTo2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(resultTo, resultTo2);
  }

  /**
   * Test To {@link To#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link To#equals(Object)}
   */
  @Test
  @DisplayName("Test To equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean To.equals(Object)", "int To.hashCode()"})
  void testToEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ArrayList<String> streamIds = new ArrayList<>();
    streamIds.add("42");

    To resultTo = new To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(streamIds);
    resultTo.setUserIds(new ArrayList<>());

    To resultTo2 = new To();
    resultTo2.setStreamId("42");
    resultTo2.setStreamIds(new ArrayList<>());
    resultTo2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(resultTo, resultTo2);
  }

  /**
   * Test To {@link To#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link To#equals(Object)}
   */
  @Test
  @DisplayName("Test To equals(Object); when other is different; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean To.equals(Object)", "int To.hashCode()"})
  void testToEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ArrayList<Long> userIds = new ArrayList<>();
    userIds.add(1L);

    To resultTo = new To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(userIds);

    To resultTo2 = new To();
    resultTo2.setStreamId("42");
    resultTo2.setStreamIds(new ArrayList<>());
    resultTo2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(resultTo, resultTo2);
  }

  /**
   * Test To {@link To#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link To#equals(Object)}
   */
  @Test
  @DisplayName("Test To equals(Object); when other is 'null'; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean To.equals(Object)", "int To.hashCode()"})
  void testToEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    To resultTo = new To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(resultTo, null);
  }

  /**
   * Test To {@link To#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link To#equals(Object)}
   */
  @Test
  @DisplayName("Test To equals(Object); when other is wrong type; then return not equal")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"boolean To.equals(Object)", "int To.hashCode()"})
  void testToEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    To resultTo = new To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(resultTo, "Different type to To");
  }

  /**
   * Test To getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link To}
   *   <li>{@link To#setStreamId(String)}
   *   <li>{@link To#setStreamIds(List)}
   *   <li>{@link To#setUserIds(List)}
   *   <li>{@link To#toString()}
   *   <li>{@link To#getStreamId()}
   *   <li>{@link To#getStreamIds()}
   *   <li>{@link To#getUserIds()}
   * </ul>
   */
  @Test
  @DisplayName("Test To getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void To.<init>()", "String To.getStreamId()", "List To.getStreamIds()", "List To.getUserIds()",
      "void To.setStreamId(String)", "void To.setStreamIds(List)", "void To.setUserIds(List)", "String To.toString()"})
  void testToGettersAndSetters() {
    // Arrange and Act
    To actualResultTo = new To();
    actualResultTo.setStreamId("42");
    ArrayList<String> streamIds = new ArrayList<>();
    actualResultTo.setStreamIds(streamIds);
    ArrayList<Long> userIds = new ArrayList<>();
    actualResultTo.setUserIds(userIds);
    String actualToStringResult = actualResultTo.toString();
    String actualStreamId = actualResultTo.getStreamId();
    List<String> actualStreamIds = actualResultTo.getStreamIds();
    List<Long> actualUserIds = actualResultTo.getUserIds();

    // Assert
    assertEquals("42", actualStreamId);
    assertEquals("SendMessage.To(streamId=42, streamIds=[], userIds=[])", actualToStringResult);
    assertTrue(actualStreamIds.isEmpty());
    assertTrue(actualUserIds.isEmpty());
    assertSame(streamIds, actualStreamIds);
    assertSame(userIds, actualUserIds);
  }
}
