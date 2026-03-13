package com.symphony.bdk.workflow.swadl.v1.activity.message;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SendMessageTest {

    @Test
    void setContentWithMapShouldSetTemplateAndTemplatePath() {
        // Arrange
        SendMessage sendMessage = new SendMessage();
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("template", "testTemplate");
        contentMap.put("template-path", "testTemplatePath");

        // Act
        sendMessage.setContent(contentMap);

        // Assert
        assertEquals("testTemplate", sendMessage.getTemplate());
        assertEquals("testTemplatePath", sendMessage.getTemplatePath());
        assertNull(sendMessage.getContent());
    }

    @Test
    void setContentWithMapContainingOnlyTemplateShouldSetTemplateOnly() {
        // Arrange
        SendMessage sendMessage = new SendMessage();
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("template", "testTemplate");

        // Act
        sendMessage.setContent(contentMap);

        // Assert
        assertEquals("testTemplate", sendMessage.getTemplate());
        assertNull(sendMessage.getTemplatePath());
        assertNull(sendMessage.getContent());
    }

    @Test
    void setContentWithMapContainingOnlyTemplatePathShouldSetTemplatePathOnly() {
        // Arrange
        SendMessage sendMessage = new SendMessage();
        Map<String, String> contentMap = new HashMap<>();
        contentMap.put("template-path", "testTemplatePath");

        // Act
        sendMessage.setContent(contentMap);

        // Assert
        assertNull(sendMessage.getTemplate());
        assertEquals("testTemplatePath", sendMessage.getTemplatePath());
        assertNull(sendMessage.getContent());
    }

    @Test
    void setContentWithStringShouldSetContent() {
        // Arrange
        SendMessage sendMessage = new SendMessage();
        String content = "testContent";

        // Act
        sendMessage.setContent(content);

        // Assert
        assertEquals("testContent", sendMessage.getContent());
        assertNull(sendMessage.getTemplate());
        assertNull(sendMessage.getTemplatePath());
    }

    @Test
    void setContentWithNullShouldDoNothing() {
        // Arrange
        SendMessage sendMessage = new SendMessage();
        sendMessage.setTemplate("existingTemplate");
        sendMessage.setTemplatePath("existingTemplatePath");
        sendMessage.setContent("existingContent");

        // Act
        sendMessage.setContent(null);

        // Assert
        assertEquals("existingTemplate", sendMessage.getTemplate());
        assertEquals("existingTemplatePath", sendMessage.getTemplatePath());
        assertEquals("existingContent", sendMessage.getContent());
    }

    @Test
    void setContentWithUnsupportedTypeShouldDoNothing() {
        // Arrange
        SendMessage sendMessage = new SendMessage();
        sendMessage.setTemplate("existingTemplate");
        sendMessage.setTemplatePath("existingTemplatePath");
        sendMessage.setContent("existingContent");
        Integer unsupportedContent = 123;

        // Act
        sendMessage.setContent(unsupportedContent);

        // Assert
        assertEquals("existingTemplate", sendMessage.getTemplate());
        assertEquals("existingTemplatePath", sendMessage.getTemplatePath());
        assertEquals("existingContent", sendMessage.getContent());
    }
}
