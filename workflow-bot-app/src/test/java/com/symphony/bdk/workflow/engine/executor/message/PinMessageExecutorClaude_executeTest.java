package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.StreamType;
import com.symphony.bdk.gen.api.model.V1IMAttributes;
import com.symphony.bdk.gen.api.model.V3RoomAttributes;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.gen.api.model.V4Stream;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.message.PinMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PinMessageExecutorClaude_executeTest {

  private PinMessageExecutor executor;
  private ActivityExecutorContext<PinMessage> context;
  private PinMessage activity;
  private BdkGateway bdkGateway;
  private MessageService messageService;
  private StreamService streamService;
  private V4Message message;
  private V4Stream stream;

  @BeforeEach
  void setUp() {
    executor = new PinMessageExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new PinMessage();
    bdkGateway = mock(BdkGateway.class);
    messageService = mock(MessageService.class);
    streamService = mock(StreamService.class);
    message = new V4Message();
    stream = new V4Stream();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.messages()).thenReturn(messageService);
    when(bdkGateway.streams()).thenReturn(streamService);

    message.setStream(stream);
  }

  // Tests for pinning IM messages (non-OBO)

  @Test
  void execute_withIMMessage_shouldPinMessageInInstantMessage() throws IOException {
    // Given: An IM message to pin
    String messageId = "msg123";
    String streamId = "im-stream-123";
    activity.setMessageId(messageId);
    activity.setId("pinActivity1");

    stream.setStreamId(streamId);
    stream.setStreamType(StreamType.TypeEnum.IM.getValue());

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update IM with pinned message
    ArgumentCaptor<V1IMAttributes> captor = ArgumentCaptor.forClass(V1IMAttributes.class);
    verify(streamService).updateInstantMessage(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo(messageId);
  }

  @Test
  void execute_withIMMessageDifferentIds_shouldPinCorrectMessage() throws IOException {
    // Given: A different IM message to pin
    String messageId = "msg-xyz-789";
    String streamId = "im-stream-xyz";
    activity.setMessageId(messageId);
    activity.setId("pinActivity2");

    stream.setStreamId(streamId);
    stream.setStreamType(StreamType.TypeEnum.IM.getValue());

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update correct IM with correct pinned message
    ArgumentCaptor<V1IMAttributes> captor = ArgumentCaptor.forClass(V1IMAttributes.class);
    verify(streamService).updateInstantMessage(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo(messageId);
  }

  // Tests for pinning Room messages (non-OBO)

  @Test
  void execute_withRoomMessage_shouldPinMessageInRoom() throws IOException {
    // Given: A room message to pin
    String messageId = "msg456";
    String streamId = "room-stream-456";
    activity.setMessageId(messageId);
    activity.setId("pinActivity3");

    stream.setStreamId(streamId);
    stream.setStreamType(StreamType.TypeEnum.ROOM.getValue());

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update room with pinned message
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo(messageId);
  }

  @Test
  void execute_withRoomMessageDifferentIds_shouldPinCorrectMessage() throws IOException {
    // Given: A different room message to pin
    String messageId = "msg-abc-999";
    String streamId = "room-stream-abc";
    activity.setMessageId(messageId);
    activity.setId("pinActivity4");

    stream.setStreamId(streamId);
    stream.setStreamType(StreamType.TypeEnum.ROOM.getValue());

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update correct room with correct pinned message
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo(messageId);
  }

  // Tests for pinning Room messages with OBO

  @Test
  void execute_withRoomMessageAndOboUsername_shouldPinMessageWithObo() throws IOException {
    // Given: A room message to pin with OBO enabled via username
    String messageId = "msg789";
    String streamId = "room-stream-789";
    String username = "obo-user";
    activity.setMessageId(messageId);
    activity.setId("pinActivity5");

    Obo obo = new Obo();
    obo.setUsername(username);
    activity.setObo(obo);

    stream.setStreamId(streamId);
    stream.setStreamType(StreamType.TypeEnum.ROOM.getValue());

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);

    when(messageService.getMessage(messageId)).thenReturn(message);
    when(bdkGateway.obo(username)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update room with pinned message using OBO
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo(messageId);
  }

  @Test
  void execute_withRoomMessageAndOboUserId_shouldPinMessageWithObo() throws IOException {
    // Given: A room message to pin with OBO enabled via userId
    String messageId = "msg101";
    String streamId = "room-stream-101";
    Long userId = 12345L;
    activity.setMessageId(messageId);
    activity.setId("pinActivity6");

    Obo obo = new Obo();
    obo.setUserId(userId);
    activity.setObo(obo);

    stream.setStreamId(streamId);
    stream.setStreamType(StreamType.TypeEnum.ROOM.getValue());

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);

    when(messageService.getMessage(messageId)).thenReturn(message);
    when(bdkGateway.obo(userId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: Execute is called
    executor.execute(context);

    // Then: Should update room with pinned message using OBO
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo(messageId);
  }

  // Tests for error cases

  @Test
  void execute_withIMMessageAndObo_shouldThrowIllegalArgumentException() {
    // Given: An IM message with OBO enabled (not allowed)
    String messageId = "msg202";
    String streamId = "im-stream-202";
    activity.setMessageId(messageId);
    activity.setId("pinActivity7");

    Obo obo = new Obo();
    obo.setUsername("obo-user");
    activity.setObo(obo);

    stream.setStreamId(streamId);
    stream.setStreamType(StreamType.TypeEnum.IM.getValue());

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When/Then: Execute should throw IllegalArgumentException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Pin instant message")
        .hasMessageContaining("pinActivity7")
        .hasMessageContaining("is not OBO enabled");
  }

  @Test
  void execute_withUnsupportedStreamType_shouldThrowIllegalArgumentException() {
    // Given: A message with unsupported stream type
    String messageId = "msg303";
    String streamId = "post-stream-303";
    activity.setMessageId(messageId);
    activity.setId("pinActivity8");

    stream.setStreamId(streamId);
    stream.setStreamType("POST");

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When/Then: Execute should throw IllegalArgumentException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unable to pin message in stream type POST")
        .hasMessageContaining("pinActivity8");
  }

  @Test
  void execute_withNullStreamType_shouldThrowIllegalArgumentException() {
    // Given: A message with null stream type
    String messageId = "msg404";
    String streamId = "null-stream-404";
    activity.setMessageId(messageId);
    activity.setId("pinActivity9");

    stream.setStreamId(streamId);
    stream.setStreamType(null);

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When/Then: Execute should throw IllegalArgumentException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unable to pin message in stream type null")
        .hasMessageContaining("pinActivity9");
  }

  // Tests for additional edge cases

  @Test
  void execute_withSpecialCharactersInIds_shouldHandleCorrectly() throws IOException {
    // Given: Message and stream IDs with special characters
    String messageId = "msg-with-special_chars!@#";
    String streamId = "room-stream-special_123!@#";
    activity.setMessageId(messageId);
    activity.setId("pinActivity10");

    stream.setStreamId(streamId);
    stream.setStreamType(StreamType.TypeEnum.ROOM.getValue());

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo(messageId);
  }

  @Test
  void execute_withLongIds_shouldHandleCorrectly() throws IOException {
    // Given: Very long message and stream IDs
    String messageId = "msg" + "1234567890".repeat(20);
    String streamId = "room" + "9876543210".repeat(20);
    activity.setMessageId(messageId);
    activity.setId("pinActivity11");

    stream.setStreamId(streamId);
    stream.setStreamType(StreamType.TypeEnum.ROOM.getValue());

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();

    // Then: Should complete without exception
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo(messageId);
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() throws IOException {
    // Given: Valid room message setup
    String messageId = "msg555";
    String streamId = "room-stream-555";
    activity.setMessageId(messageId);
    activity.setId("pinActivity12");

    stream.setStreamId(streamId);
    stream.setStreamType(StreamType.TypeEnum.ROOM.getValue());

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(messageService, org.mockito.Mockito.times(2)).getMessage(messageId);
    verify(streamService, org.mockito.Mockito.times(2)).updateRoom(eq(streamId), any(V3RoomAttributes.class));
  }

  @Test
  void execute_withIOExceptionFromGetMessage_shouldPropagateException() {
    // Given: MessageService throws IOException
    String messageId = "msg666";
    activity.setMessageId(messageId);
    activity.setId("pinActivity13");

    when(messageService.getMessage(messageId)).thenThrow(new RuntimeException("Failed to get message"));

    // When/Then: Execute should propagate the exception
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(RuntimeException.class)
        .hasMessageContaining("Failed to get message");
  }

  @Test
  void execute_withRoomAndOboButNullOboObject_shouldUseNonOboPath() throws IOException {
    // Given: Room message with null OBO object (should use non-OBO path)
    String messageId = "msg777";
    String streamId = "room-stream-777";
    activity.setMessageId(messageId);
    activity.setId("pinActivity14");
    activity.setObo(null);

    stream.setStreamId(streamId);
    stream.setStreamType(StreamType.TypeEnum.ROOM.getValue());

    when(messageService.getMessage(messageId)).thenReturn(message);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO path
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo(messageId);
  }

  @Test
  void execute_withRoomAndOboWithBothUsernameAndUserId_shouldUseUsername() throws IOException {
    // Given: Room message with OBO having both username and userId (username takes precedence)
    String messageId = "msg888";
    String streamId = "room-stream-888";
    String username = "primary-user";
    Long userId = 99999L;
    activity.setMessageId(messageId);
    activity.setId("pinActivity15");

    Obo obo = new Obo();
    obo.setUsername(username);
    obo.setUserId(userId);
    activity.setObo(obo);

    stream.setStreamId(streamId);
    stream.setStreamType(StreamType.TypeEnum.ROOM.getValue());

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);

    when(messageService.getMessage(messageId)).thenReturn(message);
    when(bdkGateway.obo(username)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use username for OBO authentication
    verify(bdkGateway).obo(eq(username));
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq(streamId), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo(messageId);
  }
}
