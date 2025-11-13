package com.symphony.bdk.workflow.monitoring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.retry.RetryWithRecoveryBuilder;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.SessionApi;
import com.symphony.bdk.workflow.api.v1.dto.NodeView;
import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstLifeCycleFilter;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.converter.BiConverter;
import com.symphony.bdk.workflow.converter.Converter;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph.NodeChildren;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.camunda.monitoring.repository.VariableCmdaApiQueryRepository;
import com.symphony.bdk.workflow.engine.camunda.monitoring.repository.WorkflowCmdaApiQueryRepository;
import com.symphony.bdk.workflow.engine.camunda.monitoring.repository.WorkflowInstCmdaApiQueryRepository;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.monitoring.repository.ActivityQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.VariableQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.WorkflowInstQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.WorkflowQueryRepository;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.camunda.bpm.engine.impl.HistoryServiceImpl;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.RuntimeServiceImpl;
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
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class MonitoringServiceDiffblueTest {
  @MockBean private ActivityQueryRepository activityQueryRepository;

  @Autowired private MonitoringService monitoringService;

  @MockBean private ObjectConverter objectConverter;

  @MockBean private VariableQueryRepository variableQueryRepository;

  @MockBean private VersionedWorkflowRepository versionedWorkflowRepository;

  @MockBean private WorkflowDirectedGraphService workflowDirectedGraphService;

  @MockBean private WorkflowInstQueryRepository workflowInstQueryRepository;

  @MockBean private WorkflowQueryRepository workflowQueryRepository;

  /**
   * Test {@link MonitoringService#listAllWorkflows()}.
   *
   * <p>Method under test: {@link MonitoringService#listAllWorkflows()}
   */
  @Test
  @DisplayName("Test listAllWorkflows()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MonitoringService.listAllWorkflows()"})
  void testListAllWorkflows() {
    // Arrange
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    when(versionedWorkflowRepository.findByActiveTrue()).thenReturn(new ArrayList<>());

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.listAllWorkflows());
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByActiveTrue();
  }

  /**
   * Test {@link MonitoringService#listAllWorkflows()}.
   *
   * <ul>
   *   <li>Given {@link ObjectConverter} {@link ObjectConverter#convertCollection(List, Class)}
   *       return {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#listAllWorkflows()}
   */
  @Test
  @DisplayName(
      "Test listAllWorkflows(); given ObjectConverter convertCollection(List, Class) return ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MonitoringService.listAllWorkflows()"})
  void testListAllWorkflows_givenObjectConverterConvertCollectionReturnArrayList() {
    // Arrange
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
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
   * Test {@link MonitoringService#listAllWorkflows()}.
   *
   * <ul>
   *   <li>Given {@link ObjectConverter}.
   *   <li>Then throw {@link NotFoundException}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#listAllWorkflows()}
   */
  @Test
  @DisplayName("Test listAllWorkflows(); given ObjectConverter; then throw NotFoundException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MonitoringService.listAllWorkflows()"})
  void testListAllWorkflows_givenObjectConverter_thenThrowNotFoundException() {
    // Arrange
    when(versionedWorkflowRepository.findByActiveTrue())
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.listAllWorkflows());
    verify(versionedWorkflowRepository).findByActiveTrue();
  }

  /**
   * Test {@link MonitoringService#listAllWorkflows()}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowCmdaApiQueryRepository#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#listAllWorkflows()}
   */
  @Test
  @DisplayName("Test listAllWorkflows(); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MonitoringService.listAllWorkflows()"})
  void testListAllWorkflows_thenCallsFindAll() {
    // Arrange
    WorkflowCmdaApiQueryRepository workflowQueryRepository =
        mock(WorkflowCmdaApiQueryRepository.class);
    when(workflowQueryRepository.findAll()).thenReturn(new ArrayList<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository =
        Optional.of(mock(VersionedWorkflowRepository.class));
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService =
        new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowDirectedGraphService workflowDirectedGraphService =
        new WorkflowDirectedGraphService(
            versionedWorkflowRepository, sessionService, objectConverter);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters2 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters2 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter2 =
        new DefaultObjectConverter(converters2, optionalBiConverters2);

    WorkflowInstCmdaApiQueryRepository workflowInstQueryRepository =
        new WorkflowInstCmdaApiQueryRepository(
            repositoryService, historyService, runtimeService, objectConverter2);
    ActivityQueryRepository activityQueryRepository = mock(ActivityQueryRepository.class);
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService2 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService2 = new RuntimeServiceImpl();
    ArrayList<Converter> converters3 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters3 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter3 =
        new DefaultObjectConverter(converters3, optionalBiConverters3);

    VariableCmdaApiQueryRepository variableQueryRepository =
        new VariableCmdaApiQueryRepository(
            repositoryService2, historyService2, runtimeService2, objectConverter3);
    ArrayList<Converter> converters4 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters4 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter4 =
        new DefaultObjectConverter(converters4, optionalBiConverters4);
    Optional<VersionedWorkflowRepository> optionalVersionedRepository = Optional.empty();

    MonitoringService monitoringService =
        new MonitoringService(
            workflowDirectedGraphService,
            workflowQueryRepository,
            workflowInstQueryRepository,
            activityQueryRepository,
            variableQueryRepository,
            objectConverter4,
            optionalVersionedRepository);

    // Act
    List<WorkflowView> actualListAllWorkflowsResult = monitoringService.listAllWorkflows();

    // Assert
    verify(workflowQueryRepository).findAll();
    assertTrue(actualListAllWorkflowsResult.isEmpty());
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstances(String, String, Long)}.
   *
   * <p>Method under test: {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  @DisplayName("Test listWorkflowInstances(String, String, Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MonitoringService.listWorkflowInstances(String, String, Long)"})
  void testListWorkflowInstances() {
    // Arrange
    when(workflowInstQueryRepository.findAllByIdAndVersion(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        NotFoundException.class, () -> monitoringService.listWorkflowInstances("42", null, 1L));
    verify(workflowInstQueryRepository).findAllByIdAndVersion("42", "1");
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstances(String, String, Long)}.
   *
   * <p>Method under test: {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  @DisplayName("Test listWorkflowInstances(String, String, Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MonitoringService.listWorkflowInstances(String, String, Long)"})
  void testListWorkflowInstances2() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        NotFoundException.class, () -> monitoringService.listWorkflowInstances("42", null, null));
    verify(workflowInstQueryRepository).findAllById("42");
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstances(String, String, Long)}.
   *
   * <p>Method under test: {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  @DisplayName("Test listWorkflowInstances(String, String, Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MonitoringService.listWorkflowInstances(String, String, Long)"})
  void testListWorkflowInstances3() {
    // Arrange
    when(workflowInstQueryRepository.findAllByIdAndStatusAndVersion(
            Mockito.<String>any(), Mockito.<StatusEnum>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstView> actualListWorkflowInstancesResult =
        monitoringService.listWorkflowInstances("42", "ACTIVE", 1L);

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository)
        .findAllByIdAndStatusAndVersion("42", StatusEnum.PENDING, "1");
    assertTrue(actualListWorkflowInstancesResult.isEmpty());
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstances(String, String, Long)}.
   *
   * <p>Method under test: {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  @DisplayName("Test listWorkflowInstances(String, String, Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MonitoringService.listWorkflowInstances(String, String, Long)"})
  void testListWorkflowInstances4() {
    // Arrange
    when(workflowInstQueryRepository.findAllByIdAndStatusAndVersion(
            Mockito.<String>any(), Mockito.<StatusEnum>any(), Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        NotFoundException.class, () -> monitoringService.listWorkflowInstances("42", "ACTIVE", 1L));
    verify(workflowInstQueryRepository)
        .findAllByIdAndStatusAndVersion("42", StatusEnum.PENDING, "1");
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstances(String, String, Long)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowInstQueryRepository#findAllById(String)}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  @DisplayName("Test listWorkflowInstances(String, String, Long); then calls findAllById(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MonitoringService.listWorkflowInstances(String, String, Long)"})
  void testListWorkflowInstances_thenCallsFindAllById() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstView> actualListWorkflowInstancesResult =
        monitoringService.listWorkflowInstances("42", null, null);

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllById("42");
    assertTrue(actualListWorkflowInstancesResult.isEmpty());
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstances(String, String, Long)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowInstQueryRepository#findAllByIdAndStatus(String, StatusEnum)}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  @DisplayName(
      "Test listWorkflowInstances(String, String, Long); then calls findAllByIdAndStatus(String, StatusEnum)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MonitoringService.listWorkflowInstances(String, String, Long)"})
  void testListWorkflowInstances_thenCallsFindAllByIdAndStatus() {
    // Arrange
    when(workflowInstQueryRepository.findAllByIdAndStatus(
            Mockito.<String>any(), Mockito.<StatusEnum>any()))
        .thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstView> actualListWorkflowInstancesResult =
        monitoringService.listWorkflowInstances("42", "ACTIVE", null);

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllByIdAndStatus("42", StatusEnum.PENDING);
    assertTrue(actualListWorkflowInstancesResult.isEmpty());
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstances(String, String, Long)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowInstQueryRepository#findAllByIdAndVersion(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#listWorkflowInstances(String, String, Long)}
   */
  @Test
  @DisplayName(
      "Test listWorkflowInstances(String, String, Long); then calls findAllByIdAndVersion(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List MonitoringService.listWorkflowInstances(String, String, Long)"})
  void testListWorkflowInstances_thenCallsFindAllByIdAndVersion() {
    // Arrange
    when(workflowInstQueryRepository.findAllByIdAndVersion(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act
    List<WorkflowInstView> actualListWorkflowInstancesResult =
        monitoringService.listWorkflowInstances("42", null, 1L);

    // Assert
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllByIdAndVersion("42", "1");
    assertTrue(actualListWorkflowInstancesResult.isEmpty());
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstanceNodes(String, String,
   * WorkflowInstLifeCycleFilter)}.
   *
   * <p>Method under test: {@link MonitoringService#listWorkflowInstanceNodes(String, String,
   * WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName("Test listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesStateView MonitoringService.listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter)"
  })
  void testListWorkflowInstanceNodes() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    WorkflowInstLifeCycleFilter lifeCycleFilter =
        new WorkflowInstLifeCycleFilter(
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () -> monitoringService.listWorkflowInstanceNodes("42", "42", lifeCycleFilter));
    verify(workflowInstQueryRepository).findAllById("42");
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstanceNodes(String, String,
   * WorkflowInstLifeCycleFilter)}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectConverter#convertCollection(List, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#listWorkflowInstanceNodes(String, String,
   * WorkflowInstLifeCycleFilter)}
   */
  @Test
  @DisplayName(
      "Test listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter); then calls convertCollection(List, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesStateView MonitoringService.listWorkflowInstanceNodes(String, String, WorkflowInstLifeCycleFilter)"
  })
  void testListWorkflowInstanceNodes_thenCallsConvertCollection() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());
    WorkflowInstLifeCycleFilter lifeCycleFilter =
        new WorkflowInstLifeCycleFilter(
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () -> monitoringService.listWorkflowInstanceNodes("42", "42", lifeCycleFilter));
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllById("42");
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String) with 'workflowId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId() {
    // Arrange
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.getWorkflowDefinition("42"));
    verify(workflowDirectedGraphService).getDirectedGraph("42");
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String) with 'workflowId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId2() {
    // Arrange
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any())).thenReturn(null);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.getWorkflowDefinition("42"));
    verify(workflowDirectedGraphService).getDirectedGraph("42");
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String) with 'workflowId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId3() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getDictionary())
        .thenThrow(new NotFoundException("An error occurred"));
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(workflowDirectedGraph);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.getWorkflowDefinition("42"));
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String) with 'workflowId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId4() {
    // Arrange
    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("Key", new WorkflowNode());

    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(workflowDirectedGraph);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.getWorkflowDefinition("42"));
    verify(workflowDirectedGraph).getChildren("Key");
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String) with 'workflowId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId5() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(workflowNode.getWrappedType()).thenReturn(forNameResult);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("Key", workflowNode);

    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new NodeChildren());
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    when(workflowDirectedGraph.getVariables()).thenReturn(new HashMap<>());
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(workflowDirectedGraph);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42");

    // Assert
    verify(workflowDirectedGraph).getChildren("Key");
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents("Key");
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowNode).getWrappedType();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("Key", getResult.getNodeId());
    assertEquals("OBJECT", getResult.getType());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String) with 'workflowId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId6() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    Class<WorkflowDirectedGraph> forNameResult = WorkflowDirectedGraph.class;
    Mockito.<Class<?>>when(workflowNode.getWrappedType()).thenReturn(forNameResult);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("Key", workflowNode);

    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new NodeChildren());
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    when(workflowDirectedGraph.getVariables()).thenReturn(new HashMap<>());
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(workflowDirectedGraph);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42");

    // Assert
    verify(workflowDirectedGraph).getChildren("Key");
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents("Key");
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowNode).getWrappedType();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("Key", getResult.getNodeId());
    assertEquals("WORKFLOW_DIRECTED_GRAPH", getResult.getType());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String) with 'workflowId'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId7() {
    // Arrange
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService =
        new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowDirectedGraphService workflowDirectedGraphService =
        new WorkflowDirectedGraphService(
            versionedWorkflowRepository, sessionService, objectConverter);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters2 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters2 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter2 =
        new DefaultObjectConverter(converters2, optionalBiConverters2);

    WorkflowCmdaApiQueryRepository workflowQueryRepository =
        new WorkflowCmdaApiQueryRepository(
            repositoryService, historyService, runtimeService, objectConverter2);
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService2 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService2 = new RuntimeServiceImpl();
    ArrayList<Converter> converters3 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters3 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter3 =
        new DefaultObjectConverter(converters3, optionalBiConverters3);

    WorkflowInstCmdaApiQueryRepository workflowInstQueryRepository =
        new WorkflowInstCmdaApiQueryRepository(
            repositoryService2, historyService2, runtimeService2, objectConverter3);
    ActivityQueryRepository activityQueryRepository = mock(ActivityQueryRepository.class);
    RepositoryServiceImpl repositoryService3 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService3 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService3 = new RuntimeServiceImpl();
    ArrayList<Converter> converters4 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters4 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter4 =
        new DefaultObjectConverter(converters4, optionalBiConverters4);

    VariableCmdaApiQueryRepository variableQueryRepository =
        new VariableCmdaApiQueryRepository(
            repositoryService3, historyService3, runtimeService3, objectConverter4);
    ArrayList<Converter> converters5 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters5 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter5 =
        new DefaultObjectConverter(converters5, optionalBiConverters5);
    Optional<VersionedWorkflowRepository> optionalVersionedRepository =
        Optional.of(mock(VersionedWorkflowRepository.class));

    MonitoringService monitoringService =
        new MonitoringService(
            workflowDirectedGraphService,
            workflowQueryRepository,
            workflowInstQueryRepository,
            activityQueryRepository,
            variableQueryRepository,
            objectConverter5,
            optionalVersionedRepository);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.getWorkflowDefinition("42"));
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId},
   * {@code version}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    verify(workflowDirectedGraphService).getDirectedGraph("42", 1L);
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    assertNull(actualWorkflowDefinition.getVersion());
    assertTrue(actualWorkflowDefinition.getFlowNodes().isEmpty());
    assertTrue(actualWorkflowDefinition.getVariables().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId},
   * {@code version}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion2() {
    // Arrange
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any(), Mockito.<Long>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.getWorkflowDefinition("42", 1L));
    verify(workflowDirectedGraphService).getDirectedGraph("42", 1L);
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId},
   * {@code version}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion3() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getDictionary())
        .thenThrow(new NotFoundException("An error occurred"));
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(workflowDirectedGraph);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.getWorkflowDefinition("42", 1L));
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    verify(workflowDirectedGraphService).getDirectedGraph("42", 1L);
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId},
   * {@code version}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion4() {
    // Arrange
    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("Key", new WorkflowNode());

    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(workflowDirectedGraph);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.getWorkflowDefinition("42", 1L));
    verify(workflowDirectedGraph).getChildren("Key");
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    verify(workflowDirectedGraphService).getDirectedGraph("42", 1L);
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId},
   * {@code version}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion5() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    Class<Object> clz = Object.class;
    workflowNode.wrappedType(clz);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("Key", workflowNode);

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
    verify(workflowDirectedGraph).getChildren("Key");
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents("Key");
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    verify(workflowDirectedGraphService).getDirectedGraph("42", 1L);
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("Key", getResult.getNodeId());
    assertEquals("OBJECT", getResult.getType());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId},
   * {@code version}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion6() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    Class<Object> forNameResult = Object.class;
    Mockito.<Class<?>>when(workflowNode.getWrappedType()).thenReturn(forNameResult);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("Key", workflowNode);

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
    verify(workflowDirectedGraph).getChildren("Key");
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents("Key");
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowNode).getWrappedType();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    verify(workflowDirectedGraphService).getDirectedGraph("42", 1L);
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("Key", getResult.getNodeId());
    assertEquals("OBJECT", getResult.getType());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId},
   * {@code version}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion7() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    Class<WorkflowDirectedGraph> forNameResult = WorkflowDirectedGraph.class;
    Mockito.<Class<?>>when(workflowNode.getWrappedType()).thenReturn(forNameResult);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("Key", workflowNode);

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
    verify(workflowDirectedGraph).getChildren("Key");
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents("Key");
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowNode).getWrappedType();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    verify(workflowDirectedGraphService).getDirectedGraph("42", 1L);
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("Key", getResult.getNodeId());
    assertEquals("WORKFLOW_DIRECTED_GRAPH", getResult.getType());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId},
   * {@code version}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion8() {
    // Arrange
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any())).thenReturn(null);

    // Act and Assert
    assertThrows(
        NotFoundException.class, () -> monitoringService.getWorkflowDefinition("42", null));
    verify(workflowDirectedGraphService).getDirectedGraph("42");
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId},
   * {@code version}.
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName("Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion9() {
    // Arrange
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService =
        new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowDirectedGraphService workflowDirectedGraphService =
        new WorkflowDirectedGraphService(
            versionedWorkflowRepository, sessionService, objectConverter);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters2 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters2 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter2 =
        new DefaultObjectConverter(converters2, optionalBiConverters2);

    WorkflowCmdaApiQueryRepository workflowQueryRepository =
        new WorkflowCmdaApiQueryRepository(
            repositoryService, historyService, runtimeService, objectConverter2);
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService2 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService2 = new RuntimeServiceImpl();
    ArrayList<Converter> converters3 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters3 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter3 =
        new DefaultObjectConverter(converters3, optionalBiConverters3);

    WorkflowInstCmdaApiQueryRepository workflowInstQueryRepository =
        new WorkflowInstCmdaApiQueryRepository(
            repositoryService2, historyService2, runtimeService2, objectConverter3);
    ActivityQueryRepository activityQueryRepository = mock(ActivityQueryRepository.class);
    RepositoryServiceImpl repositoryService3 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService3 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService3 = new RuntimeServiceImpl();
    ArrayList<Converter> converters4 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters4 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter4 =
        new DefaultObjectConverter(converters4, optionalBiConverters4);

    VariableCmdaApiQueryRepository variableQueryRepository =
        new VariableCmdaApiQueryRepository(
            repositoryService3, historyService3, runtimeService3, objectConverter4);
    ArrayList<Converter> converters5 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters5 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter5 =
        new DefaultObjectConverter(converters5, optionalBiConverters5);
    Optional<VersionedWorkflowRepository> optionalVersionedRepository =
        Optional.of(mock(VersionedWorkflowRepository.class));

    MonitoringService monitoringService =
        new MonitoringService(
            workflowDirectedGraphService,
            workflowQueryRepository,
            workflowInstQueryRepository,
            activityQueryRepository,
            variableQueryRepository,
            objectConverter5,
            optionalVersionedRepository);

    // Act and Assert
    assertThrows(NotFoundException.class, () -> monitoringService.getWorkflowDefinition("42", 1L));
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId},
   * {@code version}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectConverter#convert(Object, Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName(
      "Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'; then calls convert(Object, Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion_thenCallsConvert() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);

    VersionedWorkflow versionedWorkflow2 = new VersionedWorkflow();
    versionedWorkflow2.setActive(true);
    versionedWorkflow2.setCreatedBy(1L);
    versionedWorkflow2.setDeploymentId("42");
    versionedWorkflow2.setDescription("The characteristics of someone or something");
    versionedWorkflow2.setEtag(1L);
    versionedWorkflow2.setId("42");
    versionedWorkflow2.setPublished(true);
    versionedWorkflow2.setSwadl("Swadl");
    versionedWorkflow2.setVersion(1L);
    versionedWorkflow2.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult2 = Optional.of(versionedWorkflow2);

    VersionedWorkflowRepository versionedWorkflowRepository =
        mock(VersionedWorkflowRepository.class);
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(ofResult);
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult2);
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository2 =
        Optional.of(versionedWorkflowRepository);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), eq(Workflow.class)))
        .thenReturn(workflow);
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService =
        new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());

    WorkflowDirectedGraphService workflowDirectedGraphService =
        new WorkflowDirectedGraphService(
            versionedWorkflowRepository2, sessionService, objectConverter);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter2 =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowCmdaApiQueryRepository workflowQueryRepository =
        new WorkflowCmdaApiQueryRepository(
            repositoryService, historyService, runtimeService, objectConverter2);
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService2 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService2 = new RuntimeServiceImpl();
    ArrayList<Converter> converters2 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters2 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter3 =
        new DefaultObjectConverter(converters2, optionalBiConverters2);

    WorkflowInstCmdaApiQueryRepository workflowInstQueryRepository =
        new WorkflowInstCmdaApiQueryRepository(
            repositoryService2, historyService2, runtimeService2, objectConverter3);
    ActivityQueryRepository activityQueryRepository = mock(ActivityQueryRepository.class);
    RepositoryServiceImpl repositoryService3 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService3 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService3 = new RuntimeServiceImpl();
    ArrayList<Converter> converters3 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters3 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter4 =
        new DefaultObjectConverter(converters3, optionalBiConverters3);

    VariableCmdaApiQueryRepository variableQueryRepository =
        new VariableCmdaApiQueryRepository(
            repositoryService3, historyService3, runtimeService3, objectConverter4);
    ArrayList<Converter> converters4 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters4 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter5 =
        new DefaultObjectConverter(converters4, optionalBiConverters4);
    Optional<VersionedWorkflowRepository> optionalVersionedRepository =
        Optional.of(mock(VersionedWorkflowRepository.class));

    MonitoringService monitoringService =
        new MonitoringService(
            workflowDirectedGraphService,
            workflowQueryRepository,
            workflowInstQueryRepository,
            activityQueryRepository,
            variableQueryRepository,
            objectConverter5,
            optionalVersionedRepository);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42", 1L);

    // Assert
    verify(objectConverter, atLeast(1))
        .convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    assertEquals(1L, actualWorkflowDefinition.getVersion().longValue());
    assertTrue(actualWorkflowDefinition.getFlowNodes().isEmpty());
    assertTrue(actualWorkflowDefinition.getVariables().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId},
   * {@code version}.
   *
   * <ul>
   *   <li>Then return Version longValue is one.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName(
      "Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'; then return Version longValue is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
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
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    verify(workflowDirectedGraphService).getDirectedGraph("42", 1L);
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    assertEquals(1L, actualWorkflowDefinition.getVersion().longValue());
    assertTrue(actualWorkflowDefinition.getFlowNodes().isEmpty());
    assertTrue(actualWorkflowDefinition.getVariables().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String, Long)} with {@code workflowId},
   * {@code version}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Version is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String, Long)}
   */
  @Test
  @DisplayName(
      "Test getWorkflowDefinition(String, Long) with 'workflowId', 'version'; when 'null'; then return Version is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String, Long)"})
  void testGetWorkflowDefinitionWithWorkflowIdVersion_whenNull_thenReturnVersionIsNull() {
    // Arrange
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));

    // Act
    WorkflowNodesView actualWorkflowDefinition =
        monitoringService.getWorkflowDefinition("42", null);

    // Assert
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    assertNull(actualWorkflowDefinition.getVersion());
    assertTrue(actualWorkflowDefinition.getFlowNodes().isEmpty());
    assertTrue(actualWorkflowDefinition.getVariables().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode} (default constructor) wrappedType {@link Object}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName(
      "Test getWorkflowDefinition(String) with 'workflowId'; given WorkflowNode (default constructor) wrappedType Object")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId_givenWorkflowNodeWrappedTypeObject() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    Class<Object> clz = Object.class;
    workflowNode.wrappedType(clz);

    HashMap<String, WorkflowNode> stringWorkflowNodeMap = new HashMap<>();
    stringWorkflowNodeMap.put("Key", workflowNode);

    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getChildren(Mockito.<String>any())).thenReturn(new NodeChildren());
    when(workflowDirectedGraph.getParents(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(stringWorkflowNodeMap);
    when(workflowDirectedGraph.getVariables()).thenReturn(new HashMap<>());
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(workflowDirectedGraph);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42");

    // Assert
    verify(workflowDirectedGraph).getChildren("Key");
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents("Key");
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    List<NodeView> flowNodes = actualWorkflowDefinition.getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("Key", getResult.getNodeId());
    assertEquals("OBJECT", getResult.getType());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectConverter#convert(Object, Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName(
      "Test getWorkflowDefinition(String) with 'workflowId'; then calls convert(Object, Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId_thenCallsConvert() {
    // Arrange
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("42");
    versionedWorkflow.setDescription("The characteristics of someone or something");
    versionedWorkflow.setEtag(1L);
    versionedWorkflow.setId("42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("Swadl");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setWorkflowId("42");
    Optional<VersionedWorkflow> ofResult = Optional.of(versionedWorkflow);

    VersionedWorkflowRepository versionedWorkflowRepository =
        mock(VersionedWorkflowRepository.class);
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(ofResult);
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository2 =
        Optional.of(versionedWorkflowRepository);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    ObjectConverter objectConverter = mock(ObjectConverter.class);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), eq(Workflow.class)))
        .thenReturn(workflow);
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService =
        new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());

    WorkflowDirectedGraphService workflowDirectedGraphService =
        new WorkflowDirectedGraphService(
            versionedWorkflowRepository2, sessionService, objectConverter);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter2 =
        new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowCmdaApiQueryRepository workflowQueryRepository =
        new WorkflowCmdaApiQueryRepository(
            repositoryService, historyService, runtimeService, objectConverter2);
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService2 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService2 = new RuntimeServiceImpl();
    ArrayList<Converter> converters2 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters2 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter3 =
        new DefaultObjectConverter(converters2, optionalBiConverters2);

    WorkflowInstCmdaApiQueryRepository workflowInstQueryRepository =
        new WorkflowInstCmdaApiQueryRepository(
            repositoryService2, historyService2, runtimeService2, objectConverter3);
    ActivityQueryRepository activityQueryRepository = mock(ActivityQueryRepository.class);
    RepositoryServiceImpl repositoryService3 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService3 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService3 = new RuntimeServiceImpl();
    ArrayList<Converter> converters3 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters3 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter4 =
        new DefaultObjectConverter(converters3, optionalBiConverters3);

    VariableCmdaApiQueryRepository variableQueryRepository =
        new VariableCmdaApiQueryRepository(
            repositoryService3, historyService3, runtimeService3, objectConverter4);
    ArrayList<Converter> converters4 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters4 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter5 =
        new DefaultObjectConverter(converters4, optionalBiConverters4);
    Optional<VersionedWorkflowRepository> optionalVersionedRepository =
        Optional.of(mock(VersionedWorkflowRepository.class));

    MonitoringService monitoringService =
        new MonitoringService(
            workflowDirectedGraphService,
            workflowQueryRepository,
            workflowInstQueryRepository,
            activityQueryRepository,
            variableQueryRepository,
            objectConverter5,
            optionalVersionedRepository);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42");

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    assertEquals(1L, actualWorkflowDefinition.getVersion().longValue());
    assertTrue(actualWorkflowDefinition.getFlowNodes().isEmpty());
    assertTrue(actualWorkflowDefinition.getVariables().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   *
   * <ul>
   *   <li>Then return Version is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName(
      "Test getWorkflowDefinition(String) with 'workflowId'; then return Version is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId_thenReturnVersionIsNull() {
    // Arrange
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42");

    // Assert
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    assertNull(actualWorkflowDefinition.getVersion());
    assertTrue(actualWorkflowDefinition.getFlowNodes().isEmpty());
    assertTrue(actualWorkflowDefinition.getVariables().isEmpty());
  }

  /**
   * Test {@link MonitoringService#getWorkflowDefinition(String)} with {@code workflowId}.
   *
   * <ul>
   *   <li>Then return Version longValue is one.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#getWorkflowDefinition(String)}
   */
  @Test
  @DisplayName(
      "Test getWorkflowDefinition(String) with 'workflowId'; then return Version longValue is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodesView MonitoringService.getWorkflowDefinition(String)"})
  void testGetWorkflowDefinitionWithWorkflowId_thenReturnVersionLongValueIsOne() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(new HashMap<>());
    when(workflowDirectedGraph.getVariables()).thenReturn(new HashMap<>());
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(workflowDirectedGraph);

    // Act
    WorkflowNodesView actualWorkflowDefinition = monitoringService.getWorkflowDefinition("42");

    // Assert
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    assertEquals("42", actualWorkflowDefinition.getWorkflowId());
    assertEquals(1L, actualWorkflowDefinition.getVersion().longValue());
    assertTrue(actualWorkflowDefinition.getFlowNodes().isEmpty());
    assertTrue(actualWorkflowDefinition.getVariables().isEmpty());
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstanceGlobalVars(String, String, Instant,
   * Instant)}.
   *
   * <p>Method under test: {@link MonitoringService#listWorkflowInstanceGlobalVars(String, String,
   * Instant, Instant)}
   */
  @Test
  @DisplayName("Test listWorkflowInstanceGlobalVars(String, String, Instant, Instant)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List MonitoringService.listWorkflowInstanceGlobalVars(String, String, Instant, Instant)"
  })
  void testListWorkflowInstanceGlobalVars() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any()))
        .thenThrow(new NotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () ->
            monitoringService.listWorkflowInstanceGlobalVars(
                "42",
                "42",
                LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
                LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    verify(workflowInstQueryRepository).findAllById("42");
  }

  /**
   * Test {@link MonitoringService#listWorkflowInstanceGlobalVars(String, String, Instant,
   * Instant)}.
   *
   * <ul>
   *   <li>Then calls {@link ObjectConverter#convertCollection(List, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link MonitoringService#listWorkflowInstanceGlobalVars(String, String,
   * Instant, Instant)}
   */
  @Test
  @DisplayName(
      "Test listWorkflowInstanceGlobalVars(String, String, Instant, Instant); then calls convertCollection(List, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List MonitoringService.listWorkflowInstanceGlobalVars(String, String, Instant, Instant)"
  })
  void testListWorkflowInstanceGlobalVars_thenCallsConvertCollection() {
    // Arrange
    when(workflowInstQueryRepository.findAllById(Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    when(objectConverter.convertCollection(Mockito.<List<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(new ArrayList<>());

    // Act and Assert
    assertThrows(
        NotFoundException.class,
        () ->
            monitoringService.listWorkflowInstanceGlobalVars(
                "42",
                "42",
                LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
                LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    verify(objectConverter).convertCollection(isA(List.class), isA(Class.class));
    verify(workflowInstQueryRepository).findAllById("42");
  }
}
