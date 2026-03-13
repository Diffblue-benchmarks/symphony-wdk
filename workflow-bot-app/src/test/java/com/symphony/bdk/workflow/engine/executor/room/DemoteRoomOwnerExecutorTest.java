package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.DemoteRoomOwner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class DemoteRoomOwnerExecutorTest {

  @Mock
  private ActivityExecutorContext<DemoteRoomOwner> context;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private StreamService streamService;

  @Mock
  private OboServices oboServices;

  @Mock
  private AuthSession authSession;

  private DemoteRoomOwnerExecutor executor;

  @BeforeEach
  void setUp() {
    executor = new DemoteRoomOwnerExecutor();
    lenient().when(context.bdk()).thenReturn(bdkGateway);
    lenient().when(bdkGateway.streams()).thenReturn(streamService);
  }

  @Test
  void shouldDemoteRoomOwnerWithoutObo() {
    DemoteRoomOwner activity = new DemoteRoomOwner();
    activity.setStreamId("room123");
    activity.setUserIds(List.of(12345L));

    when(context.getActivity()).thenReturn(activity);

    executor.execute(context);

    verify(streamService).demoteUserToRoomParticipant(12345L, "room123");
  }

  @Test
  void shouldDemoteMultipleRoomOwnersWithoutObo() {
    DemoteRoomOwner activity = new DemoteRoomOwner();
    activity.setStreamId("room456");
    activity.setUserIds(List.of(11111L, 22222L, 33333L));

    when(context.getActivity()).thenReturn(activity);

    executor.execute(context);

    verify(streamService).demoteUserToRoomParticipant(11111L, "room456");
    verify(streamService).demoteUserToRoomParticipant(22222L, "room456");
    verify(streamService).demoteUserToRoomParticipant(33333L, "room456");
    verify(streamService, times(3)).demoteUserToRoomParticipant(org.mockito.ArgumentMatchers.anyLong(), org.mockito.ArgumentMatchers.anyString());
  }

  @Test
  void shouldDemoteRoomOwnerWithOboUsername() {
    DemoteRoomOwner activity = new DemoteRoomOwner();
    activity.setStreamId("room789");
    activity.setUserIds(List.of(54321L));
    Obo obo = new Obo();
    obo.setUsername("oboUser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.obo("oboUser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);

    executor.execute(context);

    verify(bdkGateway).obo("oboUser");
    verify(oboServices.streams()).demoteUserToRoomParticipant(54321L, "room789");
  }

  @Test
  void shouldDemoteRoomOwnerWithOboUserId() {
    DemoteRoomOwner activity = new DemoteRoomOwner();
    activity.setStreamId("room999");
    activity.setUserIds(List.of(99999L));
    Obo obo = new Obo();
    obo.setUserId(77777L);
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.obo(77777L)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);

    executor.execute(context);

    verify(bdkGateway).obo(77777L);
    verify(oboServices.streams()).demoteUserToRoomParticipant(99999L, "room999");
  }

  @Test
  void shouldDemoteMultipleRoomOwnersWithOboUsername() {
    DemoteRoomOwner activity = new DemoteRoomOwner();
    activity.setStreamId("room555");
    activity.setUserIds(List.of(11111L, 22222L));
    Obo obo = new Obo();
    obo.setUsername("oboUser2");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.obo("oboUser2")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);

    executor.execute(context);

    verify(bdkGateway).obo("oboUser2");
    verify(oboServices.streams()).demoteUserToRoomParticipant(11111L, "room555");
    verify(oboServices.streams()).demoteUserToRoomParticipant(22222L, "room555");
    verify(oboServices.streams(), times(2)).demoteUserToRoomParticipant(org.mockito.ArgumentMatchers.anyLong(), org.mockito.ArgumentMatchers.anyString());
  }
}
