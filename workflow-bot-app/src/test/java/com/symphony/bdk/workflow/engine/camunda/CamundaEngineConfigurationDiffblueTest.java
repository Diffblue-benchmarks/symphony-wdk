package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeast;
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
import com.symphony.bdk.workflow.engine.secret.DefaultSecretKeeper;
import com.symphony.bdk.workflow.engine.secret.SecretCryptVault;
import com.symphony.bdk.workflow.engine.secret.SecretRepository;
import com.symphony.bdk.workflow.engine.shared.DefaultSharedDataStore;
import com.symphony.bdk.workflow.engine.shared.SharedDataRepository;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineBootstrapCommand;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

@ExtendWith(MockitoExtension.class)
class CamundaEngineConfigurationDiffblueTest {
  @InjectMocks private CamundaEngineConfiguration camundaEngineConfiguration;

  /**
   * Test {@link CamundaEngineConfiguration#preInit(ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>Given {@link JuelExpressionManager#JuelExpressionManager()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * CamundaEngineConfiguration#preInit(ProcessEngineConfigurationImpl)}
   */
  @Test
  @DisplayName("Test preInit(ProcessEngineConfigurationImpl); given JuelExpressionManager()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngineConfiguration.preInit(ProcessEngineConfigurationImpl)"})
  void testPreInit_givenJuelExpressionManager() {
    // Arrange
    JakartaTransactionProcessEngineConfiguration processEngineConfiguration =
        mock(JakartaTransactionProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExpressionManager()).thenReturn(new JuelExpressionManager());

    // Act
    camundaEngineConfiguration.preInit(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).getExpressionManager();
  }

  /**
   * Test {@link CamundaEngineConfiguration#preInit(ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>Then calls {@link JuelExpressionManager#addFunction(String, Method)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * CamundaEngineConfiguration#preInit(ProcessEngineConfigurationImpl)}
   */
  @Test
  @DisplayName(
      "Test preInit(ProcessEngineConfigurationImpl); then calls addFunction(String, Method)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngineConfiguration.preInit(ProcessEngineConfigurationImpl)"})
  void testPreInit_thenCallsAddFunction() {
    // Arrange
    JuelExpressionManager juelExpressionManager = mock(JuelExpressionManager.class);
    doNothing()
        .when(juelExpressionManager)
        .addFunction(Mockito.<String>any(), Mockito.<Method>any());

    JakartaTransactionProcessEngineConfiguration processEngineConfiguration =
        mock(JakartaTransactionProcessEngineConfiguration.class);
    when(processEngineConfiguration.getExpressionManager()).thenReturn(juelExpressionManager);

    // Act
    camundaEngineConfiguration.preInit(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).getExpressionManager();
    verify(juelExpressionManager, atLeast(1))
        .addFunction(Mockito.<String>any(), Mockito.<Method>any());
  }

  /**
   * Test {@link CamundaEngineConfiguration#postInit(ProcessEngineConfigurationImpl)}.
   *
   * <ul>
   *   <li>Then {@link JakartaTransactionProcessEngineConfiguration} (default constructor) Beans
   *       size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * CamundaEngineConfiguration#postInit(ProcessEngineConfigurationImpl)}
   */
  @Test
  @DisplayName(
      "Test postInit(ProcessEngineConfigurationImpl); then JakartaTransactionProcessEngineConfiguration (default constructor) Beans size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CamundaEngineConfiguration.postInit(ProcessEngineConfigurationImpl)"})
  void testPostInit_thenJakartaTransactionProcessEngineConfigurationBeansSizeIsOne() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    SpringBdkGateway bdkGateway =
        new SpringBdkGateway(
            mock(BdkConfig.class),
            mock(AuthenticatorFactory.class),
            mock(MessageService.class),
            mock(StreamService.class),
            mock(UserService.class),
            mock(ConnectionService.class),
            mock(SymphonyGroupService.class),
            mock(SessionService.class));
    DefaultSharedDataStore sharedDataStore =
        new DefaultSharedDataStore(mock(SharedDataRepository.class));
    SecretRepository repository = mock(SecretRepository.class);
    DefaultSecretKeeper secretKeeper = new DefaultSecretKeeper(repository, new SecretCryptVault());

    CamundaEngineConfiguration camundaEngineConfiguration =
        new CamundaEngineConfiguration(bdkGateway, sharedDataStore, secretKeeper);

    HashMap<Object, Object> beans = new HashMap<>();
    beans.put(UtilityFunctionsMapper.WDK_PREFIX, "Process Engine Configuration");

    JakartaTransactionProcessEngineConfiguration processEngineConfiguration =
        new JakartaTransactionProcessEngineConfiguration();
    processEngineConfiguration.setBeans(beans);

    // Act
    camundaEngineConfiguration.postInit(processEngineConfiguration);

    // Assert
    Map<Object, Object> beans2 = processEngineConfiguration.getBeans();
    assertEquals(1, beans2.size());
    assertTrue(beans2.get(UtilityFunctionsMapper.WDK_PREFIX) instanceof UtilityFunctionsMapper);
  }

  /**
   * Test {@link CamundaEngineConfiguration#processEngineHealthIndicator(ProcessEngine)}.
   *
   * <ul>
   *   <li>Then return health Details {@code name} is {@code Process Engine Name}.
   * </ul>
   *
   * <p>Method under test: {@link
   * CamundaEngineConfiguration#processEngineHealthIndicator(ProcessEngine)}
   */
  @Test
  @DisplayName(
      "Test processEngineHealthIndicator(ProcessEngine); then return health Details 'name' is 'Process Engine Name'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HealthIndicator CamundaEngineConfiguration.processEngineHealthIndicator(ProcessEngine)"
  })
  void testProcessEngineHealthIndicator_thenReturnHealthDetailsNameIsProcessEngineName() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Void>>any())).thenReturn(null);

    JakartaTransactionProcessEngineConfiguration processEngineConfiguration =
        mock(JakartaTransactionProcessEngineConfiguration.class);
    when(processEngineConfiguration.isDbMetricsReporterActivate()).thenReturn(true);
    when(processEngineConfiguration.getHostnameProvider()).thenReturn(mock(HostnameProvider.class));
    when(processEngineConfiguration.getHostname()).thenReturn("localhost");
    when(processEngineConfiguration.getMetricsReporterIdProvider())
        .thenReturn(mock(MetricsReporterIdProvider.class));
    DbMetricsReporter dbMetricsReporter =
        new DbMetricsReporter(new MetricsRegistry(), mock(CommandExecutor.class));
    when(processEngineConfiguration.getDbMetricsReporter()).thenReturn(dbMetricsReporter);
    when(processEngineConfiguration.isMetricsEnabled()).thenReturn(true);
    when(processEngineConfiguration.getProcessEngineBootstrapCommand())
        .thenReturn(mock(ProcessEngineBootstrapCommand.class));
    when(processEngineConfiguration.getHistoryLevelCommand())
        .thenReturn(new HistoryLevelSetupCommand());
    when(processEngineConfiguration.getDatabaseSchemaUpdate()).thenReturn("2020-03-01");
    when(processEngineConfiguration.getProcessEngineName()).thenReturn("Process Engine Name");
    Mockito.<Map<Class<?>, SessionFactory>>when(processEngineConfiguration.getSessionFactories())
        .thenReturn(new HashMap<>());
    when(processEngineConfiguration.getAuthorizationService())
        .thenReturn(new AuthorizationServiceImpl());
    when(processEngineConfiguration.getCaseService()).thenReturn(new CaseServiceImpl());
    when(processEngineConfiguration.getDecisionService()).thenReturn(new DecisionServiceImpl());
    when(processEngineConfiguration.getExternalTaskService())
        .thenReturn(new ExternalTaskServiceImpl());
    when(processEngineConfiguration.getFilterService()).thenReturn(new FilterServiceImpl());
    when(processEngineConfiguration.getFormService()).thenReturn(new FormServiceImpl());
    when(processEngineConfiguration.getHistoryService()).thenReturn(new HistoryServiceImpl());
    when(processEngineConfiguration.getIdentityService()).thenReturn(new IdentityServiceImpl());
    when(processEngineConfiguration.getManagementService())
        .thenReturn(new ManagementServiceImpl(new JakartaTransactionProcessEngineConfiguration()));
    when(processEngineConfiguration.getRepositoryService()).thenReturn(new RepositoryServiceImpl());
    when(processEngineConfiguration.getRuntimeService()).thenReturn(new RuntimeServiceImpl());
    when(processEngineConfiguration.getSchemaOperationsCommand())
        .thenReturn(mock(SchemaOperationsCommand.class));
    when(processEngineConfiguration.getTaskService()).thenReturn(new TaskServiceImpl());
    when(processEngineConfiguration.getTransactionContextFactory())
        .thenReturn(mock(TransactionContextFactory.class));
    when(processEngineConfiguration.getHistoryLevel()).thenReturn(new HistoryLevelActivity());
    when(processEngineConfiguration.getCommandExecutorSchemaOperations())
        .thenReturn(commandExecutor);
    when(processEngineConfiguration.getCommandExecutorTxRequired())
        .thenReturn(mock(CommandExecutor.class));
    when(processEngineConfiguration.getJobExecutor()).thenReturn(new DefaultJobExecutor());

    // Act
    Health actualHealthResult =
        camundaEngineConfiguration
            .processEngineHealthIndicator(new ProcessEngineImpl(processEngineConfiguration))
            .health();

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
    Map<String, Object> details = actualHealthResult.getDetails();
    assertEquals(1, details.size());
    assertEquals("Process Engine Name", details.get("name"));
    Status status = actualHealthResult.getStatus();
    assertEquals("UP", status.getCode());
    assertEquals("UP", status.toString());
  }

  /**
   * Test {@link CamundaEngineConfiguration#processEngineHealthIndicator(ProcessEngine)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return health Status Code is {@code DOWN}.
   * </ul>
   *
   * <p>Method under test: {@link
   * CamundaEngineConfiguration#processEngineHealthIndicator(ProcessEngine)}
   */
  @Test
  @DisplayName(
      "Test processEngineHealthIndicator(ProcessEngine); when 'null'; then return health Status Code is 'DOWN'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "HealthIndicator CamundaEngineConfiguration.processEngineHealthIndicator(ProcessEngine)"
  })
  void testProcessEngineHealthIndicator_whenNull_thenReturnHealthStatusCodeIsDown() {
    // Arrange and Act
    Health actualHealthResult =
        camundaEngineConfiguration.processEngineHealthIndicator(null).health();

    // Assert
    Status status = actualHealthResult.getStatus();
    assertEquals("DOWN", status.getCode());
    assertEquals("DOWN", status.toString());
    Map<String, Object> details = actualHealthResult.getDetails();
    assertEquals(1, details.size());
    assertEquals(
        "java.lang.NullPointerException: Cannot invoke \"org.camunda.bpm.engine.ProcessEngine.getName()\" because"
            + " \"this.val$processEngine\" is null",
        details.get("error"));
  }
}
