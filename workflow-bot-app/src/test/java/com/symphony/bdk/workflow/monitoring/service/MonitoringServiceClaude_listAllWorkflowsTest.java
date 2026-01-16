package com.symphony.bdk.workflow.monitoring.service;

import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.monitoring.repository.ActivityQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.VariableQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.WorkflowInstQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.WorkflowQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for MonitoringService.listAllWorkflows() method.
 * Tests both scenarios: with and without VersionedWorkflowRepository present.
 */
@ExtendWith(MockitoExtension.class)
class MonitoringServiceClaude_listAllWorkflowsTest {

  @Mock
  private WorkflowDirectedGraphService workflowDirectedGraphService;

  @Mock
  private WorkflowQueryRepository workflowQueryRepository;

  @Mock
  private WorkflowInstQueryRepository workflowInstQueryRepository;

  @Mock
  private ActivityQueryRepository activityQueryRepository;

  @Mock
  private VariableQueryRepository variableQueryRepository;

  @Mock
  private ObjectConverter objectConverter;

  @Mock
  private VersionedWorkflowRepository versionedWorkflowRepository;

  private MonitoringService monitoringService;

  // ==================== Tests with VersionedWorkflowRepository present ====================

  @Test
  void listAllWorkflows_withVersionedRepository_shouldReturnActiveWorkflows() {
    // Given: VersionedWorkflowRepository is present with active workflows
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.of(versionedWorkflowRepository)
    );

    List<VersionedWorkflow> activeWorkflows = createMockVersionedWorkflows();
    List<WorkflowView> expectedViews = createMockWorkflowViews();

    when(versionedWorkflowRepository.findByActiveTrue()).thenReturn(activeWorkflows);
    when(objectConverter.convertCollection(activeWorkflows, WorkflowView.class))
        .thenReturn(expectedViews);

    // When: calling listAllWorkflows
    List<WorkflowView> result = monitoringService.listAllWorkflows();

    // Then: should return active workflows converted to WorkflowView
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(expectedViews);

    verify(versionedWorkflowRepository).findByActiveTrue();
    verify(objectConverter).convertCollection(activeWorkflows, WorkflowView.class);
    verify(workflowQueryRepository, never()).findAll();
  }

  @Test
  void listAllWorkflows_withVersionedRepository_whenNoActiveWorkflows_shouldReturnEmptyList() {
    // Given: VersionedWorkflowRepository is present but has no active workflows
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.of(versionedWorkflowRepository)
    );

    when(versionedWorkflowRepository.findByActiveTrue()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(Collections.emptyList(), WorkflowView.class))
        .thenReturn(Collections.emptyList());

    // When: calling listAllWorkflows
    List<WorkflowView> result = monitoringService.listAllWorkflows();

    // Then: should return empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();

    verify(versionedWorkflowRepository).findByActiveTrue();
    verify(objectConverter).convertCollection(Collections.emptyList(), WorkflowView.class);
    verify(workflowQueryRepository, never()).findAll();
  }

  @Test
  void listAllWorkflows_withVersionedRepository_shouldOnlyReturnActiveWorkflows() {
    // Given: VersionedWorkflowRepository has multiple workflows, but only some are active
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.of(versionedWorkflowRepository)
    );

    // Create workflows where only some are active
    List<VersionedWorkflow> activeWorkflows = Arrays.asList(
        createVersionedWorkflow("workflow-1", 1L, true),
        createVersionedWorkflow("workflow-2", 3L, true)
    );

    List<WorkflowView> expectedViews = Arrays.asList(
        createWorkflowView("workflow-1", 1L),
        createWorkflowView("workflow-2", 3L)
    );

    when(versionedWorkflowRepository.findByActiveTrue()).thenReturn(activeWorkflows);
    when(objectConverter.convertCollection(activeWorkflows, WorkflowView.class))
        .thenReturn(expectedViews);

    // When: calling listAllWorkflows
    List<WorkflowView> result = monitoringService.listAllWorkflows();

    // Then: should only return the active workflows
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getId()).isEqualTo("workflow-1");
    assertThat(result.get(1).getId()).isEqualTo("workflow-2");

    verify(versionedWorkflowRepository).findByActiveTrue();
    verify(workflowQueryRepository, never()).findAll();
  }

  // ==================== Tests without VersionedWorkflowRepository ====================

  @Test
  void listAllWorkflows_withoutVersionedRepository_shouldUseWorkflowQueryRepository() {
    // Given: VersionedWorkflowRepository is not present (Optional.empty())
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    List<WorkflowDomain> workflowDomains = createMockWorkflowDomains();
    List<WorkflowView> expectedViews = createMockWorkflowViews();

    when(workflowQueryRepository.findAll()).thenReturn(workflowDomains);
    when(objectConverter.convertCollection(workflowDomains, WorkflowView.class))
        .thenReturn(expectedViews);

    // When: calling listAllWorkflows
    List<WorkflowView> result = monitoringService.listAllWorkflows();

    // Then: should use workflowQueryRepository.findAll() as fallback
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(expectedViews);

    verify(workflowQueryRepository).findAll();
    verify(objectConverter).convertCollection(workflowDomains, WorkflowView.class);
  }

  @Test
  void listAllWorkflows_withoutVersionedRepository_whenNoWorkflows_shouldReturnEmptyList() {
    // Given: VersionedWorkflowRepository is not present and workflowQueryRepository returns empty
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    when(workflowQueryRepository.findAll()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(Collections.emptyList(), WorkflowView.class))
        .thenReturn(Collections.emptyList());

    // When: calling listAllWorkflows
    List<WorkflowView> result = monitoringService.listAllWorkflows();

    // Then: should return empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();

    verify(workflowQueryRepository).findAll();
    verify(objectConverter).convertCollection(Collections.emptyList(), WorkflowView.class);
  }

  @Test
  void listAllWorkflows_withoutVersionedRepository_shouldReturnAllWorkflows() {
    // Given: VersionedWorkflowRepository is not present
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );

    List<WorkflowDomain> workflowDomains = Arrays.asList(
        createWorkflowDomain("workflow-1"),
        createWorkflowDomain("workflow-2"),
        createWorkflowDomain("workflow-3")
    );

    List<WorkflowView> expectedViews = Arrays.asList(
        createWorkflowView("workflow-1", null),
        createWorkflowView("workflow-2", null),
        createWorkflowView("workflow-3", null)
    );

    when(workflowQueryRepository.findAll()).thenReturn(workflowDomains);
    when(objectConverter.convertCollection(workflowDomains, WorkflowView.class))
        .thenReturn(expectedViews);

    // When: calling listAllWorkflows
    List<WorkflowView> result = monitoringService.listAllWorkflows();

    // Then: should return all workflows from workflowQueryRepository
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);

    verify(workflowQueryRepository).findAll();
  }

  // ==================== Test ObjectConverter interaction ====================

  @Test
  void listAllWorkflows_shouldPassCorrectParametersToObjectConverter() {
    // Given: VersionedWorkflowRepository is present
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.of(versionedWorkflowRepository)
    );

    List<VersionedWorkflow> activeWorkflows = createMockVersionedWorkflows();
    List<WorkflowView> expectedViews = createMockWorkflowViews();

    when(versionedWorkflowRepository.findByActiveTrue()).thenReturn(activeWorkflows);
    when(objectConverter.convertCollection(activeWorkflows, WorkflowView.class))
        .thenReturn(expectedViews);

    // When: calling listAllWorkflows
    monitoringService.listAllWorkflows();

    // Then: should call objectConverter with correct parameters
    verify(objectConverter).convertCollection(
        eq(activeWorkflows),
        eq(WorkflowView.class)
    );
  }

  @Test
  void listAllWorkflows_withVersionedRepository_shouldReturnConvertedResult() {
    // Given: VersionedWorkflowRepository is present and converter returns specific views
    monitoringService = new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.of(versionedWorkflowRepository)
    );

    List<VersionedWorkflow> activeWorkflows = createMockVersionedWorkflows();
    List<WorkflowView> convertedViews = new ArrayList<>();
    WorkflowView view1 = WorkflowView.builder()
        .id("converted-1")
        .version(10L)
        .createdBy(100L)
        .build();
    WorkflowView view2 = WorkflowView.builder()
        .id("converted-2")
        .version(20L)
        .createdBy(200L)
        .build();
    convertedViews.add(view1);
    convertedViews.add(view2);

    when(versionedWorkflowRepository.findByActiveTrue()).thenReturn(activeWorkflows);
    when(objectConverter.convertCollection(activeWorkflows, WorkflowView.class))
        .thenReturn(convertedViews);

    // When: calling listAllWorkflows
    List<WorkflowView> result = monitoringService.listAllWorkflows();

    // Then: should return exactly what the converter returns
    assertThat(result).isSameAs(convertedViews);
    assertThat(result.get(0).getId()).isEqualTo("converted-1");
    assertThat(result.get(0).getVersion()).isEqualTo(10L);
    assertThat(result.get(1).getId()).isEqualTo("converted-2");
    assertThat(result.get(1).getVersion()).isEqualTo(20L);
  }

  // ==================== Helper Methods ====================

  private List<VersionedWorkflow> createMockVersionedWorkflows() {
    return Arrays.asList(
        createVersionedWorkflow("workflow-1", 1L, true),
        createVersionedWorkflow("workflow-2", 2L, true)
    );
  }

  private VersionedWorkflow createVersionedWorkflow(String workflowId, Long version, Boolean active) {
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("id-" + workflowId);
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setActive(active);
    workflow.setPublished(true);
    workflow.setSwadl("workflow: " + workflowId);
    workflow.setCreatedBy(1000L);
    return workflow;
  }

  private List<WorkflowView> createMockWorkflowViews() {
    return Arrays.asList(
        createWorkflowView("workflow-1", 1L),
        createWorkflowView("workflow-2", 2L)
    );
  }

  private WorkflowView createWorkflowView(String id, Long version) {
    return WorkflowView.builder()
        .id(id)
        .version(version)
        .createdBy(1000L)
        .build();
  }

  private List<WorkflowDomain> createMockWorkflowDomains() {
    return Arrays.asList(
        createWorkflowDomain("workflow-1"),
        createWorkflowDomain("workflow-2")
    );
  }

  private WorkflowDomain createWorkflowDomain(String workflowId) {
    return WorkflowDomain.builder()
        .id(workflowId)
        .name(workflowId + "-name")
        .version(1L)
        .build();
  }
}
