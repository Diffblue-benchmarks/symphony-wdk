package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.el.ExpressionManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

class CamundaEngineConfigurationClaude_preInitTest {

  private CamundaEngineConfiguration configuration;
  private BdkGateway bdkGateway;
  private SharedDataStore sharedDataStore;
  private SecretKeeper secretKeeper;
  private ProcessEngineConfigurationImpl processEngineConfiguration;
  private ExpressionManager expressionManager;

  @BeforeEach
  void setUp() {
    bdkGateway = mock(BdkGateway.class);
    sharedDataStore = mock(SharedDataStore.class);
    secretKeeper = mock(SecretKeeper.class);
    processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    expressionManager = mock(ExpressionManager.class);

    when(processEngineConfiguration.getExpressionManager()).thenReturn(expressionManager);

    configuration = new CamundaEngineConfiguration(bdkGateway, sharedDataStore, secretKeeper);
  }

  @Test
  void preInit_shouldRegisterTextFunction() {
    // When: preInit is called
    configuration.preInit(processEngineConfiguration);

    // Then: text function should be registered
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.TEXT), any(Method.class));
  }

  @Test
  void preInit_shouldRegisterJsonFunction() {
    // When: preInit is called
    configuration.preInit(processEngineConfiguration);

    // Then: json function should be registered
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.JSON), any(Method.class));
  }

  @Test
  void preInit_shouldRegisterEscapeFunction() {
    // When: preInit is called
    configuration.preInit(processEngineConfiguration);

    // Then: escape function should be registered
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.ESCAPE), any(Method.class));
  }

  @Test
  void preInit_shouldRegisterMentionsFunction() {
    // When: preInit is called
    configuration.preInit(processEngineConfiguration);

    // Then: mentions function should be registered
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.MENTIONS), any(Method.class));
  }

  @Test
  void preInit_shouldRegisterHashtagsFunction() {
    // When: preInit is called
    configuration.preInit(processEngineConfiguration);

    // Then: hashtags function should be registered
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.HASHTAGS), any(Method.class));
  }

  @Test
  void preInit_shouldRegisterCashtagsFunction() {
    // When: preInit is called
    configuration.preInit(processEngineConfiguration);

    // Then: cashtags function should be registered
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.CASHTAGS), any(Method.class));
  }

  @Test
  void preInit_shouldRegisterEmojisFunction() {
    // When: preInit is called
    configuration.preInit(processEngineConfiguration);

    // Then: emojis function should be registered
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.EMOJIS), any(Method.class));
  }

  @Test
  void preInit_shouldRegisterSessionFunction() {
    // When: preInit is called
    configuration.preInit(processEngineConfiguration);

    // Then: session function should be registered
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.SESSION), any(Method.class));
  }

  @Test
  void preInit_shouldRegisterReadSharedFunction() {
    // When: preInit is called
    configuration.preInit(processEngineConfiguration);

    // Then: readShared function should be registered
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.READSHARED), any(Method.class));
  }

  @Test
  void preInit_shouldRegisterWriteSharedFunction() {
    // When: preInit is called
    configuration.preInit(processEngineConfiguration);

    // Then: writeShared function should be registered
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.WRITESHARED), any(Method.class));
  }

  @Test
  void preInit_shouldRegisterSecretFunction() {
    // When: preInit is called
    configuration.preInit(processEngineConfiguration);

    // Then: secret function should be registered
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.SECRET), any(Method.class));
  }

  @Test
  void preInit_shouldRegisterAllFunctions() {
    // When: preInit is called
    configuration.preInit(processEngineConfiguration);

    // Then: all 11 functions should be registered
    verify(expressionManager, times(11)).addFunction(any(String.class), any(Method.class));
  }

  @Test
  void preInit_shouldGetExpressionManagerFromConfiguration() {
    // When: preInit is called
    configuration.preInit(processEngineConfiguration);

    // Then: expression manager should be retrieved from configuration
    verify(processEngineConfiguration).getExpressionManager();
  }

  @Test
  void preInit_calledMultipleTimes_shouldRegisterFunctionsEachTime() {
    // When: preInit is called twice
    configuration.preInit(processEngineConfiguration);
    configuration.preInit(processEngineConfiguration);

    // Then: functions should be registered twice (11 * 2 = 22 times)
    verify(expressionManager, times(22)).addFunction(any(String.class), any(Method.class));
  }

  @Test
  void preInit_withDifferentConfiguration_shouldWorkCorrectly() {
    // Given: A different process engine configuration
    ProcessEngineConfigurationImpl anotherConfig = mock(ProcessEngineConfigurationImpl.class);
    ExpressionManager anotherExpressionManager = mock(ExpressionManager.class);
    when(anotherConfig.getExpressionManager()).thenReturn(anotherExpressionManager);

    // When: preInit is called on the different configuration
    configuration.preInit(anotherConfig);

    // Then: functions should be registered on the new expression manager
    verify(anotherExpressionManager, times(11)).addFunction(any(String.class), any(Method.class));
    verify(anotherConfig).getExpressionManager();
  }
}
