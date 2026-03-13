package com.symphony.bdk.workflow.engine.handler;

import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HistoricEventActionExecutorTest {

  @Test
  void executeActionShouldDelegateToHistoricEventAction() {
    HistoricEventAction action = mock(HistoricEventAction.class);
    HistoryEvent event = mock(HistoryEvent.class);

    HistoricEventActionExecutor executor = new HistoricEventActionExecutor();
    executor.executeAction(action, event);

    verify(action).execute(event);
  }
}
