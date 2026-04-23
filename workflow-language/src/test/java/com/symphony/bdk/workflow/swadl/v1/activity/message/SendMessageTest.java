package com.symphony.bdk.workflow.swadl.v1.activity.message;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SendMessageTest {

  @Test
  void shouldSetTemplateAndTemplatePathWhenContentIsMap() {
    SendMessage sendMessage = new SendMessage();
    Map<String, String> map = Map.of("template", "myTemplate", "template-path", "/path/to/template");

    sendMessage.setContent(map);

    assertEquals("myTemplate", sendMessage.getTemplate());
    assertEquals("/path/to/template", sendMessage.getTemplatePath());
    assertNull(sendMessage.getContent());
  }

  @Test
  void shouldSetContentWhenContentIsString() {
    SendMessage sendMessage = new SendMessage();

    sendMessage.setContent("hello world");

    assertEquals("hello world", sendMessage.getContent());
    assertNull(sendMessage.getTemplate());
    assertNull(sendMessage.getTemplatePath());
  }

  @Test
  void shouldDoNothingWhenContentIsNeitherMapNorString() {
    SendMessage sendMessage = new SendMessage();

    sendMessage.setContent(42);

    assertNull(sendMessage.getContent());
    assertNull(sendMessage.getTemplate());
    assertNull(sendMessage.getTemplatePath());
  }
}
