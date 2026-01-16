package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4SharedPost;
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
 * Test class for DatafeedEventToWorkflowEvent.onSharedPost method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onSharedPostTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4SharedPost> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onSharedPost_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onSharedPost is called with a valid event
    datafeedEventToWorkflowEvent.onSharedPost(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onSharedPost_shouldNotThrowException() {
    // When/Then: calling onSharedPost should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onSharedPost(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onSharedPost_multipleCalls_shouldForwardAllEvents() {
    // When: onSharedPost is called multiple times
    datafeedEventToWorkflowEvent.onSharedPost(event);
    datafeedEventToWorkflowEvent.onSharedPost(event);
    datafeedEventToWorkflowEvent.onSharedPost(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onSharedPost_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4SharedPost> nullEvent = null;

    // When: onSharedPost is called with null
    datafeedEventToWorkflowEvent.onSharedPost(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onSharedPost_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4SharedPost> event1 = (RealTimeEvent<V4SharedPost>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4SharedPost> event2 = (RealTimeEvent<V4SharedPost>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onSharedPost is called with different events
    datafeedEventToWorkflowEvent.onSharedPost(event1);
    datafeedEventToWorkflowEvent.onSharedPost(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onSharedPost_shouldOnlyInteractWithWorkflowEngine() {
    // When: onSharedPost is called
    datafeedEventToWorkflowEvent.onSharedPost(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
