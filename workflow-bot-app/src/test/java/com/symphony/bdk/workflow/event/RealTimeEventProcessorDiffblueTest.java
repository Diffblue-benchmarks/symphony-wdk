package com.symphony.bdk.workflow.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;
import org.camunda.bpm.engine.impl.RuntimeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class RealTimeEventProcessorDiffblueTest {
  /**
   * Test {@link RealTimeEventProcessor#sourceType()}.
   *
   * <p>Method under test: {@link RealTimeEventProcessor#sourceType()}
   */
  @Test
  @DisplayName("Test sourceType()")
  @Tag("MaintainedByDiffblue")
  void testSourceType() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange and Act
    Class<RequestReceivedEvent> actualSourceTypeResult =
        new RequestReceivedEventProcessor(new RuntimeServiceImpl()).sourceType();

    // Assert
    Class<RequestReceivedEvent> expectedSourceTypeResult = RequestReceivedEvent.class;
    assertEquals(expectedSourceTypeResult, actualSourceTypeResult);
  }
}
