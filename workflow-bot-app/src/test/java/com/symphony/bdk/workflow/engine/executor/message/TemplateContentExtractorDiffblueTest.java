package com.symphony.bdk.workflow.engine.executor.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import java.io.IOException;
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
}
