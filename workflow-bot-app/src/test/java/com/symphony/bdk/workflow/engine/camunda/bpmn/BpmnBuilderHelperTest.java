package com.symphony.bdk.workflow.engine.camunda.bpmn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.ExclusiveGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;

class BpmnBuilderHelperTest {

  @Mock
  private BuildProcessContext context;

  @Mock
  private WorkflowDirectedGraph.NodeChildren nodeChildren;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldEndEventSubProcessAndReturnBuilder() {
    // Arrange
    AbstractFlowNodeBuilder<?, ?> builder = Bpmn.createExecutableProcess("test").startEvent();
    SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);
    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);
    SubProcessBuilder returnedBuilder = mock(SubProcessBuilder.class);

    when(context.removeLastEventSubProcessBuilder()).thenReturn(eventSubProcessBuilder);
    when(eventSubProcessBuilder.subProcessDone()).thenReturn(returnedBuilder);

    // Act
    AbstractFlowNodeBuilder<?, ?> result = BpmnBuilderHelper.endEventSubProcess(context, builder);

    // Assert
    assertThat(result).isNotNull();
    verify(context).removeLastEventSubProcessBuilder();
    verify(eventSubProcessBuilder).subProcessDone();
    verify(context).cacheSubProcessTimeoutToDone(returnedBuilder);
  }

  @Test
  void shouldReturnTrueWhenHasActivitiesOnly() {
    // Arrange
    WorkflowNode activityNode1 = new WorkflowNode().elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode activityNode2 = new WorkflowNode().elementType(WorkflowNodeType.JOIN_ACTIVITY);

    when(nodeChildren.getChildren()).thenReturn(Arrays.asList("child-1", "child-2"));
    when(context.readWorkflowNode("child-1")).thenReturn(activityNode1);
    when(context.readWorkflowNode("child-2")).thenReturn(activityNode2);

    // Act
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Assert
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenHasSignalEvent() {
    // Arrange
    WorkflowNode activityNode = new WorkflowNode().elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode signalNode = new WorkflowNode().elementType(WorkflowNodeType.SIGNAL_EVENT);

    when(nodeChildren.getChildren()).thenReturn(Arrays.asList("child-1", "child-2"));
    when(context.readWorkflowNode("child-1")).thenReturn(activityNode);
    when(context.readWorkflowNode("child-2")).thenReturn(signalNode);

    // Act
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnFalseWhenHasFormRepliedEvent() {
    // Arrange
    WorkflowNode activityNode = new WorkflowNode().elementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode formReplyNode = new WorkflowNode().elementType(WorkflowNodeType.FORM_REPLIED_EVENT);

    when(nodeChildren.getChildren()).thenReturn(Arrays.asList("child-1", "child-2"));
    when(context.readWorkflowNode("child-1")).thenReturn(activityNode);
    when(context.readWorkflowNode("child-2")).thenReturn(formReplyNode);

    // Act
    boolean result = BpmnBuilderHelper.hasActivitiesOnly(context, nodeChildren);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnTrueWhenAllChildrenAreConditional() {
    // Arrange
    String parentId = "parent-1";
    WorkflowNode conditionalNode1 = new WorkflowNode().addIfCondition(parentId, "condition1");
    WorkflowNode conditionalNode2 = new WorkflowNode().addIfCondition(parentId, "condition2");

    when(nodeChildren.getChildren()).thenReturn(Arrays.asList("child-1", "child-2"));
    when(context.readWorkflowNode("child-1")).thenReturn(conditionalNode1);
    when(context.readWorkflowNode("child-2")).thenReturn(conditionalNode2);

    // Act
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, parentId);

    // Assert
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenNotAllChildrenAreConditional() {
    // Arrange
    String parentId = "parent-1";
    WorkflowNode conditionalNode = new WorkflowNode().addIfCondition(parentId, "condition1");
    WorkflowNode nonConditionalNode = new WorkflowNode();

    when(nodeChildren.getChildren()).thenReturn(Arrays.asList("child-1", "child-2"));
    when(context.readWorkflowNode("child-1")).thenReturn(conditionalNode);
    when(context.readWorkflowNode("child-2")).thenReturn(nonConditionalNode);

    // Act
    boolean result = BpmnBuilderHelper.hasAllConditionalChildren(context, nodeChildren, parentId);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnTrueWhenHasConditionalString() {
    // Arrange
    String parentId = "parent-1";
    WorkflowNode conditionalNode = new WorkflowNode().addIfCondition(parentId, "condition1");
    WorkflowNode nonConditionalNode = new WorkflowNode();

    when(nodeChildren.getChildren()).thenReturn(Arrays.asList("child-1", "child-2"));
    when(context.readWorkflowNode("child-1")).thenReturn(conditionalNode);
    when(context.readWorkflowNode("child-2")).thenReturn(nonConditionalNode);

    // Act
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, parentId);

    // Assert
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenHasNoConditionalString() {
    // Arrange
    String parentId = "parent-1";
    WorkflowNode nonConditionalNode1 = new WorkflowNode();
    WorkflowNode nonConditionalNode2 = new WorkflowNode();

    when(nodeChildren.getChildren()).thenReturn(Arrays.asList("child-1", "child-2"));
    when(context.readWorkflowNode("child-1")).thenReturn(nonConditionalNode1);
    when(context.readWorkflowNode("child-2")).thenReturn(nonConditionalNode2);

    // Act
    boolean result = BpmnBuilderHelper.hasConditionalString(context, nodeChildren, parentId);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnTrueWhenIsConditionalLoop() {
    // Arrange
    ExclusiveGatewayBuilder gatewayBuilder = Bpmn.createExecutableProcess("test")
        .startEvent()
        .exclusiveGateway();
    String uniqueChild = "child-1";
    WorkflowNode nonConditionalNode = new WorkflowNode();

    when(nodeChildren.isChildUnique()).thenReturn(true);
    when(nodeChildren.getUniqueChild()).thenReturn(uniqueChild);
    when(context.isAlreadyBuilt(uniqueChild)).thenReturn(true);
    when(context.readWorkflowNode(uniqueChild)).thenReturn(nonConditionalNode);

    // Act
    boolean result = BpmnBuilderHelper.isConditionalLoop(gatewayBuilder, context, nodeChildren);

    // Assert
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenIsNotConditionalLoopDueToMultipleChildren() {
    // Arrange
    ExclusiveGatewayBuilder gatewayBuilder = Bpmn.createExecutableProcess("test")
        .startEvent()
        .exclusiveGateway();

    when(nodeChildren.isChildUnique()).thenReturn(false);

    // Act
    boolean result = BpmnBuilderHelper.isConditionalLoop(gatewayBuilder, context, nodeChildren);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnFalseWhenIsNotConditionalLoopDueToNotAlreadyBuilt() {
    // Arrange
    ExclusiveGatewayBuilder gatewayBuilder = Bpmn.createExecutableProcess("test")
        .startEvent()
        .exclusiveGateway();
    String uniqueChild = "child-1";

    when(nodeChildren.isChildUnique()).thenReturn(true);
    when(nodeChildren.getUniqueChild()).thenReturn(uniqueChild);
    when(context.isAlreadyBuilt(uniqueChild)).thenReturn(false);

    // Act
    boolean result = BpmnBuilderHelper.isConditionalLoop(gatewayBuilder, context, nodeChildren);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnFalseWhenIsNotConditionalLoopDueToConditionalNode() {
    // Arrange
    ExclusiveGatewayBuilder gatewayBuilder = Bpmn.createExecutableProcess("test")
        .startEvent()
        .exclusiveGateway();
    String uniqueChild = "child-1";
    WorkflowNode conditionalNode = new WorkflowNode().addIfCondition("parent", "condition");

    when(nodeChildren.isChildUnique()).thenReturn(true);
    when(nodeChildren.getUniqueChild()).thenReturn(uniqueChild);
    when(context.isAlreadyBuilt(uniqueChild)).thenReturn(true);
    when(context.readWorkflowNode(uniqueChild)).thenReturn(conditionalNode);

    // Act
    boolean result = BpmnBuilderHelper.isConditionalLoop(gatewayBuilder, context, nodeChildren);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnFalseWhenIsNotConditionalLoopDueToNonGatewayBuilder() {
    // Arrange
    AbstractFlowNodeBuilder<?, ?> serviceTaskBuilder = Bpmn.createExecutableProcess("test")
        .startEvent()
        .serviceTask();
    String uniqueChild = "child-1";
    WorkflowNode nonConditionalNode = new WorkflowNode();

    when(nodeChildren.isChildUnique()).thenReturn(true);
    when(nodeChildren.getUniqueChild()).thenReturn(uniqueChild);
    when(context.isAlreadyBuilt(uniqueChild)).thenReturn(true);
    when(context.readWorkflowNode(uniqueChild)).thenReturn(nonConditionalNode);

    // Act
    boolean result = BpmnBuilderHelper.isConditionalLoop(serviceTaskBuilder, context, nodeChildren);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnTrueWhenHasLoopAfterSubProcess() {
    // Arrange
    String alreadyBuiltChild = "child-1";
    String notBuiltChild = "child-2";

    when(nodeChildren.getChildren()).thenReturn(Arrays.asList(alreadyBuiltChild, notBuiltChild));
    when(context.hasEventSubProcess()).thenReturn(true);
    when(context.isAlreadyBuilt(alreadyBuiltChild)).thenReturn(true);
    when(context.isAlreadyBuilt(notBuiltChild)).thenReturn(false);

    // Act
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Assert
    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenHasLoopAfterSubProcessButWrongNodeType() {
    // Arrange
    when(context.hasEventSubProcess()).thenReturn(true);

    // Act
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnFalseWhenHasLoopAfterSubProcessButNoEventSubProcess() {
    // Arrange
    when(context.hasEventSubProcess()).thenReturn(false);

    // Act
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnFalseWhenHasLoopAfterSubProcessButNoAlreadyBuiltChildren() {
    // Arrange
    String notBuiltChild = "child-1";

    when(nodeChildren.getChildren()).thenReturn(Collections.singletonList(notBuiltChild));
    when(context.hasEventSubProcess()).thenReturn(true);
    when(context.isAlreadyBuilt(notBuiltChild)).thenReturn(false);

    // Act
    boolean result = BpmnBuilderHelper.hasLoopAfterSubProcess(context, nodeChildren,
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Assert
    assertThat(result).isFalse();
  }
}
