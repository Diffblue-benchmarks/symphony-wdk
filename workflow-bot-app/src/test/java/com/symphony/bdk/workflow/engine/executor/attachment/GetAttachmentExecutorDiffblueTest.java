package com.symphony.bdk.workflow.engine.executor.attachment;

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
import com.symphony.bdk.gen.api.model.V4Message;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.swadl.v1.activity.attachment.GetAttachment;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetAttachmentExecutorDiffblueTest {
  /**
   * Test {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}.
   * <p>
   * Method under test:
   * {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext)")
  void testExecute() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GetAttachmentExecutor getAttachmentExecutor = new GetAttachmentExecutor();
    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage(Mockito.<String>any()))
        .thenThrow(new IllegalArgumentException("Message with id %s not found"));
    ActivityExecutorContext<GetAttachment> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        messageService, mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new GetAttachment());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getAttachmentExecutor.execute(execution));
    verify(messageService).getMessage(isNull());
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}.
   * <p>
   * Method under test:
   * {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext)")
  void testExecute2() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GetAttachmentExecutor getAttachmentExecutor = new GetAttachmentExecutor();
    V4Message v4Message = mock(V4Message.class);
    when(v4Message.getAttachments())
        .thenThrow(new IllegalArgumentException("No attachment with id %s found in message with id %s"));
    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage(Mockito.<String>any())).thenReturn(v4Message);
    ActivityExecutorContext<GetAttachment> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        messageService, mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new GetAttachment());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getAttachmentExecutor.execute(execution));
    verify(messageService).getMessage(isNull());
    verify(v4Message).getAttachments();
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link MessageService} {@link MessageService#getMessage(String)}
   * return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given MessageService getMessage(String) return 'null'")
  void testExecute_givenMessageServiceGetMessageReturnNull() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GetAttachmentExecutor getAttachmentExecutor = new GetAttachmentExecutor();
    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage(Mockito.<String>any())).thenReturn(null);
    ActivityExecutorContext<GetAttachment> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        messageService, mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new GetAttachment());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> getAttachmentExecutor.execute(execution));
    verify(messageService).getMessage(isNull());
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then throw IllegalStateException")
  void testExecute_thenThrowIllegalStateException() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GetAttachmentExecutor getAttachmentExecutor = new GetAttachmentExecutor();
    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage(Mockito.<String>any())).thenReturn(new V4Message());
    ActivityExecutorContext<GetAttachment> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        messageService, mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new GetAttachment());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> getAttachmentExecutor.execute(execution));
    verify(messageService).getMessage(isNull());
    verify(execution).bdk();
    verify(execution).getActivity();
  }

  /**
   * Test {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Then throw {@link IllegalStateException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link GetAttachmentExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then throw IllegalStateException")
  void testExecute_thenThrowIllegalStateException2() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    GetAttachmentExecutor getAttachmentExecutor = new GetAttachmentExecutor();
    V4Message v4Message = mock(V4Message.class);
    when(v4Message.getAttachments()).thenReturn(new ArrayList<>());
    MessageService messageService = mock(MessageService.class);
    when(messageService.getMessage(Mockito.<String>any())).thenReturn(v4Message);
    ActivityExecutorContext<GetAttachment> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        messageService, mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new GetAttachment());

    // Act and Assert
    assertThrows(IllegalStateException.class, () -> getAttachmentExecutor.execute(execution));
    verify(messageService).getMessage(isNull());
    verify(v4Message, atLeast(1)).getAttachments();
    verify(execution).bdk();
    verify(execution).getActivity();
  }
}
