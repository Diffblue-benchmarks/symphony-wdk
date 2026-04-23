package com.symphony.bdk.workflow.management;

import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Workflow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WorkflowManagementServiceTest {

  private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;
  private VersionedWorkflowRepository versionRepository;
  private ObjectConverter objectConverter;
  private WorkflowManagementService workflowManagementService;

  @BeforeEach
  void setUp() {
    workflowEngine = mock(WorkflowEngine.class);
    versionRepository = mock(VersionedWorkflowRepository.class);
    objectConverter = mock(ObjectConverter.class);
    workflowManagementService = new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
  }

  @Test
  void shouldDeployWorkflowWhenToPublishIsTrue() {
    SwadlView swadlView = SwadlView.builder().swadl("swadl-content").description("desc").build();
    Workflow workflow = new Workflow();
    workflow.setId("workflow-id");
    workflow.setVersion(1L);

    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);
    when(objectConverter.convert(eq("swadl-content"), eq(Workflow.class))).thenReturn(workflow);
    when(versionRepository.findByWorkflowIdAndPublishedFalse("workflow-id")).thenReturn(Optional.empty());
    when(workflowEngine.translate(workflow)).thenReturn(context);
    when(workflowEngine.deploy(context)).thenReturn("deployment-id");
    when(versionRepository.findByWorkflowIdAndActiveTrue("workflow-id")).thenReturn(Optional.empty());

    workflowManagementService.deploy(swadlView);

    verify(workflowEngine).deploy(context);
    verify(versionRepository).save(any(VersionedWorkflow.class));
  }

  @Test
  void shouldDeployWorkflowWhenToPublishIsFalse() {
    SwadlView swadlView = SwadlView.builder().swadl("swadl-content").build();
    Workflow workflow = new Workflow();
    workflow.setId("workflow-id");
    workflow.setVersion(1L);
    com.symphony.bdk.workflow.swadl.v1.Properties props = new com.symphony.bdk.workflow.swadl.v1.Properties();
    props.setPublish(false);
    workflow.setProperties(props);

    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);
    when(objectConverter.convert(eq("swadl-content"), eq(Workflow.class))).thenReturn(workflow);
    when(versionRepository.findByWorkflowIdAndPublishedFalse("workflow-id")).thenReturn(Optional.empty());
    when(workflowEngine.translate(workflow)).thenReturn(context);

    workflowManagementService.deploy(swadlView);

    verify(workflowEngine, never()).deploy(context);
    verify(versionRepository).save(any(VersionedWorkflow.class));
  }

  @Test
  void shouldThrowExceptionWhenUnpublishedVersionExistsDuringDeploy() {
    SwadlView swadlView = SwadlView.builder().swadl("swadl-content").build();
    Workflow workflow = new Workflow();
    workflow.setId("workflow-id");
    workflow.setVersion(2L);

    VersionedWorkflow existingDraft = new VersionedWorkflow();
    existingDraft.setVersion(1L);

    when(objectConverter.convert(eq("swadl-content"), eq(Workflow.class))).thenReturn(workflow);
    when(versionRepository.findByWorkflowIdAndPublishedFalse("workflow-id")).thenReturn(Optional.of(existingDraft));

    assertThatThrownBy(() -> workflowManagementService.deploy(swadlView))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("has not been published yet");
  }

  @Test
  void shouldUpdateWorkflowWhenToPublishIsFalse() {
    SwadlView swadlView = SwadlView.builder().swadl("swadl-content").description("updated desc").build();
    Workflow workflow = new Workflow();
    workflow.setId("workflow-id");
    workflow.setVersion(1L);
    com.symphony.bdk.workflow.swadl.v1.Properties props = new com.symphony.bdk.workflow.swadl.v1.Properties();
    props.setPublish(false);
    workflow.setProperties(props);

    VersionedWorkflow existingWorkflow = new VersionedWorkflow();
    existingWorkflow.setPublished(false);

    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);
    when(objectConverter.convert(eq("swadl-content"), eq(Workflow.class))).thenReturn(workflow);
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc("workflow-id")).thenReturn(Optional.of(existingWorkflow));
    when(workflowEngine.translate(workflow)).thenReturn(context);

    workflowManagementService.update(swadlView);

    verify(workflowEngine, never()).deploy(context);
    verify(versionRepository).save(existingWorkflow);
    assertThat(existingWorkflow.getSwadl()).isEqualTo("swadl-content");
    assertThat(existingWorkflow.getDescription()).isEqualTo("updated desc");
  }

  @Test
  void shouldUpdateAndDeployWorkflowWhenToPublishIsTrue() {
    SwadlView swadlView = SwadlView.builder().swadl("swadl-content").description("desc").build();
    Workflow workflow = new Workflow();
    workflow.setId("workflow-id");
    workflow.setVersion(1L);

    VersionedWorkflow existingWorkflow = new VersionedWorkflow();
    existingWorkflow.setPublished(false);

    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);
    when(objectConverter.convert(eq("swadl-content"), eq(Workflow.class))).thenReturn(workflow);
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc("workflow-id")).thenReturn(Optional.of(existingWorkflow));
    when(workflowEngine.translate(workflow)).thenReturn(context);
    when(workflowEngine.deploy(context)).thenReturn("deployment-id");
    when(versionRepository.findByWorkflowIdAndActiveTrue("workflow-id")).thenReturn(Optional.empty());

    workflowManagementService.update(swadlView);

    verify(workflowEngine).deploy(context);
    verify(versionRepository).save(existingWorkflow);
    assertThat(existingWorkflow.getDeploymentId()).isEqualTo("deployment-id");
    assertThat(existingWorkflow.getActive()).isTrue();
  }

  @Test
  void shouldThrowNotFoundExceptionWhenWorkflowNotExistsDuringUpdate() {
    SwadlView swadlView = SwadlView.builder().swadl("swadl-content").build();
    Workflow workflow = new Workflow();
    workflow.setId("workflow-id");
    workflow.setVersion(1L);

    when(objectConverter.convert(eq("swadl-content"), eq(Workflow.class))).thenReturn(workflow);
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc("workflow-id")).thenReturn(Optional.empty());

    assertThatThrownBy(() -> workflowManagementService.update(swadlView))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("workflow-id");
  }

  @Test
  void shouldThrowUnsupportedOperationExceptionWhenUpdateOnPublishedWorkflow() {
    SwadlView swadlView = SwadlView.builder().swadl("swadl-content").build();
    Workflow workflow = new Workflow();
    workflow.setId("workflow-id");
    workflow.setVersion(1L);

    VersionedWorkflow publishedWorkflow = new VersionedWorkflow();
    publishedWorkflow.setPublished(true);

    when(objectConverter.convert(eq("swadl-content"), eq(Workflow.class))).thenReturn(workflow);
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc("workflow-id")).thenReturn(Optional.of(publishedWorkflow));

    assertThatThrownBy(() -> workflowManagementService.update(swadlView))
        .isInstanceOf(UnsupportedOperationException.class)
        .hasMessageContaining("Update on a published Workflow is forbidden");
  }

  @Test
  void shouldGetActiveVersionByWorkflowId() {
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setWorkflowId("workflow-id");
    VersionedWorkflowView view = mock(VersionedWorkflowView.class);

    when(versionRepository.findByWorkflowIdAndActiveTrue("workflow-id")).thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(eq(versionedWorkflow), eq(VersionedWorkflowView.class))).thenReturn(view);

    Optional<VersionedWorkflowView> result = workflowManagementService.get("workflow-id");

    assertThat(result).isPresent().contains(view);
  }

  @Test
  void shouldReturnEmptyWhenNoActiveVersionForWorkflowId() {
    when(versionRepository.findByWorkflowIdAndActiveTrue("workflow-id")).thenReturn(Optional.empty());

    Optional<VersionedWorkflowView> result = workflowManagementService.get("workflow-id");

    assertThat(result).isEmpty();
  }

  @Test
  void shouldGetWorkflowByIdAndVersion() {
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    VersionedWorkflowView view = mock(VersionedWorkflowView.class);

    when(versionRepository.findByWorkflowIdAndVersion("workflow-id", 1L)).thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(eq(versionedWorkflow), eq(VersionedWorkflowView.class))).thenReturn(view);

    Optional<VersionedWorkflowView> result = workflowManagementService.get("workflow-id", 1L);

    assertThat(result).isPresent().contains(view);
  }

  @Test
  void shouldReturnEmptyWhenVersionNotFound() {
    when(versionRepository.findByWorkflowIdAndVersion("workflow-id", 99L)).thenReturn(Optional.empty());

    Optional<VersionedWorkflowView> result = workflowManagementService.get("workflow-id", 99L);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldGetAllVersionsForWorkflowId() {
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    VersionedWorkflowView view = mock(VersionedWorkflowView.class);
    List<VersionedWorkflow> workflows = List.of(versionedWorkflow);
    List<VersionedWorkflowView> views = List.of(view);

    when(versionRepository.findByWorkflowId("workflow-id")).thenReturn(workflows);
    when(objectConverter.convertCollection(eq(workflows), eq(VersionedWorkflowView.class))).thenReturn(views);

    List<VersionedWorkflowView> result = workflowManagementService.getAllVersions("workflow-id");

    assertThat(result).hasSize(1).contains(view);
  }

  @Test
  void shouldDeleteAllVersionsWhenNoVersionSpecified() {
    workflowManagementService.delete("workflow-id");

    verify(versionRepository).deleteByWorkflowId("workflow-id");
    verify(workflowEngine).undeployByWorkflowId("workflow-id");
  }

  @Test
  void shouldDeleteSpecificVersionAndUndeployWhenActiveVersion() {
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setDeploymentId("deployment-id");
    versionedWorkflow.setActive(true);

    when(versionRepository.findByWorkflowIdAndVersion("workflow-id", 1L)).thenReturn(Optional.of(versionedWorkflow));

    workflowManagementService.delete("workflow-id", 1L);

    verify(versionRepository).deleteByWorkflowIdAndVersion("workflow-id", 1L);
    verify(workflowEngine).undeployByDeploymentId("deployment-id");
  }

  @Test
  void shouldDeleteSpecificVersionWithoutUndeployWhenNotActiveVersion() {
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setDeploymentId("deployment-id");
    versionedWorkflow.setActive(null);

    when(versionRepository.findByWorkflowIdAndVersion("workflow-id", 1L)).thenReturn(Optional.of(versionedWorkflow));

    workflowManagementService.delete("workflow-id", 1L);

    verify(versionRepository).deleteByWorkflowIdAndVersion("workflow-id", 1L);
    verify(workflowEngine, never()).undeployByDeploymentId(any());
  }

  @Test
  void shouldNotDeleteWhenVersionDoesNotExist() {
    when(versionRepository.findByWorkflowIdAndVersion("workflow-id", 1L)).thenReturn(Optional.empty());

    workflowManagementService.delete("workflow-id", 1L);

    verify(versionRepository, never()).deleteByWorkflowIdAndVersion(any(), any());
    verify(workflowEngine, never()).undeployByDeploymentId(any());
  }

  @Test
  void shouldSetActiveVersionSuccessfully() {
    VersionedWorkflow deployedWorkflow = new VersionedWorkflow();
    deployedWorkflow.setSwadl("swadl-content");
    deployedWorkflow.setPublished(true);

    Workflow workflow = new Workflow();
    workflow.setId("workflow-id");
    workflow.setVersion(1L);

    when(versionRepository.findByWorkflowIdAndVersion("workflow-id", 1L)).thenReturn(Optional.of(deployedWorkflow));
    when(objectConverter.convert(eq("swadl-content"), eq(1L), eq(Workflow.class))).thenReturn(workflow);
    when(workflowEngine.deploy(workflow)).thenReturn("new-deployment-id");
    when(versionRepository.findByWorkflowIdAndActiveTrue("workflow-id")).thenReturn(Optional.empty());

    workflowManagementService.setActiveVersion("workflow-id", 1L);

    verify(workflowEngine).deploy(workflow);
    verify(versionRepository).save(deployedWorkflow);
    assertThat(deployedWorkflow.getDeploymentId()).isEqualTo("new-deployment-id");
    assertThat(deployedWorkflow.getActive()).isTrue();
  }

  @Test
  void shouldThrowNotFoundExceptionWhenVersionDoesNotExistDuringSetActiveVersion() {
    when(versionRepository.findByWorkflowIdAndVersion("workflow-id", 1L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> workflowManagementService.setActiveVersion("workflow-id", 1L))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("Version 1 of the workflow workflow-id does not exist");
  }

  @Test
  void shouldThrowIllegalArgumentExceptionWhenVersionIsDraftDuringSetActiveVersion() {
    VersionedWorkflow draftWorkflow = new VersionedWorkflow();
    draftWorkflow.setPublished(false);

    when(versionRepository.findByWorkflowIdAndVersion("workflow-id", 1L)).thenReturn(Optional.of(draftWorkflow));

    assertThatThrownBy(() -> workflowManagementService.setActiveVersion("workflow-id", 1L))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("is in draft mode");
  }

  @Test
  void shouldSetPreviousActiveVersionToInactiveWhenDeploying() {
    VersionedWorkflow activeWorkflow = new VersionedWorkflow();
    activeWorkflow.setActive(true);

    SwadlView swadlView = SwadlView.builder().swadl("swadl-content").build();
    Workflow workflow = new Workflow();
    workflow.setId("workflow-id");
    workflow.setVersion(1L);

    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);
    when(objectConverter.convert(eq("swadl-content"), eq(Workflow.class))).thenReturn(workflow);
    when(versionRepository.findByWorkflowIdAndPublishedFalse("workflow-id")).thenReturn(Optional.empty());
    when(workflowEngine.translate(workflow)).thenReturn(context);
    when(workflowEngine.deploy(context)).thenReturn("deployment-id");
    when(versionRepository.findByWorkflowIdAndActiveTrue("workflow-id")).thenReturn(Optional.of(activeWorkflow));

    workflowManagementService.deploy(swadlView);

    assertThat(activeWorkflow.getActive()).isFalse();
    verify(versionRepository).saveAndFlush(activeWorkflow);
  }
}
