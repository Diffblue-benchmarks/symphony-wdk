package com.symphony.bdk.workflow.swadl.v1.activity;

import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityCompletedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityExpiredEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityFailedEvent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RelationalEventsTest {

    @Test
    void isEmpty_shouldReturnTrue_whenEventsIsNull() {
        RelationalEvents relationalEvents = new RelationalEvents(null, false);

        assertTrue(relationalEvents.isEmpty());
    }

    @Test
    void isEmpty_shouldReturnTrue_whenEventsIsEmpty() {
        RelationalEvents relationalEvents = new RelationalEvents(Collections.emptyList(), false);

        assertTrue(relationalEvents.isEmpty());
    }

    @Test
    void isEmpty_shouldReturnFalse_whenEventsHasItems() {
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        RelationalEvents relationalEvents = new RelationalEvents(events, false);

        assertFalse(relationalEvents.isEmpty());
    }

    @Test
    void getParentId_shouldReturnNull_whenNotParallel() {
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        ActivityCompletedEvent completedEvent = new ActivityCompletedEvent();
        completedEvent.setActivityId("activity123");
        event.setActivityCompleted(completedEvent);
        events.add(event);

        RelationalEvents relationalEvents = new RelationalEvents(events, false);

        assertNull(relationalEvents.getParentId());
    }

    @Test
    void getParentId_shouldReturnNull_whenParallelButEventsIsNull() {
        RelationalEvents relationalEvents = new RelationalEvents(null, true);

        assertNull(relationalEvents.getParentId());
    }

    @Test
    void getParentId_shouldReturnNull_whenParallelButEventsIsEmpty() {
        RelationalEvents relationalEvents = new RelationalEvents(Collections.emptyList(), true);

        assertNull(relationalEvents.getParentId());
    }

    @Test
    void getParentId_shouldReturnExistingParentId_whenAlreadySet() {
        List<Event> events = new ArrayList<>();
        events.add(new Event());
        RelationalEvents relationalEvents = new RelationalEvents(events, true);
        relationalEvents.setParentId("existingId");

        String result = relationalEvents.getParentId();

        assertEquals("existingId", result);
    }

    @Test
    void getParentId_shouldReturnActivityId_whenActivityCompletedEventPresent() {
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        ActivityCompletedEvent completedEvent = new ActivityCompletedEvent();
        completedEvent.setActivityId("completed123");
        event.setActivityCompleted(completedEvent);
        events.add(event);

        RelationalEvents relationalEvents = new RelationalEvents(events, true);

        String result = relationalEvents.getParentId();

        assertEquals("completed123", result);
        assertEquals("completed123", relationalEvents.getParentId());
    }

    @Test
    void getParentId_shouldReturnActivityId_whenActivityFailedEventPresent() {
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        ActivityFailedEvent failedEvent = new ActivityFailedEvent();
        failedEvent.setActivityId("failed123");
        event.setActivityFailed(failedEvent);
        events.add(event);

        RelationalEvents relationalEvents = new RelationalEvents(events, true);

        String result = relationalEvents.getParentId();

        assertEquals("failed123", result);
    }

    @Test
    void getParentId_shouldReturnActivityId_whenActivityExpiredEventPresent() {
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        ActivityExpiredEvent expiredEvent = new ActivityExpiredEvent();
        expiredEvent.setActivityId("expired123");
        event.setActivityExpired(expiredEvent);
        events.add(event);

        RelationalEvents relationalEvents = new RelationalEvents(events, true);

        String result = relationalEvents.getParentId();

        assertEquals("expired123", result);
    }

    @Test
    void getParentId_shouldReturnNull_whenNoActivityEventsPresent() {
        List<Event> events = new ArrayList<>();
        events.add(new Event());

        RelationalEvents relationalEvents = new RelationalEvents(events, true);

        String result = relationalEvents.getParentId();

        assertNull(result);
    }

    @Test
    void getParentId_shouldPrioritizeActivityCompleted_overFailed() {
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        ActivityCompletedEvent completedEvent = new ActivityCompletedEvent();
        completedEvent.setActivityId("completed123");
        event.setActivityCompleted(completedEvent);
        ActivityFailedEvent failedEvent = new ActivityFailedEvent();
        failedEvent.setActivityId("failed123");
        event.setActivityFailed(failedEvent);
        events.add(event);

        RelationalEvents relationalEvents = new RelationalEvents(events, true);

        String result = relationalEvents.getParentId();

        assertEquals("completed123", result);
    }

    @Test
    void getParentId_shouldPrioritizeActivityFailed_overExpired() {
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        ActivityFailedEvent failedEvent = new ActivityFailedEvent();
        failedEvent.setActivityId("failed123");
        event.setActivityFailed(failedEvent);
        ActivityExpiredEvent expiredEvent = new ActivityExpiredEvent();
        expiredEvent.setActivityId("expired123");
        event.setActivityExpired(expiredEvent);
        events.add(event);

        RelationalEvents relationalEvents = new RelationalEvents(events, true);

        String result = relationalEvents.getParentId();

        assertEquals("failed123", result);
    }

    @Test
    void getParentId_shouldReturnNull_whenActivityIdIsNull() {
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        ActivityExpiredEvent expiredEvent = new ActivityExpiredEvent();
        expiredEvent.setActivityId(null);
        event.setActivityExpired(expiredEvent);
        events.add(event);

        RelationalEvents relationalEvents = new RelationalEvents(events, true);

        String result = relationalEvents.getParentId();

        assertNull(result);
    }
}
