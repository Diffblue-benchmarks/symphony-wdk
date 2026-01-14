package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowInstDomainConverter.class})
@ExtendWith(SpringExtension.class)
class WorkflowInstDomainConverterDiffblueTest {
  @Autowired private WorkflowInstDomainConverter workflowInstDomainConverter;

  /**
   * Test {@link WorkflowInstDomainConverter#apply(HistoricProcessInstanceEntity)} with {@code
   * HistoricProcessInstanceEntity}.
   *
   * <ul>
   *   <li>Then return Version is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link WorkflowInstDomainConverter#apply(HistoricProcessInstanceEntity)}
   */
  @Test
  @DisplayName(
      "Test apply(HistoricProcessInstanceEntity) with 'HistoricProcessInstanceEntity'; then return Version is 'null'")
  @Tag("MaintainedByDiffblue")
  void testApplyWithHistoricProcessInstanceEntity_thenReturnVersionIsNull() {
    // Arrange
    HistoricProcessInstanceEntity hisProcInstance = new HistoricProcessInstanceEntity();
    hisProcInstance.setStartTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

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
}
