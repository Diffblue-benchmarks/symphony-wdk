package com.symphony.bdk.workflow.monitoring.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowView;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowDomain;
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
   * Method under test: {@link WorkflowViewConverter#apply(WorkflowDomain)}
   */
  @Test
  void testApply() {
    // Arrange and Act
    WorkflowView actualApplyResult = workflowViewConverter.apply(new WorkflowDomain("42", "Name", 1L));

    // Assert
    assertEquals("Name", actualApplyResult.getId());
    assertNull(actualApplyResult.getCreatedBy());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
  }

  /**
   * Method under test: {@link WorkflowViewConverter#apply(WorkflowDomain)}
   */
  @Test
  void testApply2() {
    // Arrange
    WorkflowDomain workflowDomain = mock(WorkflowDomain.class);
    when(workflowDomain.getVersion()).thenReturn(1L);
    when(workflowDomain.getName()).thenReturn("Name");

    // Act
    WorkflowView actualApplyResult = workflowViewConverter.apply(workflowDomain);

    // Assert
    verify(workflowDomain).getName();
    verify(workflowDomain).getVersion();
    assertEquals("Name", actualApplyResult.getId());
    assertNull(actualApplyResult.getCreatedBy());
    assertEquals(1L, actualApplyResult.getVersion().longValue());
  }
}
