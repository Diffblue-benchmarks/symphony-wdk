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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetConnectionsExecutorTest {

  @InjectMocks
  private GetConnectionsExecutor executor;

  @Mock
  private ActivityExecutorContext<GetConnections> context;

  @Mock
  private BdkGateway bdk;

  @Mock
  private ConnectionService connectionService;

  @Mock
  private OboServices oboServices;

  @Mock
  private AuthSession authSession;

  @Test
  void shouldExecuteNonOboPathWhenNoOboConfigured() throws Exception {
    // given
    GetConnections activity = new GetConnections();
    activity.setStatus(null);

    List<UserConnection> connections = Collections.emptyList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.connections()).thenReturn(connectionService);
    when(connectionService.listConnections(null, null)).thenReturn(connections);

    // when
    executor.execute(context);

    // then
    verify(context).setOutputVariable("connections", connections);
  }

  @Test
  void shouldExecuteNonOboPathWithStatusWhenStatusProvided() throws Exception {
    // given
    GetConnections activity = new GetConnections();
    activity.setStatus("ACCEPTED");

    List<UserConnection> connections = Collections.singletonList(new UserConnection());

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.connections()).thenReturn(connectionService);
    when(connectionService.listConnections(ConnectionStatus.ACCEPTED, null)).thenReturn(connections);

    // when
    executor.execute(context);

    // then
    verify(context).setOutputVariable("connections", connections);
  }

  @Test
  void shouldExecuteOboPathWhenOboUsernameConfigured() throws Exception {
    // given
    Obo obo = new Obo();
    obo.setUsername("obo-user");

    GetConnections activity = new GetConnections();
    activity.setObo(obo);
    activity.setStatus(null);

    List<UserConnection> connections = Collections.emptyList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo("obo-user")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);
    when(connectionService.listConnections(null, null)).thenReturn(connections);

    // when
    executor.execute(context);

    // then
    verify(context).setOutputVariable("connections", connections);
  }

  @Test
  void shouldExecuteOboPathWhenOboUserIdConfigured() throws Exception {
    // given
    Obo obo = new Obo();
    obo.setUserId(12345L);

    GetConnections activity = new GetConnections();
    activity.setObo(obo);
    activity.setStatus("ACCEPTED");

    List<UserConnection> connections = Collections.singletonList(new UserConnection());

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.obo(12345L)).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);
    when(connectionService.listConnections(ConnectionStatus.ACCEPTED, null)).thenReturn(connections);

    // when
    executor.execute(context);

    // then
    verify(context).setOutputVariable("connections", connections);
  }

  @Test
  void shouldReturnNullConnectionStatusWhenStatusIsNull() throws Exception {
    // given
    GetConnections activity = new GetConnections();
    activity.setStatus(null);
    activity.setUserIds(Collections.singletonList(999L));

    List<UserConnection> connections = Collections.emptyList();

    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);
    when(bdk.connections()).thenReturn(connectionService);
    when(connectionService.listConnections(null, activity.getUserIds())).thenReturn(connections);

    // when
    executor.execute(context);

    // then
    verify(connectionService).listConnections(null, activity.getUserIds());
  }
}
