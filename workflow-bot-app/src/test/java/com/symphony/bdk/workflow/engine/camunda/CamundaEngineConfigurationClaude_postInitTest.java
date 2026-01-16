package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.engine.executor.BdkGateway;
import com.symphony.bdk.workflow.engine.executor.SecretKeeper;
import com.symphony.bdk.workflow.engine.executor.SharedDataStore;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.scripting.ExecutableScript;
import org.camunda.bpm.engine.impl.scripting.env.ScriptingEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CamundaEngineConfigurationClaude_postInitTest {

  private CamundaEngineConfiguration configuration;
  private BdkGateway bdkGateway;
  private SharedDataStore sharedDataStore;
  private SecretKeeper secretKeeper;
  private ProcessEngineConfigurationImpl processEngineConfiguration;
  private SessionService sessionService;
  private Map<Object, Object> beans;
  private ScriptingEnvironment scriptingEnvironment;

  @BeforeEach
  void setUp() {
    bdkGateway = mock(BdkGateway.class);
    sharedDataStore = mock(SharedDataStore.class);
    secretKeeper = mock(SecretKeeper.class);
    sessionService = mock(SessionService.class);
    processEngineConfiguration = mock(ProcessEngineConfigurationImpl.class);
    scriptingEnvironment = mock(ScriptingEnvironment.class);
    beans = new HashMap<>();

    when(bdkGateway.session()).thenReturn(sessionService);
    when(processEngineConfiguration.getBeans()).thenReturn(beans);
    when(processEngineConfiguration.getScriptingEnvironment()).thenReturn(scriptingEnvironment);

    configuration = new CamundaEngineConfiguration(bdkGateway, sharedDataStore, secretKeeper);
  }

  @Test
  void postInit_shouldAddUtilityFunctionsMapperToBeansWithCorrectKey() {
    // When: postInit is called
    configuration.postInit(processEngineConfiguration);

    // Then: UtilityFunctionsMapper should be added to beans with WDK_PREFIX key
    assertThat(beans).containsKey(UtilityFunctionsMapper.WDK_PREFIX);
    assertThat(beans.get(UtilityFunctionsMapper.WDK_PREFIX)).isInstanceOf(UtilityFunctionsMapper.class);
  }

  @Test
  void postInit_shouldCreateUtilityFunctionsMapperWithSessionService() {
    // When: postInit is called
    configuration.postInit(processEngineConfiguration);

    // Then: session() should be called on bdkGateway
    verify(bdkGateway).session();
  }

  @Test
  void postInit_shouldRetrieveBeansFromConfiguration() {
    // When: postInit is called
    configuration.postInit(processEngineConfiguration);

    // Then: getBeans should be called on configuration
    verify(processEngineConfiguration).getBeans();
  }

  @Test
  void postInit_shouldRetrieveScriptingEnvironmentFromConfiguration() {
    // When: postInit is called
    configuration.postInit(processEngineConfiguration);

    // Then: getScriptingEnvironment should be called on configuration
    verify(processEngineConfiguration).getScriptingEnvironment();
  }

  @Test
  void postInit_shouldSetNewScriptingEnvironmentOnConfiguration() {
    // When: postInit is called
    configuration.postInit(processEngineConfiguration);

    // Then: setScriptingEnvironment should be called with a new ScriptingEnvironment
    verify(processEngineConfiguration).setScriptingEnvironment(any(ScriptingEnvironment.class));
  }

  @Test
  void postInit_calledMultipleTimes_shouldAddUtilityFunctionsMapperEachTime() {
    // When: postInit is called twice
    configuration.postInit(processEngineConfiguration);
    Object firstMapper = beans.get(UtilityFunctionsMapper.WDK_PREFIX);

    configuration.postInit(processEngineConfiguration);
    Object secondMapper = beans.get(UtilityFunctionsMapper.WDK_PREFIX);

    // Then: A new UtilityFunctionsMapper should be created each time
    assertThat(firstMapper).isNotNull();
    assertThat(secondMapper).isNotNull();
    assertThat(firstMapper).isNotSameAs(secondMapper);
  }

  @Test
  void postInit_withDifferentConfiguration_shouldAddMapperToThatConfiguration() {
    // Given: A different process engine configuration
    ProcessEngineConfigurationImpl anotherConfig = mock(ProcessEngineConfigurationImpl.class);
    Map<Object, Object> anotherBeans = new HashMap<>();
    ScriptingEnvironment anotherScriptingEnv = mock(ScriptingEnvironment.class);
    when(anotherConfig.getBeans()).thenReturn(anotherBeans);
    when(anotherConfig.getScriptingEnvironment()).thenReturn(anotherScriptingEnv);

    // When: postInit is called on the different configuration
    configuration.postInit(anotherConfig);

    // Then: UtilityFunctionsMapper should be added to the other configuration's beans
    assertThat(anotherBeans).containsKey(UtilityFunctionsMapper.WDK_PREFIX);
    assertThat(beans).isEmpty();
  }

  @Test
  void postInit_scriptingEnvironmentExecute_whenNoException_shouldReturnResult() {
    // Given: postInit has been called to set up the new scripting environment
    configuration.postInit(processEngineConfiguration);

    // Capture the ScriptingEnvironment that was set
    org.mockito.ArgumentCaptor<ScriptingEnvironment> captor =
        org.mockito.ArgumentCaptor.forClass(ScriptingEnvironment.class);
    verify(processEngineConfiguration).setScriptingEnvironment(captor.capture());
    ScriptingEnvironment capturedEnv = captor.getValue();

    // Set up the original scripting environment to return a result
    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    String expectedResult = "success";
    when(scriptingEnvironment.execute(script, scope)).thenReturn(expectedResult);

    // When: execute is called on the new scripting environment
    Object result = capturedEnv.execute(script, scope);

    // Then: The result should be returned
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void postInit_scriptingEnvironmentExecute_whenExceptionThrown_shouldWrapInBpmnError() {
    // Given: postInit has been called to set up the new scripting environment
    configuration.postInit(processEngineConfiguration);

    // Capture the ScriptingEnvironment that was set
    org.mockito.ArgumentCaptor<ScriptingEnvironment> captor =
        org.mockito.ArgumentCaptor.forClass(ScriptingEnvironment.class);
    verify(processEngineConfiguration).setScriptingEnvironment(captor.capture());
    ScriptingEnvironment capturedEnv = captor.getValue();

    // Set up the original scripting environment to throw an exception
    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    RuntimeException originalException = new RuntimeException("Script execution failed");
    when(scriptingEnvironment.execute(script, scope)).thenThrow(originalException);

    // When/Then: execute should throw BpmnError wrapping the original exception
    assertThatThrownBy(() -> capturedEnv.execute(script, scope))
        .isInstanceOf(BpmnError.class)
        .hasFieldOrPropertyWithValue("errorCode", "FAILURE")
        .hasCause(originalException);
  }

  @Test
  void postInit_scriptingEnvironmentExecuteWithBindings_whenNoException_shouldReturnResult() {
    // Given: postInit has been called to set up the new scripting environment
    configuration.postInit(processEngineConfiguration);

    // Capture the ScriptingEnvironment that was set
    org.mockito.ArgumentCaptor<ScriptingEnvironment> captor =
        org.mockito.ArgumentCaptor.forClass(ScriptingEnvironment.class);
    verify(processEngineConfiguration).setScriptingEnvironment(captor.capture());
    ScriptingEnvironment capturedEnv = captor.getValue();

    // Set up the original scripting environment to return a result
    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    Bindings bindings = mock(Bindings.class);
    ScriptEngine scriptEngine = mock(ScriptEngine.class);
    String expectedResult = "success with bindings";
    when(scriptingEnvironment.execute(script, scope, bindings, scriptEngine)).thenReturn(expectedResult);

    // When: execute with bindings is called on the new scripting environment
    Object result = capturedEnv.execute(script, scope, bindings, scriptEngine);

    // Then: The result should be returned
    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void postInit_scriptingEnvironmentExecuteWithBindings_whenExceptionThrown_shouldWrapInBpmnError() {
    // Given: postInit has been called to set up the new scripting environment
    configuration.postInit(processEngineConfiguration);

    // Capture the ScriptingEnvironment that was set
    org.mockito.ArgumentCaptor<ScriptingEnvironment> captor =
        org.mockito.ArgumentCaptor.forClass(ScriptingEnvironment.class);
    verify(processEngineConfiguration).setScriptingEnvironment(captor.capture());
    ScriptingEnvironment capturedEnv = captor.getValue();

    // Set up the original scripting environment to throw an exception
    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    Bindings bindings = mock(Bindings.class);
    ScriptEngine scriptEngine = mock(ScriptEngine.class);
    RuntimeException originalException = new RuntimeException("Script execution with bindings failed");
    when(scriptingEnvironment.execute(script, scope, bindings, scriptEngine)).thenThrow(originalException);

    // When/Then: execute should throw BpmnError wrapping the original exception
    assertThatThrownBy(() -> capturedEnv.execute(script, scope, bindings, scriptEngine))
        .isInstanceOf(BpmnError.class)
        .hasFieldOrPropertyWithValue("errorCode", "FAILURE")
        .hasCause(originalException);
  }

  @Test
  void postInit_scriptingEnvironmentExecute_whenBpmnErrorThrown_shouldWrapInNewBpmnError() {
    // Given: postInit has been called to set up the new scripting environment
    configuration.postInit(processEngineConfiguration);

    // Capture the ScriptingEnvironment that was set
    org.mockito.ArgumentCaptor<ScriptingEnvironment> captor =
        org.mockito.ArgumentCaptor.forClass(ScriptingEnvironment.class);
    verify(processEngineConfiguration).setScriptingEnvironment(captor.capture());
    ScriptingEnvironment capturedEnv = captor.getValue();

    // Set up the original scripting environment to throw a BpmnError
    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    BpmnError originalBpmnError = new BpmnError("CUSTOM_ERROR", "Original BPMN error");
    when(scriptingEnvironment.execute(script, scope)).thenThrow(originalBpmnError);

    // When/Then: execute should throw a new BpmnError with "FAILURE" code wrapping the original
    assertThatThrownBy(() -> capturedEnv.execute(script, scope))
        .isInstanceOf(BpmnError.class)
        .hasFieldOrPropertyWithValue("errorCode", "FAILURE")
        .hasCause(originalBpmnError);
  }

  @Test
  void postInit_shouldDelegateToOriginalScriptingEnvironment() {
    // Given: postInit has been called
    configuration.postInit(processEngineConfiguration);

    // Capture the ScriptingEnvironment that was set
    org.mockito.ArgumentCaptor<ScriptingEnvironment> captor =
        org.mockito.ArgumentCaptor.forClass(ScriptingEnvironment.class);
    verify(processEngineConfiguration).setScriptingEnvironment(captor.capture());
    ScriptingEnvironment capturedEnv = captor.getValue();

    // Set up script execution
    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    when(scriptingEnvironment.execute(script, scope)).thenReturn("result");

    // When: execute is called
    capturedEnv.execute(script, scope);

    // Then: The original scripting environment should be called
    verify(scriptingEnvironment).execute(script, scope);
  }

  @Test
  void postInit_shouldDelegateToOriginalScriptingEnvironmentWithBindings() {
    // Given: postInit has been called
    configuration.postInit(processEngineConfiguration);

    // Capture the ScriptingEnvironment that was set
    org.mockito.ArgumentCaptor<ScriptingEnvironment> captor =
        org.mockito.ArgumentCaptor.forClass(ScriptingEnvironment.class);
    verify(processEngineConfiguration).setScriptingEnvironment(captor.capture());
    ScriptingEnvironment capturedEnv = captor.getValue();

    // Set up script execution with bindings
    ExecutableScript script = mock(ExecutableScript.class);
    VariableScope scope = mock(VariableScope.class);
    Bindings bindings = mock(Bindings.class);
    ScriptEngine scriptEngine = mock(ScriptEngine.class);
    when(scriptingEnvironment.execute(script, scope, bindings, scriptEngine)).thenReturn("result");

    // When: execute with bindings is called
    capturedEnv.execute(script, scope, bindings, scriptEngine);

    // Then: The original scripting environment should be called with bindings
    verify(scriptingEnvironment).execute(script, scope, bindings, scriptEngine);
  }

  @Test
  void postInit_withExistingBeansMap_shouldAddMapperToExistingMap() {
    // Given: beans map already contains some entries
    beans.put("existingKey", "existingValue");

    // When: postInit is called
    configuration.postInit(processEngineConfiguration);

    // Then: Both the existing entry and new mapper should be in the beans map
    assertThat(beans).containsKey("existingKey");
    assertThat(beans).containsKey(UtilityFunctionsMapper.WDK_PREFIX);
    assertThat(beans).hasSize(2);
  }

  @Test
  void postInit_shouldCreateNewUtilityFunctionsMapperInstance() {
    // When: postInit is called
    configuration.postInit(processEngineConfiguration);

    // Then: A new UtilityFunctionsMapper instance should be created
    Object mapper = beans.get(UtilityFunctionsMapper.WDK_PREFIX);
    assertThat(mapper).isNotNull();
    assertThat(mapper.getClass()).isEqualTo(UtilityFunctionsMapper.class);
  }
}
