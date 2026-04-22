package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricActivityInstanceQuery;
import org.camunda.bpm.engine.history.HistoricProcessInstanceQuery;
import org.camunda.bpm.engine.impl.ProcessDefinitionQueryImpl;
import org.camunda.bpm.engine.runtime.ExecutionQuery;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CamundaMetricsDiffblueTest {
  @InjectMocks private CamundaMetrics camundaMetrics;

  @Mock private HistoryService historyService;

  @Mock private RepositoryService repositoryService;

  @Mock private RuntimeService runtimeService;

  /**
   * Test {@link CamundaMetrics#countDeployedWorkflows()}.
   *
   * <ul>
   *   <li>Given array of {@link String} with {@code Ids}.
   *   <li>Then return zero.
   * </ul>
   *
   * <p>Method under test: {@link CamundaMetrics#countDeployedWorkflows()}
   */
  @Test
  @DisplayName("Test countDeployedWorkflows(); given array of String with 'Ids'; then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long CamundaMetrics.countDeployedWorkflows()"})
  void testCountDeployedWorkflows_givenArrayOfStringWithIds_thenReturnZero() {
    // Arrange
    ProcessDefinitionQueryImpl processDefinitionQueryImpl = new ProcessDefinitionQueryImpl();
    processDefinitionQueryImpl.processDefinitionIdIn("Ids");
    processDefinitionQueryImpl.processDefinitionId("42");
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQueryImpl);

    // Act
    long actualCountDeployedWorkflowsResult = camundaMetrics.countDeployedWorkflows();

    // Assert
    verify(repositoryService).createProcessDefinitionQuery();
    assertEquals(0L, actualCountDeployedWorkflowsResult);
  }

  /**
   * Test {@link CamundaMetrics#countRunningProcesses()}.
   *
   * <p>Method under test: {@link CamundaMetrics#countRunningProcesses()}
   */
  @Test
  @DisplayName("Test countRunningProcesses(); then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long CamundaMetrics.countRunningProcesses()"})
  void testCountRunningProcesses_thenReturnZero() {
    // Arrange
    ProcessInstanceQuery processInstanceQuery = mock(ProcessInstanceQuery.class);
    when(processInstanceQuery.active()).thenReturn(processInstanceQuery);
    when(processInstanceQuery.count()).thenReturn(0L);
    when(runtimeService.createProcessInstanceQuery()).thenReturn(processInstanceQuery);

    // Act
    long actualResult = camundaMetrics.countRunningProcesses();

    // Assert
    verify(runtimeService).createProcessInstanceQuery();
    assertEquals(0L, actualResult);
  }

  /**
   * Test {@link CamundaMetrics#countCompletedProcesses()}.
   *
   * <p>Method under test: {@link CamundaMetrics#countCompletedProcesses()}
   */
  @Test
  @DisplayName("Test countCompletedProcesses(); then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long CamundaMetrics.countCompletedProcesses()"})
  void testCountCompletedProcesses_thenReturnZero() {
    // Arrange
    HistoricProcessInstanceQuery historicProcessInstanceQuery = mock(HistoricProcessInstanceQuery.class);
    when(historicProcessInstanceQuery.finished()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.count()).thenReturn(0L);
    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);

    // Act
    long actualResult = camundaMetrics.countCompletedProcesses();

    // Assert
    verify(historyService).createHistoricProcessInstanceQuery();
    assertEquals(0L, actualResult);
  }

  /**
   * Test {@link CamundaMetrics#countRunningActivities()}.
   *
   * <p>Method under test: {@link CamundaMetrics#countRunningActivities()}
   */
  @Test
  @DisplayName("Test countRunningActivities(); then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long CamundaMetrics.countRunningActivities()"})
  void testCountRunningActivities_thenReturnZero() {
    // Arrange
    ExecutionQuery executionQuery = mock(ExecutionQuery.class);
    when(executionQuery.active()).thenReturn(executionQuery);
    when(executionQuery.count()).thenReturn(0L);
    when(runtimeService.createExecutionQuery()).thenReturn(executionQuery);

    // Act
    long actualResult = camundaMetrics.countRunningActivities();

    // Assert
    verify(runtimeService).createExecutionQuery();
    assertEquals(0L, actualResult);
  }

  /**
   * Test {@link CamundaMetrics#countCompletedActivities()}.
   *
   * <p>Method under test: {@link CamundaMetrics#countCompletedActivities()}
   */
  @Test
  @DisplayName("Test countCompletedActivities(); then return zero")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"long CamundaMetrics.countCompletedActivities()"})
  void testCountCompletedActivities_thenReturnZero() {
    // Arrange
    HistoricActivityInstanceQuery historicActivityInstanceQuery = mock(HistoricActivityInstanceQuery.class);
    when(historicActivityInstanceQuery.activityType("scriptTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.activityType("serviceTask")).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finished()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.count()).thenReturn(0L);
    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);

    // Act
    long actualResult = camundaMetrics.countCompletedActivities();

    // Assert
    verify(historyService).createHistoricActivityInstanceQuery();
    assertEquals(0L, actualResult);
  }
}
