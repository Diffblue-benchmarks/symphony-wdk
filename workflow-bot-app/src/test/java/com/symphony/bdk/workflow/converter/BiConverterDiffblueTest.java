package com.symphony.bdk.workflow.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.symphony.bdk.workflow.engine.camunda.monitoring.converter.WorkflowInstDomainVersionConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BiConverterDiffblueTest {
  /**
   * Test {@link BiConverter#getSourceClass()}.
   *
   * <p>Method under test: {@link BiConverter#getSourceClass()}
   */
  @Test
  @DisplayName("Test getSourceClass()")
  @Tag("MaintainedByDiffblue")
  void testGetSourceClass() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    Class<HistoricProcessInstanceEntity> actualSourceClass =
        new WorkflowInstDomainVersionConverter().getSourceClass();

    // Assert
    Class<HistoricProcessInstanceEntity> expectedSourceClass = HistoricProcessInstanceEntity.class;
    assertEquals(expectedSourceClass, actualSourceClass);
  }

  /**
   * Test {@link BiConverter#getTargetClass()}.
   *
   * <p>Method under test: {@link BiConverter#getTargetClass()}
   */
  @Test
  @DisplayName("Test getTargetClass()")
  @Tag("MaintainedByDiffblue")
  void testGetTargetClass() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    Class<WorkflowInstanceDomain> actualTargetClass =
        new WorkflowInstDomainVersionConverter().getTargetClass();

    // Assert
    Class<WorkflowInstanceDomain> expectedTargetClass = WorkflowInstanceDomain.class;
    assertEquals(expectedTargetClass, actualTargetClass);
  }
}
