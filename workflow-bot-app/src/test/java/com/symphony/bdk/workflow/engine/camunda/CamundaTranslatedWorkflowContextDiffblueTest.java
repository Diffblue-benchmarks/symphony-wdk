package com.symphony.bdk.workflow.engine.camunda;

import static org.junit.jupiter.api.Assertions.assertSame;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
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
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class CamundaTranslatedWorkflowContextDiffblueTest {
  /**
   * Test {@link CamundaTranslatedWorkflowContext#CamundaTranslatedWorkflowContext(Workflow,
   * WorkflowDirectedGraph, BpmnModelInstance)}.
   *
   * <p>Method under test: {@link
   * CamundaTranslatedWorkflowContext#CamundaTranslatedWorkflowContext(Workflow,
   * WorkflowDirectedGraph, BpmnModelInstance)}
   */
  @Test
  @DisplayName(
      "Test new CamundaTranslatedWorkflowContext(Workflow, WorkflowDirectedGraph, BpmnModelInstance)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CamundaTranslatedWorkflowContext.<init>(Workflow, WorkflowDirectedGraph, BpmnModelInstance)"
  })
  void testNewCamundaTranslatedWorkflowContext() {
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

    BpmnModelInstanceImpl instance =
        new BpmnModelInstanceImpl(model, modelBuilder, new DomDocumentImpl(null));

    // Act
    CamundaTranslatedWorkflowContext actualCamundaTranslatedWorkflowContext =
        new CamundaTranslatedWorkflowContext(workflow, workflowDirectedGraph, instance);

    // Assert
    assertSame(
        workflowDirectedGraph, actualCamundaTranslatedWorkflowContext.getWorkflowDirectedGraph());
    assertSame(workflow, actualCamundaTranslatedWorkflowContext.getWorkflow());
    assertSame(instance, actualCamundaTranslatedWorkflowContext.getBpmnModelInstance());
  }
}
