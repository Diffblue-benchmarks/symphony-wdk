package com.symphony.bdk.workflow.engine.executor.group;

import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.ReadGroup;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.group.GetGroup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGroupExecutorTest {

  @InjectMocks
  private GetGroupExecutor executor;

  @Mock
  private ActivityExecutorContext<GetGroup> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private SymphonyGroupService groupService;

  @Test
  void shouldGetGroupAndSetOutputVariableWhenGroupIdProvided() throws Exception {
    // given
    GetGroup activity = new GetGroup();
    activity.setGroupId("group-123");

    ReadGroup readGroup = new ReadGroup();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.groups()).thenReturn(groupService);
    when(groupService.getGroup("group-123")).thenReturn(readGroup);

    // when
    executor.execute(context);

    // then
    verify(context).setOutputVariable("group", readGroup);
  }
}
