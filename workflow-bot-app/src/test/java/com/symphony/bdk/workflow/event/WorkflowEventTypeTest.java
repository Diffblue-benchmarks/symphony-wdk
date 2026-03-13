package com.symphony.bdk.workflow.event;

import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.event.MessageReceivedEvent;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkflowEventTypeTest {

    @Test
    void getEventTypeWithMessageReceivedEventShouldReturnMessageReceivedType() {
        // Arrange
        Event event = new Event();
        MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();
        messageReceivedEvent.setContent("test message");
        event.setMessageReceived(messageReceivedEvent);

        // Act
        Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(WorkflowEventType.MESSAGE_RECEIVED, result.get());
    }

    @Test
    void getEventTypeWithNoMatchingEventShouldReturnEmpty() {
        // Arrange
        Event event = new Event();

        // Act
        Optional<WorkflowEventType> result = WorkflowEventType.getEventType(event);

        // Assert
        assertTrue(result.isEmpty());
    }
}
