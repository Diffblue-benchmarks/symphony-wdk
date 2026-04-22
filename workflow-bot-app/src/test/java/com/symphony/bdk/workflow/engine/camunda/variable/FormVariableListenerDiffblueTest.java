package com.symphony.bdk.workflow.engine.camunda.variable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class FormVariableListenerDiffblueTest {

  /**
   * Test {@link FormVariableListener#notify(DelegateExecution)}.
   *
   * <p>When form variable is null, no variables are set or removed.
   */
  @Test
  @DisplayName("Test notify(DelegateExecution); when form variable is null, does nothing")
  void testNotify_whenFormVariableIsNull_doesNothing() {
    // Arrange
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(null);

    // Act
    listener.notify(execution);

    // Assert
    verify(execution, never()).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  /**
   * Test {@link FormVariableListener#notify(DelegateExecution)}.
   *
   * <p>When form variable is not a Map, no variables are set or removed.
   */
  @Test
  @DisplayName("Test notify(DelegateExecution); when form variable is not a Map, does nothing")
  void testNotify_whenFormVariableIsNotMap_doesNothing() {
    // Arrange
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn("not-a-map");

    // Act
    listener.notify(execution);

    // Assert
    verify(execution, never()).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  /**
   * Test {@link FormVariableListener#notify(DelegateExecution)}.
   *
   * <p>When activity variable is null, sets the variable directly from form entry.
   */
  @Test
  @DisplayName("Test notify(DelegateExecution); when activity variable is null, sets variable")
  void testNotify_whenActivityVariableIsNull_setsVariable() {
    // Arrange
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);

    Map<String, Object> formEntryValue = new HashMap<>();
    formEntryValue.put("answer", "yes");

    Map<String, Map<String, Object>> formMap = new HashMap<>();
    formMap.put("formActivityId", formEntryValue);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(formMap);
    when(execution.getVariable("formActivityId")).thenReturn(null);

    // Act
    listener.notify(execution);

    // Assert
    verify(execution).setVariable("formActivityId", formEntryValue);
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  /**
   * Test {@link FormVariableListener#notify(DelegateExecution)}.
   *
   * <p>When activity variable is a Map, merges form entry values into it.
   */
  @Test
  @DisplayName("Test notify(DelegateExecution); when activity variable is a Map, merges values")
  void testNotify_whenActivityVariableIsMap_mergesValues() {
    // Arrange
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);

    Map<String, Object> formEntryValue = new HashMap<>();
    formEntryValue.put("reply", "approved");

    Map<String, Map<String, Object>> formMap = new HashMap<>();
    formMap.put("activityId", formEntryValue);

    Map<String, Object> existingActivity = new HashMap<>();
    existingActivity.put("outputs", "someOutput");

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(formMap);
    when(execution.getVariable("activityId")).thenReturn(existingActivity);

    // Act
    listener.notify(execution);

    // Assert
    assertEquals("approved", existingActivity.get("reply"));
    assertEquals("someOutput", existingActivity.get("outputs"));
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  /**
   * Test {@link FormVariableListener#notify(DelegateExecution)}.
   *
   * <p>When activity variable exists but is not a Map, neither sets nor merges.
   */
  @Test
  @DisplayName("Test notify(DelegateExecution); when activity variable is not a Map, skips merge")
  void testNotify_whenActivityVariableIsNotMap_skipsMerge() {
    // Arrange
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);

    Map<String, Object> formEntryValue = new HashMap<>();
    formEntryValue.put("data", "value");

    Map<String, Map<String, Object>> formMap = new HashMap<>();
    formMap.put("activityId", formEntryValue);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(formMap);
    when(execution.getVariable("activityId")).thenReturn("not-a-map");

    // Act
    listener.notify(execution);

    // Assert
    verify(execution, never()).setVariable("activityId", formEntryValue);
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }
}
