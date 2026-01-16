package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4ConnectionRequested;
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
 * Test class for DatafeedEventToWorkflowEvent.onConnectionRequested method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onConnectionRequestedTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4ConnectionRequested> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onConnectionRequested_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onConnectionRequested is called with a valid event
    datafeedEventToWorkflowEvent.onConnectionRequested(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onConnectionRequested_shouldNotThrowException() {
    // When/Then: calling onConnectionRequested should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onConnectionRequested(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onConnectionRequested_multipleCalls_shouldForwardAllEvents() {
    // When: onConnectionRequested is called multiple times
    datafeedEventToWorkflowEvent.onConnectionRequested(event);
    datafeedEventToWorkflowEvent.onConnectionRequested(event);
    datafeedEventToWorkflowEvent.onConnectionRequested(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onConnectionRequested_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4ConnectionRequested> nullEvent = null;

    // When: onConnectionRequested is called with null
    datafeedEventToWorkflowEvent.onConnectionRequested(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onConnectionRequested_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4ConnectionRequested> event1 = (RealTimeEvent<V4ConnectionRequested>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4ConnectionRequested> event2 = (RealTimeEvent<V4ConnectionRequested>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onConnectionRequested is called with different events
    datafeedEventToWorkflowEvent.onConnectionRequested(event1);
    datafeedEventToWorkflowEvent.onConnectionRequested(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onConnectionRequested_shouldOnlyInteractWithWorkflowEngine() {
    // When: onConnectionRequested is called
    datafeedEventToWorkflowEvent.onConnectionRequested(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
