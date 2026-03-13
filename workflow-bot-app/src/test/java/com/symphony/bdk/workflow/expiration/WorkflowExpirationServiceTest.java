package com.symphony.bdk.workflow.expiration;

import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.WorkflowExpirationJobRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.management.repository.domain.WorkflowExpirationJob;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WorkflowExpirationServiceTest {

  private WorkflowExpirationJobRepository expirationJobRepository;
  private VersionedWorkflowRepository versioningRepository;
  private WorkflowExpirationPlanner workflowExpirationPlanner;
  private WorkflowExpirationService service;

  @BeforeEach
  void setUp() {
    expirationJobRepository = mock(WorkflowExpirationJobRepository.class);
    versioningRepository = mock(VersionedWorkflowRepository.class);
    workflowExpirationPlanner = mock(WorkflowExpirationPlanner.class);
    service = new WorkflowExpirationService(expirationJobRepository, versioningRepository,
        workflowExpirationPlanner);
  }

  @Test
  void scheduleWorkflowExpirationShouldSaveAndPlanWhenWorkflowsExist() {
    String workflowId = "testWorkflow";
    Instant expirationTime = Instant.now();

    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setId("versionId1");
    workflow1.setWorkflowId(workflowId);
    workflow1.setDeploymentId("deployment1");

    VersionedWorkflow workflow2 = new VersionedWorkflow();
    workflow2.setId("versionId2");
    workflow2.setWorkflowId(workflowId);
    workflow2.setDeploymentId("deployment2");

    List<VersionedWorkflow> workflows = Arrays.asList(workflow1, workflow2);

    when(versioningRepository.findByWorkflowId(workflowId)).thenReturn(workflows);

    service.scheduleWorkflowExpiration(workflowId, expirationTime);

    ArgumentCaptor<List<WorkflowExpirationJob>> captor = ArgumentCaptor.forClass(List.class);
    verify(expirationJobRepository).saveAll(captor.capture());

    List<WorkflowExpirationJob> savedJobs = captor.getValue();
    assertEquals(2, savedJobs.size());
    assertEquals("versionId1", savedJobs.get(0).getId());
    assertEquals(workflowId, savedJobs.get(0).getWorkflowId());
    assertEquals("deployment1", savedJobs.get(0).getDeploymentId());
    assertEquals(expirationTime, savedJobs.get(0).getExpirationDate());
    assertEquals("versionId2", savedJobs.get(1).getId());
    assertEquals(workflowId, savedJobs.get(1).getWorkflowId());
    assertEquals("deployment2", savedJobs.get(1).getDeploymentId());
    assertEquals(expirationTime, savedJobs.get(1).getExpirationDate());

    verify(workflowExpirationPlanner, times(2)).planExpiration(any(WorkflowExpirationJob.class));
  }

  @Test
  void scheduleWorkflowExpirationShouldThrowNotFoundExceptionWhenNoWorkflowsExist() {
    String workflowId = "nonExistentWorkflow";
    Instant expirationTime = Instant.now();

    when(versioningRepository.findByWorkflowId(workflowId)).thenReturn(Collections.emptyList());

    NotFoundException exception = assertThrows(NotFoundException.class,
        () -> service.scheduleWorkflowExpiration(workflowId, expirationTime));

    assertEquals("Workflow nonExistentWorkflow does not exist.", exception.getMessage());
  }
}
