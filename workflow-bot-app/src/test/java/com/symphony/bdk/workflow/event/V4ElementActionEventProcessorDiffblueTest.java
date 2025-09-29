package com.symphony.bdk.workflow.event;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.gen.api.model.V4Initiator;
import com.symphony.bdk.gen.api.model.V4SymphonyElementsAction;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.MismatchingMessageCorrelationException;
import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {V4ElementActionEventProcessor.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class V4ElementActionEventProcessorDiffblueTest {
  @MockBean private RuntimeService runtimeService;

  @Autowired private V4ElementActionEventProcessor v4ElementActionEventProcessor;

  /**
   * Test {@link V4ElementActionEventProcessor#processEventSource(V4SymphonyElementsAction, Map)}
   * with {@code V4SymphonyElementsAction}, {@code Map}.
   *
   * <p>Method under test: {@link
   * V4ElementActionEventProcessor#processEventSource(V4SymphonyElementsAction, Map)}
   */
  @Test
  @DisplayName(
      "Test processEventSource(V4SymphonyElementsAction, Map) with 'V4SymphonyElementsAction', 'Map'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void V4ElementActionEventProcessor.processEventSource(V4SymphonyElementsAction, Map)"
  })
  void testProcessEventSourceWithV4SymphonyElementsActionMap() {
    // Arrange
    when(runtimeService.createMessageCorrelation(Mockito.<String>any()))
        .thenThrow(new MismatchingMessageCorrelationException("An error occurred"));

    V4SymphonyElementsAction eventSource = new V4SymphonyElementsAction();
    eventSource.formValues(new HashMap<>());

    HashMap<String, Object> args = new HashMap<>();
    args.put(RealTimeEventProcessor.EVENT_NAME_KEY, "Args");
    EventHolder<Object> eventHolder = new EventHolder<>(new V4Initiator(), "Source", args);

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("form", "Variables");
    variables.put("event", eventHolder);

    // Act and Assert
    assertThrows(
        MismatchingMessageCorrelationException.class,
        () -> v4ElementActionEventProcessor.processEventSource(eventSource, variables));
    verify(runtimeService).createMessageCorrelation("form-reply_null");
  }
}
