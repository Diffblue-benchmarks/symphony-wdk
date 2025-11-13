package com.symphony.bdk.workflow.engine.executor;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DateTimeUtilsDiffblueTest {
  /**
   * Test {@link DateTimeUtils#toEpochMilli(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DateTimeUtils#toEpochMilli(String)}
   */
  @Test
  @DisplayName("Test toEpochMilli(String); when 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Long DateTimeUtils.toEpochMilli(String)"})
  void testToEpochMilli_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(DateTimeUtils.toEpochMilli(null));
  }
}
