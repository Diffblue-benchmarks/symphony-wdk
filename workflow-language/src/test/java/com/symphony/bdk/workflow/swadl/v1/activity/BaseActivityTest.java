package com.symphony.bdk.workflow.swadl.v1.activity;

import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BaseActivityTest {

    static class ConcreteActivity extends BaseActivity {
    }

    @Test
    void addShouldStoreKeyValueInVariableProperties() {
        ConcreteActivity activity = new ConcreteActivity();

        activity.add("testKey", "testValue");

        assertEquals("testValue", activity.getVariableProperties().get("testKey"));
    }

    @Test
    void getEventsShouldReturnRelationalEventsWithOneOf() {
        ConcreteActivity activity = new ConcreteActivity();
        EventWithTimeout eventWithTimeout = new EventWithTimeout();
        Event event1 = new Event();
        Event event2 = new Event();
        eventWithTimeout.setOneOf(Arrays.asList(event1, event2));
        activity.setOn(eventWithTimeout);

        RelationalEvents result = activity.getEvents();

        assertNotNull(result);
        assertEquals(2, result.getEvents().size());
        assertFalse(result.isParallel());
    }

    @Test
    void getEventsShouldReturnRelationalEventsWithAllOf() {
        ConcreteActivity activity = new ConcreteActivity();
        EventWithTimeout eventWithTimeout = new EventWithTimeout();
        Event event1 = new Event();
        Event event2 = new Event();
        eventWithTimeout.setAllOf(Arrays.asList(event1, event2));
        activity.setOn(eventWithTimeout);

        RelationalEvents result = activity.getEvents();

        assertNotNull(result);
        assertEquals(2, result.getEvents().size());
        assertEquals(true, result.isParallel());
    }

    @Test
    void getEventsShouldReturnSingleEventWhenNoOneOfOrAllOf() {
        ConcreteActivity activity = new ConcreteActivity();
        EventWithTimeout eventWithTimeout = new EventWithTimeout();
        activity.setOn(eventWithTimeout);

        RelationalEvents result = activity.getEvents();

        assertNotNull(result);
        assertEquals(1, result.getEvents().size());
        assertEquals(eventWithTimeout, result.getEvents().get(0));
        assertFalse(result.isParallel());
    }

    @Test
    void getEventsShouldReturnEmptyEventsWhenOnIsNull() {
        ConcreteActivity activity = new ConcreteActivity();

        RelationalEvents result = activity.getEvents();

        assertNotNull(result);
        assertEquals(0, result.getEvents().size());
        assertFalse(result.isParallel());
    }
}
