package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowInstDomainVersionConverter.class})
@ExtendWith(SpringExtension.class)
class WorkflowInstDomainVersionConverterDiffblueTest {
  @Autowired private WorkflowInstDomainVersionConverter workflowInstDomainVersionConverter;

  /**
   * Test {@link WorkflowInstDomainVersionConverter#apply(HistoricProcessInstanceEntity, Map)} with
   * {@code HistoricProcessInstanceEntity}, {@code Map}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * WorkflowInstDomainVersionConverter#apply(HistoricProcessInstanceEntity, Map)}
   */
  @Test
  @DisplayName(
      "Test apply(HistoricProcessInstanceEntity, Map) with 'HistoricProcessInstanceEntity', 'Map'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain WorkflowInstDomainVersionConverter.apply(HistoricProcessInstanceEntity, Map)"
  })
  void testApplyWithHistoricProcessInstanceEntityMap_thenReturnNull() {
    // Arrange
    HistoricProcessInstanceEntity hisProcInstance = new HistoricProcessInstanceEntity();

    // Act and Assert
    assertNull(workflowInstDomainVersionConverter.apply(hisProcInstance, new HashMap<>()));
  }

  /**
   * Test {@link WorkflowInstDomainVersionConverter#apply(HistoricProcessInstanceEntity, Map)} with
   * {@code HistoricProcessInstanceEntity}, {@code Map}.
   *
   * <ul>
   *   <li>When map contains the processDefinitionId, then return domain with expected version.
   * </ul>
   *
   * <p>Method under test: {@link
   * WorkflowInstDomainVersionConverter#apply(HistoricProcessInstanceEntity, Map)}
   */
  @Test
  @DisplayName(
      "Test apply(HistoricProcessInstanceEntity, Map) with 'HistoricProcessInstanceEntity', 'Map'; when version found then return domain with version")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({
    "com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain WorkflowInstDomainVersionConverter.apply(HistoricProcessInstanceEntity, Map)"
  })
  void testApplyWithHistoricProcessInstanceEntityMap_whenVersionFound_thenReturnDomainWithVersion() {
    // Arrange
    HistoricProcessInstanceEntity hisProcInstance = new HistoricProcessInstanceEntity();
    hisProcInstance.setProcessDefinitionId("proc-def-1");
    hisProcInstance.setStartTime(
        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));

    Map<String, String> procIdVersionTagMap = new HashMap<>();
    procIdVersionTagMap.put("proc-def-1", "42");

    // Act
    WorkflowInstanceDomain result =
        workflowInstDomainVersionConverter.apply(hisProcInstance, procIdVersionTagMap);

    // Assert
    assertNotNull(result);
    assertEquals(42L, result.getVersion());
  }
}
