package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowInstDomainVersionConverterTest {

  private WorkflowInstDomainVersionConverter converter;

  @BeforeEach
  void setUp() {
    converter = new WorkflowInstDomainVersionConverter();
  }

  @Test
  void shouldApplyVersionWhenPresentInMap() {
    HistoricProcessInstanceEntity entity = createHistoricProcessInstance();
    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("process-def-123", "42");

    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    assertThat(result).isNotNull();
    assertThat(result.getVersion()).isEqualTo(42L);
    assertThat(result.getId()).isEqualTo("instance-123");
    assertThat(result.getName()).isEqualTo("testProcess");
    assertThat(result.getInstanceId()).isEqualTo("instance-123");
  }

  @Test
  void shouldReturnNullWhenVersionNotPresentInMap() {
    HistoricProcessInstanceEntity entity = createHistoricProcessInstance();
    Map<String, String> versionMap = new HashMap<>();

    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    assertThat(result).isNull();
  }

  @Test
  void shouldReturnNullWhenVersionMapContainsDifferentProcessDefinitionId() {
    HistoricProcessInstanceEntity entity = createHistoricProcessInstance();
    Map<String, String> versionMap = new HashMap<>();
    versionMap.put("different-process-def", "42");

    WorkflowInstanceDomain result = converter.apply(entity, versionMap);

    assertThat(result).isNull();
  }

  private HistoricProcessInstanceEntity createHistoricProcessInstance() {
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("instance-123");
    entity.setProcessDefinitionId("process-def-123");
    entity.setProcessDefinitionKey("testProcess");
    entity.setProcessInstanceId("instance-123");
    entity.setStartTime(new Date());
    entity.setState("COMPLETED");
    entity.setEndActivityId("endEvent");
    entity.setDurationInMillis(1000L);
    return entity;
  }
}
