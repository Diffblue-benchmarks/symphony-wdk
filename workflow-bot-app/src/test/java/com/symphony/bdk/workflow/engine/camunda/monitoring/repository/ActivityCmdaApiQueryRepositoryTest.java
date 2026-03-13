package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstLifeCycleFilter;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
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
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityCmdaApiQueryRepositoryTest {

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

  @Mock
  private HistoricActivityInstance historicActivityInstance;

  @Mock
  private NativeHistoricVariableInstanceQuery nativeHistoricVariableInstanceQuery;

  @Mock
  private HistoricVariableInstance historicVariableInstance;

  private ActivityCmdaApiQueryRepository repository;

  @BeforeEach
  void setUp() {
    repository = new ActivityCmdaApiQueryRepository(repositoryService, historyService, runtimeService, objectConverter);
  }

  @Test
  void shouldCreateRepository() {
    assertThat(repository).isNotNull();
  }

  @Test
  void shouldFindAllByWorkflowInstanceIdWithNoFilters() {
    var instanceId = "instance123";
    var workflowId = "workflow456";
    var filter = new WorkflowInstLifeCycleFilter(null, null, null, null);
    var activityDomain = ActivityInstanceDomain.builder()
        .id("activity1")
        .name("testActivity")
        .type("userTask")
        .build();

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(instanceId)).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(List.of(historicActivityInstance));
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(List.of(activityDomain));

    var result = repository.findAllByWorkflowInstanceId(workflowId, instanceId, filter);

    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(activityDomain);
    verify(historicActivityInstanceQuery, never()).startedBefore(any(Date.class));
    verify(historicActivityInstanceQuery, never()).startedAfter(any(Date.class));
    verify(historicActivityInstanceQuery, never()).finishedBefore(any(Date.class));
    verify(historicActivityInstanceQuery, never()).finishedAfter(any(Date.class));
  }

  @Test
  void shouldFindAllByWorkflowInstanceIdWithStartedBeforeFilter() {
    var instanceId = "instance123";
    var workflowId = "workflow456";
    var startedBefore = Instant.parse("2023-12-01T10:00:00Z");
    var filter = new WorkflowInstLifeCycleFilter(startedBefore, null, null, null);
    var activityDomain = ActivityInstanceDomain.builder()
        .id("activity1")
        .name("testActivity")
        .type("userTask")
        .build();

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(instanceId)).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.startedBefore(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(List.of(historicActivityInstance));
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(List.of(activityDomain));

    var result = repository.findAllByWorkflowInstanceId(workflowId, instanceId, filter);

    assertThat(result).hasSize(1);
    verify(historicActivityInstanceQuery).startedBefore(Date.from(startedBefore));
  }

  @Test
  void shouldFindAllByWorkflowInstanceIdWithStartedAfterFilter() {
    var instanceId = "instance123";
    var workflowId = "workflow456";
    var startedAfter = Instant.parse("2023-11-01T10:00:00Z");
    var filter = new WorkflowInstLifeCycleFilter(null, startedAfter, null, null);
    var activityDomain = ActivityInstanceDomain.builder()
        .id("activity1")
        .name("testActivity")
        .type("userTask")
        .build();

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(instanceId)).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.startedAfter(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(List.of(historicActivityInstance));
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(List.of(activityDomain));

    var result = repository.findAllByWorkflowInstanceId(workflowId, instanceId, filter);

    assertThat(result).hasSize(1);
    verify(historicActivityInstanceQuery).startedAfter(Date.from(startedAfter));
  }

  @Test
  void shouldFindAllByWorkflowInstanceIdWithFinishedBeforeFilter() {
    var instanceId = "instance123";
    var workflowId = "workflow456";
    var finishedBefore = Instant.parse("2023-12-01T12:00:00Z");
    var filter = new WorkflowInstLifeCycleFilter(null, null, finishedBefore, null);
    var activityDomain = ActivityInstanceDomain.builder()
        .id("activity1")
        .name("testActivity")
        .type("userTask")
        .build();

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(instanceId)).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finishedBefore(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(List.of(historicActivityInstance));
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(List.of(activityDomain));

    var result = repository.findAllByWorkflowInstanceId(workflowId, instanceId, filter);

    assertThat(result).hasSize(1);
    verify(historicActivityInstanceQuery).finishedBefore(Date.from(finishedBefore));
  }

  @Test
  void shouldFindAllByWorkflowInstanceIdWithFinishedAfterFilter() {
    var instanceId = "instance123";
    var workflowId = "workflow456";
    var finishedAfter = Instant.parse("2023-11-01T12:00:00Z");
    var filter = new WorkflowInstLifeCycleFilter(null, null, null, finishedAfter);
    var activityDomain = ActivityInstanceDomain.builder()
        .id("activity1")
        .name("testActivity")
        .type("userTask")
        .build();

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(instanceId)).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finishedAfter(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(List.of(historicActivityInstance));
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(List.of(activityDomain));

    var result = repository.findAllByWorkflowInstanceId(workflowId, instanceId, filter);

    assertThat(result).hasSize(1);
    verify(historicActivityInstanceQuery).finishedAfter(Date.from(finishedAfter));
  }

  @Test
  void shouldFindAllByWorkflowInstanceIdWithAllFilters() {
    var instanceId = "instance123";
    var workflowId = "workflow456";
    var startedBefore = Instant.parse("2023-12-01T10:00:00Z");
    var startedAfter = Instant.parse("2023-11-01T10:00:00Z");
    var finishedBefore = Instant.parse("2023-12-01T12:00:00Z");
    var finishedAfter = Instant.parse("2023-11-01T12:00:00Z");
    var filter = new WorkflowInstLifeCycleFilter(startedBefore, startedAfter, finishedBefore, finishedAfter);
    var activityDomain = ActivityInstanceDomain.builder()
        .id("activity1")
        .name("testActivity")
        .type("userTask")
        .build();

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(instanceId)).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.startedBefore(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.startedAfter(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finishedBefore(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.finishedAfter(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(List.of(historicActivityInstance));
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(List.of(activityDomain));

    var result = repository.findAllByWorkflowInstanceId(workflowId, instanceId, filter);

    assertThat(result).hasSize(1);
    verify(historicActivityInstanceQuery).startedBefore(Date.from(startedBefore));
    verify(historicActivityInstanceQuery).startedAfter(Date.from(startedAfter));
    verify(historicActivityInstanceQuery).finishedBefore(Date.from(finishedBefore));
    verify(historicActivityInstanceQuery).finishedAfter(Date.from(finishedAfter));
  }

  @Test
  void shouldFindAllByWorkflowInstanceIdWithServiceTaskAndVariables() {
    var instanceId = "instance123";
    var workflowId = "workflow456";
    var filter = new WorkflowInstLifeCycleFilter(null, null, null, null);
    var createTime = Date.from(Instant.parse("2023-12-01T10:00:00Z"));

    var activityDomain = ActivityInstanceDomain.builder()
        .id("activity1")
        .name("myServiceTask")
        .type("serviceTask")
        .build();

    Map<String, Object> variableValue = new HashMap<>();
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("result", "success");
    variableValue.put("outputs", outputs);

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(instanceId)).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(List.of(historicActivityInstance));
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(List.of(activityDomain));

    when(historyService.createNativeHistoricVariableInstanceQuery()).thenReturn(nativeHistoricVariableInstanceQuery);
    when(nativeHistoricVariableInstanceQuery.sql(anyString())).thenReturn(nativeHistoricVariableInstanceQuery);
    when(nativeHistoricVariableInstanceQuery.list()).thenReturn(List.of(historicVariableInstance));

    when(historicVariableInstance.getName()).thenReturn("myServiceTask");
    when(historicVariableInstance.getValue()).thenReturn(variableValue);
    when(historicVariableInstance.getCreateTime()).thenReturn(createTime);

    var result = repository.findAllByWorkflowInstanceId(workflowId, instanceId, filter);

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getVariables()).isNotNull();
    assertThat(result.get(0).getVariables().getOutputs()).containsEntry("result", "success");
    assertThat(result.get(0).getVariables().getUpdateTime()).isEqualTo(createTime.toInstant());
  }

  @Test
  void shouldFindAllByWorkflowInstanceIdWithServiceTaskButNoVariables() {
    var instanceId = "instance123";
    var workflowId = "workflow456";
    var filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    var activityDomain = ActivityInstanceDomain.builder()
        .id("activity1")
        .name("myServiceTask")
        .type("serviceTask")
        .build();

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(instanceId)).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(List.of(historicActivityInstance));
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(List.of(activityDomain));

    when(historyService.createNativeHistoricVariableInstanceQuery()).thenReturn(nativeHistoricVariableInstanceQuery);
    when(nativeHistoricVariableInstanceQuery.sql(anyString())).thenReturn(nativeHistoricVariableInstanceQuery);
    when(nativeHistoricVariableInstanceQuery.list()).thenReturn(Collections.emptyList());

    var result = repository.findAllByWorkflowInstanceId(workflowId, instanceId, filter);

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getVariables().getOutputs()).isEmpty();
  }

  @Test
  void shouldFindAllByWorkflowInstanceIdWithNoServiceTasks() {
    var instanceId = "instance123";
    var workflowId = "workflow456";
    var filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    var activityDomain = ActivityInstanceDomain.builder()
        .id("activity1")
        .name("myUserTask")
        .type("userTask")
        .build();

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(instanceId)).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(List.of(historicActivityInstance));
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(List.of(activityDomain));

    var result = repository.findAllByWorkflowInstanceId(workflowId, instanceId, filter);

    assertThat(result).hasSize(1);
    verify(historyService, never()).createNativeHistoricVariableInstanceQuery();
  }

  @Test
  void shouldFindAllByWorkflowInstanceIdWithMultipleServiceTasks() {
    var instanceId = "instance123";
    var workflowId = "workflow456";
    var filter = new WorkflowInstLifeCycleFilter(null, null, null, null);
    var createTime = Date.from(Instant.parse("2023-12-01T10:00:00Z"));

    var activityDomain1 = ActivityInstanceDomain.builder()
        .id("activity1")
        .name("serviceTask1")
        .type("serviceTask")
        .build();

    var activityDomain2 = ActivityInstanceDomain.builder()
        .id("activity2")
        .name("serviceTask2")
        .type("serviceTask")
        .build();

    Map<String, Object> variableValue1 = new HashMap<>();
    Map<String, Object> outputs1 = new HashMap<>();
    outputs1.put("result", "success1");
    variableValue1.put("outputs", outputs1);

    Map<String, Object> variableValue2 = new HashMap<>();
    Map<String, Object> outputs2 = new HashMap<>();
    outputs2.put("result", "success2");
    variableValue2.put("outputs", outputs2);

    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(instanceId)).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(List.of(historicActivityInstance));
    when(objectConverter.convertCollection(any(), eq(ActivityInstanceDomain.class))).thenReturn(List.of(activityDomain1, activityDomain2));

    when(historyService.createNativeHistoricVariableInstanceQuery()).thenReturn(nativeHistoricVariableInstanceQuery);
    when(nativeHistoricVariableInstanceQuery.sql(anyString())).thenReturn(nativeHistoricVariableInstanceQuery);

    var historicVariableInstance2 = org.mockito.Mockito.mock(HistoricVariableInstance.class);
    when(nativeHistoricVariableInstanceQuery.list()).thenReturn(List.of(historicVariableInstance, historicVariableInstance2));

    lenient().when(historicVariableInstance.getName()).thenReturn("serviceTask1");
    lenient().when(historicVariableInstance.getValue()).thenReturn(variableValue1);
    lenient().when(historicVariableInstance.getCreateTime()).thenReturn(createTime);

    lenient().when(historicVariableInstance2.getName()).thenReturn("serviceTask2");
    lenient().when(historicVariableInstance2.getValue()).thenReturn(variableValue2);
    lenient().when(historicVariableInstance2.getCreateTime()).thenReturn(createTime);

    var result = repository.findAllByWorkflowInstanceId(workflowId, instanceId, filter);

    assertThat(result).hasSize(2);
    assertThat(result.get(0).getVariables().getOutputs()).containsEntry("result", "success1");
    assertThat(result.get(1).getVariables().getOutputs()).containsEntry("result", "success2");
  }
}
