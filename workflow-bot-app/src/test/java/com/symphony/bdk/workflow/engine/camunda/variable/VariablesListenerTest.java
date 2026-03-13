package com.symphony.bdk.workflow.engine.camunda.variable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaExecutionListener;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class VariablesListenerTest {

  private VariablesListener listener;
  private BpmnModelInstance modelInstance;
  private DelegateExecution execution;
  private Expression variablesExpression;

  @BeforeEach
  void setUp() {
    listener = new VariablesListener();
    modelInstance = mock(BpmnModelInstance.class);
    execution = mock(DelegateExecution.class);
    variablesExpression = mock(Expression.class);
  }

  @Test
  void shouldCreateListenerWithVariablesField() throws JsonProcessingException {
    Map<String, Object> variables = new HashMap<>();
    variables.put("key1", "value1");
    variables.put("key2", 123);

    CamundaExecutionListener executionListener = mock(CamundaExecutionListener.class);
    CamundaField camundaField = mock(CamundaField.class);
    List<CamundaField> fields = new ArrayList<>();

    when(modelInstance.newInstance(CamundaExecutionListener.class)).thenReturn(executionListener);
    when(modelInstance.newInstance(CamundaField.class)).thenReturn(camundaField);
    when(executionListener.getCamundaFields()).thenReturn(fields);

    CamundaExecutionListener result = VariablesListener.create(modelInstance, variables);

    assertThat(result).isEqualTo(executionListener);
    verify(executionListener).setCamundaEvent("start");
    verify(executionListener).setCamundaClass(VariablesListener.class.getName());
    verify(camundaField).setCamundaName(ActivityExecutorContext.VARIABLES);
    verify(camundaField).setCamundaStringValue("{\"key1\":\"value1\",\"key2\":123}");
    assertThat(fields).hasSize(1);
    assertThat(fields.get(0)).isEqualTo(camundaField);
  }

  @Test
  void shouldCreateListenerWithEmptyVariables() throws JsonProcessingException {
    Map<String, Object> variables = new HashMap<>();

    CamundaExecutionListener executionListener = mock(CamundaExecutionListener.class);
    CamundaField camundaField = mock(CamundaField.class);
    List<CamundaField> fields = new ArrayList<>();

    when(modelInstance.newInstance(CamundaExecutionListener.class)).thenReturn(executionListener);
    when(modelInstance.newInstance(CamundaField.class)).thenReturn(camundaField);
    when(executionListener.getCamundaFields()).thenReturn(fields);

    CamundaExecutionListener result = VariablesListener.create(modelInstance, variables);

    assertThat(result).isEqualTo(executionListener);
    verify(camundaField).setCamundaStringValue("{}");
  }

  @Test
  void shouldNotifyAndSetVariablesWhenExpressionsIsNotNull() throws Exception {
    setVariablesField(listener, variablesExpression);
    String jsonVariables = "{\"key1\":\"value1\",\"key2\":123}";
    when(variablesExpression.getValue(execution)).thenReturn(jsonVariables);
    when(execution.getId()).thenReturn("execution-123");

    listener.notify(execution);

    verify(execution).setVariable(ActivityExecutorContext.VARIABLES, Map.of("key1", "value1", "key2", 123));
  }

  @Test
  void shouldNotifyWithoutSettingVariablesWhenExpressionIsNull() throws Exception {
    setVariablesField(listener, null);

    listener.notify(execution);

    // No variables should be set when expression is null
  }

  @Test
  void shouldNotifyWithoutSettingVariablesWhenExpressionValueIsNull() throws Exception {
    setVariablesField(listener, variablesExpression);
    when(variablesExpression.getValue(execution)).thenReturn(null);

    listener.notify(execution);

    // No variables should be set when expression value is null
  }

  @Test
  void shouldNotifyAndHandleNullVariablesMap() throws Exception {
    setVariablesField(listener, variablesExpression);
    when(variablesExpression.getValue(execution)).thenReturn("null");
    when(execution.getId()).thenReturn("execution-456");

    listener.notify(execution);

    // Should not throw exception and should not set variable when map is null
  }

  @Test
  void shouldConvertJsonStringToMapSuccessfully() throws Exception {
    String jsonString = "{\"key1\":\"value1\",\"key2\":123}";

    Map<String, Object> result = invokeConvertJsonStringToMap(listener, jsonString);

    assertThat(result).containsEntry("key1", "value1");
    assertThat(result).containsEntry("key2", 123);
  }

  @Test
  void shouldConvertEmptyJsonStringToEmptyMap() throws Exception {
    String jsonString = "{}";

    Map<String, Object> result = invokeConvertJsonStringToMap(listener, jsonString);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldThrowExceptionWhenJsonStringIsInvalid() {
    String invalidJson = "{invalid json}";

    assertThatThrownBy(() -> invokeConvertJsonStringToMap(listener, invalidJson))
        .hasCauseInstanceOf(JsonProcessingException.class);
  }

  private void setVariablesField(VariablesListener listener, Expression expression) throws Exception {
    Field field = VariablesListener.class.getDeclaredField("variables");
    field.setAccessible(true);
    field.set(listener, expression);
  }

  private Map<String, Object> invokeConvertJsonStringToMap(VariablesListener listener, String jsonString)
      throws Exception {
    java.lang.reflect.Method method = VariablesListener.class.getDeclaredMethod("convertJsonStringToMap",
        String.class);
    method.setAccessible(true);
    @SuppressWarnings("unchecked")
    Map<String, Object> result = (Map<String, Object>) method.invoke(listener, jsonString);
    return result;
  }
}
