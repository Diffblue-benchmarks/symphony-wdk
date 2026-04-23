package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstLifeCycleFilter;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricActivityInstanceQuery;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.history.NativeHistoricVariableInstanceQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityCmdaApiQueryRepositoryTest {

  private ActivityCmdaApiQueryRepository repository;

  @Mock
  private RepositoryService repositoryService;

  @Mock
  private HistoryService historyService;

  @Mock
  private RuntimeService runtimeService;

  @Mock
  private ObjectConverter objectConverter;

  @Mock
  private HistoricActivityInstanceQuery historicActivityInstanceQuery;

  @BeforeEach
  void setUp() {
    repository = new ActivityCmdaApiQueryRepository(repositoryService, historyService, runtimeService, objectConverter);
  }

  @Test
  void shouldCreateRepositoryWithDependencies() {
    assertThat(repository).isNotNull();
  }

  @Test
  void shouldReturnActivitiesWhenNoFiltersApplied() {
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    ActivityInstanceDomain activity = ActivityInstanceDomain.builder()
        .id("act1")
        .name("task1")
        .type("userTask")
        .build();
    List<ActivityInstanceDomain> activities = List.of(activity);

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(anyString())).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(activities);

    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId("workflowId", "instanceId", filter);

    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    verify(historicActivityInstanceQuery, never()).startedBefore(any());
    verify(historicActivityInstanceQuery, never()).startedAfter(any());
    verify(historicActivityInstanceQuery, never()).finishedBefore(any());
    verify(historicActivityInstanceQuery, never()).finishedAfter(any());
  }

  @Test
  void shouldApplyStartedBeforeFilterWhenFilterProvided() {
    Instant startedBefore = Instant.now();
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(startedBefore, null, null, null);

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(anyString())).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.startedBefore(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(Collections.emptyList());

    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId("workflowId", "instanceId", filter);

    assertThat(result).isEmpty();
    verify(historicActivityInstanceQuery).startedBefore(Date.from(startedBefore));
  }

  @Test
  void shouldApplyStartedAfterFilterWhenFilterProvided() {
    Instant startedAfter = Instant.now();
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, startedAfter, null, null);

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(anyString())).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.startedAfter(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(Collections.emptyList());

    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId("workflowId", "instanceId", filter);

    assertThat(result).isEmpty();
    verify(historicActivityInstanceQuery).startedAfter(Date.from(startedAfter));
  }

  @Test
  void shouldApplyFinishedBeforeFilterWhenFilterProvided() {
    Instant finishedBefore = Instant.now();
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, finishedBefore, null);

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(anyString())).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finishedBefore(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(Collections.emptyList());

    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId("workflowId", "instanceId", filter);

    assertThat(result).isEmpty();
    verify(historicActivityInstanceQuery).finishedBefore(Date.from(finishedBefore));
  }

  @Test
  void shouldApplyFinishedAfterFilterWhenFilterProvided() {
    Instant finishedAfter = Instant.now();
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, finishedAfter);

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(anyString())).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finishedAfter(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(Collections.emptyList());

    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId("workflowId", "instanceId", filter);

    assertThat(result).isEmpty();
    verify(historicActivityInstanceQuery).finishedAfter(Date.from(finishedAfter));
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldMapVariablesToServiceTasksWhenServiceTasksPresent() {
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    ActivityInstanceDomain serviceTask = ActivityInstanceDomain.builder()
        .id("act1")
        .name("myServiceTask")
        .type("serviceTask")
        .build();
    List<ActivityInstanceDomain> activities = List.of(serviceTask);

    HistoricVariableInstance varInstance = mock(HistoricVariableInstance.class);
    Map<String, Object> objectMap = new HashMap<>();
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("key", "value");
    objectMap.put("outputs", outputs);
    NativeHistoricVariableInstanceQuery nativeQuery = mock(NativeHistoricVariableInstanceQuery.class);

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(anyString())).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(activities);
    when(historyService.createNativeHistoricVariableInstanceQuery()).thenReturn(nativeQuery);
    when(nativeQuery.sql(anyString())).thenReturn(nativeQuery);
    when(nativeQuery.list()).thenReturn(List.of(varInstance));
    when(varInstance.getName()).thenReturn("myServiceTask");
    when(varInstance.getValue()).thenReturn(objectMap);
    when(varInstance.getCreateTime()).thenReturn(new Date(0));

    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId("workflowId", "instanceId", filter);

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getVariables()).isNotNull();
    assertThat(result.get(0).getVariables().getOutputs()).isEqualTo(outputs);
  }

  @Test
  void shouldReturnEmptyVariablesWhenNoServiceTasksPresent() {
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    ActivityInstanceDomain userTask = ActivityInstanceDomain.builder()
        .id("act1")
        .name("myUserTask")
        .type("userTask")
        .build();
    List<ActivityInstanceDomain> activities = List.of(userTask);

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(anyString())).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(activities);

    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId("workflowId", "instanceId", filter);

    assertThat(result).hasSize(1);
    verify(historyService, never()).createNativeHistoricVariableInstanceQuery();
  }
}
