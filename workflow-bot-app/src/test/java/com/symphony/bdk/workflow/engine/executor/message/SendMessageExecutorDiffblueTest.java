package com.symphony.bdk.workflow.engine.executor.message;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.auth.exception.AuthInitializationException;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.ext.group.SymphonyGroupService;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.swadl.v1.activity.Obo;
import com.symphony.bdk.workflow.swadl.v1.activity.message.SendMessage;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SendMessageExecutorDiffblueTest {
  /**
   * Test {@link SendMessageExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link EventHolder#EventHolder()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given EventHolder()")
  void testExecute_givenEventHolder() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SendMessageExecutor sendMessageExecutor = new SendMessageExecutor();
    ActivityExecutorContext<SendMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getEvent()).thenReturn(new EventHolder<>());
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new SendMessage());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> sendMessageExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(execution, atLeast(1)).getEvent();
  }

  /**
   * Test {@link SendMessageExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link IllegalArgumentException#IllegalArgumentException(String)}
   * with {@code Sending message...}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given IllegalArgumentException(String) with 'Sending message...'")
  void testExecute_givenIllegalArgumentExceptionWithSendingMessage() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SendMessageExecutor sendMessageExecutor = new SendMessageExecutor();
    ActivityExecutorContext<SendMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getEvent()).thenThrow(new IllegalArgumentException("Sending message..."));
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new SendMessage());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> sendMessageExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(execution).getEvent();
  }

  /**
   * Test {@link SendMessageExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ActivityExecutorContext}
   * {@link ActivityExecutorContext#getEvent()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given 'null'; when ActivityExecutorContext getEvent() return 'null'")
  void testExecute_givenNull_whenActivityExecutorContextGetEventReturnNull() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SendMessageExecutor sendMessageExecutor = new SendMessageExecutor();
    ActivityExecutorContext<SendMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getEvent()).thenReturn(null);
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new SendMessage());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> sendMessageExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(execution, atLeast(1)).getEvent();
  }

  /**
   * Test {@link SendMessageExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link SendMessage} (default constructor).</li>
   *   <li>Then calls {@link EventHolder#getSource()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given SendMessage (default constructor); then calls getSource()")
  void testExecute_givenSendMessage_thenCallsGetSource() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SendMessageExecutor sendMessageExecutor = new SendMessageExecutor();
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenThrow(new IllegalArgumentException("foo"));
    ActivityExecutorContext<SendMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getEvent()).thenReturn(eventHolder);
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new SendMessage());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> sendMessageExecutor.execute(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(execution, atLeast(1)).getEvent();
    verify(eventHolder).getSource();
  }

  /**
   * Test {@link SendMessageExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Then calls {@link AuthenticatorFactory#getOboAuthenticator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); then calls getOboAuthenticator()")
  void testExecute_thenCallsGetOboAuthenticator() throws AuthInitializationException, IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SendMessageExecutor sendMessageExecutor = new SendMessageExecutor();
    BdkConfig config = mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);
    AuthenticatorFactory authenticatorFactory = mock(AuthenticatorFactory.class);
    when(authenticatorFactory.getOboAuthenticator()).thenThrow(new IllegalArgumentException("Sending message..."));
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(config, authenticatorFactory, mock(MessageService.class),
        mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class));

    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");
    SendMessage sendMessage = mock(SendMessage.class);
    when(sendMessage.getObo()).thenReturn(obo);
    when(sendMessage.getData()).thenReturn("Data");
    when(sendMessage.getAttachments()).thenReturn(new ArrayList<>());
    when(sendMessage.getContent()).thenReturn("Not all who wander are lost");
    when(sendMessage.getTemplate()).thenReturn("Template");
    when(sendMessage.getTemplatePath()).thenReturn("Template Path");
    when(sendMessage.getTo()).thenReturn(resultTo);
    ActivityExecutorContext<SendMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(sendMessage);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> sendMessageExecutor.execute(execution));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(config).isOboConfigured();
    verify(execution, atLeast(1)).bdk();
    verify(execution, atLeast(1)).getActivity();
    verify(sendMessage, atLeast(1)).getObo();
    verify(sendMessage, atLeast(1)).getAttachments();
    verify(sendMessage, atLeast(1)).getContent();
    verify(sendMessage, atLeast(1)).getData();
    verify(sendMessage, atLeast(1)).getTemplate();
    verify(sendMessage, atLeast(1)).getTemplatePath();
    verify(sendMessage, atLeast(1)).getTo();
  }

  /**
   * Test {@link SendMessageExecutor#doOboWithCache(ActivityExecutorContext)}.
   * <p>
   * Method under test:
   * {@link SendMessageExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext)")
  void testDoOboWithCache() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SendMessageExecutor sendMessageExecutor = new SendMessageExecutor();
    ActivityExecutorContext<SendMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getEvent())
        .thenThrow(new IllegalArgumentException("No stream id set to send a message in activity %s"));
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new SendMessage());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> sendMessageExecutor.doOboWithCache(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(execution).getEvent();
  }

  /**
   * Test {@link SendMessageExecutor#doOboWithCache(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link EventHolder#EventHolder()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendMessageExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); given EventHolder()")
  void testDoOboWithCache_givenEventHolder() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SendMessageExecutor sendMessageExecutor = new SendMessageExecutor();
    ActivityExecutorContext<SendMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getEvent()).thenReturn(new EventHolder<>());
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new SendMessage());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> sendMessageExecutor.doOboWithCache(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(execution, atLeast(1)).getEvent();
  }

  /**
   * Test {@link SendMessageExecutor#doOboWithCache(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ActivityExecutorContext}
   * {@link ActivityExecutorContext#getEvent()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendMessageExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); given 'null'; when ActivityExecutorContext getEvent() return 'null'")
  void testDoOboWithCache_givenNull_whenActivityExecutorContextGetEventReturnNull() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SendMessageExecutor sendMessageExecutor = new SendMessageExecutor();
    ActivityExecutorContext<SendMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getEvent()).thenReturn(null);
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new SendMessage());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> sendMessageExecutor.doOboWithCache(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(execution, atLeast(1)).getEvent();
  }

  /**
   * Test {@link SendMessageExecutor#doOboWithCache(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link SendMessage} (default constructor).</li>
   *   <li>Then calls {@link EventHolder#getSource()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendMessageExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); given SendMessage (default constructor); then calls getSource()")
  void testDoOboWithCache_givenSendMessage_thenCallsGetSource() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SendMessageExecutor sendMessageExecutor = new SendMessageExecutor();
    EventHolder<Object> eventHolder = mock(EventHolder.class);
    when(eventHolder.getSource()).thenThrow(new IllegalArgumentException("foo"));
    ActivityExecutorContext<SendMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.getEvent()).thenReturn(eventHolder);
    when(execution.bdk()).thenReturn(new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        mock(MessageService.class), mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class)));
    when(execution.getActivity()).thenReturn(new SendMessage());

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> sendMessageExecutor.doOboWithCache(execution));
    verify(execution).bdk();
    verify(execution).getActivity();
    verify(execution, atLeast(1)).getEvent();
    verify(eventHolder).getSource();
  }

  /**
   * Test {@link SendMessageExecutor#doOboWithCache(ActivityExecutorContext)}.
   * <ul>
   *   <li>Then calls {@link AuthenticatorFactory#getOboAuthenticator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendMessageExecutor#doOboWithCache(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test doOboWithCache(ActivityExecutorContext); then calls getOboAuthenticator()")
  void testDoOboWithCache_thenCallsGetOboAuthenticator() throws AuthInitializationException, IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SendMessageExecutor sendMessageExecutor = new SendMessageExecutor();
    BdkConfig config = mock(BdkConfig.class);
    when(config.isOboConfigured()).thenReturn(true);
    AuthenticatorFactory authenticatorFactory = mock(AuthenticatorFactory.class);
    when(authenticatorFactory.getOboAuthenticator()).thenThrow(new IllegalArgumentException("2.0"));
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(config, authenticatorFactory, mock(MessageService.class),
        mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class));

    SendMessage.To resultTo = new SendMessage.To();
    resultTo.setStreamId("42");
    resultTo.setStreamIds(new ArrayList<>());
    resultTo.setUserIds(new ArrayList<>());

    Obo obo = new Obo();
    obo.setUserId(1L);
    obo.setUsername("janedoe");
    SendMessage sendMessage = mock(SendMessage.class);
    when(sendMessage.getObo()).thenReturn(obo);
    when(sendMessage.getData()).thenReturn("Data");
    when(sendMessage.getAttachments()).thenReturn(new ArrayList<>());
    when(sendMessage.getContent()).thenReturn("Not all who wander are lost");
    when(sendMessage.getTemplate()).thenReturn("Template");
    when(sendMessage.getTemplatePath()).thenReturn("Template Path");
    when(sendMessage.getTo()).thenReturn(resultTo);
    ActivityExecutorContext<SendMessage> execution = mock(ActivityExecutorContext.class);
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(sendMessage);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> sendMessageExecutor.doOboWithCache(execution));
    verify(authenticatorFactory).getOboAuthenticator();
    verify(config).isOboConfigured();
    verify(execution, atLeast(1)).bdk();
    verify(execution, atLeast(1)).getActivity();
    verify(sendMessage, atLeast(1)).getObo();
    verify(sendMessage, atLeast(1)).getAttachments();
    verify(sendMessage).getContent();
    verify(sendMessage, atLeast(1)).getData();
    verify(sendMessage).getTemplate();
    verify(sendMessage).getTemplatePath();
    verify(sendMessage, atLeast(1)).getTo();
  }
}
