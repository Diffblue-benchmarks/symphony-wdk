package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4UserJoinedRoom;
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
 * Test class for DatafeedEventToWorkflowEvent.onUserJoinedRoom method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onUserJoinedRoomTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4UserJoinedRoom> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onUserJoinedRoom_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onUserJoinedRoom is called with a valid event
    datafeedEventToWorkflowEvent.onUserJoinedRoom(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onUserJoinedRoom_shouldNotThrowException() {
    // When/Then: calling onUserJoinedRoom should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onUserJoinedRoom(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onUserJoinedRoom_multipleCalls_shouldForwardAllEvents() {
    // When: onUserJoinedRoom is called multiple times
    datafeedEventToWorkflowEvent.onUserJoinedRoom(event);
    datafeedEventToWorkflowEvent.onUserJoinedRoom(event);
    datafeedEventToWorkflowEvent.onUserJoinedRoom(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onUserJoinedRoom_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4UserJoinedRoom> nullEvent = null;

    // When: onUserJoinedRoom is called with null
    datafeedEventToWorkflowEvent.onUserJoinedRoom(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onUserJoinedRoom_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4UserJoinedRoom> event1 = (RealTimeEvent<V4UserJoinedRoom>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4UserJoinedRoom> event2 = (RealTimeEvent<V4UserJoinedRoom>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onUserJoinedRoom is called with different events
    datafeedEventToWorkflowEvent.onUserJoinedRoom(event1);
    datafeedEventToWorkflowEvent.onUserJoinedRoom(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onUserJoinedRoom_shouldOnlyInteractWithWorkflowEngine() {
    // When: onUserJoinedRoom is called
    datafeedEventToWorkflowEvent.onUserJoinedRoom(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
