package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import javax.imageio.metadata.IIOMetadataNode;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.BoundaryEventBuilder;
import org.camunda.bpm.model.bpmn.builder.ComplexGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.impl.BpmnModelInstanceImpl;
import org.camunda.bpm.model.bpmn.impl.instance.BoundaryEventImpl;
import org.camunda.bpm.model.bpmn.impl.instance.ProcessImpl;
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
import org.w3c.dom.Document;

@ContextConfiguration(classes = {ActivityCompleteNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class AbstractNodeBpmnBuilderDiffblueTest {
  @Autowired
  private AbstractNodeBpmnBuilder abstractNodeBpmnBuilder;

  /**
   * Test {@link AbstractNodeBpmnBuilder#connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext)}.
   * <p>
   * Method under test: {@link AbstractNodeBpmnBuilder#connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext)}
   */
  @Test
  @DisplayName("Test connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "AbstractFlowNodeBuilder AbstractNodeBpmnBuilder.connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext)"})
  void testConnect() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivityCompleteNodeBuilder activityCompleteNodeBuilder = new ActivityCompleteNodeBuilder();
    WorkflowNode element = mock(WorkflowNode.class);
    when(element.isConditional()).thenReturn(false);
    when(element.getId()).thenReturn("42");
    ComplexGatewayBuilder builder = mock(ComplexGatewayBuilder.class);
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode("foo"));
    ModelImpl model2 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");
    ModelInstanceImpl model3 = new ModelInstanceImpl(model2, modelBuilder2, new DomDocumentImpl(mock(Document.class)));

    ModelImpl model4 = new ModelImpl("Model Name");
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    when(builder.connectTo(Mockito.<String>any()))
        .thenReturn(new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(
            new ModelTypeInstanceContext(domElement, model3, new ModelElementTypeImpl(model4, "Name", instanceType)))));
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model5 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder3 = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance2 = new BpmnModelInstanceImpl(model5, modelBuilder3,
        new DomDocumentImpl(mock(Document.class)));

    BuildProcessContext context = new BuildProcessContext(workflowGraph,
        new ProcessBuilder(modelInstance2, new ProcessImpl(new ModelTypeInstanceContext(null, null, null))));
    ModelImpl model6 = new ModelImpl("if");
    ModelBuilderImpl modelBuilder4 = new ModelBuilderImpl("if");
    BpmnModelInstanceImpl modelInstance3 = new BpmnModelInstanceImpl(model6, modelBuilder4,
        new DomDocumentImpl(mock(Document.class)));

    DomElementImpl domElement2 = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model7 = new ModelInstanceImpl(null, null, null);

    Class<ModelElementInstance> instanceType2 = ModelElementInstance.class;
    context.addNodeBuilder("42", new BoundaryEventBuilder(modelInstance3, new BoundaryEventImpl(
        new ModelTypeInstanceContext(domElement2, model7, new ModelElementTypeImpl(null, "if", instanceType2)))));

    // Act
    AbstractFlowNodeBuilder<?, ?> actualConnectResult = activityCompleteNodeBuilder.connect(element, "42", builder,
        context);

    // Assert
    verify(element).getId();
    verify(element).isConditional();
    verify(builder).connectTo(eq("42"));
    assertSame(builder, actualConnectResult);
  }

  /**
   * Test {@link AbstractNodeBpmnBuilder#connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext)}.
   * <ul>
   *   <li>Then calls {@link WorkflowNode#getIfCondition(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractNodeBpmnBuilder#connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext)}
   */
  @Test
  @DisplayName("Test connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext); then calls getIfCondition(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "AbstractFlowNodeBuilder AbstractNodeBpmnBuilder.connect(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext)"})
  void testConnect_thenCallsGetIfCondition() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.
    //   Run dcover create --keep-partial-tests to gain insights into why
    //   a non-Spring test was created.

    // Arrange
    ActivityCompleteNodeBuilder activityCompleteNodeBuilder = new ActivityCompleteNodeBuilder();
    WorkflowNode element = mock(WorkflowNode.class);
    when(element.getIfCondition(Mockito.<String>any())).thenReturn("If Condition");
    when(element.isConditional()).thenReturn(true);
    when(element.getId()).thenReturn("42");
    ComplexGatewayBuilder complexGatewayBuilder = mock(ComplexGatewayBuilder.class);
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode("foo"));
    ModelImpl model2 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");
    ModelInstanceImpl model3 = new ModelInstanceImpl(model2, modelBuilder2, new DomDocumentImpl(mock(Document.class)));

    ModelImpl model4 = new ModelImpl("Model Name");
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    when(complexGatewayBuilder.connectTo(Mockito.<String>any()))
        .thenReturn(new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(
            new ModelTypeInstanceContext(domElement, model3, new ModelElementTypeImpl(model4, "Name", instanceType)))));
    ComplexGatewayBuilder builder = mock(ComplexGatewayBuilder.class);
    when(builder.condition(Mockito.<String>any(), Mockito.<String>any())).thenReturn(complexGatewayBuilder);
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model5 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder3 = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance2 = new BpmnModelInstanceImpl(model5, modelBuilder3,
        new DomDocumentImpl(mock(Document.class)));

    BuildProcessContext context = new BuildProcessContext(workflowGraph,
        new ProcessBuilder(modelInstance2, new ProcessImpl(new ModelTypeInstanceContext(null, null, null))));
    ModelImpl model6 = new ModelImpl("if");
    ModelBuilderImpl modelBuilder4 = new ModelBuilderImpl("if");
    BpmnModelInstanceImpl modelInstance3 = new BpmnModelInstanceImpl(model6, modelBuilder4,
        new DomDocumentImpl(mock(Document.class)));

    DomElementImpl domElement2 = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model7 = new ModelInstanceImpl(null, null, null);

    Class<ModelElementInstance> instanceType2 = ModelElementInstance.class;
    context.addNodeBuilder("42", new BoundaryEventBuilder(modelInstance3, new BoundaryEventImpl(
        new ModelTypeInstanceContext(domElement2, model7, new ModelElementTypeImpl(null, "if", instanceType2)))));

    // Act
    AbstractFlowNodeBuilder<?, ?> actualConnectResult = activityCompleteNodeBuilder.connect(element, "42", builder,
        context);

    // Assert
    verify(element).getId();
    verify(element).getIfCondition(eq("42"));
    verify(element).isConditional();
    verify(builder).condition(eq("if"), eq("If Condition"));
    verify(complexGatewayBuilder).connectTo(eq("42"));
    assertSame(builder, actualConnectResult);
  }

  /**
   * Test {@link AbstractNodeBpmnBuilder#connectToExistingNode(String, AbstractFlowNodeBuilder)}.
   * <ul>
   *   <li>Given {@link ModelImpl#ModelImpl(String)} with {@code Model Name}.</li>
   *   <li>Then calls {@link AbstractFlowNodeBuilder#connectTo(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractNodeBpmnBuilder#connectToExistingNode(String, AbstractFlowNodeBuilder)}
   */
  @Test
  @DisplayName("Test connectToExistingNode(String, AbstractFlowNodeBuilder); given ModelImpl(String) with 'Model Name'; then calls connectTo(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void AbstractNodeBpmnBuilder.connectToExistingNode(String, AbstractFlowNodeBuilder)"})
  void testConnectToExistingNode_givenModelImplWithModelName_thenCallsConnectTo() {
    // Arrange
    BoundaryEventBuilder builder = mock(BoundaryEventBuilder.class);
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode("foo"));
    ModelImpl model2 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");
    ModelInstanceImpl model3 = new ModelInstanceImpl(model2, modelBuilder2, new DomDocumentImpl(mock(Document.class)));

    ModelImpl model4 = new ModelImpl("Model Name");
    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    when(builder.connectTo(Mockito.<String>any()))
        .thenReturn(new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(
            new ModelTypeInstanceContext(domElement, model3, new ModelElementTypeImpl(model4, "Name", instanceType)))));

    // Act
    abstractNodeBpmnBuilder.connectToExistingNode("42", builder);

    // Assert
    verify(builder).connectTo(eq("42"));
  }
}
