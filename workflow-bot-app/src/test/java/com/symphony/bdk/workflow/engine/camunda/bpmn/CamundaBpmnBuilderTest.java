package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.EndEventBuilder;
import org.camunda.bpm.model.bpmn.builder.EventBasedGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.ExclusiveGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
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

  @Test
  void shouldAddExclusiveGatewayWhenActivitiesAndConditional() throws Exception {
    // Given
    String nodeId = "test/node";
    ExclusiveGatewayBuilder exclusiveGatewayBuilder = org.mockito.Mockito.mock(ExclusiveGatewayBuilder.class);
    when(flowNodeBuilder.exclusiveGateway("testnode_exclusive_gateway")).thenReturn(exclusiveGatewayBuilder);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeAddGateway(nodeId, flowNodeBuilder, true, true, 2);

    // Then
    verify(flowNodeBuilder).exclusiveGateway("testnode_exclusive_gateway");
    assertThat(result).isEqualTo(exclusiveGatewayBuilder);
  }

  @Test
  void shouldAddEventBasedGatewayWhenNotActivitiesAndConditional() throws Exception {
    // Given
    String nodeId = "testNode";
    EventBasedGatewayBuilder eventGatewayBuilder = org.mockito.Mockito.mock(EventBasedGatewayBuilder.class);
    when(flowNodeBuilder.eventBasedGateway()).thenReturn(eventGatewayBuilder);
    when(eventGatewayBuilder.id(nodeId + "_event_gateway")).thenReturn(eventGatewayBuilder);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeAddGateway(nodeId, flowNodeBuilder, false, true, 1);

    // Then
    verify(flowNodeBuilder).eventBasedGateway();
    verify(eventGatewayBuilder).id("testNode_event_gateway");
    assertThat(result).isEqualTo(eventGatewayBuilder);
  }

  @Test
  void shouldAddEventBasedGatewayWhenNotActivitiesAndMultipleChildren() throws Exception {
    // Given
    String nodeId = "testNode";
    EventBasedGatewayBuilder eventGatewayBuilder = org.mockito.Mockito.mock(EventBasedGatewayBuilder.class);
    when(flowNodeBuilder.eventBasedGateway()).thenReturn(eventGatewayBuilder);
    when(eventGatewayBuilder.id(nodeId + "_event_gateway")).thenReturn(eventGatewayBuilder);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeAddGateway(nodeId, flowNodeBuilder, false, false, 2);

    // Then
    verify(flowNodeBuilder).eventBasedGateway();
    verify(eventGatewayBuilder).id("testNode_event_gateway");
    assertThat(result).isEqualTo(eventGatewayBuilder);
  }

  @Test
  void shouldReturnBuilderUnchangedWhenNoGatewayNeeded() throws Exception {
    // Given
    String nodeId = "testNode";

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeAddGateway(nodeId, flowNodeBuilder, false, false, 1);

    // Then
    verify(flowNodeBuilder, never()).exclusiveGateway(any());
    verify(flowNodeBuilder, never()).eventBasedGateway();
    assertThat(result).isEqualTo(flowNodeBuilder);
  }

  private AbstractFlowNodeBuilder<?, ?> invokeAddGateway(String nodeId, AbstractFlowNodeBuilder<?, ?> builder,
      boolean activities, boolean conditional, int childrenSize) throws Exception {
    Method addGatewayMethod = CamundaBpmnBuilder.class.getDeclaredMethod("addGateway", String.class,
        AbstractFlowNodeBuilder.class, boolean.class, boolean.class, int.class);
    addGatewayMethod.setAccessible(true);
    return (AbstractFlowNodeBuilder<?, ?>) addGatewayMethod.invoke(camundaBpmnBuilder, nodeId, builder, activities,
        conditional, childrenSize);
  }

  @Test
  void shouldReturnBuilderUnchangedWhenNoSubProcesses() throws Exception {
    // Given
    when(context.hasEventSubProcess()).thenReturn(false);
    when(context.hasTimeoutSubProcess()).thenReturn(false);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeCloseUpSubProcessesIfAny(context, flowNodeBuilder);

    // Then
    assertThat(result).isEqualTo(flowNodeBuilder);
    verify(context, never()).removeLastEventSubProcessBuilder();
    verify(context, never()).removeLastSubProcessTimeoutBuilder();
  }

  @Test
  void shouldCloseEventSubProcessesWhenPresent() throws Exception {
    // Given
    EventSubProcessBuilder eventSubProcessBuilder = org.mockito.Mockito.mock(EventSubProcessBuilder.class);
    SubProcessBuilder resultSubProcessBuilder = org.mockito.Mockito.mock(SubProcessBuilder.class);
    EndEventBuilder resultEndEventBuilder = org.mockito.Mockito.mock(EndEventBuilder.class);

    when(context.hasEventSubProcess()).thenReturn(true, false);
    when(context.hasTimeoutSubProcess()).thenReturn(false);
    when(context.removeLastEventSubProcessBuilder()).thenReturn(eventSubProcessBuilder);
    when(eventSubProcessBuilder.subProcessDone()).thenReturn(resultSubProcessBuilder);
    when(resultSubProcessBuilder.endEvent()).thenReturn(resultEndEventBuilder);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeCloseUpSubProcessesIfAny(context, flowNodeBuilder);

    // Then
    verify(context).removeLastEventSubProcessBuilder();
    verify(eventSubProcessBuilder).subProcessDone();
    verify(context).cacheSubProcessTimeoutToDone(resultSubProcessBuilder);
    verify(resultSubProcessBuilder).endEvent();
    assertThat(result).isEqualTo(resultEndEventBuilder);
  }

  @Test
  void shouldCloseTimeoutSubProcessesWhenPresent() throws Exception {
    // Given
    AbstractFlowNodeBuilder<?, ?> timeoutSubProcessBuilder = org.mockito.Mockito.mock(AbstractFlowNodeBuilder.class);

    when(context.hasEventSubProcess()).thenReturn(false);
    when(context.hasTimeoutSubProcess()).thenReturn(true, false);
    doReturn(timeoutSubProcessBuilder).when(context).removeLastSubProcessTimeoutBuilder();
    when(timeoutSubProcessBuilder.endEvent()).thenReturn(endEventBuilder);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeCloseUpSubProcessesIfAny(context, flowNodeBuilder);

    // Then
    verify(context).removeLastSubProcessTimeoutBuilder();
    verify(timeoutSubProcessBuilder).endEvent();
    assertThat(result).isEqualTo(flowNodeBuilder);
  }

  @Test
  void shouldCloseBothEventAndTimeoutSubProcessesWhenPresent() throws Exception {
    // Given
    EventSubProcessBuilder eventSubProcessBuilder = org.mockito.Mockito.mock(EventSubProcessBuilder.class);
    SubProcessBuilder resultSubProcessBuilder = org.mockito.Mockito.mock(SubProcessBuilder.class);
    EndEventBuilder eventEndEventBuilder = org.mockito.Mockito.mock(EndEventBuilder.class);
    AbstractFlowNodeBuilder<?, ?> timeoutSubProcessBuilder = org.mockito.Mockito.mock(AbstractFlowNodeBuilder.class);

    when(context.hasEventSubProcess()).thenReturn(true, false);
    when(context.hasTimeoutSubProcess()).thenReturn(true, false);
    when(context.removeLastEventSubProcessBuilder()).thenReturn(eventSubProcessBuilder);
    when(eventSubProcessBuilder.subProcessDone()).thenReturn(resultSubProcessBuilder);
    when(resultSubProcessBuilder.endEvent()).thenReturn(eventEndEventBuilder);
    doReturn(timeoutSubProcessBuilder).when(context).removeLastSubProcessTimeoutBuilder();
    when(timeoutSubProcessBuilder.endEvent()).thenReturn(endEventBuilder);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeCloseUpSubProcessesIfAny(context, flowNodeBuilder);

    // Then
    verify(context).removeLastEventSubProcessBuilder();
    verify(eventSubProcessBuilder).subProcessDone();
    verify(context).cacheSubProcessTimeoutToDone(resultSubProcessBuilder);
    verify(resultSubProcessBuilder).endEvent();
    verify(context).removeLastSubProcessTimeoutBuilder();
    verify(timeoutSubProcessBuilder).endEvent();
    assertThat(result).isEqualTo(eventEndEventBuilder);
  }

  @Test
  void shouldCloseMultipleEventSubProcessesWhenPresent() throws Exception {
    // Given
    EventSubProcessBuilder eventSubProcessBuilder1 = org.mockito.Mockito.mock(EventSubProcessBuilder.class);
    EventSubProcessBuilder eventSubProcessBuilder2 = org.mockito.Mockito.mock(EventSubProcessBuilder.class);
    SubProcessBuilder resultSubProcessBuilder1 = org.mockito.Mockito.mock(SubProcessBuilder.class);
    SubProcessBuilder resultSubProcessBuilder2 = org.mockito.Mockito.mock(SubProcessBuilder.class);
    EndEventBuilder eventEndEventBuilder1 = org.mockito.Mockito.mock(EndEventBuilder.class);
    EndEventBuilder eventEndEventBuilder2 = org.mockito.Mockito.mock(EndEventBuilder.class);

    when(context.hasEventSubProcess()).thenReturn(true, true, false);
    when(context.hasTimeoutSubProcess()).thenReturn(false);
    when(context.removeLastEventSubProcessBuilder()).thenReturn(eventSubProcessBuilder1, eventSubProcessBuilder2);
    when(eventSubProcessBuilder1.subProcessDone()).thenReturn(resultSubProcessBuilder1);
    when(eventSubProcessBuilder2.subProcessDone()).thenReturn(resultSubProcessBuilder2);
    when(resultSubProcessBuilder1.endEvent()).thenReturn(eventEndEventBuilder1);
    when(resultSubProcessBuilder2.endEvent()).thenReturn(eventEndEventBuilder2);

    // When
    AbstractFlowNodeBuilder<?, ?> result = invokeCloseUpSubProcessesIfAny(context, flowNodeBuilder);

    // Then
    verify(context, org.mockito.Mockito.times(2)).removeLastEventSubProcessBuilder();
    verify(eventSubProcessBuilder1).subProcessDone();
    verify(eventSubProcessBuilder2).subProcessDone();
    verify(context).cacheSubProcessTimeoutToDone(resultSubProcessBuilder1);
    verify(context).cacheSubProcessTimeoutToDone(resultSubProcessBuilder2);
    assertThat(result).isEqualTo(eventEndEventBuilder2);
  }

  private AbstractFlowNodeBuilder<?, ?> invokeCloseUpSubProcessesIfAny(BuildProcessContext context,
      AbstractFlowNodeBuilder<?, ?> builder) throws Exception {
    Method closeUpSubProcessesIfAnyMethod = CamundaBpmnBuilder.class.getDeclaredMethod("closeUpSubProcessesIfAny",
        BuildProcessContext.class, AbstractFlowNodeBuilder.class);
    closeUpSubProcessesIfAnyMethod.setAccessible(true);
    return (AbstractFlowNodeBuilder<?, ?>) closeUpSubProcessesIfAnyMethod.invoke(camundaBpmnBuilder, context, builder);
  }
}
