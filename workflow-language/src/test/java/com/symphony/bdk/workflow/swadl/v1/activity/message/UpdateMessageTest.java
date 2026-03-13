package com.symphony.bdk.workflow.swadl.v1.activity.message;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UpdateMessageTest {

  @Test
  void setContent_withMap_setsTemplateAndTemplatePath() {
    UpdateMessage updateMessage = new UpdateMessage();
    Map<String, String> contentMap = new HashMap<>();
    contentMap.put("template", "testTemplate");
    contentMap.put("template-path", "testPath");

    updateMessage.setContent(contentMap);

    assertEquals("testTemplate", updateMessage.getTemplate());
    assertEquals("testPath", updateMessage.getTemplatePath());
    assertNull(updateMessage.getContent());
  }

  @Test
  void setContent_withMapMissingKeys_setsNullValues() {
    UpdateMessage updateMessage = new UpdateMessage();
    Map<String, String> contentMap = new HashMap<>();

    updateMessage.setContent(contentMap);

    assertNull(updateMessage.getTemplate());
    assertNull(updateMessage.getTemplatePath());
    assertNull(updateMessage.getContent());
  }

  @Test
  void setContent_withString_setsContentField() {
    UpdateMessage updateMessage = new UpdateMessage();
    String contentString = "test content";

    updateMessage.setContent(contentString);

    assertEquals("test content", updateMessage.getContent());
    assertNull(updateMessage.getTemplate());
    assertNull(updateMessage.getTemplatePath());
  }

  @Test
  void setContent_withNull_doesNothing() {
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setTemplate("existingTemplate");
    updateMessage.setTemplatePath("existingPath");
    updateMessage.setContent("existingContent");

    updateMessage.setContent(null);

    assertEquals("existingTemplate", updateMessage.getTemplate());
    assertEquals("existingPath", updateMessage.getTemplatePath());
    assertEquals("existingContent", updateMessage.getContent());
  }

  @Test
  void setContent_withOtherType_doesNothing() {
    UpdateMessage updateMessage = new UpdateMessage();
    updateMessage.setTemplate("existingTemplate");
    updateMessage.setTemplatePath("existingPath");
    updateMessage.setContent("existingContent");

    updateMessage.setContent(123);

    assertEquals("existingTemplate", updateMessage.getTemplate());
    assertEquals("existingPath", updateMessage.getTemplatePath());
    assertEquals("existingContent", updateMessage.getContent());
  }
}
