package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.pagination.model.PaginationAttribute;
import com.symphony.bdk.core.service.stream.OboStreamService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.gen.api.model.V3RoomSearchResults;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.GetRooms;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetRoomsExecutorTest {

  @Mock
  private ActivityExecutorContext<GetRooms> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private StreamService streamService;

  @Mock
  private OboServices oboServices;

  @Mock
  private AuthSession authSession;

  @Mock
  private OboStreamService oboStreamService;

  @Test
  void shouldSearchRoomsWithPaginationWhenLimitAndSkipAreSet() {
    GetRooms activity = new GetRooms();
    activity.setQuery("test");
    activity.setLimit(10);
    activity.setSkip(5);

    V3RoomSearchResults results = new V3RoomSearchResults();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.searchRooms(any(), any(PaginationAttribute.class))).thenReturn(results);

    new GetRoomsExecutor().execute(context);

    verify(streamService).searchRooms(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("rooms", results);
  }

  @Test
  void shouldSearchRoomsWithNoPaginationWhenLimitAndSkipAreNull() {
    GetRooms activity = new GetRooms();
    activity.setQuery("test");

    V3RoomSearchResults results = new V3RoomSearchResults();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.searchRooms(any())).thenReturn(results);

    new GetRoomsExecutor().execute(context);

    verify(streamService).searchRooms(any());
    verify(context).setOutputVariable("rooms", results);
  }

  @Test
  void shouldThrowWhenOnlyLimitIsSetWithoutSkip() {
    GetRooms activity = new GetRooms();
    activity.setId("activity1");
    activity.setLimit(10);

    when(context.getActivity()).thenReturn(activity);

    assertThatThrownBy(() -> new GetRoomsExecutor().execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("activity1");
  }

  @Test
  void shouldThrowWhenOnlySkipIsSetWithoutLimit() {
    GetRooms activity = new GetRooms();
    activity.setId("activity2");
    activity.setSkip(5);

    when(context.getActivity()).thenReturn(activity);

    assertThatThrownBy(() -> new GetRoomsExecutor().execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("activity2");
  }

  @Test
  void shouldDoOboWithPaginationWhenOboAndLimitAndSkipAreSet() {
    GetRooms activity = new GetRooms();
    activity.setQuery("oboQuery");
    activity.setLimit(20);
    activity.setSkip(10);
    Obo obo = new Obo();
    obo.setUsername("oboUser");
    activity.setObo(obo);

    V3RoomSearchResults results = new V3RoomSearchResults();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("oboUser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any(), any(PaginationAttribute.class))).thenReturn(results);

    new GetRoomsExecutor().execute(context);

    verify(oboStreamService).searchRooms(any(), any(PaginationAttribute.class));
    verify(context).setOutputVariable("rooms", results);
  }

  @Test
  void shouldDoOboWithNoPaginationWhenOboAndLimitAndSkipAreNull() {
    GetRooms activity = new GetRooms();
    activity.setQuery("oboQuery");
    Obo obo = new Obo();
    obo.setUsername("oboUser");
    activity.setObo(obo);

    V3RoomSearchResults results = new V3RoomSearchResults();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("oboUser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);
    when(oboStreamService.searchRooms(any())).thenReturn(results);

    new GetRoomsExecutor().execute(context);

    verify(oboStreamService).searchRooms(any());
    verify(context).setOutputVariable("rooms", results);
  }

  @Test
  void shouldThrowWhenOboAndOnlyLimitIsSetWithoutSkip() {
    GetRooms activity = new GetRooms();
    activity.setId("oboActivity1");
    activity.setLimit(10);
    Obo obo = new Obo();
    obo.setUsername("oboUser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("oboUser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    assertThatThrownBy(() -> new GetRoomsExecutor().execute(context))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("oboActivity1");
  }

  @Test
  void shouldBuildCriteriaWithAllOptionalFields() {
    GetRooms activity = new GetRooms();
    activity.setQuery("rooms");
    activity.setSortOrder("BASIC");
    activity.setCreatorId("100");
    activity.setOwnerId("200");
    activity.setMemberId("300");

    V3RoomSearchResults results = new V3RoomSearchResults();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);
    when(streamService.searchRooms(any())).thenReturn(results);

    new GetRoomsExecutor().execute(context);

    verify(streamService).searchRooms(any());
    verify(context).setOutputVariable("rooms", results);
  }
}
