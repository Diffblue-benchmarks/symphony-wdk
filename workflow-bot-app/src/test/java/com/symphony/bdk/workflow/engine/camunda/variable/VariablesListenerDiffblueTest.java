package com.symphony.bdk.workflow.engine.camunda.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaExecutionListener;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaField;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class VariablesListenerDiffblueTest {

  /**
   * Test {@link VariablesListener#create(BpmnModelInstance, Map)}.
   *
   * <p>Method under test: {@link VariablesListener#create(BpmnModelInstance, Map)}
   */
  @Test
  @DisplayName("Test create(BpmnModelInstance, Map)")
  void testCreate() throws JsonProcessingException {
    // Arrange
    BpmnModelInstance instance =
        Bpmn.createExecutableProcess("test").startEvent().endEvent().done();
    Map<String, Object> variables = new HashMap<>();
    variables.put("key1", "value1");
    variables.put("key2", 42);

    // Act
    CamundaExecutionListener listener = VariablesListener.create(instance, variables);

    // Assert
    assertNotNull(listener);
    assertEquals("start", listener.getCamundaEvent());
    assertEquals(VariablesListener.class.getName(), listener.getCamundaClass());
    Collection<CamundaField> fields = listener.getCamundaFields();
    assertNotNull(fields);
    assertEquals(1, fields.size());
    CamundaField field = fields.iterator().next();
    assertEquals("variables", field.getCamundaName());
    assertNotNull(field.getCamundaStringValue());
  }

  /**
   * Test {@link VariablesListener#create(BpmnModelInstance, Map)} with empty variables map.
   *
   * <p>Method under test: {@link VariablesListener#create(BpmnModelInstance, Map)}
   */
  @Test
  @DisplayName("Test create(BpmnModelInstance, Map); when empty variables map")
  void testCreate_withEmptyVariables() throws JsonProcessingException {
    // Arrange
    BpmnModelInstance instance =
        Bpmn.createExecutableProcess("test2").startEvent().endEvent().done();
    Map<String, Object> variables = new HashMap<>();

    // Act
    CamundaExecutionListener listener = VariablesListener.create(instance, variables);

    // Assert
    assertNotNull(listener);
    assertEquals("start", listener.getCamundaEvent());
    assertEquals(VariablesListener.class.getName(), listener.getCamundaClass());
    Collection<CamundaField> fields = listener.getCamundaFields();
    assertEquals(1, fields.size());
    assertEquals("{}", fields.iterator().next().getCamundaStringValue());
  }

  /**
   * Test {@link VariablesListener#notify(DelegateExecution)} when variables field is null.
   *
   * <p>Method under test: {@link VariablesListener#notify(DelegateExecution)}
   */
  @Test
  @DisplayName("Test notify(DelegateExecution); when variables is null")
  void testNotify_variablesNull() throws Exception {
    // Arrange
    VariablesListener listener = new VariablesListener();
    DelegateExecution execution = mock(DelegateExecution.class);

    // Act
    listener.notify(execution);

    // Assert - setVariable is never called since variables field is null
    verify(execution, never()).setVariable(eq("variables"), eq(null));
  }

  /**
   * Test {@link VariablesListener#notify(DelegateExecution)} when variables getValue returns null.
   *
   * <p>Method under test: {@link VariablesListener#notify(DelegateExecution)}
   */
  @Test
  @DisplayName("Test notify(DelegateExecution); when variables getValue returns null")
  void testNotify_variablesGetValueNull() throws Exception {
    // Arrange
    VariablesListener listener = new VariablesListener();
    DelegateExecution execution = mock(DelegateExecution.class);
    Expression expressionMock = mock(Expression.class);
    when(expressionMock.getValue(execution)).thenReturn(null);
    setVariablesField(listener, expressionMock);

    // Act
    listener.notify(execution);

    // Assert - setVariable is never called when getValue returns null
    verify(execution, never()).setVariable(eq("variables"), eq(null));
    verify(expressionMock).getValue(execution);
  }

  /**
   * Test {@link VariablesListener#notify(DelegateExecution)} when variables getValue returns valid JSON.
   *
   * <p>Method under test: {@link VariablesListener#notify(DelegateExecution)}
   */
  @Test
  @DisplayName("Test notify(DelegateExecution); when variables getValue returns valid JSON")
  void testNotify_withValidVariables() throws Exception {
    // Arrange
    VariablesListener listener = new VariablesListener();
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getId()).thenReturn("execId");
    Expression expressionMock = mock(Expression.class);
    when(expressionMock.getValue(execution)).thenReturn("{\"key1\":\"value1\",\"key2\":42}");
    setVariablesField(listener, expressionMock);

    // Act
    listener.notify(execution);

    // Assert
    verify(execution).setVariable(eq("variables"), eq(Map.of("key1", "value1", "key2", 42)));
  }

  /**
   * Test {@link VariablesListener#notify(DelegateExecution)} when variables getValue returns
   * invalid JSON - should throw JsonProcessingException.
   *
   * <p>Method under test: {@link VariablesListener#notify(DelegateExecution)}
   */
  @Test
  @DisplayName("Test notify(DelegateExecution); when variables getValue returns invalid JSON")
  void testNotify_withInvalidJson() throws Exception {
    // Arrange
    VariablesListener listener = new VariablesListener();
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getId()).thenReturn("execId");
    Expression expressionMock = mock(Expression.class);
    when(expressionMock.getValue(execution)).thenReturn("not-valid-json");
    setVariablesField(listener, expressionMock);

    // Act and Assert
    assertThrows(Exception.class, () -> listener.notify(execution));
  }

  private void setVariablesField(VariablesListener listener, Expression expression)
      throws NoSuchFieldException, IllegalAccessException {
    Field field = VariablesListener.class.getDeclaredField("variables");
    field.setAccessible(true);
    field.set(listener, expression);
  }
}
