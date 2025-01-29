package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivityDomainConverter.class})
@ExtendWith(SpringExtension.class)
class ActivityDomainConverterDiffblueTest {
  @Autowired
  private ActivityDomainConverter activityDomainConverter;

  /**
   * Test {@link ActivityDomainConverter#apply(HistoricActivityInstanceEntity)}
   * with {@code HistoricActivityInstanceEntity}.
   * <ul>
   *   <li>Given one.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivityDomainConverter#apply(HistoricActivityInstanceEntity)}
   */
  @Test
  @DisplayName("Test apply(HistoricActivityInstanceEntity) with 'HistoricActivityInstanceEntity'; given one; then return Id is '42'")
  void testApplyWithHistoricActivityInstanceEntity_givenOne_thenReturnIdIs42() {
    // Arrange
    HistoricActivityInstanceEntity historicActivityInstance = mock(HistoricActivityInstanceEntity.class);
    when(historicActivityInstance.getDurationInMillis()).thenReturn(1L);
    when(historicActivityInstance.getEndTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    when(historicActivityInstance.getActivityId()).thenReturn("42");
    when(historicActivityInstance.getActivityName()).thenReturn("Activity Name");
    when(historicActivityInstance.getActivityType()).thenReturn("Activity Type");
    when(historicActivityInstance.getProcessDefinitionKey()).thenReturn("Process Definition Key");
    when(historicActivityInstance.getProcessInstanceId()).thenReturn("42");
    when(historicActivityInstance.getStartTime())
        .thenReturn(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    ActivityInstanceDomain actualApplyResult = activityDomainConverter.apply(historicActivityInstance);

    // Assert
    verify(historicActivityInstance).getActivityId();
    verify(historicActivityInstance).getActivityName();
    verify(historicActivityInstance).getActivityType();
    verify(historicActivityInstance, atLeast(1)).getDurationInMillis();
    verify(historicActivityInstance, atLeast(1)).getEndTime();
    verify(historicActivityInstance).getStartTime();
    verify(historicActivityInstance).getProcessDefinitionKey();
    verify(historicActivityInstance).getProcessInstanceId();
    assertEquals("42", actualApplyResult.getId());
    assertEquals("42", actualApplyResult.getProcInstId());
    assertEquals("Activity Name", actualApplyResult.getName());
    assertEquals("Activity Type", actualApplyResult.getType());
    assertEquals("Process Definition Key", actualApplyResult.getWorkflowId());
    Instant endDate = actualApplyResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    assertEquals(1000000L, actualApplyResult.getDuration().toNanos());
    assertSame(endDate, actualApplyResult.getStartDate());
  }

  /**
   * Test {@link ActivityDomainConverter#apply(HistoricActivityInstanceEntity)}
   * with {@code HistoricActivityInstanceEntity}.
   * <ul>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivityDomainConverter#apply(HistoricActivityInstanceEntity)}
   */
  @Test
  @DisplayName("Test apply(HistoricActivityInstanceEntity) with 'HistoricActivityInstanceEntity'; then return Id is 'null'")
  void testApplyWithHistoricActivityInstanceEntity_thenReturnIdIsNull() {
    // Arrange
    HistoricActivityInstanceEntity historicActivityInstance = new HistoricActivityInstanceEntity();
    historicActivityInstance
        .setStartTime(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    // Act
    ActivityInstanceDomain actualApplyResult = activityDomainConverter.apply(historicActivityInstance);

    // Assert
    assertNull(actualApplyResult.getId());
    assertNull(actualApplyResult.getName());
    assertNull(actualApplyResult.getProcInstId());
    assertNull(actualApplyResult.getType());
    assertNull(actualApplyResult.getWorkflowId());
    assertNull(actualApplyResult.getDuration());
    assertNull(actualApplyResult.getEndDate());
    Instant startDate = actualApplyResult.getStartDate();
    assertEquals(0, startDate.getNano());
    assertEquals(0L, startDate.getEpochSecond());
  }
}
