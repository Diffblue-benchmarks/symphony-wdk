package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4RoomUpdated;
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
 * Test class for DatafeedEventToWorkflowEvent.onRoomUpdated method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onRoomUpdatedTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4RoomUpdated> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onRoomUpdated_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onRoomUpdated is called with a valid event
    datafeedEventToWorkflowEvent.onRoomUpdated(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onRoomUpdated_shouldNotThrowException() {
    // When/Then: calling onRoomUpdated should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onRoomUpdated(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onRoomUpdated_multipleCalls_shouldForwardAllEvents() {
    // When: onRoomUpdated is called multiple times
    datafeedEventToWorkflowEvent.onRoomUpdated(event);
    datafeedEventToWorkflowEvent.onRoomUpdated(event);
    datafeedEventToWorkflowEvent.onRoomUpdated(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onRoomUpdated_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4RoomUpdated> nullEvent = null;

    // When: onRoomUpdated is called with null
    datafeedEventToWorkflowEvent.onRoomUpdated(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onRoomUpdated_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4RoomUpdated> event1 = (RealTimeEvent<V4RoomUpdated>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4RoomUpdated> event2 = (RealTimeEvent<V4RoomUpdated>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onRoomUpdated is called with different events
    datafeedEventToWorkflowEvent.onRoomUpdated(event1);
    datafeedEventToWorkflowEvent.onRoomUpdated(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onRoomUpdated_shouldOnlyInteractWithWorkflowEngine() {
    // When: onRoomUpdated is called
    datafeedEventToWorkflowEvent.onRoomUpdated(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
