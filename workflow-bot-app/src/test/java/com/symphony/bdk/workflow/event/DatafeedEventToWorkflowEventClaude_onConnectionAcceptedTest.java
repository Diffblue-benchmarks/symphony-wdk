package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4ConnectionAccepted;
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
 * Test class for DatafeedEventToWorkflowEvent.onConnectionAccepted method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onConnectionAcceptedTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4ConnectionAccepted> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onConnectionAccepted_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onConnectionAccepted is called with a valid event
    datafeedEventToWorkflowEvent.onConnectionAccepted(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onConnectionAccepted_shouldNotThrowException() {
    // When/Then: calling onConnectionAccepted should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onConnectionAccepted(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onConnectionAccepted_multipleCalls_shouldForwardAllEvents() {
    // When: onConnectionAccepted is called multiple times
    datafeedEventToWorkflowEvent.onConnectionAccepted(event);
    datafeedEventToWorkflowEvent.onConnectionAccepted(event);
    datafeedEventToWorkflowEvent.onConnectionAccepted(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onConnectionAccepted_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4ConnectionAccepted> nullEvent = null;

    // When: onConnectionAccepted is called with null
    datafeedEventToWorkflowEvent.onConnectionAccepted(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onConnectionAccepted_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4ConnectionAccepted> event1 = (RealTimeEvent<V4ConnectionAccepted>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4ConnectionAccepted> event2 = (RealTimeEvent<V4ConnectionAccepted>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onConnectionAccepted is called with different events
    datafeedEventToWorkflowEvent.onConnectionAccepted(event1);
    datafeedEventToWorkflowEvent.onConnectionAccepted(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onConnectionAccepted_shouldOnlyInteractWithWorkflowEngine() {
    // When: onConnectionAccepted is called
    datafeedEventToWorkflowEvent.onConnectionAccepted(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
