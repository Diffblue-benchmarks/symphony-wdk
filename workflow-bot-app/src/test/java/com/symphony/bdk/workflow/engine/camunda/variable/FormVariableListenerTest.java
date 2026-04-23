package com.symphony.bdk.workflow.engine.camunda.variable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class FormVariableListenerTest {

  private final FormVariableListener underTest = new FormVariableListener();

  @Test
  void shouldDoNothingWhenFormVariableIsNull() {
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(null);

    underTest.notify(execution);

    verify(execution, never()).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void shouldDoNothingWhenFormVariableIsNotAMap() {
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn("not-a-map");

    underTest.notify(execution);

    verify(execution, never()).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void shouldSetVariableWhenActivityVariableIsNull() {
    DelegateExecution execution = mock(DelegateExecution.class);
    Map<String, Object> formData = new HashMap<>();
    formData.put("answer", "yes");
    Map<String, Map<String, Object>> form = new HashMap<>();
    form.put("formActivityId", formData);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form);
    when(execution.getVariable("formActivityId")).thenReturn(null);

    underTest.notify(execution);

    verify(execution).setVariable("formActivityId", formData);
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void shouldMergeVariablesWhenActivityVariableIsAMap() {
    DelegateExecution execution = mock(DelegateExecution.class);
    Map<String, Object> formData = new HashMap<>();
    formData.put("answer", "yes");
    Map<String, Map<String, Object>> form = new HashMap<>();
    form.put("formActivityId", formData);

    Map<String, Object> existingActivity = new HashMap<>();
    existingActivity.put("outputs", "someOutput");

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form);
    when(execution.getVariable("formActivityId")).thenReturn(existingActivity);

    underTest.notify(execution);

    assertThat(existingActivity).containsEntry("answer", "yes").containsEntry("outputs", "someOutput");
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void shouldNotSetVariableWhenActivityVariableIsNotAMap() {
    DelegateExecution execution = mock(DelegateExecution.class);
    Map<String, Object> formData = new HashMap<>();
    formData.put("answer", "yes");
    Map<String, Map<String, Object>> form = new HashMap<>();
    form.put("formActivityId", formData);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form);
    when(execution.getVariable("formActivityId")).thenReturn("not-a-map");

    underTest.notify(execution);

    verify(execution, never()).setVariable("formActivityId", formData);
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }
}
