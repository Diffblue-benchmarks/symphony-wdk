package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4UserRequestedToJoinRoom;
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
 * Test class for DatafeedEventToWorkflowEvent.onUserRequestedToJoinRoom method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onUserRequestedToJoinRoomTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4UserRequestedToJoinRoom> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onUserRequestedToJoinRoom_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onUserRequestedToJoinRoom is called with a valid event
    datafeedEventToWorkflowEvent.onUserRequestedToJoinRoom(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onUserRequestedToJoinRoom_shouldNotThrowException() {
    // When/Then: calling onUserRequestedToJoinRoom should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onUserRequestedToJoinRoom(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onUserRequestedToJoinRoom_multipleCalls_shouldForwardAllEvents() {
    // When: onUserRequestedToJoinRoom is called multiple times
    datafeedEventToWorkflowEvent.onUserRequestedToJoinRoom(event);
    datafeedEventToWorkflowEvent.onUserRequestedToJoinRoom(event);
    datafeedEventToWorkflowEvent.onUserRequestedToJoinRoom(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onUserRequestedToJoinRoom_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4UserRequestedToJoinRoom> nullEvent = null;

    // When: onUserRequestedToJoinRoom is called with null
    datafeedEventToWorkflowEvent.onUserRequestedToJoinRoom(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onUserRequestedToJoinRoom_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4UserRequestedToJoinRoom> event1 = (RealTimeEvent<V4UserRequestedToJoinRoom>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4UserRequestedToJoinRoom> event2 = (RealTimeEvent<V4UserRequestedToJoinRoom>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onUserRequestedToJoinRoom is called with different events
    datafeedEventToWorkflowEvent.onUserRequestedToJoinRoom(event1);
    datafeedEventToWorkflowEvent.onUserRequestedToJoinRoom(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onUserRequestedToJoinRoom_shouldOnlyInteractWithWorkflowEngine() {
    // When: onUserRequestedToJoinRoom is called
    datafeedEventToWorkflowEvent.onUserRequestedToJoinRoom(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
