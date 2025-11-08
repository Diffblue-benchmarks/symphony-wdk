package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
  @Autowired
  private WorkflowInstDomainVersionConverter workflowInstDomainVersionConverter;

  /**
   * Test {@link WorkflowInstDomainVersionConverter#apply(HistoricProcessInstanceEntity, Map)} with {@code HistoricProcessInstanceEntity}, {@code Map}.
   * <ul>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstDomainVersionConverter#apply(HistoricProcessInstanceEntity, Map)}
   */
  @Test
  @DisplayName("Test apply(HistoricProcessInstanceEntity, Map) with 'HistoricProcessInstanceEntity', 'Map'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain WorkflowInstDomainVersionConverter.apply(HistoricProcessInstanceEntity, Map)"})
  void testApplyWithHistoricProcessInstanceEntityMap_thenReturnNull() {
    // Arrange
    HistoricProcessInstanceEntity hisProcInstance = new HistoricProcessInstanceEntity();

    // Act and Assert
    assertNull(workflowInstDomainVersionConverter.apply(hisProcInstance, new HashMap<>()));
  }
}
