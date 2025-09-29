package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.converter.BiConverter;
import com.symphony.bdk.workflow.converter.Converter;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.camunda.bpm.engine.impl.HistoryServiceImpl;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.RuntimeServiceImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class VariableCmdaApiQueryRepositoryDiffblueTest {
  /**
   * Test {@link VariableCmdaApiQueryRepository#findGlobalVarsHistoryByWorkflowInstId(String,
   * Instant, Instant)}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * VariableCmdaApiQueryRepository#findGlobalVarsHistoryByWorkflowInstId(String, Instant, Instant)}
   */
  @Test
  @DisplayName(
      "Test findGlobalVarsHistoryByWorkflowInstId(String, Instant, Instant); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List VariableCmdaApiQueryRepository.findGlobalVarsHistoryByWorkflowInstId(String, Instant, Instant)"
  })
  void testFindGlobalVarsHistoryByWorkflowInstId_thenReturnEmpty() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(null);

    HistoryServiceImpl historyService = new HistoryServiceImpl();
    historyService.setCommandExecutor(commandExecutor);
    RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
    RuntimeServiceImpl runtimeService = new RuntimeServiceImpl();
    ArrayList<Converter> converters = new ArrayList<>();
    Optional<List<BiConverter>> optionalBiConverters = Optional.of(new ArrayList<>());

    DefaultObjectConverter objectConverter =
        new DefaultObjectConverter(converters, optionalBiConverters);

    VariableCmdaApiQueryRepository variableCmdaApiQueryRepository =
        new VariableCmdaApiQueryRepository(
            repositoryService, historyService, runtimeService, objectConverter);

    // Act
    List<VariablesDomain> actualFindGlobalVarsHistoryByWorkflowInstIdResult =
        variableCmdaApiQueryRepository.findGlobalVarsHistoryByWorkflowInstId("42", null, null);

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertTrue(actualFindGlobalVarsHistoryByWorkflowInstIdResult.isEmpty());
  }
}
