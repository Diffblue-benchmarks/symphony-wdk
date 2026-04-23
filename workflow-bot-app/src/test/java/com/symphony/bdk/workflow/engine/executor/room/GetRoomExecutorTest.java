package com.symphony.bdk.workflow.engine.executor.room;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V3RoomDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.GetRoom;

import org.junit.jupiter.api.Test;

class GetRoomExecutorTest {

  private final GetRoomExecutor underTest = new GetRoomExecutor();

  @Test
  @SuppressWarnings("unchecked")
  void shouldGetRoomWithoutObo() {
    ActivityExecutorContext<GetRoom> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    StreamService streamService = mock(StreamService.class);
    V3RoomDetail roomDetail = new V3RoomDetail();

    GetRoom activity = new GetRoom();
    activity.setStreamId("streamId123");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.getRoomInfo("streamId123")).thenReturn(roomDetail);

    underTest.execute(context);

    verify(streamService).getRoomInfo("streamId123");
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldGetRoomWithOboUsername() {
    ActivityExecutorContext<GetRoom> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService streamService = mock(StreamService.class);
    AuthSession authSession = mock(AuthSession.class);
    V3RoomDetail roomDetail = new V3RoomDetail();

    GetRoom activity = new GetRoom();
    activity.setStreamId("streamId456");
    Obo obo = new Obo();
    obo.setUsername("testuser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("testuser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);
    when(streamService.getRoomInfo("streamId456")).thenReturn(roomDetail);

    underTest.execute(context);

    verify(streamService).getRoomInfo("streamId456");
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldGetRoomWithOboUserId() {
    ActivityExecutorContext<GetRoom> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService streamService = mock(StreamService.class);
    AuthSession authSession = mock(AuthSession.class);
    V3RoomDetail roomDetail = new V3RoomDetail();

    GetRoom activity = new GetRoom();
    activity.setStreamId("streamId789");
    Obo obo = new Obo();
    obo.setUserId(999L);
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo(999L)).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);
    when(streamService.getRoomInfo("streamId789")).thenReturn(roomDetail);

    underTest.execute(context);

    verify(streamService).getRoomInfo("streamId789");
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldReturnRoomDetailFromDoOboWithCache() throws Exception {
    ActivityExecutorContext<GetRoom> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService streamService = mock(StreamService.class);
    AuthSession authSession = mock(AuthSession.class);
    V3RoomDetail roomDetail = new V3RoomDetail();

    GetRoom activity = new GetRoom();
    activity.setStreamId("streamIdObo");
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("obouser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);
    when(streamService.getRoomInfo("streamIdObo")).thenReturn(roomDetail);

    V3RoomDetail result = underTest.doOboWithCache(context);

    assertThat(result).isEqualTo(roomDetail);
  }
}
