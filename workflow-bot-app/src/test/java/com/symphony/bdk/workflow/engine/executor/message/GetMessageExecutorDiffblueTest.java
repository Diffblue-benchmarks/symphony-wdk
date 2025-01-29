package com.symphony.bdk.workflow.engine.executor.message;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.message.GetMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetMessageExecutorDiffblueTest {
  /**
   * Test {@link GetMessageExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link MessageService} {@link MessageService#getMessage(String)}
   * return {@link V4Message} (default constructor).</li>
   *   <li>Then calls {@link MessageService#getMessage(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given MessageService getMessage(String) return V4Message (default constructor); then calls getMessage(String)")
  void testExecute_givenMessageServiceGetMessageReturnV4Message_thenCallsGetMessage() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GetMessageExecutor getMessageExecutor = new GetMessageExecutor();
    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage(Mockito.<String>any())).thenReturn(new V4Message());
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        messageService, mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class));

    ActivityExecutorContext<GetMessage> context = mock(ActivityExecutorContext.class);
    doNothing().when(context).setOutputVariable(Mockito.<String>any(), Mockito.<Object>any());
    when(context.bdk()).thenReturn(springBdkGateway);
    when(context.getActivity()).thenReturn(new GetMessage());

    // Act
    getMessageExecutor.execute(context);

    // Assert
    verify(messageService).getMessage(isNull());
    verify(context).bdk();
    verify(context).getActivity();
    verify(context).setOutputVariable(eq("message"), isA(Object.class));
  }
}
