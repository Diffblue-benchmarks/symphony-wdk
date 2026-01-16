package com.symphony.bdk.workflow.engine.handler;

import org.camunda.bpm.engine.impl.history.event.HistoryEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class HistoricEventActionExecutorClaudeTest {

  @Mock
  private HistoricEventAction historicEventAction;

  @Mock
  private HistoryEvent historyEvent;

  private HistoricEventActionExecutor executor;

  @BeforeEach
  void setUp() {
    executor = new HistoricEventActionExecutor();
  }

  @Test
  void testConstructor() {
    // Test that constructor creates a non-null instance
    HistoricEventActionExecutor newExecutor = new HistoricEventActionExecutor();
    assertThat(newExecutor).isNotNull();
  }

  @Test
  void testExecuteAction_callsActionExecute() {
    // When
    executor.executeAction(historicEventAction, historyEvent);

    // Then
    verify(historicEventAction, times(1)).execute(historyEvent);
  }

  @Test
  void testExecuteAction_withNullAction() {
    // This test verifies behavior when a null action is passed
    // The method does not perform null checks, so this should throw NullPointerException
    org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
      executor.executeAction(null, historyEvent);
    });
  }

  @Test
  void testExecuteAction_withNullEvent() {
    // This test verifies behavior when a null event is passed
    // The action's execute method should be called with null
    executor.executeAction(historicEventAction, null);

    // Verify that execute was called with null
    verify(historicEventAction, times(1)).execute(null);
  }

  @Test
  void testExecuteAction_withBothNull() {
    // Test behavior when both parameters are null
    org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
      executor.executeAction(null, null);
    });
  }

  @Test
  void testExecuteAction_multipleCalls() {
    // Test that the executor can handle multiple calls
    executor.executeAction(historicEventAction, historyEvent);
    executor.executeAction(historicEventAction, historyEvent);
    executor.executeAction(historicEventAction, historyEvent);

    // Verify that execute was called three times
    verify(historicEventAction, times(3)).execute(historyEvent);
  }
}
