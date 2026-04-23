package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.activity.ExecuteScript;
import com.symphony.bdk.workflow.swadl.v1.event.FormRepliedEvent;

import org.camunda.bpm.model.bpmn.builder.AbstractCatchEventBuilder;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.AbstractGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

@ExtendWith(MockitoExtension.class)
class ActivityExpiredNodeBuilderTest {

  @Mock
  private BuildProcessContext context;

  private ActivityExpiredNodeBuilder underTest;

  @BeforeEach
  void setUp() {
    underTest = new ActivityExpiredNodeBuilder();
  }

  @Test
  void shouldReturnActivityExpiredEventType() {
    assertThat(underTest.type()).isEqualTo(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT);
  }

  @Test
  void shouldRemoveLastSubProcessTimeoutBuilderWhenParentIsNonExclusiveFormReplyAndTimeoutSubProcessExists() {
    WorkflowNode parentNode = createNonExclusiveFormReplyNode("parentId");
    WorkflowNode element = createElementWithExecuteScriptActivity("elementId");
    setupParentInContext("elementId", "parentId", parentNode);

    AbstractFlowNodeBuilder<?, ?> timeoutBuilder = mock(AbstractFlowNodeBuilder.class, RETURNS_DEEP_STUBS);
    AbstractFlowNodeBuilder<?, ?> initialBuilder = mock(AbstractFlowNodeBuilder.class, RETURNS_DEEP_STUBS);

    when(context.hasTimeoutSubProcess()).thenReturn(true);
    doReturn(timeoutBuilder).when(context).removeLastSubProcessTimeoutBuilder();

    underTest.build(element, "parentId", initialBuilder, context);

    verify(context).removeLastSubProcessTimeoutBuilder();
  }

  @Test
  void shouldRemoveLastEventSubProcessBuilderWhenParentIsNonExclusiveFormReplyAndNoTimeoutSubProcess() {
    WorkflowNode parentNode = createNonExclusiveFormReplyNode("parentId");
    WorkflowNode element = createElementWithExecuteScriptActivity("elementId");
    setupParentInContext("elementId", "parentId", parentNode);

    EventSubProcessBuilder mockEventSubProcess = mock(EventSubProcessBuilder.class, RETURNS_DEEP_STUBS);
    SubProcessBuilder mockSubProcess = mock(SubProcessBuilder.class, RETURNS_DEEP_STUBS);
    AbstractFlowNodeBuilder<?, ?> initialBuilder = mock(AbstractFlowNodeBuilder.class, RETURNS_DEEP_STUBS);

    when(context.hasTimeoutSubProcess()).thenReturn(false);
    when(context.removeLastEventSubProcessBuilder()).thenReturn(mockEventSubProcess);
    when(mockEventSubProcess.subProcessDone()).thenReturn(mockSubProcess);

    underTest.build(element, "parentId", initialBuilder, context);

    verify(context).removeLastEventSubProcessBuilder();
  }

  @Test
  void shouldApplyTimerWithDurationWhenBuilderIsAbstractCatchEventBuilder() {
    WorkflowNode element = new WorkflowNode().id("elementId");
    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("PT1H");
    element.event(event);

    when(context.getParents("elementId")).thenReturn(List.of());

    AbstractCatchEventBuilder<?, ?> catchEventBuilder = mock(AbstractCatchEventBuilder.class, RETURNS_DEEP_STUBS);
    AbstractFlowNodeBuilder<?, ?> timerBuilder = mock(AbstractFlowNodeBuilder.class, RETURNS_DEEP_STUBS);
    doReturn(timerBuilder).when(catchEventBuilder).timerWithDuration("PT1H");

    AbstractFlowNodeBuilder<?, ?> result = underTest.build(element, null, catchEventBuilder, context);

    assertThat(result).isEqualTo(timerBuilder);
    verify(catchEventBuilder).timerWithDuration("PT1H");
  }

  @Test
  void shouldAddIntermediateCatchEventWhenBuilderIsAbstractGatewayBuilder() {
    WorkflowNode element = new WorkflowNode().id("elementId");
    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("PT30M");
    element.event(event);

    when(context.getParents("elementId")).thenReturn(List.of());

    AbstractGatewayBuilder<?, ?> gatewayBuilder = mock(AbstractGatewayBuilder.class, RETURNS_DEEP_STUBS);

    AbstractFlowNodeBuilder<?, ?> result = underTest.build(element, null, gatewayBuilder, context);

    assertThat(result).isNotNull();
    verify(gatewayBuilder).intermediateCatchEvent();
  }

  @Test
  void shouldReturnBuilderUnchangedWhenBuilderIsNeitherCatchEventNorGateway() {
    WorkflowNode element = new WorkflowNode().id("elementId");
    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("PT1H");
    element.event(event);

    when(context.getParents("elementId")).thenReturn(List.of());

    AbstractFlowNodeBuilder<?, ?> builder = mock(AbstractFlowNodeBuilder.class);

    AbstractFlowNodeBuilder<?, ?> result = underTest.build(element, null, builder, context);

    assertThat(result).isEqualTo(builder);
  }

  private WorkflowNode createNonExclusiveFormReplyNode(String id) {
    FormRepliedEvent formRepliedEvent = new FormRepliedEvent();
    formRepliedEvent.setExclusive(false);
    Event event = new Event();
    event.setFormReplied(formRepliedEvent);

    WorkflowNode node = new WorkflowNode().id(id);
    node.setElementType(WorkflowNodeType.FORM_REPLIED_EVENT);
    node.setEvent(event);
    return node;
  }

  private WorkflowNode createElementWithExecuteScriptActivity(String id) {
    ExecuteScript script = new ExecuteScript();
    script.setId(id + "-script");
    script.setScript("print('test')");

    return new WorkflowNode().id(id).activity(script);
  }

  private void setupParentInContext(String elementId, String parentId, WorkflowNode parentNode) {
    when(context.getParents(elementId)).thenReturn(List.of(parentId));
    when(context.readWorkflowNode(parentId)).thenReturn(parentNode);
  }
}
