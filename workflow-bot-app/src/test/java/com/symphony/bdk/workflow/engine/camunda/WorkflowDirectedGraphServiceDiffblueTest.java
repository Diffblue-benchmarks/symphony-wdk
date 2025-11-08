package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowDirectedGraphService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class WorkflowDirectedGraphServiceDiffblueTest {
  @MockBean
  private ObjectConverter objectConverter;

  @MockBean
  private SessionService sessionService;

  @MockBean
  private VersionedWorkflowRepository versionedWorkflowRepository;

  @Autowired
  private WorkflowDirectedGraphService workflowDirectedGraphService;

  /**
   * Method under test:
   * {@link WorkflowDirectedGraphService#getDirectedGraph(String)}
   */
  @Test
  void testGetDirectedGraph() {
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
    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue(Mockito.<String>any())).thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow);

    // Act
    WorkflowDirectedGraph actualDirectedGraph = workflowDirectedGraphService.getDirectedGraph("42");

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndActiveTrue(eq("42"));
    assertEquals("42", actualDirectedGraph.getWorkflowId());
    assertEquals(1L, actualDirectedGraph.getVersion().longValue());
    assertTrue(actualDirectedGraph.getStartEvents().isEmpty());
    assertTrue(actualDirectedGraph.getDictionary().isEmpty());
    assertTrue(actualDirectedGraph.getParents().isEmpty());
    assertTrue(actualDirectedGraph.getVariables().isEmpty());
  }

  /**
   * Method under test:
   * {@link WorkflowDirectedGraphService#getDirectedGraph(String, Long)}
   */
  @Test
  void testGetDirectedGraph2() {
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
    when(versionedWorkflowRepository.findByWorkflowIdAndVersion(Mockito.<String>any(), Mockito.<Long>any()))
        .thenReturn(ofResult);

    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    when(objectConverter.convert(Mockito.<Object>any(), Mockito.<Object>any(), Mockito.<Class<Workflow>>any()))
        .thenReturn(workflow);

    // Act
    WorkflowDirectedGraph actualDirectedGraph = workflowDirectedGraphService.getDirectedGraph("42", 1L);

    // Assert
    verify(objectConverter).convert(isA(Object.class), isA(Object.class), isA(Class.class));
    verify(versionedWorkflowRepository).findByWorkflowIdAndVersion(eq("42"), eq(1L));
    assertEquals("42", actualDirectedGraph.getWorkflowId());
    assertEquals(1L, actualDirectedGraph.getVersion().longValue());
    assertTrue(actualDirectedGraph.getStartEvents().isEmpty());
    assertTrue(actualDirectedGraph.getDictionary().isEmpty());
    assertTrue(actualDirectedGraph.getParents().isEmpty());
    assertTrue(actualDirectedGraph.getVariables().isEmpty());
  }

  /**
   * Method under test:
   * {@link WorkflowDirectedGraphService#putDirectedGraph(WorkflowDirectedGraph)}
   */
  @Test
  void testPutDirectedGraph() {
    // Arrange
    WorkflowDirectedGraph directedGraph = new WorkflowDirectedGraph("42");

    // Act and Assert
    assertSame(directedGraph, workflowDirectedGraphService.putDirectedGraph(directedGraph));
  }

  /**
   * Method under test:
   * {@link WorkflowDirectedGraphService#putDirectedGraph(WorkflowDirectedGraph)}
   */
  @Test
  void testPutDirectedGraph2() {
    // Arrange
    WorkflowDirectedGraph directedGraph = mock(WorkflowDirectedGraph.class);

    // Act and Assert
    assertSame(directedGraph, workflowDirectedGraphService.putDirectedGraph(directedGraph));
  }
}
