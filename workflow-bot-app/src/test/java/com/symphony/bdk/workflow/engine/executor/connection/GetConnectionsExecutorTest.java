package com.symphony.bdk.workflow.engine.executor.connection;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.connection.constant.ConnectionStatus;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.GetConnections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetConnectionsExecutorTest {

  private GetConnectionsExecutor executor;
  private ActivityExecutorContext<GetConnections> context;
  private BdkGateway bdkGateway;
  private ConnectionService connectionService;
  private GetConnections activity;

  @BeforeEach
  void setUp() {
    executor = new GetConnectionsExecutor();
    context = mock(ActivityExecutorContext.class);
    bdkGateway = mock(BdkGateway.class);
    connectionService = mock(ConnectionService.class);
    activity = new GetConnections();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdkGateway);
    when(bdkGateway.connections()).thenReturn(connectionService);
  }

  @Test
  void shouldExecuteWithoutObo() {
    List<Long> userIds = Arrays.asList(1L, 2L, 3L);
    activity.setUserIds(userIds);
    activity.setStatus("ACCEPTED");

    List<UserConnection> expectedConnections = Collections.singletonList(new UserConnection());
    when(connectionService.listConnections(ConnectionStatus.ACCEPTED, userIds))
        .thenReturn(expectedConnections);

    executor.execute(context);

    verify(context).setOutputVariable("connections", expectedConnections);
  }

  @Test
  void shouldExecuteWithoutOboAndNullStatus() {
    List<Long> userIds = Arrays.asList(1L, 2L);
    activity.setUserIds(userIds);
    activity.setStatus(null);

    List<UserConnection> expectedConnections = Collections.singletonList(new UserConnection());
    when(connectionService.listConnections(null, userIds))
        .thenReturn(expectedConnections);

    executor.execute(context);

    verify(context).setOutputVariable("connections", expectedConnections);
  }

  @Test
  void shouldExecuteWithObo() {
    Obo obo = new Obo();
    obo.setUsername("testUser");
    activity.setObo(obo);
    activity.setUserIds(Arrays.asList(1L));
    activity.setStatus("PENDING_INCOMING");

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo("testUser")).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);

    List<UserConnection> expectedConnections = Collections.singletonList(new UserConnection());
    when(oboConnectionService.listConnections(ConnectionStatus.PENDING_INCOMING, activity.getUserIds()))
        .thenReturn(expectedConnections);

    executor.execute(context);

    verify(context).setOutputVariable("connections", expectedConnections);
  }

  @Test
  void shouldExecuteWithOboUserId() {
    Obo obo = new Obo();
    obo.setUserId(12345L);
    activity.setObo(obo);
    activity.setUserIds(Arrays.asList(1L, 2L));
    activity.setStatus("REJECTED");

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo(12345L)).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);

    List<UserConnection> expectedConnections = Collections.singletonList(new UserConnection());
    when(oboConnectionService.listConnections(ConnectionStatus.REJECTED, activity.getUserIds()))
        .thenReturn(expectedConnections);

    executor.execute(context);

    verify(context).setOutputVariable("connections", expectedConnections);
  }

  @Test
  void shouldConvertStatusStringToConnectionStatus() throws Exception {
    Method method = GetConnectionsExecutor.class.getDeclaredMethod("toConnectionStatus", String.class);
    method.setAccessible(true);

    assertThat(method.invoke(executor, "ACCEPTED")).isEqualTo(ConnectionStatus.ACCEPTED);
    assertThat(method.invoke(executor, "PENDING_INCOMING")).isEqualTo(ConnectionStatus.PENDING_INCOMING);
    assertThat(method.invoke(executor, "PENDING_OUTGOING")).isEqualTo(ConnectionStatus.PENDING_OUTGOING);
    assertThat(method.invoke(executor, "REJECTED")).isEqualTo(ConnectionStatus.REJECTED);
  }

  @Test
  void shouldReturnNullWhenStatusStringIsNull() throws Exception {
    Method method = GetConnectionsExecutor.class.getDeclaredMethod("toConnectionStatus", String.class);
    method.setAccessible(true);

    assertThat(method.invoke(executor, (String) null)).isNull();
  }

  @Test
  void shouldExecuteDoOboWithCache() {
    Obo obo = new Obo();
    obo.setUsername("cacheUser");
    activity.setObo(obo);
    activity.setUserIds(Arrays.asList(1L, 2L, 3L));
    activity.setStatus("ACCEPTED");

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo("cacheUser")).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);

    List<UserConnection> expectedConnections = Arrays.asList(
        new UserConnection(), new UserConnection()
    );
    when(oboConnectionService.listConnections(ConnectionStatus.ACCEPTED, activity.getUserIds()))
        .thenReturn(expectedConnections);

    List<UserConnection> result = executor.doOboWithCache(context);

    assertThat(result).isEqualTo(expectedConnections);
    verify(oboConnectionService).listConnections(ConnectionStatus.ACCEPTED, activity.getUserIds());
  }

  @Test
  void shouldExecuteDoOboWithCacheWithNullStatus() {
    Obo obo = new Obo();
    obo.setUserId(99999L);
    activity.setObo(obo);
    activity.setUserIds(Collections.singletonList(5L));
    activity.setStatus(null);

    AuthSession authSession = mock(AuthSession.class);
    when(bdkGateway.obo(99999L)).thenReturn(authSession);

    OboServices oboServices = mock(OboServices.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(oboConnectionService);

    List<UserConnection> expectedConnections = Collections.emptyList();
    when(oboConnectionService.listConnections(null, activity.getUserIds()))
        .thenReturn(expectedConnections);

    List<UserConnection> result = executor.doOboWithCache(context);

    assertThat(result).isEmpty();
    verify(oboConnectionService).listConnections(null, activity.getUserIds());
  }
}
