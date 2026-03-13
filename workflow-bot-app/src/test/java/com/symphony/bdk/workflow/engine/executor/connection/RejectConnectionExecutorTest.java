package com.symphony.bdk.workflow.engine.executor.connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RejectConnectionExecutorTest {

  private RejectConnectionExecutor executor;
  private ActivityExecutorContext<RejectConnection> context;
  private BdkGateway bdkGateway;
  private ConnectionService connectionService;
  private RejectConnection activity;

  @BeforeEach
  void setUp() {
    executor = new RejectConnectionExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    connectionService = mock(ConnectionService.class);
    activity = new RejectConnection();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.connections()).thenReturn(connectionService);
  }

  @Test
  void shouldRejectConnectionWithoutObo() {
    activity.setUserId("123456789");
    UserConnection expectedConnection = new UserConnection();

    when(connectionService.rejectConnection(anyLong())).thenReturn(expectedConnection);

    executor.execute(context);

    verify(connectionService).rejectConnection(eq(123456789L));
    verify(context).setOutputVariable(eq("connection"), eq(expectedConnection));
  }

  @Test
  void shouldRejectConnectionWithOboUsername() {
    activity.setUserId("987654321");
    Obo obo = new Obo();
    obo.setUsername("testuser");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);
    UserConnection expectedConnection = new UserConnection();

    when(bdkGateway.obo("testuser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.rejectConnection(anyLong())).thenReturn(expectedConnection);

    executor.execute(context);

    verify(bdkGateway).obo("testuser");
    verify(oboConnectionService).rejectConnection(eq(987654321L));
    verify(context).setOutputVariable(eq("connection"), eq(expectedConnection));
  }

  @Test
  void shouldRejectConnectionWithOboUserId() {
    activity.setUserId("111222333");
    Obo obo = new Obo();
    obo.setUserId(555666777L);
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);
    UserConnection expectedConnection = new UserConnection();

    when(bdkGateway.obo(555666777L)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.rejectConnection(anyLong())).thenReturn(expectedConnection);

    executor.execute(context);

    verify(bdkGateway).obo(555666777L);
    verify(oboConnectionService).rejectConnection(eq(111222333L));
    verify(context).setOutputVariable(eq("connection"), eq(expectedConnection));
  }

  @Test
  void shouldCallDoOboWithCacheWhenOboIsSet() {
    activity.setUserId("123456789");
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);
    UserConnection expectedConnection = new UserConnection();

    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(any(AuthSession.class))).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);
    when(oboConnectionService.rejectConnection(anyLong())).thenReturn(expectedConnection);

    executor.execute(context);

    verify(oboServices).connections();
    verify(oboConnectionService).rejectConnection(eq(123456789L));
  }
}
