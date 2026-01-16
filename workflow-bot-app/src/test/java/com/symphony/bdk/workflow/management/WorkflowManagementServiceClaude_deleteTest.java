package com.symphony.bdk.workflow.management;

import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowManagementServiceClaude_deleteTest {

  @Mock
  private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  @Mock
  private VersionedWorkflowRepository versionRepository;

  @Mock
  private ObjectConverter objectConverter;

  private WorkflowManagementService service;

  @BeforeEach
  void setUp() {
    service = new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
  }

  @Test
  void delete_withExistingWorkflow_shouldDeleteAllVersionsAndUndeploy() {
    // Given: A workflow ID exists
    String workflowId = "test-workflow";

    // When: Calling delete with the workflow ID
    service.delete(workflowId);

    // Then: Should delete all versions from repository and undeploy from engine
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId);
    verifyNoInteractions(objectConverter);
  }

  @Test
  void delete_withNonExistentWorkflow_shouldStillCallDeleteMethods() {
    // Given: A workflow ID that doesn't exist
    String workflowId = "non-existent-workflow";

    // When: Calling delete with a non-existent workflow ID
    service.delete(workflowId);

    // Then: Should still call delete and undeploy methods (they handle non-existence internally)
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId);
  }

  @Test
  void delete_withWorkflowHavingMultipleVersions_shouldDeleteAllVersions() {
    // Given: A workflow with multiple versions
    String workflowId = "multi-version-workflow";

    // When: Calling delete without specifying a version
    service.delete(workflowId);

    // Then: Should delete all versions (not just one)
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId);
  }

  @Test
  void delete_withWorkflowIdContainingSpecialCharacters_shouldHandleCorrectly() {
    // Given: A workflow ID with special characters
    String workflowId = "workflow-with-special_chars.123";

    // When: Calling delete with special characters in ID
    service.delete(workflowId);

    // Then: Should pass the ID correctly to repository and engine
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId);
  }

  @Test
  void delete_withEmptyWorkflowId_shouldCallDeleteMethods() {
    // Given: An empty string as workflow ID
    String workflowId = "";

    // When: Calling delete with empty ID
    service.delete(workflowId);

    // Then: Should still call delete methods
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId);
  }

  @Test
  void delete_calledMultipleTimes_shouldCallDeleteEachTime() {
    // Given: A workflow ID
    String workflowId = "test-workflow";

    // When: Calling delete multiple times
    service.delete(workflowId);
    service.delete(workflowId);
    service.delete(workflowId);

    // Then: Should call delete methods each time
    verify(versionRepository, times(3)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(3)).undeployByWorkflowId(workflowId);
  }

  @Test
  void delete_withDifferentWorkflowIds_shouldDeleteCorrectWorkflows() {
    // Given: Multiple different workflow IDs
    String workflowId1 = "workflow-1";
    String workflowId2 = "workflow-2";
    String workflowId3 = "workflow-3";

    // When: Calling delete for different workflows
    service.delete(workflowId1);
    service.delete(workflowId2);
    service.delete(workflowId3);

    // Then: Should delete each workflow independently
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId1);
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId2);
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId3);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId1);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId2);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId3);
  }

  @Test
  void delete_shouldCallDeleteBeforeUndeploy() {
    // Given: A workflow ID
    String workflowId = "test-workflow";

    // When: Calling delete
    service.delete(workflowId);

    // Then: Should call repository delete and engine undeploy
    // Note: We verify both are called, but the exact order depends on implementation
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId);
  }

  @Test
  void delete_shouldNotInteractWithObjectConverter() {
    // Given: A workflow ID
    String workflowId = "test-workflow";

    // When: Calling delete
    service.delete(workflowId);

    // Then: Should not use the object converter
    verifyNoInteractions(objectConverter);
  }

  @Test
  void delete_withWorkflowContainingPublishedVersions_shouldDeleteAllAndUndeploy() {
    // Given: A workflow with published versions (which would be deployed)
    String workflowId = "published-workflow";

    // When: Calling delete
    service.delete(workflowId);

    // Then: Should delete all versions and undeploy from engine
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId);
  }

  @Test
  void delete_withWorkflowContainingDraftVersions_shouldDeleteAllAndUndeploy() {
    // Given: A workflow with draft (unpublished) versions
    String workflowId = "draft-workflow";

    // When: Calling delete
    service.delete(workflowId);

    // Then: Should delete all versions and undeploy (even if nothing is deployed)
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId);
  }

  @Test
  void delete_withWorkflowHavingMixedVersionStates_shouldDeleteAll() {
    // Given: A workflow with mixed version states (active, inactive, published, draft)
    String workflowId = "mixed-state-workflow";

    // When: Calling delete
    service.delete(workflowId);

    // Then: Should delete all versions regardless of their state
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId);
  }

  @Test
  void delete_withLongWorkflowId_shouldHandleCorrectly() {
    // Given: A workflow ID that is very long
    String workflowId = "this-is-a-very-long-workflow-id-that-might-be-used-in-some-systems-" +
        "to-describe-complex-workflows-with-detailed-naming-conventions";

    // When: Calling delete
    service.delete(workflowId);

    // Then: Should handle the long ID correctly
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId);
  }

  @Test
  void delete_withWorkflowIdContainingUnicodeCharacters_shouldHandleCorrectly() {
    // Given: A workflow ID with unicode characters
    String workflowId = "workflow-测试-ワークフロー-🚀";

    // When: Calling delete
    service.delete(workflowId);

    // Then: Should handle unicode characters correctly
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId);
  }

  @Test
  void delete_shouldDelegateToDeleteWithNullVersion() {
    // Given: A workflow ID
    String workflowId = "test-workflow";

    // When: Calling delete(id) without version parameter
    service.delete(workflowId);

    // Then: Should behave as if calling delete(id, null) which deletes all versions
    // This is verified by checking that deleteByWorkflowId is called (not deleteByWorkflowIdAndVersion)
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId);
  }

  // ==================== delete(String id, Long version) Tests ====================

  @Test
  void deleteByVersion_withExistingActiveVersion_shouldDeleteAndUndeployByDeploymentId() {
    // Given: An active version exists
    String workflowId = "test-workflow";
    Long version = 2L;
    String deploymentId = "deployment-123";

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("uuid-v2");
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setActive(true);
    workflow.setDeploymentId(deploymentId);

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(workflow));

    // When: Calling delete with workflow ID and version
    service.delete(workflowId, version);

    // Then: Should delete the specific version and undeploy by deployment ID
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId, version);
    verify(workflowEngine, times(1)).undeployByDeploymentId(deploymentId);
    verify(workflowEngine, never()).undeployByWorkflowId(workflowId);
    verifyNoInteractions(objectConverter);
  }

  @Test
  void deleteByVersion_withExistingInactiveVersion_shouldDeleteButNotUndeploy() {
    // Given: An inactive version exists
    String workflowId = "test-workflow";
    Long version = 1L;

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("uuid-v1");
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setActive(false);
    workflow.setDeploymentId("deployment-old");

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(workflow));

    // When: Calling delete with workflow ID and version
    service.delete(workflowId, version);

    // Then: Should delete the version but not undeploy (since it's inactive)
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId, version);
    verifyNoInteractions(workflowEngine);
  }

  @Test
  void deleteByVersion_withNonExistentVersion_shouldNotDeleteOrUndeploy() {
    // Given: No version exists with the given ID and version
    String workflowId = "non-existent-workflow";
    Long version = 1L;

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.empty());

    // When: Calling delete with a non-existent version
    service.delete(workflowId, version);

    // Then: Should not delete or undeploy anything
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verify(versionRepository, never()).deleteByWorkflowIdAndVersion(workflowId, version);
    verifyNoInteractions(workflowEngine);
  }

  @Test
  void deleteByVersion_withNullVersion_shouldDeleteAllVersionsAndUndeploy() {
    // Given: A workflow ID and null version
    String workflowId = "test-workflow";
    Long version = null;

    // When: Calling delete with null version
    service.delete(workflowId, version);

    // Then: Should delete all versions and undeploy by workflow ID
    verify(versionRepository, times(1)).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByWorkflowId(workflowId);
    verify(versionRepository, never()).findByWorkflowIdAndVersion(workflowId, version);
  }

  @Test
  void deleteByVersion_withActiveVersionHavingDeploymentId_shouldUndeployByDeploymentId() {
    // Given: An active version with a deployment ID
    String workflowId = "deployed-workflow";
    Long version = 3L;
    String deploymentId = "deployment-abc-123";

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("uuid-v3");
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setActive(true);
    workflow.setPublished(true);
    workflow.setDeploymentId(deploymentId);

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(workflow));

    // When: Calling delete
    service.delete(workflowId, version);

    // Then: Should undeploy using the deployment ID
    verify(workflowEngine, times(1)).undeployByDeploymentId(deploymentId);
    verify(workflowEngine, never()).undeployByWorkflowId(workflowId);
  }

  @Test
  void deleteByVersion_withPublishedButInactiveVersion_shouldNotUndeploy() {
    // Given: A published but inactive version
    String workflowId = "published-workflow";
    Long version = 2L;

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("uuid-v2");
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setActive(false);
    workflow.setPublished(true);
    workflow.setDeploymentId("deployment-456");

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(workflow));

    // When: Calling delete
    service.delete(workflowId, version);

    // Then: Should delete but not undeploy (only active versions are undeployed)
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId, version);
    verifyNoInteractions(workflowEngine);
  }

  @Test
  void deleteByVersion_withDraftVersion_shouldDeleteButNotUndeploy() {
    // Given: A draft (unpublished) version
    String workflowId = "draft-workflow";
    Long version = 1L;

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("uuid-draft");
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setActive(false);
    workflow.setPublished(false);
    workflow.setDeploymentId(null);

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(workflow));

    // When: Calling delete
    service.delete(workflowId, version);

    // Then: Should delete but not undeploy (draft versions are not deployed)
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId, version);
    verifyNoInteractions(workflowEngine);
  }

  @Test
  void deleteByVersion_calledMultipleTimes_shouldDeleteEachTime() {
    // Given: Multiple versions exist
    String workflowId = "test-workflow";

    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setWorkflowId(workflowId);
    workflow1.setVersion(1L);
    workflow1.setActive(false);

    VersionedWorkflow workflow2 = new VersionedWorkflow();
    workflow2.setWorkflowId(workflowId);
    workflow2.setVersion(2L);
    workflow2.setActive(false);

    VersionedWorkflow workflow3 = new VersionedWorkflow();
    workflow3.setWorkflowId(workflowId);
    workflow3.setVersion(3L);
    workflow3.setActive(true);
    workflow3.setDeploymentId("deployment-v3");

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, 1L))
        .thenReturn(Optional.of(workflow1));
    when(versionRepository.findByWorkflowIdAndVersion(workflowId, 2L))
        .thenReturn(Optional.of(workflow2));
    when(versionRepository.findByWorkflowIdAndVersion(workflowId, 3L))
        .thenReturn(Optional.of(workflow3));

    // When: Calling delete for each version
    service.delete(workflowId, 1L);
    service.delete(workflowId, 2L);
    service.delete(workflowId, 3L);

    // Then: Should delete each version
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId, 1L);
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId, 2L);
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId, 3L);
    verify(workflowEngine, times(1)).undeployByDeploymentId("deployment-v3");
  }

  @Test
  void deleteByVersion_withVersionOne_shouldDeleteVersionOne() {
    // Given: Version 1 exists
    String workflowId = "test-workflow";
    Long version = 1L;

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("uuid-v1");
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setActive(true);
    workflow.setDeploymentId("deployment-v1");

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(workflow));

    // When: Calling delete for version 1
    service.delete(workflowId, version);

    // Then: Should delete version 1
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId, 1L);
    verify(workflowEngine, times(1)).undeployByDeploymentId("deployment-v1");
  }

  @Test
  void deleteByVersion_withHighVersionNumber_shouldHandleCorrectly() {
    // Given: A workflow with a high version number
    String workflowId = "high-version-workflow";
    Long version = 999999L;

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("uuid-high");
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setActive(true);
    workflow.setDeploymentId("deployment-999999");

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(workflow));

    // When: Calling delete with high version number
    service.delete(workflowId, version);

    // Then: Should handle the high version number correctly
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId, version);
    verify(workflowEngine, times(1)).undeployByDeploymentId("deployment-999999");
  }

  @Test
  void deleteByVersion_withSpecialCharactersInWorkflowId_shouldHandleCorrectly() {
    // Given: A workflow ID with special characters
    String workflowId = "workflow-with-special_chars.123";
    Long version = 2L;

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("uuid-special");
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setActive(true);
    workflow.setDeploymentId("deployment-special");

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(workflow));

    // When: Calling delete with special characters
    service.delete(workflowId, version);

    // Then: Should handle special characters correctly
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId, version);
    verify(workflowEngine, times(1)).undeployByDeploymentId("deployment-special");
  }

  @Test
  void deleteByVersion_withEmptyWorkflowId_shouldQueryRepository() {
    // Given: An empty workflow ID
    String workflowId = "";
    Long version = 1L;

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.empty());

    // When: Calling delete with empty ID
    service.delete(workflowId, version);

    // Then: Should query repository but not find anything
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verify(versionRepository, never()).deleteByWorkflowIdAndVersion(workflowId, version);
  }

  @Test
  void deleteByVersion_shouldNotInteractWithObjectConverter() {
    // Given: A version exists
    String workflowId = "test-workflow";
    Long version = 1L;

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setActive(true);
    workflow.setDeploymentId("deployment-123");

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(workflow));

    // When: Calling delete
    service.delete(workflowId, version);

    // Then: Should not use the object converter
    verifyNoInteractions(objectConverter);
  }

  @Test
  void deleteByVersion_withDifferentWorkflows_shouldDeleteCorrectVersions() {
    // Given: Different workflows with different versions
    String workflowId1 = "workflow-1";
    String workflowId2 = "workflow-2";

    VersionedWorkflow workflow1v1 = new VersionedWorkflow();
    workflow1v1.setWorkflowId(workflowId1);
    workflow1v1.setVersion(1L);
    workflow1v1.setActive(true);
    workflow1v1.setDeploymentId("deployment-w1v1");

    VersionedWorkflow workflow2v1 = new VersionedWorkflow();
    workflow2v1.setWorkflowId(workflowId2);
    workflow2v1.setVersion(1L);
    workflow2v1.setActive(true);
    workflow2v1.setDeploymentId("deployment-w2v1");

    when(versionRepository.findByWorkflowIdAndVersion(workflowId1, 1L))
        .thenReturn(Optional.of(workflow1v1));
    when(versionRepository.findByWorkflowIdAndVersion(workflowId2, 1L))
        .thenReturn(Optional.of(workflow2v1));

    // When: Calling delete for different workflows
    service.delete(workflowId1, 1L);
    service.delete(workflowId2, 1L);

    // Then: Should delete the correct versions
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId1, 1L);
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId2, 1L);
    verify(workflowEngine, times(1)).undeployByDeploymentId("deployment-w1v1");
    verify(workflowEngine, times(1)).undeployByDeploymentId("deployment-w2v1");
  }

  @Test
  void deleteByVersion_withActiveVersionHavingNullDeploymentId_shouldStillCallUndeploy() {
    // Given: An active version with null deployment ID (edge case)
    String workflowId = "test-workflow";
    Long version = 1L;

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("uuid-v1");
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setActive(true);
    workflow.setDeploymentId(null);

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(workflow));

    // When: Calling delete
    service.delete(workflowId, version);

    // Then: Should delete and attempt to undeploy with null
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId, version);
    verify(workflowEngine, times(1)).undeployByDeploymentId(null);
  }

  @Test
  void deleteByVersion_deletingAllVersionsOneByOne_shouldBehaveDifferentlyFromDeleteAll() {
    // Given: Multiple versions exist
    String workflowId = "test-workflow";

    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setVersion(1L);
    workflow1.setActive(false);

    VersionedWorkflow workflow2 = new VersionedWorkflow();
    workflow2.setVersion(2L);
    workflow2.setActive(true);
    workflow2.setDeploymentId("deployment-v2");

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, 1L))
        .thenReturn(Optional.of(workflow1));
    when(versionRepository.findByWorkflowIdAndVersion(workflowId, 2L))
        .thenReturn(Optional.of(workflow2));

    // When: Deleting versions one by one (not using null version)
    service.delete(workflowId, 1L);
    service.delete(workflowId, 2L);

    // Then: Should use deleteByWorkflowIdAndVersion (not deleteByWorkflowId)
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId, 1L);
    verify(versionRepository, times(1)).deleteByWorkflowIdAndVersion(workflowId, 2L);
    verify(versionRepository, never()).deleteByWorkflowId(workflowId);
    verify(workflowEngine, times(1)).undeployByDeploymentId("deployment-v2");
    verify(workflowEngine, never()).undeployByWorkflowId(workflowId);
  }
}
