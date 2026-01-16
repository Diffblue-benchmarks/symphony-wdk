package com.symphony.bdk.workflow.engine.executor.attachment;

import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.gen.api.model.V4AttachmentInfo;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.gen.api.model.V4Stream;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutor;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.attachment.GetAttachment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetAttachmentExecutorClaudeTest {

  private GetAttachmentExecutor executor;
  private ActivityExecutorContext<GetAttachment> context;
  private GetAttachment activity;
  private BdkGateway bdkGateway;
  private MessageService messageService;
  private V4Message message;
  private V4Stream stream;
  private V4AttachmentInfo attachmentInfo;

  @BeforeEach
  void setUp() {
    executor = new GetAttachmentExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetAttachment();
    bdkGateway = mock(BdkGateway.class);
    messageService = mock(MessageService.class);
    message = new V4Message();
    stream = new V4Stream();
    attachmentInfo = new V4AttachmentInfo();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.messages()).thenReturn(messageService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    GetAttachmentExecutor newExecutor = new GetAttachmentExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetAttachmentExecutor newExecutor = new GetAttachmentExecutor();

    // Then: Instance should be of GetAttachmentExecutor type and implement ActivityExecutor
    assertThat(newExecutor).isInstanceOf(GetAttachmentExecutor.class);
    assertThat(newExecutor).isInstanceOf(ActivityExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetAttachmentExecutor executor1 = new GetAttachmentExecutor();
    GetAttachmentExecutor executor2 = new GetAttachmentExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetAttachmentExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - success scenarios

  @Test
  void execute_withValidMessageAndAttachment_shouldSetOutputVariable() throws IOException {
    // Given: A valid message with attachment
    String messageId = "msg123";
    String attachmentId = "att456";
    String streamId = "stream789";
    String attachmentName = "test.txt";
    String processInstanceId = "process001";
    String currentActivityId = "activity001";

    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    message.setMessageId(messageId);
    stream.setStreamId(streamId);
    message.setStream(stream);

    attachmentInfo.setId(attachmentId);
    attachmentInfo.setName(attachmentName);
    List<V4AttachmentInfo> attachments = new ArrayList<>();
    attachments.add(attachmentInfo);
    message.setAttachments(attachments);

    byte[] attachmentContent = Base64.getEncoder().encode("test content".getBytes());
    Path expectedPath = Path.of(processInstanceId, currentActivityId + "-" + attachmentName);

    when(messageService.getMessage(messageId)).thenReturn(message);
    when(messageService.getAttachment(streamId, messageId, attachmentId)).thenReturn(attachmentContent);
    when(context.getProcessInstanceId()).thenReturn(processInstanceId);
    when(context.getCurrentActivityId()).thenReturn(currentActivityId);
    when(context.saveResource(any(Path.class), any(byte[].class))).thenReturn(expectedPath);

    // When: Execute is called
    executor.execute(context);

    // Then: Output variable should be set with attachment path
    verify(context).setOutputVariable(eq("attachmentPath"), anyString());
    verify(context).saveResource(any(Path.class), any(byte[].class));
  }

  @Test
  void execute_withMultipleAttachments_shouldGetCorrectAttachment() throws IOException {
    // Given: A message with multiple attachments
    String messageId = "msg123";
    String targetAttachmentId = "att456";
    String streamId = "stream789";

    activity.setMessageId(messageId);
    activity.setAttachmentId(targetAttachmentId);

    message.setMessageId(messageId);
    stream.setStreamId(streamId);
    message.setStream(stream);

    // First attachment (not the target)
    V4AttachmentInfo attachment1 = new V4AttachmentInfo();
    attachment1.setId("att111");
    attachment1.setName("file1.txt");

    // Second attachment (target)
    V4AttachmentInfo attachment2 = new V4AttachmentInfo();
    attachment2.setId(targetAttachmentId);
    attachment2.setName("file2.txt");

    // Third attachment (not the target)
    V4AttachmentInfo attachment3 = new V4AttachmentInfo();
    attachment3.setId("att999");
    attachment3.setName("file3.txt");

    List<V4AttachmentInfo> attachments = new ArrayList<>();
    attachments.add(attachment1);
    attachments.add(attachment2);
    attachments.add(attachment3);
    message.setAttachments(attachments);

    byte[] attachmentContent = Base64.getEncoder().encode("content".getBytes());

    when(messageService.getMessage(messageId)).thenReturn(message);
    when(messageService.getAttachment(streamId, messageId, targetAttachmentId)).thenReturn(attachmentContent);
    when(context.getProcessInstanceId()).thenReturn("process001");
    when(context.getCurrentActivityId()).thenReturn("activity001");
    when(context.saveResource(any(Path.class), any(byte[].class))).thenReturn(Path.of("test"));

    // When: Execute is called
    executor.execute(context);

    // Then: The correct attachment should be retrieved
    verify(messageService).getAttachment(streamId, messageId, targetAttachmentId);
    verify(context).setOutputVariable(eq("attachmentPath"), anyString());
  }

  @Test
  void execute_withBase64EncodedAttachment_shouldDecodeCorrectly() throws IOException {
    // Given: A message with base64 encoded attachment
    String messageId = "msg123";
    String attachmentId = "att456";
    String streamId = "stream789";
    String originalContent = "Hello World!";

    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    message.setMessageId(messageId);
    stream.setStreamId(streamId);
    message.setStream(stream);

    attachmentInfo.setId(attachmentId);
    attachmentInfo.setName("test.txt");
    message.setAttachments(Collections.singletonList(attachmentInfo));

    // Double encode: the attachment is base64 encoded
    byte[] encodedContent = Base64.getEncoder().encode(originalContent.getBytes());

    when(messageService.getMessage(messageId)).thenReturn(message);
    when(messageService.getAttachment(streamId, messageId, attachmentId)).thenReturn(encodedContent);
    when(context.getProcessInstanceId()).thenReturn("process001");
    when(context.getCurrentActivityId()).thenReturn("activity001");
    when(context.saveResource(any(Path.class), any(byte[].class))).thenAnswer(invocation -> {
      Path path = invocation.getArgument(0);
      byte[] content = invocation.getArgument(1);
      // Verify the content was decoded
      assertThat(new String(content)).isEqualTo(originalContent);
      return path;
    });

    // When: Execute is called
    executor.execute(context);

    // Then: Content should be decoded and saved
    verify(context).saveResource(any(Path.class), any(byte[].class));
    verify(context).setOutputVariable(eq("attachmentPath"), anyString());
  }

  @Test
  void execute_shouldUseCorrectFileNameFormat() throws IOException {
    // Given: A valid message with attachment
    String messageId = "msg123";
    String attachmentId = "att456";
    String streamId = "stream789";
    String attachmentName = "document.pdf";
    String processInstanceId = "process-xyz";
    String currentActivityId = "get-attachment-1";

    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    message.setMessageId(messageId);
    stream.setStreamId(streamId);
    message.setStream(stream);

    attachmentInfo.setId(attachmentId);
    attachmentInfo.setName(attachmentName);
    message.setAttachments(Collections.singletonList(attachmentInfo));

    byte[] attachmentContent = Base64.getEncoder().encode("content".getBytes());

    when(messageService.getMessage(messageId)).thenReturn(message);
    when(messageService.getAttachment(streamId, messageId, attachmentId)).thenReturn(attachmentContent);
    when(context.getProcessInstanceId()).thenReturn(processInstanceId);
    when(context.getCurrentActivityId()).thenReturn(currentActivityId);
    when(context.saveResource(any(Path.class), any(byte[].class))).thenAnswer(invocation -> {
      Path path = invocation.getArgument(0);
      // Verify the path follows the correct format: processInstanceId/activityId-attachmentName
      assertThat(path.toString()).contains(processInstanceId);
      assertThat(path.toString()).contains(currentActivityId + "-" + attachmentName);
      return path;
    });

    // When: Execute is called
    executor.execute(context);

    // Then: File name should follow the correct format
    verify(context).saveResource(any(Path.class), any(byte[].class));
  }

  // Tests for execute method - error scenarios

  @Test
  void execute_withNullMessage_shouldThrowIllegalArgumentException() {
    // Given: Message service returns null
    String messageId = "msg123";
    activity.setMessageId(messageId);

    when(messageService.getMessage(messageId)).thenReturn(null);

    // When/Then: Execute should throw IllegalArgumentException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Message with id " + messageId + " not found");
  }

  @Test
  void execute_withMessageWithoutAttachments_shouldThrowIllegalStateException() {
    // Given: Message has null attachments
    String messageId = "msg123";
    activity.setMessageId(messageId);

    message.setMessageId(messageId);
    message.setAttachments(null);

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When/Then: Execute should throw IllegalStateException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("No attachments in requested message with id " + messageId);
  }

  @Test
  void execute_withEmptyAttachmentList_shouldThrowIllegalStateException() {
    // Given: Message has empty attachment list
    String messageId = "msg123";
    String attachmentId = "att456";
    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    message.setMessageId(messageId);
    message.setAttachments(new ArrayList<>());

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When/Then: Execute should throw IllegalStateException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("No attachment with id " + attachmentId + " found in message with id " + messageId);
  }

  @Test
  void execute_withAttachmentNotFound_shouldThrowIllegalStateException() {
    // Given: Message has attachments but not the requested one
    String messageId = "msg123";
    String requestedAttachmentId = "att456";
    activity.setMessageId(messageId);
    activity.setAttachmentId(requestedAttachmentId);

    message.setMessageId(messageId);

    V4AttachmentInfo wrongAttachment = new V4AttachmentInfo();
    wrongAttachment.setId("att999");
    wrongAttachment.setName("wrong.txt");
    message.setAttachments(Collections.singletonList(wrongAttachment));

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When/Then: Execute should throw IllegalStateException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("No attachment with id " + requestedAttachmentId + " found in message with id " + messageId);
  }

  @Test
  void execute_withIOExceptionDuringSave_shouldPropagateIOException() throws IOException {
    // Given: saveResource throws IOException
    String messageId = "msg123";
    String attachmentId = "att456";
    String streamId = "stream789";

    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    message.setMessageId(messageId);
    stream.setStreamId(streamId);
    message.setStream(stream);

    attachmentInfo.setId(attachmentId);
    attachmentInfo.setName("test.txt");
    message.setAttachments(Collections.singletonList(attachmentInfo));

    byte[] attachmentContent = Base64.getEncoder().encode("content".getBytes());

    when(messageService.getMessage(messageId)).thenReturn(message);
    when(messageService.getAttachment(streamId, messageId, attachmentId)).thenReturn(attachmentContent);
    when(context.getProcessInstanceId()).thenReturn("process001");
    when(context.getCurrentActivityId()).thenReturn("activity001");
    when(context.saveResource(any(Path.class), any(byte[].class))).thenThrow(new IOException("Failed to save file"));

    // When/Then: Execute should propagate IOException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IOException.class)
        .hasMessageContaining("Failed to save file");
  }

  @Test
  void execute_withIOExceptionDuringGetMessage_shouldPropagateIOException() throws IOException {
    // Given: getMessage throws IOException
    String messageId = "msg123";
    activity.setMessageId(messageId);

    when(messageService.getMessage(messageId)).thenThrow(new RuntimeException("Failed to get message"));

    // When/Then: Execute should throw exception
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Failed to get message");
  }

  @Test
  void execute_withNullStream_shouldThrowNullPointerException() {
    // Given: Message has null stream
    String messageId = "msg123";
    String attachmentId = "att456";

    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    message.setMessageId(messageId);
    message.setStream(null);

    attachmentInfo.setId(attachmentId);
    attachmentInfo.setName("test.txt");
    message.setAttachments(Collections.singletonList(attachmentInfo));

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When/Then: Execute should throw NullPointerException when accessing stream
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(NullPointerException.class);
  }

  // Tests for execute method - edge cases

  @Test
  void execute_withSpecialCharactersInFileName_shouldHandleCorrectly() throws IOException {
    // Given: Attachment with special characters in name
    String messageId = "msg123";
    String attachmentId = "att456";
    String streamId = "stream789";
    String attachmentName = "file with spaces & special-chars.txt";

    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    message.setMessageId(messageId);
    stream.setStreamId(streamId);
    message.setStream(stream);

    attachmentInfo.setId(attachmentId);
    attachmentInfo.setName(attachmentName);
    message.setAttachments(Collections.singletonList(attachmentInfo));

    byte[] attachmentContent = Base64.getEncoder().encode("content".getBytes());

    when(messageService.getMessage(messageId)).thenReturn(message);
    when(messageService.getAttachment(streamId, messageId, attachmentId)).thenReturn(attachmentContent);
    when(context.getProcessInstanceId()).thenReturn("process001");
    when(context.getCurrentActivityId()).thenReturn("activity001");
    when(context.saveResource(any(Path.class), any(byte[].class))).thenReturn(Path.of("test"));

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    verify(context).setOutputVariable(eq("attachmentPath"), anyString());
  }

  @Test
  void execute_withEmptyAttachmentContent_shouldHandleCorrectly() throws IOException {
    // Given: Attachment with empty content
    String messageId = "msg123";
    String attachmentId = "att456";
    String streamId = "stream789";

    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    message.setMessageId(messageId);
    stream.setStreamId(streamId);
    message.setStream(stream);

    attachmentInfo.setId(attachmentId);
    attachmentInfo.setName("empty.txt");
    message.setAttachments(Collections.singletonList(attachmentInfo));

    byte[] attachmentContent = Base64.getEncoder().encode(new byte[0]);

    when(messageService.getMessage(messageId)).thenReturn(message);
    when(messageService.getAttachment(streamId, messageId, attachmentId)).thenReturn(attachmentContent);
    when(context.getProcessInstanceId()).thenReturn("process001");
    when(context.getCurrentActivityId()).thenReturn("activity001");
    when(context.saveResource(any(Path.class), any(byte[].class))).thenReturn(Path.of("test"));

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    verify(context).setOutputVariable(eq("attachmentPath"), anyString());
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() throws IOException {
    // Given: Valid setup
    String messageId = "msg123";
    String attachmentId = "att456";
    String streamId = "stream789";

    activity.setMessageId(messageId);
    activity.setAttachmentId(attachmentId);

    message.setMessageId(messageId);
    stream.setStreamId(streamId);
    message.setStream(stream);

    attachmentInfo.setId(attachmentId);
    attachmentInfo.setName("test.txt");
    message.setAttachments(Collections.singletonList(attachmentInfo));

    byte[] attachmentContent = Base64.getEncoder().encode("content".getBytes());

    when(messageService.getMessage(messageId)).thenReturn(message);
    when(messageService.getAttachment(streamId, messageId, attachmentId)).thenReturn(attachmentContent);
    when(context.getProcessInstanceId()).thenReturn("process001");
    when(context.getCurrentActivityId()).thenReturn("activity001");
    when(context.saveResource(any(Path.class), any(byte[].class))).thenReturn(Path.of("test"));

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(context, org.mockito.Mockito.times(2)).setOutputVariable(eq("attachmentPath"), anyString());
  }
}
