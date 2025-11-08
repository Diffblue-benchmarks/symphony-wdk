package com.symphony.bdk.workflow.engine.camunda;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.script.ScriptEngineManager;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineBootstrapCommand;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.SchemaOperationsCommand;
import org.camunda.bpm.engine.impl.AuthorizationServiceImpl;
import org.camunda.bpm.engine.impl.DecisionServiceImpl;
import org.camunda.bpm.engine.impl.ExternalTaskServiceImpl;
import org.camunda.bpm.engine.impl.FilterServiceImpl;
import org.camunda.bpm.engine.impl.FormServiceImpl;
import org.camunda.bpm.engine.impl.HistoryLevelSetupCommand;
import org.camunda.bpm.engine.impl.HistoryServiceImpl;
import org.camunda.bpm.engine.impl.IdentityServiceImpl;
import org.camunda.bpm.engine.impl.ManagementServiceImpl;
import org.camunda.bpm.engine.impl.ProcessEngineImpl;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.RuntimeServiceImpl;
import org.camunda.bpm.engine.impl.TaskServiceImpl;
import org.camunda.bpm.engine.impl.cfg.JakartaTransactionProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.TransactionContextFactory;
import org.camunda.bpm.engine.impl.cmmn.CaseServiceImpl;
import org.camunda.bpm.engine.impl.el.JuelExpressionManager;
import org.camunda.bpm.engine.impl.history.HistoryLevelActivity;
import org.camunda.bpm.engine.impl.history.event.HostnameProvider;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;
import org.camunda.bpm.engine.impl.jobexecutor.DefaultJobExecutor;
import org.camunda.bpm.engine.impl.metrics.MetricsRegistry;
import org.camunda.bpm.engine.impl.metrics.MetricsReporterIdProvider;
import org.camunda.bpm.engine.impl.metrics.reporter.DbMetricsReporter;
import org.camunda.bpm.engine.impl.scripting.ScriptFactory;
import org.camunda.bpm.engine.impl.scripting.engine.DefaultScriptEngineResolver;
import org.camunda.bpm.engine.impl.scripting.engine.ScriptingEngines;
import org.camunda.bpm.engine.impl.scripting.env.ScriptEnvResolver;
import org.camunda.bpm.engine.impl.scripting.env.ScriptingEnvironment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CamundaEngineConfigurationDiffblueTest {
  @InjectMocks
  private CamundaEngineConfiguration camundaEngineConfiguration;

  /**
   * Test {@link CamundaEngineConfiguration#preInit(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link JuelExpressionManager#JuelExpressionManager()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngineConfiguration#preInit(ProcessEngineConfigurationImpl)}
   */
  @Test
  @DisplayName("Test preInit(ProcessEngineConfigurationImpl); given JuelExpressionManager()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CamundaEngineConfiguration.preInit(ProcessEngineConfigurationImpl)"})
  void testPreInit_givenJuelExpressionManager() {
    // Arrange
    JakartaTransactionProcessEngineConfiguration processEngineConfiguration = mock(
        JakartaTransactionProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExpressionManager()).thenReturn(new JuelExpressionManager());

    // Act
    camundaEngineConfiguration.preInit(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).getExpressionManager();
  }

  /**
   * Test {@link CamundaEngineConfiguration#preInit(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Then calls {@link JuelExpressionManager#addFunction(String, Method)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngineConfiguration#preInit(ProcessEngineConfigurationImpl)}
   */
  @Test
  @DisplayName("Test preInit(ProcessEngineConfigurationImpl); then calls addFunction(String, Method)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CamundaEngineConfiguration.preInit(ProcessEngineConfigurationImpl)"})
  void testPreInit_thenCallsAddFunction() {
    // Arrange
    JuelExpressionManager juelExpressionManager = mock(JuelExpressionManager.class);
    doNothing().when(juelExpressionManager).addFunction(Mockito.<String>any(), Mockito.<Method>any());
    JakartaTransactionProcessEngineConfiguration processEngineConfiguration = mock(
        JakartaTransactionProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExpressionManager()).thenReturn(juelExpressionManager);

    // Act
    camundaEngineConfiguration.preInit(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).getExpressionManager();
    verify(juelExpressionManager, atLeast(1)).addFunction(Mockito.<String>any(), Mockito.<Method>any());
  }

  /**
   * Test {@link CamundaEngineConfiguration#postInit(ProcessEngineConfigurationImpl)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls {@link ProcessEngineConfigurationImpl#getBeans()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngineConfiguration#postInit(ProcessEngineConfigurationImpl)}
   */
  @Test
  @DisplayName("Test postInit(ProcessEngineConfigurationImpl); given HashMap(); then calls getBeans()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void CamundaEngineConfiguration.postInit(ProcessEngineConfigurationImpl)"})
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

  /**
   * Test {@link CamundaEngineConfiguration#processEngineHealthIndicator(ProcessEngine)}.
   * <ul>
   *   <li>Then calls {@link ProcessEngineConfiguration#getDatabaseSchemaUpdate()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaEngineConfiguration#processEngineHealthIndicator(ProcessEngine)}
   */
  @Test
  @DisplayName("Test processEngineHealthIndicator(ProcessEngine); then calls getDatabaseSchemaUpdate()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "org.springframework.boot.actuate.health.HealthIndicator CamundaEngineConfiguration.processEngineHealthIndicator(ProcessEngine)"})
  void testProcessEngineHealthIndicator_thenCallsGetDatabaseSchemaUpdate() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Void>>any())).thenReturn(null);
    JakartaTransactionProcessEngineConfiguration processEngineConfiguration = mock(
        JakartaTransactionProcessEngineConfiguration.class);
    when(processEngineConfiguration.isDbMetricsReporterActivate()).thenReturn(true);
    when(processEngineConfiguration.getHostnameProvider()).thenReturn(mock(HostnameProvider.class));
    when(processEngineConfiguration.getHostname()).thenReturn("localhost");
    when(processEngineConfiguration.getMetricsReporterIdProvider()).thenReturn(mock(MetricsReporterIdProvider.class));
    when(processEngineConfiguration.getDbMetricsReporter())
        .thenReturn(new DbMetricsReporter(new MetricsRegistry(), mock(CommandExecutor.class)));
    when(processEngineConfiguration.isMetricsEnabled()).thenReturn(true);
    when(processEngineConfiguration.getProcessEngineBootstrapCommand())
        .thenReturn(mock(ProcessEngineBootstrapCommand.class));
    when(processEngineConfiguration.getHistoryLevelCommand()).thenReturn(new HistoryLevelSetupCommand());
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getAuthorizationService()).thenReturn(new AuthorizationServiceImpl());
    when(processEngineConfiguration.getCaseService()).thenReturn(new CaseServiceImpl());
    when(processEngineConfiguration.getDecisionService()).thenReturn(new DecisionServiceImpl());
    when(processEngineConfiguration.getExternalTaskService()).thenReturn(new ExternalTaskServiceImpl());
    when(processEngineConfiguration.getFilterService()).thenReturn(new FilterServiceImpl());
    when(processEngineConfiguration.getFormService()).thenReturn(new FormServiceImpl());
    when(processEngineConfiguration.getHistoryService()).thenReturn(new HistoryServiceImpl());
    when(processEngineConfiguration.getIdentityService()).thenReturn(new IdentityServiceImpl());
    when(processEngineConfiguration.getManagementService())
        .thenReturn(new ManagementServiceImpl(new JakartaTransactionProcessEngineConfiguration()));
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getSchemaOperationsCommand()).thenReturn(mock(SchemaOperationsCommand.class));
    when(processEngineConfiguration.getTaskService()).thenReturn(new TaskServiceImpl());
    when(processEngineConfiguration.getTransactionContextFactory()).thenReturn(mock(TransactionContextFactory.class));
    when(processEngineConfiguration.getHistoryLevel()).thenReturn(new HistoryLevelActivity());
    when(processEngineConfiguration.getCommandExecutorSchemaOperations()).thenReturn(commandExecutor);
    when(processEngineConfiguration.getCommandExecutorTxRequired()).thenReturn(mock(CommandExecutor.class));
    when(processEngineConfiguration.getJobExecutor()).thenReturn(new DefaultJobExecutor());

    // Act
    camundaEngineConfiguration.processEngineHealthIndicator(new ProcessEngineImpl(processEngineConfiguration));

    // Assert
    verify(processEngineConfiguration).getDatabaseSchemaUpdate();
    verify(processEngineConfiguration).getHistoryLevelCommand();
    verify(processEngineConfiguration).getProcessEngineBootstrapCommand();
    verify(processEngineConfiguration).getSchemaOperationsCommand();
    verify(processEngineConfiguration).getAuthorizationService();
    verify(processEngineConfiguration).getCaseService();
    verify(processEngineConfiguration).getCommandExecutorSchemaOperations();
    verify(processEngineConfiguration).getCommandExecutorTxRequired();
    verify(processEngineConfiguration).getDbMetricsReporter();
    verify(processEngineConfiguration).getDecisionService();
    verify(processEngineConfiguration).getExternalTaskService();
    verify(processEngineConfiguration).getFilterService();
    verify(processEngineConfiguration).getFormService();
    verify(processEngineConfiguration).getHistoryLevel();
    verify(processEngineConfiguration).getHistoryService();
    verify(processEngineConfiguration).getHostname();
    verify(processEngineConfiguration).getHostnameProvider();
    verify(processEngineConfiguration).getIdentityService();
    verify(processEngineConfiguration).getJobExecutor();
    verify(processEngineConfiguration).getManagementService();
    verify(processEngineConfiguration).getMetricsReporterIdProvider();
    verify(processEngineConfiguration).getProcessEngineName();
    verify(processEngineConfiguration).getRepositoryService();
    verify(processEngineConfiguration).getRuntimeService();
    verify(processEngineConfiguration).getSessionFactories();
    verify(processEngineConfiguration).getTaskService();
    verify(processEngineConfiguration).getTransactionContextFactory();
    verify(processEngineConfiguration).isDbMetricsReporterActivate();
    verify(processEngineConfiguration).isMetricsEnabled();
    verify(commandExecutor, atLeast(1)).execute(Mockito.<Command<Void>>any());
  }
}
