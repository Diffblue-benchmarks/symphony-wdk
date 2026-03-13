package com.symphony.bdk.workflow.engine.camunda.bpmn.builder;

import com.symphony.bdk.workflow.engine.camunda.CamundaExecutor;
import com.symphony.bdk.workflow.swadl.ActivityRegistry;
import com.symphony.bdk.workflow.swadl.v1.activity.message.SendMessage;
import org.camunda.bpm.model.bpmn.builder.AbstractFlowNodeBuilder;
import org.camunda.bpm.model.bpmn.builder.ServiceTaskBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ActivityNodeBuilderTest {

  private ActivityNodeBuilder activityNodeBuilder;

  @BeforeEach
  void setUp() {
    activityNodeBuilder = new ActivityNodeBuilder();
  }

  @Test
  @SuppressWarnings("unchecked")
  void addServiceTaskShouldConfigureServiceTaskWithActivityProperties() {
    SendMessage activity = new SendMessage();
    activity.setId("testActivity");

    AbstractFlowNodeBuilder<?, ?> mockBuilder = mock(AbstractFlowNodeBuilder.class);
    ServiceTaskBuilder mockServiceTaskBuilder = mock(ServiceTaskBuilder.class);

    when(mockBuilder.serviceTask()).thenReturn(mockServiceTaskBuilder);
    when(mockServiceTaskBuilder.id(anyString())).thenReturn(mockServiceTaskBuilder);
    when(mockServiceTaskBuilder.name(anyString())).thenReturn(mockServiceTaskBuilder);
    when(mockServiceTaskBuilder.camundaAsyncAfter()).thenReturn(mockServiceTaskBuilder);
    when(mockServiceTaskBuilder.camundaClass(CamundaExecutor.class)).thenReturn(mockServiceTaskBuilder);
    when(mockServiceTaskBuilder.camundaInputParameter(anyString(), anyString()))
        .thenReturn(mockServiceTaskBuilder);

    AbstractFlowNodeBuilder<?, ?> result = activityNodeBuilder.addTask(mockBuilder, activity);

    assertNotNull(result);
    verify(mockBuilder).serviceTask();
    verify(mockServiceTaskBuilder).id("testActivity");
    verify(mockServiceTaskBuilder).name("testActivity");
    verify(mockServiceTaskBuilder).camundaAsyncAfter();
    verify(mockServiceTaskBuilder).camundaClass(eq(CamundaExecutor.class));
    verify(mockServiceTaskBuilder).camundaInputParameter(eq(CamundaExecutor.EXECUTOR),
        eq(ActivityRegistry.getActivityExecutors().get(activity.getClass()).getName()));
    verify(mockServiceTaskBuilder).camundaInputParameter(eq(CamundaExecutor.ACTIVITY), eq("testActivity"));
  }

  @Test
  @SuppressWarnings("unchecked")
  void addServiceTaskShouldHandleDifferentActivityIds() {
    SendMessage activity = new SendMessage();
    activity.setId("customActivityId");

    AbstractFlowNodeBuilder<?, ?> mockBuilder = mock(AbstractFlowNodeBuilder.class);
    ServiceTaskBuilder mockServiceTaskBuilder = mock(ServiceTaskBuilder.class);

    when(mockBuilder.serviceTask()).thenReturn(mockServiceTaskBuilder);
    when(mockServiceTaskBuilder.id(anyString())).thenReturn(mockServiceTaskBuilder);
    when(mockServiceTaskBuilder.name(anyString())).thenReturn(mockServiceTaskBuilder);
    when(mockServiceTaskBuilder.camundaAsyncAfter()).thenReturn(mockServiceTaskBuilder);
    when(mockServiceTaskBuilder.camundaClass(CamundaExecutor.class)).thenReturn(mockServiceTaskBuilder);
    when(mockServiceTaskBuilder.camundaInputParameter(anyString(), anyString()))
        .thenReturn(mockServiceTaskBuilder);

    AbstractFlowNodeBuilder<?, ?> result = activityNodeBuilder.addTask(mockBuilder, activity);

    assertNotNull(result);
    verify(mockServiceTaskBuilder).id("customActivityId");
    verify(mockServiceTaskBuilder).name("customActivityId");
    verify(mockServiceTaskBuilder).camundaInputParameter(eq(CamundaExecutor.ACTIVITY), eq("customActivityId"));
  }
}
