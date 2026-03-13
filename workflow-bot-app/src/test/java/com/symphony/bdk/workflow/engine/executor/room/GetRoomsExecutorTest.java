package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.core.service.stream.OboStreamService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.UserId;
import com.symphony.bdk.gen.api.model.V2RoomSearchCriteria;
import com.symphony.bdk.gen.api.model.V3RoomSearchResults;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.GetRooms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetRoomsExecutorTest {

  private GetRoomsExecutor executor;
  private ActivityExecutorContext<GetRooms> context;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private GetRooms activity;

  @BeforeEach
  void setUp() {
    executor = new GetRoomsExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);
    activity = new GetRooms();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
  }

  @Test
  void shouldExecuteWithPagination() {
    activity.setLimit(10);
    activity.setSkip(5);
    activity.setQuery("test query");

    V3RoomSearchResults expectedResults = new V3RoomSearchResults();
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class), any(PaginationAttribute.class)))
        .thenReturn(expectedResults);

    executor.execute(context);

    verify(context).setOutputVariable("rooms", expectedResults);
    verify(streamService).searchRooms(any(V2RoomSearchCriteria.class), any(PaginationAttribute.class));
  }

  @Test
  void shouldExecuteWithoutPagination() {
    activity.setQuery("test query");

    V3RoomSearchResults expectedResults = new V3RoomSearchResults();
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class)))
        .thenReturn(expectedResults);

    executor.execute(context);

    verify(context).setOutputVariable("rooms", expectedResults);
    verify(streamService).searchRooms(any(V2RoomSearchCriteria.class));
  }

  @Test
  void shouldThrowExceptionWhenOnlyLimitIsSet() {
    activity.setLimit(10);
    activity.setSkip(null);
    activity.setId("test-activity");

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set in activity test-activity to get rooms");
  }

  @Test
  void shouldThrowExceptionWhenOnlySkipIsSet() {
    activity.setLimit(null);
    activity.setSkip(5);
    activity.setId("test-activity");

    assertThatThrownBy(() -> executor.execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set in activity test-activity to get rooms");
  }

  @Test
  void shouldExecuteWithObo() {
    Obo obo = new Obo();
    obo.setUsername("testUser");
    activity.setObo(obo);
    activity.setLimit(10);
    activity.setSkip(0);

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo("testUser")).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    OboStreamService oboStreamService = mock(OboStreamService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomSearchResults expectedResults = new V3RoomSearchResults();
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class), any(PaginationAttribute.class)))
        .thenReturn(expectedResults);

    executor.execute(context);

    verify(context).setOutputVariable("rooms", expectedResults);
  }

  @Test
  void shouldExecuteWithOboUserId() {
    Obo obo = new Obo();
    obo.setUserId(12345L);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo(12345L)).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    OboStreamService oboStreamService = mock(OboStreamService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomSearchResults expectedResults = new V3RoomSearchResults();
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class)))
        .thenReturn(expectedResults);

    executor.execute(context);

    verify(context).setOutputVariable("rooms", expectedResults);
  }

  @Test
  void shouldConvertToCriteriaWithAllFields() {
    activity.setQuery("test query");
    activity.setLabels(Arrays.asList("label1", "label2"));
    activity.setIsPrivate(true);
    activity.setActive(false);
    activity.setSortOrder("RELEVANCE");
    activity.setCreatorId("123");
    activity.setOwnerId("456");
    activity.setMemberId("789");

    V3RoomSearchResults expectedResults = new V3RoomSearchResults();
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class)))
        .thenReturn(expectedResults);

    executor.execute(context);

    verify(streamService).searchRooms(any(V2RoomSearchCriteria.class));
  }

  @Test
  void shouldConvertToCriteriaWithMinimalFields() {
    activity.setQuery("test query");

    V3RoomSearchResults expectedResults = new V3RoomSearchResults();
    when(streamService.searchRooms(any(V2RoomSearchCriteria.class)))
        .thenReturn(expectedResults);

    executor.execute(context);

    verify(streamService).searchRooms(any(V2RoomSearchCriteria.class));
  }

  @Test
  void shouldExecuteDoOboWithCacheWithPagination() {
    Obo obo = new Obo();
    obo.setUsername("cacheUser");
    activity.setObo(obo);
    activity.setLimit(20);
    activity.setSkip(10);
    activity.setQuery("test");

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo("cacheUser")).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    OboStreamService oboStreamService = mock(OboStreamService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomSearchResults expectedResults = new V3RoomSearchResults();
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class), any(PaginationAttribute.class)))
        .thenReturn(expectedResults);

    V3RoomSearchResults result = executor.doOboWithCache(context);

    assertThat(result).isEqualTo(expectedResults);
    verify(oboStreamService).searchRooms(any(V2RoomSearchCriteria.class), any(PaginationAttribute.class));
  }

  @Test
  void shouldExecuteDoOboWithCacheWithoutPagination() {
    Obo obo = new Obo();
    obo.setUserId(99999L);
    activity.setObo(obo);
    activity.setQuery("test");

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo(99999L)).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    OboStreamService oboStreamService = mock(OboStreamService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    V3RoomSearchResults expectedResults = new V3RoomSearchResults();
    when(oboStreamService.searchRooms(any(V2RoomSearchCriteria.class)))
        .thenReturn(expectedResults);

    V3RoomSearchResults result = executor.doOboWithCache(context);

    assertThat(result).isEqualTo(expectedResults);
    verify(oboStreamService).searchRooms(any(V2RoomSearchCriteria.class));
  }

  @Test
  void shouldThrowExceptionInDoOboWithCacheWhenOnlyLimitIsSet() {
    Obo obo = new Obo();
    obo.setUsername("testUser");
    activity.setObo(obo);
    activity.setLimit(10);
    activity.setSkip(null);
    activity.setId("test-activity-obo");

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo("testUser")).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    OboStreamService oboStreamService = mock(OboStreamService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    assertThatThrownBy(() -> executor.doOboWithCache(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set to get rooms in activity test-activity-obo");
  }

  @Test
  void shouldThrowExceptionInDoOboWithCacheWhenOnlySkipIsSet() {
    Obo obo = new Obo();
    obo.setUserId(12345L);
    activity.setObo(obo);
    activity.setLimit(null);
    activity.setSkip(5);
    activity.setId("test-activity-obo");

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo(12345L)).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    OboStreamService oboStreamService = mock(OboStreamService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    assertThatThrownBy(() -> executor.doOboWithCache(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Skip and limit should both be set to get rooms in activity test-activity-obo");
  }
}
