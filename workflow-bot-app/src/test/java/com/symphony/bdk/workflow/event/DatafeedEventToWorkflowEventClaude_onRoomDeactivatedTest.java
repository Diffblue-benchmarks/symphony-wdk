package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4RoomDeactivated;
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
 * Test class for DatafeedEventToWorkflowEvent.onRoomDeactivated method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onRoomDeactivatedTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4RoomDeactivated> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onRoomDeactivated_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onRoomDeactivated is called with a valid event
    datafeedEventToWorkflowEvent.onRoomDeactivated(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onRoomDeactivated_shouldNotThrowException() {
    // When/Then: calling onRoomDeactivated should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onRoomDeactivated(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onRoomDeactivated_multipleCalls_shouldForwardAllEvents() {
    // When: onRoomDeactivated is called multiple times
    datafeedEventToWorkflowEvent.onRoomDeactivated(event);
    datafeedEventToWorkflowEvent.onRoomDeactivated(event);
    datafeedEventToWorkflowEvent.onRoomDeactivated(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onRoomDeactivated_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4RoomDeactivated> nullEvent = null;

    // When: onRoomDeactivated is called with null
    datafeedEventToWorkflowEvent.onRoomDeactivated(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onRoomDeactivated_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4RoomDeactivated> event1 = (RealTimeEvent<V4RoomDeactivated>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4RoomDeactivated> event2 = (RealTimeEvent<V4RoomDeactivated>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onRoomDeactivated is called with different events
    datafeedEventToWorkflowEvent.onRoomDeactivated(event1);
    datafeedEventToWorkflowEvent.onRoomDeactivated(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onRoomDeactivated_shouldOnlyInteractWithWorkflowEngine() {
    // When: onRoomDeactivated is called
    datafeedEventToWorkflowEvent.onRoomDeactivated(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
