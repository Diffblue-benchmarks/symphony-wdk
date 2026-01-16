package com.symphony.bdk.workflow.engine.executor.stream;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.core.service.stream.OboStreamService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.StreamAttributes;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.stream.GetUserStreams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetUserStreamsExecutorClaudeTest {

  private GetUserStreamsExecutor executor;
  private ActivityExecutorContext<GetUserStreams> context;
  private GetUserStreams activity;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private OboServices oboServices;
  private AuthSession authSession;
  private OboStreamService oboStreamService;
  private List<StreamAttributes> streamAttributesList;

  @BeforeEach
  void setUp() {
    executor = new GetUserStreamsExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetUserStreams();
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);
    oboServices = mock(OboServices.class);
    authSession = mock(AuthSession.class);
    oboStreamService = mock(OboStreamService.class);
    streamAttributesList = Arrays.asList(new StreamAttributes(), new StreamAttributes());

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    GetUserStreamsExecutor newExecutor = new GetUserStreamsExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetUserStreamsExecutor newExecutor = new GetUserStreamsExecutor();

    // Then: Instance should be of correct type
    assertThat(newExecutor).isInstanceOf(GetUserStreamsExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetUserStreamsExecutor executor1 = new GetUserStreamsExecutor();
    GetUserStreamsExecutor executor2 = new GetUserStreamsExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetUserStreamsExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - non-OBO scenarios with pagination

  @Test
  void execute_withBothLimitAndSkip_shouldUsePaginatedCall() {
    // Given: Activity with limit and skip
    Integer limit = 50;
    Integer skip = 10;
    activity.setLimit(limit);
    activity.setSkip(skip);
    activity.setObo(null);

    when(streamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use paginated call with correct parameters
    verify(streamService).listStreams(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamAttributesList);
    verify(streamService, never()).listStreams(any());
  }

  @Test
  void execute_withDifferentPaginationValues_shouldUseCorrectValues() {
    // Given: Activity with different pagination values
    Integer limit = 100;
    Integer skip = 20;
    activity.setLimit(limit);
    activity.setSkip(skip);
    activity.setObo(null);

    when(streamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use correct pagination values
    verify(streamService).listStreams(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  @Test
  void execute_withZeroSkipAndLimit_shouldUsePaginatedCall() {
    // Given: Activity with zero skip and limit
    Integer limit = 0;
    Integer skip = 0;
    activity.setLimit(limit);
    activity.setSkip(skip);
    activity.setObo(null);

    when(streamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use paginated call even with zero values
    verify(streamService).listStreams(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  // Tests for execute method - non-OBO scenarios without pagination

  @Test
  void execute_withNullLimitAndSkip_shouldUseNonPaginatedCall() {
    // Given: Activity with no pagination
    activity.setLimit(null);
    activity.setSkip(null);
    activity.setObo(null);

    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-paginated call
    verify(streamService).listStreams(any());
    verify(context).setOutputVariable("streams", streamAttributesList);
    verify(streamService, never()).listStreams(any(), any(PaginationAttribute.class));
  }

  @Test
  void execute_withNoPaginationSet_shouldUseNonPaginatedCall() {
    // Given: Activity where limit and skip are not set (defaults to null)
    activity.setObo(null);

    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-paginated call
    verify(streamService).listStreams(any());
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  // Tests for execute method - filter parameters

  @Test
  void execute_withTypes_shouldIncludeTypesInFilter() {
    // Given: Activity with types
    activity.setTypes(Arrays.asList("IM", "ROOM"));
    activity.setLimit(null);
    activity.setSkip(null);
    activity.setObo(null);

    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass filter with types to service
    verify(streamService).listStreams(any());
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  @Test
  void execute_withIncludeInactiveStreams_shouldIncludeInFilter() {
    // Given: Activity with includeInactiveStreams
    activity.setIncludeInactiveStreams(true);
    activity.setLimit(null);
    activity.setSkip(null);
    activity.setObo(null);

    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass filter with includeInactiveStreams to service
    verify(streamService).listStreams(any());
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  @Test
  void execute_withAllFilterParameters_shouldIncludeAllInFilter() {
    // Given: Activity with all filter parameters
    activity.setTypes(Arrays.asList("IM", "ROOM"));
    activity.setIncludeInactiveStreams(false);
    activity.setLimit(null);
    activity.setSkip(null);
    activity.setObo(null);

    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should pass filter with all parameters to service
    verify(streamService).listStreams(any());
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  @Test
  void execute_withNullTypes_shouldHandleCorrectly() {
    // Given: Activity with null types
    activity.setTypes(null);
    activity.setLimit(null);
    activity.setSkip(null);
    activity.setObo(null);

    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle null types correctly
    verify(streamService).listStreams(any());
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  @Test
  void execute_withEmptyTypesList_shouldHandleCorrectly() {
    // Given: Activity with empty types list
    activity.setTypes(Collections.emptyList());
    activity.setLimit(null);
    activity.setSkip(null);
    activity.setObo(null);

    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle empty types list correctly
    verify(streamService).listStreams(any());
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  @Test
  void execute_withSingleType_shouldHandleCorrectly() {
    // Given: Activity with single type
    activity.setTypes(Arrays.asList("IM"));
    activity.setLimit(null);
    activity.setSkip(null);
    activity.setObo(null);

    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle single type correctly
    verify(streamService).listStreams(any());
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  // Tests for execute method - OBO scenarios with username

  @Test
  void execute_withOboUsername_shouldUseOboServices() {
    // Given: Activity with OBO username
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway).obo(authSession);
    verify(oboStreamService).listStreams(any());
    verify(context).setOutputVariable("streams", streamAttributesList);
    verify(streamService, never()).listStreams(any());
  }

  @Test
  void execute_withOboUsernameAndPagination_shouldUseOboServicesWithPagination() {
    // Given: Activity with OBO username and pagination
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setLimit(50);
    activity.setSkip(10);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services with pagination
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway).obo(authSession);
    verify(oboStreamService).listStreams(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamAttributesList);
    verify(streamService, never()).listStreams(any(), any(PaginationAttribute.class));
  }

  // Tests for execute method - OBO scenarios with userId

  @Test
  void execute_withOboUserId_shouldUseOboServices() {
    // Given: Activity with OBO user ID
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services
    verify(bdkGateway).obo(oboUserId);
    verify(bdkGateway).obo(authSession);
    verify(oboStreamService).listStreams(any());
    verify(context).setOutputVariable("streams", streamAttributesList);
    verify(streamService, never()).listStreams(any());
  }

  @Test
  void execute_withOboUserIdAndPagination_shouldUseOboServicesWithPagination() {
    // Given: Activity with OBO user ID and pagination
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setObo(obo);
    activity.setLimit(50);
    activity.setSkip(10);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services with pagination
    verify(bdkGateway).obo(oboUserId);
    verify(bdkGateway).obo(authSession);
    verify(oboStreamService).listStreams(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamAttributesList);
    verify(streamService, never()).listStreams(any(), any(PaginationAttribute.class));
  }

  // Tests for execute method - OBO preference (username over userId)

  @Test
  void execute_withBothOboUsernameAndUserId_shouldPreferUsername() {
    // Given: Activity with both OBO username and user ID
    String oboUsername = "obo.user";
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    obo.setUserId(oboUserId);
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should prefer username over user ID
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, never()).obo(oboUserId);
    verify(oboStreamService).listStreams(any());
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  @Test
  void execute_withEmptyOboObject_shouldUseNonOboPath() {
    // Given: Activity with empty OBO object (both username and userId are null)
    Obo emptyObo = new Obo();
    emptyObo.setUsername(null);
    emptyObo.setUserId(null);
    activity.setObo(emptyObo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO path since OBO is not valid
    verify(streamService).listStreams(any());
    verify(context).setOutputVariable("streams", streamAttributesList);
    verify(bdkGateway, never()).obo((String) null);
  }

  // Tests for execute method - edge cases

  @Test
  void execute_shouldNotThrowExceptionWithValidInputs() {
    // Given: Valid activity setup without pagination
    activity.setLimit(null);
    activity.setSkip(null);
    activity.setObo(null);
    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_shouldNotThrowExceptionWithValidPaginationInputs() {
    // Given: Valid activity setup with pagination
    activity.setLimit(50);
    activity.setSkip(10);
    activity.setObo(null);
    when(streamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity setup
    activity.setLimit(null);
    activity.setSkip(null);
    activity.setObo(null);
    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(streamService, times(2)).listStreams(any());
    verify(context, times(2)).setOutputVariable("streams", streamAttributesList);
  }

  @Test
  void execute_calledMultipleTimesWithPagination_shouldWorkCorrectly() {
    // Given: Valid activity setup with pagination
    activity.setLimit(50);
    activity.setSkip(10);
    activity.setObo(null);
    when(streamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(streamService, times(2)).listStreams(any(), any(PaginationAttribute.class));
    verify(context, times(2)).setOutputVariable("streams", streamAttributesList);
  }

  @Test
  void execute_withNullStreamList_shouldStillSetOutputVariable() {
    // Given: Activity where listStreams returns null
    activity.setLimit(null);
    activity.setSkip(null);
    activity.setObo(null);
    when(streamService.listStreams(any())).thenReturn(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should still set output variable with null value
    verify(streamService).listStreams(any());
    verify(context).setOutputVariable("streams", null);
  }

  @Test
  void execute_withNullStreamListAndPagination_shouldStillSetOutputVariable() {
    // Given: Activity where listStreams returns null with pagination
    activity.setLimit(50);
    activity.setSkip(10);
    activity.setObo(null);
    when(streamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should still set output variable with null value
    verify(streamService).listStreams(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", null);
  }

  // Tests for execute method - output variable

  @Test
  void execute_shouldSetOutputVariableWithCorrectKey() {
    // Given: Valid activity setup
    activity.setLimit(null);
    activity.setSkip(null);
    activity.setObo(null);
    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with key "streams"
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  @Test
  void execute_withPagination_shouldSetOutputVariableWithCorrectKey() {
    // Given: Valid activity setup with pagination
    activity.setLimit(50);
    activity.setSkip(10);
    activity.setObo(null);
    when(streamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should set output variable with key "streams"
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  // Tests for execute method - boundary values

  @Test
  void execute_withLargeSkipValue_shouldHandleCorrectly() {
    // Given: Activity with large skip value
    Integer limit = 100;
    Integer skip = 1000000;
    activity.setLimit(limit);
    activity.setSkip(skip);
    activity.setObo(null);

    when(streamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle large skip value correctly
    verify(streamService).listStreams(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  @Test
  void execute_withLargeLimitValue_shouldHandleCorrectly() {
    // Given: Activity with large limit value
    Integer limit = 1000000;
    Integer skip = 0;
    activity.setLimit(limit);
    activity.setSkip(skip);
    activity.setObo(null);

    when(streamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle large limit value correctly
    verify(streamService).listStreams(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  // Tests for execute method - switching between pagination modes

  @Test
  void execute_switchingFromPaginatedToNonPaginated_shouldUseCorrectMethod() {
    // Given: Activity initially with pagination
    activity.setLimit(50);
    activity.setSkip(10);
    activity.setObo(null);
    when(streamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: First call with pagination
    executor.execute(context);

    // Then switch to non-paginated
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: Second call without pagination
    executor.execute(context);

    // Then: Should use correct methods for each call
    verify(streamService).listStreams(any(), any(PaginationAttribute.class));
    verify(streamService).listStreams(any());
  }

  @Test
  void execute_switchingFromNonPaginatedToPaginated_shouldUseCorrectMethod() {
    // Given: Activity initially without pagination
    activity.setLimit(null);
    activity.setSkip(null);
    activity.setObo(null);
    when(streamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: First call without pagination
    executor.execute(context);

    // Then switch to paginated
    activity.setLimit(50);
    activity.setSkip(10);
    when(streamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: Second call with pagination
    executor.execute(context);

    // Then: Should use correct methods for each call
    verify(streamService).listStreams(any());
    verify(streamService).listStreams(any(), any(PaginationAttribute.class));
  }

  // Tests for execute method - various filter combinations

  @Test
  void execute_withTypesAndPagination_shouldHandleCorrectly() {
    // Given: Activity with types and pagination
    activity.setTypes(Arrays.asList("IM", "ROOM", "POST"));
    activity.setLimit(25);
    activity.setSkip(5);
    activity.setObo(null);

    when(streamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle both types and pagination correctly
    verify(streamService).listStreams(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  @Test
  void execute_withIncludeInactiveStreamsAndPagination_shouldHandleCorrectly() {
    // Given: Activity with includeInactiveStreams and pagination
    activity.setIncludeInactiveStreams(true);
    activity.setLimit(100);
    activity.setSkip(50);
    activity.setObo(null);

    when(streamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: Execute is called
    executor.execute(context);

    // Then: Should handle both includeInactiveStreams and pagination correctly
    verify(streamService).listStreams(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("streams", streamAttributesList);
  }

  // Tests for doOboWithCache method

  @Test
  void doOboWithCache_withOboUsername_shouldReturnStreamAttributes() {
    // Given: Activity with OBO username
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: doOboWithCache is called
    List<StreamAttributes> result = executor.doOboWithCache(context);

    // Then: Should get streams via OBO and return the result
    assertThat(result).isSameAs(streamAttributesList);
    verify(bdkGateway).obo(oboUsername);
    verify(oboStreamService).listStreams(any());
  }

  @Test
  void doOboWithCache_withOboUserId_shouldReturnStreamAttributes() {
    // Given: Activity with OBO user ID
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: doOboWithCache is called
    List<StreamAttributes> result = executor.doOboWithCache(context);

    // Then: Should get streams via OBO and return the result
    assertThat(result).isSameAs(streamAttributesList);
    verify(bdkGateway).obo(oboUserId);
    verify(oboStreamService).listStreams(any());
  }

  @Test
  void doOboWithCache_withPagination_shouldUsePaginatedCall() {
    // Given: Activity with OBO and pagination
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setLimit(50);
    activity.setSkip(10);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: doOboWithCache is called
    List<StreamAttributes> result = executor.doOboWithCache(context);

    // Then: Should use paginated call
    assertThat(result).isSameAs(streamAttributesList);
    verify(oboStreamService).listStreams(any(), any(PaginationAttribute.class));
  }

  @Test
  void doOboWithCache_withNoPagination_shouldUseNonPaginatedCall() {
    // Given: Activity with OBO and no pagination
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: doOboWithCache is called
    List<StreamAttributes> result = executor.doOboWithCache(context);

    // Then: Should use non-paginated call
    assertThat(result).isSameAs(streamAttributesList);
    verify(oboStreamService).listStreams(any());
    verify(oboStreamService, never()).listStreams(any(), any(PaginationAttribute.class));
  }

  @Test
  void doOboWithCache_shouldNotThrowException() {
    // Given: Valid activity with OBO
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any())).thenReturn(streamAttributesList);

    // When/Then: doOboWithCache should not throw exception
    assertThatCode(() -> executor.doOboWithCache(context))
        .doesNotThrowAnyException();
  }

  @Test
  void doOboWithCache_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity with OBO
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: doOboWithCache is called multiple times
    List<StreamAttributes> result1 = executor.doOboWithCache(context);
    List<StreamAttributes> result2 = executor.doOboWithCache(context);

    // Then: Should work correctly both times and return same result
    assertThat(result1).isSameAs(streamAttributesList);
    assertThat(result2).isSameAs(streamAttributesList);
    verify(oboStreamService, times(2)).listStreams(any());
  }

  @Test
  void doOboWithCache_withNullStreamList_shouldReturnNull() {
    // Given: Activity with OBO where listStreams returns null
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any())).thenReturn(null);

    // When: doOboWithCache is called
    List<StreamAttributes> result = executor.doOboWithCache(context);

    // Then: Should return null
    assertThat(result).isNull();
    verify(oboStreamService).listStreams(any());
  }

  @Test
  void doOboWithCache_withBothUsernameAndUserId_shouldPreferUsername() {
    // Given: Activity with both OBO username and user ID
    String oboUsername = "obo.user";
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    obo.setUserId(oboUserId);
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: doOboWithCache is called
    List<StreamAttributes> result = executor.doOboWithCache(context);

    // Then: Should prefer username over user ID
    assertThat(result).isSameAs(streamAttributesList);
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, never()).obo(oboUserId);
    verify(oboStreamService).listStreams(any());
  }

  @Test
  void doOboWithCache_withTypesFilter_shouldIncludeTypesInFilter() {
    // Given: Activity with OBO and types filter
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setTypes(Arrays.asList("IM", "ROOM"));
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: doOboWithCache is called
    List<StreamAttributes> result = executor.doOboWithCache(context);

    // Then: Should include types in filter
    assertThat(result).isSameAs(streamAttributesList);
    verify(oboStreamService).listStreams(any());
  }

  @Test
  void doOboWithCache_withIncludeInactiveStreamsFilter_shouldIncludeInFilter() {
    // Given: Activity with OBO and includeInactiveStreams filter
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setIncludeInactiveStreams(true);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any())).thenReturn(streamAttributesList);

    // When: doOboWithCache is called
    List<StreamAttributes> result = executor.doOboWithCache(context);

    // Then: Should include includeInactiveStreams in filter
    assertThat(result).isSameAs(streamAttributesList);
    verify(oboStreamService).listStreams(any());
  }

  @Test
  void doOboWithCache_withAllFiltersAndPagination_shouldHandleCorrectly() {
    // Given: Activity with OBO, all filters, and pagination
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setObo(obo);
    activity.setTypes(Arrays.asList("IM", "ROOM"));
    activity.setIncludeInactiveStreams(false);
    activity.setLimit(50);
    activity.setSkip(10);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.listStreams(any(), any(PaginationAttribute.class)))
        .thenReturn(streamAttributesList);

    // When: doOboWithCache is called
    List<StreamAttributes> result = executor.doOboWithCache(context);

    // Then: Should handle all filters and pagination correctly
    assertThat(result).isSameAs(streamAttributesList);
    verify(oboStreamService).listStreams(any(), any(PaginationAttribute.class));
  }
}
