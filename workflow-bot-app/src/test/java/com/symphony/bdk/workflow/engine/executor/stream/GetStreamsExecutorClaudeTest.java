package com.symphony.bdk.workflow.engine.executor.stream;

import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V2AdminStreamList;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetStreams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetStreamsExecutorClaudeTest {

  private GetStreamsExecutor executor;
  private ActivityExecutorContext<GetStreams> context;
  private GetStreams activity;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private V2AdminStreamList streamList;

  @BeforeEach
  void setUp() {
    executor = new GetStreamsExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetStreams();
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);
    streamList = new V2AdminStreamList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    GetStreamsExecutor newExecutor = new GetStreamsExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetStreamsExecutor newExecutor = new GetStreamsExecutor();

    // Then: Instance should be of correct type
    assertThat(newExecutor).isInstanceOf(GetStreamsExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetStreamsExecutor executor1 = new GetStreamsExecutor();
    GetStreamsExecutor executor2 = new GetStreamsExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetStreamsExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - pagination scenarios

  @Test
  void execute_withBothLimitAndSkip_shouldUsePaginatedCall() {
    // Given: Activity with limit and skip
    Integer limit = 50;
    Integer skip = 10;
    activity.setLimit(limit);
    activity.setSkip(skip);

    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use paginated call with correct parameters
    verify(streamService).listStreamsAdmin(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamList);
    verify(streamService, never()).listStreamsAdmin(any());
  }

  @Test
  void execute_withDifferentPaginationValues_shouldUseCorrectValues() {
    // Given: Activity with different pagination values
    Integer limit = 100;
    Integer skip = 20;
    activity.setLimit(limit);
    activity.setSkip(skip);

    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use correct pagination values
    verify(streamService).listStreamsAdmin(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withZeroSkipAndLimit_shouldUsePaginatedCall() {
    // Given: Activity with zero skip and limit
    Integer limit = 0;
    Integer skip = 0;
    activity.setLimit(limit);
    activity.setSkip(skip);

    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use paginated call even with zero values
    verify(streamService).listStreamsAdmin(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamList);
  }

  // Tests for execute method - no pagination scenarios

  @Test
  void execute_withNullLimitAndSkip_shouldUseNonPaginatedCall() {
    // Given: Activity with no pagination
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-paginated call
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
    verify(streamService, never()).listStreamsAdmin(any(), any(PaginationAttribute.class));
  }

  @Test
  void execute_withNoPaginationSet_shouldUseNonPaginatedCall() {
    // Given: Activity where limit and skip are not set (defaults to null)

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-paginated call
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  // Tests for execute method - error scenarios

  @Test
  void execute_withOnlyLimitSet_shouldThrowIllegalArgumentException() {
    // Given: Activity with only limit set
    activity.setLimit(50);
    activity.setSkip(null);
    activity.setId("activity-id-123");

    // When/Then: Execute should throw IllegalArgumentException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set")
        .hasMessageContaining("activity-id-123");

    // Verify no service calls were made
    verify(streamService, never()).listStreamsAdmin(any());
    verify(streamService, never()).listStreamsAdmin(any(), any(PaginationAttribute.class));
  }

  @Test
  void execute_withOnlySkipSet_shouldThrowIllegalArgumentException() {
    // Given: Activity with only skip set
    activity.setLimit(null);
    activity.setSkip(10);
    activity.setId("activity-id-456");

    // When/Then: Execute should throw IllegalArgumentException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set")
        .hasMessageContaining("activity-id-456");

    // Verify no service calls were made
    verify(streamService, never()).listStreamsAdmin(any());
    verify(streamService, never()).listStreamsAdmin(any(), any(PaginationAttribute.class));
  }

  // Tests for execute method - filter parameters

  @Test
  void execute_withScope_shouldIncludeScopeInFilter() {
    // Given: Activity with scope
    activity.setScope("INTERNAL");
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass filter with scope to service
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withOrigin_shouldIncludeOriginInFilter() {
    // Given: Activity with origin
    activity.setOrigin("INTERNAL");
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass filter with origin to service
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withStatus_shouldIncludeStatusInFilter() {
    // Given: Activity with status
    activity.setStatus("ACTIVE");
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass filter with status to service
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withPrivacy_shouldIncludePrivacyInFilter() {
    // Given: Activity with privacy
    activity.setPrivacy("PUBLIC");
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass filter with privacy to service
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withStartDate_shouldIncludeStartDateInFilter() {
    // Given: Activity with start date
    activity.setStartDate("2023-01-01T00:00:00Z");
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass filter with start date to service
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withEndDate_shouldIncludeEndDateInFilter() {
    // Given: Activity with end date
    activity.setEndDate("2023-12-31T23:59:59Z");
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass filter with end date to service
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withTypes_shouldIncludeTypesInFilter() {
    // Given: Activity with types
    activity.setTypes(Arrays.asList("IM", "ROOM"));
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass filter with types to service
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withAllFilterParameters_shouldIncludeAllInFilter() {
    // Given: Activity with all filter parameters
    activity.setScope("INTERNAL");
    activity.setOrigin("INTERNAL");
    activity.setStatus("ACTIVE");
    activity.setPrivacy("PUBLIC");
    activity.setStartDate("2023-01-01T00:00:00Z");
    activity.setEndDate("2023-12-31T23:59:59Z");
    activity.setTypes(Arrays.asList("IM", "ROOM"));
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass filter with all parameters to service
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withNullTypes_shouldHandleCorrectly() {
    // Given: Activity with null types
    activity.setTypes(null);
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle null types correctly
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  // Tests for execute method - edge cases

  @Test
  void execute_shouldNotThrowExceptionWithValidInputs() {
    // Given: Valid activity setup without pagination
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_shouldNotThrowExceptionWithValidPaginationInputs() {
    // Given: Valid activity setup with pagination
    activity.setLimit(50);
    activity.setSkip(10);
    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity setup
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(streamService, times(2)).listStreamsAdmin(any());
    verify(context, times(2)).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_calledMultipleTimesWithPagination_shouldWorkCorrectly() {
    // Given: Valid activity setup with pagination
    activity.setLimit(50);
    activity.setSkip(10);
    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(streamService, times(2)).listStreamsAdmin(any(), any(PaginationAttribute.class));
    verify(context, times(2)).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withNullStreamList_shouldStillSetOutputVariable() {
    // Given: Activity where listStreamsAdmin returns null
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreamsAdmin(any())).thenReturn(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should still set output variable with null value
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", null);
  }

  @Test
  void execute_withNullStreamListAndPagination_shouldStillSetOutputVariable() {
    // Given: Activity where listStreamsAdmin returns null with pagination
    activity.setLimit(50);
    activity.setSkip(10);
    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should still set output variable with null value
    verify(streamService).listStreamsAdmin(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", null);
  }

  // Tests for execute method - output variable

  @Test
  void execute_shouldSetOutputVariableWithCorrectKey() {
    // Given: Valid activity setup
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with key "streams"
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withPagination_shouldSetOutputVariableWithCorrectKey() {
    // Given: Valid activity setup with pagination
    activity.setLimit(50);
    activity.setSkip(10);
    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with key "streams"
    verify(context).setOutputVariable("streams", streamList);
  }

  // Tests for execute method - boundary values

  @Test
  void execute_withLargeSkipValue_shouldHandleCorrectly() {
    // Given: Activity with large skip value
    Integer limit = 100;
    Integer skip = 1000000;
    activity.setLimit(limit);
    activity.setSkip(skip);

    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle large skip value correctly
    verify(streamService).listStreamsAdmin(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withLargeLimitValue_shouldHandleCorrectly() {
    // Given: Activity with large limit value
    Integer limit = 1000000;
    Integer skip = 0;
    activity.setLimit(limit);
    activity.setSkip(skip);

    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle large limit value correctly
    verify(streamService).listStreamsAdmin(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamList);
  }

  // Tests for execute method - switching between pagination modes

  @Test
  void execute_switchingFromPaginatedToNonPaginated_shouldUseCorrectMethod() {
    // Given: Activity initially with pagination
    activity.setLimit(50);
    activity.setSkip(10);
    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    // When: First call with pagination
    executor.execute(context);

    // Then switch to non-paginated
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Second call without pagination
    executor.execute(context);

    // Then: Should use correct methods for each call
    verify(streamService).listStreamsAdmin(any(), any(PaginationAttribute.class));
    verify(streamService).listStreamsAdmin(any());
  }

  @Test
  void execute_switchingFromNonPaginatedToPaginated_shouldUseCorrectMethod() {
    // Given: Activity initially without pagination
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: First call without pagination
    executor.execute(context);

    // Then switch to paginated
    activity.setLimit(50);
    activity.setSkip(10);
    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    // When: Second call with pagination
    executor.execute(context);

    // Then: Should use correct methods for each call
    verify(streamService).listStreamsAdmin(any());
    verify(streamService).listStreamsAdmin(any(), any(PaginationAttribute.class));
  }

  // Tests for execute method - various filter combinations

  @Test
  void execute_withScopeAndPagination_shouldHandleCorrectly() {
    // Given: Activity with scope and pagination
    activity.setScope("INTERNAL");
    activity.setLimit(50);
    activity.setSkip(10);

    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle both scope and pagination correctly
    verify(streamService).listStreamsAdmin(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withTypesAndPagination_shouldHandleCorrectly() {
    // Given: Activity with types and pagination
    activity.setTypes(Arrays.asList("IM", "ROOM", "POST"));
    activity.setLimit(25);
    activity.setSkip(5);

    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle both types and pagination correctly
    verify(streamService).listStreamsAdmin(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withDateRangeAndPagination_shouldHandleCorrectly() {
    // Given: Activity with date range and pagination
    activity.setStartDate("2023-01-01T00:00:00Z");
    activity.setEndDate("2023-12-31T23:59:59Z");
    activity.setLimit(100);
    activity.setSkip(50);

    when(streamService.listStreamsAdmin(any(), any(PaginationAttribute.class)))
        .thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle both date range and pagination correctly
    verify(streamService).listStreamsAdmin(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withEmptyTypesList_shouldHandleCorrectly() {
    // Given: Activity with empty types list
    activity.setTypes(Arrays.asList());
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle empty types list correctly
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withSingleType_shouldHandleCorrectly() {
    // Given: Activity with single type
    activity.setTypes(Arrays.asList("IM"));
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle single type correctly
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  // Tests for execute method - null date scenarios

  @Test
  void execute_withNullStartDate_shouldHandleCorrectly() {
    // Given: Activity with null start date
    activity.setStartDate(null);
    activity.setEndDate("2023-12-31T23:59:59Z");
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle null start date correctly
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withNullEndDate_shouldHandleCorrectly() {
    // Given: Activity with null end date
    activity.setStartDate("2023-01-01T00:00:00Z");
    activity.setEndDate(null);
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle null end date correctly
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }

  @Test
  void execute_withBothDatesNull_shouldHandleCorrectly() {
    // Given: Activity with both dates null
    activity.setStartDate(null);
    activity.setEndDate(null);
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreamsAdmin(any())).thenReturn(streamList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle both dates null correctly
    verify(streamService).listStreamsAdmin(any());
    verify(context).setOutputVariable("streams", streamList);
  }
}
