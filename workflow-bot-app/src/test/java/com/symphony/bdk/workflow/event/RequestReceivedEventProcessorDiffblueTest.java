package com.symphony.bdk.workflow.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.executor.EventHolder;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;
import java.util.HashMap;
import java.util.Map;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.SignalEventReceivedBuilderImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
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

@ContextConfiguration(classes = {RequestReceivedEventProcessor.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RequestReceivedEventProcessorDiffblueTest {
  @Autowired
  private RequestReceivedEventProcessor requestReceivedEventProcessor;

  @MockBean
  private RuntimeService runtimeService;

  /**
   * Test {@link RequestReceivedEventProcessor#processEventSource(RequestReceivedEvent, Map)} with {@code RequestReceivedEvent}, {@code Map}.
   * <ul>
   *   <li>Then {@link HashMap#HashMap()} size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link RequestReceivedEventProcessor#processEventSource(RequestReceivedEvent, Map)}
   */
  @Test
  @DisplayName("Test processEventSource(RequestReceivedEvent, Map) with 'RequestReceivedEvent', 'Map'; then HashMap() size is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void RequestReceivedEventProcessor.processEventSource(RequestReceivedEvent, Map)"})
  void testProcessEventSourceWithRequestReceivedEventMap_thenHashMapSizeIsOne() throws Exception {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Void>>any())).thenReturn(null);
    when(runtimeService.createSignalEvent(Mockito.<String>any()))
        .thenReturn(new SignalEventReceivedBuilderImpl(commandExecutor, "Signal Name"));

    RequestReceivedEvent eventSource = new RequestReceivedEvent();
    eventSource.setArguments(new HashMap<>());
    eventSource.setId("42");
    eventSource.setToken("ABC123");
    eventSource.setWorkflowId("42");

    HashMap<String, Object> variables = new HashMap<>();
    variables.put("event", new EventHolder<>());

    // Act
    requestReceivedEventProcessor.processEventSource(eventSource, variables);

    // Assert
    verify(runtimeService).createSignalEvent(eq("request-received_42"));
    verify(commandExecutor).execute(isA(Command.class));
    assertEquals(1, variables.size());
    Object getResult = variables.get("event");
    assertTrue(getResult instanceof EventHolder);
    Map<String, Object> args = ((EventHolder<Object>) getResult).getArgs();
    assertEquals(1, args.size());
    assertEquals("request-received_42", args.get(RealTimeEventProcessor.EVENT_NAME_KEY));
  }
}
