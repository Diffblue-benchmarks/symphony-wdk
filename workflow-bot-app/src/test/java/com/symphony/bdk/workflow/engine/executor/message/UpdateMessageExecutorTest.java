package com.symphony.bdk.workflow.engine.executor.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.message.model.Message;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.message.UpdateMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class UpdateMessageExecutorTest {

  private UpdateMessageExecutor executor;
  private ActivityExecutorContext<UpdateMessage> context;
  private BdkGateway bdkGateway;
  private MessageService messageService;
  private UpdateMessage activity;

  @BeforeEach
  void setUp() {
    executor = new UpdateMessageExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    messageService = mock(MessageService.class);
    activity = new UpdateMessage();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.messages()).thenReturn(messageService);
  }

  @Test
  void shouldUpdateMessageWithContent() throws IOException {
    String messageId = "msg-123";
    String content = "<messageML>Updated content</messageML>";
    activity.setMessageId(messageId);
    activity.setContent(content);
    activity.setSilent(false);

    V4Message existingMessage = new V4Message();
    existingMessage.setMessageId(messageId);
    when(messageService.getMessage(eq(messageId))).thenReturn(existingMessage);

    V4Message updatedMessage = new V4Message();
    updatedMessage.setMessageId(messageId);
    updatedMessage.setMessage(content);
    when(messageService.update(any(V4Message.class), any(Message.class))).thenReturn(updatedMessage);

    executor.execute(context);

    verify(messageService).getMessage(eq(messageId));
    ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
    verify(messageService).update(eq(existingMessage), messageCaptor.capture());

    Message capturedMessage = messageCaptor.getValue();
    assertThat(capturedMessage.getContent()).isEqualTo(content);
    assertThat(capturedMessage.getSilent()).isFalse();

    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());

    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs).containsEntry("message", updatedMessage);
    assertThat(outputs).containsEntry("messages", updatedMessage);
    assertThat(outputs).containsEntry("msgId", messageId);
    assertThat(outputs).containsKey("msgIds");
    assertThat((List<String>) outputs.get("msgIds")).containsExactly(messageId);
  }

  @Test
  void shouldUpdateMessageSilently() throws IOException {
    String messageId = "msg-456";
    String content = "<messageML>Silent update</messageML>";
    activity.setMessageId(messageId);
    activity.setContent(content);
    activity.setSilent(true);

    V4Message existingMessage = new V4Message();
    existingMessage.setMessageId(messageId);
    when(messageService.getMessage(eq(messageId))).thenReturn(existingMessage);

    V4Message updatedMessage = new V4Message();
    updatedMessage.setMessageId(messageId);
    when(messageService.update(any(V4Message.class), any(Message.class))).thenReturn(updatedMessage);

    executor.execute(context);

    ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
    verify(messageService).update(any(V4Message.class), messageCaptor.capture());

    Message capturedMessage = messageCaptor.getValue();
    assertThat(capturedMessage.getSilent()).isTrue();
  }

  @Test
  void shouldUpdateMessageWithTemplate() throws IOException {
    String messageId = "msg-789";
    String template = "<messageML>${variable}</messageML>";
    String extractedContent = "<messageML>Processed content</messageML>";
    activity.setMessageId(messageId);
    activity.setTemplate(template);

    V4Message existingMessage = new V4Message();
    existingMessage.setMessageId(messageId);
    when(messageService.getMessage(eq(messageId))).thenReturn(existingMessage);

    V4Message updatedMessage = new V4Message();
    updatedMessage.setMessageId(messageId);
    when(messageService.update(any(V4Message.class), any(Message.class))).thenReturn(updatedMessage);

    try (MockedStatic<TemplateContentExtractor> mockedExtractor = mockStatic(TemplateContentExtractor.class)) {
      mockedExtractor.when(() -> TemplateContentExtractor.extractContent(
          eq(context), eq(null), eq(null), eq(template))).thenReturn(extractedContent);

      executor.execute(context);

      mockedExtractor.verify(() -> TemplateContentExtractor.extractContent(
          eq(context), eq(null), eq(null), eq(template)));

      ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
      verify(messageService).update(any(V4Message.class), messageCaptor.capture());

      Message capturedMessage = messageCaptor.getValue();
      assertThat(capturedMessage.getContent()).isEqualTo(extractedContent);
    }
  }

  @Test
  void shouldUpdateMessageWithTemplatePath() throws IOException {
    String messageId = "msg-101";
    String templatePath = "templates/message.ftl";
    String extractedContent = "<messageML>Template content</messageML>";
    activity.setMessageId(messageId);
    activity.setTemplatePath(templatePath);

    V4Message existingMessage = new V4Message();
    existingMessage.setMessageId(messageId);
    when(messageService.getMessage(eq(messageId))).thenReturn(existingMessage);

    V4Message updatedMessage = new V4Message();
    updatedMessage.setMessageId(messageId);
    when(messageService.update(any(V4Message.class), any(Message.class))).thenReturn(updatedMessage);

    try (MockedStatic<TemplateContentExtractor> mockedExtractor = mockStatic(TemplateContentExtractor.class)) {
      mockedExtractor.when(() -> TemplateContentExtractor.extractContent(
          eq(context), eq(null), eq(templatePath), eq(null))).thenReturn(extractedContent);

      executor.execute(context);

      mockedExtractor.verify(() -> TemplateContentExtractor.extractContent(
          eq(context), eq(null), eq(templatePath), eq(null)));

      ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
      verify(messageService).update(any(V4Message.class), messageCaptor.capture());

      Message capturedMessage = messageCaptor.getValue();
      assertThat(capturedMessage.getContent()).isEqualTo(extractedContent);
    }
  }

  @Test
  void shouldSetAllOutputVariables() throws IOException {
    String messageId = "msg-202";
    String content = "<messageML>Test output</messageML>";
    activity.setMessageId(messageId);
    activity.setContent(content);

    V4Message existingMessage = new V4Message();
    when(messageService.getMessage(eq(messageId))).thenReturn(existingMessage);

    V4Message updatedMessage = new V4Message();
    updatedMessage.setMessageId(messageId);
    when(messageService.update(any(V4Message.class), any(Message.class))).thenReturn(updatedMessage);

    executor.execute(context);

    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());

    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs).hasSize(4);
    assertThat(outputs).containsEntry("message", updatedMessage);
    assertThat(outputs).containsEntry("messages", updatedMessage);
    assertThat(outputs).containsEntry("msgId", messageId);
    assertThat(outputs).containsKey("msgIds");

    List<String> messageIds = (List<String>) outputs.get("msgIds");
    assertThat(messageIds).hasSize(1);
    assertThat(messageIds.get(0)).isEqualTo(messageId);
  }
}
