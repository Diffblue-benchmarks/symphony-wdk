package com.symphony.bdk.workflow.monitoring.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowViewConverter.class})
@ExtendWith(SpringExtension.class)
class WorkflowViewConverterDiffblueTest {
  @Autowired
  private WorkflowViewConverter workflowViewConverter;

  /**
   * Test {@link WorkflowViewConverter#apply(WorkflowDomain)} with
   * {@code WorkflowDomain}.
   * <ul>
   *   <li>Then return Id is {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowViewConverter#apply(WorkflowDomain)}
   */
  @Test
  @DisplayName("Test apply(WorkflowDomain) with 'WorkflowDomain'; then return Id is 'Name'")
  void testApplyWithWorkflowDomain_thenReturnIdIsName() {
    // Arrange and Act
    WorkflowView actualApplyResult = workflowViewConverter.apply(new WorkflowDomain("42", "Name", 1L));

    // Assert
    assertEquals("Name", actualApplyResult.getId());
    assertNull(actualApplyResult.getCreatedBy());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
  }
}
