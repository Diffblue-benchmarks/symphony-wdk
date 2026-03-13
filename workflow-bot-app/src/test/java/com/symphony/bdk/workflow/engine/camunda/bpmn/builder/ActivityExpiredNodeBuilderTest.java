package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.event.FormRepliedEvent;

import org.camunda.bpm.model.bpmn.builder.AbstractCatchEventBuilder;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.AbstractGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.BoundaryEventBuilder;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.IntermediateCatchEventBuilder;
import org.camunda.bpm.model.bpmn.builder.ServiceTaskBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivityExpiredNodeBuilderTest {

  @Mock
  private BuildProcessContext context;

  @Mock
  private WorkflowNode element;

  @Mock
  private BaseActivity activity;

  @Mock
  private EventWithTimeout eventWithTimeout;

  private ActivityExpiredNodeBuilder builder;

  @BeforeEach
  void setUp() {
    builder = new ActivityExpiredNodeBuilder();
  }

  @Test
  void shouldReturnActivityExpiredEventType() {
    WorkflowNodeType type = builder.type();

    assertThat(type).isEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
  }


  @Test
  @SuppressWarnings("unchecked")
  void shouldBuildWithTimerWhenBuilderIsAbstractCatchEventBuilder() {
    String parentId = "parentId";
    String elementId = "elementId";
    String timeout = "PT1H";
    AbstractCatchEventBuilder catchEventBuilder = mock(AbstractCatchEventBuilder.class);
    AbstractCatchEventBuilder resultBuilder = mock(AbstractCatchEventBuilder.class);

    when(element.getId()).thenReturn(elementId);
    when(element.getEvent()).thenReturn(eventWithTimeout);
    when(eventWithTimeout.getTimeout()).thenReturn(timeout);
    when(context.getParents(elementId)).thenReturn(List.of(parentId));
    WorkflowNode parentNode = mock(WorkflowNode.class);
    when(context.readWorkflowNode(parentId)).thenReturn(parentNode);
    when(parentNode.isNotExclusiveFormReply()).thenReturn(false);
    when(catchEventBuilder.timerWithDuration(timeout)).thenReturn(resultBuilder);

    AbstractFlowNodeBuilder<?, ?> result = builder.build(element, parentId, catchEventBuilder, context);

    assertThat(result).isEqualTo(resultBuilder);
    verify(catchEventBuilder).timerWithDuration(timeout);
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldBuildWithIntermediateCatchEventWhenBuilderIsAbstractGatewayBuilder() {
    String parentId = "parentId";
    String elementId = "elementId";
    String timeout = "PT30M";
    AbstractGatewayBuilder gatewayBuilder = mock(AbstractGatewayBuilder.class);
    IntermediateCatchEventBuilder intermediateCatchEventBuilder = mock(IntermediateCatchEventBuilder.class);

    when(element.getId()).thenReturn(elementId);
    when(element.getEvent()).thenReturn(eventWithTimeout);
    when(eventWithTimeout.getTimeout()).thenReturn(timeout);
    when(context.getParents(elementId)).thenReturn(List.of(parentId));
    WorkflowNode parentNode = mock(WorkflowNode.class);
    when(context.readWorkflowNode(parentId)).thenReturn(parentNode);
    when(parentNode.isNotExclusiveFormReply()).thenReturn(false);
    when(gatewayBuilder.intermediateCatchEvent()).thenReturn(intermediateCatchEventBuilder);
    when(intermediateCatchEventBuilder.name(elementId)).thenReturn(intermediateCatchEventBuilder);
    when(intermediateCatchEventBuilder.timerWithDuration(timeout)).thenReturn(intermediateCatchEventBuilder);

    AbstractFlowNodeBuilder<?, ?> result = builder.build(element, parentId, gatewayBuilder, context);

    assertThat(result).isEqualTo(intermediateCatchEventBuilder);
    verify(gatewayBuilder).intermediateCatchEvent();
    verify(intermediateCatchEventBuilder).name(elementId);
    verify(intermediateCatchEventBuilder).timerWithDuration(timeout);
  }

  @Test
  void shouldReturnBuilderWhenNoExclusiveFormReplyParentNotFoundAndBuilderIsNotSpecialType() {
    String parentId = "parentId";
    String elementId = "elementId";
    String timeout = "PT15M";
    AbstractFlowNodeBuilder<?, ?> regularBuilder = mock(AbstractFlowNodeBuilder.class);

    when(element.getId()).thenReturn(elementId);
    when(element.getEvent()).thenReturn(eventWithTimeout);
    when(eventWithTimeout.getTimeout()).thenReturn(timeout);
    when(context.getParents(elementId)).thenReturn(List.of(parentId));
    WorkflowNode parentNode = mock(WorkflowNode.class);
    when(context.readWorkflowNode(parentId)).thenReturn(parentNode);
    when(parentNode.isNotExclusiveFormReply()).thenReturn(false);

    AbstractFlowNodeBuilder<?, ?> result = builder.build(element, parentId, regularBuilder, context);

    assertThat(result).isEqualTo(regularBuilder);
  }


  @Test
  void shouldHandleMultipleParentsWhenCheckingExclusiveFormReply() {
    String elementId = "elementId";
    String parentId1 = "parentId1";
    String parentId2 = "parentId2";
    AbstractFlowNodeBuilder<?, ?> mockBuilder = mock(AbstractFlowNodeBuilder.class);

    when(element.getId()).thenReturn(elementId);
    when(context.getParents(elementId)).thenReturn(List.of(parentId1, parentId2));

    WorkflowNode parentNode1 = mock(WorkflowNode.class);
    WorkflowNode parentNode2 = mock(WorkflowNode.class);
    when(context.readWorkflowNode(parentId1)).thenReturn(parentNode1);
    when(context.readWorkflowNode(parentId2)).thenReturn(parentNode2);
    when(parentNode1.isNotExclusiveFormReply()).thenReturn(false);
    when(parentNode2.isNotExclusiveFormReply()).thenReturn(false);

    when(element.getEvent()).thenReturn(eventWithTimeout);
    when(eventWithTimeout.getTimeout()).thenReturn("PT1H");

    builder.build(element, "someParentId", mockBuilder, context);

    verify(context).getParents(elementId);
    verify(context).readWorkflowNode(parentId1);
    verify(context).readWorkflowNode(parentId2);
  }

  @Test
  void shouldHandleEmptyParentsList() {
    String elementId = "elementId";
    String parentId = "parentId";
    AbstractFlowNodeBuilder<?, ?> mockBuilder = mock(AbstractFlowNodeBuilder.class);

    when(element.getId()).thenReturn(elementId);
    when(element.getEvent()).thenReturn(eventWithTimeout);
    when(eventWithTimeout.getTimeout()).thenReturn("PT1H");
    when(context.getParents(elementId)).thenReturn(Collections.emptyList());

    AbstractFlowNodeBuilder<?, ?> result = builder.build(element, parentId, mockBuilder, context);

    assertThat(result).isEqualTo(mockBuilder);
    verify(context).getParents(elementId);
  }
}
