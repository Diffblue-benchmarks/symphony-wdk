package com.symphony.bdk.workflow.engine.executor.stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.ArgumentCaptor;

import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V2MembershipList;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetStreamMembers;

import org.junit.jupiter.api.Test;

class GetStreamMembersExecutorTest {

  private final GetStreamMembersExecutor underTest = new GetStreamMembersExecutor();

  @Test
  @SuppressWarnings("unchecked")
  void shouldGetStreamMembersWithoutPagination() {
    ActivityExecutorContext<GetStreamMembers> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    StreamService streamService = mock(StreamService.class);
    V2MembershipList members = new V2MembershipList();

    GetStreamMembers activity = new GetStreamMembers();
    activity.setStreamId("streamId123");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.listStreamMembers("streamId123")).thenReturn(members);

    underTest.execute(context);

    verify(streamService).listStreamMembers("streamId123");
    verify(context).setOutputVariable("members", members);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldGetStreamMembersWithPagination() {
    ActivityExecutorContext<GetStreamMembers> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    StreamService streamService = mock(StreamService.class);
    V2MembershipList members = new V2MembershipList();

    GetStreamMembers activity = new GetStreamMembers();
    activity.setStreamId("streamId456");
    activity.setLimit(10);
    activity.setSkip(5);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.listStreamMembers(eq("streamId456"), any(PaginationAttribute.class))).thenReturn(members);

    underTest.execute(context);

    ArgumentCaptor<PaginationAttribute> paginationCaptor = ArgumentCaptor.forClass(PaginationAttribute.class);
    verify(streamService).listStreamMembers(eq("streamId456"), paginationCaptor.capture());
    assertThat(paginationCaptor.getValue().getSkip()).isEqualTo(5);
    assertThat(paginationCaptor.getValue().getLimit()).isEqualTo(10);
    verify(context).setOutputVariable("members", members);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldThrowWhenOnlyLimitIsSet() {
    ActivityExecutorContext<GetStreamMembers> context = mock(ActivityExecutorContext.class);

    GetStreamMembers activity = new GetStreamMembers();
    activity.setStreamId("streamId789");
    activity.setId("activity1");
    activity.setLimit(10);

    when(context.getActivity()).thenReturn(activity);

    assertThatThrownBy(() -> underTest.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("activity1");
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldThrowWhenOnlySkipIsSet() {
    ActivityExecutorContext<GetStreamMembers> context = mock(ActivityExecutorContext.class);

    GetStreamMembers activity = new GetStreamMembers();
    activity.setStreamId("streamIdABC");
    activity.setId("activity2");
    activity.setSkip(5);

    when(context.getActivity()).thenReturn(activity);

    assertThatThrownBy(() -> underTest.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("activity2");
  }
}
