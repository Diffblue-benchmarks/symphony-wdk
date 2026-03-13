package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class WorkflowCmdaApiQueryRepositoryTest {

  @Test
  void shouldCreateRepositoryWithAllDependencies() {
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    RuntimeService runtimeService = mock(RuntimeService.class);
    ObjectConverter objectConverter = mock(ObjectConverter.class);

    WorkflowCmdaApiQueryRepository repository = new WorkflowCmdaApiQueryRepository(
        repositoryService, historyService, runtimeService, objectConverter);

    assertThat(repository).isNotNull();
  }

  @Test
  void shouldFindAllLatestVersionWorkflows() {
    RepositoryService repositoryService = mock(RepositoryService.class);
    HistoryService historyService = mock(HistoryService.class);
    RuntimeService runtimeService = mock(RuntimeService.class);
    ObjectConverter objectConverter = mock(ObjectConverter.class);
    ProcessDefinitionQuery query = mock(ProcessDefinitionQuery.class);
    ProcessDefinition processDefinition1 = mock(ProcessDefinition.class);
    ProcessDefinition processDefinition2 = mock(ProcessDefinition.class);
    List<ProcessDefinition> processDefinitions = Arrays.asList(processDefinition1, processDefinition2);
    WorkflowDomain workflow1 = WorkflowDomain.builder()
        .id("workflow-1")
        .name("Workflow 1")
        .version(1L)
        .build();
    WorkflowDomain workflow2 = WorkflowDomain.builder()
        .id("workflow-2")
        .name("Workflow 2")
        .version(2L)
        .build();
    List<WorkflowDomain> expectedWorkflows = Arrays.asList(workflow1, workflow2);

    when(repositoryService.createProcessDefinitionQuery()).thenReturn(query);
    when(query.latestVersion()).thenReturn(query);
    when(query.list()).thenReturn(processDefinitions);
    when(objectConverter.convertCollection(processDefinitions, WorkflowDomain.class))
        .thenReturn(expectedWorkflows);

    WorkflowCmdaApiQueryRepository repository = new WorkflowCmdaApiQueryRepository(
        repositoryService, historyService, runtimeService, objectConverter);
    List<WorkflowDomain> result = repository.findAll();

    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).containsExactly(workflow1, workflow2);
  }
}
