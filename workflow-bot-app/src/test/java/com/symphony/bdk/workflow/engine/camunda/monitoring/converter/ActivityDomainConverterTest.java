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

  private final ActivityDomainConverter underTest = new ActivityDomainConverter();

  @Test
  void shouldConvertHistoricActivityInstanceWithEndTimeAndDuration() {
    HistoricActivityInstanceEntity entity = mock(HistoricActivityInstanceEntity.class);
    Date startTime = new Date(1000L);
    Date endTime = new Date(3000L);
    when(entity.getActivityId()).thenReturn("activity1");
    when(entity.getActivityName()).thenReturn("My Activity");
    when(entity.getProcessInstanceId()).thenReturn("procInst1");
    when(entity.getProcessDefinitionKey()).thenReturn("workflow1");
    when(entity.getActivityType()).thenReturn("serviceTask");
    when(entity.getStartTime()).thenReturn(startTime);
    when(entity.getEndTime()).thenReturn(endTime);
    when(entity.getDurationInMillis()).thenReturn(2000L);

    ActivityInstanceDomain result = underTest.apply(entity);

    assertThat(result.getId()).isEqualTo("activity1");
    assertThat(result.getName()).isEqualTo("My Activity");
    assertThat(result.getProcInstId()).isEqualTo("procInst1");
    assertThat(result.getWorkflowId()).isEqualTo("workflow1");
    assertThat(result.getType()).isEqualTo("serviceTask");
    assertThat(result.getStartDate()).isEqualTo(Instant.ofEpochMilli(1000L));
    assertThat(result.getEndDate()).isEqualTo(Instant.ofEpochMilli(3000L));
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(2000L));
  }

  @Test
  void shouldConvertHistoricActivityInstanceWithNullEndTimeAndNullDuration() {
    HistoricActivityInstanceEntity entity = mock(HistoricActivityInstanceEntity.class);
    Date startTime = new Date(5000L);
    when(entity.getActivityId()).thenReturn("activity2");
    when(entity.getActivityName()).thenReturn("Running Activity");
    when(entity.getProcessInstanceId()).thenReturn("procInst2");
    when(entity.getProcessDefinitionKey()).thenReturn("workflow2");
    when(entity.getActivityType()).thenReturn("userTask");
    when(entity.getStartTime()).thenReturn(startTime);
    when(entity.getEndTime()).thenReturn(null);
    when(entity.getDurationInMillis()).thenReturn(null);

    ActivityInstanceDomain result = underTest.apply(entity);

    assertThat(result.getEndDate()).isNull();
    assertThat(result.getDuration()).isNull();
    assertThat(result.getStartDate()).isEqualTo(Instant.ofEpochMilli(5000L));
  }
}
