package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.workflow.event.RealTimeEventProcessor;
import com.symphony.bdk.workflow.event.RequestReceivedEventProcessor;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.RuntimeServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.when;

@org.springframework.boot.test.context.TestConfiguration
public class CamundaTestConfig {

  RuntimeService runtimeService = new RuntimeServiceImpl();

  @Bean
  public RealTimeEventProcessor realTimeEventProcessor() {
    RealTimeEventProcessor realTimeEventProcessor = Mockito.mock(RequestReceivedEventProcessor.class);
    when(realTimeEventProcessor.sourceType()).thenReturn(String.class);
    return realTimeEventProcessor;
    //return new RequestReceivedEventProcessor(runtimeService);
  }
}
