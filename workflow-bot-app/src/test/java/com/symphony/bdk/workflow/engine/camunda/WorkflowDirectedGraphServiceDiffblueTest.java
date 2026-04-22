package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.retry.RetryWithRecoveryBuilder;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.SessionApi;
import com.symphony.bdk.workflow.converter.BiConverter;
import com.symphony.bdk.workflow.converter.Converter;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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

@ContextConfiguration(classes = {WorkflowDirectedGraphService.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class WorkflowDirectedGraphServiceDiffblueTest {
  @MockBean private ObjectConverter objectConverter;

  @MockBean private SessionService sessionService;

  @MockBean private VersionedWorkflowRepository versionedWorkflowRepository;

  @Autowired private WorkflowDirectedGraphService workflowDirectedGraphService;

  /**
   * Test {@link WorkflowDirectedGraphService#getDirectedGraph(String)} with {@code id}.
   *
   * <p>Method under test: {@link WorkflowDirectedGraphService#getDirectedGraph(String)}
   */
  @Test
  @DisplayName("Test getDirectedGraph(String) with 'id'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowDirectedGraph WorkflowDirectedGraphService.getDirectedGraph(String)"})
  void testGetDirectedGraphWithId() {
    // Arrange
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(emptyResult);

    // Act
    WorkflowDirectedGraph actualDirectedGraph = workflowDirectedGraphService.getDirectedGraph("42");

    // Assert
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
    assertNull(actualDirectedGraph);
  }

  /**
   * Test {@link WorkflowDirectedGraphService#getDirectedGraph(String, Long)} with {@code id},
   * {@code version}.
   *
   * <p>Method under test: {@link WorkflowDirectedGraphService#getDirectedGraph(String, Long)}
   */
  @Test
  @DisplayName("Test getDirectedGraph(String, Long) with 'id', 'version'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "WorkflowDirectedGraph WorkflowDirectedGraphService.getDirectedGraph(String, Long)"
  })
  void testGetDirectedGraphWithIdVersion() {
    // Arrange
    Optional<VersionedWorkflow> emptyResult = Optional.empty();
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(emptyResult);

    // Act
    WorkflowDirectedGraph actualDirectedGraph =
        workflowDirectedGraphService.getDirectedGraph("42", 1L);

    // Assert
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
    assertNull(actualDirectedGraph);
  }

  /**
   * Test {@link WorkflowDirectedGraphService#getDirectedGraph(String, Long)} with {@code id},
   * {@code version}.
   *
   * <ul>
   *   <li>Given {@link SessionApi#SessionApi(ApiClient)} with apiClient is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraphService#getDirectedGraph(String, Long)}
   */
  @Test
  @DisplayName(
      "Test getDirectedGraph(String, Long) with 'id', 'version'; given SessionApi(ApiClient) with apiClient is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "WorkflowDirectedGraph WorkflowDirectedGraphService.getDirectedGraph(String, Long)"
  })
  void testGetDirectedGraphWithIdVersion_givenSessionApiWithApiClientIsNull() {
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

    // Act and Assert
    assertNull(workflowDirectedGraphService.getDirectedGraph("42", 1L));
  }

  /**
   * Test {@link WorkflowDirectedGraphService#getDirectedGraph(String, Long)} with {@code id},
   * {@code version}.
   *
   * <ul>
   *   <li>Given {@link SessionService}.
   *   <li>Then return WorkflowId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraphService#getDirectedGraph(String, Long)}
   */
  @Test
  @DisplayName(
      "Test getDirectedGraph(String, Long) with 'id', 'version'; given SessionService; then return WorkflowId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "WorkflowDirectedGraph WorkflowDirectedGraphService.getDirectedGraph(String, Long)"
  })
  void testGetDirectedGraphWithIdVersion_givenSessionService_thenReturnWorkflowIdIs42() {
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
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(
            Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), eq(Workflow.class)))
        .thenReturn(workflow);

    // Act
    WorkflowDirectedGraph actualDirectedGraph =
        workflowDirectedGraphService.getDirectedGraph("42", 1L);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion("42", 1L);
    assertEquals("42", actualDirectedGraph.getWorkflowId());
    assertEquals(1L, actualDirectedGraph.getVersion().longValue());
    assertTrue(actualDirectedGraph.getStartEvents().isEmpty());
    assertTrue(actualDirectedGraph.getDictionary().isEmpty());
    assertTrue(actualDirectedGraph.getParents().isEmpty());
    assertTrue(actualDirectedGraph.getVariables().isEmpty());
  }

  /**
   * Test {@link WorkflowDirectedGraphService#getDirectedGraph(String)} with {@code id}.
   *
   * <ul>
   *   <li>Given {@link SessionApi#SessionApi(ApiClient)} with apiClient is {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraphService#getDirectedGraph(String)}
   */
  @Test
  @DisplayName(
      "Test getDirectedGraph(String) with 'id'; given SessionApi(ApiClient) with apiClient is 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowDirectedGraph WorkflowDirectedGraphService.getDirectedGraph(String)"})
  void testGetDirectedGraphWithId_givenSessionApiWithApiClientIsNull_thenReturnNull() {
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

    // Act and Assert
    assertNull(workflowDirectedGraphService.getDirectedGraph("42"));
  }

  /**
   * Test {@link WorkflowDirectedGraphService#getDirectedGraph(String)} with {@code id}.
   *
   * <ul>
   *   <li>Given {@link SessionService}.
   *   <li>Then return WorkflowId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraphService#getDirectedGraph(String)}
   */
  @Test
  @DisplayName(
      "Test getDirectedGraph(String) with 'id'; given SessionService; then return WorkflowId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowDirectedGraph WorkflowDirectedGraphService.getDirectedGraph(String)"})
  void testGetDirectedGraphWithId_givenSessionService_thenReturnWorkflowIdIs42() {
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
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), eq(Workflow.class)))
        .thenReturn(workflow);

    // Act
    WorkflowDirectedGraph actualDirectedGraph = workflowDirectedGraphService.getDirectedGraph("42");

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue("42");
    assertEquals("42", actualDirectedGraph.getWorkflowId());
    assertEquals(1L, actualDirectedGraph.getVersion().longValue());
    assertTrue(actualDirectedGraph.getStartEvents().isEmpty());
    assertTrue(actualDirectedGraph.getDictionary().isEmpty());
    assertTrue(actualDirectedGraph.getParents().isEmpty());
    assertTrue(actualDirectedGraph.getVariables().isEmpty());
  }

  /**
   * Test {@link WorkflowDirectedGraphService#putDirectedGraph(WorkflowDirectedGraph)}.
   *
   * <p>Method under test: {@link
   * WorkflowDirectedGraphService#putDirectedGraph(WorkflowDirectedGraph)}
   */
  @Test
  @DisplayName("Test putDirectedGraph(WorkflowDirectedGraph)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "WorkflowDirectedGraph WorkflowDirectedGraphService.putDirectedGraph(WorkflowDirectedGraph)"
  })
  void testPutDirectedGraph() {
    // Arrange
    WorkflowDirectedGraph directedGraph = new WorkflowDirectedGraph("42");

    // Act
    WorkflowDirectedGraph actualPutDirectedGraphResult =
        workflowDirectedGraphService.putDirectedGraph(directedGraph);

    // Assert
    assertSame(directedGraph, actualPutDirectedGraphResult);
  }

  /**
   * Test {@link WorkflowDirectedGraphService#getDirectedGraph(String, Long)} with a present
   * repository returning empty optional — covers lines 43-45 and 47-48 via direct instantiation
   * (bypasses Spring cache proxy).
   */
  @Test
  @DisplayName(
      "Test getDirectedGraph(String, Long); given empty repository; then return null")
  void testGetDirectedGraphWithIdVersion_mapToDirectedGraph_emptyRepository() {
    // Arrange
    Optional<VersionedWorkflowRepository> versionedWorkflowRepositoryOpt = Optional.empty();
    SessionApi sessionApi = new SessionApi(null);
    SessionService localSessionService =
        new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());
    ObjectConverter mockObjectConverter = mock(ObjectConverter.class);

    WorkflowDirectedGraphService service =
        new WorkflowDirectedGraphService(
            versionedWorkflowRepositoryOpt, localSessionService, mockObjectConverter);

    // Act
    WorkflowDirectedGraph result = service.getDirectedGraph("workflow-42", 1L);

    // Assert
    assertNull(result);
  }

  /**
   * Test {@link WorkflowDirectedGraphService#getDirectedGraph(String, Long)} with a present
   * repository returning valid workflow — covers lines 47-48 via direct instantiation.
   */
  @Test
  @DisplayName(
      "Test getDirectedGraph(String, Long); given present repository returning valid workflow; then return directed graph")
  void testGetDirectedGraphWithIdVersion_mapToDirectedGraph_nonEmptySupplierResult() {
    // Arrange
    VersionedWorkflowRepository mockRepo = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflowRepository> repoOpt = Optional.of(mockRepo);
    SessionApi sessionApi = new SessionApi(null);
    SessionService localSessionService =
        new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());
    ObjectConverter mockObjectConverter = mock(ObjectConverter.class);

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("deploy-42");
    versionedWorkflow.setDescription("Test workflow version");
    versionedWorkflow.setId("wf-42");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("swadl-content");
    versionedWorkflow.setVersion(5L);
    versionedWorkflow.setWorkflowId("workflow-version-42");
    when(mockRepo.findByWorkflowIdAndVersion("workflow-version-42", 5L))
        .thenReturn(Optional.of(versionedWorkflow));

    Properties properties = new Properties();
    properties.setPublish(true);
    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("workflow-version-42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(5L);
    Mockito.doReturn(workflow)
        .when(mockObjectConverter)
        .convert(Mockito.<Object>any(), Mockito.<Object>any(), eq(Workflow.class));

    WorkflowDirectedGraphService service =
        new WorkflowDirectedGraphService(repoOpt, localSessionService, mockObjectConverter);

    // Act
    WorkflowDirectedGraph result = service.getDirectedGraph("workflow-version-42", 5L);

    // Assert
    assertNotNull(result);
    assertEquals("workflow-version-42", result.getWorkflowId());
    assertEquals(5L, result.getVersion().longValue());
    verify(mockObjectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(mockRepo).findByWorkflowIdAndVersion("workflow-version-42", 5L);
  }

  /**
   * Test {@link WorkflowDirectedGraphService#getDirectedGraph(String)} with a present repository
   * that returns an empty workflow optional — covers mapToDirectedGraph body with empty supplier
   * result.
   */
  @Test
  @DisplayName(
      "Test getDirectedGraph(String); given present repository returning empty optional; then return null")
  void testGetDirectedGraph_mapToDirectedGraph_emptySupplierResult() {
    // Arrange
    VersionedWorkflowRepository mockRepo = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflowRepository> repoOpt = Optional.of(mockRepo);
    SessionApi sessionApi = new SessionApi(null);
    SessionService localSessionService =
        new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());
    ObjectConverter mockObjectConverter = mock(ObjectConverter.class);
    when(mockRepo.findByWorkflowIdAndActiveTrue(Mockito.<String>any()))
        .thenReturn(Optional.empty());

    WorkflowDirectedGraphService service =
        new WorkflowDirectedGraphService(repoOpt, localSessionService, mockObjectConverter);

    // Act
    WorkflowDirectedGraph result = service.getDirectedGraph("workflow-empty");

    // Assert
    assertNull(result);
    verify(mockRepo).findByWorkflowIdAndActiveTrue("workflow-empty");
  }

  /**
   * Test {@link WorkflowDirectedGraphService#getDirectedGraph(String)} with a present repository
   * that returns a valid VersionedWorkflow — covers mapToDirectedGraph body with non-empty supplier
   * result.
   */
  @Test
  @DisplayName(
      "Test getDirectedGraph(String); given present repository returning valid workflow; then return directed graph")
  void testGetDirectedGraph_mapToDirectedGraph_nonEmptySupplierResult() {
    // Arrange
    VersionedWorkflowRepository mockRepo = mock(VersionedWorkflowRepository.class);
    Optional<VersionedWorkflowRepository> repoOpt = Optional.of(mockRepo);
    SessionApi sessionApi = new SessionApi(null);
    SessionService localSessionService =
        new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());
    ObjectConverter mockObjectConverter = mock(ObjectConverter.class);

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setActive(true);
    versionedWorkflow.setCreatedBy(1L);
    versionedWorkflow.setDeploymentId("deploy-1");
    versionedWorkflow.setDescription("Test workflow");
    versionedWorkflow.setId("wf-1");
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("swadl-content");
    versionedWorkflow.setVersion(100L);
    versionedWorkflow.setWorkflowId("workflow-present");
    when(mockRepo.findByWorkflowIdAndActiveTrue("workflow-present"))
        .thenReturn(Optional.of(versionedWorkflow));

    Properties properties = new Properties();
    properties.setPublish(true);
    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("workflow-present");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(100L);
    Mockito.doReturn(workflow).when(mockObjectConverter)
        .convert(Mockito.<Object>any(), Mockito.<Object>any(), eq(Workflow.class));

    WorkflowDirectedGraphService service =
        new WorkflowDirectedGraphService(repoOpt, localSessionService, mockObjectConverter);

    // Act
    WorkflowDirectedGraph result = service.getDirectedGraph("workflow-present");

    // Assert
    assertNotNull(result);
    assertEquals("workflow-present", result.getWorkflowId());
    assertEquals(100L, result.getVersion().longValue());
    verify(mockObjectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(mockRepo).findByWorkflowIdAndActiveTrue("workflow-present");
  }
}
