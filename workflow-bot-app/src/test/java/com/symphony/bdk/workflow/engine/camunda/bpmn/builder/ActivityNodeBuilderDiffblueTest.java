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
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.swadl.v1.activity.BaseActivity;
import com.symphony.bdk.workflow.swadl.v1.activity.ExecuteScript;
import javax.imageio.metadata.IIOMetadataNode;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.ScriptTaskBuilder;
import org.camunda.bpm.model.bpmn.impl.BpmnModelInstanceImpl;
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

@ContextConfiguration(classes = {ActivityNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class ActivityNodeBuilderDiffblueTest {
  @Autowired private ActivityNodeBuilder activityNodeBuilder;

  /**
   * Test {@link ActivityNodeBuilder#type()}.
   *
   * <p>Method under test: {@link ActivityNodeBuilder#type()}
   */
  @Test
  @DisplayName("Test type()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodeType ActivityNodeBuilder.type()"})
  void testType() {
    // Arrange, Act and Assert
    assertEquals(WorkflowNodeType.ACTIVITY, activityNodeBuilder.type());
  }

  /**
   * Test {@link ActivityNodeBuilder#addTask(AbstractFlowNodeBuilder, BaseActivity)}.
   *
   * <p>Method under test: {@link ActivityNodeBuilder#addTask(AbstractFlowNodeBuilder,
   * BaseActivity)}
   */
  @Test
  @DisplayName("Test addTask(AbstractFlowNodeBuilder, BaseActivity)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "AbstractFlowNodeBuilder ActivityNodeBuilder.addTask(AbstractFlowNodeBuilder, BaseActivity)"
  })
  void testAddTask() {
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
    ScriptTaskBuilder scriptTaskBuilder2 =
        new ScriptTaskBuilder(modelInstance, new ScriptTaskImpl(context));
    when(scriptTaskBuilder.camundaExecutionListenerClass(
            Mockito.<String>any(), Mockito.<Class<Object>>any()))
        .thenReturn(scriptTaskBuilder2);

    ScriptTaskBuilder scriptTaskBuilder3 = mock(ScriptTaskBuilder.class);
    when(scriptTaskBuilder3.scriptFormat(Mockito.<String>any())).thenReturn(scriptTaskBuilder);

    ScriptTaskBuilder scriptTaskBuilder4 = mock(ScriptTaskBuilder.class);
    when(scriptTaskBuilder4.scriptText(Mockito.<String>any())).thenReturn(scriptTaskBuilder3);

    ScriptTaskBuilder scriptTaskBuilder5 = mock(ScriptTaskBuilder.class);
    when(scriptTaskBuilder5.camundaAsyncAfter()).thenReturn(scriptTaskBuilder4);

    ScriptTaskBuilder scriptTaskBuilder6 = mock(ScriptTaskBuilder.class);
    when(scriptTaskBuilder6.name(Mockito.<String>any())).thenReturn(scriptTaskBuilder5);

    ScriptTaskBuilder scriptTaskBuilder7 = mock(ScriptTaskBuilder.class);
    when(scriptTaskBuilder7.id(Mockito.<String>any())).thenReturn(scriptTaskBuilder6);

    AbstractFlowNodeBuilder<?, ?> eventBuilder = mock(AbstractFlowNodeBuilder.class);
    when(eventBuilder.scriptTask()).thenReturn(scriptTaskBuilder7);

    // Act
    AbstractFlowNodeBuilder<?, ?> actualAddTaskResult =
        activityNodeBuilder.addTask(eventBuilder, new ExecuteScript());

    // Assert
    verify(scriptTaskBuilder7).id(null);
    verify(scriptTaskBuilder6).name(null);
    verify(scriptTaskBuilder5).camundaAsyncAfter();
    verify(scriptTaskBuilder).camundaExecutionListenerClass(eq("start"), isA(Class.class));
    verify(eventBuilder).scriptTask();
    verify(scriptTaskBuilder3).scriptFormat("groovy");
    verify(scriptTaskBuilder4).scriptText(null);
    assertSame(scriptTaskBuilder2, actualAddTaskResult);
  }
}
