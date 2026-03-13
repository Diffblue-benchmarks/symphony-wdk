package com.symphony.bdk.workflow.swadl.v1;

import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.RelationalEvents;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityTest {

    @Mock
    private BaseActivity mockBaseActivity;

    @InjectMocks
    private Activity activity;

    @Test
    void getActivityShouldReturnImplementation() {
        activity.setImplementation(mockBaseActivity);

        BaseActivity result = activity.getActivity();

        assertSame(mockBaseActivity, result);
    }

    @Test
    void getEventShouldReturnOptionalOfEventWhenOnIsSet() {
        EventWithTimeout eventWithTimeout = new EventWithTimeout();
        when(mockBaseActivity.getOn()).thenReturn(eventWithTimeout);
        activity.setImplementation(mockBaseActivity);

        Optional<Event> result = activity.getEvent();

        assertTrue(result.isPresent());
        assertSame(eventWithTimeout, result.get());
    }

    @Test
    void getEventShouldReturnEmptyOptionalWhenOnIsNull() {
        when(mockBaseActivity.getOn()).thenReturn(null);
        activity.setImplementation(mockBaseActivity);

        Optional<Event> result = activity.getEvent();

        assertTrue(result.isEmpty());
    }

    @Test
    void getEventsShouldReturnRelationalEvents() {
        RelationalEvents expectedEvents = new RelationalEvents(Collections.emptyList(), false);
        when(mockBaseActivity.getEvents()).thenReturn(expectedEvents);
        activity.setImplementation(mockBaseActivity);

        RelationalEvents result = activity.getEvents();

        assertSame(expectedEvents, result);
    }
}
