package com.symphony.bdk.workflow.event;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.gen.api.model.V4Initiator;
import com.symphony.bdk.gen.api.model.V4User;
import com.symphony.bdk.spring.events.RealTimeEvent;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.SignalEventReceivedBuilderImpl;
import org.camunda.bpm.engine.impl.interceptor.Command;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.junit.jupiter.api.DisplayName;
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
class AbstractRealTimeEventProcessorDiffblueTest {
  @Autowired
  private AbstractRealTimeEventProcessor<RequestReceivedEvent> abstractRealTimeEventProcessor;

  @MockBean
  private RuntimeService runtimeService;

  /**
   * Test {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}.
   * <p>
   * Method under test:
   * {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test process(RealTimeEvent)")
  void testProcess() throws Exception {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Void>>any())).thenReturn(null);
    SignalEventReceivedBuilderImpl signalEventReceivedBuilderImpl = new SignalEventReceivedBuilderImpl(commandExecutor,
        "Signal Name");

    when(runtimeService.createSignalEvent(Mockito.<String>any())).thenReturn(signalEventReceivedBuilderImpl);
    V4Initiator initiator = new V4Initiator();

    // Act
    abstractRealTimeEventProcessor.process(new RealTimeEvent<>(initiator, new RequestReceivedEvent()));

    // Assert
    verify(runtimeService).createSignalEvent(eq("request-received_null"));
    verify(commandExecutor).execute(isA(Command.class));
  }

  /**
   * Test {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}.
   * <ul>
   *   <li>Given {@link V4User} {@link V4User#getUserId()} return one.</li>
   *   <li>Then calls {@link V4User#getUserId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test process(RealTimeEvent); given V4User getUserId() return one; then calls getUserId()")
  void testProcess_givenV4UserGetUserIdReturnOne_thenCallsGetUserId() throws Exception {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Void>>any())).thenReturn(null);
    SignalEventReceivedBuilderImpl signalEventReceivedBuilderImpl = new SignalEventReceivedBuilderImpl(commandExecutor,
        "Signal Name");

    when(runtimeService.createSignalEvent(Mockito.<String>any())).thenReturn(signalEventReceivedBuilderImpl);
    V4User v4User = mock(V4User.class);
    when(v4User.getUserId()).thenReturn(1L);
    V4Initiator initiator = mock(V4Initiator.class);
    when(initiator.getUser()).thenReturn(v4User);

    // Act
    abstractRealTimeEventProcessor.process(new RealTimeEvent<>(initiator, new RequestReceivedEvent()));

    // Assert
    verify(initiator, atLeast(1)).getUser();
    verify(v4User, atLeast(1)).getUserId();
    verify(runtimeService).createSignalEvent(eq("request-received_null"));
    verify(commandExecutor).execute(isA(Command.class));
  }

  /**
   * Test {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}.
   * <ul>
   *   <li>Given {@link V4User} (default constructor).</li>
   *   <li>When {@link V4Initiator} {@link V4Initiator#getUser()} return
   * {@link V4User} (default constructor).</li>
   *   <li>Then calls {@link V4Initiator#getUser()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test process(RealTimeEvent); given V4User (default constructor); when V4Initiator getUser() return V4User (default constructor); then calls getUser()")
  void testProcess_givenV4User_whenV4InitiatorGetUserReturnV4User_thenCallsGetUser() throws Exception {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Void>>any())).thenReturn(null);
    SignalEventReceivedBuilderImpl signalEventReceivedBuilderImpl = new SignalEventReceivedBuilderImpl(commandExecutor,
        "Signal Name");

    when(runtimeService.createSignalEvent(Mockito.<String>any())).thenReturn(signalEventReceivedBuilderImpl);
    V4Initiator initiator = mock(V4Initiator.class);
    when(initiator.getUser()).thenReturn(new V4User());

    // Act
    abstractRealTimeEventProcessor.process(new RealTimeEvent<>(initiator, new RequestReceivedEvent()));

    // Assert
    verify(initiator, atLeast(1)).getUser();
    verify(runtimeService).createSignalEvent(eq("request-received_null"));
    verify(commandExecutor).execute(isA(Command.class));
  }

  /**
   * Test {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}.
   * <ul>
   *   <li>When {@link RealTimeEvent#RealTimeEvent(V4Initiator, Object)} with
   * initiator is {@code null} and source is {@link RequestReceivedEvent} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractRealTimeEventProcessor#process(RealTimeEvent)}
   */
  @Test
  @DisplayName("Test process(RealTimeEvent); when RealTimeEvent(V4Initiator, Object) with initiator is 'null' and source is RequestReceivedEvent (default constructor)")
  void testProcess_whenRealTimeEventWithInitiatorIsNullAndSourceIsRequestReceivedEvent() throws Exception {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Void>>any())).thenReturn(null);
    SignalEventReceivedBuilderImpl signalEventReceivedBuilderImpl = new SignalEventReceivedBuilderImpl(commandExecutor,
        "Signal Name");

    when(runtimeService.createSignalEvent(Mockito.<String>any())).thenReturn(signalEventReceivedBuilderImpl);

    // Act
    abstractRealTimeEventProcessor.process(new RealTimeEvent<>(null, new RequestReceivedEvent()));

    // Assert
    verify(runtimeService).createSignalEvent(eq("request-received_null"));
    verify(commandExecutor).execute(isA(Command.class));
  }
}
