package com.symphony.bdk.workflow.configuration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.core.retry.RetryWithRecoveryBuilder;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.gen.api.SessionApi;
import com.symphony.bdk.workflow.converter.BiConverter;
import com.symphony.bdk.workflow.converter.Converter;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.engine.camunda.CamundaEngine;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;
import com.symphony.bdk.workflow.management.repository.VersionedWorkflowRepository;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WorkflowDeployerDiffblueTest {
  /**
   * Test {@link WorkflowDeployer#addAllWorkflowsFromFolder(Path)}.
   * <ul>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowDeployer#addAllWorkflowsFromFolder(Path)}
   */
  @Test
  @DisplayName("Test addAllWorkflowsFromFolder(Path); then throw IllegalArgumentException")
  void testAddAllWorkflowsFromFolder_thenThrowIllegalArgumentException() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RepositoryServiceImpl repositoryService2 = new RepositoryServiceImpl();
    WorkflowNodeBpmnBuilderRegistry builderFactory = new WorkflowNodeBpmnBuilderRegistry(new ArrayList<>());
    SessionService sessionService = new SessionService(null, new RetryWithRecoveryBuilder<>());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository = Optional.empty();
    CamundaBpmnBuilder bpmnBuilder = new CamundaBpmnBuilder(repositoryService2, builderFactory, sessionService,
        new WorkflowDirectedGraphService(versionedWorkflowRepository, null, null));

    ArrayList<RealTimeEventProcessor<?>> processors = new ArrayList<>();
    CamundaEngine workflowEngine = new CamundaEngine(repositoryService, bpmnBuilder, processors,
        new AuditTrailLogAction());

    Optional<VersionedWorkflowRepository> versionedWorkflowRepository2 = Optional
        .of(mock(VersionedWorkflowRepository.class));
    SessionApi sessionApi = new SessionApi(null);
    SessionService sessionService2 = new SessionService(sessionApi, new RetryWithRecoveryBuilder<>());

    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> (new WorkflowDeployer(workflowEngine,
            new WorkflowDirectedGraphService(versionedWorkflowRepository2, sessionService2,
                new DefaultObjectConverter(converters, optionalBiConverters))))
            .addAllWorkflowsFromFolder(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt")));
  }
}
