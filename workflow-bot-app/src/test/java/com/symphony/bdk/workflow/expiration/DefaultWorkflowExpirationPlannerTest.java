package com.symphony.bdk.workflow.expiration;

import com.symphony.bdk.workflow.engine.WorkflowEngine;
import com.symphony.bdk.workflow.engine.camunda.CamundaTranslatedWorkflowContext;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.WorkflowExpirationJobRepository;
import com.symphony.bdk.workflow.management.repository.domain.WorkflowExpirationJob;
import com.symphony.bdk.workflow.scheduled.RunnableScheduledJob;
import com.symphony.bdk.workflow.scheduled.ScheduledJobsRegistry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DefaultWorkflowExpirationPlannerTest {

  @Mock
  private WorkflowEngine<CamundaTranslatedWorkflowContext> workflowEngine;

  @Mock
  private WorkflowExpirationJobRepository expirationJobRepository;

  @Mock
  private VersionedWorkflowRepository versioningRepository;

  @Mock
  private ScheduledJobsRegistry scheduledJobsRegistry;

  @InjectMocks
  private DefaultWorkflowExpirationPlanner defaultWorkflowExpirationPlanner;

  @Test
  void planExpirationTest() {
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setWorkflowId("workflowId");
    workflowExpirationJob.setId("id");
    workflowExpirationJob.setDeploymentId("deploymentId");
    workflowExpirationJob.setExpirationDate(Instant.now());

    defaultWorkflowExpirationPlanner.planExpiration(workflowExpirationJob);
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);

    verify(scheduledJobsRegistry).scheduleJob(captor.capture());
    assertThat(captor.getValue()).isNotNull();
  }

  @Test
  void planExpiration_schedulesJobWithCorrectIdAndRunsActions() {
    // Arrange
    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setWorkflowId("wf-id");
    workflowExpirationJob.setId("job-id");
    workflowExpirationJob.setDeploymentId("deploy-id");
    Instant expiration = Instant.now().plus(10, ChronoUnit.SECONDS);
    workflowExpirationJob.setExpirationDate(expiration);

    // Act
    defaultWorkflowExpirationPlanner.planExpiration(workflowExpirationJob);
    ArgumentCaptor<RunnableScheduledJob> captor = ArgumentCaptor.forClass(RunnableScheduledJob.class);
    verify(scheduledJobsRegistry).scheduleJob(captor.capture());
    RunnableScheduledJob scheduledJob = captor.getValue();

    // Assert scheduled job id contains workflow id and expiration date
    String jobId = scheduledJob.getId().id();
    assertThat(jobId).contains("wf-id").contains(expiration.toString());

    // Assert running the job triggers repository and engine calls
    scheduledJob.run();
    verify(versioningRepository).deleteById("job-id");
    verify(expirationJobRepository).deleteById("job-id");
    verify(workflowEngine).undeployByDeploymentId("deploy-id");
  }
}
