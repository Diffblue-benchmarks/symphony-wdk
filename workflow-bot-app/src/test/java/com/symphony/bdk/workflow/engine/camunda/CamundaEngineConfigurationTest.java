package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.model.UserV2;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.el.ExpressionManager;
import org.camunda.bpm.engine.impl.scripting.ExecutableScript;
import org.camunda.bpm.engine.impl.scripting.env.ScriptingEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CamundaEngineConfigurationTest {

  @Mock
  private BdkGateway bdkGateway;

  @Mock
  private SharedDataStore sharedDataStore;

  @Mock
  private SecretKeeper secretKeeper;

  @Mock
  private SessionService sessionService;

  @Mock
  private ProcessEngineConfigurationImpl processEngineConfiguration;

  @Mock
  private ExpressionManager expressionManager;

  @Mock
  private ProcessEngine processEngine;

  private CamundaEngineConfiguration configuration;

  @BeforeEach
  void setUp() {
    configuration = new CamundaEngineConfiguration(bdkGateway, sharedDataStore, secretKeeper);
  }

  @Test
  void shouldRegisterTextFunctionWhenPreInitIsCalled() {
    // Arrange
    when(processEngineConfiguration.getExpressionManager()).thenReturn(expressionManager);

    // Act
    configuration.preInit(processEngineConfiguration);

    // Assert
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.TEXT), any(Method.class));
  }

  @Test
  void shouldRegisterJsonFunctionWhenPreInitIsCalled() {
    // Arrange
    when(processEngineConfiguration.getExpressionManager()).thenReturn(expressionManager);

    // Act
    configuration.preInit(processEngineConfiguration);

    // Assert
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.JSON), any(Method.class));
  }

  @Test
  void shouldRegisterEscapeFunctionWhenPreInitIsCalled() {
    // Arrange
    when(processEngineConfiguration.getExpressionManager()).thenReturn(expressionManager);

    // Act
    configuration.preInit(processEngineConfiguration);

    // Assert
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.ESCAPE), any(Method.class));
  }

  @Test
  void shouldRegisterMentionsFunctionWhenPreInitIsCalled() {
    // Arrange
    when(processEngineConfiguration.getExpressionManager()).thenReturn(expressionManager);

    // Act
    configuration.preInit(processEngineConfiguration);

    // Assert
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.MENTIONS), any(Method.class));
  }

  @Test
  void shouldRegisterHashtagsFunctionWhenPreInitIsCalled() {
    // Arrange
    when(processEngineConfiguration.getExpressionManager()).thenReturn(expressionManager);

    // Act
    configuration.preInit(processEngineConfiguration);

    // Assert
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.HASHTAGS), any(Method.class));
  }

  @Test
  void shouldRegisterCashtagsFunctionWhenPreInitIsCalled() {
    // Arrange
    when(processEngineConfiguration.getExpressionManager()).thenReturn(expressionManager);

    // Act
    configuration.preInit(processEngineConfiguration);

    // Assert
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.CASHTAGS), any(Method.class));
  }

  @Test
  void shouldRegisterEmojisFunctionWhenPreInitIsCalled() {
    // Arrange
    when(processEngineConfiguration.getExpressionManager()).thenReturn(expressionManager);

    // Act
    configuration.preInit(processEngineConfiguration);

    // Assert
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.EMOJIS), any(Method.class));
  }

  @Test
  void shouldRegisterSessionFunctionWhenPreInitIsCalled() {
    // Arrange
    when(processEngineConfiguration.getExpressionManager()).thenReturn(expressionManager);

    // Act
    configuration.preInit(processEngineConfiguration);

    // Assert
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.SESSION), any(Method.class));
  }

  @Test
  void shouldRegisterReadSharedFunctionWhenPreInitIsCalled() {
    // Arrange
    when(processEngineConfiguration.getExpressionManager()).thenReturn(expressionManager);

    // Act
    configuration.preInit(processEngineConfiguration);

    // Assert
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.READSHARED), any(Method.class));
  }

  @Test
  void shouldRegisterWriteSharedFunctionWhenPreInitIsCalled() {
    // Arrange
    when(processEngineConfiguration.getExpressionManager()).thenReturn(expressionManager);

    // Act
    configuration.preInit(processEngineConfiguration);

    // Assert
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.WRITESHARED), any(Method.class));
  }

  @Test
  void shouldRegisterSecretFunctionWhenPreInitIsCalled() {
    // Arrange
    when(processEngineConfiguration.getExpressionManager()).thenReturn(expressionManager);

    // Act
    configuration.preInit(processEngineConfiguration);

    // Assert
    verify(expressionManager).addFunction(eq(UtilityFunctionsMapper.SECRET), any(Method.class));
  }

  @Test
  void shouldRegisterUtilityFunctionsMapperBeanWhenPostInitIsCalled() {
    // Arrange
    Map<Object, Object> beans = new HashMap<>();
    when(processEngineConfiguration.getBeans()).thenReturn(beans);
    when(bdkGateway.session()).thenReturn(sessionService);
    ScriptingEnvironment scriptingEnvironment = mock(ScriptingEnvironment.class);
    when(processEngineConfiguration.getScriptingEnvironment()).thenReturn(scriptingEnvironment);

    // Act
    configuration.postInit(processEngineConfiguration);

    // Assert
    assertThat(beans).containsKey(UtilityFunctionsMapper.WDK_PREFIX);
    assertThat(beans.get(UtilityFunctionsMapper.WDK_PREFIX)).isInstanceOf(UtilityFunctionsMapper.class);
  }

  @Test
  void shouldSetScriptingEnvironmentWhenPostInitIsCalled() {
    // Arrange
    Map<Object, Object> beans = new HashMap<>();
    when(processEngineConfiguration.getBeans()).thenReturn(beans);
    when(bdkGateway.session()).thenReturn(sessionService);
    ScriptingEnvironment scriptingEnvironment = mock(ScriptingEnvironment.class);
    when(processEngineConfiguration.getScriptingEnvironment()).thenReturn(scriptingEnvironment);

    // Act
    configuration.postInit(processEngineConfiguration);

    // Assert
    verify(processEngineConfiguration).setScriptingEnvironment(any(ScriptingEnvironment.class));
  }

  @Test
  void shouldWrapScriptExceptionInBpmnErrorWhenScriptExecutionFails() throws Exception {
    // Arrange
    Map<Object, Object> beans = new HashMap<>();
    when(processEngineConfiguration.getBeans()).thenReturn(beans);
    when(bdkGateway.session()).thenReturn(sessionService);
    ScriptingEnvironment scriptingEnvironment = mock(ScriptingEnvironment.class);
    when(processEngineConfiguration.getScriptingEnvironment()).thenReturn(scriptingEnvironment);
    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    RuntimeException expectedException = new RuntimeException("Script error");
    when(scriptingEnvironment.execute(script, scope)).thenThrow(expectedException);

    ArgumentCaptor<ScriptingEnvironment> captor = ArgumentCaptor.forClass(ScriptingEnvironment.class);

    // Act
    configuration.postInit(processEngineConfiguration);
    verify(processEngineConfiguration).setScriptingEnvironment(captor.capture());

    ScriptingEnvironment customScriptingEnvironment = captor.getValue();

    // Assert
    assertThatThrownBy(() -> customScriptingEnvironment.execute(script, scope))
        .isInstanceOf(BpmnError.class)
        .satisfies(thrown -> {
          BpmnError bpmnError = (BpmnError) thrown;
          assertThat(bpmnError.getErrorCode()).isEqualTo("FAILURE");
          assertThat(bpmnError.getCause()).isEqualTo(expectedException);
        });
  }

  @Test
  void shouldWrapScriptExceptionInBpmnErrorWhenScriptExecutionWithBindingsFails() throws Exception {
    // Arrange
    Map<Object, Object> beans = new HashMap<>();
    when(processEngineConfiguration.getBeans()).thenReturn(beans);
    when(bdkGateway.session()).thenReturn(sessionService);
    ScriptingEnvironment scriptingEnvironment = mock(ScriptingEnvironment.class);
    when(processEngineConfiguration.getScriptingEnvironment()).thenReturn(scriptingEnvironment);
    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    Bindings bindings = mock(Bindings.class);
    ScriptEngine scriptEngine = mock(ScriptEngine.class);
    RuntimeException expectedException = new RuntimeException("Script error with bindings");
    when(scriptingEnvironment.execute(script, scope, bindings, scriptEngine)).thenThrow(expectedException);

    ArgumentCaptor<ScriptingEnvironment> captor = ArgumentCaptor.forClass(ScriptingEnvironment.class);

    // Act
    configuration.postInit(processEngineConfiguration);
    verify(processEngineConfiguration).setScriptingEnvironment(captor.capture());

    ScriptingEnvironment customScriptingEnvironment = captor.getValue();

    // Assert
    assertThatThrownBy(() -> customScriptingEnvironment.execute(script, scope, bindings, scriptEngine))
        .isInstanceOf(BpmnError.class)
        .satisfies(thrown -> {
          BpmnError bpmnError = (BpmnError) thrown;
          assertThat(bpmnError.getErrorCode()).isEqualTo("FAILURE");
          assertThat(bpmnError.getCause()).isEqualTo(expectedException);
        });
  }

  @Test
  void shouldReturnScriptResultWhenScriptExecutionSucceeds() throws Exception {
    // Arrange
    Map<Object, Object> beans = new HashMap<>();
    when(processEngineConfiguration.getBeans()).thenReturn(beans);
    when(bdkGateway.session()).thenReturn(sessionService);
    ScriptingEnvironment scriptingEnvironment = mock(ScriptingEnvironment.class);
    when(processEngineConfiguration.getScriptingEnvironment()).thenReturn(scriptingEnvironment);
    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    Object expectedResult = "Success";
    when(scriptingEnvironment.execute(script, scope)).thenReturn(expectedResult);

    ArgumentCaptor<ScriptingEnvironment> captor = ArgumentCaptor.forClass(ScriptingEnvironment.class);

    // Act
    configuration.postInit(processEngineConfiguration);
    verify(processEngineConfiguration).setScriptingEnvironment(captor.capture());

    ScriptingEnvironment customScriptingEnvironment = captor.getValue();
    Object result = customScriptingEnvironment.execute(script, scope);

    // Assert
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void shouldReturnScriptResultWhenScriptExecutionWithBindingsSucceeds() throws Exception {
    // Arrange
    Map<Object, Object> beans = new HashMap<>();
    when(processEngineConfiguration.getBeans()).thenReturn(beans);
    when(bdkGateway.session()).thenReturn(sessionService);
    ScriptingEnvironment scriptingEnvironment = mock(ScriptingEnvironment.class);
    when(processEngineConfiguration.getScriptingEnvironment()).thenReturn(scriptingEnvironment);
    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    Bindings bindings = mock(Bindings.class);
    ScriptEngine scriptEngine = mock(ScriptEngine.class);
    Object expectedResult = "Success with bindings";
    when(scriptingEnvironment.execute(script, scope, bindings, scriptEngine)).thenReturn(expectedResult);

    ArgumentCaptor<ScriptingEnvironment> captor = ArgumentCaptor.forClass(ScriptingEnvironment.class);

    // Act
    configuration.postInit(processEngineConfiguration);
    verify(processEngineConfiguration).setScriptingEnvironment(captor.capture());

    ScriptingEnvironment customScriptingEnvironment = captor.getValue();
    Object result = customScriptingEnvironment.execute(script, scope, bindings, scriptEngine);

    // Assert
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void shouldNotThrowExceptionWhenPostProcessEngineBuildIsCalled() {
    // Arrange & Act & Assert
    configuration.postProcessEngineBuild(processEngine);
  }

  @Test
  void shouldCreateHealthIndicatorWhenMethodIsCalled() {
    // Arrange & Act
    HealthIndicator result = configuration.processEngineHealthIndicator(processEngine);

    // Assert
    assertThat(result).isNotNull();
  }

  @Test
  void shouldReturnUpStatusWhenHealthCheckIsPerformed() throws Exception {
    // Arrange
    String engineName = "test-engine";
    when(processEngine.getName()).thenReturn(engineName);

    // Act
    HealthIndicator healthIndicator = configuration.processEngineHealthIndicator(processEngine);
    Health health = healthIndicator.health();

    // Assert
    assertThat(health.getStatus()).isEqualTo(org.springframework.boot.actuate.health.Status.UP);
    assertThat(health.getDetails()).containsEntry("name", engineName);
  }
}
