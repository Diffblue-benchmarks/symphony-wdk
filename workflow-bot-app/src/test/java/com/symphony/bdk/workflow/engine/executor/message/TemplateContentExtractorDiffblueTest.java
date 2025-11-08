package com.symphony.bdk.workflow.engine.executor.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import java.io.IOException;
import org.junit.jupiter.api.Test;

class TemplateContentExtractorDiffblueTest {
  /**
   * Method under test:
   * {@link TemplateContentExtractor#extractContent(ActivityExecutorContext, String, String, String)}
   */
  @Test
  void testExtractContent() throws IOException {
    // Arrange, Act and Assert
    assertEquals("Not all who wander are lost", TemplateContentExtractor.extractContent(
        mock(ActivityExecutorContext.class), "Not all who wander are lost", "Template Path", "Template"));
  }
}
