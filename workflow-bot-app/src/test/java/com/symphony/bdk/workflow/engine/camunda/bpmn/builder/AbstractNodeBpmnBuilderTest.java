package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;

import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.AbstractGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbstractNodeBpmnBuilderTest {

  private static class TestNodeBpmnBuilder extends AbstractNodeBpmnBuilder {
    @Override
    public WorkflowNodeType type() {
      return WorkflowNodeType.ACTIVITY;
    }

    @Override
    public AbstractFlowNodeBuilder<?, ?> build(WorkflowNode element, String parentId,
        AbstractFlowNodeBuilder<?, ?> builder, BuildProcessContext context) {
      return builder;
    }
  }

  private final TestNodeBpmnBuilder underTest = new TestNodeBpmnBuilder();

  @Mock
  private BuildProcessContext context;

  @Test
  void shouldConnectToNewNodeWhenNodeNotBuilt() {
    WorkflowNode element = new WorkflowNode().id("nodeId");
    AbstractFlowNodeBuilder<?, ?> mockBuilder = mock(AbstractFlowNodeBuilder.class);
    when(context.isAlreadyBuilt("nodeId")).thenReturn(false);
    when(context.hasEventSubProcess()).thenReturn(false);

    AbstractFlowNodeBuilder<?, ?> result = underTest.connect(element, "parentId", mockBuilder, context);

    assertThat(result).isSameAs(mockBuilder);
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  void shouldAddConditionWhenBuilderIsGatewayAndNodeIsConditional() {
    WorkflowNode element = new WorkflowNode().id("nodeId").addIfCondition("parentId", "someCondition");
    AbstractGatewayBuilder mockGatewayBuilder = mock(AbstractGatewayBuilder.class);
    when(context.isAlreadyBuilt("nodeId")).thenReturn(false);
    when(context.hasEventSubProcess()).thenReturn(false);
    when(mockGatewayBuilder.condition("if", "someCondition")).thenReturn(mockGatewayBuilder);

    AbstractFlowNodeBuilder<?, ?> result = underTest.connect(element, "parentId", mockGatewayBuilder, context);

    verify(mockGatewayBuilder).condition("if", "someCondition");
    assertThat(result).isSameAs(mockGatewayBuilder);
  }

  @Test
  void shouldEndEventSubProcessWhenHasEventSubProcessAndMultipleParents() {
    WorkflowNode element = new WorkflowNode().id("nodeId");
    AbstractFlowNodeBuilder<?, ?> mockBuilder = mock(AbstractFlowNodeBuilder.class);
    EventSubProcessBuilder mockEventSubProcessBuilder = mock(EventSubProcessBuilder.class);
    SubProcessBuilder mockSubProcessBuilder = mock(SubProcessBuilder.class);

    when(context.isAlreadyBuilt("nodeId")).thenReturn(false);
    when(context.hasEventSubProcess()).thenReturn(true);
    when(context.getParents("nodeId")).thenReturn(Arrays.asList("parent1", "parent2"));
    when(context.removeLastEventSubProcessBuilder()).thenReturn(mockEventSubProcessBuilder);
    when(mockEventSubProcessBuilder.subProcessDone()).thenReturn(mockSubProcessBuilder);

    AbstractFlowNodeBuilder<?, ?> result = underTest.connect(element, "parentId", mockBuilder, context);

    verify(context).cacheSubProcessTimeoutToDone(mockSubProcessBuilder);
    assertThat(result).isSameAs(mockSubProcessBuilder);
  }

  @Test
  void shouldConnectToExistingNodeWhenAlreadyBuiltAndNotConditional() {
    WorkflowNode element = new WorkflowNode().id("nodeId");
    AbstractFlowNodeBuilder<?, ?> mockBuilder = mock(AbstractFlowNodeBuilder.class);

    when(context.isAlreadyBuilt("nodeId")).thenReturn(true);

    AbstractFlowNodeBuilder<?, ?> result = underTest.connect(element, "parentId", mockBuilder, context);

    verify(mockBuilder).connectTo("nodeId");
    assertThat(result).isSameAs(mockBuilder);
  }

  @Test
  void shouldDoConditionalConnectionWhenAlreadyBuiltAndConditionalWithNonGatewayBuilder() {
    WorkflowNode element = new WorkflowNode().id("nodeId").addIfCondition("parentId", "condition == true");
    AbstractFlowNodeBuilder<?, ?> mockBuilder = mock(AbstractFlowNodeBuilder.class, RETURNS_DEEP_STUBS);

    when(context.isAlreadyBuilt("nodeId")).thenReturn(true);
    when(context.hasEventSubProcess()).thenReturn(false);

    AbstractFlowNodeBuilder<?, ?> result = underTest.connect(element, "parentId", mockBuilder, context);

    verify(mockBuilder).exclusiveGateway("nodeId" + CamundaBpmnBuilder.EXCLUSIVE_GATEWAY_SUFFIX);
    assertThat(result).isSameAs(mockBuilder);
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  void shouldDoConditionalConnectionWhenAlreadyBuiltAndConditionalWithGatewayBuilder() {
    WorkflowNode element = new WorkflowNode().id("nodeId").addIfCondition("parentId", "condition == true");
    AbstractGatewayBuilder mockGatewayBuilder = mock(AbstractGatewayBuilder.class, RETURNS_DEEP_STUBS);

    when(context.isAlreadyBuilt("nodeId")).thenReturn(true);

    AbstractFlowNodeBuilder<?, ?> result = underTest.connect(element, "parentId", mockGatewayBuilder, context);

    verify(mockGatewayBuilder).condition("if", "condition == true");
    assertThat(result).isSameAs(mockGatewayBuilder);
  }

  @Test
  void shouldDoConditionalConnectionWithEventSubProcessAndNonGatewayBuilder() {
    WorkflowNode element = new WorkflowNode().id("nodeId").addIfCondition("parentId", "condition == true");
    AbstractFlowNodeBuilder<?, ?> mockBuilder = mock(AbstractFlowNodeBuilder.class, RETURNS_DEEP_STUBS);
    EventSubProcessBuilder mockEventSubProcessBuilder = mock(EventSubProcessBuilder.class);
    SubProcessBuilder mockSubProcessBuilder = mock(SubProcessBuilder.class, RETURNS_DEEP_STUBS);

    when(context.isAlreadyBuilt("nodeId")).thenReturn(true);
    when(context.hasEventSubProcess()).thenReturn(true);
    when(context.removeLastEventSubProcessBuilder()).thenReturn(mockEventSubProcessBuilder);
    when(mockEventSubProcessBuilder.subProcessDone()).thenReturn(mockSubProcessBuilder);

    AbstractFlowNodeBuilder<?, ?> result = underTest.connect(element, "parentId", mockBuilder, context);

    verify(context).cacheSubProcessTimeoutToDone(mockSubProcessBuilder);
    assertThat(result).isSameAs(mockSubProcessBuilder);
  }

  @Test
  void shouldNotConnectToExistingNodeWhenBuilderIsSubProcessBuilder() {
    SubProcessBuilder mockSubProcessBuilder = mock(SubProcessBuilder.class);

    underTest.connectToExistingNode("nodeId", mockSubProcessBuilder);

    verify(mockSubProcessBuilder, never()).connectTo(anyString());
  }

  @Test
  void shouldConnectToExistingNodeWhenBuilderIsNotSubProcessBuilder() {
    AbstractFlowNodeBuilder<?, ?> mockBuilder = mock(AbstractFlowNodeBuilder.class);

    underTest.connectToExistingNode("nodeId", mockBuilder);

    verify(mockBuilder).connectTo("nodeId");
  }
}
