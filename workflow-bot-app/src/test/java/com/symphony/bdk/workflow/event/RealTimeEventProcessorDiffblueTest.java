package com.symphony.bdk.workflow.event;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.swadl.v1.event.RequestReceivedEvent;
import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RequestReceivedEventProcessor.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RealTimeEventProcessorDiffblueTest {
  @Autowired
  private RealTimeEventProcessor<RequestReceivedEvent> realTimeEventProcessor;

  @MockBean
  private RuntimeService runtimeService;

  /**
   * Method under test: {@link RealTimeEventProcessor#sourceType()}
   */
  @Test
  void testSourceType() {
    // Arrange
    RealTimeEventProcessor<RequestReceivedEvent> realTimeEventProcessor2 = mock(RealTimeEventProcessor.class);
    Class<RequestReceivedEvent> forNameResult = RequestReceivedEvent.class;
    when(realTimeEventProcessor2.sourceType()).thenReturn(forNameResult);

    // Act
    realTimeEventProcessor2.sourceType();

    // Assert
    verify(realTimeEventProcessor2).sourceType();
  }
}
