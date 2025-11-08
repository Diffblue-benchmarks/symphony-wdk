package com.symphony.bdk.workflow.monitoring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.api.v1.dto.NodeView;
import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstLifeCycleFilter;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
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
import java.util.Map;
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
  private VersionedWorkflowRepository versionedWorkflowRepository;

  @MockBean
  private WorkflowDirectedGraphService workflowDirectedGraphService;

  @MockBean
  private WorkflowInstQueryRepository workflowInstQueryRepository;

  @MockBean
  private WorkflowQueryRepository workflowQueryRepository;

  /**
   * Method under test: {@link MonitoringService#listAllWorkflows()}
   */
  @Test
  void testListAllWorkflows() {
    // Arrange
    ArrayList<Object> objectList = new ArrayList<>();
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(objectList);
    when(versionedWorkflowRepository.findByActiveTrue()).thenReturn(new ArrayList<>());

    // Act
    List<WorkflowView> actualListAllWorkflowsResult = monitoringService.listAllWorkflows();

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByActiveTrue();
    assertTrue(actualListAllWorkflowsResult.isEmpty());
    assertSame(objectList, actualListAllWorkflowsResult);
  }

  /**
   * Method under test: {@link MonitoringService#listAllWorkflows()}
   */
  @Test
  void testListAllWorkflows2() {
    // Arrange
    when(versionedWorkflowRepository.findByActiveTrue()).thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.listAllWorkflows());
    verify(versionedWorkflowRepository).findByActiveTrue();
  }

  /**
   * Method under test: {@link MonitoringService#listAllWorkflows()}
   */
  @Test
  void testListAllWorkflows3() {
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
   * Method under test:
   * {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  void testListWorkflowInstances() {
    // Arrange
    when(workflowInstQueryRepository.findAllByIdAndStatusAndVersion(Mockito.<String>any(), Mockito.<StatusEnum>any(),
        Mockito.<String>any())).thenReturn(new ArrayList<>());
    ArrayList<Object> objectList = new ArrayList<>();
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(objectList);

    // Act
    List<WorkflowInstView> actualListWorkflowInstancesResult = monitoringService.listWorkflowInstances("42", "ACTIVE",
        1L);

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllByIdAndStatusAndVersion(eq("42"), eq(StatusEnum.PENDING), eq("1"));
    assertTrue(actualListWorkflowInstancesResult.isEmpty());
    assertSame(objectList, actualListWorkflowInstancesResult);
  }

  /**
   * Method under test:
   * {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  void testListWorkflowInstances2() {
    // Arrange
    when(workflowInstQueryRepository.findAllByIdAndStatusAndVersion(Mockito.<String>any(), Mockito.<StatusEnum>any(),
        Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.listWorkflowInstances("42", "ACTIVE", 1L));
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllByIdAndStatusAndVersion(eq("42"), eq(StatusEnum.PENDING), eq("1"));
  }

  /**
   * Method under test:
   * {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  void testListWorkflowInstances3() {
    // Arrange
    when(workflowInstQueryRepository.findAllByIdAndVersion(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    ArrayList<Object> objectList = new ArrayList<>();
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(objectList);

    // Act
    List<WorkflowInstView> actualListWorkflowInstancesResult = monitoringService.listWorkflowInstances("42", null, 1L);

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllByIdAndVersion(eq("42"), eq("1"));
    assertTrue(actualListWorkflowInstancesResult.isEmpty());
    assertSame(objectList, actualListWorkflowInstancesResult);
  }

  /**
   * Method under test:
   * {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  void testListWorkflowInstances4() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any())).thenReturn(new ArrayList<>());
    ArrayList<Object> objectList = new ArrayList<>();
    when(objectConverter.convertCollection(Mockito.<List<Object>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(objectList);

    // Act
    List<WorkflowInstView> actualListWorkflowInstancesResult = monitoringService.listWorkflowInstances("42", null,
        null);

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllById(eq("42"));
    assertTrue(actualListWorkflowInstancesResult.isEmpty());
    assertSame(objectList, actualListWorkflowInstancesResult);
  }

  /**
   * Method under test:
   * {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  void testListWorkflowInstances5() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.listWorkflowInstances("42", null, null));
    verify(workflowInstQueryRepository).findAllById(eq("42"));
  }

  /**
   * Method under test:
   * {@link MonitoringService#listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter)}
   */
  @Test
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
   * Method under test:
   * {@link MonitoringService#listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter)}
   */
  @Test
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
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  void testGetWorkflowDefinition() {
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
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  void testGetWorkflowDefinition2() {
    // Arrange
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any())).thenReturn(null);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.getWorkflowDefinition("42"));
    verify(workflowDirectedGraphService).getDirectedGraph(eq("42"));
  }

  /**
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  void testGetWorkflowDefinition3() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(new HashMap<>());
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(workflowDirectedGraph.getVariables()).thenReturn(stringObjectMap);
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
    Map<String, Object> variables = actualWorkflowDefinition.getVariables();
    assertTrue(variables.isEmpty());
    assertSame(stringObjectMap, variables);
  }

  /**
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  void testGetWorkflowDefinition4() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    Class<Object> clz = Object.class;
    workflowNode.wrappedType(clz);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("foo", workflowNode);
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new WorkflowDirectedGraph.NodeChildren());
    ArrayList<String> stringList = new ArrayList<>();
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(stringList);
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(workflowDirectedGraph.getVariables()).thenReturn(stringObjectMap);
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
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("OBJECT", getResult.getType());
    assertEquals("foo", getResult.getNodeId());
    assertEquals(1L, actualWorkflowDefinition.getVersion().longValue());
    assertTrue(getResult.getChildren().isEmpty());
    List<String> parents = getResult.getParents();
    assertTrue(parents.isEmpty());
    Map<String, Object> variables = actualWorkflowDefinition.getVariables();
    assertTrue(variables.isEmpty());
    assertSame(stringList, parents);
    assertSame(stringObjectMap, variables);
  }

  /**
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  void testGetWorkflowDefinition5() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(workflowNode.getWrappedType()).thenReturn(forNameResult);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("foo", workflowNode);
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new WorkflowDirectedGraph.NodeChildren());
    ArrayList<String> stringList = new ArrayList<>();
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(stringList);
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(workflowDirectedGraph.getVariables()).thenReturn(stringObjectMap);
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
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("OBJECT", getResult.getType());
    assertEquals("foo", getResult.getNodeId());
    assertEquals(1L, actualWorkflowDefinition.getVersion().longValue());
    assertTrue(getResult.getChildren().isEmpty());
    List<String> parents = getResult.getParents();
    assertTrue(parents.isEmpty());
    Map<String, Object> variables = actualWorkflowDefinition.getVariables();
    assertTrue(variables.isEmpty());
    assertSame(stringList, parents);
    assertSame(stringObjectMap, variables);
  }

  /**
   * Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  void testGetWorkflowDefinition6() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    Class<WorkflowDirectedGraph> forNameResult = WorkflowDirectedGraph.class;
    Mockito.<Class<?>>when(workflowNode.getWrappedType()).thenReturn(forNameResult);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("foo", workflowNode);
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new WorkflowDirectedGraph.NodeChildren());
    ArrayList<String> stringList = new ArrayList<>();
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(stringList);
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(workflowDirectedGraph.getVariables()).thenReturn(stringObjectMap);
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
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("WORKFLOW_DIRECTED_GRAPH", getResult.getType());
    assertEquals("foo", getResult.getNodeId());
    assertEquals(1L, actualWorkflowDefinition.getVersion().longValue());
    assertTrue(getResult.getChildren().isEmpty());
    List<String> parents = getResult.getParents();
    assertTrue(parents.isEmpty());
    Map<String, Object> variables = actualWorkflowDefinition.getVariables();
    assertTrue(variables.isEmpty());
    assertSame(stringList, parents);
    assertSame(stringObjectMap, variables);
  }

  /**
   * Method under test:
   * {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  void testGetWorkflowDefinition7() {
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
   * Method under test:
   * {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  void testGetWorkflowDefinition8() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(new HashMap<>());
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(workflowDirectedGraph.getVariables()).thenReturn(stringObjectMap);
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
    Map<String, Object> variables = actualWorkflowDefinition.getVariables();
    assertTrue(variables.isEmpty());
    assertSame(stringObjectMap, variables);
  }

  /**
   * Method under test:
   * {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  void testGetWorkflowDefinition9() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    Class<Object> clz = Object.class;
    workflowNode.wrappedType(clz);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("foo", workflowNode);
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new WorkflowDirectedGraph.NodeChildren());
    ArrayList<String> stringList = new ArrayList<>();
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(stringList);
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(workflowDirectedGraph.getVariables()).thenReturn(stringObjectMap);
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
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("OBJECT", getResult.getType());
    assertEquals("foo", getResult.getNodeId());
    assertEquals(1L, actualWorkflowDefinition.getVersion().longValue());
    assertTrue(getResult.getChildren().isEmpty());
    List<String> parents = getResult.getParents();
    assertTrue(parents.isEmpty());
    Map<String, Object> variables = actualWorkflowDefinition.getVariables();
    assertTrue(variables.isEmpty());
    assertSame(stringList, parents);
    assertSame(stringObjectMap, variables);
  }

  /**
   * Method under test:
   * {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  void testGetWorkflowDefinition10() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(workflowNode.getWrappedType()).thenReturn(forNameResult);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("foo", workflowNode);
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new WorkflowDirectedGraph.NodeChildren());
    ArrayList<String> stringList = new ArrayList<>();
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(stringList);
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(workflowDirectedGraph.getVariables()).thenReturn(stringObjectMap);
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
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("OBJECT", getResult.getType());
    assertEquals("foo", getResult.getNodeId());
    assertEquals(1L, actualWorkflowDefinition.getVersion().longValue());
    assertTrue(getResult.getChildren().isEmpty());
    List<String> parents = getResult.getParents();
    assertTrue(parents.isEmpty());
    Map<String, Object> variables = actualWorkflowDefinition.getVariables();
    assertTrue(variables.isEmpty());
    assertSame(stringList, parents);
    assertSame(stringObjectMap, variables);
  }

  /**
   * Method under test:
   * {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  void testGetWorkflowDefinition11() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    Class<WorkflowDirectedGraph> forNameResult = WorkflowDirectedGraph.class;
    Mockito.<Class<?>>when(workflowNode.getWrappedType()).thenReturn(forNameResult);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("foo", workflowNode);
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new WorkflowDirectedGraph.NodeChildren());
    ArrayList<String> stringList = new ArrayList<>();
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(stringList);
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    HashMap<String, Object> stringObjectMap = new HashMap<>();
    when(workflowDirectedGraph.getVariables()).thenReturn(stringObjectMap);
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
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("WORKFLOW_DIRECTED_GRAPH", getResult.getType());
    assertEquals("foo", getResult.getNodeId());
    assertEquals(1L, actualWorkflowDefinition.getVersion().longValue());
    assertTrue(getResult.getChildren().isEmpty());
    List<String> parents = getResult.getParents();
    assertTrue(parents.isEmpty());
    Map<String, Object> variables = actualWorkflowDefinition.getVariables();
    assertTrue(variables.isEmpty());
    assertSame(stringList, parents);
    assertSame(stringObjectMap, variables);
  }

  /**
   * Method under test:
   * {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  void testGetWorkflowDefinition12() {
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
   * Method under test:
   * {@link MonitoringService#listWorkflowInstanceGlobalVars(String, String, Instant, Instant)}
   */
  @Test
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
   * Method under test:
   * {@link MonitoringService#listWorkflowInstanceGlobalVars(String, String, Instant, Instant)}
   */
  @Test
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
