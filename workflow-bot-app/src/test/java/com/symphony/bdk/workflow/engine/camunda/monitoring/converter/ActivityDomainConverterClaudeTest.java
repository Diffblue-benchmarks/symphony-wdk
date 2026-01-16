package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;

import org.camunda.bpm.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for ActivityDomainConverter.
 * Tests the conversion from HistoricActivityInstanceEntity to ActivityInstanceDomain.
 */
class ActivityDomainConverterClaudeTest {

  private ActivityDomainConverter converter;

  @BeforeEach
  void setUp() {
    converter = new ActivityDomainConverter();
  }

  // ==================== Constructor Tests ====================

  @Test
  void constructor_shouldCreateInstance() {
    // When: creating a new instance
    ActivityDomainConverter newConverter = new ActivityDomainConverter();

    // Then: instance should not be null
    assertThat(newConverter).isNotNull();
  }

  // ==================== apply() Tests ====================

  @Test
  void apply_withAllFieldsSet_shouldConvertCorrectly() {
    // Given: a complete HistoricActivityInstanceEntity
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-123");
    entity.setActivityName("User Task");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setActivityType("userTask");

    Date startTime = Date.from(Instant.parse("2024-01-15T10:30:00Z"));
    entity.setStartTime(startTime);

    Date endTime = Date.from(Instant.parse("2024-01-15T11:30:00Z"));
    entity.setEndTime(endTime);

    entity.setDurationInMillis(3600000L); // 1 hour in milliseconds

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: all fields should be mapped correctly
    assertThat(result.getId()).isEqualTo("activity-123");
    assertThat(result.getName()).isEqualTo("User Task");
    assertThat(result.getProcInstId()).isEqualTo("proc-inst-456");
    assertThat(result.getWorkflowId()).isEqualTo("myWorkflow");
    assertThat(result.getType()).isEqualTo("userTask");
    assertThat(result.getStartDate()).isEqualTo(Instant.parse("2024-01-15T10:30:00Z"));
    assertThat(result.getEndDate()).isEqualTo(Instant.parse("2024-01-15T11:30:00Z"));
    assertThat(result.getDuration()).isEqualTo(Duration.ofHours(1));
  }

  @Test
  void apply_withNullEndTime_shouldSetEndDateToNull() {
    // Given: a HistoricActivityInstanceEntity with null endTime
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-123");
    entity.setActivityName("Running Task");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setActivityType("serviceTask");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(null); // null end time for active activity
    entity.setDurationInMillis(null);

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: endDate should be null
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getId()).isEqualTo("activity-123");
    assertThat(result.getStartDate()).isNotNull();
  }

  @Test
  void apply_withNullDuration_shouldSetDurationToNull() {
    // Given: a HistoricActivityInstanceEntity with null duration
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-123");
    entity.setActivityName("Running Task");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setActivityType("serviceTask");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(null);
    entity.setDurationInMillis(null); // null duration for active activity

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: duration should be null
    assertThat(result.getDuration()).isNull();
  }

  @Test
  void apply_withZeroDuration_shouldSetZeroDuration() {
    // Given: a HistoricActivityInstanceEntity with zero duration
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-123");
    entity.setActivityName("Instant Task");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setActivityType("scriptTask");
    Instant startInstant = Instant.parse("2024-01-15T10:30:00Z");
    entity.setStartTime(Date.from(startInstant));
    entity.setEndTime(Date.from(startInstant)); // same as start time
    entity.setDurationInMillis(0L); // zero duration

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: duration should be zero
    assertThat(result.getDuration()).isEqualTo(Duration.ZERO);
  }

  @Test
  void apply_withLargeDuration_shouldHandleCorrectly() {
    // Given: a HistoricActivityInstanceEntity with large duration (5 days)
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-123");
    entity.setActivityName("Long Running Task");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setActivityType("userTask");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-20T10:30:00Z")));
    entity.setDurationInMillis(432000000L); // 5 days in milliseconds

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: duration should be handled correctly
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(432000000L));
    assertThat(result.getDuration().toDays()).isEqualTo(5);
  }

  @Test
  void apply_withVariousActivityTypes_shouldPreserveType() {
    // Test different activity types

    // Given: a userTask
    HistoricActivityInstanceEntity userTask = createBasicEntity("userTask");
    // When: applying converter
    ActivityInstanceDomain result1 = converter.apply(userTask);
    // Then: type should be preserved
    assertThat(result1.getType()).isEqualTo("userTask");

    // Given: a serviceTask
    HistoricActivityInstanceEntity serviceTask = createBasicEntity("serviceTask");
    // When: applying converter
    ActivityInstanceDomain result2 = converter.apply(serviceTask);
    // Then: type should be preserved
    assertThat(result2.getType()).isEqualTo("serviceTask");

    // Given: a scriptTask
    HistoricActivityInstanceEntity scriptTask = createBasicEntity("scriptTask");
    // When: applying converter
    ActivityInstanceDomain result3 = converter.apply(scriptTask);
    // Then: type should be preserved
    assertThat(result3.getType()).isEqualTo("scriptTask");

    // Given: a startEvent
    HistoricActivityInstanceEntity startEvent = createBasicEntity("startEvent");
    // When: applying converter
    ActivityInstanceDomain result4 = converter.apply(startEvent);
    // Then: type should be preserved
    assertThat(result4.getType()).isEqualTo("startEvent");

    // Given: an endEvent
    HistoricActivityInstanceEntity endEvent = createBasicEntity("endEvent");
    // When: applying converter
    ActivityInstanceDomain result5 = converter.apply(endEvent);
    // Then: type should be preserved
    assertThat(result5.getType()).isEqualTo("endEvent");
  }

  @Test
  void apply_withSpecialCharactersInIds_shouldHandleCorrectly() {
    // Given: a HistoricActivityInstanceEntity with special characters in IDs
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-with-dashes_and_underscores");
    entity.setActivityName("Task with Special !@#$% Characters");
    entity.setProcessInstanceId("proc:inst:456");
    entity.setProcessDefinitionKey("workflow.with.dots");
    entity.setActivityType("userTask");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setDurationInMillis(3600000L);

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: special characters should be preserved
    assertThat(result.getId()).isEqualTo("activity-with-dashes_and_underscores");
    assertThat(result.getName()).isEqualTo("Task with Special !@#$% Characters");
    assertThat(result.getProcInstId()).isEqualTo("proc:inst:456");
    assertThat(result.getWorkflowId()).isEqualTo("workflow.with.dots");
  }

  @Test
  void apply_withEmptyStrings_shouldHandleCorrectly() {
    // Given: a HistoricActivityInstanceEntity with empty strings
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("");
    entity.setActivityName("");
    entity.setProcessInstanceId("");
    entity.setProcessDefinitionKey("");
    entity.setActivityType("");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setDurationInMillis(3600000L);

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: empty strings should be preserved
    assertThat(result.getId()).isEmpty();
    assertThat(result.getName()).isEmpty();
    assertThat(result.getProcInstId()).isEmpty();
    assertThat(result.getWorkflowId()).isEmpty();
    assertThat(result.getType()).isEmpty();
  }

  @Test
  void apply_withWhitespaceStrings_shouldPreserveWhitespace() {
    // Given: a HistoricActivityInstanceEntity with whitespace strings
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("   ");
    entity.setActivityName("  Task Name  ");
    entity.setProcessInstanceId(" proc-inst ");
    entity.setProcessDefinitionKey(" workflow ");
    entity.setActivityType(" userTask ");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setDurationInMillis(3600000L);

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: whitespace should be preserved (no trimming)
    assertThat(result.getId()).isEqualTo("   ");
    assertThat(result.getName()).isEqualTo("  Task Name  ");
    assertThat(result.getProcInstId()).isEqualTo(" proc-inst ");
    assertThat(result.getWorkflowId()).isEqualTo(" workflow ");
    assertThat(result.getType()).isEqualTo(" userTask ");
  }

  @Test
  void apply_withMillisecondPrecisionDuration_shouldHandleCorrectly() {
    // Given: a HistoricActivityInstanceEntity with millisecond precision
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-123");
    entity.setActivityName("Quick Task");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setActivityType("serviceTask");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00.000Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T10:30:00.123Z")));
    entity.setDurationInMillis(123L); // 123 milliseconds

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: millisecond precision should be preserved
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(123L));
    assertThat(result.getDuration().toMillis()).isEqualTo(123L);
  }

  @Test
  void apply_withOneMillisecondDuration_shouldHandleCorrectly() {
    // Given: a HistoricActivityInstanceEntity with 1ms duration
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-123");
    entity.setActivityName("Very Quick Task");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setActivityType("scriptTask");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00.000Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T10:30:00.001Z")));
    entity.setDurationInMillis(1L); // 1 millisecond

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: should handle 1ms duration
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(1L));
  }

  @Test
  void apply_withBothNullEndTimeAndNullDuration_shouldSetBothToNull() {
    // Given: a HistoricActivityInstanceEntity with both null endTime and null duration
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-123");
    entity.setActivityName("Active Task");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setActivityType("userTask");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(null);
    entity.setDurationInMillis(null);

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: both endDate and duration should be null
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getDuration()).isNull();
    // But other fields should be set
    assertThat(result.getId()).isEqualTo("activity-123");
    assertThat(result.getStartDate()).isNotNull();
  }

  @Test
  void apply_withNullEndTimeButNonNullDuration_shouldHandleMismatch() {
    // Given: a HistoricActivityInstanceEntity with null endTime but non-null duration
    // This is an edge case that might occur in certain scenarios
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-123");
    entity.setActivityName("Task");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setActivityType("serviceTask");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(null); // null end time
    entity.setDurationInMillis(5000L); // but duration is set

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: should handle the mismatch
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(5000L));
  }

  @Test
  void apply_withLongActivityName_shouldHandleCorrectly() {
    // Given: a HistoricActivityInstanceEntity with a very long activity name
    String longName = "This is a very long activity name that might be used in real-world scenarios " +
        "where activity names contain detailed descriptions of what the activity does and might include " +
        "additional context about the business process and its requirements";

    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-123");
    entity.setActivityName(longName);
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setActivityType("userTask");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setDurationInMillis(3600000L);

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: long name should be preserved
    assertThat(result.getName()).isEqualTo(longName);
  }

  @Test
  void apply_withUnicodeCharacters_shouldHandleCorrectly() {
    // Given: a HistoricActivityInstanceEntity with Unicode characters
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-123");
    entity.setActivityName("Tâche utilisateur 用户任务 タスク");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setActivityType("userTask");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setDurationInMillis(3600000L);

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: Unicode characters should be preserved
    assertThat(result.getName()).isEqualTo("Tâche utilisateur 用户任务 タスク");
  }

  @Test
  void apply_withVariablesDomain_shouldHaveDefaultVariables() {
    // Given: a HistoricActivityInstanceEntity
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-123");
    entity.setActivityName("Task");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setActivityType("userTask");
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setDurationInMillis(3600000L);

    // When: applying the converter
    ActivityInstanceDomain result = converter.apply(entity);

    // Then: should have default VariablesDomain (from @Builder.Default)
    assertThat(result.getVariables()).isNotNull();
  }

  /**
   * Helper method to create a basic HistoricActivityInstanceEntity with the given activity type.
   */
  private HistoricActivityInstanceEntity createBasicEntity(String activityType) {
    HistoricActivityInstanceEntity entity = new HistoricActivityInstanceEntity();
    entity.setActivityId("activity-123");
    entity.setActivityName("Test Task");
    entity.setProcessInstanceId("proc-inst-456");
    entity.setProcessDefinitionKey("myWorkflow");
    entity.setActivityType(activityType);
    entity.setStartTime(Date.from(Instant.parse("2024-01-15T10:30:00Z")));
    entity.setEndTime(Date.from(Instant.parse("2024-01-15T11:30:00Z")));
    entity.setDurationInMillis(3600000L);
    return entity;
  }
}
