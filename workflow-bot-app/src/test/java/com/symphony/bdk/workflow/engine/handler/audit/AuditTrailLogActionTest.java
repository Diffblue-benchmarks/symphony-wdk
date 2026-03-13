package com.symphony.bdk.workflow.engine.handler.audit;

import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.history.event.HistoricActivityInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricJobLogEvent;
import org.camunda.bpm.engine.impl.history.event.HistoricProcessInstanceEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoricVariableUpdateEventEntity;
import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.camunda.bpm.engine.impl.persistence.entity.DeploymentEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ExecutionEntity;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.repository.Deployment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditTrailLogActionTest {

    @Mock
    private HistoricJobLogEvent jobLogEvent;

    @Mock
    private HistoricProcessInstanceEventEntity processEvent;

    @Mock
    private HistoricActivityInstanceEventEntity activityEvent;

    @Mock
    private HistoricVariableUpdateEventEntity variableEvent;

    @Mock
    private DelegateExecution execution;

    @Mock
    private ExecutionEntity executionEntity;

    @Mock
    private ProcessDefinitionEntity processDefinition;

    @Mock
    private DeploymentEntity deployment;

    private AuditTrailLogAction auditTrailLogAction;
    private ListAppender<ILoggingEvent> logAppender;
    private Logger logger;

    @BeforeEach
    void setUp() {
        // Arrange
        auditTrailLogAction = new AuditTrailLogAction();

        logger = (Logger) LoggerFactory.getLogger("audit-trail");
        logAppender = new ListAppender<>();
        logAppender.start();
        logger.addAppender(logAppender);
        logger.setLevel(Level.INFO);
    }

    @AfterEach
    void tearDown() {
        logger.detachAppender(logAppender);
    }

    @Test
    void executeWithHistoricJobLogEventShouldLogJobEvent() {
        // Arrange
        when(jobLogEvent.getJobId()).thenReturn("job123");
        when(jobLogEvent.getJobDefinitionType()).thenReturn("timer");
        when(jobLogEvent.getProcessInstanceId()).thenReturn("process456");
        when(jobLogEvent.getProcessDefinitionKey()).thenReturn("processKey");
        when(jobLogEvent.getActivityId()).thenReturn("activity789");

        // Act
        auditTrailLogAction.execute((HistoryEvent) jobLogEvent);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        ILoggingEvent logEvent = logAppender.list.get(0);
        assertThat(logEvent.getLevel()).isEqualTo(Level.INFO);
        assertThat(logEvent.getFormattedMessage()).contains("job123", "timer", "process456", "processKey", "activity789");
    }

    @Test
    void executeWithHistoricProcessInstanceEventShouldLogProcessEventWithoutDuration() {
        // Arrange
        when(processEvent.getDurationInMillis()).thenReturn(null);
        when(processEvent.getEventType()).thenReturn("start");
        when(processEvent.getProcessInstanceId()).thenReturn("process123");
        when(processEvent.getProcessDefinitionKey()).thenReturn("processKey456");

        // Act
        auditTrailLogAction.execute((HistoryEvent) processEvent);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        ILoggingEvent logEvent = logAppender.list.get(0);
        assertThat(logEvent.getLevel()).isEqualTo(Level.INFO);
        assertThat(logEvent.getFormattedMessage()).contains("start_process", "process123", "processKey456");
        assertThat(logEvent.getFormattedMessage()).doesNotContain("duration");
    }

    @Test
    void executeWithHistoricProcessInstanceEventShouldLogProcessEventWithDuration() {
        // Arrange
        when(processEvent.getDurationInMillis()).thenReturn(5000L);
        when(processEvent.getEventType()).thenReturn("end");
        when(processEvent.getProcessInstanceId()).thenReturn("process789");
        when(processEvent.getProcessDefinitionKey()).thenReturn("processKeyEnd");

        // Act
        auditTrailLogAction.execute((HistoryEvent) processEvent);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        ILoggingEvent logEvent = logAppender.list.get(0);
        assertThat(logEvent.getLevel()).isEqualTo(Level.INFO);
        assertThat(logEvent.getFormattedMessage()).contains("end_process", "process789", "processKeyEnd", "5000");
    }

    @Test
    void executeWithHistoricActivityInstanceEventShouldLogActivityEventWithoutDuration() {
        // Arrange
        when(activityEvent.getDurationInMillis()).thenReturn(null);
        when(activityEvent.getEventType()).thenReturn("start");
        when(activityEvent.getProcessInstanceId()).thenReturn("process111");
        when(activityEvent.getProcessDefinitionKey()).thenReturn("processKey222");
        when(activityEvent.getActivityId()).thenReturn("activity333");
        when(activityEvent.getActivityName()).thenReturn("TestActivity");

        // Act
        auditTrailLogAction.execute((HistoryEvent) activityEvent);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        ILoggingEvent logEvent = logAppender.list.get(0);
        assertThat(logEvent.getLevel()).isEqualTo(Level.INFO);
        assertThat(logEvent.getFormattedMessage()).contains("start_activity", "process111", "processKey222", "activity333", "TestActivity");
        assertThat(logEvent.getFormattedMessage()).doesNotContain("duration");
    }

    @Test
    void executeWithHistoricActivityInstanceEventShouldLogActivityEventWithDuration() {
        // Arrange
        when(activityEvent.getDurationInMillis()).thenReturn(3000L);
        when(activityEvent.getEventType()).thenReturn("end");
        when(activityEvent.getProcessInstanceId()).thenReturn("process444");
        when(activityEvent.getProcessDefinitionKey()).thenReturn("processKey555");
        when(activityEvent.getActivityId()).thenReturn("activity666");
        when(activityEvent.getActivityName()).thenReturn("CompleteActivity");

        // Act
        auditTrailLogAction.execute((HistoryEvent) activityEvent);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        ILoggingEvent logEvent = logAppender.list.get(0);
        assertThat(logEvent.getLevel()).isEqualTo(Level.INFO);
        assertThat(logEvent.getFormattedMessage()).contains("end_activity", "process444", "processKey555", "activity666", "CompleteActivity", "3000");
    }

    @Test
    void executeWithHistoricVariableUpdateEventShouldLogVariableEventWhenInitiator() {
        // Arrange
        when(variableEvent.getVariableName()).thenReturn(ActivityExecutorContext.INITIATOR);
        when(variableEvent.getLongValue()).thenReturn(12345L);
        when(variableEvent.getProcessInstanceId()).thenReturn("process999");
        when(variableEvent.getProcessDefinitionKey()).thenReturn("processKey000");

        // Act
        auditTrailLogAction.execute((HistoryEvent) variableEvent);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        ILoggingEvent logEvent = logAppender.list.get(0);
        assertThat(logEvent.getLevel()).isEqualTo(Level.INFO);
        assertThat(logEvent.getFormattedMessage()).contains("12345", "process999", "processKey000");
    }

    @Test
    void executeWithHistoricVariableUpdateEventShouldNotLogWhenNotInitiator() {
        // Arrange
        when(variableEvent.getVariableName()).thenReturn("someOtherVariable");

        // Act
        auditTrailLogAction.execute((HistoryEvent) variableEvent);

        // Assert
        assertThat(logAppender.list).isEmpty();
    }

    @Test
    void executeWithHistoricVariableUpdateEventShouldNotLogWhenLongValueIsNull() {
        // Arrange
        when(variableEvent.getVariableName()).thenReturn(ActivityExecutorContext.INITIATOR);
        when(variableEvent.getLongValue()).thenReturn(null);

        // Act
        auditTrailLogAction.execute((HistoryEvent) variableEvent);

        // Assert
        assertThat(logAppender.list).isEmpty();
    }

    @Test
    void executeWithUnknownHistoryEventShouldLogTrace() {
        // Arrange
        logger.setLevel(Level.TRACE);
        HistoryEvent unknownEvent = mock(HistoryEvent.class);

        // Act
        auditTrailLogAction.execute(unknownEvent);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        ILoggingEvent logEvent = logAppender.list.get(0);
        assertThat(logEvent.getLevel()).isEqualTo(Level.TRACE);
    }

    @Test
    void executeWithDelegateExecutionShouldLogActivityExecution() {
        // Arrange
        when(executionEntity.getProcessDefinitionId()).thenReturn("procDef123");
        when(executionEntity.getCurrentActivityId()).thenReturn("currentActivity456");
        when(executionEntity.getCurrentActivityName()).thenReturn("CurrentActivityName");
        when(executionEntity.getProcessDefinition()).thenReturn(processDefinition);
        when(processDefinition.getKey()).thenReturn("processKey789");

        // Act
        auditTrailLogAction.execute((DelegateExecution) executionEntity, "serviceTask");

        // Assert
        assertThat(logAppender.list).hasSize(1);
        ILoggingEvent logEvent = logAppender.list.get(0);
        assertThat(logEvent.getLevel()).isEqualTo(Level.INFO);
        assertThat(logEvent.getFormattedMessage()).contains("execute_activity", "procDef123", "processKey789", "currentActivity456", "CurrentActivityName", "serviceTask");
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void deployedShouldLogDeploymentEvent() {
        // Arrange
        when(deployment.getId()).thenReturn("deploy123");
        when(deployment.getName()).thenReturn("MyDeployment");

        Map<Class<?>, List> artifacts = new HashMap<>();
        List processDefinitions = new ArrayList<>();
        ProcessDefinitionEntity procDef = mock(ProcessDefinitionEntity.class);
        when(procDef.getKey()).thenReturn("myProcessKey");
        processDefinitions.add(procDef);
        artifacts.put(ProcessDefinitionEntity.class, processDefinitions);

        when(((DeploymentEntity) deployment).getDeployedArtifacts()).thenReturn(artifacts);

        // Act
        auditTrailLogAction.deployed((Deployment) deployment);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        ILoggingEvent logEvent = logAppender.list.get(0);
        assertThat(logEvent.getLevel()).isEqualTo(Level.INFO);
        assertThat(logEvent.getFormattedMessage()).contains("deploy_workflow", "deploy123", "MyDeployment", "myProcessKey");
    }

    @Test
    void deployedWithNonDeploymentEntityShouldLogWithEmptyProcessKey() {
        // Arrange
        Deployment simpleDeployment = mock(Deployment.class);
        when(simpleDeployment.getId()).thenReturn("deploy456");
        when(simpleDeployment.getName()).thenReturn("SimpleDeployment");

        // Act
        auditTrailLogAction.deployed(simpleDeployment);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        ILoggingEvent logEvent = logAppender.list.get(0);
        assertThat(logEvent.getLevel()).isEqualTo(Level.INFO);
        assertThat(logEvent.getFormattedMessage()).contains("deploy_workflow", "deploy456", "SimpleDeployment");
    }

    @Test
    void undeployedShouldLogUndeploymentEvent() {
        // Arrange
        when(deployment.getId()).thenReturn("undeploy789");
        when(deployment.getName()).thenReturn("MyUndeployment");

        // Act
        auditTrailLogAction.undeployed(deployment);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        ILoggingEvent logEvent = logAppender.list.get(0);
        assertThat(logEvent.getLevel()).isEqualTo(Level.INFO);
        assertThat(logEvent.getFormattedMessage()).contains("undeploy_workflow", "undeploy789", "MyUndeployment");
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void getProcessKeyShouldReturnProcessKeyWhenDeploymentEntityWithArtifacts() {
        // Arrange
        Map<Class<?>, List> artifacts = new HashMap<>();
        List processDefinitions = new ArrayList<>();
        ProcessDefinitionEntity procDef = mock(ProcessDefinitionEntity.class);
        when(procDef.getKey()).thenReturn("testProcessKey");
        processDefinitions.add(procDef);
        artifacts.put(ProcessDefinitionEntity.class, processDefinitions);

        when(deployment.getDeployedArtifacts()).thenReturn(artifacts);
        when(deployment.getId()).thenReturn("deploy001");
        when(deployment.getName()).thenReturn("TestDeployment");

        // Act
        auditTrailLogAction.deployed((Deployment) deployment);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        assertThat(logAppender.list.get(0).getFormattedMessage()).contains("testProcessKey");
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void getProcessKeyShouldReturnEmptyStringWhenArtifactsIsNull() {
        // Arrange
        Map<Class<?>, List> artifacts = new HashMap<>();
        artifacts.put(ProcessDefinitionEntity.class, null);

        when(deployment.getDeployedArtifacts()).thenReturn(artifacts);
        when(deployment.getId()).thenReturn("deploy002");
        when(deployment.getName()).thenReturn("EmptyArtifactsDeployment");

        // Act
        auditTrailLogAction.deployed((Deployment) deployment);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        assertThat(logAppender.list.get(0).getFormattedMessage()).contains("deploy002", "EmptyArtifactsDeployment");
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void getProcessKeyShouldReturnEmptyStringWhenArtifactsIsEmpty() {
        // Arrange
        Map<Class<?>, List> artifacts = new HashMap<>();
        List emptyList = new ArrayList<>();
        artifacts.put(ProcessDefinitionEntity.class, emptyList);

        when(deployment.getDeployedArtifacts()).thenReturn(artifacts);
        when(deployment.getId()).thenReturn("deploy003");
        when(deployment.getName()).thenReturn("EmptyListDeployment");

        // Act
        auditTrailLogAction.deployed((Deployment) deployment);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        assertThat(logAppender.list.get(0).getFormattedMessage()).contains("deploy003", "EmptyListDeployment");
    }

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    void getProcessKeyShouldReturnEmptyStringWhenArtifactIsNotProcessDefinitionEntity() {
        // Arrange
        Map<Class<?>, List> artifacts = new HashMap<>();
        List invalidArtifacts = new ArrayList<>();
        invalidArtifacts.add("NotAProcessDefinition");
        artifacts.put(ProcessDefinitionEntity.class, invalidArtifacts);

        when(deployment.getDeployedArtifacts()).thenReturn(artifacts);
        when(deployment.getId()).thenReturn("deploy004");
        when(deployment.getName()).thenReturn("InvalidArtifactDeployment");

        // Act
        auditTrailLogAction.deployed((Deployment) deployment);

        // Assert
        assertThat(logAppender.list).hasSize(1);
        assertThat(logAppender.list.get(0).getFormattedMessage()).contains("deploy004", "InvalidArtifactDeployment");
    }
}
