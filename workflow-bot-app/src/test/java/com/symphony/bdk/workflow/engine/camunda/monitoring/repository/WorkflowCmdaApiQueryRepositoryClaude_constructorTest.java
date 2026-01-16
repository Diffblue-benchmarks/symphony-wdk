package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for WorkflowCmdaApiQueryRepository constructor.
 * Tests the constructor initialization with various scenarios.
 */
@ExtendWith(MockitoExtension.class)
class WorkflowCmdaApiQueryRepositoryClaude_constructorTest {

  @Mock
  private RepositoryService repositoryService;

  @Mock
  private HistoryService historyService;

  @Mock
  private RuntimeService runtimeService;

  @Mock
  private ObjectConverter objectConverter;

  @Mock
  private ProcessDefinitionQuery processDefinitionQuery;

  // ==================== Constructor Tests ====================

  @Test
  void constructor_withAllDependencies_shouldInitializeSuccessfully() {
    // When: creating a new instance with all dependencies
    WorkflowCmdaApiQueryRepository repository = new WorkflowCmdaApiQueryRepository(
        repositoryService,
        historyService,
        runtimeService,
        objectConverter
    );

    // Then: repository should be created successfully
    assertThat(repository).isNotNull();
  }

  @Test
  void constructor_shouldAllowRepositoryToBeUsed() {
    // Given: a repository created with all dependencies
    WorkflowCmdaApiQueryRepository repository = new WorkflowCmdaApiQueryRepository(
        repositoryService,
        historyService,
        runtimeService,
        objectConverter
    );

    // Setup mocks for findAll method to verify dependencies are properly initialized
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.latestVersion()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(WorkflowDomain.class)))
        .thenReturn(Collections.emptyList());

    // When: calling a method that uses the injected dependencies
    List<WorkflowDomain> result = repository.findAll();

    // Then: the method should execute successfully, proving dependencies were properly initialized
    assertThat(result).isNotNull();
    verify(repositoryService).createProcessDefinitionQuery();
    verify(objectConverter).convertCollection(any(), eq(WorkflowDomain.class));
  }

  @Test
  void constructor_withValidDependencies_shouldPassDependenciesToParent() {
    // When: creating repository instance
    WorkflowCmdaApiQueryRepository repository = new WorkflowCmdaApiQueryRepository(
        repositoryService,
        historyService,
        runtimeService,
        objectConverter
    );

    // Then: repository should be properly initialized with dependencies
    // We verify this indirectly by ensuring the repository can be instantiated
    // and used without errors
    assertThat(repository).isNotNull();
    assertThat(repository).isInstanceOf(WorkflowCmdaApiQueryRepository.class);
    assertThat(repository).isInstanceOf(CamundaAbstractQueryRepository.class);
  }

  @Test
  void constructor_shouldCreateRepositoryThatImplementsWorkflowQueryRepository() {
    // When: creating a new repository
    WorkflowCmdaApiQueryRepository repository = new WorkflowCmdaApiQueryRepository(
        repositoryService,
        historyService,
        runtimeService,
        objectConverter
    );

    // Then: repository should implement the WorkflowQueryRepository interface
    assertThat(repository)
        .isInstanceOf(com.symphony.bdk.workflow.monitoring.repository.WorkflowQueryRepository.class);
  }

  @Test
  void constructor_shouldAllowMultipleInstances() {
    // When: creating multiple instances with the same dependencies
    WorkflowCmdaApiQueryRepository repository1 = new WorkflowCmdaApiQueryRepository(
        repositoryService,
        historyService,
        runtimeService,
        objectConverter
    );

    WorkflowCmdaApiQueryRepository repository2 = new WorkflowCmdaApiQueryRepository(
        repositoryService,
        historyService,
        runtimeService,
        objectConverter
    );

    // Then: both instances should be created successfully and be independent
    assertThat(repository1).isNotNull();
    assertThat(repository2).isNotNull();
    assertThat(repository1).isNotSameAs(repository2);
  }

  @Test
  void constructor_withDifferentDependencies_shouldCreateIndependentInstances() {
    // Given: two different sets of dependencies
    RepositoryService repositoryService2 = mock(RepositoryService.class);
    HistoryService historyService2 = mock(HistoryService.class);
    RuntimeService runtimeService2 = mock(RuntimeService.class);
    ObjectConverter objectConverter2 = mock(ObjectConverter.class);

    // When: creating repositories with different dependencies
    WorkflowCmdaApiQueryRepository repository1 = new WorkflowCmdaApiQueryRepository(
        repositoryService,
        historyService,
        runtimeService,
        objectConverter
    );

    WorkflowCmdaApiQueryRepository repository2 = new WorkflowCmdaApiQueryRepository(
        repositoryService2,
        historyService2,
        runtimeService2,
        objectConverter2
    );

    // Then: both instances should be independent
    assertThat(repository1).isNotNull();
    assertThat(repository2).isNotNull();
    assertThat(repository1).isNotSameAs(repository2);
  }

  @Test
  void constructor_shouldPreserveRepositoryServiceFunctionality() {
    // Given: a repository with mocked dependencies
    WorkflowCmdaApiQueryRepository repository = new WorkflowCmdaApiQueryRepository(
        repositoryService,
        historyService,
        runtimeService,
        objectConverter
    );

    // Setup mock behavior
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.latestVersion()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(Arrays.asList(mock(org.camunda.bpm.engine.repository.ProcessDefinition.class)));
    when(objectConverter.convertCollection(any(), eq(WorkflowDomain.class)))
        .thenReturn(Arrays.asList(mock(WorkflowDomain.class)));

    // When: using the repository service through the repository
    List<WorkflowDomain> workflows = repository.findAll();

    // Then: the repository service should be called correctly
    assertThat(workflows).isNotNull();
    assertThat(workflows).hasSize(1);
    verify(repositoryService).createProcessDefinitionQuery();
  }

  @Test
  void constructor_shouldPreserveObjectConverterFunctionality() {
    // Given: a repository with mocked dependencies
    WorkflowCmdaApiQueryRepository repository = new WorkflowCmdaApiQueryRepository(
        repositoryService,
        historyService,
        runtimeService,
        objectConverter
    );

    List<WorkflowDomain> expectedWorkflows = Arrays.asList(
        mock(WorkflowDomain.class),
        mock(WorkflowDomain.class)
    );

    // Setup mock behavior
    when(repositoryService.createProcessDefinitionQuery()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.latestVersion()).thenReturn(processDefinitionQuery);
    when(processDefinitionQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(WorkflowDomain.class)))
        .thenReturn(expectedWorkflows);

    // When: using the object converter through the repository
    List<WorkflowDomain> workflows = repository.findAll();

    // Then: the object converter should be called correctly
    assertThat(workflows).isNotNull();
    assertThat(workflows).hasSize(2);
    verify(objectConverter).convertCollection(any(), eq(WorkflowDomain.class));
  }
}
