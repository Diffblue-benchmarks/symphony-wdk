package com.symphony.bdk.workflow.engine.executor.room;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.room.PromoteRoomOwner;

import org.junit.jupiter.api.Test;

import java.util.List;

class PromoteRoomOwnerExecutorTest {

  private final PromoteRoomOwnerExecutor underTest = new PromoteRoomOwnerExecutor();

  @Test
  @SuppressWarnings("unchecked")
  void shouldPromoteRoomOwnerWithoutObo() {
    ActivityExecutorContext<PromoteRoomOwner> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    StreamService streamService = mock(StreamService.class);

    PromoteRoomOwner activity = new PromoteRoomOwner();
    activity.setStreamId("streamId123");
    activity.setUserIds(List.of(123L, 456L));

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.streams()).thenReturn(streamService);

    underTest.execute(context);

    verify(streamService).promoteUserToRoomOwner(123L, "streamId123");
    verify(streamService).promoteUserToRoomOwner(456L, "streamId123");
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldPromoteRoomOwnerWithOboUsername() {
    ActivityExecutorContext<PromoteRoomOwner> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService streamService = mock(StreamService.class);
    AuthSession authSession = mock(AuthSession.class);

    PromoteRoomOwner activity = new PromoteRoomOwner();
    activity.setStreamId("streamId456");
    activity.setUserIds(List.of(789L));
    Obo obo = new Obo();
    obo.setUsername("testuser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("testuser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);

    underTest.execute(context);

    verify(streamService).promoteUserToRoomOwner(789L, "streamId456");
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldPromoteRoomOwnerWithOboUserId() {
    ActivityExecutorContext<PromoteRoomOwner> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService streamService = mock(StreamService.class);
    AuthSession authSession = mock(AuthSession.class);

    PromoteRoomOwner activity = new PromoteRoomOwner();
    activity.setStreamId("streamId789");
    activity.setUserIds(List.of(100L));
    Obo obo = new Obo();
    obo.setUserId(999L);
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo(999L)).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);

    underTest.execute(context);

    verify(streamService).promoteUserToRoomOwner(100L, "streamId789");
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldReturnNullFromDoOboWithCache() throws Exception {
    ActivityExecutorContext<PromoteRoomOwner> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    OboServices oboServices = mock(OboServices.class);
    StreamService streamService = mock(StreamService.class);
    AuthSession authSession = mock(AuthSession.class);

    PromoteRoomOwner activity = new PromoteRoomOwner();
    activity.setStreamId("streamIdObo");
    activity.setUserIds(List.of(200L));
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("obouser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.streams()).thenReturn(streamService);

    Void result = underTest.doOboWithCache(context);

    verify(streamService).promoteUserToRoomOwner(200L, "streamIdObo");
    org.assertj.core.api.Assertions.assertThat(result).isNull();
  }
}
