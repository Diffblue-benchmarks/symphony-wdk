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
 * Test class for AbstractInstanceDomainConverter.
 * Tests the protected methods through a concrete test implementation.
 */
class AbstractInstanceDomainConverterClaudeTest {

  private TestableAbstractInstanceDomainConverter converter;

  @BeforeEach
  void setUp() {
    converter = new TestableAbstractInstanceDomainConverter();
  }

  // ==================== resolveStatus Tests ====================

  @Test
  void resolveStatus_withActiveState_shouldReturnPending() {
    // When: state is "ACTIVE"
    String result = converter.resolveStatus("ACTIVE", null);

    // Then: should return PENDING
    assertThat(result).isEqualTo("PENDING");
  }

  @Test
  void resolveStatus_withActiveStateLowerCase_shouldReturnPending() {
    // When: state is "active" (testing case insensitivity)
    String result = converter.resolveStatus("active", null);

    // Then: should return PENDING
    assertThat(result).isEqualTo("PENDING");
  }

  @Test
  void resolveStatus_withActiveMixedCase_shouldReturnPending() {
    // When: state is "AcTiVe" (testing case insensitivity)
    String result = converter.resolveStatus("AcTiVe", null);

    // Then: should return PENDING
    assertThat(result).isEqualTo("PENDING");
  }

  @Test
  void resolveStatus_withPendingState_shouldReturnPending() {
    // When: state is "PENDING"
    String result = converter.resolveStatus("PENDING", null);

    // Then: should return PENDING
    assertThat(result).isEqualTo("PENDING");
  }

  @Test
  void resolveStatus_withPendingStateLowerCase_shouldReturnPending() {
    // When: state is "pending" (testing case insensitivity)
    String result = converter.resolveStatus("pending", null);

    // Then: should return PENDING
    assertThat(result).isEqualTo("PENDING");
  }

  @Test
  void resolveStatus_withPendingMixedCase_shouldReturnPending() {
    // When: state is "PeNdInG" (testing case insensitivity)
    String result = converter.resolveStatus("PeNdInG", null);

    // Then: should return PENDING
    assertThat(result).isEqualTo("PENDING");
  }

  @Test
  void resolveStatus_withCompletedStateAndEndEventActivity_shouldReturnCompleted() {
    // When: state is "COMPLETED" and endActivityId starts with "endEvent"
    String result = converter.resolveStatus("COMPLETED", "endEvent1");

    // Then: should return COMPLETED
    assertThat(result).isEqualTo("COMPLETED");
  }

  @Test
  void resolveStatus_withCompletedStateAndEndEventWithSuffix_shouldReturnCompleted() {
    // When: state is "COMPLETED" and endActivityId is "endEvent" with suffix
    String result = converter.resolveStatus("COMPLETED", "endEventSuccess");

    // Then: should return COMPLETED
    assertThat(result).isEqualTo("COMPLETED");
  }

  @Test
  void resolveStatus_withCompletedStateLowerCaseAndEndEvent_shouldReturnCompleted() {
    // When: state is "completed" (testing case insensitivity) and endActivityId starts with "endEvent"
    String result = converter.resolveStatus("completed", "endEvent");

    // Then: should return COMPLETED
    assertThat(result).isEqualTo("COMPLETED");
  }

  @Test
  void resolveStatus_withCompletedMixedCaseAndEndEvent_shouldReturnCompleted() {
    // When: state is "CoMpLeTeD" (testing case insensitivity)
    String result = converter.resolveStatus("CoMpLeTeD", "endEvent");

    // Then: should return COMPLETED
    assertThat(result).isEqualTo("COMPLETED");
  }

  @Test
  void resolveStatus_withCompletedStateAndNonEndEventActivity_shouldReturnFailed() {
    // When: state is "COMPLETED" but endActivityId doesn't start with "endEvent"
    String result = converter.resolveStatus("COMPLETED", "someOtherActivity");

    // Then: should return FAILED
    assertThat(result).isEqualTo("FAILED");
  }

  @Test
  void resolveStatus_withCompletedStateAndActivityStartingWithEnd_shouldReturnFailed() {
    // When: state is "COMPLETED" and endActivityId starts with "end" but not "endEvent"
    String result = converter.resolveStatus("COMPLETED", "endActivity");

    // Then: should return FAILED (must start with "endEvent" exactly)
    assertThat(result).isEqualTo("FAILED");
  }

  @Test
  void resolveStatus_withCompletedStateAndNullEndActivity_shouldReturnFailed() {
    // When: state is "COMPLETED" but endActivityId is null
    String result = converter.resolveStatus("COMPLETED", null);

    // Then: should return FAILED
    assertThat(result).isEqualTo("FAILED");
  }

  @Test
  void resolveStatus_withCompletedStateAndEmptyEndActivity_shouldReturnFailed() {
    // When: state is "COMPLETED" but endActivityId is empty
    String result = converter.resolveStatus("COMPLETED", "");

    // Then: should return FAILED
    assertThat(result).isEqualTo("FAILED");
  }

  @Test
  void resolveStatus_withCompletedStateAndBlankEndActivity_shouldReturnFailed() {
    // When: state is "COMPLETED" but endActivityId is blank (whitespace)
    String result = converter.resolveStatus("COMPLETED", "   ");

    // Then: should return FAILED
    assertThat(result).isEqualTo("FAILED");
  }

  @Test
  void resolveStatus_withFailedState_shouldReturnNull() {
    // When: state is "FAILED"
    String result = converter.resolveStatus("FAILED", null);

    // Then: should return null (not handled by the logic)
    assertThat(result).isNull();
  }

  @Test
  void resolveStatus_withUnknownState_shouldReturnNull() {
    // When: state is an unknown value
    String result = converter.resolveStatus("UNKNOWN_STATE", null);

    // Then: should return null
    assertThat(result).isNull();
  }

  @Test
  void resolveStatus_withNullState_shouldReturnNull() {
    // When: state is null
    String result = converter.resolveStatus(null, null);

    // Then: should return null (NullPointerException would occur if equalsIgnoreCase called on null)
    assertThat(result).isNull();
  }

  @Test
  void resolveStatus_withEmptyState_shouldReturnNull() {
    // When: state is empty string
    String result = converter.resolveStatus("", null);

    // Then: should return null
    assertThat(result).isNull();
  }

  // ==================== instanceCommonBuilder Tests ====================

  @Test
  void instanceCommonBuilder_withAllFieldsSet_shouldBuildCorrectly() {
    // Given: a complete HistoricProcessInstanceEntity
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("instance-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");

    Date startTime = Date.from(Instant.parse("2024-01-15T10:30:00Z"));
    entity.setStartTime(startTime);

    Date endTime = Date.from(Instant.parse("2024-01-15T11:30:00Z"));
    entity.setEndTime(endTime);

    entity.setState("COMPLETED");
    entity.setEndActivityId("endEvent1");
    entity.setDurationInMillis(3600000L); // 1 hour in milliseconds

    // When: calling instanceCommonBuilder
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder builder = converter.instanceCommonBuilder(entity);
    WorkflowInstanceDomain result = builder.build();

    // Then: all fields should be set correctly
    assertThat(result.getId()).isEqualTo("instance-123");
    assertThat(result.getName()).isEqualTo("myWorkflow");
    assertThat(result.getInstanceId()).isEqualTo("proc-inst-456");
    assertThat(result.getStartDate()).isEqualTo(Instant.parse("2024-01-15T10:30:00Z"));
    assertThat(result.getEndDate()).isEqualTo(Instant.parse("2024-01-15T11:30:00Z"));
    assertThat(result.getStatus()).isEqualTo("COMPLETED");
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(3600000L));
  }

  @Test
  void instanceCommonBuilder_withNullEndTime_shouldSetEndDateToNull() {
    // Given: a HistoricProcessInstanceEntity with null endTime
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("instance-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(null); // null end time for active instance
    entity.setState("ACTIVE");
    entity.setEndActivityId(null);
    entity.setDurationInMillis(null);

    // When: calling instanceCommonBuilder
    WorkflowInstanceDomain result = converter.instanceCommonBuilder(entity).build();

    // Then: endDate should be null
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getStatus()).isEqualTo("PENDING");
  }

  @Test
  void instanceCommonBuilder_withNullDuration_shouldSetDurationToNull() {
    // Given: a HistoricProcessInstanceEntity with null duration
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("instance-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(null);
    entity.setState("ACTIVE");
    entity.setEndActivityId(null);
    entity.setDurationInMillis(null); // null duration for active instance

    // When: calling instanceCommonBuilder
    WorkflowInstanceDomain result = converter.instanceCommonBuilder(entity).build();

    // Then: duration should be null
    assertThat(result.getDuration()).isNull();
  }

  @Test
  void instanceCommonBuilder_withPendingStatus_shouldResolveToPending() {
    // Given: a HistoricProcessInstanceEntity with PENDING state
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("instance-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("PENDING");
    entity.setEndActivityId(null);

    // When: calling instanceCommonBuilder
    WorkflowInstanceDomain result = converter.instanceCommonBuilder(entity).build();

    // Then: status should be PENDING
    assertThat(result.getStatus()).isEqualTo("PENDING");
  }

  @Test
  void instanceCommonBuilder_withCompletedStatusAndEndEvent_shouldResolveToCompleted() {
    // Given: a HistoricProcessInstanceEntity with COMPLETED state and endEvent
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("instance-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("endEventSuccess");
    entity.setDurationInMillis(3600000L);

    // When: calling instanceCommonBuilder
    WorkflowInstanceDomain result = converter.instanceCommonBuilder(entity).build();

    // Then: status should be COMPLETED
    assertThat(result.getStatus()).isEqualTo("COMPLETED");
  }

  @Test
  void instanceCommonBuilder_withCompletedStatusAndNonEndEvent_shouldResolveToFailed() {
    // Given: a HistoricProcessInstanceEntity with COMPLETED state but non-endEvent activity
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("instance-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("errorActivity");
    entity.setDurationInMillis(3600000L);

    // When: calling instanceCommonBuilder
    WorkflowInstanceDomain result = converter.instanceCommonBuilder(entity).build();

    // Then: status should be FAILED
    assertThat(result.getStatus()).isEqualTo("FAILED");
  }

  @Test
  void instanceCommonBuilder_withZeroDuration_shouldSetZeroDuration() {
    // Given: a HistoricProcessInstanceEntity with zero duration
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("instance-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("endEvent");
    entity.setDurationInMillis(0L); // zero duration

    // When: calling instanceCommonBuilder
    WorkflowInstanceDomain result = converter.instanceCommonBuilder(entity).build();

    // Then: duration should be zero
    assertThat(result.getDuration()).isEqualTo(Duration.ZERO);
  }

  @Test
  void instanceCommonBuilder_withLargeDuration_shouldHandleCorrectly() {
    // Given: a HistoricProcessInstanceEntity with large duration
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("instance-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-20T10:30:00Z")));
    entity.setState("COMPLETED");
    entity.setEndActivityId("endEvent");
    entity.setDurationInMillis(432000000L); // 5 days in milliseconds

    // When: calling instanceCommonBuilder
    WorkflowInstanceDomain result = converter.instanceCommonBuilder(entity).build();

    // Then: duration should be handled correctly
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(432000000L));
    assertThat(result.getDuration().toDays()).isEqualTo(5);
  }

  @Test
  void instanceCommonBuilder_shouldReturnBuilder_notFinalObject() {
    // Given: a HistoricProcessInstanceEntity
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("instance-123");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("ACTIVE");

    // When: calling instanceCommonBuilder
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder builder = converter.instanceCommonBuilder(entity);

    // Then: should return a builder, not the final object
    assertThat(builder).isNotNull();

    // And: the builder can be used to add more fields
    WorkflowInstanceDomain result = builder.version(1L).build();
    assertThat(result.getVersion()).isEqualTo(1L);
  }

  @Test
  void instanceCommonBuilder_withSpecialCharactersInIds_shouldHandleCorrectly() {
    // Given: a HistoricProcessInstanceEntity with special characters in IDs
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("instance-with-dashes-and_underscores");
    entity.setProcessDefinitionKey("workflow.with.dots");
    entity.setProcessInstanceId("proc:inst:456");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setState("ACTIVE");

    // When: calling instanceCommonBuilder
    WorkflowInstanceDomain result = converter.instanceCommonBuilder(entity).build();

    // Then: special characters should be preserved
    assertThat(result.getId()).isEqualTo("instance-with-dashes-and_underscores");
    assertThat(result.getName()).isEqualTo("workflow.with.dots");
    assertThat(result.getInstanceId()).isEqualTo("proc:inst:456");
  }

  /**
   * Concrete implementation of AbstractInstanceDomainConverter for testing purposes.
   * This allows us to test the protected methods of the abstract class.
   */
  private static class TestableAbstractInstanceDomainConverter extends AbstractInstanceDomainConverter {
    // Expose protected methods for testing by making them public in this test subclass

    @Override
    public WorkflowInstanceDomain.WorkflowInstanceDomainBuilder instanceCommonBuilder(
        HistoricProcessInstanceEntity hisProcInstance) {
      return super.instanceCommonBuilder(hisProcInstance);
    }

    @Override
    public String resolveStatus(String hisProcInstanceState, String hisProcInstanceEndActivityId) {
      return super.resolveStatus(hisProcInstanceState, hisProcInstanceEndActivityId);
    }
  }
}
