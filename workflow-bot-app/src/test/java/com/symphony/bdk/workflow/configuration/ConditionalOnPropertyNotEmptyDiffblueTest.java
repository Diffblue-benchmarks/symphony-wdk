package com.symphony.bdk.workflow.configuration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.configuration.ConditionalOnPropertyNotEmpty.OnPropertyNotEmptyCondition;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.context.StandardReactiveWebEnvironment;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OnPropertyNotEmptyCondition.class})
@ExtendWith(SpringExtension.class)
class ConditionalOnPropertyNotEmptyDiffblueTest {
  @Autowired private OnPropertyNotEmptyCondition onPropertyNotEmptyCondition;

  /**
   * Test OnPropertyNotEmptyCondition {@link OnPropertyNotEmptyCondition#matches(ConditionContext,
   * AnnotatedTypeMetadata)}.
   *
   * <p>Method under test: {@link OnPropertyNotEmptyCondition#matches(ConditionContext,
   * AnnotatedTypeMetadata)}
   */
  @Test
  @DisplayName("Test OnPropertyNotEmptyCondition matches(ConditionContext, AnnotatedTypeMetadata)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean OnPropertyNotEmptyCondition.matches(ConditionContext, AnnotatedTypeMetadata)"
  })
  void testOnPropertyNotEmptyConditionMatches() {
    // Arrange
    Environment environment = mock(Environment.class);
    when(environment.getProperty(Mockito.<String>any())).thenReturn(Boolean.FALSE.toString());

    ConditionContext context = mock(ConditionContext.class);
    when(context.getEnvironment()).thenReturn(environment);

    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.put("value", "Annotation Attributes");

    AnnotatedTypeMetadata metadata = mock(AnnotatedTypeMetadata.class);
    when(metadata.getAnnotationAttributes(Mockito.<String>any())).thenReturn(stringObjectMap);

    // Act
    boolean actualMatchesResult = onPropertyNotEmptyCondition.matches(context, metadata);

    // Assert
    verify(context).getEnvironment();
    verify(environment).getProperty("Annotation Attributes");
    verify(metadata)
        .getAnnotationAttributes(
            "com.symphony.bdk.workflow.configuration.ConditionalOnPropertyNotEmpty");
    assertFalse(actualMatchesResult);
  }

  /**
   * Test OnPropertyNotEmptyCondition {@link OnPropertyNotEmptyCondition#matches(ConditionContext,
   * AnnotatedTypeMetadata)}.
   *
   * <p>Method under test: {@link OnPropertyNotEmptyCondition#matches(ConditionContext,
   * AnnotatedTypeMetadata)}
   */
  @Test
  @DisplayName("Test OnPropertyNotEmptyCondition matches(ConditionContext, AnnotatedTypeMetadata)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean OnPropertyNotEmptyCondition.matches(ConditionContext, AnnotatedTypeMetadata)"
  })
  void testOnPropertyNotEmptyConditionMatches2() {
    // Arrange
    Environment environment = mock(Environment.class);
    when(environment.getProperty(Mockito.<String>any())).thenReturn("");

    ConditionContext context = mock(ConditionContext.class);
    when(context.getEnvironment()).thenReturn(environment);

    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.put("value", "Annotation Attributes");

    AnnotatedTypeMetadata metadata = mock(AnnotatedTypeMetadata.class);
    when(metadata.getAnnotationAttributes(Mockito.<String>any())).thenReturn(stringObjectMap);

    // Act
    boolean actualMatchesResult = onPropertyNotEmptyCondition.matches(context, metadata);

    // Assert
    verify(context).getEnvironment();
    verify(environment).getProperty("Annotation Attributes");
    verify(metadata)
        .getAnnotationAttributes(
            "com.symphony.bdk.workflow.configuration.ConditionalOnPropertyNotEmpty");
    assertFalse(actualMatchesResult);
  }

  /**
   * Test OnPropertyNotEmptyCondition {@link OnPropertyNotEmptyCondition#matches(ConditionContext,
   * AnnotatedTypeMetadata)}.
   *
   * <ul>
   *   <li>Given {@link StandardReactiveWebEnvironment#StandardReactiveWebEnvironment()}.
   * </ul>
   *
   * <p>Method under test: {@link OnPropertyNotEmptyCondition#matches(ConditionContext,
   * AnnotatedTypeMetadata)}
   */
  @Test
  @DisplayName(
      "Test OnPropertyNotEmptyCondition matches(ConditionContext, AnnotatedTypeMetadata); given StandardReactiveWebEnvironment()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean OnPropertyNotEmptyCondition.matches(ConditionContext, AnnotatedTypeMetadata)"
  })
  void testOnPropertyNotEmptyConditionMatches_givenStandardReactiveWebEnvironment() {
    // Arrange
    ConditionContext context = mock(ConditionContext.class);
    when(context.getEnvironment()).thenReturn(new StandardReactiveWebEnvironment());

    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.put("value", "Annotation Attributes");

    AnnotatedTypeMetadata metadata = mock(AnnotatedTypeMetadata.class);
    when(metadata.getAnnotationAttributes(Mockito.<String>any())).thenReturn(stringObjectMap);

    // Act
    boolean actualMatchesResult = onPropertyNotEmptyCondition.matches(context, metadata);

    // Assert
    verify(context).getEnvironment();
    verify(metadata)
        .getAnnotationAttributes(
            "com.symphony.bdk.workflow.configuration.ConditionalOnPropertyNotEmpty");
    assertFalse(actualMatchesResult);
  }

  /**
   * Test OnPropertyNotEmptyCondition {@link OnPropertyNotEmptyCondition#matches(ConditionContext,
   * AnnotatedTypeMetadata)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link OnPropertyNotEmptyCondition#matches(ConditionContext,
   * AnnotatedTypeMetadata)}
   */
  @Test
  @DisplayName(
      "Test OnPropertyNotEmptyCondition matches(ConditionContext, AnnotatedTypeMetadata); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean OnPropertyNotEmptyCondition.matches(ConditionContext, AnnotatedTypeMetadata)"
  })
  void testOnPropertyNotEmptyConditionMatches_thenReturnTrue() {
    // Arrange
    Environment environment = mock(Environment.class);
    when(environment.getProperty(Mockito.<String>any())).thenReturn("Property");

    ConditionContext context = mock(ConditionContext.class);
    when(context.getEnvironment()).thenReturn(environment);

    HashMap<String, Object> stringObjectMap = new HashMap<>();
    stringObjectMap.put("value", "Annotation Attributes");

    AnnotatedTypeMetadata metadata = mock(AnnotatedTypeMetadata.class);
    when(metadata.getAnnotationAttributes(Mockito.<String>any())).thenReturn(stringObjectMap);

    // Act
    boolean actualMatchesResult = onPropertyNotEmptyCondition.matches(context, metadata);

    // Assert
    verify(context).getEnvironment();
    verify(environment).getProperty("Annotation Attributes");
    verify(metadata)
        .getAnnotationAttributes(
            "com.symphony.bdk.workflow.configuration.ConditionalOnPropertyNotEmpty");
    assertTrue(actualMatchesResult);
  }
}
