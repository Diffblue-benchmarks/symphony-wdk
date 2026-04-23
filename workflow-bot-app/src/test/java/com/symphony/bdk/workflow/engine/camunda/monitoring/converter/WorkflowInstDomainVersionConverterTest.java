package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowInstDomainVersionConverterTest {

  private WorkflowInstDomainVersionConverter converter;

  @Mock
  private HistoricProcessInstanceEntity hisProcInstance;

  @BeforeEach
  void setUp() {
    converter = new WorkflowInstDomainVersionConverter();
  }

  @Test
  void shouldReturnWorkflowInstanceDomainWithVersionWhenVersionFound() {
    when(hisProcInstance.getProcessDefinitionId()).thenReturn("procDefId:1");
    when(hisProcInstance.getId()).thenReturn("id1");
    when(hisProcInstance.getProcessDefinitionKey()).thenReturn("myWorkflow");
    when(hisProcInstance.getProcessInstanceId()).thenReturn("instanceId1");
    when(hisProcInstance.getStartTime()).thenReturn(new Date(0));
    when(hisProcInstance.getEndTime()).thenReturn(null);
    when(hisProcInstance.getState()).thenReturn("COMPLETED");
    when(hisProcInstance.getEndActivityId()).thenReturn("endEvent1");
    when(hisProcInstance.getDurationInMillis()).thenReturn(null);

    Map<String, String> procIdVersionTagMap = new HashMap<>();
    procIdVersionTagMap.put("procDefId:1", "2");

    WorkflowInstanceDomain result = converter.apply(hisProcInstance, procIdVersionTagMap);

    assertThat(result).isNotNull();
    assertThat(result.getVersion()).isEqualTo(2L);
    assertThat(result.getId()).isEqualTo("id1");
    assertThat(result.getName()).isEqualTo("myWorkflow");
  }

  @Test
  void shouldReturnNullWhenVersionNotFound() {
    when(hisProcInstance.getProcessDefinitionId()).thenReturn("procDefId:unknown");

    Map<String, String> procIdVersionTagMap = new HashMap<>();
    procIdVersionTagMap.put("procDefId:1", "2");

    WorkflowInstanceDomain result = converter.apply(hisProcInstance, procIdVersionTagMap);

    assertThat(result).isNull();
  }
}
