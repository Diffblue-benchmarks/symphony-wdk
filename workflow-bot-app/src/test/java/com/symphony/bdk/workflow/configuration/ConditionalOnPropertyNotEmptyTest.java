package com.symphony.bdk.workflow.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class ConditionalOnPropertyNotEmptyTest {

  private ConditionalOnPropertyNotEmpty.OnPropertyNotEmptyCondition condition;

  @Mock
  private ConditionContext context;

  @Mock
  private AnnotatedTypeMetadata metadata;

  @Mock
  private Environment environment;

  @BeforeEach
  void setUp() {
    condition = new ConditionalOnPropertyNotEmpty.OnPropertyNotEmptyCondition();
  }

  @Test
  void matches_shouldReturnFalseWhenAnnotationAttributesAreNull() {
    // Arrange
    when(metadata.getAnnotationAttributes(ConditionalOnPropertyNotEmpty.class.getName())).thenReturn(null);

    // Act
    boolean result = condition.matches(context, metadata);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void matches_shouldReturnTrueWhenPropertyIsNotEmptyAndNotFalse() {
    // Arrange
    Map<String, Object> attrs = new HashMap<>();
    attrs.put("value", "test.property");

    when(metadata.getAnnotationAttributes(ConditionalOnPropertyNotEmpty.class.getName())).thenReturn(attrs);
    when(context.getEnvironment()).thenReturn(environment);
    when(environment.getProperty("test.property")).thenReturn("someValue");

    // Act
    boolean result = condition.matches(context, metadata);

    // Assert
    assertThat(result).isTrue();
  }

  @Test
  void matches_shouldReturnFalseWhenPropertyIsNull() {
    // Arrange
    Map<String, Object> attrs = new HashMap<>();
    attrs.put("value", "test.property");

    when(metadata.getAnnotationAttributes(ConditionalOnPropertyNotEmpty.class.getName())).thenReturn(attrs);
    when(context.getEnvironment()).thenReturn(environment);
    when(environment.getProperty("test.property")).thenReturn(null);

    // Act
    boolean result = condition.matches(context, metadata);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void matches_shouldReturnFalseWhenPropertyIsBlank() {
    // Arrange
    Map<String, Object> attrs = new HashMap<>();
    attrs.put("value", "test.property");

    when(metadata.getAnnotationAttributes(ConditionalOnPropertyNotEmpty.class.getName())).thenReturn(attrs);
    when(context.getEnvironment()).thenReturn(environment);
    when(environment.getProperty("test.property")).thenReturn("   ");

    // Act
    boolean result = condition.matches(context, metadata);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void matches_shouldReturnFalseWhenPropertyIsFalse() {
    // Arrange
    Map<String, Object> attrs = new HashMap<>();
    attrs.put("value", "test.property");

    when(metadata.getAnnotationAttributes(ConditionalOnPropertyNotEmpty.class.getName())).thenReturn(attrs);
    when(context.getEnvironment()).thenReturn(environment);
    when(environment.getProperty("test.property")).thenReturn("false");

    // Act
    boolean result = condition.matches(context, metadata);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void matches_shouldReturnFalseWhenPropertyIsEmptyString() {
    // Arrange
    Map<String, Object> attrs = new HashMap<>();
    attrs.put("value", "test.property");

    when(metadata.getAnnotationAttributes(ConditionalOnPropertyNotEmpty.class.getName())).thenReturn(attrs);
    when(context.getEnvironment()).thenReturn(environment);
    when(environment.getProperty("test.property")).thenReturn("");

    // Act
    boolean result = condition.matches(context, metadata);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void matches_shouldReturnTrueWhenPropertyIsTrue() {
    // Arrange
    Map<String, Object> attrs = new HashMap<>();
    attrs.put("value", "test.property");

    when(metadata.getAnnotationAttributes(ConditionalOnPropertyNotEmpty.class.getName())).thenReturn(attrs);
    when(context.getEnvironment()).thenReturn(environment);
    when(environment.getProperty("test.property")).thenReturn("true");

    // Act
    boolean result = condition.matches(context, metadata);

    // Assert
    assertThat(result).isTrue();
  }
}
