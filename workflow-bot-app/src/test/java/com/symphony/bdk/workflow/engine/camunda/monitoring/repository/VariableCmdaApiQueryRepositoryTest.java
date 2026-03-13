package com.symphony.bdk.workflow.engine.camunda.monitoring.repository;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VariableCmdaApiQueryRepositoryTest {

  private RepositoryService repositoryService;
  private HistoryService historyService;
  private RuntimeService runtimeService;
  private ObjectConverter objectConverter;
  private VariableCmdaApiQueryRepository repository;

  @BeforeEach
  void setUp() {
    repositoryService = mock(RepositoryService.class);
    historyService = mock(HistoryService.class);
    runtimeService = mock(RuntimeService.class);
    objectConverter = mock(ObjectConverter.class);
    repository = new VariableCmdaApiQueryRepository(repositoryService, historyService, runtimeService,
        objectConverter);
  }

  @Test
  void constructorShouldInitializeRepository() {
    assertNotNull(repository);
  }

  @Test
  void findVarsByWorkflowInstanceIdAndVarNameShouldReturnVariablesDomainWhenVariableExists() {
    HistoricVariableInstanceQuery query = mock(HistoricVariableInstanceQuery.class);
    HistoricVariableInstanceEntity mockEntity = mock(HistoricVariableInstanceEntity.class);
    Map<String, Object> testOutputs = Map.of("key1", "value1", "key2", 123);
    Instant testCreateTime = Instant.parse("2026-03-13T10:00:00Z");

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(query);
    when(query.processInstanceId("testInstanceId")).thenReturn(query);
    when(query.variableName("testVarName")).thenReturn(query);
    when(query.singleResult()).thenReturn(mockEntity);
    when(mockEntity.getValue()).thenReturn(testOutputs);
    when(mockEntity.getRevision()).thenReturn(5);
    when(mockEntity.getCreateTime()).thenReturn(Date.from(testCreateTime));

    VariablesDomain result = repository.findVarsByWorkflowInstanceIdAndVarName("testInstanceId", "testVarName");

    assertNotNull(result);
    assertEquals(testOutputs, result.getOutputs());
    assertEquals(5, result.getRevision());
    assertEquals(testCreateTime, result.getUpdateTime());
    verify(historyService).createHistoricVariableInstanceQuery();
    verify(query).processInstanceId("testInstanceId");
    verify(query).variableName("testVarName");
    verify(query).singleResult();
  }

  @Test
  void findVarsByWorkflowInstanceIdAndVarNameShouldReturnEmptyVariablesDomainWhenVariableDoesNotExist() {
    HistoricVariableInstanceQuery query = mock(HistoricVariableInstanceQuery.class);

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(query);
    when(query.processInstanceId("testInstanceId")).thenReturn(query);
    when(query.variableName("testVarName")).thenReturn(query);
    when(query.singleResult()).thenReturn(null);

    VariablesDomain result = repository.findVarsByWorkflowInstanceIdAndVarName("testInstanceId", "testVarName");

    assertNotNull(result);
    assertTrue(result.getOutputs().isEmpty());
    assertEquals(0, result.getRevision());
    assertEquals(null, result.getUpdateTime());
  }

  @Test
  void findGlobalVarsHistoryByWorkflowInstIdShouldReturnEmptyListWhenVariableDoesNotExist() {
    HistoricVariableInstanceQuery query = mock(HistoricVariableInstanceQuery.class);

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(query);
    when(query.variableName("variables")).thenReturn(query);
    when(query.processInstanceId("testInstanceId")).thenReturn(query);
    when(query.singleResult()).thenReturn(null);

    List<VariablesDomain> result = repository.findGlobalVarsHistoryByWorkflowInstId("testInstanceId", null, null);

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(historyService).createHistoricVariableInstanceQuery();
  }

  @Test
  void findGlobalVarsHistoryByWorkflowInstIdShouldReturnHistoryWhenVariableExists() {
    HistoricVariableInstanceQuery variableQuery = mock(HistoricVariableInstanceQuery.class);
    HistoricVariableInstance mockVariable = mock(HistoricVariableInstance.class);
    HistoricDetailQuery detailQuery = mock(HistoricDetailQuery.class);
    HistoricDetail mockDetail1 = mock(HistoricDetail.class);
    HistoricDetail mockDetail2 = mock(HistoricDetail.class);
    List<HistoricDetail> mockDetails = List.of(mockDetail1, mockDetail2);
    VariablesDomain domain1 = new VariablesDomain();
    VariablesDomain domain2 = new VariablesDomain();
    List<VariablesDomain> expectedDomains = List.of(domain1, domain2);

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(variableQuery);
    when(variableQuery.variableName("variables")).thenReturn(variableQuery);
    when(variableQuery.processInstanceId("testInstanceId")).thenReturn(variableQuery);
    when(variableQuery.singleResult()).thenReturn(mockVariable);
    when(mockVariable.getId()).thenReturn("varId123");
    when(historyService.createHistoricDetailQuery()).thenReturn(detailQuery);
    when(detailQuery.processInstanceId("testInstanceId")).thenReturn(detailQuery);
    when(detailQuery.variableInstanceId("varId123")).thenReturn(detailQuery);
    when(detailQuery.orderByVariableRevision()).thenReturn(detailQuery);
    when(detailQuery.asc()).thenReturn(detailQuery);
    when(detailQuery.list()).thenReturn(mockDetails);
    when(objectConverter.convertCollection(mockDetails, HistoricDetail.class, VariablesDomain.class))
        .thenReturn(expectedDomains);

    List<VariablesDomain> result = repository.findGlobalVarsHistoryByWorkflowInstId("testInstanceId", null, null);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(expectedDomains, result);
    verify(objectConverter).convertCollection(mockDetails, HistoricDetail.class, VariablesDomain.class);
  }

  @Test
  void findGlobalVarsHistoryByWorkflowInstIdShouldApplyUpdatedBeforeFilter() {
    HistoricVariableInstanceQuery variableQuery = mock(HistoricVariableInstanceQuery.class);
    HistoricVariableInstance mockVariable = mock(HistoricVariableInstance.class);
    HistoricDetailQuery detailQuery = mock(HistoricDetailQuery.class);
    Instant updatedBefore = Instant.parse("2026-03-13T12:00:00Z");

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(variableQuery);
    when(variableQuery.variableName("variables")).thenReturn(variableQuery);
    when(variableQuery.processInstanceId("testInstanceId")).thenReturn(variableQuery);
    when(variableQuery.singleResult()).thenReturn(mockVariable);
    when(mockVariable.getId()).thenReturn("varId123");
    when(historyService.createHistoricDetailQuery()).thenReturn(detailQuery);
    when(detailQuery.processInstanceId("testInstanceId")).thenReturn(detailQuery);
    when(detailQuery.variableInstanceId("varId123")).thenReturn(detailQuery);
    when(detailQuery.occurredBefore(Date.from(updatedBefore))).thenReturn(detailQuery);
    when(detailQuery.orderByVariableRevision()).thenReturn(detailQuery);
    when(detailQuery.asc()).thenReturn(detailQuery);
    when(detailQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(HistoricDetail.class), eq(VariablesDomain.class)))
        .thenReturn(Collections.emptyList());

    repository.findGlobalVarsHistoryByWorkflowInstId("testInstanceId", updatedBefore, null);

    verify(detailQuery).occurredBefore(Date.from(updatedBefore));
  }

  @Test
  void findGlobalVarsHistoryByWorkflowInstIdShouldApplyUpdatedAfterFilter() {
    HistoricVariableInstanceQuery variableQuery = mock(HistoricVariableInstanceQuery.class);
    HistoricVariableInstance mockVariable = mock(HistoricVariableInstance.class);
    HistoricDetailQuery detailQuery = mock(HistoricDetailQuery.class);
    Instant updatedAfter = Instant.parse("2026-03-13T08:00:00Z");

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(variableQuery);
    when(variableQuery.variableName("variables")).thenReturn(variableQuery);
    when(variableQuery.processInstanceId("testInstanceId")).thenReturn(variableQuery);
    when(variableQuery.singleResult()).thenReturn(mockVariable);
    when(mockVariable.getId()).thenReturn("varId123");
    when(historyService.createHistoricDetailQuery()).thenReturn(detailQuery);
    when(detailQuery.processInstanceId("testInstanceId")).thenReturn(detailQuery);
    when(detailQuery.variableInstanceId("varId123")).thenReturn(detailQuery);
    when(detailQuery.occurredAfter(Date.from(updatedAfter))).thenReturn(detailQuery);
    when(detailQuery.orderByVariableRevision()).thenReturn(detailQuery);
    when(detailQuery.asc()).thenReturn(detailQuery);
    when(detailQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(HistoricDetail.class), eq(VariablesDomain.class)))
        .thenReturn(Collections.emptyList());

    repository.findGlobalVarsHistoryByWorkflowInstId("testInstanceId", null, updatedAfter);

    verify(detailQuery).occurredAfter(Date.from(updatedAfter));
  }

  @Test
  void findGlobalVarsHistoryByWorkflowInstIdShouldApplyBothFilters() {
    HistoricVariableInstanceQuery variableQuery = mock(HistoricVariableInstanceQuery.class);
    HistoricVariableInstance mockVariable = mock(HistoricVariableInstance.class);
    HistoricDetailQuery detailQuery = mock(HistoricDetailQuery.class);
    Instant updatedBefore = Instant.parse("2026-03-13T12:00:00Z");
    Instant updatedAfter = Instant.parse("2026-03-13T08:00:00Z");

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(variableQuery);
    when(variableQuery.variableName("variables")).thenReturn(variableQuery);
    when(variableQuery.processInstanceId("testInstanceId")).thenReturn(variableQuery);
    when(variableQuery.singleResult()).thenReturn(mockVariable);
    when(mockVariable.getId()).thenReturn("varId123");
    when(historyService.createHistoricDetailQuery()).thenReturn(detailQuery);
    when(detailQuery.processInstanceId("testInstanceId")).thenReturn(detailQuery);
    when(detailQuery.variableInstanceId("varId123")).thenReturn(detailQuery);
    when(detailQuery.occurredBefore(Date.from(updatedBefore))).thenReturn(detailQuery);
    when(detailQuery.occurredAfter(Date.from(updatedAfter))).thenReturn(detailQuery);
    when(detailQuery.orderByVariableRevision()).thenReturn(detailQuery);
    when(detailQuery.asc()).thenReturn(detailQuery);
    when(detailQuery.list()).thenReturn(Collections.emptyList());
    when(objectConverter.convertCollection(any(), eq(HistoricDetail.class), eq(VariablesDomain.class)))
        .thenReturn(Collections.emptyList());

    repository.findGlobalVarsHistoryByWorkflowInstId("testInstanceId", updatedBefore, updatedAfter);

    verify(detailQuery).occurredBefore(Date.from(updatedBefore));
    verify(detailQuery).occurredAfter(Date.from(updatedAfter));
  }
}
