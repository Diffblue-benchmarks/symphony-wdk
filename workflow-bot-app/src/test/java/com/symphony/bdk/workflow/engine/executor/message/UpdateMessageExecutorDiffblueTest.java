package com.symphony.bdk.workflow.engine.executor.message;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
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
import com.symphony.bdk.core.service.message.model.Message;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.message.UpdateMessage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Map;

class UpdateMessageExecutorDiffblueTest {

  /**
   * Test {@link UpdateMessageExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>When {@link ActivityExecutorContext} {@link ActivityExecutorContext#getActivity()} throw
   *       {@link IllegalArgumentException#IllegalArgumentException()}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); when ActivityExecutorContext getActivity() throw IllegalArgumentException()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateMessageExecutor.execute(ActivityExecutorContext)"})
  void testExecute_whenActivityExecutorContextGetActivityThrowIllegalArgumentException()
      throws IOException {
    // Arrange
    UpdateMessageExecutor updateMessageExecutor = new UpdateMessageExecutor();
    ActivityExecutorContext<UpdateMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getActivity()).thenThrow(new IllegalArgumentException());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> updateMessageExecutor.execute(execution));
    verify(execution).getActivity();
  }

  /**
   * Test {@link UpdateMessageExecutor#execute(ActivityExecutorContext)}.
   *
   * <ul>
   *   <li>Given {@link MessageService} {@link MessageService#getMessage(String)} return {@link
   *       V4Message} (default constructor).
   *   <li>Then calls {@link MessageService#update(V4Message, Message)}.
   * </ul>
   *
   * <p>Method under test: {@link UpdateMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName(
      "Test execute(ActivityExecutorContext); given MessageService getMessage(String) return V4Message; then calls update(V4Message, Message)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void UpdateMessageExecutor.execute(ActivityExecutorContext)"})
  void testExecute_givenMessageServiceGetMessageReturnV4Message_thenCallsUpdate()
      throws IOException {
    // Arrange
    UpdateMessageExecutor updateMessageExecutor = new UpdateMessageExecutor();

    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage(Mockito.<String>any())).thenReturn(new V4Message());
    V4Message updatedMessage = new V4Message();
    updatedMessage.messageId("updatedMsgId");
    when(messageService.update(any(V4Message.class), any(Message.class))).thenReturn(updatedMessage);

    SpringBdkGateway springBdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            messageService,
            mock(StreamService.class),
            mock(UserService.class),
            mock(ConnectionService.class),
            mock(SymphonyGroupService.class),
            mock(SessionService.class));

    UpdateMessage activity = new UpdateMessage();
    activity.setContent("Hello World");

    ActivityExecutorContext<UpdateMessage> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariables(Mockito.<Map<String, Object>>any());
    when(context.bdk()).thenReturn(springBdkGateway);
    when(context.getActivity()).thenReturn(activity);

    // Act
    updateMessageExecutor.execute(context);

    // Assert
    verify(messageService).getMessage(null);
    verify(messageService).update(any(V4Message.class), any(Message.class));
    verify(context, Mockito.atLeast(1)).bdk();
    verify(context).setOutputVariables(isA(Map.class));
  }
}
