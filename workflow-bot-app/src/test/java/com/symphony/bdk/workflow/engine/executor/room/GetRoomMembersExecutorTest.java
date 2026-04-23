package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.MemberInfo;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.room.GetRoomMembers;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetRoomMembersExecutorTest {

  @Test
  void shouldGetRoomMembersAndSetOutputVariable() {
    GetRoomMembersExecutor executor = new GetRoomMembersExecutor();

    GetRoomMembers activity = new GetRoomMembers();
    activity.setStreamId("stream123");

    MemberInfo member = new MemberInfo();
    List<MemberInfo> members = List.of(member);

    StreamService streamService = mock(StreamService.class);
    when(streamService.listRoomMembers("stream123")).thenReturn(members);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.streams()).thenReturn(streamService);

    ActivityExecutorContext<GetRoomMembers> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(streamService).listRoomMembers("stream123");
    verify(context).setOutputVariable("members", members);
  }
}
