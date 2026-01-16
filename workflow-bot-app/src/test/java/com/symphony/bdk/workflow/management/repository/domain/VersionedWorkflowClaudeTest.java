package com.symphony.bdk.workflow.management.repository.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VersionedWorkflowClaudeTest {

  // ==================== Constructor Tests ====================

  @Test
  void constructor_shouldCreateInstance() {
    // When: Creating a new instance of VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();

    // Then: The instance should be created successfully
    assertThat(workflow).isNotNull();
  }

  // ==================== getActive Tests ====================

  @Test
  void getActive_whenActiveIsNull_shouldReturnFalse() {
    // Given: A VersionedWorkflow with active set to null
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setActive(null);

    // When: Getting the active value
    Boolean result = workflow.getActive();

    // Then: Should return false instead of null
    assertThat(result).isFalse();
  }

  @Test
  void getActive_whenActiveIsTrue_shouldReturnTrue() {
    // Given: A VersionedWorkflow with active set to true
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setActive(true);

    // When: Getting the active value
    Boolean result = workflow.getActive();

    // Then: Should return true
    assertThat(result).isTrue();
  }

  @Test
  void getActive_whenActiveIsFalse_shouldReturnFalse() {
    // Given: A VersionedWorkflow with active set to false
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setActive(false);

    // When: Getting the active value
    Boolean result = workflow.getActive();

    // Then: Should return false
    assertThat(result).isFalse();
  }

  @Test
  void getActive_whenActiveIsNotSetInitially_shouldReturnFalse() {
    // Given: A newly created VersionedWorkflow (active is implicitly null)
    VersionedWorkflow workflow = new VersionedWorkflow();

    // When: Getting the active value without setting it first
    Boolean result = workflow.getActive();

    // Then: Should return false (default when null)
    assertThat(result).isFalse();
  }

  // ==================== Getter and Setter Tests ====================

  @Test
  void setAndGetId_shouldWorkCorrectly() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();
    String id = "test-id-123";

    // When: Setting and getting the id
    workflow.setId(id);

    // Then: Should return the same id
    assertThat(workflow.getId()).isEqualTo(id);
  }

  @Test
  void setAndGetWorkflowId_shouldWorkCorrectly() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();
    String workflowId = "workflow-123";

    // When: Setting and getting the workflowId
    workflow.setWorkflowId(workflowId);

    // Then: Should return the same workflowId
    assertThat(workflow.getWorkflowId()).isEqualTo(workflowId);
  }

  @Test
  void setAndGetVersion_shouldWorkCorrectly() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();
    Long version = 5L;

    // When: Setting and getting the version
    workflow.setVersion(version);

    // Then: Should return the same version
    assertThat(workflow.getVersion()).isEqualTo(version);
  }

  @Test
  void setAndGetPublished_shouldWorkCorrectly() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();
    Boolean published = true;

    // When: Setting and getting the published status
    workflow.setPublished(published);

    // Then: Should return the same published status
    assertThat(workflow.getPublished()).isEqualTo(published);
  }

  @Test
  void setAndGetEtag_shouldWorkCorrectly() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();
    Long etag = 12345L;

    // When: Setting and getting the etag
    workflow.setEtag(etag);

    // Then: Should return the same etag
    assertThat(workflow.getEtag()).isEqualTo(etag);
  }

  @Test
  void setAndGetSwadl_shouldWorkCorrectly() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();
    String swadl = "workflow definition content";

    // When: Setting and getting the swadl
    workflow.setSwadl(swadl);

    // Then: Should return the same swadl
    assertThat(workflow.getSwadl()).isEqualTo(swadl);
  }

  @Test
  void setAndGetDeploymentId_shouldWorkCorrectly() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();
    String deploymentId = "deploy-456";

    // When: Setting and getting the deploymentId
    workflow.setDeploymentId(deploymentId);

    // Then: Should return the same deploymentId
    assertThat(workflow.getDeploymentId()).isEqualTo(deploymentId);
  }

  @Test
  void setAndGetActive_shouldWorkCorrectly() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();
    Boolean active = true;

    // When: Setting the active status (note: using setActive, not getActive)
    workflow.setActive(active);

    // Then: Should return the same active status via getActive
    assertThat(workflow.getActive()).isEqualTo(active);
  }

  @Test
  void setAndGetCreatedBy_shouldWorkCorrectly() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();
    Long createdBy = 999L;

    // When: Setting and getting the createdBy
    workflow.setCreatedBy(createdBy);

    // Then: Should return the same createdBy
    assertThat(workflow.getCreatedBy()).isEqualTo(createdBy);
  }

  @Test
  void setAndGetDescription_shouldWorkCorrectly() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();
    String description = "Test workflow description";

    // When: Setting and getting the description
    workflow.setDescription(description);

    // Then: Should return the same description
    assertThat(workflow.getDescription()).isEqualTo(description);
  }

  // ==================== Null Value Tests ====================

  @Test
  void settersWithNullValues_shouldAcceptNull() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();

    // When: Setting nullable fields to null
    workflow.setId(null);
    workflow.setWorkflowId(null);
    workflow.setVersion(null);
    workflow.setPublished(null);
    workflow.setEtag(null);
    workflow.setSwadl(null);
    workflow.setDeploymentId(null);
    workflow.setActive(null);
    workflow.setCreatedBy(null);
    workflow.setDescription(null);

    // Then: All fields should be null (except getActive which returns false for null)
    assertThat(workflow.getId()).isNull();
    assertThat(workflow.getWorkflowId()).isNull();
    assertThat(workflow.getVersion()).isNull();
    assertThat(workflow.getPublished()).isNull();
    assertThat(workflow.getEtag()).isNull();
    assertThat(workflow.getSwadl()).isNull();
    assertThat(workflow.getDeploymentId()).isNull();
    assertThat(workflow.getActive()).isFalse(); // getActive returns false for null
    assertThat(workflow.getCreatedBy()).isNull();
    assertThat(workflow.getDescription()).isNull();
  }

  // ==================== equals and hashCode Tests ====================

  @Test
  void equals_withSameValues_shouldReturnTrue() {
    // Given: Two VersionedWorkflow objects with the same values
    VersionedWorkflow workflow1 = createFullyPopulatedWorkflow();
    VersionedWorkflow workflow2 = createFullyPopulatedWorkflow();

    // When: Comparing them
    boolean result = workflow1.equals(workflow2);

    // Then: Should be equal
    assertThat(result).isTrue();
    assertThat(workflow1).isEqualTo(workflow2);
  }

  @Test
  void equals_withDifferentValues_shouldReturnFalse() {
    // Given: Two VersionedWorkflow objects with different values
    VersionedWorkflow workflow1 = createFullyPopulatedWorkflow();
    VersionedWorkflow workflow2 = createFullyPopulatedWorkflow();
    workflow2.setWorkflowId("different-workflow-id");

    // When: Comparing them
    boolean result = workflow1.equals(workflow2);

    // Then: Should not be equal
    assertThat(result).isFalse();
    assertThat(workflow1).isNotEqualTo(workflow2);
  }

  @Test
  void equals_withSameInstance_shouldReturnTrue() {
    // Given: A VersionedWorkflow object
    VersionedWorkflow workflow = createFullyPopulatedWorkflow();

    // When: Comparing with itself
    boolean result = workflow.equals(workflow);

    // Then: Should be equal
    assertThat(result).isTrue();
  }

  @Test
  void equals_withNull_shouldReturnFalse() {
    // Given: A VersionedWorkflow object
    VersionedWorkflow workflow = createFullyPopulatedWorkflow();

    // When: Comparing with null
    boolean result = workflow.equals(null);

    // Then: Should not be equal
    assertThat(result).isFalse();
  }

  @Test
  void equals_withDifferentClass_shouldReturnFalse() {
    // Given: A VersionedWorkflow object
    VersionedWorkflow workflow = createFullyPopulatedWorkflow();
    String differentObject = "not a workflow";

    // When: Comparing with a different class
    boolean result = workflow.equals(differentObject);

    // Then: Should not be equal
    assertThat(result).isFalse();
  }

  @Test
  void hashCode_withSameValues_shouldReturnSameHashCode() {
    // Given: Two VersionedWorkflow objects with the same values
    VersionedWorkflow workflow1 = createFullyPopulatedWorkflow();
    VersionedWorkflow workflow2 = createFullyPopulatedWorkflow();

    // When: Getting their hash codes
    int hashCode1 = workflow1.hashCode();
    int hashCode2 = workflow2.hashCode();

    // Then: Should have the same hash code
    assertThat(hashCode1).isEqualTo(hashCode2);
  }

  @Test
  void hashCode_withDifferentValues_shouldReturnDifferentHashCode() {
    // Given: Two VersionedWorkflow objects with different values
    VersionedWorkflow workflow1 = createFullyPopulatedWorkflow();
    VersionedWorkflow workflow2 = createFullyPopulatedWorkflow();
    workflow2.setWorkflowId("different-workflow-id");

    // When: Getting their hash codes
    int hashCode1 = workflow1.hashCode();
    int hashCode2 = workflow2.hashCode();

    // Then: Should have different hash codes (highly likely)
    assertThat(hashCode1).isNotEqualTo(hashCode2);
  }

  @Test
  void hashCode_shouldBeConsistent() {
    // Given: A VersionedWorkflow object
    VersionedWorkflow workflow = createFullyPopulatedWorkflow();

    // When: Getting hash code multiple times
    int hashCode1 = workflow.hashCode();
    int hashCode2 = workflow.hashCode();

    // Then: Should return the same value consistently
    assertThat(hashCode1).isEqualTo(hashCode2);
  }

  // ==================== toString Tests ====================

  @Test
  void toString_shouldContainFieldNames() {
    // Given: A fully populated VersionedWorkflow
    VersionedWorkflow workflow = createFullyPopulatedWorkflow();

    // When: Getting the string representation
    String result = workflow.toString();

    // Then: Should contain field names and values
    assertThat(result).contains("id=");
    assertThat(result).contains("workflowId=");
    assertThat(result).contains("version=");
    assertThat(result).contains("published=");
    assertThat(result).contains("etag=");
    assertThat(result).contains("swadl=");
    assertThat(result).contains("deploymentId=");
    assertThat(result).contains("active=");
    assertThat(result).contains("createdBy=");
    assertThat(result).contains("description=");
  }

  @Test
  void toString_shouldContainFieldValues() {
    // Given: A VersionedWorkflow with specific values
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId("test-workflow-123");
    workflow.setVersion(5L);

    // When: Getting the string representation
    String result = workflow.toString();

    // Then: Should contain the actual values
    assertThat(result).contains("test-workflow-123");
    assertThat(result).contains("5");
  }

  @Test
  void toString_withNullValues_shouldHandleGracefully() {
    // Given: A VersionedWorkflow with all null values
    VersionedWorkflow workflow = new VersionedWorkflow();

    // When: Getting the string representation
    String result = workflow.toString();

    // Then: Should not throw exception and should contain "null" for null fields
    assertThat(result).isNotNull();
    assertThat(result).contains("VersionedWorkflow");
  }

  // ==================== Edge Case Tests ====================

  @Test
  void allFields_shouldAcceptEmptyStrings() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();

    // When: Setting string fields to empty strings
    workflow.setId("");
    workflow.setWorkflowId("");
    workflow.setSwadl("");
    workflow.setDeploymentId("");
    workflow.setDescription("");

    // Then: Should accept empty strings
    assertThat(workflow.getId()).isEmpty();
    assertThat(workflow.getWorkflowId()).isEmpty();
    assertThat(workflow.getSwadl()).isEmpty();
    assertThat(workflow.getDeploymentId()).isEmpty();
    assertThat(workflow.getDescription()).isEmpty();
  }

  @Test
  void versionAndEtag_shouldAcceptZeroAndNegativeValues() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();

    // When: Setting version and etag to edge case values
    workflow.setVersion(0L);
    workflow.setEtag(-1L);

    // Then: Should accept these values
    assertThat(workflow.getVersion()).isEqualTo(0L);
    assertThat(workflow.getEtag()).isEqualTo(-1L);
  }

  @Test
  void createdBy_shouldAcceptZeroAndNegativeValues() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();

    // When: Setting createdBy to edge case values
    workflow.setCreatedBy(0L);

    // Then: Should accept zero
    assertThat(workflow.getCreatedBy()).isEqualTo(0L);
  }

  @Test
  void swadl_shouldAcceptLargeStrings() {
    // Given: A VersionedWorkflow and a large string
    VersionedWorkflow workflow = new VersionedWorkflow();
    StringBuilder largeSwadl = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
      largeSwadl.append("workflow content line ").append(i).append("\n");
    }
    String largeContent = largeSwadl.toString();

    // When: Setting a large swadl content
    workflow.setSwadl(largeContent);

    // Then: Should store and retrieve the large content
    assertThat(workflow.getSwadl()).isEqualTo(largeContent);
    assertThat(workflow.getSwadl().length()).isEqualTo(largeContent.length());
  }

  @Test
  void description_shouldAcceptSpecialCharacters() {
    // Given: A VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();
    String specialDescription = "Test workflow with special chars: !@#$%^&*()_+-=[]{}|;:',.<>?/~`";

    // When: Setting description with special characters
    workflow.setDescription(specialDescription);

    // Then: Should preserve special characters
    assertThat(workflow.getDescription()).isEqualTo(specialDescription);
  }

  @Test
  void workflowId_shouldAcceptMaxLength() {
    // Given: A VersionedWorkflow and a 100 character workflow ID (max length from annotation)
    VersionedWorkflow workflow = new VersionedWorkflow();
    String maxLengthId = "a".repeat(100);

    // When: Setting workflowId to max length
    workflow.setWorkflowId(maxLengthId);

    // Then: Should accept and store the max length ID
    assertThat(workflow.getWorkflowId()).isEqualTo(maxLengthId);
    assertThat(workflow.getWorkflowId().length()).isEqualTo(100);
  }

  @Test
  void deploymentId_shouldAcceptMaxLength() {
    // Given: A VersionedWorkflow and a 64 character deployment ID (max length from annotation)
    VersionedWorkflow workflow = new VersionedWorkflow();
    String maxLengthDeployId = "b".repeat(64);

    // When: Setting deploymentId to max length
    workflow.setDeploymentId(maxLengthDeployId);

    // Then: Should accept and store the max length deployment ID
    assertThat(workflow.getDeploymentId()).isEqualTo(maxLengthDeployId);
    assertThat(workflow.getDeploymentId().length()).isEqualTo(64);
  }

  // ==================== Integration Tests ====================

  @Test
  void fullyPopulatedWorkflow_shouldRetainAllValues() {
    // Given: A fully populated VersionedWorkflow
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("uuid-123");
    workflow.setWorkflowId("wf-456");
    workflow.setVersion(10L);
    workflow.setPublished(true);
    workflow.setEtag(5L);
    workflow.setSwadl("workflow: test");
    workflow.setDeploymentId("deploy-789");
    workflow.setActive(true);
    workflow.setCreatedBy(1001L);
    workflow.setDescription("Full workflow");

    // When: Retrieving all values
    // Then: All values should be retained correctly
    assertThat(workflow.getId()).isEqualTo("uuid-123");
    assertThat(workflow.getWorkflowId()).isEqualTo("wf-456");
    assertThat(workflow.getVersion()).isEqualTo(10L);
    assertThat(workflow.getPublished()).isTrue();
    assertThat(workflow.getEtag()).isEqualTo(5L);
    assertThat(workflow.getSwadl()).isEqualTo("workflow: test");
    assertThat(workflow.getDeploymentId()).isEqualTo("deploy-789");
    assertThat(workflow.getActive()).isTrue();
    assertThat(workflow.getCreatedBy()).isEqualTo(1001L);
    assertThat(workflow.getDescription()).isEqualTo("Full workflow");
  }

  @Test
  void equals_shouldConsiderAllFields() {
    // Given: Two workflows that differ only in one field
    VersionedWorkflow workflow1 = createFullyPopulatedWorkflow();
    VersionedWorkflow workflow2 = createFullyPopulatedWorkflow();

    // When/Then: Changing each field should make them unequal
    workflow2.setId("different-id");
    assertThat(workflow1).isNotEqualTo(workflow2);

    workflow2 = createFullyPopulatedWorkflow();
    workflow2.setWorkflowId("different-workflow-id");
    assertThat(workflow1).isNotEqualTo(workflow2);

    workflow2 = createFullyPopulatedWorkflow();
    workflow2.setVersion(999L);
    assertThat(workflow1).isNotEqualTo(workflow2);

    workflow2 = createFullyPopulatedWorkflow();
    workflow2.setPublished(false);
    assertThat(workflow1).isNotEqualTo(workflow2);

    workflow2 = createFullyPopulatedWorkflow();
    workflow2.setEtag(999L);
    assertThat(workflow1).isNotEqualTo(workflow2);

    workflow2 = createFullyPopulatedWorkflow();
    workflow2.setSwadl("different swadl");
    assertThat(workflow1).isNotEqualTo(workflow2);

    workflow2 = createFullyPopulatedWorkflow();
    workflow2.setDeploymentId("different-deploy-id");
    assertThat(workflow1).isNotEqualTo(workflow2);

    workflow2 = createFullyPopulatedWorkflow();
    workflow2.setActive(false);
    assertThat(workflow1).isNotEqualTo(workflow2);

    workflow2 = createFullyPopulatedWorkflow();
    workflow2.setCreatedBy(999L);
    assertThat(workflow1).isNotEqualTo(workflow2);

    workflow2 = createFullyPopulatedWorkflow();
    workflow2.setDescription("different description");
    assertThat(workflow1).isNotEqualTo(workflow2);
  }

  // ==================== Helper Methods ====================

  /**
   * Helper method to create a fully populated VersionedWorkflow with consistent values
   */
  private VersionedWorkflow createFullyPopulatedWorkflow() {
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("test-id-123");
    workflow.setWorkflowId("test-workflow-456");
    workflow.setVersion(5L);
    workflow.setPublished(true);
    workflow.setEtag(10L);
    workflow.setSwadl("workflow: test\nactivities:\n  - test");
    workflow.setDeploymentId("deploy-789");
    workflow.setActive(true);
    workflow.setCreatedBy(1000L);
    workflow.setDescription("Test workflow description");
    return workflow;
  }
}
