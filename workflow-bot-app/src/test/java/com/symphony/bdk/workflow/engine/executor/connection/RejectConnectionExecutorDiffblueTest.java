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
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.connection.OboConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.gen.api.model.UserConnection;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.RejectConnection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RejectConnectionExecutorDiffblueTest {

  /**
   * Test {@link RejectConnectionExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then calls {@link ConnectionService#rejectConnection(long)}.
   * </ul>
   *
   * <p>Method under test: {@link RejectConnectionExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then calls rejectConnection(long)")
  @Tag("ContributionFromDiffblue")
  void testExecute_thenCallsRejectConnection() {
    // Arrange
    RejectConnectionExecutor rejectConnectionExecutor = new RejectConnectionExecutor();

    ConnectionService connectionService = mock(ConnectionService.class);
    when(connectionService.rejectConnection(Mockito.anyLong())).thenReturn(new UserConnection());

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

    RejectConnection activity = new RejectConnection();
    activity.setUserId("1234");

    ActivityExecutorContext<RejectConnection> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.bdk()).thenReturn(springBdkGateway);
    when(context.getActivity()).thenReturn(activity);

    // Act
    rejectConnectionExecutor.execute(context);

    // Assert
    verify(connectionService).rejectConnection(1234L);
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("connection"), isA(UserConnection.class));
  }

  /**
   * Test {@link RejectConnectionExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then calls {@link OboConnectionService#rejectConnection(long)}.
   * </ul>
   *
   * <p>Method under test: {@link RejectConnectionExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); then calls OBO rejectConnection(long)")
  @Tag("ContributionFromDiffblue")
  void testDoOboWithCache_thenCallsOboRejectConnection() {
    // Arrange
    RejectConnectionExecutor rejectConnectionExecutor = new RejectConnectionExecutor();

    Obo obo = new Obo();
    obo.setUsername("testUser");

    RejectConnection activity = new RejectConnection();
    activity.setUserId("1234");
    activity.setObo(obo);

    UserConnection userConnection = new UserConnection();
    OboConnectionService oboConnectionService = mock(OboConnectionService.class);
    when(oboConnectionService.rejectConnection(Mockito.anyLong())).thenReturn(userConnection);

    OboServices oboServices = mock(OboServices.class);
    when(oboServices.connections()).thenReturn(oboConnectionService);

    AuthSession authSession = mock(AuthSession.class);

    BdkGateway bdkGateway = mock(BdkGateway.class);
    when(bdkGateway.obo(any(String.class))).thenReturn(authSession);
    when(bdkGateway.obo(any(AuthSession.class))).thenReturn(oboServices);

    ActivityExecutorContext<RejectConnection> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(bdkGateway);
    when(execution.getActivity()).thenReturn(activity);

    // Act
    rejectConnectionExecutor.doOboWithCache(execution);

    // Assert
    verify(oboConnectionService).rejectConnection(1234L);
    verify(bdkGateway).obo(authSession);
    verify(oboServices).connections();
  }
}
