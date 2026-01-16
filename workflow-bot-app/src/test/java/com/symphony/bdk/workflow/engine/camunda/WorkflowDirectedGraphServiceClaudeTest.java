package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

class WorkflowDirectedGraphServiceClaudeTest {

  private VersionedWorkflowRepository repository;
  private SessionService sessionService;
  private ObjectConverter objectConverter;
  private WorkflowDirectedGraphService service;

  @BeforeEach
  void setUp() {
    repository = mock(VersionedWorkflowRepository.class);
    sessionService = mock(SessionService.class);
    objectConverter = mock(ObjectConverter.class);
  }

  // Tests for getDirectedGraph(String id) - Active workflow retrieval

  @Test
  void getDirectedGraph_withActiveWorkflow_shouldReturnDirectedGraph() {
    // Given: Repository contains an active workflow
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    VersionedWorkflow versionedWorkflow = createVersionedWorkflow("workflow1", 1L, "swadl-content", true);
    Workflow workflow = createWorkflow("workflow1", 1L);

    when(repository.findByWorkflowIdAndActiveTrue("workflow1")).thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(eq("swadl-content"), eq(1L), eq(Workflow.class))).thenReturn(workflow);

    // When: Getting active directed graph
    WorkflowDirectedGraph result = service.getDirectedGraph("workflow1");

    // Then: Should return a directed graph built from the workflow
    assertThat(result).isNotNull();
    assertThat(result.getWorkflowId()).isEqualTo("workflow1");
    assertThat(result.getVersion()).isEqualTo(1L);
    verify(repository).findByWorkflowIdAndActiveTrue("workflow1");
    verify(objectConverter).convert("swadl-content", 1L, Workflow.class);
  }

  @Test
  void getDirectedGraph_withNoActiveWorkflow_shouldReturnNull() {
    // Given: Repository does not contain an active workflow
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    when(repository.findByWorkflowIdAndActiveTrue("nonexistent")).thenReturn(Optional.empty());

    // When: Getting active directed graph for nonexistent workflow
    WorkflowDirectedGraph result = service.getDirectedGraph("nonexistent");

    // Then: Should return null
    assertThat(result).isNull();
    verify(repository).findByWorkflowIdAndActiveTrue("nonexistent");
    verify(objectConverter, never()).convert(any(), any(), any());
  }

  @Test
  void getDirectedGraph_withEmptyRepository_shouldReturnNull() {
    // Given: Repository is not configured (empty optional)
    service = new WorkflowDirectedGraphService(Optional.empty(), sessionService, objectConverter);

    // When: Getting active directed graph
    WorkflowDirectedGraph result = service.getDirectedGraph("workflow1");

    // Then: Should return null without querying repository
    assertThat(result).isNull();
    verify(repository, never()).findByWorkflowIdAndActiveTrue(any());
  }

  @Test
  void getDirectedGraph_withMultipleCallsSameId_shouldQueryRepository() {
    // Given: Repository contains an active workflow
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    VersionedWorkflow versionedWorkflow = createVersionedWorkflow("workflow1", 2L, "swadl-content", true);
    Workflow workflow = createWorkflow("workflow1", 2L);

    when(repository.findByWorkflowIdAndActiveTrue("workflow1")).thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(eq("swadl-content"), eq(2L), eq(Workflow.class))).thenReturn(workflow);

    // When: Getting the same workflow multiple times
    WorkflowDirectedGraph result1 = service.getDirectedGraph("workflow1");
    WorkflowDirectedGraph result2 = service.getDirectedGraph("workflow1");

    // Then: Should return directed graphs (caching behavior depends on Spring configuration)
    assertThat(result1).isNotNull();
    assertThat(result2).isNotNull();
    assertThat(result1.getWorkflowId()).isEqualTo("workflow1");
    assertThat(result2.getWorkflowId()).isEqualTo("workflow1");
  }

  @Test
  void getDirectedGraph_withDifferentWorkflowIds_shouldReturnDifferentGraphs() {
    // Given: Repository contains multiple active workflows
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    VersionedWorkflow workflow1 = createVersionedWorkflow("workflow1", 1L, "swadl1", true);
    VersionedWorkflow workflow2 = createVersionedWorkflow("workflow2", 1L, "swadl2", true);
    Workflow swadl1 = createWorkflow("workflow1", 1L);
    Workflow swadl2 = createWorkflow("workflow2", 1L);

    when(repository.findByWorkflowIdAndActiveTrue("workflow1")).thenReturn(Optional.of(workflow1));
    when(repository.findByWorkflowIdAndActiveTrue("workflow2")).thenReturn(Optional.of(workflow2));
    when(objectConverter.convert(eq("swadl1"), eq(1L), eq(Workflow.class))).thenReturn(swadl1);
    when(objectConverter.convert(eq("swadl2"), eq(1L), eq(Workflow.class))).thenReturn(swadl2);

    // When: Getting different workflows
    WorkflowDirectedGraph result1 = service.getDirectedGraph("workflow1");
    WorkflowDirectedGraph result2 = service.getDirectedGraph("workflow2");

    // Then: Should return different directed graphs
    assertThat(result1).isNotNull();
    assertThat(result2).isNotNull();
    assertThat(result1.getWorkflowId()).isEqualTo("workflow1");
    assertThat(result2.getWorkflowId()).isEqualTo("workflow2");
  }

  @Test
  void getDirectedGraph_withNullId_shouldPassToRepository() {
    // Given: Service with repository
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    when(repository.findByWorkflowIdAndActiveTrue(null)).thenReturn(Optional.empty());

    // When: Getting directed graph with null id
    WorkflowDirectedGraph result = service.getDirectedGraph(null);

    // Then: Should return null
    assertThat(result).isNull();
    verify(repository).findByWorkflowIdAndActiveTrue(null);
  }

  @Test
  void getDirectedGraph_withEmptyStringId_shouldQueryRepository() {
    // Given: Repository returns nothing for empty string
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    when(repository.findByWorkflowIdAndActiveTrue("")).thenReturn(Optional.empty());

    // When: Getting directed graph with empty string id
    WorkflowDirectedGraph result = service.getDirectedGraph("");

    // Then: Should return null
    assertThat(result).isNull();
    verify(repository).findByWorkflowIdAndActiveTrue("");
  }

  // Tests for getDirectedGraph(String id, Long version) - Versioned workflow retrieval

  @Test
  void getDirectedGraphWithVersion_withExistingWorkflow_shouldReturnDirectedGraph() {
    // Given: Repository contains a versioned workflow
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    VersionedWorkflow versionedWorkflow = createVersionedWorkflow("workflow1", 5L, "swadl-v5", false);
    Workflow workflow = createWorkflow("workflow1", 5L);

    when(repository.findByWorkflowIdAndVersion("workflow1", 5L)).thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(eq("swadl-v5"), eq(5L), eq(Workflow.class))).thenReturn(workflow);

    // When: Getting versioned directed graph
    WorkflowDirectedGraph result = service.getDirectedGraph("workflow1", 5L);

    // Then: Should return directed graph with correct version
    assertThat(result).isNotNull();
    assertThat(result.getWorkflowId()).isEqualTo("workflow1");
    assertThat(result.getVersion()).isEqualTo(5L);
    verify(repository).findByWorkflowIdAndVersion("workflow1", 5L);
    verify(objectConverter).convert("swadl-v5", 5L, Workflow.class);
  }

  @Test
  void getDirectedGraphWithVersion_withNonExistentVersion_shouldReturnNull() {
    // Given: Repository does not contain the specified version
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    when(repository.findByWorkflowIdAndVersion("workflow1", 99L)).thenReturn(Optional.empty());

    // When: Getting directed graph for non-existent version
    WorkflowDirectedGraph result = service.getDirectedGraph("workflow1", 99L);

    // Then: Should return null
    assertThat(result).isNull();
    verify(repository).findByWorkflowIdAndVersion("workflow1", 99L);
    verify(objectConverter, never()).convert(any(), any(), any());
  }

  @Test
  void getDirectedGraphWithVersion_withEmptyRepository_shouldReturnNull() {
    // Given: Repository is not configured
    service = new WorkflowDirectedGraphService(Optional.empty(), sessionService, objectConverter);

    // When: Getting versioned directed graph
    WorkflowDirectedGraph result = service.getDirectedGraph("workflow1", 1L);

    // Then: Should return null without querying repository
    assertThat(result).isNull();
    verify(repository, never()).findByWorkflowIdAndVersion(any(), any());
  }

  @Test
  void getDirectedGraphWithVersion_withVersion0_shouldQueryRepository() {
    // Given: Repository may contain version 0
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    VersionedWorkflow versionedWorkflow = createVersionedWorkflow("workflow1", 0L, "swadl-v0", false);
    Workflow workflow = createWorkflow("workflow1", 0L);

    when(repository.findByWorkflowIdAndVersion("workflow1", 0L)).thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(eq("swadl-v0"), eq(0L), eq(Workflow.class))).thenReturn(workflow);

    // When: Getting directed graph with version 0
    WorkflowDirectedGraph result = service.getDirectedGraph("workflow1", 0L);

    // Then: Should return directed graph
    assertThat(result).isNotNull();
    assertThat(result.getVersion()).isEqualTo(0L);
    verify(repository).findByWorkflowIdAndVersion("workflow1", 0L);
  }

  @Test
  void getDirectedGraphWithVersion_withDifferentVersions_shouldReturnDifferentGraphs() {
    // Given: Repository contains multiple versions
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    VersionedWorkflow v1 = createVersionedWorkflow("workflow1", 1L, "swadl-v1", false);
    VersionedWorkflow v2 = createVersionedWorkflow("workflow1", 2L, "swadl-v2", false);
    VersionedWorkflow v3 = createVersionedWorkflow("workflow1", 3L, "swadl-v3", false);
    Workflow swadl1 = createWorkflow("workflow1", 1L);
    Workflow swadl2 = createWorkflow("workflow1", 2L);
    Workflow swadl3 = createWorkflow("workflow1", 3L);

    when(repository.findByWorkflowIdAndVersion("workflow1", 1L)).thenReturn(Optional.of(v1));
    when(repository.findByWorkflowIdAndVersion("workflow1", 2L)).thenReturn(Optional.of(v2));
    when(repository.findByWorkflowIdAndVersion("workflow1", 3L)).thenReturn(Optional.of(v3));
    when(objectConverter.convert(eq("swadl-v1"), eq(1L), eq(Workflow.class))).thenReturn(swadl1);
    when(objectConverter.convert(eq("swadl-v2"), eq(2L), eq(Workflow.class))).thenReturn(swadl2);
    when(objectConverter.convert(eq("swadl-v3"), eq(3L), eq(Workflow.class))).thenReturn(swadl3);

    // When: Getting different versions
    WorkflowDirectedGraph result1 = service.getDirectedGraph("workflow1", 1L);
    WorkflowDirectedGraph result2 = service.getDirectedGraph("workflow1", 2L);
    WorkflowDirectedGraph result3 = service.getDirectedGraph("workflow1", 3L);

    // Then: Should return graphs with correct versions
    assertThat(result1.getVersion()).isEqualTo(1L);
    assertThat(result2.getVersion()).isEqualTo(2L);
    assertThat(result3.getVersion()).isEqualTo(3L);
  }

  @Test
  void getDirectedGraphWithVersion_withNullVersion_shouldQueryRepository() {
    // Given: Repository handles null version
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    when(repository.findByWorkflowIdAndVersion("workflow1", null)).thenReturn(Optional.empty());

    // When: Getting directed graph with null version
    WorkflowDirectedGraph result = service.getDirectedGraph("workflow1", null);

    // Then: Should return null
    assertThat(result).isNull();
    verify(repository).findByWorkflowIdAndVersion("workflow1", null);
  }

  @Test
  void getDirectedGraphWithVersion_withNullIdAndVersion_shouldQueryRepository() {
    // Given: Repository handles null id and version
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    when(repository.findByWorkflowIdAndVersion(null, null)).thenReturn(Optional.empty());

    // When: Getting directed graph with null id and version
    WorkflowDirectedGraph result = service.getDirectedGraph(null, null);

    // Then: Should return null
    assertThat(result).isNull();
    verify(repository).findByWorkflowIdAndVersion(null, null);
  }

  @Test
  void getDirectedGraphWithVersion_withNegativeVersion_shouldQueryRepository() {
    // Given: Repository may handle negative versions
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    when(repository.findByWorkflowIdAndVersion("workflow1", -1L)).thenReturn(Optional.empty());

    // When: Getting directed graph with negative version
    WorkflowDirectedGraph result = service.getDirectedGraph("workflow1", -1L);

    // Then: Should return null
    assertThat(result).isNull();
    verify(repository).findByWorkflowIdAndVersion("workflow1", -1L);
  }

  @Test
  void getDirectedGraphWithVersion_withLargeVersion_shouldQueryRepository() {
    // Given: Repository handles large version numbers
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    Long largeVersion = Long.MAX_VALUE;
    VersionedWorkflow versionedWorkflow = createVersionedWorkflow("workflow1", largeVersion, "swadl", false);
    Workflow workflow = createWorkflow("workflow1", largeVersion);

    when(repository.findByWorkflowIdAndVersion("workflow1", largeVersion)).thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(eq("swadl"), eq(largeVersion), eq(Workflow.class))).thenReturn(workflow);

    // When: Getting directed graph with large version
    WorkflowDirectedGraph result = service.getDirectedGraph("workflow1", largeVersion);

    // Then: Should return directed graph
    assertThat(result).isNotNull();
    assertThat(result.getVersion()).isEqualTo(largeVersion);
  }

  // Tests for putDirectedGraph(WorkflowDirectedGraph)

  @Test
  void putDirectedGraph_withValidGraph_shouldReturnSameGraph() {
    // Given: A directed graph
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);

    // When: Putting the directed graph
    WorkflowDirectedGraph result = service.putDirectedGraph(graph);

    // Then: Should return the same graph
    assertThat(result).isSameAs(graph);
    assertThat(result.getWorkflowId()).isEqualTo("workflow1");
    assertThat(result.getVersion()).isEqualTo(1L);
  }

  @Test
  void putDirectedGraph_withGraphWithoutVersion_shouldReturnGraph() {
    // Given: A directed graph without version
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1");

    // When: Putting the directed graph
    WorkflowDirectedGraph result = service.putDirectedGraph(graph);

    // Then: Should return the same graph
    assertThat(result).isSameAs(graph);
    assertThat(result.getWorkflowId()).isEqualTo("workflow1");
    assertThat(result.getVersion()).isNull();
  }

  @Test
  void putDirectedGraph_withEmptyRepository_shouldStillReturnGraph() {
    // Given: Service without repository
    service = new WorkflowDirectedGraphService(Optional.empty(), sessionService, objectConverter);
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 2L);

    // When: Putting the directed graph
    WorkflowDirectedGraph result = service.putDirectedGraph(graph);

    // Then: Should return the same graph (cache operation doesn't require repository)
    assertThat(result).isSameAs(graph);
    assertThat(result.getWorkflowId()).isEqualTo("workflow1");
  }

  @Test
  void putDirectedGraph_withMultipleDifferentGraphs_shouldReturnEachGraph() {
    // Given: Multiple different directed graphs
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);
    WorkflowDirectedGraph graph1 = new WorkflowDirectedGraph("workflow1", 1L);
    WorkflowDirectedGraph graph2 = new WorkflowDirectedGraph("workflow2", 2L);
    WorkflowDirectedGraph graph3 = new WorkflowDirectedGraph("workflow3", 3L);

    // When: Putting multiple graphs
    WorkflowDirectedGraph result1 = service.putDirectedGraph(graph1);
    WorkflowDirectedGraph result2 = service.putDirectedGraph(graph2);
    WorkflowDirectedGraph result3 = service.putDirectedGraph(graph3);

    // Then: Should return each graph correctly
    assertThat(result1).isSameAs(graph1);
    assertThat(result2).isSameAs(graph2);
    assertThat(result3).isSameAs(graph3);
    assertThat(result1.getWorkflowId()).isEqualTo("workflow1");
    assertThat(result2.getWorkflowId()).isEqualTo("workflow2");
    assertThat(result3.getWorkflowId()).isEqualTo("workflow3");
  }

  @Test
  void putDirectedGraph_calledMultipleTimesWithSameWorkflowId_shouldReturnLatestGraph() {
    // Given: Service and multiple graphs with same workflow id
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);
    WorkflowDirectedGraph graph1 = new WorkflowDirectedGraph("workflow1", 1L);
    WorkflowDirectedGraph graph2 = new WorkflowDirectedGraph("workflow1", 2L);

    // When: Putting multiple versions of same workflow
    WorkflowDirectedGraph result1 = service.putDirectedGraph(graph1);
    WorkflowDirectedGraph result2 = service.putDirectedGraph(graph2);

    // Then: Should return each graph as provided
    assertThat(result1).isSameAs(graph1);
    assertThat(result2).isSameAs(graph2);
    assertThat(result1.getVersion()).isEqualTo(1L);
    assertThat(result2.getVersion()).isEqualTo(2L);
  }

  @Test
  void putDirectedGraph_withGraphContainingNodes_shouldReturnGraphWithNodes() {
    // Given: A directed graph with nodes
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 1L);
    graph.addStartEvent("start");
    graph.getVariables().put("key", "value");

    // When: Putting the directed graph with content
    WorkflowDirectedGraph result = service.putDirectedGraph(graph);

    // Then: Should return the graph with all its content
    assertThat(result).isSameAs(graph);
    assertThat(result.getStartEvents()).hasSize(1).contains("start");
    assertThat(result.getVariables()).containsEntry("key", "value");
  }

  @Test
  void putDirectedGraph_withVersion0_shouldReturnGraph() {
    // Given: A directed graph with version 0
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);
    WorkflowDirectedGraph graph = new WorkflowDirectedGraph("workflow1", 0L);

    // When: Putting the directed graph
    WorkflowDirectedGraph result = service.putDirectedGraph(graph);

    // Then: Should return the graph with version 0
    assertThat(result).isSameAs(graph);
    assertThat(result.getVersion()).isEqualTo(0L);
  }

  // Integration and edge case tests

  @Test
  void getDirectedGraph_activeAndVersioned_shouldReturnDifferentResults() {
    // Given: Repository has both active (v2) and older version (v1)
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    VersionedWorkflow activeV2 = createVersionedWorkflow("workflow1", 2L, "swadl-v2-active", true);
    VersionedWorkflow oldV1 = createVersionedWorkflow("workflow1", 1L, "swadl-v1-old", false);
    Workflow swadlActive = createWorkflow("workflow1", 2L);
    Workflow swadlOld = createWorkflow("workflow1", 1L);

    when(repository.findByWorkflowIdAndActiveTrue("workflow1")).thenReturn(Optional.of(activeV2));
    when(repository.findByWorkflowIdAndVersion("workflow1", 1L)).thenReturn(Optional.of(oldV1));
    when(objectConverter.convert(eq("swadl-v2-active"), eq(2L), eq(Workflow.class))).thenReturn(swadlActive);
    when(objectConverter.convert(eq("swadl-v1-old"), eq(1L), eq(Workflow.class))).thenReturn(swadlOld);

    // When: Getting both active and specific version
    WorkflowDirectedGraph activeResult = service.getDirectedGraph("workflow1");
    WorkflowDirectedGraph v1Result = service.getDirectedGraph("workflow1", 1L);

    // Then: Should return different graphs
    assertThat(activeResult.getVersion()).isEqualTo(2L);
    assertThat(v1Result.getVersion()).isEqualTo(1L);
  }

  @Test
  void service_withAllDependencies_shouldInitializeCorrectly() {
    // Given: All dependencies provided
    // When: Creating service
    service = new WorkflowDirectedGraphService(Optional.of(repository), sessionService, objectConverter);

    // Then: Service should be created successfully
    assertThat(service).isNotNull();
  }

  @Test
  void service_withEmptyRepositoryOptional_shouldInitializeCorrectly() {
    // Given: Empty repository optional
    // When: Creating service
    service = new WorkflowDirectedGraphService(Optional.empty(), sessionService, objectConverter);

    // Then: Service should be created successfully
    assertThat(service).isNotNull();
  }

  // Helper methods

  private VersionedWorkflow createVersionedWorkflow(String workflowId, Long version, String swadl, boolean active) {
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setSwadl(swadl);
    workflow.setActive(active);
    workflow.setPublished(active);
    return workflow;
  }

  private Workflow createWorkflow(String workflowId, Long version) {
    Workflow workflow = new Workflow();
    workflow.setId(workflowId);
    workflow.setVersion(version);
    workflow.setActivities(Collections.emptyList());
    workflow.setVariables(Collections.emptyMap());
    return workflow;
  }
}
