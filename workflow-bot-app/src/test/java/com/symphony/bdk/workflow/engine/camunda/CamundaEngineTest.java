package com.symphony.bdk.workflow.engine.camunda;

import static org.camunda.community.mockito.CamundaMockito.runtimeServiceFluentMock;
import static org.codehaus.groovy.runtime.DefaultGroovyMethods.any;
import static org.junit.Assert.assertEquals;

import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;

import org.camunda.bpm.engine.RepositoryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@ContextConfiguration(classes = {CamundaEngine.class, CamundaTestConfig.class})
@ExtendWith(value = org.springframework.test.context.junit.jupiter.SpringExtension.class)
public class CamundaEngineTest {
  @MockBean
  AuditTrailLogAction auditTrailLogAction;
  @MockBean
  CamundaBpmnBuilder camundaBpmnBuilder;
  @MockBean
  RepositoryService repositoryService;
  //@MockBean
  //Map<String, RealTimeEventProcessor<?>> processorRegistry;
 // @MockBean
  //RequestReceivedEventProcessor requestReceivedEventProcessor;
  /*@MockBean
  List<RealTimeEventProcessor<?>> list;*/
/*  @Spy
  List<RealTimeEventProcessor<?>> list = List.of(new RequestReceivedEventProcessor(
      runtimeServiceFluentMock().getRuntimeService()));*/

  @Autowired
  List<RealTimeEventProcessor<?>> list;

  @Autowired
  CamundaEngine camundaEngine;// = new CamundaEngine(repositoryService, camundaBpmnBuilder, list, auditTrailLogAction);
  @BeforeAll
  public static void setUp(){
    //camundaEngine
  }
  @Test // if JUnit 5
  public void testSpringContextLoads() {
    //runtimeServiceFluentMock().getRuntimeService();
    /*RequestReceivedEventProcessor requestReceivedEventProcessor = new RequestReceivedEventProcessor(
        runtimeServiceFluentMock().getRuntimeService());
    List<RealTimeEventProcessor<?>> list = List.of(requestReceivedEventProcessor);
    CamundaEngine camundaEngine = new CamundaEngine(repositoryService, camundaBpmnBuilder, list, auditTrailLogAction);*/
    System.out.println(camundaEngine.hashCode());
  }
}
