package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;

import org.camunda.bpm.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

class ActivityDomainConverterTest {

  @Test
  void shouldConvertHistoricActivityInstanceToActivityDomain() {
    ActivityDomainConverter converter = new ActivityDomainConverter();
    HistoricActivityInstanceEntity entity = mock(HistoricActivityInstanceEntity.class);
    Instant startTime = Instant.parse("2024-01-01T10:00:00Z");
    Instant endTime = Instant.parse("2024-01-01T10:05:00Z");
    when(entity.getActivityId()).thenReturn("activity-123");
    when(entity.getActivityName()).thenReturn("Test Activity");
    when(entity.getProcessInstanceId()).thenReturn("proc-inst-456");
    when(entity.getProcessDefinitionKey()).thenReturn("workflow-789");
    when(entity.getActivityType()).thenReturn("userTask");
    when(entity.getStartTime()).thenReturn(Date.from(startTime));
    when(entity.getEndTime()).thenReturn(Date.from(endTime));
    when(entity.getDurationInMillis()).thenReturn(300000L);

    ActivityInstanceDomain result = converter.apply(entity);

    assertThat(result.getId()).isEqualTo("activity-123");
    assertThat(result.getName()).isEqualTo("Test Activity");
    assertThat(result.getProcInstId()).isEqualTo("proc-inst-456");
    assertThat(result.getWorkflowId()).isEqualTo("workflow-789");
    assertThat(result.getType()).isEqualTo("userTask");
    assertThat(result.getStartDate()).isEqualTo(startTime);
    assertThat(result.getEndDate()).isEqualTo(endTime);
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(300000L));
  }

  @Test
  void shouldConvertHistoricActivityInstanceWhenEndTimeIsNull() {
    ActivityDomainConverter converter = new ActivityDomainConverter();
    HistoricActivityInstanceEntity entity = mock(HistoricActivityInstanceEntity.class);
    Instant startTime = Instant.parse("2024-01-01T10:00:00Z");
    when(entity.getActivityId()).thenReturn("activity-123");
    when(entity.getActivityName()).thenReturn("Test Activity");
    when(entity.getProcessInstanceId()).thenReturn("proc-inst-456");
    when(entity.getProcessDefinitionKey()).thenReturn("workflow-789");
    when(entity.getActivityType()).thenReturn("userTask");
    when(entity.getStartTime()).thenReturn(Date.from(startTime));
    when(entity.getEndTime()).thenReturn(null);
    when(entity.getDurationInMillis()).thenReturn(null);

    ActivityInstanceDomain result = converter.apply(entity);

    assertThat(result.getId()).isEqualTo("activity-123");
    assertThat(result.getName()).isEqualTo("Test Activity");
    assertThat(result.getProcInstId()).isEqualTo("proc-inst-456");
    assertThat(result.getWorkflowId()).isEqualTo("workflow-789");
    assertThat(result.getType()).isEqualTo("userTask");
    assertThat(result.getStartDate()).isEqualTo(startTime);
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getDuration()).isNull();
  }

  @Test
  void shouldConvertHistoricActivityInstanceWhenDurationIsNull() {
    ActivityDomainConverter converter = new ActivityDomainConverter();
    HistoricActivityInstanceEntity entity = mock(HistoricActivityInstanceEntity.class);
    Instant startTime = Instant.parse("2024-01-01T10:00:00Z");
    Instant endTime = Instant.parse("2024-01-01T10:05:00Z");
    when(entity.getActivityId()).thenReturn("activity-123");
    when(entity.getActivityName()).thenReturn("Test Activity");
    when(entity.getProcessInstanceId()).thenReturn("proc-inst-456");
    when(entity.getProcessDefinitionKey()).thenReturn("workflow-789");
    when(entity.getActivityType()).thenReturn("userTask");
    when(entity.getStartTime()).thenReturn(Date.from(startTime));
    when(entity.getEndTime()).thenReturn(Date.from(endTime));
    when(entity.getDurationInMillis()).thenReturn(null);

    ActivityInstanceDomain result = converter.apply(entity);

    assertThat(result.getId()).isEqualTo("activity-123");
    assertThat(result.getName()).isEqualTo("Test Activity");
    assertThat(result.getProcInstId()).isEqualTo("proc-inst-456");
    assertThat(result.getWorkflowId()).isEqualTo("workflow-789");
    assertThat(result.getType()).isEqualTo("userTask");
    assertThat(result.getStartDate()).isEqualTo(startTime);
    assertThat(result.getEndDate()).isEqualTo(endTime);
    assertThat(result.getDuration()).isNull();
  }
}
