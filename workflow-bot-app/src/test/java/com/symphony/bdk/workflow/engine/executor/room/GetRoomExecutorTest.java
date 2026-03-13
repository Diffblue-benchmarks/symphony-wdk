package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V3RoomDetail;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.GetRoom;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetRoomExecutorTest {

  @Test
  void executeShouldRetrieveRoomAndSetOutputVariableWhenNonObo() {
    ActivityExecutorContext<GetRoom> context = mock(ActivityExecutorContext.class);
    GetRoom activity = mock(GetRoom.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    StreamService streamService = mock(StreamService.class);
    V3RoomDetail roomDetail = mock(V3RoomDetail.class);
    String streamId = "test-stream-id";

    when(context.getActivity()).thenReturn(activity);
    when(activity.getStreamId()).thenReturn(streamId);
    when(activity.getObo()).thenReturn(null);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
    when(streamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    GetRoomExecutor executor = new GetRoomExecutor();
    executor.execute(context);

    verify(streamService).getRoomInfo(streamId);
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void executeShouldRetrieveRoomAndSetOutputVariableWhenOboWithUsername() {
    ActivityExecutorContext<GetRoom> context = mock(ActivityExecutorContext.class);
    GetRoom activity = mock(GetRoom.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    Obo obo = mock(Obo.class);
    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService streamService = mock(StreamService.class);
    V3RoomDetail roomDetail = mock(V3RoomDetail.class);
    String streamId = "test-stream-id";
    String username = "test-user";

    when(context.getActivity()).thenReturn(activity);
    when(activity.getStreamId()).thenReturn(streamId);
    when(activity.getObo()).thenReturn(obo);
    when(obo.getUsername()).thenReturn(username);
    when(obo.getUserId()).thenReturn(null);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.obo(username)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);
    when(streamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    GetRoomExecutor executor = new GetRoomExecutor();
    executor.execute(context);

    verify(bdkGateway).obo(username);
    verify(bdkGateway).obo(authSession);
    verify(oboServices).streams();
    verify(streamService).getRoomInfo(streamId);
    verify(context).setOutputVariable("room", roomDetail);
  }

  @Test
  void executeShouldRetrieveRoomAndSetOutputVariableWhenOboWithUserId() {
    ActivityExecutorContext<GetRoom> context = mock(ActivityExecutorContext.class);
    GetRoom activity = mock(GetRoom.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    Obo obo = mock(Obo.class);
    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService streamService = mock(StreamService.class);
    V3RoomDetail roomDetail = mock(V3RoomDetail.class);
    String streamId = "test-stream-id";
    Long userId = 12345L;

    when(context.getActivity()).thenReturn(activity);
    when(activity.getStreamId()).thenReturn(streamId);
    when(activity.getObo()).thenReturn(obo);
    when(obo.getUsername()).thenReturn(null);
    when(obo.getUserId()).thenReturn(userId);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.obo(userId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);
    when(streamService.getRoomInfo(streamId)).thenReturn(roomDetail);

    GetRoomExecutor executor = new GetRoomExecutor();
    executor.execute(context);

    verify(bdkGateway).obo(userId);
    verify(bdkGateway).obo(authSession);
    verify(oboServices).streams();
    verify(streamService).getRoomInfo(streamId);
    verify(context).setOutputVariable("room", roomDetail);
  }
}
