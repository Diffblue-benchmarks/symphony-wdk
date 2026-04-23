package com.symphony.bdk.workflow.engine.executor.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.template.api.Template;
import com.symphony.bdk.template.api.TemplateEngine;
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
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class TemplateContentExtractorTest {

  @Mock
  private ActivityExecutorContext<?> execution;

  @Mock
  private BdkGateway bdk;

  @Mock
  private MessageService messageService;

  @Mock
  private TemplateEngine templateEngine;

  @Mock
  private Template templateResult;

  @Mock
  private SessionService sessionService;

  @Mock
  private SharedDataStore sharedDataStore;

  @Mock
  private SecretKeeper secretKeeper;

  @Test
  void shouldReturnContentWhenContentIsNotNull() throws IOException {
    // given
    String content = "Hello World";

    // when
    String result = TemplateContentExtractor.extractContent(execution, content, null, null);

    // then
    assertThat(result).isEqualTo("Hello World");
  }

  @Test
  void shouldProcessTemplateFromFileWhenContentIsNullAndTemplatePathIsSet() throws IOException {
    // given
    String templatePath = "my-template.ftl";
    String expectedContent = "<messageML>Hello</messageML>";
    File mockFile = mock(File.class);

    when(execution.getVariables()).thenReturn(Collections.emptyMap());
    when(execution.bdk()).thenReturn(bdk);
    when(bdk.session()).thenReturn(sessionService);
    when(execution.sharedDataStore()).thenReturn(sharedDataStore);
    when(execution.secretKeeper()).thenReturn(secretKeeper);
    when(execution.getResourceFile(any())).thenReturn(mockFile);
    when(mockFile.getPath()).thenReturn(templatePath);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.templates()).thenReturn(templateEngine);
    when(templateEngine.newTemplateFromFile(templatePath)).thenReturn(templateResult);
    when(templateResult.process(any())).thenReturn(expectedContent);

    // when
    String result = TemplateContentExtractor.extractContent(execution, null, templatePath, null);

    // then
    assertThat(result).isEqualTo(expectedContent);
  }

  @Test
  void shouldProcessTemplateFromStringWhenContentAndTemplatePathAreNull() throws IOException {
    // given
    String templateString = "<messageML>Hello</messageML>";
    String expectedContent = "<messageML>Hello</messageML>";

    when(execution.getVariables()).thenReturn(Collections.emptyMap());
    when(execution.bdk()).thenReturn(bdk);
    when(bdk.session()).thenReturn(sessionService);
    when(execution.sharedDataStore()).thenReturn(sharedDataStore);
    when(execution.secretKeeper()).thenReturn(secretKeeper);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.templates()).thenReturn(templateEngine);
    when(templateEngine.newTemplateFromString(templateString)).thenReturn(templateResult);
    when(templateResult.process(any())).thenReturn(expectedContent);

    // when
    String result = TemplateContentExtractor.extractContent(execution, null, null, templateString);

    // then
    assertThat(result).isEqualTo(expectedContent);
  }
}
