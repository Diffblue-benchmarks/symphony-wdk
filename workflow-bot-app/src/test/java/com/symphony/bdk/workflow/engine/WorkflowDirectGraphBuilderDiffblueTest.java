package com.symphony.bdk.workflow.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {WorkflowDirectGraphBuilder.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class WorkflowDirectGraphBuilderDiffblueTest {
  @MockBean
  private SessionService sessionService;

  @MockBean
  private Workflow workflow;

  @Autowired
  private WorkflowDirectGraphBuilder workflowDirectGraphBuilder;

  /**
   * Test {@link WorkflowDirectGraphBuilder#build()}.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link WorkflowDirectGraphBuilder#build()}
   *   <li>{@link WorkflowDirectGraphBuilder#WorkflowDirectGraphBuilder(Workflow, SessionService)}
   * </ul>
   */
  @Test
  @DisplayName("Test build()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void WorkflowDirectGraphBuilder.<init>(Workflow, SessionService)",
      "WorkflowDirectedGraph WorkflowDirectGraphBuilder.build()"})
  void testBuild() {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);

    Workflow workflow2 = new Workflow();
    workflow2.setActivities(new ArrayList<>());
    workflow2.setId("42");
    workflow2.setProperties(properties);
    workflow2.setVariables(new HashMap<>());
    workflow2.setVersion(1L);

    // Act
    WorkflowDirectedGraph actualBuildResult = (new WorkflowDirectGraphBuilder(workflow2, sessionService)).build();

    // Assert
    assertEquals("42", actualBuildResult.getWorkflowId());
    assertEquals(1L, actualBuildResult.getVersion().longValue());
    assertTrue(actualBuildResult.getStartEvents().isEmpty());
    assertTrue(actualBuildResult.getDictionary().isEmpty());
    assertTrue(actualBuildResult.getParents().isEmpty());
    assertTrue(actualBuildResult.getVariables().isEmpty());
  }
}
