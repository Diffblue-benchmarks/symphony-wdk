package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import com.symphony.bdk.workflow.swadl.v1.EventWithTimeout;
import com.symphony.bdk.workflow.swadl.v1.activity.ExecuteScript;

import org.camunda.bpm.model.bpmn.builder.AbstractCatchEventBuilder;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.AbstractGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.BoundaryEventBuilder;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.IntermediateCatchEventBuilder;
import org.camunda.bpm.model.bpmn.builder.ScriptTaskBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

@ContextConfiguration(classes = {ActivityExpiredNodeBuilder.class})
@ExtendWith(SpringExtension.class)
class ActivityExpiredNodeBuilderDiffblueTest {
  @Autowired private ActivityExpiredNodeBuilder activityExpiredNodeBuilder;

  /**
   * Test {@link ActivityExpiredNodeBuilder#type()}.
   *
   * <p>Method under test: {@link ActivityExpiredNodeBuilder#type()}
   */
  @Test
  @DisplayName("Test type()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"WorkflowNodeType ActivityExpiredNodeBuilder.type()"})
  void testType() {
    // Arrange, Act and Assert
    assertEquals(WorkflowNodeType.ACTIVITY_EXPIRED_EVENT, activityExpiredNodeBuilder.type());
  }

  /**
   * Test {@link ActivityExpiredNodeBuilder#build} when there is a non-exclusive form reply parent
   * and a timeout subprocess is present.
   *
   * <p>Method under test: {@link ActivityExpiredNodeBuilder#build}
   */
  @Test
  @DisplayName("Test build() - non-exclusive form reply parent, has timeout subprocess")
  @SuppressWarnings("unchecked")
  void testBuild_hasNoExclusiveFormReplyParent_hasTimeoutSubProcess() {
    // Arrange
    BuildProcessContext context = mock(BuildProcessContext.class);
    WorkflowNode element = mock(WorkflowNode.class);
    WorkflowNode parentNode = mock(WorkflowNode.class);
    AbstractFlowNodeBuilder<?, ?> inputBuilder = mock(AbstractFlowNodeBuilder.class);

    when(element.getId()).thenReturn("nodeId");
    when(context.getParents("nodeId")).thenReturn(Collections.singletonList("parentId"));
    when(context.readWorkflowNode("parentId")).thenReturn(parentNode);
    when(parentNode.isNotExclusiveFormReply()).thenReturn(true);
    when(context.hasTimeoutSubProcess()).thenReturn(true);

    AbstractFlowNodeBuilder<?, ?> timeoutBuilder = mock(AbstractFlowNodeBuilder.class);
    doReturn(timeoutBuilder).when(context).removeLastSubProcessTimeoutBuilder();

    ScriptTaskBuilder stb6 = mock(ScriptTaskBuilder.class);
    ScriptTaskBuilder stb5 = mock(ScriptTaskBuilder.class);
    ScriptTaskBuilder stb4 = mock(ScriptTaskBuilder.class);
    ScriptTaskBuilder stb3 = mock(ScriptTaskBuilder.class);
    ScriptTaskBuilder stb2 = mock(ScriptTaskBuilder.class);
    ScriptTaskBuilder stbFinal = mock(ScriptTaskBuilder.class);
    ScriptTaskBuilder stb7 = mock(ScriptTaskBuilder.class);

    when(timeoutBuilder.scriptTask()).thenReturn(stb7);
    when(stb7.id(any())).thenReturn(stb6);
    when(stb6.name(any())).thenReturn(stb5);
    when(stb5.camundaAsyncAfter()).thenReturn(stb4);
    when(stb4.scriptText(any())).thenReturn(stb3);
    when(stb3.scriptFormat(any())).thenReturn(stb2);
    when(stb2.camundaExecutionListenerClass(any(), Mockito.<Class<Object>>any()))
        .thenReturn(stbFinal);

    when(element.getActivity()).thenReturn(new ExecuteScript());

    // Act
    AbstractFlowNodeBuilder<?, ?> result =
        activityExpiredNodeBuilder.build(element, "parentId", inputBuilder, context);

    // Assert
    assertSame(stbFinal, result);
  }

  /**
   * Test {@link ActivityExpiredNodeBuilder#build} when there is a non-exclusive form reply parent
   * and no timeout subprocess is present.
   *
   * <p>Method under test: {@link ActivityExpiredNodeBuilder#build}
   */
  @Test
  @DisplayName("Test build() - non-exclusive form reply parent, no timeout subprocess")
  @SuppressWarnings("unchecked")
  void testBuild_hasNoExclusiveFormReplyParent_noTimeoutSubProcess() {
    // Arrange
    BuildProcessContext context = mock(BuildProcessContext.class);
    WorkflowNode element = mock(WorkflowNode.class);
    WorkflowNode parentNode = mock(WorkflowNode.class);
    AbstractFlowNodeBuilder<?, ?> inputBuilder = mock(AbstractFlowNodeBuilder.class);

    when(element.getId()).thenReturn("nodeId");
    when(context.getParents("nodeId")).thenReturn(Collections.singletonList("parentId"));
    when(context.readWorkflowNode("parentId")).thenReturn(parentNode);
    when(parentNode.isNotExclusiveFormReply()).thenReturn(true);
    when(context.hasTimeoutSubProcess()).thenReturn(false);

    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);
    SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);
    BoundaryEventBuilder boundaryEventBuilder = mock(BoundaryEventBuilder.class);

    when(context.removeLastEventSubProcessBuilder()).thenReturn(eventSubProcessBuilder);
    when(eventSubProcessBuilder.subProcessDone()).thenReturn(subProcessBuilder);
    when(subProcessBuilder.boundaryEvent()).thenReturn(boundaryEventBuilder);
    when(boundaryEventBuilder.error(any())).thenReturn(boundaryEventBuilder);

    ScriptTaskBuilder stb6 = mock(ScriptTaskBuilder.class);
    ScriptTaskBuilder stb5 = mock(ScriptTaskBuilder.class);
    ScriptTaskBuilder stb4 = mock(ScriptTaskBuilder.class);
    ScriptTaskBuilder stb3 = mock(ScriptTaskBuilder.class);
    ScriptTaskBuilder stb2 = mock(ScriptTaskBuilder.class);
    ScriptTaskBuilder stbFinal = mock(ScriptTaskBuilder.class);
    ScriptTaskBuilder stb7 = mock(ScriptTaskBuilder.class);

    when(boundaryEventBuilder.scriptTask()).thenReturn(stb7);
    when(stb7.id(any())).thenReturn(stb6);
    when(stb6.name(any())).thenReturn(stb5);
    when(stb5.camundaAsyncAfter()).thenReturn(stb4);
    when(stb4.scriptText(any())).thenReturn(stb3);
    when(stb3.scriptFormat(any())).thenReturn(stb2);
    when(stb2.camundaExecutionListenerClass(any(), Mockito.<Class<Object>>any()))
        .thenReturn(stbFinal);

    when(element.getActivity()).thenReturn(new ExecuteScript());

    // Act
    AbstractFlowNodeBuilder<?, ?> result =
        activityExpiredNodeBuilder.build(element, "parentId", inputBuilder, context);

    // Assert
    assertSame(stbFinal, result);
  }

  /**
   * Test {@link ActivityExpiredNodeBuilder#build} when builder is an AbstractCatchEventBuilder.
   *
   * <p>Method under test: {@link ActivityExpiredNodeBuilder#build}
   */
  @Test
  @DisplayName("Test build() - no exclusive form reply parent, catch event builder")
  @SuppressWarnings("unchecked")
  void testBuild_noExclusiveFormReplyParent_catchEventBuilder() {
    // Arrange
    BuildProcessContext context = mock(BuildProcessContext.class);
    WorkflowNode element = mock(WorkflowNode.class);
    AbstractCatchEventBuilder<?, ?> catchEventBuilder = mock(AbstractCatchEventBuilder.class);
    AbstractCatchEventBuilder<?, ?> timerBuilder = mock(AbstractCatchEventBuilder.class);

    when(element.getId()).thenReturn("nodeId");
    when(context.getParents("nodeId")).thenReturn(Collections.emptyList());

    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("PT1H");
    when(element.getEvent()).thenReturn(event);
    when(catchEventBuilder.timerWithDuration("PT1H")).thenReturn((AbstractCatchEventBuilder) timerBuilder);

    // Act
    AbstractFlowNodeBuilder<?, ?> result =
        activityExpiredNodeBuilder.build(element, "parentId", catchEventBuilder, context);

    // Assert
    assertSame(timerBuilder, result);
  }

  /**
   * Test {@link ActivityExpiredNodeBuilder#build} when builder is an AbstractGatewayBuilder.
   *
   * <p>Method under test: {@link ActivityExpiredNodeBuilder#build}
   */
  @Test
  @DisplayName("Test build() - no exclusive form reply parent, gateway builder")
  @SuppressWarnings("unchecked")
  void testBuild_noExclusiveFormReplyParent_gatewayBuilder() {
    // Arrange
    BuildProcessContext context = mock(BuildProcessContext.class);
    WorkflowNode element = mock(WorkflowNode.class);
    AbstractGatewayBuilder<?, ?> gatewayBuilder = mock(AbstractGatewayBuilder.class);
    IntermediateCatchEventBuilder catchEventBuilder = mock(IntermediateCatchEventBuilder.class);
    IntermediateCatchEventBuilder namedBuilder = mock(IntermediateCatchEventBuilder.class);
    IntermediateCatchEventBuilder timerBuilder = mock(IntermediateCatchEventBuilder.class);

    when(element.getId()).thenReturn("nodeId");
    when(context.getParents("nodeId")).thenReturn(Collections.emptyList());

    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("PT1H");
    when(element.getEvent()).thenReturn(event);
    when(gatewayBuilder.intermediateCatchEvent()).thenReturn(catchEventBuilder);
    when(catchEventBuilder.name("nodeId")).thenReturn(namedBuilder);
    when(namedBuilder.timerWithDuration("PT1H")).thenReturn(timerBuilder);

    // Act
    AbstractFlowNodeBuilder<?, ?> result =
        activityExpiredNodeBuilder.build(element, "parentId", gatewayBuilder, context);

    // Assert
    assertSame(timerBuilder, result);
  }

  /**
   * Test {@link ActivityExpiredNodeBuilder#build} when builder is a generic AbstractFlowNodeBuilder
   * (neither CatchEvent nor Gateway).
   *
   * <p>Method under test: {@link ActivityExpiredNodeBuilder#build}
   */
  @Test
  @DisplayName("Test build() - no exclusive form reply parent, generic builder")
  void testBuild_noExclusiveFormReplyParent_genericBuilder() {
    // Arrange
    BuildProcessContext context = mock(BuildProcessContext.class);
    WorkflowNode element = mock(WorkflowNode.class);
    AbstractFlowNodeBuilder<?, ?> builder = mock(AbstractFlowNodeBuilder.class);

    when(element.getId()).thenReturn("nodeId");
    when(context.getParents("nodeId")).thenReturn(Collections.emptyList());

    EventWithTimeout event = new EventWithTimeout();
    event.setTimeout("PT1H");
    when(element.getEvent()).thenReturn(event);

    // Act
    AbstractFlowNodeBuilder<?, ?> result =
        activityExpiredNodeBuilder.build(element, "parentId", builder, context);

    // Assert
    assertSame(builder, result);
  }
}
