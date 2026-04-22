package com.symphony.bdk.workflow.engine.executor.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class TemplateContentExtractorDiffblueTest {
  /**
   * Test {@link TemplateContentExtractor#extractContent(ActivityExecutorContext, String, String,
   * String)}.
   *
   * <p>Method under test: {@link TemplateContentExtractor#extractContent(ActivityExecutorContext,
   * String, String, String)}
   */
  @Test
  @DisplayName("Test extractContent(ActivityExecutorContext, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String TemplateContentExtractor.extractContent(ActivityExecutorContext, String, String, String)"
  })
  void testExtractContent() throws IOException {
    // Arrange and Act
    String actualExtractContentResult =
        TemplateContentExtractor.extractContent(
            mock(ActivityExecutorContext.class),
            "Not all who wander are lost",
            "Template Path",
            "Template");

    // Assert
    assertEquals("Not all who wander are lost", actualExtractContentResult);
  }

  /**
   * Test {@link TemplateContentExtractor#extractContent(ActivityExecutorContext, String, String,
   * String)} when content is null and templatePath is provided.
   */
  @Test
  @DisplayName("Test extractContent with null content and non-null templatePath")
  void testExtractContent_nullContentWithTemplatePath() throws IOException {
    // Arrange
    ActivityExecutorContext<?> execution = mock(ActivityExecutorContext.class, RETURNS_DEEP_STUBS);
    when(execution.getVariables()).thenReturn(new HashMap<>());
    File mockFile = mock(File.class);
    when(mockFile.getPath()).thenReturn("/some/path/template.ftl");
    when(execution.getResourceFile(any())).thenReturn(mockFile);
    when(execution.bdk().messages().templates().newTemplateFromFile(any()).process(any()))
        .thenReturn("file template result");

    // Act
    String result = TemplateContentExtractor.extractContent(execution, null, "template.ftl", null);

    // Assert
    assertEquals("file template result", result);
  }

  /**
   * Test {@link TemplateContentExtractor#extractContent(ActivityExecutorContext, String, String,
   * String)} when content and templatePath are both null.
   */
  @Test
  @DisplayName("Test extractContent with null content and null templatePath uses template string")
  void testExtractContent_nullContentNullTemplatePath() throws IOException {
    // Arrange
    ActivityExecutorContext<?> execution = mock(ActivityExecutorContext.class, RETURNS_DEEP_STUBS);
    when(execution.getVariables()).thenReturn(new HashMap<>());
    when(execution.bdk().messages().templates().newTemplateFromString(any()).process(any()))
        .thenReturn("string template result");

    // Act
    String result = TemplateContentExtractor.extractContent(execution, null, null, "Hello ${name}");

    // Assert
    assertEquals("string template result", result);
  }
}
