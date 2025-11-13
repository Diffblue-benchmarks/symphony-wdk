package com.symphony.bdk.workflow.engine.camunda.bpmn;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph.NodeChildren;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import java.util.ArrayList;
import javax.imageio.metadata.IIOMetadataNode;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.BoundaryEventBuilder;
import org.camunda.bpm.model.bpmn.builder.ComplexGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.EndEventBuilder;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.camunda.bpm.model.bpmn.impl.BpmnModelInstanceImpl;
import org.camunda.bpm.model.bpmn.impl.instance.BoundaryEventImpl;
import org.camunda.bpm.model.bpmn.impl.instance.ComplexGatewayImpl;
import org.camunda.bpm.model.bpmn.impl.instance.EndEventImpl;
import org.camunda.bpm.model.bpmn.impl.instance.ProcessImpl;
import org.camunda.bpm.model.bpmn.impl.instance.SubProcessImpl;
import org.camunda.bpm.model.xml.impl.ModelBuilderImpl;
import org.camunda.bpm.model.xml.impl.ModelImpl;
import org.camunda.bpm.model.xml.impl.ModelInstanceImpl;
import org.camunda.bpm.model.xml.impl.instance.DomDocumentImpl;
import org.camunda.bpm.model.xml.impl.instance.DomElementImpl;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BpmnBuilderHelperDiffblueTest {
  /**
   * Test {@link BpmnBuilderHelper#endEventSubProcess(BuildProcessContext,
   * AbstractFlowNodeBuilder)}.
   *
   * <ul>
   *   <li>Then return {@link SubProcessBuilder}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#endEventSubProcess(BuildProcessContext,
   * AbstractFlowNodeBuilder)}
   */
  @Test
  @DisplayName(
      "Test endEventSubProcess(BuildProcessContext, AbstractFlowNodeBuilder); then return SubProcessBuilder")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractFlowNodeBuilder BpmnBuilderHelper.endEventSubProcess(BuildProcessContext, AbstractFlowNodeBuilder)"
  })
  void testEndEventSubProcess_thenReturnSubProcessBuilder() {
    // Arrange
    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelImpl model2 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");

    ModelInstanceImpl model3 =
        new ModelInstanceImpl(model2, modelBuilder2, new DomDocumentImpl(null));
    ModelImpl model4 = new ModelImpl("Model Name");
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;

    ModelElementTypeImpl modelType = new ModelElementTypeImpl(model4, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model3, modelType);
    SubProcessImpl element = new SubProcessImpl(context);

    SubProcessBuilder subProcessBuilder = new SubProcessBuilder(modelInstance, element);
    when(eventSubProcessBuilder.subProcessDone()).thenReturn(subProcessBuilder);

    BuildProcessContext context2 = mock(BuildProcessContext.class);
    doNothing().when(context2).cacheSubProcessTimeoutToDone(Mockito.<SubProcessBuilder>any());
    when(context2.removeLastEventSubProcessBuilder()).thenReturn(eventSubProcessBuilder);

    BoundaryEventBuilder builder = mock(BoundaryEventBuilder.class);
    ModelImpl model5 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder3 = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance2 =
        new BpmnModelInstanceImpl(model5, modelBuilder3, new DomDocumentImpl(null));
    DomElementImpl domElement2 = new DomElementImpl(new IIOMetadataNode());
    ModelImpl model6 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder4 = new ModelBuilderImpl("Model Name");

    ModelInstanceImpl model7 =
        new ModelInstanceImpl(model6, modelBuilder4, new DomDocumentImpl(null));
    ModelImpl model8 = new ModelImpl("Model Name");
    Class<ModelElementInstance> instanceType2 = ModelElementInstance.class;

    ModelElementTypeImpl modelType2 = new ModelElementTypeImpl(model8, "Name", instanceType2);

    ModelTypeInstanceContext context3 =
        new ModelTypeInstanceContext(domElement2, model7, modelType2);
    EndEventBuilder endEventBuilder =
        new EndEventBuilder(modelInstance2, new EndEventImpl(context3));
    when(builder.endEvent()).thenReturn(endEventBuilder);

    // Act
    AbstractFlowNodeBuilder<?, ?> actualEndEventSubProcessResult =
        BpmnBuilderHelper.endEventSubProcess(context2, builder);

    // Assert
    verify(context2).cacheSubProcessTimeoutToDone(isA(SubProcessBuilder.class));
    verify(context2).removeLastEventSubProcessBuilder();
    verify(eventSubProcessBuilder).subProcessDone();
    verify(builder).endEvent();
    assertTrue(actualEndEventSubProcessResult instanceof SubProcessBuilder);
    assertSame(element, actualEndEventSubProcessResult.getElement());
  }

  /**
   * Test {@link BpmnBuilderHelper#endEventSubProcess(BuildProcessContext,
   * AbstractFlowNodeBuilder)}.
   *
   * <ul>
   *   <li>Then throw {@link UnsupportedOperationException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#endEventSubProcess(BuildProcessContext,
   * AbstractFlowNodeBuilder)}
   */
  @Test
  @DisplayName(
      "Test endEventSubProcess(BuildProcessContext, AbstractFlowNodeBuilder); then throw UnsupportedOperationException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractFlowNodeBuilder BpmnBuilderHelper.endEventSubProcess(BuildProcessContext, AbstractFlowNodeBuilder)"
  })
  void testEndEventSubProcess_thenThrowUnsupportedOperationException() {
    // Arrange
    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelImpl model2 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");

    ModelInstanceImpl model3 =
        new ModelInstanceImpl(model2, modelBuilder2, new DomDocumentImpl(null));
    ModelImpl model4 = new ModelImpl("Model Name");
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;

    ModelElementTypeImpl modelType = new ModelElementTypeImpl(model4, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model3, modelType);
    SubProcessBuilder subProcessBuilder =
        new SubProcessBuilder(modelInstance, new SubProcessImpl(context));
    when(eventSubProcessBuilder.subProcessDone()).thenReturn(subProcessBuilder);

    BuildProcessContext context2 = mock(BuildProcessContext.class);
    doThrow(new UnsupportedOperationException())
        .when(context2)
        .cacheSubProcessTimeoutToDone(Mockito.<SubProcessBuilder>any());
    when(context2.removeLastEventSubProcessBuilder()).thenReturn(eventSubProcessBuilder);

    BoundaryEventBuilder builder = mock(BoundaryEventBuilder.class);
    ModelImpl model5 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder3 = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance2 =
        new BpmnModelInstanceImpl(model5, modelBuilder3, new DomDocumentImpl(null));
    DomElementImpl domElement2 = new DomElementImpl(new IIOMetadataNode());
    ModelImpl model6 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder4 = new ModelBuilderImpl("Model Name");

    ModelInstanceImpl model7 =
        new ModelInstanceImpl(model6, modelBuilder4, new DomDocumentImpl(null));
    ModelImpl model8 = new ModelImpl("Model Name");
    Class<ModelElementInstance> instanceType2 = ModelElementInstance.class;

    ModelElementTypeImpl modelType2 = new ModelElementTypeImpl(model8, "Name", instanceType2);

    ModelTypeInstanceContext context3 =
        new ModelTypeInstanceContext(domElement2, model7, modelType2);
    EndEventBuilder endEventBuilder =
        new EndEventBuilder(modelInstance2, new EndEventImpl(context3));
    when(builder.endEvent()).thenReturn(endEventBuilder);

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> BpmnBuilderHelper.endEventSubProcess(context2, builder));
    verify(context2).cacheSubProcessTimeoutToDone(isA(SubProcessBuilder.class));
    verify(context2).removeLastEventSubProcessBuilder();
    verify(eventSubProcessBuilder).subProcessDone();
    verify(builder).endEvent();
  }

  /**
   * Test {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext, NodeChildren)}.
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName("Test hasActivitiesOnly(BuildProcessContext, NodeChildren)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasActivitiesOnly(BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testHasActivitiesOnly() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = mock(WorkflowDirectedGraph.class);
    when(workflowGraph.readWorkflowNode(Mockito.<String>any())).thenReturn(new WorkflowNode());
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    ModelTypeInstanceContext context = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    BuildProcessContext context2 = new BuildProcessContext(workflowGraph, process);

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasActivitiesOnlyResult =
        BpmnBuilderHelper.hasActivitiesOnly(context2, currentNodeChildren);

    // Assert
    verify(workflowGraph).readWorkflowNode("Child");
    assertTrue(actualHasActivitiesOnlyResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext, NodeChildren)}.
   *
   * <ul>
   *   <li>Given {@code String}.
   *   <li>When {@link WorkflowDirectedGraph.NodeChildren#NodeChildren()} addChild {@code String}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName(
      "Test hasActivitiesOnly(BuildProcessContext, NodeChildren); given 'java.lang.String'; when NodeChildren() addChild 'java.lang.String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasActivitiesOnly(BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testHasActivitiesOnly_givenJavaLangString_whenNodeChildrenAddChildJavaLangString() {
    // Arrange
    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.readWorkflowNode(Mockito.<String>any())).thenReturn(new WorkflowNode());

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("java.lang.String");
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasActivitiesOnlyResult =
        BpmnBuilderHelper.hasActivitiesOnly(context, currentNodeChildren);

    // Assert
    verify(context, atLeast(1)).readWorkflowNode(Mockito.<String>any());
    assertTrue(actualHasActivitiesOnlyResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext, NodeChildren)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode} (default constructor) elementType {@link
   *       WorkflowNodeType#SIGNAL_EVENT}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName(
      "Test hasActivitiesOnly(BuildProcessContext, NodeChildren); given WorkflowNode (default constructor) elementType SIGNAL_EVENT")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasActivitiesOnly(BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testHasActivitiesOnly_givenWorkflowNodeElementTypeSignal_event() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.elementType(WorkflowNodeType.SIGNAL_EVENT);

    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasActivitiesOnlyResult =
        BpmnBuilderHelper.hasActivitiesOnly(context, currentNodeChildren);

    // Assert
    verify(context).readWorkflowNode("Child");
    assertFalse(actualHasActivitiesOnlyResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext, NodeChildren)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode} {@link WorkflowNode#getElementType()} return {@link
   *       WorkflowNodeType#FORM_REPLIED_EVENT}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName(
      "Test hasActivitiesOnly(BuildProcessContext, NodeChildren); given WorkflowNode getElementType() return FORM_REPLIED_EVENT")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasActivitiesOnly(BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testHasActivitiesOnly_givenWorkflowNodeGetElementTypeReturnForm_replied_event() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    when(workflowNode.getElementType()).thenReturn(WorkflowNodeType.FORM_REPLIED_EVENT);

    WorkflowDirectedGraph workflowGraph = mock(WorkflowDirectedGraph.class);
    when(workflowGraph.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    ModelTypeInstanceContext context = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    BuildProcessContext context2 = new BuildProcessContext(workflowGraph, process);

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasActivitiesOnlyResult =
        BpmnBuilderHelper.hasActivitiesOnly(context2, currentNodeChildren);

    // Assert
    verify(workflowGraph).readWorkflowNode("Child");
    verify(workflowNode, atLeast(1)).getElementType();
    assertFalse(actualHasActivitiesOnlyResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext, NodeChildren)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode} {@link WorkflowNode#getElementType()} return {@link
   *       WorkflowNodeType#SIGNAL_EVENT}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName(
      "Test hasActivitiesOnly(BuildProcessContext, NodeChildren); given WorkflowNode getElementType() return SIGNAL_EVENT")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasActivitiesOnly(BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testHasActivitiesOnly_givenWorkflowNodeGetElementTypeReturnSignal_event() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    when(workflowNode.getElementType()).thenReturn(WorkflowNodeType.SIGNAL_EVENT);

    WorkflowDirectedGraph workflowGraph = mock(WorkflowDirectedGraph.class);
    when(workflowGraph.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    ModelTypeInstanceContext context = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    BuildProcessContext context2 = new BuildProcessContext(workflowGraph, process);

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasActivitiesOnlyResult =
        BpmnBuilderHelper.hasActivitiesOnly(context2, currentNodeChildren);

    // Assert
    verify(workflowGraph).readWorkflowNode("Child");
    verify(workflowNode).getElementType();
    assertFalse(actualHasActivitiesOnlyResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext, NodeChildren)}.
   *
   * <ul>
   *   <li>When {@link WorkflowDirectedGraph.NodeChildren#NodeChildren()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName(
      "Test hasActivitiesOnly(BuildProcessContext, NodeChildren); when NodeChildren(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasActivitiesOnly(BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testHasActivitiesOnly_whenNodeChildren_thenReturnTrue() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    ModelTypeInstanceContext context = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    BuildProcessContext context2 = new BuildProcessContext(workflowGraph, process);

    // Act and Assert
    assertTrue(BpmnBuilderHelper.hasActivitiesOnly(context2, new NodeChildren()));
  }

  /**
   * Test {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext, NodeChildren,
   * String)}.
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName("Test hasAllConditionalChildren(BuildProcessContext, NodeChildren, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasAllConditionalChildren(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasAllConditionalChildren() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = mock(WorkflowDirectedGraph.class);
    when(workflowGraph.readWorkflowNode(Mockito.<String>any())).thenReturn(new WorkflowNode());
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    ModelTypeInstanceContext context = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    BuildProcessContext context2 = new BuildProcessContext(workflowGraph, process);

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasAllConditionalChildrenResult =
        BpmnBuilderHelper.hasAllConditionalChildren(context2, currentNodeChildren, "42");

    // Assert
    verify(workflowGraph).readWorkflowNode("Child");
    assertFalse(actualHasAllConditionalChildrenResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext, NodeChildren,
   * String)}.
   *
   * <ul>
   *   <li>Given {@code String}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName(
      "Test hasAllConditionalChildren(BuildProcessContext, NodeChildren, String); given 'java.lang.String'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasAllConditionalChildren(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasAllConditionalChildren_givenJavaLangString() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.addIfCondition("42", "If Condition");

    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("java.lang.String");
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasAllConditionalChildrenResult =
        BpmnBuilderHelper.hasAllConditionalChildren(context, currentNodeChildren, "42");

    // Assert
    verify(context, atLeast(1)).readWorkflowNode(Mockito.<String>any());
    assertTrue(actualHasAllConditionalChildrenResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext, NodeChildren,
   * String)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode} (default constructor) addIfCondition {@code 42} and {@code If
   *       Condition}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName(
      "Test hasAllConditionalChildren(BuildProcessContext, NodeChildren, String); given WorkflowNode (default constructor) addIfCondition '42' and 'If Condition'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasAllConditionalChildren(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasAllConditionalChildren_givenWorkflowNodeAddIfCondition42AndIfCondition() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.addIfCondition("42", "If Condition");

    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasAllConditionalChildrenResult =
        BpmnBuilderHelper.hasAllConditionalChildren(context, currentNodeChildren, "42");

    // Assert
    verify(context).readWorkflowNode("Child");
    assertTrue(actualHasAllConditionalChildrenResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext, NodeChildren,
   * String)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode} (default constructor).
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName(
      "Test hasAllConditionalChildren(BuildProcessContext, NodeChildren, String); given WorkflowNode (default constructor); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasAllConditionalChildren(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasAllConditionalChildren_givenWorkflowNode_thenReturnFalse() {
    // Arrange
    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.readWorkflowNode(Mockito.<String>any())).thenReturn(new WorkflowNode());

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasAllConditionalChildrenResult =
        BpmnBuilderHelper.hasAllConditionalChildren(context, currentNodeChildren, "42");

    // Assert
    verify(context).readWorkflowNode("Child");
    assertFalse(actualHasAllConditionalChildrenResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext, NodeChildren,
   * String)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowNode#isConditional(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName(
      "Test hasAllConditionalChildren(BuildProcessContext, NodeChildren, String); then calls isConditional(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasAllConditionalChildren(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasAllConditionalChildren_thenCallsIsConditional() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    when(workflowNode.isConditional(Mockito.<String>any())).thenReturn(true);

    WorkflowDirectedGraph workflowGraph = mock(WorkflowDirectedGraph.class);
    when(workflowGraph.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    ModelTypeInstanceContext context = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    BuildProcessContext context2 = new BuildProcessContext(workflowGraph, process);

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasAllConditionalChildrenResult =
        BpmnBuilderHelper.hasAllConditionalChildren(context2, currentNodeChildren, "42");

    // Assert
    verify(workflowGraph).readWorkflowNode("Child");
    verify(workflowNode).isConditional("42");
    assertTrue(actualHasAllConditionalChildrenResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext, NodeChildren,
   * String)}.
   *
   * <ul>
   *   <li>When {@link WorkflowDirectedGraph.NodeChildren#NodeChildren()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName(
      "Test hasAllConditionalChildren(BuildProcessContext, NodeChildren, String); when NodeChildren(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasAllConditionalChildren(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasAllConditionalChildren_whenNodeChildren_thenReturnTrue() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    ModelTypeInstanceContext context = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    BuildProcessContext context2 = new BuildProcessContext(workflowGraph, process);

    // Act
    boolean actualHasAllConditionalChildrenResult =
        BpmnBuilderHelper.hasAllConditionalChildren(context2, new NodeChildren(), "42");

    // Assert
    assertTrue(actualHasAllConditionalChildrenResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext, NodeChildren,
   * String)}.
   *
   * <ul>
   *   <li>When {@code Parent Id}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName(
      "Test hasAllConditionalChildren(BuildProcessContext, NodeChildren, String); when 'Parent Id'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasAllConditionalChildren(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasAllConditionalChildren_whenParentId_thenReturnFalse() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.addIfCondition("42", "If Condition");

    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasAllConditionalChildrenResult =
        BpmnBuilderHelper.hasAllConditionalChildren(context, currentNodeChildren, "Parent Id");

    // Assert
    verify(context).readWorkflowNode("Child");
    assertFalse(actualHasAllConditionalChildrenResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext, NodeChildren, String)}.
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName("Test hasConditionalString(BuildProcessContext, NodeChildren, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasConditionalString(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasConditionalString() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = mock(WorkflowDirectedGraph.class);
    when(workflowGraph.readWorkflowNode(Mockito.<String>any())).thenReturn(new WorkflowNode());
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    ModelTypeInstanceContext context = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    BuildProcessContext context2 = new BuildProcessContext(workflowGraph, process);

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasConditionalStringResult =
        BpmnBuilderHelper.hasConditionalString(context2, currentNodeChildren, "42");

    // Assert
    verify(workflowGraph).readWorkflowNode("Child");
    assertFalse(actualHasConditionalStringResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext, NodeChildren, String)}.
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName("Test hasConditionalString(BuildProcessContext, NodeChildren, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasConditionalString(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasConditionalString2() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.addIfCondition("com.symphony.bdk.workflow.engine.WorkflowNode", "42");

    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("42");
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasConditionalStringResult =
        BpmnBuilderHelper.hasConditionalString(context, currentNodeChildren, "42");

    // Assert
    verify(context, atLeast(1)).readWorkflowNode(Mockito.<String>any());
    assertFalse(actualHasConditionalStringResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext, NodeChildren, String)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link WorkflowDirectedGraph.NodeChildren#NodeChildren()} addChild {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName(
      "Test hasConditionalString(BuildProcessContext, NodeChildren, String); given '42'; when NodeChildren() addChild '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasConditionalString(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasConditionalString_given42_whenNodeChildrenAddChild42() {
    // Arrange
    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.readWorkflowNode(Mockito.<String>any())).thenReturn(new WorkflowNode());

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("42");
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasConditionalStringResult =
        BpmnBuilderHelper.hasConditionalString(context, currentNodeChildren, "42");

    // Assert
    verify(context, atLeast(1)).readWorkflowNode(Mockito.<String>any());
    assertFalse(actualHasConditionalStringResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext, NodeChildren, String)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode} (default constructor) addIfCondition {@code 42} and {@code If
   *       Condition}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName(
      "Test hasConditionalString(BuildProcessContext, NodeChildren, String); given WorkflowNode (default constructor) addIfCondition '42' and 'If Condition'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasConditionalString(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasConditionalString_givenWorkflowNodeAddIfCondition42AndIfCondition() {
    // Arrange
    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.addIfCondition("42", "If Condition");

    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasConditionalStringResult =
        BpmnBuilderHelper.hasConditionalString(context, currentNodeChildren, "42");

    // Assert
    verify(context).readWorkflowNode("Child");
    assertTrue(actualHasConditionalStringResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext, NodeChildren, String)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode} (default constructor).
   *   <li>Then calls {@link BuildProcessContext#readWorkflowNode(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName(
      "Test hasConditionalString(BuildProcessContext, NodeChildren, String); given WorkflowNode (default constructor); then calls readWorkflowNode(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasConditionalString(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasConditionalString_givenWorkflowNode_thenCallsReadWorkflowNode() {
    // Arrange
    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.readWorkflowNode(Mockito.<String>any())).thenReturn(new WorkflowNode());

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasConditionalStringResult =
        BpmnBuilderHelper.hasConditionalString(context, currentNodeChildren, "42");

    // Assert
    verify(context).readWorkflowNode("Child");
    assertFalse(actualHasConditionalStringResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext, NodeChildren, String)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowNode#isConditional(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName(
      "Test hasConditionalString(BuildProcessContext, NodeChildren, String); then calls isConditional(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasConditionalString(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasConditionalString_thenCallsIsConditional() {
    // Arrange
    WorkflowNode workflowNode = mock(WorkflowNode.class);
    when(workflowNode.isConditional(Mockito.<String>any())).thenReturn(true);

    WorkflowDirectedGraph workflowGraph = mock(WorkflowDirectedGraph.class);
    when(workflowGraph.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    ModelTypeInstanceContext context = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    BuildProcessContext context2 = new BuildProcessContext(workflowGraph, process);

    NodeChildren currentNodeChildren = new NodeChildren();
    currentNodeChildren.addChild("Child");

    // Act
    boolean actualHasConditionalStringResult =
        BpmnBuilderHelper.hasConditionalString(context2, currentNodeChildren, "42");

    // Assert
    verify(workflowGraph).readWorkflowNode("Child");
    verify(workflowNode).isConditional("42");
    assertTrue(actualHasConditionalStringResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext, NodeChildren, String)}.
   *
   * <ul>
   *   <li>When {@link WorkflowDirectedGraph.NodeChildren#NodeChildren()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName(
      "Test hasConditionalString(BuildProcessContext, NodeChildren, String); when NodeChildren(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasConditionalString(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)"
  })
  void testHasConditionalString_whenNodeChildren_thenReturnFalse() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    ModelTypeInstanceContext context = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    BuildProcessContext context2 = new BuildProcessContext(workflowGraph, process);

    // Act
    boolean actualHasConditionalStringResult =
        BpmnBuilderHelper.hasConditionalString(context2, new NodeChildren(), "42");

    // Assert
    assertFalse(actualHasConditionalStringResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext,
   * NodeChildren)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder,
   * BuildProcessContext, WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName(
      "Test isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, NodeChildren); given 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testIsConditionalLoop_givenFalse() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model2, modelType);
    BoundaryEventBuilder builder =
        new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(context));

    BuildProcessContext context2 = mock(BuildProcessContext.class);
    when(context2.isAlreadyBuilt(Mockito.<String>any())).thenReturn(false);

    ArrayList<String> children = new ArrayList<>();
    children.add("Children");

    // Act
    boolean actualIsConditionalLoopResult =
        BpmnBuilderHelper.isConditionalLoop(builder, context2, new NodeChildren(children));

    // Assert
    verify(context2).isAlreadyBuilt("Children");
    assertFalse(actualIsConditionalLoopResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext,
   * NodeChildren)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder,
   * BuildProcessContext, WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName(
      "Test isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, NodeChildren); given WorkflowNode (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testIsConditionalLoop_givenWorkflowNode() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model2, modelType);
    BoundaryEventBuilder builder =
        new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(context));

    BuildProcessContext context2 = mock(BuildProcessContext.class);
    when(context2.readWorkflowNode(Mockito.<String>any())).thenReturn(new WorkflowNode());
    when(context2.isAlreadyBuilt(Mockito.<String>any())).thenReturn(true);

    ArrayList<String> children = new ArrayList<>();
    children.add("Children");

    // Act
    boolean actualIsConditionalLoopResult =
        BpmnBuilderHelper.isConditionalLoop(builder, context2, new NodeChildren(children));

    // Assert
    verify(context2).isAlreadyBuilt("Children");
    verify(context2).readWorkflowNode("Children");
    assertFalse(actualIsConditionalLoopResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext,
   * NodeChildren)}.
   *
   * <ul>
   *   <li>Given {@link WorkflowNode} (default constructor) addIfCondition {@code 42} and {@code If
   *       Condition}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder,
   * BuildProcessContext, WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName(
      "Test isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, NodeChildren); given WorkflowNode (default constructor) addIfCondition '42' and 'If Condition'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testIsConditionalLoop_givenWorkflowNodeAddIfCondition42AndIfCondition() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model2, modelType);
    BoundaryEventBuilder builder =
        new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(context));

    WorkflowNode workflowNode = new WorkflowNode();
    workflowNode.addIfCondition("42", "If Condition");

    BuildProcessContext context2 = mock(BuildProcessContext.class);
    when(context2.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);
    when(context2.isAlreadyBuilt(Mockito.<String>any())).thenReturn(true);

    ArrayList<String> children = new ArrayList<>();
    children.add("Children");

    // Act
    boolean actualIsConditionalLoopResult =
        BpmnBuilderHelper.isConditionalLoop(builder, context2, new NodeChildren(children));

    // Assert
    verify(context2).isAlreadyBuilt("Children");
    verify(context2).readWorkflowNode("Children");
    assertFalse(actualIsConditionalLoopResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext,
   * NodeChildren)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowNode#addIfCondition(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder,
   * BuildProcessContext, WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName(
      "Test isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, NodeChildren); then calls addIfCondition(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testIsConditionalLoop_thenCallsAddIfCondition() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model2, modelType);
    BoundaryEventBuilder builder =
        new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(context));

    WorkflowNode workflowNode = mock(WorkflowNode.class);
    when(workflowNode.isConditional()).thenReturn(false);
    when(workflowNode.addIfCondition(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new WorkflowNode());
    workflowNode.addIfCondition("42", "If Condition");

    BuildProcessContext context2 = mock(BuildProcessContext.class);
    when(context2.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);
    when(context2.isAlreadyBuilt(Mockito.<String>any())).thenReturn(true);

    ArrayList<String> children = new ArrayList<>();
    children.add("Children");

    // Act
    boolean actualIsConditionalLoopResult =
        BpmnBuilderHelper.isConditionalLoop(builder, context2, new NodeChildren(children));

    // Assert
    verify(workflowNode).addIfCondition("42", "If Condition");
    verify(workflowNode).isConditional();
    verify(context2).isAlreadyBuilt("Children");
    verify(context2).readWorkflowNode("Children");
    assertFalse(actualIsConditionalLoopResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext,
   * NodeChildren)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder,
   * BuildProcessContext, WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName(
      "Test isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, NodeChildren); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testIsConditionalLoop_thenReturnTrue() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelImpl model2 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");

    ModelInstanceImpl model3 =
        new ModelInstanceImpl(model2, modelBuilder2, new DomDocumentImpl(null));
    ModelImpl model4 = new ModelImpl("Model Name");
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;

    ModelElementTypeImpl modelType = new ModelElementTypeImpl(model4, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model3, modelType);
    ComplexGatewayBuilder builder =
        new ComplexGatewayBuilder(modelInstance, new ComplexGatewayImpl(context));

    WorkflowNode workflowNode = mock(WorkflowNode.class);
    when(workflowNode.isConditional()).thenReturn(false);
    when(workflowNode.addIfCondition(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(new WorkflowNode());
    workflowNode.addIfCondition("42", "If Condition");

    BuildProcessContext context2 = mock(BuildProcessContext.class);
    when(context2.readWorkflowNode(Mockito.<String>any())).thenReturn(workflowNode);
    when(context2.isAlreadyBuilt(Mockito.<String>any())).thenReturn(true);

    ArrayList<String> children = new ArrayList<>();
    children.add("Children");

    // Act
    boolean actualIsConditionalLoopResult =
        BpmnBuilderHelper.isConditionalLoop(builder, context2, new NodeChildren(children));

    // Assert
    verify(workflowNode).addIfCondition("42", "If Condition");
    verify(workflowNode).isConditional();
    verify(context2).isAlreadyBuilt("Children");
    verify(context2).readWorkflowNode("Children");
    assertTrue(actualIsConditionalLoopResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext,
   * NodeChildren)}.
   *
   * <ul>
   *   <li>Then throw {@link UnsupportedOperationException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder,
   * BuildProcessContext, WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName(
      "Test isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, NodeChildren); then throw UnsupportedOperationException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testIsConditionalLoop_thenThrowUnsupportedOperationException() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model2, modelType);
    BoundaryEventBuilder builder =
        new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(context));

    BuildProcessContext context2 = mock(BuildProcessContext.class);
    when(context2.readWorkflowNode(Mockito.<String>any()))
        .thenThrow(new UnsupportedOperationException());
    when(context2.isAlreadyBuilt(Mockito.<String>any())).thenReturn(true);

    ArrayList<String> children = new ArrayList<>();
    children.add("Children");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () -> BpmnBuilderHelper.isConditionalLoop(builder, context2, new NodeChildren(children)));
    verify(context2).isAlreadyBuilt("Children");
    verify(context2).readWorkflowNode("Children");
  }

  /**
   * Test {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext,
   * NodeChildren)}.
   *
   * <ul>
   *   <li>When {@link WorkflowDirectedGraph.NodeChildren#NodeChildren()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder,
   * BuildProcessContext, WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName(
      "Test isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, NodeChildren); when NodeChildren(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testIsConditionalLoop_whenNodeChildren_thenReturnFalse() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model2, modelType);
    BoundaryEventBuilder builder =
        new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(context));
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model3 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance2 =
        new BpmnModelInstanceImpl(model3, modelBuilder2, new DomDocumentImpl(null));
    ModelTypeInstanceContext context2 = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance2, new ProcessImpl(context2));

    BuildProcessContext context3 = new BuildProcessContext(workflowGraph, process);

    // Act
    boolean actualIsConditionalLoopResult =
        BpmnBuilderHelper.isConditionalLoop(builder, context3, new NodeChildren());

    // Assert
    assertFalse(actualIsConditionalLoopResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext,
   * NodeChildren)}.
   *
   * <ul>
   *   <li>When {@link WorkflowDirectedGraph#WorkflowDirectedGraph(String)} with workflowId is
   *       {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder,
   * BuildProcessContext, WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName(
      "Test isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, NodeChildren); when WorkflowDirectedGraph(String) with workflowId is '42'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, WorkflowDirectedGraph.NodeChildren)"
  })
  void testIsConditionalLoop_whenWorkflowDirectedGraphWithWorkflowIdIs42() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model2, modelType);
    BoundaryEventBuilder builder =
        new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(context));
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model3 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance2 =
        new BpmnModelInstanceImpl(model3, modelBuilder2, new DomDocumentImpl(null));
    ModelTypeInstanceContext context2 = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance2, new ProcessImpl(context2));

    BuildProcessContext context3 = new BuildProcessContext(workflowGraph, process);

    ArrayList<String> children = new ArrayList<>();
    children.add("Children");

    // Act
    boolean actualIsConditionalLoopResult =
        BpmnBuilderHelper.isConditionalLoop(builder, context3, new NodeChildren(children));

    // Assert
    assertFalse(actualIsConditionalLoopResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext, NodeChildren,
   * WorkflowNodeType)}.
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)}
   */
  @Test
  @DisplayName("Test hasLoopAfterSubProcess(BuildProcessContext, NodeChildren, WorkflowNodeType)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasLoopAfterSubProcess(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)"
  })
  void testHasLoopAfterSubProcess() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    ModelTypeInstanceContext context = new ModelTypeInstanceContext(null, null, null);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    BuildProcessContext context2 = new BuildProcessContext(workflowGraph, process);

    // Act and Assert
    assertFalse(
        BpmnBuilderHelper.hasLoopAfterSubProcess(
            context2, new NodeChildren(), WorkflowNodeType.TIMER_FIRED_EVENT));
  }

  /**
   * Test {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext, NodeChildren,
   * WorkflowNodeType)}.
   *
   * <ul>
   *   <li>Given {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)}
   */
  @Test
  @DisplayName(
      "Test hasLoopAfterSubProcess(BuildProcessContext, NodeChildren, WorkflowNodeType); given 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasLoopAfterSubProcess(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)"
  })
  void testHasLoopAfterSubProcess_givenFalse() {
    // Arrange
    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.hasEventSubProcess()).thenReturn(false);

    // Act
    boolean actualHasLoopAfterSubProcessResult =
        BpmnBuilderHelper.hasLoopAfterSubProcess(
            context,
            new NodeChildren(new ArrayList<>()),
            WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Assert
    verify(context).hasEventSubProcess();
    assertFalse(actualHasLoopAfterSubProcessResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext, NodeChildren,
   * WorkflowNodeType)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)}
   */
  @Test
  @DisplayName(
      "Test hasLoopAfterSubProcess(BuildProcessContext, NodeChildren, WorkflowNodeType); given 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasLoopAfterSubProcess(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)"
  })
  void testHasLoopAfterSubProcess_givenTrue() {
    // Arrange
    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.hasEventSubProcess()).thenReturn(true);

    // Act
    boolean actualHasLoopAfterSubProcessResult =
        BpmnBuilderHelper.hasLoopAfterSubProcess(
            context,
            new NodeChildren(new ArrayList<>()),
            WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Assert
    verify(context).hasEventSubProcess();
    assertFalse(actualHasLoopAfterSubProcessResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext, NodeChildren,
   * WorkflowNodeType)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)}
   */
  @Test
  @DisplayName(
      "Test hasLoopAfterSubProcess(BuildProcessContext, NodeChildren, WorkflowNodeType); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasLoopAfterSubProcess(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)"
  })
  void testHasLoopAfterSubProcess_thenReturnTrue() {
    // Arrange
    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.isAlreadyBuilt(Mockito.<String>any())).thenReturn(true);
    when(context.hasEventSubProcess()).thenReturn(true);

    ArrayList<String> children = new ArrayList<>();
    children.add("foo");

    // Act
    boolean actualHasLoopAfterSubProcessResult =
        BpmnBuilderHelper.hasLoopAfterSubProcess(
            context, new NodeChildren(children), WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Assert
    verify(context).hasEventSubProcess();
    verify(context).isAlreadyBuilt("foo");
    assertTrue(actualHasLoopAfterSubProcessResult);
  }

  /**
   * Test {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext, NodeChildren,
   * WorkflowNodeType)}.
   *
   * <ul>
   *   <li>Then throw {@link UnsupportedOperationException}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)}
   */
  @Test
  @DisplayName(
      "Test hasLoopAfterSubProcess(BuildProcessContext, NodeChildren, WorkflowNodeType); then throw UnsupportedOperationException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasLoopAfterSubProcess(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)"
  })
  void testHasLoopAfterSubProcess_thenThrowUnsupportedOperationException() {
    // Arrange
    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.isAlreadyBuilt(Mockito.<String>any()))
        .thenThrow(new UnsupportedOperationException());
    when(context.hasEventSubProcess()).thenReturn(true);

    ArrayList<String> children = new ArrayList<>();
    children.add("foo");

    // Act and Assert
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            BpmnBuilderHelper.hasLoopAfterSubProcess(
                context, new NodeChildren(children), WorkflowNodeType.ACTIVITY_COMPLETED_EVENT));
    verify(context).hasEventSubProcess();
    verify(context).isAlreadyBuilt("foo");
  }

  /**
   * Test {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext, NodeChildren,
   * WorkflowNodeType)}.
   *
   * <ul>
   *   <li>When {@link DomElementImpl#DomElementImpl(Element)} with element is {@link
   *       IIOMetadataNode#IIOMetadataNode()}.
   * </ul>
   *
   * <p>Method under test: {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext,
   * WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)}
   */
  @Test
  @DisplayName(
      "Test hasLoopAfterSubProcess(BuildProcessContext, NodeChildren, WorkflowNodeType); when DomElementImpl(Element) with element is IIOMetadataNode()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BpmnBuilderHelper.hasLoopAfterSubProcess(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)"
  })
  void testHasLoopAfterSubProcess_whenDomElementImplWithElementIsIIOMetadataNode() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    BpmnModelInstanceImpl modelInstance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));
    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    ModelElementTypeImpl modelType = new ModelElementTypeImpl(null, "Name", instanceType);

    ModelTypeInstanceContext context = new ModelTypeInstanceContext(domElement, model2, modelType);
    ProcessBuilder process = new ProcessBuilder(modelInstance, new ProcessImpl(context));

    BuildProcessContext context2 = new BuildProcessContext(workflowGraph, process);

    // Act
    boolean actualHasLoopAfterSubProcessResult =
        BpmnBuilderHelper.hasLoopAfterSubProcess(
            context2, new NodeChildren(), WorkflowNodeType.ACTIVITY_COMPLETED_EVENT);

    // Assert
    assertFalse(actualHasLoopAfterSubProcessResult);
  }
}
