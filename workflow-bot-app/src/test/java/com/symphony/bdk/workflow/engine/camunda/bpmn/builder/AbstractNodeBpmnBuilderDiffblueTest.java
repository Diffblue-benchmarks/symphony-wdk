package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import javax.imageio.metadata.IIOMetadataNode;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.BoundaryEventBuilder;
import org.camunda.bpm.model.bpmn.builder.ComplexGatewayBuilder;
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
  @Tag("MaintainedByDiffblue")
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
  @Tag("MaintainedByDiffblue")
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
  @Tag("MaintainedByDiffblue")
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
}
