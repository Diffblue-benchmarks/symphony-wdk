package com.symphony.bdk.workflow.engine.executor.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class TemplateContentExtractorTest {

  @Mock
  private ActivityExecutorContext<?> context;

  @Mock(answer = RETURNS_DEEP_STUBS)
  private BdkGateway bdkGateway;

  @Mock
  private SessionService sessionService;

  @Mock
  private SharedDataStore sharedDataStore;

  @Mock
  private SecretKeeper secretKeeper;

  @Test
  void extractContent_shouldReturnContentWhenContentIsNotNull() throws IOException {
    // Arrange
    String content = "Direct content";

    // Act
    String result = TemplateContentExtractor.extractContent(context, content, null, null);

    // Assert
    assertThat(result).isEqualTo("Direct content");
  }

  @Test
  void extractContent_shouldProcessTemplateFromFileWhenTemplatePathIsProvided() throws IOException {
    // Arrange
    String templatePath = "templates/message.ftl";
    File templateFile = new File("/path/to/template.ftl");
    Map<String, Object> variables = new HashMap<>();
    variables.put("key1", "value1");

    when(context.getVariables()).thenReturn(variables);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.session()).thenReturn(sessionService);
    when(context.sharedDataStore()).thenReturn(sharedDataStore);
    when(context.secretKeeper()).thenReturn(secretKeeper);
    when(context.getResourceFile(Path.of(templatePath))).thenReturn(templateFile);
    when(bdkGateway.messages().templates().newTemplateFromFile(anyString()).process(anyMap()))
        .thenReturn("Processed template from file");

    // Act
    String result = TemplateContentExtractor.extractContent(context, null, templatePath, null);

    // Assert
    assertThat(result).isEqualTo("Processed template from file");
    verify(context).getResourceFile(Path.of(templatePath));
  }

  @Test
  void extractContent_shouldProcessTemplateFromStringWhenTemplateIsProvided() throws IOException {
    // Arrange
    String templateString = "<messageML>Hello ${name}</messageML>";
    Map<String, Object> variables = new HashMap<>();
    variables.put("name", "John");

    when(context.getVariables()).thenReturn(variables);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.session()).thenReturn(sessionService);
    when(context.sharedDataStore()).thenReturn(sharedDataStore);
    when(context.secretKeeper()).thenReturn(secretKeeper);
    when(bdkGateway.messages().templates().newTemplateFromString(anyString()).process(anyMap()))
        .thenReturn("Processed template from string");

    // Act
    String result = TemplateContentExtractor.extractContent(context, null, null, templateString);

    // Assert
    assertThat(result).isEqualTo("Processed template from string");
  }

  @Test
  void extractContent_shouldIncludeUtilityFunctionsInTemplateVariables() throws IOException {
    // Arrange
    String templateString = "<messageML>Test</messageML>";
    Map<String, Object> variables = new HashMap<>();

    when(context.getVariables()).thenReturn(variables);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.session()).thenReturn(sessionService);
    when(context.sharedDataStore()).thenReturn(sharedDataStore);
    when(context.secretKeeper()).thenReturn(secretKeeper);
    when(bdkGateway.messages().templates().newTemplateFromString(anyString()).process(anyMap()))
        .thenReturn("Processed");

    // Act
    TemplateContentExtractor.extractContent(context, null, null, templateString);

    // Assert
    verify(bdkGateway).session();
    verify(context).sharedDataStore();
    verify(context).secretKeeper();
  }

  @Test
  void extractContent_shouldProcessTemplateWithEmptyVariables() throws IOException {
    // Arrange
    String templateString = "<messageML>Static content</messageML>";
    Map<String, Object> emptyVariables = new HashMap<>();

    when(context.getVariables()).thenReturn(emptyVariables);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.session()).thenReturn(sessionService);
    when(context.sharedDataStore()).thenReturn(sharedDataStore);
    when(context.secretKeeper()).thenReturn(secretKeeper);
    when(bdkGateway.messages().templates().newTemplateFromString(anyString()).process(anyMap()))
        .thenReturn("Static content processed");

    // Act
    String result = TemplateContentExtractor.extractContent(context, null, null, templateString);

    // Assert
    assertThat(result).isEqualTo("Static content processed");
  }
}
