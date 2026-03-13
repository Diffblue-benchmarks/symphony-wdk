package com.symphony.bdk.workflow.engine.executor.stream;

import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V2MembershipList;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetStreamMembers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetStreamMembersExecutorTest {

  @Mock
  private ActivityExecutorContext<GetStreamMembers> context;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private StreamService streamService;

  @Mock
  private V2MembershipList membershipList;

  private GetStreamMembersExecutor executor;

  @BeforeEach
  void setUp() {
    executor = new GetStreamMembersExecutor();
    lenient().when(context.bdk()).thenReturn(bdkGateway);
    lenient().when(bdkGateway.streams()).thenReturn(streamService);
  }

  @Test
  void shouldGetStreamMembersWithPagination() {
    GetStreamMembers activity = new GetStreamMembers();
    activity.setId("activity-id");
    activity.setStreamId("stream123");
    activity.setLimit(50);
    activity.setSkip(10);
    when(context.getActivity()).thenReturn(activity);
    when(streamService.listStreamMembers(eq("stream123"), any(PaginationAttribute.class)))
        .thenReturn(membershipList);

    executor.execute(context);

    verify(streamService).listStreamMembers(eq("stream123"), any(PaginationAttribute.class));
    verify(context).setOutputVariable("members", membershipList);
  }

  @Test
  void shouldGetStreamMembersWithoutPagination() {
    GetStreamMembers activity = new GetStreamMembers();
    activity.setId("activity-id");
    activity.setStreamId("stream456");
    activity.setLimit(null);
    activity.setSkip(null);
    when(context.getActivity()).thenReturn(activity);
    when(streamService.listStreamMembers("stream456")).thenReturn(membershipList);

    executor.execute(context);

    verify(streamService).listStreamMembers("stream456");
    verify(context).setOutputVariable("members", membershipList);
  }

  @Test
  void shouldThrowExceptionWhenOnlySkipIsSet() {
    GetStreamMembers activity = new GetStreamMembers();
    activity.setId("activity-id");
    activity.setStreamId("stream789");
    activity.setSkip(10);
    activity.setLimit(null);
    when(context.getActivity()).thenReturn(activity);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set to get stream members in activity activity-id");
  }

  @Test
  void shouldThrowExceptionWhenOnlyLimitIsSet() {
    GetStreamMembers activity = new GetStreamMembers();
    activity.setId("activity-id");
    activity.setStreamId("stream789");
    activity.setSkip(null);
    activity.setLimit(50);
    when(context.getActivity()).thenReturn(activity);

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set to get stream members in activity activity-id");
  }
}
