package com.symphony.bdk.workflow.engine.executor.connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.RejectConnection;

import org.junit.jupiter.api.Test;

class RejectConnectionExecutorTest {

  private final RejectConnectionExecutor underTest = new RejectConnectionExecutor();

  @Test
  @SuppressWarnings("unchecked")
  void shouldRejectConnectionWithoutObo() {
    ActivityExecutorContext<RejectConnection> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    ConnectionService connectionService = mock(ConnectionService.class);
    UserConnection connection = new UserConnection();

    RejectConnection activity = new RejectConnection();
    activity.setUserId("123");

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.connections()).thenReturn(connectionService);
    when(connectionService.rejectConnection(123L)).thenReturn(connection);

    underTest.execute(context);

    verify(connectionService).rejectConnection(123L);
    verify(context).setOutputVariable("connection", connection);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldRejectConnectionWithOboUsername() {
    ActivityExecutorContext<RejectConnection> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    ConnectionService connectionService = mock(ConnectionService.class);
    OboServices oboServices = mock(OboServices.class);
    AuthSession authSession = mock(AuthSession.class);
    UserConnection connection = new UserConnection();

    RejectConnection activity = new RejectConnection();
    activity.setUserId("456");
    Obo obo = new Obo();
    obo.setUsername("testuser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("testuser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);
    when(connectionService.rejectConnection(456L)).thenReturn(connection);

    underTest.execute(context);

    verify(connectionService).rejectConnection(456L);
    verify(context).setOutputVariable("connection", connection);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldRejectConnectionWithOboUserId() {
    ActivityExecutorContext<RejectConnection> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    ConnectionService connectionService = mock(ConnectionService.class);
    OboServices oboServices = mock(OboServices.class);
    AuthSession authSession = mock(AuthSession.class);
    UserConnection connection = new UserConnection();

    RejectConnection activity = new RejectConnection();
    activity.setUserId("789");
    Obo obo = new Obo();
    obo.setUserId(999L);
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo(999L)).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);
    when(connectionService.rejectConnection(789L)).thenReturn(connection);

    underTest.execute(context);

    verify(connectionService).rejectConnection(789L);
    verify(context).setOutputVariable("connection", connection);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldReturnConnectionFromDoOboWithCache() throws Exception {
    ActivityExecutorContext<RejectConnection> context = mock(ActivityExecutorContext.class);
    BdkGateway bdk = mock(BdkGateway.class);
    ConnectionService connectionService = mock(ConnectionService.class);
    OboServices oboServices = mock(OboServices.class);
    AuthSession authSession = mock(AuthSession.class);
    UserConnection connection = new UserConnection();

    RejectConnection activity = new RejectConnection();
    activity.setUserId("321");
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("obouser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);
    when(connectionService.rejectConnection(321L)).thenReturn(connection);

    UserConnection result = underTest.doOboWithCache(context);

    assertThat(result).isEqualTo(connection);
  }
}
