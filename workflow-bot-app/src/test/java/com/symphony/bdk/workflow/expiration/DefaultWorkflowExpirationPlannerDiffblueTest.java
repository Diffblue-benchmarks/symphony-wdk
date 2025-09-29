package com.symphony.bdk.workflow.expiration;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.management.repository.domain.WorkflowExpirationJob;
import com.symphony.bdk.workflow.scheduled.RunnableScheduledJob;
import com.symphony.bdk.workflow.scheduled.ScheduledJobsRegistry;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultWorkflowExpirationPlannerDiffblueTest {
  @InjectMocks private DefaultWorkflowExpirationPlanner defaultWorkflowExpirationPlanner;

  @Mock private ScheduledJobsRegistry scheduledJobsRegistry;

  /**
   * Test {@link DefaultWorkflowExpirationPlanner#planExpiration(WorkflowExpirationJob)}.
   *
   * <p>Method under test: {@link
   * DefaultWorkflowExpirationPlanner#planExpiration(WorkflowExpirationJob)}
   */
  @Test
  @DisplayName("Test planExpiration(WorkflowExpirationJob)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultWorkflowExpirationPlanner.planExpiration(WorkflowExpirationJob)"})
  void testPlanExpiration() {
    // Arrange
    doNothing().when(scheduledJobsRegistry).scheduleJob(Mockito.<RunnableScheduledJob>any());

    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("42");
    workflowExpirationJob.setExpirationDate(
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("42");
    workflowExpirationJob.setWorkflowId("42");

    // Act
    defaultWorkflowExpirationPlanner.planExpiration(workflowExpirationJob);

    // Assert
    verify(scheduledJobsRegistry).scheduleJob(isA(RunnableScheduledJob.class));
  }
}
