package com.symphony.bdk.workflow.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.gen.api.model.V4ConnectionRequested;
import com.symphony.bdk.gen.api.model.V4Initiator;
import com.symphony.bdk.spring.events.RealTimeEvent;

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

@ContextConfiguration(classes = {V4ConnectionRequestedProcessor.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class V4ConnectionRequestedProcessorDiffblueTest {

  @Autowired
  private V4ConnectionRequestedProcessor v4ConnectionRequestedProcessor;

  @MockBean
  private RuntimeService runtimeService;

  /**
   * Test {@link V4ConnectionRequestedProcessor#V4ConnectionRequestedProcessor(RuntimeService)}.
   *
   * <p>Method under test:
   * {@link V4ConnectionRequestedProcessor#V4ConnectionRequestedProcessor(RuntimeService)}
   */
  @Test
  @DisplayName("Test V4ConnectionRequestedProcessor(RuntimeService)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void V4ConnectionRequestedProcessor.<init>(RuntimeService)"
  })
  void testConstructor() throws Exception {
    // Arrange
    CommandExecutor commandExecutor = mock(CommandExecutor.class);
    when(commandExecutor.execute(Mockito.<Command<Void>>any())).thenReturn(null);
    when(runtimeService.createSignalEvent(Mockito.<String>any()))
        .thenReturn(new SignalEventReceivedBuilderImpl(commandExecutor, "Signal Name"));

    // Act
    v4ConnectionRequestedProcessor.process(
        new RealTimeEvent<>(new V4Initiator(), new V4ConnectionRequested()));

    // Assert
    verify(runtimeService).createSignalEvent("connection-requested");
    verify(commandExecutor).execute(isA(Command.class));
    assertEquals("connection-requested", v4ConnectionRequestedProcessor.eventName);
  }
}
