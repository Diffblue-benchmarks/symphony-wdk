package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4UserLeftRoom;
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
 * Test class for DatafeedEventToWorkflowEvent.onUserLeftRoom method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onUserLeftRoomTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4UserLeftRoom> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onUserLeftRoom_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onUserLeftRoom is called with a valid event
    datafeedEventToWorkflowEvent.onUserLeftRoom(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onUserLeftRoom_shouldNotThrowException() {
    // When/Then: calling onUserLeftRoom should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onUserLeftRoom(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onUserLeftRoom_multipleCalls_shouldForwardAllEvents() {
    // When: onUserLeftRoom is called multiple times
    datafeedEventToWorkflowEvent.onUserLeftRoom(event);
    datafeedEventToWorkflowEvent.onUserLeftRoom(event);
    datafeedEventToWorkflowEvent.onUserLeftRoom(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onUserLeftRoom_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4UserLeftRoom> nullEvent = null;

    // When: onUserLeftRoom is called with null
    datafeedEventToWorkflowEvent.onUserLeftRoom(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onUserLeftRoom_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4UserLeftRoom> event1 = (RealTimeEvent<V4UserLeftRoom>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4UserLeftRoom> event2 = (RealTimeEvent<V4UserLeftRoom>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onUserLeftRoom is called with different events
    datafeedEventToWorkflowEvent.onUserLeftRoom(event1);
    datafeedEventToWorkflowEvent.onUserLeftRoom(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onUserLeftRoom_shouldOnlyInteractWithWorkflowEngine() {
    // When: onUserLeftRoom is called
    datafeedEventToWorkflowEvent.onUserLeftRoom(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
