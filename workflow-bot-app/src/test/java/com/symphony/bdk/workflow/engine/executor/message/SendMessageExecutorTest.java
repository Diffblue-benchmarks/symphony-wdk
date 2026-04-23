package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.message.OboMessageService;
import com.symphony.bdk.core.service.message.model.Message;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.Stream;
import com.symphony.bdk.gen.api.model.V4AttachmentInfo;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.gen.api.model.V4MessageBlastResponse;
import com.symphony.bdk.gen.api.model.V4MessageSent;
import com.symphony.bdk.gen.api.model.V4Stream;
import com.symphony.bdk.gen.api.model.V4SymphonyElementsAction;
import com.symphony.bdk.gen.api.model.V4UserJoinedRoom;
import com.symphony.bdk.http.api.ApiRuntimeException;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.message.SendMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendMessageExecutorTest {

  @InjectMocks
  private SendMessageExecutor executor;

  @Mock
  private ActivityExecutorContext<SendMessage> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private MessageService messageService;

  @Mock
  private StreamService streamService;

  private SendMessage activityWithContent(String streamId) {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello World");
    if (streamId != null) {
      SendMessage.To to = new SendMessage.To();
      to.setStreamId(streamId);
      activity.setTo(to);
    }
    return activity;
  }

  @Test
  void shouldSendMessageToSingleStreamId() throws IOException {
    SendMessage activity = activityWithContent("stream1");
    V4Message sentMessage = new V4Message().messageId("msg1");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.send(eq("stream1"), any(Message.class))).thenReturn(sentMessage);

    executor.execute(context);

    verify(messageService).send(eq("stream1"), any(Message.class));
    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());
    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs.get(SendMessageExecutor.OUTPUT_MESSAGE_KEY)).isEqualTo(sentMessage);
    assertThat(outputs.get(SendMessageExecutor.OUTPUT_MESSAGE_ID_KEY)).isEqualTo("msg1");
  }

  @Test
  void shouldSendMessageToMultipleStreamIds() throws IOException {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");
    SendMessage.To to = new SendMessage.To();
    to.setStreamIds(List.of("stream1", "stream2"));
    activity.setTo(to);

    V4Message msg1 = new V4Message().messageId("msg1");
    V4MessageBlastResponse blastResponse = new V4MessageBlastResponse();
    blastResponse.setMessages(List.of(msg1));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.send(anyList(), any(Message.class))).thenReturn(blastResponse);

    executor.execute(context);

    verify(messageService).send(eq(List.of("stream1", "stream2")), any(Message.class));
    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());
    Map<String, Object> outputs = outputCaptor.getValue();
    assertThat(outputs.get(SendMessageExecutor.OUTPUT_MESSAGE_KEY)).isEqualTo(msg1);
  }

  @Test
  void shouldSendMessageToUserIds() throws IOException {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");
    SendMessage.To to = new SendMessage.To();
    to.setUserIds(List.of(123L));
    activity.setTo(to);

    V4Message sentMessage = new V4Message().messageId("msg1");
    Stream stream = new Stream().id("streamFromUser");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(streamService.create(List.of(123L))).thenReturn(stream);
    when(messageService.send(eq("streamFromUser"), any(Message.class))).thenReturn(sentMessage);

    executor.execute(context);

    verify(streamService).create(List.of(123L));
    verify(messageService).send(eq("streamFromUser"), any(Message.class));
  }

  @Test
  void shouldSendMessageFromV4MessageSentEvent() throws IOException {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");

    V4Stream v4Stream = new V4Stream().streamId("stream-from-event");
    V4Message eventMsg = new V4Message();
    eventMsg.setStream(v4Stream);
    V4MessageSent messageSent = new V4MessageSent().message(eventMsg);
    EventHolder<Object> event = new EventHolder<>(null, messageSent, null);

    V4Message sentMessage = new V4Message().messageId("msg1");

    when(context.getActivity()).thenReturn(activity);
    when(context.getEvent()).thenReturn(event);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.send(eq("stream-from-event"), any(Message.class))).thenReturn(sentMessage);

    executor.execute(context);

    verify(messageService).send(eq("stream-from-event"), any(Message.class));
  }

  @Test
  void shouldSendMessageFromV4SymphonyElementsActionEvent() throws IOException {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");

    V4Stream v4Stream = new V4Stream().streamId("elements-stream");
    V4SymphonyElementsAction elementsAction = new V4SymphonyElementsAction().stream(v4Stream);
    EventHolder<Object> event = new EventHolder<>(null, elementsAction, null);

    V4Message sentMessage = new V4Message().messageId("msg1");

    when(context.getActivity()).thenReturn(activity);
    when(context.getEvent()).thenReturn(event);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.send(eq("elements-stream"), any(Message.class))).thenReturn(sentMessage);

    executor.execute(context);

    verify(messageService).send(eq("elements-stream"), any(Message.class));
  }

  @Test
  void shouldSendMessageFromV4UserJoinedRoomEvent() throws IOException {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");

    V4Stream v4Stream = new V4Stream().streamId("room-stream");
    V4UserJoinedRoom userJoinedRoom = new V4UserJoinedRoom().stream(v4Stream);
    EventHolder<Object> event = new EventHolder<>(null, userJoinedRoom, null);

    V4Message sentMessage = new V4Message().messageId("msg1");

    when(context.getActivity()).thenReturn(activity);
    when(context.getEvent()).thenReturn(event);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.send(eq("room-stream"), any(Message.class))).thenReturn(sentMessage);

    executor.execute(context);

    verify(messageService).send(eq("room-stream"), any(Message.class));
  }

  @Test
  void shouldThrowWhenNoStreamIdSetAndNoEvent() {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(context.getEvent()).thenReturn(null);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No stream id set to send a message in activity send-message");
  }

  @Test
  void shouldThrowWhenStreamIdsIsEmpty() throws IOException {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");
    SendMessage.To to = new SendMessage.To();
    to.setUserIds(List.of());
    activity.setTo(to);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No stream/user ids set to send a message in activity send-message");
  }

  @Test
  void shouldThrowWhenOboWithMultipleStreams() {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");
    SendMessage.To to = new SendMessage.To();
    to.setStreamIds(List.of("stream1", "stream2"));
    activity.setTo(to);

    Obo obo = new Obo();
    obo.setUsername("oboUser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Blast message, in activity send-message, is not OBO enabled");
  }

  @Test
  void shouldSendMessageOboWithSingleStream() throws IOException {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");
    SendMessage.To to = new SendMessage.To();
    to.setStreamId("obo-stream");
    activity.setTo(to);

    Obo obo = new Obo();
    obo.setUsername("oboUser");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    OboMessageService oboMessageService = mock(OboMessageService.class);
    V4Message sentMessage = new V4Message().messageId("obo-msg1");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.obo("oboUser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.messages()).thenReturn(oboMessageService);
    when(oboMessageService.send(eq("obo-stream"), any(Message.class))).thenReturn(sentMessage);

    executor.execute(context);

    verify(oboMessageService).send(eq("obo-stream"), any(Message.class));
    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());
    assertThat(outputCaptor.getValue().get(SendMessageExecutor.OUTPUT_MESSAGE_ID_KEY)).isEqualTo("obo-msg1");
  }

  @Test
  void shouldThrowWhenBlastMessageAllFailed() {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");
    SendMessage.To to = new SendMessage.To();
    to.setStreamIds(List.of("stream1", "stream2"));
    activity.setTo(to);

    V4MessageBlastResponse blastResponse = new V4MessageBlastResponse();
    blastResponse.setMessages(null);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.send(anyList(), any(Message.class))).thenReturn(blastResponse);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("All messages have failed in activity send-message");
  }

  @Test
  void shouldSetFailedStreamIdsWhenBlastHasErrors() throws IOException {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");
    SendMessage.To to = new SendMessage.To();
    to.setStreamIds(List.of("stream1", "stream2"));
    activity.setTo(to);

    V4Message msg1 = new V4Message().messageId("msg1");
    V4MessageBlastResponse blastResponse = new V4MessageBlastResponse();
    blastResponse.setMessages(List.of(msg1));
    blastResponse.setErrors((Map) Map.of("failed-stream", new Object()));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.send(anyList(), any(Message.class))).thenReturn(blastResponse);

    executor.execute(context);

    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());
    @SuppressWarnings("unchecked")
    List<String> failedStreamIds = (List<String>) outputCaptor.getValue().get(SendMessageExecutor.OUTPUT_FAILED_MESSAGES_KEY);
    assertThat(failedStreamIds).contains("failed-stream");
  }

  @Test
  void shouldIgnore403ExceptionWhenCreatingStream() throws IOException {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");
    SendMessage.To to = new SendMessage.To();
    to.setUserIds(List.of(123L, 456L));
    activity.setTo(to);

    ApiRuntimeException exception403 = mock(ApiRuntimeException.class);
    when(exception403.getCode()).thenReturn(403);

    Stream stream = new Stream().id("stream-456");
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(streamService.create(List.of(123L))).thenThrow(exception403);
    when(streamService.create(List.of(456L))).thenReturn(stream);

    V4Message sentMessage = new V4Message().messageId("msg1");
    when(messageService.send(eq("stream-456"), any(Message.class))).thenReturn(sentMessage);

    executor.execute(context);

    verify(messageService).send(eq("stream-456"), any(Message.class));
  }

  @Test
  void shouldRethrowNon403ExceptionWhenCreatingStream() {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");
    SendMessage.To to = new SendMessage.To();
    to.setUserIds(List.of(123L));
    activity.setTo(to);

    ApiRuntimeException exception500 = mock(ApiRuntimeException.class);
    when(exception500.getCode()).thenReturn(500);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.create(List.of(123L))).thenThrow(exception500);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(ApiRuntimeException.class);
  }

  @Test
  void shouldBuildMessageWithData() throws IOException {
    SendMessage activity = activityWithContent("stream1");
    activity.setData("{\"key\":\"value\"}");

    V4Message sentMessage = new V4Message().messageId("msg1");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.send(eq("stream1"), any(Message.class))).thenReturn(sentMessage);

    executor.execute(context);

    ArgumentCaptor<Message> messageCaptor = ArgumentCaptor.forClass(Message.class);
    verify(messageService).send(eq("stream1"), messageCaptor.capture());
    assertThat(messageCaptor.getValue().getData()).isEqualTo("{\"key\":\"value\"}");
  }

  @Test
  void shouldBuildMessageWithFileAttachment() throws IOException {
    SendMessage activity = activityWithContent("stream1");
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setContentPath("test-file.txt");
    activity.setAttachments(List.of(attachment));

    V4Message sentMessage = new V4Message().messageId("msg1");
    ByteArrayInputStream inputStream = new ByteArrayInputStream("file content".getBytes());

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(context.getResource(any())).thenReturn(inputStream);
    when(messageService.send(eq("stream1"), any(Message.class))).thenReturn(sentMessage);

    executor.execute(context);

    verify(messageService).send(eq("stream1"), any(Message.class));
  }

  @Test
  void shouldNotAddAttachmentWhenContentPathIsNull() throws IOException {
    SendMessage activity = activityWithContent("stream1");
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setContentPath(null);
    activity.setAttachments(List.of(attachment));

    V4Message sentMessage = new V4Message().messageId("msg1");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.send(eq("stream1"), any(Message.class))).thenReturn(sentMessage);

    executor.execute(context);

    verify(context, never()).getResource(any());
  }

  @Test
  void shouldThrowWhenForwardedMessageNotFound() {
    SendMessage activity = activityWithContent("stream1");
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setMessageId("messageToForward");
    activity.setAttachments(List.of(attachment));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.getMessage("messageToForward")).thenReturn(null);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Message with id messageToForward not found");
  }

  @Test
  void shouldThrowWhenAttachmentIdSetButMessageHasNoAttachments() {
    SendMessage activity = activityWithContent("stream1");
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setMessageId("msgId");
    attachment.setAttachmentId("attachId");
    activity.setAttachments(List.of(attachment));

    V4Message actualMessage = new V4Message().messageId("msgId");
    actualMessage.setAttachments(null);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.getMessage("msgId")).thenReturn(actualMessage);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("No attachment in requested message with id msgId");
  }

  @Test
  void shouldThrowWhenAttachmentIdNotFoundInMessage() {
    SendMessage activity = activityWithContent("stream1");
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setMessageId("msgId");
    attachment.setAttachmentId("nonExistentAttachId");
    activity.setAttachments(List.of(attachment));

    V4AttachmentInfo attachmentInfo = new V4AttachmentInfo().id("otherId").name("file.txt");
    V4Message actualMessage = new V4Message().messageId("msgId");
    actualMessage.setAttachments(List.of(attachmentInfo));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.getMessage("msgId")).thenReturn(actualMessage);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("No attachment with id nonExistentAttachId found in message with id msgId");
  }

  @Test
  void shouldForwardSpecificAttachmentWhenAttachmentIdSet() throws IOException {
    SendMessage activity = activityWithContent("stream1");
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setMessageId("msgId");
    attachment.setAttachmentId("attachId");
    activity.setAttachments(List.of(attachment));

    V4AttachmentInfo attachmentInfo = new V4AttachmentInfo().id("attachId").name("file.txt");
    V4Stream stream = new V4Stream().streamId("stream1");
    V4Message actualMessage = new V4Message().messageId("msgId").stream(stream);
    actualMessage.setAttachments(List.of(attachmentInfo));

    byte[] base64Content = java.util.Base64.getEncoder().encode("content".getBytes());
    V4Message sentMessage = new V4Message().messageId("sent1");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.getMessage("msgId")).thenReturn(actualMessage);
    when(messageService.getAttachment("stream1", "msgId", "attachId")).thenReturn(base64Content);
    when(messageService.send(eq("stream1"), any(Message.class))).thenReturn(sentMessage);

    executor.execute(context);

    verify(messageService).getAttachment("stream1", "msgId", "attachId");
    verify(messageService).send(eq("stream1"), any(Message.class));
  }

  @Test
  void shouldForwardAllAttachmentsWhenNoAttachmentId() throws IOException {
    SendMessage activity = activityWithContent("stream1");
    SendMessage.Attachment attachment = new SendMessage.Attachment();
    attachment.setMessageId("msgId");
    activity.setAttachments(List.of(attachment));

    V4AttachmentInfo attach1 = new V4AttachmentInfo().id("a1").name("file1.txt");
    V4AttachmentInfo attach2 = new V4AttachmentInfo().id("a2").name("file2.txt");
    V4Stream stream = new V4Stream().streamId("stream1");
    V4Message actualMessage = new V4Message().messageId("msgId").stream(stream);
    actualMessage.setAttachments(List.of(attach1, attach2));

    byte[] base64Content = java.util.Base64.getEncoder().encode("content".getBytes());
    V4Message sentMessage = new V4Message().messageId("sent1");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.getMessage("msgId")).thenReturn(actualMessage);
    when(messageService.getAttachment(eq("stream1"), eq("msgId"), anyString())).thenReturn(base64Content);
    when(messageService.send(eq("stream1"), any(Message.class))).thenReturn(sentMessage);

    executor.execute(context);

    verify(messageService).getAttachment("stream1", "msgId", "a1");
    verify(messageService).getAttachment("stream1", "msgId", "a2");
  }

  @Test
  void shouldOutputMessageIdsForBlastMessage() throws IOException {
    SendMessage activity = new SendMessage();
    activity.setId("send-message");
    activity.setContent("Hello");
    SendMessage.To to = new SendMessage.To();
    to.setStreamIds(List.of("stream1", "stream2"));
    activity.setTo(to);

    V4Message msg1 = new V4Message().messageId("msgId1");
    V4Message msg2 = new V4Message().messageId("msgId2");
    V4MessageBlastResponse blastResponse = new V4MessageBlastResponse();
    blastResponse.setMessages(List.of(msg1, msg2));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.send(anyList(), any(Message.class))).thenReturn(blastResponse);

    executor.execute(context);

    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());
    Map<String, Object> outputs = outputCaptor.getValue();
    @SuppressWarnings("unchecked")
    List<String> msgIds = (List<String>) outputs.get(SendMessageExecutor.OUTPUT_MESSAGE_IDS_KEY);
    assertThat(msgIds).containsExactly("msgId1", "msgId2");
  }

  @Test
  void shouldOutputMessageIdForSingleMessage() throws IOException {
    SendMessage activity = activityWithContent("stream1");
    V4Message sentMessage = new V4Message().messageId("singleMsgId");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(bdk.messages()).thenReturn(messageService);
    when(messageService.send(eq("stream1"), any(Message.class))).thenReturn(sentMessage);

    executor.execute(context);

    ArgumentCaptor<Map<String, Object>> outputCaptor = ArgumentCaptor.forClass(Map.class);
    verify(context).setOutputVariables(outputCaptor.capture());
    @SuppressWarnings("unchecked")
    List<String> msgIds = (List<String>) outputCaptor.getValue().get(SendMessageExecutor.OUTPUT_MESSAGE_IDS_KEY);
    assertThat(msgIds).containsExactly("singleMsgId");
  }
}
