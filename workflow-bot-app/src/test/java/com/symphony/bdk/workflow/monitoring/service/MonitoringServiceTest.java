package com.symphony.bdk.workflow.monitoring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.symphony.bdk.workflow.engine.executor.ActivityExecutorContext;
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
import com.symphony.bdk.workflow.swadl.v1.Activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class MonitoringServiceTest {

  private WorkflowDirectedGraphService workflowDirectedGraphService;
  private WorkflowQueryRepository workflowQueryRepository;
  private WorkflowInstQueryRepository workflowInstQueryRepository;
  private ActivityQueryRepository activityQueryRepository;
  private VariableQueryRepository variableQueryRepository;
  private ObjectConverter objectConverter;
  private VersionedWorkflowRepository versionedWorkflowRepository;
  private MonitoringService monitoringService;

  @BeforeEach
  void setUp() {
    workflowDirectedGraphService = mock(WorkflowDirectedGraphService.class);
    workflowQueryRepository = mock(WorkflowQueryRepository.class);
    workflowInstQueryRepository = mock(WorkflowInstQueryRepository.class);
    activityQueryRepository = mock(ActivityQueryRepository.class);
    variableQueryRepository = mock(VariableQueryRepository.class);
    objectConverter = mock(ObjectConverter.class);
    versionedWorkflowRepository = mock(VersionedWorkflowRepository.class);
  }

  @Test
  void shouldReturnActiveWorkflowsWhenVersionedRepositoryPresent() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.of(versionedWorkflowRepository)
    );

    VersionedWorkflow workflow1 = mock(VersionedWorkflow.class);
    VersionedWorkflow workflow2 = mock(VersionedWorkflow.class);
    List<VersionedWorkflow> activeWorkflows = Arrays.asList(workflow1, workflow2);

    WorkflowView view1 = mock(WorkflowView.class);
    WorkflowView view2 = mock(WorkflowView.class);
    List<WorkflowView> expectedViews = Arrays.asList(view1, view2);

    when(versionedWorkflowRepository.findByActiveTrue()).thenReturn(activeWorkflows);
    when(objectConverter.convertCollection(activeWorkflows, WorkflowView.class)).thenReturn(expectedViews);

    // Act
    List<WorkflowView> result = monitoringService.listAllWorkflows();

    // Assert
    assertThat(result).isEqualTo(expectedViews);
    verify(versionedWorkflowRepository).findByActiveTrue();
  }

  @Test
  void shouldReturnAllWorkflowsWhenVersionedRepositoryNotPresent() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    WorkflowDomain domain1 = mock(WorkflowDomain.class);
    WorkflowDomain domain2 = mock(WorkflowDomain.class);
    List<WorkflowDomain> allWorkflows = Arrays.asList(domain1, domain2);

    WorkflowView view1 = mock(WorkflowView.class);
    WorkflowView view2 = mock(WorkflowView.class);
    List<WorkflowView> expectedViews = Arrays.asList(view1, view2);

    when(workflowQueryRepository.findAll()).thenReturn(allWorkflows);
    when(objectConverter.convertCollection(allWorkflows, WorkflowView.class)).thenReturn(expectedViews);

    // Act
    List<WorkflowView> result = monitoringService.listAllWorkflows();

    // Assert
    assertThat(result).isEqualTo(expectedViews);
  }

  @Test
  void shouldReturnWorkflowInstancesWhenStatusAndVersionProvided() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";
    String status = "COMPLETED";
    Long version = 1L;

    WorkflowInstanceDomain instance1 = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> instances = Arrays.asList(instance1);

    WorkflowInstView instView = mock(WorkflowInstView.class);
    List<WorkflowInstView> expectedViews = Arrays.asList(instView);

    when(workflowInstQueryRepository.findAllByIdAndStatusAndVersion(
        eq(workflowId),
        any(StatusEnum.class),
        eq("1")
    )).thenReturn(instances);
    when(objectConverter.convertCollection(instances, WorkflowInstView.class)).thenReturn(expectedViews);

    // Act
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, status, version);

    // Assert
    assertThat(result).isEqualTo(expectedViews);
  }

  @Test
  void shouldReturnWorkflowInstancesWhenStatusProvidedButVersionNull() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";
    String status = "PENDING";

    WorkflowInstanceDomain instance1 = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> instances = Arrays.asList(instance1);

    WorkflowInstView instView = mock(WorkflowInstView.class);
    List<WorkflowInstView> expectedViews = Arrays.asList(instView);

    when(workflowInstQueryRepository.findAllByIdAndStatus(
        eq(workflowId),
        any(StatusEnum.class)
    )).thenReturn(instances);
    when(objectConverter.convertCollection(instances, WorkflowInstView.class)).thenReturn(expectedViews);

    // Act
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, status, null);

    // Assert
    assertThat(result).isEqualTo(expectedViews);
  }

  @Test
  void shouldReturnWorkflowInstancesWhenStatusNullAndVersionProvided() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";
    Long version = 2L;

    WorkflowInstanceDomain instance1 = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> instances = Arrays.asList(instance1);

    WorkflowInstView instView = mock(WorkflowInstView.class);
    List<WorkflowInstView> expectedViews = Arrays.asList(instView);

    when(workflowInstQueryRepository.findAllByIdAndVersion(workflowId, "2")).thenReturn(instances);
    when(objectConverter.convertCollection(instances, WorkflowInstView.class)).thenReturn(expectedViews);

    // Act
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, null, version);

    // Assert
    assertThat(result).isEqualTo(expectedViews);
  }

  @Test
  void shouldReturnWorkflowInstancesWhenStatusAndVersionNull() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";

    WorkflowInstanceDomain instance1 = mock(WorkflowInstanceDomain.class);
    List<WorkflowInstanceDomain> instances = Arrays.asList(instance1);

    WorkflowInstView instView = mock(WorkflowInstView.class);
    List<WorkflowInstView> expectedViews = Arrays.asList(instView);

    when(workflowInstQueryRepository.findAllById(workflowId)).thenReturn(instances);
    when(objectConverter.convertCollection(instances, WorkflowInstView.class)).thenReturn(expectedViews);

    // Act
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, null, null);

    // Assert
    assertThat(result).isEqualTo(expectedViews);
  }

  @Test
  void shouldReturnWorkflowInstanceNodesWhenValidInstanceProvided() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";
    String instanceId = "instance1";
    WorkflowInstLifeCycleFilter filter = mock(WorkflowInstLifeCycleFilter.class);

    // Mock checkIsInstanceOfWorkflow
    WorkflowInstView instView = mock(WorkflowInstView.class);
    when(instView.getInstanceId()).thenReturn(instanceId);
    when(instView.getId()).thenReturn(workflowId);
    when(instView.getVersion()).thenReturn(1L);

    WorkflowInstanceDomain instanceDomain = mock(WorkflowInstanceDomain.class);
    when(workflowInstQueryRepository.findAllById(workflowId)).thenReturn(Arrays.asList(instanceDomain));
    when(objectConverter.convertCollection(any(), eq(WorkflowInstView.class))).thenReturn(Arrays.asList(instView));

    // Mock activity instances
    ActivityInstanceDomain activityInstance = mock(ActivityInstanceDomain.class);
    List<ActivityInstanceDomain> activityInstances = Arrays.asList(activityInstance);
    when(activityQueryRepository.findAllByWorkflowInstanceId(workflowId, instanceId, filter))
        .thenReturn(activityInstances);

    // Mock directed graph
    WorkflowDirectedGraph directedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraphService.getDirectedGraph(workflowId, 1L)).thenReturn(directedGraph);

    WorkflowNode workflowNode = mock(WorkflowNode.class);
    when(workflowNode.getWrappedType()).thenAnswer(invocation -> Activity.class);

    Map<String, WorkflowNode> dictionary = new HashMap<>();
    dictionary.put("node1", workflowNode);
    when(directedGraph.getDictionary()).thenReturn(dictionary);
    when(directedGraph.isRegistered("node1")).thenReturn(true);

    NodeStateView nodeStateView = NodeStateView.builder()
        .nodeId("node1")
        .build();
    when(objectConverter.convertCollection(activityInstances, NodeStateView.class))
        .thenReturn(Arrays.asList(nodeStateView));

    // Mock variables
    VariablesDomain globalVars = new VariablesDomain();
    globalVars.setOutputs(new HashMap<>());
    when(variableQueryRepository.findVarsByWorkflowInstanceIdAndVarName(instanceId, ActivityExecutorContext.VARIABLES))
        .thenReturn(globalVars);

    VariablesDomain errorVars = new VariablesDomain();
    errorVars.setOutputs(new HashMap<>());
    when(variableQueryRepository.findVarsByWorkflowInstanceIdAndVarName(instanceId, ActivityExecutorContext.ERROR))
        .thenReturn(errorVars);

    // Act
    WorkflowNodesStateView result = monitoringService.listWorkflowInstanceNodes(workflowId, instanceId, filter);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getNodes()).isNotNull();
    assertThat(result.getGlobalVariables()).isNotNull();
  }

  @Test
  void shouldReturnWorkflowDefinitionWithNullVersion() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";

    WorkflowDirectedGraph directedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraphService.getDirectedGraph(workflowId)).thenReturn(directedGraph);

    WorkflowNode workflowNode = mock(WorkflowNode.class);
    when(workflowNode.getWrappedType()).thenAnswer(invocation -> Activity.class);

    Map<String, WorkflowNode> dictionary = new HashMap<>();
    dictionary.put("node1", workflowNode);
    when(directedGraph.getDictionary()).thenReturn(dictionary);

    WorkflowDirectedGraph.NodeChildren children = mock(WorkflowDirectedGraph.NodeChildren.class);
    when(children.getChildren()).thenReturn(Collections.emptyList());
    when(directedGraph.getChildren("node1")).thenReturn(children);
    when(directedGraph.getParents("node1")).thenReturn(Collections.emptyList());
    when(directedGraph.getVersion()).thenReturn(1L);
    when(directedGraph.getVariables()).thenReturn(Collections.emptyMap());

    // Act
    WorkflowNodesView result = monitoringService.getWorkflowDefinition(workflowId);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getWorkflowId()).isEqualTo(workflowId);
    assertThat(result.getVersion()).isEqualTo(1L);
  }

  @Test
  void shouldReturnWorkflowDefinitionWithVersion() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";
    Long version = 2L;

    WorkflowDirectedGraph directedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraphService.getDirectedGraph(workflowId, version)).thenReturn(directedGraph);

    WorkflowNode workflowNode = mock(WorkflowNode.class);
    when(workflowNode.getWrappedType()).thenAnswer(invocation -> Activity.class);

    Map<String, WorkflowNode> dictionary = new HashMap<>();
    dictionary.put("node1", workflowNode);
    when(directedGraph.getDictionary()).thenReturn(dictionary);

    WorkflowDirectedGraph.NodeChildren children = mock(WorkflowDirectedGraph.NodeChildren.class);
    when(children.getChildren()).thenReturn(Collections.emptyList());
    when(directedGraph.getChildren("node1")).thenReturn(children);
    when(directedGraph.getParents("node1")).thenReturn(Collections.emptyList());
    when(directedGraph.getVersion()).thenReturn(version);
    when(directedGraph.getVariables()).thenReturn(Collections.emptyMap());

    // Act
    WorkflowNodesView result = monitoringService.getWorkflowDefinition(workflowId, version);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getWorkflowId()).isEqualTo(workflowId);
    assertThat(result.getVersion()).isEqualTo(version);
    assertThat(result.getFlowNodes()).hasSize(1);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenWorkflowDirectedGraphNotFound() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";
    Long version = 1L;

    when(workflowDirectedGraphService.getDirectedGraph(workflowId, version)).thenReturn(null);

    // Act & Assert
    assertThatThrownBy(() -> monitoringService.getWorkflowDefinition(workflowId, version))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("No workflow with id 'workflow1' and version '1' is found");
  }

  @Test
  void shouldReturnWorkflowInstanceGlobalVars() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";
    String instanceId = "instance1";
    Instant updatedBefore = Instant.now();
    Instant updatedAfter = Instant.now().minusSeconds(3600);

    // Mock checkIsInstanceOfWorkflow
    WorkflowInstView instView = mock(WorkflowInstView.class);
    when(instView.getInstanceId()).thenReturn(instanceId);
    when(instView.getId()).thenReturn(workflowId);

    WorkflowInstanceDomain instanceDomain = mock(WorkflowInstanceDomain.class);
    when(workflowInstQueryRepository.findAllById(workflowId)).thenReturn(Arrays.asList(instanceDomain));
    when(objectConverter.convertCollection(any(), eq(WorkflowInstView.class))).thenReturn(Arrays.asList(instView));

    // Mock global variables
    VariablesDomain vars1 = new VariablesDomain();
    vars1.setOutputs(Collections.singletonMap("var1", "value1"));
    List<VariablesDomain> variablesList = Arrays.asList(vars1);

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, updatedBefore, updatedAfter))
        .thenReturn(variablesList);

    // Act
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, updatedBefore, updatedAfter);

    // Assert
    assertThat(result).hasSize(1);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenInstanceNotFoundInCheckIsInstanceOfWorkflow() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";
    String instanceId = "instance1";
    Instant updatedBefore = Instant.now();
    Instant updatedAfter = Instant.now().minusSeconds(3600);

    when(workflowInstQueryRepository.findAllById(workflowId)).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(WorkflowInstView.class))).thenReturn(Collections.emptyList());

    // Act & Assert
    assertThatThrownBy(() -> monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, updatedBefore, updatedAfter))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Either no workflow deployed with id workflow1, or instance1 is not an instance of it");
  }

  @Test
  void shouldBuildNodeWithChildrenAndConditions() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";
    Long version = 1L;

    WorkflowDirectedGraph directedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraphService.getDirectedGraph(workflowId, version)).thenReturn(directedGraph);

    WorkflowNode parentNode = mock(WorkflowNode.class);
    WorkflowNode childNode = mock(WorkflowNode.class);
    when(parentNode.getWrappedType()).thenAnswer(invocation -> Activity.class);
    when(childNode.getWrappedType()).thenAnswer(invocation -> Activity.class);
    when(childNode.getIfCondition("parentNode")).thenReturn("condition1");
    when(childNode.getElementType()).thenReturn(WorkflowNodeType.ACTIVITY);

    Map<String, WorkflowNode> dictionary = new HashMap<>();
    dictionary.put("parentNode", parentNode);
    dictionary.put("childNode", childNode);
    when(directedGraph.getDictionary()).thenReturn(dictionary);

    WorkflowDirectedGraph.NodeChildren parentChildren = mock(WorkflowDirectedGraph.NodeChildren.class);
    when(parentChildren.getChildren()).thenReturn(Arrays.asList("childNode"));
    WorkflowDirectedGraph.NodeChildren childChildren = mock(WorkflowDirectedGraph.NodeChildren.class);
    when(childChildren.getChildren()).thenReturn(Collections.emptyList());
    when(directedGraph.getChildren("parentNode")).thenReturn(parentChildren);
    when(directedGraph.getChildren("childNode")).thenReturn(childChildren);
    when(directedGraph.getParents("parentNode")).thenReturn(Collections.emptyList());
    when(directedGraph.getParents("childNode")).thenReturn(Arrays.asList("parentNode"));
    when(directedGraph.getVersion()).thenReturn(version);
    when(directedGraph.getVariables()).thenReturn(Collections.emptyMap());

    // Act
    WorkflowNodesView result = monitoringService.getWorkflowDefinition(workflowId, version);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getFlowNodes()).hasSize(2);
  }

  @Test
  void shouldDetermineConditionForExpiredEvent() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";
    Long version = 1L;

    WorkflowDirectedGraph directedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraphService.getDirectedGraph(workflowId, version)).thenReturn(directedGraph);

    WorkflowNode parentNode = mock(WorkflowNode.class);
    WorkflowNode childNode = mock(WorkflowNode.class);
    when(parentNode.getWrappedType()).thenAnswer(invocation -> Activity.class);
    when(childNode.getWrappedType()).thenAnswer(invocation -> Activity.class);
    when(childNode.getIfCondition("parentNode")).thenReturn(null);
    when(childNode.getElementType()).thenReturn(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);

    Map<String, WorkflowNode> dictionary = new HashMap<>();
    dictionary.put("parentNode", parentNode);
    dictionary.put("childNode", childNode);
    when(directedGraph.getDictionary()).thenReturn(dictionary);

    WorkflowDirectedGraph.NodeChildren parentChildren = mock(WorkflowDirectedGraph.NodeChildren.class);
    when(parentChildren.getChildren()).thenReturn(Arrays.asList("childNode"));
    WorkflowDirectedGraph.NodeChildren childChildren = mock(WorkflowDirectedGraph.NodeChildren.class);
    when(childChildren.getChildren()).thenReturn(Collections.emptyList());
    when(directedGraph.getChildren("parentNode")).thenReturn(parentChildren);
    when(directedGraph.getChildren("childNode")).thenReturn(childChildren);
    when(directedGraph.getParents("parentNode")).thenReturn(Collections.emptyList());
    when(directedGraph.getParents("childNode")).thenReturn(Arrays.asList("parentNode"));
    when(directedGraph.getVersion()).thenReturn(version);
    when(directedGraph.getVariables()).thenReturn(Collections.emptyMap());

    // Act
    WorkflowNodesView result = monitoringService.getWorkflowDefinition(workflowId, version);

    // Assert
    assertThat(result).isNotNull();
  }

  @Test
  void shouldDetermineConditionForFailedEvent() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";
    Long version = 1L;

    WorkflowDirectedGraph directedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraphService.getDirectedGraph(workflowId, version)).thenReturn(directedGraph);

    WorkflowNode parentNode = mock(WorkflowNode.class);
    WorkflowNode childNode = mock(WorkflowNode.class);
    when(parentNode.getWrappedType()).thenAnswer(invocation -> Activity.class);
    when(childNode.getWrappedType()).thenAnswer(invocation -> Activity.class);
    when(childNode.getIfCondition("parentNode")).thenReturn(null);
    when(childNode.getElementType()).thenReturn(WorkflowNodeType.ACTIVITY_FAILED_EVENT);

    Map<String, WorkflowNode> dictionary = new HashMap<>();
    dictionary.put("parentNode", parentNode);
    dictionary.put("childNode", childNode);
    when(directedGraph.getDictionary()).thenReturn(dictionary);

    WorkflowDirectedGraph.NodeChildren parentChildren = mock(WorkflowDirectedGraph.NodeChildren.class);
    when(parentChildren.getChildren()).thenReturn(Arrays.asList("childNode"));
    WorkflowDirectedGraph.NodeChildren childChildren = mock(WorkflowDirectedGraph.NodeChildren.class);
    when(childChildren.getChildren()).thenReturn(Collections.emptyList());
    when(directedGraph.getChildren("parentNode")).thenReturn(parentChildren);
    when(directedGraph.getChildren("childNode")).thenReturn(childChildren);
    when(directedGraph.getParents("parentNode")).thenReturn(Collections.emptyList());
    when(directedGraph.getParents("childNode")).thenReturn(Arrays.asList("parentNode"));
    when(directedGraph.getVersion()).thenReturn(version);
    when(directedGraph.getVariables()).thenReturn(Collections.emptyMap());

    // Act
    WorkflowNodesView result = monitoringService.getWorkflowDefinition(workflowId, version);

    // Assert
    assertThat(result).isNotNull();
  }

  @Test
  void shouldBuildWorkflowNodesStateViewWithError() {
    // Arrange
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    String workflowId = "workflow1";
    String instanceId = "instance1";
    WorkflowInstLifeCycleFilter filter = mock(WorkflowInstLifeCycleFilter.class);

    // Mock checkIsInstanceOfWorkflow
    WorkflowInstView instView = mock(WorkflowInstView.class);
    when(instView.getInstanceId()).thenReturn(instanceId);
    when(instView.getId()).thenReturn(workflowId);
    when(instView.getVersion()).thenReturn(1L);

    WorkflowInstanceDomain instanceDomain = mock(WorkflowInstanceDomain.class);
    when(workflowInstQueryRepository.findAllById(workflowId)).thenReturn(Arrays.asList(instanceDomain));
    when(objectConverter.convertCollection(any(), eq(WorkflowInstView.class))).thenReturn(Arrays.asList(instView));

    // Mock activity instances
    ActivityInstanceDomain activityInstance = mock(ActivityInstanceDomain.class);
    List<ActivityInstanceDomain> activityInstances = Arrays.asList(activityInstance);
    when(activityQueryRepository.findAllByWorkflowInstanceId(workflowId, instanceId, filter))
        .thenReturn(activityInstances);

    // Mock directed graph
    WorkflowDirectedGraph directedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraphService.getDirectedGraph(workflowId, 1L)).thenReturn(directedGraph);

    WorkflowNode workflowNode = mock(WorkflowNode.class);
    when(workflowNode.getWrappedType()).thenAnswer(invocation -> Activity.class);

    Map<String, WorkflowNode> dictionary = new HashMap<>();
    dictionary.put("node1", workflowNode);
    when(directedGraph.getDictionary()).thenReturn(dictionary);
    when(directedGraph.isRegistered("node1")).thenReturn(true);

    NodeStateView nodeStateView = NodeStateView.builder()
        .nodeId("node1")
        .build();
    when(objectConverter.convertCollection(activityInstances, NodeStateView.class))
        .thenReturn(Arrays.asList(nodeStateView));

    // Mock variables
    VariablesDomain globalVars = new VariablesDomain();
    globalVars.setOutputs(new HashMap<>());
    when(variableQueryRepository.findVarsByWorkflowInstanceIdAndVarName(instanceId, ActivityExecutorContext.VARIABLES))
        .thenReturn(globalVars);

    VariablesDomain errorVars = new VariablesDomain();
    Map<String, Object> errorMap = new HashMap<>();
    errorMap.put("errorMessage", "Test error");
    errorVars.setOutputs(errorMap);
    when(variableQueryRepository.findVarsByWorkflowInstanceIdAndVarName(instanceId, ActivityExecutorContext.ERROR))
        .thenReturn(errorVars);

    // Act
    WorkflowNodesStateView result = monitoringService.listWorkflowInstanceNodes(workflowId, instanceId, filter);

    // Assert
    assertThat(result).isNotNull();
    assertThat(result.getError()).isNotNull();
    assertThat(result.getError()).containsKey("errorMessage");
  }
}
