package com.symphony.bdk.workflow.monitoring.service.converter;

import com.symphony.bdk.workflow.api.v1.dto.NodeStateView;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for ActivityInstViewConverter.
 * Tests the conversion from ActivityInstanceDomain to NodeStateView.
 */
class ActivityInstViewConverterClaudeTest {

  private ActivityInstViewConverter converter;

  @BeforeEach
  void setUp() {
    converter = new ActivityInstViewConverter();
  }

  // ==================== Constructor Tests ====================

  @Test
  void constructor_shouldCreateInstance() {
    // When: creating a new instance
    ActivityInstViewConverter newConverter = new ActivityInstViewConverter();

    // Then: instance should not be null
    assertThat(newConverter).isNotNull();
  }

  // ==================== apply() Tests ====================

  @Test
  void apply_withAllFieldsSet_shouldConvertCorrectly() {
    // Given: a complete ActivityInstanceDomain with all fields set
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("result", "success");
    outputs.put("count", 42);

    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(outputs);

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("activityName")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("userTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: all fields should be mapped correctly
    assertThat(result.getNodeId()).isEqualTo("activityName");
    assertThat(result.getInstanceId()).isEqualTo("proc-inst-456");
    assertThat(result.getWorkflowId()).isEqualTo("workflow-789");
    assertThat(result.getStartDate()).isEqualTo(Instant.parse("2024-01-15T10:30:00Z"));
    assertThat(result.getEndDate()).isEqualTo(Instant.parse("2024-01-15T11:30:00Z"));
    assertThat(result.getDuration()).isEqualTo(Duration.ofHours(1));
    assertThat(result.getOutputs()).isEqualTo(outputs);
    assertThat(result.getOutputs()).containsEntry("result", "success");
    assertThat(result.getOutputs()).containsEntry("count", 42);
  }

  @Test
  void apply_withNullEndDate_shouldSetEndDateToNull() {
    // Given: an ActivityInstanceDomain with null endDate (active activity)
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(Collections.emptyMap());

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("runningActivity")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("serviceTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(null)
        .duration(null)
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: endDate should be null
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getNodeId()).isEqualTo("runningActivity");
    assertThat(result.getStartDate()).isNotNull();
  }

  @Test
  void apply_withNullDuration_shouldSetDurationToNull() {
    // Given: an ActivityInstanceDomain with null duration
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(Collections.emptyMap());

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("activityName")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("serviceTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(null)
        .duration(null)
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: duration should be null
    assertThat(result.getDuration()).isNull();
  }

  @Test
  void apply_withZeroDuration_shouldSetZeroDuration() {
    // Given: an ActivityInstanceDomain with zero duration
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(Collections.emptyMap());

    Instant startInstant = Instant.parse("2024-01-15T10:30:00Z");
    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("instantTask")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("scriptTask")
        .startDate(startInstant)
        .endDate(startInstant)
        .duration(Duration.ZERO)
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: duration should be zero
    assertThat(result.getDuration()).isEqualTo(Duration.ZERO);
  }

  @Test
  void apply_withLargeDuration_shouldHandleCorrectly() {
    // Given: an ActivityInstanceDomain with large duration (5 days)
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(Collections.emptyMap());

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("longRunningTask")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("userTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-20T10:30:00Z"))
        .duration(Duration.ofMillis(432000000L))
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: duration should be handled correctly
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(432000000L));
    assertThat(result.getDuration().toDays()).isEqualTo(5);
  }

  @Test
  void apply_withEmptyOutputs_shouldSetEmptyMap() {
    // Given: an ActivityInstanceDomain with empty outputs
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(Collections.emptyMap());

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("activityName")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("userTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: outputs should be empty map
    assertThat(result.getOutputs()).isEmpty();
    assertThat(result.getOutputs()).isNotNull();
  }

  @Test
  void apply_withMultipleOutputs_shouldPreserveAllOutputs() {
    // Given: an ActivityInstanceDomain with multiple outputs of different types
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("stringValue", "test");
    outputs.put("intValue", 123);
    outputs.put("boolValue", true);
    outputs.put("doubleValue", 45.67);
    outputs.put("nullValue", null);

    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(outputs);

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("activityName")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("serviceTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: all outputs should be preserved with correct types
    assertThat(result.getOutputs()).hasSize(5);
    assertThat(result.getOutputs().get("stringValue")).isEqualTo("test");
    assertThat(result.getOutputs().get("intValue")).isEqualTo(123);
    assertThat(result.getOutputs().get("boolValue")).isEqualTo(true);
    assertThat(result.getOutputs().get("doubleValue")).isEqualTo(45.67);
    assertThat(result.getOutputs()).containsKey("nullValue");
    assertThat(result.getOutputs().get("nullValue")).isNull();
  }

  @Test
  void apply_withSpecialCharactersInNames_shouldHandleCorrectly() {
    // Given: an ActivityInstanceDomain with special characters in IDs and names
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(Collections.emptyMap());

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-with-dashes_and_underscores")
        .name("Activity with Special !@#$% Characters")
        .procInstId("proc:inst:456")
        .workflowId("workflow.with.dots")
        .type("userTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: special characters should be preserved
    assertThat(result.getNodeId()).isEqualTo("Activity with Special !@#$% Characters");
    assertThat(result.getInstanceId()).isEqualTo("proc:inst:456");
    assertThat(result.getWorkflowId()).isEqualTo("workflow.with.dots");
  }

  @Test
  void apply_withEmptyStrings_shouldHandleCorrectly() {
    // Given: an ActivityInstanceDomain with empty strings
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(Collections.emptyMap());

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("")
        .name("")
        .procInstId("")
        .workflowId("")
        .type("")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: empty strings should be preserved
    assertThat(result.getNodeId()).isEmpty();
    assertThat(result.getInstanceId()).isEmpty();
    assertThat(result.getWorkflowId()).isEmpty();
  }

  @Test
  void apply_withWhitespaceStrings_shouldPreserveWhitespace() {
    // Given: an ActivityInstanceDomain with whitespace strings
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(Collections.emptyMap());

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("   ")
        .name("  Activity Name  ")
        .procInstId(" proc-inst ")
        .workflowId(" workflow ")
        .type(" userTask ")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: whitespace should be preserved (no trimming)
    assertThat(result.getNodeId()).isEqualTo("  Activity Name  ");
    assertThat(result.getInstanceId()).isEqualTo(" proc-inst ");
    assertThat(result.getWorkflowId()).isEqualTo(" workflow ");
  }

  @Test
  void apply_withMillisecondPrecisionDuration_shouldHandleCorrectly() {
    // Given: an ActivityInstanceDomain with millisecond precision
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(Collections.emptyMap());

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("quickTask")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("serviceTask")
        .startDate(Instant.parse("2024-01-15T10:30:00.000Z"))
        .endDate(Instant.parse("2024-01-15T10:30:00.123Z"))
        .duration(Duration.ofMillis(123L))
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: millisecond precision should be preserved
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(123L));
    assertThat(result.getDuration().toMillis()).isEqualTo(123L);
  }

  @Test
  void apply_withUnicodeCharacters_shouldHandleCorrectly() {
    // Given: an ActivityInstanceDomain with Unicode characters
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(Collections.emptyMap());

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("Tâche utilisateur 用户任务 タスク")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("userTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: Unicode characters should be preserved
    assertThat(result.getNodeId()).isEqualTo("Tâche utilisateur 用户任务 タスク");
  }

  @Test
  void apply_withLongActivityName_shouldHandleCorrectly() {
    // Given: an ActivityInstanceDomain with a very long activity name
    String longName = "This is a very long activity name that might be used in real-world scenarios " +
        "where activity names contain detailed descriptions of what the activity does and might include " +
        "additional context about the business process and its requirements";

    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(Collections.emptyMap());

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name(longName)
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("userTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: long name should be preserved
    assertThat(result.getNodeId()).isEqualTo(longName);
  }

  @Test
  void apply_withComplexOutputMap_shouldHandleNestedObjects() {
    // Given: an ActivityInstanceDomain with complex nested outputs
    Map<String, Object> nestedMap = new HashMap<>();
    nestedMap.put("nested1", "value1");
    nestedMap.put("nested2", 456);

    Map<String, Object> outputs = new HashMap<>();
    outputs.put("simpleValue", "test");
    outputs.put("nestedMap", nestedMap);

    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(outputs);

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("activityName")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("serviceTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: complex outputs should be preserved
    assertThat(result.getOutputs()).hasSize(2);
    assertThat(result.getOutputs().get("simpleValue")).isEqualTo("test");
    assertThat(result.getOutputs().get("nestedMap")).isEqualTo(nestedMap);
  }

  @Test
  void apply_withDefaultVariablesDomain_shouldHandleCorrectly() {
    // Given: an ActivityInstanceDomain built with default VariablesDomain
    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("activityName")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("userTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: should handle default VariablesDomain with empty outputs
    assertThat(result.getOutputs()).isNotNull();
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void apply_withVariousTimestamps_shouldPreserveTimestamps() {
    // Given: an ActivityInstanceDomain with timestamps with nanosecond precision
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(Collections.emptyMap());

    Instant startDate = Instant.parse("2024-01-15T10:30:00.123456789Z");
    Instant endDate = Instant.parse("2024-01-15T11:30:00.987654321Z");

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("activityName")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("serviceTask")
        .startDate(startDate)
        .endDate(endDate)
        .duration(Duration.between(startDate, endDate))
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: timestamps should be preserved with full precision
    assertThat(result.getStartDate()).isEqualTo(startDate);
    assertThat(result.getEndDate()).isEqualTo(endDate);
  }

  @Test
  void apply_shouldNotSetTypeOrGroupFields() {
    // Given: an ActivityInstanceDomain
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(Collections.emptyMap());

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("activityName")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("userTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .variables(variables)
        .build();

    // When: applying the converter
    NodeStateView result = converter.apply(domain);

    // Then: type and group fields in NodeStateView should be null
    // (these are populated elsewhere by the service layer)
    assertThat(result.getType()).isNull();
    assertThat(result.getGroup()).isNull();
  }

  @Test
  void apply_multipleConversions_shouldProduceConsistentResults() {
    // Given: an ActivityInstanceDomain
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("key", "value");

    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(outputs);

    ActivityInstanceDomain domain = ActivityInstanceDomain.builder()
        .id("activity-id-123")
        .name("activityName")
        .procInstId("proc-inst-456")
        .workflowId("workflow-789")
        .type("userTask")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .variables(variables)
        .build();

    // When: applying the converter multiple times
    NodeStateView result1 = converter.apply(domain);
    NodeStateView result2 = converter.apply(domain);

    // Then: results should be consistent (converter should be stateless)
    assertThat(result1.getNodeId()).isEqualTo(result2.getNodeId());
    assertThat(result1.getInstanceId()).isEqualTo(result2.getInstanceId());
    assertThat(result1.getWorkflowId()).isEqualTo(result2.getWorkflowId());
    assertThat(result1.getStartDate()).isEqualTo(result2.getStartDate());
    assertThat(result1.getEndDate()).isEqualTo(result2.getEndDate());
    assertThat(result1.getDuration()).isEqualTo(result2.getDuration());
    assertThat(result1.getOutputs()).isEqualTo(result2.getOutputs());
  }
}
