package com.symphony.bdk.workflow.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class V4RoomDeactivatedProcessorDiffblueTest {

  /**
   * Test {@link V4RoomDeactivatedProcessor#V4RoomDeactivatedProcessor(RuntimeService)}.
   *
   * <p>Method under test: {@link V4RoomDeactivatedProcessor#V4RoomDeactivatedProcessor(RuntimeService)}
   */
  @Test
  @DisplayName("Test V4RoomDeactivatedProcessor(RuntimeService)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void V4RoomDeactivatedProcessor.<init>(RuntimeService)"})
  void testNewV4RoomDeactivatedProcessor() {
    // Arrange and Act
    V4RoomDeactivatedProcessor processor =
        new V4RoomDeactivatedProcessor(mock(RuntimeService.class));

    // Assert
    assertEquals("room-deactivated", processor.eventName);
  }
}
