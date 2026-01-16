package com.symphony.bdk.workflow.management.converter;

import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VersionedWorkflowConverterClaudeTest {

  // ==================== Constructor Tests ====================

  @Test
  void constructor_shouldCreateInstance() {
    // When: Creating a new converter
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    // Then: The instance should be created successfully
    assertThat(converter).isNotNull();
  }

  // ==================== apply() Tests ====================

  @Test
  void apply_withAllFieldsPopulated_shouldConvertCorrectly() {
    // Given: A VersionedWorkflow with all fields populated
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("id-123");
    workflow.setWorkflowId("workflow-abc");
    workflow.setVersion(5L);
    workflow.setActive(true);
    workflow.setPublished(true);
    workflow.setDeploymentId("deployment-xyz");
    workflow.setSwadl("id: workflow-abc\nactivities:\n  - send-message");
    workflow.setDescription("Test workflow description");
    workflow.setCreatedBy(12345L);

    // When: Converting to VersionedWorkflowView
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: All fields should be copied correctly
    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("id-123");
    assertThat(result.getWorkflowId()).isEqualTo("workflow-abc");
    assertThat(result.getVersion()).isEqualTo(5L);
    assertThat(result.getActive()).isTrue();
    assertThat(result.getPublished()).isTrue();
    assertThat(result.getDeploymentId()).isEqualTo("deployment-xyz");
    assertThat(result.getSwadl()).isEqualTo("id: workflow-abc\nactivities:\n  - send-message");
    assertThat(result.getDescription()).isEqualTo("Test workflow description");
    assertThat(result.getCreatedBy()).isEqualTo(12345L);
  }

  @Test
  void apply_withNullActive_shouldReturnFalse() {
    // Given: A VersionedWorkflow with null active field (getActive() returns false)
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("id-456");
    workflow.setWorkflowId("workflow-def");
    workflow.setVersion(1L);
    workflow.setActive(null); // Will be returned as false by getActive()
    workflow.setPublished(false);

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Active should be false (from VersionedWorkflow.getActive() default behavior)
    assertThat(result.getActive()).isFalse();
  }

  @Test
  void apply_withActiveFalse_shouldReturnFalse() {
    // Given: A VersionedWorkflow with active set to false
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("id-789");
    workflow.setWorkflowId("workflow-ghi");
    workflow.setVersion(2L);
    workflow.setActive(false);
    workflow.setPublished(true);

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Active should be false
    assertThat(result.getActive()).isFalse();
  }

  @Test
  void apply_withPublishedTrue_shouldReturnTrue() {
    // Given: A VersionedWorkflow with published set to true
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("id-pub-1");
    workflow.setWorkflowId("workflow-pub");
    workflow.setVersion(1L);
    workflow.setPublished(true);

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Published should be true
    assertThat(result.getPublished()).isTrue();
  }

  @Test
  void apply_withPublishedFalse_shouldReturnFalse() {
    // Given: A VersionedWorkflow with published set to false
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("id-unpub-1");
    workflow.setWorkflowId("workflow-unpub");
    workflow.setVersion(1L);
    workflow.setPublished(false);

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Published should be false
    assertThat(result.getPublished()).isFalse();
  }

  @Test
  void apply_withNullOptionalFields_shouldHandleNulls() {
    // Given: A VersionedWorkflow with only required fields
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId(null);
    workflow.setWorkflowId("workflow-minimal");
    workflow.setVersion(1L);
    workflow.setActive(null);
    workflow.setPublished(true);
    workflow.setDeploymentId(null);
    workflow.setSwadl(null);
    workflow.setDescription(null);
    workflow.setCreatedBy(null);

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Null fields should be preserved
    assertThat(result.getId()).isNull();
    assertThat(result.getWorkflowId()).isEqualTo("workflow-minimal");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getActive()).isFalse(); // getActive() returns false for null
    assertThat(result.getPublished()).isTrue();
    assertThat(result.getDeploymentId()).isNull();
    assertThat(result.getSwadl()).isNull();
    assertThat(result.getDescription()).isNull();
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withEmptyStringFields_shouldPreserveEmptyStrings() {
    // Given: A VersionedWorkflow with empty strings
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("");
    workflow.setWorkflowId("");
    workflow.setVersion(0L);
    workflow.setActive(false);
    workflow.setPublished(false);
    workflow.setDeploymentId("");
    workflow.setSwadl("");
    workflow.setDescription("");
    workflow.setCreatedBy(0L);

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Empty strings should be preserved
    assertThat(result.getId()).isEmpty();
    assertThat(result.getWorkflowId()).isEmpty();
    assertThat(result.getVersion()).isZero();
    assertThat(result.getActive()).isFalse();
    assertThat(result.getPublished()).isFalse();
    assertThat(result.getDeploymentId()).isEmpty();
    assertThat(result.getSwadl()).isEmpty();
    assertThat(result.getDescription()).isEmpty();
    assertThat(result.getCreatedBy()).isZero();
  }

  @Test
  void apply_withLargeVersionNumber_shouldHandleLargeValues() {
    // Given: A VersionedWorkflow with a large version number
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("id-large");
    workflow.setWorkflowId("workflow-large");
    workflow.setVersion(Long.MAX_VALUE);
    workflow.setPublished(true);
    workflow.setCreatedBy(Long.MAX_VALUE);

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Large values should be handled correctly
    assertThat(result.getVersion()).isEqualTo(Long.MAX_VALUE);
    assertThat(result.getCreatedBy()).isEqualTo(Long.MAX_VALUE);
  }

  @Test
  void apply_withSpecialCharactersInFields_shouldPreserveSpecialCharacters() {
    // Given: A VersionedWorkflow with special characters and Unicode
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("id-特殊-🎉");
    workflow.setWorkflowId("workflow-特殊-🎉");
    workflow.setVersion(1L);
    workflow.setPublished(true);
    workflow.setDeploymentId("deploy-特殊");
    workflow.setSwadl("id: workflow-特殊-🎉\ndescription: \"Test with émojis 🚀\"");
    workflow.setDescription("Description with special chars: @#$%^&*() and Unicode: 世界");
    workflow.setCreatedBy(999L);

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Special characters should be preserved
    assertThat(result.getId()).isEqualTo("id-特殊-🎉");
    assertThat(result.getWorkflowId()).isEqualTo("workflow-特殊-🎉");
    assertThat(result.getSwadl()).contains("🚀");
    assertThat(result.getDescription()).contains("世界");
    assertThat(result.getDeploymentId()).isEqualTo("deploy-特殊");
  }

  @Test
  void apply_withLongSwadlContent_shouldHandleLargeContent() {
    // Given: A VersionedWorkflow with very long swadl content
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    StringBuilder longSwadl = new StringBuilder("id: workflow-long\n");
    for (int i = 0; i < 1000; i++) {
      longSwadl.append("  - activity-").append(i).append(":\n");
      longSwadl.append("      execute: action-").append(i).append("\n");
    }

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("id-long");
    workflow.setWorkflowId("workflow-long");
    workflow.setVersion(1L);
    workflow.setPublished(true);
    workflow.setSwadl(longSwadl.toString());
    workflow.setDescription("Long workflow");
    workflow.setCreatedBy(777L);

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Long content should be handled correctly
    assertThat(result.getSwadl()).isEqualTo(longSwadl.toString());
    assertThat(result.getSwadl().length()).isGreaterThan(10000);
  }

  @Test
  void apply_multipleCallsWithSameConverter_shouldProduceIndependentResults() {
    // Given: A single converter instance
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setId("id-1");
    workflow1.setWorkflowId("workflow-1");
    workflow1.setVersion(1L);
    workflow1.setPublished(true);
    workflow1.setSwadl("swadl-1");
    workflow1.setDescription("Description 1");
    workflow1.setCreatedBy(100L);

    VersionedWorkflow workflow2 = new VersionedWorkflow();
    workflow2.setId("id-2");
    workflow2.setWorkflowId("workflow-2");
    workflow2.setVersion(2L);
    workflow2.setPublished(false);
    workflow2.setSwadl("swadl-2");
    workflow2.setDescription("Description 2");
    workflow2.setCreatedBy(200L);

    // When: Converting multiple times with the same converter
    VersionedWorkflowView result1 = converter.apply(workflow1);
    VersionedWorkflowView result2 = converter.apply(workflow2);

    // Then: Results should be independent
    assertThat(result1.getId()).isEqualTo("id-1");
    assertThat(result1.getWorkflowId()).isEqualTo("workflow-1");
    assertThat(result1.getVersion()).isEqualTo(1L);
    assertThat(result1.getPublished()).isTrue();
    assertThat(result1.getSwadl()).isEqualTo("swadl-1");
    assertThat(result1.getCreatedBy()).isEqualTo(100L);

    assertThat(result2.getId()).isEqualTo("id-2");
    assertThat(result2.getWorkflowId()).isEqualTo("workflow-2");
    assertThat(result2.getVersion()).isEqualTo(2L);
    assertThat(result2.getPublished()).isFalse();
    assertThat(result2.getSwadl()).isEqualTo("swadl-2");
    assertThat(result2.getCreatedBy()).isEqualTo(200L);
  }

  @Test
  void apply_withNegativeCreatedBy_shouldHandleNegativeValue() {
    // Given: A VersionedWorkflow with negative createdBy value
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("id-neg");
    workflow.setWorkflowId("workflow-neg");
    workflow.setVersion(1L);
    workflow.setPublished(true);
    workflow.setCreatedBy(-999L);

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Negative value should be preserved
    assertThat(result.getCreatedBy()).isEqualTo(-999L);
  }

  @Test
  void apply_withWhitespaceOnlyFields_shouldPreserveWhitespace() {
    // Given: A VersionedWorkflow with fields containing only whitespace
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("   ");
    workflow.setWorkflowId("   ");
    workflow.setVersion(1L);
    workflow.setPublished(true);
    workflow.setDeploymentId("   ");
    workflow.setSwadl("   ");
    workflow.setDescription("   ");

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Whitespace should be preserved
    assertThat(result.getId()).isEqualTo("   ");
    assertThat(result.getWorkflowId()).isEqualTo("   ");
    assertThat(result.getSwadl()).isEqualTo("   ");
    assertThat(result.getDescription()).isEqualTo("   ");
    assertThat(result.getDeploymentId()).isEqualTo("   ");
  }

  @Test
  void apply_withMixedActiveAndPublishedStates_shouldHandleAllCombinations() {
    // Given: A converter
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    // Test case 1: active=true, published=true
    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setId("id-1");
    workflow1.setWorkflowId("workflow-1");
    workflow1.setVersion(1L);
    workflow1.setActive(true);
    workflow1.setPublished(true);

    VersionedWorkflowView result1 = converter.apply(workflow1);
    assertThat(result1.getActive()).isTrue();
    assertThat(result1.getPublished()).isTrue();

    // Test case 2: active=true, published=false
    VersionedWorkflow workflow2 = new VersionedWorkflow();
    workflow2.setId("id-2");
    workflow2.setWorkflowId("workflow-2");
    workflow2.setVersion(2L);
    workflow2.setActive(true);
    workflow2.setPublished(false);

    VersionedWorkflowView result2 = converter.apply(workflow2);
    assertThat(result2.getActive()).isTrue();
    assertThat(result2.getPublished()).isFalse();

    // Test case 3: active=false, published=true
    VersionedWorkflow workflow3 = new VersionedWorkflow();
    workflow3.setId("id-3");
    workflow3.setWorkflowId("workflow-3");
    workflow3.setVersion(3L);
    workflow3.setActive(false);
    workflow3.setPublished(true);

    VersionedWorkflowView result3 = converter.apply(workflow3);
    assertThat(result3.getActive()).isFalse();
    assertThat(result3.getPublished()).isTrue();

    // Test case 4: active=false, published=false
    VersionedWorkflow workflow4 = new VersionedWorkflow();
    workflow4.setId("id-4");
    workflow4.setWorkflowId("workflow-4");
    workflow4.setVersion(4L);
    workflow4.setActive(false);
    workflow4.setPublished(false);

    VersionedWorkflowView result4 = converter.apply(workflow4);
    assertThat(result4.getActive()).isFalse();
    assertThat(result4.getPublished()).isFalse();
  }

  @Test
  void apply_withNewlineAndTabCharactersInSwadl_shouldPreserveFormatting() {
    // Given: A VersionedWorkflow with multiline swadl containing tabs and newlines
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    String swadlWithFormatting = "id: workflow-formatted\n" +
        "activities:\n" +
        "\t- send-message:\n" +
        "\t\t\tcontent: \"Hello\\nWorld\"\n" +
        "\t- receive-message";

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("id-formatted");
    workflow.setWorkflowId("workflow-formatted");
    workflow.setVersion(1L);
    workflow.setPublished(true);
    workflow.setSwadl(swadlWithFormatting);

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Formatting should be preserved
    assertThat(result.getSwadl()).isEqualTo(swadlWithFormatting);
    assertThat(result.getSwadl()).contains("\n");
    assertThat(result.getSwadl()).contains("\t");
  }

  @Test
  void apply_withLongDescription_shouldHandleLongText() {
    // Given: A VersionedWorkflow with a very long description
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    StringBuilder longDescription = new StringBuilder();
    for (int i = 0; i < 500; i++) {
      longDescription.append("This is a very long description line ").append(i).append(". ");
    }

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("id-long-desc");
    workflow.setWorkflowId("workflow-long-desc");
    workflow.setVersion(1L);
    workflow.setPublished(true);
    workflow.setDescription(longDescription.toString());

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Long description should be handled correctly
    assertThat(result.getDescription()).isEqualTo(longDescription.toString());
    assertThat(result.getDescription().length()).isGreaterThan(10000);
  }

  @Test
  void apply_withMinimalVersionedWorkflow_shouldConvertSuccessfully() {
    // Given: A VersionedWorkflow with only the most basic fields set
    VersionedWorkflowConverter converter = new VersionedWorkflowConverter();

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("minimal");
    workflow.setVersion(1L);
    workflow.setPublished(false);

    // When: Converting
    VersionedWorkflowView result = converter.apply(workflow);

    // Then: Conversion should succeed with defaults for unset fields
    assertThat(result).isNotNull();
    assertThat(result.getWorkflowId()).isEqualTo("minimal");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getPublished()).isFalse();
    assertThat(result.getActive()).isFalse();
  }
}
