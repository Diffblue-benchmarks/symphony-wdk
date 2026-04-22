package com.symphony.bdk.workflow.engine.executor.connection;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.CreateConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CreateConnectionExecutorDiffblueTest {

  /**
   * Test {@link CreateConnectionExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then calls {@link ConnectionService#createConnection(long)}.
   * </ul>
   *
   * <p>Method under test: {@link CreateConnectionExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then calls createConnection(long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateConnectionExecutor.execute(ActivityExecutorContext)"})
  void testExecute_thenCallsCreateConnection() {
    // Arrange
    CreateConnectionExecutor createConnectionExecutor = new CreateConnectionExecutor();

    ConnectionService connectionService = mock(ConnectionService.class);
    UserConnection userConnection = new UserConnection();
    when(connectionService.createConnection(123L)).thenReturn(userConnection);

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

    CreateConnection activity = new CreateConnection();
    activity.setUserId("123");

    ActivityExecutorContext<CreateConnection> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.bdk()).thenReturn(springBdkGateway);
    when(context.getActivity()).thenReturn(activity);

    // Act
    createConnectionExecutor.execute(context);

    // Assert
    verify(connectionService).createConnection(123L);
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("connection"), isA(Object.class));
  }

  /**
   * Test {@link CreateConnectionExecutor#execute(ActivityExecutorContext)} with OBO.
   *
   * <ul>
   *   <li>Then calls {@link ConnectionService#createConnection(long)} via OBO session.
   * </ul>
   *
   * <p>Method under test: {@link CreateConnectionExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); with OBO; then calls createConnection via OBO session")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CreateConnectionExecutor.execute(ActivityExecutorContext)"})
  void testExecute_withObo_thenCallsCreateConnectionViaOboSession() {
    // Arrange
    CreateConnectionExecutor createConnectionExecutor = new CreateConnectionExecutor();

    AuthSession authSession = mock(AuthSession.class);
    ConnectionService oboConnectionService = mock(ConnectionService.class);
    OboServices oboServices = mock(OboServices.class);
    UserConnection userConnection = new UserConnection();

    when(oboConnectionService.createConnection(456L)).thenReturn(userConnection);
    when(oboServices.connections()).thenReturn(oboConnectionService);

    BdkGateway bdk = mock(BdkGateway.class);
    when(bdk.obo(Mockito.<String>any())).thenReturn(authSession);
    when(bdk.obo(Mockito.<AuthSession>any())).thenReturn(oboServices);

    Obo obo = new Obo();
    obo.setUsername("testUser");

    CreateConnection activity = new CreateConnection();
    activity.setUserId("456");
    activity.setObo(obo);

    ActivityExecutorContext<CreateConnection> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.bdk()).thenReturn(bdk);
    when(context.getActivity()).thenReturn(activity);

    // Act
    createConnectionExecutor.execute(context);

    // Assert
    verify(oboConnectionService).createConnection(456L);
    verify(context).setOutputVariable(eq("connection"), isA(Object.class));
  }

  /**
   * Test {@link CreateConnectionExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link CreateConnectionExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test doOboWithCache(ActivityExecutorContext); then throw IllegalArgumentException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.gen.api.model.UserConnection CreateConnectionExecutor.doOboWithCache(ActivityExecutorContext)"
  })
  void testDoOboWithCache_thenThrowIllegalArgumentException() {
    // Arrange
    CreateConnectionExecutor createConnectionExecutor = new CreateConnectionExecutor();
    ActivityExecutorContext<CreateConnection> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class,
        () -> createConnectionExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }
}
