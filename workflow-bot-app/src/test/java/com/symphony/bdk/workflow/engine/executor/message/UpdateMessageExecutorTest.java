package com.symphony.bdk.workflow.engine.executor.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.message.model.Message;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.message.UpdateMessage;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

class UpdateMessageExecutorTest {

  private final UpdateMessageExecutor underTest = new UpdateMessageExecutor();

  @Test
  @SuppressWarnings("unchecked")
  void shouldUpdateMessageWithContentAndSetOutputs() throws IOException {
    ActivityExecutorContext<UpdateMessage> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    MessageService messageService = mock(MessageService.class);

    UpdateMessage activity = new UpdateMessage();
    activity.setMessageId("msgId123");
    activity.setContent("Hello updated");
    activity.setSilent(Boolean.TRUE);

    V4Message existingMessage = new V4Message();
    existingMessage.setMessageId("msgId123");

    V4Message updatedMessage = new V4Message();
    updatedMessage.setMessageId("updatedMsgId");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.getMessage("msgId123")).thenReturn(existingMessage);
    when(messageService.update(eq(existingMessage), any(Message.class))).thenReturn(updatedMessage);

    underTest.execute(context);

    ArgumentCaptor<Map<String, Object>> outputsCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputsCaptor.capture());

    Map<String, Object> outputs = outputsCaptor.getValue();
    assertThat(outputs.get("message")).isEqualTo(updatedMessage);
    assertThat(outputs.get("messages")).isEqualTo(updatedMessage);
    assertThat(outputs.get("msgId")).isEqualTo("updatedMsgId");
    @SuppressWarnings("unchecked")
    List<String> msgIds = (List<String>) outputs.get("msgIds");
    assertThat(msgIds).containsExactly("updatedMsgId");
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldUpdateMessageAndCallBdkUpdate() throws IOException {
    ActivityExecutorContext<UpdateMessage> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    MessageService messageService = mock(MessageService.class);

    UpdateMessage activity = new UpdateMessage();
    activity.setMessageId("msgId456");
    activity.setContent("Updated content");
    activity.setSilent(Boolean.FALSE);

    V4Message existingMessage = new V4Message();
    existingMessage.setMessageId("msgId456");

    V4Message updatedMessage = new V4Message();
    updatedMessage.setMessageId("updatedId456");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.getMessage("msgId456")).thenReturn(existingMessage);
    when(messageService.update(eq(existingMessage), any(Message.class))).thenReturn(updatedMessage);

    underTest.execute(context);

    verify(messageService).getMessage("msgId456");
    verify(messageService).update(eq(existingMessage), any(Message.class));

    ArgumentCaptor<Map<String, Object>> outputsCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputsCaptor.capture());
    assertThat(outputsCaptor.getValue().get("msgId")).isEqualTo("updatedId456");
  }
}
