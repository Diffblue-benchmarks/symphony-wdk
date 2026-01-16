package com.symphony.bdk.workflow.event;

import com.symphony.bdk.gen.api.model.V4RoomCreated;
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
 * Test class for DatafeedEventToWorkflowEvent.onRoomCreated method.
 */
@ExtendWith(MockitoExtension.class)
class DatafeedEventToWorkflowEventClaude_onRoomCreatedTest {

  @Mock
  private WorkflowEngine workflowEngine;

  @Mock
  private RealTimeEvent<V4RoomCreated> event;

  private DatafeedEventToWorkflowEvent datafeedEventToWorkflowEvent;

  @BeforeEach
  void setUp() {
    datafeedEventToWorkflowEvent = new DatafeedEventToWorkflowEvent(workflowEngine);
  }

  @Test
  void onRoomCreated_withValidEvent_shouldCallWorkflowEngineOnEvent() {
    // When: onRoomCreated is called with a valid event
    datafeedEventToWorkflowEvent.onRoomCreated(event);

    // Then: workflowEngine.onEvent should be called exactly once with the event
    verify(workflowEngine, times(1)).onEvent(event);
  }

  @Test
  void onRoomCreated_shouldNotThrowException() {
    // When/Then: calling onRoomCreated should not throw any exception
    assertThatCode(() -> datafeedEventToWorkflowEvent.onRoomCreated(event))
        .doesNotThrowAnyException();
  }

  @Test
  void onRoomCreated_multipleCalls_shouldForwardAllEvents() {
    // When: onRoomCreated is called multiple times
    datafeedEventToWorkflowEvent.onRoomCreated(event);
    datafeedEventToWorkflowEvent.onRoomCreated(event);
    datafeedEventToWorkflowEvent.onRoomCreated(event);

    // Then: workflowEngine.onEvent should be called three times
    verify(workflowEngine, times(3)).onEvent(event);
  }

  @Test
  void onRoomCreated_withNullEvent_shouldStillCallWorkflowEngine() {
    // Given: a null event
    RealTimeEvent<V4RoomCreated> nullEvent = null;

    // When: onRoomCreated is called with null
    datafeedEventToWorkflowEvent.onRoomCreated(nullEvent);

    // Then: workflowEngine.onEvent should still be called with null
    verify(workflowEngine, times(1)).onEvent(nullEvent);
  }

  @Test
  void onRoomCreated_withDifferentEvents_shouldForwardEachEvent() {
    // Given: multiple different events
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4RoomCreated> event1 = (RealTimeEvent<V4RoomCreated>) org.mockito.Mockito.mock(RealTimeEvent.class);
    @SuppressWarnings("unchecked")
    RealTimeEvent<V4RoomCreated> event2 = (RealTimeEvent<V4RoomCreated>) org.mockito.Mockito.mock(RealTimeEvent.class);

    // When: onRoomCreated is called with different events
    datafeedEventToWorkflowEvent.onRoomCreated(event1);
    datafeedEventToWorkflowEvent.onRoomCreated(event2);

    // Then: workflowEngine.onEvent should be called for each event
    verify(workflowEngine, times(1)).onEvent(event1);
    verify(workflowEngine, times(1)).onEvent(event2);
  }

  @Test
  void onRoomCreated_shouldOnlyInteractWithWorkflowEngine() {
    // When: onRoomCreated is called
    datafeedEventToWorkflowEvent.onRoomCreated(event);

    // Then: only the onEvent method of workflowEngine should be called
    verify(workflowEngine, times(1)).onEvent(event);
    // No other interactions should occur
    verifyNoInteractions(event);
  }
}
