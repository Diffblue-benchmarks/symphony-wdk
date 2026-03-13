package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.group.GetGroup;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetGroupExecutorTest {

  @Test
  void executeShouldRetrieveGroupAndSetOutputVariable() {
    ActivityExecutorContext<GetGroup> context = mock(ActivityExecutorContext.class);
    GetGroup activity = mock(GetGroup.class);
    BdkGateway bdkGateway = mock(BdkGateway.class);
    SymphonyGroupService groupService = mock(SymphonyGroupService.class);
    ReadGroup readGroup = mock(ReadGroup.class);
    String groupId = "test-group-id";

    when(context.getActivity()).thenReturn(activity);
    when(activity.getGroupId()).thenReturn(groupId);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.groups()).thenReturn(groupService);
    when(groupService.getGroup(groupId)).thenReturn(readGroup);

    GetGroupExecutor executor = new GetGroupExecutor();
    executor.execute(context);

    verify(groupService).getGroup(groupId);
    verify(context).setOutputVariable("group", readGroup);
  }
}
