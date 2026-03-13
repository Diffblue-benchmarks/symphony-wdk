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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static com.symphony.bdk.workflow.engine.executor.message.PinMessageExecutor.IM;
import static com.symphony.bdk.workflow.engine.executor.message.PinMessageExecutor.ROOM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UnpinMessageExecutorTest {

  private UnpinMessageExecutor executor;
  private ActivityExecutorContext<UnpinMessage> context;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private UnpinMessage activity;

  @BeforeEach
  void setUp() {
    executor = new UnpinMessageExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);
    activity = new UnpinMessage();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
  }

  @Test
  void shouldUnpinMessageWithOboUsingUsername() {
    Obo obo = new Obo();
    obo.setUsername("testUser");
    activity.setObo(obo);
    activity.setStreamId("testStreamId123");

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo("testUser")).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    Void result = executor.doOboWithCache(context);

    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq("testStreamId123"), captor.capture());

    V3RoomAttributes capturedAttributes = captor.getValue();
    assertThat(capturedAttributes.getPinnedMessageId()).isEqualTo("");
    assertThat(result).isNull();
  }

  @Test
  void shouldUnpinMessageWithOboUsingUserId() {
    Obo obo = new Obo();
    obo.setUserId(99999L);
    activity.setObo(obo);
    activity.setStreamId("anotherStreamId456");

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo(99999L)).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    Void result = executor.doOboWithCache(context);

    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq("anotherStreamId456"), captor.capture());

    V3RoomAttributes capturedAttributes = captor.getValue();
    assertThat(capturedAttributes.getPinnedMessageId()).isEqualTo("");
    assertThat(result).isNull();
  }

  @Test
  void shouldThrowExceptionWhenUnpinIMWithObo() {
    Obo obo = new Obo();
    obo.setUsername("testUser");
    activity.setObo(obo);
    activity.setId("activity123");
    activity.setStreamId("imStreamId");

    V2StreamAttributes streamAttributes = mock(V2StreamAttributes.class);
    V2StreamType streamType = mock(V2StreamType.class);
    when(streamType.getType()).thenReturn(IM);
    when(streamAttributes.getStreamType()).thenReturn(streamType);

    when(bdkGateway.streams()).thenReturn(streamService);
    when(streamService.getStream("imStreamId")).thenReturn(streamAttributes);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unpin instant message, in activity activity123, is not OBO enabled");
  }

  @Test
  void shouldUnpinInstantMessageWithoutObo() throws Exception {
    activity.setStreamId("imStreamId");

    V2StreamAttributes streamAttributes = mock(V2StreamAttributes.class);
    V2StreamType streamType = mock(V2StreamType.class);
    when(streamType.getType()).thenReturn(IM);
    when(streamAttributes.getStreamType()).thenReturn(streamType);

    when(bdkGateway.streams()).thenReturn(streamService);
    when(streamService.getStream("imStreamId")).thenReturn(streamAttributes);

    executor.execute(context);

    ArgumentCaptor<V1IMAttributes> captor = ArgumentCaptor.forClass(V1IMAttributes.class);
    verify(streamService).updateInstantMessage(eq("imStreamId"), captor.capture());

    V1IMAttributes capturedAttributes = captor.getValue();
    assertThat(capturedAttributes.getPinnedMessageId()).isEqualTo("");
  }

  @Test
  void shouldUnpinRoomMessageWithObo() throws Exception {
    Obo obo = new Obo();
    obo.setUsername("testUser");
    activity.setObo(obo);
    activity.setStreamId("roomStreamId");

    V2StreamAttributes streamAttributes = mock(V2StreamAttributes.class);
    V2StreamType streamType = mock(V2StreamType.class);
    when(streamType.getType()).thenReturn(ROOM);
    when(streamAttributes.getStreamType()).thenReturn(streamType);

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo("testUser")).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    when(bdkGateway.streams()).thenReturn(streamService);
    when(streamService.getStream("roomStreamId")).thenReturn(streamAttributes);

    executor.execute(context);

    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboStreamService).updateRoom(eq("roomStreamId"), captor.capture());

    V3RoomAttributes capturedAttributes = captor.getValue();
    assertThat(capturedAttributes.getPinnedMessageId()).isEqualTo("");
  }

  @Test
  void shouldUnpinRoomMessageWithoutObo() throws Exception {
    activity.setStreamId("roomStreamId");

    V2StreamAttributes streamAttributes = mock(V2StreamAttributes.class);
    V2StreamType streamType = mock(V2StreamType.class);
    when(streamType.getType()).thenReturn(ROOM);
    when(streamAttributes.getStreamType()).thenReturn(streamType);

    when(bdkGateway.streams()).thenReturn(streamService);
    when(streamService.getStream("roomStreamId")).thenReturn(streamAttributes);

    executor.execute(context);

    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(eq("roomStreamId"), captor.capture());

    V3RoomAttributes capturedAttributes = captor.getValue();
    assertThat(capturedAttributes.getPinnedMessageId()).isEqualTo("");
  }

  @Test
  void shouldThrowExceptionWhenStreamTypeIsNotSupported() {
    activity.setId("activity456");
    activity.setStreamId("unknownStreamId");

    V2StreamAttributes streamAttributes = mock(V2StreamAttributes.class);
    V2StreamType streamType = mock(V2StreamType.class);
    when(streamType.getType()).thenReturn("UNKNOWN_TYPE");
    when(streamAttributes.getStreamType()).thenReturn(streamType);

    when(bdkGateway.streams()).thenReturn(streamService);
    when(streamService.getStream("unknownStreamId")).thenReturn(streamAttributes);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unable to unpin message in stream type UNKNOWN_TYPE in activity activity456");
  }
}
