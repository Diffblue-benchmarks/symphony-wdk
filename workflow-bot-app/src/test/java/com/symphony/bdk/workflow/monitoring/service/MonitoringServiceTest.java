package com.symphony.bdk.workflow.monitoring.service;

import com.symphony.bdk.workflow.api.v1.dto.NodeStateView;
import com.symphony.bdk.workflow.api.v1.dto.NodeView;
import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.api.v1.dto.VariableView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstLifeCycleFilter;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesStateView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.monitoring.repository.ActivityQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.VariableQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.WorkflowInstQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.WorkflowQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MonitoringServiceTest {

  private WorkflowDirectedGraphService workflowDirectedGraphService;
  private WorkflowQueryRepository workflowQueryRepository;
  private WorkflowInstQueryRepository workflowInstQueryRepository;
  private ActivityQueryRepository activityQueryRepository;
  private VariableQueryRepository variableQueryRepository;
  private ObjectConverter objectConverter;
  private VersionedWorkflowRepository versionedWorkflowRepository;

  private MonitoringService monitoringServiceWithVersionedRepo;
  private MonitoringService monitoringServiceWithoutVersionedRepo;

  @BeforeEach
  void setUp() {
    workflowDirectedGraphService = mock(WorkflowDirectedGraphService.class);
    workflowQueryRepository = mock(WorkflowQueryRepository.class);
    workflowInstQueryRepository = mock(WorkflowInstQueryRepository.class);
    activityQueryRepository = mock(ActivityQueryRepository.class);
    variableQueryRepository = mock(VariableQueryRepository.class);
    objectConverter = mock(ObjectConverter.class);
    versionedWorkflowRepository = mock(VersionedWorkflowRepository.class);

    monitoringServiceWithVersionedRepo = new MonitoringService(
        workflowDirectedGraphService, workflowQueryRepository, workflowInstQueryRepository,
        activityQueryRepository, variableQueryRepository, objectConverter,
        Optional.of(versionedWorkflowRepository));

    monitoringServiceWithoutVersionedRepo = new MonitoringService(
        workflowDirectedGraphService, workflowQueryRepository, workflowInstQueryRepository,
        activityQueryRepository, variableQueryRepository, objectConverter,
        Optional.empty());
  }

  @Test
  void shouldListAllWorkflowsFromVersionedRepositoryWhenPresent() {
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    WorkflowView workflowView = WorkflowView.builder().id("wf1").build();
    when(versionedWorkflowRepository.findByActiveTrue()).thenReturn(List.of(versionedWorkflow));
    when(objectConverter.convertCollection(List.of(versionedWorkflow), WorkflowView.class))
        .thenReturn(List.of(workflowView));

    List<WorkflowView> result = monitoringServiceWithVersionedRepo.listAllWorkflows();

    assertThat(result).containsExactly(workflowView);
  }

  @Test
  void shouldListAllWorkflowsFromQueryRepositoryWhenVersionedRepoAbsent() {
    WorkflowDomain workflowDomain = WorkflowDomain.builder().id("wf1").name("name").version(1L).build();
    WorkflowView workflowView = WorkflowView.builder().id("wf1").build();
    when(workflowQueryRepository.findAll()).thenReturn(List.of(workflowDomain));
    when(objectConverter.convertCollection(List.of(workflowDomain), WorkflowView.class))
        .thenReturn(List.of(workflowView));

    List<WorkflowView> result = monitoringServiceWithoutVersionedRepo.listAllWorkflows();

    assertThat(result).containsExactly(workflowView);
  }

  @Test
  void shouldListWorkflowInstancesWithStatusAndVersion() {
    WorkflowInstanceDomain domain = buildWorkflowInstanceDomain("wf1", "inst1", 1L);
    WorkflowInstView view = buildWorkflowInstView("wf1", "inst1", 1L);
    when(workflowInstQueryRepository.findAllByIdAndStatusAndVersion("wf1", StatusEnum.COMPLETED, "1"))
        .thenReturn(List.of(domain));
    when(objectConverter.convertCollection(List.of(domain), WorkflowInstView.class)).thenReturn(List.of(view));

    List<WorkflowInstView> result = monitoringServiceWithVersionedRepo.listWorkflowInstances("wf1", "COMPLETED", 1L);

    assertThat(result).containsExactly(view);
  }

  @Test
  void shouldListWorkflowInstancesWithStatusAndNoVersion() {
    WorkflowInstanceDomain domain = buildWorkflowInstanceDomain("wf1", "inst1", null);
    WorkflowInstView view = buildWorkflowInstView("wf1", "inst1", null);
    when(workflowInstQueryRepository.findAllByIdAndStatus("wf1", StatusEnum.PENDING))
        .thenReturn(List.of(domain));
    when(objectConverter.convertCollection(List.of(domain), WorkflowInstView.class)).thenReturn(List.of(view));

    List<WorkflowInstView> result = monitoringServiceWithVersionedRepo.listWorkflowInstances("wf1", "PENDING", null);

    assertThat(result).containsExactly(view);
  }

  @Test
  void shouldListWorkflowInstancesWithoutStatusAndWithVersion() {
    WorkflowInstanceDomain domain = buildWorkflowInstanceDomain("wf1", "inst1", 2L);
    WorkflowInstView view = buildWorkflowInstView("wf1", "inst1", 2L);
    when(workflowInstQueryRepository.findAllByIdAndVersion("wf1", "2")).thenReturn(List.of(domain));
    when(objectConverter.convertCollection(List.of(domain), WorkflowInstView.class)).thenReturn(List.of(view));

    List<WorkflowInstView> result = monitoringServiceWithVersionedRepo.listWorkflowInstances("wf1", null, 2L);

    assertThat(result).containsExactly(view);
  }

  @Test
  void shouldListWorkflowInstancesWithoutStatusAndWithoutVersion() {
    WorkflowInstanceDomain domain = buildWorkflowInstanceDomain("wf1", "inst1", null);
    WorkflowInstView view = buildWorkflowInstView("wf1", "inst1", null);
    when(workflowInstQueryRepository.findAllById("wf1")).thenReturn(List.of(domain));
    when(objectConverter.convertCollection(List.of(domain), WorkflowInstView.class)).thenReturn(List.of(view));

    List<WorkflowInstView> result = monitoringServiceWithVersionedRepo.listWorkflowInstances("wf1", null, null);

    assertThat(result).containsExactly(view);
  }

  @Test
  void shouldListWorkflowInstanceNodes() {
    WorkflowInstView instView = buildWorkflowInstView("wf1", "inst1", 1L);
    WorkflowInstanceDomain domain = buildWorkflowInstanceDomain("wf1", "inst1", 1L);
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1", 1L);
    ActivityInstanceDomain activityDomain = ActivityInstanceDomain.builder()
        .id("act1").name("activity1").procInstId("inst1").workflowId("wf1").build();
    NodeStateView nodeStateView = NodeStateView.builder().nodeId("act1").build();
    VariablesDomain globalVars = new VariablesDomain();
    VariablesDomain error = new VariablesDomain();

    when(workflowInstQueryRepository.findAllById("wf1")).thenReturn(List.of(domain));
    when(objectConverter.convertCollection(List.of(domain), WorkflowInstView.class)).thenReturn(List.of(instView));
    when(activityQueryRepository.findAllByWorkflowInstanceId(eq("wf1"), eq("inst1"), any()))
        .thenReturn(List.of(activityDomain));
    when(workflowDirectedGraphService.getDirectedGraph("wf1", 1L)).thenReturn(graph);
    when(objectConverter.convertCollection(List.of(activityDomain), NodeStateView.class))
        .thenReturn(List.of(nodeStateView));
    when(variableQueryRepository.findVarsByWorkflowInstanceIdAndVarName("inst1", "variables")).thenReturn(globalVars);
    when(variableQueryRepository.findVarsByWorkflowInstanceIdAndVarName("inst1", "error")).thenReturn(error);

    WorkflowNodesStateView result = monitoringServiceWithVersionedRepo.listWorkflowInstanceNodes(
        "wf1", "inst1", null);

    assertThat(result).isNotNull();
    assertThat(result.getNodes()).isNotNull();
  }

  @Test
  void shouldListWorkflowInstanceNodesWithErrorVariable() {
    WorkflowInstView instView = buildWorkflowInstView("wf1", "inst1", 1L);
    WorkflowInstanceDomain domain = buildWorkflowInstanceDomain("wf1", "inst1", 1L);
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1", 1L);
    NodeStateView nodeStateView = NodeStateView.builder().nodeId("act1").build();
    VariablesDomain globalVars = new VariablesDomain();
    VariablesDomain error = new VariablesDomain();
    error.setOutputs(Map.of("message", "error occurred"));

    when(workflowInstQueryRepository.findAllById("wf1")).thenReturn(List.of(domain));
    when(objectConverter.convertCollection(List.of(domain), WorkflowInstView.class)).thenReturn(List.of(instView));
    when(activityQueryRepository.findAllByWorkflowInstanceId(eq("wf1"), eq("inst1"), any()))
        .thenReturn(Collections.emptyList());
    when(workflowDirectedGraphService.getDirectedGraph("wf1", 1L)).thenReturn(graph);
    when(objectConverter.convertCollection(Collections.emptyList(), NodeStateView.class))
        .thenReturn(List.of(nodeStateView));
    when(variableQueryRepository.findVarsByWorkflowInstanceIdAndVarName("inst1", "variables")).thenReturn(globalVars);
    when(variableQueryRepository.findVarsByWorkflowInstanceIdAndVarName("inst1", "error")).thenReturn(error);

    WorkflowNodesStateView result = monitoringServiceWithVersionedRepo.listWorkflowInstanceNodes(
        "wf1", "inst1", null);

    assertThat(result.getError()).isNotNull().containsKey("message");
  }

  @Test
  void shouldGetWorkflowDefinitionWithoutVersion() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1", 1L);
    WorkflowNode node = new WorkflowNode().id("act1").wrappedType(DummyActivity.class)
        .elementType(WorkflowNodeType.ACTIVITY);
    graph.registerToDictionary("act1", node);
    when(workflowDirectedGraphService.getDirectedGraph("wf1")).thenReturn(graph);

    WorkflowNodesView result = monitoringServiceWithVersionedRepo.getWorkflowDefinition("wf1");

    assertThat(result).isNotNull();
    assertThat(result.getWorkflowId()).isEqualTo("wf1");
    assertThat(result.getFlowNodes()).hasSize(1);
  }

  @Test
  void shouldGetWorkflowDefinitionWithVersion() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1", 2L);
    WorkflowNode node = new WorkflowNode().id("act1").wrappedType(DummyActivity.class)
        .elementType(WorkflowNodeType.ACTIVITY);
    graph.registerToDictionary("act1", node);
    when(workflowDirectedGraphService.getDirectedGraph("wf1", 2L)).thenReturn(graph);

    WorkflowNodesView result = monitoringServiceWithVersionedRepo.getWorkflowDefinition("wf1", 2L);

    assertThat(result).isNotNull();
    assertThat(result.getWorkflowId()).isEqualTo("wf1");
    assertThat(result.getVersion()).isEqualTo(2L);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenWorkflowGraphNotFound() {
    when(workflowDirectedGraphService.getDirectedGraph("unknown")).thenReturn(null);

    assertThatThrownBy(() -> monitoringServiceWithVersionedRepo.getWorkflowDefinition("unknown"))
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenWorkflowGraphNotFoundWithVersion() {
    when(workflowDirectedGraphService.getDirectedGraph("unknown", 1L)).thenReturn(null);

    assertThatThrownBy(() -> monitoringServiceWithVersionedRepo.getWorkflowDefinition("unknown", 1L))
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  void shouldListWorkflowInstanceGlobalVars() {
    WorkflowInstView instView = buildWorkflowInstView("wf1", "inst1", 1L);
    WorkflowInstanceDomain domain = buildWorkflowInstanceDomain("wf1", "inst1", 1L);
    VariablesDomain varsDomain = new VariablesDomain();
    varsDomain.setOutputs(Map.of("key", "value"));

    when(workflowInstQueryRepository.findAllById("wf1")).thenReturn(List.of(domain));
    when(objectConverter.convertCollection(List.of(domain), WorkflowInstView.class)).thenReturn(List.of(instView));
    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId("inst1", null, null))
        .thenReturn(List.of(varsDomain));

    List<VariableView> result = monitoringServiceWithVersionedRepo.listWorkflowInstanceGlobalVars(
        "wf1", "inst1", null, null);

    assertThat(result).hasSize(1);
  }

  @Test
  void shouldThrowNotFoundWhenInstanceDoesNotBelongToWorkflow() {
    WorkflowInstView instView = buildWorkflowInstView("wf1", "other-inst", 1L);
    WorkflowInstanceDomain domain = buildWorkflowInstanceDomain("wf1", "other-inst", 1L);

    when(workflowInstQueryRepository.findAllById("wf1")).thenReturn(List.of(domain));
    when(objectConverter.convertCollection(List.of(domain), WorkflowInstView.class)).thenReturn(List.of(instView));

    assertThatThrownBy(
        () -> monitoringServiceWithVersionedRepo.listWorkflowInstanceGlobalVars("wf1", "inst1", null, null))
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  void shouldDetermineConditionForExpiredEvent() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1", 1L);
    WorkflowNode parentNode = new WorkflowNode().id("act1").wrappedType(DummyActivity.class)
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode childNode = new WorkflowNode().id("act2").wrappedType(DummyActivity.class)
        .elementType(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
    graph.registerToDictionary("act1", parentNode);
    graph.registerToDictionary("act2", childNode);
    graph.getChildren("act1").addChild("act2");
    when(workflowDirectedGraphService.getDirectedGraph("wf1", 1L)).thenReturn(graph);

    WorkflowNodesView result = monitoringServiceWithVersionedRepo.getWorkflowDefinition("wf1", 1L);

    NodeView act1View = result.getFlowNodes().stream()
        .filter(n -> "act1".equals(n.getNodeId())).findFirst().orElseThrow();
    assertThat(act1View.getChildren()).hasSize(1);
    assertThat(act1View.getChildren().get(0).getCondition()).isEqualTo("expired");
  }

  @Test
  void shouldDetermineConditionForFailedEvent() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1", 1L);
    WorkflowNode parentNode = new WorkflowNode().id("act1").wrappedType(DummyActivity.class)
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode childNode = new WorkflowNode().id("act2").wrappedType(DummyActivity.class)
        .elementType(WorkflowNodeType.ACTIVITY_FAILED_EVENT);
    graph.registerToDictionary("act1", parentNode);
    graph.registerToDictionary("act2", childNode);
    graph.getChildren("act1").addChild("act2");
    when(workflowDirectedGraphService.getDirectedGraph("wf1", 1L)).thenReturn(graph);

    WorkflowNodesView result = monitoringServiceWithVersionedRepo.getWorkflowDefinition("wf1", 1L);

    NodeView act1View = result.getFlowNodes().stream()
        .filter(n -> "act1".equals(n.getNodeId())).findFirst().orElseThrow();
    assertThat(act1View.getChildren()).hasSize(1);
    assertThat(act1View.getChildren().get(0).getCondition()).isEqualTo("failed");
  }

  @Test
  void shouldDetermineConditionFromIfCondition() {
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1", 1L);
    WorkflowNode parentNode = new WorkflowNode().id("act1").wrappedType(DummyActivity.class)
        .elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode childNode = new WorkflowNode().id("act2").wrappedType(DummyActivity.class)
        .elementType(WorkflowNodeType.ACTIVITY).addIfCondition("act1", "someCondition");
    graph.registerToDictionary("act1", parentNode);
    graph.registerToDictionary("act2", childNode);
    graph.getChildren("act1").addChild("act2");
    when(workflowDirectedGraphService.getDirectedGraph("wf1", 1L)).thenReturn(graph);

    WorkflowNodesView result = monitoringServiceWithVersionedRepo.getWorkflowDefinition("wf1", 1L);

    NodeView act1View = result.getFlowNodes().stream()
        .filter(n -> "act1".equals(n.getNodeId())).findFirst().orElseThrow();
    assertThat(act1View.getChildren().get(0).getCondition()).isEqualTo("someCondition");
  }

  @Test
  void shouldSetNodeTypeAndGroupForRegisteredNodes() {
    WorkflowInstView instView = buildWorkflowInstView("wf1", "inst1", 1L);
    WorkflowInstanceDomain domain = buildWorkflowInstanceDomain("wf1", "inst1", 1L);
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("wf1", 1L);
    WorkflowNode node = new WorkflowNode().id("act1").wrappedType(DummyActivity.class)
        .elementType(WorkflowNodeType.ACTIVITY);
    graph.registerToDictionary("act1", node);

    ActivityInstanceDomain activityDomain = ActivityInstanceDomain.builder()
        .id("act1").name("activity1").procInstId("inst1").workflowId("wf1").build();
    NodeStateView nodeStateView = NodeStateView.builder().nodeId("act1").build();
    VariablesDomain globalVars = new VariablesDomain();
    VariablesDomain error = new VariablesDomain();

    when(workflowInstQueryRepository.findAllById("wf1")).thenReturn(List.of(domain));
    when(objectConverter.convertCollection(List.of(domain), WorkflowInstView.class)).thenReturn(List.of(instView));
    when(activityQueryRepository.findAllByWorkflowInstanceId(eq("wf1"), eq("inst1"), any()))
        .thenReturn(List.of(activityDomain));
    when(workflowDirectedGraphService.getDirectedGraph("wf1", 1L)).thenReturn(graph);
    when(objectConverter.convertCollection(List.of(activityDomain), NodeStateView.class))
        .thenReturn(List.of(nodeStateView));
    when(variableQueryRepository.findVarsByWorkflowInstanceIdAndVarName("inst1", "variables")).thenReturn(globalVars);
    when(variableQueryRepository.findVarsByWorkflowInstanceIdAndVarName("inst1", "error")).thenReturn(error);

    monitoringServiceWithVersionedRepo.listWorkflowInstanceNodes("wf1", "inst1", null);

    assertThat(nodeStateView.getType()).isEqualTo("DUMMY_ACTIVITY");
    assertThat(nodeStateView.getGroup()).isEqualTo("ACTIVITY");
  }

  static class DummyActivity {}

  private WorkflowInstanceDomain buildWorkflowInstanceDomain(String id, String instanceId, Long version) {
    return WorkflowInstanceDomain.builder()
        .id(id)
        .instanceId(instanceId)
        .version(version)
        .status("COMPLETED")
        .build();
  }

  private WorkflowInstView buildWorkflowInstView(String id, String instanceId, Long version) {
    return WorkflowInstView.builder()
        .id(id)
        .instanceId(instanceId)
        .version(version)
        .build();
  }
}
