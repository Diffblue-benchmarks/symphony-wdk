package com.symphony.bdk.workflow.engine.camunda;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricActivityInstanceQuery;
import org.camunda.bpm.engine.history.HistoricProcessInstanceQuery;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CamundaMetricsClaudeTest {

  @Test
  void countDeployedWorkflows_withZeroWorkflows_shouldReturnZero() {
    // Given: Repository service with no deployed workflows
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.count()).thenReturn(0L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countDeployedWorkflows is called
    long result = metrics.countDeployedWorkflows();

    // Then: Should return 0
    assertThat(result).isEqualTo(0L);
    verify(repositoryService).createProcessDefinitionQuery();
    verify(processDefinitionQuery).active();
    verify(processDefinitionQuery).count();
  }

  @Test
  void countDeployedWorkflows_withOneWorkflow_shouldReturnOne() {
    // Given: Repository service with one deployed workflow
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.count()).thenReturn(1L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countDeployedWorkflows is called
    long result = metrics.countDeployedWorkflows();

    // Then: Should return 1
    assertThat(result).isEqualTo(1L);
  }

  @Test
  void countDeployedWorkflows_withMultipleWorkflows_shouldReturnCorrectCount() {
    // Given: Repository service with multiple deployed workflows
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.count()).thenReturn(5L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countDeployedWorkflows is called
    long result = metrics.countDeployedWorkflows();

    // Then: Should return 5
    assertThat(result).isEqualTo(5L);
  }

  @Test
  void countRunningProcesses_withZeroProcesses_shouldReturnZero() {
    // Given: Runtime service with no running processes
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);

    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(processInstanceQuery.active()).thenReturn(processInstanceQuery);
    when(processInstanceQuery.count()).thenReturn(0L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countRunningProcesses is called
    long result = metrics.countRunningProcesses();

    // Then: Should return 0
    assertThat(result).isEqualTo(0L);
    verify(runtimeService).createProcessInstanceQuery();
    verify(processInstanceQuery).active();
    verify(processInstanceQuery).count();
  }

  @Test
  void countRunningProcesses_withOneProcess_shouldReturnOne() {
    // Given: Runtime service with one running process
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);

    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(processInstanceQuery.active()).thenReturn(processInstanceQuery);
    when(processInstanceQuery.count()).thenReturn(1L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countRunningProcesses is called
    long result = metrics.countRunningProcesses();

    // Then: Should return 1
    assertThat(result).isEqualTo(1L);
  }

  @Test
  void countRunningProcesses_withMultipleProcesses_shouldReturnCorrectCount() {
    // Given: Runtime service with multiple running processes
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);

    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(processInstanceQuery.active()).thenReturn(processInstanceQuery);
    when(processInstanceQuery.count()).thenReturn(10L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countRunningProcesses is called
    long result = metrics.countRunningProcesses();

    // Then: Should return 10
    assertThat(result).isEqualTo(10L);
  }

  @Test
  void countCompletedProcesses_withZeroProcesses_shouldReturnZero() {
    // Given: History service with no completed processes
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    HistoricProcessInstanceQuery historicProcessInstanceQuery = mock(HistoricProcessInstanceQuery.class);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.finished()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.count()).thenReturn(0L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countCompletedProcesses is called
    long result = metrics.countCompletedProcesses();

    // Then: Should return 0
    assertThat(result).isEqualTo(0L);
    verify(historyService).createHistoricProcessInstanceQuery();
    verify(historicProcessInstanceQuery).finished();
    verify(historicProcessInstanceQuery).count();
  }

  @Test
  void countCompletedProcesses_withOneProcess_shouldReturnOne() {
    // Given: History service with one completed process
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    HistoricProcessInstanceQuery historicProcessInstanceQuery = mock(HistoricProcessInstanceQuery.class);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.finished()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.count()).thenReturn(1L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countCompletedProcesses is called
    long result = metrics.countCompletedProcesses();

    // Then: Should return 1
    assertThat(result).isEqualTo(1L);
  }

  @Test
  void countCompletedProcesses_withMultipleProcesses_shouldReturnCorrectCount() {
    // Given: History service with multiple completed processes
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    HistoricProcessInstanceQuery historicProcessInstanceQuery = mock(HistoricProcessInstanceQuery.class);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.finished()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.count()).thenReturn(15L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countCompletedProcesses is called
    long result = metrics.countCompletedProcesses();

    // Then: Should return 15
    assertThat(result).isEqualTo(15L);
  }

  @Test
  void countRunningActivities_withZeroActivities_shouldReturnZero() {
    // Given: Runtime service with no running activities
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    ExecutionQuery executionQuery = mock(ExecutionQuery.class);

    when(runtimeService.createExecutionQuery()).thenReturn(executionQuery);
    when(executionQuery.active()).thenReturn(executionQuery);
    when(executionQuery.count()).thenReturn(0L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countRunningActivities is called
    long result = metrics.countRunningActivities();

    // Then: Should return 0
    assertThat(result).isEqualTo(0L);
    verify(runtimeService).createExecutionQuery();
    verify(executionQuery).active();
    verify(executionQuery).count();
  }

  @Test
  void countRunningActivities_withOneActivity_shouldReturnOne() {
    // Given: Runtime service with one running activity
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    ExecutionQuery executionQuery = mock(ExecutionQuery.class);

    when(runtimeService.createExecutionQuery()).thenReturn(executionQuery);
    when(executionQuery.active()).thenReturn(executionQuery);
    when(executionQuery.count()).thenReturn(1L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countRunningActivities is called
    long result = metrics.countRunningActivities();

    // Then: Should return 1
    assertThat(result).isEqualTo(1L);
  }

  @Test
  void countRunningActivities_withMultipleActivities_shouldReturnCorrectCount() {
    // Given: Runtime service with multiple running activities
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    ExecutionQuery executionQuery = mock(ExecutionQuery.class);

    when(runtimeService.createExecutionQuery()).thenReturn(executionQuery);
    when(executionQuery.active()).thenReturn(executionQuery);
    when(executionQuery.count()).thenReturn(20L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countRunningActivities is called
    long result = metrics.countRunningActivities();

    // Then: Should return 20
    assertThat(result).isEqualTo(20L);
  }

  @Test
  void countCompletedActivities_withZeroActivities_shouldReturnZero() {
    // Given: History service with no completed activities
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    HistoricActivityInstanceQuery historicActivityInstanceQuery = mock(HistoricActivityInstanceQuery.class);

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("scriptTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("serviceTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finished()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.count()).thenReturn(0L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countCompletedActivities is called
    long result = metrics.countCompletedActivities();

    // Then: Should return 0
    assertThat(result).isEqualTo(0L);
    verify(historyService).createHistoricActivityInstanceQuery();
    verify(historicActivityInstanceQuery).activityType("scriptTask");
    verify(historicActivityInstanceQuery).activityType("serviceTask");
    verify(historicActivityInstanceQuery).finished();
    verify(historicActivityInstanceQuery).count();
  }

  @Test
  void countCompletedActivities_withOneActivity_shouldReturnOne() {
    // Given: History service with one completed activity
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    HistoricActivityInstanceQuery historicActivityInstanceQuery = mock(HistoricActivityInstanceQuery.class);

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("scriptTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("serviceTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finished()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.count()).thenReturn(1L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countCompletedActivities is called
    long result = metrics.countCompletedActivities();

    // Then: Should return 1
    assertThat(result).isEqualTo(1L);
  }

  @Test
  void countCompletedActivities_withMultipleActivities_shouldReturnCorrectCount() {
    // Given: History service with multiple completed activities
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    HistoricActivityInstanceQuery historicActivityInstanceQuery = mock(HistoricActivityInstanceQuery.class);

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("scriptTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("serviceTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finished()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.count()).thenReturn(25L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countCompletedActivities is called
    long result = metrics.countCompletedActivities();

    // Then: Should return 25
    assertThat(result).isEqualTo(25L);
  }

  @Test
  void countCompletedActivities_shouldFilterByScriptTaskAndServiceTaskOnly() {
    // Given: History service with completed activities
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    HistoricActivityInstanceQuery historicActivityInstanceQuery = mock(HistoricActivityInstanceQuery.class);

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("scriptTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("serviceTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finished()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.count()).thenReturn(10L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When: countCompletedActivities is called
    long result = metrics.countCompletedActivities();

    // Then: Should filter by scriptTask and serviceTask only (not other activity types like events)
    verify(historicActivityInstanceQuery).activityType("scriptTask");
    verify(historicActivityInstanceQuery).activityType("serviceTask");
    assertThat(result).isEqualTo(10L);
  }

  @Test
  void constructor_shouldAcceptAllThreeServices() {
    // Given: All three required services
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);

    // When: CamundaMetrics is instantiated
    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // Then: Instance should be created successfully
    assertThat(metrics).isNotNull();
  }

  @Test
  void allMethods_shouldReturnLongType() {
    // Given: All services properly mocked
    RuntimeService runtimeService = mock(RuntimeService.class);
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);

    ProcessDefinitionQuery processDefinitionQuery = mock(ProcessDefinitionQuery.class);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.active()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.count()).thenReturn(1L);

    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);
    when(processInstanceQuery.active()).thenReturn(processInstanceQuery);
    when(processInstanceQuery.count()).thenReturn(2L);

    HistoricProcessInstanceQuery historicProcessInstanceQuery = mock(HistoricProcessInstanceQuery.class);
    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.finished()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.count()).thenReturn(3L);

    ExecutionQuery executionQuery = mock(ExecutionQuery.class);
    when(runtimeService.createExecutionQuery()).thenReturn(executionQuery);
    when(executionQuery.active()).thenReturn(executionQuery);
    when(executionQuery.count()).thenReturn(4L);

    HistoricActivityInstanceQuery historicActivityInstanceQuery = mock(HistoricActivityInstanceQuery.class);
    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("scriptTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("serviceTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finished()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.count()).thenReturn(5L);

    CamundaMetrics metrics = new CamundaMetrics(runtimeService, repositoryService, historyService);

    // When/Then: All methods should return long values
    assertThat(metrics.countDeployedWorkflows()).isInstanceOf(Long.class);
    assertThat(metrics.countRunningProcesses()).isInstanceOf(Long.class);
    assertThat(metrics.countCompletedProcesses()).isInstanceOf(Long.class);
    assertThat(metrics.countRunningActivities()).isInstanceOf(Long.class);
    assertThat(metrics.countCompletedActivities()).isInstanceOf(Long.class);
  }
}
