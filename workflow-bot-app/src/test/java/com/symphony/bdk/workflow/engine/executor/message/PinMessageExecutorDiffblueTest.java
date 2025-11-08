package com.symphony.bdk.workflow.engine.executor.message;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
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
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.message.PinMessage;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PinMessageExecutor.class})
@ExtendWith(SpringExtension.class)
class PinMessageExecutorDiffblueTest {
  @Autowired
  private PinMessageExecutor pinMessageExecutor;

  /**
   * Method under test:
   * {@link PinMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  void testExecute() throws IOException {
    // Arrange
    ActivityExecutorContext<PinMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenThrow(new IllegalArgumentException("foo"));
    when(execution.getActivity()).thenReturn(new PinMessage());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> pinMessageExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution, atLeast(1)).getActivity();
  }

  /**
   * Method under test:
   * {@link PinMessageExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  void testDoOboWithCache() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    PinMessageExecutor pinMessageExecutor = new PinMessageExecutor();
    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage(Mockito.<String>any())).thenThrow(new IllegalArgumentException("foo"));
    ActivityExecutorContext<PinMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        messageService, mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new PinMessage());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> pinMessageExecutor.doOboWithCache(execution));
    verify(messageService).getMessage(isNull());
    verify(execution).bdk();
    verify(execution).getActivity();
  }
}
