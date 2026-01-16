package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4MessageSuppressed;
import com.symphony.bdk.spring.events.RealTimeEvent;
import com.symphony.bdk.workflow.engine.WorkflowEngine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

/**
 * Test class for DatafeedEventToWorkflowEvent.onMessageSuppressed method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onMessageSuppressedTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4MessageSuppressed> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onMessageSuppressed_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onMessageSuppressed is called with a valid event
    datafeedEventToWorkflowEvent.onMessageSuppressed(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onMessageSuppressed_shouldNotThrowException() {
    // When/Then: calling onMessageSuppressed should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onMessageSuppressed(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onMessageSuppressed_multipleCalls_shouldForwardAllEvents() {
    // When: onMessageSuppressed is called multiple times
    datafeedEventToWorkflowEvent.onMessageSuppressed(event);
    datafeedEventToWorkflowEvent.onMessageSuppressed(event);
    datafeedEventToWorkflowEvent.onMessageSuppressed(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onMessageSuppressed_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4MessageSuppressed> nullEvent = null;

    // When: onMessageSuppressed is called with null
    datafeedEventToWorkflowEvent.onMessageSuppressed(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onMessageSuppressed_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4MessageSuppressed> event1 = (RealTimeEvent<V4MessageSuppressed>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4MessageSuppressed> event2 = (RealTimeEvent<V4MessageSuppressed>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onMessageSuppressed is called with different events
    datafeedEventToWorkflowEvent.onMessageSuppressed(event1);
    datafeedEventToWorkflowEvent.onMessageSuppressed(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onMessageSuppressed_shouldOnlyInteractWithWorkflowEngine() {
    // When: onMessageSuppressed is called
    datafeedEventToWorkflowEvent.onMessageSuppressed(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
