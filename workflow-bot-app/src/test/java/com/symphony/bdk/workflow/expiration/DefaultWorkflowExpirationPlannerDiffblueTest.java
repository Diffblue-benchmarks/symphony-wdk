package com.symphony.bdk.workflow.expiration;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DefaultWorkflowExpirationPlannerDiffblueTest {
  /**
   * Method under test:
   * {@link DefaultWorkflowExpirationPlanner#planExpiration(WorkflowExpirationJob)}
   */
  @Test
  void testPlanExpiration() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ScheduledJobsRegistry scheduledJobsRegistry = mock(ScheduledJobsRegistry.class);
    doNothing().when(scheduledJobsRegistry).scheduleJob(Mockito.<RunnableScheduledJob>any());
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    DefaultWorkflowExpirationPlanner defaultWorkflowExpirationPlanner = new DefaultWorkflowExpirationPlanner(
        new CamundaEngine(repositoryService, bpmnBuilder, processors, new AuditTrailLogAction()),
        mock(WorkflowExpirationJobRepository.class), mock(VersionedWorkflowRepository.class), scheduledJobsRegistry);

    WorkflowExpirationJob workflowExpirationJob = new WorkflowExpirationJob();
    workflowExpirationJob.setDeploymentId("42");
    workflowExpirationJob.setExpirationDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    workflowExpirationJob.setId("42");
    workflowExpirationJob.setWorkflowId("42");

    // Act
    defaultWorkflowExpirationPlanner.planExpiration(workflowExpirationJob);

    // Assert
    verify(scheduledJobsRegistry).scheduleJob(isA(RunnableScheduledJob.class));
  }
}
