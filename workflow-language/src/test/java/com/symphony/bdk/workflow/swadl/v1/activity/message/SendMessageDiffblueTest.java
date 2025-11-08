package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.Test;

class SendMessageDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SendMessage.Attachment#equals(Object)}
   *   <li>{@link SendMessage.Attachment#hashCode()}
   * </ul>
   */
  @Test
  void testAttachmentEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    SendMessage.Attachment attachment2 = new SendMessage.Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertEquals(attachment, attachment2);
    int expectedHashCodeResult = attachment.hashCode();
    assertEquals(expectedHashCodeResult, attachment2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SendMessage.Attachment#equals(Object)}
   *   <li>{@link SendMessage.Attachment#hashCode()}
   * </ul>
   */
  @Test
  void testAttachmentEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setAttachmentId(null);
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    SendMessage.Attachment attachment2 = new SendMessage.Attachment();
    attachment2.setAttachmentId(null);
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertEquals(attachment, attachment2);
    int expectedHashCodeResult = attachment.hashCode();
    assertEquals(expectedHashCodeResult, attachment2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SendMessage.Attachment#equals(Object)}
   *   <li>{@link SendMessage.Attachment#hashCode()}
   * </ul>
   */
  @Test
  void testAttachmentEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath(null);
    attachment.setMessageId("42");

    SendMessage.Attachment attachment2 = new SendMessage.Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath(null);
    attachment2.setMessageId("42");

    // Act and Assert
    assertEquals(attachment, attachment2);
    int expectedHashCodeResult = attachment.hashCode();
    assertEquals(expectedHashCodeResult, attachment2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SendMessage.Attachment#equals(Object)}
   *   <li>{@link SendMessage.Attachment#hashCode()}
   * </ul>
   */
  @Test
  void testAttachmentEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId(null);

    SendMessage.Attachment attachment2 = new SendMessage.Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId(null);

    // Act and Assert
    assertEquals(attachment, attachment2);
    int expectedHashCodeResult = attachment.hashCode();
    assertEquals(expectedHashCodeResult, attachment2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SendMessage.Attachment#equals(Object)}
   *   <li>{@link SendMessage.Attachment#hashCode()}
   * </ul>
   */
  @Test
  void testAttachmentEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    // Act and Assert
    assertEquals(attachment, attachment);
    int expectedHashCodeResult = attachment.hashCode();
    assertEquals(expectedHashCodeResult, attachment.hashCode());
  }

  /**
   * Method under test: {@link SendMessage.Attachment#equals(Object)}
   */
  @Test
  void testAttachmentEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setAttachmentId("Not all who wander are lost");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    SendMessage.Attachment attachment2 = new SendMessage.Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, attachment2);
  }

  /**
   * Method under test: {@link SendMessage.Attachment#equals(Object)}
   */
  @Test
  void testAttachmentEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setAttachmentId(null);
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    SendMessage.Attachment attachment2 = new SendMessage.Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, attachment2);
  }

  /**
   * Method under test: {@link SendMessage.Attachment#equals(Object)}
   */
  @Test
  void testAttachmentEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("42");
    attachment.setMessageId("42");

    SendMessage.Attachment attachment2 = new SendMessage.Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, attachment2);
  }

  /**
   * Method under test: {@link SendMessage.Attachment#equals(Object)}
   */
  @Test
  void testAttachmentEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath(null);
    attachment.setMessageId("42");

    SendMessage.Attachment attachment2 = new SendMessage.Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, attachment2);
  }

  /**
   * Method under test: {@link SendMessage.Attachment#equals(Object)}
   */
  @Test
  void testAttachmentEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("Not all who wander are lost");

    SendMessage.Attachment attachment2 = new SendMessage.Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, attachment2);
  }

  /**
   * Method under test: {@link SendMessage.Attachment#equals(Object)}
   */
  @Test
  void testAttachmentEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId(null);

    SendMessage.Attachment attachment2 = new SendMessage.Attachment();
    attachment2.setAttachmentId("42");
    attachment2.setContentPath("Not all who wander are lost");
    attachment2.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, attachment2);
  }

  /**
   * Method under test: {@link SendMessage.Attachment#equals(Object)}
   */
  @Test
  void testAttachmentEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, null);
  }

  /**
   * Method under test: {@link SendMessage.Attachment#equals(Object)}
   */
  @Test
  void testAttachmentEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setAttachmentId("42");
    attachment.setContentPath("Not all who wander are lost");
    attachment.setMessageId("42");

    // Act and Assert
    assertNotEquals(attachment, "Different type to Attachment");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SendMessage.Attachment}
   *   <li>{@link SendMessage.Attachment#setAttachmentId(String)}
   *   <li>{@link SendMessage.Attachment#setContentPath(String)}
   *   <li>{@link SendMessage.Attachment#setMessageId(String)}
   *   <li>{@link SendMessage.Attachment#toString()}
   *   <li>{@link SendMessage.Attachment#getAttachmentId()}
   *   <li>{@link SendMessage.Attachment#getContentPath()}
   *   <li>{@link SendMessage.Attachment#getMessageId()}
   * </ul>
   */
  @Test
  void testAttachmentGettersAndSetters() {
    // Arrange and Act
    SendMessage.Attachment actualAttachment = new SendMessage.Attachment();
    actualAttachment.setAttachmentId("42");
    actualAttachment.setContentPath("Not all who wander are lost");
    actualAttachment.setMessageId("42");
    String actualToStringResult = actualAttachment.toString();
    String actualAttachmentId = actualAttachment.getAttachmentId();
    String actualContentPath = actualAttachment.getContentPath();

    // Assert that nothing has changed
    assertEquals("42", actualAttachmentId);
    assertEquals("42", actualAttachment.getMessageId());
    assertEquals("Not all who wander are lost", actualContentPath);
    assertEquals("SendMessage.Attachment(messageId=42, attachmentId=42, contentPath=Not all who wander are lost)",
        actualToStringResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SendMessage#equals(Object)}
   *   <li>{@link SendMessage#hashCode()}
   * </ul>
   */
  @Test
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
   * Method under test: {@link SendMessage#setContent(Object)}
   */
  @Test
  void testSetContent() {
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
   * Method under test: {@link SendMessage#setContent(Object)}
   */
  @Test
  void testSetContent2() {
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
   * Method under test: {@link SendMessage#setContent(Object)}
   */
  @Test
  void testSetContent3() {
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
   * Methods under test:
   * <ul>
   *   <li>{@link SendMessage#equals(Object)}
   *   <li>{@link SendMessage#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    // Act and Assert
    assertEquals(sendMessage, sendMessage);
    int expectedHashCodeResult = sendMessage.hashCode();
    assertEquals(expectedHashCodeResult, sendMessage.hashCode());
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    sendMessage.add("Key", "Value");

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    sendMessage.add("Key", mock(PinMessage.class));

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    sendMessage.setContent("Content");

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    sendMessage.setTemplate("Template");

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    sendMessage.setTemplatePath("Template Path");

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    SendMessage sendMessage = new SendMessage();
    sendMessage.setTo(resultTo);

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    sendMessage.setAttachments(new ArrayList<>());

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    SendMessage sendMessage = new SendMessage();
    sendMessage.setData("Data");

    // Act and Assert
    assertNotEquals(sendMessage, new SendMessage());
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    SendMessage sendMessage2 = new SendMessage();
    sendMessage2.setContent("Content");

    // Act and Assert
    assertNotEquals(sendMessage, sendMessage2);
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    SendMessage sendMessage2 = new SendMessage();
    sendMessage2.setTemplate("Template");

    // Act and Assert
    assertNotEquals(sendMessage, sendMessage2);
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    SendMessage sendMessage2 = new SendMessage();
    sendMessage2.setTemplatePath("Template Path");

    // Act and Assert
    assertNotEquals(sendMessage, sendMessage2);
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    SendMessage sendMessage2 = new SendMessage();
    sendMessage2.setTo(resultTo);

    // Act and Assert
    assertNotEquals(sendMessage, sendMessage2);
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    SendMessage sendMessage2 = new SendMessage();
    sendMessage2.setAttachments(new ArrayList<>());

    // Act and Assert
    assertNotEquals(sendMessage, sendMessage2);
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    SendMessage sendMessage = new SendMessage();

    SendMessage sendMessage2 = new SendMessage();
    sendMessage2.setData("Data");

    // Act and Assert
    assertNotEquals(sendMessage, sendMessage2);
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SendMessage(), null);
  }

  /**
   * Method under test: {@link SendMessage#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new SendMessage(), "Different type to SendMessage");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SendMessage}
   *   <li>{@link SendMessage#setAttachments(List)}
   *   <li>{@link SendMessage#setData(String)}
   *   <li>{@link SendMessage#setTemplate(String)}
   *   <li>{@link SendMessage#setTemplatePath(String)}
   *   <li>{@link SendMessage#setTo(SendMessage.To)}
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
  void testGettersAndSetters() {
    // Arrange and Act
    SendMessage actualSendMessage = new SendMessage();
    ArrayList<SendMessage.Attachment> attachments = new ArrayList<>();
    actualSendMessage.setAttachments(attachments);
    actualSendMessage.setData("Data");
    actualSendMessage.setTemplate("Template");
    actualSendMessage.setTemplatePath("Template Path");
    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());
    actualSendMessage.setTo(resultTo);
    String actualToStringResult = actualSendMessage.toString();
    List<SendMessage.Attachment> actualAttachments = actualSendMessage.getAttachments();
    actualSendMessage.getContent();
    String actualData = actualSendMessage.getData();
    String actualTemplate = actualSendMessage.getTemplate();
    String actualTemplatePath = actualSendMessage.getTemplatePath();
    SendMessage.To actualTo = actualSendMessage.getTo();

    // Assert that nothing has changed
    assertEquals("Data", actualData);
    assertEquals(
        "SendMessage(template=Template, templatePath=Template Path, content=null, to=SendMessage.To(streamId=42,"
            + " streamIds=[], userIds=[]), attachments=[], data=Data)",
        actualToStringResult);
    assertEquals("Template Path", actualTemplatePath);
    assertEquals("Template", actualTemplate);
    assertTrue(actualAttachments.isEmpty());
    assertTrue(actualSendMessage.getVariableProperties().isEmpty());
    assertSame(resultTo, actualTo);
    assertSame(attachments, actualAttachments);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SendMessage.To#equals(Object)}
   *   <li>{@link SendMessage.To#hashCode()}
   * </ul>
   */
  @Test
  void testToEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    SendMessage.To resultTo2 = new SendMessage.To();
    resultTo2.setStreamId("42");
    resultTo2.setStreamIds(new ArrayList<>());
    resultTo2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(resultTo, resultTo2);
    int expectedHashCodeResult = resultTo.hashCode();
    assertEquals(expectedHashCodeResult, resultTo2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SendMessage.To#equals(Object)}
   *   <li>{@link SendMessage.To#hashCode()}
   * </ul>
   */
  @Test
  void testToEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId(null);
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    SendMessage.To resultTo2 = new SendMessage.To();
    resultTo2.setStreamId(null);
    resultTo2.setStreamIds(new ArrayList<>());
    resultTo2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(resultTo, resultTo2);
    int expectedHashCodeResult = resultTo.hashCode();
    assertEquals(expectedHashCodeResult, resultTo2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link SendMessage.To#equals(Object)}
   *   <li>{@link SendMessage.To#hashCode()}
   * </ul>
   */
  @Test
  void testToEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    // Act and Assert
    assertEquals(resultTo, resultTo);
    int expectedHashCodeResult = resultTo.hashCode();
    assertEquals(expectedHashCodeResult, resultTo.hashCode());
  }

  /**
   * Method under test: {@link SendMessage.To#equals(Object)}
   */
  @Test
  void testToEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId("Stream Id");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    SendMessage.To resultTo2 = new SendMessage.To();
    resultTo2.setStreamId("42");
    resultTo2.setStreamIds(new ArrayList<>());
    resultTo2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(resultTo, resultTo2);
  }

  /**
   * Method under test: {@link SendMessage.To#equals(Object)}
   */
  @Test
  void testToEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId(null);
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    SendMessage.To resultTo2 = new SendMessage.To();
    resultTo2.setStreamId("42");
    resultTo2.setStreamIds(new ArrayList<>());
    resultTo2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(resultTo, resultTo2);
  }

  /**
   * Method under test: {@link SendMessage.To#equals(Object)}
   */
  @Test
  void testToEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ArrayList<String> streamIds = new ArrayList<>();
    streamIds.add("42");

    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(streamIds);
    resultTo.setUserIds(new ArrayList<>());

    SendMessage.To resultTo2 = new SendMessage.To();
    resultTo2.setStreamId("42");
    resultTo2.setStreamIds(new ArrayList<>());
    resultTo2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(resultTo, resultTo2);
  }

  /**
   * Method under test: {@link SendMessage.To#equals(Object)}
   */
  @Test
  void testToEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ArrayList<Long> userIds = new ArrayList<>();
    userIds.add(1L);

    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(userIds);

    SendMessage.To resultTo2 = new SendMessage.To();
    resultTo2.setStreamId("42");
    resultTo2.setStreamIds(new ArrayList<>());
    resultTo2.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(resultTo, resultTo2);
  }

  /**
   * Method under test: {@link SendMessage.To#equals(Object)}
   */
  @Test
  void testToEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(resultTo, null);
  }

  /**
   * Method under test: {@link SendMessage.To#equals(Object)}
   */
  @Test
  void testToEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    // Act and Assert
    assertNotEquals(resultTo, "Different type to To");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SendMessage.To}
   *   <li>{@link SendMessage.To#setStreamId(String)}
   *   <li>{@link SendMessage.To#setStreamIds(List)}
   *   <li>{@link SendMessage.To#setUserIds(List)}
   *   <li>{@link SendMessage.To#toString()}
   *   <li>{@link SendMessage.To#getStreamId()}
   *   <li>{@link SendMessage.To#getStreamIds()}
   *   <li>{@link SendMessage.To#getUserIds()}
   * </ul>
   */
  @Test
  void testToGettersAndSetters() {
    // Arrange and Act
    SendMessage.To actualResultTo = new SendMessage.To();
    actualResultTo.setStreamId("42");
    ArrayList<String> streamIds = new ArrayList<>();
    actualResultTo.setStreamIds(streamIds);
    ArrayList<Long> userIds = new ArrayList<>();
    actualResultTo.setUserIds(userIds);
    String actualToStringResult = actualResultTo.toString();
    String actualStreamId = actualResultTo.getStreamId();
    List<String> actualStreamIds = actualResultTo.getStreamIds();
    List<Long> actualUserIds = actualResultTo.getUserIds();

    // Assert that nothing has changed
    assertEquals("42", actualStreamId);
    assertEquals("SendMessage.To(streamId=42, streamIds=[], userIds=[])", actualToStringResult);
    assertTrue(actualStreamIds.isEmpty());
    assertTrue(actualUserIds.isEmpty());
    assertSame(streamIds, actualStreamIds);
    assertSame(userIds, actualUserIds);
  }
}
