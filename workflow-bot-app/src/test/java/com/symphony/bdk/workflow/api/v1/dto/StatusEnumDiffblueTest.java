package com.symphony.bdk.workflow.api.v1.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class StatusEnumDiffblueTest {
  /**
   * Method under test: {@link StatusEnum#toInstanceStatusEnum(String)}
   */
  @Test
  void testToInstanceStatusEnum() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> StatusEnum.toInstanceStatusEnum("Status"));
    assertEquals(StatusEnum.PENDING, StatusEnum.toInstanceStatusEnum("ACTIVE"));
    assertNull(StatusEnum.toInstanceStatusEnum(null));
    assertEquals(StatusEnum.COMPLETED, StatusEnum.toInstanceStatusEnum("COMPLETED"));
    assertEquals(StatusEnum.FAILED, StatusEnum.toInstanceStatusEnum("FAILED"));
    assertEquals(StatusEnum.PENDING, StatusEnum.toInstanceStatusEnum("PENDING"));
  }
}
