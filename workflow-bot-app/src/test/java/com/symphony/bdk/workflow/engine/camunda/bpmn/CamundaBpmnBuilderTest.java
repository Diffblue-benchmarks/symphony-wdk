package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.EndEventBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CamundaBpmnBuilderTest {

  @Mock
  private RepositoryService repositoryService;

  @Mock
  private WorkflowNodeBpmnBuilderRegistry builderFactory;

  @Mock
  private SessionService sessionService;

  @Mock
  private WorkflowDirectedGraphService directedGraphService;

  @Mock
  private BuildProcessContext context;

  @Mock
  private AbstractFlowNodeBuilder<?, ?> flowNodeBuilder;

  @Mock
  private EndEventBuilder endEventBuilder;

  @Mock
  private SubProcessBuilder subProcessBuilder;

  private CamundaBpmnBuilder camundaBpmnBuilder;

  @BeforeEach
  void setUp() {
    camundaBpmnBuilder = new CamundaBpmnBuilder(repositoryService, builderFactory, sessionService,
        directedGraphService);
  }

  @Test
  void shouldAddEndEventWhenBuilderIsNotSubProcessBuilder() throws Exception {
    // Given
    String nodeId = "testNode";
    when(flowNodeBuilder.endEvent()).thenReturn(endEventBuilder);

    // When
    invokeLeafNode(nodeId, flowNodeBuilder, context);

    // Then
    verify(flowNodeBuilder).endEvent();
    verify(context).addNodeBuilder(eq(nodeId), any(AbstractFlowNodeBuilder.class));
    verify(context).addLastNodeBuilder(any(AbstractFlowNodeBuilder.class));
  }

  @Test
  void shouldNotAddEndEventWhenBuilderIsSubProcessBuilder() throws Exception {
    // Given
    String nodeId = "testNode";

    // When
    invokeLeafNode(nodeId, subProcessBuilder, context);

    // Then
    verify(subProcessBuilder, never()).endEvent();
    verify(context, never()).addNodeBuilder(eq(nodeId), any(AbstractFlowNodeBuilder.class));
    verify(context, never()).addLastNodeBuilder(any(AbstractFlowNodeBuilder.class));
  }

  private void invokeLeafNode(String nodeId, AbstractFlowNodeBuilder<?, ?> builder, BuildProcessContext context)
      throws Exception {
    Method leafNodeMethod = CamundaBpmnBuilder.class.getDeclaredMethod("leafNode", String.class,
        AbstractFlowNodeBuilder.class, BuildProcessContext.class);
    leafNodeMethod.setAccessible(true);
    leafNodeMethod.invoke(camundaBpmnBuilder, nodeId, builder, context);
  }
}
