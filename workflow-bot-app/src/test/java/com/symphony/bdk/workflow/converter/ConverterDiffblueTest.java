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
import java.util.Arrays;
import java.util.List;

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

  /**
   * Test {@link Converter#applyCollection(List)}.
   *
   * <p>Method under test: {@link Converter#applyCollection(List)}
   */
  @Test
  @DisplayName("Test applyCollection(List)")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"List Converter.applyCollection(List)"})
  void testApplyCollection() {
    // Arrange
    Converter<String, String> converter = new Converter<String, String>() {
      @Override
      public String apply(String s) {
        return "mapped_" + s;
      }
    };
    List<String> source = Arrays.asList("a", "b", "c");

    // Act
    List<String> result = converter.applyCollection(source);

    // Assert
    assertEquals(3, result.size());
    assertEquals("mapped_a", result.get(0));
    assertEquals("mapped_b", result.get(1));
    assertEquals("mapped_c", result.get(2));
  }

  /**
   * Test {@link Converter#applyCollection(List)} filters out null results.
   *
   * <p>Method under test: {@link Converter#applyCollection(List)}
   */
  @Test
  @DisplayName("Test applyCollection(List) filters nulls")
  @Tag("ContributionFromDiffblue")
  @MethodsUnderTest({"List Converter.applyCollection(List)"})
  void testApplyCollection_filtersNulls() {
    // Arrange
    Converter<String, String> converter = new Converter<String, String>() {
      @Override
      public String apply(String s) {
        return s.isEmpty() ? null : s.toUpperCase();
      }
    };
    List<String> source = Arrays.asList("hello", "", "world");

    // Act
    List<String> result = converter.applyCollection(source);

    // Assert
    assertEquals(2, result.size());
    assertEquals("HELLO", result.get(0));
    assertEquals("WORLD", result.get(1));
  }
}
