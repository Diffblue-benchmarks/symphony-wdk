package com.symphony.bdk.workflow.engine.camunda;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.core.auth.AuthenticatorFactory;
import com.symphony.bdk.core.client.ApiClientFactory;
import com.symphony.bdk.core.config.model.BdkConfig;
import com.symphony.bdk.core.retry.RetryWithRecoveryBuilder;
import com.symphony.bdk.core.service.connection.ConnectionService;
import com.symphony.bdk.core.service.message.MessageService;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.core.service.stream.StreamService;
import com.symphony.bdk.core.service.user.UserService;
import com.symphony.bdk.gen.api.AttachmentsApi;
import com.symphony.bdk.gen.api.AuditTrailApi;
import com.symphony.bdk.gen.api.ConnectionApi;
import com.symphony.bdk.gen.api.DefaultApi;
import com.symphony.bdk.gen.api.MessageApi;
import com.symphony.bdk.gen.api.MessageSuppressionApi;
import com.symphony.bdk.gen.api.MessagesApi;
import com.symphony.bdk.gen.api.PodApi;
import com.symphony.bdk.gen.api.RoomMembershipApi;
import com.symphony.bdk.gen.api.SessionApi;
import com.symphony.bdk.gen.api.ShareApi;
import com.symphony.bdk.gen.api.StreamsApi;
import com.symphony.bdk.gen.api.UserApi;
import com.symphony.bdk.gen.api.UsersApi;
import com.symphony.bdk.template.freemarker.FreeMarkerEngine;
import com.symphony.bdk.workflow.engine.SpringBdkGateway;
import com.symphony.bdk.workflow.engine.secret.DefaultSecretKeeper;
import com.symphony.bdk.workflow.engine.secret.SecretCryptVault;
import com.symphony.bdk.workflow.engine.secret.SecretRepository;
import com.symphony.bdk.workflow.engine.shared.DefaultSharedDataStore;
import com.symphony.bdk.workflow.engine.shared.SharedDataRepository;
import java.util.ArrayList;
import java.util.HashMap;
import javax.script.ScriptEngineManager;
import org.camunda.bpm.engine.impl.cfg.JakartaTransactionProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.el.JuelExpressionManager;
import org.camunda.bpm.engine.impl.scripting.ScriptFactory;
import org.camunda.bpm.engine.impl.scripting.engine.DefaultScriptEngineResolver;
import org.camunda.bpm.engine.impl.scripting.engine.ScriptingEngines;
import org.camunda.bpm.engine.impl.scripting.env.ScriptEnvResolver;
import org.camunda.bpm.engine.impl.scripting.env.ScriptingEnvironment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CamundaEngineConfigurationDiffblueTest {
  /**
   * Test
   * {@link CamundaEngineConfiguration#preInit(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link JuelExpressionManager#JuelExpressionManager()}.</li>
   *   <li>Then calls
   * {@link ProcessEngineConfigurationImpl#getExpressionManager()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CamundaEngineConfiguration#preInit(ProcessEngineConfigurationImpl)}
   */
  @Test
  @DisplayName("Test preInit(ProcessEngineConfigurationImpl); given JuelExpressionManager(); then calls getExpressionManager()")
  void testPreInit_givenJuelExpressionManager_thenCallsGetExpressionManager() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BdkConfig config = new BdkConfig();
    BdkConfig bdkConfig = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = new AuthenticatorFactory(bdkConfig,
        new ApiClientFactory(new BdkConfig()));

    MessagesApi messagesApi = new MessagesApi(null);
    MessageApi messageApi = new MessageApi(null);
    MessageSuppressionApi messageSuppressionApi = new MessageSuppressionApi(null);
    StreamsApi streamsApi = new StreamsApi(null);
    PodApi podApi = new PodApi(null);
    AttachmentsApi attachmentsApi = new AttachmentsApi(null);
    DefaultApi defaultApi = new DefaultApi(null);
    FreeMarkerEngine templateEngine = new FreeMarkerEngine();
    MessageService messageService = new MessageService(messagesApi, messageApi, messageSuppressionApi, streamsApi,
        podApi, attachmentsApi, defaultApi, templateEngine, new RetryWithRecoveryBuilder<>());

    StreamsApi streamsApi2 = new StreamsApi(null);
    RoomMembershipApi membershipApi = new RoomMembershipApi(null);
    ShareApi shareApi = new ShareApi(null);
    StreamService streamService = new StreamService(streamsApi2, membershipApi, shareApi,
        new RetryWithRecoveryBuilder<>());

    UserApi userApi = new UserApi(null);
    UsersApi usersApi = new UsersApi(null);
    AuditTrailApi auditTrailApi = new AuditTrailApi(null);
    UserService userService = new UserService(userApi, usersApi, auditTrailApi, new RetryWithRecoveryBuilder<>());

    ConnectionApi connectionApi = new ConnectionApi(null);
    ConnectionService connectionService = new ConnectionService(connectionApi, new RetryWithRecoveryBuilder<>());

    SessionApi sessionApi = new SessionApi(null);
    SpringBdkGateway bdkGateway = new SpringBdkGateway(config, authenticatorFactory, messageService, streamService,
        userService, connectionService, null, new SessionService(sessionApi, new RetryWithRecoveryBuilder<>()));

    DefaultSharedDataStore sharedDataStore = new DefaultSharedDataStore(mock(SharedDataRepository.class));
    SecretRepository repository = mock(SecretRepository.class);
    CamundaEngineConfiguration camundaEngineConfiguration = new CamundaEngineConfiguration(bdkGateway, sharedDataStore,
        new DefaultSecretKeeper(repository, new SecretCryptVault()));
    JakartaTransactionProcessEngineConfiguration processEngineConfiguration = mock(
        JakartaTransactionProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExpressionManager()).thenReturn(new JuelExpressionManager());

    // Act
    camundaEngineConfiguration.preInit(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).getExpressionManager();
  }

  /**
   * Test
   * {@link CamundaEngineConfiguration#postInit(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getBeans()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CamundaEngineConfiguration#postInit(ProcessEngineConfigurationImpl)}
   */
  @Test
  @DisplayName("Test postInit(ProcessEngineConfigurationImpl); given HashMap(); then calls getBeans()")
  void testPostInit_givenHashMap_thenCallsGetBeans() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    BdkConfig config = new BdkConfig();
    BdkConfig bdkConfig = new BdkConfig();
    AuthenticatorFactory authenticatorFactory = new AuthenticatorFactory(bdkConfig,
        new ApiClientFactory(new BdkConfig()));

    MessagesApi messagesApi = new MessagesApi(null);
    MessageApi messageApi = new MessageApi(null);
    MessageSuppressionApi messageSuppressionApi = new MessageSuppressionApi(null);
    StreamsApi streamsApi = new StreamsApi(null);
    PodApi podApi = new PodApi(null);
    AttachmentsApi attachmentsApi = new AttachmentsApi(null);
    DefaultApi defaultApi = new DefaultApi(null);
    FreeMarkerEngine templateEngine = new FreeMarkerEngine();
    MessageService messageService = new MessageService(messagesApi, messageApi, messageSuppressionApi, streamsApi,
        podApi, attachmentsApi, defaultApi, templateEngine, new RetryWithRecoveryBuilder<>());

    StreamsApi streamsApi2 = new StreamsApi(null);
    RoomMembershipApi membershipApi = new RoomMembershipApi(null);
    ShareApi shareApi = new ShareApi(null);
    StreamService streamService = new StreamService(streamsApi2, membershipApi, shareApi,
        new RetryWithRecoveryBuilder<>());

    UserApi userApi = new UserApi(null);
    UsersApi usersApi = new UsersApi(null);
    AuditTrailApi auditTrailApi = new AuditTrailApi(null);
    UserService userService = new UserService(userApi, usersApi, auditTrailApi, new RetryWithRecoveryBuilder<>());

    ConnectionApi connectionApi = new ConnectionApi(null);
    ConnectionService connectionService = new ConnectionService(connectionApi, new RetryWithRecoveryBuilder<>());

    SessionApi sessionApi = new SessionApi(null);
    SpringBdkGateway bdkGateway = new SpringBdkGateway(config, authenticatorFactory, messageService, streamService,
        userService, connectionService, null, new SessionService(sessionApi, new RetryWithRecoveryBuilder<>()));

    DefaultSharedDataStore sharedDataStore = new DefaultSharedDataStore(mock(SharedDataRepository.class));
    SecretRepository repository = mock(SecretRepository.class);
    CamundaEngineConfiguration camundaEngineConfiguration = new CamundaEngineConfiguration(bdkGateway, sharedDataStore,
        new DefaultSecretKeeper(repository, new SecretCryptVault()));
    JakartaTransactionProcessEngineConfiguration processEngineConfiguration = mock(
        JakartaTransactionProcessEngineConfiguration.class);
    when(processEngineConfiguration.getBeans()).thenReturn(new HashMap<>());
    ScriptFactory scriptFactory = new ScriptFactory();
    ArrayList<ScriptEnvResolver> scriptEnvResolvers = new ArrayList<>();
    when(processEngineConfiguration.getScriptingEnvironment()).thenReturn(new ScriptingEnvironment(scriptFactory,
        scriptEnvResolvers, new ScriptingEngines(new DefaultScriptEngineResolver(new ScriptEngineManager()))));
    doNothing().when(processEngineConfiguration).setScriptingEnvironment(Mockito.<ScriptingEnvironment>any());

    // Act
    camundaEngineConfiguration.postInit(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).getBeans();
    verify(processEngineConfiguration).getScriptingEnvironment();
    verify(processEngineConfiguration).setScriptingEnvironment(isA(ScriptingEnvironment.class));
  }
}
