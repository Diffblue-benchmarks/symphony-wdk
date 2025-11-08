package com.symphony.bdk.workflow.api.v1.dto;

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

class WorkflowInstViewDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstView#equals(Object)}
   *   <li>{@link WorkflowInstView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
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
   *   <li>{@link WorkflowInstView#equals(Object)}
   *   <li>{@link WorkflowInstView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.duration(Mockito.<Duration>any())).thenReturn(WorkflowInstView.builder());
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
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
   *   <li>{@link WorkflowInstView#equals(Object)}
   *   <li>{@link WorkflowInstView#hashCode()}
   * </ul>
   */
  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult.hashCode());
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.endDate(Mockito.<Instant>any())).thenReturn(WorkflowInstView.builder());
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder2 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder2.duration(Mockito.<Duration>any())).thenReturn(workflowInstViewBuilder);
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder2.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.id(Mockito.<String>any())).thenReturn(WorkflowInstView.builder());
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder2 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder2.endDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder3 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder3.duration(Mockito.<Duration>any())).thenReturn(workflowInstViewBuilder2);
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder3.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.id(Mockito.<String>any())).thenReturn(WorkflowInstView.builder());
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder2 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder2.endDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder3 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder3.duration(Mockito.<Duration>any())).thenReturn(workflowInstViewBuilder2);
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder3.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(3L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.id(Mockito.<String>any())).thenReturn(WorkflowInstView.builder());
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder2 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder2.endDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder3 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder3.duration(Mockito.<Duration>any())).thenReturn(workflowInstViewBuilder2);
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder3.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(null)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.id(Mockito.<String>any())).thenReturn(WorkflowInstView.builder());
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder2 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder2.endDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder3 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder3.duration(Mockito.<Duration>any())).thenReturn(workflowInstViewBuilder2);
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder3.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId("42");
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.id(Mockito.<String>any())).thenReturn(WorkflowInstView.builder());
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder2 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder2.endDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder3 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder3.duration(Mockito.<Duration>any())).thenReturn(workflowInstViewBuilder2);
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder3.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(null)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(null)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.instanceId(Mockito.<String>any())).thenReturn(WorkflowInstView.builder());
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder2 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder2.id(Mockito.<String>any())).thenReturn(workflowInstViewBuilder);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder3 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder3.endDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder2);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder4 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder4.duration(Mockito.<Duration>any())).thenReturn(workflowInstViewBuilder3);
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder4.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId("42");
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.instanceId(Mockito.<String>any())).thenReturn(WorkflowInstView.builder());
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder2 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder2.id(Mockito.<String>any())).thenReturn(workflowInstViewBuilder);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder3 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder3.endDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder2);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder4 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder4.duration(Mockito.<Duration>any())).thenReturn(workflowInstViewBuilder3);
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder4.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId(null);
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder builderResult = WorkflowInstView.builder();
    builderResult.id("42");
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.instanceId(Mockito.<String>any())).thenReturn(builderResult);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder2 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder2.id(Mockito.<String>any())).thenReturn(workflowInstViewBuilder);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder3 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder3.endDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder2);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder4 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder4.duration(Mockito.<Duration>any())).thenReturn(workflowInstViewBuilder3);
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder4.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId("42");
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.startDate(Mockito.<Instant>any())).thenReturn(WorkflowInstView.builder());
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder2 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder2.instanceId(Mockito.<String>any())).thenReturn(workflowInstViewBuilder);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder3 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder3.id(Mockito.<String>any())).thenReturn(workflowInstViewBuilder2);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder4 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder4.endDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder3);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder5 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder5.duration(Mockito.<Duration>any())).thenReturn(workflowInstViewBuilder4);
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder5.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId(null);
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual11() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.status(Mockito.<StatusEnum>any())).thenReturn(WorkflowInstView.builder());
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder2 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder2.startDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder3 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder3.instanceId(Mockito.<String>any())).thenReturn(workflowInstViewBuilder2);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder4 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder4.id(Mockito.<String>any())).thenReturn(workflowInstViewBuilder3);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder5 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder5.endDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder4);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder6 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder6.duration(Mockito.<Duration>any())).thenReturn(workflowInstViewBuilder5);
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder6.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId(null);
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual12() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.status(Mockito.<StatusEnum>any())).thenReturn(WorkflowInstView.builder());
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder2 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder2.startDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder3 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder3.instanceId(Mockito.<String>any())).thenReturn(workflowInstViewBuilder2);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder4 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder4.id(Mockito.<String>any())).thenReturn(workflowInstViewBuilder3);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder5 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder5.endDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder4);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder6 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder6.duration(Mockito.<Duration>any())).thenReturn(workflowInstViewBuilder5);
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder6.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId(null);
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(null)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual13() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder builderResult = WorkflowInstView.builder();
    builderResult.instanceId("42");
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder.status(Mockito.<StatusEnum>any())).thenReturn(builderResult);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder2 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder2.startDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder3 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder3.instanceId(Mockito.<String>any())).thenReturn(workflowInstViewBuilder2);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder4 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder4.id(Mockito.<String>any())).thenReturn(workflowInstViewBuilder3);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder5 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder5.endDate(Mockito.<Instant>any())).thenReturn(workflowInstViewBuilder4);
    WorkflowInstView.WorkflowInstViewBuilder workflowInstViewBuilder6 = mock(
        WorkflowInstView.WorkflowInstViewBuilder.class);
    when(workflowInstViewBuilder6.duration(Mockito.<Duration>any())).thenReturn(workflowInstViewBuilder5);
    WorkflowInstView.WorkflowInstViewBuilder durationResult = workflowInstViewBuilder6.duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
        .instanceId(null);
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, buildResult2);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, null);
  }

  /**
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Act and Assert
    assertNotEquals(buildResult, "Different type to WorkflowInstView");
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link WorkflowInstView#WorkflowInstView(String, Long, String, StatusEnum, Instant, Instant, Duration)}
   *   <li>{@link WorkflowInstView#setDuration(Duration)}
   *   <li>{@link WorkflowInstView#setEndDate(Instant)}
   *   <li>{@link WorkflowInstView#setId(String)}
   *   <li>{@link WorkflowInstView#setInstanceId(String)}
   *   <li>{@link WorkflowInstView#setStartDate(Instant)}
   *   <li>{@link WorkflowInstView#setStatus(StatusEnum)}
   *   <li>{@link WorkflowInstView#setVersion(Long)}
   *   <li>{@link WorkflowInstView#toString()}
   *   <li>{@link WorkflowInstView#getDuration()}
   *   <li>{@link WorkflowInstView#getEndDate()}
   *   <li>{@link WorkflowInstView#getId()}
   *   <li>{@link WorkflowInstView#getInstanceId()}
   *   <li>{@link WorkflowInstView#getStartDate()}
   *   <li>{@link WorkflowInstView#getStatus()}
   *   <li>{@link WorkflowInstView#getVersion()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    Instant startDate = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

    // Act
    WorkflowInstView actualWorkflowInstView = new WorkflowInstView("42", 1L, "42", StatusEnum.PENDING, startDate,
        LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant(), null);
    actualWorkflowInstView.setDuration(null);
    actualWorkflowInstView.setEndDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualWorkflowInstView.setId("42");
    actualWorkflowInstView.setInstanceId("42");
    actualWorkflowInstView.setStartDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualWorkflowInstView.setStatus(StatusEnum.PENDING);
    actualWorkflowInstView.setVersion(1L);
    String actualToStringResult = actualWorkflowInstView.toString();
    actualWorkflowInstView.getDuration();
    Instant actualEndDate = actualWorkflowInstView.getEndDate();
    String actualId = actualWorkflowInstView.getId();
    String actualInstanceId = actualWorkflowInstView.getInstanceId();
    Instant actualStartDate = actualWorkflowInstView.getStartDate();
    StatusEnum actualStatus = actualWorkflowInstView.getStatus();

    // Assert that nothing has changed
    assertEquals("42", actualId);
    assertEquals("42", actualInstanceId);
    assertEquals("WorkflowInstView(id=42, version=1, instanceId=42, status=PENDING, startDate=1970-01-01T00:00:00Z,"
        + " endDate=1970-01-01T00:00:00Z, duration=null)", actualToStringResult);
    assertEquals(1L, actualWorkflowInstView.getVersion().longValue());
    assertEquals(StatusEnum.PENDING, actualStatus);
    Instant instant = actualStartDate.EPOCH;
    assertSame(instant, actualEndDate);
    assertSame(instant, actualStartDate);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstView.WorkflowInstViewBuilder#build()}
   *   <li>{@link WorkflowInstView.WorkflowInstViewBuilder#duration(Duration)}
   *   <li>{@link WorkflowInstView.WorkflowInstViewBuilder#endDate(Instant)}
   *   <li>{@link WorkflowInstView.WorkflowInstViewBuilder#id(String)}
   *   <li>{@link WorkflowInstView.WorkflowInstViewBuilder#instanceId(String)}
   *   <li>{@link WorkflowInstView.WorkflowInstViewBuilder#startDate(Instant)}
   *   <li>{@link WorkflowInstView.WorkflowInstViewBuilder#status(StatusEnum)}
   *   <li>{@link WorkflowInstView.WorkflowInstViewBuilder#version(Long)}
   * </ul>
   */
  @Test
  void testWorkflowInstViewBuilderBuild() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");

    // Act
    WorkflowInstView actualBuildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();

    // Assert
    assertEquals("42", actualBuildResult.getId());
    assertEquals("42", actualBuildResult.getInstanceId());
    assertNull(actualBuildResult.getDuration());
    Instant endDate = actualBuildResult.getEndDate();
    assertEquals(0, endDate.getNano());
    assertEquals(0L, endDate.getEpochSecond());
    assertEquals(1L, actualBuildResult.getVersion().longValue());
    assertEquals(StatusEnum.PENDING, actualBuildResult.getStatus());
  }
}
