package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
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
   *   <li>Then return WorkflowId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraphService#getDirectedGraph(String, Long)}
   */
  @Test
  @DisplayName(
      "Test getDirectedGraph(String, Long) with 'id', 'version'; then return WorkflowId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "WorkflowDirectedGraph WorkflowDirectedGraphService.getDirectedGraph(String, Long)"
  })
  void testGetDirectedGraphWithIdVersion_thenReturnWorkflowIdIs42() {
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
    when(objectConverter.convert(
            Mockito.<Object>any(), Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
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
   *   <li>Then return WorkflowId is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowDirectedGraphService#getDirectedGraph(String)}
   */
  @Test
  @DisplayName("Test getDirectedGraph(String) with 'id'; then return WorkflowId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowDirectedGraph WorkflowDirectedGraphService.getDirectedGraph(String)"})
  void testGetDirectedGraphWithId_thenReturnWorkflowIdIs42() {
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
    when(objectConverter.convert(
            Mockito.<Object>any(), Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
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
}
