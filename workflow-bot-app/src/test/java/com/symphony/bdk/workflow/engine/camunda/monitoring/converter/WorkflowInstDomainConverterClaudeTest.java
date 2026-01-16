package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for WorkflowInstDomainConverter.
 * Tests the constructor and apply method with various scenarios.
 */
class WorkflowInstDomainConverterClaudeTest {

  private WorkflowInstDomainConverter converter;

  @BeforeEach
  void setUp() {
    converter = new WorkflowInstDomainConverter();
  }

  // ==================== Constructor Tests ====================

  @Test
  void constructor_shouldCreateInstance() {
    // When: creating a new instance
    WorkflowInstDomainConverter newConverter = new WorkflowInstDomainConverter();

    // Then: instance should not be null
    assertThat(newConverter).isNotNull();
  }

  @Test
  void constructor_multipleInstances_shouldCreateIndependentInstances() {
    // When: creating multiple instances
    WorkflowInstDomainConverter converter1 = new WorkflowInstDomainConverter();
    WorkflowInstDomainConverter converter2 = new WorkflowInstDomainConverter();

    // Then: instances should be independent
    assertThat(converter1).isNotNull();
    assertThat(converter2).isNotNull();
    assertThat(converter1).isNotSameAs(converter2);
  }

  @Test
  void constructor_shouldExtendAbstractInstanceDomainConverter() {
    // When: creating a new instance
    WorkflowInstDomainConverter newConverter = new WorkflowInstDomainConverter();

    // Then: should be instance of AbstractInstanceDomainConverter
    assertThat(newConverter).isInstanceOf(AbstractInstanceDomainConverter.class);
  }

  // ==================== apply() Method Tests ====================

  @Test
  void apply_withCompleteEntity_shouldConvertCorrectly() {
    // Given: a complete HistoricProcessInstanceEntity
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");

    Date startTime = Date.from(Instant.parse("2024-01-15T10:30:00Z"));
    entity.setStartTime(startTime);

    Date endTime = Date.from(Instant.parse("2024-01-15T11:30:00Z"));
    entity.setEndTime(endTime);

    entity.setState("COMPLETED");
    entity.setEndActivityId("endEvent1");
    entity.setDurationInMillis(3600000L); // 1 hour in milliseconds

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: all fields should be converted correctly
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("workflow-inst-123");
    assertThat(result.getName()).isEqualTo("myWorkflow");
    assertThat(result.getInstanceId()).isEqualTo("proc-inst-456");
    assertThat(result.getStartDate()).isEqualTo(Instant.parse("2024-01-15T10:30:00Z"));
    assertThat(result.getEndDate()).isEqualTo(Instant.parse("2024-01-15T11:30:00Z"));
    assertThat(result.getStatus()).isEqualTo("COMPLETED");
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(3600000L));
  }

  @Test
  void apply_withActiveInstance_shouldSetCorrectStatus() {
    // Given: an active HistoricProcessInstanceEntity
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-active");
    entity.setProcessDefinitionKey("activeWorkflow");
    entity.setProcessInstanceId("proc-inst-active");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(null); // null for active instances
    entity.setState("ACTIVE");
    entity.setEndActivityId(null);
    entity.setDurationInMillis(null);

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: status should be PENDING
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("PENDING");
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getDuration()).isNull();
  }

  @Test
  void apply_withPendingStatus_shouldSetStatusToPending() {
    // Given: a HistoricProcessInstanceEntity with PENDING state
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-pending");
    entity.setProcessDefinitionKey("pendingWorkflow");
    entity.setProcessInstanceId("proc-inst-pending");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("PENDING");
    entity.setEndActivityId(null);

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: status should be PENDING
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("PENDING");
  }

  @Test
  void apply_withCompletedStatusAndEndEvent_shouldSetStatusToCompleted() {
    // Given: a completed HistoricProcessInstanceEntity with endEvent
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-completed");
    entity.setProcessDefinitionKey("completedWorkflow");
    entity.setProcessInstanceId("proc-inst-completed");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("endEventSuccess");
    entity.setDurationInMillis(3600000L);

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: status should be COMPLETED
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("COMPLETED");
  }

  @Test
  void apply_withCompletedStatusAndNonEndEvent_shouldSetStatusToFailed() {
    // Given: a completed HistoricProcessInstanceEntity without endEvent
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-failed");
    entity.setProcessDefinitionKey("failedWorkflow");
    entity.setProcessInstanceId("proc-inst-failed");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("errorActivity"); // not an endEvent
    entity.setDurationInMillis(3600000L);

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: status should be FAILED
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("FAILED");
  }

  @Test
  void apply_withCompletedStatusAndNullEndActivity_shouldSetStatusToFailed() {
    // Given: a completed HistoricProcessInstanceEntity with null endActivityId
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-failed-null");
    entity.setProcessDefinitionKey("failedWorkflow");
    entity.setProcessInstanceId("proc-inst-failed-null");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId(null); // null endActivityId
    entity.setDurationInMillis(3600000L);

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: status should be FAILED
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("FAILED");
  }

  @Test
  void apply_withZeroDuration_shouldSetZeroDuration() {
    // Given: a HistoricProcessInstanceEntity with zero duration
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-zero-duration");
    entity.setProcessDefinitionKey("quickWorkflow");
    entity.setProcessInstanceId("proc-inst-zero");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("endEvent");
    entity.setDurationInMillis(0L);

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: duration should be zero
    assertThat(result).isNotNull();
    assertThat(result.getDuration()).isEqualTo(Duration.ZERO);
  }

  @Test
  void apply_withLongDuration_shouldHandleCorrectly() {
    // Given: a HistoricProcessInstanceEntity with long duration (5 days)
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-long");
    entity.setProcessDefinitionKey("longRunningWorkflow");
    entity.setProcessInstanceId("proc-inst-long");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-20T10:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("endEvent");
    entity.setDurationInMillis(432000000L); // 5 days in milliseconds

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: duration should be handled correctly
    assertThat(result).isNotNull();
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(432000000L));
    assertThat(result.getDuration().toDays()).isEqualTo(5);
  }

  @Test
  void apply_withSpecialCharactersInIds_shouldPreserveIds() {
    // Given: a HistoricProcessInstanceEntity with special characters
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-with-dashes_and_underscores");
    entity.setProcessDefinitionKey("workflow.with.dots");
    entity.setProcessInstanceId("proc:inst:456");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("ACTIVE");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: special characters should be preserved
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("workflow-inst-with-dashes_and_underscores");
    assertThat(result.getName()).isEqualTo("workflow.with.dots");
    assertThat(result.getInstanceId()).isEqualTo("proc:inst:456");
  }

  @Test
  void apply_withCaseInsensitiveActiveState_shouldSetStatusToPending() {
    // Given: a HistoricProcessInstanceEntity with lowercase "active" state
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-active-lower");
    entity.setProcessDefinitionKey("workflow");
    entity.setProcessInstanceId("proc-inst");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("active"); // lowercase

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: status should be PENDING (case-insensitive comparison)
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("PENDING");
  }

  @Test
  void apply_withCaseInsensitiveCompletedState_shouldResolveCorrectly() {
    // Given: a HistoricProcessInstanceEntity with lowercase "completed" state
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-completed-lower");
    entity.setProcessDefinitionKey("workflow");
    entity.setProcessInstanceId("proc-inst");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setState("completed"); // lowercase
    entity.setEndActivityId("endEventSuccess");
    entity.setDurationInMillis(3600000L);

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: status should be COMPLETED (case-insensitive comparison)
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("COMPLETED");
  }

  @Test
  void apply_withUnknownState_shouldSetStatusToNull() {
    // Given: a HistoricProcessInstanceEntity with unknown state
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-unknown");
    entity.setProcessDefinitionKey("workflow");
    entity.setProcessInstanceId("proc-inst");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("UNKNOWN_STATE");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: status should be null (not handled by the logic)
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isNull();
  }

  @Test
  void apply_withMultipleDifferentEntities_shouldConvertEachIndependently() {
    // Given: multiple different entities
    HistoricProcessInstanceEntity entity1 = new HistoricProcessInstanceEntity();
    entity1.setId("workflow-inst-1");
    entity1.setProcessDefinitionKey("workflow1");
    entity1.setProcessInstanceId("proc-inst-1");
    entity1.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity1.setState("ACTIVE");

    HistoricProcessInstanceEntity entity2 = new HistoricProcessInstanceEntity();
    entity2.setId("workflow-inst-2");
    entity2.setProcessDefinitionKey("workflow2");
    entity2.setProcessInstanceId("proc-inst-2");
    entity2.setStartTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity2.setEndTime(Date.from(Instant.parse("2024-01-15T12:30:00Z")));
    entity2.setState("COMPLETED");
    entity2.setEndActivityId("endEvent");
    entity2.setDurationInMillis(3600000L);

    // When: calling apply on both entities
    WorkflowInstanceDomain result1 = converter.apply(entity1);
    WorkflowInstanceDomain result2 = converter.apply(entity2);

    // Then: each should be converted independently
    assertThat(result1).isNotNull();
    assertThat(result1.getId()).isEqualTo("workflow-inst-1");
    assertThat(result1.getStatus()).isEqualTo("PENDING");

    assertThat(result2).isNotNull();
    assertThat(result2.getId()).isEqualTo("workflow-inst-2");
    assertThat(result2.getStatus()).isEqualTo("COMPLETED");

    // And: results should be different objects
    assertThat(result1).isNotSameAs(result2);
  }

  @Test
  void apply_shouldNotSetVersionField() {
    // Given: a HistoricProcessInstanceEntity
    // Note: The version field is not set by WorkflowInstDomainConverter
    // It may be set by other converters (like WorkflowDomainConverter)
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("ACTIVE");

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: version field should be null (not set by this converter)
    assertThat(result).isNotNull();
    assertThat(result.getVersion()).isNull();
  }

  @Test
  void apply_withEndActivityStartingWithEnd_butNotEndEvent_shouldSetStatusToFailed() {
    // Given: a completed entity with endActivity starting with "end" but not "endEvent"
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-end");
    entity.setProcessDefinitionKey("workflow");
    entity.setProcessInstanceId("proc-inst");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("endActivity"); // starts with "end" but not "endEvent"
    entity.setDurationInMillis(3600000L);

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: status should be FAILED (must start with "endEvent" exactly)
    assertThat(result).isNotNull();
    assertThat(result.getStatus()).isEqualTo("FAILED");
  }

  @Test
  void apply_withVeryShortDuration_shouldHandleCorrectly() {
    // Given: a HistoricProcessInstanceEntity with very short duration (1 millisecond)
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("workflow-inst-short");
    entity.setProcessDefinitionKey("quickWorkflow");
    entity.setProcessInstanceId("proc-inst-short");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00.000Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T10:30:00.001Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("endEvent");
    entity.setDurationInMillis(1L);

    // When: calling apply
    WorkflowInstanceDomain result = converter.apply(entity);

    // Then: duration should be handled correctly
    assertThat(result).isNotNull();
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(1L));
  }
}
