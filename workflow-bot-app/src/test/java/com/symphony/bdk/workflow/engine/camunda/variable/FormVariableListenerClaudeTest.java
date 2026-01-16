package com.symphony.bdk.workflow.engine.camunda.variable;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FormVariableListenerClaudeTest {

  // Tests for constructor

  @Test
  void constructor_shouldCreateInstance() {
    // When: Constructor is called
    FormVariableListener listener = new FormVariableListener();

    // Then: Instance should be created successfully
    assertThat(listener).isNotNull();
  }

  @Test
  void constructor_shouldNotThrowException() {
    // When/Then: Constructor should not throw exception
    assertThatCode(() -> new FormVariableListener())
        .doesNotThrowAnyException();
  }

  @Test
  void constructor_shouldImplementExecutionListener() {
    // When: Constructor is called
    FormVariableListener listener = new FormVariableListener();

    // Then: Instance should implement ExecutionListener
    assertThat(listener).isInstanceOf(org.camunda.bpm.engine.delegate.ExecutionListener.class);
  }

  @Test
  void constructor_calledMultipleTimes_shouldCreateDistinctInstances() {
    // When: Constructor is called multiple times
    FormVariableListener listener1 = new FormVariableListener();
    FormVariableListener listener2 = new FormVariableListener();

    // Then: Each call should create a distinct instance
    assertThat(listener1).isNotNull();
    assertThat(listener2).isNotNull();
    assertThat(listener1).isNotSameAs(listener2);
  }

  // Tests for notify method

  @Test
  void notify_withNoFormVariable_shouldNotModifyVariables() {
    // Given: A listener and execution with no form variable
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(null);

    // When: notify is called
    listener.notify(execution);

    // Then: Should not attempt to set or remove variables
    verify(execution).getVariable(FormVariableListener.FORM_VARIABLES);
    verify(execution, never()).setVariable(eq(FormVariableListener.FORM_VARIABLES), eq(null));
    verify(execution, never()).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notify_withNonMapFormVariable_shouldNotModifyVariables() {
    // Given: A listener and execution with a non-Map form variable (e.g., a String)
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);
    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn("not a map");

    // When: notify is called
    listener.notify(execution);

    // Then: Should not attempt to modify variables (except for the initial get)
    verify(execution).getVariable(FormVariableListener.FORM_VARIABLES);
    verify(execution, never()).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notify_withEmptyFormMap_shouldRemoveFormVariable() {
    // Given: A listener and execution with an empty form map
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);
    Map<String, Map<String, Object>> emptyForm = new HashMap<>();
    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(emptyForm);

    // When: notify is called
    listener.notify(execution);

    // Then: Should remove the form variable
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notify_withFormMapAndNoExistingActivity_shouldSetActivityVariable() {
    // Given: A listener with a form map containing form data
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);

    Map<String, Object> formData = new HashMap<>();
    formData.put("field1", "value1");
    formData.put("field2", "value2");

    Map<String, Map<String, Object>> form = new HashMap<>();
    form.put("activity1", formData);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form);
    when(execution.getVariable("activity1")).thenReturn(null);

    // When: notify is called
    listener.notify(execution);

    // Then: Should set the activity variable with form data and remove form variable
    verify(execution).setVariable("activity1", formData);
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notify_withFormMapAndExistingActivityMap_shouldMergeVariables() {
    // Given: A listener with a form map and existing activity map
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);

    Map<String, Object> formData = new HashMap<>();
    formData.put("formField1", "formValue1");
    formData.put("formField2", "formValue2");

    Map<String, Object> existingActivity = new HashMap<>();
    existingActivity.put("outputs", "someOutput");
    existingActivity.put("existingField", "existingValue");

    Map<String, Map<String, Object>> form = new HashMap<>();
    form.put("activity1", formData);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form);
    when(execution.getVariable("activity1")).thenReturn(existingActivity);

    // When: notify is called
    listener.notify(execution);

    // Then: Should merge form data into existing activity map
    assertThat(existingActivity).containsEntry("formField1", "formValue1");
    assertThat(existingActivity).containsEntry("formField2", "formValue2");
    assertThat(existingActivity).containsEntry("outputs", "someOutput");
    assertThat(existingActivity).containsEntry("existingField", "existingValue");
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notify_withFormMapOverwritingExistingKeys_shouldOverwriteValues() {
    // Given: A listener with form data that has keys matching existing activity data
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);

    Map<String, Object> formData = new HashMap<>();
    formData.put("field1", "newValue");

    Map<String, Object> existingActivity = new HashMap<>();
    existingActivity.put("field1", "oldValue");
    existingActivity.put("field2", "keepValue");

    Map<String, Map<String, Object>> form = new HashMap<>();
    form.put("activity1", formData);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form);
    when(execution.getVariable("activity1")).thenReturn(existingActivity);

    // When: notify is called
    listener.notify(execution);

    // Then: Form data should overwrite existing activity data
    assertThat(existingActivity).containsEntry("field1", "newValue");
    assertThat(existingActivity).containsEntry("field2", "keepValue");
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notify_withMultipleFormEntries_shouldProcessAll() {
    // Given: A listener with multiple form entries
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);

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

    // When: notify is called
    listener.notify(execution);

    // Then: Should set both activity variables and remove form variable
    verify(execution).setVariable("activity1", formData1);
    verify(execution).setVariable("activity2", formData2);
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notify_withExistingNonMapActivityVariable_shouldNotMerge() {
    // Given: A listener with a form map where the activity variable exists but is not a Map
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);

    Map<String, Object> formData = new HashMap<>();
    formData.put("field1", "value1");

    Map<String, Map<String, Object>> form = new HashMap<>();
    form.put("activity1", formData);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form);
    when(execution.getVariable("activity1")).thenReturn("not a map");

    // When: notify is called
    listener.notify(execution);

    // Then: Should not attempt to merge (no setVariable or putAll on non-Map)
    verify(execution, never()).setVariable(eq("activity1"), eq(formData));
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notify_withNullExecution_shouldThrowNullPointerException() {
    // Given: A listener
    FormVariableListener listener = new FormVariableListener();

    // When/Then: Calling notify with null execution throws NullPointerException
    // as it attempts to call getVariable on null
    assertThatCode(() -> listener.notify(null))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void notify_multipleCalls_shouldProcessEachIndependently() {
    // Given: A listener and multiple executions
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution1 = mock(DelegateExecution.class);
    DelegateExecution execution2 = mock(DelegateExecution.class);

    Map<String, Object> formData1 = new HashMap<>();
    formData1.put("field1", "value1");
    Map<String, Map<String, Object>> form1 = new HashMap<>();
    form1.put("activity1", formData1);

    Map<String, Object> formData2 = new HashMap<>();
    formData2.put("field2", "value2");
    Map<String, Map<String, Object>> form2 = new HashMap<>();
    form2.put("activity2", formData2);

    when(execution1.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form1);
    when(execution1.getVariable("activity1")).thenReturn(null);
    when(execution2.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form2);
    when(execution2.getVariable("activity2")).thenReturn(null);

    // When: notify is called multiple times
    listener.notify(execution1);
    listener.notify(execution2);

    // Then: Should process each execution independently
    verify(execution1).setVariable("activity1", formData1);
    verify(execution1).removeVariable(FormVariableListener.FORM_VARIABLES);
    verify(execution2).setVariable("activity2", formData2);
    verify(execution2).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void notify_withComplexFormData_shouldHandleCorrectly() {
    // Given: A listener with complex nested form data
    FormVariableListener listener = new FormVariableListener();
    DelegateExecution execution = mock(DelegateExecution.class);

    Map<String, Object> nestedData = new HashMap<>();
    nestedData.put("nested", "value");

    Map<String, Object> formData = new HashMap<>();
    formData.put("simpleField", "simpleValue");
    formData.put("complexField", nestedData);
    formData.put("numberField", 42);
    formData.put("booleanField", true);

    Map<String, Object> existingActivity = new HashMap<>();
    existingActivity.put("existingField", "existingValue");

    Map<String, Map<String, Object>> form = new HashMap<>();
    form.put("activity1", formData);

    when(execution.getVariable(FormVariableListener.FORM_VARIABLES)).thenReturn(form);
    when(execution.getVariable("activity1")).thenReturn(existingActivity);

    // When: notify is called
    listener.notify(execution);

    // Then: Should merge all complex data types correctly
    assertThat(existingActivity).containsEntry("simpleField", "simpleValue");
    assertThat(existingActivity).containsEntry("complexField", nestedData);
    assertThat(existingActivity).containsEntry("numberField", 42);
    assertThat(existingActivity).containsEntry("booleanField", true);
    assertThat(existingActivity).containsEntry("existingField", "existingValue");
    verify(execution).removeVariable(FormVariableListener.FORM_VARIABLES);
  }

  @Test
  void formVariablesConstant_shouldHaveCorrectValue() {
    // Then: The constant should have the expected value
    assertThat(FormVariableListener.FORM_VARIABLES).isEqualTo("form");
  }
}
