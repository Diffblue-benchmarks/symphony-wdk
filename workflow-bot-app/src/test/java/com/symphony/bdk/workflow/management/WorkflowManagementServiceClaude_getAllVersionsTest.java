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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowManagementServiceClaude_getAllVersionsTest {

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
  void getAllVersions_withWorkflowHavingMultipleVersions_shouldReturnAllVersions() {
    // Given: A workflow with multiple versions exists
    String workflowId = "multi-version-workflow";

    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setId("uuid-v1");
    workflow1.setWorkflowId(workflowId);
    workflow1.setVersion(1L);
    workflow1.setActive(false);
    workflow1.setPublished(true);
    workflow1.setSwadl("id: test-workflow\nversion: 1");

    VersionedWorkflow workflow2 = new VersionedWorkflow();
    workflow2.setId("uuid-v2");
    workflow2.setWorkflowId(workflowId);
    workflow2.setVersion(2L);
    workflow2.setActive(false);
    workflow2.setPublished(true);
    workflow2.setSwadl("id: test-workflow\nversion: 2");

    VersionedWorkflow workflow3 = new VersionedWorkflow();
    workflow3.setId("uuid-v3");
    workflow3.setWorkflowId(workflowId);
    workflow3.setVersion(3L);
    workflow3.setActive(true);
    workflow3.setPublished(true);
    workflow3.setSwadl("id: test-workflow\nversion: 3");

    List<VersionedWorkflow> workflows = Arrays.asList(workflow1, workflow2, workflow3);

    VersionedWorkflowView view1 = VersionedWorkflowView.builder()
        .id("uuid-v1")
        .workflowId(workflowId)
        .version(1L)
        .active(false)
        .published(true)
        .swadl("id: test-workflow\nversion: 1")
        .build();

    VersionedWorkflowView view2 = VersionedWorkflowView.builder()
        .id("uuid-v2")
        .workflowId(workflowId)
        .version(2L)
        .active(false)
        .published(true)
        .swadl("id: test-workflow\nversion: 2")
        .build();

    VersionedWorkflowView view3 = VersionedWorkflowView.builder()
        .id("uuid-v3")
        .workflowId(workflowId)
        .version(3L)
        .active(true)
        .published(true)
        .swadl("id: test-workflow\nversion: 3")
        .build();

    List<VersionedWorkflowView> expectedViews = Arrays.asList(view1, view2, view3);

    when(versionRepository.findByWorkflowId(workflowId)).thenReturn(workflows);
    when(objectConverter.convertCollection(workflows, VersionedWorkflowView.class))
        .thenReturn(expectedViews);

    // When: Calling getAllVersions with the workflow ID
    List<VersionedWorkflowView> result = service.getAllVersions(workflowId);

    // Then: Should return all three versions
    assertThat(result).isNotNull();
    assertThat(result).hasSize(3);
    assertThat(result).containsExactlyInAnyOrder(view1, view2, view3);
    verify(versionRepository, times(1)).findByWorkflowId(workflowId);
    verify(objectConverter, times(1)).convertCollection(workflows, VersionedWorkflowView.class);
    verifyNoInteractions(workflowEngine);
  }

  @Test
  void getAllVersions_withWorkflowHavingSingleVersion_shouldReturnSingleVersion() {
    // Given: A workflow with only one version exists
    String workflowId = "single-version-workflow";

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("uuid-v1");
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(1L);
    workflow.setActive(true);
    workflow.setPublished(true);
    workflow.setSwadl("id: test-workflow");

    List<VersionedWorkflow> workflows = Collections.singletonList(workflow);

    VersionedWorkflowView view = VersionedWorkflowView.builder()
        .id("uuid-v1")
        .workflowId(workflowId)
        .version(1L)
        .active(true)
        .published(true)
        .swadl("id: test-workflow")
        .build();

    List<VersionedWorkflowView> expectedViews = Collections.singletonList(view);

    when(versionRepository.findByWorkflowId(workflowId)).thenReturn(workflows);
    when(objectConverter.convertCollection(workflows, VersionedWorkflowView.class))
        .thenReturn(expectedViews);

    // When: Calling getAllVersions with the workflow ID
    List<VersionedWorkflowView> result = service.getAllVersions(workflowId);

    // Then: Should return the single version
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEqualTo(view);
    assertThat(result.get(0).getVersion()).isEqualTo(1L);
    verify(versionRepository, times(1)).findByWorkflowId(workflowId);
    verify(objectConverter, times(1)).convertCollection(workflows, VersionedWorkflowView.class);
  }

  @Test
  void getAllVersions_withNonExistentWorkflow_shouldReturnEmptyList() {
    // Given: No workflow exists with the given ID
    String workflowId = "non-existent-workflow";

    when(versionRepository.findByWorkflowId(workflowId)).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(Collections.emptyList(), VersionedWorkflowView.class))
        .thenReturn(Collections.emptyList());

    // When: Calling getAllVersions with a non-existent workflow ID
    List<VersionedWorkflowView> result = service.getAllVersions(workflowId);

    // Then: Should return an empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
    verify(versionRepository, times(1)).findByWorkflowId(workflowId);
    verify(objectConverter, times(1)).convertCollection(Collections.emptyList(), VersionedWorkflowView.class);
  }

  @Test
  void getAllVersions_withMixedActiveAndInactiveVersions_shouldReturnAllVersions() {
    // Given: A workflow with both active and inactive versions
    String workflowId = "mixed-status-workflow";

    VersionedWorkflow inactiveVersion1 = new VersionedWorkflow();
    inactiveVersion1.setId("uuid-v1");
    inactiveVersion1.setWorkflowId(workflowId);
    inactiveVersion1.setVersion(1L);
    inactiveVersion1.setActive(false);
    inactiveVersion1.setPublished(true);

    VersionedWorkflow inactiveVersion2 = new VersionedWorkflow();
    inactiveVersion2.setId("uuid-v2");
    inactiveVersion2.setWorkflowId(workflowId);
    inactiveVersion2.setVersion(2L);
    inactiveVersion2.setActive(false);
    inactiveVersion2.setPublished(true);

    VersionedWorkflow activeVersion = new VersionedWorkflow();
    activeVersion.setId("uuid-v3");
    activeVersion.setWorkflowId(workflowId);
    activeVersion.setVersion(3L);
    activeVersion.setActive(true);
    activeVersion.setPublished(true);

    List<VersionedWorkflow> workflows = Arrays.asList(inactiveVersion1, inactiveVersion2, activeVersion);

    VersionedWorkflowView view1 = VersionedWorkflowView.builder()
        .id("uuid-v1")
        .workflowId(workflowId)
        .version(1L)
        .active(false)
        .published(true)
        .build();

    VersionedWorkflowView view2 = VersionedWorkflowView.builder()
        .id("uuid-v2")
        .workflowId(workflowId)
        .version(2L)
        .active(false)
        .published(true)
        .build();

    VersionedWorkflowView view3 = VersionedWorkflowView.builder()
        .id("uuid-v3")
        .workflowId(workflowId)
        .version(3L)
        .active(true)
        .published(true)
        .build();

    List<VersionedWorkflowView> expectedViews = Arrays.asList(view1, view2, view3);

    when(versionRepository.findByWorkflowId(workflowId)).thenReturn(workflows);
    when(objectConverter.convertCollection(workflows, VersionedWorkflowView.class))
        .thenReturn(expectedViews);

    // When: Calling getAllVersions
    List<VersionedWorkflowView> result = service.getAllVersions(workflowId);

    // Then: Should return all versions including both active and inactive
    assertThat(result).hasSize(3);
    assertThat(result.stream().filter(v -> v.getActive()).count()).isEqualTo(1);
    assertThat(result.stream().filter(v -> !v.getActive()).count()).isEqualTo(2);
    verify(versionRepository, times(1)).findByWorkflowId(workflowId);
    verify(objectConverter, times(1)).convertCollection(workflows, VersionedWorkflowView.class);
  }

  @Test
  void getAllVersions_withPublishedAndUnpublishedVersions_shouldReturnAllVersions() {
    // Given: A workflow with both published and unpublished versions
    String workflowId = "mixed-published-workflow";

    VersionedWorkflow publishedVersion1 = new VersionedWorkflow();
    publishedVersion1.setId("uuid-v1");
    publishedVersion1.setWorkflowId(workflowId);
    publishedVersion1.setVersion(1L);
    publishedVersion1.setActive(false);
    publishedVersion1.setPublished(true);

    VersionedWorkflow publishedVersion2 = new VersionedWorkflow();
    publishedVersion2.setId("uuid-v2");
    publishedVersion2.setWorkflowId(workflowId);
    publishedVersion2.setVersion(2L);
    publishedVersion2.setActive(true);
    publishedVersion2.setPublished(true);

    VersionedWorkflow draftVersion = new VersionedWorkflow();
    draftVersion.setId("uuid-v3");
    draftVersion.setWorkflowId(workflowId);
    draftVersion.setVersion(3L);
    draftVersion.setActive(false);
    draftVersion.setPublished(false);

    List<VersionedWorkflow> workflows = Arrays.asList(publishedVersion1, publishedVersion2, draftVersion);

    VersionedWorkflowView view1 = VersionedWorkflowView.builder()
        .id("uuid-v1")
        .workflowId(workflowId)
        .version(1L)
        .active(false)
        .published(true)
        .build();

    VersionedWorkflowView view2 = VersionedWorkflowView.builder()
        .id("uuid-v2")
        .workflowId(workflowId)
        .version(2L)
        .active(true)
        .published(true)
        .build();

    VersionedWorkflowView view3 = VersionedWorkflowView.builder()
        .id("uuid-v3")
        .workflowId(workflowId)
        .version(3L)
        .active(false)
        .published(false)
        .build();

    List<VersionedWorkflowView> expectedViews = Arrays.asList(view1, view2, view3);

    when(versionRepository.findByWorkflowId(workflowId)).thenReturn(workflows);
    when(objectConverter.convertCollection(workflows, VersionedWorkflowView.class))
        .thenReturn(expectedViews);

    // When: Calling getAllVersions
    List<VersionedWorkflowView> result = service.getAllVersions(workflowId);

    // Then: Should return all versions including draft
    assertThat(result).hasSize(3);
    assertThat(result.stream().filter(v -> v.getPublished()).count()).isEqualTo(2);
    assertThat(result.stream().filter(v -> !v.getPublished()).count()).isEqualTo(1);
    verify(versionRepository, times(1)).findByWorkflowId(workflowId);
    verify(objectConverter, times(1)).convertCollection(workflows, VersionedWorkflowView.class);
  }

  @Test
  void getAllVersions_withManyVersions_shouldReturnAllVersions() {
    // Given: A workflow with many versions
    String workflowId = "many-versions-workflow";

    List<VersionedWorkflow> workflows = Arrays.asList(
        createVersionedWorkflow("uuid-v1", workflowId, 1L, false),
        createVersionedWorkflow("uuid-v2", workflowId, 2L, false),
        createVersionedWorkflow("uuid-v3", workflowId, 3L, false),
        createVersionedWorkflow("uuid-v4", workflowId, 4L, false),
        createVersionedWorkflow("uuid-v5", workflowId, 5L, false),
        createVersionedWorkflow("uuid-v6", workflowId, 6L, false),
        createVersionedWorkflow("uuid-v7", workflowId, 7L, false),
        createVersionedWorkflow("uuid-v8", workflowId, 8L, false),
        createVersionedWorkflow("uuid-v9", workflowId, 9L, false),
        createVersionedWorkflow("uuid-v10", workflowId, 10L, true)
    );

    List<VersionedWorkflowView> expectedViews = Arrays.asList(
        createVersionedWorkflowView("uuid-v1", workflowId, 1L, false),
        createVersionedWorkflowView("uuid-v2", workflowId, 2L, false),
        createVersionedWorkflowView("uuid-v3", workflowId, 3L, false),
        createVersionedWorkflowView("uuid-v4", workflowId, 4L, false),
        createVersionedWorkflowView("uuid-v5", workflowId, 5L, false),
        createVersionedWorkflowView("uuid-v6", workflowId, 6L, false),
        createVersionedWorkflowView("uuid-v7", workflowId, 7L, false),
        createVersionedWorkflowView("uuid-v8", workflowId, 8L, false),
        createVersionedWorkflowView("uuid-v9", workflowId, 9L, false),
        createVersionedWorkflowView("uuid-v10", workflowId, 10L, true)
    );

    when(versionRepository.findByWorkflowId(workflowId)).thenReturn(workflows);
    when(objectConverter.convertCollection(workflows, VersionedWorkflowView.class))
        .thenReturn(expectedViews);

    // When: Calling getAllVersions
    List<VersionedWorkflowView> result = service.getAllVersions(workflowId);

    // Then: Should return all 10 versions
    assertThat(result).hasSize(10);
    assertThat(result.stream().map(VersionedWorkflowView::getVersion))
        .containsExactlyInAnyOrder(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
    verify(versionRepository, times(1)).findByWorkflowId(workflowId);
    verify(objectConverter, times(1)).convertCollection(workflows, VersionedWorkflowView.class);
  }

  @Test
  void getAllVersions_withVersionsHavingDescriptions_shouldIncludeDescriptions() {
    // Given: A workflow with versions having descriptions
    String workflowId = "documented-workflow";

    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setId("uuid-v1");
    workflow1.setWorkflowId(workflowId);
    workflow1.setVersion(1L);
    workflow1.setActive(false);
    workflow1.setPublished(true);
    workflow1.setDescription("Initial version");

    VersionedWorkflow workflow2 = new VersionedWorkflow();
    workflow2.setId("uuid-v2");
    workflow2.setWorkflowId(workflowId);
    workflow2.setVersion(2L);
    workflow2.setActive(true);
    workflow2.setPublished(true);
    workflow2.setDescription("Updated with bug fixes");

    List<VersionedWorkflow> workflows = Arrays.asList(workflow1, workflow2);

    VersionedWorkflowView view1 = VersionedWorkflowView.builder()
        .id("uuid-v1")
        .workflowId(workflowId)
        .version(1L)
        .active(false)
        .published(true)
        .description("Initial version")
        .build();

    VersionedWorkflowView view2 = VersionedWorkflowView.builder()
        .id("uuid-v2")
        .workflowId(workflowId)
        .version(2L)
        .active(true)
        .published(true)
        .description("Updated with bug fixes")
        .build();

    List<VersionedWorkflowView> expectedViews = Arrays.asList(view1, view2);

    when(versionRepository.findByWorkflowId(workflowId)).thenReturn(workflows);
    when(objectConverter.convertCollection(workflows, VersionedWorkflowView.class))
        .thenReturn(expectedViews);

    // When: Calling getAllVersions
    List<VersionedWorkflowView> result = service.getAllVersions(workflowId);

    // Then: Should include descriptions in all versions
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getDescription()).isNotNull();
    assertThat(result.get(1).getDescription()).isNotNull();
    verify(versionRepository, times(1)).findByWorkflowId(workflowId);
    verify(objectConverter, times(1)).convertCollection(workflows, VersionedWorkflowView.class);
  }

  @Test
  void getAllVersions_withVersionsHavingCreatedBy_shouldIncludeCreatedBy() {
    // Given: A workflow with versions having createdBy field
    String workflowId = "user-created-workflow";

    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setId("uuid-v1");
    workflow1.setWorkflowId(workflowId);
    workflow1.setVersion(1L);
    workflow1.setActive(false);
    workflow1.setPublished(true);
    workflow1.setCreatedBy(12345L);

    VersionedWorkflow workflow2 = new VersionedWorkflow();
    workflow2.setId("uuid-v2");
    workflow2.setWorkflowId(workflowId);
    workflow2.setVersion(2L);
    workflow2.setActive(true);
    workflow2.setPublished(true);
    workflow2.setCreatedBy(67890L);

    List<VersionedWorkflow> workflows = Arrays.asList(workflow1, workflow2);

    VersionedWorkflowView view1 = VersionedWorkflowView.builder()
        .id("uuid-v1")
        .workflowId(workflowId)
        .version(1L)
        .active(false)
        .published(true)
        .createdBy(12345L)
        .build();

    VersionedWorkflowView view2 = VersionedWorkflowView.builder()
        .id("uuid-v2")
        .workflowId(workflowId)
        .version(2L)
        .active(true)
        .published(true)
        .createdBy(67890L)
        .build();

    List<VersionedWorkflowView> expectedViews = Arrays.asList(view1, view2);

    when(versionRepository.findByWorkflowId(workflowId)).thenReturn(workflows);
    when(objectConverter.convertCollection(workflows, VersionedWorkflowView.class))
        .thenReturn(expectedViews);

    // When: Calling getAllVersions
    List<VersionedWorkflowView> result = service.getAllVersions(workflowId);

    // Then: Should include createdBy in all versions
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getCreatedBy()).isEqualTo(12345L);
    assertThat(result.get(1).getCreatedBy()).isEqualTo(67890L);
    verify(versionRepository, times(1)).findByWorkflowId(workflowId);
    verify(objectConverter, times(1)).convertCollection(workflows, VersionedWorkflowView.class);
  }

  @Test
  void getAllVersions_withVersionsHavingComplexSwadl_shouldPreserveSwadl() {
    // Given: A workflow with versions having complex SWADL
    String workflowId = "complex-swadl-workflow";
    String complexSwadl = "id: complex-workflow\n" +
        "version: 1.0\n" +
        "activities:\n" +
        "  - send-message:\n" +
        "      id: sendMsg\n" +
        "      on:\n" +
        "        message-received:\n" +
        "          content: /hello";

    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setId("uuid-v1");
    workflow1.setWorkflowId(workflowId);
    workflow1.setVersion(1L);
    workflow1.setActive(true);
    workflow1.setPublished(true);
    workflow1.setSwadl(complexSwadl);

    List<VersionedWorkflow> workflows = Collections.singletonList(workflow1);

    VersionedWorkflowView view1 = VersionedWorkflowView.builder()
        .id("uuid-v1")
        .workflowId(workflowId)
        .version(1L)
        .active(true)
        .published(true)
        .swadl(complexSwadl)
        .build();

    List<VersionedWorkflowView> expectedViews = Collections.singletonList(view1);

    when(versionRepository.findByWorkflowId(workflowId)).thenReturn(workflows);
    when(objectConverter.convertCollection(workflows, VersionedWorkflowView.class))
        .thenReturn(expectedViews);

    // When: Calling getAllVersions
    List<VersionedWorkflowView> result = service.getAllVersions(workflowId);

    // Then: Should preserve complex SWADL content
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getSwadl()).isEqualTo(complexSwadl);
    verify(versionRepository, times(1)).findByWorkflowId(workflowId);
    verify(objectConverter, times(1)).convertCollection(workflows, VersionedWorkflowView.class);
  }

  @Test
  void getAllVersions_withWorkflowIdContainingSpecialCharacters_shouldHandleCorrectly() {
    // Given: A workflow ID with special characters
    String workflowId = "workflow-with-special_chars.123";

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("uuid-v1");
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(1L);
    workflow.setActive(true);
    workflow.setPublished(true);

    List<VersionedWorkflow> workflows = Collections.singletonList(workflow);

    VersionedWorkflowView view = VersionedWorkflowView.builder()
        .id("uuid-v1")
        .workflowId(workflowId)
        .version(1L)
        .active(true)
        .published(true)
        .build();

    List<VersionedWorkflowView> expectedViews = Collections.singletonList(view);

    when(versionRepository.findByWorkflowId(workflowId)).thenReturn(workflows);
    when(objectConverter.convertCollection(workflows, VersionedWorkflowView.class))
        .thenReturn(expectedViews);

    // When: Calling getAllVersions with special characters in ID
    List<VersionedWorkflowView> result = service.getAllVersions(workflowId);

    // Then: Should handle special characters correctly
    assertThat(result).hasSize(1);
    assertThat(result.get(0).getWorkflowId()).isEqualTo(workflowId);
    verify(versionRepository, times(1)).findByWorkflowId(workflowId);
    verify(objectConverter, times(1)).convertCollection(workflows, VersionedWorkflowView.class);
  }

  @Test
  void getAllVersions_withEmptyWorkflowId_shouldReturnEmptyList() {
    // Given: An empty string as workflow ID
    String workflowId = "";

    when(versionRepository.findByWorkflowId(workflowId)).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(Collections.emptyList(), VersionedWorkflowView.class))
        .thenReturn(Collections.emptyList());

    // When: Calling getAllVersions with empty ID
    List<VersionedWorkflowView> result = service.getAllVersions(workflowId);

    // Then: Should return empty list
    assertThat(result).isEmpty();
    verify(versionRepository, times(1)).findByWorkflowId(workflowId);
    verify(objectConverter, times(1)).convertCollection(Collections.emptyList(), VersionedWorkflowView.class);
  }

  @Test
  void getAllVersions_calledMultipleTimes_shouldQueryRepositoryEachTime() {
    // Given: A workflow with versions exists
    String workflowId = "test-workflow";

    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("uuid-v1");
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(1L);
    workflow.setActive(true);
    workflow.setPublished(true);

    List<VersionedWorkflow> workflows = Collections.singletonList(workflow);

    VersionedWorkflowView view = VersionedWorkflowView.builder()
        .id("uuid-v1")
        .workflowId(workflowId)
        .version(1L)
        .active(true)
        .published(true)
        .build();

    List<VersionedWorkflowView> expectedViews = Collections.singletonList(view);

    when(versionRepository.findByWorkflowId(workflowId)).thenReturn(workflows);
    when(objectConverter.convertCollection(workflows, VersionedWorkflowView.class))
        .thenReturn(expectedViews);

    // When: Calling getAllVersions multiple times
    service.getAllVersions(workflowId);
    service.getAllVersions(workflowId);
    service.getAllVersions(workflowId);

    // Then: Should query the repository each time (no caching)
    verify(versionRepository, times(3)).findByWorkflowId(workflowId);
    verify(objectConverter, times(3)).convertCollection(workflows, VersionedWorkflowView.class);
  }

  @Test
  void getAllVersions_withVersionsHavingDeploymentIds_shouldIncludeDeploymentIds() {
    // Given: A workflow with versions having deployment IDs
    String workflowId = "deployed-workflow";

    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setId("uuid-v1");
    workflow1.setWorkflowId(workflowId);
    workflow1.setVersion(1L);
    workflow1.setActive(false);
    workflow1.setPublished(true);
    workflow1.setDeploymentId("deployment-v1-123");

    VersionedWorkflow workflow2 = new VersionedWorkflow();
    workflow2.setId("uuid-v2");
    workflow2.setWorkflowId(workflowId);
    workflow2.setVersion(2L);
    workflow2.setActive(true);
    workflow2.setPublished(true);
    workflow2.setDeploymentId("deployment-v2-456");

    List<VersionedWorkflow> workflows = Arrays.asList(workflow1, workflow2);

    VersionedWorkflowView view1 = VersionedWorkflowView.builder()
        .id("uuid-v1")
        .workflowId(workflowId)
        .version(1L)
        .active(false)
        .published(true)
        .deploymentId("deployment-v1-123")
        .build();

    VersionedWorkflowView view2 = VersionedWorkflowView.builder()
        .id("uuid-v2")
        .workflowId(workflowId)
        .version(2L)
        .active(true)
        .published(true)
        .deploymentId("deployment-v2-456")
        .build();

    List<VersionedWorkflowView> expectedViews = Arrays.asList(view1, view2);

    when(versionRepository.findByWorkflowId(workflowId)).thenReturn(workflows);
    when(objectConverter.convertCollection(workflows, VersionedWorkflowView.class))
        .thenReturn(expectedViews);

    // When: Calling getAllVersions
    List<VersionedWorkflowView> result = service.getAllVersions(workflowId);

    // Then: Should include deployment IDs
    assertThat(result).hasSize(2);
    assertThat(result.get(0).getDeploymentId()).isEqualTo("deployment-v1-123");
    assertThat(result.get(1).getDeploymentId()).isEqualTo("deployment-v2-456");
    verify(versionRepository, times(1)).findByWorkflowId(workflowId);
    verify(objectConverter, times(1)).convertCollection(workflows, VersionedWorkflowView.class);
  }

  @Test
  void getAllVersions_withDifferentWorkflowIds_shouldReturnDifferentWorkflows() {
    // Given: Two different workflows
    String workflowId1 = "workflow-1";
    String workflowId2 = "workflow-2";

    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setId("uuid-w1-v1");
    workflow1.setWorkflowId(workflowId1);
    workflow1.setVersion(1L);

    VersionedWorkflow workflow2 = new VersionedWorkflow();
    workflow2.setId("uuid-w2-v1");
    workflow2.setWorkflowId(workflowId2);
    workflow2.setVersion(1L);

    List<VersionedWorkflow> workflows1 = Collections.singletonList(workflow1);
    List<VersionedWorkflow> workflows2 = Collections.singletonList(workflow2);

    VersionedWorkflowView view1 = VersionedWorkflowView.builder()
        .id("uuid-w1-v1")
        .workflowId(workflowId1)
        .version(1L)
        .build();

    VersionedWorkflowView view2 = VersionedWorkflowView.builder()
        .id("uuid-w2-v1")
        .workflowId(workflowId2)
        .version(1L)
        .build();

    List<VersionedWorkflowView> expectedViews1 = Collections.singletonList(view1);
    List<VersionedWorkflowView> expectedViews2 = Collections.singletonList(view2);

    when(versionRepository.findByWorkflowId(workflowId1)).thenReturn(workflows1);
    when(versionRepository.findByWorkflowId(workflowId2)).thenReturn(workflows2);
    when(objectConverter.convertCollection(workflows1, VersionedWorkflowView.class))
        .thenReturn(expectedViews1);
    when(objectConverter.convertCollection(workflows2, VersionedWorkflowView.class))
        .thenReturn(expectedViews2);

    // When: Calling getAllVersions for different workflows
    List<VersionedWorkflowView> result1 = service.getAllVersions(workflowId1);
    List<VersionedWorkflowView> result2 = service.getAllVersions(workflowId2);

    // Then: Should return correct versions for each workflow
    assertThat(result1).hasSize(1);
    assertThat(result1.get(0).getWorkflowId()).isEqualTo(workflowId1);
    assertThat(result2).hasSize(1);
    assertThat(result2.get(0).getWorkflowId()).isEqualTo(workflowId2);
    verify(versionRepository, times(1)).findByWorkflowId(workflowId1);
    verify(versionRepository, times(1)).findByWorkflowId(workflowId2);
  }

  // Helper methods to reduce code duplication
  private VersionedWorkflow createVersionedWorkflow(String id, String workflowId, Long version, boolean active) {
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId(id);
    workflow.setWorkflowId(workflowId);
    workflow.setVersion(version);
    workflow.setActive(active);
    workflow.setPublished(true);
    workflow.setSwadl("id: " + workflowId + "\nversion: " + version);
    return workflow;
  }

  private VersionedWorkflowView createVersionedWorkflowView(String id, String workflowId, Long version,
      boolean active) {
    return VersionedWorkflowView.builder()
        .id(id)
        .workflowId(workflowId)
        .version(version)
        .active(active)
        .published(true)
        .swadl("id: " + workflowId + "\nversion: " + version)
        .build();
  }
}
