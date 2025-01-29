package com.symphony.bdk.workflow.engine.camunda.bpmn;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph.NodeChildren;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

class BpmnBuilderHelperDiffblueTest {
  /**
   * Test
   * {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext, NodeChildren)}.
   * <ul>
   *   <li>When {@link NodeChildren#NodeChildren()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnBuilderHelper#hasActivitiesOnly(BuildProcessContext, WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName("Test hasActivitiesOnly(BuildProcessContext, NodeChildren); when NodeChildren(); then return 'true'")
  void testHasActivitiesOnly_whenNodeChildren_thenReturnTrue() {
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
   * Test
   * {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext, NodeChildren, String)}.
   * <ul>
   *   <li>When {@link NodeChildren#NodeChildren()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnBuilderHelper#hasAllConditionalChildren(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName("Test hasAllConditionalChildren(BuildProcessContext, NodeChildren, String); when NodeChildren(); then return 'true'")
  void testHasAllConditionalChildren_whenNodeChildren_thenReturnTrue() {
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
   * Test
   * {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext, NodeChildren, String)}.
   * <ul>
   *   <li>When {@link NodeChildren#NodeChildren()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnBuilderHelper#hasConditionalString(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, String)}
   */
  @Test
  @DisplayName("Test hasConditionalString(BuildProcessContext, NodeChildren, String); when NodeChildren(); then return 'false'")
  void testHasConditionalString_whenNodeChildren_thenReturnFalse() {
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
   * Test
   * {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, NodeChildren)}.
   * <p>
   * Method under test:
   * {@link BpmnBuilderHelper#isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, WorkflowDirectedGraph.NodeChildren)}
   */
  @Test
  @DisplayName("Test isConditionalLoop(AbstractFlowNodeBuilder, BuildProcessContext, NodeChildren)")
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
   * Test
   * {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext, NodeChildren, WorkflowNodeType)}.
   * <ul>
   *   <li>When {@code ACTIVITY_COMPLETED_EVENT}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)}
   */
  @Test
  @DisplayName("Test hasLoopAfterSubProcess(BuildProcessContext, NodeChildren, WorkflowNodeType); when 'ACTIVITY_COMPLETED_EVENT'")
  void testHasLoopAfterSubProcess_whenActivityCompletedEvent() {
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

  /**
   * Test
   * {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext, NodeChildren, WorkflowNodeType)}.
   * <ul>
   *   <li>When {@code TIMER_FIRED_EVENT}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BpmnBuilderHelper#hasLoopAfterSubProcess(BuildProcessContext, WorkflowDirectedGraph.NodeChildren, WorkflowNodeType)}
   */
  @Test
  @DisplayName("Test hasLoopAfterSubProcess(BuildProcessContext, NodeChildren, WorkflowNodeType); when 'TIMER_FIRED_EVENT'")
  void testHasLoopAfterSubProcess_whenTimerFiredEvent() {
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
}
