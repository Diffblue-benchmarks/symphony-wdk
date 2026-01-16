package com.symphony.bdk.workflow.engine.camunda;

import com.symphony.bdk.workflow.engine.camunda.bpmn.CamundaBpmnBuilder;
import com.symphony.bdk.workflow.engine.handler.audit.AuditTrailLogAction;
import com.symphony.bdk.workflow.event.RealTimeEventProcessor;
import com.symphony.bdk.workflow.swadl.exception.UniqueIdViolationException;
import com.symphony.bdk.workflow.swadl.v1.Activity;
import com.symphony.bdk.workflow.swadl.v1.Workflow;
import com.symphony.bdk.workflow.swadl.v1.activity.message.SendMessage;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.xml.ModelValidationException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CamundaEngineClaude_translateTest {

  @Test
  void translate_withValidWorkflow_shouldReturnTranslatedContext() throws Exception {
    // Given: A valid workflow with unique activity IDs
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);

    Workflow workflow = createWorkflow("testWorkflow");
    Activity activity1 = createActivityWrapper("activity1");
    workflow.setActivities(Collections.singletonList(activity1));

    CamundaTranslatedWorkflowContext expectedContext = mock(CamundaTranslatedWorkflowContext.class);
    when(bpmnBuilder.translateWorkflow(workflow)).thenReturn(expectedContext);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: translate is called
    CamundaTranslatedWorkflowContext result = engine.translate(workflow);

    // Then: Should return the translated context and call bpmnBuilder
    assertThat(result).isEqualTo(expectedContext);
    verify(bpmnBuilder).translateWorkflow(workflow);
  }

  @Test
  void translate_withMultipleUniqueActivities_shouldReturnTranslatedContext() throws Exception {
    // Given: A workflow with multiple unique activity IDs
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);

    Workflow workflow = createWorkflow("testWorkflow");
    List<Activity> activities = new ArrayList<>();
    activities.add(createActivityWrapper("activity1"));
    activities.add(createActivityWrapper("activity2"));
    activities.add(createActivityWrapper("activity3"));
    workflow.setActivities(activities);

    CamundaTranslatedWorkflowContext expectedContext = mock(CamundaTranslatedWorkflowContext.class);
    when(bpmnBuilder.translateWorkflow(workflow)).thenReturn(expectedContext);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: translate is called with multiple unique activities
    CamundaTranslatedWorkflowContext result = engine.translate(workflow);

    // Then: Should return the translated context
    assertThat(result).isEqualTo(expectedContext);
    verify(bpmnBuilder).translateWorkflow(workflow);
  }

  @Test
  void translate_withEmptyActivitiesList_shouldReturnTranslatedContext() throws Exception {
    // Given: A workflow with no activities
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);

    Workflow workflow = createWorkflow("testWorkflow");
    workflow.setActivities(Collections.emptyList());

    CamundaTranslatedWorkflowContext expectedContext = mock(CamundaTranslatedWorkflowContext.class);
    when(bpmnBuilder.translateWorkflow(workflow)).thenReturn(expectedContext);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: translate is called with empty activities
    CamundaTranslatedWorkflowContext result = engine.translate(workflow);

    // Then: Should return the translated context
    assertThat(result).isEqualTo(expectedContext);
    verify(bpmnBuilder).translateWorkflow(workflow);
  }

  @Test
  void translate_withDuplicateActivityIds_shouldThrowUniqueIdViolationException() {
    // Given: A workflow with duplicate activity IDs
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);

    Workflow workflow = createWorkflow("testWorkflow");
    List<Activity> activities = new ArrayList<>();
    activities.add(createActivityWrapper("duplicateId"));
    activities.add(createActivityWrapper("duplicateId"));
    workflow.setActivities(activities);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When/Then: translate should throw UniqueIdViolationException
    assertThatThrownBy(() -> engine.translate(workflow))
        .isInstanceOf(UniqueIdViolationException.class)
        .hasMessageContaining("duplicateId")
        .hasMessageContaining("testWorkflow");
  }

  @Test
  void translate_withMultipleDuplicateActivityIds_shouldThrowUniqueIdViolationException() {
    // Given: A workflow with multiple sets of duplicate activity IDs
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);

    Workflow workflow = createWorkflow("testWorkflow");
    List<Activity> activities = new ArrayList<>();
    activities.add(createActivityWrapper("duplicate1"));
    activities.add(createActivityWrapper("duplicate1"));
    activities.add(createActivityWrapper("duplicate2"));
    activities.add(createActivityWrapper("duplicate2"));
    activities.add(createActivityWrapper("unique1"));
    workflow.setActivities(activities);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When/Then: translate should throw UniqueIdViolationException with all duplicates
    assertThatThrownBy(() -> engine.translate(workflow))
        .isInstanceOf(UniqueIdViolationException.class)
        .hasMessageContaining("duplicate1")
        .hasMessageContaining("duplicate2")
        .hasMessageContaining("testWorkflow");
  }

  @Test
  void translate_withThreeDuplicateActivityIds_shouldThrowUniqueIdViolationException() {
    // Given: A workflow with three activities having the same ID
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);

    Workflow workflow = createWorkflow("testWorkflow");
    List<Activity> activities = new ArrayList<>();
    activities.add(createActivityWrapper("triplicateId"));
    activities.add(createActivityWrapper("triplicateId"));
    activities.add(createActivityWrapper("triplicateId"));
    workflow.setActivities(activities);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When/Then: translate should throw UniqueIdViolationException
    assertThatThrownBy(() -> engine.translate(workflow))
        .isInstanceOf(UniqueIdViolationException.class)
        .hasMessageContaining("triplicateId")
        .hasMessageContaining("testWorkflow");
  }

  @Test
  void translate_whenBpmnBuilderThrowsJsonProcessingException_shouldWrapInIllegalArgumentException()
      throws Exception {
    // Given: bpmnBuilder throws JsonProcessingException
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);

    Workflow workflow = createWorkflow("testWorkflow");
    Activity activity1 = createActivityWrapper("activity1");
    workflow.setActivities(Collections.singletonList(activity1));

    com.fasterxml.jackson.core.JsonProcessingException jsonException =
        mock(com.fasterxml.jackson.core.JsonProcessingException.class);
    when(bpmnBuilder.translateWorkflow(workflow)).thenThrow(jsonException);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When/Then: translate should wrap JsonProcessingException in IllegalArgumentException
    assertThatThrownBy(() -> engine.translate(workflow))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Workflow parsing process failed")
        .hasMessageContaining("testWorkflow")
        .hasMessageContaining("may not be a valid workflow")
        .hasCause(jsonException);
  }

  @Test
  void translate_whenBpmnBuilderThrowsModelValidationException_shouldWrapInIllegalArgumentException()
      throws Exception {
    // Given: bpmnBuilder throws ModelValidationException
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);

    Workflow workflow = createWorkflow("testWorkflow");
    Activity activity1 = createActivityWrapper("activity1");
    workflow.setActivities(Collections.singletonList(activity1));

    ModelValidationException validationException = mock(ModelValidationException.class);
    when(bpmnBuilder.translateWorkflow(workflow)).thenThrow(validationException);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When/Then: translate should wrap ModelValidationException in IllegalArgumentException
    assertThatThrownBy(() -> engine.translate(workflow))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Workflow parsing process failed")
        .hasMessageContaining("testWorkflow")
        .hasMessageContaining("may not be a valid workflow")
        .hasCause(validationException);
  }

  @Test
  void translate_withDuplicateCheckPassingButBpmnBuilderFailing_shouldThrowIllegalArgumentException()
      throws Exception {
    // Given: Activities are unique but bpmnBuilder fails
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);

    Workflow workflow = createWorkflow("testWorkflow");
    List<Activity> activities = new ArrayList<>();
    activities.add(createActivityWrapper("activity1"));
    activities.add(createActivityWrapper("activity2"));
    workflow.setActivities(activities);

    com.fasterxml.jackson.core.JsonProcessingException jsonException =
        mock(com.fasterxml.jackson.core.JsonProcessingException.class);
    when(bpmnBuilder.translateWorkflow(workflow)).thenThrow(jsonException);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When/Then: Should throw IllegalArgumentException wrapping the JsonProcessingException
    assertThatThrownBy(() -> engine.translate(workflow))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Workflow parsing process failed")
        .hasCause(jsonException);
  }

  @Test
  void translate_calledMultipleTimes_shouldInvokeBpmnBuilderEachTime() throws Exception {
    // Given: Valid workflow setup
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);

    Workflow workflow = createWorkflow("testWorkflow");
    Activity activity1 = createActivityWrapper("activity1");
    workflow.setActivities(Collections.singletonList(activity1));

    CamundaTranslatedWorkflowContext expectedContext = mock(CamundaTranslatedWorkflowContext.class);
    when(bpmnBuilder.translateWorkflow(any())).thenReturn(expectedContext);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: translate is called multiple times
    engine.translate(workflow);
    engine.translate(workflow);

    // Then: bpmnBuilder should be invoked each time
    verify(bpmnBuilder, org.mockito.Mockito.times(2)).translateWorkflow(workflow);
  }

  @Test
  void translate_withDifferentWorkflows_shouldTranslateBoth() throws Exception {
    // Given: Two different workflows
    RepositoryService repositoryService = mock(RepositoryService.class);
    CamundaBpmnBuilder bpmnBuilder = mock(CamundaBpmnBuilder.class);
    AuditTrailLogAction auditTrailLogger = mock(AuditTrailLogAction.class);

    Workflow workflow1 = createWorkflow("workflow1");
    workflow1.setActivities(Collections.singletonList(createActivityWrapper("activity1")));

    Workflow workflow2 = createWorkflow("workflow2");
    workflow2.setActivities(Collections.singletonList(createActivityWrapper("activity2")));

    CamundaTranslatedWorkflowContext context1 = mock(CamundaTranslatedWorkflowContext.class);
    CamundaTranslatedWorkflowContext context2 = mock(CamundaTranslatedWorkflowContext.class);
    when(bpmnBuilder.translateWorkflow(workflow1)).thenReturn(context1);
    when(bpmnBuilder.translateWorkflow(workflow2)).thenReturn(context2);

    CamundaEngine engine = new CamundaEngine(repositoryService, bpmnBuilder, Collections.emptyList(),
        auditTrailLogger);

    // When: Both workflows are translated
    CamundaTranslatedWorkflowContext result1 = engine.translate(workflow1);
    CamundaTranslatedWorkflowContext result2 = engine.translate(workflow2);

    // Then: Both should be translated correctly
    assertThat(result1).isEqualTo(context1);
    assertThat(result2).isEqualTo(context2);
    verify(bpmnBuilder).translateWorkflow(workflow1);
    verify(bpmnBuilder).translateWorkflow(workflow2);
  }

  // Helper methods

  private Workflow createWorkflow(String id) {
    Workflow workflow = new Workflow();
    workflow.setId(id);
    return workflow;
  }

  private Activity createActivityWrapper(String id) {
    Activity wrapper = new Activity();
    SendMessage activity = new SendMessage();
    activity.setId(id);
    wrapper.setImplementation(activity);
    return wrapper;
  }
}
