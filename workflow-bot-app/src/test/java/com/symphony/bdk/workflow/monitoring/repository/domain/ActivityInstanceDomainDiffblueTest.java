package com.symphony.bdk.workflow.monitoring.repository.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivityInstanceDomain.ActivityInstanceDomainBuilder.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ActivityInstanceDomainDiffblueTest {
  @Autowired
  private ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder;

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityInstanceDomain.ActivityInstanceDomainBuilder#build()}
   *   <li>
   * {@link ActivityInstanceDomain.ActivityInstanceDomainBuilder#duration(Duration)}
   *   <li>
   * {@link ActivityInstanceDomain.ActivityInstanceDomainBuilder#endDate(Instant)}
   *   <li>{@link ActivityInstanceDomain.ActivityInstanceDomainBuilder#id(String)}
   *   <li>{@link ActivityInstanceDomain.ActivityInstanceDomainBuilder#name(String)}
   *   <li>
   * {@link ActivityInstanceDomain.ActivityInstanceDomainBuilder#procInstId(String)}
   *   <li>
   * {@link ActivityInstanceDomain.ActivityInstanceDomainBuilder#startDate(Instant)}
   *   <li>{@link ActivityInstanceDomain.ActivityInstanceDomainBuilder#type(String)}
   *   <li>
   * {@link ActivityInstanceDomain.ActivityInstanceDomainBuilder#workflowId(String)}
   * </ul>
   */
  @Test
  void testActivityInstanceDomainBuilderBuild() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");

    // Act
    ActivityInstanceDomain actualBuildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getId());
    assertEquals("42", actualBuildResult.getProcInstId());
    assertEquals("42", actualBuildResult.getWorkflowId());
    assertEquals("Name", actualBuildResult.getName());
    assertEquals("Type", actualBuildResult.getType());
    assertNull(actualBuildResult.getDuration());
    VariablesDomain variables = actualBuildResult.getVariables();
    assertNull(variables.getUpdateTime());
    assertEquals(0, variables.getRevision());
    Instant endDate = actualBuildResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    assertTrue(variables.getOutputs().isEmpty());
  }

  /**
   * Method under test:
   * {@link ActivityInstanceDomain.ActivityInstanceDomainBuilder#variables(VariablesDomain)}
   */
  @Test
  void testActivityInstanceDomainBuilderVariables() {
    // Arrange
    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(new HashMap<>());
    variables.setRevision(1);
    variables.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(activityInstanceDomainBuilder, activityInstanceDomainBuilder.variables(variables));
  }

  /**
   * Method under test:
   * {@link ActivityInstanceDomain.ActivityInstanceDomainBuilder#variables(VariablesDomain)}
   */
  @Test
  void testActivityInstanceDomainBuilderVariables2() {
    // Arrange
    VariablesDomain variables = mock(VariablesDomain.class);
    doNothing().when(variables).setOutputs(Mockito.<Map<String, Object>>any());
    doNothing().when(variables).setRevision(anyInt());
    doNothing().when(variables).setUpdateTime(Mockito.<Instant>any());
    variables.setOutputs(new HashMap<>());
    variables.setRevision(1);
    variables.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    ActivityInstanceDomain.ActivityInstanceDomainBuilder actualVariablesResult = activityInstanceDomainBuilder
        .variables(variables);

    // Assert
    verify(variables).setOutputs(isA(Map.class));
    verify(variables).setRevision(eq(1));
    verify(variables).setUpdateTime(isA(Instant.class));
    assertSame(activityInstanceDomainBuilder, actualVariablesResult);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityInstanceDomain#equals(Object)}
   *   <li>{@link ActivityInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityInstanceDomain#equals(Object)}
   *   <li>{@link ActivityInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.duration(Mockito.<Duration>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link ActivityInstanceDomain#equals(Object)}
   *   <li>{@link ActivityInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.endDate(Mockito.<Instant>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder2.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.id(Mockito.<String>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder3.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.id(Mockito.<String>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder3.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.name(Mockito.<String>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.id(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder4 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder4.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder3);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder4.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.name(Mockito.<String>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.id(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder4 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder4.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder3);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder4.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name(null)
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder builderResult = ActivityInstanceDomain.builder();
    builderResult.id("42");
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.name(Mockito.<String>any())).thenReturn(builderResult);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.id(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder4 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder4.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder3);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder4.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.procInstId(Mockito.<String>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.name(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.id(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder4 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder4.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder3);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder5 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder5.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder4);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder5.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name(null)
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.procInstId(Mockito.<String>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.name(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.id(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder4 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder4.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder3);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder5 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder5.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder4);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder5.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name(null)
        .procInstId(null);
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder builderResult = ActivityInstanceDomain.builder();
    builderResult.name("Name");
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.procInstId(Mockito.<String>any())).thenReturn(builderResult);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.name(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.id(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder4 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder4.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder3);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder5 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder5.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder4);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder5.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name(null)
        .procInstId("42");
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.startDate(Mockito.<Instant>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.procInstId(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.name(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder4 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder4.id(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder3);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder5 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder5.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder4);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder6 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder6.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder5);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder6.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name(null)
        .procInstId(null);
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.type(Mockito.<String>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.startDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.procInstId(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder4 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder4.name(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder3);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder5 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder5.id(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder4);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder6 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder6.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder5);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder7 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder7.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder6);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder7.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name(null)
        .procInstId(null);
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.workflowId(Mockito.<String>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.type(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.startDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder4 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder4.procInstId(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder3);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder5 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder5.name(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder4);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder6 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder6.id(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder5);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder7 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder7.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder6);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder8 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder8.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder7);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder8.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name(null)
        .procInstId(null);
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.workflowId(Mockito.<String>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.type(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.startDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder4 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder4.procInstId(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder3);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder5 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder5.name(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder4);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder6 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder6.id(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder5);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder7 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder7.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder6);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder8 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder8.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder7);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder8.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name(null)
        .procInstId(null);
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId(null)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder builderResult = ActivityInstanceDomain.builder();
    builderResult.procInstId("42");
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.workflowId(Mockito.<String>any())).thenReturn(builderResult);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.type(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.startDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder4 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder4.procInstId(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder3);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder5 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder5.name(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder4);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder6 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder6.id(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder5);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder7 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder7.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder6);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder8 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder8.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder7);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder8.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name(null)
        .procInstId(null);
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual15() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder.workflowId(Mockito.<String>any())).thenReturn(ActivityInstanceDomain.builder());
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder2 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder2.type(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder3 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder3.startDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder2);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder4 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder4.procInstId(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder3);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder5 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder5.name(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder4);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder6 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder6.id(Mockito.<String>any())).thenReturn(activityInstanceDomainBuilder5);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder7 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder7.endDate(Mockito.<Instant>any())).thenReturn(activityInstanceDomainBuilder6);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder activityInstanceDomainBuilder8 = mock(
        ActivityInstanceDomain.ActivityInstanceDomainBuilder.class);
    when(activityInstanceDomainBuilder8.duration(Mockito.<Duration>any())).thenReturn(activityInstanceDomainBuilder7);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = activityInstanceDomainBuilder8.duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult2 = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .name(null)
        .procInstId(null);
    ActivityInstanceDomain buildResult2 = procInstIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type(null)
        .workflowId(null)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Method under test: {@link ActivityInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ActivityInstanceDomain.ActivityInstanceDomainBuilder durationResult = ActivityInstanceDomain.builder()
        .duration(null);
    ActivityInstanceDomain.ActivityInstanceDomainBuilder procInstIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .name("Name")
        .procInstId("42");
    ActivityInstanceDomain buildResult = procInstIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .type("Type")
        .workflowId("42")
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to ActivityInstanceDomain");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ActivityInstanceDomain#ActivityInstanceDomain(String, String, String, String, String, Instant, Instant, Duration, VariablesDomain)}
   *   <li>{@link ActivityInstanceDomain#setVariables(VariablesDomain)}
   *   <li>{@link ActivityInstanceDomain#toString()}
   *   <li>{@link ActivityInstanceDomain#getDuration()}
   *   <li>{@link ActivityInstanceDomain#getEndDate()}
   *   <li>{@link ActivityInstanceDomain#getId()}
   *   <li>{@link ActivityInstanceDomain#getName()}
   *   <li>{@link ActivityInstanceDomain#getProcInstId()}
   *   <li>{@link ActivityInstanceDomain#getStartDate()}
   *   <li>{@link ActivityInstanceDomain#getType()}
   *   <li>{@link ActivityInstanceDomain#getVariables()}
   *   <li>{@link ActivityInstanceDomain#getWorkflowId()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    Instant startDate = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
    Instant endDate = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    VariablesDomain variables = new VariablesDomain();
    variables.setOutputs(new HashMap<>());
    variables.setRevision(1);
    variables.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act
    ActivityInstanceDomain actualActivityInstanceDomain = new ActivityInstanceDomain("42", "Name", "42", "42", "Type",
        startDate, endDate, null, variables);
    VariablesDomain variables2 = new VariablesDomain();
    variables2.setOutputs(new HashMap<>());
    variables2.setRevision(1);
    variables2.setUpdateTime(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualActivityInstanceDomain.setVariables(variables2);
    String actualToStringResult = actualActivityInstanceDomain.toString();
    actualActivityInstanceDomain.getDuration();
    Instant actualEndDate = actualActivityInstanceDomain.getEndDate();
    String actualId = actualActivityInstanceDomain.getId();
    String actualName = actualActivityInstanceDomain.getName();
    String actualProcInstId = actualActivityInstanceDomain.getProcInstId();
    Instant actualStartDate = actualActivityInstanceDomain.getStartDate();
    String actualType = actualActivityInstanceDomain.getType();
    VariablesDomain actualVariables = actualActivityInstanceDomain.getVariables();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("42", actualProcInstId);
    assertEquals("42", actualActivityInstanceDomain.getWorkflowId());
    assertEquals("ActivityInstanceDomain(id=42, name=Name, procInstId=42, workflowId=42, type=Type, startDate=1970-01"
        + "-01T00:00:00Z, endDate=1970-01-01T00:00:00Z, duration=null, variables=VariablesDomain(outputs={},"
        + " revision=1, updateTime=1970-01-01T00:00:00Z))", actualToStringResult);
    assertEquals("Name", actualName);
    assertEquals("Type", actualType);
    assertSame(variables2, actualVariables);
    Instant instant = actualStartDate.EPOCH;
    assertSame(instant, actualEndDate);
    assertSame(instant, actualStartDate);
  }
}
