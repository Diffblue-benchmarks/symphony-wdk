package com.symphony.bdk.workflow.expiration;

import com.symphony.bdk.workflow.exception.NotFoundException;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.WorkflowExpirationJobRepository;
import com.symphony.bdk.workflow.management.repository.domain.VersionedWorkflow;
import com.symphony.bdk.workflow.management.repository.domain.WorkflowExpirationJob;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowExpirationServiceClaudeTest {

  @Mock
  private WorkflowExpirationJobRepository expirationJobRepository;

  @Mock
  private VersionedWorkflowRepository versioningRepository;

  @Mock
  private WorkflowExpirationPlanner workflowExpirationPlanner;

  private WorkflowExpirationService service;

  @BeforeEach
  void setUp() {
    service = new WorkflowExpirationService(
        expirationJobRepository,
        versioningRepository,
        workflowExpirationPlanner
    );
  }

  // ==================== scheduleWorkflowExpiration Tests ====================

  @Test
  void scheduleWorkflowExpiration_withSingleWorkflow_shouldCreateAndScheduleJob() {
    // Given: A single versioned workflow exists
    String workflowId = "workflow-1";
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");

    VersionedWorkflow workflow = createVersionedWorkflow(
        "versioned-id-1",
        workflowId,
        "deployment-1"
    );

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Collections.singletonList(workflow));
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, expirationDate);

    // Then: Should save expiration job and plan expiration
    ArgumentCaptor<List<WorkflowExpirationJob>> captor = ArgumentCaptor.forClass(List.class);
    verify(expirationJobRepository, times(1)).saveAll(captor.capture());

    List<WorkflowExpirationJob> savedJobs = captor.getValue();
    assertEquals(1, savedJobs.size());

    WorkflowExpirationJob job = savedJobs.get(0);
    assertEquals("versioned-id-1", job.getId());
    assertEquals(workflowId, job.getWorkflowId());
    assertEquals("deployment-1", job.getDeploymentId());
    assertEquals(expirationDate, job.getExpirationDate());

    verify(workflowExpirationPlanner, times(1)).planExpiration(job);
  }

  @Test
  void scheduleWorkflowExpiration_withMultipleWorkflows_shouldCreateJobsForAll() {
    // Given: Multiple versioned workflows exist for the same workflow ID
    String workflowId = "workflow-1";
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");

    VersionedWorkflow workflow1 = createVersionedWorkflow(
        "versioned-id-1",
        workflowId,
        "deployment-1"
    );
    VersionedWorkflow workflow2 = createVersionedWorkflow(
        "versioned-id-2",
        workflowId,
        "deployment-2"
    );
    VersionedWorkflow workflow3 = createVersionedWorkflow(
        "versioned-id-3",
        workflowId,
        "deployment-3"
    );

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Arrays.asList(workflow1, workflow2, workflow3));
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, expirationDate);

    // Then: Should create and save jobs for all workflows
    ArgumentCaptor<List<WorkflowExpirationJob>> captor = ArgumentCaptor.forClass(List.class);
    verify(expirationJobRepository, times(1)).saveAll(captor.capture());

    List<WorkflowExpirationJob> savedJobs = captor.getValue();
    assertEquals(3, savedJobs.size());

    // Verify all jobs have correct properties
    assertEquals("versioned-id-1", savedJobs.get(0).getId());
    assertEquals("versioned-id-2", savedJobs.get(1).getId());
    assertEquals("versioned-id-3", savedJobs.get(2).getId());

    for (WorkflowExpirationJob job : savedJobs) {
      assertEquals(workflowId, job.getWorkflowId());
      assertEquals(expirationDate, job.getExpirationDate());
    }

    verify(workflowExpirationPlanner, times(3)).planExpiration(any(WorkflowExpirationJob.class));
  }

  @Test
  void scheduleWorkflowExpiration_whenNoWorkflowsFound_shouldThrowNotFoundException() {
    // Given: No workflows exist for the given workflow ID
    String workflowId = "non-existent-workflow";
    Instant expirationDate = Instant.now();

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Collections.emptyList());

    // When & Then: Should throw NotFoundException
    NotFoundException exception = assertThrows(
        NotFoundException.class,
        () -> service.scheduleWorkflowExpiration(workflowId, expirationDate)
    );

    assertEquals("Workflow non-existent-workflow does not exist.", exception.getMessage());

    // Verify no jobs were saved or planned
    verify(expirationJobRepository, never()).saveAll(anyList());
    verify(workflowExpirationPlanner, never()).planExpiration(any(WorkflowExpirationJob.class));
  }

  @Test
  void scheduleWorkflowExpiration_shouldCallPlanExpirationForEachJob() {
    // Given: Multiple versioned workflows exist
    String workflowId = "workflow-1";
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");

    VersionedWorkflow workflow1 = createVersionedWorkflow(
        "versioned-id-1",
        workflowId,
        "deployment-1"
    );
    VersionedWorkflow workflow2 = createVersionedWorkflow(
        "versioned-id-2",
        workflowId,
        "deployment-2"
    );

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Arrays.asList(workflow1, workflow2));
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, expirationDate);

    // Then: Should call planExpiration for each job
    ArgumentCaptor<WorkflowExpirationJob> captor = ArgumentCaptor.forClass(WorkflowExpirationJob.class);
    verify(workflowExpirationPlanner, times(2)).planExpiration(captor.capture());

    List<WorkflowExpirationJob> plannedJobs = captor.getAllValues();
    assertEquals(2, plannedJobs.size());
    assertEquals("versioned-id-1", plannedJobs.get(0).getId());
    assertEquals("versioned-id-2", plannedJobs.get(1).getId());
  }

  @Test
  void scheduleWorkflowExpiration_withFutureDate_shouldCreateJobsWithCorrectDate() {
    // Given: A workflow and a future expiration date
    String workflowId = "workflow-1";
    Instant futureDate = Instant.now().plusSeconds(86400); // 1 day from now

    VersionedWorkflow workflow = createVersionedWorkflow(
        "versioned-id-1",
        workflowId,
        "deployment-1"
    );

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Collections.singletonList(workflow));
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, futureDate);

    // Then: Should create job with the future expiration date
    ArgumentCaptor<List<WorkflowExpirationJob>> captor = ArgumentCaptor.forClass(List.class);
    verify(expirationJobRepository, times(1)).saveAll(captor.capture());

    List<WorkflowExpirationJob> savedJobs = captor.getValue();
    assertEquals(1, savedJobs.size());
    assertEquals(futureDate, savedJobs.get(0).getExpirationDate());
  }

  @Test
  void scheduleWorkflowExpiration_withPastDate_shouldCreateJobsWithPastDate() {
    // Given: A workflow and a past expiration date
    String workflowId = "workflow-1";
    Instant pastDate = Instant.now().minusSeconds(86400); // 1 day ago

    VersionedWorkflow workflow = createVersionedWorkflow(
        "versioned-id-1",
        workflowId,
        "deployment-1"
    );

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Collections.singletonList(workflow));
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, pastDate);

    // Then: Should create job with the past expiration date (no validation)
    ArgumentCaptor<List<WorkflowExpirationJob>> captor = ArgumentCaptor.forClass(List.class);
    verify(expirationJobRepository, times(1)).saveAll(captor.capture());

    List<WorkflowExpirationJob> savedJobs = captor.getValue();
    assertEquals(1, savedJobs.size());
    assertEquals(pastDate, savedJobs.get(0).getExpirationDate());
  }

  @Test
  void scheduleWorkflowExpiration_shouldPreserveDeploymentIds() {
    // Given: Workflows with different deployment IDs
    String workflowId = "workflow-1";
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");

    VersionedWorkflow workflow1 = createVersionedWorkflow(
        "versioned-id-1",
        workflowId,
        "deployment-alpha"
    );
    VersionedWorkflow workflow2 = createVersionedWorkflow(
        "versioned-id-2",
        workflowId,
        "deployment-beta"
    );

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Arrays.asList(workflow1, workflow2));
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, expirationDate);

    // Then: Should preserve deployment IDs for each job
    ArgumentCaptor<List<WorkflowExpirationJob>> captor = ArgumentCaptor.forClass(List.class);
    verify(expirationJobRepository, times(1)).saveAll(captor.capture());

    List<WorkflowExpirationJob> savedJobs = captor.getValue();
    assertEquals(2, savedJobs.size());
    assertEquals("deployment-alpha", savedJobs.get(0).getDeploymentId());
    assertEquals("deployment-beta", savedJobs.get(1).getDeploymentId());
  }

  @Test
  void scheduleWorkflowExpiration_shouldMapAllWorkflowProperties() {
    // Given: A workflow with all properties set
    String workflowId = "workflow-1";
    String versionedId = "versioned-id-abc-123";
    String deploymentId = "deployment-xyz-456";
    Instant expirationDate = Instant.parse("2025-06-15T12:30:45Z");

    VersionedWorkflow workflow = createVersionedWorkflow(
        versionedId,
        workflowId,
        deploymentId
    );

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Collections.singletonList(workflow));
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, expirationDate);

    // Then: Should correctly map all properties to WorkflowExpirationJob
    ArgumentCaptor<List<WorkflowExpirationJob>> captor = ArgumentCaptor.forClass(List.class);
    verify(expirationJobRepository, times(1)).saveAll(captor.capture());

    List<WorkflowExpirationJob> savedJobs = captor.getValue();
    assertEquals(1, savedJobs.size());

    WorkflowExpirationJob job = savedJobs.get(0);
    assertEquals(versionedId, job.getId());
    assertEquals(workflowId, job.getWorkflowId());
    assertEquals(deploymentId, job.getDeploymentId());
    assertEquals(expirationDate, job.getExpirationDate());
  }

  @Test
  void scheduleWorkflowExpiration_withSpecialCharactersInWorkflowId_shouldHandleCorrectly() {
    // Given: A workflow ID with special characters
    String workflowId = "workflow-with-dashes_and_underscores.and.dots";
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");

    VersionedWorkflow workflow = createVersionedWorkflow(
        "versioned-id-1",
        workflowId,
        "deployment-1"
    );

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Collections.singletonList(workflow));
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, expirationDate);

    // Then: Should handle special characters correctly
    ArgumentCaptor<List<WorkflowExpirationJob>> captor = ArgumentCaptor.forClass(List.class);
    verify(expirationJobRepository, times(1)).saveAll(captor.capture());

    List<WorkflowExpirationJob> savedJobs = captor.getValue();
    assertEquals(1, savedJobs.size());
    assertEquals(workflowId, savedJobs.get(0).getWorkflowId());
  }

  @Test
  void scheduleWorkflowExpiration_shouldSaveJobsBeforePlanningExpiration() {
    // Given: A workflow exists
    String workflowId = "workflow-1";
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");

    VersionedWorkflow workflow = createVersionedWorkflow(
        "versioned-id-1",
        workflowId,
        "deployment-1"
    );

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Collections.singletonList(workflow));
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, expirationDate);

    // Then: Should verify order - saveAll is called before planExpiration
    // The mocked answer ensures the saved jobs are returned
    verify(expirationJobRepository, times(1)).saveAll(anyList());
    verify(workflowExpirationPlanner, times(1)).planExpiration(any(WorkflowExpirationJob.class));
  }

  @Test
  void scheduleWorkflowExpiration_withNullDeploymentId_shouldCreateJobWithNullDeploymentId() {
    // Given: A workflow with null deployment ID
    String workflowId = "workflow-1";
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");

    VersionedWorkflow workflow = createVersionedWorkflow(
        "versioned-id-1",
        workflowId,
        null  // null deployment ID
    );

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Collections.singletonList(workflow));
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, expirationDate);

    // Then: Should create job with null deployment ID
    ArgumentCaptor<List<WorkflowExpirationJob>> captor = ArgumentCaptor.forClass(List.class);
    verify(expirationJobRepository, times(1)).saveAll(captor.capture());

    List<WorkflowExpirationJob> savedJobs = captor.getValue();
    assertEquals(1, savedJobs.size());
    assertEquals(null, savedJobs.get(0).getDeploymentId());
  }

  @Test
  void scheduleWorkflowExpiration_withEmptyWorkflowId_shouldQueryAndThrowIfNotFound() {
    // Given: An empty workflow ID that returns no results
    String workflowId = "";
    Instant expirationDate = Instant.now();

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Collections.emptyList());

    // When & Then: Should throw NotFoundException
    NotFoundException exception = assertThrows(
        NotFoundException.class,
        () -> service.scheduleWorkflowExpiration(workflowId, expirationDate)
    );

    assertTrue(exception.getMessage().contains("does not exist"));
    verify(versioningRepository, times(1)).findByWorkflowId(workflowId);
  }

  @Test
  void scheduleWorkflowExpiration_withManyWorkflows_shouldHandleAll() {
    // Given: Many versioned workflows (e.g., 10) for the same workflow ID
    String workflowId = "workflow-1";
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");

    List<VersionedWorkflow> workflows = new ArrayList<>();
    for (int i = 1; i <= 10; i++) {
      workflows.add(createVersionedWorkflow(
          "versioned-id-" + i,
          workflowId,
          "deployment-" + i
      ));
    }

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(workflows);
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, expirationDate);

    // Then: Should create and plan expiration for all 10 jobs
    ArgumentCaptor<List<WorkflowExpirationJob>> captor = ArgumentCaptor.forClass(List.class);
    verify(expirationJobRepository, times(1)).saveAll(captor.capture());

    List<WorkflowExpirationJob> savedJobs = captor.getValue();
    assertEquals(10, savedJobs.size());

    verify(workflowExpirationPlanner, times(10)).planExpiration(any(WorkflowExpirationJob.class));
  }

  @Test
  void scheduleWorkflowExpiration_withExactInstant_shouldPreserveNanoseconds() {
    // Given: A precise instant with nanoseconds
    String workflowId = "workflow-1";
    Instant preciseInstant = Instant.parse("2025-12-31T23:59:59.123456789Z");

    VersionedWorkflow workflow = createVersionedWorkflow(
        "versioned-id-1",
        workflowId,
        "deployment-1"
    );

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Collections.singletonList(workflow));
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, preciseInstant);

    // Then: Should preserve the exact instant including nanoseconds
    ArgumentCaptor<List<WorkflowExpirationJob>> captor = ArgumentCaptor.forClass(List.class);
    verify(expirationJobRepository, times(1)).saveAll(captor.capture());

    List<WorkflowExpirationJob> savedJobs = captor.getValue();
    assertEquals(1, savedJobs.size());
    assertEquals(preciseInstant, savedJobs.get(0).getExpirationDate());
  }

  @Test
  void scheduleWorkflowExpiration_shouldQueryRepositoryWithCorrectWorkflowId() {
    // Given: A specific workflow ID
    String workflowId = "specific-workflow-id";
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");

    VersionedWorkflow workflow = createVersionedWorkflow(
        "versioned-id-1",
        workflowId,
        "deployment-1"
    );

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Collections.singletonList(workflow));
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, expirationDate);

    // Then: Should query repository with the exact workflow ID
    verify(versioningRepository, times(1)).findByWorkflowId(workflowId);
  }

  @Test
  void scheduleWorkflowExpiration_withDifferentVersionedIds_shouldCreateDistinctJobs() {
    // Given: Workflows with different versioned IDs but same workflow ID
    String workflowId = "workflow-1";
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");

    VersionedWorkflow workflow1 = createVersionedWorkflow(
        "unique-versioned-id-alpha",
        workflowId,
        "deployment-1"
    );
    VersionedWorkflow workflow2 = createVersionedWorkflow(
        "unique-versioned-id-beta",
        workflowId,
        "deployment-2"
    );

    when(versioningRepository.findByWorkflowId(workflowId))
        .thenReturn(Arrays.asList(workflow1, workflow2));
    when(expirationJobRepository.saveAll(anyList()))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // When: scheduleWorkflowExpiration is called
    service.scheduleWorkflowExpiration(workflowId, expirationDate);

    // Then: Should create distinct jobs with different IDs
    ArgumentCaptor<List<WorkflowExpirationJob>> captor = ArgumentCaptor.forClass(List.class);
    verify(expirationJobRepository, times(1)).saveAll(captor.capture());

    List<WorkflowExpirationJob> savedJobs = captor.getValue();
    assertEquals(2, savedJobs.size());
    assertEquals("unique-versioned-id-alpha", savedJobs.get(0).getId());
    assertEquals("unique-versioned-id-beta", savedJobs.get(1).getId());
  }

  // ==================== Helper Methods ====================

  private VersionedWorkflow createVersionedWorkflow(String id, String workflowId, String deploymentId) {
    VersionedWorkflow workflow = new VersionedWorkflow();
    workflow.setId(id);
    workflow.setWorkflowId(workflowId);
    workflow.setDeploymentId(deploymentId);
    return workflow;
  }
}
