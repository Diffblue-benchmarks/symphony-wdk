package com.symphony.bdk.workflow.management.converter;

import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VersionedWorkflowBiConverterClaudeTest {

  // ==================== Constructor Tests ====================

  @Test
  void constructor_withNullDeploymentId_shouldCreateInstance() {
    // Given: A null deployment ID
    String deploymentId = null;

    // When: Creating a new converter with null deployment ID
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter(deploymentId);

    // Then: The instance should be created successfully
    assertThat(converter).isNotNull();
  }

  @Test
  void constructor_withNonNullDeploymentId_shouldCreateInstance() {
    // Given: A non-null deployment ID
    String deploymentId = "test-deployment-123";

    // When: Creating a new converter with deployment ID
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter(deploymentId);

    // Then: The instance should be created successfully
    assertThat(converter).isNotNull();
  }

  @Test
  void constructor_withEmptyDeploymentId_shouldCreateInstance() {
    // Given: An empty deployment ID
    String deploymentId = "";

    // When: Creating a new converter with empty deployment ID
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter(deploymentId);

    // Then: The instance should be created successfully
    assertThat(converter).isNotNull();
  }

  // ==================== apply() Tests with Null DeploymentId ====================

  @Test
  void apply_withNullDeploymentId_shouldSetActiveToNull() {
    // Given: A converter with null deployment ID
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter(null);

    Workflow workflow = new Workflow();
    workflow.setId("workflow-1");
    workflow.setVersion(1L);

    SwadlView swadlView = SwadlView.builder()
        .swadl("id: workflow-1")
        .description("Test workflow")
        .createdBy(12345L)
        .build();

    // When: Applying the converter
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: Active should be null when deployment ID is null
    assertThat(result).isNotNull();
    assertThat(result.getActive()).isFalse(); // getter returns false for null
    assertThat(result.getDeploymentId()).isNull();
  }

  @Test
  void apply_withNullDeploymentId_shouldCopyAllFields() {
    // Given: A converter with null deployment ID, and complete workflow and swadl view
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter(null);

    Workflow workflow = new Workflow();
    workflow.setId("workflow-123");
    workflow.setVersion(5L);
    Properties properties = new Properties();
    properties.setPublish(true);
    workflow.setProperties(properties);

    SwadlView swadlView = SwadlView.builder()
        .swadl("id: workflow-123\nactivities:\n  - send-message")
        .description("A test workflow description")
        .createdBy(98765L)
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: All fields should be copied correctly
    assertThat(result.getWorkflowId()).isEqualTo("workflow-123");
    assertThat(result.getVersion()).isEqualTo(5L);
    assertThat(result.getSwadl()).isEqualTo("id: workflow-123\nactivities:\n  - send-message");
    assertThat(result.getDescription()).isEqualTo("A test workflow description");
    assertThat(result.getCreatedBy()).isEqualTo(98765L);
    assertThat(result.getPublished()).isTrue();
    assertThat(result.getDeploymentId()).isNull();
    assertThat(result.getActive()).isFalse();
  }

  // ==================== apply() Tests with Non-Null DeploymentId ====================

  @Test
  void apply_withNonNullDeploymentId_shouldSetActiveToTrue() {
    // Given: A converter with non-null deployment ID
    String deploymentId = "deployment-abc";
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter(deploymentId);

    Workflow workflow = new Workflow();
    workflow.setId("workflow-2");
    workflow.setVersion(2L);

    SwadlView swadlView = SwadlView.builder()
        .swadl("id: workflow-2")
        .description("Test")
        .createdBy(111L)
        .build();

    // When: Applying the converter
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: Active should be true and deployment ID should be set
    assertThat(result).isNotNull();
    assertThat(result.getActive()).isTrue();
    assertThat(result.getDeploymentId()).isEqualTo("deployment-abc");
  }

  @Test
  void apply_withNonNullDeploymentId_shouldCopyAllFields() {
    // Given: A converter with non-null deployment ID
    String deploymentId = "deploy-xyz-789";
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter(deploymentId);

    Workflow workflow = new Workflow();
    workflow.setId("workflow-advanced");
    workflow.setVersion(10L);
    Properties properties = new Properties();
    properties.setPublish(false);
    workflow.setProperties(properties);

    SwadlView swadlView = SwadlView.builder()
        .swadl("id: workflow-advanced\nproperties:\n  publish: false")
        .description("Advanced workflow")
        .createdBy(55555L)
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: All fields should be copied correctly with deployment ID set
    assertThat(result.getWorkflowId()).isEqualTo("workflow-advanced");
    assertThat(result.getVersion()).isEqualTo(10L);
    assertThat(result.getSwadl()).isEqualTo("id: workflow-advanced\nproperties:\n  publish: false");
    assertThat(result.getDescription()).isEqualTo("Advanced workflow");
    assertThat(result.getCreatedBy()).isEqualTo(55555L);
    assertThat(result.getPublished()).isFalse();
    assertThat(result.getDeploymentId()).isEqualTo("deploy-xyz-789");
    assertThat(result.getActive()).isTrue();
  }

  // ==================== apply() Tests with Published Flag ====================

  @Test
  void apply_withPublishTrue_shouldSetPublishedToTrue() {
    // Given: A workflow with publish flag set to true
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("deploy-1");

    Workflow workflow = new Workflow();
    workflow.setId("workflow-pub");
    workflow.setVersion(1L);
    Properties properties = new Properties();
    properties.setPublish(true);
    workflow.setProperties(properties);

    SwadlView swadlView = SwadlView.builder()
        .swadl("id: workflow-pub")
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: Published should be true
    assertThat(result.getPublished()).isTrue();
  }

  @Test
  void apply_withPublishFalse_shouldSetPublishedToFalse() {
    // Given: A workflow with publish flag set to false
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("deploy-2");

    Workflow workflow = new Workflow();
    workflow.setId("workflow-unpub");
    workflow.setVersion(1L);
    Properties properties = new Properties();
    properties.setPublish(false);
    workflow.setProperties(properties);

    SwadlView swadlView = SwadlView.builder()
        .swadl("id: workflow-unpub")
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: Published should be false
    assertThat(result.getPublished()).isFalse();
  }

  @Test
  void apply_withNullProperties_shouldUseDefaultPublishValue() {
    // Given: A workflow with null properties (uses default from getProperties())
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("deploy-3");

    Workflow workflow = new Workflow();
    workflow.setId("workflow-default");
    workflow.setVersion(1L);
    workflow.setProperties(null);

    SwadlView swadlView = SwadlView.builder()
        .swadl("id: workflow-default")
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: Published should use the default value (true from Properties default)
    assertThat(result.getPublished()).isTrue();
  }

  // ==================== apply() Tests with Various Field Values ====================

  @Test
  void apply_withNullSwadl_shouldSetNullSwadl() {
    // Given: SwadlView with null swadl
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("deploy-4");

    Workflow workflow = new Workflow();
    workflow.setId("workflow-null-swadl");
    workflow.setVersion(1L);

    SwadlView swadlView = SwadlView.builder()
        .swadl(null)
        .description("Description")
        .createdBy(123L)
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: Swadl should be null
    assertThat(result.getSwadl()).isNull();
  }

  @Test
  void apply_withNullDescription_shouldSetNullDescription() {
    // Given: SwadlView with null description
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("deploy-5");

    Workflow workflow = new Workflow();
    workflow.setId("workflow-null-desc");
    workflow.setVersion(1L);

    SwadlView swadlView = SwadlView.builder()
        .swadl("id: workflow-null-desc")
        .description(null)
        .createdBy(456L)
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: Description should be null
    assertThat(result.getDescription()).isNull();
  }

  @Test
  void apply_withNullCreatedBy_shouldSetNullCreatedBy() {
    // Given: SwadlView with null createdBy
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("deploy-6");

    Workflow workflow = new Workflow();
    workflow.setId("workflow-null-creator");
    workflow.setVersion(1L);

    SwadlView swadlView = SwadlView.builder()
        .swadl("id: workflow-null-creator")
        .description("Desc")
        .createdBy(null)
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: CreatedBy should be null
    assertThat(result.getCreatedBy()).isNull();
  }

  @Test
  void apply_withEmptyStringFields_shouldSetEmptyStrings() {
    // Given: SwadlView and Workflow with empty strings
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("");

    Workflow workflow = new Workflow();
    workflow.setId("");
    workflow.setVersion(0L);

    SwadlView swadlView = SwadlView.builder()
        .swadl("")
        .description("")
        .createdBy(0L)
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: Empty strings should be preserved
    assertThat(result.getWorkflowId()).isEmpty();
    assertThat(result.getSwadl()).isEmpty();
    assertThat(result.getDescription()).isEmpty();
    assertThat(result.getVersion()).isZero();
    assertThat(result.getCreatedBy()).isZero();
    assertThat(result.getDeploymentId()).isEmpty();
  }

  @Test
  void apply_withLargeVersionNumber_shouldHandleLargeVersion() {
    // Given: A workflow with a large version number
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("deploy-7");

    Workflow workflow = new Workflow();
    workflow.setId("workflow-large-version");
    workflow.setVersion(Long.MAX_VALUE);

    SwadlView swadlView = SwadlView.builder()
        .swadl("id: workflow-large-version")
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: Large version should be handled correctly
    assertThat(result.getVersion()).isEqualTo(Long.MAX_VALUE);
  }

  @Test
  void apply_withSpecialCharactersInFields_shouldPreserveSpecialCharacters() {
    // Given: Fields with special characters and Unicode
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("deploy-特殊");

    Workflow workflow = new Workflow();
    workflow.setId("workflow-特殊-🎉");
    workflow.setVersion(1L);

    SwadlView swadlView = SwadlView.builder()
        .swadl("id: workflow-特殊-🎉\ndescription: \"Test with émojis 🚀\"")
        .description("Description with special chars: @#$%^&*() and Unicode: 世界")
        .createdBy(999L)
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: Special characters should be preserved
    assertThat(result.getWorkflowId()).isEqualTo("workflow-特殊-🎉");
    assertThat(result.getSwadl()).contains("🚀");
    assertThat(result.getDescription()).contains("世界");
    assertThat(result.getDeploymentId()).isEqualTo("deploy-特殊");
  }

  @Test
  void apply_withLongSwadlContent_shouldHandleLargeContent() {
    // Given: SwadlView with very long swadl content
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("deploy-8");

    StringBuilder longSwadl = new StringBuilder("id: workflow-long\n");
    for (int i = 0; i < 1000; i++) {
      longSwadl.append("  - activity-").append(i).append(":\n");
      longSwadl.append("      execute: action-").append(i).append("\n");
    }

    Workflow workflow = new Workflow();
    workflow.setId("workflow-long");
    workflow.setVersion(1L);

    SwadlView swadlView = SwadlView.builder()
        .swadl(longSwadl.toString())
        .description("Long workflow")
        .createdBy(777L)
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: Long content should be handled correctly
    assertThat(result.getSwadl()).isEqualTo(longSwadl.toString());
    assertThat(result.getSwadl().length()).isGreaterThan(10000);
  }

  // ==================== apply() Tests for Edge Cases ====================

  @Test
  void apply_multipleCallsWithSameConverter_shouldProduceIndependentResults() {
    // Given: A single converter instance
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("deploy-9");

    Workflow workflow1 = new Workflow();
    workflow1.setId("workflow-1");
    workflow1.setVersion(1L);

    Workflow workflow2 = new Workflow();
    workflow2.setId("workflow-2");
    workflow2.setVersion(2L);

    SwadlView swadlView1 = SwadlView.builder()
        .swadl("swadl-1")
        .description("Description 1")
        .createdBy(100L)
        .build();

    SwadlView swadlView2 = SwadlView.builder()
        .swadl("swadl-2")
        .description("Description 2")
        .createdBy(200L)
        .build();

    // When: Converting multiple times with the same converter
    VersionedWorkflow result1 = converter.apply(workflow1, swadlView1);
    VersionedWorkflow result2 = converter.apply(workflow2, swadlView2);

    // Then: Results should be independent
    assertThat(result1.getWorkflowId()).isEqualTo("workflow-1");
    assertThat(result1.getVersion()).isEqualTo(1L);
    assertThat(result1.getSwadl()).isEqualTo("swadl-1");
    assertThat(result1.getCreatedBy()).isEqualTo(100L);

    assertThat(result2.getWorkflowId()).isEqualTo("workflow-2");
    assertThat(result2.getVersion()).isEqualTo(2L);
    assertThat(result2.getSwadl()).isEqualTo("swadl-2");
    assertThat(result2.getCreatedBy()).isEqualTo(200L);

    // Both should have the same deployment ID from the converter
    assertThat(result1.getDeploymentId()).isEqualTo("deploy-9");
    assertThat(result2.getDeploymentId()).isEqualTo("deploy-9");
  }

  @Test
  void apply_withNegativeCreatedBy_shouldHandleNegativeValue() {
    // Given: A negative createdBy value
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("deploy-10");

    Workflow workflow = new Workflow();
    workflow.setId("workflow-neg");
    workflow.setVersion(1L);

    SwadlView swadlView = SwadlView.builder()
        .swadl("id: workflow-neg")
        .createdBy(-999L)
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: Negative value should be preserved
    assertThat(result.getCreatedBy()).isEqualTo(-999L);
  }

  @Test
  void apply_withWhitespaceOnlyFields_shouldPreserveWhitespace() {
    // Given: Fields with only whitespace
    VersionedWorkflowBiConverter converter = new VersionedWorkflowBiConverter("   ");

    Workflow workflow = new Workflow();
    workflow.setId("   ");
    workflow.setVersion(1L);

    SwadlView swadlView = SwadlView.builder()
        .swadl("   ")
        .description("   ")
        .build();

    // When: Converting
    VersionedWorkflow result = converter.apply(workflow, swadlView);

    // Then: Whitespace should be preserved
    assertThat(result.getWorkflowId()).isEqualTo("   ");
    assertThat(result.getSwadl()).isEqualTo("   ");
    assertThat(result.getDescription()).isEqualTo("   ");
    assertThat(result.getDeploymentId()).isEqualTo("   ");
  }
}
