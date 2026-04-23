package com.symphony.bdk.workflow.swadl.v1.activity;

import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BaseActivityTest {

  static class ConcreteActivity extends BaseActivity {
  }

  @Test
  void shouldAddKeyValueToVariableProperties() {
    ConcreteActivity activity = new ConcreteActivity();

    activity.add("myKey", "myValue");

    assertEquals("myValue", activity.getVariableProperties().get("myKey"));
  }

  @Test
  void shouldReturnEmptyRelationalEventsWhenOnIsNull() {
    ConcreteActivity activity = new ConcreteActivity();

    RelationalEvents events = activity.getEvents();

    assertNotNull(events);
    assertTrue(events.getEvents().isEmpty());
    assertFalse(events.isParallel());
  }

  @Test
  void shouldReturnOneOfEventsWhenOnHasOneOf() {
    ConcreteActivity activity = new ConcreteActivity();
    EventWithTimeout on = new EventWithTimeout();
    EventWithTimeout child = new EventWithTimeout();
    on.setOneOf(Collections.singletonList(child));
    activity.setOn(on);

    RelationalEvents events = activity.getEvents();

    assertEquals(1, events.getEvents().size());
    assertFalse(events.isParallel());
  }

  @Test
  void shouldReturnAllOfEventsWhenOnHasAllOf() {
    ConcreteActivity activity = new ConcreteActivity();
    EventWithTimeout on = new EventWithTimeout();
    EventWithTimeout child = new EventWithTimeout();
    on.setAllOf(Collections.singletonList(child));
    activity.setOn(on);

    RelationalEvents events = activity.getEvents();

    assertEquals(1, events.getEvents().size());
    assertTrue(events.isParallel());
  }

  @Test
  void shouldReturnSingletonListWhenOnHasNoOneOfOrAllOf() {
    ConcreteActivity activity = new ConcreteActivity();
    EventWithTimeout on = new EventWithTimeout();
    activity.setOn(on);

    RelationalEvents events = activity.getEvents();

    assertEquals(1, events.getEvents().size());
    assertEquals(on, events.getEvents().get(0));
    assertFalse(events.isParallel());
  }
}
