package com.symphony.bdk.workflow.engine.executor.connection;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.GetConnection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetConnectionExecutorTest {

  private GetConnectionExecutor executor;
  private ActivityExecutorContext<GetConnection> context;
  private BdkGateway bdkGateway;
  private ConnectionService connectionService;
  private GetConnection activity;

  @BeforeEach
  void setUp() {
    executor = new GetConnectionExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    connectionService = mock(ConnectionService.class);
    activity = new GetConnection();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.connections()).thenReturn(connectionService);
  }

  @Test
  void shouldExecuteWithoutObo() {
    activity.setUserId("12345");

    UserConnection expectedConnection = new UserConnection();
    when(connectionService.getConnection(12345L)).thenReturn(expectedConnection);

    executor.execute(context);

    verify(context).setOutputVariable("connection", expectedConnection);
  }

  @Test
  void shouldExecuteWithObo() {
    Obo obo = new Obo();
    obo.setUsername("testUser");
    activity.setObo(obo);
    activity.setUserId("67890");

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo("testUser")).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);

    UserConnection expectedConnection = new UserConnection();
    when(oboConnectionService.getConnection(67890L)).thenReturn(expectedConnection);

    executor.execute(context);

    verify(context).setOutputVariable("connection", expectedConnection);
  }

  @Test
  void shouldExecuteWithOboUserId() {
    Obo obo = new Obo();
    obo.setUserId(99999L);
    activity.setObo(obo);
    activity.setUserId("11111");

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo(99999L)).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);

    UserConnection expectedConnection = new UserConnection();
    when(oboConnectionService.getConnection(11111L)).thenReturn(expectedConnection);

    executor.execute(context);

    verify(context).setOutputVariable("connection", expectedConnection);
  }

  @Test
  void shouldExecuteDoOboWithCache() {
    Obo obo = new Obo();
    obo.setUsername("cacheUser");
    activity.setObo(obo);
    activity.setUserId("22222");

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo("cacheUser")).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);

    UserConnection expectedConnection = new UserConnection();
    when(oboConnectionService.getConnection(22222L)).thenReturn(expectedConnection);

    UserConnection result = executor.doOboWithCache(context);

    assertThat(result).isEqualTo(expectedConnection);
    verify(oboConnectionService).getConnection(22222L);
  }

  @Test
  void shouldExecuteDoOboWithCacheUsingUserId() {
    Obo obo = new Obo();
    obo.setUserId(88888L);
    activity.setObo(obo);
    activity.setUserId("33333");

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo(88888L)).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);

    UserConnection expectedConnection = new UserConnection();
    when(oboConnectionService.getConnection(33333L)).thenReturn(expectedConnection);

    UserConnection result = executor.doOboWithCache(context);

    assertThat(result).isEqualTo(expectedConnection);
    verify(oboConnectionService).getConnection(33333L);
  }
}
