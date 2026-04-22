package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder.EXCLUSIVE_GATEWAY_SUFFIX;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import javax.imageio.metadata.IIOMetadataNode;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.BoundaryEventBuilder;
import org.camunda.bpm.model.bpmn.builder.ComplexGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.ExclusiveGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.camunda.bpm.model.bpmn.impl.BpmnModelInstanceImpl;
import org.camunda.bpm.model.bpmn.impl.instance.BoundaryEventImpl;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ActivityCompleteNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class AbstractNodeBpmnBuilderDiffblueTest {
  @Autowired private AbstractNodeBpmnBuilder abstractNodeBpmnBuilder;

  /**
   * Test {@link AbstractNodeBpmnBuilder#connect(WorkflowNode, String, AbstractFlowNodeBuilder,
   * BuildProcessContext)}.
   *
   * <p>Method under test: {@link AbstractNodeBpmnBuilder#connect(WorkflowNode, String,
   * AbstractFlowNodeBuilder, BuildProcessContext)}
   */
  @Test
  @DisplayName("Test connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractFlowNodeBuilder AbstractNodeBpmnBuilder.connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext)"
  })
  void testConnect() {
    // Arrange
    WorkflowNode element = mock(WorkflowNode.class);
    when(element.isConditional()).thenReturn(false);
    when(element.getId()).thenReturn("42");

    ComplexGatewayBuilder builder = mock(ComplexGatewayBuilder.class);
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
    BoundaryEventBuilder boundaryEventBuilder =
        new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(context));
    when(builder.connectTo(Mockito.<String>any())).thenReturn(boundaryEventBuilder);

    BuildProcessContext context2 = mock(BuildProcessContext.class);
    when(context2.isAlreadyBuilt(Mockito.<String>any())).thenReturn(true);

    // Act
    AbstractFlowNodeBuilder<?, ?> actualConnectResult =
        abstractNodeBpmnBuilder.connect(element, "42", builder, context2);

    // Assert
    verify(element).getId();
    verify(element).isConditional();
    verify(context2).isAlreadyBuilt("42");
    verify(builder).connectTo("42");
    assertSame(builder, actualConnectResult);
  }

  /**
   * Test {@link AbstractNodeBpmnBuilder#connect(WorkflowNode, String, AbstractFlowNodeBuilder,
   * BuildProcessContext)}.
   *
   * <ul>
   *   <li>Then calls {@link WorkflowNode#getIfCondition(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractNodeBpmnBuilder#connect(WorkflowNode, String,
   * AbstractFlowNodeBuilder, BuildProcessContext)}
   */
  @Test
  @DisplayName(
      "Test connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext); then calls getIfCondition(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractFlowNodeBuilder AbstractNodeBpmnBuilder.connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext)"
  })
  void testConnect_thenCallsGetIfCondition() {
    // Arrange
    WorkflowNode element = mock(WorkflowNode.class);
    when(element.getIfCondition(Mockito.<String>any())).thenReturn("If Condition");
    when(element.isConditional()).thenReturn(true);
    when(element.getId()).thenReturn("42");

    ComplexGatewayBuilder complexGatewayBuilder = mock(ComplexGatewayBuilder.class);
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
    BoundaryEventBuilder boundaryEventBuilder =
        new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(context));
    when(complexGatewayBuilder.connectTo(Mockito.<String>any())).thenReturn(boundaryEventBuilder);

    ComplexGatewayBuilder builder = mock(ComplexGatewayBuilder.class);
    when(builder.condition(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(complexGatewayBuilder);

    BuildProcessContext context2 = mock(BuildProcessContext.class);
    when(context2.isAlreadyBuilt(Mockito.<String>any())).thenReturn(true);

    // Act
    AbstractFlowNodeBuilder<?, ?> actualConnectResult =
        abstractNodeBpmnBuilder.connect(element, "42", builder, context2);

    // Assert
    verify(element).getId();
    verify(element).getIfCondition("42");
    verify(element).isConditional();
    verify(context2).isAlreadyBuilt("42");
    verify(builder).condition("if", "If Condition");
    verify(complexGatewayBuilder).connectTo("42");
    assertSame(builder, actualConnectResult);
  }

  /**
   * Test {@link AbstractNodeBpmnBuilder#connectToExistingNode(String, AbstractFlowNodeBuilder)}.
   *
   * <ul>
   *   <li>Then calls {@link BoundaryEventBuilder#connectTo(String)}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractNodeBpmnBuilder#connectToExistingNode(String,
   * AbstractFlowNodeBuilder)}
   */
  @Test
  @DisplayName(
      "Test connectToExistingNode(String, AbstractFlowNodeBuilder); then calls connectTo(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AbstractNodeBpmnBuilder.connectToExistingNode(String, AbstractFlowNodeBuilder)"
  })
  void testConnectToExistingNode_thenCallsConnectTo() {
    // Arrange
    BoundaryEventBuilder builder = mock(BoundaryEventBuilder.class);
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
    BoundaryEventBuilder boundaryEventBuilder =
        new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(context));
    when(builder.connectTo(Mockito.<String>any())).thenReturn(boundaryEventBuilder);

    // Act
    abstractNodeBpmnBuilder.connectToExistingNode("42", builder);

    // Assert
    verify(builder).connectTo("42");
  }

  /**
   * Test {@link AbstractNodeBpmnBuilder#connectToExistingNode(String, AbstractFlowNodeBuilder)}.
   *
   * <ul>
   *   <li>Given builder is a SubProcessBuilder; then connectTo is NOT called.
   * </ul>
   *
   * <p>Method under test: {@link AbstractNodeBpmnBuilder#connectToExistingNode(String,
   * AbstractFlowNodeBuilder)}
   */
  @Test
  @DisplayName(
      "Test connectToExistingNode(String, AbstractFlowNodeBuilder); given SubProcessBuilder then connectTo is skipped")
  void testConnectToExistingNode_withSubProcessBuilder() {
    // Arrange
    SubProcessBuilder builder = mock(SubProcessBuilder.class);

    // Act
    abstractNodeBpmnBuilder.connectToExistingNode("nodeId", builder);

    // Assert
    verify(builder, never()).connectTo(anyString());
  }

  /**
   * Test {@link AbstractNodeBpmnBuilder#connect(WorkflowNode, String, AbstractFlowNodeBuilder,
   * BuildProcessContext)}.
   *
   * <ul>
   *   <li>When node is not already built; then build() is called.
   * </ul>
   */
  @Test
  @DisplayName(
      "Test connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext); when node not already built")
  void testConnect_notAlreadyBuilt() {
    // Arrange
    AbstractNodeBpmnBuilder underTest = new AbstractNodeBpmnBuilder() {
      @Override
      public AbstractFlowNodeBuilder<?, ?> build(WorkflowNode element, String parentId,
          AbstractFlowNodeBuilder<?, ?> builder, BuildProcessContext context) {
        return builder;
      }

      @Override
      public WorkflowNodeType type() {
        return WorkflowNodeType.ACTIVITY;
      }
    };

    WorkflowNode element = mock(WorkflowNode.class);
    when(element.getId()).thenReturn("nodeId");
    when(element.isConditional()).thenReturn(false);

    BoundaryEventBuilder builder = mock(BoundaryEventBuilder.class);

    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.isAlreadyBuilt("nodeId")).thenReturn(false);
    when(context.hasEventSubProcess()).thenReturn(false);

    // Act
    AbstractFlowNodeBuilder<?, ?> result = underTest.connect(element, "parentId", builder, context);

    // Assert
    verify(context).isAlreadyBuilt("nodeId");
    assertSame(builder, result);
  }

  /**
   * Test {@link AbstractNodeBpmnBuilder#connect(WorkflowNode, String, AbstractFlowNodeBuilder,
   * BuildProcessContext)}.
   *
   * <ul>
   *   <li>When not already built, builder is a gateway and element is conditional; then condition
   *       is set on the gateway.
   * </ul>
   */
  @Test
  @DisplayName(
      "Test connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext); when not already built with gateway and conditional")
  void testConnect_notAlreadyBuilt_gatewayConditional() {
    // Arrange
    AbstractNodeBpmnBuilder underTest = new AbstractNodeBpmnBuilder() {
      @Override
      public AbstractFlowNodeBuilder<?, ?> build(WorkflowNode element, String parentId,
          AbstractFlowNodeBuilder<?, ?> builder, BuildProcessContext context) {
        return builder;
      }

      @Override
      public WorkflowNodeType type() {
        return WorkflowNodeType.ACTIVITY;
      }
    };

    WorkflowNode element = mock(WorkflowNode.class);
    when(element.getId()).thenReturn("nodeId");
    when(element.isConditional()).thenReturn(true);
    when(element.getIfCondition("parentId")).thenReturn("${condition}");

    ComplexGatewayBuilder builder = mock(ComplexGatewayBuilder.class);
    when(builder.condition(Mockito.<String>any(), Mockito.<String>any())).thenReturn(builder);

    BuildProcessContext context = mock(BuildProcessContext.class);
    when(context.isAlreadyBuilt("nodeId")).thenReturn(false);
    when(context.hasEventSubProcess()).thenReturn(false);

    // Act
    underTest.connect(element, "parentId", builder, context);

    // Assert
    verify(builder).condition("if", "${condition}");
  }

  /**
   * Test {@link AbstractNodeBpmnBuilder#connect(WorkflowNode, String, AbstractFlowNodeBuilder,
   * BuildProcessContext)}.
   *
   * <ul>
   *   <li>When already built, conditional, and builder is not a gateway; then exclusive gateway
   *       is created for the conditional connection.
   * </ul>
   */
  @Test
  @DisplayName(
      "Test connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext); doConditionalConnection non-gateway path")
  @SuppressWarnings("unchecked")
  void testConnect_alreadyBuilt_conditional_nonGateway() {
    // Arrange
    WorkflowNode element = mock(WorkflowNode.class);
    when(element.getId()).thenReturn("42");
    when(element.isConditional()).thenReturn(true);
    when(element.getIfCondition(Mockito.<String>any())).thenReturn("${cond}");

    BoundaryEventBuilder builder = mock(BoundaryEventBuilder.class);
    ExclusiveGatewayBuilder exclusiveGwBuilder = mock(ExclusiveGatewayBuilder.class);
    AbstractFlowNodeBuilder<?, ?> connectToResult = mock(AbstractFlowNodeBuilder.class);
    AbstractFlowNodeBuilder<?, ?> moveToResult = mock(AbstractFlowNodeBuilder.class);

    when(builder.exclusiveGateway(anyString())).thenReturn(exclusiveGwBuilder);
    when(exclusiveGwBuilder.condition(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(exclusiveGwBuilder);
    when(exclusiveGwBuilder.connectTo(Mockito.<String>any())).thenReturn(connectToResult);
    when(connectToResult.moveToNode(Mockito.<String>any())).thenReturn(moveToResult);

    BuildProcessContext context2 = mock(BuildProcessContext.class);
    when(context2.isAlreadyBuilt(Mockito.<String>any())).thenReturn(true);
    when(context2.hasEventSubProcess()).thenReturn(false);

    // Act
    AbstractFlowNodeBuilder<?, ?> result =
        abstractNodeBpmnBuilder.connect(element, "parentId", builder, context2);

    // Assert
    verify(builder).exclusiveGateway("42" + EXCLUSIVE_GATEWAY_SUFFIX);
    verify(exclusiveGwBuilder).condition("if", "${cond}");
    verify(exclusiveGwBuilder).connectTo("42");
    verify(connectToResult).moveToNode("42" + EXCLUSIVE_GATEWAY_SUFFIX);
    verify(moveToResult).endEvent();
    assertSame(builder, result);
  }

  /**
   * Test {@link AbstractNodeBpmnBuilder#connect(WorkflowNode, String, AbstractFlowNodeBuilder,
   * BuildProcessContext)}.
   *
   * <ul>
   *   <li>When already built, conditional, non-gateway builder, and has event sub process; then
   *       event sub process is ended before the conditional connection.
   * </ul>
   */
  @Test
  @DisplayName(
      "Test connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext); doConditionalConnection non-gateway with event sub process")
  @SuppressWarnings("unchecked")
  void testConnect_alreadyBuilt_conditional_nonGateway_withEventSubProcess() {
    // Arrange
    WorkflowNode element = mock(WorkflowNode.class);
    when(element.getId()).thenReturn("42");
    when(element.isConditional()).thenReturn(true);
    when(element.getIfCondition(Mockito.<String>any())).thenReturn("${cond}");

    BoundaryEventBuilder originalBuilder = mock(BoundaryEventBuilder.class);

    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);
    SubProcessBuilder subProcessDoneBuilder = mock(SubProcessBuilder.class);
    ExclusiveGatewayBuilder exclusiveGwBuilder = mock(ExclusiveGatewayBuilder.class);
    AbstractFlowNodeBuilder<?, ?> connectToResult = mock(AbstractFlowNodeBuilder.class);
    AbstractFlowNodeBuilder<?, ?> moveToResult = mock(AbstractFlowNodeBuilder.class);

    when(subProcessDoneBuilder.exclusiveGateway(anyString())).thenReturn(exclusiveGwBuilder);
    when(exclusiveGwBuilder.condition(Mockito.<String>any(), Mockito.<String>any()))
        .thenReturn(exclusiveGwBuilder);
    when(exclusiveGwBuilder.connectTo(Mockito.<String>any())).thenReturn(connectToResult);
    when(connectToResult.moveToNode(Mockito.<String>any())).thenReturn(moveToResult);

    BuildProcessContext context2 = mock(BuildProcessContext.class);
    when(context2.isAlreadyBuilt(Mockito.<String>any())).thenReturn(true);
    when(context2.hasEventSubProcess()).thenReturn(true);
    when(context2.removeLastEventSubProcessBuilder()).thenReturn(eventSubProcessBuilder);
    when(eventSubProcessBuilder.subProcessDone()).thenReturn(subProcessDoneBuilder);

    // Act
    abstractNodeBpmnBuilder.connect(element, "parentId", originalBuilder, context2);

    // Assert
    verify(context2).removeLastEventSubProcessBuilder();
    verify(context2).cacheSubProcessTimeoutToDone(subProcessDoneBuilder);
    verify(subProcessDoneBuilder).exclusiveGateway("42" + EXCLUSIVE_GATEWAY_SUFFIX);
    verify(exclusiveGwBuilder).condition("if", "${cond}");
  }
}
