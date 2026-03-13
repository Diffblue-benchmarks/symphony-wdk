package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.event.FormRepliedEvent;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

class SignalNodeBuilderTest {

  private SignalNodeBuilder signalNodeBuilder;

  @Mock
  private BuildProcessContext context;

  @Mock
  private WorkflowDirectedGraph.NodeChildren nodeChildren;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    signalNodeBuilder = new SignalNodeBuilder();
  }

  @Test
  void shouldBuildEventSubProcessWhenHasFormRepliedEventBrother() {
    // Arrange
    WorkflowNode element = new WorkflowNode()
        .id("signal-1")
        .eventId("signal-event-1");
    String parentId = "parent-1";

    when(context.readChildren(parentId)).thenReturn(nodeChildren);
    when(nodeChildren.getGateway()).thenReturn(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
    when(nodeChildren.getChildren()).thenReturn(Arrays.asList("child-1", "child-2"));

    WorkflowNode formReplyNode = new WorkflowNode()
        .elementType(WorkflowNodeType.FORM_REPLIED_EVENT);
    Event event = new Event();
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(false);
    event.setFormReplied(formRepliedEvent);
    formReplyNode.setEvent(event);

    when(context.readWorkflowNode("child-1")).thenReturn(formReplyNode);
    when(context.readWorkflowNode("child-2")).thenReturn(new WorkflowNode());

    AbstractFlowNodeBuilder<?, ?> builder = Bpmn.createExecutableProcess("test").startEvent();
    var subProcessBuilder = builder.subProcess();
    when(context.getLastSubProcessBuilder()).thenReturn(subProcessBuilder);

    // Act
    AbstractFlowNodeBuilder<?, ?> result = signalNodeBuilder.build(element, parentId, builder, context);

    // Assert
    assertThat(result).isNotNull();
    verify(context).getLastSubProcessBuilder();
    verify(context, atLeastOnce()).readChildren(parentId);
  }

  @Test
  void shouldBuildSignalWithCatchEventBuilder() {
    // Arrange
    WorkflowNode element = new WorkflowNode()
        .id("signal-2")
        .eventId("signal-event-2");
    String parentId = "parent-2";

    when(context.readChildren(parentId)).thenReturn(null);

    AbstractFlowNodeBuilder<?, ?> catchEventBuilder = Bpmn.createExecutableProcess("test")
        .startEvent()
        .intermediateCatchEvent();

    // Act
    AbstractFlowNodeBuilder<?, ?> result = signalNodeBuilder.build(element, parentId, catchEventBuilder, context);

    // Assert
    assertThat(result).isNotNull();
    verify(context).readChildren(parentId);
  }

  @Test
  void shouldBuildIntermediateCatchEventWhenBuilderIsNotCatchEventBuilder() {
    // Arrange
    WorkflowNode element = new WorkflowNode()
        .id("signal-3")
        .eventId("signal-event-3");
    String parentId = "parent-3";

    when(context.readChildren(parentId)).thenReturn(null);

    AbstractFlowNodeBuilder<?, ?> serviceTaskBuilder = Bpmn.createExecutableProcess("test")
        .startEvent()
        .serviceTask();

    // Act
    AbstractFlowNodeBuilder<?, ?> result = signalNodeBuilder.build(element, parentId, serviceTaskBuilder, context);

    // Assert
    assertThat(result).isNotNull();
    verify(context).readChildren(parentId);
  }

  @Test
  void shouldBuildIntermediateCatchEventWhenReadChildrenReturnsNull() {
    // Arrange
    String parentId = "parent-4";
    when(context.readChildren(parentId)).thenReturn(null);

    WorkflowNode element = new WorkflowNode().id("signal-4").eventId("signal-event-4");
    AbstractFlowNodeBuilder<?, ?> builder = Bpmn.createExecutableProcess("test").startEvent();

    // Act
    AbstractFlowNodeBuilder<?, ?> result = signalNodeBuilder.build(element, parentId, builder, context);

    // Assert
    assertThat(result).isNotNull();
    verify(context).readChildren(parentId);
  }

  @Test
  void shouldBuildIntermediateCatchEventWhenGatewayIsParallel() {
    // Arrange
    String parentId = "parent-5";
    when(context.readChildren(parentId)).thenReturn(nodeChildren);
    when(nodeChildren.getGateway()).thenReturn(WorkflowDirectedGraph.Gateway.PARALLEL);

    WorkflowNode element = new WorkflowNode().id("signal-5").eventId("signal-event-5");
    AbstractFlowNodeBuilder<?, ?> builder = Bpmn.createExecutableProcess("test").startEvent();

    // Act
    AbstractFlowNodeBuilder<?, ?> result = signalNodeBuilder.build(element, parentId, builder, context);

    // Assert
    assertThat(result).isNotNull();
    verify(nodeChildren).getGateway();
  }

  @Test
  void shouldBuildIntermediateCatchEventWhenNoFormRepliedEventBrotherExists() {
    // Arrange
    String parentId = "parent-6";
    when(context.readChildren(parentId)).thenReturn(nodeChildren);
    when(nodeChildren.getGateway()).thenReturn(WorkflowDirectedGraph.Gateway.EXCLUSIVE);
    when(nodeChildren.getChildren()).thenReturn(Arrays.asList("child-1"));

    WorkflowNode signalNode = new WorkflowNode().elementType(WorkflowNodeType.SIGNAL_EVENT);
    when(context.readWorkflowNode("child-1")).thenReturn(signalNode);

    WorkflowNode element = new WorkflowNode().id("signal-6").eventId("signal-event-6");
    AbstractFlowNodeBuilder<?, ?> builder = Bpmn.createExecutableProcess("test").startEvent();

    // Act
    AbstractFlowNodeBuilder<?, ?> result = signalNodeBuilder.build(element, parentId, builder, context);

    // Assert
    assertThat(result).isNotNull();
    verify(context).readWorkflowNode("child-1");
  }

  @Test
  void shouldReturnSignalEventType() {
    // Act
    WorkflowNodeType type = signalNodeBuilder.type();

    // Assert
    assertThat(type).isEqualTo(WorkflowNodeType.SIGNAL_EVENT);
  }
}
