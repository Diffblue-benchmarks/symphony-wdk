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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for VariableCmdaApiQueryRepository.
 * Tests the constructor, findVarsByWorkflowInstanceIdAndVarName, and findGlobalVarsHistoryByWorkflowInstId methods.
 */
@ExtendWith(MockitoExtension.class)
class VariableCmdaApiQueryRepositoryClaudeTest {

  @Mock
  private RepositoryService repositoryService;

  @Mock
  private HistoryService historyService;

  @Mock
  private RuntimeService runtimeService;

  @Mock
  private ObjectConverter objectConverter;

  @Mock
  private HistoricVariableInstanceQuery historicVariableInstanceQuery;

  @Mock
  private HistoricDetailQuery historicDetailQuery;

  private VariableCmdaApiQueryRepository repository;

  @BeforeEach
  void setUp() {
    repository = new VariableCmdaApiQueryRepository(
        repositoryService,
        historyService,
        runtimeService,
        objectConverter
    );
  }

  // ==================== Constructor Tests ====================

  @Test
  void constructor_withAllDependencies_shouldInitializeSuccessfully() {
    // When: creating a new instance with all dependencies
    VariableCmdaApiQueryRepository newRepository = new VariableCmdaApiQueryRepository(
        repositoryService,
        historyService,
        runtimeService,
        objectConverter
    );

    // Then: repository should be created successfully
    assertThat(newRepository).isNotNull();
  }

  @Test
  void constructor_shouldPassDependenciesToParent() {
    // When: creating repository instance (done in setUp)
    // Then: repository should be properly initialized with dependencies
    assertThat(repository).isNotNull();
  }

  // ==================== findVarsByWorkflowInstanceIdAndVarName Tests ====================

  @Test
  void findVarsByWorkflowInstanceIdAndVarName_withExistingVariable_shouldReturnVariablesDomain() {
    // Given: a workflow instance with an existing variable
    String instanceId = "instance-123";
    String varName = "myVariable";

    HistoricVariableInstanceEntity variableEntity = mock(HistoricVariableInstanceEntity.class);
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("result", "success");
    outputs.put("code", 200);

    when(variableEntity.getValue()).thenReturn(outputs);
    when(variableEntity.getRevision()).thenReturn(3);
    Instant createTime = Instant.parse("2024-01-15T10:30:00Z");
    when(variableEntity.getCreateTime()).thenReturn(Date.from(createTime));

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName(varName)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(variableEntity);

    // When: calling findVarsByWorkflowInstanceIdAndVarName
    VariablesDomain result = repository.findVarsByWorkflowInstanceIdAndVarName(instanceId, varName);

    // Then: should return a populated VariablesDomain
    assertThat(result).isNotNull();
    assertThat(result.getOutputs()).isEqualTo(outputs);
    assertThat(result.getOutputs()).containsEntry("result", "success");
    assertThat(result.getOutputs()).containsEntry("code", 200);
    assertThat(result.getRevision()).isEqualTo(3);
    assertThat(result.getUpdateTime()).isEqualTo(createTime);

    verify(historicVariableInstanceQuery).processInstanceId(instanceId);
    verify(historicVariableInstanceQuery).variableName(varName);
    verify(historicVariableInstanceQuery).singleResult();
  }

  @Test
  void findVarsByWorkflowInstanceIdAndVarName_withNullVariable_shouldReturnEmptyVariablesDomain() {
    // Given: a workflow instance with no matching variable
    String instanceId = "instance-123";
    String varName = "nonExistentVariable";

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName(varName)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(null);

    // When: calling findVarsByWorkflowInstanceIdAndVarName
    VariablesDomain result = repository.findVarsByWorkflowInstanceIdAndVarName(instanceId, varName);

    // Then: should return an empty VariablesDomain with default values
    assertThat(result).isNotNull();
    assertThat(result.getOutputs()).isEmpty();
    assertThat(result.getRevision()).isEqualTo(0);
    assertThat(result.getUpdateTime()).isNull();
  }

  @Test
  void findVarsByWorkflowInstanceIdAndVarName_withEmptyOutputs_shouldHandleEmptyMap() {
    // Given: a variable with an empty outputs map
    String instanceId = "instance-123";
    String varName = "emptyVariable";

    HistoricVariableInstanceEntity variableEntity = mock(HistoricVariableInstanceEntity.class);
    Map<String, Object> emptyOutputs = Collections.emptyMap();

    when(variableEntity.getValue()).thenReturn(emptyOutputs);
    when(variableEntity.getRevision()).thenReturn(1);
    Instant createTime = Instant.parse("2024-01-15T10:30:00Z");
    when(variableEntity.getCreateTime()).thenReturn(Date.from(createTime));

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName(varName)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(variableEntity);

    // When: calling findVarsByWorkflowInstanceIdAndVarName
    VariablesDomain result = repository.findVarsByWorkflowInstanceIdAndVarName(instanceId, varName);

    // Then: should return a VariablesDomain with empty outputs
    assertThat(result).isNotNull();
    assertThat(result.getOutputs()).isEmpty();
    assertThat(result.getRevision()).isEqualTo(1);
    assertThat(result.getUpdateTime()).isEqualTo(createTime);
  }

  @Test
  void findVarsByWorkflowInstanceIdAndVarName_withZeroRevision_shouldHandleZeroRevision() {
    // Given: a variable with revision 0
    String instanceId = "instance-123";
    String varName = "zeroRevisionVar";

    HistoricVariableInstanceEntity variableEntity = mock(HistoricVariableInstanceEntity.class);
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("data", "test");

    when(variableEntity.getValue()).thenReturn(outputs);
    when(variableEntity.getRevision()).thenReturn(0);
    Instant createTime = Instant.parse("2024-01-15T10:30:00Z");
    when(variableEntity.getCreateTime()).thenReturn(Date.from(createTime));

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName(varName)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(variableEntity);

    // When: calling findVarsByWorkflowInstanceIdAndVarName
    VariablesDomain result = repository.findVarsByWorkflowInstanceIdAndVarName(instanceId, varName);

    // Then: should correctly handle revision 0
    assertThat(result.getRevision()).isEqualTo(0);
    assertThat(result.getOutputs()).containsEntry("data", "test");
  }

  @Test
  void findVarsByWorkflowInstanceIdAndVarName_withHighRevision_shouldHandleHighRevisionNumber() {
    // Given: a variable with a high revision number
    String instanceId = "instance-123";
    String varName = "highRevisionVar";

    HistoricVariableInstanceEntity variableEntity = mock(HistoricVariableInstanceEntity.class);
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("version", "latest");

    when(variableEntity.getValue()).thenReturn(outputs);
    when(variableEntity.getRevision()).thenReturn(999);
    Instant createTime = Instant.parse("2024-01-15T10:30:00Z");
    when(variableEntity.getCreateTime()).thenReturn(Date.from(createTime));

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName(varName)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(variableEntity);

    // When: calling findVarsByWorkflowInstanceIdAndVarName
    VariablesDomain result = repository.findVarsByWorkflowInstanceIdAndVarName(instanceId, varName);

    // Then: should correctly handle high revision number
    assertThat(result.getRevision()).isEqualTo(999);
  }

  @Test
  void findVarsByWorkflowInstanceIdAndVarName_withComplexOutputs_shouldHandleVariousDataTypes() {
    // Given: a variable with complex outputs containing various data types
    String instanceId = "instance-123";
    String varName = "complexVariable";

    HistoricVariableInstanceEntity variableEntity = mock(HistoricVariableInstanceEntity.class);
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("string", "text");
    outputs.put("integer", 123);
    outputs.put("boolean", true);
    outputs.put("null", null);

    Map<String, String> nestedMap = new HashMap<>();
    nestedMap.put("nested", "value");
    outputs.put("map", nestedMap);

    when(variableEntity.getValue()).thenReturn(outputs);
    when(variableEntity.getRevision()).thenReturn(5);
    Instant createTime = Instant.parse("2024-01-15T10:30:00Z");
    when(variableEntity.getCreateTime()).thenReturn(Date.from(createTime));

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName(varName)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(variableEntity);

    // When: calling findVarsByWorkflowInstanceIdAndVarName
    VariablesDomain result = repository.findVarsByWorkflowInstanceIdAndVarName(instanceId, varName);

    // Then: should preserve all data types
    assertThat(result.getOutputs()).containsEntry("string", "text");
    assertThat(result.getOutputs()).containsEntry("integer", 123);
    assertThat(result.getOutputs()).containsEntry("boolean", true);
    assertThat(result.getOutputs()).containsKey("null");
    assertThat(result.getOutputs().get("null")).isNull();
    assertThat(result.getOutputs()).containsKey("map");
    assertThat(result.getOutputs().get("map")).isEqualTo(nestedMap);
  }

  @Test
  void findVarsByWorkflowInstanceIdAndVarName_withEpochTime_shouldHandleEpochTime() {
    // Given: a variable created at epoch time
    String instanceId = "instance-123";
    String varName = "epochVariable";

    HistoricVariableInstanceEntity variableEntity = mock(HistoricVariableInstanceEntity.class);
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("data", "epoch");

    when(variableEntity.getValue()).thenReturn(outputs);
    when(variableEntity.getRevision()).thenReturn(1);
    Instant epochTime = Instant.EPOCH;
    when(variableEntity.getCreateTime()).thenReturn(Date.from(epochTime));

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName(varName)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(variableEntity);

    // When: calling findVarsByWorkflowInstanceIdAndVarName
    VariablesDomain result = repository.findVarsByWorkflowInstanceIdAndVarName(instanceId, varName);

    // Then: should correctly handle epoch time
    assertThat(result.getUpdateTime()).isEqualTo(Instant.EPOCH);
  }

  // ==================== findGlobalVarsHistoryByWorkflowInstId Tests ====================

  @Test
  void findGlobalVarsHistoryByWorkflowInstId_withExistingHistory_shouldReturnVariablesList() {
    // Given: a workflow instance with variables history
    String instanceId = "instance-123";
    Instant updatedBefore = null;
    Instant updatedAfter = null;

    HistoricVariableInstance variableInstance = mock(HistoricVariableInstance.class);
    when(variableInstance.getId()).thenReturn("var-id-123");

    List<HistoricDetail> historicDetails = createMockHistoricDetails();
    List<VariablesDomain> expectedDomains = createMockVariablesDomains();

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName("variables")).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(variableInstance);

    when(historyService.createHistoricDetailQuery()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.processInstanceId(instanceId)).thenReturn(historicDetailQuery);
    when(historicDetailQuery.variableInstanceId("var-id-123")).thenReturn(historicDetailQuery);
    when(historicDetailQuery.orderByVariableRevision()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.asc()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.list()).thenReturn(historicDetails);

    when(objectConverter.convertCollection(historicDetails, HistoricDetail.class, VariablesDomain.class))
        .thenReturn(expectedDomains);

    // When: calling findGlobalVarsHistoryByWorkflowInstId
    List<VariablesDomain> result = repository.findGlobalVarsHistoryByWorkflowInstId(
        instanceId, updatedBefore, updatedAfter
    );

    // Then: should return the converted list of VariablesDomain
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    assertThat(result).isEqualTo(expectedDomains);

    verify(historicVariableInstanceQuery).variableName("variables");
    verify(historicVariableInstanceQuery).processInstanceId(instanceId);
    verify(historicDetailQuery).processInstanceId(instanceId);
    verify(historicDetailQuery).variableInstanceId("var-id-123");
    verify(historicDetailQuery).orderByVariableRevision();
    verify(historicDetailQuery).asc();
    verify(historicDetailQuery, never()).occurredBefore(any(Date.class));
    verify(historicDetailQuery, never()).occurredAfter(any(Date.class));
  }

  @Test
  void findGlobalVarsHistoryByWorkflowInstId_withNoVariables_shouldReturnEmptyList() {
    // Given: a workflow instance with no "variables" variable
    String instanceId = "instance-123";
    Instant updatedBefore = null;
    Instant updatedAfter = null;

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName("variables")).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(null);

    // When: calling findGlobalVarsHistoryByWorkflowInstId
    List<VariablesDomain> result = repository.findGlobalVarsHistoryByWorkflowInstId(
        instanceId, updatedBefore, updatedAfter
    );

    // Then: should return an empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();

    verify(historyService, never()).createHistoricDetailQuery();
  }

  @Test
  void findGlobalVarsHistoryByWorkflowInstId_withUpdatedBeforeFilter_shouldApplyFilter() {
    // Given: a workflow instance with updatedBefore filter
    String instanceId = "instance-123";
    Instant updatedBefore = Instant.parse("2024-01-15T12:00:00Z");
    Instant updatedAfter = null;

    HistoricVariableInstance variableInstance = mock(HistoricVariableInstance.class);
    when(variableInstance.getId()).thenReturn("var-id-123");

    List<HistoricDetail> historicDetails = createMockHistoricDetails();
    List<VariablesDomain> expectedDomains = createMockVariablesDomains();

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName("variables")).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(variableInstance);

    when(historyService.createHistoricDetailQuery()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.processInstanceId(instanceId)).thenReturn(historicDetailQuery);
    when(historicDetailQuery.variableInstanceId("var-id-123")).thenReturn(historicDetailQuery);
    when(historicDetailQuery.occurredBefore(Date.from(updatedBefore))).thenReturn(historicDetailQuery);
    when(historicDetailQuery.orderByVariableRevision()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.asc()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.list()).thenReturn(historicDetails);

    when(objectConverter.convertCollection(historicDetails, HistoricDetail.class, VariablesDomain.class))
        .thenReturn(expectedDomains);

    // When: calling findGlobalVarsHistoryByWorkflowInstId
    List<VariablesDomain> result = repository.findGlobalVarsHistoryByWorkflowInstId(
        instanceId, updatedBefore, updatedAfter
    );

    // Then: should apply updatedBefore filter
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    verify(historicDetailQuery).occurredBefore(Date.from(updatedBefore));
    verify(historicDetailQuery, never()).occurredAfter(any(Date.class));
  }

  @Test
  void findGlobalVarsHistoryByWorkflowInstId_withUpdatedAfterFilter_shouldApplyFilter() {
    // Given: a workflow instance with updatedAfter filter
    String instanceId = "instance-123";
    Instant updatedBefore = null;
    Instant updatedAfter = Instant.parse("2024-01-15T10:00:00Z");

    HistoricVariableInstance variableInstance = mock(HistoricVariableInstance.class);
    when(variableInstance.getId()).thenReturn("var-id-123");

    List<HistoricDetail> historicDetails = createMockHistoricDetails();
    List<VariablesDomain> expectedDomains = createMockVariablesDomains();

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName("variables")).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(variableInstance);

    when(historyService.createHistoricDetailQuery()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.processInstanceId(instanceId)).thenReturn(historicDetailQuery);
    when(historicDetailQuery.variableInstanceId("var-id-123")).thenReturn(historicDetailQuery);
    when(historicDetailQuery.occurredAfter(Date.from(updatedAfter))).thenReturn(historicDetailQuery);
    when(historicDetailQuery.orderByVariableRevision()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.asc()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.list()).thenReturn(historicDetails);

    when(objectConverter.convertCollection(historicDetails, HistoricDetail.class, VariablesDomain.class))
        .thenReturn(expectedDomains);

    // When: calling findGlobalVarsHistoryByWorkflowInstId
    List<VariablesDomain> result = repository.findGlobalVarsHistoryByWorkflowInstId(
        instanceId, updatedBefore, updatedAfter
    );

    // Then: should apply updatedAfter filter
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    verify(historicDetailQuery).occurredAfter(Date.from(updatedAfter));
    verify(historicDetailQuery, never()).occurredBefore(any(Date.class));
  }

  @Test
  void findGlobalVarsHistoryByWorkflowInstId_withBothFilters_shouldApplyBothFilters() {
    // Given: a workflow instance with both updatedBefore and updatedAfter filters
    String instanceId = "instance-123";
    Instant updatedBefore = Instant.parse("2024-01-15T12:00:00Z");
    Instant updatedAfter = Instant.parse("2024-01-15T10:00:00Z");

    HistoricVariableInstance variableInstance = mock(HistoricVariableInstance.class);
    when(variableInstance.getId()).thenReturn("var-id-123");

    List<HistoricDetail> historicDetails = createMockHistoricDetails();
    List<VariablesDomain> expectedDomains = createMockVariablesDomains();

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName("variables")).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(variableInstance);

    when(historyService.createHistoricDetailQuery()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.processInstanceId(instanceId)).thenReturn(historicDetailQuery);
    when(historicDetailQuery.variableInstanceId("var-id-123")).thenReturn(historicDetailQuery);
    when(historicDetailQuery.occurredBefore(Date.from(updatedBefore))).thenReturn(historicDetailQuery);
    when(historicDetailQuery.occurredAfter(Date.from(updatedAfter))).thenReturn(historicDetailQuery);
    when(historicDetailQuery.orderByVariableRevision()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.asc()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.list()).thenReturn(historicDetails);

    when(objectConverter.convertCollection(historicDetails, HistoricDetail.class, VariablesDomain.class))
        .thenReturn(expectedDomains);

    // When: calling findGlobalVarsHistoryByWorkflowInstId
    List<VariablesDomain> result = repository.findGlobalVarsHistoryByWorkflowInstId(
        instanceId, updatedBefore, updatedAfter
    );

    // Then: should apply both filters
    assertThat(result).isNotNull();
    assertThat(result).hasSize(2);
    verify(historicDetailQuery).occurredBefore(Date.from(updatedBefore));
    verify(historicDetailQuery).occurredAfter(Date.from(updatedAfter));
  }

  @Test
  void findGlobalVarsHistoryByWorkflowInstId_withEmptyHistory_shouldReturnEmptyList() {
    // Given: a workflow instance with variables but no history
    String instanceId = "instance-123";
    Instant updatedBefore = null;
    Instant updatedAfter = null;

    HistoricVariableInstance variableInstance = mock(HistoricVariableInstance.class);
    when(variableInstance.getId()).thenReturn("var-id-123");

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName("variables")).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(variableInstance);

    when(historyService.createHistoricDetailQuery()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.processInstanceId(instanceId)).thenReturn(historicDetailQuery);
    when(historicDetailQuery.variableInstanceId("var-id-123")).thenReturn(historicDetailQuery);
    when(historicDetailQuery.orderByVariableRevision()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.asc()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.list()).thenReturn(Collections.emptyList());

    when(objectConverter.convertCollection(Collections.emptyList(), HistoricDetail.class, VariablesDomain.class))
        .thenReturn(Collections.emptyList());

    // When: calling findGlobalVarsHistoryByWorkflowInstId
    List<VariablesDomain> result = repository.findGlobalVarsHistoryByWorkflowInstId(
        instanceId, updatedBefore, updatedAfter
    );

    // Then: should return an empty list
    assertThat(result).isNotNull();
    assertThat(result).isEmpty();
  }

  @Test
  void findGlobalVarsHistoryByWorkflowInstId_shouldOrderByVariableRevisionAscending() {
    // Given: a workflow instance with variables history
    String instanceId = "instance-123";
    Instant updatedBefore = null;
    Instant updatedAfter = null;

    HistoricVariableInstance variableInstance = mock(HistoricVariableInstance.class);
    when(variableInstance.getId()).thenReturn("var-id-123");

    List<HistoricDetail> historicDetails = createMockHistoricDetails();
    List<VariablesDomain> expectedDomains = createMockVariablesDomains();

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName("variables")).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(variableInstance);

    when(historyService.createHistoricDetailQuery()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.processInstanceId(instanceId)).thenReturn(historicDetailQuery);
    when(historicDetailQuery.variableInstanceId("var-id-123")).thenReturn(historicDetailQuery);
    when(historicDetailQuery.orderByVariableRevision()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.asc()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.list()).thenReturn(historicDetails);

    when(objectConverter.convertCollection(historicDetails, HistoricDetail.class, VariablesDomain.class))
        .thenReturn(expectedDomains);

    // When: calling findGlobalVarsHistoryByWorkflowInstId
    List<VariablesDomain> result = repository.findGlobalVarsHistoryByWorkflowInstId(
        instanceId, updatedBefore, updatedAfter
    );

    // Then: should order by variable revision ascending
    assertThat(result).isNotNull();
    verify(historicDetailQuery).orderByVariableRevision();
    verify(historicDetailQuery).asc();
    verify(historicDetailQuery).list();
  }

  @Test
  void findGlobalVarsHistoryByWorkflowInstId_withEpochTimes_shouldHandleEpochTimes() {
    // Given: filters with epoch times
    String instanceId = "instance-123";
    Instant updatedBefore = Instant.EPOCH.plusSeconds(1000);
    Instant updatedAfter = Instant.EPOCH;

    HistoricVariableInstance variableInstance = mock(HistoricVariableInstance.class);
    when(variableInstance.getId()).thenReturn("var-id-123");

    List<HistoricDetail> historicDetails = Collections.emptyList();
    List<VariablesDomain> expectedDomains = Collections.emptyList();

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName("variables")).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(variableInstance);

    when(historyService.createHistoricDetailQuery()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.processInstanceId(instanceId)).thenReturn(historicDetailQuery);
    when(historicDetailQuery.variableInstanceId("var-id-123")).thenReturn(historicDetailQuery);
    when(historicDetailQuery.occurredBefore(Date.from(updatedBefore))).thenReturn(historicDetailQuery);
    when(historicDetailQuery.occurredAfter(Date.from(updatedAfter))).thenReturn(historicDetailQuery);
    when(historicDetailQuery.orderByVariableRevision()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.asc()).thenReturn(historicDetailQuery);
    when(historicDetailQuery.list()).thenReturn(historicDetails);

    when(objectConverter.convertCollection(historicDetails, HistoricDetail.class, VariablesDomain.class))
        .thenReturn(expectedDomains);

    // When: calling findGlobalVarsHistoryByWorkflowInstId
    List<VariablesDomain> result = repository.findGlobalVarsHistoryByWorkflowInstId(
        instanceId, updatedBefore, updatedAfter
    );

    // Then: should handle epoch times correctly
    assertThat(result).isNotNull();
    verify(historicDetailQuery).occurredBefore(Date.from(updatedBefore));
    verify(historicDetailQuery).occurredAfter(Date.from(updatedAfter));
  }

  @Test
  void findGlobalVarsHistoryByWorkflowInstId_shouldQueryForVariablesNamedVariables() {
    // Given: a workflow instance
    String instanceId = "instance-123";

    when(historyService.createHistoricVariableInstanceQuery()).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.variableName("variables")).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.processInstanceId(instanceId)).thenReturn(historicVariableInstanceQuery);
    when(historicVariableInstanceQuery.singleResult()).thenReturn(null);

    // When: calling findGlobalVarsHistoryByWorkflowInstId
    List<VariablesDomain> result = repository.findGlobalVarsHistoryByWorkflowInstId(
        instanceId, null, null
    );

    // Then: should query specifically for variable named "variables"
    verify(historicVariableInstanceQuery).variableName("variables");
    assertThat(result).isEmpty();
  }

  // ==================== Helper Methods ====================

  private List<HistoricDetail> createMockHistoricDetails() {
    HistoricDetail detail1 = mock(HistoricDetail.class);
    HistoricDetail detail2 = mock(HistoricDetail.class);
    return Arrays.asList(detail1, detail2);
  }

  private List<VariablesDomain> createMockVariablesDomains() {
    VariablesDomain domain1 = new VariablesDomain();
    Map<String, Object> outputs1 = new HashMap<>();
    outputs1.put("revision1", "data1");
    domain1.setOutputs(outputs1);
    domain1.setRevision(1);
    domain1.setUpdateTime(Instant.parse("2024-01-15T10:00:00Z"));

    VariablesDomain domain2 = new VariablesDomain();
    Map<String, Object> outputs2 = new HashMap<>();
    outputs2.put("revision2", "data2");
    domain2.setOutputs(outputs2);
    domain2.setRevision(2);
    domain2.setUpdateTime(Instant.parse("2024-01-15T11:00:00Z"));

    return Arrays.asList(domain1, domain2);
  }
}
