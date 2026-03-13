package com.symphony.bdk.workflow.management;

import com.symphony.bdk.workflow.api.v1.dto.SwadlView;
import com.symphony.bdk.workflow.api.v1.dto.VersionedWorkflowView;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
  private WorkflowManagementService service;

  @BeforeEach
  void setUp() {
    workflowEngine = mock(WorkflowEngine.class);
    versionRepository = mock(VersionedWorkflowRepository.class);
    objectConverter = mock(ObjectConverter.class);
    service = new WorkflowManagementService(workflowEngine, versionRepository, objectConverter);
  }

  @Test
  void deployShouldSaveWorkflowWhenNotPublished() {
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: testWorkflow")
        .description("Test workflow")
        .build();
    Workflow workflow = new Workflow();
    workflow.setId("testWorkflow");
    Properties properties = new Properties();
    properties.setPublish(false);
    workflow.setProperties(properties);
    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);

    when(objectConverter.convert(swadlView.getSwadl(), Workflow.class)).thenReturn(workflow);
    when(versionRepository.findByWorkflowIdAndPublishedFalse("testWorkflow")).thenReturn(Optional.empty());
    when(workflowEngine.translate(workflow)).thenReturn(context);

    service.deploy(swadlView);

    verify(versionRepository).save(any(VersionedWorkflow.class));
    verify(workflowEngine, never()).deploy(any(CamundaTranslatedWorkflowContext.class));
  }

  @Test
  void deployShouldDeployAndSaveWorkflowWhenPublished() {
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: testWorkflow\nproperties:\n  publish: true")
        .description("Test workflow")
        .build();
    Workflow workflow = new Workflow();
    workflow.setId("testWorkflow");
    Properties properties = new Properties();
    properties.setPublish(true);
    workflow.setProperties(properties);
    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);

    when(objectConverter.convert(swadlView.getSwadl(), Workflow.class)).thenReturn(workflow);
    when(versionRepository.findByWorkflowIdAndPublishedFalse("testWorkflow")).thenReturn(Optional.empty());
    when(workflowEngine.translate(workflow)).thenReturn(context);
    when(workflowEngine.deploy(context)).thenReturn("deploymentId123");
    when(versionRepository.findByWorkflowIdAndActiveTrue("testWorkflow")).thenReturn(Optional.empty());

    service.deploy(swadlView);

    verify(workflowEngine).deploy(context);
    verify(versionRepository).save(any(VersionedWorkflow.class));
  }

  @Test
  void deployShouldSetCurrentActiveVersionToInactiveWhenPublished() {
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: testWorkflow\nproperties:\n  publish: true")
        .description("Test workflow")
        .build();
    Workflow workflow = new Workflow();
    workflow.setId("testWorkflow");
    Properties properties = new Properties();
    properties.setPublish(true);
    workflow.setProperties(properties);
    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);
    VersionedWorkflow activeWorkflow = new VersionedWorkflow();
    activeWorkflow.setActive(true);

    when(objectConverter.convert(swadlView.getSwadl(), Workflow.class)).thenReturn(workflow);
    when(versionRepository.findByWorkflowIdAndPublishedFalse("testWorkflow")).thenReturn(Optional.empty());
    when(workflowEngine.translate(workflow)).thenReturn(context);
    when(workflowEngine.deploy(context)).thenReturn("deploymentId123");
    when(versionRepository.findByWorkflowIdAndActiveTrue("testWorkflow")).thenReturn(Optional.of(activeWorkflow));

    service.deploy(swadlView);

    verify(versionRepository).saveAndFlush(activeWorkflow);
    assertFalse(activeWorkflow.getActive());
  }

  @Test
  void deployShouldThrowExceptionWhenUnPublishedVersionExists() {
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: testWorkflow")
        .description("Test workflow")
        .build();
    Workflow workflow = new Workflow();
    workflow.setId("testWorkflow");
    Properties properties = new Properties();
    properties.setPublish(false);
    workflow.setProperties(properties);
    VersionedWorkflow unpublishedWorkflow = new VersionedWorkflow();
    unpublishedWorkflow.setVersion(1L);

    when(objectConverter.convert(swadlView.getSwadl(), Workflow.class)).thenReturn(workflow);
    when(versionRepository.findByWorkflowIdAndPublishedFalse("testWorkflow"))
        .thenReturn(Optional.of(unpublishedWorkflow));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      service.deploy(swadlView);
    });

    assertTrue(exception.getMessage().contains("Version 1 of workflow has not been published yet."));
    verify(workflowEngine, never()).translate(any());
  }

  @Test
  void updateShouldUpdateWorkflowWhenNotPublished() {
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: testWorkflow")
        .description("Updated workflow")
        .build();
    Workflow workflow = new Workflow();
    workflow.setId("testWorkflow");
    workflow.setVersion(1L);
    Properties properties = new Properties();
    properties.setPublish(false);
    workflow.setProperties(properties);
    VersionedWorkflow existingWorkflow = new VersionedWorkflow();
    existingWorkflow.setPublished(false);
    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);

    when(objectConverter.convert(swadlView.getSwadl(), Workflow.class)).thenReturn(workflow);
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc("testWorkflow"))
        .thenReturn(Optional.of(existingWorkflow));
    when(workflowEngine.translate(workflow)).thenReturn(context);

    service.update(swadlView);

    assertEquals(1L, existingWorkflow.getVersion());
    assertEquals(swadlView.getSwadl(), existingWorkflow.getSwadl());
    assertEquals(swadlView.getDescription(), existingWorkflow.getDescription());
    verify(versionRepository).save(existingWorkflow);
    verify(workflowEngine, never()).deploy(any(CamundaTranslatedWorkflowContext.class));
  }

  @Test
  void updateShouldDeployAndUpdateWorkflowWhenPublished() {
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: testWorkflow\nproperties:\n  publish: true")
        .description("Updated workflow")
        .build();
    Workflow workflow = new Workflow();
    workflow.setId("testWorkflow");
    workflow.setVersion(1L);
    Properties properties = new Properties();
    properties.setPublish(true);
    workflow.setProperties(properties);
    VersionedWorkflow existingWorkflow = new VersionedWorkflow();
    existingWorkflow.setPublished(false);
    CamundaTranslatedWorkflowContext context = mock(CamundaTranslatedWorkflowContext.class);

    when(objectConverter.convert(swadlView.getSwadl(), Workflow.class)).thenReturn(workflow);
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc("testWorkflow"))
        .thenReturn(Optional.of(existingWorkflow));
    when(workflowEngine.translate(workflow)).thenReturn(context);
    when(workflowEngine.deploy(context)).thenReturn("deploymentId456");
    when(versionRepository.findByWorkflowIdAndActiveTrue("testWorkflow")).thenReturn(Optional.empty());

    service.update(swadlView);

    assertEquals("deploymentId456", existingWorkflow.getDeploymentId());
    assertTrue(existingWorkflow.getActive());
    verify(workflowEngine).deploy(context);
    verify(versionRepository).save(existingWorkflow);
  }

  @Test
  void updateShouldThrowNotFoundExceptionWhenWorkflowDoesNotExist() {
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: testWorkflow")
        .description("Updated workflow")
        .build();
    Workflow workflow = new Workflow();
    workflow.setId("testWorkflow");
    Properties properties = new Properties();
    properties.setPublish(false);
    workflow.setProperties(properties);

    when(objectConverter.convert(swadlView.getSwadl(), Workflow.class)).thenReturn(workflow);
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc("testWorkflow")).thenReturn(Optional.empty());

    NotFoundException exception = assertThrows(NotFoundException.class, () -> {
      service.update(swadlView);
    });

    assertTrue(exception.getMessage().contains("Workflow testWorkflow does not exist."));
  }

  @Test
  void updateShouldThrowUnsupportedOperationExceptionWhenLatestVersionIsPublished() {
    SwadlView swadlView = SwadlView.builder()
        .swadl("id: testWorkflow")
        .description("Updated workflow")
        .build();
    Workflow workflow = new Workflow();
    workflow.setId("testWorkflow");
    Properties properties = new Properties();
    properties.setPublish(false);
    workflow.setProperties(properties);
    VersionedWorkflow publishedWorkflow = new VersionedWorkflow();
    publishedWorkflow.setPublished(true);

    when(objectConverter.convert(swadlView.getSwadl(), Workflow.class)).thenReturn(workflow);
    when(versionRepository.findTopByWorkflowIdOrderByVersionDesc("testWorkflow"))
        .thenReturn(Optional.of(publishedWorkflow));

    UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> {
      service.update(swadlView);
    });

    assertTrue(exception.getMessage().contains("Update on a published Workflow is forbidden."));
  }

  @Test
  void getShouldReturnActiveVersionWhenExists() {
    VersionedWorkflow activeWorkflow = new VersionedWorkflow();
    activeWorkflow.setWorkflowId("testWorkflow");
    VersionedWorkflowView workflowView = VersionedWorkflowView.builder()
        .workflowId("testWorkflow")
        .build();

    when(versionRepository.findByWorkflowIdAndActiveTrue("testWorkflow"))
        .thenReturn(Optional.of(activeWorkflow));
    when(objectConverter.convert(activeWorkflow, VersionedWorkflowView.class)).thenReturn(workflowView);

    Optional<VersionedWorkflowView> result = service.get("testWorkflow");

    assertTrue(result.isPresent());
    assertEquals("testWorkflow", result.get().getWorkflowId());
  }

  @Test
  void getShouldReturnEmptyWhenActiveVersionDoesNotExist() {
    when(versionRepository.findByWorkflowIdAndActiveTrue("testWorkflow")).thenReturn(Optional.empty());

    Optional<VersionedWorkflowView> result = service.get("testWorkflow");

    assertTrue(result.isEmpty());
  }

  @Test
  void getShouldReturnSpecificVersionWhenExists() {
    VersionedWorkflow versionedWorkflow = new VersionedWorkflow();
    versionedWorkflow.setWorkflowId("testWorkflow");
    versionedWorkflow.setVersion(2L);
    VersionedWorkflowView workflowView = VersionedWorkflowView.builder()
        .workflowId("testWorkflow")
        .version(2L)
        .build();

    when(versionRepository.findByWorkflowIdAndVersion("testWorkflow", 2L))
        .thenReturn(Optional.of(versionedWorkflow));
    when(objectConverter.convert(versionedWorkflow, VersionedWorkflowView.class)).thenReturn(workflowView);

    Optional<VersionedWorkflowView> result = service.get("testWorkflow", 2L);

    assertTrue(result.isPresent());
    assertEquals(2L, result.get().getVersion());
  }

  @Test
  void getAllVersionsShouldReturnAllVersionsOfWorkflow() {
    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setWorkflowId("testWorkflow");
    workflow1.setVersion(1L);
    VersionedWorkflow workflow2 = new VersionedWorkflow();
    workflow2.setWorkflowId("testWorkflow");
    workflow2.setVersion(2L);
    List<VersionedWorkflow> workflows = List.of(workflow1, workflow2);
    List<VersionedWorkflowView> workflowViews = List.of(
        VersionedWorkflowView.builder().workflowId("testWorkflow").version(1L).build(),
        VersionedWorkflowView.builder().workflowId("testWorkflow").version(2L).build()
    );

    when(versionRepository.findByWorkflowId("testWorkflow")).thenReturn(workflows);
    when(objectConverter.convertCollection(workflows, VersionedWorkflowView.class)).thenReturn(workflowViews);

    List<VersionedWorkflowView> result = service.getAllVersions("testWorkflow");

    assertNotNull(result);
    assertEquals(2, result.size());
  }

  @Test
  void deleteShouldDeleteAllVersionsWhenVersionIsNull() {
    service.delete("testWorkflow");

    verify(versionRepository).deleteByWorkflowId("testWorkflow");
    verify(workflowEngine).undeployByWorkflowId("testWorkflow");
  }

  @Test
  void deleteShouldDeleteSpecificVersionWhenVersionProvided() {
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setActive(false);

    when(versionRepository.findByWorkflowIdAndVersion("testWorkflow", 2L))
        .thenReturn(Optional.of(workflow));

    service.delete("testWorkflow", 2L);

    verify(versionRepository).deleteByWorkflowIdAndVersion("testWorkflow", 2L);
    verify(workflowEngine, never()).undeployByDeploymentId(any());
  }

  @Test
  void deleteShouldUndeployWhenVersionIsActiveAndProvided() {
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setActive(true);
    workflow.setDeploymentId("deploymentId123");

    when(versionRepository.findByWorkflowIdAndVersion("testWorkflow", 2L))
        .thenReturn(Optional.of(workflow));

    service.delete("testWorkflow", 2L);

    verify(versionRepository).deleteByWorkflowIdAndVersion("testWorkflow", 2L);
    verify(workflowEngine).undeployByDeploymentId("deploymentId123");
  }

  @Test
  void deleteShouldDoNothingWhenVersionProvidedButNotFound() {
    when(versionRepository.findByWorkflowIdAndVersion("testWorkflow", 2L)).thenReturn(Optional.empty());

    service.delete("testWorkflow", 2L);

    verify(versionRepository, never()).deleteByWorkflowIdAndVersion(any(), any());
    verify(workflowEngine, never()).undeployByDeploymentId(any());
  }

  @Test
  void setActiveVersionShouldSetVersionAsActive() {
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setPublished(true);
    workflow.setSwadl("id: testWorkflow");
    Workflow workflowToDeploy = new Workflow();
    workflowToDeploy.setId("testWorkflow");

    when(versionRepository.findByWorkflowIdAndVersion("testWorkflow", 2L))
        .thenReturn(Optional.of(workflow));
    when(objectConverter.convert(workflow.getSwadl(), 2L, Workflow.class)).thenReturn(workflowToDeploy);
    when(workflowEngine.deploy(workflowToDeploy)).thenReturn("deploymentId789");
    when(versionRepository.findByWorkflowIdAndActiveTrue("testWorkflow")).thenReturn(Optional.empty());

    service.setActiveVersion("testWorkflow", 2L);

    assertEquals("deploymentId789", workflow.getDeploymentId());
    assertTrue(workflow.getActive());
    verify(versionRepository).save(workflow);
  }

  @Test
  void setActiveVersionShouldThrowNotFoundExceptionWhenVersionDoesNotExist() {
    when(versionRepository.findByWorkflowIdAndVersion("testWorkflow", 2L)).thenReturn(Optional.empty());

    NotFoundException exception = assertThrows(NotFoundException.class, () -> {
      service.setActiveVersion("testWorkflow", 2L);
    });

    assertTrue(exception.getMessage().contains("Version 2 of the workflow testWorkflow does not exist."));
  }

  @Test
  void setActiveVersionShouldThrowIllegalArgumentExceptionWhenVersionIsNotPublished() {
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setPublished(false);

    when(versionRepository.findByWorkflowIdAndVersion("testWorkflow", 2L))
        .thenReturn(Optional.of(workflow));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      service.setActiveVersion("testWorkflow", 2L);
    });

    assertTrue(exception.getMessage().contains("Version 2 of the workflow testWorkflow is in draft mode."));
  }
}
