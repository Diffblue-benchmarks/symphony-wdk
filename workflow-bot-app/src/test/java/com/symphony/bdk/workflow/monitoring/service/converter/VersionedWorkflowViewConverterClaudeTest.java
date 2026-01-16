package com.symphony.bdk.workflow.monitoring.service.converter;

import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for VersionedWorkflowViewConverter.
 * Tests the conversion from VersionedWorkflow to WorkflowView.
 */
class VersionedWorkflowViewConverterClaudeTest {

  private VersionedWorkflowViewConverter converter;

  @BeforeEach
  void setUp() {
    converter = new VersionedWorkflowViewConverter();
  }

  // ==================== Constructor Tests ====================

  @Test
  void constructor_shouldCreateInstance() {
    // When: creating a new instance
    VersionedWorkflowViewConverter newConverter = new VersionedWorkflowViewConverter();

    // Then: instance should not be null
    assertThat(newConverter).isNotNull();
  }

  // ==================== apply() Tests ====================

  @Test
  void apply_withAllFieldsSet_shouldConvertCorrectly() {
    // Given: a complete VersionedWorkflow with all relevant fields set
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("workflow-123");
    workflow.setVersion(5L);
    workflow.setCreatedBy(123456789L);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: all fields should be mapped correctly
    assertThat(result.getId()).isEqualTo("workflow-123");
    assertThat(result.getVersion()).isEqualTo(5L);
    assertThat(result.getCreatedBy()).isEqualTo(123456789L);
  }

  @Test
  void apply_withNullCreatedBy_shouldSetCreatedByToNull() {
    // Given: a VersionedWorkflow with null createdBy
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("workflow-456");
    workflow.setVersion(3L);
    workflow.setCreatedBy(null);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: createdBy should be null in the result
    assertThat(result.getId()).isEqualTo("workflow-456");
    assertThat(result.getVersion()).isEqualTo(3L);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withVersionZero_shouldHandleCorrectly() {
    // Given: a VersionedWorkflow with version 0 (initial version)
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("workflow-789");
    workflow.setVersion(0L);
    workflow.setCreatedBy(987654321L);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: version 0 should be preserved
    assertThat(result.getId()).isEqualTo("workflow-789");
    assertThat(result.getVersion()).isEqualTo(0L);
    assertThat(result.getCreatedBy()).isEqualTo(987654321L);
  }

  @Test
  void apply_withVersionOne_shouldHandleCorrectly() {
    // Given: a VersionedWorkflow with version 1 (first version)
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("workflow-first");
    workflow.setVersion(1L);
    workflow.setCreatedBy(111111111L);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: version 1 should be preserved
    assertThat(result.getId()).isEqualTo("workflow-first");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isEqualTo(111111111L);
  }

  @Test
  void apply_withLargeVersion_shouldHandleCorrectly() {
    // Given: a VersionedWorkflow with a very large version number
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("workflow-many-versions");
    workflow.setVersion(999999L);
    workflow.setCreatedBy(123456789L);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: large version should be handled correctly
    assertThat(result.getId()).isEqualTo("workflow-many-versions");
    assertThat(result.getVersion()).isEqualTo(999999L);
    assertThat(result.getCreatedBy()).isEqualTo(123456789L);
  }

  @Test
  void apply_withSpecialCharactersInWorkflowId_shouldHandleCorrectly() {
    // Given: a VersionedWorkflow with special characters in workflowId
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("workflow-with-dashes_and_underscores.and.dots");
    workflow.setVersion(2L);
    workflow.setCreatedBy(555555555L);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: special characters should be preserved
    assertThat(result.getId()).isEqualTo("workflow-with-dashes_and_underscores.and.dots");
    assertThat(result.getVersion()).isEqualTo(2L);
    assertThat(result.getCreatedBy()).isEqualTo(555555555L);
  }

  @Test
  void apply_withEmptyWorkflowId_shouldHandleCorrectly() {
    // Given: a VersionedWorkflow with empty workflowId
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("");
    workflow.setVersion(1L);
    workflow.setCreatedBy(123456789L);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: empty string should be preserved
    assertThat(result.getId()).isEmpty();
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isEqualTo(123456789L);
  }

  @Test
  void apply_withWhitespaceWorkflowId_shouldPreserveWhitespace() {
    // Given: a VersionedWorkflow with whitespace in workflowId
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("  workflow-with-spaces  ");
    workflow.setVersion(1L);
    workflow.setCreatedBy(123456789L);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: whitespace should be preserved (no trimming)
    assertThat(result.getId()).isEqualTo("  workflow-with-spaces  ");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isEqualTo(123456789L);
  }

  @Test
  void apply_withUnicodeCharacters_shouldHandleCorrectly() {
    // Given: a VersionedWorkflow with Unicode characters in workflowId
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("workflow-用户流程-フロー");
    workflow.setVersion(1L);
    workflow.setCreatedBy(123456789L);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: Unicode characters should be preserved
    assertThat(result.getId()).isEqualTo("workflow-用户流程-フロー");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isEqualTo(123456789L);
  }

  @Test
  void apply_withLongWorkflowId_shouldHandleCorrectly() {
    // Given: a VersionedWorkflow with a long workflowId (within the 100 char limit)
    String longId = "workflow-with-a-very-long-identifier-that-contains-many-characters-but-is-still-within-limit";
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId(longId);
    workflow.setVersion(1L);
    workflow.setCreatedBy(123456789L);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: long workflowId should be preserved
    assertThat(result.getId()).isEqualTo(longId);
    assertThat(result.getId()).hasSize(longId.length());
  }

  @Test
  void apply_withZeroCreatedBy_shouldHandleCorrectly() {
    // Given: a VersionedWorkflow with createdBy = 0
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("workflow-zero-user");
    workflow.setVersion(1L);
    workflow.setCreatedBy(0L);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: createdBy = 0 should be preserved
    assertThat(result.getId()).isEqualTo("workflow-zero-user");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isEqualTo(0L);
  }

  @Test
  void apply_withNegativeCreatedBy_shouldHandleCorrectly() {
    // Given: a VersionedWorkflow with negative createdBy (edge case)
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("workflow-negative-user");
    workflow.setVersion(1L);
    workflow.setCreatedBy(-1L);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: negative createdBy should be preserved
    assertThat(result.getId()).isEqualTo("workflow-negative-user");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isEqualTo(-1L);
  }

  @Test
  void apply_withLargeCreatedBy_shouldHandleCorrectly() {
    // Given: a VersionedWorkflow with very large createdBy value
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("workflow-large-user-id");
    workflow.setVersion(1L);
    workflow.setCreatedBy(Long.MAX_VALUE);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: large createdBy should be handled correctly
    assertThat(result.getId()).isEqualTo("workflow-large-user-id");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isEqualTo(Long.MAX_VALUE);
  }

  @Test
  void apply_multipleConversions_shouldProduceConsistentResults() {
    // Given: a VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("workflow-consistent");
    workflow.setVersion(10L);
    workflow.setCreatedBy(123456789L);

    // When: applying the converter multiple times
    WorkflowView result1 = converter.apply(workflow);
    WorkflowView result2 = converter.apply(workflow);

    // Then: results should be consistent (converter should be stateless)
    assertThat(result1.getId()).isEqualTo(result2.getId());
    assertThat(result1.getVersion()).isEqualTo(result2.getVersion());
    assertThat(result1.getCreatedBy()).isEqualTo(result2.getCreatedBy());
  }

  @Test
  void apply_shouldNotIncludeOtherWorkflowFields() {
    // Given: a VersionedWorkflow with additional fields not mapped to WorkflowView
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("workflow-extra-fields");
    workflow.setVersion(1L);
    workflow.setCreatedBy(123456789L);
    // These fields should not be mapped to WorkflowView
    workflow.setId("internal-id-123");
    workflow.setPublished(true);
    workflow.setActive(true);
    workflow.setSwadl("workflow: test");
    workflow.setDeploymentId("deploy-456");
    workflow.setDescription("Test workflow");

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: only workflowId, version, and createdBy should be mapped
    assertThat(result.getId()).isEqualTo("workflow-extra-fields");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isEqualTo(123456789L);
  }

  @Test
  void apply_withDifferentWorkflows_shouldProduceIndependentResults() {
    // Given: two different VersionedWorkflow instances
    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setWorkflowId("workflow-1");
    workflow1.setVersion(1L);
    workflow1.setCreatedBy(111L);

    VersionedWorkflow workflow2 = new VersionedWorkflow();
    workflow2.setWorkflowId("workflow-2");
    workflow2.setVersion(2L);
    workflow2.setCreatedBy(222L);

    // When: applying the converter to both
    WorkflowView result1 = converter.apply(workflow1);
    WorkflowView result2 = converter.apply(workflow2);

    // Then: results should be independent and different
    assertThat(result1.getId()).isEqualTo("workflow-1");
    assertThat(result1.getVersion()).isEqualTo(1L);
    assertThat(result1.getCreatedBy()).isEqualTo(111L);

    assertThat(result2.getId()).isEqualTo("workflow-2");
    assertThat(result2.getVersion()).isEqualTo(2L);
    assertThat(result2.getCreatedBy()).isEqualTo(222L);

    assertThat(result1).isNotEqualTo(result2);
  }

  @Test
  void apply_withMinimalFields_shouldHandleCorrectly() {
    // Given: a VersionedWorkflow with minimal required fields
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("minimal-workflow");
    workflow.setVersion(1L);
    // createdBy is not set (defaults to null)

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: should handle minimal fields correctly
    assertThat(result.getId()).isEqualTo("minimal-workflow");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withNullWorkflowId_shouldSetIdToNull() {
    // Given: a VersionedWorkflow with null workflowId
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId(null);
    workflow.setVersion(1L);
    workflow.setCreatedBy(123456789L);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: id should be null in the result
    assertThat(result.getId()).isNull();
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isEqualTo(123456789L);
  }

  @Test
  void apply_withNullVersion_shouldSetVersionToNull() {
    // Given: a VersionedWorkflow with null version
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("workflow-no-version");
    workflow.setVersion(null);
    workflow.setCreatedBy(123456789L);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: version should be null in the result
    assertThat(result.getId()).isEqualTo("workflow-no-version");
    assertThat(result.getVersion()).isNull();
    assertThat(result.getCreatedBy()).isEqualTo(123456789L);
  }

  @Test
  void apply_withAllNullFields_shouldHandleCorrectly() {
    // Given: a VersionedWorkflow with all relevant fields null
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId(null);
    workflow.setVersion(null);
    workflow.setCreatedBy(null);

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: all fields in result should be null
    assertThat(result.getId()).isNull();
    assertThat(result.getVersion()).isNull();
    assertThat(result.getCreatedBy()).isNull();
  }
}
