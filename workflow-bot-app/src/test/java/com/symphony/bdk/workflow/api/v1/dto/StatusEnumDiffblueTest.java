package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class StatusEnumDiffblueTest {
  /**
   * Test {@link StatusEnum#toInstanceStatusEnum(String)}.
   * <ul>
   *   <li>When {@code ACTIVE}.</li>
   *   <li>Then return {@code PENDING}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusEnum#toInstanceStatusEnum(String)}
   */
  @Test
  @DisplayName("Test toInstanceStatusEnum(String); when 'ACTIVE'; then return 'PENDING'")
  void testToInstanceStatusEnum_whenActive_thenReturnPending() {
    // Arrange, Act and Assert
    assertEquals(StatusEnum.PENDING, StatusEnum.toInstanceStatusEnum("ACTIVE"));
  }

  /**
   * Test {@link StatusEnum#toInstanceStatusEnum(String)}.
   * <ul>
   *   <li>When {@code COMPLETED}.</li>
   *   <li>Then return {@code COMPLETED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusEnum#toInstanceStatusEnum(String)}
   */
  @Test
  @DisplayName("Test toInstanceStatusEnum(String); when 'COMPLETED'; then return 'COMPLETED'")
  void testToInstanceStatusEnum_whenCompleted_thenReturnCompleted() {
    // Arrange, Act and Assert
    assertEquals(StatusEnum.COMPLETED, StatusEnum.toInstanceStatusEnum("COMPLETED"));
  }

  /**
   * Test {@link StatusEnum#toInstanceStatusEnum(String)}.
   * <ul>
   *   <li>When {@code FAILED}.</li>
   *   <li>Then return {@code FAILED}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusEnum#toInstanceStatusEnum(String)}
   */
  @Test
  @DisplayName("Test toInstanceStatusEnum(String); when 'FAILED'; then return 'FAILED'")
  void testToInstanceStatusEnum_whenFailed_thenReturnFailed() {
    // Arrange, Act and Assert
    assertEquals(StatusEnum.FAILED, StatusEnum.toInstanceStatusEnum("FAILED"));
  }

  /**
   * Test {@link StatusEnum#toInstanceStatusEnum(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusEnum#toInstanceStatusEnum(String)}
   */
  @Test
  @DisplayName("Test toInstanceStatusEnum(String); when 'null'; then return 'null'")
  void testToInstanceStatusEnum_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(StatusEnum.toInstanceStatusEnum(null));
  }

  /**
   * Test {@link StatusEnum#toInstanceStatusEnum(String)}.
   * <ul>
   *   <li>When {@code PENDING}.</li>
   *   <li>Then return {@code PENDING}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusEnum#toInstanceStatusEnum(String)}
   */
  @Test
  @DisplayName("Test toInstanceStatusEnum(String); when 'PENDING'; then return 'PENDING'")
  void testToInstanceStatusEnum_whenPending_thenReturnPending() {
    // Arrange, Act and Assert
    assertEquals(StatusEnum.PENDING, StatusEnum.toInstanceStatusEnum("PENDING"));
  }

  /**
   * Test {@link StatusEnum#toInstanceStatusEnum(String)}.
   * <ul>
   *   <li>When {@code Status}.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link StatusEnum#toInstanceStatusEnum(String)}
   */
  @Test
  @DisplayName("Test toInstanceStatusEnum(String); when 'Status'; then throw IllegalArgumentException")
  void testToInstanceStatusEnum_whenStatus_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> StatusEnum.toInstanceStatusEnum("Status"));
  }
}
