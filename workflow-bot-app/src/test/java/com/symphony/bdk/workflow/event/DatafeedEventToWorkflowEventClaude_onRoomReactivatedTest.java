package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4RoomReactivated;
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
 * Test class for DatafeedEventToWorkflowEvent.onRoomReactivated method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onRoomReactivatedTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4RoomReactivated> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onRoomReactivated_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onRoomReactivated is called with a valid event
    datafeedEventToWorkflowEvent.onRoomReactivated(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onRoomReactivated_shouldNotThrowException() {
    // When/Then: calling onRoomReactivated should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onRoomReactivated(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onRoomReactivated_multipleCalls_shouldForwardAllEvents() {
    // When: onRoomReactivated is called multiple times
    datafeedEventToWorkflowEvent.onRoomReactivated(event);
    datafeedEventToWorkflowEvent.onRoomReactivated(event);
    datafeedEventToWorkflowEvent.onRoomReactivated(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onRoomReactivated_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4RoomReactivated> nullEvent = null;

    // When: onRoomReactivated is called with null
    datafeedEventToWorkflowEvent.onRoomReactivated(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onRoomReactivated_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4RoomReactivated> event1 = (RealTimeEvent<V4RoomReactivated>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4RoomReactivated> event2 = (RealTimeEvent<V4RoomReactivated>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onRoomReactivated is called with different events
    datafeedEventToWorkflowEvent.onRoomReactivated(event1);
    datafeedEventToWorkflowEvent.onRoomReactivated(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onRoomReactivated_shouldOnlyInteractWithWorkflowEngine() {
    // When: onRoomReactivated is called
    datafeedEventToWorkflowEvent.onRoomReactivated(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
