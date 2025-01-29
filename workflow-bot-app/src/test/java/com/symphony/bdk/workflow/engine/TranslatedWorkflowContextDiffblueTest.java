package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertSame;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TranslatedWorkflowContextDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TranslatedWorkflowContext#TranslatedWorkflowContext(Workflow, WorkflowDirectedGraph)}
   *   <li>{@link TranslatedWorkflowContext#getWorkflow()}
   *   <li>{@link TranslatedWorkflowContext#getWorkflowDirectedGraph()}
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

    // Act
    TranslatedWorkflowContext actualTranslatedWorkflowContext = new TranslatedWorkflowContext(workflow,
        workflowDirectedGraph);
    Workflow actualWorkflow = actualTranslatedWorkflowContext.getWorkflow();

    // Assert
    assertSame(workflowDirectedGraph, actualTranslatedWorkflowContext.getWorkflowDirectedGraph());
    assertSame(workflow, actualWorkflow);
  }
}
