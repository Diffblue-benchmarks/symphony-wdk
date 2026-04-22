package com.symphony.bdk.workflow.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class V4ConnectionAcceptedProcessorDiffblueTest {

  /**
   * Test {@link V4ConnectionAcceptedProcessor#V4ConnectionAcceptedProcessor(RuntimeService)}.
   *
   * <p>Method under test:
   * {@link V4ConnectionAcceptedProcessor#V4ConnectionAcceptedProcessor(RuntimeService)}
   */
  @Test
  @DisplayName("Test new V4ConnectionAcceptedProcessor(RuntimeService)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void V4ConnectionAcceptedProcessor.<init>(RuntimeService)"})
  void testNewV4ConnectionAcceptedProcessor() {
    // Arrange
    RuntimeService runtimeService = mock(RuntimeService.class);

    // Act
    V4ConnectionAcceptedProcessor processor = new V4ConnectionAcceptedProcessor(runtimeService);

    // Assert
    assertEquals("connection-accepted", processor.eventName);
    assertEquals(runtimeService, processor.runtimeService);
  }
}
