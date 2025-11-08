package com.symphony.bdk.workflow.management.converter;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowConverter.class})
@ExtendWith(SpringExtension.class)
class WorkflowConverterDiffblueTest {
  @Autowired
  private WorkflowConverter workflowConverter;

  /**
   * Method under test: {@link WorkflowConverter#apply(String)}
   */
  @Test
  void testApply() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> workflowConverter.apply("Not all who wander are lost"));
    assertThrows(IllegalArgumentException.class, () -> workflowConverter.apply(".properties"));
    assertThrows(IllegalArgumentException.class, () -> workflowConverter.apply(" "));
    assertThrows(IllegalArgumentException.class, () -> workflowConverter.apply("'"));
  }
}
