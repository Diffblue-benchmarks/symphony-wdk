package com.symphony.bdk.workflow.engine.executor.connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import com.symphony.bdk.workflow.swadl.v1.activity.connection.CreateConnection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateConnectionExecutorTest {

  private CreateConnectionExecutor executor;

  @Mock
  private ActivityExecutorContext<CreateConnection> context;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private ConnectionService connectionService;

  @Mock
  private OboServices oboServices;

  @Mock
  private AuthSession authSession;

  @BeforeEach
  void setUp() {
    executor = new CreateConnectionExecutor();
    when(context.bdk()).thenReturn(bdkGateway);
  }

  @Test
  void execute_shouldCreateConnectionWithoutObo() {
    // Arrange
    CreateConnection activity = new CreateConnection();
    activity.setUserId("12345");
    activity.setObo(null);

    UserConnection expectedConnection = mock(UserConnection.class);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.connections()).thenReturn(connectionService);
    when(connectionService.createConnection(12345L)).thenReturn(expectedConnection);

    // Act
    executor.execute(context);

    // Assert
    verify(connectionService).createConnection(12345L);
    verify(context).setOutputVariable("connection", expectedConnection);
  }

  @Test
  void execute_shouldCreateConnectionWithOboUsername() {
    // Arrange
    CreateConnection activity = new CreateConnection();
    activity.setUserId("67890");
    Obo obo = new Obo();
    obo.setUsername("testuser");
    activity.setObo(obo);

    UserConnection expectedConnection = mock(UserConnection.class);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.obo("testuser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);
    when(connectionService.createConnection(67890L)).thenReturn(expectedConnection);

    // Act
    executor.execute(context);

    // Assert
    verify(bdkGateway).obo("testuser");
    verify(bdkGateway).obo(authSession);
    verify(oboServices.connections()).createConnection(67890L);
    verify(context).setOutputVariable("connection", expectedConnection);
  }

  @Test
  void execute_shouldCreateConnectionWithOboUserId() {
    // Arrange
    CreateConnection activity = new CreateConnection();
    activity.setUserId("11111");
    Obo obo = new Obo();
    obo.setUserId(99999L);
    activity.setObo(obo);

    UserConnection expectedConnection = mock(UserConnection.class);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.obo(99999L)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);
    when(connectionService.createConnection(11111L)).thenReturn(expectedConnection);

    // Act
    executor.execute(context);

    // Assert
    verify(bdkGateway).obo(99999L);
    verify(bdkGateway).obo(authSession);
    verify(oboServices.connections()).createConnection(11111L);
    verify(context).setOutputVariable("connection", expectedConnection);
  }

  @Test
  void doOboWithCache_shouldCreateConnectionWithObo() {
    // Arrange
    CreateConnection activity = new CreateConnection();
    activity.setUserId("22222");
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    UserConnection expectedConnection = mock(UserConnection.class);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);
    when(connectionService.createConnection(22222L)).thenReturn(expectedConnection);

    // Act
    UserConnection result = executor.doOboWithCache(context);

    // Assert
    assertThat(result).isEqualTo(expectedConnection);
    verify(bdkGateway).obo("obouser");
    verify(bdkGateway).obo(authSession);
    verify(oboServices.connections()).createConnection(22222L);
  }
}
