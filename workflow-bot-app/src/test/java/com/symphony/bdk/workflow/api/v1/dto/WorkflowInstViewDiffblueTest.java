package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import com.symphony.bdk.workflow.api.v1.dto.WorkflowInstView.WorkflowInstViewBuilder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WorkflowInstViewDiffblueTest {
  /**
   * Test {@link WorkflowInstView#equals(Object)}, and
   * {@link WorkflowInstView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstView#equals(Object)}
   *   <li>{@link WorkflowInstView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
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
   * Test {@link WorkflowInstView#equals(Object)}, and
   * {@link WorkflowInstView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstView#equals(Object)}
   *   <li>{@link WorkflowInstView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
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
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link WorkflowInstView#equals(Object)}, and
   * {@link WorkflowInstView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstView#equals(Object)}
   *   <li>{@link WorkflowInstView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId(null);
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.PENDING)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId(null);
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
   * Test {@link WorkflowInstView#equals(Object)}, and
   * {@link WorkflowInstView#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstView#equals(Object)}
   *   <li>{@link WorkflowInstView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(null)
        .version(1L)
        .build();
    WorkflowInstView.WorkflowInstViewBuilder durationResult2 = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult2 = durationResult2
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult2 = instanceIdResult2
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(null)
        .version(1L)
        .build();

    // Act and Assert
    assertEquals(buildResult, buildResult2);
    int expectedHashCodeResult = buildResult.hashCode();
    assertEquals(expectedHashCodeResult, buildResult2.hashCode());
  }

  /**
   * Test {@link WorkflowInstView#equals(Object)}, and
   * {@link WorkflowInstView#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowInstView#equals(Object)}
   *   <li>{@link WorkflowInstView#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
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
   * Test {@link WorkflowInstView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
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
   * Test {@link WorkflowInstView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("Id")
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
   * Test {@link WorkflowInstView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual3() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id(null)
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
   * Test {@link WorkflowInstView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual4() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("Instance Id");
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
   * Test {@link WorkflowInstView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual5() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId(null);
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
   * Test {@link WorkflowInstView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual6() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.now().atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
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
   * Test {@link WorkflowInstView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual7() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(null)
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
   * Test {@link WorkflowInstView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual8() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
    WorkflowInstView.WorkflowInstViewBuilder instanceIdResult = durationResult
        .endDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .id("42")
        .instanceId("42");
    WorkflowInstView buildResult = instanceIdResult
        .startDate(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant())
        .status(StatusEnum.COMPLETED)
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
   * Test {@link WorkflowInstView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual9() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
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
   * Test {@link WorkflowInstView#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual10() {
    // Arrange
    WorkflowInstView.WorkflowInstViewBuilder durationResult = WorkflowInstView.builder().duration(null);
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
   * Test {@link WorkflowInstView#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
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
   * Test {@link WorkflowInstView#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link WorkflowInstView#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
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
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
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
    Duration actualDuration = actualWorkflowInstView.getDuration();
    Instant actualEndDate = actualWorkflowInstView.getEndDate();
    String actualId = actualWorkflowInstView.getId();
    String actualInstanceId = actualWorkflowInstView.getInstanceId();
    Instant actualStartDate = actualWorkflowInstView.getStartDate();
    StatusEnum actualStatus = actualWorkflowInstView.getStatus();

    // Assert
    assertEquals("42", actualId);
    assertEquals("42", actualInstanceId);
    assertEquals("WorkflowInstView(id=42, version=1, instanceId=42, status=PENDING, startDate=1970-01-01T00:00:00Z,"
        + " endDate=1970-01-01T00:00:00Z, duration=null)", actualToStringResult);
    assertNull(actualDuration);
    assertEquals(1L, actualWorkflowInstView.getVersion().longValue());
    assertEquals(StatusEnum.PENDING, actualStatus);
    Instant instant = actualStartDate.EPOCH;
    assertSame(instant, actualEndDate);
    assertSame(instant, actualStartDate);
  }

  /**
   * Test WorkflowInstViewBuilder {@link WorkflowInstViewBuilder#build()}.
   * <p>
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
  @DisplayName("Test WorkflowInstViewBuilder build()")
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
