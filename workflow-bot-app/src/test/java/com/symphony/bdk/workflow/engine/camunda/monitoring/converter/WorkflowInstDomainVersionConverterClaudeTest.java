package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for WorkflowInstDomainVersionConverter.
 * Tests the constructor and apply method with various scenarios.
 */
class WorkflowInstDomainVersionConverterClaudeTest {

  private WorkflowInstDomainVersionConverter converter;

  @BeforeEach
  void setUp() {
    converter = new WorkflowInstDomainVersionConverter();
  }

  // ==================== Constructor Tests ====================

  @Test
  void constructor_shouldCreateInstance() {
    // When: creating a new instance
    WorkflowInstDomainVersionConverter newConverter = new WorkflowInstDomainVersionConverter();

    // Then: instance should not be null
    assertThat(newConverter).isNotNull();
  }

  @Test
  void constructor_multipleInstances_shouldCreateIndependentInstances() {
    // When: creating multiple instances
    WorkflowInstDomainVersionConverter converter1 = new WorkflowInstDomainVersionConverter();
    WorkflowInstDomainVersionConverter converter2 = new WorkflowInstDomainVersionConverter();

    // Then: instances should be independent
    assertThat(converter1).isNotNull();
    assertThat(converter2).isNotNull();
    assertThat(converter1).isNotSameAs(converter2);
  }

  @Test
  void constructor_shouldExtendAbstractInstanceDomainConverter() {
    // When: creating a new instance
    WorkflowInstDomainVersionConverter newConverter = new WorkflowInstDomainVersionConverter();

    // Then: should be instance of AbstractInstanceDomainConverter
    assertThat(newConverter).isInstanceOf(AbstractInstanceDomainConverter.class);
  }

  // ==================== apply() Method Tests ====================

  @Test
  void apply_withVersionInMap_shouldConvertWithVersion() {
    // Given: a complete HistoricProcessInstanceEntity and version map
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionId("process-def-789");

    Date startTime = Date.from(Instant.parse("2024-01-15T10:30:00Z"));
    entity.setStartTime(startTime);

    Date endTime = Date.from(Instant.parse("2024-01-15T11:30:00Z"));
    entity.setEndTime(endTime);

    entity.setState("COMPLETED");
    entity.setEndActivityId("endEvent1");
    entity.setDurationInMillis(3600000L); // 1 hour in milliseconds

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-789", "5");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: all fields including version should be converted correctly
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("workflow-inst-123");
    assertThat(result.getName()).isEqualTo("myWorkflow");
    assertThat(result.getInstanceId()).isEqualTo("proc-inst-456");
    assertThat(result.getStartDate()).isEqualTo(Instant.parse("2024-01-15T10:30:00Z"));
    assertThat(result.getEndDate()).isEqualTo(Instant.parse("2024-01-15T11:30:00Z"));
    assertThat(result.getStatus()).isEqualTo("COMPLETED");
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(3600000L));
    assertThat(result.getVersion()).isEqualTo(5L);
  }

  @Test
  void apply_withVersionNotInMap_shouldReturnNull() {
    // Given: a HistoricProcessInstanceEntity with process definition ID not in version map
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionId("process-def-not-in-map");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("ACTIVE");

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-different", "5");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: result should be null (no version found)
    assertThat(result).isNull();
  }

  @Test
  void apply_withEmptyVersionMap_shouldReturnNull() {
    // Given: a HistoricProcessInstanceEntity and empty version map
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionId("process-def-789");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("ACTIVE");

    Map<String, String> versionMap = new HashMap<>();

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: result should be null (empty map)
    assertThat(result).isNull();
  }

  @Test
  void apply_withNullVersionInMap_shouldReturnNull() {
    // Given: a HistoricProcessInstanceEntity and version map with null value
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionId("process-def-789");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("ACTIVE");

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-789", null);

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: result should be null (null value in map)
    assertThat(result).isNull();
  }

  @Test
  void apply_withActiveInstanceAndVersion_shouldSetCorrectStatusAndVersion() {
    // Given: an active HistoricProcessInstanceEntity with version in map
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-active");
    entity.setProcessDefinitionKey("activeWorkflow");
    entity.setProcessInstanceId("proc-inst-active");
    entity.setProcessDefinitionId("process-def-active");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(null); // null for active instances
    entity.setState("ACTIVE");
    entity.setEndActivityId(null);
    entity.setDurationInMillis(null);

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-active", "3");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: status should be PENDING and version should be set
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("PENDING");
    assertThat(result.getVersion()).isEqualTo(3L);
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getDuration()).isNull();
  }

  @Test
  void apply_withCompletedStatusEndEventAndVersion_shouldSetCorrectStatusAndVersion() {
    // Given: a completed HistoricProcessInstanceEntity with endEvent and version in map
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-completed");
    entity.setProcessDefinitionKey("completedWorkflow");
    entity.setProcessInstanceId("proc-inst-completed");
    entity.setProcessDefinitionId("process-def-completed");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("endEventSuccess");
    entity.setDurationInMillis(3600000L);

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-completed", "7");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: status should be COMPLETED and version should be set
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("COMPLETED");
    assertThat(result.getVersion()).isEqualTo(7L);
  }

  @Test
  void apply_withCompletedStatusNonEndEventAndVersion_shouldSetFailedStatusAndVersion() {
    // Given: a completed HistoricProcessInstanceEntity without endEvent but with version
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-failed");
    entity.setProcessDefinitionKey("failedWorkflow");
    entity.setProcessInstanceId("proc-inst-failed");
    entity.setProcessDefinitionId("process-def-failed");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("errorActivity"); // not an endEvent
    entity.setDurationInMillis(3600000L);

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-failed", "2");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: status should be FAILED and version should be set
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("FAILED");
    assertThat(result.getVersion()).isEqualTo(2L);
  }

  @Test
  void apply_withZeroVersion_shouldHandleCorrectly() {
    // Given: a HistoricProcessInstanceEntity with version "0" in map
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-v0");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-v0");
    entity.setProcessDefinitionId("process-def-v0");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("ACTIVE");

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-v0", "0");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: version should be 0
    assertThat(result).isNotNull();
    assertThat(result.getVersion()).isEqualTo(0L);
  }

  @Test
  void apply_withLargeVersion_shouldHandleCorrectly() {
    // Given: a HistoricProcessInstanceEntity with large version number
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-large-v");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-large-v");
    entity.setProcessDefinitionId("process-def-large-v");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("ACTIVE");

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-large-v", "99999");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: large version should be handled correctly
    assertThat(result).isNotNull();
    assertThat(result.getVersion()).isEqualTo(99999L);
  }

  @Test
  void apply_withMultipleVersionsInMap_shouldUseCorrectVersion() {
    // Given: a HistoricProcessInstanceEntity and version map with multiple entries
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-multi");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-multi");
    entity.setProcessDefinitionId("process-def-target");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("ACTIVE");

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-one", "1");
    versionMap.put("process-def-two", "2");
    versionMap.put("process-def-target", "10");
    versionMap.put("process-def-four", "4");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: should use the correct version for the process definition ID
    assertThat(result).isNotNull();
    assertThat(result.getVersion()).isEqualTo(10L);
  }

  @Test
  void apply_withPendingStatusAndVersion_shouldSetStatusToPending() {
    // Given: a HistoricProcessInstanceEntity with PENDING state and version
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-pending");
    entity.setProcessDefinitionKey("pendingWorkflow");
    entity.setProcessInstanceId("proc-inst-pending");
    entity.setProcessDefinitionId("process-def-pending");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("PENDING");
    entity.setEndActivityId(null);

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-pending", "1");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: status should be PENDING and version should be set
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("PENDING");
    assertThat(result.getVersion()).isEqualTo(1L);
  }

  @Test
  void apply_withZeroDurationAndVersion_shouldSetZeroDurationAndVersion() {
    // Given: a HistoricProcessInstanceEntity with zero duration and version
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-zero-duration");
    entity.setProcessDefinitionKey("quickWorkflow");
    entity.setProcessInstanceId("proc-inst-zero");
    entity.setProcessDefinitionId("process-def-zero");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("endEvent");
    entity.setDurationInMillis(0L);

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-zero", "4");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: duration should be zero and version should be set
    assertThat(result).isNotNull();
    assertThat(result.getDuration()).isEqualTo(Duration.ZERO);
    assertThat(result.getVersion()).isEqualTo(4L);
  }

  @Test
  void apply_withLongDurationAndVersion_shouldHandleCorrectly() {
    // Given: a HistoricProcessInstanceEntity with long duration and version
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-long");
    entity.setProcessDefinitionKey("longRunningWorkflow");
    entity.setProcessInstanceId("proc-inst-long");
    entity.setProcessDefinitionId("process-def-long");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-20T10:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("endEvent");
    entity.setDurationInMillis(432000000L); // 5 days in milliseconds

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-long", "15");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: duration and version should be handled correctly
    assertThat(result).isNotNull();
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(432000000L));
    assertThat(result.getDuration().toDays()).isEqualTo(5);
    assertThat(result.getVersion()).isEqualTo(15L);
  }

  @Test
  void apply_withSpecialCharactersInIdsAndVersion_shouldPreserveIdsAndVersion() {
    // Given: a HistoricProcessInstanceEntity with special characters and version
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-with-dashes_and_underscores");
    entity.setProcessDefinitionKey("workflow.with.dots");
    entity.setProcessInstanceId("proc:inst:456");
    entity.setProcessDefinitionId("process-def:with:colons");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("ACTIVE");

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def:with:colons", "8");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: special characters should be preserved and version should be set
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("workflow-inst-with-dashes_and_underscores");
    assertThat(result.getName()).isEqualTo("workflow.with.dots");
    assertThat(result.getInstanceId()).isEqualTo("proc:inst:456");
    assertThat(result.getVersion()).isEqualTo(8L);
  }

  @Test
  void apply_withCaseInsensitiveActiveStateAndVersion_shouldSetStatusToPendingAndVersion() {
    // Given: a HistoricProcessInstanceEntity with lowercase "active" state and version
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-active-lower");
    entity.setProcessDefinitionKey("workflow");
    entity.setProcessInstanceId("proc-inst");
    entity.setProcessDefinitionId("process-def-lower");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("active"); // lowercase

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-lower", "6");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: status should be PENDING (case-insensitive) and version should be set
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("PENDING");
    assertThat(result.getVersion()).isEqualTo(6L);
  }

  @Test
  void apply_withCaseInsensitiveCompletedStateAndVersion_shouldResolveCorrectlyAndSetVersion() {
    // Given: a HistoricProcessInstanceEntity with lowercase "completed" state and version
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-completed-lower");
    entity.setProcessDefinitionKey("workflow");
    entity.setProcessInstanceId("proc-inst");
    entity.setProcessDefinitionId("process-def-completed-lower");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setState("completed"); // lowercase
    entity.setEndActivityId("endEventSuccess");
    entity.setDurationInMillis(3600000L);

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-completed-lower", "11");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: status should be COMPLETED (case-insensitive) and version should be set
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("COMPLETED");
    assertThat(result.getVersion()).isEqualTo(11L);
  }

  @Test
  void apply_withUnknownStateButVersionInMap_shouldReturnNullStatus() {
    // Given: a HistoricProcessInstanceEntity with unknown state but version in map
    // Note: According to the implementation, if version is found, the result is built
    // even with unknown state (status will be null but object is not null)
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-unknown");
    entity.setProcessDefinitionKey("workflow");
    entity.setProcessInstanceId("proc-inst");
    entity.setProcessDefinitionId("process-def-unknown");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("UNKNOWN_STATE");

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-unknown", "9");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: result should be not null with version set but status is null
    assertThat(result).isNotNull();
    assertThat(result.getVersion()).isEqualTo(9L);
    assertThat(result.getStatus()).isNull();
  }

  @Test
  void apply_withCompletedStatusNullEndActivityAndVersion_shouldSetStatusToFailedAndVersion() {
    // Given: a completed entity with null endActivityId and version
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-failed-null");
    entity.setProcessDefinitionKey("failedWorkflow");
    entity.setProcessInstanceId("proc-inst-failed-null");
    entity.setProcessDefinitionId("process-def-failed-null");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId(null); // null endActivityId
    entity.setDurationInMillis(3600000L);

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-failed-null", "12");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: status should be FAILED and version should be set
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("FAILED");
    assertThat(result.getVersion()).isEqualTo(12L);
  }

  @Test
  void apply_withEndActivityStartingWithEndButNotEndEventAndVersion_shouldSetStatusToFailedAndVersion() {
    // Given: a completed entity with endActivity starting with "end" but not "endEvent" and version
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-end");
    entity.setProcessDefinitionKey("workflow");
    entity.setProcessInstanceId("proc-inst");
    entity.setProcessDefinitionId("process-def-end");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("endActivity"); // starts with "end" but not "endEvent"
    entity.setDurationInMillis(3600000L);

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-end", "13");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: status should be FAILED and version should be set
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("FAILED");
    assertThat(result.getVersion()).isEqualTo(13L);
  }

  @Test
  void apply_withVeryShortDurationAndVersion_shouldHandleCorrectly() {
    // Given: a HistoricProcessInstanceEntity with very short duration and version
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-short");
    entity.setProcessDefinitionKey("quickWorkflow");
    entity.setProcessInstanceId("proc-inst-short");
    entity.setProcessDefinitionId("process-def-short");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00.000Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T10:30:00.001Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("endEvent");
    entity.setDurationInMillis(1L);

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-short", "14");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: duration and version should be handled correctly
    assertThat(result).isNotNull();
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(1L));
    assertThat(result.getVersion()).isEqualTo(14L);
  }

  @Test
  void apply_withMultipleDifferentEntities_shouldConvertEachIndependently() {
    // Given: multiple different entities with version map
    HistoricProcessInstanceEntity entity1 = new HistoricProcessInstanceEntity();
    entity1.setId("workflow-inst-1");
    entity1.setProcessDefinitionKey("workflow1");
    entity1.setProcessInstanceId("proc-inst-1");
    entity1.setProcessDefinitionId("process-def-1");
    entity1.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity1.setState("ACTIVE");

    HistoricProcessInstanceEntity entity2 = new HistoricProcessInstanceEntity();
    entity2.setId("workflow-inst-2");
    entity2.setProcessDefinitionKey("workflow2");
    entity2.setProcessInstanceId("proc-inst-2");
    entity2.setProcessDefinitionId("process-def-2");
    entity2.setStartTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity2.setEndTime(Date.from(Instant.parse("2024-01-15T12:30:00Z")));
    entity2.setState("COMPLETED");
    entity2.setEndActivityId("endEvent");
    entity2.setDurationInMillis(3600000L);

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-1", "20");
    versionMap.put("process-def-2", "21");

    // When: calling apply on both entities
    WorkflowInstanceDomain result1 = converter.apply(entity1, versionMap);
    WorkflowInstanceDomain result2 = converter.apply(entity2, versionMap);

    // Then: each should be converted independently with correct versions
    assertThat(result1).isNotNull();
    assertThat(result1.getId()).isEqualTo("workflow-inst-1");
    assertThat(result1.getStatus()).isEqualTo("PENDING");
    assertThat(result1.getVersion()).isEqualTo(20L);

    assertThat(result2).isNotNull();
    assertThat(result2.getId()).isEqualTo("workflow-inst-2");
    assertThat(result2.getStatus()).isEqualTo("COMPLETED");
    assertThat(result2.getVersion()).isEqualTo(21L);

    // And: results should be different objects
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void apply_withNullProcessDefinitionId_shouldReturnNull() {
    // Given: a HistoricProcessInstanceEntity with null process definition ID
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-null-def");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-null-def");
    entity.setProcessDefinitionId(null);
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("ACTIVE");

    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-789", "5");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    // Then: result should be null (null process definition ID)
    assertThat(result).isNull();
  }
}
