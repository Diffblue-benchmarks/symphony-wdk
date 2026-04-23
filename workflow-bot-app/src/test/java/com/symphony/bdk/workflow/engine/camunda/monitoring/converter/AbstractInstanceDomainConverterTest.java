package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractInstanceDomainConverterTest {

  private WorkflowInstDomainConverter converter;

  @BeforeEach
  void setUp() {
    converter = new WorkflowInstDomainConverter();
  }

  // ---- instanceCommonBuilder ----

  @Test
  void shouldBuildInstanceDomainWithAllFieldsWhenEndTimeAndDurationPresent() {
    // given
    HistoricProcessInstanceEntity entity = Mockito.mock(HistoricProcessInstanceEntity.class);
    Date startTime = new Date(1000L);
    Date endTime = new Date(5000L);
    Mockito.when(entity.getId()).thenReturn("proc-id-1");
    Mockito.when(entity.getProcessDefinitionKey()).thenReturn("my-workflow");
    Mockito.when(entity.getProcessInstanceId()).thenReturn("instance-id-1");
    Mockito.when(entity.getStartTime()).thenReturn(startTime);
    Mockito.when(entity.getEndTime()).thenReturn(endTime);
    Mockito.when(entity.getState()).thenReturn("COMPLETED");
    Mockito.when(entity.getEndActivityId()).thenReturn("endEvent_1");
    Mockito.when(entity.getDurationInMillis()).thenReturn(4000L);

    // when
    WorkflowInstanceDomain result = converter.apply(entity);

    // then
    assertThat(result.getId()).isEqualTo("proc-id-1");
    assertThat(result.getName()).isEqualTo("my-workflow");
    assertThat(result.getInstanceId()).isEqualTo("instance-id-1");
    assertThat(result.getStartDate()).isEqualTo(Instant.ofEpochMilli(1000L));
    assertThat(result.getEndDate()).isEqualTo(Instant.ofEpochMilli(5000L));
    assertThat(result.getStatus()).isEqualTo(StatusEnum.COMPLETED.name());
    assertThat(result.getDuration()).isEqualTo(Duration.ofMillis(4000L));
  }

  @Test
  void shouldBuildInstanceDomainWithNullEndDateAndDurationWhenEndTimeAndDurationAreNull() {
    // given
    HistoricProcessInstanceEntity entity = Mockito.mock(HistoricProcessInstanceEntity.class);
    Date startTime = new Date(1000L);
    Mockito.when(entity.getId()).thenReturn("proc-id-2");
    Mockito.when(entity.getProcessDefinitionKey()).thenReturn("my-workflow");
    Mockito.when(entity.getProcessInstanceId()).thenReturn("instance-id-2");
    Mockito.when(entity.getStartTime()).thenReturn(startTime);
    Mockito.when(entity.getEndTime()).thenReturn(null);
    Mockito.when(entity.getState()).thenReturn("ACTIVE");
    Mockito.when(entity.getEndActivityId()).thenReturn(null);
    Mockito.when(entity.getDurationInMillis()).thenReturn(null);

    // when
    WorkflowInstanceDomain result = converter.apply(entity);

    // then
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getDuration()).isNull();
    assertThat(result.getStatus()).isEqualTo(StatusEnum.PENDING.name());
  }

  // ---- resolveStatus ----

  @Test
  void shouldReturnPendingStatusWhenStateIsActive() {
    // given
    HistoricProcessInstanceEntity entity = Mockito.mock(HistoricProcessInstanceEntity.class);
    Mockito.when(entity.getId()).thenReturn("id");
    Mockito.when(entity.getProcessDefinitionKey()).thenReturn("wf");
    Mockito.when(entity.getProcessInstanceId()).thenReturn("inst");
    Mockito.when(entity.getStartTime()).thenReturn(new Date());
    Mockito.when(entity.getEndTime()).thenReturn(null);
    Mockito.when(entity.getDurationInMillis()).thenReturn(null);
    Mockito.when(entity.getState()).thenReturn("ACTIVE");
    Mockito.when(entity.getEndActivityId()).thenReturn(null);

    // when
    WorkflowInstanceDomain result = converter.apply(entity);

    // then
    assertThat(result.getStatus()).isEqualTo(StatusEnum.PENDING.name());
  }

  @Test
  void shouldReturnPendingStatusWhenStateIsPending() {
    // given
    HistoricProcessInstanceEntity entity = Mockito.mock(HistoricProcessInstanceEntity.class);
    Mockito.when(entity.getId()).thenReturn("id");
    Mockito.when(entity.getProcessDefinitionKey()).thenReturn("wf");
    Mockito.when(entity.getProcessInstanceId()).thenReturn("inst");
    Mockito.when(entity.getStartTime()).thenReturn(new Date());
    Mockito.when(entity.getEndTime()).thenReturn(null);
    Mockito.when(entity.getDurationInMillis()).thenReturn(null);
    Mockito.when(entity.getState()).thenReturn("PENDING");
    Mockito.when(entity.getEndActivityId()).thenReturn(null);

    // when
    WorkflowInstanceDomain result = converter.apply(entity);

    // then
    assertThat(result.getStatus()).isEqualTo(StatusEnum.PENDING.name());
  }

  @Test
  void shouldReturnCompletedStatusWhenStateIsCompletedAndEndActivityStartsWithEndEvent() {
    // given
    HistoricProcessInstanceEntity entity = Mockito.mock(HistoricProcessInstanceEntity.class);
    Mockito.when(entity.getId()).thenReturn("id");
    Mockito.when(entity.getProcessDefinitionKey()).thenReturn("wf");
    Mockito.when(entity.getProcessInstanceId()).thenReturn("inst");
    Mockito.when(entity.getStartTime()).thenReturn(new Date());
    Mockito.when(entity.getEndTime()).thenReturn(new Date());
    Mockito.when(entity.getDurationInMillis()).thenReturn(100L);
    Mockito.when(entity.getState()).thenReturn("COMPLETED");
    Mockito.when(entity.getEndActivityId()).thenReturn("endEvent_abc");

    // when
    WorkflowInstanceDomain result = converter.apply(entity);

    // then
    assertThat(result.getStatus()).isEqualTo(StatusEnum.COMPLETED.name());
  }

  @Test
  void shouldReturnFailedStatusWhenStateIsCompletedButEndActivityDoesNotStartWithEndEvent() {
    // given
    HistoricProcessInstanceEntity entity = Mockito.mock(HistoricProcessInstanceEntity.class);
    Mockito.when(entity.getId()).thenReturn("id");
    Mockito.when(entity.getProcessDefinitionKey()).thenReturn("wf");
    Mockito.when(entity.getProcessInstanceId()).thenReturn("inst");
    Mockito.when(entity.getStartTime()).thenReturn(new Date());
    Mockito.when(entity.getEndTime()).thenReturn(new Date());
    Mockito.when(entity.getDurationInMillis()).thenReturn(100L);
    Mockito.when(entity.getState()).thenReturn("COMPLETED");
    Mockito.when(entity.getEndActivityId()).thenReturn("someOtherActivity");

    // when
    WorkflowInstanceDomain result = converter.apply(entity);

    // then
    assertThat(result.getStatus()).isEqualTo(StatusEnum.FAILED.name());
  }

  @Test
  void shouldReturnFailedStatusWhenStateIsCompletedAndEndActivityIdIsBlank() {
    // given
    HistoricProcessInstanceEntity entity = Mockito.mock(HistoricProcessInstanceEntity.class);
    Mockito.when(entity.getId()).thenReturn("id");
    Mockito.when(entity.getProcessDefinitionKey()).thenReturn("wf");
    Mockito.when(entity.getProcessInstanceId()).thenReturn("inst");
    Mockito.when(entity.getStartTime()).thenReturn(new Date());
    Mockito.when(entity.getEndTime()).thenReturn(new Date());
    Mockito.when(entity.getDurationInMillis()).thenReturn(100L);
    Mockito.when(entity.getState()).thenReturn("COMPLETED");
    Mockito.when(entity.getEndActivityId()).thenReturn("");

    // when
    WorkflowInstanceDomain result = converter.apply(entity);

    // then
    assertThat(result.getStatus()).isEqualTo(StatusEnum.FAILED.name());
  }

  @Test
  void shouldReturnNullStatusWhenStateDoesNotMatchAnyKnownStatus() {
    // given
    HistoricProcessInstanceEntity entity = Mockito.mock(HistoricProcessInstanceEntity.class);
    Mockito.when(entity.getId()).thenReturn("id");
    Mockito.when(entity.getProcessDefinitionKey()).thenReturn("wf");
    Mockito.when(entity.getProcessInstanceId()).thenReturn("inst");
    Mockito.when(entity.getStartTime()).thenReturn(new Date());
    Mockito.when(entity.getEndTime()).thenReturn(null);
    Mockito.when(entity.getDurationInMillis()).thenReturn(null);
    Mockito.when(entity.getState()).thenReturn("UNKNOWN_STATE");
    Mockito.when(entity.getEndActivityId()).thenReturn(null);

    // when
    WorkflowInstanceDomain result = converter.apply(entity);

    // then
    assertThat(result.getStatus()).isNull();
  }
}
