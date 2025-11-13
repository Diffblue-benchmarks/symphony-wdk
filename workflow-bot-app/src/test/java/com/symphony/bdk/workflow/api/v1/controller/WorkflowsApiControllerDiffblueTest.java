package com.symphony.bdk.workflow.api.v1.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.retry.RetryWithRecoveryBuilder;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.SessionApi;
import com.symphony.bdk.workflow.api.v1.dto.NodeView;
import com.symphony.bdk.workflow.api.v1.dto.VariableView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowExecutionRequest;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstLifeCycleFilter;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesStateView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowNodesView.WorkflowNodesViewBuilder;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.converter.BiConverter;
import com.symphony.bdk.workflow.converter.Converter;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.ExecutionParameters;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph.NodeChildren;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.camunda.CamundaEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;
import com.symphony.bdk.workflow.engine.camunda.monitoring.repository.VariableCmdaApiQueryRepository;
import com.symphony.bdk.workflow.engine.camunda.monitoring.repository.WorkflowCmdaApiQueryRepository;
import com.symphony.bdk.workflow.engine.camunda.monitoring.repository.WorkflowInstCmdaApiQueryRepository;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;
import com.symphony.bdk.workflow.exception.UnauthorizedException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.monitoring.repository.ActivityQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.WorkflowInstQueryRepository;
import com.symphony.bdk.workflow.monitoring.service.MonitoringService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowsApiController.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class WorkflowsApiControllerDiffblueTest {
  @MockBean private MonitoringService monitoringService;

  @MockBean private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  @Autowired private WorkflowsApiController workflowsApiController;

  /**
   * Test {@link WorkflowsApiController#executeWorkflowById(String, String,
   * WorkflowExecutionRequest)}.
   *
   * <p>Method under test: {@link WorkflowsApiController#executeWorkflowById(String, String,
   * WorkflowExecutionRequest)}
   */
  @Test
  @DisplayName("Test executeWorkflowById(String, String, WorkflowExecutionRequest)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApiController.executeWorkflowById(String, String, WorkflowExecutionRequest)"
  })
  void testExecuteWorkflowById() throws UnauthorizedException {
    // Arrange
    doNothing()
        .when(workflowEngine)
        .execute(Mockito.<String>any(), Mockito.<ExecutionParameters>any());

    WorkflowExecutionRequest arguments = new WorkflowExecutionRequest();
    arguments.setArgs(new HashMap<>());

    // Act
    ResponseEntity<Object> actualExecuteWorkflowByIdResult =
        workflowsApiController.executeWorkflowById("ABC123", "42", arguments);

    // Assert
    verify(workflowEngine).execute(eq("42"), isA(ExecutionParameters.class));
    HttpStatusCode statusCode = actualExecuteWorkflowByIdResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertNull(actualExecuteWorkflowByIdResult.getBody());
    assertEquals(204, actualExecuteWorkflowByIdResult.getStatusCodeValue());
    assertEquals(HttpStatus.NO_CONTENT, statusCode);
    assertFalse(actualExecuteWorkflowByIdResult.hasBody());
    assertTrue(actualExecuteWorkflowByIdResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsApiController#listAllWorkflows(String)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowCmdaApiQueryRepository#findAll()}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsApiController#listAllWorkflows(String)}
   */
  @Test
  @DisplayName("Test listAllWorkflows(String); then calls findAll()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity WorkflowsApiController.listAllWorkflows(String)"})
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
    RepositoryServiceImpl repositoryService3 = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService4 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionApi sessionApi2 = new SessionApi(null);
    SessionService sessionService2 =
        new SessionService(sessionApi2, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository2 =
        Optional.of(mock(VersionedWorkflowRepository.class));
    SessionService sessionService3 = new SessionService(null, new RetryWithRecoveryBuilder<>());
    ArrayList<Converter> converters5 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters5 = Optional.empty();

    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(
            versionedWorkflowRepository2,
            sessionService3,
            new DefaultObjectConverter(converters5, optionalBiConverters5));

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService4, builderFactory, sessionService2, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService3, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowsApiController workflowsApiController =
        new WorkflowsApiController(monitoringService, workflowEngine);

    // Act
    ResponseEntity<List<WorkflowView>> actualListAllWorkflowsResult =
        workflowsApiController.listAllWorkflows("ABC123");

    // Assert
    verify(workflowQueryRepository).findAll();
    HttpStatusCode statusCode = actualListAllWorkflowsResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualListAllWorkflowsResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualListAllWorkflowsResult.getBody().isEmpty());
    assertTrue(actualListAllWorkflowsResult.hasBody());
    assertTrue(actualListAllWorkflowsResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsApiController#listAllWorkflows(String)}.
   *
   * <ul>
   *   <li>Then calls {@link VersionedWorkflowRepository#findByActiveTrue()}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsApiController#listAllWorkflows(String)}
   */
  @Test
  @DisplayName("Test listAllWorkflows(String); then calls findByActiveTrue()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity WorkflowsApiController.listAllWorkflows(String)"})
  void testListAllWorkflows_thenCallsFindByActiveTrue() {
    // Arrange
    VersionedWorkflowRepository versionedWorkflowRepository =
        mock(VersionedWorkflowRepository.class);
    when(versionedWorkflowRepository.findByActiveTrue()).thenReturn(new ArrayList<>());
    Optional<VersionedWorkflowRepository> optionalVersionedRepository =
        Optional.of(versionedWorkflowRepository);
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository2 =
        Optional.of(mock(VersionedWorkflowRepository.class));
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.empty();

    WorkflowDirectedGraphService workflowDirectedGraphService =
        new WorkflowDirectedGraphService(
            versionedWorkflowRepository2,
            sessionService,
            new DefaultObjectConverter(converters, optionalBiConverters));
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters2 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters2 = Optional.empty();

    WorkflowCmdaApiQueryRepository workflowQueryRepository =
        new WorkflowCmdaApiQueryRepository(
            repositoryService,
            historyService,
            runtimeService,
            new DefaultObjectConverter(converters2, optionalBiConverters2));
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService2 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService2 = new RuntimeServiceImpl();
    ArrayList<Converter> converters3 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters3 = Optional.empty();

    WorkflowInstCmdaApiQueryRepository workflowInstQueryRepository =
        new WorkflowInstCmdaApiQueryRepository(
            repositoryService2,
            historyService2,
            runtimeService2,
            new DefaultObjectConverter(converters3, optionalBiConverters3));
    ActivityQueryRepository activityQueryRepository = mock(ActivityQueryRepository.class);
    RepositoryServiceImpl repositoryService3 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService3 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService3 = new RuntimeServiceImpl();
    ArrayList<Converter> converters4 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters4 = Optional.empty();

    VariableCmdaApiQueryRepository variableQueryRepository =
        new VariableCmdaApiQueryRepository(
            repositoryService3,
            historyService3,
            runtimeService3,
            new DefaultObjectConverter(converters4, optionalBiConverters4));
    ArrayList<Converter> converters5 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters5 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters5, optionalBiConverters5);

    MonitoringService monitoringService =
        new MonitoringService(
            workflowDirectedGraphService,
            workflowQueryRepository,
            workflowInstQueryRepository,
            activityQueryRepository,
            variableQueryRepository,
            objectConverter,
            optionalVersionedRepository);
    RepositoryServiceImpl repositoryService4 = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService5 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService2 = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository3 = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository3, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService5, builderFactory, sessionService2, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService4, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowsApiController workflowsApiController =
        new WorkflowsApiController(monitoringService, workflowEngine);

    // Act
    ResponseEntity<List<WorkflowView>> actualListAllWorkflowsResult =
        workflowsApiController.listAllWorkflows("ABC123");

    // Assert
    verify(versionedWorkflowRepository).findByActiveTrue();
    HttpStatusCode statusCode = actualListAllWorkflowsResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualListAllWorkflowsResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualListAllWorkflowsResult.getBody().isEmpty());
    assertTrue(actualListAllWorkflowsResult.hasBody());
    assertTrue(actualListAllWorkflowsResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsApiController#listAllWorkflows(String)}.
   *
   * <ul>
   *   <li>Then calls {@link MonitoringService#listAllWorkflows()}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsApiController#listAllWorkflows(String)}
   */
  @Test
  @DisplayName("Test listAllWorkflows(String); then calls listAllWorkflows()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"ResponseEntity WorkflowsApiController.listAllWorkflows(String)"})
  void testListAllWorkflows_thenCallsListAllWorkflows() {
    // Arrange
    when(monitoringService.listAllWorkflows()).thenReturn(new ArrayList<>());

    // Act
    ResponseEntity<List<WorkflowView>> actualListAllWorkflowsResult =
        workflowsApiController.listAllWorkflows("ABC123");

    // Assert
    verify(monitoringService).listAllWorkflows();
    HttpStatusCode statusCode = actualListAllWorkflowsResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualListAllWorkflowsResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualListAllWorkflowsResult.getBody().isEmpty());
    assertTrue(actualListAllWorkflowsResult.hasBody());
    assertTrue(actualListAllWorkflowsResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsApiController#listWorkflowInstances(String, String, String, Long)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowInstQueryRepository#findAllByIdAndVersion(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsApiController#listWorkflowInstances(String, String,
   * String, Long)}
   */
  @Test
  @DisplayName(
      "Test listWorkflowInstances(String, String, String, Long); then calls findAllByIdAndVersion(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApiController.listWorkflowInstances(String, String, String, Long)"
  })
  void testListWorkflowInstances_thenCallsFindAllByIdAndVersion() {
    // Arrange
    WorkflowInstQueryRepository workflowInstQueryRepository =
        mock(WorkflowInstQueryRepository.class);
    when(workflowInstQueryRepository.findAllByIdAndVersion(
            Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new ArrayList<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository =
        Optional.of(mock(VersionedWorkflowRepository.class));
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.empty();

    WorkflowDirectedGraphService workflowDirectedGraphService =
        new WorkflowDirectedGraphService(
            versionedWorkflowRepository,
            sessionService,
            new DefaultObjectConverter(converters, optionalBiConverters));
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters2 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters2 = Optional.empty();

    WorkflowCmdaApiQueryRepository workflowQueryRepository =
        new WorkflowCmdaApiQueryRepository(
            repositoryService,
            historyService,
            runtimeService,
            new DefaultObjectConverter(converters2, optionalBiConverters2));
    ActivityQueryRepository activityQueryRepository = mock(ActivityQueryRepository.class);
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService2 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService2 = new RuntimeServiceImpl();
    ArrayList<Converter> converters3 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters3 = Optional.empty();

    VariableCmdaApiQueryRepository variableQueryRepository =
        new VariableCmdaApiQueryRepository(
            repositoryService2,
            historyService2,
            runtimeService2,
            new DefaultObjectConverter(converters3, optionalBiConverters3));
    ArrayList<Converter> converters4 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters4 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
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
            objectConverter,
            optionalVersionedRepository);
    RepositoryServiceImpl repositoryService3 = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService4 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService2 = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository2 = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository2, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService4, builderFactory, sessionService2, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService3, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowsApiController workflowsApiController =
        new WorkflowsApiController(monitoringService, workflowEngine);

    // Act
    ResponseEntity<List<WorkflowInstView>> actualListWorkflowInstancesResult =
        workflowsApiController.listWorkflowInstances("42", "ABC123", null, 1L);

    // Assert
    verify(workflowInstQueryRepository).findAllByIdAndVersion("42", "1");
    HttpStatusCode statusCode = actualListWorkflowInstancesResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualListWorkflowInstancesResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualListWorkflowInstancesResult.getBody().isEmpty());
    assertTrue(actualListWorkflowInstancesResult.hasBody());
    assertTrue(actualListWorkflowInstancesResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsApiController#listWorkflowInstances(String, String, String, Long)}.
   *
   * <ul>
   *   <li>Then calls {@link MonitoringService#listWorkflowInstances(String, String, Long)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsApiController#listWorkflowInstances(String, String,
   * String, Long)}
   */
  @Test
  @DisplayName(
      "Test listWorkflowInstances(String, String, String, Long); then calls listWorkflowInstances(String, String, Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApiController.listWorkflowInstances(String, String, String, Long)"
  })
  void testListWorkflowInstances_thenCallsListWorkflowInstances() {
    // Arrange
    when(monitoringService.listWorkflowInstances(
            Mockito.<String>any(), Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(new ArrayList<>());

    // Act
    ResponseEntity<List<WorkflowInstView>> actualListWorkflowInstancesResult =
        workflowsApiController.listWorkflowInstances("42", "ABC123", "Status", 1L);

    // Assert
    verify(monitoringService).listWorkflowInstances("42", "Status", 1L);
    HttpStatusCode statusCode = actualListWorkflowInstancesResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualListWorkflowInstancesResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualListWorkflowInstancesResult.getBody().isEmpty());
    assertTrue(actualListWorkflowInstancesResult.hasBody());
    assertTrue(actualListWorkflowInstancesResult.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsApiController#getInstanceState(String, String, String, Instant, Instant,
   * Instant, Instant)}.
   *
   * <p>Method under test: {@link WorkflowsApiController#getInstanceState(String, String, String,
   * Instant, Instant, Instant, Instant)}
   */
  @Test
  @DisplayName("Test getInstanceState(String, String, String, Instant, Instant, Instant, Instant)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApiController.getInstanceState(String, String, String, Instant, Instant, Instant, Instant)"
  })
  void testGetInstanceState() {
    // Arrange
    WorkflowNodesStateView workflowNodesStateView = new WorkflowNodesStateView();
    workflowNodesStateView.setError(new HashMap<>());
    workflowNodesStateView.setGlobalVariables(new VariableView());
    workflowNodesStateView.setNodes(new ArrayList<>());
    when(monitoringService.listWorkflowInstanceNodes(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<WorkflowInstLifeCycleFilter>any()))
        .thenReturn(workflowNodesStateView);

    // Act
    ResponseEntity<WorkflowNodesStateView> actualInstanceState =
        workflowsApiController.getInstanceState(
            "42",
            "42",
            "ABC123",
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(monitoringService)
        .listWorkflowInstanceNodes(eq("42"), eq("42"), isA(WorkflowInstLifeCycleFilter.class));
    HttpStatusCode statusCode = actualInstanceState.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualInstanceState.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualInstanceState.hasBody());
    assertTrue(actualInstanceState.getHeaders().isEmpty());
    assertSame(workflowNodesStateView, actualInstanceState.getBody());
  }

  /**
   * Test {@link WorkflowsApiController#getWorkflowGraphNodes(String, String, Long)}.
   *
   * <ul>
   *   <li>Given {@link VersionedWorkflow} (default constructor) Active is {@code true}.
   *   <li>Then calls {@link ObjectConverter#convert(Object, Object, Class)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsApiController#getWorkflowGraphNodes(String, String,
   * Long)}
   */
  @Test
  @DisplayName(
      "Test getWorkflowGraphNodes(String, String, Long); given VersionedWorkflow (default constructor) Active is 'true'; then calls convert(Object, Object, Class)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApiController.getWorkflowGraphNodes(String, String, Long)"
  })
  void testGetWorkflowGraphNodes_givenVersionedWorkflowActiveIsTrue_thenCallsConvert() {
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
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    WorkflowDirectedGraphService workflowDirectedGraphService =
        new WorkflowDirectedGraphService(
            versionedWorkflowRepository2, sessionService, objectConverter);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.empty();

    WorkflowCmdaApiQueryRepository workflowQueryRepository =
        new WorkflowCmdaApiQueryRepository(
            repositoryService,
            historyService,
            runtimeService,
            new DefaultObjectConverter(converters, optionalBiConverters));
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService2 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService2 = new RuntimeServiceImpl();
    ArrayList<Converter> converters2 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters2 = Optional.empty();

    WorkflowInstCmdaApiQueryRepository workflowInstQueryRepository =
        new WorkflowInstCmdaApiQueryRepository(
            repositoryService2,
            historyService2,
            runtimeService2,
            new DefaultObjectConverter(converters2, optionalBiConverters2));
    ActivityQueryRepository activityQueryRepository = mock(ActivityQueryRepository.class);
    RepositoryServiceImpl repositoryService3 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService3 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService3 = new RuntimeServiceImpl();
    ArrayList<Converter> converters3 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters3 = Optional.empty();

    VariableCmdaApiQueryRepository variableQueryRepository =
        new VariableCmdaApiQueryRepository(
            repositoryService3,
            historyService3,
            runtimeService3,
            new DefaultObjectConverter(converters3, optionalBiConverters3));
    ArrayList<Converter> converters4 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters4 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter2 =
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
            objectConverter2,
            optionalVersionedRepository);
    RepositoryServiceImpl repositoryService4 = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService5 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService2 = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository3 = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository3, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService5, builderFactory, sessionService2, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService4, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowsApiController workflowsApiController =
        new WorkflowsApiController(monitoringService, workflowEngine);

    // Act
    ResponseEntity<WorkflowNodesView> actualWorkflowGraphNodes =
        workflowsApiController.getWorkflowGraphNodes("42", "ABC123", 1L);

    // Assert
    verify(objectConverter, atLeast(1))
        .convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
    HttpStatusCode statusCode = actualWorkflowGraphNodes.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    WorkflowNodesView body = actualWorkflowGraphNodes.getBody();
    assertEquals("42", body.getWorkflowId());
    assertEquals(1L, body.getVersion().longValue());
    assertEquals(200, actualWorkflowGraphNodes.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(body.getFlowNodes().isEmpty());
    assertTrue(body.getVariables().isEmpty());
    assertTrue(actualWorkflowGraphNodes.hasBody());
    assertTrue(actualWorkflowGraphNodes.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsApiController#getWorkflowGraphNodes(String, String, Long)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode} (default constructor) wrappedType {@link Object}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsApiController#getWorkflowGraphNodes(String, String,
   * Long)}
   */
  @Test
  @DisplayName(
      "Test getWorkflowGraphNodes(String, String, Long); given WorkflowNode (default constructor) wrappedType Object")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApiController.getWorkflowGraphNodes(String, String, Long)"
  })
  void testGetWorkflowGraphNodes_givenWorkflowNodeWrappedTypeObject() {
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

    WorkflowDirectedGraphService workflowDirectedGraphService =
        mock(WorkflowDirectedGraphService.class);
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(workflowDirectedGraph);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.empty();

    WorkflowCmdaApiQueryRepository workflowQueryRepository =
        new WorkflowCmdaApiQueryRepository(
            repositoryService,
            historyService,
            runtimeService,
            new DefaultObjectConverter(converters, optionalBiConverters));
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService2 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService2 = new RuntimeServiceImpl();
    ArrayList<Converter> converters2 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters2 = Optional.empty();

    WorkflowInstCmdaApiQueryRepository workflowInstQueryRepository =
        new WorkflowInstCmdaApiQueryRepository(
            repositoryService2,
            historyService2,
            runtimeService2,
            new DefaultObjectConverter(converters2, optionalBiConverters2));
    ActivityQueryRepository activityQueryRepository = mock(ActivityQueryRepository.class);
    RepositoryServiceImpl repositoryService3 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService3 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService3 = new RuntimeServiceImpl();
    ArrayList<Converter> converters3 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters3 = Optional.empty();

    VariableCmdaApiQueryRepository variableQueryRepository =
        new VariableCmdaApiQueryRepository(
            repositoryService3,
            historyService3,
            runtimeService3,
            new DefaultObjectConverter(converters3, optionalBiConverters3));
    ArrayList<Converter> converters4 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters4 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
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
            objectConverter,
            optionalVersionedRepository);
    RepositoryServiceImpl repositoryService4 = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService5 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService5, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService4, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowsApiController workflowsApiController =
        new WorkflowsApiController(monitoringService, workflowEngine);

    // Act
    ResponseEntity<WorkflowNodesView> actualWorkflowGraphNodes =
        workflowsApiController.getWorkflowGraphNodes("42", "ABC123", 1L);

    // Assert
    verify(workflowDirectedGraph).getChildren("Key");
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents("Key");
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    verify(workflowDirectedGraphService).getDirectedGraph("42", 1L);
    List<NodeView> flowNodes = actualWorkflowGraphNodes.getBody().getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("Key", getResult.getNodeId());
    assertEquals("OBJECT", getResult.getType());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link WorkflowsApiController#getWorkflowGraphNodes(String, String, Long)}.
   *
   * <ul>
   *   <li>Then calls {@link MonitoringService#getWorkflowDefinition(String, Long)}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsApiController#getWorkflowGraphNodes(String, String,
   * Long)}
   */
  @Test
  @DisplayName(
      "Test getWorkflowGraphNodes(String, String, Long); then calls getWorkflowDefinition(String, Long)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApiController.getWorkflowGraphNodes(String, String, Long)"
  })
  void testGetWorkflowGraphNodes_thenCallsGetWorkflowDefinition() {
    // Arrange
    WorkflowNodesViewBuilder builderResult = WorkflowNodesView.builder();

    WorkflowNodesViewBuilder flowNodesResult = builderResult.flowNodes(new ArrayList<>());
    when(monitoringService.getWorkflowDefinition(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(
            flowNodesResult.variables(new HashMap<>()).version(1L).workflowId("42").build());

    // Act
    ResponseEntity<WorkflowNodesView> actualWorkflowGraphNodes =
        workflowsApiController.getWorkflowGraphNodes("42", "ABC123", 1L);

    // Assert
    verify(monitoringService).getWorkflowDefinition("42", 1L);
    HttpStatusCode statusCode = actualWorkflowGraphNodes.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    WorkflowNodesView body = actualWorkflowGraphNodes.getBody();
    assertEquals("42", body.getWorkflowId());
    assertEquals(1L, body.getVersion().longValue());
    assertEquals(200, actualWorkflowGraphNodes.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(body.getFlowNodes().isEmpty());
    assertTrue(body.getVariables().isEmpty());
    assertTrue(actualWorkflowGraphNodes.hasBody());
    assertTrue(actualWorkflowGraphNodes.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsApiController#getWorkflowGraphNodes(String, String, Long)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowNode#getWrappedType()}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsApiController#getWorkflowGraphNodes(String, String,
   * Long)}
   */
  @Test
  @DisplayName("Test getWorkflowGraphNodes(String, String, Long); then calls getWrappedType()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApiController.getWorkflowGraphNodes(String, String, Long)"
  })
  void testGetWorkflowGraphNodes_thenCallsGetWrappedType() {
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

    WorkflowDirectedGraphService workflowDirectedGraphService =
        mock(WorkflowDirectedGraphService.class);
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(workflowDirectedGraph);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.empty();

    WorkflowCmdaApiQueryRepository workflowQueryRepository =
        new WorkflowCmdaApiQueryRepository(
            repositoryService,
            historyService,
            runtimeService,
            new DefaultObjectConverter(converters, optionalBiConverters));
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService2 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService2 = new RuntimeServiceImpl();
    ArrayList<Converter> converters2 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters2 = Optional.empty();

    WorkflowInstCmdaApiQueryRepository workflowInstQueryRepository =
        new WorkflowInstCmdaApiQueryRepository(
            repositoryService2,
            historyService2,
            runtimeService2,
            new DefaultObjectConverter(converters2, optionalBiConverters2));
    ActivityQueryRepository activityQueryRepository = mock(ActivityQueryRepository.class);
    RepositoryServiceImpl repositoryService3 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService3 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService3 = new RuntimeServiceImpl();
    ArrayList<Converter> converters3 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters3 = Optional.empty();

    VariableCmdaApiQueryRepository variableQueryRepository =
        new VariableCmdaApiQueryRepository(
            repositoryService3,
            historyService3,
            runtimeService3,
            new DefaultObjectConverter(converters3, optionalBiConverters3));
    ArrayList<Converter> converters4 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters4 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
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
            objectConverter,
            optionalVersionedRepository);
    RepositoryServiceImpl repositoryService4 = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService5 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService5, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService4, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowsApiController workflowsApiController =
        new WorkflowsApiController(monitoringService, workflowEngine);

    // Act
    ResponseEntity<WorkflowNodesView> actualWorkflowGraphNodes =
        workflowsApiController.getWorkflowGraphNodes("42", "ABC123", 1L);

    // Assert
    verify(workflowDirectedGraph).getChildren("Key");
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getParents("Key");
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowNode).getWrappedType();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    verify(workflowDirectedGraphService).getDirectedGraph("42", 1L);
    List<NodeView> flowNodes = actualWorkflowGraphNodes.getBody().getFlowNodes();
    assertEquals(1, flowNodes.size());
    NodeView getResult = flowNodes.get(0);
    assertEquals("ACTIVITY", getResult.getGroup());
    assertEquals("Key", getResult.getNodeId());
    assertEquals("OBJECT", getResult.getType());
    assertTrue(getResult.getChildren().isEmpty());
    assertTrue(getResult.getParents().isEmpty());
  }

  /**
   * Test {@link WorkflowsApiController#getWorkflowGraphNodes(String, String, Long)}.
   *
   * <ul>
   *   <li>Then return Body Version is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsApiController#getWorkflowGraphNodes(String, String,
   * Long)}
   */
  @Test
  @DisplayName(
      "Test getWorkflowGraphNodes(String, String, Long); then return Body Version is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApiController.getWorkflowGraphNodes(String, String, Long)"
  })
  void testGetWorkflowGraphNodes_thenReturnBodyVersionIsNull() {
    // Arrange
    WorkflowDirectedGraphService workflowDirectedGraphService =
        mock(WorkflowDirectedGraphService.class);
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.empty();

    WorkflowCmdaApiQueryRepository workflowQueryRepository =
        new WorkflowCmdaApiQueryRepository(
            repositoryService,
            historyService,
            runtimeService,
            new DefaultObjectConverter(converters, optionalBiConverters));
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService2 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService2 = new RuntimeServiceImpl();
    ArrayList<Converter> converters2 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters2 = Optional.empty();

    WorkflowInstCmdaApiQueryRepository workflowInstQueryRepository =
        new WorkflowInstCmdaApiQueryRepository(
            repositoryService2,
            historyService2,
            runtimeService2,
            new DefaultObjectConverter(converters2, optionalBiConverters2));
    ActivityQueryRepository activityQueryRepository = mock(ActivityQueryRepository.class);
    RepositoryServiceImpl repositoryService3 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService3 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService3 = new RuntimeServiceImpl();
    ArrayList<Converter> converters3 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters3 = Optional.empty();

    VariableCmdaApiQueryRepository variableQueryRepository =
        new VariableCmdaApiQueryRepository(
            repositoryService3,
            historyService3,
            runtimeService3,
            new DefaultObjectConverter(converters3, optionalBiConverters3));
    ArrayList<Converter> converters4 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters4 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
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
            objectConverter,
            optionalVersionedRepository);
    RepositoryServiceImpl repositoryService4 = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService5 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService5, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService4, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowsApiController workflowsApiController =
        new WorkflowsApiController(monitoringService, workflowEngine);

    // Act
    ResponseEntity<WorkflowNodesView> actualWorkflowGraphNodes =
        workflowsApiController.getWorkflowGraphNodes("42", "ABC123", 1L);

    // Assert
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    verify(workflowDirectedGraphService).getDirectedGraph("42", 1L);
    HttpStatusCode statusCode = actualWorkflowGraphNodes.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    WorkflowNodesView body = actualWorkflowGraphNodes.getBody();
    assertEquals("42", body.getWorkflowId());
    assertNull(body.getVersion());
    assertEquals(200, actualWorkflowGraphNodes.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(body.getFlowNodes().isEmpty());
    assertTrue(body.getVariables().isEmpty());
    assertTrue(actualWorkflowGraphNodes.hasBody());
    assertTrue(actualWorkflowGraphNodes.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsApiController#getWorkflowGraphNodes(String, String, Long)}.
   *
   * <ul>
   *   <li>Then return Body Version longValue is one.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowsApiController#getWorkflowGraphNodes(String, String,
   * Long)}
   */
  @Test
  @DisplayName(
      "Test getWorkflowGraphNodes(String, String, Long); then return Body Version longValue is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApiController.getWorkflowGraphNodes(String, String, Long)"
  })
  void testGetWorkflowGraphNodes_thenReturnBodyVersionLongValueIsOne() {
    // Arrange
    WorkflowDirectedGraph workflowDirectedGraph = mock(WorkflowDirectedGraph.class);
    when(workflowDirectedGraph.getVersion()).thenReturn(1L);
    when(workflowDirectedGraph.getDictionary()).thenReturn(new HashMap<>());
    when(workflowDirectedGraph.getVariables()).thenReturn(new HashMap<>());

    WorkflowDirectedGraphService workflowDirectedGraphService =
        mock(WorkflowDirectedGraphService.class);
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any()))
        .thenReturn(new WorkflowDirectedGraph("42"));
    when(workflowDirectedGraphService.getDirectedGraph(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(workflowDirectedGraph);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.empty();

    WorkflowCmdaApiQueryRepository workflowQueryRepository =
        new WorkflowCmdaApiQueryRepository(
            repositoryService,
            historyService,
            runtimeService,
            new DefaultObjectConverter(converters, optionalBiConverters));
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService2 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService2 = new RuntimeServiceImpl();
    ArrayList<Converter> converters2 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters2 = Optional.empty();

    WorkflowInstCmdaApiQueryRepository workflowInstQueryRepository =
        new WorkflowInstCmdaApiQueryRepository(
            repositoryService2,
            historyService2,
            runtimeService2,
            new DefaultObjectConverter(converters2, optionalBiConverters2));
    ActivityQueryRepository activityQueryRepository = mock(ActivityQueryRepository.class);
    RepositoryServiceImpl repositoryService3 = new RepositoryServiceImpl();
    HistoryServiceImpl historyService3 = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService3 = new RuntimeServiceImpl();
    ArrayList<Converter> converters3 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters3 = Optional.empty();

    VariableCmdaApiQueryRepository variableQueryRepository =
        new VariableCmdaApiQueryRepository(
            repositoryService3,
            historyService3,
            runtimeService3,
            new DefaultObjectConverter(converters3, optionalBiConverters3));
    ArrayList<Converter> converters4 = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters4 = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
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
            objectConverter,
            optionalVersionedRepository);
    RepositoryServiceImpl repositoryService4 = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService5 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService5, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService4, bpmnBuilder, processors, new AuditTrailLogAction());

    WorkflowsApiController workflowsApiController =
        new WorkflowsApiController(monitoringService, workflowEngine);

    // Act
    ResponseEntity<WorkflowNodesView> actualWorkflowGraphNodes =
        workflowsApiController.getWorkflowGraphNodes("42", "ABC123", 1L);

    // Assert
    verify(workflowDirectedGraph).getDictionary();
    verify(workflowDirectedGraph).getVariables();
    verify(workflowDirectedGraph).getVersion();
    verify(workflowDirectedGraphService).getDirectedGraph("42");
    verify(workflowDirectedGraphService).getDirectedGraph("42", 1L);
    HttpStatusCode statusCode = actualWorkflowGraphNodes.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    WorkflowNodesView body = actualWorkflowGraphNodes.getBody();
    assertEquals("42", body.getWorkflowId());
    assertEquals(1L, body.getVersion().longValue());
    assertEquals(200, actualWorkflowGraphNodes.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(body.getFlowNodes().isEmpty());
    assertTrue(body.getVariables().isEmpty());
    assertTrue(actualWorkflowGraphNodes.hasBody());
    assertTrue(actualWorkflowGraphNodes.getHeaders().isEmpty());
  }

  /**
   * Test {@link WorkflowsApiController#listWorkflowGlobalVariables(String, String, String, Instant,
   * Instant)}.
   *
   * <p>Method under test: {@link WorkflowsApiController#listWorkflowGlobalVariables(String, String,
   * String, Instant, Instant)}
   */
  @Test
  @DisplayName("Test listWorkflowGlobalVariables(String, String, String, Instant, Instant)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ResponseEntity WorkflowsApiController.listWorkflowGlobalVariables(String, String, String, Instant, Instant)"
  })
  void testListWorkflowGlobalVariables() {
    // Arrange
    when(monitoringService.listWorkflowInstanceGlobalVars(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<Instant>any(),
            Mockito.<Instant>any()))
        .thenReturn(new ArrayList<>());

    // Act
    ResponseEntity<List<VariableView>> actualListWorkflowGlobalVariablesResult =
        workflowsApiController.listWorkflowGlobalVariables(
            "42",
            "42",
            "ABC123",
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(),
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(monitoringService)
        .listWorkflowInstanceGlobalVars(eq("42"), eq("42"), isA(Instant.class), isA(Instant.class));
    HttpStatusCode statusCode = actualListWorkflowGlobalVariablesResult.getStatusCode();
    assertTrue(statusCode instanceof HttpStatus);
    assertEquals(200, actualListWorkflowGlobalVariablesResult.getStatusCodeValue());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(actualListWorkflowGlobalVariablesResult.getBody().isEmpty());
    assertTrue(actualListWorkflowGlobalVariablesResult.hasBody());
    assertTrue(actualListWorkflowGlobalVariablesResult.getHeaders().isEmpty());
  }
}
