package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StatusEnumDiffblueTest {
  /**
   * Test {@link StatusEnum#toInstanceStatusEnum(String)}.
   *
   * <ul>
   *   <li>When {@code ACTIVE}.
   *   <li>Then return {@code PENDING}.
   * </ul>
   *
   * <p>Method under test: {@link StatusEnum#toInstanceStatusEnum(String)}
   */
  @Test
  @DisplayName("Test toInstanceStatusEnum(String); when 'ACTIVE'; then return 'PENDING'")
  @Tag("MaintainedByDiffblue")
  void testToInstanceStatusEnum_whenActive_thenReturnPending() {
    // Arrange, Act and Assert
    assertEquals(StatusEnum.PENDING, StatusEnum.toInstanceStatusEnum("ACTIVE"));
  }

  /**
   * Test {@link StatusEnum#toInstanceStatusEnum(String)}.
   *
   * <ul>
   *   <li>When {@code COMPLETED}.
   *   <li>Then return {@code COMPLETED}.
   * </ul>
   *
   * <p>Method under test: {@link StatusEnum#toInstanceStatusEnum(String)}
   */
  @Test
  @DisplayName("Test toInstanceStatusEnum(String); when 'COMPLETED'; then return 'COMPLETED'")
  @Tag("MaintainedByDiffblue")
  void testToInstanceStatusEnum_whenCompleted_thenReturnCompleted() {
    // Arrange, Act and Assert
    assertEquals(StatusEnum.COMPLETED, StatusEnum.toInstanceStatusEnum("COMPLETED"));
  }

  /**
   * Test {@link StatusEnum#toInstanceStatusEnum(String)}.
   *
   * <ul>
   *   <li>When {@code FAILED}.
   *   <li>Then return {@code FAILED}.
   * </ul>
   *
   * <p>Method under test: {@link StatusEnum#toInstanceStatusEnum(String)}
   */
  @Test
  @DisplayName("Test toInstanceStatusEnum(String); when 'FAILED'; then return 'FAILED'")
  @Tag("MaintainedByDiffblue")
  void testToInstanceStatusEnum_whenFailed_thenReturnFailed() {
    // Arrange, Act and Assert
    assertEquals(StatusEnum.FAILED, StatusEnum.toInstanceStatusEnum("FAILED"));
  }

  /**
   * Test {@link StatusEnum#toInstanceStatusEnum(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StatusEnum#toInstanceStatusEnum(String)}
   */
  @Test
  @DisplayName("Test toInstanceStatusEnum(String); when 'null'; then return 'null'")
  @Tag("MaintainedByDiffblue")
  void testToInstanceStatusEnum_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(StatusEnum.toInstanceStatusEnum(null));
  }

  /**
   * Test {@link StatusEnum#toInstanceStatusEnum(String)}.
   *
   * <ul>
   *   <li>When {@code PENDING}.
   *   <li>Then return {@code PENDING}.
   * </ul>
   *
   * <p>Method under test: {@link StatusEnum#toInstanceStatusEnum(String)}
   */
  @Test
  @DisplayName("Test toInstanceStatusEnum(String); when 'PENDING'; then return 'PENDING'")
  @Tag("MaintainedByDiffblue")
  void testToInstanceStatusEnum_whenPending_thenReturnPending() {
    // Arrange, Act and Assert
    assertEquals(StatusEnum.PENDING, StatusEnum.toInstanceStatusEnum("PENDING"));
  }

  /**
   * Test {@link StatusEnum#toInstanceStatusEnum(String)}.
   *
   * <ul>
   *   <li>When {@code Status}.
   *   <li>Then throw {@link IllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link StatusEnum#toInstanceStatusEnum(String)}
   */
  @Test
  @DisplayName(
      "Test toInstanceStatusEnum(String); when 'Status'; then throw IllegalArgumentException")
  @Tag("MaintainedByDiffblue")
  void testToInstanceStatusEnum_whenStatus_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> StatusEnum.toInstanceStatusEnum("Status"));
  }
}
