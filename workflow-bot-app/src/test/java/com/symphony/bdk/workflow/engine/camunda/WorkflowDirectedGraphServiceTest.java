package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.model.UserV2;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowDirectGraphBuilder;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkflowDirectedGraphServiceTest {

  private VersionedWorkflowRepository versionedWorkflowRepository;
  private SessionService sessionService;
  private ObjectConverter objectConverter;
  private WorkflowDirectedGraphService service;
  private UserV2 mockUser;

  @BeforeEach
  void setUp() {
    versionedWorkflowRepository = mock(VersionedWorkflowRepository.class);
    sessionService = mock(SessionService.class);
    objectConverter = mock(ObjectConverter.class);
    mockUser = mock(UserV2.class);

    when(sessionService.getSession()).thenReturn(mockUser);
    when(mockUser.getDisplayName()).thenReturn("Test User");
  }

  @Test
  void getDirectedGraphShouldReturnNullWhenRepositoryIsEmpty() {
    service = new WorkflowDirectedGraphService(Optional.empty(), sessionService, objectConverter);

    WorkflowDirectedGraph result = service.getDirectedGraph("testWorkflow");

    assertNull(result);
  }

  @Test
  void getDirectedGraphShouldReturnDirectedGraphWhenWorkflowExists() {
    service = new WorkflowDirectedGraphService(Optional.of(versionedWorkflowRepository), sessionService,
        objectConverter);

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setWorkflowId("testWorkflow");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setSwadl("id: testWorkflow");

    Workflow workflow = new Workflow();
    workflow.setId("testWorkflow");
    workflow.setVersion(1L);
    workflow.setActivities(java.util.Collections.emptyList());

    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue("testWorkflow"))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(eq("id: testWorkflow"), eq(1L), eq(Workflow.class))).thenReturn(workflow);

    WorkflowDirectedGraph result = service.getDirectedGraph("testWorkflow");

    assertNotNull(result);
    assertEquals("testWorkflow", result.getWorkflowId());
  }

  @Test
  void getDirectedGraphShouldReturnNullWhenWorkflowNotFound() {
    service = new WorkflowDirectedGraphService(Optional.of(versionedWorkflowRepository), sessionService,
        objectConverter);

    when(versionedWorkflowRepository.findByWorkflowIdAndActiveTrue("testWorkflow")).thenReturn(Optional.empty());

    WorkflowDirectedGraph result = service.getDirectedGraph("testWorkflow");

    assertNull(result);
  }

  @Test
  void getDirectedGraphWithVersionShouldReturnNullWhenRepositoryIsEmpty() {
    service = new WorkflowDirectedGraphService(Optional.empty(), sessionService, objectConverter);

    WorkflowDirectedGraph result = service.getDirectedGraph("testWorkflow", 1L);

    assertNull(result);
  }

  @Test
  void getDirectedGraphWithVersionShouldReturnDirectedGraphWhenWorkflowExists() {
    service = new WorkflowDirectedGraphService(Optional.of(versionedWorkflowRepository), sessionService,
        objectConverter);

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setWorkflowId("testWorkflow");
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setSwadl("id: testWorkflow");

    Workflow workflow = new Workflow();
    workflow.setId("testWorkflow");
    workflow.setVersion(1L);
    workflow.setActivities(java.util.Collections.emptyList());

    when(versionedWorkflowRepository.findByWorkflowIdAndVersion("testWorkflow", 1L))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(eq("id: testWorkflow"), eq(1L), eq(Workflow.class))).thenReturn(workflow);

    WorkflowDirectedGraph result = service.getDirectedGraph("testWorkflow", 1L);

    assertNotNull(result);
    assertEquals("testWorkflow", result.getWorkflowId());
  }

  @Test
  void getDirectedGraphWithVersionShouldReturnNullWhenWorkflowNotFound() {
    service = new WorkflowDirectedGraphService(Optional.of(versionedWorkflowRepository), sessionService,
        objectConverter);

    when(versionedWorkflowRepository.findByWorkflowIdAndVersion("testWorkflow", 1L)).thenReturn(Optional.empty());

    WorkflowDirectedGraph result = service.getDirectedGraph("testWorkflow", 1L);

    assertNull(result);
  }

  @Test
  void putDirectedGraphShouldReturnSameGraph() {
    service = new WorkflowDirectedGraphService(Optional.empty(), sessionService, objectConverter);

    WorkflowDirectedGraph directedGraph = new WorkflowDirectedGraph("testWorkflow", 1L);

    WorkflowDirectedGraph result = service.putDirectedGraph(directedGraph);

    assertNotNull(result);
    assertEquals("testWorkflow", result.getWorkflowId());
    assertEquals(1L, result.getVersion());
  }
}
