package com.symphony.bdk.workflow.expiration;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.retry.RetryWithRecoveryBuilder;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.engine.camunda.CamundaEngine;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import com.symphony.bdk.workflow.management.repository.WorkflowExpirationJobRepository;
import com.symphony.bdk.workflow.management.repository.domain.WorkflowExpirationJob;
import com.symphony.bdk.workflow.scheduled.RunnableScheduledJob;
import com.symphony.bdk.workflow.scheduled.ScheduledJobsRegistry;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Optional;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DefaultWorkflowExpirationPlannerDiffblueTest {
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
    ScheduledJobsRegistry scheduledJobsRegistry = mock(ScheduledJobsRegistry.class);
    doNothing().when(scheduledJobsRegistry).scheduleJob(Mockito.<RunnableScheduledJob>any());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory =
        new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());
    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    WorkflowDirectedGraphService directedGraphService =
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null);

    CamundaBpmnBuilder bpmnBuilder =
        new CamundaBpmnBuilder(
            repositoryService2, builderFactory, sessionService, directedGraphService);
    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();

    CamundaEngine workflowEngine =
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction());

    DefaultWorkflowExpirationPlanner defaultWorkflowExpirationPlanner =
        new DefaultWorkflowExpirationPlanner(
            workflowEngine,
            mock(WorkflowExpirationJobRepository.class),
            mock(VersionedWorkflowRepository.class),
            scheduledJobsRegistry);

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
