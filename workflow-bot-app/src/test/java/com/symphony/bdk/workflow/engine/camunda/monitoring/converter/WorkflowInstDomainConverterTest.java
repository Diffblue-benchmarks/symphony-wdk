package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowInstDomainConverterTest {

  private final WorkflowInstDomainConverter converter = new WorkflowInstDomainConverter();

  @Test
  void shouldReturnWorkflowInstanceDomainWhenApplyIsCalled() {
    HistoricProcessInstanceEntity entity = new HistoricProcessInstanceEntity();
    entity.setId("proc-id");
    entity.setProcessDefinitionKey("my-workflow");
    entity.setStartTime(new Date());
    entity.setState("ACTIVE");

    WorkflowInstanceDomain result = converter.apply(entity);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo("proc-id");
    assertThat(result.getName()).isEqualTo("my-workflow");
  }
}
