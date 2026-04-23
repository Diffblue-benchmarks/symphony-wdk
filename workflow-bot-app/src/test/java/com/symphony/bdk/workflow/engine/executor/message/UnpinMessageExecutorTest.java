package com.symphony.bdk.workflow.engine.executor.message;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V1IMAttributes;
import com.symphony.bdk.gen.api.model.V2StreamAttributes;
import com.symphony.bdk.gen.api.model.V2StreamType;
import com.symphony.bdk.gen.api.model.V3RoomAttributes;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.message.UnpinMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UnpinMessageExecutorTest {

  @Mock
  private ActivityExecutorContext<UnpinMessage> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private StreamService streamService;

  @Mock
  private OboServices oboServices;

  @Mock
  private AuthSession authSession;

  @Mock
  private V2StreamAttributes stream;

  @Mock
  private V2StreamType streamType;

  @Test
  void shouldUnpinInstantMessage() throws Exception {
    UnpinMessage activity = new UnpinMessage();
    activity.setStreamId("stream123");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.getStream("stream123")).thenReturn(stream);
    when(stream.getStreamType()).thenReturn(streamType);
    when(streamType.getType()).thenReturn(PinMessageExecutor.IM);

    new UnpinMessageExecutor().execute(context);

    ArgumentCaptor<V1IMAttributes> captor = ArgumentCaptor.forClass(V1IMAttributes.class);
    verify(streamService).updateInstantMessage(eq("stream123"), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEmpty();
  }

  @Test
  void shouldThrowWhenUnpinInstantMessageWithObo() {
    UnpinMessage activity = new UnpinMessage();
    activity.setStreamId("stream123");
    activity.setId("activity1");
    Obo obo = new Obo();
    obo.setUsername("oboUser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.getStream("stream123")).thenReturn(stream);
    when(stream.getStreamType()).thenReturn(streamType);
    when(streamType.getType()).thenReturn(PinMessageExecutor.IM);

    assertThatThrownBy(() -> new UnpinMessageExecutor().execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("activity1");
  }

  @Test
  void shouldUnpinRoomMessage() throws Exception {
    UnpinMessage activity = new UnpinMessage();
    activity.setStreamId("room456");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.getStream("room456")).thenReturn(stream);
    when(stream.getStreamType()).thenReturn(streamType);
    when(streamType.getType()).thenReturn(PinMessageExecutor.ROOM);

    new UnpinMessageExecutor().execute(context);

    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq("room456"), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEmpty();
  }

  @Test
  void shouldUnpinRoomMessageWithObo() throws Exception {
    UnpinMessage activity = new UnpinMessage();
    activity.setStreamId("room456");
    Obo obo = new Obo();
    obo.setUsername("oboUser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.getStream("room456")).thenReturn(stream);
    when(stream.getStreamType()).thenReturn(streamType);
    when(streamType.getType()).thenReturn(PinMessageExecutor.ROOM);
    when(bdk.obo("oboUser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);

    new UnpinMessageExecutor().execute(context);

    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq("room456"), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEmpty();
  }

  @Test
  void shouldThrowWhenUnknownStreamType() {
    UnpinMessage activity = new UnpinMessage();
    activity.setStreamId("stream789");
    activity.setId("activity2");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.getStream("stream789")).thenReturn(stream);
    when(stream.getStreamType()).thenReturn(streamType);
    when(streamType.getType()).thenReturn("UNKNOWN");

    assertThatThrownBy(() -> new UnpinMessageExecutor().execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("activity2");
  }
}
