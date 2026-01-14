package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.converter.BiConverter;
import com.symphony.bdk.workflow.converter.Converter;
import com.symphony.bdk.workflow.converter.DefaultObjectConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
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
  @Tag("MaintainedByDiffblue")
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
        variableCmdaApiQueryRepository.findGlobalVarsHistoryByWorkflowInstId(
            "42", LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(), null);

    // Assert
    verify(commandExecutor).execute(isA(Command.class));
    assertTrue(actualFindGlobalVarsHistoryByWorkflowInstIdResult.isEmpty());
  }
}
