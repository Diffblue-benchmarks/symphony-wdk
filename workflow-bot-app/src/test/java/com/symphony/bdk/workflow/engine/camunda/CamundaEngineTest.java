package com.symphony.bdk.workflow.engine.camunda;

import org.checkerframework.checker.units.qual.C;
import org.mockito.Mockito;

@org.springframework.test.context.ContextConfiguration(classes = {})
@org.junit.jupiter.api.extension.ExtendWith(value = org.springframework.test.context.junit.jupiter.SpringExtension.class) // if JUnit 5
public class CamundaEngineTest {
  @org.springframework.boot.test.mock.mockito.MockBean com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction auditTrailLogAction;
  @org.springframework.boot.test.mock.mockito.MockBean com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder camundaBpmnBuilder;
  //@org.springframework.beans.factory.annotation.Autowired com.symphony.bdk.workflow.engine.camunda.CamundaEngine camundaEngine;
  @org.springframework.beans.factory.annotation.Autowired java.util.List<com.symphony.bdk.workflow.event.RealTimeEventProcessor<?>> list;
  @org.springframework.boot.test.mock.mockito.MockBean com.symphony.bdk.workflow.event.RealTimeEventProcessor<java.lang.Object> realTimeEventProcessor;
  @org.springframework.boot.test.mock.mockito.MockBean org.camunda.bpm.engine.RepositoryService repositoryService;
  @org.junit.jupiter.api.Test // if JUnit 5
  public void testSpringContextLoads() {
    Mockito.when(realTimeEventProcessor.sourceType()).thenReturn(Object.class);
    CamundaEngine camundaEngine = new CamundaEngine(repositoryService, camundaBpmnBuilder, list, auditTrailLogAction);
  }
}
