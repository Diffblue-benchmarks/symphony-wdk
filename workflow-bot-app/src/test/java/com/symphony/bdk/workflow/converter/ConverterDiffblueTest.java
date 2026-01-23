package com.symphony.bdk.workflow.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.camunda.monitoring.converter.ActivityDomainConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.ActivityInstanceDomain;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ConverterDiffblueTest {
  /**
   * Test {@link Converter#getSourceClass()}.
   *
   * <p>Method under test: {@link Converter#getSourceClass()}
   */
  @Test
  @DisplayName("Test getSourceClass()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class Converter.getSourceClass()"})
  void testGetSourceClass() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    Class<HistoricActivityInstanceEntity> actualSourceClass =
        new ActivityDomainConverter().getSourceClass();

    // Assert
    Class<HistoricActivityInstanceEntity> expectedSourceClass =
        HistoricActivityInstanceEntity.class;
    assertEquals(expectedSourceClass, actualSourceClass);
  }

  /**
   * Test {@link Converter#getTargetClass()}.
   *
   * <p>Method under test: {@link Converter#getTargetClass()}
   */
  @Test
  @DisplayName("Test getTargetClass()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class Converter.getTargetClass()"})
  void testGetTargetClass() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    Class<ActivityInstanceDomain> actualTargetClass =
        new ActivityDomainConverter().getTargetClass();

    // Assert
    Class<ActivityInstanceDomain> expectedTargetClass = ActivityInstanceDomain.class;
    assertEquals(expectedTargetClass, actualTargetClass);
  }
}
