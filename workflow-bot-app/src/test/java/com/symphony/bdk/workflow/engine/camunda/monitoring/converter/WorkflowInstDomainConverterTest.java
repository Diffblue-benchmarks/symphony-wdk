package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkflowInstDomainConverterTest {

  @Test
  void applyShouldConvertHistoricProcessInstanceToWorkflowInstanceDomain() {
    WorkflowInstDomainConverter converter = new WorkflowInstDomainConverter();
    HistoricProcessInstanceEntity mockEntity = mock(HistoricProcessInstanceEntity.class);
    String testId = "test-id-123";
    String testProcessDefKey = "test-workflow";
    String testInstanceId = "test-instance-123";
    Instant testStartTime = Instant.parse("2026-03-13T10:00:00Z");
    Instant testEndTime = Instant.parse("2026-03-13T11:00:00Z");
    when(mockEntity.getId()).thenReturn(testId);
    when(mockEntity.getProcessDefinitionKey()).thenReturn(testProcessDefKey);
    when(mockEntity.getProcessInstanceId()).thenReturn(testInstanceId);
    when(mockEntity.getStartTime()).thenReturn(Date.from(testStartTime));
    when(mockEntity.getEndTime()).thenReturn(Date.from(testEndTime));
    when(mockEntity.getState()).thenReturn("COMPLETED");
    when(mockEntity.getEndActivityId()).thenReturn("endEvent1");
    when(mockEntity.getDurationInMillis()).thenReturn(3600000L);

    WorkflowInstanceDomain result = converter.apply(mockEntity);

    assertNotNull(result);
    assertEquals(testId, result.getId());
    assertEquals(testProcessDefKey, result.getName());
    assertEquals(testInstanceId, result.getInstanceId());
    assertEquals(testStartTime, result.getStartDate());
    assertEquals(testEndTime, result.getEndDate());
    assertEquals("COMPLETED", result.getStatus());
    assertNotNull(result.getDuration());
    assertEquals(3600000L, result.getDuration().toMillis());
  }

  @Test
  void applyShouldHandleNullEndTime() {
    WorkflowInstDomainConverter converter = new WorkflowInstDomainConverter();
    HistoricProcessInstanceEntity mockEntity = mock(HistoricProcessInstanceEntity.class);
    Instant testStartTime = Instant.parse("2026-03-13T10:00:00Z");
    when(mockEntity.getId()).thenReturn("test-id");
    when(mockEntity.getProcessDefinitionKey()).thenReturn("test-workflow");
    when(mockEntity.getProcessInstanceId()).thenReturn("test-instance");
    when(mockEntity.getStartTime()).thenReturn(Date.from(testStartTime));
    when(mockEntity.getEndTime()).thenReturn(null);
    when(mockEntity.getState()).thenReturn("ACTIVE");
    when(mockEntity.getEndActivityId()).thenReturn(null);
    when(mockEntity.getDurationInMillis()).thenReturn(null);

    WorkflowInstanceDomain result = converter.apply(mockEntity);

    assertNotNull(result);
    assertEquals(testStartTime, result.getStartDate());
    assertEquals(null, result.getEndDate());
    assertEquals("PENDING", result.getStatus());
    assertEquals(null, result.getDuration());
  }
}
