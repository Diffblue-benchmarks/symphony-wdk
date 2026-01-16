package com.symphony.bdk.workflow.engine.executor.message;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class TemplateContentExtractorClaude_constructorTest {

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    TemplateContentExtractor extractor = new TemplateContentExtractor();

    // Then: Instance should be created successfully
    assertThat(extractor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    TemplateContentExtractor extractor = new TemplateContentExtractor();

    // Then: Instance should be of TemplateContentExtractor type
    assertThat(extractor).isInstanceOf(TemplateContentExtractor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    TemplateContentExtractor extractor1 = new TemplateContentExtractor();
    TemplateContentExtractor extractor2 = new TemplateContentExtractor();

    // Then: Each instance should be distinct
    assertThat(extractor1).isNotSameAs(extractor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new TemplateContentExtractor())
        .doesNotThrowAnyException();
  }
}
