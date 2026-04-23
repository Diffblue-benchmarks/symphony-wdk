package com.symphony.bdk.workflow.swadl.v1.activity.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import java.util.Map;

class UpdateMessageTest {

  @Test
  void shouldSetTemplateAndTemplatePathWhenContentIsMap() {
    // given
    UpdateMessage updateMessage = new UpdateMessage();
    Map<String, String> content = Map.of("template", "myTemplate", "template-path", "/path/to/template");

    // when
    updateMessage.setContent(content);

    // then
    assertEquals("myTemplate", updateMessage.getTemplate());
    assertEquals("/path/to/template", updateMessage.getTemplatePath());
    assertNull(updateMessage.getContent());
  }

  @Test
  void shouldSetOnlyTemplateWhenContentIsMapWithTemplateOnly() {
    // given
    UpdateMessage updateMessage = new UpdateMessage();
    Map<String, String> content = Map.of("template", "myTemplate");

    // when
    updateMessage.setContent(content);

    // then
    assertEquals("myTemplate", updateMessage.getTemplate());
    assertNull(updateMessage.getTemplatePath());
  }

  @Test
  void shouldSetContentWhenContentIsString() {
    // given
    UpdateMessage updateMessage = new UpdateMessage();
    String content = "Hello World";

    // when
    updateMessage.setContent(content);

    // then
    assertEquals("Hello World", updateMessage.getContent());
    assertNull(updateMessage.getTemplate());
    assertNull(updateMessage.getTemplatePath());
  }

  @Test
  void shouldNotSetAnythingWhenContentIsNeitherMapNorString() {
    // given
    UpdateMessage updateMessage = new UpdateMessage();

    // when
    updateMessage.setContent(42);

    // then
    assertNull(updateMessage.getContent());
    assertNull(updateMessage.getTemplate());
    assertNull(updateMessage.getTemplatePath());
  }
}
