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

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultWorkflowExpirationPlannerTest {

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
    planner = new DefaultWorkflowExpirationPlanner(workflowEngine, expirationJobRepository,
        versioningRepository, scheduledJobsRegistry);
  }

  @Test
  void shouldPlanExpirationWithCorrectScheduledJob() {
    Instant expirationDate = Instant.now().plus(1, ChronoUnit.HOURS);
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "workflow-id-123",
        "workflow-123",
        "deployment-456",
        expirationDate
    );

    planner.planExpiration(job);

    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    assertThat(scheduledJob.getId().id()).isEqualTo("workflow-123." + expirationDate);
    assertThat(scheduledJob.getDelay()).isGreaterThan(0);
  }

  @Test
  void shouldExecuteCleanupWhenScheduledJobRuns() {
    Instant expirationDate = Instant.now().plus(1, ChronoUnit.HOURS);
    WorkflowExpirationJob job = new WorkflowExpirationJob(
        "workflow-id-123",
        "workflow-123",
        "deployment-456",
        expirationDate
    );

    planner.planExpiration(job);

    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry).scheduleJob(captor.capture());

    RunnableScheduledJob scheduledJob = captor.getValue();
    scheduledJob.run();

    verify(versioningRepository).deleteById("workflow-id-123");
    verify(expirationJobRepository).deleteById("workflow-id-123");
    verify(workflowEngine).undeployByDeploymentId("deployment-456");
  }
}
