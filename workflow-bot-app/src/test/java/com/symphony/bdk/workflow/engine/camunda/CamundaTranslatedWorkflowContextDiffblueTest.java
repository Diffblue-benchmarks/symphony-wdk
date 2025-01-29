package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.util.ArrayList;
import java.util.HashMap;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.BpmnModelInstanceImpl;
import org.camunda.bpm.model.xml.impl.ModelBuilderImpl;
import org.camunda.bpm.model.xml.impl.ModelImpl;
import org.camunda.bpm.model.xml.impl.instance.DomDocumentImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

class CamundaTranslatedWorkflowContextDiffblueTest {
  /**
   * Test {@link CamundaTranslatedWorkflowContext#equals(Object)}, and
   * {@link CamundaTranslatedWorkflowContext#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link CamundaTranslatedWorkflowContext#equals(Object)}
   *   <li>{@link CamundaTranslatedWorkflowContext#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    CamundaTranslatedWorkflowContext camundaTranslatedWorkflowContext = new CamundaTranslatedWorkflowContext(workflow,
        workflowDirectedGraph,
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class))));

    // Act and Assert
    assertEquals(camundaTranslatedWorkflowContext, camundaTranslatedWorkflowContext);
    int expectedHashCodeResult = camundaTranslatedWorkflowContext.hashCode();
    assertEquals(expectedHashCodeResult, camundaTranslatedWorkflowContext.hashCode());
  }

  /**
   * Test {@link CamundaTranslatedWorkflowContext#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaTranslatedWorkflowContext#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    CamundaTranslatedWorkflowContext camundaTranslatedWorkflowContext = new CamundaTranslatedWorkflowContext(workflow,
        workflowDirectedGraph,
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class))));
    Workflow workflow2 = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph2 = new WorkflowDirectedGraph("42");
    ModelImpl model2 = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder2 = new ModelBuilderImpl("Model Name");

    // Act and Assert
    assertNotEquals(camundaTranslatedWorkflowContext,
        new CamundaTranslatedWorkflowContext(workflow2, workflowDirectedGraph2,
            new BpmnModelInstanceImpl(model2, modelBuilder2, new DomDocumentImpl(mock(Document.class)))));
  }

  /**
   * Test {@link CamundaTranslatedWorkflowContext#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaTranslatedWorkflowContext#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    // Act and Assert
    assertNotEquals(new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class)))), null);
  }

  /**
   * Test {@link CamundaTranslatedWorkflowContext#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaTranslatedWorkflowContext#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    Workflow workflow = new Workflow();
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");

    // Act and Assert
    assertNotEquals(
        new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph,
            new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(mock(Document.class)))),
        "Different type to CamundaTranslatedWorkflowContext");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link CamundaTranslatedWorkflowContext#CamundaTranslatedWorkflowContext(Workflow, WorkflowDirectedGraph, BpmnModelInstance)}
   *   <li>{@link CamundaTranslatedWorkflowContext#getBpmnModelInstance()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow = new Workflow();
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);
    WorkflowDirectedGraph workflowDirectedGraph = new WorkflowDirectedGraph("42");
    ModelImpl model = new ModelImpl("Model Name");
    ModelBuilderImpl modelBuilder = new ModelBuilderImpl("Model Name");
    BpmnModelInstanceImpl instance = new BpmnModelInstanceImpl(model, modelBuilder,
        new DomDocumentImpl(mock(Document.class)));

    // Act
    CamundaTranslatedWorkflowContext actualCamundaTranslatedWorkflowContext = new CamundaTranslatedWorkflowContext(
        workflow, workflowDirectedGraph, instance);
    BpmnModelInstance actualBpmnModelInstance = actualCamundaTranslatedWorkflowContext.getBpmnModelInstance();

    // Assert
    assertSame(workflowDirectedGraph, actualCamundaTranslatedWorkflowContext.getWorkflowDirectedGraph());
    assertSame(workflow, actualCamundaTranslatedWorkflowContext.getWorkflow());
    assertSame(instance, actualBpmnModelInstance);
  }
}
