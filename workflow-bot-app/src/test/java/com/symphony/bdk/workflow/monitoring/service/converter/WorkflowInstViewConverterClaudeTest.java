package com.symphony.bdk.workflow.monitoring.service.converter;

import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test class for WorkflowInstViewConverter.
 * Tests the conversion from WorkflowInstanceDomain to WorkflowInstView.
 */
class WorkflowInstViewConverterClaudeTest {

  private WorkflowInstViewConverter converter;

  @BeforeEach
  void setUp() {
    converter = new WorkflowInstViewConverter();
  }

  // ==================== Constructor Tests ====================

  @Test
  void constructor_shouldCreateInstance() {
    // When: creating a new instance
    WorkflowInstViewConverter newConverter = new WorkflowInstViewConverter();

    // Then: instance should not be null
    assertThat(newConverter).isNotNull();
  }

  // ==================== apply() Tests ====================

  @Test
  void apply_withAllFieldsSet_shouldConvertCorrectly() {
    // Given: a complete WorkflowInstanceDomain with all fields set
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(1L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: all fields should be mapped correctly
    assertThat(result.getId()).isEqualTo("workflowName");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getInstanceId()).isEqualTo("instance-456");
    assertThat(result.getStatus()).isEqualTo(StatusEnum.COMPLETED);
    assertThat(result.getStartDate()).isEqualTo(Instant.parse("2024-01-15T10:30:00Z"));
    assertThat(result.getEndDate()).isEqualTo(Instant.parse("2024-01-15T11:30:00Z"));
    assertThat(result.getDuration()).isEqualTo(Duration.ofHours(1));
  }

  @Test
  void apply_withStatusPending_shouldConvertCorrectly() {
    // Given: a WorkflowInstanceDomain with PENDING status
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(2L)
        .instanceId("instance-456")
        .status("PENDING")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(null)
        .duration(null)
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: status should be PENDING
    assertThat(result.getStatus()).isEqualTo(StatusEnum.PENDING);
    assertThat(result.getId()).isEqualTo("workflowName");
    assertThat(result.getVersion()).isEqualTo(2L);
  }

  @Test
  void apply_withStatusActive_shouldMapToPending() {
    // Given: a WorkflowInstanceDomain with ACTIVE status
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(3L)
        .instanceId("instance-456")
        .status("ACTIVE")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(null)
        .duration(null)
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: ACTIVE status should be mapped to PENDING
    assertThat(result.getStatus()).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void apply_withStatusFailed_shouldConvertCorrectly() {
    // Given: a WorkflowInstanceDomain with FAILED status
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(4L)
        .instanceId("instance-456")
        .status("FAILED")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T10:35:00Z"))
        .duration(Duration.ofMinutes(5))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: status should be FAILED
    assertThat(result.getStatus()).isEqualTo(StatusEnum.FAILED);
  }

  @Test
  void apply_withNullStatus_shouldSetStatusToNull() {
    // Given: a WorkflowInstanceDomain with null status
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(1L)
        .instanceId("instance-456")
        .status(null)
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(null)
        .duration(null)
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: status should be null
    assertThat(result.getStatus()).isNull();
  }

  @Test
  void apply_withInvalidStatus_shouldThrowException() {
    // Given: a WorkflowInstanceDomain with invalid status
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(1L)
        .instanceId("instance-456")
        .status("INVALID_STATUS")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(null)
        .duration(null)
        .build();

    // When/Then: applying the converter should throw IllegalArgumentException
    assertThatThrownBy(() -> converter.apply(domain))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Workflow instance status INVALID_STATUS is not known");
  }

  @Test
  void apply_withNullEndDate_shouldSetEndDateToNull() {
    // Given: a WorkflowInstanceDomain with null endDate (running workflow)
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("runningWorkflow")
        .version(1L)
        .instanceId("instance-456")
        .status("ACTIVE")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(null)
        .duration(null)
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: endDate should be null
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getId()).isEqualTo("runningWorkflow");
    assertThat(result.getStartDate()).isNotNull();
  }

  @Test
  void apply_withNullDuration_shouldSetDurationToNull() {
    // Given: a WorkflowInstanceDomain with null duration
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(1L)
        .instanceId("instance-456")
        .status("ACTIVE")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(null)
        .duration(null)
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: duration should be null
    assertThat(result.getDuration()).isNull();
  }

  @Test
  void apply_withZeroDuration_shouldSetZeroDuration() {
    // Given: a WorkflowInstanceDomain with zero duration
    Instant startInstant = Instant.parse("2024-01-15T10:30:00Z");
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("instantWorkflow")
        .version(1L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(startInstant)
        .endDate(startInstant)
        .duration(Duration.ZERO)
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: duration should be zero
    assertThat(result.getDuration()).isEqualTo(Duration.ZERO);
  }

  @Test
  void apply_withLargeDuration_shouldHandleCorrectly() {
    // Given: a WorkflowInstanceDomain with large duration (10 days)
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("longRunningWorkflow")
        .version(1L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-25T10:30:00Z"))
        .duration(Duration.ofDays(10))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: duration should be handled correctly
    assertThat(result.getDuration()).isEqualTo(Duration.ofDays(10));
    assertThat(result.getDuration().toDays()).isEqualTo(10);
  }

  @Test
  void apply_withVersionZero_shouldHandleCorrectly() {
    // Given: a WorkflowInstanceDomain with version 0
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(0L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: version should be 0
    assertThat(result.getVersion()).isEqualTo(0L);
  }

  @Test
  void apply_withLargeVersion_shouldHandleCorrectly() {
    // Given: a WorkflowInstanceDomain with large version number
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(999999L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: large version should be handled correctly
    assertThat(result.getVersion()).isEqualTo(999999L);
  }

  @Test
  void apply_withSpecialCharactersInNames_shouldHandleCorrectly() {
    // Given: a WorkflowInstanceDomain with special characters in names and IDs
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-with-dashes_and_underscores")
        .name("Workflow with Special !@#$% Characters")
        .version(1L)
        .instanceId("instance:with:colons")
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: special characters should be preserved
    assertThat(result.getId()).isEqualTo("Workflow with Special !@#$% Characters");
    assertThat(result.getInstanceId()).isEqualTo("instance:with:colons");
  }

  @Test
  void apply_withEmptyStrings_shouldHandleCorrectly() {
    // Given: a WorkflowInstanceDomain with empty strings
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("")
        .name("")
        .version(1L)
        .instanceId("")
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: empty strings should be preserved
    assertThat(result.getId()).isEmpty();
    assertThat(result.getInstanceId()).isEmpty();
  }

  @Test
  void apply_withWhitespaceStrings_shouldPreserveWhitespace() {
    // Given: a WorkflowInstanceDomain with whitespace strings
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("   ")
        .name("  Workflow Name  ")
        .version(1L)
        .instanceId(" instance-id ")
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: whitespace should be preserved (no trimming)
    assertThat(result.getId()).isEqualTo("  Workflow Name  ");
    assertThat(result.getInstanceId()).isEqualTo(" instance-id ");
  }

  @Test
  void apply_withMillisecondPrecisionDuration_shouldHandleCorrectly() {
    // Given: a WorkflowInstanceDomain with millisecond precision
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("quickWorkflow")
        .version(1L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T10:30:00.000Z"))
        .endDate(Instant.parse("2024-01-15T10:30:00.456Z"))
        .duration(Duration.ofMillis(456L))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: millisecond precision should be preserved
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(456L));
    assertThat(result.getDuration().toMillis()).isEqualTo(456L);
  }

  @Test
  void apply_withUnicodeCharacters_shouldHandleCorrectly() {
    // Given: a WorkflowInstanceDomain with Unicode characters
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("Flux de travail 工作流程 ワークフロー")
        .version(1L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: Unicode characters should be preserved
    assertThat(result.getId()).isEqualTo("Flux de travail 工作流程 ワークフロー");
  }

  @Test
  void apply_withLongWorkflowName_shouldHandleCorrectly() {
    // Given: a WorkflowInstanceDomain with a very long workflow name
    String longName = "This is a very long workflow name that might be used in real-world scenarios " +
        "where workflow names contain detailed descriptions of what the workflow does and might include " +
        "additional context about the business process and its requirements and might even be longer than expected";

    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name(longName)
        .version(1L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: long name should be preserved
    assertThat(result.getId()).isEqualTo(longName);
  }

  @Test
  void apply_withVariousTimestamps_shouldPreserveTimestamps() {
    // Given: a WorkflowInstanceDomain with timestamps with nanosecond precision
    Instant startDate = Instant.parse("2024-01-15T10:30:00.123456789Z");
    Instant endDate = Instant.parse("2024-01-15T11:30:00.987654321Z");

    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(1L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(startDate)
        .endDate(endDate)
        .duration(Duration.between(startDate, endDate))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: timestamps should be preserved with full precision
    assertThat(result.getStartDate()).isEqualTo(startDate);
    assertThat(result.getEndDate()).isEqualTo(endDate);
  }

  @Test
  void apply_multipleConversions_shouldProduceConsistentResults() {
    // Given: a WorkflowInstanceDomain
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(1L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter multiple times
    WorkflowInstView result1 = converter.apply(domain);
    WorkflowInstView result2 = converter.apply(domain);

    // Then: results should be consistent (converter should be stateless)
    assertThat(result1.getId()).isEqualTo(result2.getId());
    assertThat(result1.getVersion()).isEqualTo(result2.getVersion());
    assertThat(result1.getInstanceId()).isEqualTo(result2.getInstanceId());
    assertThat(result1.getStatus()).isEqualTo(result2.getStatus());
    assertThat(result1.getStartDate()).isEqualTo(result2.getStartDate());
    assertThat(result1.getEndDate()).isEqualTo(result2.getEndDate());
    assertThat(result1.getDuration()).isEqualTo(result2.getDuration());
  }

  @Test
  void apply_withStatusCaseInsensitive_shouldHandleCorrectly() {
    // Given: a WorkflowInstanceDomain with lowercase status
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(1L)
        .instanceId("instance-456")
        .status("completed")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: status should be correctly mapped (case insensitive)
    assertThat(result.getStatus()).isEqualTo(StatusEnum.COMPLETED);
  }

  @Test
  void apply_withMixedCaseStatus_shouldHandleCorrectly() {
    // Given: a WorkflowInstanceDomain with mixed case status
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(1L)
        .instanceId("instance-456")
        .status("PeNdInG")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(null)
        .duration(null)
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: status should be correctly mapped (case insensitive)
    assertThat(result.getStatus()).isEqualTo(StatusEnum.PENDING);
  }

  @Test
  void apply_withNegativeVersion_shouldHandleCorrectly() {
    // Given: a WorkflowInstanceDomain with negative version (edge case)
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(-1L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: negative version should be preserved
    assertThat(result.getVersion()).isEqualTo(-1L);
  }

  @Test
  void apply_withDomainIdNotUsed_shouldUseNameForId() {
    // Given: a WorkflowInstanceDomain where id field differs from name field
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("internal-domain-id-123")
        .name("displayWorkflowName")
        .version(1L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(Instant.parse("2024-01-15T10:30:00Z"))
        .endDate(Instant.parse("2024-01-15T11:30:00Z"))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: the view should use name (not the domain's id field)
    assertThat(result.getId()).isEqualTo("displayWorkflowName");
    assertThat(result.getId()).isNotEqualTo("internal-domain-id-123");
  }

  @Test
  void apply_withVeryOldTimestamp_shouldHandleCorrectly() {
    // Given: a WorkflowInstanceDomain with very old timestamp
    Instant oldTimestamp = Instant.parse("1970-01-01T00:00:00Z");
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(1L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(oldTimestamp)
        .endDate(oldTimestamp.plusSeconds(3600))
        .duration(Duration.ofHours(1))
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: old timestamp should be handled correctly
    assertThat(result.getStartDate()).isEqualTo(oldTimestamp);
    assertThat(result.getEndDate()).isEqualTo(oldTimestamp.plusSeconds(3600));
  }

  @Test
  void apply_withFutureTimestamp_shouldHandleCorrectly() {
    // Given: a WorkflowInstanceDomain with future timestamp
    Instant futureTimestamp = Instant.parse("2099-12-31T23:59:59Z");
    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("workflowName")
        .version(1L)
        .instanceId("instance-456")
        .status("PENDING")
        .startDate(futureTimestamp)
        .endDate(null)
        .duration(null)
        .build();

    // When: applying the converter
    WorkflowInstView result = converter.apply(domain);

    // Then: future timestamp should be handled correctly
    assertThat(result.getStartDate()).isEqualTo(futureTimestamp);
  }
}
