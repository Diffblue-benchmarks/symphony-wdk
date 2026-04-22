package com.symphony.bdk.workflow.engine.executor.connection;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.connection.RemoveConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RemoveConnectionExecutor.class})
@ExtendWith(SpringExtension.class)
class RemoveConnectionExecutorDiffblueTest {
  @Autowired private RemoveConnectionExecutor removeConnectionExecutor;

  /**
   * Test {@link RemoveConnectionExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Then calls {@link ConnectionService#removeConnection(long)}.
   * </ul>
   *
   * <p>Method under test: {@link RemoveConnectionExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); then calls removeConnection(long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RemoveConnectionExecutor.execute(ActivityExecutorContext)"})
  void testExecute_thenCallsRemoveConnection() {
    // Arrange
    ConnectionService connectionService = mock(ConnectionService.class);
    doNothing().when(connectionService).removeConnection(123L);
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

    RemoveConnection activity = new RemoveConnection();
    activity.setUserId("123");

    ActivityExecutorContext<RemoveConnection> context = mock(ActivityExecutorContext.class);
    when(context.bdk()).thenReturn(springBdkGateway);
    when(context.getActivity()).thenReturn(activity);

    // Act
    removeConnectionExecutor.execute(context);

    // Assert
    verify(connectionService).removeConnection(123L);
    verify(context).bdk();
    verify(context).getActivity();
  }

  /**
   * Test {@link RemoveConnectionExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>When {@link ActivityExecutorContext} {@link ActivityExecutorContext#getActivity()} throw
   *       {@link IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link RemoveConnectionExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); when ActivityExecutorContext getActivity() throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void RemoveConnectionExecutor.execute(ActivityExecutorContext)"})
  void testExecute_whenActivityExecutorContextGetActivityThrowIllegalArgumentException() {
    // Arrange
    ActivityExecutorContext<RemoveConnection> context = mock(ActivityExecutorContext.class);
    when(context.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> removeConnectionExecutor.execute(context));
    verify(context).getActivity();
  }

  /**
   * Test {@link RemoveConnectionExecutor#doOboWithCache(ActivityExecutorContext)}.
   *
   * <p>Method under test: {@link RemoveConnectionExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Void RemoveConnectionExecutor.doOboWithCache(ActivityExecutorContext)"})
  void testDoOboWithCache() {
    // Arrange
    ActivityExecutorContext<RemoveConnection> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(
        IllegalArgumentException.class, () -> removeConnectionExecutor.doOboWithCache(execution));
    verify(execution).getActivity();
  }
}
