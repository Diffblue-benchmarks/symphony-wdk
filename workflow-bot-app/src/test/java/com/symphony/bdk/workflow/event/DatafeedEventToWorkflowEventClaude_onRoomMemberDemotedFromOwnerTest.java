package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4RoomMemberDemotedFromOwner;
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
 * Test class for DatafeedEventToWorkflowEvent.onRoomMemberDemotedFromOwner method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onRoomMemberDemotedFromOwnerTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4RoomMemberDemotedFromOwner> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onRoomMemberDemotedFromOwner_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onRoomMemberDemotedFromOwner is called with a valid event
    datafeedEventToWorkflowEvent.onRoomMemberDemotedFromOwner(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onRoomMemberDemotedFromOwner_shouldNotThrowException() {
    // When/Then: calling onRoomMemberDemotedFromOwner should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onRoomMemberDemotedFromOwner(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onRoomMemberDemotedFromOwner_multipleCalls_shouldForwardAllEvents() {
    // When: onRoomMemberDemotedFromOwner is called multiple times
    datafeedEventToWorkflowEvent.onRoomMemberDemotedFromOwner(event);
    datafeedEventToWorkflowEvent.onRoomMemberDemotedFromOwner(event);
    datafeedEventToWorkflowEvent.onRoomMemberDemotedFromOwner(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onRoomMemberDemotedFromOwner_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4RoomMemberDemotedFromOwner> nullEvent = null;

    // When: onRoomMemberDemotedFromOwner is called with null
    datafeedEventToWorkflowEvent.onRoomMemberDemotedFromOwner(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onRoomMemberDemotedFromOwner_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4RoomMemberDemotedFromOwner> event1 = (RealTimeEvent<V4RoomMemberDemotedFromOwner>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4RoomMemberDemotedFromOwner> event2 = (RealTimeEvent<V4RoomMemberDemotedFromOwner>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onRoomMemberDemotedFromOwner is called with different events
    datafeedEventToWorkflowEvent.onRoomMemberDemotedFromOwner(event1);
    datafeedEventToWorkflowEvent.onRoomMemberDemotedFromOwner(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onRoomMemberDemotedFromOwner_shouldOnlyInteractWithWorkflowEngine() {
    // When: onRoomMemberDemotedFromOwner is called
    datafeedEventToWorkflowEvent.onRoomMemberDemotedFromOwner(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
