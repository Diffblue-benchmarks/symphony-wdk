package com.symphony.bdk.workflow.swadl.v1.activity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DebugDiffblueTest {
  /**
   * Test new {@link Debug} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link Debug}
   */
  @Test
  @DisplayName("Test new Debug (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void Debug.<init>()"})
  void testNewDebug() {
    // Arrange and Act
    Debug actualDebug = new Debug();

    // Assert
    assertNull(actualDebug.getOn());
    assertNull(actualDebug.getElseCondition());
    assertNull(actualDebug.getObject());
    assertNull(actualDebug.getIfCondition());
    RelationalEvents events = actualDebug.getEvents();
    assertNull(events.getParentId());
    assertFalse(events.isParallel());
    assertTrue(events.isEmpty());
    assertTrue(events.getEvents().isEmpty());
    assertTrue(actualDebug.getVariableProperties().isEmpty());
  }
}
