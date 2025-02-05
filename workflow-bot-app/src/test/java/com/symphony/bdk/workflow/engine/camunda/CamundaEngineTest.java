package com.symphony.bdk.workflow.engine.camunda;

import static org.camunda.community.mockito.CamundaMockito.runtimeServiceFluentMock;

import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;
import com.symphony.bdk.workflow.event.RequestReceivedEventProcessor;

import org.camunda.bpm.engine.RepositoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@ContextConfiguration(classes = {})
@ExtendWith(value = org.springframework.test.context.junit.jupiter.SpringExtension.class)
public class CamundaEngineTest {
  @MockBean
  AuditTrailLogAction auditTrailLogAction;
  @MockBean
  CamundaBpmnBuilder camundaBpmnBuilder;
  @MockBean
  RealTimeEventProcessor<Object> realTimeEventProcessor;
  @MockBean
  RepositoryService repositoryService;
  @Test // if JUnit 5
  public void testSpringContextLoads() {
    RequestReceivedEventProcessor requestReceivedEventProcessor = new RequestReceivedEventProcessor(
        runtimeServiceFluentMock().getRuntimeService());
    List<RealTimeEventProcessor<?>> list = List.of(requestReceivedEventProcessor);
    CamundaEngine camundaEngine = new CamundaEngine(repositoryService, camundaBpmnBuilder, list, auditTrailLogAction);
  }
}
