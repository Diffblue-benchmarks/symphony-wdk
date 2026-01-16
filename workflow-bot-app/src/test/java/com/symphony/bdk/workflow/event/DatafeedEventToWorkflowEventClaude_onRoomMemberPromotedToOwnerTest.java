package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4RoomMemberPromotedToOwner;
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
 * Test class for DatafeedEventToWorkflowEvent.onRoomMemberPromotedToOwner method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onRoomMemberPromotedToOwnerTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4RoomMemberPromotedToOwner> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onRoomMemberPromotedToOwner_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onRoomMemberPromotedToOwner is called with a valid event
    datafeedEventToWorkflowEvent.onRoomMemberPromotedToOwner(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onRoomMemberPromotedToOwner_shouldNotThrowException() {
    // When/Then: calling onRoomMemberPromotedToOwner should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onRoomMemberPromotedToOwner(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onRoomMemberPromotedToOwner_multipleCalls_shouldForwardAllEvents() {
    // When: onRoomMemberPromotedToOwner is called multiple times
    datafeedEventToWorkflowEvent.onRoomMemberPromotedToOwner(event);
    datafeedEventToWorkflowEvent.onRoomMemberPromotedToOwner(event);
    datafeedEventToWorkflowEvent.onRoomMemberPromotedToOwner(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onRoomMemberPromotedToOwner_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4RoomMemberPromotedToOwner> nullEvent = null;

    // When: onRoomMemberPromotedToOwner is called with null
    datafeedEventToWorkflowEvent.onRoomMemberPromotedToOwner(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onRoomMemberPromotedToOwner_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4RoomMemberPromotedToOwner> event1 = (RealTimeEvent<V4RoomMemberPromotedToOwner>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4RoomMemberPromotedToOwner> event2 = (RealTimeEvent<V4RoomMemberPromotedToOwner>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onRoomMemberPromotedToOwner is called with different events
    datafeedEventToWorkflowEvent.onRoomMemberPromotedToOwner(event1);
    datafeedEventToWorkflowEvent.onRoomMemberPromotedToOwner(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onRoomMemberPromotedToOwner_shouldOnlyInteractWithWorkflowEngine() {
    // When: onRoomMemberPromotedToOwner is called
    datafeedEventToWorkflowEvent.onRoomMemberPromotedToOwner(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
