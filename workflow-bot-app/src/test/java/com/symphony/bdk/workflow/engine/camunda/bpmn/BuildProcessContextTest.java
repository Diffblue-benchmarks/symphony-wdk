package com.symphony.bdk.workflow.engine.camunda.bpmn;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.symphony.bdk.workflow.engine.WorkflowDirectedGraph;

import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.BoundaryEventBuilder;
import org.camunda.bpm.model.bpmn.builder.EventSubProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.ProcessBuilder;
import org.camunda.bpm.model.bpmn.builder.StartEventBuilder;
import org.camunda.bpm.model.bpmn.builder.SubProcessBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuildProcessContextTest {

  private BuildProcessContext context;
  private WorkflowDirectedGraph workflowGraph;
  private ProcessBuilder processBuilder;

  @BeforeEach
  void setUp() {
    workflowGraph = mock(WorkflowDirectedGraph.class);
    processBuilder = mock(ProcessBuilder.class);
    context = new BuildProcessContext(workflowGraph, processBuilder);
  }

  @Test
  void shouldCreateContextWithGraphAndProcessBuilder() {
    assertThat(context).isNotNull();
  }

  @Test
  void shouldAddAndGetLastNodeBuilder() {
    AbstractFlowNodeBuilder<?, ?> builder = mock(AbstractFlowNodeBuilder.class);

    context.addLastNodeBuilder(builder);

    assertThat(context.getLastNodeBuilder()).isEqualTo(builder);
  }

  @Test
  void shouldReturnEmptyBuilderWhenLastNodeBuilderIsNull() {
    AbstractFlowNodeBuilder<?, ?> emptyBuilder = mock(AbstractFlowNodeBuilder.class);
    context.addNodeBuilder("", emptyBuilder);

    assertThat(context.getLastNodeBuilder()).isEqualTo(emptyBuilder);
  }

  @Test
  void shouldAddAndGetNodeBuilder() {
    AbstractFlowNodeBuilder<?, ?> builder = mock(AbstractFlowNodeBuilder.class);
    String nodeId = "node1";

    context.addNodeBuilder(nodeId, builder);

    assertThat(context.getNodeBuilder(nodeId)).isEqualTo(builder);
  }

  @Test
  void shouldCreateStartEventWhenNodeIdIsEmpty() {
    StartEventBuilder startEventBuilder = mock(StartEventBuilder.class);
    when(processBuilder.startEvent()).thenReturn(startEventBuilder);

    AbstractFlowNodeBuilder<?, ?> result = context.getNodeBuilder("");

    assertThat(result).isEqualTo(startEventBuilder);
  }

  @Test
  void shouldCreateStartEventWhenNodeIdIsNull() {
    StartEventBuilder startEventBuilder = mock(StartEventBuilder.class);
    when(processBuilder.startEvent()).thenReturn(startEventBuilder);

    AbstractFlowNodeBuilder<?, ?> result = context.getNodeBuilder(null);

    assertThat(result).isEqualTo(startEventBuilder);
  }

  @Test
  void shouldReturnTrueWhenNodeIsAlreadyBuilt() {
    AbstractFlowNodeBuilder<?, ?> builder = mock(AbstractFlowNodeBuilder.class);
    String nodeId = "node1";
    context.addNodeBuilder(nodeId, builder);

    boolean result = context.isAlreadyBuilt(nodeId);

    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenNodeIsNotBuilt() {
    boolean result = context.isAlreadyBuilt("nonexistent");

    assertThat(result).isFalse();
  }

  @Test
  void shouldCacheAndGetLastSubProcessBuilder() {
    SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);

    context.cacheSubProcess(subProcessBuilder);

    assertThat(context.getLastSubProcessBuilder()).isEqualTo(subProcessBuilder);
  }

  @Test
  void shouldCacheAndRemoveLastEventSubProcessBuilder() {
    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);

    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    assertThat(context.removeLastEventSubProcessBuilder()).isEqualTo(eventSubProcessBuilder);
  }

  @Test
  void shouldCacheAndRemoveLastSubProcessTimeoutBuilder() {
    SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);
    BoundaryEventBuilder boundaryBuilder = mock(BoundaryEventBuilder.class);
    BoundaryEventBuilder errorBuilder = mock(BoundaryEventBuilder.class);

    when(subProcessBuilder.boundaryEvent()).thenReturn(boundaryBuilder);
    when(boundaryBuilder.error("408")).thenReturn(errorBuilder);

    context.cacheSubProcessTimeoutToDone(subProcessBuilder);

    assertThat(context.removeLastSubProcessTimeoutBuilder()).isEqualTo(errorBuilder);
  }

  @Test
  void shouldReturnTrueWhenHasEventSubProcess() {
    EventSubProcessBuilder eventSubProcessBuilder = mock(EventSubProcessBuilder.class);
    context.cacheEventSubProcessToDone(eventSubProcessBuilder);

    boolean result = context.hasEventSubProcess();

    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenNoEventSubProcess() {
    boolean result = context.hasEventSubProcess();

    assertThat(result).isFalse();
  }

  @Test
  void shouldReturnTrueWhenHasTimeoutSubProcess() {
    SubProcessBuilder subProcessBuilder = mock(SubProcessBuilder.class);
    context.cacheSubProcessTimeoutToDone(subProcessBuilder);

    boolean result = context.hasTimeoutSubProcess();

    assertThat(result).isTrue();
  }

  @Test
  void shouldReturnFalseWhenNoTimeoutSubProcess() {
    boolean result = context.hasTimeoutSubProcess();

    assertThat(result).isFalse();
  }
}
