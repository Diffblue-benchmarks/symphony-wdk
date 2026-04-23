package com.symphony.bdk.workflow.expiration;

import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.WorkflowExpirationJobRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.management.repository.domain.WorkflowExpirationJob;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class WorkflowExpirationServiceTest {

  private WorkflowExpirationJobRepository expirationJobRepository;
  private VersionedWorkflowRepository versioningRepository;
  private WorkflowExpirationPlanner workflowExpirationPlanner;
  private WorkflowExpirationService workflowExpirationService;

  @BeforeEach
  void setUp() {
    expirationJobRepository = mock(WorkflowExpirationJobRepository.class);
    versioningRepository = mock(VersionedWorkflowRepository.class);
    workflowExpirationPlanner = mock(WorkflowExpirationPlanner.class);
    workflowExpirationService =
        new WorkflowExpirationService(expirationJobRepository, versioningRepository, workflowExpirationPlanner);
  }

  @Test
  void shouldThrowNotFoundExceptionWhenWorkflowDoesNotExist() {
    when(versioningRepository.findByWorkflowId("unknown-workflow")).thenReturn(Collections.emptyList());

    assertThatThrownBy(() -> workflowExpirationService.scheduleWorkflowExpiration("unknown-workflow", Instant.now()))
        .isInstanceOf(NotFoundException.class)
        .hasMessageContaining("unknown-workflow");

    verify(expirationJobRepository, never()).saveAll(anyList());
    verify(workflowExpirationPlanner, never()).planExpiration(org.mockito.ArgumentMatchers.any());
  }

  @Test
  void shouldSaveAndPlanExpirationWhenWorkflowExists() {
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId("versioned-id");
    workflow.setWorkflowId("my-workflow");
    workflow.setDeploymentId("deployment-id");

    Instant expiration = Instant.parse("2030-01-01T00:00:00Z");
    when(versioningRepository.findByWorkflowId("my-workflow")).thenReturn(List.of(workflow));
    when(expirationJobRepository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

    workflowExpirationService.scheduleWorkflowExpiration("my-workflow", expiration);

    verify(expirationJobRepository).saveAll(anyList());
    verify(workflowExpirationPlanner).planExpiration(
        new WorkflowExpirationJob("versioned-id", "my-workflow", "deployment-id", expiration));
  }

  @Test
  void shouldPlanExpirationForEachVersionedWorkflow() {
    VersionedWorkflow workflow1 = new VersionedWorkflow();
    workflow1.setId("v-id-1");
    workflow1.setWorkflowId("multi-workflow");
    workflow1.setDeploymentId("dep-1");

    VersionedWorkflow workflow2 = new VersionedWorkflow();
    workflow2.setId("v-id-2");
    workflow2.setWorkflowId("multi-workflow");
    workflow2.setDeploymentId("dep-2");

    Instant expiration = Instant.parse("2030-06-15T12:00:00Z");
    when(versioningRepository.findByWorkflowId("multi-workflow")).thenReturn(List.of(workflow1, workflow2));
    when(expirationJobRepository.saveAll(anyList())).thenAnswer(inv -> inv.getArgument(0));

    workflowExpirationService.scheduleWorkflowExpiration("multi-workflow", expiration);

    verify(expirationJobRepository).saveAll(anyList());
    verify(workflowExpirationPlanner).planExpiration(
        new WorkflowExpirationJob("v-id-1", "multi-workflow", "dep-1", expiration));
    verify(workflowExpirationPlanner).planExpiration(
        new WorkflowExpirationJob("v-id-2", "multi-workflow", "dep-2", expiration));
  }
}
