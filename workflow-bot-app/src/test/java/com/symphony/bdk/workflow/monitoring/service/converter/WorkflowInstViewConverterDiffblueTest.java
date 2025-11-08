package com.symphony.bdk.workflow.monitoring.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.api.v1.dto.StatusEnum;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowInstViewConverter.class})
@ExtendWith(SpringExtension.class)
class WorkflowInstViewConverterDiffblueTest {
  @Autowired
  private WorkflowInstViewConverter workflowInstViewConverter;

  /**
   * Method under test:
   * {@link WorkflowInstViewConverter#apply(WorkflowInstanceDomain)}
   */
  @Test
  void testApply() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.duration(Mockito.<Duration>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain domain = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("ACTIVE")
        .version(1L)
        .build();

    // Act
    WorkflowInstView actualApplyResult = workflowInstViewConverter.apply(domain);

    // Assert
    verify(workflowInstanceDomainBuilder).duration(isNull());
    assertEquals("42", actualApplyResult.getInstanceId());
    assertEquals("Name", actualApplyResult.getId());
    assertNull(actualApplyResult.getDuration());
    Instant endDate = actualApplyResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
    assertEquals(StatusEnum.PENDING, actualApplyResult.getStatus());
    assertSame(endDate, actualApplyResult.getStartDate());
  }
}
