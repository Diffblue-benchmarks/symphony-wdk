package com.symphony.bdk.workflow.engine.executor.connection;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.CreateConnection;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateConnectionExecutorTest {

  @Test
  void shouldExecuteCreateConnectionWithoutObo() {
    CreateConnectionExecutor executor = new CreateConnectionExecutor();

    CreateConnection activity = new CreateConnection();
    activity.setUserId("12345");

    UserConnection connection = new UserConnection();
    ConnectionService connectionService = mock(ConnectionService.class);
    when(connectionService.createConnection(12345L)).thenReturn(connection);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.connections()).thenReturn(connectionService);

    ActivityExecutorContext<CreateConnection> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(connectionService).createConnection(12345L);
    verify(context).setOutputVariable("connection", connection);
  }

  @Test
  void shouldExecuteCreateConnectionWithObo() {
    CreateConnectionExecutor executor = new CreateConnectionExecutor();

    Obo obo = new Obo();
    obo.setUsername("oboUser");

    CreateConnection activity = new CreateConnection();
    activity.setUserId("67890");
    activity.setObo(obo);

    UserConnection connection = new UserConnection();
    ConnectionService connectionService = mock(ConnectionService.class);
    when(connectionService.createConnection(67890L)).thenReturn(connection);

    AuthSession authSession = mock(AuthSession.class);
    OboServices oboServices = mock(OboServices.class);
    when(oboServices.connections()).thenReturn(connectionService);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo("oboUser")).thenReturn(authSession);
    when(bdk.obo(authSession)).thenReturn(oboServices);

    ActivityExecutorContext<CreateConnection> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenReturn(activity);
    when(context.bdk()).thenReturn(bdk);

    executor.execute(context);

    verify(connectionService).createConnection(67890L);
    verify(context).setOutputVariable("connection", connection);
  }
}
