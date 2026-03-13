package com.symphony.bdk.workflow.engine.executor.group;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.ext.group.gen.api.model.GroupList;
import com.symphony.bdk.ext.group.gen.api.model.SortOrder;
import com.symphony.bdk.ext.group.gen.api.model.Status;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.group.GetGroups;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetGroupsExecutorTest {

  private GetGroupsExecutor executor;
  private ActivityExecutorContext<GetGroups> context;
  private BdkGateway bdkGateway;
  private SymphonyGroupService groupService;
  private GetGroups activity;

  @BeforeEach
  void setUp() {
    executor = new GetGroupsExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    groupService = mock(SymphonyGroupService.class);
    activity = new GetGroups();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.groups()).thenReturn(groupService);
  }

  @Test
  void shouldGetGroupsWithAllParameters() {
    activity.setStatus("ACTIVE");
    activity.setBefore("before-cursor");
    activity.setAfter("after-cursor");
    activity.setLimit(50);
    activity.setSortOrder("ASC");

    GroupList expectedGroups = new GroupList();
    when(groupService.listGroups(any(Status.class), eq("before-cursor"), eq("after-cursor"), eq(50),
        any(SortOrder.class))).thenReturn(expectedGroups);

    executor.execute(context);

    verify(groupService).listGroups(any(Status.class), eq("before-cursor"), eq("after-cursor"), eq(50),
        any(SortOrder.class));
    verify(context).setOutputVariable(eq("groups"), eq(expectedGroups));
  }

  @Test
  void shouldGetGroupsWithNullStatus() {
    activity.setStatus(null);
    activity.setLimit(100);

    GroupList expectedGroups = new GroupList();
    when(groupService.listGroups(eq(null), eq(null), eq(null), eq(100), any())).thenReturn(expectedGroups);

    executor.execute(context);

    verify(groupService).listGroups(eq(null), eq(null), eq(null), eq(100), any());
    verify(context).setOutputVariable(eq("groups"), eq(expectedGroups));
  }

  @Test
  void shouldGetGroupsWithNullSortOrder() {
    activity.setSortOrder(null);
    activity.setLimit(25);

    GroupList expectedGroups = new GroupList();
    when(groupService.listGroups(any(), eq(null), eq(null), eq(25), eq(null))).thenReturn(expectedGroups);

    executor.execute(context);

    verify(groupService).listGroups(any(), eq(null), eq(null), eq(25), eq(null));
    verify(context).setOutputVariable(eq("groups"), eq(expectedGroups));
  }

  @Test
  void shouldGetGroupsWithMinimalParameters() {
    activity.setLimit(10);

    GroupList expectedGroups = new GroupList();
    when(groupService.listGroups(any(), any(), any(), eq(10), any())).thenReturn(expectedGroups);

    executor.execute(context);

    verify(groupService).listGroups(any(), any(), any(), eq(10), any());
    verify(context).setOutputVariable(eq("groups"), eq(expectedGroups));
  }

  @Test
  void shouldGetGroupsWithDescSortOrder() {
    activity.setSortOrder("DESC");
    activity.setLimit(15);

    GroupList expectedGroups = new GroupList();
    when(groupService.listGroups(any(), any(), any(), eq(15), any(SortOrder.class))).thenReturn(expectedGroups);

    executor.execute(context);

    verify(groupService).listGroups(any(), any(), any(), eq(15), any(SortOrder.class));
    verify(context).setOutputVariable(eq("groups"), eq(expectedGroups));
  }
}
