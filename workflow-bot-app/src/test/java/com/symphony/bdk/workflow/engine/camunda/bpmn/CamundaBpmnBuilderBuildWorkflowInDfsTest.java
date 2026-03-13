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
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CamundaBpmnBuilderBuildWorkflowInDfsTest {

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
  private AbstractFlowNodeBuilder<?, ?> parentFlowNodeBuilder;

  @Mock
  private AbstractFlowNodeBuilder<?, ?> childFlowNodeBuilder;

  @Mock
  private WorkflowNodeBpmnBuilder workflowNodeBpmnBuilder;

  private CamundaBpmnBuilder camundaBpmnBuilder;

  @BeforeEach
  void setUp() {
    camundaBpmnBuilder = new CamundaBpmnBuilder(repositoryService, builderFactory, sessionService,
        directedGraphService);
  }

  @Test
  void shouldHandleEmptyChildrenList() throws Exception {
    // Given
    WorkflowDirectedGraph.NodeChildren emptyChildren = new WorkflowDirectedGraph.NodeChildren(
        Collections.emptyList()
    );
    String parentNodeId = "parentNode";

    // When
    invokeBuildWorkflowInDfs(emptyChildren, parentNodeId, context);

    // Then
    verify(context, never()).readWorkflowNode(any());
    verify(context, never()).isAlreadyBuilt(any());
  }

  @Test
  void shouldProcessSingleChildNodeNotAlreadyBuilt() throws Exception {
    // Given
    String parentNodeId = "parentNode";
    String childNodeId = "childNode";
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList(childNodeId)
    );
    WorkflowNode childNode = mock(WorkflowNode.class);
    WorkflowDirectedGraph.NodeChildren childNodeChildren = new WorkflowDirectedGraph.NodeChildren();

    when(childNode.getId()).thenReturn(childNodeId);
    when(childNode.getElementType()).thenReturn(WorkflowNodeType.ACTIVITY);
    when(context.readWorkflowNode(childNodeId)).thenReturn(childNode);
    when(context.isAlreadyBuilt(childNodeId)).thenReturn(false);
    doReturn(parentFlowNodeBuilder).when(context).getNodeBuilder(parentNodeId);
    when(builderFactory.getBuilder(childNode)).thenReturn(workflowNodeBpmnBuilder);
    doReturn(childFlowNodeBuilder).when(workflowNodeBpmnBuilder).connect(childNode, parentNodeId, parentFlowNodeBuilder, context);
    when(context.readChildren(childNodeId)).thenReturn(childNodeChildren);
    when(childFlowNodeBuilder.endEvent()).thenReturn(mock(org.camunda.bpm.model.bpmn.builder.EndEventBuilder.class));

    // When
    invokeBuildWorkflowInDfs(children, parentNodeId, context);

    // Then
    verify(context).readWorkflowNode(childNodeId);
    verify(context).isAlreadyBuilt(childNodeId);
    verify(context).getNodeBuilder(parentNodeId);
    verify(builderFactory).getBuilder(childNode);
    verify(workflowNodeBpmnBuilder).connect(childNode, parentNodeId, parentFlowNodeBuilder, context);
    verify(context).readChildren(childNodeId);
  }

  @Test
  void shouldProcessSingleChildNodeAlreadyBuilt() throws Exception {
    // Given
    String parentNodeId = "parentNode";
    String childNodeId = "childNode";
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList(childNodeId)
    );
    WorkflowNode childNode = mock(WorkflowNode.class);

    when(childNode.getId()).thenReturn(childNodeId);
    when(context.readWorkflowNode(childNodeId)).thenReturn(childNode);
    when(context.isAlreadyBuilt(childNodeId)).thenReturn(true);
    doReturn(parentFlowNodeBuilder).when(context).getNodeBuilder(parentNodeId);
    when(builderFactory.getBuilder(childNode)).thenReturn(workflowNodeBpmnBuilder);
    doReturn(childFlowNodeBuilder).when(workflowNodeBpmnBuilder).connect(childNode, parentNodeId, parentFlowNodeBuilder, context);

    // When
    invokeBuildWorkflowInDfs(children, parentNodeId, context);

    // Then
    verify(context).readWorkflowNode(childNodeId);
    verify(context).isAlreadyBuilt(childNodeId);
    verify(context).getNodeBuilder(parentNodeId);
    verify(builderFactory).getBuilder(childNode);
    verify(workflowNodeBpmnBuilder).connect(childNode, parentNodeId, parentFlowNodeBuilder, context);
    verify(context, never()).readChildren(childNodeId);
  }

  @Test
  void shouldProcessMultipleChildNodes() throws Exception {
    // Given
    String parentNodeId = "parentNode";
    String childNodeId1 = "childNode1";
    String childNodeId2 = "childNode2";
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList(childNodeId1, childNodeId2)
    );
    WorkflowNode childNode1 = mock(WorkflowNode.class);
    WorkflowNode childNode2 = mock(WorkflowNode.class);

    when(childNode1.getId()).thenReturn(childNodeId1);
    when(childNode2.getId()).thenReturn(childNodeId2);
    when(context.readWorkflowNode(childNodeId1)).thenReturn(childNode1);
    when(context.readWorkflowNode(childNodeId2)).thenReturn(childNode2);
    when(context.isAlreadyBuilt(childNodeId1)).thenReturn(true);
    when(context.isAlreadyBuilt(childNodeId2)).thenReturn(true);
    doReturn(parentFlowNodeBuilder).when(context).getNodeBuilder(parentNodeId);
    when(builderFactory.getBuilder(childNode1)).thenReturn(workflowNodeBpmnBuilder);
    when(builderFactory.getBuilder(childNode2)).thenReturn(workflowNodeBpmnBuilder);
    doReturn(childFlowNodeBuilder).when(workflowNodeBpmnBuilder).connect(any(), eq(parentNodeId), eq(parentFlowNodeBuilder), eq(context));

    // When
    invokeBuildWorkflowInDfs(children, parentNodeId, context);

    // Then
    verify(context).readWorkflowNode(childNodeId1);
    verify(context).readWorkflowNode(childNodeId2);
    verify(context).isAlreadyBuilt(childNodeId1);
    verify(context).isAlreadyBuilt(childNodeId2);
    verify(context, times(2)).getNodeBuilder(parentNodeId);
    verify(builderFactory).getBuilder(childNode1);
    verify(builderFactory).getBuilder(childNode2);
  }

  @Test
  void shouldProcessChildNodeWithParallelGatewayChildren() throws Exception {
    // Given
    String parentNodeId = "parentNode";
    String childNodeId = "childNode";
    String grandChildId = "grandChild";
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList(childNodeId)
    );
    WorkflowDirectedGraph.NodeChildren grandChildren = new WorkflowDirectedGraph.NodeChildren(
        WorkflowDirectedGraph.Gateway.PARALLEL,
        Arrays.asList(grandChildId)
    );
    WorkflowNode childNode = mock(WorkflowNode.class);
    WorkflowNode grandChildNode = mock(WorkflowNode.class);
    ParallelGatewayBuilder parallelGatewayBuilder = mock(ParallelGatewayBuilder.class);

    when(childNode.getId()).thenReturn(childNodeId);
    when(childNode.getElementType()).thenReturn(WorkflowNodeType.ACTIVITY);
    when(grandChildNode.getId()).thenReturn(grandChildId);
    when(context.readWorkflowNode(childNodeId)).thenReturn(childNode);
    when(context.readWorkflowNode(grandChildId)).thenReturn(grandChildNode);
    when(context.isAlreadyBuilt(childNodeId)).thenReturn(false);
    when(context.isAlreadyBuilt(grandChildId)).thenReturn(true);
    doReturn(parentFlowNodeBuilder).when(context).getNodeBuilder(parentNodeId);
    doReturn(parallelGatewayBuilder).when(context).getNodeBuilder(childNodeId);
    when(builderFactory.getBuilder(childNode)).thenReturn(workflowNodeBpmnBuilder);
    when(builderFactory.getBuilder(grandChildNode)).thenReturn(workflowNodeBpmnBuilder);
    doReturn(childFlowNodeBuilder).when(workflowNodeBpmnBuilder).connect(eq(childNode), eq(parentNodeId), eq(parentFlowNodeBuilder), eq(context));
    doReturn(parallelGatewayBuilder).when(workflowNodeBpmnBuilder).connect(eq(grandChildNode), eq(childNodeId), eq(parallelGatewayBuilder), eq(context));
    when(context.readChildren(childNodeId)).thenReturn(grandChildren);
    when(childFlowNodeBuilder.parallelGateway(childNodeId + "_fork_gateway")).thenReturn(parallelGatewayBuilder);

    // When
    invokeBuildWorkflowInDfs(children, parentNodeId, context);

    // Then
    verify(context).readWorkflowNode(childNodeId);
    verify(context, times(2)).readWorkflowNode(grandChildId);
    verify(context).isAlreadyBuilt(childNodeId);
    verify(context).isAlreadyBuilt(grandChildId);
    verify(builderFactory).getBuilder(childNode);
    verify(builderFactory).getBuilder(grandChildNode);
  }

  @Test
  void shouldHandleEmptyParentNodeId() throws Exception {
    // Given
    String parentNodeId = "";
    String childNodeId = "childNode";
    WorkflowDirectedGraph.NodeChildren children = new WorkflowDirectedGraph.NodeChildren(
        Arrays.asList(childNodeId)
    );
    WorkflowNode childNode = mock(WorkflowNode.class);

    when(childNode.getId()).thenReturn(childNodeId);
    when(context.readWorkflowNode(childNodeId)).thenReturn(childNode);
    when(context.isAlreadyBuilt(childNodeId)).thenReturn(true);
    doReturn(parentFlowNodeBuilder).when(context).getNodeBuilder(parentNodeId);
    when(builderFactory.getBuilder(childNode)).thenReturn(workflowNodeBpmnBuilder);
    doReturn(childFlowNodeBuilder).when(workflowNodeBpmnBuilder).connect(childNode, parentNodeId, parentFlowNodeBuilder, context);

    // When
    invokeBuildWorkflowInDfs(children, parentNodeId, context);

    // Then
    verify(context).readWorkflowNode(childNodeId);
    verify(context).getNodeBuilder("");
    verify(builderFactory).getBuilder(childNode);
  }

  private void invokeBuildWorkflowInDfs(WorkflowDirectedGraph.NodeChildren nodes, String parentNodeId,
      BuildProcessContext context) throws Exception {
    Method buildWorkflowInDfsMethod = CamundaBpmnBuilder.class.getDeclaredMethod("buildWorkflowInDfs",
        WorkflowDirectedGraph.NodeChildren.class, String.class, BuildProcessContext.class);
    buildWorkflowInDfsMethod.setAccessible(true);
    buildWorkflowInDfsMethod.invoke(camundaBpmnBuilder, nodes, parentNodeId, context);
  }
}
