package com.symphony.bdk.workflow.engine.executor.connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.RemoveConnection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RemoveConnectionExecutorTest {

  private RemoveConnectionExecutor executor;

  @Mock
  private ActivityExecutorContext<RemoveConnection> context;

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
    executor = new RemoveConnectionExecutor();
    when(context.bdk()).thenReturn(bdkGateway);
  }

  @Test
  void execute_shouldRemoveConnectionWithoutObo() {
    // Arrange
    RemoveConnection activity = new RemoveConnection();
    activity.setUserId("12345");
    activity.setObo(null);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.connections()).thenReturn(connectionService);

    // Act
    executor.execute(context);

    // Assert
    verify(connectionService).removeConnection(12345L);
  }

  @Test
  void execute_shouldRemoveConnectionWithOboUsername() {
    // Arrange
    RemoveConnection activity = new RemoveConnection();
    activity.setUserId("67890");
    Obo obo = new Obo();
    obo.setUsername("testuser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.obo("testuser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);

    // Act
    executor.execute(context);

    // Assert
    verify(bdkGateway).obo("testuser");
    verify(bdkGateway).obo(authSession);
    verify(oboServices.connections()).removeConnection(67890L);
  }

  @Test
  void execute_shouldRemoveConnectionWithOboUserId() {
    // Arrange
    RemoveConnection activity = new RemoveConnection();
    activity.setUserId("11111");
    Obo obo = new Obo();
    obo.setUserId(99999L);
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.obo(99999L)).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);

    // Act
    executor.execute(context);

    // Assert
    verify(bdkGateway).obo(99999L);
    verify(bdkGateway).obo(authSession);
    verify(oboServices.connections()).removeConnection(11111L);
  }

  @Test
  void doOboWithCache_shouldRemoveConnectionWithObo() {
    // Arrange
    RemoveConnection activity = new RemoveConnection();
    activity.setUserId("22222");
    Obo obo = new Obo();
    obo.setUsername("obouser");
    activity.setObo(obo);

    when(context.getActivity()).thenReturn(activity);
    when(bdkGateway.obo("obouser")).thenReturn(authSession);
    when(bdkGateway.obo(authSession)).thenReturn(oboServices);
    when(oboServices.connections()).thenReturn(connectionService);

    // Act
    Void result = executor.doOboWithCache(context);

    // Assert
    assertThat(result).isNull();
    verify(bdkGateway).obo("obouser");
    verify(bdkGateway).obo(authSession);
    verify(oboServices.connections()).removeConnection(22222L);
  }
}
