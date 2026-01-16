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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for ActivityCmdaApiQueryRepository.
 * Tests the constructor and findAllByWorkflowInstanceId method with various scenarios.
 */
@ExtendWith(MockitoExtension.class)
class ActivityCmdaApiQueryRepositoryClaudeTest {

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
  private NativeHistoricVariableInstanceQuery nativeHistoricVariableInstanceQuery;

  private ActivityCmdaApiQueryRepository repository;

  @BeforeEach
  void setUp() {
    repository = new ActivityCmdaApiQueryRepository(
        repositoryService,
        historyService,
        runtimeService,
        objectConverter
    );
  }

  // ==================== Constructor Tests ====================

  @Test
  void constructor_withAllDependencies_shouldInitializeSuccessfully() {
    // When: creating a new instance with all dependencies
    ActivityCmdaApiQueryRepository newRepository = new ActivityCmdaApiQueryRepository(
        repositoryService,
        historyService,
        runtimeService,
        objectConverter
    );

    // Then: repository should be created successfully
    assertThat(newRepository).isNotNull();
  }

  @Test
  void constructor_shouldPassDependenciesToParent() {
    // When: creating repository instance (done in setUp)
    // Then: repository should be properly initialized with dependencies
    assertThat(repository).isNotNull();
  }

  // ==================== findAllByWorkflowInstanceId Tests ====================

  @Test
  void findAllByWorkflowInstanceId_withNoFilters_shouldReturnActivities() {
    // Given: a workflow instance with activities but no lifecycle filters
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    List<HistoricActivityInstance> historicActivities = createMockHistoricActivities();
    List<ActivityInstanceDomain> expectedDomains = createMockActivityDomains(false);

    setupHistoryServiceMock(instanceId, historicActivities, filter);
    when(objectConverter.convertCollection(historicActivities, ActivityInstanceDomain.class))
        .thenReturn(expectedDomains);

    // When: calling findAllByWorkflowInstanceId
    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId(
        workflowId, instanceId, filter
    );

    // Then: should return activities without filters applied
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    verify(historicActivityInstanceQuery).processInstanceId(instanceId);
    verify(historicActivityInstanceQuery, never()).startedBefore(any(Date.class));
    verify(historicActivityInstanceQuery, never()).startedAfter(any(Date.class));
    verify(historicActivityInstanceQuery, never()).finishedBefore(any(Date.class));
    verify(historicActivityInstanceQuery, never()).finishedAfter(any(Date.class));
  }

  @Test
  void findAllByWorkflowInstanceId_withStartedBeforeFilter_shouldApplyFilter() {
    // Given: a filter with startedBefore set
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    Instant startedBefore = Instant.parse("2024-01-15T12:00:00Z");
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(startedBefore, null, null, null);

    List<HistoricActivityInstance> historicActivities = createMockHistoricActivities();
    List<ActivityInstanceDomain> expectedDomains = createMockActivityDomains(false);

    setupHistoryServiceMock(instanceId, historicActivities, filter);
    when(objectConverter.convertCollection(historicActivities, ActivityInstanceDomain.class))
        .thenReturn(expectedDomains);

    // When: calling findAllByWorkflowInstanceId
    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId(
        workflowId, instanceId, filter
    );

    // Then: should apply startedBefore filter
    assertThat(result).isNotNull();
    verify(historicActivityInstanceQuery).startedBefore(Date.from(startedBefore));
    verify(historicActivityInstanceQuery, never()).startedAfter(any(Date.class));
    verify(historicActivityInstanceQuery, never()).finishedBefore(any(Date.class));
    verify(historicActivityInstanceQuery, never()).finishedAfter(any(Date.class));
  }

  @Test
  void findAllByWorkflowInstanceId_withStartedAfterFilter_shouldApplyFilter() {
    // Given: a filter with startedAfter set
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    Instant startedAfter = Instant.parse("2024-01-15T10:00:00Z");
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, startedAfter, null, null);

    List<HistoricActivityInstance> historicActivities = createMockHistoricActivities();
    List<ActivityInstanceDomain> expectedDomains = createMockActivityDomains(false);

    setupHistoryServiceMock(instanceId, historicActivities, filter);
    when(objectConverter.convertCollection(historicActivities, ActivityInstanceDomain.class))
        .thenReturn(expectedDomains);

    // When: calling findAllByWorkflowInstanceId
    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId(
        workflowId, instanceId, filter
    );

    // Then: should apply startedAfter filter
    assertThat(result).isNotNull();
    verify(historicActivityInstanceQuery).startedAfter(Date.from(startedAfter));
    verify(historicActivityInstanceQuery, never()).startedBefore(any(Date.class));
    verify(historicActivityInstanceQuery, never()).finishedBefore(any(Date.class));
    verify(historicActivityInstanceQuery, never()).finishedAfter(any(Date.class));
  }

  @Test
  void findAllByWorkflowInstanceId_withFinishedBeforeFilter_shouldApplyFilter() {
    // Given: a filter with finishedBefore set
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    Instant finishedBefore = Instant.parse("2024-01-15T14:00:00Z");
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, finishedBefore, null);

    List<HistoricActivityInstance> historicActivities = createMockHistoricActivities();
    List<ActivityInstanceDomain> expectedDomains = createMockActivityDomains(false);

    setupHistoryServiceMock(instanceId, historicActivities, filter);
    when(objectConverter.convertCollection(historicActivities, ActivityInstanceDomain.class))
        .thenReturn(expectedDomains);

    // When: calling findAllByWorkflowInstanceId
    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId(
        workflowId, instanceId, filter
    );

    // Then: should apply finishedBefore filter
    assertThat(result).isNotNull();
    verify(historicActivityInstanceQuery).finishedBefore(Date.from(finishedBefore));
    verify(historicActivityInstanceQuery, never()).startedBefore(any(Date.class));
    verify(historicActivityInstanceQuery, never()).startedAfter(any(Date.class));
    verify(historicActivityInstanceQuery, never()).finishedAfter(any(Date.class));
  }

  @Test
  void findAllByWorkflowInstanceId_withFinishedAfterFilter_shouldApplyFilter() {
    // Given: a filter with finishedAfter set
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    Instant finishedAfter = Instant.parse("2024-01-15T11:00:00Z");
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, finishedAfter);

    List<HistoricActivityInstance> historicActivities = createMockHistoricActivities();
    List<ActivityInstanceDomain> expectedDomains = createMockActivityDomains(false);

    setupHistoryServiceMock(instanceId, historicActivities, filter);
    when(objectConverter.convertCollection(historicActivities, ActivityInstanceDomain.class))
        .thenReturn(expectedDomains);

    // When: calling findAllByWorkflowInstanceId
    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId(
        workflowId, instanceId, filter
    );

    // Then: should apply finishedAfter filter
    assertThat(result).isNotNull();
    verify(historicActivityInstanceQuery).finishedAfter(Date.from(finishedAfter));
    verify(historicActivityInstanceQuery, never()).startedBefore(any(Date.class));
    verify(historicActivityInstanceQuery, never()).startedAfter(any(Date.class));
    verify(historicActivityInstanceQuery, never()).finishedBefore(any(Date.class));
  }

  @Test
  void findAllByWorkflowInstanceId_withAllFilters_shouldApplyAllFilters() {
    // Given: a filter with all fields set
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    Instant startedBefore = Instant.parse("2024-01-15T12:00:00Z");
    Instant startedAfter = Instant.parse("2024-01-15T10:00:00Z");
    Instant finishedBefore = Instant.parse("2024-01-15T14:00:00Z");
    Instant finishedAfter = Instant.parse("2024-01-15T11:00:00Z");
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(
        startedBefore, startedAfter, finishedBefore, finishedAfter
    );

    List<HistoricActivityInstance> historicActivities = createMockHistoricActivities();
    List<ActivityInstanceDomain> expectedDomains = createMockActivityDomains(false);

    setupHistoryServiceMock(instanceId, historicActivities, filter);
    when(objectConverter.convertCollection(historicActivities, ActivityInstanceDomain.class))
        .thenReturn(expectedDomains);

    // When: calling findAllByWorkflowInstanceId
    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId(
        workflowId, instanceId, filter
    );

    // Then: should apply all filters
    assertThat(result).isNotNull();
    verify(historicActivityInstanceQuery).startedBefore(Date.from(startedBefore));
    verify(historicActivityInstanceQuery).startedAfter(Date.from(startedAfter));
    verify(historicActivityInstanceQuery).finishedBefore(Date.from(finishedBefore));
    verify(historicActivityInstanceQuery).finishedAfter(Date.from(finishedAfter));
  }

  @Test
  void findAllByWorkflowInstanceId_withServiceTasks_shouldEnrichWithVariables() {
    // Given: activities including service tasks with variables
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    List<HistoricActivityInstance> historicActivities = createMockHistoricActivities();
    List<ActivityInstanceDomain> activityDomains = createMockActivityDomains(true);
    List<HistoricVariableInstance> variableInstances = createMockVariableInstances();

    setupHistoryServiceMock(instanceId, historicActivities, filter);
    when(objectConverter.convertCollection(historicActivities, ActivityInstanceDomain.class))
        .thenReturn(activityDomains);

    // Mock native query for variables
    when(historyService.createNativeHistoricVariableInstanceQuery())
        .thenReturn(nativeHistoricVariableInstanceQuery);
    when(nativeHistoricVariableInstanceQuery.sql(anyString()))
        .thenReturn(nativeHistoricVariableInstanceQuery);
    when(nativeHistoricVariableInstanceQuery.list()).thenReturn(variableInstances);

    // When: calling findAllByWorkflowInstanceId
    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId(
        workflowId, instanceId, filter
    );

    // Then: should enrich service tasks with variables
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);

    // Find the service task in results
    ActivityInstanceDomain serviceTaskActivity = result.stream()
        .filter(a -> a.getType().equals("serviceTask"))
        .findFirst()
        .orElse(null);

    assertThat(serviceTaskActivity).isNotNull();
    assertThat(serviceTaskActivity.getVariables()).isNotNull();
    assertThat(serviceTaskActivity.getVariables().getOutputs()).isNotEmpty();
    assertThat(serviceTaskActivity.getVariables().getUpdateTime()).isNotNull();
  }

  @Test
  void findAllByWorkflowInstanceId_withNoServiceTasks_shouldNotQueryVariables() {
    // Given: activities with no service tasks
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    List<HistoricActivityInstance> historicActivities = createMockHistoricActivities();
    List<ActivityInstanceDomain> activityDomains = createMockActivityDomainsWithoutServiceTasks();

    setupHistoryServiceMock(instanceId, historicActivities, filter);
    when(objectConverter.convertCollection(historicActivities, ActivityInstanceDomain.class))
        .thenReturn(activityDomains);

    // When: calling findAllByWorkflowInstanceId
    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId(
        workflowId, instanceId, filter
    );

    // Then: should not query for variables
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    verify(historyService, never()).createNativeHistoricVariableInstanceQuery();
  }

  @Test
  void findAllByWorkflowInstanceId_withEmptyActivities_shouldReturnEmptyList() {
    // Given: no historic activities for the instance
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    setupHistoryServiceMock(instanceId, Collections.emptyList(), filter);
    when(objectConverter.convertCollection(Collections.emptyList(), ActivityInstanceDomain.class))
        .thenReturn(Collections.emptyList());

    // When: calling findAllByWorkflowInstanceId
    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId(
        workflowId, instanceId, filter
    );

    // Then: should return empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
    verify(historyService, never()).createNativeHistoricVariableInstanceQuery();
  }

  @Test
  void findAllByWorkflowInstanceId_withServiceTaskButNoVariables_shouldHandleGracefully() {
    // Given: service tasks exist but no variables are found
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    List<HistoricActivityInstance> historicActivities = createMockHistoricActivities();
    List<ActivityInstanceDomain> activityDomains = createMockActivityDomains(true);

    setupHistoryServiceMock(instanceId, historicActivities, filter);
    when(objectConverter.convertCollection(historicActivities, ActivityInstanceDomain.class))
        .thenReturn(activityDomains);

    // Mock native query for variables returning empty list
    when(historyService.createNativeHistoricVariableInstanceQuery())
        .thenReturn(nativeHistoricVariableInstanceQuery);
    when(nativeHistoricVariableInstanceQuery.sql(anyString()))
        .thenReturn(nativeHistoricVariableInstanceQuery);
    when(nativeHistoricVariableInstanceQuery.list()).thenReturn(Collections.emptyList());

    // When: calling findAllByWorkflowInstanceId
    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId(
        workflowId, instanceId, filter
    );

    // Then: should handle gracefully without enriching variables
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);

    // Service task should still have default empty variables
    ActivityInstanceDomain serviceTaskActivity = result.stream()
        .filter(a -> a.getType().equals("serviceTask"))
        .findFirst()
        .orElse(null);

    assertThat(serviceTaskActivity).isNotNull();
    assertThat(serviceTaskActivity.getVariables().getOutputs()).isEmpty();
  }

  @Test
  void findAllByWorkflowInstanceId_shouldOrderByStartTimeAscending() {
    // Given: multiple activities
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    List<HistoricActivityInstance> historicActivities = createMockHistoricActivities();
    List<ActivityInstanceDomain> expectedDomains = createMockActivityDomains(false);

    setupHistoryServiceMock(instanceId, historicActivities, filter);
    when(objectConverter.convertCollection(historicActivities, ActivityInstanceDomain.class))
        .thenReturn(expectedDomains);

    // When: calling findAllByWorkflowInstanceId
    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId(
        workflowId, instanceId, filter
    );

    // Then: should order by historic activity start time ascending
    assertThat(result).isNotNull();
    verify(historicActivityInstanceQuery).orderByHistoricActivityInstanceStartTime();
    verify(historicActivityInstanceQuery).asc();
    verify(historicActivityInstanceQuery).list();
  }

  @Test
  void findAllByWorkflowInstanceId_withMultipleServiceTasks_shouldEnrichAll() {
    // Given: multiple service tasks with variables
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    List<HistoricActivityInstance> historicActivities = createMockHistoricActivities();
    List<ActivityInstanceDomain> activityDomains = createMockMultipleServiceTaskDomains();
    List<HistoricVariableInstance> variableInstances = createMockMultipleVariableInstances();

    setupHistoryServiceMock(instanceId, historicActivities, filter);
    when(objectConverter.convertCollection(historicActivities, ActivityInstanceDomain.class))
        .thenReturn(activityDomains);

    when(historyService.createNativeHistoricVariableInstanceQuery())
        .thenReturn(nativeHistoricVariableInstanceQuery);
    when(nativeHistoricVariableInstanceQuery.sql(anyString()))
        .thenReturn(nativeHistoricVariableInstanceQuery);
    when(nativeHistoricVariableInstanceQuery.list()).thenReturn(variableInstances);

    // When: calling findAllByWorkflowInstanceId
    List<ActivityInstanceDomain> result = repository.findAllByWorkflowInstanceId(
        workflowId, instanceId, filter
    );

    // Then: all service tasks should be enriched with variables
    assertThat(result).isNotNull();
    long serviceTasksWithVariables = result.stream()
        .filter(a -> a.getType().equals("serviceTask"))
        .filter(a -> !a.getVariables().getOutputs().isEmpty())
        .count();

    assertThat(serviceTasksWithVariables).isEqualTo(2);
  }

  @Test
  void findAllByWorkflowInstanceId_shouldConstructCorrectSqlForVariables() {
    // Given: service tasks that need variables
    String workflowId = "workflow-123";
    String instanceId = "instance-456";
    WorkflowInstLifeCycleFilter filter = new WorkflowInstLifeCycleFilter(null, null, null, null);

    List<HistoricActivityInstance> historicActivities = createMockHistoricActivities();
    List<ActivityInstanceDomain> activityDomains = createMockActivityDomains(true);

    setupHistoryServiceMock(instanceId, historicActivities, filter);
    when(objectConverter.convertCollection(historicActivities, ActivityInstanceDomain.class))
        .thenReturn(activityDomains);

    when(historyService.createNativeHistoricVariableInstanceQuery())
        .thenReturn(nativeHistoricVariableInstanceQuery);
    when(nativeHistoricVariableInstanceQuery.sql(anyString()))
        .thenReturn(nativeHistoricVariableInstanceQuery);
    when(nativeHistoricVariableInstanceQuery.list()).thenReturn(Collections.emptyList());

    // When: calling findAllByWorkflowInstanceId
    repository.findAllByWorkflowInstanceId(workflowId, instanceId, filter);

    // Then: should construct SQL query with correct format
    ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
    verify(nativeHistoricVariableInstanceQuery).sql(sqlCaptor.capture());

    String capturedSql = sqlCaptor.getValue();
    assertThat(capturedSql).contains("select * from ACT_HI_VARINST");
    assertThat(capturedSql).contains("PROC_INST_ID_ = '" + instanceId + "'");
    assertThat(capturedSql).contains("NAME_ in (");
    assertThat(capturedSql).contains("'serviceTask1'");
  }

  // ==================== Helper Methods ====================

  private void setupHistoryServiceMock(String instanceId, List<HistoricActivityInstance> activities,
      WorkflowInstLifeCycleFilter filter) {
    when(historyService.createHistoricActivityInstanceQuery()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.processInstanceId(instanceId)).thenReturn(historicActivityInstanceQuery);

    if (filter.getStartedBefore() != null) {
      when(historicActivityInstanceQuery.startedBefore(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    }
    if (filter.getStartedAfter() != null) {
      when(historicActivityInstanceQuery.startedAfter(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    }
    if (filter.getFinishedBefore() != null) {
      when(historicActivityInstanceQuery.finishedBefore(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    }
    if (filter.getFinishedAfter() != null) {
      when(historicActivityInstanceQuery.finishedAfter(any(Date.class))).thenReturn(historicActivityInstanceQuery);
    }

    when(historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime())
        .thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.asc()).thenReturn(historicActivityInstanceQuery);
    when(historicActivityInstanceQuery.list()).thenReturn(activities);
  }

  private List<HistoricActivityInstance> createMockHistoricActivities() {
    HistoricActivityInstance activity1 = mock(HistoricActivityInstance.class);
    HistoricActivityInstance activity2 = mock(HistoricActivityInstance.class);
    return Arrays.asList(activity1, activity2);
  }

  private List<ActivityInstanceDomain> createMockActivityDomains(boolean withServiceTask) {
    ActivityInstanceDomain activity1 = ActivityInstanceDomain.builder()
        .id("activity-1")
        .name(withServiceTask ? "serviceTask1" : "userTask1")
        .procInstId("instance-456")
        .workflowId("workflow-123")
        .type(withServiceTask ? "serviceTask" : "userTask")
        .startDate(Instant.parse("2024-01-15T10:00:00Z"))
        .endDate(Instant.parse("2024-01-15T10:30:00Z"))
        .duration(Duration.ofMinutes(30))
        .build();

    ActivityInstanceDomain activity2 = ActivityInstanceDomain.builder()
        .id("activity-2")
        .name("userTask2")
        .procInstId("instance-456")
        .workflowId("workflow-123")
        .type("userTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:00:00Z"))
        .duration(Duration.ofMinutes(30))
        .build();

    return Arrays.asList(activity1, activity2);
  }

  private List<ActivityInstanceDomain> createMockActivityDomainsWithoutServiceTasks() {
    ActivityInstanceDomain activity1 = ActivityInstanceDomain.builder()
        .id("activity-1")
        .name("userTask1")
        .procInstId("instance-456")
        .workflowId("workflow-123")
        .type("userTask")
        .startDate(Instant.parse("2024-01-15T10:00:00Z"))
        .endDate(Instant.parse("2024-01-15T10:30:00Z"))
        .duration(Duration.ofMinutes(30))
        .build();

    ActivityInstanceDomain activity2 = ActivityInstanceDomain.builder()
        .id("activity-2")
        .name("userTask2")
        .procInstId("instance-456")
        .workflowId("workflow-123")
        .type("userTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:00:00Z"))
        .duration(Duration.ofMinutes(30))
        .build();

    return Arrays.asList(activity1, activity2);
  }

  private List<ActivityInstanceDomain> createMockMultipleServiceTaskDomains() {
    ActivityInstanceDomain activity1 = ActivityInstanceDomain.builder()
        .id("activity-1")
        .name("serviceTask1")
        .procInstId("instance-456")
        .workflowId("workflow-123")
        .type("serviceTask")
        .startDate(Instant.parse("2024-01-15T10:00:00Z"))
        .endDate(Instant.parse("2024-01-15T10:30:00Z"))
        .duration(Duration.ofMinutes(30))
        .build();

    ActivityInstanceDomain activity2 = ActivityInstanceDomain.builder()
        .id("activity-2")
        .name("serviceTask2")
        .procInstId("instance-456")
        .workflowId("workflow-123")
        .type("serviceTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:00:00Z"))
        .duration(Duration.ofMinutes(30))
        .build();

    return Arrays.asList(activity1, activity2);
  }

  private List<HistoricVariableInstance> createMockVariableInstances() {
    HistoricVariableInstance varInstance = mock(HistoricVariableInstance.class);
    when(varInstance.getName()).thenReturn("serviceTask1");

    Map<String, Object> variableValue = new HashMap<>();
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("result", "success");
    outputs.put("code", 200);
    variableValue.put("outputs", outputs);

    when(varInstance.getValue()).thenReturn(variableValue);
    when(varInstance.getCreateTime()).thenReturn(Date.from(Instant.parse("2024-01-15T10:30:00Z")));

    return Collections.singletonList(varInstance);
  }

  private List<HistoricVariableInstance> createMockMultipleVariableInstances() {
    HistoricVariableInstance varInstance1 = mock(HistoricVariableInstance.class);
    when(varInstance1.getName()).thenReturn("serviceTask1");

    Map<String, Object> variableValue1 = new HashMap<>();
    Map<String, Object> outputs1 = new HashMap<>();
    outputs1.put("result", "success");
    variableValue1.put("outputs", outputs1);

    when(varInstance1.getValue()).thenReturn(variableValue1);
    when(varInstance1.getCreateTime()).thenReturn(Date.from(Instant.parse("2024-01-15T10:30:00Z")));

    HistoricVariableInstance varInstance2 = mock(HistoricVariableInstance.class);
    when(varInstance2.getName()).thenReturn("serviceTask2");

    Map<String, Object> variableValue2 = new HashMap<>();
    Map<String, Object> outputs2 = new HashMap<>();
    outputs2.put("data", "processed");
    variableValue2.put("outputs", outputs2);

    when(varInstance2.getValue()).thenReturn(variableValue2);
    when(varInstance2.getCreateTime()).thenReturn(Date.from(Instant.parse("2024-01-15T11:00:00Z")));

    return Arrays.asList(varInstance1, varInstance2);
  }
}
