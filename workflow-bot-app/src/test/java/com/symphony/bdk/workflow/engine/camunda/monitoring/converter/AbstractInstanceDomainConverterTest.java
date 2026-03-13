package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

class AbstractInstanceDomainConverterTest {

  private TestInstanceDomainConverter converter;

  @BeforeEach
  void setUp() {
    converter = new TestInstanceDomainConverter();
  }

  @Test
  void shouldBuildInstanceWithAllFields() {
    HistoricProcessInstanceEntity entity = mock(HistoricProcessInstanceEntity.class);
    Date startTime = new Date(1000000000L);
    Date endTime = new Date(2000000000L);

    when(entity.getId()).thenReturn("instance-id-123");
    when(entity.getProcessDefinitionKey()).thenReturn("test-workflow");
    when(entity.getProcessInstanceId()).thenReturn("process-instance-456");
    when(entity.getStartTime()).thenReturn(startTime);
    when(entity.getEndTime()).thenReturn(endTime);
    when(entity.getState()).thenReturn("COMPLETED");
    when(entity.getEndActivityId()).thenReturn("endEvent-success");
    when(entity.getDurationInMillis()).thenReturn(1000000L);

    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder builder = converter.testInstanceCommonBuilder(entity);
    WorkflowInstanceDomain domain = builder.build();

    assertThat(domain.getId()).isEqualTo("instance-id-123");
    assertThat(domain.getName()).isEqualTo("test-workflow");
    assertThat(domain.getInstanceId()).isEqualTo("process-instance-456");
    assertThat(domain.getStartDate()).isEqualTo(startTime.toInstant());
    assertThat(domain.getEndDate()).isEqualTo(endTime.toInstant());
    assertThat(domain.getStatus()).isEqualTo(StatusEnum.COMPLETED.name());
    assertThat(domain.getDuration()).isEqualTo(Duration.ofMillis(1000000L));
  }

  @Test
  void shouldBuildInstanceWithNullEndDate() {
    HistoricProcessInstanceEntity entity = mock(HistoricProcessInstanceEntity.class);
    Date startTime = new Date(1000000000L);

    when(entity.getId()).thenReturn("instance-id-123");
    when(entity.getProcessDefinitionKey()).thenReturn("test-workflow");
    when(entity.getProcessInstanceId()).thenReturn("process-instance-456");
    when(entity.getStartTime()).thenReturn(startTime);
    when(entity.getEndTime()).thenReturn(null);
    when(entity.getState()).thenReturn("ACTIVE");
    when(entity.getEndActivityId()).thenReturn(null);
    when(entity.getDurationInMillis()).thenReturn(null);

    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder builder = converter.testInstanceCommonBuilder(entity);
    WorkflowInstanceDomain domain = builder.build();

    assertThat(domain.getEndDate()).isNull();
    assertThat(domain.getDuration()).isNull();
  }

  @Test
  void shouldResolveStatusAsPendingWhenStateIsActive() {
    String status = converter.testResolveStatus("ACTIVE", null);

    assertThat(status).isEqualTo(StatusEnum.PENDING.name());
  }

  @Test
  void shouldResolveStatusAsPendingWhenStateIsPending() {
    String status = converter.testResolveStatus("PENDING", null);

    assertThat(status).isEqualTo(StatusEnum.PENDING.name());
  }

  @Test
  void shouldResolveStatusAsPendingWhenStateIsActiveCaseInsensitive() {
    String status = converter.testResolveStatus("active", null);

    assertThat(status).isEqualTo(StatusEnum.PENDING.name());
  }

  @Test
  void shouldResolveStatusAsCompletedWhenStateIsCompletedAndEndsWithEndEvent() {
    String status = converter.testResolveStatus("COMPLETED", "endEvent-success");

    assertThat(status).isEqualTo(StatusEnum.COMPLETED.name());
  }

  @Test
  void shouldResolveStatusAsFailedWhenStateIsCompletedButNoEndEvent() {
    String status = converter.testResolveStatus("COMPLETED", "someActivity");

    assertThat(status).isEqualTo(StatusEnum.FAILED.name());
  }

  @Test
  void shouldResolveStatusAsFailedWhenStateIsCompletedButEndActivityIdIsNull() {
    String status = converter.testResolveStatus("COMPLETED", null);

    assertThat(status).isEqualTo(StatusEnum.FAILED.name());
  }

  @Test
  void shouldResolveStatusAsFailedWhenStateIsCompletedButEndActivityIdIsEmpty() {
    String status = converter.testResolveStatus("COMPLETED", "");

    assertThat(status).isEqualTo(StatusEnum.FAILED.name());
  }

  @Test
  void shouldResolveStatusAsNullWhenStateIsUnknown() {
    String status = converter.testResolveStatus("UNKNOWN_STATE", null);

    assertThat(status).isNull();
  }

  @Test
  void shouldResolveStatusAsNullWhenStateIsNull() {
    String status = converter.testResolveStatus(null, null);

    assertThat(status).isNull();
  }

  private static class TestInstanceDomainConverter extends AbstractInstanceDomainConverter {
    public WorkflowInstanceDomain.WorkflowInstanceDomainBuilder testInstanceCommonBuilder(
        HistoricProcessInstanceEntity entity) {
      return instanceCommonBuilder(entity);
    }

    public String testResolveStatus(String state, String endActivityId) {
      return resolveStatus(state, endActivityId);
    }
  }
}
