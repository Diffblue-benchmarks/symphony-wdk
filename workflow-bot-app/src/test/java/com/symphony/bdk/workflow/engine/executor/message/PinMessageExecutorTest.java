package com.symphony.bdk.workflow.engine.executor.message;

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
import com.symphony.bdk.core.OboServices;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PinMessageExecutorTest {

  @Mock
  private ActivityExecutorContext<PinMessage> context;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private MessageService messageService;

  @Mock
  private StreamService streamService;

  @Mock
  private V4Message message;

  @Mock
  private V4Stream stream;

  @Mock
  private OboServices oboServices;

  @Mock
  private AuthSession authSession;

  private PinMessageExecutor executor;

  @BeforeEach
  void setUp() {
    executor = new PinMessageExecutor();
    lenient().when(context.bdk()).thenReturn(bdkGateway);
    lenient().when(bdkGateway.messages()).thenReturn(messageService);
    lenient().when(bdkGateway.streams()).thenReturn(streamService);
    lenient().when(message.getStream()).thenReturn(stream);
  }

  @Test
  void shouldPinInstantMessageWithoutObo() throws IOException {
    PinMessage activity = new PinMessage();
    activity.setId("pin-activity-1");
    activity.setMessageId("msg123");

    when(context.getActivity()).thenReturn(activity);
    when(messageService.getMessage("msg123")).thenReturn(message);
    when(stream.getStreamId()).thenReturn("streamId123");
    when(stream.getStreamType()).thenReturn(StreamType.TypeEnum.IM.getValue());

    executor.execute(context);

    ArgumentCaptor<V1IMAttributes> captor = ArgumentCaptor.forClass(V1IMAttributes.class);
    verify(streamService).updateInstantMessage(anyString(), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo("msg123");
  }

  @Test
  void shouldThrowExceptionWhenPinningInstantMessageWithObo() {
    PinMessage activity = new PinMessage();
    activity.setId("pin-activity-1");
    activity.setMessageId("msg123");
    Obo obo = new Obo();
    obo.setUsername("user123");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(messageService.getMessage("msg123")).thenReturn(message);
    when(stream.getStreamId()).thenReturn("streamId123");
    when(stream.getStreamType()).thenReturn(StreamType.TypeEnum.IM.getValue());

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Pin instant message, in activity pin-activity-1, is not OBO enabled");
  }

  @Test
  void shouldPinRoomMessageWithoutObo() throws IOException {
    PinMessage activity = new PinMessage();
    activity.setId("pin-activity-2");
    activity.setMessageId("msg456");

    when(context.getActivity()).thenReturn(activity);
    when(messageService.getMessage("msg456")).thenReturn(message);
    when(stream.getStreamId()).thenReturn("roomId123");
    when(stream.getStreamType()).thenReturn(StreamType.TypeEnum.ROOM.getValue());

    executor.execute(context);

    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(streamService).updateRoom(anyString(), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo("msg456");
  }

  @Test
  void shouldPinRoomMessageWithObo() throws IOException {
    PinMessage activity = new PinMessage();
    activity.setId("pin-activity-3");
    activity.setMessageId("msg789");
    Obo obo = new Obo();
    obo.setUsername("user456");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(messageService.getMessage("msg789")).thenReturn(message);
    when(stream.getStreamId()).thenReturn("roomId456");
    when(stream.getStreamType()).thenReturn(StreamType.TypeEnum.ROOM.getValue());
    when(bdkGateway.obo(anyString())).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);

    executor.execute(context);

    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboServices.streams()).updateRoom(anyString(), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo("msg789");
  }

  @Test
  void shouldThrowExceptionForUnsupportedStreamType() {
    PinMessage activity = new PinMessage();
    activity.setId("pin-activity-4");
    activity.setMessageId("msg999");

    when(context.getActivity()).thenReturn(activity);
    when(messageService.getMessage("msg999")).thenReturn(message);
    when(stream.getStreamId()).thenReturn("unknownStreamId");
    when(stream.getStreamType()).thenReturn("UNKNOWN_TYPE");

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Unable to pin message in stream type UNKNOWN_TYPE in activity pin-activity-4");
  }

  @Test
  void shouldPinRoomMessageWithOboUserId() throws IOException {
    PinMessage activity = new PinMessage();
    activity.setId("pin-activity-5");
    activity.setMessageId("msg111");
    Obo obo = new Obo();
    obo.setUserId(12345L);
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(messageService.getMessage("msg111")).thenReturn(message);
    when(stream.getStreamId()).thenReturn("roomId789");
    when(stream.getStreamType()).thenReturn(StreamType.TypeEnum.ROOM.getValue());
    when(bdkGateway.obo(12345L)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);

    executor.execute(context);

    verify(bdkGateway).obo(12345L);
    ArgumentCaptor<V3RoomAttributes> captor = ArgumentCaptor.forClass(V3RoomAttributes.class);
    verify(oboServices.streams()).updateRoom(anyString(), captor.capture());
    assertThat(captor.getValue().getPinnedMessageId()).isEqualTo("msg111");
  }
}
