package com.symphony.bdk.workflow.monitoring.service;

import com.symphony.bdk.workflow.api.v1.dto.VariableView;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.monitoring.repository.ActivityQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.VariableQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.WorkflowInstQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.WorkflowQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for MonitoringService.listWorkflowInstanceGlobalVars() method.
 * Tests various scenarios including parameter combinations, exception handling, and data mapping.
 */
@ExtendWith(MockitoExtension.class)
class MonitoringServiceClaude_listWorkflowInstanceGlobalVarsTest {

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

  private MonitoringService monitoringService;

  // ==================== Basic Functionality Tests ====================

  @Test
  void listWorkflowInstanceGlobalVars_withValidParameters_shouldReturnVariableViews() {
    // Given: valid workflow and instance IDs with null Instant parameters
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    setupWorkflowInstanceExists(workflowId, instanceId);

    List<VariablesDomain> domains = createMockVariablesDomains();
    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(domains);

    // When: calling listWorkflowInstanceGlobalVars
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, null, null);

    // Then: should return mapped VariableViews
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    verify(variableQueryRepository).findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null);
  }

  @Test
  void listWorkflowInstanceGlobalVars_withAllParameters_shouldPassParametersToRepository() {
    // Given: all parameters provided
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant updatedBefore = Instant.parse("2024-01-15T10:00:00Z");
    Instant updatedAfter = Instant.parse("2024-01-15T09:00:00Z");

    setupWorkflowInstanceExists(workflowId, instanceId);

    List<VariablesDomain> domains = createMockVariablesDomains();
    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(
        instanceId, updatedBefore, updatedAfter))
        .thenReturn(domains);

    // When: calling listWorkflowInstanceGlobalVars
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, updatedBefore, updatedAfter);

    // Then: should pass parameters to repository
    assertThat(result).isNotNull();
    verify(variableQueryRepository).findGlobalVarsHistoryByWorkflowInstId(
        instanceId, updatedBefore, updatedAfter);
  }

  @Test
  void listWorkflowInstanceGlobalVars_withEmptyResult_shouldReturnEmptyList() {
    // Given: repository returns empty list
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    setupWorkflowInstanceExists(workflowId, instanceId);

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: calling listWorkflowInstanceGlobalVars
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, null, null);

    // Then: should return empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  void listWorkflowInstanceGlobalVars_withSingleVariable_shouldReturnSingleVariableView() {
    // Given: repository returns single variable
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    setupWorkflowInstanceExists(workflowId, instanceId);

    VariablesDomain domain = createVariablesDomain(1, Instant.parse("2024-01-15T10:00:00Z"));
    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(Collections.singletonList(domain));

    // When: calling listWorkflowInstanceGlobalVars
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, null, null);

    // Then: should return single VariableView
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getRevision()).isEqualTo(1);
  }

  @Test
  void listWorkflowInstanceGlobalVars_withMultipleVariables_shouldReturnAllVariableViews() {
    // Given: repository returns multiple variables
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    setupWorkflowInstanceExists(workflowId, instanceId);

    List<VariablesDomain> domains = Arrays.asList(
        createVariablesDomain(1, Instant.parse("2024-01-15T10:00:00Z")),
        createVariablesDomain(2, Instant.parse("2024-01-15T11:00:00Z")),
        createVariablesDomain(3, Instant.parse("2024-01-15T12:00:00Z"))
    );
    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(domains);

    // When: calling listWorkflowInstanceGlobalVars
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, null, null);

    // Then: should return all VariableViews
    assertThat(result).hasSize(3);
    assertThat(result.get(0).getRevision()).isEqualTo(1);
    assertThat(result.get(1).getRevision()).isEqualTo(2);
    assertThat(result.get(2).getRevision()).isEqualTo(3);
  }

  // ==================== Instant Parameter Tests ====================

  @Test
  void listWorkflowInstanceGlobalVars_withOnlyUpdatedBefore_shouldPassToRepository() {
    // Given: only updatedBefore provided
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant updatedBefore = Instant.parse("2024-01-15T10:00:00Z");

    setupWorkflowInstanceExists(workflowId, instanceId);

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, updatedBefore, null))
        .thenReturn(Collections.emptyList());

    // When: calling listWorkflowInstanceGlobalVars
    monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, updatedBefore, null);

    // Then: should pass updatedBefore to repository
    verify(variableQueryRepository).findGlobalVarsHistoryByWorkflowInstId(
        instanceId, updatedBefore, null);
  }

  @Test
  void listWorkflowInstanceGlobalVars_withOnlyUpdatedAfter_shouldPassToRepository() {
    // Given: only updatedAfter provided
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant updatedAfter = Instant.parse("2024-01-15T09:00:00Z");

    setupWorkflowInstanceExists(workflowId, instanceId);

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, updatedAfter))
        .thenReturn(Collections.emptyList());

    // When: calling listWorkflowInstanceGlobalVars
    monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, updatedAfter);

    // Then: should pass updatedAfter to repository
    verify(variableQueryRepository).findGlobalVarsHistoryByWorkflowInstId(
        instanceId, null, updatedAfter);
  }

  @Test
  void listWorkflowInstanceGlobalVars_withBothInstants_shouldPassBothToRepository() {
    // Given: both Instant parameters provided
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant updatedBefore = Instant.parse("2024-01-15T10:00:00Z");
    Instant updatedAfter = Instant.parse("2024-01-15T08:00:00Z");

    setupWorkflowInstanceExists(workflowId, instanceId);

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(
        instanceId, updatedBefore, updatedAfter))
        .thenReturn(Collections.emptyList());

    // When: calling listWorkflowInstanceGlobalVars
    monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, updatedBefore, updatedAfter);

    // Then: should pass both instants to repository
    verify(variableQueryRepository).findGlobalVarsHistoryByWorkflowInstId(
        instanceId, updatedBefore, updatedAfter);
  }

  @Test
  void listWorkflowInstanceGlobalVars_withInstantAtEpoch_shouldHandleCorrectly() {
    // Given: Instant at epoch
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";
    Instant epoch = Instant.EPOCH;

    setupWorkflowInstanceExists(workflowId, instanceId);

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, epoch, null))
        .thenReturn(Collections.emptyList());

    // When: calling listWorkflowInstanceGlobalVars
    monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, epoch, null);

    // Then: should handle epoch correctly
    verify(variableQueryRepository).findGlobalVarsHistoryByWorkflowInstId(instanceId, epoch, null);
  }

  // ==================== Instance Validation Tests ====================

  @Test
  void listWorkflowInstanceGlobalVars_whenInstanceNotFound_shouldThrowNotFoundException() {
    // Given: instance does not belong to workflow
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "non-existent-instance";

    when(workflowInstQueryRepository.findAllById(workflowId))
        .thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(WorkflowInstView.class)))
        .thenReturn(Collections.emptyList());

    // When/Then: should throw NotFoundException
    assertThatThrownBy(() ->
        monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Either no workflow deployed with id workflow-1")
        .hasMessageContaining("or non-existent-instance is not an instance of it");
  }

  @Test
  void listWorkflowInstanceGlobalVars_whenWorkflowNotDeployed_shouldThrowNotFoundException() {
    // Given: workflow not deployed
    monitoringService = createMonitoringService();

    String workflowId = "non-existent-workflow";
    String instanceId = "instance-1";

    when(workflowInstQueryRepository.findAllById(workflowId))
        .thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(WorkflowInstView.class)))
        .thenReturn(Collections.emptyList());

    // When/Then: should throw NotFoundException
    assertThatThrownBy(() ->
        monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Either no workflow deployed with id non-existent-workflow");
  }

  @Test
  void listWorkflowInstanceGlobalVars_whenInstanceBelongsToDifferentWorkflow_shouldThrowNotFoundException() {
    // Given: instance belongs to different workflow
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-of-workflow-2";

    List<WorkflowInstanceDomain> domains = Collections.singletonList(
        createWorkflowInstanceDomain("workflow-2", instanceId)
    );
    List<WorkflowInstView> views = Collections.singletonList(
        createWorkflowInstView("workflow-2", instanceId)
    );

    when(workflowInstQueryRepository.findAllById(workflowId))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(views);

    // When/Then: should throw NotFoundException
    assertThatThrownBy(() ->
        monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Either no workflow deployed with id workflow-1");
  }

  @Test
  void listWorkflowInstanceGlobalVars_whenInstanceExists_shouldNotThrowException() {
    // Given: valid instance belonging to workflow
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    setupWorkflowInstanceExists(workflowId, instanceId);

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: calling listWorkflowInstanceGlobalVars
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, null, null);

    // Then: should not throw exception
    assertThat(result).isNotNull();
  }

  @Test
  void listWorkflowInstanceGlobalVars_shouldCheckInstanceBeforeQueryingVariables() {
    // Given: instance validation should happen first
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "non-existent-instance";

    when(workflowInstQueryRepository.findAllById(workflowId))
        .thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(WorkflowInstView.class)))
        .thenReturn(Collections.emptyList());

    // When/Then: should throw NotFoundException before querying variables
    assertThrows(NotFoundException.class, () ->
        monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null));

    // Then: should not call variableQueryRepository
    verify(variableQueryRepository, never()).findGlobalVarsHistoryByWorkflowInstId(
        any(), any(), any());
  }

  // ==================== VariableView Mapping Tests ====================

  @Test
  void listWorkflowInstanceGlobalVars_shouldMapVariablesDomainToVariableView() {
    // Given: VariablesDomain with all fields
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    setupWorkflowInstanceExists(workflowId, instanceId);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("key1", "value1");
    outputs.put("key2", 42);

    VariablesDomain domain = new VariablesDomain();
    domain.setOutputs(outputs);
    domain.setRevision(5);
    domain.setUpdateTime(Instant.parse("2024-01-15T10:00:00Z"));

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(Collections.singletonList(domain));

    // When: calling listWorkflowInstanceGlobalVars
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, null, null);

    // Then: should correctly map to VariableView
    assertThat(result).hasSize(1);
    VariableView view = result.get(0);
    assertThat(view.getOutputs()).containsEntry("key1", "value1");
    assertThat(view.getOutputs()).containsEntry("key2", 42);
    assertThat(view.getRevision()).isEqualTo(5);
    assertThat(view.getUpdateTime()).isEqualTo(Instant.parse("2024-01-15T10:00:00Z"));
  }

  @Test
  void listWorkflowInstanceGlobalVars_withEmptyOutputs_shouldMapCorrectly() {
    // Given: VariablesDomain with empty outputs
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    setupWorkflowInstanceExists(workflowId, instanceId);

    VariablesDomain domain = new VariablesDomain();
    domain.setOutputs(Collections.emptyMap());
    domain.setRevision(1);
    domain.setUpdateTime(Instant.parse("2024-01-15T10:00:00Z"));

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(Collections.singletonList(domain));

    // When: calling listWorkflowInstanceGlobalVars
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, null, null);

    // Then: should map empty outputs
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getOutputs()).isEmpty();
  }

  @Test
  void listWorkflowInstanceGlobalVars_withComplexOutputs_shouldMapCorrectly() {
    // Given: VariablesDomain with complex outputs
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    setupWorkflowInstanceExists(workflowId, instanceId);

    Map<String, Object> nestedMap = new HashMap<>();
    nestedMap.put("nested", "value");

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("string", "text");
    outputs.put("number", 123);
    outputs.put("boolean", true);
    outputs.put("list", Arrays.asList("a", "b", "c"));
    outputs.put("map", nestedMap);
    outputs.put("nullValue", null);

    VariablesDomain domain = new VariablesDomain();
    domain.setOutputs(outputs);
    domain.setRevision(1);
    domain.setUpdateTime(Instant.parse("2024-01-15T10:00:00Z"));

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(Collections.singletonList(domain));

    // When: calling listWorkflowInstanceGlobalVars
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, null, null);

    // Then: should map complex outputs
    assertThat(result).hasSize(1);
    VariableView view = result.get(0);
    assertThat(view.getOutputs()).containsEntry("string", "text");
    assertThat(view.getOutputs()).containsEntry("number", 123);
    assertThat(view.getOutputs()).containsEntry("boolean", true);
    assertThat(view.getOutputs()).containsKey("list");
    assertThat(view.getOutputs()).containsKey("map");
    assertThat(view.getOutputs()).containsEntry("nullValue", null);
  }

  @Test
  void listWorkflowInstanceGlobalVars_withMultipleRevisions_shouldReturnAllInOrder() {
    // Given: multiple variables with different revisions
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    setupWorkflowInstanceExists(workflowId, instanceId);

    List<VariablesDomain> domains = Arrays.asList(
        createVariablesDomain(1, Instant.parse("2024-01-15T10:00:00Z")),
        createVariablesDomain(2, Instant.parse("2024-01-15T11:00:00Z")),
        createVariablesDomain(5, Instant.parse("2024-01-15T12:00:00Z"))
    );

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(domains);

    // When: calling listWorkflowInstanceGlobalVars
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, null, null);

    // Then: should return all revisions in order
    assertThat(result).hasSize(3);
    assertThat(result.get(0).getRevision()).isEqualTo(1);
    assertThat(result.get(1).getRevision()).isEqualTo(2);
    assertThat(result.get(2).getRevision()).isEqualTo(5);
  }

  // ==================== Edge Cases ====================

  @Test
  void listWorkflowInstanceGlobalVars_withZeroRevision_shouldMapCorrectly() {
    // Given: variable with revision 0
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    setupWorkflowInstanceExists(workflowId, instanceId);

    VariablesDomain domain = createVariablesDomain(0, Instant.parse("2024-01-15T10:00:00Z"));

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(Collections.singletonList(domain));

    // When: calling listWorkflowInstanceGlobalVars
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, null, null);

    // Then: should map revision 0 correctly
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getRevision()).isEqualTo(0);
  }

  @Test
  void listWorkflowInstanceGlobalVars_withNullUpdateTime_shouldMapCorrectly() {
    // Given: variable with null updateTime
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    setupWorkflowInstanceExists(workflowId, instanceId);

    VariablesDomain domain = new VariablesDomain();
    domain.setRevision(1);
    domain.setUpdateTime(null);

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(Collections.singletonList(domain));

    // When: calling listWorkflowInstanceGlobalVars
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, null, null);

    // Then: should map null updateTime correctly
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getUpdateTime()).isNull();
  }

  @Test
  void listWorkflowInstanceGlobalVars_withLargeNumberOfVariables_shouldReturnAll() {
    // Given: large number of variables
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    setupWorkflowInstanceExists(workflowId, instanceId);

    List<VariablesDomain> domains = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      domains.add(createVariablesDomain(i, Instant.parse("2024-01-15T10:00:00Z").plusSeconds(i)));
    }

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(domains);

    // When: calling listWorkflowInstanceGlobalVars
    List<VariableView> result = monitoringService.listWorkflowInstanceGlobalVars(
        workflowId, instanceId, null, null);

    // Then: should return all 100 variables
    assertThat(result).hasSize(100);
    assertThat(result.get(0).getRevision()).isEqualTo(0);
    assertThat(result.get(99).getRevision()).isEqualTo(99);
  }

  // ==================== Repository Interaction Tests ====================

  @Test
  void listWorkflowInstanceGlobalVars_shouldCallRepositoryWithCorrectInstanceId() {
    // Given: valid parameters
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "specific-instance-id";

    setupWorkflowInstanceExists(workflowId, instanceId);

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: calling listWorkflowInstanceGlobalVars
    monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null);

    // Then: should call repository with correct instanceId
    verify(variableQueryRepository).findGlobalVarsHistoryByWorkflowInstId(
        eq(instanceId), eq(null), eq(null));
  }

  @Test
  void listWorkflowInstanceGlobalVars_shouldCallListWorkflowInstancesForValidation() {
    // Given: valid parameters
    monitoringService = createMonitoringService();

    String workflowId = "workflow-1";
    String instanceId = "instance-1";

    setupWorkflowInstanceExists(workflowId, instanceId);

    when(variableQueryRepository.findGlobalVarsHistoryByWorkflowInstId(instanceId, null, null))
        .thenReturn(Collections.emptyList());

    // When: calling listWorkflowInstanceGlobalVars
    monitoringService.listWorkflowInstanceGlobalVars(workflowId, instanceId, null, null);

    // Then: should call workflowInstQueryRepository for validation
    verify(workflowInstQueryRepository).findAllById(workflowId);
  }

  // ==================== Helper Methods ====================

  private MonitoringService createMonitoringService() {
    return new MonitoringService(
        workflowDirectedGraphService,
        workflowQueryRepository,
        workflowInstQueryRepository,
        activityQueryRepository,
        variableQueryRepository,
        objectConverter,
        Optional.empty()
    );
  }

  private void setupWorkflowInstanceExists(String workflowId, String instanceId) {
    List<WorkflowInstanceDomain> domains = Collections.singletonList(
        createWorkflowInstanceDomain(workflowId, instanceId)
    );
    List<WorkflowInstView> views = Collections.singletonList(
        createWorkflowInstView(workflowId, instanceId)
    );

    when(workflowInstQueryRepository.findAllById(workflowId))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(views);
  }

  private List<VariablesDomain> createMockVariablesDomains() {
    return Arrays.asList(
        createVariablesDomain(1, Instant.parse("2024-01-15T10:00:00Z")),
        createVariablesDomain(2, Instant.parse("2024-01-15T11:00:00Z"))
    );
  }

  private VariablesDomain createVariablesDomain(int revision, Instant updateTime) {
    VariablesDomain domain = new VariablesDomain();
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("var" + revision, "value" + revision);
    domain.setOutputs(outputs);
    domain.setRevision(revision);
    domain.setUpdateTime(updateTime);
    return domain;
  }

  private WorkflowInstanceDomain createWorkflowInstanceDomain(String workflowId, String instanceId) {
    return WorkflowInstanceDomain.builder()
        .id(workflowId)
        .instanceId(instanceId)
        .name("test-workflow")
        .version(1L)
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T09:00:00Z"))
        .endDate(Instant.parse("2024-01-15T10:00:00Z"))
        .duration(Duration.ofHours(1))
        .build();
  }

  private WorkflowInstView createWorkflowInstView(String workflowId, String instanceId) {
    return WorkflowInstView.builder()
        .id(workflowId)
        .instanceId(instanceId)
        .version(1L)
        .build();
  }
}
