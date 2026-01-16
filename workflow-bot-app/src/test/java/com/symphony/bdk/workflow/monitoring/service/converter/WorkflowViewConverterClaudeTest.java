package com.symphony.bdk.workflow.monitoring.service.converter;

import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for WorkflowViewConverter.
 * Tests the conversion from WorkflowDomain to WorkflowView.
 */
class WorkflowViewConverterClaudeTest {

  private WorkflowViewConverter converter;

  @BeforeEach
  void setUp() {
    converter = new WorkflowViewConverter();
  }

  // ==================== Constructor Tests ====================

  @Test
  void constructor_shouldCreateInstance() {
    // When: creating a new instance
    WorkflowViewConverter newConverter = new WorkflowViewConverter();

    // Then: instance should not be null
    assertThat(newConverter).isNotNull();
  }

  // ==================== apply() Tests ====================

  @Test
  void apply_withAllFieldsSet_shouldConvertCorrectly() {
    // Given: a complete WorkflowDomain with all fields set
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("internal-id-123")
        .name("workflow-123")
        .version(5L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: name should be mapped to id, version should be preserved
    assertThat(result.getId()).isEqualTo("workflow-123");
    assertThat(result.getVersion()).isEqualTo(5L);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_shouldMapNameToId() {
    // Given: a WorkflowDomain where name and id are different
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("domain-id-999")
        .name("workflow-name-abc")
        .version(1L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: the name field should be mapped to id (not the id field)
    assertThat(result.getId()).isEqualTo("workflow-name-abc");
    assertThat(result.getId()).isNotEqualTo("domain-id-999");
  }

  @Test
  void apply_shouldIgnoreDomainIdField() {
    // Given: a WorkflowDomain with an id field
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("ignored-id")
        .name("used-name")
        .version(1L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: the domain id field should be ignored, only name should be used
    assertThat(result.getId()).isEqualTo("used-name");
    assertThat(result.getId()).doesNotContain("ignored-id");
  }

  @Test
  void apply_shouldAlwaysSetCreatedByToNull() {
    // Given: a WorkflowDomain (which has no createdBy field)
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-1")
        .name("workflow-1")
        .version(1L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: createdBy should always be null since WorkflowDomain has no such field
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withVersionZero_shouldHandleCorrectly() {
    // Given: a WorkflowDomain with version 0 (initial version)
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-789")
        .name("workflow-789")
        .version(0L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: version 0 should be preserved
    assertThat(result.getId()).isEqualTo("workflow-789");
    assertThat(result.getVersion()).isEqualTo(0L);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withVersionOne_shouldHandleCorrectly() {
    // Given: a WorkflowDomain with version 1 (first version)
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-first")
        .name("workflow-first")
        .version(1L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: version 1 should be preserved
    assertThat(result.getId()).isEqualTo("workflow-first");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withLargeVersion_shouldHandleCorrectly() {
    // Given: a WorkflowDomain with a very large version number
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-many")
        .name("workflow-many-versions")
        .version(999999L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: large version should be handled correctly
    assertThat(result.getId()).isEqualTo("workflow-many-versions");
    assertThat(result.getVersion()).isEqualTo(999999L);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withMaxLongVersion_shouldHandleCorrectly() {
    // Given: a WorkflowDomain with Long.MAX_VALUE version
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-max")
        .name("workflow-max-version")
        .version(Long.MAX_VALUE)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: maximum version should be handled correctly
    assertThat(result.getId()).isEqualTo("workflow-max-version");
    assertThat(result.getVersion()).isEqualTo(Long.MAX_VALUE);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withSpecialCharactersInName_shouldHandleCorrectly() {
    // Given: a WorkflowDomain with special characters in name
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-special")
        .name("workflow-with-dashes_and_underscores.and.dots")
        .version(2L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: special characters should be preserved
    assertThat(result.getId()).isEqualTo("workflow-with-dashes_and_underscores.and.dots");
    assertThat(result.getVersion()).isEqualTo(2L);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withEmptyName_shouldHandleCorrectly() {
    // Given: a WorkflowDomain with empty name
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-empty")
        .name("")
        .version(1L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: empty string should be preserved
    assertThat(result.getId()).isEmpty();
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withWhitespaceName_shouldPreserveWhitespace() {
    // Given: a WorkflowDomain with whitespace in name
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-spaces")
        .name("  workflow-with-spaces  ")
        .version(1L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: whitespace should be preserved (no trimming)
    assertThat(result.getId()).isEqualTo("  workflow-with-spaces  ");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withUnicodeCharacters_shouldHandleCorrectly() {
    // Given: a WorkflowDomain with Unicode characters in name
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-unicode")
        .name("workflow-用户流程-フロー")
        .version(1L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: Unicode characters should be preserved
    assertThat(result.getId()).isEqualTo("workflow-用户流程-フロー");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withLongName_shouldHandleCorrectly() {
    // Given: a WorkflowDomain with a long name
    String longName = "workflow-with-a-very-long-identifier-that-contains-many-characters-but-is-still-within-limit";
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-long")
        .name(longName)
        .version(1L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: long name should be preserved
    assertThat(result.getId()).isEqualTo(longName);
    assertThat(result.getId()).hasSize(longName.length());
  }

  @Test
  void apply_multipleConversions_shouldProduceConsistentResults() {
    // Given: a WorkflowDomain
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-consistent")
        .name("workflow-consistent")
        .version(10L)
        .build();

    // When: applying the converter multiple times
    WorkflowView result1 = converter.apply(workflow);
    WorkflowView result2 = converter.apply(workflow);

    // Then: results should be consistent (converter should be stateless)
    assertThat(result1.getId()).isEqualTo(result2.getId());
    assertThat(result1.getVersion()).isEqualTo(result2.getVersion());
    assertThat(result1.getCreatedBy()).isEqualTo(result2.getCreatedBy());
  }

  @Test
  void apply_withDifferentWorkflows_shouldProduceIndependentResults() {
    // Given: two different WorkflowDomain instances
    WorkflowDomain workflow1 = WorkflowDomain.builder()
        .id("id-1")
        .name("workflow-1")
        .version(1L)
        .build();

    WorkflowDomain workflow2 = WorkflowDomain.builder()
        .id("id-2")
        .name("workflow-2")
        .version(2L)
        .build();

    // When: applying the converter to both
    WorkflowView result1 = converter.apply(workflow1);
    WorkflowView result2 = converter.apply(workflow2);

    // Then: results should be independent and different
    assertThat(result1.getId()).isEqualTo("workflow-1");
    assertThat(result1.getVersion()).isEqualTo(1L);

    assertThat(result2.getId()).isEqualTo("workflow-2");
    assertThat(result2.getVersion()).isEqualTo(2L);

    assertThat(result1).isNotEqualTo(result2);
  }

  @Test
  void apply_withNullName_shouldSetIdToNull() {
    // Given: a WorkflowDomain with null name
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-null-name")
        .name(null)
        .version(1L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: id should be null in the result
    assertThat(result.getId()).isNull();
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withNullVersion_shouldSetVersionToNull() {
    // Given: a WorkflowDomain with null version
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-null-version")
        .name("workflow-no-version")
        .version(null)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: version should be null in the result
    assertThat(result.getId()).isEqualTo("workflow-no-version");
    assertThat(result.getVersion()).isNull();
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withAllNullFields_shouldHandleCorrectly() {
    // Given: a WorkflowDomain with all fields null
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id(null)
        .name(null)
        .version(null)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: all fields in result should be null
    assertThat(result.getId()).isNull();
    assertThat(result.getVersion()).isNull();
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withNameContainingSlashes_shouldHandleCorrectly() {
    // Given: a WorkflowDomain with slashes in name
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-slashes")
        .name("workflow/path/like/name")
        .version(1L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: slashes should be preserved
    assertThat(result.getId()).isEqualTo("workflow/path/like/name");
    assertThat(result.getVersion()).isEqualTo(1L);
  }

  @Test
  void apply_withNameContainingNumbers_shouldHandleCorrectly() {
    // Given: a WorkflowDomain with numbers in name
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-123")
        .name("workflow-v2-2024")
        .version(1L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: numbers should be preserved
    assertThat(result.getId()).isEqualTo("workflow-v2-2024");
    assertThat(result.getVersion()).isEqualTo(1L);
  }

  @Test
  void apply_withNegativeVersion_shouldHandleCorrectly() {
    // Given: a WorkflowDomain with negative version (edge case)
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("id-neg")
        .name("workflow-negative-version")
        .version(-1L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: negative version should be preserved
    assertThat(result.getId()).isEqualTo("workflow-negative-version");
    assertThat(result.getVersion()).isEqualTo(-1L);
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_shouldOnlyMapNameAndVersion() {
    // Given: a WorkflowDomain with all fields
    WorkflowDomain workflow = WorkflowDomain.builder()
        .id("internal-id-to-ignore")
        .name("workflow-mapping-test")
        .version(5L)
        .build();

    // When: applying the converter
    WorkflowView result = converter.apply(workflow);

    // Then: only name (as id) and version should be mapped, createdBy should be null
    assertThat(result.getId()).isEqualTo("workflow-mapping-test");
    assertThat(result.getVersion()).isEqualTo(5L);
    assertThat(result.getCreatedBy()).isNull();
  }
}
