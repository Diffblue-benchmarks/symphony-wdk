package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.WorkflowNode;
import com.symphony.bdk.workflow.engine.WorkflowNodeType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WorkflowNodeBpmnBuilderRegistryTest {

  @Test
  void constructorShouldInitializeFactoryWithBuilders() {
    WorkflowNodeBpmnBuilder builder1 = mock(WorkflowNodeBpmnBuilder.class);
    WorkflowNodeBpmnBuilder builder2 = mock(WorkflowNodeBpmnBuilder.class);
    when(builder1.type()).thenReturn(WorkflowNodeType.ACTIVITY);
    when(builder2.type()).thenReturn(WorkflowNodeType.TIMER_FIRED_EVENT);
    List<WorkflowNodeBpmnBuilder> builders = Arrays.asList(builder1, builder2);

    WorkflowNodeBpmnBuilderRegistry registry = new WorkflowNodeBpmnBuilderRegistry(builders);

    assertNotNull(registry);
  }

  @Test
  void getBuilderShouldReturnCorrectBuilderForActivityType() {
    WorkflowNodeBpmnBuilder activityBuilder = mock(WorkflowNodeBpmnBuilder.class);
    when(activityBuilder.type()).thenReturn(WorkflowNodeType.ACTIVITY);
    List<WorkflowNodeBpmnBuilder> builders = Arrays.asList(activityBuilder);
    WorkflowNodeBpmnBuilderRegistry registry = new WorkflowNodeBpmnBuilderRegistry(builders);
    WorkflowNode node = new WorkflowNode();
    node.setElementType(WorkflowNodeType.ACTIVITY);

    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    assertEquals(activityBuilder, result);
  }

  @Test
  void getBuilderShouldReturnCorrectBuilderForTimerFiredEventType() {
    WorkflowNodeBpmnBuilder timerBuilder = mock(WorkflowNodeBpmnBuilder.class);
    when(timerBuilder.type()).thenReturn(WorkflowNodeType.TIMER_FIRED_EVENT);
    List<WorkflowNodeBpmnBuilder> builders = Arrays.asList(timerBuilder);
    WorkflowNodeBpmnBuilderRegistry registry = new WorkflowNodeBpmnBuilderRegistry(builders);
    WorkflowNode node = new WorkflowNode();
    node.setElementType(WorkflowNodeType.TIMER_FIRED_EVENT);

    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    assertEquals(timerBuilder, result);
  }

  @Test
  void getBuilderShouldReturnNullWhenNoBuilderRegistered() {
    WorkflowNodeBpmnBuilder activityBuilder = mock(WorkflowNodeBpmnBuilder.class);
    when(activityBuilder.type()).thenReturn(WorkflowNodeType.ACTIVITY);
    List<WorkflowNodeBpmnBuilder> builders = Arrays.asList(activityBuilder);
    WorkflowNodeBpmnBuilderRegistry registry = new WorkflowNodeBpmnBuilderRegistry(builders);
    WorkflowNode node = new WorkflowNode();
    node.setElementType(WorkflowNodeType.SIGNAL_EVENT);

    WorkflowNodeBpmnBuilder result = registry.getBuilder(node);

    assertNull(result);
  }

  @Test
  void constructorShouldHandleMultipleBuildersOfDifferentTypes() {
    WorkflowNodeBpmnBuilder activityBuilder = mock(WorkflowNodeBpmnBuilder.class);
    WorkflowNodeBpmnBuilder timerBuilder = mock(WorkflowNodeBpmnBuilder.class);
    WorkflowNodeBpmnBuilder signalBuilder = mock(WorkflowNodeBpmnBuilder.class);
    when(activityBuilder.type()).thenReturn(WorkflowNodeType.ACTIVITY);
    when(timerBuilder.type()).thenReturn(WorkflowNodeType.TIMER_FIRED_EVENT);
    when(signalBuilder.type()).thenReturn(WorkflowNodeType.SIGNAL_EVENT);
    List<WorkflowNodeBpmnBuilder> builders = Arrays.asList(activityBuilder, timerBuilder, signalBuilder);

    WorkflowNodeBpmnBuilderRegistry registry = new WorkflowNodeBpmnBuilderRegistry(builders);
    WorkflowNode activityNode = new WorkflowNode();
    activityNode.setElementType(WorkflowNodeType.ACTIVITY);
    WorkflowNode signalNode = new WorkflowNode();
    signalNode.setElementType(WorkflowNodeType.SIGNAL_EVENT);

    assertEquals(activityBuilder, registry.getBuilder(activityNode));
    assertEquals(signalBuilder, registry.getBuilder(signalNode));
  }
}
