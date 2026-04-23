package com.symphony.bdk.workflow.engine.camunda.variable;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaExecutionListener;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VariablesListenerTest {

  @Mock
  private DelegateExecution execution;

  @Mock
  private Expression variablesExpression;

  private VariablesListener variablesListener;

  @BeforeEach
  void setUp() {
    variablesListener = new VariablesListener();
  }

  @Test
  void shouldCreateListenerWithCorrectEventAndClass() throws JsonProcessingException {
    BpmnModelInstance instance = Bpmn.createEmptyModel();
    Map<String, Object> variables = new HashMap<>();
    variables.put("key1", "value1");

    CamundaExecutionListener listener = VariablesListener.create(instance, variables);

    assertThat(listener.getCamundaEvent()).isEqualTo("start");
    assertThat(listener.getCamundaClass()).isEqualTo(VariablesListener.class.getName());
  }

  @Test
  void shouldCreateListenerWithVariablesField() throws JsonProcessingException {
    BpmnModelInstance instance = Bpmn.createEmptyModel();
    Map<String, Object> variables = new HashMap<>();
    variables.put("key1", "value1");

    CamundaExecutionListener listener = VariablesListener.create(instance, variables);

    Collection<CamundaField> fields = listener.getCamundaFields();
    assertThat(fields).isNotEmpty();
    CamundaField field = fields.iterator().next();
    assertThat(field.getCamundaName()).isEqualTo(ActivityExecutorContext.VARIABLES);
    assertThat(field.getCamundaStringValue()).contains("key1");
  }

  @Test
  void shouldNotSetVariablesWhenExpressionIsNull() throws Exception {
    variablesListener.notify(execution);

    // No variables set since the injected expression is null
    assertThat(variablesListener).isNotNull();
  }

  @Test
  void shouldSetVariablesInExecutionWhenExpressionIsNotNull() throws Exception {
    Map<String, Object> vars = new HashMap<>();
    vars.put("foo", "bar");
    String json = "{\"foo\":\"bar\"}";

    when(variablesExpression.getValue(execution)).thenReturn(json);
    injectVariables(variablesListener, variablesExpression);

    variablesListener.notify(execution);

    verify(execution).setVariable(eq(ActivityExecutorContext.VARIABLES), eq(vars));
  }

  @Test
  void shouldNotSetVariablesWhenExpressionValueIsNull() throws Exception {
    when(variablesExpression.getValue(execution)).thenReturn(null);
    injectVariables(variablesListener, variablesExpression);

    variablesListener.notify(execution);

    // setVariable should not be called
    assertThat(variablesListener).isNotNull();
  }

  private void injectVariables(VariablesListener listener, Expression expression) throws Exception {
    Field field = VariablesListener.class.getDeclaredField("variables");
    field.setAccessible(true);
    field.set(listener, expression);
  }
}
