package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.converter.ObjectConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.VariablesDomain;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.history.HistoricDetail;
import org.camunda.bpm.engine.history.HistoricDetailQuery;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.camunda.bpm.engine.history.HistoricVariableInstanceQuery;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricVariableInstanceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

class VariableCmdaApiQueryRepositoryTest {

  private RepositoryService repositoryService;
  private HistoryService historyService;
  private RuntimeService runtimeService;
  private ObjectConverter objectConverter;
  private VariableCmdaApiQueryRepository underTest;

  @BeforeEach
  void setUp() {
    repositoryService = mock(RepositoryService.class);
    historyService = mock(HistoryService.class);
    runtimeService = mock(RuntimeService.class);
    objectConverter = mock(ObjectConverter.class);
    underTest = new VariableCmdaApiQueryRepository(repositoryService, historyService, runtimeService, objectConverter);
  }

  @Test
  void shouldFindVarsByWorkflowInstanceIdAndVarNameWhenVariableExists() {
    HistoricVariableInstanceQuery query = mock(HistoricVariableInstanceQuery.class);
    HistoricVariableInstanceEntity entity = mock(HistoricVariableInstanceEntity.class);
    Map<String, Object> outputs = Map.of("key", "value");
    Date createTime = new Date(1000L);

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(query);
    when(query.processInstanceId("instId")).thenReturn(query);
    when(query.variableName("varName")).thenReturn(query);
    when(query.singleResult()).thenReturn(entity);
    when(entity.getValue()).thenReturn(outputs);
    when(entity.getRevision()).thenReturn(3);
    when(entity.getCreateTime()).thenReturn(createTime);

    VariablesDomain result = underTest.findVarsByWorkflowInstanceIdAndVarName("instId", "varName");

    assertThat(result.getOutputs()).isEqualTo(outputs);
    assertThat(result.getRevision()).isEqualTo(3);
    assertThat(result.getUpdateTime()).isEqualTo(Instant.ofEpochMilli(1000L));
  }

  @Test
  void shouldFindVarsByWorkflowInstanceIdAndVarNameWhenVariableIsNull() {
    HistoricVariableInstanceQuery query = mock(HistoricVariableInstanceQuery.class);

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(query);
    when(query.processInstanceId("instId")).thenReturn(query);
    when(query.variableName("varName")).thenReturn(query);
    when(query.singleResult()).thenReturn(null);

    VariablesDomain result = underTest.findVarsByWorkflowInstanceIdAndVarName("instId", "varName");

    assertThat(result).isNotNull();
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void shouldReturnEmptyListWhenFindGlobalVarsAndVariablesIsNull() {
    HistoricVariableInstanceQuery query = mock(HistoricVariableInstanceQuery.class);

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(query);
    when(query.variableName("variables")).thenReturn(query);
    when(query.processInstanceId("instId")).thenReturn(query);
    when(query.singleResult()).thenReturn(null);

    List<VariablesDomain> result = underTest.findGlobalVarsHistoryByWorkflowInstId("instId", null, null);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldFindGlobalVarsHistoryByWorkflowInstIdWithNullDates() {
    HistoricVariableInstanceQuery varQuery = mock(HistoricVariableInstanceQuery.class);
    HistoricVariableInstance variable = mock(HistoricVariableInstance.class);
    HistoricDetailQuery detailQuery = mock(HistoricDetailQuery.class);
    List<HistoricDetail> details = Collections.emptyList();
    List<VariablesDomain> expected = Collections.emptyList();

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(varQuery);
    when(varQuery.variableName("variables")).thenReturn(varQuery);
    when(varQuery.processInstanceId("instId")).thenReturn(varQuery);
    when(varQuery.singleResult()).thenReturn(variable);
    when(variable.getId()).thenReturn("varId");

    when(historyService.createHistoricDetailQuery()).thenReturn(detailQuery);
    when(detailQuery.processInstanceId("instId")).thenReturn(detailQuery);
    when(detailQuery.variableInstanceId("varId")).thenReturn(detailQuery);
    when(detailQuery.orderByVariableRevision()).thenReturn(detailQuery);
    when(detailQuery.asc()).thenReturn(detailQuery);
    when(detailQuery.list()).thenReturn(details);
    when(objectConverter.convertCollection(details, HistoricDetail.class, VariablesDomain.class)).thenReturn(expected);

    List<VariablesDomain> result = underTest.findGlobalVarsHistoryByWorkflowInstId("instId", null, null);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  void shouldFindGlobalVarsHistoryByWorkflowInstIdWithBothDates() {
    HistoricVariableInstanceQuery varQuery = mock(HistoricVariableInstanceQuery.class);
    HistoricVariableInstance variable = mock(HistoricVariableInstance.class);
    HistoricDetailQuery detailQuery = mock(HistoricDetailQuery.class);
    Instant updatedBefore = Instant.ofEpochMilli(5000L);
    Instant updatedAfter = Instant.ofEpochMilli(1000L);
    List<HistoricDetail> details = Collections.emptyList();
    List<VariablesDomain> expected = Collections.emptyList();

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(varQuery);
    when(varQuery.variableName("variables")).thenReturn(varQuery);
    when(varQuery.processInstanceId("instId")).thenReturn(varQuery);
    when(varQuery.singleResult()).thenReturn(variable);
    when(variable.getId()).thenReturn("varId");

    when(historyService.createHistoricDetailQuery()).thenReturn(detailQuery);
    when(detailQuery.processInstanceId("instId")).thenReturn(detailQuery);
    when(detailQuery.variableInstanceId("varId")).thenReturn(detailQuery);
    when(detailQuery.occurredBefore(Date.from(updatedBefore))).thenReturn(detailQuery);
    when(detailQuery.occurredAfter(Date.from(updatedAfter))).thenReturn(detailQuery);
    when(detailQuery.orderByVariableRevision()).thenReturn(detailQuery);
    when(detailQuery.asc()).thenReturn(detailQuery);
    when(detailQuery.list()).thenReturn(details);
    when(objectConverter.convertCollection(details, HistoricDetail.class, VariablesDomain.class)).thenReturn(expected);

    List<VariablesDomain> result = underTest.findGlobalVarsHistoryByWorkflowInstId("instId", updatedBefore, updatedAfter);

    assertThat(result).isEqualTo(expected);
  }
}
