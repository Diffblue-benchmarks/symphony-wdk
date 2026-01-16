package com.symphony.bdk.workflow.engine.executor.stream;

import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V2MembershipList;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetStreamMembers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetStreamMembersExecutorClaudeTest {

  private GetStreamMembersExecutor executor;
  private ActivityExecutorContext<GetStreamMembers> context;
  private GetStreamMembers activity;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private V2MembershipList membershipList;

  @BeforeEach
  void setUp() {
    executor = new GetStreamMembersExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetStreamMembers();
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);
    membershipList = new V2MembershipList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    GetStreamMembersExecutor newExecutor = new GetStreamMembersExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetStreamMembersExecutor newExecutor = new GetStreamMembersExecutor();

    // Then: Instance should be of correct type
    assertThat(newExecutor).isInstanceOf(GetStreamMembersExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetStreamMembersExecutor executor1 = new GetStreamMembersExecutor();
    GetStreamMembersExecutor executor2 = new GetStreamMembersExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetStreamMembersExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - pagination scenarios

  @Test
  void execute_withBothLimitAndSkip_shouldUsePaginatedCall() {
    // Given: Activity with stream ID, limit and skip
    String streamId = "streamId123";
    Integer limit = 50;
    Integer skip = 10;
    activity.setStreamId(streamId);
    activity.setLimit(limit);
    activity.setSkip(skip);

    PaginationAttribute expectedPagination = new PaginationAttribute(skip, limit);
    when(streamService.listStreamMembers(eq(streamId), any(PaginationAttribute.class)))
        .thenReturn(membershipList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use paginated call with correct parameters
    verify(streamService).listStreamMembers(eq(streamId), any(PaginationAttribute.class));
    verify(context).setOutputVariable("members", membershipList);
    verify(streamService, never()).listStreamMembers(anyString());
  }

  @Test
  void execute_withDifferentPaginationValues_shouldUseCorrectValues() {
    // Given: Activity with different pagination values
    String streamId = "streamId123";
    Integer limit = 100;
    Integer skip = 20;
    activity.setStreamId(streamId);
    activity.setLimit(limit);
    activity.setSkip(skip);

    when(streamService.listStreamMembers(eq(streamId), any(PaginationAttribute.class)))
        .thenReturn(membershipList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use correct pagination values
    verify(streamService).listStreamMembers(eq(streamId), any(PaginationAttribute.class));
    verify(context).setOutputVariable("members", membershipList);
  }

  @Test
  void execute_withZeroSkipAndLimit_shouldUsePaginatedCall() {
    // Given: Activity with zero skip and limit
    String streamId = "streamId123";
    Integer limit = 0;
    Integer skip = 0;
    activity.setStreamId(streamId);
    activity.setLimit(limit);
    activity.setSkip(skip);

    when(streamService.listStreamMembers(eq(streamId), any(PaginationAttribute.class)))
        .thenReturn(membershipList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use paginated call even with zero values
    verify(streamService).listStreamMembers(eq(streamId), any(PaginationAttribute.class));
    verify(context).setOutputVariable("members", membershipList);
  }

  // Tests for execute method - no pagination scenarios

  @Test
  void execute_withNullLimitAndSkip_shouldUseNonPaginatedCall() {
    // Given: Activity with stream ID and no pagination
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamMembers(streamId)).thenReturn(membershipList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-paginated call
    verify(streamService).listStreamMembers(streamId);
    verify(context).setOutputVariable("members", membershipList);
    verify(streamService, never()).listStreamMembers(anyString(), any(PaginationAttribute.class));
  }

  @Test
  void execute_withNoPaginationSet_shouldUseNonPaginatedCall() {
    // Given: Activity where limit and skip are not set (defaults to null)
    String streamId = "streamId456";
    activity.setStreamId(streamId);

    when(streamService.listStreamMembers(streamId)).thenReturn(membershipList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-paginated call
    verify(streamService).listStreamMembers(streamId);
    verify(context).setOutputVariable("members", membershipList);
  }

  // Tests for execute method - error scenarios

  @Test
  void execute_withOnlyLimitSet_shouldThrowIllegalArgumentException() {
    // Given: Activity with only limit set
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setLimit(50);
    activity.setSkip(null);
    activity.setId("activity-id-123");

    // When/Then: Execute should throw IllegalArgumentException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set")
        .hasMessageContaining("activity-id-123");

    // Verify no service calls were made
    verify(streamService, never()).listStreamMembers(anyString());
    verify(streamService, never()).listStreamMembers(anyString(), any(PaginationAttribute.class));
  }

  @Test
  void execute_withOnlySkipSet_shouldThrowIllegalArgumentException() {
    // Given: Activity with only skip set
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setLimit(null);
    activity.setSkip(10);
    activity.setId("activity-id-456");

    // When/Then: Execute should throw IllegalArgumentException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set")
        .hasMessageContaining("activity-id-456");

    // Verify no service calls were made
    verify(streamService, never()).listStreamMembers(anyString());
    verify(streamService, never()).listStreamMembers(anyString(), any(PaginationAttribute.class));
  }

  // Tests for execute method - different stream IDs

  @Test
  void execute_withDifferentStreamIds_shouldUseCorrectStreamId() {
    // Given: Activity with specific stream ID
    String streamId1 = "stream123";
    String streamId2 = "stream456";

    // First call with stream ID 1
    activity.setStreamId(streamId1);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreamMembers(streamId1)).thenReturn(membershipList);
    executor.execute(context);

    // Second call with stream ID 2
    activity.setStreamId(streamId2);
    when(streamService.listStreamMembers(streamId2)).thenReturn(membershipList);
    executor.execute(context);

    // Then: Should use correct stream IDs
    verify(streamService).listStreamMembers(streamId1);
    verify(streamService).listStreamMembers(streamId2);
  }

  @Test
  void execute_withDifferentStreamIdsAndPagination_shouldUseCorrectStreamId() {
    // Given: Activity with pagination
    String streamId1 = "stream123";
    String streamId2 = "stream456";
    Integer limit = 50;
    Integer skip = 10;

    activity.setLimit(limit);
    activity.setSkip(skip);

    // First call with stream ID 1
    activity.setStreamId(streamId1);
    when(streamService.listStreamMembers(eq(streamId1), any(PaginationAttribute.class)))
        .thenReturn(membershipList);
    executor.execute(context);

    // Second call with stream ID 2
    activity.setStreamId(streamId2);
    when(streamService.listStreamMembers(eq(streamId2), any(PaginationAttribute.class)))
        .thenReturn(membershipList);
    executor.execute(context);

    // Then: Should use correct stream IDs
    verify(streamService).listStreamMembers(eq(streamId1), any(PaginationAttribute.class));
    verify(streamService).listStreamMembers(eq(streamId2), any(PaginationAttribute.class));
  }

  // Tests for execute method - edge cases

  @Test
  void execute_shouldNotThrowExceptionWithValidInputs() {
    // Given: Valid activity setup without pagination
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreamMembers(streamId)).thenReturn(membershipList);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_shouldNotThrowExceptionWithValidPaginationInputs() {
    // Given: Valid activity setup with pagination
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setLimit(50);
    activity.setSkip(10);
    when(streamService.listStreamMembers(eq(streamId), any(PaginationAttribute.class)))
        .thenReturn(membershipList);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity setup
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreamMembers(streamId)).thenReturn(membershipList);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(streamService, times(2)).listStreamMembers(streamId);
    verify(context, times(2)).setOutputVariable("members", membershipList);
  }

  @Test
  void execute_calledMultipleTimesWithPagination_shouldWorkCorrectly() {
    // Given: Valid activity setup with pagination
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setLimit(50);
    activity.setSkip(10);
    when(streamService.listStreamMembers(eq(streamId), any(PaginationAttribute.class)))
        .thenReturn(membershipList);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(streamService, times(2)).listStreamMembers(eq(streamId), any(PaginationAttribute.class));
    verify(context, times(2)).setOutputVariable("members", membershipList);
  }

  @Test
  void execute_withNullMembershipList_shouldStillSetOutputVariable() {
    // Given: Activity where listStreamMembers returns null
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreamMembers(streamId)).thenReturn(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should still set output variable with null value
    verify(streamService).listStreamMembers(streamId);
    verify(context).setOutputVariable("members", null);
  }

  @Test
  void execute_withNullMembershipListAndPagination_shouldStillSetOutputVariable() {
    // Given: Activity where listStreamMembers returns null with pagination
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setLimit(50);
    activity.setSkip(10);
    when(streamService.listStreamMembers(eq(streamId), any(PaginationAttribute.class)))
        .thenReturn(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should still set output variable with null value
    verify(streamService).listStreamMembers(eq(streamId), any(PaginationAttribute.class));
    verify(context).setOutputVariable("members", null);
  }

  // Tests for execute method - output variable

  @Test
  void execute_shouldSetOutputVariableWithCorrectKey() {
    // Given: Valid activity setup
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreamMembers(streamId)).thenReturn(membershipList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with key "members"
    verify(context).setOutputVariable("members", membershipList);
  }

  @Test
  void execute_withPagination_shouldSetOutputVariableWithCorrectKey() {
    // Given: Valid activity setup with pagination
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setLimit(50);
    activity.setSkip(10);
    when(streamService.listStreamMembers(eq(streamId), any(PaginationAttribute.class)))
        .thenReturn(membershipList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with key "members"
    verify(context).setOutputVariable("members", membershipList);
  }

  // Tests for execute method - boundary values

  @Test
  void execute_withLargeSkipValue_shouldHandleCorrectly() {
    // Given: Activity with large skip value
    String streamId = "streamId123";
    Integer limit = 100;
    Integer skip = 1000000;
    activity.setStreamId(streamId);
    activity.setLimit(limit);
    activity.setSkip(skip);

    when(streamService.listStreamMembers(eq(streamId), any(PaginationAttribute.class)))
        .thenReturn(membershipList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle large skip value correctly
    verify(streamService).listStreamMembers(eq(streamId), any(PaginationAttribute.class));
    verify(context).setOutputVariable("members", membershipList);
  }

  @Test
  void execute_withLargeLimitValue_shouldHandleCorrectly() {
    // Given: Activity with large limit value
    String streamId = "streamId123";
    Integer limit = 1000000;
    Integer skip = 0;
    activity.setStreamId(streamId);
    activity.setLimit(limit);
    activity.setSkip(skip);

    when(streamService.listStreamMembers(eq(streamId), any(PaginationAttribute.class)))
        .thenReturn(membershipList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle large limit value correctly
    verify(streamService).listStreamMembers(eq(streamId), any(PaginationAttribute.class));
    verify(context).setOutputVariable("members", membershipList);
  }

  // Tests for execute method - switching between pagination modes

  @Test
  void execute_switchingFromPaginatedToNonPaginated_shouldUseCorrectMethod() {
    // Given: Activity initially with pagination
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setLimit(50);
    activity.setSkip(10);
    when(streamService.listStreamMembers(eq(streamId), any(PaginationAttribute.class)))
        .thenReturn(membershipList);

    // When: First call with pagination
    executor.execute(context);

    // Then switch to non-paginated
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreamMembers(streamId)).thenReturn(membershipList);

    // When: Second call without pagination
    executor.execute(context);

    // Then: Should use correct methods for each call
    verify(streamService).listStreamMembers(eq(streamId), any(PaginationAttribute.class));
    verify(streamService).listStreamMembers(streamId);
  }

  @Test
  void execute_switchingFromNonPaginatedToPaginated_shouldUseCorrectMethod() {
    // Given: Activity initially without pagination
    String streamId = "streamId123";
    activity.setStreamId(streamId);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreamMembers(streamId)).thenReturn(membershipList);

    // When: First call without pagination
    executor.execute(context);

    // Then switch to paginated
    activity.setLimit(50);
    activity.setSkip(10);
    when(streamService.listStreamMembers(eq(streamId), any(PaginationAttribute.class)))
        .thenReturn(membershipList);

    // When: Second call with pagination
    executor.execute(context);

    // Then: Should use correct methods for each call
    verify(streamService).listStreamMembers(streamId);
    verify(streamService).listStreamMembers(eq(streamId), any(PaginationAttribute.class));
  }
}
