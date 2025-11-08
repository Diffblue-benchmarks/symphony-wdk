package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.HistoricVariableInstanceQueryImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VariableCmdaApiQueryRepository.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class VariableCmdaApiQueryRepositoryDiffblueTest {
  @MockBean
  private HistoryService historyService;

  @MockBean
  private ObjectConverter objectConverter;

  @MockBean
  private RepositoryService repositoryService;

  @MockBean
  private RuntimeService runtimeService;

  @Autowired
  private VariableCmdaApiQueryRepository variableCmdaApiQueryRepository;

  /**
   * Method under test:
   * {@link VariableCmdaApiQueryRepository#findVarsByWorkflowInstanceIdAndVarName(String, String)}
   */
  @Test
  void testFindVarsByWorkflowInstanceIdAndVarName() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(null);
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl = new HistoricVariableInstanceQueryImpl(
        commandExecutor);
    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQueryImpl);

    // Act
    VariablesDomain actualFindVarsByWorkflowInstanceIdAndVarNameResult = variableCmdaApiQueryRepository
        .findVarsByWorkflowInstanceIdAndVarName("42", "Name");

    // Assert
    verify(historyService).createHistoricVariableInstanceQuery();
    verify(commandExecutor).execute(isA(Command.class));
    assertNull(actualFindVarsByWorkflowInstanceIdAndVarNameResult.getUpdateTime());
    assertEquals(0, actualFindVarsByWorkflowInstanceIdAndVarNameResult.getRevision());
    assertTrue(actualFindVarsByWorkflowInstanceIdAndVarNameResult.getOutputs().isEmpty());
  }

  /**
   * Method under test:
   * {@link VariableCmdaApiQueryRepository#findVarsByWorkflowInstanceIdAndVarName(String, String)}
   */
  @Test
  void testFindVarsByWorkflowInstanceIdAndVarName2() {
    // Arrange
    HistoricVariableInstanceEntity historicVariableInstanceEntity = mock(HistoricVariableInstanceEntity.class);
    when(historicVariableInstanceEntity.getRevision()).thenReturn(1);
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    when(historicVariableInstanceEntity.getValue()).thenReturn(objectObjectMap);
    when(historicVariableInstanceEntity.getCreateTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(historicVariableInstanceEntity);
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl = new HistoricVariableInstanceQueryImpl(
        commandExecutor);
    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQueryImpl);

    // Act
    VariablesDomain actualFindVarsByWorkflowInstanceIdAndVarNameResult = variableCmdaApiQueryRepository
        .findVarsByWorkflowInstanceIdAndVarName("42", "Name");

    // Assert
    verify(historyService).createHistoricVariableInstanceQuery();
    verify(commandExecutor).execute(isA(Command.class));
    verify(historicVariableInstanceEntity).getCreateTime();
    verify(historicVariableInstanceEntity).getRevision();
    verify(historicVariableInstanceEntity).getValue();
    Instant updateTime = actualFindVarsByWorkflowInstanceIdAndVarNameResult.getUpdateTime();
    assertEquals(0, updateTime.getNano());
    assertEquals(0L, updateTime.getEpochSecond());
    assertEquals(1, actualFindVarsByWorkflowInstanceIdAndVarNameResult.getRevision());
    Map<String, Object> outputs = actualFindVarsByWorkflowInstanceIdAndVarNameResult.getOutputs();
    assertTrue(outputs.isEmpty());
    assertSame(objectObjectMap, outputs);
  }

  /**
   * Method under test:
   * {@link VariableCmdaApiQueryRepository#findGlobalVarsHistoryByWorkflowInstId(String, Instant, Instant)}
   */
  @Test
  void testFindGlobalVarsHistoryByWorkflowInstId() {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Object>>any())).thenReturn(null);
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl = new HistoricVariableInstanceQueryImpl(
        commandExecutor);
    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQueryImpl);
    Instant updatedBefore = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    List<VariablesDomain> actualFindGlobalVarsHistoryByWorkflowInstIdResult = variableCmdaApiQueryRepository
        .findGlobalVarsHistoryByWorkflowInstId("42", updatedBefore,
            LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Assert
    verify(historyService).createHistoricVariableInstanceQuery();
    verify(commandExecutor).execute(isA(Command.class));
    assertTrue(actualFindGlobalVarsHistoryByWorkflowInstIdResult.isEmpty());
  }
}
