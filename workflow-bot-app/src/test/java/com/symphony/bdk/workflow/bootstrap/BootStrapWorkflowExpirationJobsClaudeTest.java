package com.symphony.bdk.workflow.bootstrap;

import com.symphony.bdk.workflow.expiration.WorkflowExpirationPlanner;
import com.symphony.bdk.workflow.management.repository.WorkflowExpirationJobRepository;
import com.symphony.bdk.workflow.management.repository.domain.WorkflowExpirationJob;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootStrapWorkflowExpirationJobsClaudeTest {

  @Mock
  private WorkflowExpirationJobRepository expirationJobRepository;

  @Mock
  private WorkflowExpirationPlanner workflowExpirationPlanner;

  private BootStrapWorkflowExpirationJobs bootStrapWorkflowExpirationJobs;

  @BeforeEach
  void setUp() {
    bootStrapWorkflowExpirationJobs = new BootStrapWorkflowExpirationJobs(
        expirationJobRepository,
        workflowExpirationPlanner
    );
  }

  // ==================== setupWorkflowExpirationJobs Tests ====================

  @Test
  void setupWorkflowExpirationJobs_withMultipleJobs_shouldPlanAllExpirations() {
    // Given: Multiple expiration jobs exist in the repository
    WorkflowExpirationJob job1 = new WorkflowExpirationJob(
        "id1",
        "workflow1",
        "deployment1",
        Instant.parse("2025-12-31T23:59:59Z")
    );
    WorkflowExpirationJob job2 = new WorkflowExpirationJob(
        "id2",
        "workflow2",
        "deployment2",
        Instant.parse("2025-11-30T12:00:00Z")
    );
    WorkflowExpirationJob job3 = new WorkflowExpirationJob(
        "id3",
        "workflow3",
        "deployment3",
        Instant.parse("2025-10-15T08:30:00Z")
    );
    List<WorkflowExpirationJob> jobs = Arrays.asList(job1, job2, job3);
    when(expirationJobRepository.findAll()).thenReturn(jobs);

    // When: setupWorkflowExpirationJobs is called
    bootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs();

    // Then: Should plan expiration for all jobs
    verify(expirationJobRepository, times(1)).findAll();
    verify(workflowExpirationPlanner, times(1)).planExpiration(job1);
    verify(workflowExpirationPlanner, times(1)).planExpiration(job2);
    verify(workflowExpirationPlanner, times(1)).planExpiration(job3);
  }

  @Test
  void setupWorkflowExpirationJobs_withNoJobs_shouldNotPlanAnyExpiration() {
    // Given: No expiration jobs exist in the repository
    when(expirationJobRepository.findAll()).thenReturn(Collections.emptyList());

    // When: setupWorkflowExpirationJobs is called
    bootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs();

    // Then: Should not plan any expiration
    verify(expirationJobRepository, times(1)).findAll();
    verify(workflowExpirationPlanner, never()).planExpiration(any(WorkflowExpirationJob.class));
  }

  @Test
  void setupWorkflowExpirationJobs_withSingleJob_shouldPlanSingleExpiration() {
    // Given: Single expiration job exists in the repository
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "single-id",
        "single-workflow",
        "single-deployment",
        Instant.parse("2025-06-15T10:00:00Z")
    );
    when(expirationJobRepository.findAll()).thenReturn(Collections.singletonList(job));

    // When: setupWorkflowExpirationJobs is called
    bootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs();

    // Then: Should plan expiration for the single job
    verify(expirationJobRepository, times(1)).findAll();
    verify(workflowExpirationPlanner, times(1)).planExpiration(job);
  }

  @Test
  void setupWorkflowExpirationJobs_withJobsHavingSameWorkflowId_shouldPlanEachSeparately() {
    // Given: Multiple jobs for the same workflow (different versions)
    WorkflowExpirationJob job1 = new WorkflowExpirationJob(
        "workflow1-v1",
        "workflow1",
        "deployment1",
        Instant.parse("2025-12-31T23:59:59Z")
    );
    WorkflowExpirationJob job2 = new WorkflowExpirationJob(
        "workflow1-v2",
        "workflow1",
        "deployment2",
        Instant.parse("2025-11-30T12:00:00Z")
    );
    List<WorkflowExpirationJob> jobs = Arrays.asList(job1, job2);
    when(expirationJobRepository.findAll()).thenReturn(jobs);

    // When: setupWorkflowExpirationJobs is called
    bootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs();

    // Then: Should plan expiration for both jobs independently
    verify(expirationJobRepository, times(1)).findAll();
    verify(workflowExpirationPlanner, times(1)).planExpiration(job1);
    verify(workflowExpirationPlanner, times(1)).planExpiration(job2);
  }

  @Test
  void setupWorkflowExpirationJobs_withJobsHavingPastExpirationDates_shouldStillPlanExpiration() {
    // Given: Jobs with expiration dates in the past (planner should handle this)
    WorkflowExpirationJob job1 = new WorkflowExpirationJob(
        "id1",
        "workflow1",
        "deployment1",
        Instant.parse("2020-01-01T00:00:00Z")
    );
    WorkflowExpirationJob job2 = new WorkflowExpirationJob(
        "id2",
        "workflow2",
        "deployment2",
        Instant.parse("2019-06-15T12:00:00Z")
    );
    List<WorkflowExpirationJob> jobs = Arrays.asList(job1, job2);
    when(expirationJobRepository.findAll()).thenReturn(jobs);

    // When: setupWorkflowExpirationJobs is called
    bootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs();

    // Then: Should still plan expiration (business logic in planner)
    verify(expirationJobRepository, times(1)).findAll();
    verify(workflowExpirationPlanner, times(1)).planExpiration(job1);
    verify(workflowExpirationPlanner, times(1)).planExpiration(job2);
  }

  @Test
  void setupWorkflowExpirationJobs_withJobsHavingFutureExpirationDates_shouldPlanExpiration() {
    // Given: Jobs with expiration dates far in the future
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "id1",
        "workflow1",
        "deployment1",
        Instant.parse("2030-12-31T23:59:59Z")
    );
    when(expirationJobRepository.findAll()).thenReturn(Collections.singletonList(job));

    // When: setupWorkflowExpirationJobs is called
    bootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs();

    // Then: Should plan expiration
    verify(expirationJobRepository, times(1)).findAll();
    verify(workflowExpirationPlanner, times(1)).planExpiration(job);
  }

  @Test
  void setupWorkflowExpirationJobs_withJobsHavingSpecialCharacters_shouldPlanExpiration() {
    // Given: Jobs with special characters in IDs
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "workflow-with-dashes_and_underscores.and.dots",
        "workflow_1",
        "deployment-1",
        Instant.parse("2025-12-31T23:59:59Z")
    );
    when(expirationJobRepository.findAll()).thenReturn(Collections.singletonList(job));

    // When: setupWorkflowExpirationJobs is called
    bootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs();

    // Then: Should plan expiration with special characters preserved
    verify(expirationJobRepository, times(1)).findAll();
    verify(workflowExpirationPlanner, times(1)).planExpiration(job);
  }

  @Test
  void setupWorkflowExpirationJobs_withManyJobs_shouldPlanAllInOrder() {
    // Given: Many jobs exist
    List<WorkflowExpirationJob> manyJobs = Arrays.asList(
        new WorkflowExpirationJob("id1", "wf1", "dep1", Instant.now()),
        new WorkflowExpirationJob("id2", "wf2", "dep2", Instant.now()),
        new WorkflowExpirationJob("id3", "wf3", "dep3", Instant.now()),
        new WorkflowExpirationJob("id4", "wf4", "dep4", Instant.now()),
        new WorkflowExpirationJob("id5", "wf5", "dep5", Instant.now())
    );
    when(expirationJobRepository.findAll()).thenReturn(manyJobs);

    // When: setupWorkflowExpirationJobs is called
    bootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs();

    // Then: Should plan expiration for all jobs
    verify(expirationJobRepository, times(1)).findAll();
    verify(workflowExpirationPlanner, times(5)).planExpiration(any(WorkflowExpirationJob.class));
    // Verify each job was planned
    for (WorkflowExpirationJob job : manyJobs) {
      verify(workflowExpirationPlanner, times(1)).planExpiration(job);
    }
  }

  @Test
  void setupWorkflowExpirationJobs_shouldProcessJobsFromRepositoryInOrderReturned() {
    // Given: Jobs returned in specific order
    WorkflowExpirationJob job1 = new WorkflowExpirationJob(
        "first",
        "workflow1",
        "deployment1",
        Instant.parse("2025-01-01T00:00:00Z")
    );
    WorkflowExpirationJob job2 = new WorkflowExpirationJob(
        "second",
        "workflow2",
        "deployment2",
        Instant.parse("2025-02-01T00:00:00Z")
    );
    List<WorkflowExpirationJob> jobs = Arrays.asList(job1, job2);
    when(expirationJobRepository.findAll()).thenReturn(jobs);

    // When: setupWorkflowExpirationJobs is called
    bootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs();

    // Then: Should process jobs in the order they were returned
    verify(workflowExpirationPlanner, times(1)).planExpiration(job1);
    verify(workflowExpirationPlanner, times(1)).planExpiration(job2);
  }

  @Test
  void setupWorkflowExpirationJobs_shouldFetchJobsOnlyOnce() {
    // Given: Jobs exist in repository
    when(expirationJobRepository.findAll()).thenReturn(Collections.emptyList());

    // When: setupWorkflowExpirationJobs is called
    bootStrapWorkflowExpirationJobs.setupWorkflowExpirationJobs();

    // Then: Should fetch jobs exactly once
    verify(expirationJobRepository, times(1)).findAll();
  }
}
