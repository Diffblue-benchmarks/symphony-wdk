package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V1IMAttributes;
import com.symphony.bdk.gen.api.model.V3RoomAttributes;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.message.PinMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

@ExtendWith(MockitoExtension.class)
class PinMessageExecutorTest {

  private final PinMessageExecutor underTest = new PinMessageExecutor();

  @Mock
  private BdkGateway bdk;

  @Mock
  private MessageService messageService;

  @Mock
  private StreamService streamService;

  @Mock
  private V4Message v4Message;

  private ActivityExecutorContext<PinMessage> execution;

  @BeforeEach
  void setUp() {
    execution = mock(ActivityExecutorContext.class, RETURNS_DEEP_STUBS);
    when(execution.bdk()).thenReturn(bdk);
    when(bdk.messages()).thenReturn(messageService);
    when(bdk.streams()).thenReturn(streamService);
  }

  @Test
  void shouldThrowExceptionWhenStreamTypeIsImAndOboIsEnabled() {
    PinMessage activity = buildPinMessage("msgId123", "user1", null);
    when(execution.getActivity()).thenReturn(activity);
    when(messageService.getMessage("msgId123")).thenReturn(v4Message);
    when(v4Message.getStream().getStreamId()).thenReturn("streamId1");
    when(v4Message.getStream().getStreamType()).thenReturn(PinMessageExecutor.IM);

    assertThatThrownBy(() -> underTest.execute(execution))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Pin instant message")
        .hasMessageContaining("is not OBO enabled");
  }

  @Test
  void shouldUpdateInstantMessageWhenStreamTypeIsIm() throws IOException {
    PinMessage activity = buildPinMessage("msgId123", null, null);
    when(execution.getActivity()).thenReturn(activity);
    when(messageService.getMessage("msgId123")).thenReturn(v4Message);
    when(v4Message.getStream().getStreamId()).thenReturn("streamId1");
    when(v4Message.getStream().getStreamType()).thenReturn(PinMessageExecutor.IM);

    underTest.execute(execution);

    ArgumentCaptor<V1IMAttributes> captor = ArgumentCaptor.forClass(V1IMAttributes.class);
    verify(streamService).updateInstantMessage(org.mockito.ArgumentMatchers.eq("streamId1"), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo("msgId123");
  }

  @Test
  void shouldDoOboWithCacheWhenStreamTypeIsRoomAndOboIsEnabled() throws IOException {
    PinMessage activity = buildPinMessage("msgId123", "oboUser", null);
    when(execution.getActivity()).thenReturn(activity);
    when(messageService.getMessage("msgId123")).thenReturn(v4Message);
    when(v4Message.getStream().getStreamId()).thenReturn("roomStreamId");
    when(v4Message.getStream().getStreamType()).thenReturn(PinMessageExecutor.ROOM);

    AuthSession authSession = mock(AuthSession.class);
    when(bdk.obo("oboUser")).thenReturn(authSession);
    OboServices oboServices = mock(OboServices.class, RETURNS_DEEP_STUBS);
    when(bdk.obo(authSession)).thenReturn(oboServices);

    underTest.execute(execution);

    verify(oboServices.streams()).updateRoom(org.mockito.ArgumentMatchers.eq("roomStreamId"),
        org.mockito.ArgumentMatchers.any(V3RoomAttributes.class));
  }

  @Test
  void shouldUpdateRoomMessageWhenStreamTypeIsRoom() throws IOException {
    PinMessage activity = buildPinMessage("msgId123", null, null);
    when(execution.getActivity()).thenReturn(activity);
    when(messageService.getMessage("msgId123")).thenReturn(v4Message);
    when(v4Message.getStream().getStreamId()).thenReturn("roomStreamId");
    when(v4Message.getStream().getStreamType()).thenReturn(PinMessageExecutor.ROOM);

    underTest.execute(execution);

    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(org.mockito.ArgumentMatchers.eq("roomStreamId"), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo("msgId123");
  }

  @Test
  void shouldThrowExceptionWhenStreamTypeIsUnknown() {
    PinMessage activity = buildPinMessage("msgId123", null, null);
    activity.setId("activityId");
    when(execution.getActivity()).thenReturn(activity);
    when(messageService.getMessage("msgId123")).thenReturn(v4Message);
    when(v4Message.getStream().getStreamId()).thenReturn("streamId1");
    when(v4Message.getStream().getStreamType()).thenReturn("UNKNOWN");

    assertThatThrownBy(() -> underTest.execute(execution))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unable to pin message in stream type");
  }

  @Test
  void shouldDoOboWithCacheUsingOboUserId() throws Exception {
    PinMessage activity = buildPinMessage("msgId456", null, 12345L);
    when(execution.getActivity()).thenReturn(activity);
    when(messageService.getMessage("msgId456")).thenReturn(v4Message);
    when(v4Message.getStream().getStreamId()).thenReturn("roomStreamId2");
    when(v4Message.getStream().getStreamType()).thenReturn(PinMessageExecutor.ROOM);

    AuthSession authSession = mock(AuthSession.class);
    when(bdk.obo(12345L)).thenReturn(authSession);
    OboServices oboServices = mock(OboServices.class, RETURNS_DEEP_STUBS);
    when(bdk.obo(authSession)).thenReturn(oboServices);

    underTest.execute(execution);

    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboServices.streams()).updateRoom(org.mockito.ArgumentMatchers.eq("roomStreamId2"), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo("msgId456");
  }

  private PinMessage buildPinMessage(String messageId, String oboUsername, Long oboUserId) {
    PinMessage activity = new PinMessage();
    activity.setMessageId(messageId);
    if (oboUsername != null || oboUserId != null) {
      Obo obo = new Obo();
      obo.setUsername(oboUsername);
      obo.setUserId(oboUserId);
      activity.setObo(obo);
    }
    return activity;
  }
}
