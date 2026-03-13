package com.symphony.bdk.workflow.engine.executor.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.message.model.Message;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.Error;
import com.symphony.bdk.gen.api.model.Stream;
import com.symphony.bdk.gen.api.model.StreamAttributes;
import com.symphony.bdk.gen.api.model.V4AttachmentInfo;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.gen.api.model.V4MessageBlastResponse;
import com.symphony.bdk.gen.api.model.V4MessageSent;
import com.symphony.bdk.gen.api.model.V4Stream;
import com.symphony.bdk.gen.api.model.V4SymphonyElementsAction;
import com.symphony.bdk.gen.api.model.V4UserJoinedRoom;
import com.symphony.bdk.http.api.ApiException;
import com.symphony.bdk.http.api.ApiRuntimeException;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.message.SendMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SendMessageExecutorTest {

  private SendMessageExecutor executor;
  private ActivityExecutorContext<SendMessage> context;
  private BdkGateway bdkGateway;
  private MessageService messageService;
  private StreamService streamService;
  private SendMessage activity;

  @BeforeEach
  void setUp() {
    executor = new SendMessageExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    messageService = mock(MessageService.class);
    streamService = mock(StreamService.class);
    activity = new SendMessage();
    activity.setId("test-activity");

    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.messages()).thenReturn(messageService);
    when(bdkGateway.streams()).thenReturn(streamService);
    when(context.getActivity()).thenReturn(activity);
  }

  @Test
  void shouldExecuteWithSingleStreamId() throws IOException {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamId("stream123");
    activity.setTo(to);
    when(context.getVariables()).thenReturn(new HashMap<>());

    V4Message expectedMessage = new V4Message();
    expectedMessage.setMessageId("msg123");
    when(messageService.send(eq("stream123"), any(Message.class))).thenReturn(expectedMessage);

    executor.execute(context);

    verify(messageService).send(eq("stream123"), any(Message.class));
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void shouldExecuteWithMultipleStreamIds() throws IOException {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamIds(Arrays.asList("stream1", "stream2", "stream3"));
    activity.setTo(to);
    when(context.getVariables()).thenReturn(new HashMap<>());

    V4Message message1 = new V4Message();
    message1.setMessageId("msg1");
    V4Message message2 = new V4Message();
    message2.setMessageId("msg2");

    V4MessageBlastResponse response = new V4MessageBlastResponse();
    response.setMessages(Arrays.asList(message1, message2));

    when(messageService.send(anyList(), any(Message.class))).thenReturn(response);

    executor.execute(context);

    verify(messageService).send(anyList(), any(Message.class));
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void shouldExecuteWithBlastMessageAndErrors() throws IOException {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamIds(Arrays.asList("stream1", "stream2", "stream3"));
    activity.setTo(to);
    when(context.getVariables()).thenReturn(new HashMap<>());

    V4Message message1 = new V4Message();
    message1.setMessageId("msg1");

    V4MessageBlastResponse response = new V4MessageBlastResponse();
    response.setMessages(Collections.singletonList(message1));
    Map<String, Error> errors = new HashMap<>();
    Error error = new Error();
    error.setMessage("User not found");
    errors.put("stream3", error);
    response.setErrors(errors);

    when(messageService.send(anyList(), any(Message.class))).thenReturn(response);

    executor.execute(context);

    verify(messageService).send(anyList(), any(Message.class));
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void shouldThrowExceptionWhenBlastMessageCompletelyFails() throws IOException {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamIds(Arrays.asList("stream1", "stream2"));
    activity.setTo(to);
    when(context.getVariables()).thenReturn(new HashMap<>());

    V4MessageBlastResponse response = new V4MessageBlastResponse();
    response.setMessages(null);

    when(messageService.send(anyList(), any(Message.class))).thenReturn(response);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("All messages have failed");
  }

  @Test
  void shouldThrowExceptionWhenNoStreamIdSet() {
    activity.setContent("Test message");
    activity.setTo(new SendMessage.To());
    when(context.getVariables()).thenReturn(new HashMap<>());

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No stream id set");
  }

  @Test
  void shouldExecuteWithOboForSingleStream() throws IOException {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamId("stream123");
    activity.setTo(to);
    Obo obo = new Obo();
    obo.setUsername("obo-user");
    activity.setObo(obo);
    when(context.getVariables()).thenReturn(new HashMap<>());

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    MessageService oboMessageService = mock(MessageService.class);

    when(bdkGateway.obo(anyString())).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.messages()).thenReturn(oboMessageService);

    V4Message expectedMessage = new V4Message();
    expectedMessage.setMessageId("msg123");
    when(oboMessageService.send(eq("stream123"), any(Message.class))).thenReturn(expectedMessage);

    executor.execute(context);

    verify(oboMessageService).send(eq("stream123"), any(Message.class));
    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void shouldThrowExceptionWhenOboWithMultipleStreams() {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamIds(Arrays.asList("stream1", "stream2"));
    activity.setTo(to);
    Obo obo = new Obo();
    obo.setUsername("obo-user");
    activity.setObo(obo);
    when(context.getVariables()).thenReturn(new HashMap<>());

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Blast message")
        .hasMessageContaining("not OBO enabled");
  }

  @Test
  void shouldResolveStreamIdFromUserIds() throws IOException {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setUserIds(Arrays.asList(123L, 456L));
    activity.setTo(to);
    when(context.getVariables()).thenReturn(new HashMap<>());

    Stream stream1 = new Stream();
    stream1.setId("stream1");
    Stream stream2 = new Stream();
    stream2.setId("stream2");

    when(streamService.create(eq(List.of(123L)))).thenReturn(stream1);
    when(streamService.create(eq(List.of(456L)))).thenReturn(stream2);

    V4MessageBlastResponse response = new V4MessageBlastResponse();
    V4Message message = new V4Message();
    message.setMessageId("msg1");
    response.setMessages(Collections.singletonList(message));
    when(messageService.send(anyList(), any(Message.class))).thenReturn(response);

    executor.execute(context);

    verify(streamService).create(eq(List.of(123L)));
    verify(streamService).create(eq(List.of(456L)));
  }

  @Test
  void shouldSkipUserWhen403ErrorOnStreamCreation() throws IOException {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setUserIds(Arrays.asList(123L, 456L));
    activity.setTo(to);
    when(context.getVariables()).thenReturn(new HashMap<>());

    Stream stream2 = new Stream();
    stream2.setId("stream2");

    ApiException apiException = new ApiException(403, "User not found");
    ApiRuntimeException exception403 = new ApiRuntimeException(apiException);
    when(streamService.create(eq(List.of(123L)))).thenThrow(exception403);
    when(streamService.create(eq(List.of(456L)))).thenReturn(stream2);

    V4Message message = new V4Message();
    message.setMessageId("msg1");
    when(messageService.send(eq("stream2"), any(Message.class))).thenReturn(message);

    executor.execute(context);

    verify(streamService).create(eq(List.of(123L)));
    verify(streamService).create(eq(List.of(456L)));
  }

  @Test
  void shouldThrowExceptionWhenNon403ErrorOnStreamCreation() {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setUserIds(Collections.singletonList(123L));
    activity.setTo(to);
    when(context.getVariables()).thenReturn(new HashMap<>());

    ApiException apiException500 = new ApiException(500, "Server error");
    ApiRuntimeException exception500 = new ApiRuntimeException(apiException500);
    when(streamService.create(eq(List.of(123L)))).thenThrow(exception500);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(ApiRuntimeException.class);
  }

  @Test
  void shouldResolveStreamIdFromV4MessageSentEvent() throws IOException {
    activity.setContent("Test message");
    when(context.getVariables()).thenReturn(new HashMap<>());

    V4MessageSent eventSource = new V4MessageSent();
    V4Message eventMessage = new V4Message();
    V4Stream stream = new V4Stream();
    stream.setStreamId("stream123");
    eventMessage.setStream(stream);
    eventSource.setMessage(eventMessage);

    EventHolder event = new EventHolder();
    event.setSource(eventSource);
    when(context.getEvent()).thenReturn(event);

    V4Message expectedMessage = new V4Message();
    expectedMessage.setMessageId("msg123");
    when(messageService.send(eq("stream123"), any(Message.class))).thenReturn(expectedMessage);

    executor.execute(context);

    verify(messageService).send(eq("stream123"), any(Message.class));
  }

  @Test
  void shouldResolveStreamIdFromV4SymphonyElementsActionEvent() throws IOException {
    activity.setContent("Test message");
    when(context.getVariables()).thenReturn(new HashMap<>());

    V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
    V4Stream stream = new V4Stream();
    stream.setStreamId("stream456");
    eventSource.setStream(stream);

    EventHolder event = new EventHolder();
    event.setSource(eventSource);
    when(context.getEvent()).thenReturn(event);

    V4Message expectedMessage = new V4Message();
    expectedMessage.setMessageId("msg456");
    when(messageService.send(eq("stream456"), any(Message.class))).thenReturn(expectedMessage);

    executor.execute(context);

    verify(messageService).send(eq("stream456"), any(Message.class));
  }

  @Test
  void shouldResolveStreamIdFromV4UserJoinedRoomEvent() throws IOException {
    activity.setContent("Test message");
    when(context.getVariables()).thenReturn(new HashMap<>());

    V4UserJoinedRoom eventSource = new V4UserJoinedRoom();
    V4Stream stream = new V4Stream();
    stream.setStreamId("stream789");
    eventSource.setStream(stream);

    EventHolder event = new EventHolder();
    event.setSource(eventSource);
    when(context.getEvent()).thenReturn(event);

    V4Message expectedMessage = new V4Message();
    expectedMessage.setMessageId("msg789");
    when(messageService.send(eq("stream789"), any(Message.class))).thenReturn(expectedMessage);

    executor.execute(context);

    verify(messageService).send(eq("stream789"), any(Message.class));
  }

  @Test
  void shouldBuildMessageWithData() throws IOException {
    activity.setContent("Test message");
    activity.setData("{\"key\":\"value\"}");
    SendMessage.To to = new SendMessage.To();
    to.setStreamId("stream123");
    activity.setTo(to);
    when(context.getVariables()).thenReturn(new HashMap<>());

    V4Message expectedMessage = new V4Message();
    expectedMessage.setMessageId("msg123");
    when(messageService.send(eq("stream123"), any(Message.class))).thenReturn(expectedMessage);

    executor.execute(context);

    verify(messageService).send(eq("stream123"), any(Message.class));
  }

  @Test
  void shouldBuildMessageWithFileAttachment() throws IOException {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamId("stream123");
    activity.setTo(to);

    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setContentPath("test.txt");
    activity.setAttachments(Collections.singletonList(attachment));

    when(context.getVariables()).thenReturn(new HashMap<>());
    InputStream mockStream = new ByteArrayInputStream("test content".getBytes());
    when(context.getResource(any(Path.class))).thenReturn(mockStream);

    V4Message expectedMessage = new V4Message();
    expectedMessage.setMessageId("msg123");
    when(messageService.send(eq("stream123"), any(Message.class))).thenReturn(expectedMessage);

    executor.execute(context);

    verify(messageService).send(eq("stream123"), any(Message.class));
    verify(context).getResource(eq(Path.of("test.txt")));
  }

  @Test
  void shouldBuildMessageWithForwardedAttachment() throws IOException {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamId("stream123");
    activity.setTo(to);

    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setMessageId("originalMsg");
    activity.setAttachments(Collections.singletonList(attachment));

    when(context.getVariables()).thenReturn(new HashMap<>());

    V4Message originalMessage = new V4Message();
    originalMessage.setMessageId("originalMsg");
    V4Stream stream = new V4Stream();
    stream.setStreamId("originalStream");
    originalMessage.setStream(stream);

    V4AttachmentInfo attachmentInfo = new V4AttachmentInfo();
    attachmentInfo.setId("attach1");
    attachmentInfo.setName("file.pdf");
    originalMessage.setAttachments(Collections.singletonList(attachmentInfo));

    when(messageService.getMessage("originalMsg")).thenReturn(originalMessage);
    byte[] attachmentBytes = Base64.getEncoder().encode("attachment content".getBytes());
    when(messageService.getAttachment("originalStream", "originalMsg", "attach1")).thenReturn(attachmentBytes);

    V4Message expectedMessage = new V4Message();
    expectedMessage.setMessageId("msg123");
    when(messageService.send(eq("stream123"), any(Message.class))).thenReturn(expectedMessage);

    executor.execute(context);

    verify(messageService).getMessage("originalMsg");
    verify(messageService).getAttachment("originalStream", "originalMsg", "attach1");
  }

  @Test
  void shouldThrowExceptionWhenForwardedMessageNotFound() {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamId("stream123");
    activity.setTo(to);

    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setMessageId("nonexistentMsg");
    activity.setAttachments(Collections.singletonList(attachment));

    when(context.getVariables()).thenReturn(new HashMap<>());
    when(messageService.getMessage("nonexistentMsg")).thenReturn(null);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Message with id nonexistentMsg not found");
  }

  @Test
  void shouldThrowExceptionWhenForwardedAttachmentIdNotFound() {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamId("stream123");
    activity.setTo(to);

    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setMessageId("originalMsg");
    attachment.setAttachmentId("nonexistentAttach");
    activity.setAttachments(Collections.singletonList(attachment));

    when(context.getVariables()).thenReturn(new HashMap<>());

    V4Message originalMessage = new V4Message();
    originalMessage.setMessageId("originalMsg");
    V4Stream stream = new V4Stream();
    stream.setStreamId("originalStream");
    originalMessage.setStream(stream);

    V4AttachmentInfo attachmentInfo = new V4AttachmentInfo();
    attachmentInfo.setId("differentAttach");
    attachmentInfo.setName("file.pdf");
    originalMessage.setAttachments(Collections.singletonList(attachmentInfo));

    when(messageService.getMessage("originalMsg")).thenReturn(originalMessage);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("No attachment with id nonexistentAttach");
  }

  @Test
  void shouldThrowExceptionWhenRequestedMessageHasNoAttachments() {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamId("stream123");
    activity.setTo(to);

    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setMessageId("originalMsg");
    attachment.setAttachmentId("attach1");
    activity.setAttachments(Collections.singletonList(attachment));

    when(context.getVariables()).thenReturn(new HashMap<>());

    V4Message originalMessage = new V4Message();
    originalMessage.setMessageId("originalMsg");
    originalMessage.setAttachments(null);

    when(messageService.getMessage("originalMsg")).thenReturn(originalMessage);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("No attachment in requested message");
  }

  @Test
  void shouldHandleMultipleAttachmentsFromForwardedMessage() throws IOException {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamId("stream123");
    activity.setTo(to);

    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setMessageId("originalMsg");
    activity.setAttachments(Collections.singletonList(attachment));

    when(context.getVariables()).thenReturn(new HashMap<>());

    V4Message originalMessage = new V4Message();
    originalMessage.setMessageId("originalMsg");
    V4Stream stream = new V4Stream();
    stream.setStreamId("originalStream");
    originalMessage.setStream(stream);

    V4AttachmentInfo attachment1 = new V4AttachmentInfo();
    attachment1.setId("attach1");
    attachment1.setName("file1.pdf");

    V4AttachmentInfo attachment2 = new V4AttachmentInfo();
    attachment2.setId("attach2");
    attachment2.setName("file2.pdf");

    originalMessage.setAttachments(Arrays.asList(attachment1, attachment2));

    when(messageService.getMessage("originalMsg")).thenReturn(originalMessage);
    byte[] attachmentBytes1 = Base64.getEncoder().encode("content1".getBytes());
    byte[] attachmentBytes2 = Base64.getEncoder().encode("content2".getBytes());
    when(messageService.getAttachment("originalStream", "originalMsg", "attach1")).thenReturn(attachmentBytes1);
    when(messageService.getAttachment("originalStream", "originalMsg", "attach2")).thenReturn(attachmentBytes2);

    V4Message expectedMessage = new V4Message();
    expectedMessage.setMessageId("msg123");
    when(messageService.send(eq("stream123"), any(Message.class))).thenReturn(expectedMessage);

    executor.execute(context);

    verify(messageService).getAttachment("originalStream", "originalMsg", "attach1");
    verify(messageService).getAttachment("originalStream", "originalMsg", "attach2");
  }

  @Test
  void shouldHandleOboWithUserId() throws IOException {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamId("stream123");
    activity.setTo(to);
    Obo obo = new Obo();
    obo.setUserId(12345L);
    activity.setObo(obo);
    when(context.getVariables()).thenReturn(new HashMap<>());

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    MessageService oboMessageService = mock(MessageService.class);

    when(bdkGateway.obo(eq(12345L))).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.messages()).thenReturn(oboMessageService);

    V4Message expectedMessage = new V4Message();
    expectedMessage.setMessageId("msg123");
    when(oboMessageService.send(eq("stream123"), any(Message.class))).thenReturn(expectedMessage);

    executor.execute(context);

    verify(bdkGateway).obo(eq(12345L));
    verify(oboMessageService).send(eq("stream123"), any(Message.class));
  }

  @Test
  void shouldThrowExceptionInOboWithCacheWhenNoStreamIds() {
    activity.setContent("Test message");
    activity.setTo(new SendMessage.To());
    Obo obo = new Obo();
    obo.setUsername("obo-user");
    activity.setObo(obo);
    when(context.getVariables()).thenReturn(new HashMap<>());

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No stream id set");
  }

  @Test
  void shouldSetOutputVariablesCorrectlyWithSingleMessage() throws IOException {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamId("stream123");
    activity.setTo(to);
    when(context.getVariables()).thenReturn(new HashMap<>());

    V4Message expectedMessage = new V4Message();
    expectedMessage.setMessageId("msg123");
    when(messageService.send(eq("stream123"), any(Message.class))).thenReturn(expectedMessage);

    executor.execute(context);

    verify(context).setOutputVariables(any(Map.class));
  }

  @Test
  void shouldHandleNullAttachmentPathInFileAttachment() throws IOException {
    activity.setContent("Test message");
    SendMessage.To to = new SendMessage.To();
    to.setStreamId("stream123");
    activity.setTo(to);

    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setContentPath(null);
    activity.setAttachments(Collections.singletonList(attachment));

    when(context.getVariables()).thenReturn(new HashMap<>());

    V4Message expectedMessage = new V4Message();
    expectedMessage.setMessageId("msg123");
    when(messageService.send(eq("stream123"), any(Message.class))).thenReturn(expectedMessage);

    executor.execute(context);

    verify(messageService).send(eq("stream123"), any(Message.class));
  }
}
