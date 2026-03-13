package com.symphony.bdk.workflow.engine.camunda.bpmn;

import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilder;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.EndEventBuilder;
import org.camunda.bpm.model.bpmn.builder.ExclusiveGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.ParallelGatewayBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CamundaBpmnBuilderComputeChildrenTest {

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
  private WorkflowNode workflowNode;

  private CamundaBpmnBuilder camundaBpmnBuilder;

  @BeforeEach
  void setUp() {
    camundaBpmnBuilder = new CamundaBpmnBuilder(repositoryService, builderFactory, sessionService,
        directedGraphService);
  }

  @Test
  void shouldHandleLeafNodeWhenChildrenIsNull() throws Exception {
    // Given
    String nodeId = "testNode";
    EndEventBuilder endEventBuilder = mock(EndEventBuilder.class);
    when(workflowNode.getId()).thenReturn(nodeId);
    when(context.readChildren(nodeId)).thenReturn(null);
    when(flowNodeBuilder.endEvent()).thenReturn(endEventBuilder);

    // When
    invokeComputeChildren(workflowNode, flowNodeBuilder, context);

    // Then
    verify(context).readChildren(nodeId);
    verify(flowNodeBuilder).endEvent();
  }

  @Test
  void shouldHandleLeafNodeWhenChildrenIsEmpty() throws Exception {
    // Given
    String nodeId = "testNode";
    WorkflowDirectedGraph.NodeChildren emptyChildren = new WorkflowDirectedGraph.NodeChildren();
    EndEventBuilder endEventBuilder = mock(EndEventBuilder.class);
    when(workflowNode.getId()).thenReturn(nodeId);
    when(context.readChildren(nodeId)).thenReturn(emptyChildren);
    when(flowNodeBuilder.endEvent()).thenReturn(endEventBuilder);

    // When
    invokeComputeChildren(workflowNode, flowNodeBuilder, context);

    // Then
    verify(context).readChildren(nodeId);
    verify(flowNodeBuilder).endEvent();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldCreateParallelGatewayWhenGatewayIsParallel() throws Exception {
    // Given
    String nodeId = "testNode";
    String child1Id = "child1";
    String child2Id = "child2";
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(
        WorkflowDirectedGraph.Gateway.PARALLEL,
        Arrays.asList(child1Id, child2Id)
    );
    ParallelGatewayBuilder parallelGatewayBuilder = mock(ParallelGatewayBuilder.class);

    when(workflowNode.getId()).thenReturn(nodeId);
    when(workflowNode.getElementType()).thenReturn(WorkflowNodeType.ACTIVITY);
    when(context.readChildren(nodeId)).thenReturn(children);
    when(flowNodeBuilder.parallelGateway(nodeId + "_fork_gateway")).thenReturn(parallelGatewayBuilder);

    // Mock child nodes and builders for the recursive call
    WorkflowNode childNode1 = mock(WorkflowNode.class);
    WorkflowNode childNode2 = mock(WorkflowNode.class);
    when(childNode1.getId()).thenReturn(child1Id);
    when(childNode2.getId()).thenReturn(child2Id);
    when(context.readWorkflowNode(child1Id)).thenReturn(childNode1);
    when(context.readWorkflowNode(child2Id)).thenReturn(childNode2);
    when(context.isAlreadyBuilt(child1Id)).thenReturn(true); // Already built to skip recursion
    when(context.isAlreadyBuilt(child2Id)).thenReturn(true); // Already built to skip recursion
    doReturn(parallelGatewayBuilder).when(context).getNodeBuilder(nodeId);

    WorkflowNodeBpmnBuilder nodeBuilder = mock(WorkflowNodeBpmnBuilder.class);
    when(builderFactory.getBuilder(any(WorkflowNode.class))).thenReturn(nodeBuilder);
    doReturn(parallelGatewayBuilder).when(nodeBuilder).connect(any(), any(), any(), any());

    // When
    invokeComputeChildren(workflowNode, flowNodeBuilder, context);

    // Then
    verify(flowNodeBuilder).parallelGateway(nodeId + "_fork_gateway");
    verify(context).addNodeBuilder(eq(nodeId), any(AbstractFlowNodeBuilder.class));
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldCallExclusiveSubTreeNodesWhenGatewayIsNotParallel() throws Exception {
    // Given
    String nodeId = "testNode";
    String childId = "child1";
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(
        WorkflowDirectedGraph.Gateway.EXCLUSIVE,
        Arrays.asList(childId)
    );
    ExclusiveGatewayBuilder exclusiveGatewayBuilder = mock(ExclusiveGatewayBuilder.class);

    when(workflowNode.getId()).thenReturn(nodeId);
    when(workflowNode.getElementType()).thenReturn(WorkflowNodeType.ACTIVITY);
    when(context.readChildren(nodeId)).thenReturn(children);
    when(flowNodeBuilder.exclusiveGateway(any())).thenReturn(exclusiveGatewayBuilder);

    WorkflowNode childNode = mock(WorkflowNode.class);
    when(childNode.getId()).thenReturn(childId);
    when(childNode.getElementType()).thenReturn(WorkflowNodeType.ACTIVITY);
    when(childNode.isConditional()).thenReturn(true);
    when(childNode.isConditional(nodeId)).thenReturn(true);
    when(context.readWorkflowNode(childId)).thenReturn(childNode);
    when(context.isAlreadyBuilt(childId)).thenReturn(true); // Already built to skip recursion
    doReturn(exclusiveGatewayBuilder).when(context).getNodeBuilder(nodeId);

    WorkflowNodeBpmnBuilder nodeBuilder = mock(WorkflowNodeBpmnBuilder.class);
    when(builderFactory.getBuilder(childNode)).thenReturn(nodeBuilder);
    doReturn(exclusiveGatewayBuilder).when(nodeBuilder).connect(any(), any(), any(), any());

    // When
    invokeComputeChildren(workflowNode, flowNodeBuilder, context);

    // Then
    verify(context).addNodeBuilder(eq(nodeId), any(AbstractFlowNodeBuilder.class));
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldAddDefaultEndEventWhenAllChildrenAreConditionalAndBuilderIsExclusiveGateway() throws Exception {
    // Given
    String nodeId = "testNode";
    String childId = "child1";
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(
        WorkflowDirectedGraph.Gateway.EXCLUSIVE,
        Arrays.asList(childId)
    );

    ExclusiveGatewayBuilder exclusiveGatewayBuilder = mock(ExclusiveGatewayBuilder.class);
    EndEventBuilder endEventBuilder = mock(EndEventBuilder.class);
    when(exclusiveGatewayBuilder.endEvent()).thenReturn(endEventBuilder);

    when(workflowNode.getId()).thenReturn(nodeId);
    when(workflowNode.getElementType()).thenReturn(WorkflowNodeType.ACTIVITY);
    when(context.readChildren(nodeId)).thenReturn(children);
    when(flowNodeBuilder.exclusiveGateway(any())).thenReturn(exclusiveGatewayBuilder);

    WorkflowNode childNode = mock(WorkflowNode.class);
    when(childNode.getId()).thenReturn(childId);
    when(childNode.getElementType()).thenReturn(WorkflowNodeType.ACTIVITY);
    when(childNode.isConditional()).thenReturn(true);
    when(childNode.isConditional(nodeId)).thenReturn(true);
    when(context.readWorkflowNode(childId)).thenReturn(childNode);
    when(context.isAlreadyBuilt(childId)).thenReturn(true); // Already built to skip recursion
    doReturn(exclusiveGatewayBuilder).when(context).getNodeBuilder(nodeId);

    WorkflowNodeBpmnBuilder nodeBuilder = mock(WorkflowNodeBpmnBuilder.class);
    when(builderFactory.getBuilder(childNode)).thenReturn(nodeBuilder);
    doReturn(exclusiveGatewayBuilder).when(nodeBuilder).connect(any(), any(), any(), any());

    // When
    invokeComputeChildren(workflowNode, flowNodeBuilder, context);

    // Then
    verify(exclusiveGatewayBuilder).endEvent();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldNotAddDefaultEndEventWhenNotAllChildrenAreConditional() throws Exception {
    // Given
    String nodeId = "testNode";
    String childId = "child1";
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(
        WorkflowDirectedGraph.Gateway.EXCLUSIVE,
        Arrays.asList(childId)
    );

    ExclusiveGatewayBuilder exclusiveGatewayBuilder = mock(ExclusiveGatewayBuilder.class);

    when(workflowNode.getId()).thenReturn(nodeId);
    when(workflowNode.getElementType()).thenReturn(WorkflowNodeType.ACTIVITY);
    when(context.readChildren(nodeId)).thenReturn(children);
    when(flowNodeBuilder.exclusiveGateway(any())).thenReturn(exclusiveGatewayBuilder);

    WorkflowNode childNode = mock(WorkflowNode.class);
    when(childNode.getId()).thenReturn(childId);
    when(childNode.getElementType()).thenReturn(WorkflowNodeType.ACTIVITY);
    when(childNode.isConditional()).thenReturn(false);
    when(childNode.isConditional(nodeId)).thenReturn(false);
    when(context.readWorkflowNode(childId)).thenReturn(childNode);
    when(context.isAlreadyBuilt(childId)).thenReturn(true); // Already built to skip recursion
    doReturn(exclusiveGatewayBuilder).when(context).getNodeBuilder(nodeId);

    WorkflowNodeBpmnBuilder nodeBuilder = mock(WorkflowNodeBpmnBuilder.class);
    when(builderFactory.getBuilder(childNode)).thenReturn(nodeBuilder);
    doReturn(exclusiveGatewayBuilder).when(nodeBuilder).connect(any(), any(), any(), any());

    // When
    invokeComputeChildren(workflowNode, flowNodeBuilder, context);

    // Then
    verify(exclusiveGatewayBuilder, never()).endEvent();
  }

  @Test
  @SuppressWarnings("unchecked")
  void shouldNotAddDefaultEndEventWhenBuilderIsNotExclusiveGateway() throws Exception {
    // Given
    String nodeId = "testNode";
    String childId = "child1";
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(
        WorkflowDirectedGraph.Gateway.EVENT_BASED,
        Arrays.asList(childId)
    );

    org.camunda.bpm.model.bpmn.builder.EventBasedGatewayBuilder eventGatewayBuilder =
        mock(org.camunda.bpm.model.bpmn.builder.EventBasedGatewayBuilder.class);

    when(workflowNode.getId()).thenReturn(nodeId);
    when(workflowNode.getElementType()).thenReturn(WorkflowNodeType.SIGNAL_EVENT);
    when(context.readChildren(nodeId)).thenReturn(children);
    when(flowNodeBuilder.eventBasedGateway()).thenReturn(eventGatewayBuilder);
    when(eventGatewayBuilder.id(nodeId + "_event_gateway")).thenReturn(eventGatewayBuilder);

    WorkflowNode childNode = mock(WorkflowNode.class);
    when(childNode.getId()).thenReturn(childId);
    when(childNode.getElementType()).thenReturn(WorkflowNodeType.SIGNAL_EVENT);
    when(childNode.isConditional()).thenReturn(true);
    when(childNode.isConditional(nodeId)).thenReturn(true);
    when(childNode.isNotExclusiveFormReply()).thenReturn(false);
    when(context.readWorkflowNode(childId)).thenReturn(childNode);
    when(context.isAlreadyBuilt(childId)).thenReturn(true); // Already built to skip recursion
    doReturn(eventGatewayBuilder).when(context).getNodeBuilder(nodeId);

    WorkflowNodeBpmnBuilder nodeBuilder = mock(WorkflowNodeBpmnBuilder.class);
    when(builderFactory.getBuilder(childNode)).thenReturn(nodeBuilder);
    doReturn(eventGatewayBuilder).when(nodeBuilder).connect(any(), any(), any(), any());

    // When
    invokeComputeChildren(workflowNode, flowNodeBuilder, context);

    // Then
    verify(eventGatewayBuilder, never()).endEvent();
  }

  private void invokeComputeChildren(WorkflowNode currentNode, AbstractFlowNodeBuilder<?, ?> builder,
      BuildProcessContext context) throws Exception {
    Method computeChildrenMethod = CamundaBpmnBuilder.class.getDeclaredMethod("computeChildren",
        WorkflowNode.class, AbstractFlowNodeBuilder.class, BuildProcessContext.class);
    computeChildrenMethod.setAccessible(true);
    computeChildrenMethod.invoke(camundaBpmnBuilder, currentNode, builder, context);
  }
}
