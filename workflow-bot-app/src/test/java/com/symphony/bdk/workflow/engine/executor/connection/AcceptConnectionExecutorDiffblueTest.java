package com.symphony.bdk.workflow.engine.executor.connection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.auth.impl.AuthSessionImpl;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.AcceptConnection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AcceptConnectionExecutorDiffblueTest {

  /**
   * Test {@link AcceptConnectionExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then calls {@link ConnectionService#acceptConnection(Long)}.
   * </ul>
   *
   * <p>Method under test: {@link AcceptConnectionExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then calls acceptConnection(Long)")
  @Tag("ContributionFromDiffblue")
  void testExecute_thenCallsAcceptConnection() {
    // Arrange
    AcceptConnectionExecutor acceptConnectionExecutor = new AcceptConnectionExecutor();

    ConnectionService connectionService = mock(ConnectionService.class);
    UserConnection userConnection = new UserConnection();
    when(connectionService.acceptConnection(Mockito.<Long>any())).thenReturn(userConnection);

    SpringBdkGateway springBdkGateway = mock(SpringBdkGateway.class);
    when(springBdkGateway.connections()).thenReturn(connectionService);

    AcceptConnection acceptConnection = new AcceptConnection();
    acceptConnection.setUserId("123");

    ActivityExecutorContext<AcceptConnection> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.bdk()).thenReturn(springBdkGateway);
    when(context.getActivity()).thenReturn(acceptConnection);

    // Act
    acceptConnectionExecutor.execute(context);

    // Assert
    verify(connectionService).acceptConnection(eq(123L));
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("connection"), isA(Object.class));
  }

  /**
   * Test {@link AcceptConnectionExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then calls {@link ConnectionService#acceptConnection(Long)} via OBO session.
   * </ul>
   *
   * <p>Method under test: {@link AcceptConnectionExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); then calls obo acceptConnection(Long)")
  @Tag("ContributionFromDiffblue")
  void testDoOboWithCache_thenCallsOboAcceptConnection() {
    // Arrange
    AcceptConnectionExecutor acceptConnectionExecutor = new AcceptConnectionExecutor();

    Obo obo = new Obo();
    obo.setUsername("Activity");
    obo.setUserId(null);

    AcceptConnection activity = new AcceptConnection();
    activity.setUserId("456");
    activity.setObo(obo);

    AuthSessionImpl authSession = new AuthSessionImpl(null);

    ConnectionService connectionService = mock(ConnectionService.class);
    UserConnection userConnection = new UserConnection();
    when(connectionService.acceptConnection(Mockito.<Long>any())).thenReturn(userConnection);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.connections()).thenReturn(connectionService);

    SpringBdkGateway springBdkGateway = mock(SpringBdkGateway.class);
    when(springBdkGateway.obo(Mockito.<String>any())).thenReturn(authSession);
    when(springBdkGateway.obo(any(AuthSession.class))).thenReturn(oboServices);

    ActivityExecutorContext<AcceptConnection> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(activity);

    // Act
    UserConnection result = acceptConnectionExecutor.doOboWithCache(execution);

    // Assert
    verify(connectionService).acceptConnection(eq(456L));
    verify(springBdkGateway).obo("Activity");
    verify(springBdkGateway).obo(any(AuthSession.class));
  }
}
