package com.symphony.bdk.workflow.swadl.v1;

import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityTest {

  @Mock
  private BaseActivity baseActivity;

  @Test
  void shouldReturnImplementationWhenGetActivityCalled() {
    Activity activity = new Activity();
    activity.setImplementation(baseActivity);

    BaseActivity result = activity.getActivity();

    assertSame(baseActivity, result);
  }

  @Test
  void shouldReturnEventWhenOnIsSet() {
    EventWithTimeout event = new EventWithTimeout();
    when(baseActivity.getOn()).thenReturn(event);
    Activity activity = new Activity();
    activity.setImplementation(baseActivity);

    Optional<Event> result = activity.getEvent();

    assertTrue(result.isPresent());
    assertEquals(event, result.get());
  }

  @Test
  void shouldReturnEmptyOptionalWhenOnIsNull() {
    when(baseActivity.getOn()).thenReturn(null);
    Activity activity = new Activity();
    activity.setImplementation(baseActivity);

    Optional<Event> result = activity.getEvent();

    assertFalse(result.isPresent());
  }

  @Test
  void shouldReturnEventsFromActivity() {
    RelationalEvents relationalEvents = new RelationalEvents(Collections.emptyList(), false);
    when(baseActivity.getEvents()).thenReturn(relationalEvents);
    Activity activity = new Activity();
    activity.setImplementation(baseActivity);

    RelationalEvents result = activity.getEvents();

    assertEquals(relationalEvents, result);
  }
}
