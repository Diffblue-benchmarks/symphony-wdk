package com.symphony.bdk.workflow.monitoring.repository.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WorkflowInstanceDomainDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstanceDomain#equals(Object)}
   *   <li>{@link WorkflowInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstanceDomain#equals(Object)}
   *   <li>{@link WorkflowInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.duration(Mockito.<Duration>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstanceDomain#equals(Object)}
   *   <li>{@link WorkflowInstanceDomain#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.endDate(Mockito.<Instant>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder2.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.id(Mockito.<String>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.endDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder3 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder3.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder2);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder3.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.id(Mockito.<String>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.endDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder3 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder3.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder2);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder3.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(3L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.id(Mockito.<String>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.endDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder3 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder3.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder2);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder3.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(null)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.id(Mockito.<String>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.endDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder3 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder3.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder2);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder3.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.id(Mockito.<String>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.endDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder3 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder3.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder2);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder3.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(null)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(null)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.instanceId(Mockito.<String>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.id(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder3 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder3.endDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder2);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder4 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder4.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder3);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder4.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.name(Mockito.<String>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.instanceId(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder3 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder3.id(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder2);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder4 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder4.endDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder3);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder5 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder5.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder4);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder5.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.name(Mockito.<String>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.instanceId(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder3 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder3.id(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder2);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder4 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder4.endDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder3);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder5 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder5.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder4);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder5.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId("42")
        .name(null);
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder builderResult = WorkflowInstanceDomain.builder();
    builderResult.id("42");
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.name(Mockito.<String>any())).thenReturn(builderResult);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.instanceId(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder3 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder3.id(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder2);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder4 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder4.endDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder3);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder5 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder5.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder4);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder5.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.name(Mockito.<String>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.instanceId(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder3 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder3.id(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder2);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder4 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder4.endDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder3);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder5 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder5.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder4);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder5.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId(null)
        .name(null);
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder builderResult = WorkflowInstanceDomain.builder();
    builderResult.name("Name");
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.name(Mockito.<String>any())).thenReturn(builderResult);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.instanceId(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder3 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder3.id(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder2);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder4 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder4.endDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder3);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder5 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder5.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder4);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder5.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId("42")
        .name(null);
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.startDate(Mockito.<Instant>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.name(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder3 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder3.instanceId(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder2);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder4 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder4.id(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder3);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder5 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder5.endDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder4);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder6 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder6.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder5);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder6.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId(null)
        .name(null);
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual14() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder.status(Mockito.<String>any())).thenReturn(WorkflowInstanceDomain.builder());
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder2 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder2.startDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder3 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder3.name(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder2);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder4 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder4.instanceId(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder3);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder5 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder5.id(Mockito.<String>any())).thenReturn(workflowInstanceDomainBuilder4);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder6 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder6.endDate(Mockito.<Instant>any())).thenReturn(workflowInstanceDomainBuilder5);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder workflowInstanceDomainBuilder7 = mock(
        WorkflowInstanceDomain.WorkflowInstanceDomainBuilder.class);
    when(workflowInstanceDomainBuilder7.duration(Mockito.<Duration>any())).thenReturn(workflowInstanceDomainBuilder6);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = workflowInstanceDomainBuilder7.duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult2 = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId(null)
        .name(null);
    WorkflowInstanceDomain buildResult2 = nameResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Method under test: {@link WorkflowInstanceDomain#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");
    WorkflowInstanceDomain buildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to WorkflowInstanceDomain");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link WorkflowInstanceDomain#WorkflowInstanceDomain(String, String, Long, String, String, Instant, Instant, Duration)}
   *   <li>{@link WorkflowInstanceDomain#toString()}
   *   <li>{@link WorkflowInstanceDomain#getDuration()}
   *   <li>{@link WorkflowInstanceDomain#getEndDate()}
   *   <li>{@link WorkflowInstanceDomain#getId()}
   *   <li>{@link WorkflowInstanceDomain#getInstanceId()}
   *   <li>{@link WorkflowInstanceDomain#getName()}
   *   <li>{@link WorkflowInstanceDomain#getStartDate()}
   *   <li>{@link WorkflowInstanceDomain#getStatus()}
   *   <li>{@link WorkflowInstanceDomain#getVersion()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    Instant startDate = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    WorkflowInstanceDomain actualWorkflowInstanceDomain = new WorkflowInstanceDomain("42", "Name", 1L, "42", "Status",
        startDate, LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(), null);
    String actualToStringResult = actualWorkflowInstanceDomain.toString();
    Duration actualDuration = actualWorkflowInstanceDomain.getDuration();
    Instant actualEndDate = actualWorkflowInstanceDomain.getEndDate();
    String actualId = actualWorkflowInstanceDomain.getId();
    String actualInstanceId = actualWorkflowInstanceDomain.getInstanceId();
    String actualName = actualWorkflowInstanceDomain.getName();
    Instant actualStartDate = actualWorkflowInstanceDomain.getStartDate();
    String actualStatus = actualWorkflowInstanceDomain.getStatus();

    // Assert
    assertEquals("42", actualId);
    assertEquals("42", actualInstanceId);
    assertEquals("Name", actualName);
    assertEquals("Status", actualStatus);
    assertEquals("WorkflowInstanceDomain(id=42, name=Name, version=1, instanceId=42, status=Status, startDate=1970-01"
        + "-01T00:00:00Z, endDate=1970-01-01T00:00:00Z, duration=null)", actualToStringResult);
    assertNull(actualDuration);
    assertEquals(1L, actualWorkflowInstanceDomain.getVersion().longValue());
    Instant instant = actualStartDate.EPOCH;
    assertSame(instant, actualEndDate);
    assertSame(instant, actualStartDate);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstanceDomain.WorkflowInstanceDomainBuilder#build()}
   *   <li>
   * {@link WorkflowInstanceDomain.WorkflowInstanceDomainBuilder#duration(Duration)}
   *   <li>
   * {@link WorkflowInstanceDomain.WorkflowInstanceDomainBuilder#endDate(Instant)}
   *   <li>{@link WorkflowInstanceDomain.WorkflowInstanceDomainBuilder#id(String)}
   *   <li>
   * {@link WorkflowInstanceDomain.WorkflowInstanceDomainBuilder#instanceId(String)}
   *   <li>{@link WorkflowInstanceDomain.WorkflowInstanceDomainBuilder#name(String)}
   *   <li>
   * {@link WorkflowInstanceDomain.WorkflowInstanceDomainBuilder#startDate(Instant)}
   *   <li>
   * {@link WorkflowInstanceDomain.WorkflowInstanceDomainBuilder#status(String)}
   *   <li>
   * {@link WorkflowInstanceDomain.WorkflowInstanceDomainBuilder#version(Long)}
   * </ul>
   */
  @Test
  void testWorkflowInstanceDomainBuilderBuild() {
    // Arrange
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder durationResult = WorkflowInstanceDomain.builder()
        .duration(null);
    WorkflowInstanceDomain.WorkflowInstanceDomainBuilder nameResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42")
        .name("Name");

    // Act
    WorkflowInstanceDomain actualBuildResult = nameResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status("Status")
        .version(1L)
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getId());
    assertEquals("42", actualBuildResult.getInstanceId());
    assertEquals("Name", actualBuildResult.getName());
    assertEquals("Status", actualBuildResult.getStatus());
    assertNull(actualBuildResult.getDuration());
    Instant endDate = actualBuildResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    assertEquals(1L, actualBuildResult.getVersion().longValue());
  }
}
