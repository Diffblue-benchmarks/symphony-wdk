package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SendMessageDiffblueTest {
  /**
   * Test {@link SendMessage#setContent(Object)}.
   *
   * <ul>
   *   <li>Given {@code template}.
   *   <li>Then {@link SendMessage} (default constructor) Template is {@code Not all who wander are
   *       lost}.
   * </ul>
   *
   * <p>Method under test: {@link SendMessage#setContent(Object)}
   */
  @Test
  @DisplayName(
      "Test setContent(Object); given 'template'; then SendMessage (default constructor) Template is 'Not all who wander are lost'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>When {@code Content}.
   *   <li>Then {@link SendMessage} (default constructor) Content is {@code Content}.
   * </ul>
   *
   * <p>Method under test: {@link SendMessage#setContent(Object)}
   */
  @Test
  @DisplayName(
      "Test setContent(Object); when 'Content'; then SendMessage (default constructor) Content is 'Content'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
   *
   * <ul>
   *   <li>When one.
   *   <li>Then {@link SendMessage} (default constructor) Content is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SendMessage#setContent(Object)}
   */
  @Test
  @DisplayName(
      "Test setContent(Object); when one; then SendMessage (default constructor) Content is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
}
