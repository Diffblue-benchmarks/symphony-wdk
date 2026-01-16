package com.symphony.bdk.workflow.management;

import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowManagementServiceClaude_getTest {

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

  // ==================== get(String id) Tests ====================

  @Test
  void get_withExistingActiveWorkflow_shouldReturnConvertedView() {
    // Given: An active workflow exists in the repository
    String workflowId = "test-workflow-id";
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-123");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setActive(true);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: test-workflow");
    versionedWorkflow.setDeploymentId("deployment-123");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-123")
        .workflowId(workflowId)
        .version(1L)
        .active(true)
        .published(true)
        .swadl("id: test-workflow")
        .deploymentId("deployment-123")
        .build();

    when(versionRepository.findByWorkflowIdAndActiveTrue(workflowId))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with the workflow ID
    Optional<VersionedWorkflowView> result = service.get(workflowId);

    // Then: Should return the converted view
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(expectedView);
    assertThat(result.get().getWorkflowId()).isEqualTo(workflowId);
    assertThat(result.get().getActive()).isTrue();
    verify(versionRepository, times(1)).findByWorkflowIdAndActiveTrue(workflowId);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
    verifyNoInteractions(workflowEngine);
  }

  @Test
  void get_withNonExistentWorkflow_shouldReturnEmptyOptional() {
    // Given: No workflow exists with the given ID
    String workflowId = "non-existent-workflow";

    when(versionRepository.findByWorkflowIdAndActiveTrue(workflowId))
        .thenReturn(Optional.empty());

    // When: Calling get with a non-existent workflow ID
    Optional<VersionedWorkflowView> result = service.get(workflowId);

    // Then: Should return an empty Optional
    assertThat(result).isEmpty();
    verify(versionRepository, times(1)).findByWorkflowIdAndActiveTrue(workflowId);
    verifyNoInteractions(objectConverter);
    verifyNoInteractions(workflowEngine);
  }

  @Test
  void get_withWorkflowHavingNoActiveVersion_shouldReturnEmptyOptional() {
    // Given: A workflow exists but has no active version (all versions are inactive)
    String workflowId = "inactive-workflow";

    when(versionRepository.findByWorkflowIdAndActiveTrue(workflowId))
        .thenReturn(Optional.empty());

    // When: Calling get with a workflow that has no active version
    Optional<VersionedWorkflowView> result = service.get(workflowId);

    // Then: Should return an empty Optional
    assertThat(result).isEmpty();
    verify(versionRepository, times(1)).findByWorkflowIdAndActiveTrue(workflowId);
    verifyNoInteractions(objectConverter);
  }

  @Test
  void get_withEmptyWorkflowId_shouldQueryRepositoryAndReturnEmpty() {
    // Given: An empty string as workflow ID
    String workflowId = "";

    when(versionRepository.findByWorkflowIdAndActiveTrue(workflowId))
        .thenReturn(Optional.empty());

    // When: Calling get with an empty workflow ID
    Optional<VersionedWorkflowView> result = service.get(workflowId);

    // Then: Should return an empty Optional
    assertThat(result).isEmpty();
    verify(versionRepository, times(1)).findByWorkflowIdAndActiveTrue(workflowId);
    verifyNoInteractions(objectConverter);
  }

  @Test
  void get_withWorkflowHavingMultipleVersions_shouldReturnOnlyActiveVersion() {
    // Given: A workflow with multiple versions, only one is active
    String workflowId = "multi-version-workflow";
    VersionedWorkflow activeWorkflow = new VersionedWorkflow();
    activeWorkflow.setId("uuid-active");
    activeWorkflow.setWorkflowId(workflowId);
    activeWorkflow.setVersion(3L);
    activeWorkflow.setActive(true);
    activeWorkflow.setPublished(true);
    activeWorkflow.setSwadl("id: test-workflow\nversion: 3");
    activeWorkflow.setDeploymentId("deployment-v3");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-active")
        .workflowId(workflowId)
        .version(3L)
        .active(true)
        .published(true)
        .swadl("id: test-workflow\nversion: 3")
        .deploymentId("deployment-v3")
        .build();

    when(versionRepository.findByWorkflowIdAndActiveTrue(workflowId))
        .thenReturn(Optional.of(activeWorkflow));
    when(objectConverter.convert(activeWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with a workflow that has multiple versions
    Optional<VersionedWorkflowView> result = service.get(workflowId);

    // Then: Should return only the active version (version 3)
    assertThat(result).isPresent();
    assertThat(result.get().getVersion()).isEqualTo(3L);
    assertThat(result.get().getActive()).isTrue();
    verify(versionRepository, times(1)).findByWorkflowIdAndActiveTrue(workflowId);
    verify(objectConverter, times(1)).convert(activeWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void get_withWorkflowHavingDescription_shouldIncludeDescription() {
    // Given: A workflow with a description
    String workflowId = "documented-workflow";
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-123");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setActive(true);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: test-workflow");
    versionedWorkflow.setDescription("This is a test workflow");
    versionedWorkflow.setDeploymentId("deployment-123");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-123")
        .workflowId(workflowId)
        .version(1L)
        .active(true)
        .published(true)
        .swadl("id: test-workflow")
        .description("This is a test workflow")
        .deploymentId("deployment-123")
        .build();

    when(versionRepository.findByWorkflowIdAndActiveTrue(workflowId))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with the workflow ID
    Optional<VersionedWorkflowView> result = service.get(workflowId);

    // Then: Should include the description in the result
    assertThat(result).isPresent();
    assertThat(result.get().getDescription()).isEqualTo("This is a test workflow");
    verify(versionRepository, times(1)).findByWorkflowIdAndActiveTrue(workflowId);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void get_withWorkflowHavingCreatedBy_shouldIncludeCreatedBy() {
    // Given: A workflow with a createdBy field
    String workflowId = "user-created-workflow";
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-123");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setActive(true);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: test-workflow");
    versionedWorkflow.setCreatedBy(12345L);
    versionedWorkflow.setDeploymentId("deployment-123");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-123")
        .workflowId(workflowId)
        .version(1L)
        .active(true)
        .published(true)
        .swadl("id: test-workflow")
        .createdBy(12345L)
        .deploymentId("deployment-123")
        .build();

    when(versionRepository.findByWorkflowIdAndActiveTrue(workflowId))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with the workflow ID
    Optional<VersionedWorkflowView> result = service.get(workflowId);

    // Then: Should include the createdBy field in the result
    assertThat(result).isPresent();
    assertThat(result.get().getCreatedBy()).isEqualTo(12345L);
    verify(versionRepository, times(1)).findByWorkflowIdAndActiveTrue(workflowId);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void get_withWorkflowWithComplexSwadl_shouldPreserveSwadlContent() {
    // Given: A workflow with complex SWADL content
    String workflowId = "complex-workflow";
    String complexSwadl = "id: complex-workflow\n" +
        "version: 1.0\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: sendMsg\n" +
        "      on:\n" +
        "        message-received:\n" +
        "          content: /hello";

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-complex");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setActive(true);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl(complexSwadl);
    versionedWorkflow.setDeploymentId("deployment-complex");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-complex")
        .workflowId(workflowId)
        .version(1L)
        .active(true)
        .published(true)
        .swadl(complexSwadl)
        .deploymentId("deployment-complex")
        .build();

    when(versionRepository.findByWorkflowIdAndActiveTrue(workflowId))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with the workflow ID
    Optional<VersionedWorkflowView> result = service.get(workflowId);

    // Then: Should preserve the complex SWADL content
    assertThat(result).isPresent();
    assertThat(result.get().getSwadl()).isEqualTo(complexSwadl);
    verify(versionRepository, times(1)).findByWorkflowIdAndActiveTrue(workflowId);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void get_withWorkflowIdContainingSpecialCharacters_shouldHandleCorrectly() {
    // Given: A workflow ID with special characters
    String workflowId = "workflow-with-special_chars.123";
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-special");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setActive(true);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: " + workflowId);
    versionedWorkflow.setDeploymentId("deployment-special");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-special")
        .workflowId(workflowId)
        .version(1L)
        .active(true)
        .published(true)
        .swadl("id: " + workflowId)
        .deploymentId("deployment-special")
        .build();

    when(versionRepository.findByWorkflowIdAndActiveTrue(workflowId))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with a workflow ID containing special characters
    Optional<VersionedWorkflowView> result = service.get(workflowId);

    // Then: Should handle the special characters correctly
    assertThat(result).isPresent();
    assertThat(result.get().getWorkflowId()).isEqualTo(workflowId);
    verify(versionRepository, times(1)).findByWorkflowIdAndActiveTrue(workflowId);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void get_withHighVersionNumber_shouldReturnCorrectVersion() {
    // Given: A workflow with a high version number
    String workflowId = "high-version-workflow";
    Long highVersion = 999999L;
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-high-version");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(highVersion);
    versionedWorkflow.setActive(true);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: test-workflow");
    versionedWorkflow.setDeploymentId("deployment-999999");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-high-version")
        .workflowId(workflowId)
        .version(highVersion)
        .active(true)
        .published(true)
        .swadl("id: test-workflow")
        .deploymentId("deployment-999999")
        .build();

    when(versionRepository.findByWorkflowIdAndActiveTrue(workflowId))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with the workflow ID
    Optional<VersionedWorkflowView> result = service.get(workflowId);

    // Then: Should return the workflow with the high version number
    assertThat(result).isPresent();
    assertThat(result.get().getVersion()).isEqualTo(highVersion);
    verify(versionRepository, times(1)).findByWorkflowIdAndActiveTrue(workflowId);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void get_calledMultipleTimes_shouldQueryRepositoryEachTime() {
    // Given: A workflow exists in the repository
    String workflowId = "test-workflow";
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-123");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(1L);
    versionedWorkflow.setActive(true);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: test-workflow");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-123")
        .workflowId(workflowId)
        .version(1L)
        .active(true)
        .published(true)
        .swadl("id: test-workflow")
        .build();

    when(versionRepository.findByWorkflowIdAndActiveTrue(workflowId))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get multiple times
    service.get(workflowId);
    service.get(workflowId);
    service.get(workflowId);

    // Then: Should query the repository each time (no caching)
    verify(versionRepository, times(3)).findByWorkflowIdAndActiveTrue(workflowId);
    verify(objectConverter, times(3)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  // ==================== get(String id, Long version) Tests ====================

  @Test
  void getByVersion_withExistingWorkflowVersion_shouldReturnConvertedView() {
    // Given: A specific version of a workflow exists in the repository
    String workflowId = "test-workflow-id";
    Long version = 2L;
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-456");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(version);
    versionedWorkflow.setActive(false);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: test-workflow\nversion: 2");
    versionedWorkflow.setDeploymentId("deployment-v2");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-456")
        .workflowId(workflowId)
        .version(version)
        .active(false)
        .published(true)
        .swadl("id: test-workflow\nversion: 2")
        .deploymentId("deployment-v2")
        .build();

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with workflow ID and version
    Optional<VersionedWorkflowView> result = service.get(workflowId, version);

    // Then: Should return the converted view for the specific version
    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(expectedView);
    assertThat(result.get().getWorkflowId()).isEqualTo(workflowId);
    assertThat(result.get().getVersion()).isEqualTo(version);
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
    verifyNoInteractions(workflowEngine);
  }

  @Test
  void getByVersion_withNonExistentWorkflow_shouldReturnEmptyOptional() {
    // Given: No workflow exists with the given ID and version
    String workflowId = "non-existent-workflow";
    Long version = 1L;

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.empty());

    // When: Calling get with a non-existent workflow ID and version
    Optional<VersionedWorkflowView> result = service.get(workflowId, version);

    // Then: Should return an empty Optional
    assertThat(result).isEmpty();
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verifyNoInteractions(objectConverter);
    verifyNoInteractions(workflowEngine);
  }

  @Test
  void getByVersion_withNonExistentVersion_shouldReturnEmptyOptional() {
    // Given: A workflow exists but the requested version does not exist
    String workflowId = "existing-workflow";
    Long nonExistentVersion = 999L;

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, nonExistentVersion))
        .thenReturn(Optional.empty());

    // When: Calling get with a non-existent version number
    Optional<VersionedWorkflowView> result = service.get(workflowId, nonExistentVersion);

    // Then: Should return an empty Optional
    assertThat(result).isEmpty();
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, nonExistentVersion);
    verifyNoInteractions(objectConverter);
  }

  @Test
  void getByVersion_withActiveVersion_shouldReturnActiveVersion() {
    // Given: A specific version that is currently active
    String workflowId = "active-version-workflow";
    Long version = 5L;
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-active-v5");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(version);
    versionedWorkflow.setActive(true);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: test-workflow\nversion: 5");
    versionedWorkflow.setDeploymentId("deployment-v5-active");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-active-v5")
        .workflowId(workflowId)
        .version(version)
        .active(true)
        .published(true)
        .swadl("id: test-workflow\nversion: 5")
        .deploymentId("deployment-v5-active")
        .build();

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with a version that is currently active
    Optional<VersionedWorkflowView> result = service.get(workflowId, version);

    // Then: Should return the active version
    assertThat(result).isPresent();
    assertThat(result.get().getActive()).isTrue();
    assertThat(result.get().getVersion()).isEqualTo(version);
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void getByVersion_withInactiveVersion_shouldReturnInactiveVersion() {
    // Given: A specific version that is not active
    String workflowId = "inactive-version-workflow";
    Long version = 2L;
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-inactive-v2");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(version);
    versionedWorkflow.setActive(false);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: test-workflow\nversion: 2");
    versionedWorkflow.setDeploymentId("deployment-v2-inactive");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-inactive-v2")
        .workflowId(workflowId)
        .version(version)
        .active(false)
        .published(true)
        .swadl("id: test-workflow\nversion: 2")
        .deploymentId("deployment-v2-inactive")
        .build();

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with an inactive version
    Optional<VersionedWorkflowView> result = service.get(workflowId, version);

    // Then: Should return the inactive version
    assertThat(result).isPresent();
    assertThat(result.get().getActive()).isFalse();
    assertThat(result.get().getVersion()).isEqualTo(version);
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void getByVersion_withUnpublishedVersion_shouldReturnUnpublishedVersion() {
    // Given: A specific version that is in draft mode (not published)
    String workflowId = "draft-version-workflow";
    Long version = 1L;
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-draft-v1");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(version);
    versionedWorkflow.setActive(false);
    versionedWorkflow.setPublished(false);
    versionedWorkflow.setSwadl("id: test-workflow\nversion: 1");
    versionedWorkflow.setDeploymentId(null);

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-draft-v1")
        .workflowId(workflowId)
        .version(version)
        .active(false)
        .published(false)
        .swadl("id: test-workflow\nversion: 1")
        .deploymentId(null)
        .build();

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with an unpublished version
    Optional<VersionedWorkflowView> result = service.get(workflowId, version);

    // Then: Should return the unpublished version
    assertThat(result).isPresent();
    assertThat(result.get().getPublished()).isFalse();
    assertThat(result.get().getVersion()).isEqualTo(version);
    assertThat(result.get().getDeploymentId()).isNull();
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void getByVersion_withVersionOne_shouldReturnVersionOne() {
    // Given: Version 1 of a workflow exists
    String workflowId = "first-version-workflow";
    Long version = 1L;
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-v1");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(version);
    versionedWorkflow.setActive(true);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: test-workflow\nversion: 1");
    versionedWorkflow.setDeploymentId("deployment-v1");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-v1")
        .workflowId(workflowId)
        .version(version)
        .active(true)
        .published(true)
        .swadl("id: test-workflow\nversion: 1")
        .deploymentId("deployment-v1")
        .build();

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with version 1
    Optional<VersionedWorkflowView> result = service.get(workflowId, version);

    // Then: Should return version 1
    assertThat(result).isPresent();
    assertThat(result.get().getVersion()).isEqualTo(1L);
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void getByVersion_withHighVersionNumber_shouldReturnCorrectVersion() {
    // Given: A workflow with a very high version number
    String workflowId = "high-version-workflow";
    Long highVersion = 1000000L;
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-high-v");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(highVersion);
    versionedWorkflow.setActive(true);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: test-workflow");
    versionedWorkflow.setDeploymentId("deployment-1000000");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-high-v")
        .workflowId(workflowId)
        .version(highVersion)
        .active(true)
        .published(true)
        .swadl("id: test-workflow")
        .deploymentId("deployment-1000000")
        .build();

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, highVersion))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with a high version number
    Optional<VersionedWorkflowView> result = service.get(workflowId, highVersion);

    // Then: Should return the correct high version
    assertThat(result).isPresent();
    assertThat(result.get().getVersion()).isEqualTo(highVersion);
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, highVersion);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void getByVersion_withWorkflowIdContainingSpecialCharacters_shouldHandleCorrectly() {
    // Given: A workflow ID with special characters and a specific version
    String workflowId = "workflow-with-special_chars.123";
    Long version = 3L;
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-special-v3");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(version);
    versionedWorkflow.setActive(false);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: " + workflowId);
    versionedWorkflow.setDeploymentId("deployment-special-v3");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-special-v3")
        .workflowId(workflowId)
        .version(version)
        .active(false)
        .published(true)
        .swadl("id: " + workflowId)
        .deploymentId("deployment-special-v3")
        .build();

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with a workflow ID containing special characters
    Optional<VersionedWorkflowView> result = service.get(workflowId, version);

    // Then: Should handle the special characters correctly
    assertThat(result).isPresent();
    assertThat(result.get().getWorkflowId()).isEqualTo(workflowId);
    assertThat(result.get().getVersion()).isEqualTo(version);
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void getByVersion_withWorkflowHavingDescription_shouldIncludeDescription() {
    // Given: A workflow version with a description
    String workflowId = "documented-workflow";
    Long version = 2L;
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-doc-v2");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(version);
    versionedWorkflow.setActive(false);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: test-workflow");
    versionedWorkflow.setDescription("Version 2 with new features");
    versionedWorkflow.setDeploymentId("deployment-doc-v2");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-doc-v2")
        .workflowId(workflowId)
        .version(version)
        .active(false)
        .published(true)
        .swadl("id: test-workflow")
        .description("Version 2 with new features")
        .deploymentId("deployment-doc-v2")
        .build();

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with the workflow ID and version
    Optional<VersionedWorkflowView> result = service.get(workflowId, version);

    // Then: Should include the description
    assertThat(result).isPresent();
    assertThat(result.get().getDescription()).isEqualTo("Version 2 with new features");
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void getByVersion_withWorkflowHavingCreatedBy_shouldIncludeCreatedBy() {
    // Given: A workflow version with a createdBy field
    String workflowId = "user-created-workflow";
    Long version = 1L;
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-user-v1");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(version);
    versionedWorkflow.setActive(true);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: test-workflow");
    versionedWorkflow.setCreatedBy(67890L);
    versionedWorkflow.setDeploymentId("deployment-user-v1");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-user-v1")
        .workflowId(workflowId)
        .version(version)
        .active(true)
        .published(true)
        .swadl("id: test-workflow")
        .createdBy(67890L)
        .deploymentId("deployment-user-v1")
        .build();

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with the workflow ID and version
    Optional<VersionedWorkflowView> result = service.get(workflowId, version);

    // Then: Should include the createdBy field
    assertThat(result).isPresent();
    assertThat(result.get().getCreatedBy()).isEqualTo(67890L);
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void getByVersion_withComplexSwadl_shouldPreserveSwadlContent() {
    // Given: A workflow version with complex SWADL content
    String workflowId = "complex-workflow";
    Long version = 4L;
    String complexSwadl = "id: complex-workflow\n" +
        "version: 4.0\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: sendMsg\n" +
        "      on:\n" +
        "        message-received:\n" +
        "          content: /hello\n" +
        "  - create-room:\n" +
        "      id: createRoom\n" +
        "      description: Test room";

    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-complex-v4");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(version);
    versionedWorkflow.setActive(false);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl(complexSwadl);
    versionedWorkflow.setDeploymentId("deployment-complex-v4");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-complex-v4")
        .workflowId(workflowId)
        .version(version)
        .active(false)
        .published(true)
        .swadl(complexSwadl)
        .deploymentId("deployment-complex-v4")
        .build();

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get with the workflow ID and version
    Optional<VersionedWorkflowView> result = service.get(workflowId, version);

    // Then: Should preserve the complex SWADL content
    assertThat(result).isPresent();
    assertThat(result.get().getSwadl()).isEqualTo(complexSwadl);
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verify(objectConverter, times(1)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void getByVersion_withEmptyWorkflowId_shouldQueryRepositoryAndReturnEmpty() {
    // Given: An empty string as workflow ID
    String workflowId = "";
    Long version = 1L;

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.empty());

    // When: Calling get with an empty workflow ID
    Optional<VersionedWorkflowView> result = service.get(workflowId, version);

    // Then: Should return an empty Optional
    assertThat(result).isEmpty();
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version);
    verifyNoInteractions(objectConverter);
  }

  @Test
  void getByVersion_calledMultipleTimes_shouldQueryRepositoryEachTime() {
    // Given: A specific workflow version exists in the repository
    String workflowId = "test-workflow";
    Long version = 3L;
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setId("uuid-v3");
    versionedWorkflow.setWorkflowId(workflowId);
    versionedWorkflow.setVersion(version);
    versionedWorkflow.setActive(false);
    versionedWorkflow.setPublished(true);
    versionedWorkflow.setSwadl("id: test-workflow");

    VersionedWorkflowView expectedView = VersionedWorkflowView.builder()
        .id("uuid-v3")
        .workflowId(workflowId)
        .version(version)
        .active(false)
        .published(true)
        .swadl("id: test-workflow")
        .build();

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class))
        .thenReturn(expectedView);

    // When: Calling get multiple times with the same ID and version
    service.get(workflowId, version);
    service.get(workflowId, version);
    service.get(workflowId, version);

    // Then: Should query the repository each time (no caching)
    verify(versionRepository, times(3)).findByWorkflowIdAndVersion(workflowId, version);
    verify(objectConverter, times(3)).convert(versionedWorkflow, VersionedWorkflowView.class);
  }

  @Test
  void getByVersion_withDifferentVersions_shouldReturnDifferentVersions() {
    // Given: Multiple versions of the same workflow exist
    String workflowId = "multi-version-workflow";
    Long version1 = 1L;
    Long version2 = 2L;

    VersionedWorkflow versionedWorkflow1 = new VersionedWorkflow();
    versionedWorkflow1.setId("uuid-v1");
    versionedWorkflow1.setWorkflowId(workflowId);
    versionedWorkflow1.setVersion(version1);
    versionedWorkflow1.setActive(false);
    versionedWorkflow1.setPublished(true);
    versionedWorkflow1.setSwadl("id: test-workflow\nversion: 1");

    VersionedWorkflow versionedWorkflow2 = new VersionedWorkflow();
    versionedWorkflow2.setId("uuid-v2");
    versionedWorkflow2.setWorkflowId(workflowId);
    versionedWorkflow2.setVersion(version2);
    versionedWorkflow2.setActive(true);
    versionedWorkflow2.setPublished(true);
    versionedWorkflow2.setSwadl("id: test-workflow\nversion: 2");

    VersionedWorkflowView expectedView1 = VersionedWorkflowView.builder()
        .id("uuid-v1")
        .workflowId(workflowId)
        .version(version1)
        .active(false)
        .published(true)
        .swadl("id: test-workflow\nversion: 1")
        .build();

    VersionedWorkflowView expectedView2 = VersionedWorkflowView.builder()
        .id("uuid-v2")
        .workflowId(workflowId)
        .version(version2)
        .active(true)
        .published(true)
        .swadl("id: test-workflow\nversion: 2")
        .build();

    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version1))
        .thenReturn(Optional.of(versionedWorkflow1));
    when(versionRepository.findByWorkflowIdAndVersion(workflowId, version2))
        .thenReturn(Optional.of(versionedWorkflow2));
    when(objectConverter.convert(versionedWorkflow1, VersionedWorkflowView.class))
        .thenReturn(expectedView1);
    when(objectConverter.convert(versionedWorkflow2, VersionedWorkflowView.class))
        .thenReturn(expectedView2);

    // When: Calling get with different versions
    Optional<VersionedWorkflowView> result1 = service.get(workflowId, version1);
    Optional<VersionedWorkflowView> result2 = service.get(workflowId, version2);

    // Then: Should return the correct version for each call
    assertThat(result1).isPresent();
    assertThat(result1.get().getVersion()).isEqualTo(version1);
    assertThat(result1.get().getActive()).isFalse();

    assertThat(result2).isPresent();
    assertThat(result2.get().getVersion()).isEqualTo(version2);
    assertThat(result2.get().getActive()).isTrue();

    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version1);
    verify(versionRepository, times(1)).findByWorkflowIdAndVersion(workflowId, version2);
    verify(objectConverter, times(1)).convert(versionedWorkflow1, VersionedWorkflowView.class);
    verify(objectConverter, times(1)).convert(versionedWorkflow2, VersionedWorkflowView.class);
  }
}
