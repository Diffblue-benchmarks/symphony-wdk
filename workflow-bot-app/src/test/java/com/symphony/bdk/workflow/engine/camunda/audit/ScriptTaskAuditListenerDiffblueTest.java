package com.symphony.bdk.workflow.engine.camunda.audit;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ScriptTaskAuditListenerDiffblueTest {
  /**
   * Test {@link ScriptTaskAuditListener#notify(DelegateExecution)} with {@code DelegateExecution}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then calls {@link ExecutionEntity#getCurrentActivityId()}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptTaskAuditListener#notify(DelegateExecution)}
   */
  @Test
  @DisplayName(
      "Test notify(DelegateExecution) with 'DelegateExecution'; given '42'; then calls getCurrentActivityId()")
  @Tag("MaintainedByDiffblue")
  void testNotifyWithDelegateExecution_given42_thenCallsGetCurrentActivityId() {
    // Arrange
    ScriptTaskAuditListener scriptTaskAuditListener =
        new ScriptTaskAuditListener(new AuditTrailLogAction());

    ExecutionEntity execution = mock(ExecutionEntity.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getCurrentActivityName()).thenReturn("Current Activity Name");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessDefinition()).thenReturn(new ProcessDefinitionEntity());

    // Act
    scriptTaskAuditListener.notify(execution);

    // Assert
    verify(execution).getCurrentActivityId();
    verify(execution).getCurrentActivityName();
    verify(execution).getProcessDefinition();
    verify(execution).getProcessDefinitionId();
  }

  /**
   * Test {@link ScriptTaskAuditListener#notify(DelegateExecution)} with {@code DelegateExecution}.
   *
   * <ul>
   *   <li>Then calls {@link AuditTrailLogAction#execute(DelegateExecution, String)}.
   * </ul>
   *
   * <p>Method under test: {@link ScriptTaskAuditListener#notify(DelegateExecution)}
   */
  @Test
  @DisplayName(
      "Test notify(DelegateExecution) with 'DelegateExecution'; then calls execute(DelegateExecution, String)")
  @Tag("MaintainedByDiffblue")
  void testNotifyWithDelegateExecution_thenCallsExecute() {
    // Arrange
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);
    doNothing()
        .when(auditTrailLogger)
        .execute(Mockito.<DelegateExecution>any(), Mockito.<String>any());

    // Act
    new ScriptTaskAuditListener(auditTrailLogger).notify(mock(ExecutionEntity.class));

    // Assert
    verify(auditTrailLogger).execute(isA(DelegateExecution.class), eq("ExecuteScript"));
  }
}
