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
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowInstDomainVersionConverter.class})
@ExtendWith(SpringExtension.class)
class WorkflowInstDomainVersionConverterDiffblueTest {
  @Autowired
  private WorkflowInstDomainVersionConverter workflowInstDomainVersionConverter;

  /**
   * Method under test:
   * {@link WorkflowInstDomainVersionConverter#apply(HistoricProcessInstanceEntity, Map)}
   */
  @Test
  void testApply() {
    // Arrange
    HistoricProcessInstanceEntity hisProcInstance = new HistoricProcessInstanceEntity();

    // Act and Assert
    assertNull(workflowInstDomainVersionConverter.apply(hisProcInstance, new HashMap<>()));
  }

  /**
   * Method under test:
   * {@link WorkflowInstDomainVersionConverter#apply(HistoricProcessInstanceEntity, Map)}
   */
  @Test
  void testApply2() {
    // Arrange
    HistoricProcessInstanceEntity hisProcInstance = mock(HistoricProcessInstanceEntity.class);
    when(hisProcInstance.getProcessDefinitionId()).thenReturn("42");

    // Act
    WorkflowInstanceDomain actualApplyResult = workflowInstDomainVersionConverter.apply(hisProcInstance,
        new HashMap<>());

    // Assert
    verify(hisProcInstance).getProcessDefinitionId();
    assertNull(actualApplyResult);
  }

  /**
   * Method under test:
   * {@link WorkflowInstDomainVersionConverter#apply(HistoricProcessInstanceEntity, Map)}
   */
  @Test
  void testApply3() {
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
    when(hisProcInstance.getProcessDefinitionId()).thenReturn("42");

    HashMap<String, String> procIdVersionTagMap = new HashMap<>();
    procIdVersionTagMap.put("42", "42");
    procIdVersionTagMap.put("foo", "foo");

    // Act
    WorkflowInstanceDomain actualApplyResult = workflowInstDomainVersionConverter.apply(hisProcInstance,
        procIdVersionTagMap);

    // Assert
    verify(hisProcInstance).getEndActivityId();
    verify(hisProcInstance).getState();
    verify(hisProcInstance, atLeast(1)).getDurationInMillis();
    verify(hisProcInstance, atLeast(1)).getEndTime();
    verify(hisProcInstance).getStartTime();
    verify(hisProcInstance).getId();
    verify(hisProcInstance).getProcessDefinitionId();
    verify(hisProcInstance).getProcessDefinitionKey();
    verify(hisProcInstance).getProcessInstanceId();
    assertEquals("42", actualApplyResult.getId());
    assertEquals("42", actualApplyResult.getInstanceId());
    assertEquals("Process Definition Key", actualApplyResult.getName());
    assertNull(actualApplyResult.getStatus());
    Instant endDate = actualApplyResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    assertEquals(1000000L, actualApplyResult.getDuration().toNanos());
    assertEquals(42L, actualApplyResult.getVersion().longValue());
    assertSame(endDate, actualApplyResult.getStartDate());
  }
}
