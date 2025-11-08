package com.symphony.bdk.workflow.management.converter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowVersionConverter.class})
@ExtendWith(SpringExtension.class)
class WorkflowVersionConverterDiffblueTest {
  @Autowired
  private WorkflowVersionConverter workflowVersionConverter;

  /**
   * Method under test: {@link WorkflowVersionConverter#apply(String, Long)}
   */
  @Test
  void testApply() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> workflowVersionConverter.apply("Not all who wander are lost", 1L));
    assertThrows(IllegalArgumentException.class, () -> workflowVersionConverter.apply("", 1L));
    assertThrows(IllegalArgumentException.class, () -> workflowVersionConverter.apply("'", 1L));
  }
}
