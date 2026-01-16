package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.template.api.Template;
import com.symphony.bdk.template.api.TemplateEngine;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class TemplateContentExtractorClaude_extractContentTest {

  private ActivityExecutorContext<?> context;
  private BdkGateway bdkGateway;
  private MessageService messageService;
  private TemplateEngine templateEngine;
  private Template template;
  private SessionService sessionService;
  private SharedDataStore sharedDataStore;
  private SecretKeeper secretKeeper;
  private Map<String, Object> variables;

  @BeforeEach
  void setUp() {
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    messageService = mock(MessageService.class);
    templateEngine = mock(TemplateEngine.class);
    template = mock(Template.class);
    sessionService = mock(SessionService.class);
    sharedDataStore = mock(SharedDataStore.class);
    secretKeeper = mock(SecretKeeper.class);
    variables = new HashMap<>();

    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.messages()).thenReturn(messageService);
    when(messageService.templates()).thenReturn(templateEngine);
    when(context.getVariables()).thenReturn(variables);
    when(bdkGateway.session()).thenReturn(sessionService);
    when(context.sharedDataStore()).thenReturn(sharedDataStore);
    when(context.secretKeeper()).thenReturn(secretKeeper);
  }

  // Tests for when content is not null - should return content directly

  @Test
  void extractContent_withNonNullContent_shouldReturnContentDirectly() throws IOException {
    // Given: Content is provided
    String content = "Direct content message";

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, content, null, null);

    // Then: Should return the content directly without processing templates
    assertThat(result).isEqualTo(content);
    verifyNoInteractions(messageService);
    verifyNoInteractions(templateEngine);
  }

  @Test
  void extractContent_withNonNullContentAndTemplatePath_shouldIgnoreTemplatePathAndReturnContent() throws IOException {
    // Given: Both content and templatePath are provided
    String content = "Direct content message";
    String templatePath = "templates/message.ftl";

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, content, templatePath, null);

    // Then: Should return content directly and ignore templatePath
    assertThat(result).isEqualTo(content);
    verifyNoInteractions(messageService);
    verifyNoInteractions(templateEngine);
    verify(context, never()).getResourceFile(any());
  }

  @Test
  void extractContent_withNonNullContentAndTemplate_shouldIgnoreTemplateAndReturnContent() throws IOException {
    // Given: Both content and template string are provided
    String content = "Direct content message";
    String templateString = "Hello ${name}";

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, content, null, templateString);

    // Then: Should return content directly and ignore template
    assertThat(result).isEqualTo(content);
    verifyNoInteractions(messageService);
    verifyNoInteractions(templateEngine);
  }

  @Test
  void extractContent_withNonNullContentAndBothTemplates_shouldReturnContentOnly() throws IOException {
    // Given: Content, templatePath, and template string are all provided
    String content = "Direct content message";
    String templatePath = "templates/message.ftl";
    String templateString = "Hello ${name}";

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, content, templatePath, templateString);

    // Then: Should return content directly and ignore all templates
    assertThat(result).isEqualTo(content);
    verifyNoInteractions(messageService);
    verifyNoInteractions(templateEngine);
    verify(context, never()).getResourceFile(any());
  }

  @Test
  void extractContent_withEmptyStringContent_shouldReturnEmptyString() throws IOException {
    // Given: Content is an empty string
    String content = "";

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, content, null, null);

    // Then: Should return empty string directly
    assertThat(result).isEmpty();
    verifyNoInteractions(messageService);
    verifyNoInteractions(templateEngine);
  }

  @Test
  void extractContent_withWhitespaceContent_shouldReturnWhitespaceContent() throws IOException {
    // Given: Content contains only whitespace
    String content = "   \n\t  ";

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, content, null, null);

    // Then: Should return whitespace content directly
    assertThat(result).isEqualTo(content);
    verifyNoInteractions(messageService);
    verifyNoInteractions(templateEngine);
  }

  // Tests for templatePath processing

  @Test
  void extractContent_withNullContentAndTemplatePath_shouldProcessTemplateFromFile() throws IOException {
    // Given: Content is null and templatePath is provided
    String templatePath = "templates/message.ftl";
    File templateFile = new File("/path/to/template.ftl");
    String processedContent = "Processed template content";

    variables.put("name", "John");
    variables.put("age", 30);

    when(context.getResourceFile(Path.of(templatePath))).thenReturn(templateFile);
    when(templateEngine.newTemplateFromFile(templateFile.getPath())).thenReturn(template);
    when(template.process(any(Map.class))).thenReturn(processedContent);

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, null, templatePath, null);

    // Then: Should process template from file with variables
    assertThat(result).isEqualTo(processedContent);
    verify(context).getResourceFile(Path.of(templatePath));
    verify(templateEngine).newTemplateFromFile(templateFile.getPath());
    verify(template).process(any(Map.class));
  }

  @Test
  void extractContent_withTemplatePathAndEmptyVariables_shouldProcessTemplateWithEmptyMap() throws IOException {
    // Given: Content is null, templatePath is provided, and no variables
    String templatePath = "templates/simple.ftl";
    File templateFile = new File("/path/to/simple.ftl");
    String processedContent = "Static template content";

    when(context.getResourceFile(Path.of(templatePath))).thenReturn(templateFile);
    when(templateEngine.newTemplateFromFile(templateFile.getPath())).thenReturn(template);
    when(template.process(any(Map.class))).thenReturn(processedContent);

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, null, templatePath, null);

    // Then: Should process template with empty variables map
    assertThat(result).isEqualTo(processedContent);
    verify(template).process(any(Map.class));
  }

  @Test
  void extractContent_withTemplatePathThrowingIOException_shouldPropagateException() throws IOException {
    // Given: getResourceFile throws IOException
    String templatePath = "templates/nonexistent.ftl";
    IOException expectedException = new IOException("File not found");

    when(context.getResourceFile(Path.of(templatePath))).thenThrow(expectedException);

    // When/Then: Should propagate IOException
    assertThatThrownBy(() ->
        TemplateContentExtractor.extractContent(context, null, templatePath, null))
        .isInstanceOf(IOException.class)
        .hasMessage("File not found");
  }

  @Test
  void extractContent_withTemplateProcessingThrowingException_shouldPropagateException() throws IOException {
    // Given: Template processing throws exception
    String templatePath = "templates/bad.ftl";
    File templateFile = new File("/path/to/bad.ftl");
    RuntimeException expectedException = new RuntimeException("Template processing error");

    when(context.getResourceFile(Path.of(templatePath))).thenReturn(templateFile);
    when(templateEngine.newTemplateFromFile(templateFile.getPath())).thenReturn(template);
    when(template.process(any(Map.class))).thenThrow(expectedException);

    // When/Then: Should propagate exception
    assertThatThrownBy(() ->
        TemplateContentExtractor.extractContent(context, null, templatePath, null))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Template processing error");
  }

  // Tests for template string processing

  @Test
  void extractContent_withNullContentAndTemplateString_shouldProcessTemplateFromString() throws IOException {
    // Given: Content is null and template string is provided
    String templateString = "Hello ${name}, you are ${age} years old";
    String processedContent = "Hello Alice, you are 25 years old";

    variables.put("name", "Alice");
    variables.put("age", 25);

    when(templateEngine.newTemplateFromString(templateString)).thenReturn(template);
    when(template.process(any(Map.class))).thenReturn(processedContent);

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, null, null, templateString);

    // Then: Should process template from string with variables
    assertThat(result).isEqualTo(processedContent);
    verify(templateEngine).newTemplateFromString(templateString);
    verify(template).process(any(Map.class));
  }

  @Test
  void extractContent_withTemplateStringAndEmptyVariables_shouldProcessTemplateWithEmptyMap() throws IOException {
    // Given: Content is null, template string is provided, and no variables
    String templateString = "Static template text";
    String processedContent = "Static template text";

    when(templateEngine.newTemplateFromString(templateString)).thenReturn(template);
    when(template.process(any(Map.class))).thenReturn(processedContent);

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, null, null, templateString);

    // Then: Should process template with empty variables map
    assertThat(result).isEqualTo(processedContent);
    verify(template).process(any(Map.class));
  }

  @Test
  void extractContent_withTemplateStringProcessingThrowingException_shouldPropagateException() throws IOException {
    // Given: Template string processing throws exception
    String templateString = "Invalid ${template}";
    RuntimeException expectedException = new RuntimeException("Template error");

    when(templateEngine.newTemplateFromString(templateString)).thenReturn(template);
    when(template.process(any(Map.class))).thenThrow(expectedException);

    // When/Then: Should propagate exception
    assertThatThrownBy(() ->
        TemplateContentExtractor.extractContent(context, null, null, templateString))
        .isInstanceOf(RuntimeException.class)
        .hasMessage("Template error");
  }

  // Tests for priority: templatePath takes precedence over template string when both null content

  @Test
  void extractContent_withNullContentAndBothTemplates_shouldUseTemplatePathNotString() throws IOException {
    // Given: Content is null, both templatePath and template string are provided
    String templatePath = "templates/priority.ftl";
    String templateString = "This should be ignored";
    File templateFile = new File("/path/to/priority.ftl");
    String processedContent = "Content from file template";

    when(context.getResourceFile(Path.of(templatePath))).thenReturn(templateFile);
    when(templateEngine.newTemplateFromFile(templateFile.getPath())).thenReturn(template);
    when(template.process(any(Map.class))).thenReturn(processedContent);

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, null, templatePath, templateString);

    // Then: Should use templatePath and ignore template string
    assertThat(result).isEqualTo(processedContent);
    verify(context).getResourceFile(Path.of(templatePath));
    verify(templateEngine).newTemplateFromFile(templateFile.getPath());
    verify(templateEngine, never()).newTemplateFromString(anyString());
  }

  // Tests for variable handling

  @Test
  void extractContent_shouldPassAllVariablesToTemplate() throws IOException {
    // Given: Multiple variables in context
    String templateString = "Template with variables";
    String processedContent = "Processed content";

    variables.put("var1", "value1");
    variables.put("var2", 123);
    variables.put("var3", true);
    variables.put("var4", Map.of("nested", "value"));

    when(templateEngine.newTemplateFromString(templateString)).thenReturn(template);
    when(template.process(any(Map.class))).thenReturn(processedContent);

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, null, null, templateString);

    // Then: Should pass all variables to template processing
    assertThat(result).isEqualTo(processedContent);
    verify(context).getVariables();
    verify(template).process(any(Map.class));
  }

  // Tests for utility functions mapper injection

  @Test
  void extractContent_shouldInjectUtilityFunctionsMapper() throws IOException {
    // Given: Template string is provided
    String templateString = "Template using utility functions";
    String processedContent = "Processed content";

    when(templateEngine.newTemplateFromString(templateString)).thenReturn(template);
    when(template.process(any(Map.class))).thenAnswer(invocation -> {
      Map<String, Object> templateVars = invocation.getArgument(0);
      // Verify that wdk utility functions are injected
      assertThat(templateVars).containsKey("wdk");
      assertThat(templateVars.get("wdk")).isNotNull();
      return processedContent;
    });

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, null, null, templateString);

    // Then: Should inject utility functions mapper
    assertThat(result).isEqualTo(processedContent);
    verify(bdkGateway).session();
    verify(context).sharedDataStore();
    verify(context).secretKeeper();
  }

  // Tests for null/missing parameters edge cases

  @Test
  void extractContent_withAllNullParameters_shouldProcessEmptyTemplate() throws IOException {
    // Given: All parameters are null
    String processedContent = "Default content";

    when(templateEngine.newTemplateFromString(null)).thenReturn(template);
    when(template.process(any(Map.class))).thenReturn(processedContent);

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, null, null, null);

    // Then: Should process template from null string
    assertThat(result).isEqualTo(processedContent);
    verify(templateEngine).newTemplateFromString(null);
  }

  @Test
  void extractContent_withComplexVariables_shouldHandleCorrectly() throws IOException {
    // Given: Complex variable types
    String templateString = "Complex template";
    String processedContent = "Processed complex content";

    variables.put("list", java.util.Arrays.asList(1, 2, 3));
    variables.put("map", Map.of("key1", "val1", "key2", "val2"));
    variables.put("nullValue", null);

    when(templateEngine.newTemplateFromString(templateString)).thenReturn(template);
    when(template.process(any(Map.class))).thenReturn(processedContent);

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, null, null, templateString);

    // Then: Should handle complex variables
    assertThat(result).isEqualTo(processedContent);
    verify(template).process(any(Map.class));
  }

  @Test
  void extractContent_withSpecialCharactersInContent_shouldReturnAsIs() throws IOException {
    // Given: Content with special characters
    String content = "Special chars: <>&\"'${}\n\t\r";

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, content, null, null);

    // Then: Should return content as-is
    assertThat(result).isEqualTo(content);
  }

  @Test
  void extractContent_withUnicodeContent_shouldReturnAsIs() throws IOException {
    // Given: Content with unicode characters
    String content = "Unicode: 你好 🎉 café";

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, content, null, null);

    // Then: Should return content as-is
    assertThat(result).isEqualTo(content);
  }

  @Test
  void extractContent_calledMultipleTimes_shouldWorkCorrectly() throws IOException {
    // Given: Setup for multiple calls
    String content1 = "First content";
    String templateString = "Template ${var}";
    String processedContent = "Processed content";

    when(templateEngine.newTemplateFromString(templateString)).thenReturn(template);
    when(template.process(any(Map.class))).thenReturn(processedContent);

    // When: Called multiple times with different parameters
    String result1 = TemplateContentExtractor.extractContent(context, content1, null, null);
    String result2 = TemplateContentExtractor.extractContent(context, null, null, templateString);

    // Then: Both calls should work correctly
    assertThat(result1).isEqualTo(content1);
    assertThat(result2).isEqualTo(processedContent);
  }

  @Test
  void extractContent_withLongContent_shouldReturnCorrectly() throws IOException {
    // Given: Very long content string
    String content = "A".repeat(10000);

    // When: extractContent is called
    String result = TemplateContentExtractor.extractContent(context, content, null, null);

    // Then: Should return long content as-is
    assertThat(result).isEqualTo(content);
    assertThat(result).hasSize(10000);
  }

  @Test
  void extractContent_shouldNotModifyOriginalVariablesMap() throws IOException {
    // Given: Template string and variables
    String templateString = "Template";
    String processedContent = "Processed";
    int originalSize = variables.size();

    when(templateEngine.newTemplateFromString(templateString)).thenReturn(template);
    when(template.process(any(Map.class))).thenReturn(processedContent);

    // When: extractContent is called
    TemplateContentExtractor.extractContent(context, null, null, templateString);

    // Then: Original variables map should not be modified (wdk is added to a copy)
    assertThat(variables).hasSize(originalSize);
    assertThat(variables).doesNotContainKey("wdk");
  }
}
