package com.symphony.bdk.workflow.engine.camunda.bpmn;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.symphony.bdk.core.service.session.SessionService;
import com.symphony.bdk.workflow.engine.camunda.WorkflowDirectedGraphService;
import com.symphony.bdk.workflow.engine.camunda.bpmn.builder.WorkflowNodeBpmnBuilderRegistry;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import com.symphony.bdk.workflow.swadl.v1.Properties;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.xml.ModelValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CamundaBpmnBuilder.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CamundaBpmnBuilderDiffblueTest {
  @Autowired
  private CamundaBpmnBuilder camundaBpmnBuilder;

  @MockBean
  private RepositoryService repositoryService;

  @MockBean
  private SessionService sessionService;

  @MockBean
  private WorkflowDirectedGraphService workflowDirectedGraphService;

  @MockBean
  private WorkflowNodeBpmnBuilderRegistry workflowNodeBpmnBuilderRegistry;

  /**
   * Test {@link CamundaBpmnBuilder#translateWorkflow(Workflow)}.
   * <ul>
   *   <li>Given space.</li>
   *   <li>When {@link Workflow} {@link Workflow#getId()} return space.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaBpmnBuilder#translateWorkflow(Workflow)}
   */
  @Test
  @DisplayName("Test translateWorkflow(Workflow); given space; when Workflow getId() return space")
  void testTranslateWorkflow_givenSpace_whenWorkflowGetIdReturnSpace()
      throws JsonProcessingException, ModelValidationException {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);
    Workflow workflow = mock(Workflow.class);
    when(workflow.getVersion()).thenThrow(new ModelValidationException("An error occurred"));
    when(workflow.getId()).thenReturn(" ");
    doNothing().when(workflow).setActivities(Mockito.<List<Activity>>any());
    doNothing().when(workflow).setId(Mockito.<String>any());
    doNothing().when(workflow).setProperties(Mockito.<Properties>any());
    doNothing().when(workflow).setVariables(Mockito.<Map<String, Object>>any());
    doNothing().when(workflow).setVersion(Mockito.<Long>any());
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    // Act and Assert
    assertThrows(ModelValidationException.class, () -> camundaBpmnBuilder.translateWorkflow(workflow));
    verify(workflow, atLeast(1)).getId();
    verify(workflow).getVersion();
    verify(workflow).setActivities(isA(List.class));
    verify(workflow).setId(eq("42"));
    verify(workflow).setProperties(isA(Properties.class));
    verify(workflow).setVariables(isA(Map.class));
    verify(workflow).setVersion(eq(1L));
  }

  /**
   * Test {@link CamundaBpmnBuilder#translateWorkflow(Workflow)}.
   * <ul>
   *   <li>When {@link Workflow} {@link Workflow#getId()} return {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CamundaBpmnBuilder#translateWorkflow(Workflow)}
   */
  @Test
  @DisplayName("Test translateWorkflow(Workflow); when Workflow getId() return '42'")
  void testTranslateWorkflow_whenWorkflowGetIdReturn42() throws JsonProcessingException, ModelValidationException {
    // Arrange
    Properties properties = new Properties();
    properties.setPublish(true);
    Workflow workflow = mock(Workflow.class);
    when(workflow.getVersion()).thenThrow(new ModelValidationException("An error occurred"));
    when(workflow.getId()).thenReturn("42");
    doNothing().when(workflow).setActivities(Mockito.<List<Activity>>any());
    doNothing().when(workflow).setId(Mockito.<String>any());
    doNothing().when(workflow).setProperties(Mockito.<Properties>any());
    doNothing().when(workflow).setVariables(Mockito.<Map<String, Object>>any());
    doNothing().when(workflow).setVersion(Mockito.<Long>any());
    workflow.setActivities(new ArrayList<>());
    workflow.setId("42");
    workflow.setProperties(properties);
    workflow.setVariables(new HashMap<>());
    workflow.setVersion(1L);

    // Act and Assert
    assertThrows(ModelValidationException.class, () -> camundaBpmnBuilder.translateWorkflow(workflow));
    verify(workflow, atLeast(1)).getId();
    verify(workflow).getVersion();
    verify(workflow).setActivities(isA(List.class));
    verify(workflow).setId(eq("42"));
    verify(workflow).setProperties(isA(Properties.class));
    verify(workflow).setVariables(isA(Map.class));
    verify(workflow).setVersion(eq(1L));
  }
}
