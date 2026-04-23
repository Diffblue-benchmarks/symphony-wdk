package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import com.symphony.bdk.workflow.engine.camunda.bpmn.BuildProcessContext;
import com.symphony.bdk.workflow.swadl.v1.Event;
import com.symphony.bdk.workflow.swadl.v1.event.TimerFiredEvent;

import org.camunda.bpm.model.bpmn.builder.AbstractCatchEventBuilder;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.EventBasedGatewayBuilder;
import org.camunda.bpm.model.bpmn.builder.IntermediateCatchEventBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimerFiredNodeBuilderTest {

  @InjectMocks
  private TimerFiredNodeBuilder timerFiredNodeBuilder;

  @Mock
  private BuildProcessContext context;

  @Test
  void shouldReturnTimerFiredEventType() {
    assertThat(timerFiredNodeBuilder.type()).isEqualTo(WorkflowNodeType.TIMER_FIRED_EVENT);
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  void shouldBuildTimerWithCycleWhenRepeatIsSetAndBuilderIsCatchEventBuilder() {
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setRepeat("R3/PT10H");
    Event event = new Event();
    event.setTimerFired(timerFiredEvent);
    WorkflowNode element = new WorkflowNode();
    element.id("timer-node-1").event(event);

    IntermediateCatchEventBuilder catchEventBuilder = mock(IntermediateCatchEventBuilder.class);
    when(((AbstractCatchEventBuilder) catchEventBuilder).timerWithCycle("R3/PT10H")).thenReturn(catchEventBuilder);
    when(catchEventBuilder.name("timer-node-1")).thenReturn(catchEventBuilder);

    AbstractFlowNodeBuilder<?, ?> result = timerFiredNodeBuilder.build(element, "parentId", catchEventBuilder, context);

    assertThat(result).isEqualTo(catchEventBuilder);
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  void shouldBuildTimerWithDateWhenRepeatIsNullAndBuilderIsCatchEventBuilder() {
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setAt("2024-01-01T10:00:00Z");
    Event event = new Event();
    event.setTimerFired(timerFiredEvent);
    WorkflowNode element = new WorkflowNode();
    element.id("timer-node-2").event(event);

    IntermediateCatchEventBuilder catchEventBuilder = mock(IntermediateCatchEventBuilder.class);
    when(((AbstractCatchEventBuilder) catchEventBuilder).timerWithDate("2024-01-01T10:00:00Z")).thenReturn(catchEventBuilder);
    when(catchEventBuilder.name("timer-node-2")).thenReturn(catchEventBuilder);

    AbstractFlowNodeBuilder<?, ?> result = timerFiredNodeBuilder.build(element, "parentId", catchEventBuilder, context);

    assertThat(result).isEqualTo(catchEventBuilder);
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  void shouldAddEventBasedGatewayWhenBuilderIsNotCatchEventBuilderAndRepeatIsSet() {
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setRepeat("R/PT1H");
    Event event = new Event();
    event.setTimerFired(timerFiredEvent);
    WorkflowNode element = new WorkflowNode();
    element.id("timer-node-3").event(event);

    AbstractFlowNodeBuilder<?, ?> builder = mock(AbstractFlowNodeBuilder.class);
    EventBasedGatewayBuilder gatewayBuilder = mock(EventBasedGatewayBuilder.class);
    IntermediateCatchEventBuilder catchEventBuilder = mock(IntermediateCatchEventBuilder.class);

    when(builder.eventBasedGateway()).thenReturn(gatewayBuilder);
    when(gatewayBuilder.intermediateCatchEvent()).thenReturn(catchEventBuilder);
    when(((AbstractCatchEventBuilder) catchEventBuilder).timerWithCycle("R/PT1H")).thenReturn(catchEventBuilder);
    when(catchEventBuilder.name("timer-node-3")).thenReturn(catchEventBuilder);

    AbstractFlowNodeBuilder<?, ?> result = timerFiredNodeBuilder.build(element, "parentId", builder, context);

    assertThat(result).isEqualTo(catchEventBuilder);
  }

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  void shouldAddEventBasedGatewayWhenBuilderIsNotCatchEventBuilderAndRepeatIsNull() {
    TimerFiredEvent timerFiredEvent = new TimerFiredEvent();
    timerFiredEvent.setAt("2024-06-15T08:00:00Z");
    Event event = new Event();
    event.setTimerFired(timerFiredEvent);
    WorkflowNode element = new WorkflowNode();
    element.id("timer-node-4").event(event);

    AbstractFlowNodeBuilder<?, ?> builder = mock(AbstractFlowNodeBuilder.class);
    EventBasedGatewayBuilder gatewayBuilder = mock(EventBasedGatewayBuilder.class);
    IntermediateCatchEventBuilder catchEventBuilder = mock(IntermediateCatchEventBuilder.class);

    when(builder.eventBasedGateway()).thenReturn(gatewayBuilder);
    when(gatewayBuilder.intermediateCatchEvent()).thenReturn(catchEventBuilder);
    when(((AbstractCatchEventBuilder) catchEventBuilder).timerWithDate("2024-06-15T08:00:00Z")).thenReturn(catchEventBuilder);
    when(catchEventBuilder.name("timer-node-4")).thenReturn(catchEventBuilder);

    AbstractFlowNodeBuilder<?, ?> result = timerFiredNodeBuilder.build(element, "parentId", builder, context);

    assertThat(result).isEqualTo(catchEventBuilder);
  }
}
