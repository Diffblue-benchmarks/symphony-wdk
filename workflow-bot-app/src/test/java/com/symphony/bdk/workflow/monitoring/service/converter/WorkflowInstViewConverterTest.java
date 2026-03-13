package com.symphony.bdk.workflow.monitoring.service.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

class WorkflowInstViewConverterTest {

  @Test
  void shouldConvertWorkflowInstanceDomainToWorkflowInstView() {
    WorkflowInstViewConverter converter = new WorkflowInstViewConverter();
    Instant startDate = Instant.parse("2024-01-01T10:00:00Z");
    Instant endDate = Instant.parse("2024-01-01T10:05:00Z");
    Duration duration = Duration.ofMinutes(5);

    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("test-workflow")
        .version(1L)
        .instanceId("instance-456")
        .status("COMPLETED")
        .startDate(startDate)
        .endDate(endDate)
        .duration(duration)
        .build();

    WorkflowInstView result = converter.apply(domain);

    assertThat(result.getId()).isEqualTo("test-workflow");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getInstanceId()).isEqualTo("instance-456");
    assertThat(result.getStatus()).isEqualTo(StatusEnum.COMPLETED);
    assertThat(result.getStartDate()).isEqualTo(startDate);
    assertThat(result.getEndDate()).isEqualTo(endDate);
    assertThat(result.getDuration()).isEqualTo(duration);
  }

  @Test
  void shouldConvertWorkflowInstanceDomainWithPendingStatus() {
    WorkflowInstViewConverter converter = new WorkflowInstViewConverter();
    Instant startDate = Instant.parse("2024-01-01T10:00:00Z");

    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("pending-workflow")
        .version(2L)
        .instanceId("instance-789")
        .status("PENDING")
        .startDate(startDate)
        .endDate(null)
        .duration(null)
        .build();

    WorkflowInstView result = converter.apply(domain);

    assertThat(result.getId()).isEqualTo("pending-workflow");
    assertThat(result.getVersion()).isEqualTo(2L);
    assertThat(result.getInstanceId()).isEqualTo("instance-789");
    assertThat(result.getStatus()).isEqualTo(StatusEnum.PENDING);
    assertThat(result.getStartDate()).isEqualTo(startDate);
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getDuration()).isNull();
  }

  @Test
  void shouldConvertWorkflowInstanceDomainWithFailedStatus() {
    WorkflowInstViewConverter converter = new WorkflowInstViewConverter();
    Instant startDate = Instant.parse("2024-01-01T10:00:00Z");
    Instant endDate = Instant.parse("2024-01-01T10:02:00Z");
    Duration duration = Duration.ofMinutes(2);

    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("failed-workflow")
        .version(3L)
        .instanceId("instance-999")
        .status("FAILED")
        .startDate(startDate)
        .endDate(endDate)
        .duration(duration)
        .build();

    WorkflowInstView result = converter.apply(domain);

    assertThat(result.getId()).isEqualTo("failed-workflow");
    assertThat(result.getVersion()).isEqualTo(3L);
    assertThat(result.getInstanceId()).isEqualTo("instance-999");
    assertThat(result.getStatus()).isEqualTo(StatusEnum.FAILED);
    assertThat(result.getStartDate()).isEqualTo(startDate);
    assertThat(result.getEndDate()).isEqualTo(endDate);
    assertThat(result.getDuration()).isEqualTo(duration);
  }

  @Test
  void shouldConvertWorkflowInstanceDomainWithActiveStatus() {
    WorkflowInstViewConverter converter = new WorkflowInstViewConverter();
    Instant startDate = Instant.parse("2024-01-01T10:00:00Z");

    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("active-workflow")
        .version(1L)
        .instanceId("instance-111")
        .status("ACTIVE")
        .startDate(startDate)
        .endDate(null)
        .duration(null)
        .build();

    WorkflowInstView result = converter.apply(domain);

    assertThat(result.getId()).isEqualTo("active-workflow");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getInstanceId()).isEqualTo("instance-111");
    assertThat(result.getStatus()).isEqualTo(StatusEnum.PENDING);
    assertThat(result.getStartDate()).isEqualTo(startDate);
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getDuration()).isNull();
  }

  @Test
  void shouldConvertWorkflowInstanceDomainWhenEndDateAndDurationAreNull() {
    WorkflowInstViewConverter converter = new WorkflowInstViewConverter();
    Instant startDate = Instant.parse("2024-01-01T10:00:00Z");

    WorkflowInstanceDomain domain = WorkflowInstanceDomain.builder()
        .id("domain-id-123")
        .name("test-workflow")
        .version(1L)
        .instanceId("instance-456")
        .status("PENDING")
        .startDate(startDate)
        .endDate(null)
        .duration(null)
        .build();

    WorkflowInstView result = converter.apply(domain);

    assertThat(result.getId()).isEqualTo("test-workflow");
    assertThat(result.getVersion()).isEqualTo(1L);
    assertThat(result.getInstanceId()).isEqualTo("instance-456");
    assertThat(result.getStatus()).isEqualTo(StatusEnum.PENDING);
    assertThat(result.getStartDate()).isEqualTo(startDate);
    assertThat(result.getEndDate()).isNull();
    assertThat(result.getDuration()).isNull();
  }
}
