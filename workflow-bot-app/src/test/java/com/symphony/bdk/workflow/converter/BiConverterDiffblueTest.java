package com.symphony.bdk.workflow.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.camunda.monitoring.converter.WorkflowInstDomainVersionConverter;
import com.symphony.bdk.workflow.monitoring.repository.domain.WorkflowInstanceDomain;
import org.camunda.bpm.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class BiConverterDiffblueTest {
  /**
   * Test {@link BiConverter#getSourceClass()}.
   *
   * <p>Method under test: {@link BiConverter#getSourceClass()}
   */
  @Test
  @DisplayName("Test getSourceClass()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class BiConverter.getSourceClass()"})
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
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"Class BiConverter.getTargetClass()"})
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

  /**
   * Test {@link BiConverter#applyCollection(List, Object)}.
   *
   * <p>Method under test: {@link BiConverter#applyCollection(List, Object)}
   */
  @Test
  @DisplayName("Test applyCollection() returns mapped non-null results")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"List BiConverter.applyCollection(List, Object)"})
  void testApplyCollection_returnsNonNullResults() {
    // Arrange
    BiConverter<String, String, Integer> converter = (s, k) -> s.length();
    List<String> source = Arrays.asList("hello", "world", "!");

    // Act
    List<Integer> result = converter.applyCollection(source, "key");

    // Assert
    assertEquals(3, result.size());
    assertEquals(5, result.get(0));
    assertEquals(5, result.get(1));
    assertEquals(1, result.get(2));
  }

  /**
   * Test {@link BiConverter#applyCollection(List, Object)} filters null results.
   *
   * <p>Method under test: {@link BiConverter#applyCollection(List, Object)}
   */
  @Test
  @DisplayName("Test applyCollection() filters out null results")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"List BiConverter.applyCollection(List, Object)"})
  void testApplyCollection_filtersNullResults() {
    // Arrange
    BiConverter<String, String, Integer> converter = (s, k) -> s.isEmpty() ? null : s.length();
    List<String> source = Arrays.asList("hello", "", "world");

    // Act
    List<Integer> result = converter.applyCollection(source, "key");

    // Assert
    assertEquals(2, result.size());
    assertEquals(5, result.get(0));
    assertEquals(5, result.get(1));
  }

  /**
   * Test {@link BiConverter#applyCollection(List, Object)} with empty source list.
   *
   * <p>Method under test: {@link BiConverter#applyCollection(List, Object)}
   */
  @Test
  @DisplayName("Test applyCollection() with empty source returns empty list")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"List BiConverter.applyCollection(List, Object)"})
  void testApplyCollection_emptySource() {
    // Arrange
    BiConverter<String, String, Integer> converter = (s, k) -> s.length();

    // Act
    List<Integer> result = converter.applyCollection(Collections.emptyList(), "key");

    // Assert
    assertTrue(result.isEmpty());
  }
}
