package com.symphony.bdk.workflow.engine.camunda;

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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CamundaMetricsTest {

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
  private ExecutionQuery executionQuery;

  @Mock
  private HistoricProcessInstanceQuery historicProcessInstanceQuery;

  @Mock
  private HistoricActivityInstanceQuery historicActivityInstanceQuery;

  private CamundaMetrics camundaMetrics;

  @BeforeEach
  void setUp() {
    camundaMetrics = new CamundaMetrics(runtimeService, repositoryService, historyService);
  }

  @Test
  void shouldCountDeployedWorkflows() {
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.count()).thenReturn(5L);

    long result = camundaMetrics.countDeployedWorkflows();

    assertThat(result).isEqualTo(5L);
  }

  @Test
  void shouldCountRunningProcesses() {
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(processInstanceQuery.active()).thenReturn(processInstanceQuery);
    when(processInstanceQuery.count()).thenReturn(3L);

    long result = camundaMetrics.countRunningProcesses();

    assertThat(result).isEqualTo(3L);
  }

  @Test
  void shouldCountCompletedProcesses() {
    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.finished()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.count()).thenReturn(10L);

    long result = camundaMetrics.countCompletedProcesses();

    assertThat(result).isEqualTo(10L);
  }

  @Test
  void shouldCountRunningActivities() {
    when(runtimeService.createExecutionQuery()).thenReturn(executionQuery);
    when(executionQuery.active()).thenReturn(executionQuery);
    when(executionQuery.count()).thenReturn(7L);

    long result = camundaMetrics.countRunningActivities();

    assertThat(result).isEqualTo(7L);
  }

  @Test
  void shouldCountCompletedActivities() {
    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("scriptTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("serviceTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finished()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.count()).thenReturn(15L);

    long result = camundaMetrics.countCompletedActivities();

    assertThat(result).isEqualTo(15L);
  }
}
