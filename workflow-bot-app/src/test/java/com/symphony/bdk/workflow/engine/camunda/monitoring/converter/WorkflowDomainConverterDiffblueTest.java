package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowDomainConverter.class})
@ExtendWith(SpringExtension.class)
class WorkflowDomainConverterDiffblueTest {
  @Autowired
  private WorkflowDomainConverter workflowDomainConverter;

  /**
   * Test {@link WorkflowDomainConverter#apply(ProcessDefinitionEntity)} with
   * {@code ProcessDefinitionEntity}.
   * <ul>
   *   <li>Then return Version is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link WorkflowDomainConverter#apply(ProcessDefinitionEntity)}
   */
  @Test
  @DisplayName("Test apply(ProcessDefinitionEntity) with 'ProcessDefinitionEntity'; then return Version is 'null'")
  void testApplyWithProcessDefinitionEntity_thenReturnVersionIsNull() {
    // Arrange and Act
    WorkflowDomain actualApplyResult = workflowDomainConverter.apply(new ProcessDefinitionEntity());

    // Assert
    assertNull(actualApplyResult.getVersion());
    assertNull(actualApplyResult.getId());
    assertNull(actualApplyResult.getName());
  }
}
