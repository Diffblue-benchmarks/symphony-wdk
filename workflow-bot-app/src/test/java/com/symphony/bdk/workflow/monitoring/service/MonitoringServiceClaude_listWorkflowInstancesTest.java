package com.symphony.bdk.workflow.monitoring.service;

import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.monitoring.repository.ActivityQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.VariableQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.WorkflowInstQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.WorkflowQueryRepository;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;
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
 * Test class for MonitoringService.listWorkflowInstances() method.
 * Tests all four parameter combinations: status (null/non-null) and version (null/non-null).
 */
@ExtendWith(MockitoExtension.class)
class MonitoringServiceClaude_listWorkflowInstancesTest {

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

  // ==================== Tests with both status and version provided ====================

  @Test
  void listWorkflowInstances_withStatusAndVersion_shouldCallFindAllByIdAndStatusAndVersion() {
    // Given: MonitoringService with both status and version provided
    monitoringService = createMonitoringService();

    String workflowId = "workflow-123";
    String status = "COMPLETED";
    Long version = 5L;

    List<WorkflowInstanceDomain> domains = createMockInstanceDomains();
    List<WorkflowInstView> expectedViews = createMockInstanceViews();

    when(workflowInstQueryRepository.findAllByIdAndStatusAndVersion(
        workflowId, StatusEnum.COMPLETED, "5"))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(expectedViews);

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, status, version);

    // Then: should call findAllByIdAndStatusAndVersion and return converted views
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(expectedViews);

    verify(workflowInstQueryRepository).findAllByIdAndStatusAndVersion(
        eq(workflowId), eq(StatusEnum.COMPLETED), eq("5"));
    verify(workflowInstQueryRepository, never()).findAllByIdAndStatus(eq(workflowId), eq(StatusEnum.COMPLETED));
    verify(workflowInstQueryRepository, never()).findAllByIdAndVersion(eq(workflowId), eq("5"));
    verify(workflowInstQueryRepository, never()).findAllById(eq(workflowId));
    verify(objectConverter).convertCollection(domains, WorkflowInstView.class);
  }

  @Test
  void listWorkflowInstances_withStatusAndVersion_pendingStatus_shouldConvertStatusCorrectly() {
    // Given: status is "PENDING"
    monitoringService = createMonitoringService();

    String workflowId = "workflow-123";
    String status = "PENDING";
    Long version = 3L;

    List<WorkflowInstanceDomain> domains = createMockInstanceDomains();
    List<WorkflowInstView> expectedViews = createMockInstanceViews();

    when(workflowInstQueryRepository.findAllByIdAndStatusAndVersion(
        workflowId, StatusEnum.PENDING, "3"))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(expectedViews);

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, status, version);

    // Then: should convert PENDING status correctly
    assertThat(result).isNotNull();
    verify(workflowInstQueryRepository).findAllByIdAndStatusAndVersion(
        eq(workflowId), eq(StatusEnum.PENDING), eq("3"));
  }

  @Test
  void listWorkflowInstances_withStatusAndVersion_activeStatus_shouldConvertToPending() {
    // Given: status is "ACTIVE" (should be converted to PENDING)
    monitoringService = createMonitoringService();

    String workflowId = "workflow-456";
    String status = "ACTIVE";
    Long version = 2L;

    List<WorkflowInstanceDomain> domains = createMockInstanceDomains();
    List<WorkflowInstView> expectedViews = createMockInstanceViews();

    when(workflowInstQueryRepository.findAllByIdAndStatusAndVersion(
        workflowId, StatusEnum.PENDING, "2"))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(expectedViews);

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, status, version);

    // Then: should convert ACTIVE to PENDING
    assertThat(result).isNotNull();
    verify(workflowInstQueryRepository).findAllByIdAndStatusAndVersion(
        eq(workflowId), eq(StatusEnum.PENDING), eq("2"));
  }

  @Test
  void listWorkflowInstances_withStatusAndVersion_failedStatus_shouldWork() {
    // Given: status is "FAILED"
    monitoringService = createMonitoringService();

    String workflowId = "workflow-789";
    String status = "FAILED";
    Long version = 1L;

    List<WorkflowInstanceDomain> domains = Collections.singletonList(createInstanceDomain("inst-1", "FAILED"));
    List<WorkflowInstView> expectedViews = Collections.singletonList(createInstanceView("inst-1", StatusEnum.FAILED));

    when(workflowInstQueryRepository.findAllByIdAndStatusAndVersion(
        workflowId, StatusEnum.FAILED, "1"))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(expectedViews);

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, status, version);

    // Then: should handle FAILED status
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    verify(workflowInstQueryRepository).findAllByIdAndStatusAndVersion(
        eq(workflowId), eq(StatusEnum.FAILED), eq("1"));
  }

  @Test
  void listWorkflowInstances_withStatusAndVersion_noInstancesFound_shouldReturnEmptyList() {
    // Given: no instances found
    monitoringService = createMonitoringService();

    String workflowId = "workflow-999";
    String status = "COMPLETED";
    Long version = 10L;

    when(workflowInstQueryRepository.findAllByIdAndStatusAndVersion(
        workflowId, StatusEnum.COMPLETED, "10"))
        .thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(Collections.emptyList(), WorkflowInstView.class))
        .thenReturn(Collections.emptyList());

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, status, version);

    // Then: should return empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  // ==================== Tests with only status provided (version is null) ====================

  @Test
  void listWorkflowInstances_withStatusOnly_shouldCallFindAllByIdAndStatus() {
    // Given: only status provided, version is null
    monitoringService = createMonitoringService();

    String workflowId = "workflow-123";
    String status = "COMPLETED";
    Long version = null;

    List<WorkflowInstanceDomain> domains = createMockInstanceDomains();
    List<WorkflowInstView> expectedViews = createMockInstanceViews();

    when(workflowInstQueryRepository.findAllByIdAndStatus(workflowId, StatusEnum.COMPLETED))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(expectedViews);

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, status, version);

    // Then: should call findAllByIdAndStatus
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(expectedViews);

    verify(workflowInstQueryRepository).findAllByIdAndStatus(
        eq(workflowId), eq(StatusEnum.COMPLETED));
    verify(workflowInstQueryRepository, never()).findAllByIdAndStatusAndVersion(
        eq(workflowId), eq(StatusEnum.COMPLETED), eq("null"));
    verify(workflowInstQueryRepository, never()).findAllByIdAndVersion(eq(workflowId), eq("null"));
    verify(workflowInstQueryRepository, never()).findAllById(eq(workflowId));
  }

  @Test
  void listWorkflowInstances_withStatusOnly_pendingStatus_shouldWork() {
    // Given: only status provided with PENDING
    monitoringService = createMonitoringService();

    String workflowId = "workflow-456";
    String status = "PENDING";

    List<WorkflowInstanceDomain> domains = Arrays.asList(
        createInstanceDomain("inst-1", "PENDING"),
        createInstanceDomain("inst-2", "PENDING")
    );
    List<WorkflowInstView> expectedViews = Arrays.asList(
        createInstanceView("inst-1", StatusEnum.PENDING),
        createInstanceView("inst-2", StatusEnum.PENDING)
    );

    when(workflowInstQueryRepository.findAllByIdAndStatus(workflowId, StatusEnum.PENDING))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(expectedViews);

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, status, null);

    // Then: should return pending instances
    assertThat(result).hasSize(2);
    verify(workflowInstQueryRepository).findAllByIdAndStatus(
        eq(workflowId), eq(StatusEnum.PENDING));
  }

  @Test
  void listWorkflowInstances_withStatusOnly_noInstancesFound_shouldReturnEmptyList() {
    // Given: status provided but no instances found
    monitoringService = createMonitoringService();

    String workflowId = "workflow-empty";
    String status = "FAILED";

    when(workflowInstQueryRepository.findAllByIdAndStatus(workflowId, StatusEnum.FAILED))
        .thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(Collections.emptyList(), WorkflowInstView.class))
        .thenReturn(Collections.emptyList());

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, status, null);

    // Then: should return empty list
    assertThat(result).isEmpty();
  }

  // ==================== Tests with only version provided (status is null) ====================

  @Test
  void listWorkflowInstances_withVersionOnly_shouldCallFindAllByIdAndVersion() {
    // Given: only version provided, status is null
    monitoringService = createMonitoringService();

    String workflowId = "workflow-123";
    String status = null;
    Long version = 7L;

    List<WorkflowInstanceDomain> domains = createMockInstanceDomains();
    List<WorkflowInstView> expectedViews = createMockInstanceViews();

    when(workflowInstQueryRepository.findAllByIdAndVersion(workflowId, "7"))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(expectedViews);

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, status, version);

    // Then: should call findAllByIdAndVersion
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(expectedViews);

    verify(workflowInstQueryRepository).findAllByIdAndVersion(eq(workflowId), eq("7"));
    verify(workflowInstQueryRepository, never()).findAllByIdAndStatus(eq(workflowId), eq(StatusEnum.PENDING));
    verify(workflowInstQueryRepository, never()).findAllByIdAndStatusAndVersion(
        eq(workflowId), eq(StatusEnum.PENDING), eq("7"));
    verify(workflowInstQueryRepository, never()).findAllById(eq(workflowId));
  }

  @Test
  void listWorkflowInstances_withVersionOnly_multipleVersions_shouldReturnCorrectInstances() {
    // Given: version provided
    monitoringService = createMonitoringService();

    String workflowId = "workflow-multi";
    Long version = 3L;

    List<WorkflowInstanceDomain> domains = Arrays.asList(
        createInstanceDomain("inst-1", "COMPLETED"),
        createInstanceDomain("inst-2", "PENDING"),
        createInstanceDomain("inst-3", "FAILED")
    );
    List<WorkflowInstView> expectedViews = Arrays.asList(
        createInstanceView("inst-1", StatusEnum.COMPLETED),
        createInstanceView("inst-2", StatusEnum.PENDING),
        createInstanceView("inst-3", StatusEnum.FAILED)
    );

    when(workflowInstQueryRepository.findAllByIdAndVersion(workflowId, "3"))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(expectedViews);

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, null, version);

    // Then: should return all instances for that version
    assertThat(result).hasSize(3);
    verify(workflowInstQueryRepository).findAllByIdAndVersion(eq(workflowId), eq("3"));
  }

  @Test
  void listWorkflowInstances_withVersionOnly_noInstancesFound_shouldReturnEmptyList() {
    // Given: version provided but no instances found
    monitoringService = createMonitoringService();

    String workflowId = "workflow-empty";
    Long version = 99L;

    when(workflowInstQueryRepository.findAllByIdAndVersion(workflowId, "99"))
        .thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(Collections.emptyList(), WorkflowInstView.class))
        .thenReturn(Collections.emptyList());

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, null, version);

    // Then: should return empty list
    assertThat(result).isEmpty();
  }

  // ==================== Tests with neither status nor version provided ====================

  @Test
  void listWorkflowInstances_withNoStatusOrVersion_shouldCallFindAllById() {
    // Given: neither status nor version provided
    monitoringService = createMonitoringService();

    String workflowId = "workflow-123";
    String status = null;
    Long version = null;

    List<WorkflowInstanceDomain> domains = createMockInstanceDomains();
    List<WorkflowInstView> expectedViews = createMockInstanceViews();

    when(workflowInstQueryRepository.findAllById(workflowId))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(expectedViews);

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, status, version);

    // Then: should call findAllById
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(expectedViews);

    verify(workflowInstQueryRepository).findAllById(eq(workflowId));
    verify(workflowInstQueryRepository, never()).findAllByIdAndStatus(eq(workflowId), eq(StatusEnum.PENDING));
    verify(workflowInstQueryRepository, never()).findAllByIdAndVersion(eq(workflowId), eq("null"));
    verify(workflowInstQueryRepository, never()).findAllByIdAndStatusAndVersion(
        eq(workflowId), eq(StatusEnum.PENDING), eq("null"));
  }

  @Test
  void listWorkflowInstances_withNoStatusOrVersion_allStatuses_shouldReturnAllInstances() {
    // Given: no filters provided, should return all instances
    monitoringService = createMonitoringService();

    String workflowId = "workflow-all";

    List<WorkflowInstanceDomain> domains = Arrays.asList(
        createInstanceDomain("inst-1", "COMPLETED"),
        createInstanceDomain("inst-2", "PENDING"),
        createInstanceDomain("inst-3", "FAILED"),
        createInstanceDomain("inst-4", "COMPLETED")
    );
    List<WorkflowInstView> expectedViews = Arrays.asList(
        createInstanceView("inst-1", StatusEnum.COMPLETED),
        createInstanceView("inst-2", StatusEnum.PENDING),
        createInstanceView("inst-3", StatusEnum.FAILED),
        createInstanceView("inst-4", StatusEnum.COMPLETED)
    );

    when(workflowInstQueryRepository.findAllById(workflowId))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(expectedViews);

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, null, null);

    // Then: should return all instances regardless of status or version
    assertThat(result).hasSize(4);
    verify(workflowInstQueryRepository).findAllById(eq(workflowId));
  }

  @Test
  void listWorkflowInstances_withNoStatusOrVersion_noInstancesFound_shouldReturnEmptyList() {
    // Given: no filters and no instances found
    monitoringService = createMonitoringService();

    String workflowId = "workflow-empty";

    when(workflowInstQueryRepository.findAllById(workflowId))
        .thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(Collections.emptyList(), WorkflowInstView.class))
        .thenReturn(Collections.emptyList());

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, null, null);

    // Then: should return empty list
    assertThat(result).isEmpty();
  }

  // ==================== Test version conversion to String ====================

  @Test
  void listWorkflowInstances_withVersion_shouldConvertVersionToString() {
    // Given: version is provided as Long
    monitoringService = createMonitoringService();

    String workflowId = "workflow-123";
    Long version = 12345L;

    List<WorkflowInstanceDomain> domains = createMockInstanceDomains();
    List<WorkflowInstView> expectedViews = createMockInstanceViews();

    when(workflowInstQueryRepository.findAllByIdAndVersion(workflowId, "12345"))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(expectedViews);

    // When: calling listWorkflowInstances
    monitoringService.listWorkflowInstances(workflowId, null, version);

    // Then: should convert Long version to String
    verify(workflowInstQueryRepository).findAllByIdAndVersion(eq(workflowId), eq("12345"));
  }

  @Test
  void listWorkflowInstances_withStatusAndVersion_shouldConvertVersionToString() {
    // Given: both status and version provided
    monitoringService = createMonitoringService();

    String workflowId = "workflow-456";
    String status = "COMPLETED";
    Long version = 999L;

    List<WorkflowInstanceDomain> domains = createMockInstanceDomains();
    List<WorkflowInstView> expectedViews = createMockInstanceViews();

    when(workflowInstQueryRepository.findAllByIdAndStatusAndVersion(
        workflowId, StatusEnum.COMPLETED, "999"))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(expectedViews);

    // When: calling listWorkflowInstances
    monitoringService.listWorkflowInstances(workflowId, status, version);

    // Then: should convert Long version to String
    verify(workflowInstQueryRepository).findAllByIdAndStatusAndVersion(
        eq(workflowId), eq(StatusEnum.COMPLETED), eq("999"));
  }

  // ==================== Test ObjectConverter interaction ====================

  @Test
  void listWorkflowInstances_shouldPassCorrectParametersToObjectConverter() {
    // Given: valid parameters
    monitoringService = createMonitoringService();

    String workflowId = "workflow-123";
    List<WorkflowInstanceDomain> domains = createMockInstanceDomains();
    List<WorkflowInstView> expectedViews = createMockInstanceViews();

    when(workflowInstQueryRepository.findAllById(workflowId))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(expectedViews);

    // When: calling listWorkflowInstances
    monitoringService.listWorkflowInstances(workflowId, null, null);

    // Then: should call objectConverter with correct parameters
    verify(objectConverter).convertCollection(eq(domains), eq(WorkflowInstView.class));
  }

  @Test
  void listWorkflowInstances_shouldReturnConvertedResult() {
    // Given: objectConverter returns specific views
    monitoringService = createMonitoringService();

    String workflowId = "workflow-123";
    List<WorkflowInstanceDomain> domains = createMockInstanceDomains();

    WorkflowInstView view1 = WorkflowInstView.builder()
        .id("converted-1")
        .instanceId("inst-converted-1")
        .status(StatusEnum.COMPLETED)
        .version(10L)
        .build();
    WorkflowInstView view2 = WorkflowInstView.builder()
        .id("converted-2")
        .instanceId("inst-converted-2")
        .status(StatusEnum.PENDING)
        .version(20L)
        .build();
    List<WorkflowInstView> convertedViews = Arrays.asList(view1, view2);

    when(workflowInstQueryRepository.findAllById(workflowId))
        .thenReturn(domains);
    when(objectConverter.convertCollection(domains, WorkflowInstView.class))
        .thenReturn(convertedViews);

    // When: calling listWorkflowInstances
    List<WorkflowInstView> result = monitoringService.listWorkflowInstances(workflowId, null, null);

    // Then: should return exactly what the converter returns
    assertThat(result).isSameAs(convertedViews);
    assertThat(result.get(0).getId()).isEqualTo("converted-1");
    assertThat(result.get(0).getInstanceId()).isEqualTo("inst-converted-1");
    assertThat(result.get(1).getId()).isEqualTo("converted-2");
    assertThat(result.get(1).getInstanceId()).isEqualTo("inst-converted-2");
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

  private List<WorkflowInstanceDomain> createMockInstanceDomains() {
    return Arrays.asList(
        createInstanceDomain("inst-1", "COMPLETED"),
        createInstanceDomain("inst-2", "PENDING")
    );
  }

  private WorkflowInstanceDomain createInstanceDomain(String instanceId, String status) {
    return WorkflowInstanceDomain.builder()
        .id("workflow-123")
        .name("test-workflow")
        .version(1L)
        .instanceId(instanceId)
        .status(status)
        .startDate(Instant.parse("2024-01-15T10:00:00Z"))
        .endDate(Instant.parse("2024-01-15T11:00:00Z"))
        .duration(Duration.ofHours(1))
        .build();
  }

  private List<WorkflowInstView> createMockInstanceViews() {
    return Arrays.asList(
        createInstanceView("inst-1", StatusEnum.COMPLETED),
        createInstanceView("inst-2", StatusEnum.PENDING)
    );
  }

  private WorkflowInstView createInstanceView(String instanceId, StatusEnum status) {
    return WorkflowInstView.builder()
        .id("workflow-123")
        .version(1L)
        .instanceId(instanceId)
        .status(status)
        .startDate(Instant.parse("2024-01-15T10:00:00Z"))
        .endDate(Instant.parse("2024-01-15T11:00:00Z"))
        .duration(Duration.ofHours(1))
        .build();
  }
}
