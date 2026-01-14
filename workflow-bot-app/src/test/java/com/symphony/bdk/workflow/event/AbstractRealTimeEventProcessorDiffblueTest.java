package com.symphony.bdk.workflow.event;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.gen.api.model.V4Initiator;
import com.symphony.bdk.gen.api.model.V4User;
import com.symphony.bdk.spring.events.RealTimeEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;
import java.util.HashMap;
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
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class AbstractRealTimeEventProcessorDiffblueTest {
  @Autowired
  private AbstractRealTimeEventProcessor<RequestReceivedEvent> abstractRealTimeEventProcessor;

  @MockBean private RuntimeService runtimeService;

  /**
   * Test {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}.
   *
   * <p>Method under test: {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test process(RealTimeEvent)")
  @Tag("MaintainedByDiffblue")
  void testProcess() throws Exception {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Void>>any())).thenReturn(null);
    when(runtimeService.createSignalEvent(Mockito.<String>any()))
        .thenReturn(new SignalEventReceivedBuilderImpl(commandExecutor, "Signal Name"));

    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(new HashMap<>());
    requestReceivedEvent.setId("42");
    requestReceivedEvent.setToken("ABC123");
    requestReceivedEvent.setWorkflowId("42");
    RealTimeEvent<RequestReceivedEvent> event =
        new RealTimeEvent<>(new V4Initiator(), requestReceivedEvent);

    // Act
    abstractRealTimeEventProcessor.process(event);

    // Assert
    verify(runtimeService).createSignalEvent("request-received_42");
    verify(commandExecutor).execute(isA(Command.class));
  }

  /**
   * Test {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}.
   *
   * <ul>
   *   <li>Given {@link V4User} (default constructor) userId one.
   *   <li>Then calls {@link V4Initiator#getUser()}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}
   */
  @Test
  @DisplayName(
      "Test process(RealTimeEvent); given V4User (default constructor) userId one; then calls getUser()")
  @Tag("MaintainedByDiffblue")
  void testProcess_givenV4UserUserIdOne_thenCallsGetUser() throws Exception {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Void>>any())).thenReturn(null);
    when(runtimeService.createSignalEvent(Mockito.<String>any()))
        .thenReturn(new SignalEventReceivedBuilderImpl(commandExecutor, "Signal Name"));

    V4User v4User = new V4User();
    v4User.userId(1L);

    V4Initiator initiator = mock(V4Initiator.class);
    when(initiator.getUser()).thenReturn(v4User);

    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(new HashMap<>());
    requestReceivedEvent.setId("42");
    requestReceivedEvent.setToken("ABC123");
    requestReceivedEvent.setWorkflowId("42");

    RealTimeEvent<RequestReceivedEvent> event =
        new RealTimeEvent<>(initiator, requestReceivedEvent);

    // Act
    abstractRealTimeEventProcessor.process(event);

    // Assert
    verify(initiator, atLeast(1)).getUser();
    verify(runtimeService).createSignalEvent("request-received_42");
    verify(commandExecutor).execute(isA(Command.class));
  }

  /**
   * Test {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}.
   *
   * <ul>
   *   <li>Given {@link V4User} (default constructor).
   *   <li>When {@link V4Initiator} (default constructor) user {@link V4User} (default constructor).
   *   <li>Then calls {@link RuntimeService#createSignalEvent(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}
   */
  @Test
  @DisplayName(
      "Test process(RealTimeEvent); given V4User (default constructor); when V4Initiator (default constructor) user V4User (default constructor); then calls createSignalEvent(String)")
  @Tag("MaintainedByDiffblue")
  void testProcess_givenV4User_whenV4InitiatorUserV4User_thenCallsCreateSignalEvent()
      throws Exception {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Void>>any())).thenReturn(null);
    when(runtimeService.createSignalEvent(Mockito.<String>any()))
        .thenReturn(new SignalEventReceivedBuilderImpl(commandExecutor, "Signal Name"));

    V4Initiator initiator = new V4Initiator();
    initiator.user(new V4User());

    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(new HashMap<>());
    requestReceivedEvent.setId("42");
    requestReceivedEvent.setToken("ABC123");
    requestReceivedEvent.setWorkflowId("42");

    RealTimeEvent<RequestReceivedEvent> event =
        new RealTimeEvent<>(initiator, requestReceivedEvent);

    // Act
    abstractRealTimeEventProcessor.process(event);

    // Assert
    verify(runtimeService).createSignalEvent("request-received_42");
    verify(commandExecutor).execute(isA(Command.class));
  }

  /**
   * Test {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}.
   *
   * <ul>
   *   <li>When {@link RealTimeEvent#RealTimeEvent(V4Initiator, Object)} with initiator is {@code
   *       null} and source is {@link RequestReceivedEvent} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}
   */
  @Test
  @DisplayName(
      "Test process(RealTimeEvent); when RealTimeEvent(V4Initiator, Object) with initiator is 'null' and source is RequestReceivedEvent (default constructor)")
  @Tag("MaintainedByDiffblue")
  void testProcess_whenRealTimeEventWithInitiatorIsNullAndSourceIsRequestReceivedEvent()
      throws Exception {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Void>>any())).thenReturn(null);
    when(runtimeService.createSignalEvent(Mockito.<String>any()))
        .thenReturn(new SignalEventReceivedBuilderImpl(commandExecutor, "Signal Name"));

    RequestReceivedEvent requestReceivedEvent = new RequestReceivedEvent();
    requestReceivedEvent.setArguments(new HashMap<>());
    requestReceivedEvent.setId("42");
    requestReceivedEvent.setToken("ABC123");
    requestReceivedEvent.setWorkflowId("42");
    RealTimeEvent<RequestReceivedEvent> event = new RealTimeEvent<>(null, requestReceivedEvent);

    // Act
    abstractRealTimeEventProcessor.process(event);

    // Assert
    verify(runtimeService).createSignalEvent("request-received_42");
    verify(commandExecutor).execute(isA(Command.class));
  }
}
