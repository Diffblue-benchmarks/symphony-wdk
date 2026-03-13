package com.symphony.bdk.workflow.engine.camunda.variable;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FormVariableListenerTest {

  private FormVariableListener listener;
  private DelegateExecution execution;

  @BeforeEach
  void setUp() {
    listener = new FormVariableListener();
    execution = mock(DelegateExecution.class);
  }

  @Test
  void notifyShouldDoNothingWhenFormVariableIsNull() {
    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(null);

    listener.notify(execution);

    verify(execution, never()).setVariable(anyString(), anyString());
    verify(execution, never()).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notifyShouldDoNothingWhenFormVariableIsNotMap() {
    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn("not a map");

    listener.notify(execution);

    verify(execution, never()).setVariable(anyString(), anyString());
    verify(execution, never()).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notifyShouldRemoveFormVariableWhenFormMapIsEmpty() {
    Map<String, Map<String, Object>> emptyForm = new HashMap<>();
    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(emptyForm);

    listener.notify(execution);

    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notifyShouldSetVariableWhenActivityVariableIsNull() {
    Map<String, Object> formData = new HashMap<>();
    formData.put("field1", "value1");
    formData.put("field2", "value2");

    Map<String, Map<String, Object>> form = new HashMap<>();
    form.put("activity1", formData);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form);
    when(execution.getVariable("activity1")).thenReturn(null);

    listener.notify(execution);

    verify(execution).setVariable("activity1", formData);
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notifyShouldMergeVariablesWhenActivityVariableIsMap() {
    Map<String, Object> existingData = new HashMap<>();
    existingData.put("outputs", "existing");

    Map<String, Object> formData = new HashMap<>();
    formData.put("field1", "value1");
    formData.put("field2", "value2");

    Map<String, Map<String, Object>> form = new HashMap<>();
    form.put("activity1", formData);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form);
    when(execution.getVariable("activity1")).thenReturn(existingData);

    listener.notify(execution);

    verify(execution, never()).setVariable(eq("activity1"), eq(formData));
    assertEquals(3, existingData.size());
    assertEquals("existing", existingData.get("outputs"));
    assertEquals("value1", existingData.get("field1"));
    assertEquals("value2", existingData.get("field2"));
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notifyShouldHandleMultipleFormEntries() {
    Map<String, Object> formData1 = new HashMap<>();
    formData1.put("field1", "value1");

    Map<String, Object> formData2 = new HashMap<>();
    formData2.put("field2", "value2");

    Map<String, Map<String, Object>> form = new HashMap<>();
    form.put("activity1", formData1);
    form.put("activity2", formData2);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form);
    when(execution.getVariable("activity1")).thenReturn(null);
    when(execution.getVariable("activity2")).thenReturn(null);

    listener.notify(execution);

    verify(execution).setVariable("activity1", formData1);
    verify(execution).setVariable("activity2", formData2);
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notifyShouldHandleMixedScenarios() {
    Map<String, Object> existingData = new HashMap<>();
    existingData.put("outputs", "existing");

    Map<String, Object> formData1 = new HashMap<>();
    formData1.put("field1", "value1");

    Map<String, Object> formData2 = new HashMap<>();
    formData2.put("field2", "value2");

    Map<String, Map<String, Object>> form = new HashMap<>();
    form.put("activity1", formData1);
    form.put("activity2", formData2);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form);
    when(execution.getVariable("activity1")).thenReturn(null);
    when(execution.getVariable("activity2")).thenReturn(existingData);

    listener.notify(execution);

    verify(execution).setVariable("activity1", formData1);
    verify(execution, never()).setVariable(eq("activity2"), eq(formData2));
    assertEquals(2, existingData.size());
    assertEquals("existing", existingData.get("outputs"));
    assertEquals("value2", existingData.get("field2"));
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notifyShouldNotSetVariableWhenActivityVariableExistsButIsNotMap() {
    Map<String, Object> formData = new HashMap<>();
    formData.put("field1", "value1");

    Map<String, Map<String, Object>> form = new HashMap<>();
    form.put("activity1", formData);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form);
    when(execution.getVariable("activity1")).thenReturn("not a map");

    listener.notify(execution);

    verify(execution, never()).setVariable(eq("activity1"), eq(formData));
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }
}
