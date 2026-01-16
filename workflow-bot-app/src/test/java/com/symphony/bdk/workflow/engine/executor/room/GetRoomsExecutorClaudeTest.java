package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.core.service.stream.OboStreamService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V2RoomSearchCriteria;
import com.symphony.bdk.gen.api.model.V3RoomSearchResults;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.GetRooms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetRoomsExecutorClaudeTest {

  private GetRoomsExecutor executor;
  private ActivityExecutorContext<GetRooms> context;
  private GetRooms activity;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private OboServices oboServices;
  private AuthSession authSession;
  private OboStreamService oboStreamService;
  private V3RoomSearchResults searchResults;

  @BeforeEach
  void setUp() {
    executor = new GetRoomsExecutor();
    context = mock(ActivityExecutorContext.class);
    activity = new GetRooms();
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);
    oboServices = mock(OboServices.class);
    authSession = mock(AuthSession.class);
    oboStreamService = mock(OboStreamService.class);
    searchResults = new V3RoomSearchResults();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
  }

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstanceSuccessfully() {
    // When: Creating a new instance
    GetRoomsExecutor newExecutor = new GetRoomsExecutor();

    // Then: Instance should be created successfully
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void constructor_shouldCreateInstanceOfCorrectType() {
    // When: Creating a new instance
    GetRoomsExecutor newExecutor = new GetRoomsExecutor();

    // Then: Instance should be of correct type
    assertThat(newExecutor).isInstanceOf(GetRoomsExecutor.class);
  }

  @Test
  void constructor_shouldCreateDistinctInstances() {
    // When: Creating multiple instances
    GetRoomsExecutor executor1 = new GetRoomsExecutor();
    GetRoomsExecutor executor2 = new GetRoomsExecutor();

    // Then: Each instance should be distinct
    assertThat(executor1).isNotSameAs(executor2);
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new GetRoomsExecutor())
        .doesNotThrowAnyException();
  }

  // Tests for execute method - non-OBO without pagination

  @Test
  void execute_withNoOboNoPagination_shouldSearchRoomsAndSetOutput() {
    // Given: Activity with basic query and no pagination
    activity.setQuery("test room");
    activity.setObo(null);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should search rooms without pagination and set output
    verify(streamService).searchRooms(any(V2RoomSearchCriteria.class));
    verify(context).setOutputVariable("rooms", searchResults);
    verify(bdkGateway, never()).obo((String) null);
    verify(bdkGateway, never()).obo((Long) null);
  }

  @Test
  void execute_withNoOboNoPaginationWithLabels_shouldSearchRoomsWithLabels() {
    // Given: Activity with labels
    activity.setQuery("test");
    activity.setLabels(Arrays.asList("label1", "label2"));
    activity.setObo(null);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should search rooms with labels
    ArgumentCaptor<V2RoomSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(V2RoomSearchCriteria.class);
    verify(streamService).searchRooms(criteriaCaptor.capture());
    V2RoomSearchCriteria capturedCriteria = criteriaCaptor.getValue();
    assertThat(capturedCriteria.getLabels()).containsExactly("label1", "label2");
    verify(context).setOutputVariable("rooms", searchResults);
  }

  @Test
  void execute_withNoOboNoPaginationWithActive_shouldSearchActiveRooms() {
    // Given: Activity with active flag
    activity.setQuery("test");
    activity.setActive(true);
    activity.setObo(null);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should search with active criteria
    ArgumentCaptor<V2RoomSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(V2RoomSearchCriteria.class);
    verify(streamService).searchRooms(criteriaCaptor.capture());
    V2RoomSearchCriteria capturedCriteria = criteriaCaptor.getValue();
    assertThat(capturedCriteria.getActive()).isTrue();
    verify(context).setOutputVariable("rooms", searchResults);
  }

  @Test
  void execute_withNoOboNoPaginationWithPrivate_shouldSearchPrivateRooms() {
    // Given: Activity with private flag
    activity.setQuery("test");
    activity.setIsPrivate(true);
    activity.setObo(null);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should search with private criteria
    ArgumentCaptor<V2RoomSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(V2RoomSearchCriteria.class);
    verify(streamService).searchRooms(criteriaCaptor.capture());
    V2RoomSearchCriteria capturedCriteria = criteriaCaptor.getValue();
    assertThat(capturedCriteria.getPrivate()).isTrue();
    verify(context).setOutputVariable("rooms", searchResults);
  }

  @Test
  void execute_withNoOboNoPaginationWithSortOrder_shouldSearchRoomsWithSortOrder() {
    // Given: Activity with sort order
    activity.setQuery("test");
    activity.setSortOrder("RELEVANCE");
    activity.setObo(null);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should search with sort order
    ArgumentCaptor<V2RoomSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(V2RoomSearchCriteria.class);
    verify(streamService).searchRooms(criteriaCaptor.capture());
    V2RoomSearchCriteria capturedCriteria = criteriaCaptor.getValue();
    assertThat(capturedCriteria.getSortOrder()).isEqualTo(V2RoomSearchCriteria.SortOrderEnum.RELEVANCE);
    verify(context).setOutputVariable("rooms", searchResults);
  }

  @Test
  void execute_withNoOboNoPaginationWithCreatorId_shouldSearchRoomsWithCreatorId() {
    // Given: Activity with creator ID
    activity.setQuery("test");
    activity.setCreatorId("123456");
    activity.setObo(null);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should search with creator ID
    ArgumentCaptor<V2RoomSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(V2RoomSearchCriteria.class);
    verify(streamService).searchRooms(criteriaCaptor.capture());
    V2RoomSearchCriteria capturedCriteria = criteriaCaptor.getValue();
    assertThat(capturedCriteria.getCreator().getId()).isEqualTo(123456L);
    verify(context).setOutputVariable("rooms", searchResults);
  }

  @Test
  void execute_withNoOboNoPaginationWithOwnerId_shouldSearchRoomsWithOwnerId() {
    // Given: Activity with owner ID
    activity.setQuery("test");
    activity.setOwnerId("789012");
    activity.setObo(null);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should search with owner ID
    ArgumentCaptor<V2RoomSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(V2RoomSearchCriteria.class);
    verify(streamService).searchRooms(criteriaCaptor.capture());
    V2RoomSearchCriteria capturedCriteria = criteriaCaptor.getValue();
    assertThat(capturedCriteria.getOwner().getId()).isEqualTo(789012L);
    verify(context).setOutputVariable("rooms", searchResults);
  }

  @Test
  void execute_withNoOboNoPaginationWithMemberId_shouldSearchRoomsWithMemberId() {
    // Given: Activity with member ID
    activity.setQuery("test");
    activity.setMemberId("345678");
    activity.setObo(null);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should search with member ID
    ArgumentCaptor<V2RoomSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(V2RoomSearchCriteria.class);
    verify(streamService).searchRooms(criteriaCaptor.capture());
    V2RoomSearchCriteria capturedCriteria = criteriaCaptor.getValue();
    assertThat(capturedCriteria.getMember().getId()).isEqualTo(345678L);
    verify(context).setOutputVariable("rooms", searchResults);
  }

  @Test
  void execute_withNoOboNoPaginationWithAllFilters_shouldSearchRoomsWithAllFilters() {
    // Given: Activity with all filter criteria
    activity.setQuery("comprehensive test");
    activity.setLabels(Arrays.asList("label1", "label2", "label3"));
    activity.setActive(false);
    activity.setIsPrivate(true);
    activity.setSortOrder("RELEVANCE");
    activity.setCreatorId("111111");
    activity.setOwnerId("222222");
    activity.setMemberId("333333");
    activity.setObo(null);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should search with all criteria
    ArgumentCaptor<V2RoomSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(V2RoomSearchCriteria.class);
    verify(streamService).searchRooms(criteriaCaptor.capture());
    V2RoomSearchCriteria capturedCriteria = criteriaCaptor.getValue();
    assertThat(capturedCriteria.getQuery()).isEqualTo("comprehensive test");
    assertThat(capturedCriteria.getLabels()).containsExactly("label1", "label2", "label3");
    assertThat(capturedCriteria.getActive()).isFalse();
    assertThat(capturedCriteria.getPrivate()).isTrue();
    assertThat(capturedCriteria.getSortOrder()).isEqualTo(V2RoomSearchCriteria.SortOrderEnum.RELEVANCE);
    assertThat(capturedCriteria.getCreator().getId()).isEqualTo(111111L);
    assertThat(capturedCriteria.getOwner().getId()).isEqualTo(222222L);
    assertThat(capturedCriteria.getMember().getId()).isEqualTo(333333L);
    verify(context).setOutputVariable("rooms", searchResults);
  }

  // Tests for execute method - non-OBO with pagination

  @Test
  void execute_withNoOboWithPagination_shouldSearchRoomsWithPagination() {
    // Given: Activity with pagination parameters
    activity.setQuery("test");
    activity.setLimit(50);
    activity.setSkip(10);
    activity.setObo(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class), any(PaginationAttribute.class)))
        .thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should search rooms with pagination
    ArgumentCaptor<PaginationAttribute> paginationCaptor = ArgumentCaptor.forClass(PaginationAttribute.class);
    verify(streamService).searchRooms(any(V2RoomSearchCriteria.class), paginationCaptor.capture());
    PaginationAttribute pagination = paginationCaptor.getValue();
    assertThat(pagination.getSkip()).isEqualTo(10);
    assertThat(pagination.getLimit()).isEqualTo(50);
    verify(context).setOutputVariable("rooms", searchResults);
  }

  @Test
  void execute_withNoOboWithPaginationZeroSkip_shouldSearchRoomsWithZeroSkip() {
    // Given: Activity with zero skip
    activity.setQuery("test");
    activity.setLimit(100);
    activity.setSkip(0);
    activity.setObo(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class), any(PaginationAttribute.class)))
        .thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should search rooms with zero skip
    ArgumentCaptor<PaginationAttribute> paginationCaptor = ArgumentCaptor.forClass(PaginationAttribute.class);
    verify(streamService).searchRooms(any(V2RoomSearchCriteria.class), paginationCaptor.capture());
    PaginationAttribute pagination = paginationCaptor.getValue();
    assertThat(pagination.getSkip()).isEqualTo(0);
    assertThat(pagination.getLimit()).isEqualTo(100);
    verify(context).setOutputVariable("rooms", searchResults);
  }

  @Test
  void execute_withNoOboWithPaginationAndFilters_shouldSearchRoomsWithBoth() {
    // Given: Activity with pagination and filters
    activity.setQuery("filtered test");
    activity.setLabels(Collections.singletonList("important"));
    activity.setActive(true);
    activity.setLimit(25);
    activity.setSkip(5);
    activity.setObo(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class), any(PaginationAttribute.class)))
        .thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should search rooms with pagination and filters
    ArgumentCaptor<V2RoomSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(V2RoomSearchCriteria.class);
    ArgumentCaptor<PaginationAttribute> paginationCaptor = ArgumentCaptor.forClass(PaginationAttribute.class);
    verify(streamService).searchRooms(criteriaCaptor.capture(), paginationCaptor.capture());
    V2RoomSearchCriteria capturedCriteria = criteriaCaptor.getValue();
    assertThat(capturedCriteria.getQuery()).isEqualTo("filtered test");
    assertThat(capturedCriteria.getLabels()).containsExactly("important");
    assertThat(capturedCriteria.getActive()).isTrue();
    PaginationAttribute pagination = paginationCaptor.getValue();
    assertThat(pagination.getSkip()).isEqualTo(5);
    assertThat(pagination.getLimit()).isEqualTo(25);
    verify(context).setOutputVariable("rooms", searchResults);
  }

  // Tests for execute method - pagination edge cases

  @Test
  void execute_withOnlyLimit_shouldThrowIllegalArgumentException() {
    // Given: Activity with only limit set
    activity.setQuery("test");
    activity.setLimit(50);
    activity.setSkip(null);
    activity.setObo(null);
    activity.setId("testActivity1");

    // When/Then: Execute should throw IllegalArgumentException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set")
        .hasMessageContaining("testActivity1");
  }

  @Test
  void execute_withOnlySkip_shouldThrowIllegalArgumentException() {
    // Given: Activity with only skip set
    activity.setQuery("test");
    activity.setLimit(null);
    activity.setSkip(10);
    activity.setObo(null);
    activity.setId("testActivity2");

    // When/Then: Execute should throw IllegalArgumentException
    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set")
        .hasMessageContaining("testActivity2");
  }

  // Tests for execute method - OBO scenarios

  @Test
  void execute_withOboUsername_shouldUseOboServices() {
    // Given: Activity with OBO username
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setQuery("obo test");
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway).obo(authSession);
    verify(oboStreamService).searchRooms(any(V2RoomSearchCriteria.class));
    verify(context).setOutputVariable("rooms", searchResults);
    verify(streamService, never()).searchRooms(any(V2RoomSearchCriteria.class));
  }

  @Test
  void execute_withOboUserId_shouldUseOboServices() {
    // Given: Activity with OBO user ID
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setQuery("obo test");
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services with user ID
    verify(bdkGateway).obo(oboUserId);
    verify(bdkGateway).obo(authSession);
    verify(oboStreamService).searchRooms(any(V2RoomSearchCriteria.class));
    verify(context).setOutputVariable("rooms", searchResults);
    verify(streamService, never()).searchRooms(any(V2RoomSearchCriteria.class));
  }

  @Test
  void execute_withOboUsernameAndPagination_shouldUseOboServicesWithPagination() {
    // Given: Activity with OBO username and pagination
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setQuery("obo paginated test");
    activity.setObo(obo);
    activity.setLimit(30);
    activity.setSkip(15);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class), any(PaginationAttribute.class)))
        .thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use OBO services with pagination
    verify(bdkGateway).obo(oboUsername);
    verify(oboStreamService).searchRooms(any(V2RoomSearchCriteria.class), any(PaginationAttribute.class));
    verify(context).setOutputVariable("rooms", searchResults);
  }

  @Test
  void execute_withOboAndBothUsernameAndUserId_shouldPreferUsername() {
    // Given: Activity with both OBO username and user ID
    String oboUsername = "obo.user";
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    obo.setUserId(oboUserId);
    activity.setQuery("obo test");
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should prefer username over user ID
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, never()).obo(oboUserId);
    verify(oboStreamService).searchRooms(any(V2RoomSearchCriteria.class));
    verify(context).setOutputVariable("rooms", searchResults);
  }

  @Test
  void execute_withEmptyOboObject_shouldUseNonOboPath() {
    // Given: Activity with empty OBO object (both username and userId are null)
    Obo emptyObo = new Obo();
    emptyObo.setUsername(null);
    emptyObo.setUserId(null);
    activity.setQuery("test");
    activity.setObo(emptyObo);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called
    executor.execute(context);

    // Then: Should use non-OBO path since OBO is not valid
    verify(streamService).searchRooms(any(V2RoomSearchCriteria.class));
    verify(context).setOutputVariable("rooms", searchResults);
    verify(bdkGateway, never()).obo((String) null);
  }

  // Tests for execute method - edge cases

  @Test
  void execute_shouldNotThrowException() {
    // Given: Valid activity setup
    activity.setQuery("test");
    activity.setObo(null);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When/Then: Execute should not throw exception
    assertThatCode(() -> executor.execute(context))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_calledMultipleTimes_shouldWorkCorrectly() {
    // Given: Valid activity setup
    activity.setQuery("test");
    activity.setObo(null);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: Execute is called multiple times
    executor.execute(context);
    executor.execute(context);

    // Then: Should work correctly both times
    verify(streamService, times(2)).searchRooms(any(V2RoomSearchCriteria.class));
    verify(context, times(2)).setOutputVariable("rooms", searchResults);
  }

  @Test
  void execute_withNullSearchResults_shouldStillSetOutputVariable() {
    // Given: Activity where searchRooms returns null
    activity.setQuery("test");
    activity.setObo(null);
    activity.setLimit(null);
    activity.setSkip(null);
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(null);

    // When: Execute is called
    executor.execute(context);

    // Then: Should still set output variable with null value
    verify(streamService).searchRooms(any(V2RoomSearchCriteria.class));
    verify(context).setOutputVariable("rooms", null);
  }

  // Tests for doOboWithCache method - without pagination

  @Test
  void doOboWithCache_withOboUsernameNoPagination_shouldReturnSearchResults() {
    // Given: Activity with OBO username and no pagination
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setQuery("obo cache test");
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: doOboWithCache is called
    V3RoomSearchResults result = executor.doOboWithCache(context);

    // Then: Should get search results via OBO and return the result
    assertThat(result).isSameAs(searchResults);
    verify(bdkGateway).obo(oboUsername);
    verify(oboStreamService).searchRooms(any(V2RoomSearchCriteria.class));
  }

  @Test
  void doOboWithCache_withOboUserIdNoPagination_shouldReturnSearchResults() {
    // Given: Activity with OBO user ID and no pagination
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setQuery("obo cache test");
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: doOboWithCache is called
    V3RoomSearchResults result = executor.doOboWithCache(context);

    // Then: Should get search results via OBO and return the result
    assertThat(result).isSameAs(searchResults);
    verify(bdkGateway).obo(oboUserId);
    verify(oboStreamService).searchRooms(any(V2RoomSearchCriteria.class));
  }

  @Test
  void doOboWithCache_withAllFiltersNoPagination_shouldSearchWithAllFilters() {
    // Given: Activity with all filter criteria and OBO
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setQuery("comprehensive obo test");
    activity.setLabels(Arrays.asList("obo1", "obo2"));
    activity.setActive(true);
    activity.setIsPrivate(false);
    activity.setSortOrder("RELEVANCE");
    activity.setCreatorId("111111");
    activity.setOwnerId("222222");
    activity.setMemberId("333333");
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: doOboWithCache is called
    V3RoomSearchResults result = executor.doOboWithCache(context);

    // Then: Should search with all criteria via OBO
    assertThat(result).isSameAs(searchResults);
    ArgumentCaptor<V2RoomSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(V2RoomSearchCriteria.class);
    verify(oboStreamService).searchRooms(criteriaCaptor.capture());
    V2RoomSearchCriteria capturedCriteria = criteriaCaptor.getValue();
    assertThat(capturedCriteria.getQuery()).isEqualTo("comprehensive obo test");
    assertThat(capturedCriteria.getLabels()).containsExactly("obo1", "obo2");
    assertThat(capturedCriteria.getActive()).isTrue();
    assertThat(capturedCriteria.getPrivate()).isFalse();
    assertThat(capturedCriteria.getSortOrder()).isEqualTo(V2RoomSearchCriteria.SortOrderEnum.RELEVANCE);
    assertThat(capturedCriteria.getCreator().getId()).isEqualTo(111111L);
    assertThat(capturedCriteria.getOwner().getId()).isEqualTo(222222L);
    assertThat(capturedCriteria.getMember().getId()).isEqualTo(333333L);
  }

  // Tests for doOboWithCache method - with pagination

  @Test
  void doOboWithCache_withOboUsernameWithPagination_shouldReturnSearchResults() {
    // Given: Activity with OBO username and pagination
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setQuery("obo paginated cache test");
    activity.setObo(obo);
    activity.setLimit(50);
    activity.setSkip(10);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class), any(PaginationAttribute.class)))
        .thenReturn(searchResults);

    // When: doOboWithCache is called
    V3RoomSearchResults result = executor.doOboWithCache(context);

    // Then: Should get search results via OBO with pagination
    assertThat(result).isSameAs(searchResults);
    ArgumentCaptor<PaginationAttribute> paginationCaptor = ArgumentCaptor.forClass(PaginationAttribute.class);
    verify(oboStreamService).searchRooms(any(V2RoomSearchCriteria.class), paginationCaptor.capture());
    PaginationAttribute pagination = paginationCaptor.getValue();
    assertThat(pagination.getSkip()).isEqualTo(10);
    assertThat(pagination.getLimit()).isEqualTo(50);
  }

  @Test
  void doOboWithCache_withOboUserIdWithPagination_shouldReturnSearchResults() {
    // Given: Activity with OBO user ID and pagination
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(null);
    obo.setUserId(oboUserId);
    activity.setQuery("obo paginated cache test");
    activity.setObo(obo);
    activity.setLimit(100);
    activity.setSkip(0);

    when(bdkGateway.obo(oboUserId)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class), any(PaginationAttribute.class)))
        .thenReturn(searchResults);

    // When: doOboWithCache is called
    V3RoomSearchResults result = executor.doOboWithCache(context);

    // Then: Should get search results via OBO with pagination
    assertThat(result).isSameAs(searchResults);
    ArgumentCaptor<PaginationAttribute> paginationCaptor = ArgumentCaptor.forClass(PaginationAttribute.class);
    verify(oboStreamService).searchRooms(any(V2RoomSearchCriteria.class), paginationCaptor.capture());
    PaginationAttribute pagination = paginationCaptor.getValue();
    assertThat(pagination.getSkip()).isEqualTo(0);
    assertThat(pagination.getLimit()).isEqualTo(100);
  }

  @Test
  void doOboWithCache_withPaginationAndFilters_shouldSearchWithBoth() {
    // Given: Activity with OBO, pagination and filters
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setQuery("obo filtered paginated");
    activity.setLabels(Collections.singletonList("important"));
    activity.setActive(true);
    activity.setObo(obo);
    activity.setLimit(25);
    activity.setSkip(5);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class), any(PaginationAttribute.class)))
        .thenReturn(searchResults);

    // When: doOboWithCache is called
    V3RoomSearchResults result = executor.doOboWithCache(context);

    // Then: Should search with pagination and filters
    assertThat(result).isSameAs(searchResults);
    ArgumentCaptor<V2RoomSearchCriteria> criteriaCaptor = ArgumentCaptor.forClass(V2RoomSearchCriteria.class);
    ArgumentCaptor<PaginationAttribute> paginationCaptor = ArgumentCaptor.forClass(PaginationAttribute.class);
    verify(oboStreamService).searchRooms(criteriaCaptor.capture(), paginationCaptor.capture());
    V2RoomSearchCriteria capturedCriteria = criteriaCaptor.getValue();
    assertThat(capturedCriteria.getQuery()).isEqualTo("obo filtered paginated");
    assertThat(capturedCriteria.getLabels()).containsExactly("important");
    assertThat(capturedCriteria.getActive()).isTrue();
    PaginationAttribute pagination = paginationCaptor.getValue();
    assertThat(pagination.getSkip()).isEqualTo(5);
    assertThat(pagination.getLimit()).isEqualTo(25);
  }

  // Tests for doOboWithCache method - pagination edge cases

  @Test
  void doOboWithCache_withOnlyLimit_shouldThrowIllegalArgumentException() {
    // Given: Activity with OBO and only limit set
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setQuery("test");
    activity.setObo(obo);
    activity.setLimit(50);
    activity.setSkip(null);
    activity.setId("oboActivity1");

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When/Then: doOboWithCache should throw IllegalArgumentException
    assertThatThrownBy(() -> executor.doOboWithCache(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set")
        .hasMessageContaining("oboActivity1");
  }

  @Test
  void doOboWithCache_withOnlySkip_shouldThrowIllegalArgumentException() {
    // Given: Activity with OBO and only skip set
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setQuery("test");
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(10);
    activity.setId("oboActivity2");

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // When/Then: doOboWithCache should throw IllegalArgumentException
    assertThatThrownBy(() -> executor.doOboWithCache(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set")
        .hasMessageContaining("oboActivity2");
  }

  // Tests for doOboWithCache method - edge cases

  @Test
  void doOboWithCache_withBothUsernameAndUserId_shouldPreferUsername() {
    // Given: Activity with both OBO username and user ID
    String oboUsername = "obo.user";
    Long oboUserId = 987654321L;
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    obo.setUserId(oboUserId);
    activity.setQuery("obo test");
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: doOboWithCache is called
    V3RoomSearchResults result = executor.doOboWithCache(context);

    // Then: Should prefer username over user ID
    assertThat(result).isSameAs(searchResults);
    verify(bdkGateway).obo(oboUsername);
    verify(bdkGateway, never()).obo(oboUserId);
    verify(oboStreamService).searchRooms(any(V2RoomSearchCriteria.class));
  }

  @Test
  void doOboWithCache_shouldNotThrowException() {
    // Given: Valid activity with OBO
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setQuery("test");
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

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
    activity.setQuery("test");
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(searchResults);

    // When: doOboWithCache is called multiple times
    V3RoomSearchResults result1 = executor.doOboWithCache(context);
    V3RoomSearchResults result2 = executor.doOboWithCache(context);

    // Then: Should work correctly both times and return same result
    assertThat(result1).isSameAs(searchResults);
    assertThat(result2).isSameAs(searchResults);
    verify(oboStreamService, times(2)).searchRooms(any(V2RoomSearchCriteria.class));
  }

  @Test
  void doOboWithCache_withNullSearchResults_shouldReturnNull() {
    // Given: Activity with OBO where searchRooms returns null
    String oboUsername = "obo.user";
    Obo obo = new Obo();
    obo.setUsername(oboUsername);
    activity.setQuery("test");
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(null);

    when(bdkGateway.obo(oboUsername)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class))).thenReturn(null);

    // When: doOboWithCache is called
    V3RoomSearchResults result = executor.doOboWithCache(context);

    // Then: Should return null
    assertThat(result).isNull();
    verify(oboStreamService).searchRooms(any(V2RoomSearchCriteria.class));
  }
}
