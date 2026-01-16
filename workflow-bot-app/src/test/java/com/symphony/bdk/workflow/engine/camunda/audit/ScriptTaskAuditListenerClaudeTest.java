package com.symphony.bdk.workflow.engine.camunda.audit;

import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.swadl.v1.activity.ExecuteScript;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScriptTaskAuditListenerClaudeTest {

  private AuditTrailLogAction auditTrailLogger;
  private ScriptTaskAuditListener listener;

  @BeforeEach
  void setUp() {
    auditTrailLogger = mock(AuditTrailLogAction.class);
  }

  // Tests for constructor

  @Test
  void constructor_withValidAuditTrailLogger_shouldCreateListener() {
    // Given: A valid AuditTrailLogAction
    // When: Creating ScriptTaskAuditListener with the logger
    listener = new ScriptTaskAuditListener(auditTrailLogger);

    // Then: Listener should be created successfully and store the logger
    assertThat(listener).isNotNull();
  }

  @Test
  void constructor_withNullAuditTrailLogger_shouldStillCreateListener() {
    // Given: A null AuditTrailLogAction
    // When: Creating ScriptTaskAuditListener with null logger
    listener = new ScriptTaskAuditListener(null);

    // Then: Listener should be created (validation happens at usage time if needed)
    assertThat(listener).isNotNull();
  }

  // Tests for notify method

  @Test
  void notify_withValidExecution_shouldCallAuditTrailLoggerWithExecuteScriptType() {
    // Given: A listener with a mocked audit trail logger
    listener = new ScriptTaskAuditListener(auditTrailLogger);
    DelegateExecution execution = mock(DelegateExecution.class);

    // When: notify is called with a delegate execution
    listener.notify(execution);

    // Then: Should call auditTrailLogger.execute with the execution and "ExecuteScript" type
    verify(auditTrailLogger).execute(eq(execution), eq("ExecuteScript"));
  }

  @Test
  void notify_withNullExecution_shouldStillCallAuditTrailLogger() {
    // Given: A listener with a mocked audit trail logger
    listener = new ScriptTaskAuditListener(auditTrailLogger);

    // When: notify is called with null execution
    listener.notify(null);

    // Then: Should call auditTrailLogger.execute with null and "ExecuteScript" type
    verify(auditTrailLogger).execute(eq(null), eq("ExecuteScript"));
  }

  @Test
  void notify_withMultipleExecutions_shouldCallAuditTrailLoggerForEach() {
    // Given: A listener with a mocked audit trail logger
    listener = new ScriptTaskAuditListener(auditTrailLogger);
    DelegateExecution execution1 = mock(DelegateExecution.class);
    DelegateExecution execution2 = mock(DelegateExecution.class);
    DelegateExecution execution3 = mock(DelegateExecution.class);

    // When: notify is called multiple times with different executions
    listener.notify(execution1);
    listener.notify(execution2);
    listener.notify(execution3);

    // Then: Should call auditTrailLogger.execute for each execution
    verify(auditTrailLogger).execute(eq(execution1), eq("ExecuteScript"));
    verify(auditTrailLogger).execute(eq(execution2), eq("ExecuteScript"));
    verify(auditTrailLogger).execute(eq(execution3), eq("ExecuteScript"));
  }

  @Test
  void notify_withExecutionHavingProperties_shouldPassExecutionToAuditLogger() {
    // Given: A listener with a mocked audit trail logger
    listener = new ScriptTaskAuditListener(auditTrailLogger);
    DelegateExecution execution = mock(DelegateExecution.class);

    // Set up execution properties
    when(execution.getProcessDefinitionId()).thenReturn("process-123");
    when(execution.getCurrentActivityId()).thenReturn("script-task-1");
    when(execution.getCurrentActivityName()).thenReturn("Execute Script");

    // When: notify is called with an execution that has properties
    listener.notify(execution);

    // Then: Should call auditTrailLogger.execute with the execution object
    // The audit logger will extract the properties from the execution
    verify(auditTrailLogger).execute(eq(execution), eq("ExecuteScript"));
  }

  @Test
  void notify_activityTypeParameter_shouldAlwaysBeExecuteScriptSimpleName() {
    // Given: A listener with a mocked audit trail logger
    listener = new ScriptTaskAuditListener(auditTrailLogger);
    DelegateExecution execution = mock(DelegateExecution.class);

    // When: notify is called
    listener.notify(execution);

    // Then: Activity type should be the simple name of ExecuteScript class
    String expectedActivityType = ExecuteScript.class.getSimpleName();
    assertThat(expectedActivityType).isEqualTo("ExecuteScript");
    verify(auditTrailLogger).execute(eq(execution), eq(expectedActivityType));
  }

  @Test
  void notify_consecutiveCalls_shouldCallAuditTrailLoggerEachTime() {
    // Given: A listener with a mocked audit trail logger
    listener = new ScriptTaskAuditListener(auditTrailLogger);
    DelegateExecution execution = mock(DelegateExecution.class);

    // When: notify is called multiple times with the same execution
    listener.notify(execution);
    listener.notify(execution);

    // Then: Should call auditTrailLogger.execute both times
    verify(auditTrailLogger, org.mockito.Mockito.times(2)).execute(eq(execution), eq("ExecuteScript"));
  }

  // Integration tests

  @Test
  void listener_asExecutionListener_shouldImplementNotifyMethod() {
    // Given: The ScriptTaskAuditListener class
    // When: Checking if it implements ExecutionListener
    // Then: It should have a notify method that accepts DelegateExecution
    listener = new ScriptTaskAuditListener(auditTrailLogger);
    assertThat(listener).isInstanceOf(org.camunda.bpm.engine.delegate.ExecutionListener.class);
  }

  @Test
  void listener_withRealExecutionScenario_shouldAuditCorrectly() {
    // Given: A listener with a mocked audit trail logger
    listener = new ScriptTaskAuditListener(auditTrailLogger);
    DelegateExecution execution = mock(DelegateExecution.class);

    // Simulate a real script task execution scenario
    when(execution.getProcessDefinitionId()).thenReturn("workflow-process:1:abc123");
    when(execution.getCurrentActivityId()).thenReturn("executeGroovyScript");
    when(execution.getCurrentActivityName()).thenReturn("Execute Groovy Script");

    // When: The listener is notified (as it would be when attached to a script task)
    listener.notify(execution);

    // Then: Should properly delegate to audit trail logger
    verify(auditTrailLogger).execute(execution, "ExecuteScript");
  }
}
