package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.MemberInfo;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.room.GetRoomMembers;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetRoomMembersExecutorTest {

  @Test
  void executeShouldRetrieveRoomMembersAndSetOutputVariable() {
    ActivityExecutorContext<GetRoomMembers> context = mock(ActivityExecutorContext.class);
    GetRoomMembers activity = mock(GetRoomMembers.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    StreamService streamService = mock(StreamService.class);
    MemberInfo member1 = mock(MemberInfo.class);
    MemberInfo member2 = mock(MemberInfo.class);
    List<MemberInfo> members = Arrays.asList(member1, member2);
    String streamId = "test-stream-id";

    when(context.getActivity()).thenReturn(activity);
    when(activity.getStreamId()).thenReturn(streamId);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
    when(streamService.listRoomMembers(streamId)).thenReturn(members);

    GetRoomMembersExecutor executor = new GetRoomMembersExecutor();
    executor.execute(context);

    verify(streamService).listRoomMembers(streamId);
    verify(context).setOutputVariable("members", members);
  }
}
