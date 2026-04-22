package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.converter.BiConverter;
import com.symphony.bdk.workflow.converter.Converter;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain;

import org.camunda.bpm.engine.impl.HistoryServiceImpl;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.RuntimeServiceImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class WorkflowCmdaApiQueryRepositoryDiffblueTest {

  /**
   * Test {@link WorkflowCmdaApiQueryRepository#WorkflowCmdaApiQueryRepository(
   * org.camunda.bpm.engine.RepositoryService, org.camunda.bpm.engine.HistoryService,
   * org.camunda.bpm.engine.RuntimeService, com.symphony.bdk.workflow.converter.ObjectConverter)}.
   */
  @Test
  @DisplayName("Test WorkflowCmdaApiQueryRepository constructor")
  void testConstructor() {
    // Arrange
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(converters, optionalBiConverters);

    // Act
    WorkflowCmdaApiQueryRepository repository =
        new WorkflowCmdaApiQueryRepository(repositoryService, historyService, runtimeService, objectConverter);

    // Assert
    assertNotNull(repository);
  }

  /**
   * Test {@link WorkflowCmdaApiQueryRepository#findAll()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   */
  @Test
  @DisplayName("Test findAll(); then return Empty")
  void testFindAll_thenReturnEmpty() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(null);

    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    repositoryService.setCommandExecutor(commandExecutor);
    HistoryServiceImpl historyService = new HistoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());
    DefaultObjectConverter objectConverter = new DefaultObjectConverter(converters, optionalBiConverters);

    WorkflowCmdaApiQueryRepository repository =
        new WorkflowCmdaApiQueryRepository(repositoryService, historyService, runtimeService, objectConverter);

    // Act
    List<WorkflowDomain> result = repository.findAll();

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertTrue(result.isEmpty());
  }
}
