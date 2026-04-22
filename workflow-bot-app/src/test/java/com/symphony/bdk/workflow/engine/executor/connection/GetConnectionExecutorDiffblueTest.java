package com.symphony.bdk.workflow.engine.executor.connection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.OboServices;
import com.symphony.bdk.core.auth.AuthSession;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.GetConnection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetConnectionExecutorDiffblueTest {

  /**
   * Test {@link GetConnectionExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link ConnectionService} {@link ConnectionService#getConnection(Long)} return {@link UserConnection}.
   *   <li>Then calls {@link ConnectionService#getConnection(Long)}.
   * </ul>
   *
   * <p>Method under test: {@link GetConnectionExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given ConnectionService getConnection(Long) return UserConnection; then calls getConnection(Long)")
  void testExecute_thenCallsGetConnection() {
    // Arrange
    GetConnectionExecutor getConnectionExecutor = new GetConnectionExecutor();

    ConnectionService connectionService = mock(ConnectionService.class);
    when(connectionService.getConnection(Mockito.<Long>any())).thenReturn(new UserConnection());
    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            mock(StreamService.class),
            mock(UserService.class),
            connectionService,
            mock(SymphonyGroupService.class),
            mock(SessionService.class));

    GetConnection activity = new GetConnection();
    activity.setUserId("12345");

    ActivityExecutorContext<GetConnection> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.bdk()).thenReturn(springBdkGateway);
    when(context.getActivity()).thenReturn(activity);

    // Act
    getConnectionExecutor.execute(context);

    // Assert
    verify(connectionService).getConnection(12345L);
    verify(context, atLeast(1)).bdk();
    verify(context, atLeast(1)).getActivity();
    verify(context).setOutputVariable(eq("connection"), isA(Object.class));
  }

  /**
   * Test {@link GetConnectionExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given OBO activity with username set.
   *   <li>Then calls OBO connections getConnection.
   * </ul>
   *
   * <p>Method under test: {@link GetConnectionExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); given OBO username; then calls OBO getConnection")
  void testDoOboWithCache_thenCallsOboGetConnection() {
    // Arrange
    GetConnectionExecutor getConnectionExecutor = new GetConnectionExecutor();

    ConnectionService oboConnectionService = mock(ConnectionService.class);
    when(oboConnectionService.getConnection(anyLong())).thenReturn(new UserConnection());

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.connections()).thenReturn(oboConnectionService);

    AuthSession authSession = mock(AuthSession.class);

    com.symphony.bdk.workflow.engine.executor.BdkGateway bdkGateway =
        mock(com.symphony.bdk.workflow.engine.executor.BdkGateway.class);
    when(bdkGateway.obo(anyString())).thenReturn(authSession);
    when(bdkGateway.obo(any(AuthSession.class))).thenReturn(oboServices);

    Obo obo = new Obo();
    obo.setUsername("testuser");

    GetConnection activity = new GetConnection();
    activity.setUserId("67890");
    activity.setObo(obo);

    ActivityExecutorContext<GetConnection> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.bdk()).thenReturn(bdkGateway);
    when(context.getActivity()).thenReturn(activity);

    // Act
    getConnectionExecutor.execute(context);

    // Assert
    verify(oboConnectionService).getConnection(67890L);
    verify(context).setOutputVariable(eq("connection"), isA(Object.class));
  }
}
