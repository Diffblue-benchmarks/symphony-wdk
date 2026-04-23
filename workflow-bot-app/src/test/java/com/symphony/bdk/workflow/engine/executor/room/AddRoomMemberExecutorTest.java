package com.symphony.bdk.workflow.engine.executor.room;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.OboStreamService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.AddRoomMember;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddRoomMemberExecutorTest {

  @InjectMocks
  private AddRoomMemberExecutor executor;

  @Mock
  private ActivityExecutorContext<AddRoomMember> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private StreamService streamService;

  @Mock
  private OboServices oboServices;

  @Mock
  private OboStreamService oboStreamService;

  @Mock
  private AuthSession authSession;

  @Test
  void shouldAddMembersToRoomWhenNotObo() throws Exception {
    // given
    AddRoomMember activity = new AddRoomMember();
    activity.setStreamId("stream123");
    activity.setUserIds(List.of(111L, 222L));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);

    // when
    executor.execute(context);

    // then
    verify(streamService).addMemberToRoom(111L, "stream123");
    verify(streamService).addMemberToRoom(222L, "stream123");
  }

  @Test
  void shouldAddMembersToRoomOboWhenOboUsernameConfigured() throws Exception {
    // given
    Obo obo = new Obo();
    obo.setUsername("obo-user");

    AddRoomMember activity = new AddRoomMember();
    activity.setStreamId("stream123");
    activity.setUserIds(List.of(111L));
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("obo-user")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // when
    executor.execute(context);

    // then
    verify(oboStreamService).addMemberToRoom(111L, "stream123");
  }

  @Test
  void shouldAddMembersToRoomOboWhenOboUserIdConfigured() throws Exception {
    // given
    Obo obo = new Obo();
    obo.setUserId(12345L);

    AddRoomMember activity = new AddRoomMember();
    activity.setStreamId("stream456");
    activity.setUserIds(List.of(222L));
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo(12345L)).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    // when
    executor.execute(context);

    // then
    verify(oboStreamService).addMemberToRoom(222L, "stream456");
  }
}
