package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import com.symphony.bdk.workflow.swadl.v1.activity.Debug;
import com.symphony.bdk.workflow.swadl.v1.activity.ExecuteScript;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.instance.ScriptTask;
import org.camunda.bpm.model.bpmn.instance.ServiceTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ActivityNodeBuilderTest {

  private ActivityNodeBuilder activityNodeBuilder;

  @Mock
  private BuildProcessContext context;

  @BeforeEach
  void setUp() {
    activityNodeBuilder = new ActivityNodeBuilder();
  }

  @Test
  void shouldReturnActivityNodeType() {
    assertThat(activityNodeBuilder.type()).isEqualTo(WorkflowNodeType.ACTIVITY);
  }

  @Test
  void shouldBuildNodeByDelegatingToAddTask() {
    ExecuteScript scriptActivity = new ExecuteScript();
    scriptActivity.setId("myScript");
    scriptActivity.setScript("println 'hello'");

    WorkflowNode node = new WorkflowNode().activity(scriptActivity);
    AbstractFlowNodeBuilder<?, ?> builder = Bpmn.createExecutableProcess("testProcess").startEvent();

    AbstractFlowNodeBuilder<?, ?> result = activityNodeBuilder.build(node, null, builder, context);

    assertThat(result).isNotNull();
    BpmnModelInstance modelInstance = result.done();
    assertThat(modelInstance.getModelElementsByType(ScriptTask.class)).hasSize(1);
  }

  @Test
  void shouldAddScriptTaskWhenActivityIsExecuteScript() {
    ExecuteScript scriptActivity = new ExecuteScript();
    scriptActivity.setId("scriptTask1");
    scriptActivity.setScript("println 'test'");

    AbstractFlowNodeBuilder<?, ?> builder = Bpmn.createExecutableProcess("testProcess").startEvent();

    AbstractFlowNodeBuilder<?, ?> result = activityNodeBuilder.addTask(builder, scriptActivity);

    BpmnModelInstance modelInstance = result.done();
    assertThat(modelInstance.getModelElementsByType(ScriptTask.class)).hasSize(1);
    ScriptTask scriptTask = modelInstance.getModelElementsByType(ScriptTask.class).iterator().next();
    assertThat(scriptTask.getId()).isEqualTo("scriptTask1");
    assertThat(scriptTask.getName()).isEqualTo("scriptTask1");
    assertThat(scriptTask.getScriptFormat()).isEqualTo("groovy");
  }

  @Test
  void shouldAddServiceTaskWhenActivityIsNotExecuteScript() {
    Debug debugActivity = new Debug();
    debugActivity.setId("serviceTask1");

    AbstractFlowNodeBuilder<?, ?> builder = Bpmn.createExecutableProcess("testProcess").startEvent();

    AbstractFlowNodeBuilder<?, ?> result = activityNodeBuilder.addTask(builder, debugActivity);

    BpmnModelInstance modelInstance = result.done();
    assertThat(modelInstance.getModelElementsByType(ServiceTask.class)).hasSize(1);
    ServiceTask serviceTask = modelInstance.getModelElementsByType(ServiceTask.class).iterator().next();
    assertThat(serviceTask.getId()).isEqualTo("serviceTask1");
    assertThat(serviceTask.getName()).isEqualTo("serviceTask1");
  }
}
