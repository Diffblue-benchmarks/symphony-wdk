package com.symphony.bdk.workflow.engine.executor.message;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import com.symphony.bdk.template.api.Template;
import com.symphony.bdk.template.api.TemplateEngine;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
import com.symphony.bdk.workflow.engine.secret.DefaultSecretKeeper;
import com.symphony.bdk.workflow.engine.secret.SecretCryptVault;
import com.symphony.bdk.workflow.engine.secret.SecretRepository;
import com.symphony.bdk.workflow.engine.shared.DefaultSharedDataStore;
import com.symphony.bdk.workflow.engine.shared.SharedDataRepository;
import com.symphony.bdk.workflow.swadl.v1.activity.message.UpdateMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class UpdateMessageExecutorDiffblueTest {
  /**
   * Test {@link UpdateMessageExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link Template} {@link Template#process(Object)} return
   * {@code <messageML>}.</li>
   *   <li>Then calls {@link MessageService#getMessage(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UpdateMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given Template process(Object) return '<messageML>'; then calls getMessage(String)")
  void testExecute_givenTemplateProcessReturnMessageML_thenCallsGetMessage() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    UpdateMessageExecutor updateMessageExecutor = new UpdateMessageExecutor();
    Template template = mock(Template.class);
    when(template.process(Mockito.<Object>any())).thenReturn("<messageML>");
    TemplateEngine templateEngine = mock(TemplateEngine.class);
    when(templateEngine.newTemplateFromString(Mockito.<String>any())).thenReturn(template);
    MessageService messageService = mock(MessageService.class);
    when(messageService.update(Mockito.<V4Message>any(), Mockito.<Message>any())).thenReturn(new V4Message());
    when(messageService.getMessage(Mockito.<String>any())).thenReturn(new V4Message());
    when(messageService.templates()).thenReturn(templateEngine);
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        messageService, mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class));

    ActivityExecutorContext<UpdateMessage> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    SecretRepository repository = mock(SecretRepository.class);
    when(execution.secretKeeper()).thenReturn(new DefaultSecretKeeper(repository, new SecretCryptVault()));
    when(execution.sharedDataStore()).thenReturn(new DefaultSharedDataStore(mock(SharedDataRepository.class)));
    when(execution.getVariables()).thenReturn(new HashMap<>());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(new UpdateMessage());

    // Act
    updateMessageExecutor.execute(execution);

    // Assert
    verify(messageService).getMessage(isNull());
    verify(messageService).templates();
    verify(messageService).update(isA(V4Message.class), isA(Message.class));
    verify(template).process(isA(Object.class));
    verify(templateEngine).newTemplateFromString(isNull());
    verify(execution, atLeast(1)).bdk();
    verify(execution, atLeast(1)).getActivity();
    verify(execution).getVariables();
    verify(execution).secretKeeper();
    verify(execution).setOutputVariables(isA(Map.class));
    verify(execution).sharedDataStore();
  }

  /**
   * Test {@link UpdateMessageExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link Template} {@link Template#process(Object)} return
   * {@code </messageML>}.</li>
   *   <li>Then calls {@link MessageService#getMessage(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UpdateMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given Template process(Object) return '</messageML>'; then calls getMessage(String)")
  void testExecute_givenTemplateProcessReturnMessageML_thenCallsGetMessage2() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    UpdateMessageExecutor updateMessageExecutor = new UpdateMessageExecutor();
    Template template = mock(Template.class);
    when(template.process(Mockito.<Object>any())).thenReturn("</messageML>");
    TemplateEngine templateEngine = mock(TemplateEngine.class);
    when(templateEngine.newTemplateFromString(Mockito.<String>any())).thenReturn(template);
    MessageService messageService = mock(MessageService.class);
    when(messageService.update(Mockito.<V4Message>any(), Mockito.<Message>any())).thenReturn(new V4Message());
    when(messageService.getMessage(Mockito.<String>any())).thenReturn(new V4Message());
    when(messageService.templates()).thenReturn(templateEngine);
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        messageService, mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class));

    ActivityExecutorContext<UpdateMessage> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    SecretRepository repository = mock(SecretRepository.class);
    when(execution.secretKeeper()).thenReturn(new DefaultSecretKeeper(repository, new SecretCryptVault()));
    when(execution.sharedDataStore()).thenReturn(new DefaultSharedDataStore(mock(SharedDataRepository.class)));
    when(execution.getVariables()).thenReturn(new HashMap<>());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(new UpdateMessage());

    // Act
    updateMessageExecutor.execute(execution);

    // Assert
    verify(messageService).getMessage(isNull());
    verify(messageService).templates();
    verify(messageService).update(isA(V4Message.class), isA(Message.class));
    verify(template).process(isA(Object.class));
    verify(templateEngine).newTemplateFromString(isNull());
    verify(execution, atLeast(1)).bdk();
    verify(execution, atLeast(1)).getActivity();
    verify(execution).getVariables();
    verify(execution).secretKeeper();
    verify(execution).setOutputVariables(isA(Map.class));
    verify(execution).sharedDataStore();
  }

  /**
   * Test {@link UpdateMessageExecutor#execute(ActivityExecutorContext)}.
   * <ul>
   *   <li>Given {@link Template} {@link Template#process(Object)} return
   * {@code Process}.</li>
   *   <li>Then calls {@link MessageService#getMessage(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UpdateMessageExecutor#execute(ActivityExecutorContext)}
   */
  @Test
  @DisplayName("Test execute(ActivityExecutorContext); given Template process(Object) return 'Process'; then calls getMessage(String)")
  void testExecute_givenTemplateProcessReturnProcess_thenCallsGetMessage() throws IOException {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    UpdateMessageExecutor updateMessageExecutor = new UpdateMessageExecutor();
    Template template = mock(Template.class);
    when(template.process(Mockito.<Object>any())).thenReturn("Process");
    TemplateEngine templateEngine = mock(TemplateEngine.class);
    when(templateEngine.newTemplateFromString(Mockito.<String>any())).thenReturn(template);
    MessageService messageService = mock(MessageService.class);
    when(messageService.update(Mockito.<V4Message>any(), Mockito.<Message>any())).thenReturn(new V4Message());
    when(messageService.getMessage(Mockito.<String>any())).thenReturn(new V4Message());
    when(messageService.templates()).thenReturn(templateEngine);
    SpringBdkGateway springBdkGateway = new SpringBdkGateway(mock(BdkConfig.class), mock(AuthenticatorFactory.class),
        messageService, mock(StreamService.class), mock(UserService.class), mock(ConnectionService.class),
        mock(SymphonyGroupService.class), mock(SessionService.class));

    ActivityExecutorContext<UpdateMessage> execution = mock(ActivityExecutorContext.class);
    doNothing().when(execution).setOutputVariables(Mockito.<Map<String, Object>>any());
    SecretRepository repository = mock(SecretRepository.class);
    when(execution.secretKeeper()).thenReturn(new DefaultSecretKeeper(repository, new SecretCryptVault()));
    when(execution.sharedDataStore()).thenReturn(new DefaultSharedDataStore(mock(SharedDataRepository.class)));
    when(execution.getVariables()).thenReturn(new HashMap<>());
    when(execution.bdk()).thenReturn(springBdkGateway);
    when(execution.getActivity()).thenReturn(new UpdateMessage());

    // Act
    updateMessageExecutor.execute(execution);

    // Assert
    verify(messageService).getMessage(isNull());
    verify(messageService).templates();
    verify(messageService).update(isA(V4Message.class), isA(Message.class));
    verify(template).process(isA(Object.class));
    verify(templateEngine).newTemplateFromString(isNull());
    verify(execution, atLeast(1)).bdk();
    verify(execution, atLeast(1)).getActivity();
    verify(execution).getVariables();
    verify(execution).secretKeeper();
    verify(execution).setOutputVariables(isA(Map.class));
    verify(execution).sharedDataStore();
  }
}
