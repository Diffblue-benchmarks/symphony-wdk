package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

class WorkflowInstCmdaApiQueryRepositoryTest {

  private WorkflowInstCmdaApiQueryRepository repository;
  private RepositoryService repositoryService;
  private HistoryService historyService;
  private RuntimeService runtimeService;
  private ObjectConverter objectConverter;

  @BeforeEach
  void setUp() {
    repositoryService = mock(RepositoryService.class);
    historyService = mock(HistoryService.class);
    runtimeService = mock(RuntimeService.class);
    objectConverter = mock(ObjectConverter.class);

    repository = new WorkflowInstCmdaApiQueryRepository(
        repositoryService, historyService, runtimeService, objectConverter);
  }

  @Test
  void shouldCreateRepositoryWithAllDependencies() {
    assertThat(repository).isNotNull();
  }

  @Test
  void shouldFindAllByIdDelegateToFindAllByIdAndVersion() {
    String workflowId = "test-workflow";
    HistoricProcessInstanceQuery query = mock(HistoricProcessInstanceQuery.class);
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = Arrays.asList(instance);
    WorkflowInstanceDomain domain = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> expectedDomains = Arrays.asList(domain);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(query);
    when(query.processDefinitionKey(workflowId)).thenReturn(query);
    when(query.orderByProcessInstanceStartTime()).thenReturn(query);
    when(query.asc()).thenReturn(query);
    when(query.list()).thenReturn(instances);

    ProcessDefinitionQuery definitionQuery = mock(ProcessDefinitionQuery.class);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(definitionQuery);
    when(definitionQuery.processDefinitionKey(workflowId)).thenReturn(definitionQuery);
    when(definitionQuery.list()).thenReturn(Collections.emptyList());

    when(objectConverter.convertCollection(instances, WorkflowInstanceDomain.class))
        .thenReturn(expectedDomains);

    List<WorkflowInstanceDomain> result = repository.findAllById(workflowId);

    assertThat(result).isEqualTo(expectedDomains);
    verify(historyService).createHistoricProcessInstanceQuery();
    verify(query).processDefinitionKey(workflowId);
  }

  @Test
  void shouldFindAllByIdAndVersionWithNullVersion() {
    String workflowId = "test-workflow";
    HistoricProcessInstanceQuery query = mock(HistoricProcessInstanceQuery.class);
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = Arrays.asList(instance);
    WorkflowInstanceDomain domain = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> expectedDomains = Arrays.asList(domain);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(query);
    when(query.processDefinitionKey(workflowId)).thenReturn(query);
    when(query.orderByProcessInstanceStartTime()).thenReturn(query);
    when(query.asc()).thenReturn(query);
    when(query.list()).thenReturn(instances);

    ProcessDefinitionQuery definitionQuery = mock(ProcessDefinitionQuery.class);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(definitionQuery);
    when(definitionQuery.processDefinitionKey(workflowId)).thenReturn(definitionQuery);
    when(definitionQuery.list()).thenReturn(Collections.emptyList());

    when(objectConverter.convertCollection(instances, WorkflowInstanceDomain.class))
        .thenReturn(expectedDomains);

    List<WorkflowInstanceDomain> result = repository.findAllByIdAndVersion(workflowId, null);

    assertThat(result).isEqualTo(expectedDomains);
    verify(historyService).createHistoricProcessInstanceQuery();
    verify(query).processDefinitionKey(workflowId);
    verify(query).orderByProcessInstanceStartTime();
    verify(query).asc();
    verify(query).list();
  }

  @Test
  void shouldFindAllByIdAndVersionWithVersion() {
    String workflowId = "test-workflow";
    String version = "1.0";
    HistoricProcessInstanceQuery query = mock(HistoricProcessInstanceQuery.class);
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = Arrays.asList(instance);
    WorkflowInstanceDomain domain = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> expectedDomains = Arrays.asList(domain);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(query);
    when(query.processDefinitionKey(workflowId)).thenReturn(query);
    when(query.orderByProcessInstanceStartTime()).thenReturn(query);
    when(query.asc()).thenReturn(query);
    when(query.list()).thenReturn(instances);

    ProcessDefinitionQuery definitionQuery = mock(ProcessDefinitionQuery.class);
    ProcessDefinition processDefinition = mock(ProcessDefinition.class);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(definitionQuery);
    when(definitionQuery.processDefinitionKey(workflowId)).thenReturn(definitionQuery);
    when(definitionQuery.versionTag(version)).thenReturn(definitionQuery);
    when(definitionQuery.list()).thenReturn(Arrays.asList(processDefinition));
    when(processDefinition.getId()).thenReturn("process-def-123");
    when(processDefinition.getVersionTag()).thenReturn("1.0");

    Map<String, String> processIdVersionMap = Map.of("process-def-123", "1.0");
    when(objectConverter.convertCollection(eq(instances), eq(processIdVersionMap), eq(WorkflowInstanceDomain.class)))
        .thenReturn(expectedDomains);

    List<WorkflowInstanceDomain> result = repository.findAllByIdAndVersion(workflowId, version);

    assertThat(result).isEqualTo(expectedDomains);
    verify(definitionQuery).versionTag(version);
  }

  @Test
  void shouldConvertInstancesWithEmptyProcessIdVersionMap() {
    String workflowId = "test-workflow";
    HistoricProcessInstanceQuery query = mock(HistoricProcessInstanceQuery.class);
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = Arrays.asList(instance);
    WorkflowInstanceDomain domain = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> expectedDomains = Arrays.asList(domain);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(query);
    when(query.processDefinitionKey(workflowId)).thenReturn(query);
    when(query.orderByProcessInstanceStartTime()).thenReturn(query);
    when(query.asc()).thenReturn(query);
    when(query.list()).thenReturn(instances);

    ProcessDefinitionQuery definitionQuery = mock(ProcessDefinitionQuery.class);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(definitionQuery);
    when(definitionQuery.processDefinitionKey(workflowId)).thenReturn(definitionQuery);
    when(definitionQuery.list()).thenReturn(Collections.emptyList());

    when(objectConverter.convertCollection(instances, WorkflowInstanceDomain.class))
        .thenReturn(expectedDomains);

    List<WorkflowInstanceDomain> result = repository.findAllByIdAndVersion(workflowId, null);

    assertThat(result).isEqualTo(expectedDomains);
    verify(objectConverter).convertCollection(instances, WorkflowInstanceDomain.class);
  }

  @Test
  void shouldConvertInstancesWithNonEmptyProcessIdVersionMap() {
    String workflowId = "test-workflow";
    String version = "2.0";
    HistoricProcessInstanceQuery query = mock(HistoricProcessInstanceQuery.class);
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = Arrays.asList(instance);
    WorkflowInstanceDomain domain = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> expectedDomains = Arrays.asList(domain);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(query);
    when(query.processDefinitionKey(workflowId)).thenReturn(query);
    when(query.orderByProcessInstanceStartTime()).thenReturn(query);
    when(query.asc()).thenReturn(query);
    when(query.list()).thenReturn(instances);

    ProcessDefinitionQuery definitionQuery = mock(ProcessDefinitionQuery.class);
    ProcessDefinition processDefinition = mock(ProcessDefinition.class);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(definitionQuery);
    when(definitionQuery.processDefinitionKey(workflowId)).thenReturn(definitionQuery);
    when(definitionQuery.versionTag(version)).thenReturn(definitionQuery);
    when(definitionQuery.list()).thenReturn(Arrays.asList(processDefinition));
    when(processDefinition.getId()).thenReturn("process-def-456");
    when(processDefinition.getVersionTag()).thenReturn("2.0");

    Map<String, String> processIdVersionMap = Map.of("process-def-456", "2.0");
    when(objectConverter.convertCollection(eq(instances), eq(processIdVersionMap), eq(WorkflowInstanceDomain.class)))
        .thenReturn(expectedDomains);

    List<WorkflowInstanceDomain> result = repository.findAllByIdAndVersion(workflowId, version);

    assertThat(result).isEqualTo(expectedDomains);
    verify(objectConverter).convertCollection(eq(instances), eq(processIdVersionMap), eq(WorkflowInstanceDomain.class));
  }

  @Test
  void shouldGetProcessIdVersionMapWithNullVersion() {
    String workflowId = "test-workflow";
    ProcessDefinitionQuery definitionQuery = mock(ProcessDefinitionQuery.class);
    ProcessDefinition processDefinition1 = mock(ProcessDefinition.class);
    ProcessDefinition processDefinition2 = mock(ProcessDefinition.class);

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(definitionQuery);
    when(definitionQuery.processDefinitionKey(workflowId)).thenReturn(definitionQuery);
    when(definitionQuery.list()).thenReturn(Arrays.asList(processDefinition1, processDefinition2));
    when(processDefinition1.getId()).thenReturn("proc-1");
    when(processDefinition1.getVersionTag()).thenReturn("1.0");
    when(processDefinition2.getId()).thenReturn("proc-2");
    when(processDefinition2.getVersionTag()).thenReturn(null);

    HistoricProcessInstanceQuery query = mock(HistoricProcessInstanceQuery.class);
    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(query);
    when(query.processDefinitionKey(workflowId)).thenReturn(query);
    when(query.orderByProcessInstanceStartTime()).thenReturn(query);
    when(query.asc()).thenReturn(query);
    when(query.list()).thenReturn(Collections.emptyList());

    when(objectConverter.convertCollection(any(), any(Map.class), eq(WorkflowInstanceDomain.class)))
        .thenReturn(Collections.emptyList());

    repository.findAllByIdAndVersion(workflowId, null);

    verify(definitionQuery).processDefinitionKey(workflowId);
    verify(definitionQuery).list();
  }

  @Test
  void shouldFindAllByIdAndStatusDelegateToFindAllByIdAndStatusAndVersion() {
    String workflowId = "test-workflow";
    StatusEnum status = StatusEnum.COMPLETED;
    HistoricProcessInstanceQuery query = mock(HistoricProcessInstanceQuery.class);
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = Arrays.asList(instance);
    WorkflowInstanceDomain domain = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> expectedDomains = Arrays.asList(domain);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(query);
    when(query.processDefinitionKey(workflowId)).thenReturn(query);
    when(query.finished()).thenReturn(query);
    when(query.orderByProcessInstanceStartTime()).thenReturn(query);
    when(query.asc()).thenReturn(query);
    when(query.list()).thenReturn(instances);
    when(instance.getEndActivityId()).thenReturn("endEvent_1");

    ProcessDefinitionQuery definitionQuery = mock(ProcessDefinitionQuery.class);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(definitionQuery);
    when(definitionQuery.processDefinitionKey(workflowId)).thenReturn(definitionQuery);
    when(definitionQuery.list()).thenReturn(Collections.emptyList());

    when(objectConverter.convertCollection(any(), eq(WorkflowInstanceDomain.class)))
        .thenReturn(expectedDomains);

    List<WorkflowInstanceDomain> result = repository.findAllByIdAndStatus(workflowId, status);

    assertThat(result).isEqualTo(expectedDomains);
  }

  @Test
  void shouldFindAllByIdAndStatusAndVersionWithCompletedStatus() {
    String workflowId = "test-workflow";
    String version = "1.5";
    StatusEnum status = StatusEnum.COMPLETED;
    HistoricProcessInstanceQuery query = mock(HistoricProcessInstanceQuery.class);
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = Arrays.asList(instance);
    WorkflowInstanceDomain domain = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> expectedDomains = Arrays.asList(domain);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(query);
    when(query.processDefinitionKey(workflowId)).thenReturn(query);
    when(query.finished()).thenReturn(query);
    when(query.orderByProcessInstanceStartTime()).thenReturn(query);
    when(query.asc()).thenReturn(query);
    when(query.list()).thenReturn(instances);
    when(instance.getEndActivityId()).thenReturn("endEvent_completed");

    ProcessDefinitionQuery definitionQuery = mock(ProcessDefinitionQuery.class);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(definitionQuery);
    when(definitionQuery.processDefinitionKey(workflowId)).thenReturn(definitionQuery);
    when(definitionQuery.versionTag(version)).thenReturn(definitionQuery);
    when(definitionQuery.list()).thenReturn(Collections.emptyList());

    when(objectConverter.convertCollection(any(), eq(WorkflowInstanceDomain.class)))
        .thenReturn(expectedDomains);

    List<WorkflowInstanceDomain> result = repository.findAllByIdAndStatusAndVersion(workflowId, status, version);

    assertThat(result).isEqualTo(expectedDomains);
    verify(query).finished();
  }

  @Test
  void shouldQueryByStatusWithCompletedStatusAndEndEventActivity() {
    String workflowId = "test-workflow";
    StatusEnum status = StatusEnum.COMPLETED;
    HistoricProcessInstanceQuery query = mock(HistoricProcessInstanceQuery.class);
    HistoricProcessInstance instance1 = mock(HistoricProcessInstance.class);
    HistoricProcessInstance instance2 = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = Arrays.asList(instance1, instance2);
    WorkflowInstanceDomain domain = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> expectedDomains = Arrays.asList(domain);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(query);
    when(query.processDefinitionKey(workflowId)).thenReturn(query);
    when(query.finished()).thenReturn(query);
    when(query.orderByProcessInstanceStartTime()).thenReturn(query);
    when(query.asc()).thenReturn(query);
    when(query.list()).thenReturn(instances);
    when(instance1.getEndActivityId()).thenReturn("endEvent_success");
    when(instance2.getEndActivityId()).thenReturn("errorEvent_1");

    ProcessDefinitionQuery definitionQuery = mock(ProcessDefinitionQuery.class);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(definitionQuery);
    when(definitionQuery.processDefinitionKey(workflowId)).thenReturn(definitionQuery);
    when(definitionQuery.list()).thenReturn(Collections.emptyList());

    when(objectConverter.convertCollection(any(), eq(WorkflowInstanceDomain.class)))
        .thenReturn(expectedDomains);

    List<WorkflowInstanceDomain> result = repository.findAllByIdAndStatusAndVersion(workflowId, status, null);

    assertThat(result).isEqualTo(expectedDomains);
    verify(query).finished();
  }

  @Test
  void shouldQueryByStatusWithFailedStatus() {
    String workflowId = "test-workflow";
    StatusEnum status = StatusEnum.FAILED;
    HistoricProcessInstanceQuery query = mock(HistoricProcessInstanceQuery.class);
    HistoricProcessInstance instance1 = mock(HistoricProcessInstance.class);
    HistoricProcessInstance instance2 = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = Arrays.asList(instance1, instance2);
    WorkflowInstanceDomain domain = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> expectedDomains = Arrays.asList(domain);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(query);
    when(query.processDefinitionKey(workflowId)).thenReturn(query);
    when(query.finished()).thenReturn(query);
    when(query.orderByProcessInstanceStartTime()).thenReturn(query);
    when(query.asc()).thenReturn(query);
    when(query.list()).thenReturn(instances);
    when(instance1.getEndActivityId()).thenReturn("errorEvent_failure");
    when(instance2.getEndActivityId()).thenReturn("endEvent_success");

    ProcessDefinitionQuery definitionQuery = mock(ProcessDefinitionQuery.class);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(definitionQuery);
    when(definitionQuery.processDefinitionKey(workflowId)).thenReturn(definitionQuery);
    when(definitionQuery.list()).thenReturn(Collections.emptyList());

    when(objectConverter.convertCollection(any(), eq(WorkflowInstanceDomain.class)))
        .thenReturn(expectedDomains);

    List<WorkflowInstanceDomain> result = repository.findAllByIdAndStatusAndVersion(workflowId, status, null);

    assertThat(result).isEqualTo(expectedDomains);
    verify(query).finished();
  }

  @Test
  void shouldQueryByStatusWithPendingStatus() {
    String workflowId = "test-workflow";
    StatusEnum status = StatusEnum.PENDING;
    HistoricProcessInstanceQuery query = mock(HistoricProcessInstanceQuery.class);
    HistoricProcessInstance instance = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = Arrays.asList(instance);
    WorkflowInstanceDomain domain = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> expectedDomains = Arrays.asList(domain);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(query);
    when(query.processDefinitionKey(workflowId)).thenReturn(query);
    when(query.unfinished()).thenReturn(query);
    when(query.orderByProcessInstanceStartTime()).thenReturn(query);
    when(query.asc()).thenReturn(query);
    when(query.list()).thenReturn(instances);

    ProcessDefinitionQuery definitionQuery = mock(ProcessDefinitionQuery.class);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(definitionQuery);
    when(definitionQuery.processDefinitionKey(workflowId)).thenReturn(definitionQuery);
    when(definitionQuery.list()).thenReturn(Collections.emptyList());

    when(objectConverter.convertCollection(any(), eq(WorkflowInstanceDomain.class)))
        .thenReturn(expectedDomains);

    List<WorkflowInstanceDomain> result = repository.findAllByIdAndStatusAndVersion(workflowId, status, null);

    assertThat(result).isEqualTo(expectedDomains);
    verify(query).unfinished();
  }

  @Test
  void shouldQueryByStatusWithNullEndActivityId() {
    String workflowId = "test-workflow";
    StatusEnum status = StatusEnum.COMPLETED;
    HistoricProcessInstanceQuery query = mock(HistoricProcessInstanceQuery.class);
    HistoricProcessInstance instance1 = mock(HistoricProcessInstance.class);
    HistoricProcessInstance instance2 = mock(HistoricProcessInstance.class);
    List<HistoricProcessInstance> instances = Arrays.asList(instance1, instance2);

    when(historyService.createHistoricProcessInstanceQuery()).thenReturn(query);
    when(query.processDefinitionKey(workflowId)).thenReturn(query);
    when(query.finished()).thenReturn(query);
    when(query.orderByProcessInstanceStartTime()).thenReturn(query);
    when(query.asc()).thenReturn(query);
    when(query.list()).thenReturn(instances);
    when(instance1.getEndActivityId()).thenReturn(null);
    when(instance2.getEndActivityId()).thenReturn("endEvent_1");

    ProcessDefinitionQuery definitionQuery = mock(ProcessDefinitionQuery.class);
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(definitionQuery);
    when(definitionQuery.processDefinitionKey(workflowId)).thenReturn(definitionQuery);
    when(definitionQuery.list()).thenReturn(Collections.emptyList());

    when(objectConverter.convertCollection(any(), eq(WorkflowInstanceDomain.class)))
        .thenReturn(Collections.emptyList());

    List<WorkflowInstanceDomain> result = repository.findAllByIdAndStatusAndVersion(workflowId, status, null);

    assertThat(result).isNotNull();
  }
}
