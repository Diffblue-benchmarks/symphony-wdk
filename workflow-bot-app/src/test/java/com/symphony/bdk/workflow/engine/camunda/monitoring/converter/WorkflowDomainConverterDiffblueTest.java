package com.symphony.bdk.workflow.engine.camunda.monitoring.converter;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
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
   * Method under test:
   * {@link WorkflowDomainConverter#apply(ProcessDefinitionEntity)}
   */
  @Test
  void testApply() {
    // Arrange and Act
    WorkflowDomain actualApplyResult = workflowDomainConverter.apply(new ProcessDefinitionEntity());

    // Assert
    assertNull(actualApplyResult.getVersion());
    assertNull(actualApplyResult.getId());
    assertNull(actualApplyResult.getName());
  }

  /**
   * Method under test:
   * {@link WorkflowDomainConverter#apply(ProcessDefinitionEntity)}
   */
  @Test
  void testApply2() {
    // Arrange
    ProcessDefinitionEntity processDefinition = new ProcessDefinitionEntity();
    processDefinition.addToBacklog("Activity Ref", mock(ScopeImpl.BacklogErrorCallback.class));

    // Act
    WorkflowDomain actualApplyResult = workflowDomainConverter.apply(processDefinition);

    // Assert
    assertNull(actualApplyResult.getVersion());
    assertNull(actualApplyResult.getId());
    assertNull(actualApplyResult.getName());
  }
}
