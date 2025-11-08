package com.symphony.bdk.workflow.monitoring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.api.v1.dto.NodeView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstLifeCycleFilter;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph.NodeChildren;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.monitoring.repository.ActivityQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.VariableQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.WorkflowInstQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.WorkflowQueryRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MonitoringService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class MonitoringServiceDiffblueTest {
  @MockBean
  private ActivityQueryRepository activityQueryRepository;

  @Autowired
  private MonitoringService monitoringService;

  @MockBean
  private ObjectConverter objectConverter;

  @MockBean
  private VariableQueryRepository variableQueryRepository;

  @MockBean
  private WorkflowDirectedGraphService workflowDirectedGraphService;

  @MockBean
  private WorkflowInstQueryRepository workflowInstQueryRepository;

  @MockBean
  private WorkflowQueryRepository workflowQueryRepository;

  @MockBean
  private VersionedWorkflowRepository versionedWorkflowRepository;

  /**
   * Test {@link MonitoringService#listAllWorkflows()}.
   * <p>
   * Method under test: {@link MonitoringService#listAllWorkflows()}
   */
  @Test
  @DisplayName("Test listAllWorkflows()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List MonitoringService.listAllWorkflows()"})
  void testListAllWorkflows() {
    // Arrange
    when(versionedWorkflowRepository.findByActiveTrue()).thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.listAllWorkflows());
    verify(versionedWorkflowRepository).findByActiveTrue();
  }

  /**
   * Test {@link MonitoringService#listAllWorkflows()}.
   * <p>
   * Method under test: {@link MonitoringService#listAllWorkflows()}
   */
  @Test
  @DisplayName("Test listAllWorkflows()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List MonitoringService.listAllWorkflows()"})
  void testListAllWorkflows2() {
    // Arrange
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    when(versionedWorkflowRepository.findByActiveTrue()).thenReturn(new ArrayList<>());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.listAllWorkflows());
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByActiveTrue();
  }

  /**
   * Test {@link MonitoringService#listAllWorkflows()}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringService#listAllWorkflows()}
   */
  @Test
  @DisplayName("Test listAllWorkflows(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List MonitoringService.listAllWorkflows()"})
  void testListAllWorkflows_thenReturnEmpty() {
    // Arrange
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());
    when(versionedWorkflowRepository.findByActiveTrue()).thenReturn(new ArrayList<>());

    // Act
    List<WorkflowView> actualListAllWorkflowsResult = monitoringService.listAllWorkflows();

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByActiveTrue();
    assertTrue(actualListAllWorkflowsResult.isEmpty());
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstances(String, String, Long)}.
   * <p>
   * Method under test: {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  @DisplayName("Test listWorkflowInstances(String, String, Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List MonitoringService.listWorkflowInstances(String, String, Long)"})
  void testListWorkflowInstances() {
    // Arrange
    when(workflowInstQueryRepository.findAllByIdAndVersion(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.listWorkflowInstances("42", null, 1L));
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllByIdAndVersion(eq("42"), eq("1"));
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstances(String, String, Long)}.
   * <p>
   * Method under test: {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  @DisplayName("Test listWorkflowInstances(String, String, Long)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List MonitoringService.listWorkflowInstances(String, String, Long)"})
  void testListWorkflowInstances2() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.listWorkflowInstances("42", null, null));
    verify(workflowInstQueryRepository).findAllById(eq("42"));
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstances(String, String, Long)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  @DisplayName("Test listWorkflowInstances(String, String, Long); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List MonitoringService.listWorkflowInstances(String, String, Long)"})
  void testListWorkflowInstances_thenReturnEmpty() {
    // Arrange
    when(workflowInstQueryRepository.findAllByIdAndVersion(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstView> actualListWorkflowInstancesResult = monitoringService.listWorkflowInstances("42", null, 1L);

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllByIdAndVersion(eq("42"), eq("1"));
    assertTrue(actualListWorkflowInstancesResult.isEmpty());
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstances(String, String, Long)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  @DisplayName("Test listWorkflowInstances(String, String, Long); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List MonitoringService.listWorkflowInstances(String, String, Long)"})
  void testListWorkflowInstances_thenReturnEmpty2() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstView> actualListWorkflowInstancesResult = monitoringService.listWorkflowInstances("42", null,
        null);

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllById(eq("42"));
    assertTrue(actualListWorkflowInstancesResult.isEmpty());
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter)}.
   * <p>
   * Method under test: {@link MonitoringService#listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName("Test listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesStateView MonitoringService.listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter)"})
  void testListWorkflowInstanceNodes() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());
    Instant startedBefore = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Instant startedAfter = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Instant finishedBefore = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act and Assert
    assertThrows(NotFoundException.class,
        () -> monitoringService.listWorkflowInstanceNodes("42", "42", new WorkflowInstLifeCycleFilter(startedBefore,
            startedAfter, finishedBefore, LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllById(eq("42"));
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter)}.
   * <p>
   * Method under test: {@link MonitoringService#listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName("Test listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesStateView MonitoringService.listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter)"})
  void testListWorkflowInstanceNodes2() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    Instant startedBefore = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Instant startedAfter = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Instant finishedBefore = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act and Assert
    assertThrows(NotFoundException.class,
        () -> monitoringService.listWorkflowInstanceNodes("42", "42", new WorkflowInstLifeCycleFilter(startedBefore,
            startedAfter, finishedBefore, LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())));
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllById(eq("42"));
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   * <p>
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String) with 'workflowId'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    Class<WorkflowDirectedGraph> forNameResult = WorkflowDirectedGraph.class;
    Mockito.<Class<?>>when(workflowNode.getWrappedType()).thenReturn(forNameResult);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("foo", workflowNode);
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new NodeChildren());
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    when(workflowDirectedGraph.getVariables()).thenReturn(new HashMap<>());
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any())).thenReturn(workflowDirectedGraph);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42");

    // Assert
    verify(workflowDirectedGraph).getChildren(eq("foo"));
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents(eq("foo"));
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowNode).getWrappedType();
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"));
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("WORKFLOW_DIRECTED_GRAPH", getResult.getType());
    assertEquals("foo", getResult.getNodeId());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId}, {@code version}.
   * <p>
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion() {
    // Arrange
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42", 1L);

    // Assert
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"));
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"), eq(1L));
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    assertNull(actualWorkflowDefinition.getVersion());
    assertTrue(actualWorkflowDefinition.getFlowNodes().isEmpty());
    assertTrue(actualWorkflowDefinition.getVariables().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId}, {@code version}.
   * <p>
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion2() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    Class<Object> clz = Object.class;
    workflowNode.wrappedType(clz);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("foo", workflowNode);
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new NodeChildren());
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    when(workflowDirectedGraph.getVariables()).thenReturn(new HashMap<>());
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(workflowDirectedGraph);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42", 1L);

    // Assert
    verify(workflowDirectedGraph).getChildren(eq("foo"));
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents(eq("foo"));
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"));
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"), eq(1L));
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("OBJECT", getResult.getType());
    assertEquals("foo", getResult.getNodeId());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId}, {@code version}.
   * <p>
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion3() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(workflowNode.getWrappedType()).thenReturn(forNameResult);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("foo", workflowNode);
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new NodeChildren());
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    when(workflowDirectedGraph.getVariables()).thenReturn(new HashMap<>());
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(workflowDirectedGraph);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42", 1L);

    // Assert
    verify(workflowDirectedGraph).getChildren(eq("foo"));
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents(eq("foo"));
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowNode).getWrappedType();
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"));
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"), eq(1L));
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("OBJECT", getResult.getType());
    assertEquals("foo", getResult.getNodeId());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId}, {@code version}.
   * <p>
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion4() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    Class<WorkflowDirectedGraph> forNameResult = WorkflowDirectedGraph.class;
    Mockito.<Class<?>>when(workflowNode.getWrappedType()).thenReturn(forNameResult);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("foo", workflowNode);
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new NodeChildren());
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    when(workflowDirectedGraph.getVariables()).thenReturn(new HashMap<>());
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(workflowDirectedGraph);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42", 1L);

    // Assert
    verify(workflowDirectedGraph).getChildren(eq("foo"));
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents(eq("foo"));
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowNode).getWrappedType();
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"));
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"), eq(1L));
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("WORKFLOW_DIRECTED_GRAPH", getResult.getType());
    assertEquals("foo", getResult.getNodeId());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId}, {@code version}.
   * <ul>
   *   <li>Then return Version longValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'; then return Version longValue is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion_thenReturnVersionLongValueIsOne() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(new HashMap<>());
    when(workflowDirectedGraph.getVariables()).thenReturn(new HashMap<>());
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(workflowDirectedGraph);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42", 1L);

    // Assert
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"));
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"), eq(1L));
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    assertEquals(1L, actualWorkflowDefinition.getVersion().longValue());
    assertTrue(actualWorkflowDefinition.getFlowNodes().isEmpty());
    assertTrue(actualWorkflowDefinition.getVariables().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId}, {@code version}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Version is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'; when 'null'; then return Version is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion_whenNull_thenReturnVersionIsNull() {
    // Arrange
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42", null);

    // Assert
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"));
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    assertNull(actualWorkflowDefinition.getVersion());
    assertTrue(actualWorkflowDefinition.getFlowNodes().isEmpty());
    assertTrue(actualWorkflowDefinition.getVariables().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   * <ul>
   *   <li>Given {@link WorkflowNode} (default constructor) wrappedType {@link Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String) with 'workflowId'; given WorkflowNode (default constructor) wrappedType Object")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId_givenWorkflowNodeWrappedTypeObject() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    Class<Object> clz = Object.class;
    workflowNode.wrappedType(clz);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("foo", workflowNode);
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new NodeChildren());
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    when(workflowDirectedGraph.getVariables()).thenReturn(new HashMap<>());
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any())).thenReturn(workflowDirectedGraph);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42");

    // Assert
    verify(workflowDirectedGraph).getChildren(eq("foo"));
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents(eq("foo"));
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"));
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("OBJECT", getResult.getType());
    assertEquals("foo", getResult.getNodeId());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   * <ul>
   *   <li>Then return FlowNodes first Type is {@code OBJECT}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String) with 'workflowId'; then return FlowNodes first Type is 'OBJECT'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId_thenReturnFlowNodesFirstTypeIsObject() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(workflowNode.getWrappedType()).thenReturn(forNameResult);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("foo", workflowNode);
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new NodeChildren());
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    when(workflowDirectedGraph.getVariables()).thenReturn(new HashMap<>());
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any())).thenReturn(workflowDirectedGraph);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42");

    // Assert
    verify(workflowDirectedGraph).getChildren(eq("foo"));
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents(eq("foo"));
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowNode).getWrappedType();
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"));
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("OBJECT", getResult.getType());
    assertEquals("foo", getResult.getNodeId());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   * <ul>
   *   <li>Then return Version is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String) with 'workflowId'; then return Version is 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId_thenReturnVersionIsNull() {
    // Arrange
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42");

    // Assert
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"));
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    assertNull(actualWorkflowDefinition.getVersion());
    assertTrue(actualWorkflowDefinition.getFlowNodes().isEmpty());
    assertTrue(actualWorkflowDefinition.getVariables().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   * <ul>
   *   <li>Then return Version longValue is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String) with 'workflowId'; then return Version longValue is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId_thenReturnVersionLongValueIsOne() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(new HashMap<>());
    when(workflowDirectedGraph.getVariables()).thenReturn(new HashMap<>());
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any())).thenReturn(workflowDirectedGraph);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42");

    // Assert
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"));
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    assertEquals(1L, actualWorkflowDefinition.getVersion().longValue());
    assertTrue(actualWorkflowDefinition.getFlowNodes().isEmpty());
    assertTrue(actualWorkflowDefinition.getVariables().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   * <ul>
   *   <li>Then throw {@link NotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String) with 'workflowId'; then throw NotFoundException")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId_thenThrowNotFoundException() {
    // Arrange
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any())).thenReturn(null);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.getWorkflowDefinition("42"));
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"));
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstanceGlobalVars(String, String, Instant, Instant)}.
   * <p>
   * Method under test: {@link MonitoringService#listWorkflowInstanceGlobalVars(String, String, Instant, Instant)}
   */
  @Test
  @DisplayName("Test listWorkflowInstanceGlobalVars(String, String, Instant, Instant)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List MonitoringService.listWorkflowInstanceGlobalVars(String, String, Instant, Instant)"})
  void testListWorkflowInstanceGlobalVars() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());
    Instant updatedBefore = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.listWorkflowInstanceGlobalVars("42", "42",
        updatedBefore, LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllById(eq("42"));
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstanceGlobalVars(String, String, Instant, Instant)}.
   * <p>
   * Method under test: {@link MonitoringService#listWorkflowInstanceGlobalVars(String, String, Instant, Instant)}
   */
  @Test
  @DisplayName("Test listWorkflowInstanceGlobalVars(String, String, Instant, Instant)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"List MonitoringService.listWorkflowInstanceGlobalVars(String, String, Instant, Instant)"})
  void testListWorkflowInstanceGlobalVars2() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    Instant updatedBefore = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.listWorkflowInstanceGlobalVars("42", "42",
        updatedBefore, LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllById(eq("42"));
  }
}
