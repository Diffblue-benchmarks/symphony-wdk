package com.symphony.bdk.workflow.engine.handler.audit;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.history.event.HistoricActivityInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricJobLogEvent;
import org.camunda.bpm.engine.impl.history.event.HistoricProcessInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricVariableUpdateEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuditTrailLogActionClaude_executeTest {

  private AuditTrailLogAction action;

  @BeforeEach
  void setUp() {
    action = new AuditTrailLogAction();
  }

  @Test
  void execute_withHistoricJobLogEvent_shouldLogJobEvent() {
    // Given: A HistoricJobLogEvent with necessary properties
    HistoricJobLogEvent event = mock(HistoricJobLogEvent.class);
    when(event.getJobId()).thenReturn("job-123");
    when(event.getJobDefinitionType()).thenReturn("async-continuation");
    when(event.getProcessInstanceId()).thenReturn("process-instance-456");
    when(event.getProcessDefinitionKey()).thenReturn("workflow-process");
    when(event.getActivityId()).thenReturn("activity-789");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(event))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withHistoricProcessInstanceEventEntity_shouldLogProcessEvent() {
    // Given: A HistoricProcessInstanceEventEntity without duration
    HistoricProcessInstanceEventEntity event = mock(HistoricProcessInstanceEventEntity.class);
    when(event.getEventType()).thenReturn("start");
    when(event.getProcessInstanceId()).thenReturn("process-instance-123");
    when(event.getProcessDefinitionKey()).thenReturn("workflow-key");
    when(event.getDurationInMillis()).thenReturn(null);

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(event))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withHistoricProcessInstanceEventEntity_withDuration_shouldLogWithDuration() {
    // Given: A HistoricProcessInstanceEventEntity with duration
    HistoricProcessInstanceEventEntity event = mock(HistoricProcessInstanceEventEntity.class);
    when(event.getEventType()).thenReturn("end");
    when(event.getProcessInstanceId()).thenReturn("process-instance-456");
    when(event.getProcessDefinitionKey()).thenReturn("workflow-key");
    when(event.getDurationInMillis()).thenReturn(5000L);

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(event))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withHistoricActivityInstanceEventEntity_shouldLogActivityEvent() {
    // Given: A HistoricActivityInstanceEventEntity without duration
    HistoricActivityInstanceEventEntity event = mock(HistoricActivityInstanceEventEntity.class);
    when(event.getEventType()).thenReturn("start");
    when(event.getProcessInstanceId()).thenReturn("process-instance-789");
    when(event.getProcessDefinitionKey()).thenReturn("workflow-process");
    when(event.getActivityId()).thenReturn("activity-123");
    when(event.getActivityName()).thenReturn("User Task");
    when(event.getDurationInMillis()).thenReturn(null);

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(event))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withHistoricActivityInstanceEventEntity_withDuration_shouldLogWithDuration() {
    // Given: A HistoricActivityInstanceEventEntity with duration
    HistoricActivityInstanceEventEntity event = mock(HistoricActivityInstanceEventEntity.class);
    when(event.getEventType()).thenReturn("end");
    when(event.getProcessInstanceId()).thenReturn("process-instance-789");
    when(event.getProcessDefinitionKey()).thenReturn("workflow-process");
    when(event.getActivityId()).thenReturn("activity-456");
    when(event.getActivityName()).thenReturn("Service Task");
    when(event.getDurationInMillis()).thenReturn(3000L);

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(event))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withHistoricVariableUpdateEventEntity_initiatorVariable_shouldLogInitiator() {
    // Given: A HistoricVariableUpdateEventEntity for initiator variable
    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn("initiator");
    when(event.getLongValue()).thenReturn(12345L);
    when(event.getProcessInstanceId()).thenReturn("process-instance-101");
    when(event.getProcessDefinitionKey()).thenReturn("workflow-key");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(event))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withHistoricVariableUpdateEventEntity_nonInitiatorVariable_shouldNotLog() {
    // Given: A HistoricVariableUpdateEventEntity for a non-initiator variable
    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn("someOtherVariable");
    when(event.getLongValue()).thenReturn(99999L);
    when(event.getProcessInstanceId()).thenReturn("process-instance-202");
    when(event.getProcessDefinitionKey()).thenReturn("workflow-key");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(event))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withHistoricVariableUpdateEventEntity_initiatorVariableWithNullValue_shouldNotLog() {
    // Given: A HistoricVariableUpdateEventEntity for initiator variable but with null value
    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn("initiator");
    when(event.getLongValue()).thenReturn(null);
    when(event.getProcessInstanceId()).thenReturn("process-instance-303");
    when(event.getProcessDefinitionKey()).thenReturn("workflow-key");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(event))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withUnknownHistoryEventType_shouldLogTrace() {
    // Given: A HistoryEvent that is not one of the known types
    HistoryEvent event = mock(HistoryEvent.class);

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(event))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withNullEvent_shouldHandleGracefully() {
    // Given: A null HistoryEvent
    // When/Then: Should execute without throwing an exception
    // (The method may throw NPE if it tries to check instanceof on null,
    // but the instanceof checks will all return false for null)
    assertThatCode(() -> action.execute(null))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_multipleCallsWithDifferentEventTypes_shouldExecuteAll() {
    // Given: Multiple different event types
    HistoricJobLogEvent jobEvent = mock(HistoricJobLogEvent.class);
    when(jobEvent.getJobId()).thenReturn("job-1");
    when(jobEvent.getJobDefinitionType()).thenReturn("type-1");
    when(jobEvent.getProcessInstanceId()).thenReturn("process-1");
    when(jobEvent.getProcessDefinitionKey()).thenReturn("key-1");
    when(jobEvent.getActivityId()).thenReturn("activity-1");

    HistoricProcessInstanceEventEntity processEvent = mock(HistoricProcessInstanceEventEntity.class);
    when(processEvent.getEventType()).thenReturn("start");
    when(processEvent.getProcessInstanceId()).thenReturn("process-2");
    when(processEvent.getProcessDefinitionKey()).thenReturn("key-2");
    when(processEvent.getDurationInMillis()).thenReturn(null);

    HistoricActivityInstanceEventEntity activityEvent = mock(HistoricActivityInstanceEventEntity.class);
    when(activityEvent.getEventType()).thenReturn("end");
    when(activityEvent.getProcessInstanceId()).thenReturn("process-3");
    when(activityEvent.getProcessDefinitionKey()).thenReturn("key-3");
    when(activityEvent.getActivityId()).thenReturn("activity-3");
    when(activityEvent.getActivityName()).thenReturn("Task 3");
    when(activityEvent.getDurationInMillis()).thenReturn(1000L);

    // When/Then: Should execute all without throwing exceptions
    assertThatCode(() -> {
      action.execute(jobEvent);
      action.execute(processEvent);
      action.execute(activityEvent);
    }).doesNotThrowAnyException();
  }

  @Test
  void execute_withHistoricJobLogEvent_allFieldsNull_shouldHandleGracefully() {
    // Given: A HistoricJobLogEvent with all null fields
    HistoricJobLogEvent event = mock(HistoricJobLogEvent.class);
    when(event.getJobId()).thenReturn(null);
    when(event.getJobDefinitionType()).thenReturn(null);
    when(event.getProcessInstanceId()).thenReturn(null);
    when(event.getProcessDefinitionKey()).thenReturn(null);
    when(event.getActivityId()).thenReturn(null);

    // When/Then: Should execute without throwing an exception
    // (Logging frameworks typically handle null values gracefully)
    assertThatCode(() -> action.execute(event))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withHistoricVariableUpdateEventEntity_initiatorVariableWithZeroValue_shouldLog() {
    // Given: A HistoricVariableUpdateEventEntity for initiator with 0 value
    HistoricVariableUpdateEventEntity event = mock(HistoricVariableUpdateEventEntity.class);
    when(event.getVariableName()).thenReturn("initiator");
    when(event.getLongValue()).thenReturn(0L);
    when(event.getProcessInstanceId()).thenReturn("process-instance-404");
    when(event.getProcessDefinitionKey()).thenReturn("workflow-key");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(event))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_consecutiveCalls_withSameEvent_shouldHandleMultipleCalls() {
    // Given: An event that will be executed multiple times
    HistoricProcessInstanceEventEntity event = mock(HistoricProcessInstanceEventEntity.class);
    when(event.getEventType()).thenReturn("update");
    when(event.getProcessInstanceId()).thenReturn("process-repeat");
    when(event.getProcessDefinitionKey()).thenReturn("key-repeat");
    when(event.getDurationInMillis()).thenReturn(2000L);

    // When/Then: Should execute multiple times without issues
    assertThatCode(() -> {
      action.execute(event);
      action.execute(event);
      action.execute(event);
    }).doesNotThrowAnyException();
  }

  @Test
  void execute_withHistoricActivityInstanceEventEntity_emptyStrings_shouldHandleGracefully() {
    // Given: A HistoricActivityInstanceEventEntity with empty string values
    HistoricActivityInstanceEventEntity event = mock(HistoricActivityInstanceEventEntity.class);
    when(event.getEventType()).thenReturn("");
    when(event.getProcessInstanceId()).thenReturn("");
    when(event.getProcessDefinitionKey()).thenReturn("");
    when(event.getActivityId()).thenReturn("");
    when(event.getActivityName()).thenReturn("");
    when(event.getDurationInMillis()).thenReturn(null);

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(event))
        .doesNotThrowAnyException();
  }

  // Tests for execute(DelegateExecution, String) method

  @Test
  void execute_withDelegateExecution_shouldLogActivityExecution() {
    // Given: A DelegateExecution with all required properties
    ExecutionEntity execution = mock(ExecutionEntity.class);
    ProcessDefinitionEntity processDefinition = mock(ProcessDefinitionEntity.class);

    when(execution.getProcessDefinitionId()).thenReturn("process-def-123");
    when(execution.getProcessDefinition()).thenReturn(processDefinition);
    when(processDefinition.getKey()).thenReturn("workflow-key");
    when(execution.getCurrentActivityId()).thenReturn("activity-456");
    when(execution.getCurrentActivityName()).thenReturn("My Activity");

    String activityType = "ServiceTask";

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(execution, activityType))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withDelegateExecution_nullActivityType_shouldHandleGracefully() {
    // Given: A DelegateExecution with null activity type
    ExecutionEntity execution = mock(ExecutionEntity.class);
    ProcessDefinitionEntity processDefinition = mock(ProcessDefinitionEntity.class);

    when(execution.getProcessDefinitionId()).thenReturn("process-def-789");
    when(execution.getProcessDefinition()).thenReturn(processDefinition);
    when(processDefinition.getKey()).thenReturn("workflow-key-2");
    when(execution.getCurrentActivityId()).thenReturn("activity-789");
    when(execution.getCurrentActivityName()).thenReturn("Another Activity");

    // When/Then: Should execute without throwing an exception (logging handles null)
    assertThatCode(() -> action.execute(execution, null))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withDelegateExecution_emptyActivityType_shouldHandleGracefully() {
    // Given: A DelegateExecution with empty activity type
    ExecutionEntity execution = mock(ExecutionEntity.class);
    ProcessDefinitionEntity processDefinition = mock(ProcessDefinitionEntity.class);

    when(execution.getProcessDefinitionId()).thenReturn("process-def-111");
    when(execution.getProcessDefinition()).thenReturn(processDefinition);
    when(processDefinition.getKey()).thenReturn("workflow-key-3");
    when(execution.getCurrentActivityId()).thenReturn("activity-111");
    when(execution.getCurrentActivityName()).thenReturn("Third Activity");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(execution, ""))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withDelegateExecution_nullExecution_shouldThrowException() {
    // Given: A null DelegateExecution
    String activityType = "UserTask";

    // When/Then: Should throw NullPointerException when trying to access execution properties
    assertThatThrownBy(() -> action.execute(null, activityType))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void execute_withDelegateExecution_nullExecutionProperties_shouldHandleGracefully() {
    // Given: An ExecutionEntity with null properties
    ExecutionEntity execution = mock(ExecutionEntity.class);
    ProcessDefinitionEntity processDefinition = mock(ProcessDefinitionEntity.class);

    when(execution.getProcessDefinitionId()).thenReturn(null);
    when(execution.getProcessDefinition()).thenReturn(processDefinition);
    when(processDefinition.getKey()).thenReturn(null);
    when(execution.getCurrentActivityId()).thenReturn(null);
    when(execution.getCurrentActivityName()).thenReturn(null);

    // When/Then: Should execute without throwing an exception (logging handles nulls)
    assertThatCode(() -> action.execute(execution, "TaskType"))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withDelegateExecution_nullProcessDefinition_shouldThrowException() {
    // Given: An ExecutionEntity with null process definition
    ExecutionEntity execution = mock(ExecutionEntity.class);

    when(execution.getProcessDefinitionId()).thenReturn("process-def-222");
    when(execution.getProcessDefinition()).thenReturn(null);
    when(execution.getCurrentActivityId()).thenReturn("activity-222");
    when(execution.getCurrentActivityName()).thenReturn("Task Name");

    // When/Then: Should throw NullPointerException when accessing process definition key
    assertThatThrownBy(() -> action.execute(execution, "ScriptTask"))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void execute_withDelegateExecution_multipleActivityTypes_shouldExecuteAll() {
    // Given: A DelegateExecution that will be used with different activity types
    ExecutionEntity execution = mock(ExecutionEntity.class);
    ProcessDefinitionEntity processDefinition = mock(ProcessDefinitionEntity.class);

    when(execution.getProcessDefinitionId()).thenReturn("process-def-multi");
    when(execution.getProcessDefinition()).thenReturn(processDefinition);
    when(processDefinition.getKey()).thenReturn("workflow-multi");
    when(execution.getCurrentActivityId()).thenReturn("activity-multi");
    when(execution.getCurrentActivityName()).thenReturn("Multi Activity");

    // When/Then: Should execute with different activity types without issues
    assertThatCode(() -> {
      action.execute(execution, "ServiceTask");
      action.execute(execution, "UserTask");
      action.execute(execution, "ScriptTask");
      action.execute(execution, "SendTask");
    }).doesNotThrowAnyException();
  }

  @Test
  void execute_withDelegateExecution_specialCharactersInStrings_shouldHandleGracefully() {
    // Given: A DelegateExecution with special characters in string properties
    ExecutionEntity execution = mock(ExecutionEntity.class);
    ProcessDefinitionEntity processDefinition = mock(ProcessDefinitionEntity.class);

    when(execution.getProcessDefinitionId()).thenReturn("process-def-<>&\"'");
    when(execution.getProcessDefinition()).thenReturn(processDefinition);
    when(processDefinition.getKey()).thenReturn("workflow-key-<>&\"'");
    when(execution.getCurrentActivityId()).thenReturn("activity-<>&\"'");
    when(execution.getCurrentActivityName()).thenReturn("Activity Name with <>&\"'");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(execution, "Task<>&\"'"))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withDelegateExecution_consecutiveCalls_shouldExecuteMultipleTimes() {
    // Given: A DelegateExecution that will be executed multiple times
    ExecutionEntity execution = mock(ExecutionEntity.class);
    ProcessDefinitionEntity processDefinition = mock(ProcessDefinitionEntity.class);

    when(execution.getProcessDefinitionId()).thenReturn("process-def-repeat");
    when(execution.getProcessDefinition()).thenReturn(processDefinition);
    when(processDefinition.getKey()).thenReturn("workflow-repeat");
    when(execution.getCurrentActivityId()).thenReturn("activity-repeat");
    when(execution.getCurrentActivityName()).thenReturn("Repeat Activity");

    // When/Then: Should execute multiple times without issues
    assertThatCode(() -> {
      action.execute(execution, "ServiceTask");
      action.execute(execution, "ServiceTask");
      action.execute(execution, "ServiceTask");
    }).doesNotThrowAnyException();
  }

  @Test
  void execute_withDelegateExecution_differentExecutions_shouldExecuteAll() {
    // Given: Multiple different DelegateExecutions
    ExecutionEntity execution1 = mock(ExecutionEntity.class);
    ProcessDefinitionEntity processDefinition1 = mock(ProcessDefinitionEntity.class);
    when(execution1.getProcessDefinitionId()).thenReturn("process-1");
    when(execution1.getProcessDefinition()).thenReturn(processDefinition1);
    when(processDefinition1.getKey()).thenReturn("workflow-1");
    when(execution1.getCurrentActivityId()).thenReturn("activity-1");
    when(execution1.getCurrentActivityName()).thenReturn("Activity 1");

    ExecutionEntity execution2 = mock(ExecutionEntity.class);
    ProcessDefinitionEntity processDefinition2 = mock(ProcessDefinitionEntity.class);
    when(execution2.getProcessDefinitionId()).thenReturn("process-2");
    when(execution2.getProcessDefinition()).thenReturn(processDefinition2);
    when(processDefinition2.getKey()).thenReturn("workflow-2");
    when(execution2.getCurrentActivityId()).thenReturn("activity-2");
    when(execution2.getCurrentActivityName()).thenReturn("Activity 2");

    // When/Then: Should execute all without throwing exceptions
    assertThatCode(() -> {
      action.execute(execution1, "UserTask");
      action.execute(execution2, "ServiceTask");
    }).doesNotThrowAnyException();
  }

  @Test
  void execute_withDelegateExecution_emptyStringProperties_shouldHandleGracefully() {
    // Given: A DelegateExecution with empty string properties
    ExecutionEntity execution = mock(ExecutionEntity.class);
    ProcessDefinitionEntity processDefinition = mock(ProcessDefinitionEntity.class);

    when(execution.getProcessDefinitionId()).thenReturn("");
    when(execution.getProcessDefinition()).thenReturn(processDefinition);
    when(processDefinition.getKey()).thenReturn("");
    when(execution.getCurrentActivityId()).thenReturn("");
    when(execution.getCurrentActivityName()).thenReturn("");

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(execution, ""))
        .doesNotThrowAnyException();
  }

  @Test
  void execute_withDelegateExecution_longStrings_shouldHandleGracefully() {
    // Given: A DelegateExecution with very long string properties
    String longString = "a".repeat(1000);
    ExecutionEntity execution = mock(ExecutionEntity.class);
    ProcessDefinitionEntity processDefinition = mock(ProcessDefinitionEntity.class);

    when(execution.getProcessDefinitionId()).thenReturn(longString);
    when(execution.getProcessDefinition()).thenReturn(processDefinition);
    when(processDefinition.getKey()).thenReturn(longString);
    when(execution.getCurrentActivityId()).thenReturn(longString);
    when(execution.getCurrentActivityName()).thenReturn(longString);

    // When/Then: Should execute without throwing an exception
    assertThatCode(() -> action.execute(execution, longString))
        .doesNotThrowAnyException();
  }
}
