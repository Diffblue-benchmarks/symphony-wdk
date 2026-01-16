package com.symphony.bdk.workflow.expiration;

import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.WorkflowExpirationJobRepository;
import com.symphony.bdk.workflow.management.repository.domain.WorkflowExpirationJob;
import com.symphony.bdk.workflow.scheduled.RunnableScheduledJob;
import com.symphony.bdk.workflow.scheduled.ScheduledJobsRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class DefaultWorkflowExpirationPlannerClaudeTest {

  @Mock
  private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  @Mock
  private WorkflowExpirationJobRepository expirationJobRepository;

  @Mock
  private VersionedWorkflowRepository versioningRepository;

  @Mock
  private ScheduledJobsRegistry scheduledJobsRegistry;

  private DefaultWorkflowExpirationPlanner planner;

  @BeforeEach
  void setUp() {
    planner = new DefaultWorkflowExpirationPlanner(
        workflowEngine,
        expirationJobRepository,
        versioningRepository,
        scheduledJobsRegistry
    );
  }

  // ==================== planExpiration Tests ====================

  @Test
  void planExpiration_withFutureExpirationDate_shouldScheduleJob() {
    // Given: A workflow expiration job with future expiration date
    Instant futureDate = Instant.now().plus(Duration.ofHours(2));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "deployment-1",
        futureDate
    );

    // When: planExpiration is called
    planner.planExpiration(job);

    // Then: Should schedule a job in the registry
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    assertNotNull(scheduledJob);
    assertNotNull(scheduledJob.getId());
    assertEquals("workflow-1." + futureDate, scheduledJob.getId().id());
    assertTrue(scheduledJob.getDelay() > 0);
  }

  @Test
  void planExpiration_shouldCreateJobIdWithCorrectFormat() {
    // Given: A workflow expiration job
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "my-workflow",
        "deployment-1",
        expirationDate
    );

    // When: planExpiration is called
    planner.planExpiration(job);

    // Then: Should schedule a job with ID in format "workflowId.expirationDate"
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    assertEquals("my-workflow.2025-12-31T23:59:59Z", scheduledJob.getId().id());
  }

  @Test
  void planExpiration_shouldCalculateCorrectDelay() {
    // Given: A workflow expiration job expiring in 1 hour
    Instant futureDate = Instant.now().plus(Duration.ofHours(1));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "deployment-1",
        futureDate
    );

    // When: planExpiration is called
    planner.planExpiration(job);

    // Then: Should schedule a job with delay approximately 3600 seconds
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    // Allow some variance due to execution time (within 10 seconds)
    assertTrue(scheduledJob.getDelay() >= 3590 && scheduledJob.getDelay() <= 3610,
        "Expected delay around 3600 seconds, but was: " + scheduledJob.getDelay());
  }

  @Test
  void planExpiration_whenJobExecuted_shouldDeleteFromVersioningRepository() {
    // Given: A workflow expiration job
    Instant futureDate = Instant.now().plus(Duration.ofSeconds(1));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "deployment-1",
        futureDate
    );

    // When: planExpiration is called and the job is executed
    planner.planExpiration(job);
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    scheduledJob.run(); // Execute the scheduled job

    // Then: Should delete from versioning repository
    verify(versioningRepository, times(1)).deleteById("versioned-id-1");
  }

  @Test
  void planExpiration_whenJobExecuted_shouldDeleteFromExpirationJobRepository() {
    // Given: A workflow expiration job
    Instant futureDate = Instant.now().plus(Duration.ofSeconds(1));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "deployment-1",
        futureDate
    );

    // When: planExpiration is called and the job is executed
    planner.planExpiration(job);
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    scheduledJob.run(); // Execute the scheduled job

    // Then: Should delete from expiration job repository
    verify(expirationJobRepository, times(1)).deleteById("versioned-id-1");
  }

  @Test
  void planExpiration_whenJobExecuted_shouldUndeployFromWorkflowEngine() {
    // Given: A workflow expiration job
    Instant futureDate = Instant.now().plus(Duration.ofSeconds(1));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "deployment-1",
        futureDate
    );

    // When: planExpiration is called and the job is executed
    planner.planExpiration(job);
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    scheduledJob.run(); // Execute the scheduled job

    // Then: Should undeploy from workflow engine using deployment ID
    verify(workflowEngine, times(1)).undeployByDeploymentId("deployment-1");
  }

  @Test
  void planExpiration_whenJobExecuted_shouldPerformAllCleanupOperations() {
    // Given: A workflow expiration job
    Instant futureDate = Instant.now().plus(Duration.ofSeconds(1));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "deployment-1",
        futureDate
    );

    // When: planExpiration is called and the job is executed
    planner.planExpiration(job);
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    scheduledJob.run(); // Execute the scheduled job

    // Then: Should perform all cleanup operations in correct order
    verify(versioningRepository, times(1)).deleteById("versioned-id-1");
    verify(expirationJobRepository, times(1)).deleteById("versioned-id-1");
    verify(workflowEngine, times(1)).undeployByDeploymentId("deployment-1");
  }

  @Test
  void planExpiration_beforeJobExecuted_shouldNotPerformAnyCleanup() {
    // Given: A workflow expiration job
    Instant futureDate = Instant.now().plus(Duration.ofHours(1));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "deployment-1",
        futureDate
    );

    // When: planExpiration is called but job is not executed
    planner.planExpiration(job);

    // Then: Should only schedule the job, not perform cleanup
    verify(scheduledJobsRegistry, times(1)).scheduleJob(any(RunnableScheduledJob.class));
    verify(versioningRepository, never()).deleteById(any());
    verify(expirationJobRepository, never()).deleteById(any());
    verify(workflowEngine, never()).undeployByDeploymentId(any());
  }

  @Test
  void planExpiration_withPastExpirationDate_shouldScheduleJobWithNegativeDelay() {
    // Given: A workflow expiration job with past expiration date
    Instant pastDate = Instant.now().minus(Duration.ofHours(1));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "deployment-1",
        pastDate
    );

    // When: planExpiration is called
    planner.planExpiration(job);

    // Then: Should schedule a job with negative delay
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    assertTrue(scheduledJob.getDelay() < 0, "Expected negative delay for past expiration date");
  }

  @Test
  void planExpiration_withSpecialCharactersInWorkflowId_shouldCreateCorrectJobId() {
    // Given: A workflow with special characters in ID
    Instant expirationDate = Instant.parse("2025-12-31T23:59:59Z");
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-with-dashes_and_underscores.and.dots",
        "deployment-1",
        expirationDate
    );

    // When: planExpiration is called
    planner.planExpiration(job);

    // Then: Should create job ID with special characters preserved
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    assertEquals("workflow-with-dashes_and_underscores.and.dots.2025-12-31T23:59:59Z",
        scheduledJob.getId().id());
  }

  @Test
  void planExpiration_withMultipleJobs_shouldScheduleEachJobIndependently() {
    // Given: Multiple workflow expiration jobs
    Instant date1 = Instant.now().plus(Duration.ofHours(1));
    Instant date2 = Instant.now().plus(Duration.ofHours(2));
    WorkflowExpirationJob job1 = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "deployment-1",
        date1
    );
    WorkflowExpirationJob job2 = new WorkflowExpirationJob(
        "versioned-id-2",
        "workflow-2",
        "deployment-2",
        date2
    );

    // When: planExpiration is called for both jobs
    planner.planExpiration(job1);
    planner.planExpiration(job2);

    // Then: Should schedule both jobs independently
    verify(scheduledJobsRegistry, times(2)).scheduleJob(any(RunnableScheduledJob.class));
  }

  @Test
  void planExpiration_withSameWorkflowIdDifferentVersions_shouldScheduleSeparateJobs() {
    // Given: Two jobs for the same workflow ID (different versions)
    Instant date1 = Instant.parse("2025-12-31T23:59:59Z");
    Instant date2 = Instant.parse("2025-11-30T23:59:59Z");
    WorkflowExpirationJob job1 = new WorkflowExpirationJob(
        "workflow-1-v1",
        "workflow-1",
        "deployment-1",
        date1
    );
    WorkflowExpirationJob job2 = new WorkflowExpirationJob(
        "workflow-1-v2",
        "workflow-1",
        "deployment-2",
        date2
    );

    // When: planExpiration is called for both jobs
    planner.planExpiration(job1);
    planner.planExpiration(job2);

    // Then: Should schedule both jobs with different IDs
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(2)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob1 = captor.getAllValues().get(0);
    RunnableScheduledJob scheduledJob2 = captor.getAllValues().get(1);
    assertEquals("workflow-1.2025-12-31T23:59:59Z", scheduledJob1.getId().id());
    assertEquals("workflow-1.2025-11-30T23:59:59Z", scheduledJob2.getId().id());
  }

  @Test
  void planExpiration_withVeryShortDelay_shouldScheduleJob() {
    // Given: A workflow expiring in 1 second
    Instant nearFuture = Instant.now().plus(Duration.ofSeconds(1));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "deployment-1",
        nearFuture
    );

    // When: planExpiration is called
    planner.planExpiration(job);

    // Then: Should schedule the job
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    assertTrue(scheduledJob.getDelay() <= 2, "Expected delay around 1 second");
  }

  @Test
  void planExpiration_withVeryLongDelay_shouldScheduleJob() {
    // Given: A workflow expiring in far future (1 year)
    Instant farFuture = Instant.now().plus(Duration.ofDays(365));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "deployment-1",
        farFuture
    );

    // When: planExpiration is called
    planner.planExpiration(job);

    // Then: Should schedule the job with large delay
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    long expectedDelay = Duration.ofDays(365).getSeconds();
    // Allow 10 second variance
    assertTrue(scheduledJob.getDelay() >= expectedDelay - 10 && scheduledJob.getDelay() <= expectedDelay + 10,
        "Expected delay around " + expectedDelay + " seconds");
  }

  @Test
  void planExpiration_whenJobExecuted_shouldUseCorrectRepositoryIds() {
    // Given: A workflow expiration job with specific ID
    Instant futureDate = Instant.now().plus(Duration.ofSeconds(1));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "custom-versioned-id",
        "workflow-1",
        "deployment-1",
        futureDate
    );

    // When: planExpiration is called and job is executed
    planner.planExpiration(job);
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    scheduledJob.run();

    // Then: Should use the correct ID for both repository deletions
    verify(versioningRepository, times(1)).deleteById("custom-versioned-id");
    verify(expirationJobRepository, times(1)).deleteById("custom-versioned-id");
  }

  @Test
  void planExpiration_whenJobExecuted_shouldUseCorrectDeploymentId() {
    // Given: A workflow expiration job with specific deployment ID
    Instant futureDate = Instant.now().plus(Duration.ofSeconds(1));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "custom-deployment-id-123",
        futureDate
    );

    // When: planExpiration is called and job is executed
    planner.planExpiration(job);
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    scheduledJob.run();

    // Then: Should use the correct deployment ID for undeployment
    verify(workflowEngine, times(1)).undeployByDeploymentId("custom-deployment-id-123");
  }

  @Test
  void planExpiration_shouldOnlyInteractWithScheduledJobsRegistryDuringScheduling() {
    // Given: A workflow expiration job
    Instant futureDate = Instant.now().plus(Duration.ofHours(1));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "deployment-1",
        futureDate
    );

    // When: planExpiration is called
    planner.planExpiration(job);

    // Then: Should only interact with scheduledJobsRegistry
    verify(scheduledJobsRegistry, times(1)).scheduleJob(any(RunnableScheduledJob.class));
    verifyNoInteractions(workflowEngine);
    verifyNoInteractions(versioningRepository);
    verifyNoInteractions(expirationJobRepository);
  }

  @Test
  void planExpiration_withExpirationDateAtExactSecond_shouldCalculateCorrectDelay() {
    // Given: A workflow with expiration at exact second boundary
    Instant exactSecond = Instant.parse("2025-12-31T23:59:59Z");
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "versioned-id-1",
        "workflow-1",
        "deployment-1",
        exactSecond
    );

    // When: planExpiration is called
    planner.planExpiration(job);

    // Then: Should schedule a job with the calculated delay
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    assertNotNull(scheduledJob);
    long expectedDelay = Duration.between(Instant.now(), exactSecond).getSeconds();
    // The actual delay should be close to expected (within a few seconds due to execution time)
    assertTrue(Math.abs(scheduledJob.getDelay() - expectedDelay) < 5,
        "Delay calculation should be within 5 seconds of expected value");
  }

  @Test
  void planExpiration_withJobContainingAllValidFields_shouldScheduleSuccessfully() {
    // Given: A complete workflow expiration job
    Instant expirationDate = Instant.now().plus(Duration.ofMinutes(30));
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "complete-versioned-id",
        "complete-workflow-id",
        "complete-deployment-id",
        expirationDate
    );

    // When: planExpiration is called
    planner.planExpiration(job);

    // Then: Should successfully schedule the job
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry, times(1)).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    assertNotNull(scheduledJob);
    assertNotNull(scheduledJob.getId());
    assertEquals("complete-workflow-id." + expirationDate, scheduledJob.getId().id());
    assertTrue(scheduledJob.getDelay() > 0);
  }
}
