package com.symphony.bdk.workflow.engine.executor.room;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.AddRoomMember;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class AddRoomMemberExecutorTest {

  private AddRoomMemberExecutor executor;
  private ActivityExecutorContext<AddRoomMember> context;
  private BdkGateway bdkGateway;
  private StreamService streamService;
  private AddRoomMember activity;

  @BeforeEach
  void setUp() {
    executor = new AddRoomMemberExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    streamService = mock(StreamService.class);
    activity = new AddRoomMember();

    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.streams()).thenReturn(streamService);
    when(context.getActivity()).thenReturn(activity);
  }

  @Test
  void shouldExecuteWithoutObo() {
    activity.setStreamId("stream123");
    activity.setUserIds(Arrays.asList(123L, 456L, 789L));

    executor.execute(context);

    verify(streamService).addMemberToRoom(eq(123L), eq("stream123"));
    verify(streamService).addMemberToRoom(eq(456L), eq("stream123"));
    verify(streamService).addMemberToRoom(eq(789L), eq("stream123"));
  }

  @Test
  void shouldExecuteWithoutOboSingleUser() {
    activity.setStreamId("stream456");
    activity.setUserIds(Collections.singletonList(999L));

    executor.execute(context);

    verify(streamService, times(1)).addMemberToRoom(eq(999L), eq("stream456"));
  }

  @Test
  void shouldExecuteWithOboUsername() {
    activity.setStreamId("stream789");
    activity.setUserIds(Arrays.asList(111L, 222L));
    Obo obo = new Obo();
    obo.setUsername("obo-user");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);

    when(bdkGateway.obo(eq("obo-user"))).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    executor.execute(context);

    verify(bdkGateway).obo(eq("obo-user"));
    verify(oboStreamService).addMemberToRoom(eq(111L), eq("stream789"));
    verify(oboStreamService).addMemberToRoom(eq(222L), eq("stream789"));
  }

  @Test
  void shouldExecuteWithOboUserId() {
    activity.setStreamId("stream321");
    activity.setUserIds(Collections.singletonList(333L));
    Obo obo = new Obo();
    obo.setUserId(12345L);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);

    when(bdkGateway.obo(eq(12345L))).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    executor.execute(context);

    verify(bdkGateway).obo(eq(12345L));
    verify(oboStreamService).addMemberToRoom(eq(333L), eq("stream321"));
  }

  @Test
  void shouldExecuteWithOboMultipleUsers() {
    activity.setStreamId("stream999");
    activity.setUserIds(Arrays.asList(100L, 200L, 300L, 400L));
    Obo obo = new Obo();
    obo.setUsername("test-obo");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService oboStreamService = mock(StreamService.class);

    when(bdkGateway.obo(eq("test-obo"))).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(oboStreamService);

    executor.execute(context);

    verify(oboStreamService).addMemberToRoom(eq(100L), eq("stream999"));
    verify(oboStreamService).addMemberToRoom(eq(200L), eq("stream999"));
    verify(oboStreamService).addMemberToRoom(eq(300L), eq("stream999"));
    verify(oboStreamService).addMemberToRoom(eq(400L), eq("stream999"));
  }

  @Test
  void shouldExecuteWithEmptyUserList() {
    activity.setStreamId("stream555");
    activity.setUserIds(Collections.emptyList());

    executor.execute(context);

    verify(streamService, times(0)).addMemberToRoom(org.mockito.ArgumentMatchers.anyLong(),
        org.mockito.ArgumentMatchers.anyString());
  }
}
