package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstanceQuery;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WorkflowInstCmdaApiQueryRepositoryTest {

  private RepositoryService repositoryService;
  private HistoryService historyService;
  private RuntimeService runtimeService;
  private ObjectConverter objectConverter;
  private WorkflowInstCmdaApiQueryRepository repository;

  private HistoricProcessInstanceQuery historicProcessInstanceQuery;
  private ProcessDefinitionQuery processDefinitionQuery;

  @BeforeEach
  void setUp() {
    repositoryService = mock(RepositoryService.class);
    historyService = mock(HistoryService.class);
    runtimeService = mock(RuntimeService.class);
    objectConverter = mock(ObjectConverter.class);

    repository =
        new WorkflowInstCmdaApiQueryRepository(repositoryService, historyService, runtimeService, objectConverter);

    historicProcessInstanceQuery = mock(HistoricProcessInstanceQuery.class);
    processDefinitionQuery = mock(ProcessDefinitionQuery.class);

    when(historicProcessInstanceQuery.processDefinitionKey(any())).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.orderByProcessInstanceStartTime()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.asc()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.finished()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.unfinished()).thenReturn(historicProcessInstanceQuery);

    when(processDefinitionQuery.processDefinitionKey(any())).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.versionTag(any())).thenReturn(processDefinitionQuery);
  }

  // ---- findAllById ----

  @Test
  void shouldFindAllByIdWhenNoVersionFilter() {
    // given
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = List.of(instance);
    List<WorkflowInstanceDomain> expected = List.of(mock(WorkflowInstanceDomain.class));

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.list()).thenReturn(instances);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(eq(instances), eq(WorkflowInstanceDomain.class))).thenReturn(expected);

    // when
    List<WorkflowInstanceDomain> result = repository.findAllById("workflow-id");

    // then
    assertThat(result).isEqualTo(expected);
  }

  // ---- findAllByIdAndVersion ----

  @Test
  void shouldFindAllByIdAndVersionWhenVersionIsNull() {
    // given
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = List.of(instance);
    List<WorkflowInstanceDomain> expected = List.of(mock(WorkflowInstanceDomain.class));

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.list()).thenReturn(instances);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(eq(instances), eq(WorkflowInstanceDomain.class))).thenReturn(expected);

    // when
    List<WorkflowInstanceDomain> result = repository.findAllByIdAndVersion("workflow-id", null);

    // then
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void shouldFindAllByIdAndVersionWhenVersionIsProvided() {
    // given
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = List.of(instance);
    List<WorkflowInstanceDomain> expected = List.of(mock(WorkflowInstanceDomain.class));

    ProcessDefinition definition = mock(ProcessDefinition.class);
    when(definition.getId()).thenReturn("proc-def-id");
    when(definition.getVersionTag()).thenReturn("v1");

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.list()).thenReturn(instances);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(List.of(definition));
    when(objectConverter.convertCollection(any(), any(java.util.Map.class), eq(WorkflowInstanceDomain.class))).thenReturn(
        expected);

    // when
    List<WorkflowInstanceDomain> result = repository.findAllByIdAndVersion("workflow-id", "v1");

    // then
    assertThat(result).isEqualTo(expected);
    verify(processDefinitionQuery).versionTag("v1");
  }

  // ---- findAllByIdAndStatus ----

  @Test
  void shouldFindAllByIdAndStatusDelegatingToFindAllByIdAndStatusAndVersion() {
    // given
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    when(instance.getEndActivityId()).thenReturn("endEvent_1");
    List<HistoricProcessInstance> instances = List.of(instance);
    List<WorkflowInstanceDomain> expected = List.of(mock(WorkflowInstanceDomain.class));

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.list()).thenReturn(instances);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(WorkflowInstanceDomain.class))).thenReturn(expected);

    // when
    List<WorkflowInstanceDomain> result = repository.findAllByIdAndStatus("workflow-id", StatusEnum.COMPLETED);

    // then
    assertThat(result).isEqualTo(expected);
  }

  // ---- findAllByIdAndStatusAndVersion ----

  @Test
  void shouldFindAllByIdAndStatusAndVersionWithCompletedStatus() {
    // given
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    when(instance.getEndActivityId()).thenReturn("endEvent_1");
    List<HistoricProcessInstance> instances = List.of(instance);
    List<WorkflowInstanceDomain> expected = List.of(mock(WorkflowInstanceDomain.class));

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.list()).thenReturn(instances);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(WorkflowInstanceDomain.class))).thenReturn(expected);

    // when
    List<WorkflowInstanceDomain> result =
        repository.findAllByIdAndStatusAndVersion("workflow-id", StatusEnum.COMPLETED, null);

    // then
    assertThat(result).isEqualTo(expected);
  }

  // ---- queryByStatus (COMPLETED) ----

  @Test
  void shouldQueryByStatusCompletedAndReturnInstancesWithEndEventActivity() {
    // given
    HistoricProcessInstance completedInstance = mock(HistoricProcessInstance.class);
    when(completedInstance.getEndActivityId()).thenReturn("endEvent_finish");

    HistoricProcessInstance failedInstance = mock(HistoricProcessInstance.class);
    when(failedInstance.getEndActivityId()).thenReturn("errorBoundary");

    List<HistoricProcessInstance> instances = List.of(completedInstance, failedInstance);
    List<WorkflowInstanceDomain> expected = List.of(mock(WorkflowInstanceDomain.class));

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.list()).thenReturn(instances);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(WorkflowInstanceDomain.class))).thenReturn(expected);

    // when
    List<WorkflowInstanceDomain> result =
        repository.findAllByIdAndStatusAndVersion("workflow-id", StatusEnum.COMPLETED, null);

    // then
    assertThat(result).isEqualTo(expected);
    verify(historicProcessInstanceQuery).finished();
  }

  @Test
  void shouldQueryByStatusCompletedAndExcludeInstancesWithNullEndActivityId() {
    // given
    HistoricProcessInstance instanceWithNullEndActivity = mock(HistoricProcessInstance.class);
    when(instanceWithNullEndActivity.getEndActivityId()).thenReturn(null);

    List<HistoricProcessInstance> instances = List.of(instanceWithNullEndActivity);
    List<WorkflowInstanceDomain> expected = List.of();

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.list()).thenReturn(instances);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(WorkflowInstanceDomain.class))).thenReturn(expected);

    // when
    List<WorkflowInstanceDomain> result =
        repository.findAllByIdAndStatusAndVersion("workflow-id", StatusEnum.COMPLETED, null);

    // then
    assertThat(result).isEqualTo(expected);
  }

  // ---- queryByStatus (FAILED) ----

  @Test
  void shouldQueryByStatusFailedAndReturnInstancesWithoutEndEventActivity() {
    // given
    HistoricProcessInstance failedInstance = mock(HistoricProcessInstance.class);
    when(failedInstance.getEndActivityId()).thenReturn("errorBoundary");

    HistoricProcessInstance completedInstance = mock(HistoricProcessInstance.class);
    when(completedInstance.getEndActivityId()).thenReturn("endEvent_finish");

    List<HistoricProcessInstance> instances = List.of(failedInstance, completedInstance);
    List<WorkflowInstanceDomain> expected = List.of(mock(WorkflowInstanceDomain.class));

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.list()).thenReturn(instances);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(WorkflowInstanceDomain.class))).thenReturn(expected);

    // when
    List<WorkflowInstanceDomain> result =
        repository.findAllByIdAndStatusAndVersion("workflow-id", StatusEnum.FAILED, null);

    // then
    assertThat(result).isEqualTo(expected);
    verify(historicProcessInstanceQuery).finished();
  }

  @Test
  void shouldQueryByStatusFailedAndExcludeInstancesWithNullEndActivityId() {
    // given
    HistoricProcessInstance instanceWithNullEndActivity = mock(HistoricProcessInstance.class);
    when(instanceWithNullEndActivity.getEndActivityId()).thenReturn(null);

    List<HistoricProcessInstance> instances = List.of(instanceWithNullEndActivity);
    List<WorkflowInstanceDomain> expected = List.of();

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.list()).thenReturn(instances);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(WorkflowInstanceDomain.class))).thenReturn(expected);

    // when
    List<WorkflowInstanceDomain> result =
        repository.findAllByIdAndStatusAndVersion("workflow-id", StatusEnum.FAILED, null);

    // then
    assertThat(result).isEqualTo(expected);
  }

  // ---- queryByStatus (PENDING) ----

  @Test
  void shouldQueryByStatusPendingAndReturnUnfinishedInstances() {
    // given
    HistoricProcessInstance pendingInstance = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = List.of(pendingInstance);
    List<WorkflowInstanceDomain> expected = List.of(mock(WorkflowInstanceDomain.class));

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.list()).thenReturn(instances);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(WorkflowInstanceDomain.class))).thenReturn(expected);

    // when
    List<WorkflowInstanceDomain> result =
        repository.findAllByIdAndStatusAndVersion("workflow-id", StatusEnum.PENDING, null);

    // then
    assertThat(result).isEqualTo(expected);
    verify(historicProcessInstanceQuery).unfinished();
  }

  // ---- getProcessIdVersionMap with blank versionTag ----

  @Test
  void shouldFilterOutDefinitionsWithBlankVersionTag() {
    // given
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = List.of(instance);
    List<WorkflowInstanceDomain> expected = List.of(mock(WorkflowInstanceDomain.class));

    ProcessDefinition defWithBlankTag = mock(ProcessDefinition.class);
    when(defWithBlankTag.getId()).thenReturn("proc-def-id");
    when(defWithBlankTag.getVersionTag()).thenReturn("");

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(historicProcessInstanceQuery);
    when(historicProcessInstanceQuery.list()).thenReturn(instances);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(List.of(defWithBlankTag));
    when(objectConverter.convertCollection(eq(instances), eq(WorkflowInstanceDomain.class))).thenReturn(expected);

    // when
    List<WorkflowInstanceDomain> result = repository.findAllByIdAndVersion("workflow-id", null);

    // then
    assertThat(result).isEqualTo(expected);
  }
}
