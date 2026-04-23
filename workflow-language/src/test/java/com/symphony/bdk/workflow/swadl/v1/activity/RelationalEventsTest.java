package com.symphony.bdk.workflow.swadl.v1.activity;

import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityCompletedEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityExpiredEvent;
import com.symphony.bdk.workflow.swadl.v1.event.ActivityFailedEvent;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RelationalEventsTest {

  @Test
  void shouldReturnTrueWhenEventsIsNull() {
    RelationalEvents relationalEvents = new RelationalEvents(null, false);

    assertTrue(relationalEvents.isEmpty());
  }

  @Test
  void shouldReturnTrueWhenEventsIsEmpty() {
    RelationalEvents relationalEvents = new RelationalEvents(Collections.emptyList(), false);

    assertTrue(relationalEvents.isEmpty());
  }

  @Test
  void shouldReturnFalseWhenEventsIsNotEmpty() {
    Event event = new Event();
    RelationalEvents relationalEvents = new RelationalEvents(List.of(event), false);

    assertFalse(relationalEvents.isEmpty());
  }

  @Test
  void shouldReturnNullParentIdWhenNotParallel() {
    Event event = new Event();
    RelationalEvents relationalEvents = new RelationalEvents(List.of(event), false);

    assertNull(relationalEvents.getParentId());
  }

  @Test
  void shouldReturnNullParentIdWhenParallelAndEventsIsNull() {
    RelationalEvents relationalEvents = new RelationalEvents(null, true);

    assertNull(relationalEvents.getParentId());
  }

  @Test
  void shouldReturnNullParentIdWhenParallelAndEventsIsEmpty() {
    RelationalEvents relationalEvents = new RelationalEvents(Collections.emptyList(), true);

    assertNull(relationalEvents.getParentId());
  }

  @Test
  void shouldReturnExistingParentIdWhenAlreadySet() {
    Event event = new Event();
    RelationalEvents relationalEvents = new RelationalEvents(List.of(event), true);
    relationalEvents.setParentId("existing-parent");

    assertEquals("existing-parent", relationalEvents.getParentId());
  }

  @Test
  void shouldReturnActivityCompletedIdWhenParallelAndEventHasActivityCompleted() {
    ActivityCompletedEvent completedEvent = new ActivityCompletedEvent();
    completedEvent.setActivityId("completed-activity-id");
    Event event = new Event();
    event.setActivityCompleted(completedEvent);
    RelationalEvents relationalEvents = new RelationalEvents(List.of(event), true);

    assertEquals("completed-activity-id", relationalEvents.getParentId());
  }

  @Test
  void shouldReturnActivityFailedIdWhenParallelAndEventHasActivityFailed() {
    ActivityFailedEvent failedEvent = new ActivityFailedEvent();
    failedEvent.setActivityId("failed-activity-id");
    Event event = new Event();
    event.setActivityFailed(failedEvent);
    RelationalEvents relationalEvents = new RelationalEvents(List.of(event), true);

    assertEquals("failed-activity-id", relationalEvents.getParentId());
  }

  @Test
  void shouldReturnActivityExpiredIdWhenParallelAndEventHasActivityExpired() {
    ActivityExpiredEvent expiredEvent = new ActivityExpiredEvent();
    expiredEvent.setActivityId("expired-activity-id");
    Event event = new Event();
    event.setActivityExpired(expiredEvent);
    RelationalEvents relationalEvents = new RelationalEvents(List.of(event), true);

    assertEquals("expired-activity-id", relationalEvents.getParentId());
  }

  @Test
  void shouldReturnNullWhenParallelAndNoMatchingActivityEvent() {
    Event event = new Event();
    RelationalEvents relationalEvents = new RelationalEvents(List.of(event), true);

    assertNull(relationalEvents.getParentId());
  }
}
