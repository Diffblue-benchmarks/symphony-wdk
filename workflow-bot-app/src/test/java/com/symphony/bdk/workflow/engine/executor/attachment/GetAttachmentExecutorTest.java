package com.symphony.bdk.workflow.engine.executor.attachment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.gen.api.model.V4AttachmentInfo;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.gen.api.model.V4Stream;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.attachment.GetAttachment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

class GetAttachmentExecutorTest {

  private GetAttachmentExecutor executor;
  private ActivityExecutorContext<GetAttachment> context;
  private BdkGateway bdkGateway;
  private MessageService messageService;
  private GetAttachment activity;

  @BeforeEach
  void setUp() {
    executor = new GetAttachmentExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    messageService = mock(MessageService.class);
    activity = new GetAttachment();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.messages()).thenReturn(messageService);
  }

  @Test
  void shouldGetAttachmentSuccessfully() throws IOException {
    String messageId = "msg-123";
    String attachmentId = "att-456";
    String streamId = "stream-789";
    String attachmentName = "file.txt";
    String currentActivityId = "activity-1";
    String processInstanceId = "process-1";
    byte[] base64EncodedContent = Base64.getEncoder().encode("test content".getBytes());

    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    V4Stream stream = new V4Stream();
    stream.setStreamId(streamId);

    V4AttachmentInfo attachmentInfo = new V4AttachmentInfo();
    attachmentInfo.setId(attachmentId);
    attachmentInfo.setName(attachmentName);

    V4Message message = new V4Message();
    message.setMessageId(messageId);
    message.setStream(stream);
    message.setAttachments(List.of(attachmentInfo));

    when(messageService.getMessage(eq(messageId))).thenReturn(message);
    when(messageService.getAttachment(eq(streamId), eq(messageId), eq(attachmentId)))
        .thenReturn(base64EncodedContent);
    when(context.getCurrentActivityId()).thenReturn(currentActivityId);
    when(context.getProcessInstanceId()).thenReturn(processInstanceId);

    Path expectedPath = Path.of(processInstanceId, currentActivityId + "-" + attachmentName);
    when(context.saveResource(any(Path.class), any(byte[].class))).thenReturn(expectedPath);

    executor.execute(context);

    verify(messageService).getMessage(eq(messageId));
    verify(messageService).getAttachment(eq(streamId), eq(messageId), eq(attachmentId));

    ArgumentCaptor<Path> pathCaptor = ArgumentCaptor.forClass(Path.class);
    ArgumentCaptor<byte[]> contentCaptor = ArgumentCaptor.forClass(byte[].class);
    verify(context).saveResource(pathCaptor.capture(), contentCaptor.capture());

    Path capturedPath = pathCaptor.getValue();
    assertThat(capturedPath.toString()).isEqualTo(processInstanceId + "/" + currentActivityId + "-" + attachmentName);

    byte[] capturedContent = contentCaptor.getValue();
    assertThat(new String(capturedContent)).isEqualTo("test content");

    verify(context).setOutputVariable(eq("attachmentPath"), eq(expectedPath.toString()));
  }

  @Test
  void shouldThrowExceptionWhenMessageNotFound() {
    String messageId = "msg-not-found";
    activity.setMessageId(messageId);

    when(messageService.getMessage(eq(messageId))).thenReturn(null);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Message with id msg-not-found not found");

    verify(messageService).getMessage(eq(messageId));
  }

  @Test
  void shouldThrowExceptionWhenMessageHasNoAttachments() {
    String messageId = "msg-no-attachments";
    activity.setMessageId(messageId);

    V4Message message = new V4Message();
    message.setMessageId(messageId);
    message.setAttachments(null);

    when(messageService.getMessage(eq(messageId))).thenReturn(message);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("No attachments in requested message with id msg-no-attachments");

    verify(messageService).getMessage(eq(messageId));
  }

  @Test
  void shouldThrowExceptionWhenMessageHasEmptyAttachmentsList() {
    String messageId = "msg-empty-attachments";
    String attachmentId = "att-not-found";
    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    V4Message message = new V4Message();
    message.setMessageId(messageId);
    message.setAttachments(Collections.emptyList());

    when(messageService.getMessage(eq(messageId))).thenReturn(message);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("No attachment with id att-not-found found in message with id msg-empty-attachments");

    verify(messageService).getMessage(eq(messageId));
  }

  @Test
  void shouldThrowExceptionWhenAttachmentNotFoundInMessage() {
    String messageId = "msg-456";
    String attachmentId = "att-not-found";
    String otherAttachmentId = "att-other";
    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    V4AttachmentInfo otherAttachment = new V4AttachmentInfo();
    otherAttachment.setId(otherAttachmentId);
    otherAttachment.setName("other.txt");

    V4Message message = new V4Message();
    message.setMessageId(messageId);
    message.setAttachments(List.of(otherAttachment));

    when(messageService.getMessage(eq(messageId))).thenReturn(message);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalStateException.class)
        .hasMessage("No attachment with id att-not-found found in message with id msg-456");

    verify(messageService).getMessage(eq(messageId));
  }

  @Test
  void shouldHandleMultipleAttachmentsAndSelectCorrectOne() throws IOException {
    String messageId = "msg-multi";
    String attachmentId = "att-target";
    String streamId = "stream-123";
    String currentActivityId = "activity-2";
    String processInstanceId = "process-2";
    byte[] base64EncodedContent = Base64.getEncoder().encode("target content".getBytes());

    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    V4Stream stream = new V4Stream();
    stream.setStreamId(streamId);

    V4AttachmentInfo attachment1 = new V4AttachmentInfo();
    attachment1.setId("att-1");
    attachment1.setName("file1.txt");

    V4AttachmentInfo attachment2 = new V4AttachmentInfo();
    attachment2.setId(attachmentId);
    attachment2.setName("target.pdf");

    V4AttachmentInfo attachment3 = new V4AttachmentInfo();
    attachment3.setId("att-3");
    attachment3.setName("file3.doc");

    V4Message message = new V4Message();
    message.setMessageId(messageId);
    message.setStream(stream);
    message.setAttachments(List.of(attachment1, attachment2, attachment3));

    when(messageService.getMessage(eq(messageId))).thenReturn(message);
    when(messageService.getAttachment(eq(streamId), eq(messageId), eq(attachmentId)))
        .thenReturn(base64EncodedContent);
    when(context.getCurrentActivityId()).thenReturn(currentActivityId);
    when(context.getProcessInstanceId()).thenReturn(processInstanceId);

    Path expectedPath = Path.of(processInstanceId, currentActivityId + "-target.pdf");
    when(context.saveResource(any(Path.class), any(byte[].class))).thenReturn(expectedPath);

    executor.execute(context);

    verify(messageService).getAttachment(eq(streamId), eq(messageId), eq(attachmentId));

    ArgumentCaptor<Path> pathCaptor = ArgumentCaptor.forClass(Path.class);
    verify(context).saveResource(pathCaptor.capture(), any(byte[].class));

    Path capturedPath = pathCaptor.getValue();
    assertThat(capturedPath.toString()).contains("target.pdf");
    assertThat(capturedPath.toString()).contains(currentActivityId);
  }

  @Test
  void shouldDecodeBase64ContentCorrectly() throws IOException {
    String messageId = "msg-base64";
    String attachmentId = "att-base64";
    String streamId = "stream-base64";
    String currentActivityId = "activity-base64";
    String processInstanceId = "process-base64";
    String originalContent = "Hello World!";
    byte[] base64EncodedContent = Base64.getEncoder().encode(originalContent.getBytes());

    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    V4Stream stream = new V4Stream();
    stream.setStreamId(streamId);

    V4AttachmentInfo attachmentInfo = new V4AttachmentInfo();
    attachmentInfo.setId(attachmentId);
    attachmentInfo.setName("encoded.txt");

    V4Message message = new V4Message();
    message.setMessageId(messageId);
    message.setStream(stream);
    message.setAttachments(List.of(attachmentInfo));

    when(messageService.getMessage(eq(messageId))).thenReturn(message);
    when(messageService.getAttachment(eq(streamId), eq(messageId), eq(attachmentId)))
        .thenReturn(base64EncodedContent);
    when(context.getCurrentActivityId()).thenReturn(currentActivityId);
    when(context.getProcessInstanceId()).thenReturn(processInstanceId);

    Path expectedPath = Path.of(processInstanceId, currentActivityId + "-encoded.txt");
    when(context.saveResource(any(Path.class), any(byte[].class))).thenReturn(expectedPath);

    executor.execute(context);

    ArgumentCaptor<byte[]> contentCaptor = ArgumentCaptor.forClass(byte[].class);
    verify(context).saveResource(any(Path.class), contentCaptor.capture());

    byte[] decodedContent = contentCaptor.getValue();
    assertThat(new String(decodedContent)).isEqualTo(originalContent);
  }
}
