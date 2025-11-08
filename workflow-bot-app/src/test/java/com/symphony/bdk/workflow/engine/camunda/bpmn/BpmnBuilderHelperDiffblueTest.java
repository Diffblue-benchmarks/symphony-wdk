package com.symphony.bdk.workflow.engine.camunda.bpmn;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import javax.imageio.metadata.IIOMetadataNode;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.BoundaryEventBuilder;
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
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

class BpmnBuilderHelperDiffblueTest {
  /**
   * Method under test:
   * {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext, WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  void testHasActivitiesOnly() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    BuildProcessContext context = new BuildProcessContext(workflowGraph,
        new ProcessBuilder(modelInstance, new ProcessImpl(new ModelTypeInstanceContext(null, null, null))));

    // Act and Assert
    assertTrue(BpmnBuilderHelper.hasActivitiesOnly(context, new WorkflowDirectedGraph.NodeChildren()));
  }

  /**
   * Method under test:
   * {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  void testHasAllConditionalChildren() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    BuildProcessContext context = new BuildProcessContext(workflowGraph,
        new ProcessBuilder(modelInstance, new ProcessImpl(new ModelTypeInstanceContext(null, null, null))));

    // Act and Assert
    assertTrue(BpmnBuilderHelper.hasAllConditionalChildren(context, new WorkflowDirectedGraph.NodeChildren(), "42"));
  }

  /**
   * Method under test:
   * {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  void testHasConditionalString() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    BuildProcessContext context = new BuildProcessContext(workflowGraph,
        new ProcessBuilder(modelInstance, new ProcessImpl(new ModelTypeInstanceContext(null, null, null))));

    // Act and Assert
    assertFalse(BpmnBuilderHelper.hasConditionalString(context, new WorkflowDirectedGraph.NodeChildren(), "42"));
  }

  /**
   * Method under test:
   * {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  void testIsConditionalLoop() {
    // Arrange
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    DomElementImpl domElement = new DomElementImpl(new IIOMetadataNode());
    ModelInstanceImpl model2 = new ModelInstanceImpl(null, null, null);

    Class<ModelElementInstance> instanceType = ModelElementInstance.class;
    BoundaryEventBuilder builder = new BoundaryEventBuilder(modelInstance, new BoundaryEventImpl(
        new ModelTypeInstanceContext(domElement, model2, new ModelElementTypeImpl(null, "Name", instanceType))));

    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model3 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance2 = new BpmnModelInstanceImpl(model3, modelBuilder2,
        new DomDocumentImpl(mock(Document.class)));

    BuildProcessContext context = new BuildProcessContext(workflowGraph,
        new ProcessBuilder(modelInstance2, new ProcessImpl(new ModelTypeInstanceContext(null, null, null))));

    // Act and Assert
    assertFalse(BpmnBuilderHelper.isConditionalLoop(builder, context, new WorkflowDirectedGraph.NodeChildren()));
  }

  /**
   * Method under test:
   * {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)}
   */
  @Test
  void testHasLoopAfterSubProcess() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    BuildProcessContext context = new BuildProcessContext(workflowGraph,
        new ProcessBuilder(modelInstance, new ProcessImpl(new ModelTypeInstanceContext(null, null, null))));

    // Act and Assert
    assertFalse(BpmnBuilderHelper.hasLoopAfterSubProcess(context, new WorkflowDirectedGraph.NodeChildren(),
        WorkflowNodeType.TIMER_FIRED_EVENT));
  }

  /**
   * Method under test:
   * {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)}
   */
  @Test
  void testHasLoopAfterSubProcess2() {
    // Arrange
    WorkflowDirectedGraph workflowGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl modelInstance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    BuildProcessContext context = new BuildProcessContext(workflowGraph,
        new ProcessBuilder(modelInstance, new ProcessImpl(new ModelTypeInstanceContext(null, null, null))));

    // Act and Assert
    assertFalse(BpmnBuilderHelper.hasLoopAfterSubProcess(context, new WorkflowDirectedGraph.NodeChildren(),
        WorkflowNodeType.ACTIVITY_COMPLETED_EVENT));
  }
}
