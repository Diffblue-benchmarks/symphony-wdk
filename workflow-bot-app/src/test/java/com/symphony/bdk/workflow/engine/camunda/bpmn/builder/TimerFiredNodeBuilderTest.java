package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.event.TimerFiredEvent;

import org.camunda.bpm.model.bpmn.builder.AbstractCatchEventBuilder;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.EventBasedGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.IntermediateCatchEventBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimerFiredNodeBuilderTest {

  private TimerFiredNodeBuilder builder;
  private WorkflowNode workflowNode;
  private Event event;
  private TimerFiredEvent timerFiredEvent;
  private BuildProcessContext context;

  @BeforeEach
  void setUp() {
    builder = new TimerFiredNodeBuilder();
    workflowNode = new WorkflowNode();
    event = new Event();
    timerFiredEvent = new TimerFiredEvent();
    context = mock(BuildProcessContext.class);

    event.setTimerFired(timerFiredEvent);
    workflowNode.setEvent(event);
    workflowNode.setId("timer-node-1");
  }

  @SuppressWarnings("unchecked")
  @Test
  void shouldCreateEventBasedGatewayWhenBuilderIsNotCatchEvent() {
    AbstractFlowNodeBuilder<?, ?> mockBuilder = mock(AbstractFlowNodeBuilder.class);
    EventBasedGatewayBuilder mockGatewayBuilder = mock(EventBasedGatewayBuilder.class);
    IntermediateCatchEventBuilder mockCatchEventBuilder = mock(IntermediateCatchEventBuilder.class);
    IntermediateCatchEventBuilder mockTimerBuilder = mock(IntermediateCatchEventBuilder.class);
    IntermediateCatchEventBuilder mockNamedBuilder = mock(IntermediateCatchEventBuilder.class);

    doReturn(mockGatewayBuilder).when(mockBuilder).eventBasedGateway();
    doReturn(mockCatchEventBuilder).when(mockGatewayBuilder).intermediateCatchEvent();
    doReturn(mockTimerBuilder).when(mockCatchEventBuilder).timerWithDate("2024-01-01T10:00:00");
    doReturn(mockNamedBuilder).when(mockTimerBuilder).name("timer-node-1");

    timerFiredEvent.setAt("2024-01-01T10:00:00");

    AbstractFlowNodeBuilder<?, ?> result = builder.build(workflowNode, "parent-1", mockBuilder, context);

    assertThat(result).isEqualTo(mockNamedBuilder);
    verify(mockBuilder).eventBasedGateway();
    verify(mockGatewayBuilder).intermediateCatchEvent();
  }

  @SuppressWarnings("unchecked")
  @Test
  void shouldUseTimerWithCycleWhenRepeatIsNotNull() {
    AbstractCatchEventBuilder<?, ?> mockCatchEventBuilder = mock(AbstractCatchEventBuilder.class);
    AbstractCatchEventBuilder<?, ?> mockTimerBuilder = mock(AbstractCatchEventBuilder.class);
    AbstractCatchEventBuilder<?, ?> mockNamedBuilder = mock(AbstractCatchEventBuilder.class);

    doReturn(mockTimerBuilder).when(mockCatchEventBuilder).timerWithCycle("R/PT1H");
    doReturn(mockNamedBuilder).when(mockTimerBuilder).name("timer-node-1");

    timerFiredEvent.setRepeat("R/PT1H");

    AbstractFlowNodeBuilder<?, ?> result = builder.build(workflowNode, "parent-1", mockCatchEventBuilder, context);

    assertThat(result).isEqualTo(mockNamedBuilder);
    verify(mockCatchEventBuilder).timerWithCycle("R/PT1H");
  }

  @SuppressWarnings("unchecked")
  @Test
  void shouldUseTimerWithDateWhenRepeatIsNull() {
    AbstractCatchEventBuilder<?, ?> mockCatchEventBuilder = mock(AbstractCatchEventBuilder.class);
    AbstractCatchEventBuilder<?, ?> mockTimerBuilder = mock(AbstractCatchEventBuilder.class);
    AbstractCatchEventBuilder<?, ?> mockNamedBuilder = mock(AbstractCatchEventBuilder.class);

    doReturn(mockTimerBuilder).when(mockCatchEventBuilder).timerWithDate("2024-01-01T10:00:00");
    doReturn(mockNamedBuilder).when(mockTimerBuilder).name("timer-node-1");

    timerFiredEvent.setAt("2024-01-01T10:00:00");
    timerFiredEvent.setRepeat(null);

    AbstractFlowNodeBuilder<?, ?> result = builder.build(workflowNode, "parent-1", mockCatchEventBuilder, context);

    assertThat(result).isEqualTo(mockNamedBuilder);
    verify(mockCatchEventBuilder).timerWithDate("2024-01-01T10:00:00");
  }

  @SuppressWarnings("unchecked")
  @Test
  void shouldSetNodeNameOnBuilder() {
    AbstractCatchEventBuilder<?, ?> mockCatchEventBuilder = mock(AbstractCatchEventBuilder.class);
    AbstractCatchEventBuilder<?, ?> mockTimerBuilder = mock(AbstractCatchEventBuilder.class);
    AbstractCatchEventBuilder<?, ?> mockNamedBuilder = mock(AbstractCatchEventBuilder.class);

    doReturn(mockTimerBuilder).when(mockCatchEventBuilder).timerWithDate("2024-01-01T10:00:00");
    doReturn(mockNamedBuilder).when(mockTimerBuilder).name("custom-timer-id");

    timerFiredEvent.setAt("2024-01-01T10:00:00");
    workflowNode.setId("custom-timer-id");

    AbstractFlowNodeBuilder<?, ?> result = builder.build(workflowNode, "parent-1", mockCatchEventBuilder, context);

    assertThat(result).isEqualTo(mockNamedBuilder);
    verify(mockTimerBuilder).name("custom-timer-id");
  }

  @Test
  void shouldReturnTimerFiredEventType() {
    WorkflowNodeType result = builder.type();

    assertThat(result).isEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
  }
}
