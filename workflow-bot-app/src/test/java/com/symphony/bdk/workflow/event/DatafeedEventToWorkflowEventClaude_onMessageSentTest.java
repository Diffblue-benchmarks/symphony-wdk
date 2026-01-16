package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4MessageSent;
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
 * Test class for DatafeedEventToWorkflowEvent.onMessageSent method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onMessageSentTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4MessageSent> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onMessageSent_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onMessageSent is called with a valid event
    datafeedEventToWorkflowEvent.onMessageSent(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onMessageSent_shouldNotThrowException() {
    // When/Then: calling onMessageSent should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onMessageSent(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onMessageSent_multipleCalls_shouldForwardAllEvents() {
    // When: onMessageSent is called multiple times
    datafeedEventToWorkflowEvent.onMessageSent(event);
    datafeedEventToWorkflowEvent.onMessageSent(event);
    datafeedEventToWorkflowEvent.onMessageSent(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onMessageSent_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4MessageSent> nullEvent = null;

    // When: onMessageSent is called with null
    datafeedEventToWorkflowEvent.onMessageSent(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onMessageSent_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4MessageSent> event1 = (RealTimeEvent<V4MessageSent>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4MessageSent> event2 = (RealTimeEvent<V4MessageSent>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onMessageSent is called with different events
    datafeedEventToWorkflowEvent.onMessageSent(event1);
    datafeedEventToWorkflowEvent.onMessageSent(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onMessageSent_shouldOnlyInteractWithWorkflowEngine() {
    // When: onMessageSent is called
    datafeedEventToWorkflowEvent.onMessageSent(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
