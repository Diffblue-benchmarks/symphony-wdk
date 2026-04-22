package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.el.JuelExpressionManager;
import org.camunda.bpm.engine.impl.scripting.ExecutableScript;
import org.camunda.bpm.engine.impl.scripting.env.ScriptingEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptEngine;

@ExtendWith(MockitoExtension.class)
class CamundaEngineConfigurationTest {

  @Mock private BdkGateway bdkGateway;
  @Mock private SharedDataStore sharedDataStore;
  @Mock private SecretKeeper secretKeeper;
  @Mock private SessionService sessionService;

  private CamundaEngineConfiguration camundaEngineConfiguration;

  @BeforeEach
  void setUp() {
    lenient().when(bdkGateway.session()).thenReturn(sessionService);
    camundaEngineConfiguration =
        new CamundaEngineConfiguration(bdkGateway, sharedDataStore, secretKeeper);
  }

  @Test
  @DisplayName("preInit registers utility functions into expression manager")
  void testPreInit_registersUtilityFunctions() {
    // Arrange
    JuelExpressionManager juelExpressionManager = mock(JuelExpressionManager.class);
    ProcessEngineConfigurationImpl config = mock(ProcessEngineConfigurationImpl.class);
    when(config.getExpressionManager()).thenReturn(juelExpressionManager);

    // Act
    camundaEngineConfiguration.preInit(config);

    // Assert
    verify(config).getExpressionManager();
    verify(juelExpressionManager, atLeast(1)).addFunction(anyString(), any(Method.class));
  }

  @Test
  @DisplayName("postInit puts UtilityFunctionsMapper into engine beans")
  void testPostInit_putsUtilityFunctionsMapperInBeans() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    ProcessEngineConfigurationImpl config = mock(ProcessEngineConfigurationImpl.class);
    when(config.getBeans()).thenReturn(beans);
    ScriptingEnvironment scriptingEnvironment = mock(ScriptingEnvironment.class);
    when(config.getScriptingEnvironment()).thenReturn(scriptingEnvironment);

    // Act
    camundaEngineConfiguration.postInit(config);

    // Assert
    assertEquals(1, beans.size());
    Object wdk = beans.get(UtilityFunctionsMapper.WDK_PREFIX);
    assert wdk instanceof UtilityFunctionsMapper;
  }

  @Test
  @DisplayName("postInit wraps ScriptingEnvironment to handle exceptions as BpmnErrors")
  void testPostInit_wrapsScriptingEnvironment() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    ProcessEngineConfigurationImpl config = mock(ProcessEngineConfigurationImpl.class);
    when(config.getBeans()).thenReturn(beans);
    ScriptingEnvironment scriptingEnvironment = mock(ScriptingEnvironment.class);
    when(config.getScriptingEnvironment()).thenReturn(scriptingEnvironment);
    ArgumentCaptor<ScriptingEnvironment> captor = ArgumentCaptor.forClass(ScriptingEnvironment.class);
    doNothing().when(config).setScriptingEnvironment(captor.capture());

    // Act
    camundaEngineConfiguration.postInit(config);

    // Assert
    ScriptingEnvironment wrapped = captor.getValue();
    assert wrapped != null;
    assert wrapped != scriptingEnvironment;
  }

  @Test
  @DisplayName("handleScriptExceptionsAsBpmnErrors - execute delegates to original on success")
  void testHandleScriptExceptionsAsBpmnErrors_execute_delegatesToOriginal() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    ProcessEngineConfigurationImpl config = mock(ProcessEngineConfigurationImpl.class);
    when(config.getBeans()).thenReturn(beans);
    ScriptingEnvironment originalEnv = mock(ScriptingEnvironment.class);
    when(config.getScriptingEnvironment()).thenReturn(originalEnv);
    ArgumentCaptor<ScriptingEnvironment> captor = ArgumentCaptor.forClass(ScriptingEnvironment.class);
    doNothing().when(config).setScriptingEnvironment(captor.capture());

    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    when(originalEnv.execute(script, scope)).thenReturn("result");

    camundaEngineConfiguration.postInit(config);
    ScriptingEnvironment wrapped = captor.getValue();

    // Act
    Object result = wrapped.execute(script, scope);

    // Assert
    assertEquals("result", result);
    verify(originalEnv).execute(script, scope);
  }

  @Test
  @DisplayName("handleScriptExceptionsAsBpmnErrors - execute wraps exception in BpmnError")
  void testHandleScriptExceptionsAsBpmnErrors_execute_wrapsBpmnError() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    ProcessEngineConfigurationImpl config = mock(ProcessEngineConfigurationImpl.class);
    when(config.getBeans()).thenReturn(beans);
    ScriptingEnvironment originalEnv = mock(ScriptingEnvironment.class);
    when(config.getScriptingEnvironment()).thenReturn(originalEnv);
    ArgumentCaptor<ScriptingEnvironment> captor = ArgumentCaptor.forClass(ScriptingEnvironment.class);
    doNothing().when(config).setScriptingEnvironment(captor.capture());

    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    when(originalEnv.execute(script, scope)).thenThrow(new RuntimeException("script error"));

    camundaEngineConfiguration.postInit(config);
    ScriptingEnvironment wrapped = captor.getValue();

    // Act & Assert
    assertThrows(BpmnError.class, () -> wrapped.execute(script, scope));
  }

  @Test
  @DisplayName("handleScriptExceptionsAsBpmnErrors - execute with bindings delegates to original")
  void testHandleScriptExceptionsAsBpmnErrors_executeWithBindings_delegatesToOriginal() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    ProcessEngineConfigurationImpl config = mock(ProcessEngineConfigurationImpl.class);
    when(config.getBeans()).thenReturn(beans);
    ScriptingEnvironment originalEnv = mock(ScriptingEnvironment.class);
    when(config.getScriptingEnvironment()).thenReturn(originalEnv);
    ArgumentCaptor<ScriptingEnvironment> captor = ArgumentCaptor.forClass(ScriptingEnvironment.class);
    doNothing().when(config).setScriptingEnvironment(captor.capture());

    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    Bindings bindings = mock(Bindings.class);
    ScriptEngine scriptEngine = mock(ScriptEngine.class);
    when(originalEnv.execute(script, scope, bindings, scriptEngine)).thenReturn("result2");

    camundaEngineConfiguration.postInit(config);
    ScriptingEnvironment wrapped = captor.getValue();

    // Act
    Object result = wrapped.execute(script, scope, bindings, scriptEngine);

    // Assert
    assertEquals("result2", result);
    verify(originalEnv).execute(script, scope, bindings, scriptEngine);
  }

  @Test
  @DisplayName("handleScriptExceptionsAsBpmnErrors - execute with bindings wraps exception in BpmnError")
  void testHandleScriptExceptionsAsBpmnErrors_executeWithBindings_wrapsBpmnError() {
    // Arrange
    HashMap<Object, Object> beans = new HashMap<>();
    ProcessEngineConfigurationImpl config = mock(ProcessEngineConfigurationImpl.class);
    when(config.getBeans()).thenReturn(beans);
    ScriptingEnvironment originalEnv = mock(ScriptingEnvironment.class);
    when(config.getScriptingEnvironment()).thenReturn(originalEnv);
    ArgumentCaptor<ScriptingEnvironment> captor = ArgumentCaptor.forClass(ScriptingEnvironment.class);
    doNothing().when(config).setScriptingEnvironment(captor.capture());

    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    Bindings bindings = mock(Bindings.class);
    ScriptEngine scriptEngine = mock(ScriptEngine.class);
    when(originalEnv.execute(script, scope, bindings, scriptEngine))
        .thenThrow(new RuntimeException("script error"));

    camundaEngineConfiguration.postInit(config);
    ScriptingEnvironment wrapped = captor.getValue();

    // Act & Assert
    assertThrows(BpmnError.class, () -> wrapped.execute(script, scope, bindings, scriptEngine));
  }

  @Test
  @DisplayName("postProcessEngineBuild completes without error")
  void testPostProcessEngineBuild_noError() {
    // Arrange
    ProcessEngine processEngine = mock(ProcessEngine.class);

    // Act & Assert (empty method, just verify no exception)
    camundaEngineConfiguration.postProcessEngineBuild(processEngine);
  }

  @Test
  @DisplayName("processEngineHealthIndicator returns UP health with engine name")
  void testProcessEngineHealthIndicator_returnsUpHealth() {
    // Arrange
    ProcessEngine processEngine = mock(ProcessEngine.class);
    when(processEngine.getName()).thenReturn("test-engine");

    // Act
    HealthIndicator indicator = camundaEngineConfiguration.processEngineHealthIndicator(processEngine);
    Health health = indicator.health();

    // Assert
    assertEquals(Status.UP, health.getStatus());
    Map<String, Object> details = health.getDetails();
    assertEquals("test-engine", details.get("name"));
  }

  @Test
  @DisplayName("processEngineHealthIndicator returns DOWN when engine throws")
  void testProcessEngineHealthIndicator_returnsDownOnError() {
    // Arrange
    ProcessEngine processEngine = mock(ProcessEngine.class);
    when(processEngine.getName()).thenThrow(new RuntimeException("engine failure"));

    // Act
    HealthIndicator indicator = camundaEngineConfiguration.processEngineHealthIndicator(processEngine);
    Health health = indicator.health();

    // Assert
    assertEquals(Status.DOWN, health.getStatus());
  }
}
