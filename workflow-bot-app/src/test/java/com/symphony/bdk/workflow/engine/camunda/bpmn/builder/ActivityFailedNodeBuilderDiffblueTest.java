package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import com.symphony.bdk.workflow.swadl.v1.activity.ExecuteScript;
import javax.imageio.metadata.IIOMetadataNode;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.BoundaryEventBuilder;
import org.camunda.bpm.model.bpmn.builder.ScriptTaskBuilder;
import org.camunda.bpm.model.bpmn.builder.ServiceTaskBuilder;
import org.camunda.bpm.model.bpmn.impl.BpmnModelInstanceImpl;
import org.camunda.bpm.model.bpmn.impl.instance.BoundaryEventImpl;
import org.camunda.bpm.model.bpmn.impl.instance.ScriptTaskImpl;
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

@ContextConfiguration(classes = {ActivityFailedNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class ActivityFailedNodeBuilderDiffblueTest {
  @Autowired private ActivityFailedNodeBuilder activityFailedNodeBuilder;

  /**
   * Test {@link ActivityFailedNodeBuilder#type()}.
   *
   * <p>Method under test: {@link ActivityFailedNodeBuilder#type()}
   */
  @Test
  @DisplayName("Test type()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodeType ActivityFailedNodeBuilder.type()"})
  void testType() {
    // Arrange, Act and Assert
    assertEquals(WorkflowNodeType.ACTIVITY_FAILED_EVENT, activityFailedNodeBuilder.type());
  }

  /**
   * Test {@link ActivityFailedNodeBuilder#connectToExistingNode(String, AbstractFlowNodeBuilder)}.
   *
   * <p>Method under test: {@link ActivityFailedNodeBuilder#connectToExistingNode(String,
   * AbstractFlowNodeBuilder)}
   */
  @Test
  @DisplayName("Test connectToExistingNode(String, AbstractFlowNodeBuilder)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ActivityFailedNodeBuilder.connectToExistingNode(String, AbstractFlowNodeBuilder)"
  })
  void testConnectToExistingNode() {
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
    BoundaryEventBuilder realBoundaryEventBuilder =
        new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(context));

    BoundaryEventBuilder errorBuilder = mock(BoundaryEventBuilder.class);
    when(errorBuilder.connectTo(Mockito.<String>any())).thenReturn(realBoundaryEventBuilder);

    BoundaryEventBuilder namedBuilder = mock(BoundaryEventBuilder.class);
    when(namedBuilder.error()).thenReturn(errorBuilder);

    BoundaryEventBuilder boundaryEventBuilder = mock(BoundaryEventBuilder.class);
    when(boundaryEventBuilder.name(Mockito.<String>any())).thenReturn(namedBuilder);

    ServiceTaskBuilder activityBuilder = mock(ServiceTaskBuilder.class);
    when(activityBuilder.boundaryEvent()).thenReturn(boundaryEventBuilder);

    // Act
    activityFailedNodeBuilder.connectToExistingNode("42", activityBuilder);

    // Assert
    verify(activityBuilder).boundaryEvent();
    verify(boundaryEventBuilder).name("error_42");
    verify(namedBuilder).error();
    verify(errorBuilder).connectTo("42");
  }

  /**
   * Test {@link ActivityFailedNodeBuilder#build(WorkflowNode, String, AbstractFlowNodeBuilder,
   * BuildProcessContext)}.
   *
   * <p>Method under test: {@link ActivityFailedNodeBuilder#build(WorkflowNode, String,
   * AbstractFlowNodeBuilder, BuildProcessContext)}
   */
  @Test
  @DisplayName("Test build(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractFlowNodeBuilder ActivityFailedNodeBuilder.build(WorkflowNode, String, AbstractFlowNodeBuilder, BuildProcessContext)"
  })
  void testBuild() {
    // Arrange
    ScriptTaskBuilder scriptTaskBuilder = mock(ScriptTaskBuilder.class);
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
    ScriptTaskBuilder realScriptTaskBuilder =
        new ScriptTaskBuilder(modelInstance, new ScriptTaskImpl(context));
    when(scriptTaskBuilder.camundaExecutionListenerClass(
            Mockito.<String>any(), Mockito.<Class<Object>>any()))
        .thenReturn(realScriptTaskBuilder);

    ScriptTaskBuilder scriptTaskBuilder2 = mock(ScriptTaskBuilder.class);
    when(scriptTaskBuilder2.scriptFormat(Mockito.<String>any())).thenReturn(scriptTaskBuilder);

    ScriptTaskBuilder scriptTaskBuilder3 = mock(ScriptTaskBuilder.class);
    when(scriptTaskBuilder3.scriptText(Mockito.<String>any())).thenReturn(scriptTaskBuilder2);

    ScriptTaskBuilder scriptTaskBuilder4 = mock(ScriptTaskBuilder.class);
    when(scriptTaskBuilder4.camundaAsyncAfter()).thenReturn(scriptTaskBuilder3);

    ScriptTaskBuilder scriptTaskBuilder5 = mock(ScriptTaskBuilder.class);
    when(scriptTaskBuilder5.name(Mockito.<String>any())).thenReturn(scriptTaskBuilder4);

    ScriptTaskBuilder scriptTaskBuilder6 = mock(ScriptTaskBuilder.class);
    when(scriptTaskBuilder6.id(Mockito.<String>any())).thenReturn(scriptTaskBuilder5);

    BoundaryEventBuilder errorBuilder = mock(BoundaryEventBuilder.class);
    when(errorBuilder.scriptTask()).thenReturn(scriptTaskBuilder6);

    BoundaryEventBuilder namedBuilder = mock(BoundaryEventBuilder.class);
    when(namedBuilder.error()).thenReturn(errorBuilder);

    BoundaryEventBuilder boundaryEventBuilder = mock(BoundaryEventBuilder.class);
    when(boundaryEventBuilder.name(Mockito.<String>any())).thenReturn(namedBuilder);

    ServiceTaskBuilder activityBuilder = mock(ServiceTaskBuilder.class);
    when(activityBuilder.boundaryEvent()).thenReturn(boundaryEventBuilder);

    WorkflowNode element = mock(WorkflowNode.class);
    when(element.getId()).thenReturn("42");
    when(element.getActivity()).thenReturn(new ExecuteScript());

    BuildProcessContext buildProcessContext = mock(BuildProcessContext.class);

    // Act
    AbstractFlowNodeBuilder<?, ?> actualBuildResult =
        activityFailedNodeBuilder.build(element, "parentId", activityBuilder, buildProcessContext);

    // Assert
    verify(activityBuilder).boundaryEvent();
    verify(boundaryEventBuilder).name("error_42");
    verify(namedBuilder).error();
    verify(errorBuilder).scriptTask();
    verify(scriptTaskBuilder6).id(null);
    verify(scriptTaskBuilder5).name(null);
    verify(scriptTaskBuilder4).camundaAsyncAfter();
    verify(scriptTaskBuilder).camundaExecutionListenerClass(eq("start"), isA(Class.class));
    verify(scriptTaskBuilder2).scriptFormat("groovy");
    verify(scriptTaskBuilder3).scriptText(null);
    assertSame(realScriptTaskBuilder, actualBuildResult);
  }
}
