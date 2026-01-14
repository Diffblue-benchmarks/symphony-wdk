package com.symphony.bdk.workflow.engine.handler.audit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuditTrailLogAction.class})
@ExtendWith(SpringExtension.class)
class AuditTrailLogActionDiffblueTest {
  @Autowired private AuditTrailLogAction auditTrailLogAction;

  /**
   * Test {@link AuditTrailLogAction#execute(DelegateExecution, String)} with {@code execution},
   * {@code activityType}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>Then calls {@link ExecutionEntity#getCurrentActivityId()}.
   * </ul>
   *
   * <p>Method under test: {@link AuditTrailLogAction#execute(DelegateExecution, String)}
   */
  @Test
  @DisplayName(
      "Test execute(DelegateExecution, String) with 'execution', 'activityType'; given '42'; then calls getCurrentActivityId()")
  @Tag("MaintainedByDiffblue")
  void testExecuteWithExecutionActivityType_given42_thenCallsGetCurrentActivityId() {
    // Arrange
    ExecutionEntity execution = mock(ExecutionEntity.class);
    when(execution.getCurrentActivityId()).thenReturn("42");
    when(execution.getCurrentActivityName()).thenReturn("Current Activity Name");
    when(execution.getProcessDefinitionId()).thenReturn("42");
    when(execution.getProcessDefinition()).thenReturn(new ProcessDefinitionEntity());

    // Act
    auditTrailLogAction.execute(execution, "Activity Type");

    // Assert
    verify(execution).getCurrentActivityId();
    verify(execution).getCurrentActivityName();
    verify(execution).getProcessDefinition();
    verify(execution).getProcessDefinitionId();
  }
}
