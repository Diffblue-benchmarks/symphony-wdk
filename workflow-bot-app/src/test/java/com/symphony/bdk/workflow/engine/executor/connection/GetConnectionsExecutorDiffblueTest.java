package com.symphony.bdk.workflow.engine.executor.connection;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.connection.constant.ConnectionStatus;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.GetConnections;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetConnectionsExecutorDiffblueTest {
  /**
   * Test {@link GetConnectionsExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Then calls {@link ConnectionService#listConnections(ConnectionStatus, List)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link GetConnectionsExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then calls listConnections(ConnectionStatus, List)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void GetConnectionsExecutor.execute(ActivityExecutorContext)"})
  void testExecute_thenCallsListConnections() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GetConnectionsExecutor getConnectionsExecutor = new GetConnectionsExecutor();
    ConnectionService connectionService = mock(ConnectionService.class);
    when(connectionService.listConnections(Mockito.<ConnectionStatus>any(), Mockito.<List<Long>>any()))
        .thenReturn(new ArrayList<>());
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), mock(UserService.class), connectionService,
        mock(SymphonyGroupService.class), mock(SessionService.class));

    ActivityExecutorContext<GetConnections> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.bdk()).thenReturn(springBdkGateway);
    when(context.getActivity()).thenReturn(new GetConnections());

    // Act
    getConnectionsExecutor.execute(context);

    // Assert
    verify(connectionService).listConnections(isNull(), isNull());
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("connections"), isA(Object.class));
  }
}
