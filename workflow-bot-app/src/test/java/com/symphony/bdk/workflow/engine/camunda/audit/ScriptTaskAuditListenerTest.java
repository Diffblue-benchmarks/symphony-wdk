package com.symphony.bdk.workflow.engine.camunda.audit;

import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.swadl.v1.activity.ExecuteScript;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ScriptTaskAuditListenerTest {

  @Mock
  private AuditTrailLogAction auditTrailLogger;

  @InjectMocks
  private ScriptTaskAuditListener scriptTaskAuditListener;

  @Test
  void shouldCreateScriptTaskAuditListenerWithAuditTrailLogger() {
    ScriptTaskAuditListener listener = new ScriptTaskAuditListener(auditTrailLogger);
    assertThat(listener).isNotNull();
  }

  @Test
  void shouldCallAuditTrailLoggerWithExecuteScriptTypeWhenNotified() {
    DelegateExecution execution = org.mockito.Mockito.mock(DelegateExecution.class);

    scriptTaskAuditListener.notify(execution);

    verify(auditTrailLogger).execute(execution, ExecuteScript.class.getSimpleName());
  }
}
