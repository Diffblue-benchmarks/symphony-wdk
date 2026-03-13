package com.symphony.bdk.workflow.event;

import org.camunda.bpm.engine.RuntimeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RealTimeEventProcessorTest {

  @Mock
  private RuntimeService runtimeService;

  @Test
  void shouldReturnCorrectSourceTypeWhenCalledOnConcreteImplementation() {
    TestEventProcessor processor = new TestEventProcessor(runtimeService);

    Class<String> sourceType = processor.sourceType();

    assertThat(sourceType).isEqualTo(String.class);
  }

  private static class TestEventProcessor extends AbstractRealTimeEventProcessor<String> {
    public TestEventProcessor(RuntimeService runtimeService) {
      super(runtimeService, "test-event");
    }
  }
}
