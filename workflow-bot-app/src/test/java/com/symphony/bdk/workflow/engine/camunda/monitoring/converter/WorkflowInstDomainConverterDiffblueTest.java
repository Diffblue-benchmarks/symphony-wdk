package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowInstDomainConverter.class})
@ExtendWith(SpringExtension.class)
class WorkflowInstDomainConverterDiffblueTest {
  @Autowired
  private WorkflowInstDomainConverter workflowInstDomainConverter;

  /**
   * Method under test:
   * {@link WorkflowInstDomainConverter#apply(HistoricProcessInstanceEntity)}
   */
  @Test
  void testApply() {
    // Arrange
    HistoricProcessInstanceEntity hisProcInstance = new HistoricProcessInstanceEntity();
    hisProcInstance.setStartTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    WorkflowInstanceDomain actualApplyResult = workflowInstDomainConverter.apply(hisProcInstance);

    // Assert
    assertNull(actualApplyResult.getVersion());
    assertNull(actualApplyResult.getId());
    assertNull(actualApplyResult.getInstanceId());
    assertNull(actualApplyResult.getName());
    assertNull(actualApplyResult.getStatus());
    assertNull(actualApplyResult.getDuration());
    assertNull(actualApplyResult.getEndDate());
    Instant startDate = actualApplyResult.getStartDate();
    assertEquals(0, startDate.getNano());
    assertEquals(0L, startDate.getEpochSecond());
  }

  /**
   * Method under test:
   * {@link WorkflowInstDomainConverter#apply(HistoricProcessInstanceEntity)}
   */
  @Test
  void testApply2() {
    // Arrange
    HistoricProcessInstanceEntity hisProcInstance = mock(HistoricProcessInstanceEntity.class);
    when(hisProcInstance.getDurationInMillis()).thenReturn(1L);
    when(hisProcInstance.getEndActivityId()).thenReturn("42");
    when(hisProcInstance.getState()).thenReturn("MD");
    when(hisProcInstance.getEndTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(hisProcInstance.getId()).thenReturn("42");
    when(hisProcInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(hisProcInstance.getProcessInstanceId()).thenReturn("42");
    when(hisProcInstance.getStartTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    WorkflowInstanceDomain actualApplyResult = workflowInstDomainConverter.apply(hisProcInstance);

    // Assert
    verify(hisProcInstance).getEndActivityId();
    verify(hisProcInstance).getState();
    verify(hisProcInstance, atLeast(1)).getDurationInMillis();
    verify(hisProcInstance, atLeast(1)).getEndTime();
    verify(hisProcInstance).getStartTime();
    verify(hisProcInstance).getId();
    verify(hisProcInstance).getProcessDefinitionKey();
    verify(hisProcInstance).getProcessInstanceId();
    assertEquals("42", actualApplyResult.getId());
    assertEquals("42", actualApplyResult.getInstanceId());
    assertEquals("Process Definition Key", actualApplyResult.getName());
    assertNull(actualApplyResult.getVersion());
    assertNull(actualApplyResult.getStatus());
    Instant endDate = actualApplyResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    assertEquals(1000000L, actualApplyResult.getDuration().toNanos());
    assertSame(endDate, actualApplyResult.getStartDate());
  }
}
