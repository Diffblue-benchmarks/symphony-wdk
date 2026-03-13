package com.symphony.bdk.workflow.engine.executor.connection;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.AcceptConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AcceptConnectionExecutorTest {

  @Mock
  private ActivityExecutorContext<AcceptConnection> context;

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private ConnectionService connectionService;

  @Mock
  private OboServices oboServices;

  @Mock
  private AuthSession authSession;

  @InjectMocks
  private AcceptConnectionExecutor executor;

  private AcceptConnection acceptConnection;
  private UserConnection expectedConnection;

  @BeforeEach
  void setUp() {
    acceptConnection = new AcceptConnection();
    expectedConnection = new UserConnection();
    expectedConnection.setUserId(123L);

    when(context.getActivity()).thenReturn(acceptConnection);
    when(context.bdk()).thenReturn(bdkGateway);
  }

  @Test
  void shouldExecuteAcceptConnectionWithoutObo() {
    // Arrange
    acceptConnection.setUserId("123");
    acceptConnection.setObo(null);
    when(bdkGateway.connections()).thenReturn(connectionService);
    when(connectionService.acceptConnection(123L)).thenReturn(expectedConnection);

    // Act
    executor.execute(context);

    // Assert
    verify(connectionService).acceptConnection(123L);
    verify(context).setOutputVariable("connection", expectedConnection);
  }

  @Test
  void shouldExecuteAcceptConnectionWithOboUsername() {
    // Arrange
    acceptConnection.setUserId("456");
    Obo obo = new Obo();
    obo.setUsername("testUser");
    acceptConnection.setObo(obo);

    when(bdkGateway.obo("testUser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);
    when(connectionService.acceptConnection(456L)).thenReturn(expectedConnection);

    // Act
    executor.execute(context);

    // Assert
    verify(bdkGateway).obo("testUser");
    verify(bdkGateway).obo(authSession);
    verify(oboServices).connections();
    verify(connectionService).acceptConnection(456L);
    verify(context).setOutputVariable("connection", expectedConnection);
  }

  @Test
  void shouldExecuteAcceptConnectionWithOboUserId() {
    // Arrange
    acceptConnection.setUserId("789");
    Obo obo = new Obo();
    obo.setUserId(999L);
    acceptConnection.setObo(obo);

    when(bdkGateway.obo(999L)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);
    when(connectionService.acceptConnection(789L)).thenReturn(expectedConnection);

    // Act
    executor.execute(context);

    // Assert
    verify(bdkGateway).obo(999L);
    verify(bdkGateway).obo(authSession);
    verify(oboServices).connections();
    verify(connectionService).acceptConnection(789L);
    verify(context).setOutputVariable("connection", expectedConnection);
  }
}
