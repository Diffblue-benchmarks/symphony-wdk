package com.symphony.bdk.workflow.engine.camunda;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricActivityInstanceQuery;
import org.camunda.bpm.engine.history.HistoricProcessInstanceQuery;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CamundaMetricsTest {

  private CamundaMetrics camundaMetrics;

  @Mock
  private RuntimeService runtimeService;

  @Mock
  private RepositoryService repositoryService;

  @Mock
  private HistoryService historyService;

  @Mock
  private ProcessDefinitionQuery processDefinitionQuery;

  @Mock
  private ProcessInstanceQuery processInstanceQuery;

  @Mock
  private HistoricProcessInstanceQuery historicProcessInstanceQuery;

  @Mock
  private ExecutionQuery executionQuery;

  @Mock
  private HistoricActivityInstanceQuery historicActivityInstanceQuery;

  @BeforeEach
  void setUp() {
    camundaMetrics = new CamundaMetrics(runtimeService, repositoryService, historyService);
  }

  @Test
  void countDeployedWorkflows_shouldReturnCountOfActiveProcessDefinitions() {
    // Arrange
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.count()).thenReturn(5L);

    // Act
    long result = camundaMetrics.countDeployedWorkflows();

    // Assert
    assertThat(result).isEqualTo(5L);
  }

  @Test
  void countRunningProcesses_shouldReturnCountOfActiveProcessInstances() {
    // Arrange
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(processInstanceQuery.active()).thenReturn(processInstanceQuery);
    when(processInstanceQuery.count()).thenReturn(3L);

    // Act
    long result = camundaMetrics.countRunningProcesses();

    // Assert
    assertThat(result).isEqualTo(3L);
  }

  @Test
  void countCompletedProcesses_shouldReturnCountOfFinishedHistoricProcessInstances() {
    // Arrange
    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.finished()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.count()).thenReturn(10L);

    // Act
    long result = camundaMetrics.countCompletedProcesses();

    // Assert
    assertThat(result).isEqualTo(10L);
  }

  @Test
  void countRunningActivities_shouldReturnCountOfActiveExecutions() {
    // Arrange
    when(runtimeService.createExecutionQuery()).thenReturn(executionQuery);
    when(executionQuery.active()).thenReturn(executionQuery);
    when(executionQuery.count()).thenReturn(7L);

    // Act
    long result = camundaMetrics.countRunningActivities();

    // Assert
    assertThat(result).isEqualTo(7L);
  }

  @Test
  void countCompletedActivities_shouldReturnCountOfFinishedScriptAndServiceTasks() {
    // Arrange
    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("scriptTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("serviceTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finished()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.count()).thenReturn(15L);

    // Act
    long result = camundaMetrics.countCompletedActivities();

    // Assert
    assertThat(result).isEqualTo(15L);
  }
}
