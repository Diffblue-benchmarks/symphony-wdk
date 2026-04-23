package com.symphony.bdk.workflow.event;

import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RoomCreatedEvent;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowEventTypeTest {

  @Test
  void shouldReturnEventTypeWhenEventMatches() {
    Event event = new Event();
    MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
    event.setMessageReceived(messageReceivedEvent);

    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.MESSAGE_RECEIVED);
  }

  @Test
  void shouldReturnEmptyWhenNoEventMatches() {
    Event event = new Event();

    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    assertThat(result).isEmpty();
  }

  @Test
  void shouldReturnRoomCreatedEventTypeWhenRoomCreatedEventMatches() {
    Event event = new Event();
    RoomCreatedEvent roomCreatedEvent = new RoomCreatedEvent();
    event.setRoomCreated(roomCreatedEvent);

    Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

    assertThat(result).isPresent();
    assertThat(result.get()).isEqualTo(WorkflowEventType.ROOM_CREATED);
  }
}
